package com.socket.tcp.protocolLearn;

import com.socket.tcp.demo1.TimeServerHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 此demo主要是对tcp协议的学习演示
 */
@Slf4j
public class TcpProtocolLearnServer {
		public static void main(String[] args) {
			int port=8084;
			ServerSocket serverSocket=null;
				try {
					serverSocket=new ServerSocket(port);
					System.out.println("TCP服务监听端口 :"+port);
					System.out.println("TCP服务getInetAddress地址:"+serverSocket.getInetAddress());
					System.out.println("TCP服务getLocalSocketAddress地址:"+serverSocket.getLocalSocketAddress());
					System.out.println("TCP服务getReuseAddress地址:"+serverSocket.getReuseAddress());
					System.out.println("TCP服务getChannel:"+serverSocket.getChannel());
					Socket socket=null;
					while(true){
					socket =serverSocket.accept();
						TcpProtocolLearnHandler handler=new TcpProtocolLearnHandler(socket);
						handler.run();
					}
				} catch (Exception e) {
					log.error("tcp服务端异常:",e);
				}
		}
}
