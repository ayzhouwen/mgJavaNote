package com.meConcurrent;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
//http://blog.csdn.net/a258831020/article/details/49074725
public class ScheduledExecutorServiceTest {
	/**
	 * 以固定周期频率执行任务,依然要等待上一个任务执行完毕才继续往下执行
	 */
	
	
	public static void executeFixedRate() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);  //newScheduledThreadPool一般都是1,否则并行执行任务
		executor.scheduleAtFixedRate(
				new EchoServer1("李逵"),
				0,
				100,
				TimeUnit.MILLISECONDS);
		executor.scheduleAtFixedRate(
			   new EchoServer1("阮小七"),
				0,
				100,
				TimeUnit.MILLISECONDS);
	}
	
	//以固定延迟时间进行执行 ,本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
	public static void executeFixedDelay(){
		ScheduledExecutorService executor=Executors.newScheduledThreadPool(1);
		executor.scheduleWithFixedDelay(new EchoServer1("武松"), 0, 10000, TimeUnit.MILLISECONDS);
	}
	
	public static void executeEightAtNightPerDay(){
		ScheduledExecutorService executor=Executors.newScheduledThreadPool(3);
		long oneDay=24*60*60*1000;
		long initDelay =getTimeMillis("23:38:00")-System.currentTimeMillis();
		initDelay=initDelay>0?initDelay:oneDay+initDelay;
		executor.scheduleAtFixedRate(new EchoServer("林冲"), initDelay, oneDay, TimeUnit.MILLISECONDS);
	}
	
	private static long getTimeMillis(String time){
		try {
			DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat dayFormat=new SimpleDateFormat("yyyy-MM-dd");
			Date curDate= dateFormat.parse(dayFormat.format(new Date())+" "+time);
			return curDate.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return 0;  
	}
	public static void main(String[] args) {
		//ScheduledExecutorServiceTest.executeFixedDelay();
		ScheduledExecutorServiceTest.executeFixedRate();
		//ScheduledExecutorServiceTest.executeEightAtNightPerDay();
	}
	


}


class EchoServer implements Runnable {
	public  static int count;
	private String name;
	EchoServer(String name){
		this.name =name;
	}
	@Override
	public void run() {
				System.out.println(Thread.currentThread().getName()+"开始执行"+",名字:"+name);
				try {
					//Thread.sleep(1000 * (new Random()).nextInt(8));
					Thread.sleep(3000);
					System.out.println(Thread.currentThread().getName()+"执行完毕"+",名字:"+name);
					if (count>3) {
						throw new RuntimeException("自我异常");
					}else {
						count++;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		
	}
}

class EchoServer1 implements Runnable {
	public  static int count;
	private String name;
	EchoServer1(String name){
		this.name =name;
	}
	@Override
	public void run() {
				ArrayList< Object> list=new ArrayList<>();
				System.out.println(Thread.currentThread().getName()+"开始执行"+",名字:"+name);
				try {
					//Thread.sleep(1000 * (new Random()).nextInt(8));
					Thread.sleep(5000);
					System.out.println(Thread.currentThread().getName()+"执行完毕"+",名字:"+name);
					if (count>3) {
						//throw new RuntimeException("自我异常");
					}else {
						count++;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		
	}
}
