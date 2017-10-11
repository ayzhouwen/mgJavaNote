package com.socket;

import java.net.ServerSocket;
import java.net.Socket;


public class TimeServer {
		public static void main(String[] args) {
			int port=8084;
			ServerSocket serverSocket=null;
				try {
					serverSocket=new ServerSocket(port);
					System.out.println("the time server is start in port :"+port);
					System.out.println("getInetAddress:"+serverSocket.getInetAddress());
					System.out.println("getLocalSocketAddress:"+serverSocket.getLocalSocketAddress());
					System.out.println("getReuseAddress:"+serverSocket.getReuseAddress());
					System.out.println("getChannel:"+serverSocket.getChannel());
					Socket socket=null;
					while(true){
					socket =serverSocket.accept();
						TimeServerHandler tsh=new	TimeServerHandler(socket);
						Thread thread=new Thread(tsh);
						thread.start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
}
