package com.socket.tcp.demo1;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 注意消息里边要包含回车才有有消息回应
 */
@Slf4j
public class TimeServerHandler  implements Runnable {
	private Socket socket;
	public  TimeServerHandler(Socket socket) {
		this.socket=socket;
	}
	@Override
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
						log.info("接收到客户端消息: "+Thread.currentThread().getName()+":"+body);
						out.println("你好用户:"+Thread.currentThread().getName()+":【"+ DateUtil.now()+"】" +System.currentTimeMillis());
						//currentTime="Query Time Order".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"bad order";
						//System.out.println(currentTime);
						
					}
				} catch (Exception e) {
					if (in !=null) {
						try {
							in.close();
						} catch (Exception e2) {
							log.error("in.close() error", e2);
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
						log.error("this.socket.close() error", e2);
						}
					}
					this.socket=null;
					log.error("消息处理异常", e);
				}
	}

}
