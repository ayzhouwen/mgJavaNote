package com.syntax;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.checksum.CRC16;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.HexUtil;
import com.util.CRCUtils;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author admin
 */
//临时随手代码测试类,可以忽略次类d


class MyDog<A extends String, B, C, D, E, F> {
    A getDog() {
        return (A) "AAAAA";
    }
}

@FunctionalInterface
interface SFunction<T, R> extends Function<T, R>, Serializable {
}

@Slf4j
public class SyntaxTest {

    int i;

    public void incr() {
        synchronized (this) {
            i++;
        }
    }

    public static void main(String[] args)  {

        for (int j = 0; j < 2; j++) {
            new Thread(()->{
                Long i=1L;
                while (true){
                    i++;
                }
            }).start();
        }

        int port = 9080; // 选择要绑定的端口号
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


    /**
     * 获取线程快照信息
     *
     * @return
     */
    public static String getJavaStackTrace() {
        StringBuffer msg = new StringBuffer();
        for (Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()) {
            Thread thread = (Thread) stackTrace.getKey();
            StackTraceElement[] stack = (StackTraceElement[]) stackTrace.getValue();
            if (thread.equals(Thread.currentThread())) {
                continue;
            }
            msg.append("\n 线程:").append(thread.getName()).append("\n");
            for (StackTraceElement element : stack) {
                msg.append("\t").append(element).append("\n");
            }
        }
        return msg.toString();
    }


}











