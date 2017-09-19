package com.meConcurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

//测试CyclicBarrier,无限循环
public class line2 {
	final CyclicBarrier barrier;
	
	//线程数
	int count;
	
	class Worker implements Runnable{
		int index;
		Worker(int index){
			this.index = index;
		}
		public void run() {
			System.out.println("第" + index + "个线程休眠"+(2 * index)+"秒！");
			try {
				Thread.sleep(2000 * index);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("第" + index + "个线程结束休眠！");
			
			try {
				//等待其它线程都处理完毕后，再继续以下代码的执行
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			
			System.out.println("第" + index + "个线程继续剩下的任务");
		}
	}
	
	public line2(int count){
		this.count = count;
		
		//公共屏障点 等待到5个线程后，执行相应的barrierAction
		barrier = new CyclicBarrier(count, new Runnable() {
			public void run(){
				System.out.println("全部线程已执行完毕！");
				System.out.println("----------------------");
				for(int i=1;i<=5;i++){		//当全部线程执行完，重新添加线程，重用CyclicBarrier对象									
					new Thread(new Worker(i)).start();//此程序无限循环需手动结束
				}
				try {
					TimeUnit.MILLISECONDS.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		for(int i=1;i<=5;i++){
			new Thread(new Worker(i)).start();
		}
	}
	
	public static void main(String[] args) {
		new line2(5);
	}
}
