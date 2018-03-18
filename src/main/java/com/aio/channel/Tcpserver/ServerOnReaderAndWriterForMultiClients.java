package com.aio.channel.Tcpserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ServerOnReaderAndWriterForMultiClients {
    static final int DEFAULT_PORT = 7777;
    static final String IP = "127.0.0.1";
  //    static final String IP="192.168.0.106";
    static AsynchronousChannelGroup threadGroup = null;
    static ExecutorService executorService = Executors.newCachedThreadPool();
    public static Object readLock = new Object();
    public static Object writeLock = new Object();

    public static void main(String[] args) {
        try {
            threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 5);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open(threadGroup);
            if (serverSocketChannel.isOpen()) {
                //服务端的通道支持两种选项SO_RCVBUF和SO_REUSEADDR，一般无需显式设置，使用其默认即可，此处仅为展示设置方法
                //在面向流的通道中，此选项表示在前一个连接处于TIME_WAIT状态时，下一个连接是否可以重用通道地址
                serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
                //设置通道接收字节大小
                serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 8 * 1024);
                serverSocketChannel.bind(new InetSocketAddress(IP, DEFAULT_PORT));
                System.out.println("等待连接...");
                AcceptCompletionHandler acceptCompletionHandler = new AcceptCompletionHandler();
                Context serverContext = new Context(); //服务器通道上下文
                serverContext.setAcceptCompletionHandler(acceptCompletionHandler);
                serverContext.setAsynchronousServerSocketChannel(serverSocketChannel);
                serverSocketChannel.accept(serverContext, acceptCompletionHandler);
                try {
                    threadGroup.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Context> {


    @Override
    public void completed(AsynchronousSocketChannel asynchronousSocketChannel, Context serverContext) {


        try {
            System.out.println("进来连接:从" + asynchronousSocketChannel.getRemoteAddress());
            Context clientContext=new Context(); //客户端通道
            clientContext.setAsynchronousSocketChannel(asynchronousSocketChannel);
            clientContext.setServerContext(serverContext);
            ByteBuffer readByteBuffer = clientContext.getReadBuffer();
            ReadCompletionHandler readCompletionHandler = new ReadCompletionHandler();

            asynchronousSocketChannel.read(readByteBuffer, clientContext, readCompletionHandler);


            serverContext.getAsynchronousServerSocketChannel().accept(serverContext, this);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void failed(Throwable exc, Context serverContext) {
        System.out.println("监听失败:");
        //     context.getAsynchronousServerSocketChannel().accept(context, this);
    }
}


class ReadCompletionHandler implements CompletionHandler<Integer, Context> {
    @Override
    public void completed(Integer result, Context clientContext) {

        System.out.println("读->result:" + result);
        System.out.println("读->attachment:" + clientContext.getReadBuffer());
        AsynchronousSocketChannel asynchronousSocketChannel = clientContext.getAsynchronousSocketChannel();
        clientContext.getReadBuffer().flip();
        String revStr="";
        try {
             revStr=BuffUtil.getString(clientContext.getReadBuffer());
            System.out.println("读结果:" +revStr );
            if (revStr.equals("sleep")){
                try {
                    Thread.sleep(1000*5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
        clientContext.getReadBuffer().position(0).limit(clientContext.getReadBuffer().capacity());
        asynchronousSocketChannel.read(clientContext.getReadBuffer(), clientContext, this);
        //将读到的数据再写入通道
        byte []  revB= revStr.getBytes();
        ByteBuffer writeBufer=ByteBuffer.wrap(revB);
        clientContext.setWriteBuffer(writeBufer);
        WriteCompletionHandler writeCompletionHandler=new  WriteCompletionHandler();
        asynchronousSocketChannel.write(writeBufer,clientContext,writeCompletionHandler);

    }


    @Override
    public void failed(Throwable exc, Context attachment) {
        System.out.println("读失败");
    }

//     @Override
//     public void completed(Integer result, ByteBuffer byteBuffer) {
//         System.out.println("result:"+result);
//         System.out.println("attachment:"+byteBuffer);
//         AsynchronousSocketChannel asynchronousSocketChannel = channelContext.getAsynchronousSocketChannel();
//         asynchronousSocketChannel.read(readByteBuffer, readByteBuffer, this);
//     }
//
//     @Override
//     public void failed(Throwable exc, ByteBuffer attachment) {
//
//     }
}


class WriteCompletionHandler implements CompletionHandler<Integer, Context> {

    @Override
    public void completed(Integer result, Context clientContext) {
//
        System.out.println("写->result:" + result);
        System.out.println("写->attachment:" + clientContext.getWriteBuffer());
//        String  sendStr= null;
//        try {
//            sendStr = BuffUtil.getString(clientContext.getWriteBuffer());
//        } catch (CharacterCodingException e) {
//            e.printStackTrace();
//        }
//        System.out.println("写结果:" +sendStr );
       clientContext.getWriteBuffer().position(0).limit(  clientContext.getWriteBuffer().capacity());
        AsynchronousSocketChannel asynchronousSocketChannel = clientContext.getAsynchronousSocketChannel();
      //  asynchronousSocketChannel.write(clientContext.getWriteBuffer(), clientContext, this);
    }

    @Override
    public void failed(Throwable exc, Context attachment) {
        System.out.println("写失败");
    }
}

//这里简写,理论上应该把服务器通道上下文和客户端通道上下文分出2个类
class Context {
    private ByteBuffer readBuffer = ByteBuffer.allocate(2048); //读缓存
    private ByteBuffer writeBuffer; //写缓存
    private AcceptCompletionHandler acceptCompletionHandler; //接收连接
    private AsynchronousServerSocketChannel asynchronousServerSocketChannel;  //server通道
    private AsynchronousSocketChannel asynchronousSocketChannel; //连接通道
    private Context serverContext;//服务器通道上下文

    public Context getServerContext() {
        return serverContext;
    }

    public void setServerContext(Context serverContext) {
        this.serverContext = serverContext;
    }

    public AcceptCompletionHandler getAcceptCompletionHandler() {
        return acceptCompletionHandler;
    }

    public void setAcceptCompletionHandler(AcceptCompletionHandler acceptCompletionHandler) {
        this.acceptCompletionHandler = acceptCompletionHandler;
    }

    public AsynchronousServerSocketChannel getAsynchronousServerSocketChannel() {
        return asynchronousServerSocketChannel;
    }

    public void setAsynchronousServerSocketChannel(AsynchronousServerSocketChannel asynchronousServerSocketChannel) {
        this.asynchronousServerSocketChannel = asynchronousServerSocketChannel;
    }

    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }

    public void setReadBuffer(ByteBuffer readBuffer) {
        this.readBuffer = readBuffer;
    }

    public ByteBuffer getWriteBuffer() {
        return writeBuffer;
    }

    public void setWriteBuffer(ByteBuffer writeBuffer) {
        this.writeBuffer = writeBuffer;
    }

    public AsynchronousSocketChannel getAsynchronousSocketChannel() {
        return asynchronousSocketChannel;
    }

    public void setAsynchronousSocketChannel(AsynchronousSocketChannel asynchronousSocketChannel) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
    }
}

class BuffUtil {
    public static String getString(ByteBuffer buffer) throws CharacterCodingException {
        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;

        charset = Charset.forName("UTF-8");
        decoder = charset.newDecoder();
        charBuffer = decoder.decode(buffer);//用这个的话，只能输出来一次结果，第二次显示为空
        //charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
        return charBuffer.toString();


    }
}
