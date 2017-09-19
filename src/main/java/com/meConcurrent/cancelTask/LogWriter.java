package com.meConcurrent.cancelTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import com.mysql.jdbc.log.Log;

//生产者-消费者日志服务,没有停止服务
public class LogWriter {
	private final BlockingDeque<String> queue;
	private final LoggerThread logger;
	
	public LogWriter(Writer writer){
		this.queue=new LinkedBlockingDeque<String>(10);
		this.logger=new LoggerThread(writer);
	}
	
	public void start(){ logger.start();}
	
	public void log(String msg) throws InterruptedException{
		queue.put(msg);
		System.out.println("put");
	}
	private class LoggerThread extends Thread{
		private final PrintWriter writer;
		public LoggerThread(Writer writer){
			 this.writer=(PrintWriter) writer;
		}
		
		public void run(){
			try {
					while(true){
						writer.println(queue.take());
						System.out.println("take");
					}
			} catch (Exception e) {
					
			}finally{
					writer.close();
			}
			
		}
	}
	
	public static void main(String[] args) throws IOException {
		PrintWriter  pw=new PrintWriter(new FileWriter(new File("d:\\printWriter.txt")),true);
		String name="太极拳";
		pw.printf("姓名：%s;年龄：%d;性别：%c;分数：%5.2f;", name,12,33,44.3);
		pw.println();
		pw.println("多多指教");
	



		
		LogWriter lw=new LogWriter(pw);
		
		Thread thread=new Thread( new Runnable() {
			
			@Override
			public void run() {
				int count=0;
				while(count++<1000){
					
		
				try {
					Thread.sleep(0);
					lw.log(System.currentTimeMillis()+"");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
				
			}
		});
		
		thread.start();
		lw.start();
	}
}
