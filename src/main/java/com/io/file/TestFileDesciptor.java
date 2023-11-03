package com.io.file;


import cn.hutool.core.date.TimeInterval;
import com.util.MyDateUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//测试文件描述符,在Windows平台是fd是-1,handle是wind的handle

/**
 *  Files.write 测试
 * 固态加锁写入测试
 * 2023-11-03 14:32:22 [Thread-0]-> 线程:Thread-0已追加写入10000行，字符串测试长度:10000,耗时:6466.0毫秒,6.47秒,0.11分钟
 * 2023-11-03 14:32:23 [Thread-1]-> 线程:Thread-1已追加写入10000行，字符串测试长度:10000,耗时:6742.0毫秒,6.74秒,0.11分钟
 * <p>
 * 固态不加锁测试
 * 2023-11-03 14:31:15 [Thread-1]-> 线程:Thread-1已追加写入10000行，字符串测试长度:10000,耗时:4950.0毫秒,4.95秒,0.08分钟
 * 2023-11-03 14:31:15 [Thread-0]-> 线程:Thread-0已追加写入10000行，字符串测试长度:10000,耗时:5020.0毫秒,5.02秒,0.08分钟
 *
 *
 * bufferedWriter 测试
 * 固态不加锁写入测试，如过加锁性能反而会更快，因为是多次批量写入，（能达到0.8秒，但是实际观测起来不及时，都是成批写入）
 * 2023-11-03 15:54:34 [pool-1-thread-2]-> 线程:pool-1-thread-2已追加写入10000行，字符串测试长度:10000,耗时:1014.0毫秒,1.01秒,0.02分钟
 * 2023-11-03 15:54:34 [pool-1-thread-1]-> 线程:pool-1-thread-1已追加写入10000行，字符串测试长度:10000,耗时:1014.0毫秒,1.01秒,0.02分钟
 */
@Slf4j
public class TestFileDesciptor {
    //测试在win和linux平台fd 和handle的不同
    public static void test() {
        //   File file=new File(TestFileDesciptor.class.getResource("/test.txt").getPath()); //有斜杠则是根路径
        try {
            System.out.println(TestFileDesciptor.class.getResource("/test.txt").getPath());
            FileInputStream fileInputStream = new FileInputStream(TestFileDesciptor.class.getResource("/test.txt").getPath());
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            Field[] fields = FileDescriptor.class.getDeclaredFields();
            StringBuffer stringBuffer = new StringBuffer();
            //获取私有属性
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                try {
                    stringBuffer.append(fields[i].getName()).append(":").append(fields[i].get(fileDescriptor)).append("\r\n");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }


            System.out.println(stringBuffer.toString());
            System.out.println("开始打开文件");
            int num = 1;
            String path;
            TimeInterval timer = new TimeInterval(true);

            for (int i = 0; i < num; i++) {
                fileInputStream = new FileInputStream(TestFileDesciptor.class.getResource("/test2.txt").getPath());
                // fileInputStream=new FileInputStream("D:\\zw\\mgJavaNote\\src\\main\\resources\\test.txt");
                //  path=TestFileDesciptor.class.getResource("/test.txt").getPath();

            }

            //System.out.println(num+"个文件全部打开完毕");
            System.out.println(num + "次" + timer.interval());//花费毫秒数
            //通过FileDescriptor获取流

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //多线程测试追加文件，看会不会造成写冲突
    public static void fileWriteTest() {
        String fileName = "example.txt";
        String[] wFlag = {"a", "b"};

        Path filePath = Paths.get(fileName);
        try {
            Files.delete(filePath);
        } catch (IOException e) {

        }
        for (int i = 0; i < wFlag.length; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    //字符串长度
                    int wLen = 100;
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < wLen; j++) {
                        sb.append(wFlag[finalI]);
                    }
                    sb.append(System.lineSeparator());
                    //写入次数
                    int wCount = 10000;
                    long start = System.currentTimeMillis();
                    for (int j = 0; j < wCount; j++) {

                        //windows上不加锁10000字符串长度会出现串行即ab活ba的情况
//                        synchronized (TestFileDesciptor.class){
//                            Files.write(filePath, sb.toString().getBytes(),StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//                        }
                        Files.write(filePath, ("j行:"+j+sb.toString()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

                    }

                    log.info("线程:{}已追加写入{}行，字符串测试长度:{},耗时{}", Thread.currentThread().getName(), wCount, wLen, MyDateUtil.execTime("", start));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

        }

    }

    //

    /**
     * BufferedWriter 在单线程立即刷新下能达到100万，3秒速度，基本跟logback同步写日志性能持平
     * 注意；多线程下BufferedWriter必须加锁，并且每个线程必须操作的是同一个BufferedWriter对象
     */
    public static void BufferedWriterTest() {
        String fileName = "example.txt";
        String[] wFlag = {"a", "b"};

        Path filePath = Paths.get(fileName);
        try {
            Files.delete(filePath);
        } catch (IOException e) {

        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath.toString()))) {
            // 创建线程池以并发写入文件
            ExecutorService executorService = Executors.newFixedThreadPool(wFlag.length);
            CountDownLatch count=new CountDownLatch(wFlag.length);
            for (int i = 0; i < wFlag.length; i++) {
                int finalI = i;
                executorService.execute(() -> {
                    //字符串长度
                    int wLen = 10000;
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < wLen; j++) {
                        sb.append(wFlag[finalI]);
                    }
                    sb.append(System.lineSeparator());
                    //写入次数
                    int wCount = 10000;
                    long start = System.currentTimeMillis();

                    for (int j = 0; j < wCount; j++) {
//                        synchronized (TestFileDesciptor.class){
                            try {
                                bufferedWriter.append( "j行:"+j+sb.toString());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                bufferedWriter.flush();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
//                        }

                    }
                    log.info("线程:{}已追加写入{}行，字符串测试长度:{},耗时{}", Thread.currentThread().getName(), wCount, wLen, MyDateUtil.execTime("", start));
                    count.countDown();
                });

            }

            count.await();
            executorService.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }


    public static void main(String[] args) {
//        test();
//        fileWriteTest();
        BufferedWriterTest();
    }
}


