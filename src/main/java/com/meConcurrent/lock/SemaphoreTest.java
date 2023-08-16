package com.meConcurrent.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

//测试信号量
//http://blog.csdn.net/shihuacai/article/details/8856526
public class SemaphoreTest {
	public static void main(String[] args) {
		// 线程池
		ExecutorService exec = Executors.newCachedThreadPool();
		// 只能5个线程同时访问
		final Semaphore semp = new Semaphore(3);//获取锁资源数量,为0一个也不能访问(除非	最先执行semp.release();),会一直阻塞,相当去最大并发数量,类似阻塞队列,
		// 模拟20个客户端访问
	
		for (int index = 0; index < 20; index++) {
			final int No = index;
			Runnable run = new Runnable() {
				@Override
				public void run() {

					try {
						// 获取许可
					
						semp.acquire();
						System.out.println("Accessing: " + No);
						//Thread.sleep((long) (Math.random() * 10000));
						Thread.sleep(3000);
						// 访问完后,释放,如果屏蔽下面的语句,则在控制台只能打印5条记录,之后线程一直阻塞
						semp.release();

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			exec.execute(run);
		}
		//退出线程池
		exec.shutdown();

	}
}
