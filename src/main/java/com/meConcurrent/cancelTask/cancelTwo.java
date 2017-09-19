package com.meConcurrent.cancelTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//通过future来实现取消任务
public class cancelTwo {
	private ExecutorService taskExec=   Executors.newFixedThreadPool(1);
	public  void timedRun(Runnable  r,long timeout,TimeUnit unit) throws InterruptedException{
			Future<?> task=taskExec.submit(r);
			try {
				task.get(timeout,unit);
			}
			catch (TimeoutException e) {
				e.printStackTrace();
			}
			 catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("运行测试");
			}
			finally {
					task.cancel(true);
			}
			
			taskExec.shutdownNow();
			
	}
	
	public static void main(String[] args) {
		cancelTwo ct=new cancelTwo();
		try {
			ct.timedRun(new Runnable() {
				@Override
				public void run() {
							System.out.println("开始执行");
							try {
								Thread.sleep(2000);
								int i=3;
								int j=2;
								int s=4/(i-j-1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.out.println("执行结束");
				}
			}, 3000, TimeUnit.MILLISECONDS);
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
