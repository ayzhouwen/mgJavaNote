package com.aio.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

//测试aio访问文件 ,aio参考https://segmentfault.com/a/1190000007653010
public class AsynchronousFileChannelTest {
    private String filepath = "D:\\zw\\test\\";

    //读写文件主线程等待结果方式进行异步调用,一般没有这么用的都是用的,回调方式比这种方式性能更好,但是异步的读写都是异步的目前想到的是用队列实现数据传递,队列元素存储字节数组
    public void readFile() throws IOException, ExecutionException, InterruptedException {
        //  Path readPath= Paths.get(filepath+"localhost_access_log.2017-10-16.txt");
        Path readPath = Paths.get(filepath + "1.png");
        Path writePath = Paths.get(filepath + "writeTest" + System.currentTimeMillis() + ".png");
        AsynchronousFileChannel readChannel = AsynchronousFileChannel.open(readPath);
        AsynchronousFileChannel writeChannel = AsynchronousFileChannel.open(writePath, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long size = readChannel.size(); //文件大小
        long nextReadPosition = 0;//下次读位置
        int i = 0; //方便调试
        while (true) {
            buffer.clear();
            Future<Integer> readFuture = readChannel.read(buffer, nextReadPosition);
            Integer readNumber = readFuture.get();
            buffer.flip();
            int remain = buffer.remaining();
            //输出字符串
//            CharBuffer charBuffer=CharBuffer.allocate((int)nextReadPosition);
//            CharsetDecoder decoder= Charset.defaultCharset().newDecoder();
//            decoder.decode(buffer,charBuffer,false);
//            charBuffer.flip();
//            String data=new String(charBuffer.array(),0,charBuffer.limit());
            //  System.out.println("read number:"+readNumber);
            //System.out.println(data);

            //写入文件
            Future<Integer> writeFuture = writeChannel.write(buffer, nextReadPosition);
            writeFuture.get();
            nextReadPosition += remain;
            if (size <= nextReadPosition) {
                break;
            }
            i++;
        }

    }



    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        AsynchronousFileChannelTest asynchronousFileChannelTest = new AsynchronousFileChannelTest();
        //   asynchronousFileChannelTest.readFile();


    }
}
