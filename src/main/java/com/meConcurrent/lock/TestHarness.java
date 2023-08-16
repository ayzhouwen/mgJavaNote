package com.meConcurrent.lock;

import java.util.concurrent.CountDownLatch;

//另一种多任务同步锁机制
public class TestHarness {
	public long timeTasks(int nThreads,final Runnable task) throws InterruptedException{
		final CountDownLatch startGate=new CountDownLatch(1);
		final CountDownLatch endGate=new CountDownLatch(nThreads);
		for(int i=0;i<nThreads;i++){
			Thread t=new Thread(){
				public void run(){
					try {
						try {
							startGate.await();
							task.run();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} finally{
							endGate.countDown();
					}
				}
			};
			t.start();
		}
		long start=System.nanoTime();
		System.out.println("t"+start);
		startGate.countDown();
		endGate.await();
		long end=System.nanoTime();
		return end-start;

	}
	
	public static void main(String[] args) {
		Runnable myrun=new Runnable(){
			@Override
			public void run() {
						System.out.println(Thread.currentThread().getName()+"开始执行任务");
					 
						try {
							Thread.sleep((int)(Math.random()*100)+500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName()+"结束执行任务");
			}
			
		};
		
		TestHarness th=new  TestHarness();
		try {
		long t=	th.timeTasks(10, myrun);
		System.out.println("t:"+t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
