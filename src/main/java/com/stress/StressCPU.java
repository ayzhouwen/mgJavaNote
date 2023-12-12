package com.stress;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 压力测试CPU
 */
public class StressCPU {
    static  String str="";
    public static void main(String[] args) {
        for (int j = 0; j < 2; j++) {
            new Thread(()->{
                Long i=1L;
                while (true){
                    i++;
                    str+=i;
//                    log.info(str);
                    if (i%100000==0){
                        str="";
                    }
                }
            }).start();
        }

//        int port = RandomUtil.randomInt(8000,9000); // 选择要绑定的端口号
        int port = 9080;// 选择要绑定的端口号
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("当前服务监听端口 " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // 阻塞等待客户端连接
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // 在这里可以处理客户端请求，例如读取/写入数据
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
