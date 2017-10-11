package com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class TimeClient {
	public static void mySend(final Socket socket){
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					int error=0;
						while(true){
							
					
					try {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						PrintWriter out=new PrintWriter(socket.getOutputStream(), true);
						out.println("当前时间:"+System.currentTimeMillis());
					} catch (IOException e) {
						if(error>3){
								
								System.out.println("强制退出!");
								break;
							
						}
						error++;
						e.printStackTrace();
					}
				}
				}
			});
			t.start();
	}
	
	public static void createConnect(){
		Thread t=new Thread(new Runnable() {
			@Override
			public void run() {

				int port=8084;
				Socket socket=null;
				BufferedReader in=null;
				PrintWriter out=null;
				try {
					socket = new Socket("10.0.1.243", port);
					in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
					//out=new PrintWriter(socket.getOutputStream(), true);
					TimeClient.mySend(socket);
					while (true) {
						String resp=in.readLine();
						System.out.println("接收到服务器的消息 :"+resp);
					}

					
					
				} catch (Exception e) {
					
				}finally {
					if (out !=null) {
								out.close();
								out=null;
					}
					
					if (in !=null) {
						try {
									in.close();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						in=null;
					}
					
					if (socket !=null) {
						try {
							socket.close();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						socket =null;
					}
				}
			
				
			}
		});
		t.start();
	}
	public static void main(String[] args) {
		for(int i=0;i<1000;i++){
			createConnect();
		}
		
	}
}
