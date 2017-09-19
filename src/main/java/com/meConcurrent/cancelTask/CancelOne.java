package com.meConcurrent.cancelTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//取消任务,通过定时任务自定义线程的阻塞到指定时间抛出异常,属于一般的方法
public class CancelOne {
private static final ScheduledExecutorService cancelExec=Executors.newScheduledThreadPool(1);
	public static  void timedRun(final Runnable r,long timeout,TimeUnit unit) throws InterruptedException{
		class  RethrowableTask implements Runnable{
			private volatile Throwable t;
			@Override
			public void run() {
						try {
							r.run();
						} catch ( Throwable t) {
								this.t=t;
						}
				
			}
			
			void rethrow(){
				if (t!=null) {
					throw new RuntimeException("发现异常");
				}
			}
		}
		
		RethrowableTask task=new  RethrowableTask();
		final Thread taskThead=new Thread(task);
		taskThead.start();
		cancelExec.schedule(new Runnable() {
			@Override
			public void run() {
				taskThead.interrupt();
			}
		}, timeout, unit);
		taskThead.join(timeout);
		task.rethrow();
		
	}
	
	public static void main(String[] args) {
		try {
			CancelOne.timedRun(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(5000);//当执行时间小于定时任务的等待时间,即时也会走taskThead.interrupt();,但是毫无大碍,否则执行taskThead.interrupt(),时,则会有异常
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("执行完毕");
				}
			}, 3000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
