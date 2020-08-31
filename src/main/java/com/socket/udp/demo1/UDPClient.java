package com.socket.udp.demo1;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;

/**
 * 参考:https://www.liaoxuefeng.com/wiki/1252599548343744/1319099802058785
 * 测试udp最大发送<64*1024-29个字节,否则再大的话不让发送
 * 实际与1m带宽阿里云udp服务端发送1000个包时,发到几百个会有丢包,延迟,本地虚拟机没有问题,在公司局域网测试也没有问题,发送一万次也没有丢包,应该是是阿里云带宽限制问题
 * 在公司服务器上就行iftop   -nNBP -m 1KB -f ' port 8000' 流量统计,发现1000次udp发送峰值可以达到300kb/s,可能被阿里云的流量限制了
 * 如果udp的包大于1000多的话,ip层会进行分包传输给对方,因为ip层不是可靠协议,所以可能会丢包,导致报错
 */
@Slf4j
public class UDPClient {
    public static void sendudp(){
        try {
            DatagramSocket ds = null;
            ds = new DatagramSocket();
            ds.setSoTimeout(10000);
            ds.connect(InetAddress.getByName("192.168.51.80"), 8000); // 连接指定服务器和端口


// 发送:
            StringBuilder sb=new StringBuilder();
//            for (int i = 0; i <64*1024-29 ; i++) {
            for (int i = 0; i <1024; i++) {
                sb.append("1");
            }
//        byte[] data = "Hello".getBytes();
            byte[] data = sb.toString().getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length);

            ds.send(packet);

// 接收:
            byte[] buffer = new byte[1024];
            packet = new DatagramPacket(buffer, buffer.length);

            ds.receive(packet);

            String resp = new String(packet.getData(), packet.getOffset(), packet.getLength());
            log.info("接收服务端数据:"+resp);
            ds.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            sendudp();
            log.info("完成第"+i+"次发送udp");
        }
    }

}
