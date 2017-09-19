package com.meConcurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//测试在同步对象下,锁后面的代码是否会执行还是阻塞,结论是阻塞的
public class TestObjSync {
	private Object lock = new Object();
	private Lock rLock = new ReentrantLock();  

	
	class MyRun implements Runnable {
		private String name;

		MyRun(String tname) {
			this.name = tname;
		}

		@Override
		public void run() {
			System.out.println(name + "开始加锁");
			int count = 0;
			synchronized (lock) {
				while (true) {
					try {
						count++;
						System.out.println(name + "等待" + count + "秒");
						Thread.sleep(1000);
						if (count>4) {
							break;
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			System.out.println(name+"结束加锁");
		}

	}
	
	  class   MyLockRun implements Runnable{
			private String name;

			MyLockRun(String tname) {
				this.name = tname;
			}
		@Override
		public void run() {
			System.out.println(name + "开始加锁");
			int count = 0;
			rLock.lock();  
			try {
				while (true) {
					try {
						count++;
						System.out.println(name + "等待" + count + "秒");
						Thread.sleep(1000);
						if (count>4) {
							break;
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			} finally {
				rLock.unlock();   
			}
			
			System.out.println(name+"结束加锁");
		
			
		}
		  
	  }

	public void testSync() {
	//MyRun mr = new MyRun("孙悟空");
		//MyRun mr1 = new MyRun("唐僧");
		
		MyLockRun mr2 = new MyLockRun("赵云");
		MyLockRun mr3 = new MyLockRun("吕布");
		//new Thread(mr).start();
	//	new Thread(mr1).start();
		new Thread(mr2).start();
	   new Thread(mr3).start();
		// mr.run();
		// mr1.run();
	}

	public static void main(String[] args) {
		TestObjSync to = new TestObjSync();
		to.testSync();
	}
}
