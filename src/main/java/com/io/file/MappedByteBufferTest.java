package com.io.file;

import com.io.file.stream.CheckedInputStreamTest;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

//内存映射读取,速度读取大文件是真的快
//参考:https://yq.aliyun.com/articles/37857
//85m的文件测试结果如下:
//sum:1942458984流读取文件time:184471
//        sum:1942458984缓存流读取文件time:416
////        sum:119740888 内存文件映射time:10
public class MappedByteBufferTest {
    void test1() throws IOException {

        //普通流,一个一个字节读取
        String path = "D:\\install\\vmware\\winPreVista.iso";
        FileInputStream fis = new FileInputStream(path);
        int sum = 0;
        int n;
        long t1 = System.currentTimeMillis();
        while ((n = fis.read()) >= 0) {
            sum += n;
        }
        long t = System.currentTimeMillis() - t1;
        System.out.println("sum:" + sum + "流读取文件time:" + t);


        //缓冲区读取

        fis = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(fis);
        sum = 0;
        n = 0;
        t1 = System.currentTimeMillis();
        try {
            while ((n = bis.read()) >= 0) {
                sum += n;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        t = System.currentTimeMillis() - t1;
        System.out.println("sum:" + sum + "缓存流读取文件time:" + t);


        //内存文件映射
        MappedByteBuffer buffer = null;

        buffer = new RandomAccessFile(path, "rw").getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 1253244);
        sum = 0;
        n = 0;
        t1 = System.currentTimeMillis();
        for (int i = 0; i < 1253244; i++) {
            n = 0x000000ff & buffer.get(i);
            sum += n;
        }
        t = System.currentTimeMillis() - t1;
        System.out.println("sum:" + sum + " 内存文件映射time:" + t);


    }


    //写文件测试
    void test2() throws IOException, InterruptedException {
        String path = "D:\\zw\\myTest\\test.iso";
        String path1 = "D:\\zw\\myTest\\test_1.iso";
        File f = new File(path1);
        FileInputStream fis = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(fis);

        FileOutputStream fos1 = new FileOutputStream(path1);
        BufferedOutputStream bis1 = new BufferedOutputStream(fos1);
        int n = 0;
        long t1 = System.currentTimeMillis();

        byte[] barr = new byte[1024 * 512];
        try {
            while ((n = bis.read(barr)) >= 0) {
                bis1.write(barr);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long t = System.currentTimeMillis() - t1;
        System.out.println("缓存流读取文件time:" + t);
        fos1.close();
        bis1.close();
        Thread.sleep(2000);
        f.delete();
            fos1.close();
        bis1.close();






         fis = new FileInputStream(path);
         bis = new BufferedInputStream(fis);
        t1 = System.currentTimeMillis();
        MappedByteBuffer buffer = null;

        buffer = new RandomAccessFile(path1, "rw").getChannel().map(FileChannel.MapMode.READ_WRITE, 0, (new File(path).length()));
        n = 0;
        t1 = System.currentTimeMillis();
        while ((n = bis.read(barr)) >= 0) {

       //     System.out.println(buffer);
            buffer.put(barr,0,n);
        }
        t = System.currentTimeMillis() - t1;
      //  buffer.force();
        System.out.println("内存文件映射time:" + t);

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        MappedByteBufferTest mbt = new MappedByteBufferTest();
        //mbt.test1();
        mbt.test2();
    }
}
