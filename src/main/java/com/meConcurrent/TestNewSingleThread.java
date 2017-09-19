package com.meConcurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestNewSingleThread {
	
	public  class  R1 implements Runnable{

		
		@Override
		public void run() {
			int count=0;
			while(count++<10){
				
	
			try {
				Thread.sleep(100);
				if (count==5) {
					count=count/(count-5);
				}
			   System.out.println(Thread.currentThread().getName()+":执行中..."+count+"次");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("error");
				e.printStackTrace();
			}
			
		}
			   System.out.println(Thread.currentThread().getName()+":执行完毕**********************");
			
		}
	
	
}	

public static void main(String[] args) throws InterruptedException {

	ExecutorService exec =Executors.newSingleThreadScheduledExecutor();
	TestNewSingleThread tst=new  TestNewSingleThread();
	TestNewSingleThread.R1  r= tst.new R1();
	exec.execute( r);
	exec.execute( r);  //依次顺序执行,及时遇到异常,照样执行
	Thread.sleep(3000);
	exec.execute(r);
	
	exec.shutdown(); //关闭线程池
}
}
