package com.meConcurrent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Future;

public class MyTimerTask {
	public static void main(String[] args) {
		Timer t=new  Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
			  try {
				Thread.sleep(4000);   //一个任务执行完成第二个任务才能执行,而且第一个任务歇菜后,第二个不会被执行,很烂
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				System.out.println("你好a");
		throw new RuntimeException();
				
			}
		}, 3000);
		
	t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("你好b");
		//	throw new RuntimeException();
				
			}
		}, 3000);
	}
	

}
