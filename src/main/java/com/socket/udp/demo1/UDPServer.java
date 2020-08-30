package com.socket.udp.demo1;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
/**
 * 参考:https://www.liaoxuefeng.com/wiki/1252599548343744/1319099802058785
 */
@Slf4j
public class UDPServer {
    public static void main(String[] args) {
        //收到的包数量
        int receiveNum=0;
        //发送的包数量
        int sendNum=0;
        DatagramSocket ds = null; // 监听指定端口
        try {
            ds = new DatagramSocket(8000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        for (;;) { // 无限循环
            // 数据缓冲区:
            try {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                ds.receive(packet); // 收取一个UDP数据包
                receiveNum++;
                // 收取到的数据存储在buffer中，由packet.getOffset(), packet.getLength()指定起始位置和长度
                // 将其按UTF-8编码转换为String:
                String s = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
                log.info("收到第"+receiveNum+"个包,客户端数据:"+s);
                // 发送数据:
                byte[] data = "ACK".getBytes(StandardCharsets.UTF_8);
                packet.setData(data);
                ds.send(packet);
                sendNum++;
                log.info("发送第"+sendNum+"个包");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
