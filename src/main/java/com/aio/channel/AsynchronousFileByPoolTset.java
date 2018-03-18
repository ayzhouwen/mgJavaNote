package com.aio.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//通过线程池+回调函数来异步读取文件

//自己搞的线程池和线程一定要释放掉,否则jvm进程一直被挂起
public class AsynchronousFileByPoolTset {
   // 参考http://www.what21.com/sys/view/java_jdk1-7_1473704948893.html

    //但代码自己改的很多

    public static void test1() throws IOException, InterruptedException {
        CountDownLatch countDownLatch=new  CountDownLatch(1);
        ExecutorService pool = Executors.newFixedThreadPool(4);
        AsynchronousFileChannel fileChannel=AsynchronousFileChannel.open(Paths.get("D:\\zw\\asynOptFileTest\\haha.txt"), EnumSet.of(StandardOpenOption.READ),pool);

        CompletionHandler handler=new CompletionHandler() {
            @Override
            public   void  completed(Object result, Object attachment) {
             //   System.out.println(  Thread.currentThread().getName());
              System.out.println(Thread.currentThread().getName()+":"+byteBufferToString((ByteBuffer) attachment));
                //System.out.println(Thread.currentThread().getName());
             countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName()+":执行完毕");
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println(Thread.currentThread().getName()+":失败");
            }
        };
//        final int bufferCount = 5;
//        ByteBuffer buffers[] = new ByteBuffer[bufferCount];
//        for (int i = 0; i < bufferCount; i++) {
//            buffers[i] = ByteBuffer.allocate(1000*20);
//            fileChannel.read(buffers[i], i * 10, buffers[i], handler);
//        }
//        pool.awaitTermination(1, TimeUnit.SECONDS);
//        for (ByteBuffer byteBuffer : buffers) {
//            for (int i = 0; i < byteBuffer.limit(); i++) {
//                System.out.print(byteBuffer.get(i));
//            }
//        }
        ByteBuffer buffers= ByteBuffer.allocate(100);
        fileChannel.read(buffers, 0, buffers, handler);
        fileChannel.read(buffers, 0, buffers, handler);
        fileChannel.read(buffers, 0, buffers, handler);
        fileChannel.read(buffers, 0, buffers, handler);

      countDownLatch.await(); //由于是异步的,所以此处必须阻塞住
      pool.shutdown(); //线程池不关闭,jvm不会被关闭
        System.out.println("ok");
    }

    public static void test2() throws InterruptedException {
        CountDownLatch countDownLatch=new  CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"开始执行任务");
                try {
                    Thread.sleep(new Random().nextInt(5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"执行完毕");
                countDownLatch.countDown();
            }
        }).start();

       //countDownLatch.await(); //由于是异步的,所以此处必须阻塞住
        System.out.println("ok");
    }

//线程池里只要有线程,那么就会一直挂住jvm进程
    public static void test3() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        pool.submit(new Runnable() {
            @Override
            public void run() {

                System.out.println(Thread.currentThread().getName()+"开始执行任务");
                try {
                    Thread.sleep(new Random().nextInt(5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"执行完毕");
            }
        });


        //countDownLatch.await(); //由于是异步的,所以此处必须阻塞住
        System.out.println("ok");
    }

    public static String byteBufferToString(ByteBuffer buffer) {
        buffer.flip();
        CharBuffer charBuffer = null;
        try {
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer);
            buffer.flip();
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
      //  test1();
    //  test2();
        test3();

    }
}
