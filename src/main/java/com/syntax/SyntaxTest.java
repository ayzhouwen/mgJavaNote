package com.syntax;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.util.MyDateUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author admin
 */
//临时随手代码测试类,可以忽略次类d
@Slf4j
public class SyntaxTest {

    int i;
    static  String str="";
//    public static void main(String[] args)  {
//        for (int j = 0; j < 2; j++) {
//            new Thread(()->{
//                Long i=1L;
//                while (true){
//                    i++;
//                    str+=i;
////                    log.info(str);
//                    if (i%100000==0){
//                        str="";
//                    }
//                }
//            }).start();
//        }
//
//        int port = RandomUtil.randomInt(8000,9000); // 选择要绑定的端口号
//        ServerSocket serverSocket = null;
//        try {
//             serverSocket = new ServerSocket(port);
//            System.out.println("当前服务监听端口 " + port);
//
//            while (true) {
//                Socket clientSocket = serverSocket.accept(); // 阻塞等待客户端连接
//                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
//
//                // 在这里可以处理客户端请求，例如读取/写入数据
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                serverSocket.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//    }

    public static void main(String[] args) {
//        new Thread(()->{
//            try {
//                for (int j = 0; j <3 ; j++) {
//                    Thread.sleep(1000*30);
//                    long start=System.currentTimeMillis();
//                    int size=1000000;
//                    for (int i = 0; i <size ; i++) {
//                        log.info("完成写入:"+i);
//                    }
//                    log.info(MyDateUtil.execTime("执行"+size+"次",start));
//                }
//
//
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }).start();

        try {
            for (int j = 0; j <3 ; j++) {
                Thread.sleep(1000*30);
                long start=System.currentTimeMillis();
                int size=1000000;
                for (int i = 0; i <size ; i++) {
                    log.info("完成写入:"+i);
                }
                log.info(MyDateUtil.execTime("执行"+size+"次",start));
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}











