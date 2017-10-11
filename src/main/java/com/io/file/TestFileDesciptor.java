package com.io.file;

import com.xiaoleilu.hutool.date.TimeInterval;

import java.io.*;
import java.lang.reflect.Field;

//测试文件描述符,在Windows平台是fd是-1,handle是wind的handle
public class TestFileDesciptor {
    //测试在win和linux平台fd 和handle的不同
    public  static void   test(){
     //   File file=new File(TestFileDesciptor.class.getResource("/test.txt").getPath()); //有斜杠则是根路径
        try {
            System.out.println(TestFileDesciptor.class.getResource("/test.txt").getPath());
            FileInputStream fileInputStream=new FileInputStream(TestFileDesciptor.class.getResource("/test.txt").getPath());
            FileDescriptor fileDescriptor=fileInputStream.getFD();
            Field[] fields = FileDescriptor.class.getDeclaredFields();
            StringBuffer stringBuffer=new StringBuffer();
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
            int num=1;
            String path;
            TimeInterval timer = new TimeInterval(true);

            for (int i=0;i<num;i++){
                fileInputStream=new FileInputStream(TestFileDesciptor.class.getResource("/test2.txt").getPath());
                // fileInputStream=new FileInputStream("D:\\zw\\mgJavaNote\\src\\main\\resources\\test.txt");
              //  path=TestFileDesciptor.class.getResource("/test.txt").getPath();

            }

            //System.out.println(num+"个文件全部打开完毕");
            System.out.println(num+"次"+timer.interval());//花费毫秒数
            //通过FileDescriptor获取流

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //测试通过文件描述符进行读写,读取test1的内容进行加工后写入test3,然后在全部读取出来
    public  static  void test2(){
        FileInputStream fileInputStream;
        try {
             fileInputStream=new FileInputStream(TestFileDesciptor.class.getResource("/test1.txt").getPath());
            BufferedInputStream in = null;
            BufferedOutputStream out = null;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test();
    }
}
