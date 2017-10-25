package com.nio.TcpServer.encode;

import org.apache.commons.lang.StringUtils;

import java.nio.ByteBuffer;

//数据编码器解决粘包问题
public class PacketEncode {
    //一个完整的应用层数据包格式:消息头(4个字节存储消息体长度)+消息体 进行数据编码,消息头记录数据长度,占用4个字节,消息体包含
    //msglen=4+bodylen
    public  static ByteBuffer HeadBodyEncode(String msg){
        if (StringUtils.isEmpty(msg)){
            return  null;
        }
        int bodylen=msg.getBytes().length;
        int msglen=4+bodylen;
        ByteBuffer buffer=ByteBuffer.allocate(msglen);
        buffer.putInt(bodylen);
        buffer.put(msg.getBytes());
        buffer.flip();
        return buffer;
    }

    public static void main(String[] args) {
        ByteBuffer bf= ByteBuffer.allocate(8);
         bf.putInt(9000);
        bf.getInt(0);
        bf.clear();


    }
}
