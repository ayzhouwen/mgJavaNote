package com.nio.TcpServer.decode;

import com.nio.TcpServer.util.BuffUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.List;

public class PacketDecode {

    //一个完整的应用层数据包格式:消息头(4个字节存储消息体长度)+消息体 方式进行数据编码,消息头记录数据长度,占用4个字节,消息体包含
//解码  msglen=4+bodylen
    private static ByteBuffer packBuf = null; //一个完整的应用层数据包
    private static  int headLen=4;
    private  static  	int decodeNum=1;
    public static List<String> headBodyDecode(SocketChannel channel) throws IOException {
    //    System.out.println("************************开始第+"+decodeNum+"+解码*******************");
        ByteBuffer revBuffer = ByteBuffer.allocate(32);
      List<String>   result = null;
        int bodylen;
        if (channel.read(revBuffer) <= 0) {
            return null;
        }
        revBuffer.flip();

        int index=1;

        while (revBuffer.hasRemaining()){

            int pRemain=0;
            if (packBuf!=null){
                pRemain=packBuf.limit()-packBuf.position(); //拼接一个应用包还剩的数量
            }
            int rRemain=revBuffer.remaining();//当前缓冲区数据长度

             //   if (packBuf == null||pRemain<headLen) { //拼装应用包头和拼装应用包尾
                    //缓冲区不够消息头返回
                    int forHead=0;
                    int forTail=0;
                    ByteBuffer tem;
                    if (packBuf == null) {
                        forHead=Math.min(headLen, rRemain);
                        packBuf = ByteBuffer.allocate(forHead);
                        revBuffer.get(packBuf.array(), 0, forHead);
                        //验证这次消息头是否拼接成功
                        if (packBuf.remaining() <headLen) {
                            break;
                        }

                        bodylen = packBuf.getInt(0);
                     //   System.out.println("第" + index + "次消息头前设置,packBuf:" + packBuf + ";revBuffer:" + revBuffer);
                        //消息体长度
                        try {
                            packBuf = ByteBuffer.allocate(headLen + bodylen); //消息头长度4+消息体长度
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        packBuf.putInt(bodylen);
                    //    System.out.println("第" + index + "次消息头后设置,packBuf:" + packBuf + ";revBuffer:" + revBuffer);
                    } else {
                        if (pRemain<headLen&&packBuf.position()<headLen){
                            forHead=Math.min( headLen-pRemain, rRemain);//消息头剩下的字节
                            tem=ByteBuffer.allocate(forHead+pRemain);
                            packBuf.get(tem.array(),0,pRemain);
                            revBuffer.get(tem.array(),pRemain,forHead);
                            packBuf=tem;
                            packBuf.position(pRemain+forHead);
                            if (packBuf.position()==headLen){
                                bodylen = packBuf.getInt(0);
                                try {
                                    packBuf = ByteBuffer.allocate(headLen + bodylen); //消息头长度4+消息体长度
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                packBuf.putInt(bodylen);
                            }else {
                                break;
                            }
                        }else {
                            forTail=Math.min( pRemain, rRemain);//消息尾剩下的字节
                            revBuffer.get(packBuf.array(), packBuf.position(), forTail);
                        }

                        if (forTail>0){
                            packBuf.position(packBuf.position()+forTail);
                            if (packBuf.hasRemaining()){
                                break;  //拼接应用包尾不成功,如果为空标示旧应用包已完成拼接
                            }
                        }

                    }


                if (forHead!=0){ //新应用包,直接取缓冲区可允许数量即可
                    int getLen=Math.min(packBuf.getInt(0),revBuffer.remaining());
                    int revlen=revBuffer.remaining();
                    revBuffer.get(packBuf.array(),headLen,getLen);
                    packBuf.position(packBuf.position()+getLen);
                    if (packBuf.getInt(0)>revlen){  //如果出现分包则直接break;等待下次组包
                        break;
                    }
                }


                packBuf.clear();
                packBuf.position(headLen); //略过消息头,方便消息体接收数据
                if (result==null){
                    result=new ArrayList<>();
                }

                String data = null;
                try {
                    data = BuffUtil.getString(packBuf);
                } catch (CharacterCodingException e) {
                    e.printStackTrace();
                 //   throw new  RuntimeException("解码失败");
                }
                result.add(data);
                //应用层包接收完毕,并且接受缓冲区没有数据时释放内存,以便下次重新组包

                packBuf = null;


                //System.out.println("第"+index+"次解码");
                index++;
            }

       // System.out.println("************************第"+decodeNum+"结束解码*******************");
        decodeNum++;
        return result;


    }

    public static void main(String[] args) {
        System.out.println(1|500);
    }
}

