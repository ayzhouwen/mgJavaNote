package com.nio.TcpServer.sessoin;

import com.nio.TcpServer.util.BuffUtil;

import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 注意,实际的协议中得加上消息前缀的权限认证标志,否则瞎造的数据容易造成直接分配个特别大的内存,造成对内和对外内存的溢出
 */
public class ChannelSessoin {

    final SocketChannel sock;
    protected final SelectionKey sk;
    ByteBuffer lenBuffer = ByteBuffer.allocate(4);

    ByteBuffer incomingBuffer = lenBuffer;
    LinkedBlockingQueue<ByteBuffer> outgoingBuffers = new LinkedBlockingQueue<ByteBuffer>();
    private  int index=0; //解码次数
    private  int nian_bao=0; //粘包次数

    public  ChannelSessoin(SocketChannel sock, SelectionKey sk) throws SocketException {
        this.sock = sock;
        this.sk = sk;
        sock.socket().setTcpNoDelay(true);
        sock.socket().setSoLinger(false,-1);
        sock.socket().setReceiveBufferSize(1024*8);
    }
 public    void doIo(SelectionKey k) throws InterruptedException {
        try {
            if (sock.isOpen() == false) {
                throw new RuntimeException("连接失败");

            }
            if (k.isReadable()) {
                int rc = sock.read(incomingBuffer);
                //System.out.println("读取字节大小:"+rc);
                if (rc < 0) {
                    throw new RuntimeException("无法读取数据");
                }
                if (incomingBuffer.remaining() == 0) {
                    boolean isPayload;
                    if (incomingBuffer == lenBuffer) { // start of next request
                        incomingBuffer.flip();
                        isPayload = readLength(k);
                        incomingBuffer.clear();
                    } else {
                        // continuation
                        isPayload = true;
                    }
                    if (isPayload) { // not the case for 4letterword
                        readPayload();  //处理粘包
                    }
                    else {
                        // four letter words take care
                        // need not do anything else
                        return;
                    }
                }else {
                   // System.out.println("有粘包"+niaobao++);
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
            try {
                sock.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            k.cancel();
        }
    }

    private boolean readLength(SelectionKey k) throws IOException {
        // Read the length, now get the buffer
        int len = incomingBuffer.getInt();
        if (len < 0 ) {

         throw new IOException("数据长度错误: " + len);
        }
        incomingBuffer = ByteBuffer.allocate(len);
        return true;
    }


    /** Read the request payload (everything following the length prefix) */
    private void readPayload() throws IOException, InterruptedException {
        if (incomingBuffer.remaining() != 0) { // have we read length bytes?
            int rc = sock.read(incomingBuffer); // sock is non-blocking, so ok
            if (rc < 0) {
                throw new RuntimeException(
                        "读取数据异常");
            }
        }

        if (incomingBuffer.remaining() == 0) { // have we read length bytes?
            incomingBuffer.flip();
            processPacket(incomingBuffer); //数据包已完整,处理数据
            lenBuffer.clear();
            incomingBuffer = lenBuffer;
            nian_bao=0;
        }
    }


    //数据接收完毕,处理包,目前直接解析字符串即可
    private void processPacket( ByteBuffer buf){
        index++;
        String data = null;
        try {
            data = BuffUtil.getString(buf);
            System.out.println("第 "+index+" 次解码:"+data);
        } catch (Exception e) {
            e.printStackTrace();
            //   throw new  RuntimeException("解码失败");
        }
    }



}
