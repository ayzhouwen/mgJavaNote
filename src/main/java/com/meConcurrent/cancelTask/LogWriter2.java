package com.meConcurrent.cancelTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import com.mysql.jdbc.log.Log;

//生产者-消费者日志服务,有停止服务(目前只停止了消费者服务)
public class LogWriter2 {
	private final BlockingDeque<String> queue;
	private final LoggerThread logger;
	private boolean isShutdown;
	private int reservations; //预定
	
	public LogWriter2(Writer writer){
		this.queue=new LinkedBlockingDeque<String>(10);
		this.logger=new LoggerThread(writer);
	}
	
	public void start(){ logger.start();}
	
	public  void stop(){
		synchronized (this) {
			isShutdown=true;
		}
		
		logger.interrupt();
	}
	
	public void log(String msg) throws InterruptedException{
		synchronized (this) {
				if (isShutdown) {
					throw new RuntimeException("服务已停止");
				}
				reservations++;
		}
		
		queue.put(msg);
		System.out.println("put:"+msg);
	}
	private class LoggerThread extends Thread{
		private final PrintWriter writer;
		public LoggerThread(Writer writer){
			 this.writer=(PrintWriter) writer;
		}
		
		public void run(){
			try {
					while(true&&!Thread.currentThread().isInterrupted()){
						try {
							synchronized (LogWriter2.this) {
								if (isShutdown&& reservations==0) {
									break;
								}
							}
							String msg=queue.take();
							writer.println(msg);
							System.out.println("take:"+msg);
							synchronized (LogWriter2.this) {
								reservations--;
							}
						} catch ( InterruptedException e) {
							 	Thread.currentThread().interrupt();
								System.out.println("消费线程被主线程停止");
						}
					
					}
			} catch (Exception e) {
					
			}finally{
					writer.close();
			}
			
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		PrintWriter  pw=new PrintWriter(new FileWriter(new File("d:\\printWriter.txt")),true);
		String name="太极拳";
		pw.printf("姓名：%s;年龄：%d;性别：%c;分数：%5.2f;", name,12,33,44.3);
		pw.println();
		pw.println("多多指教");
	



		
		LogWriter2 lw=new LogWriter2(pw);
		
		Thread thread=new Thread( new Runnable() {
			
			@Override
			public void run() {
				int count=0;
				while(2>1&&!Thread.currentThread().isInterrupted()){
					
		
				try {
					Thread.sleep(15);
					lw.log(System.currentTimeMillis()+"");
				} catch (InterruptedException e) {
					System.out.println("被主中断");
					Thread.currentThread().interrupted();
					e.printStackTrace();
				}
				
			}
				
			}
		});
		
		thread.start();
		lw.start();
		
		
		Thread.sleep(3000*10);
		   thread.interrupt();
		lw.stop();

		
	}
}
