package com.meConcurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
//测试线程池钩子
import java.util.concurrent.atomic.AtomicLong;

import sun.util.logging.resources.logging;
public class TheadPoolHook extends ThreadPoolExecutor {
	public TheadPoolHook(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		// TODO Auto-generated constructor stub
	}

	private final ThreadLocal<Long> startTime=new ThreadLocal<>();
	private final AtomicLong numTasks=new AtomicLong();
	private final AtomicLong totalTime=new AtomicLong();
	
	
   @Override
protected void beforeExecute(Thread t, Runnable r) {
	super.beforeExecute(t, r);
	//System.out.println("Thead:"+t+",strat:"+r);
	startTime.set(System.nanoTime());
}
   
 @Override
protected void afterExecute(Runnable r, Throwable t) {
	super.afterExecute(r, t);
	try {
		long endTime=System.nanoTime();
		long taskTime=endTime-startTime.get();
		numTasks.incrementAndGet();
		totalTime.addAndGet(taskTime);
		//System.out.println("  Thead:"+t+",end:"+r+",time="+taskTime);
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		super.afterExecute(r, t);
	}
	
}
   
@Override
protected void terminated() {
	// TODO Auto-generated method stub
	super.terminated();
	try {
		System.out.println("Terminater:avg time="+(totalTime.get()/numTasks.get()));
	} finally {
		// TODO: handle finally clause
	}
}
   
public static void main(String[] args) {
	int c=0;
//	abc:
//	      for (int index=0;;index++) {
//	    	  System.out.println(index);
//	    	  c++;
//	    	  if (c>3) {
//				for (int i = 0; i < 5; i++) {
//						System.out.println("内循环"+i);
//					break abc; //直接提出最外层循环
//					//break;
//				}
//			}
//	    	  if (c>5) {
//				break abc;
//			}
//	      }
	//Executors.newScheduledThreadPool(corePoolSize)
	TheadPoolHook tph=new  TheadPoolHook(2,2,200,TimeUnit.MILLISECONDS ,new ArrayBlockingQueue<>(2));
	//tph.setRejectedExecutionHandler( new CallerRunsPolicy());
	def:
	
	for (int i = 0; i < 2; i++) {
		

	tph.execute(new Runnable() {
		@Override
		public void run() {
				try {
					//Thread.sleep( (long)Math.random()*1000*2);
					Thread.sleep(5000);
					System.out.println("线程名:"+Thread.currentThread().getName());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	});
	

	System.out.println("执行"+i+"次");

	}
//	tph.allowCoreThreadTimeOut(true);
	//tph.shutdown();
}
   
}
