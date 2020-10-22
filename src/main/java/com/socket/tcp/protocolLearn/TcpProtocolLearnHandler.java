package com.socket.tcp.protocolLearn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpProtocolLearnHandler {
	private Socket socket;
	public TcpProtocolLearnHandler(Socket socket) {
		this.socket=socket;
	}

	public void run() {
				BufferedReader in =null;
		        PrintWriter out=null;
		        try {
					in =new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
					out=new PrintWriter(this.socket.getOutputStream(),true);
					String currentTime=null;
					String body=null;
					while (true) {
						body =in.readLine();
						if (body==null) {
							break;
						}
						System.out.println("接收到客户端消息: "+Thread.currentThread().getName()+":"+body);
						out.println("你好用户:"+Thread.currentThread().getName()+":"+System.currentTimeMillis());
						//currentTime="Query Time Order".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"bad order";
						//System.out.println(currentTime);
						
					}
				} catch (Exception e) {
					if (in !=null) {
						try {
							in.close();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
					if (out !=null) {
						out.close();
						out=null;
					}
					
					if (this.socket !=null) {
						try {
							this.socket.close();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
					this.socket=null;
				}
	}



}
