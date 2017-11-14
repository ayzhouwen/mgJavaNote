package com.meConcurrent.concurrentTest;
//在并发下测试并发队列的安全性

//自己的感悟:自己照着书上的代码写都能写错(出了2处错误),造成了与书上的结果不一致的情况(现象是程序一直阻塞,不会自动结束)
//并发调试是很蛋疼的,最土的方法,最简陋的方法往往也是最有效的方法,逐步分析,设好打印日志的关键点,逐步分析,缩小包围圈,也可以借助visualVm来分析线程的大致状态
//当然要有实事求是的心态,和坚持不懈,不骄不躁,不能图快,以追求赶紧看完书为目的,学习本身是对知识的领悟和掌握
//而不是一味地囫囵吞枣,那不学也罢

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PutTakeTest {
		private static final ExecutorService pool= Executors.newCachedThreadPool();
		private final AtomicInteger putSum=new AtomicInteger(0);
		private final AtomicInteger takeSum=new AtomicInteger(0);
		private final CyclicBarrier barrier; //CyclicBarrier 状态值减到0时,会自动恢复到原来的值
		private final BoundeBuffer<Integer> bb;
		private final int nTrials,nPairs;
		public static int xorShift(int y){  //随机函数 (marsaglia 2003) ,基于hashcode和nanotime计算随机数,结果不可预知,几乎每次运行都不同
			y^=(y<<6);
			y^=(y>>>21);
			y^=(y<<7);
			return y;
		}
		
		
		public PutTakeTest(int capacity,int npairs, int ntrials){
			this.bb=new BoundeBuffer<>(capacity);
			this.nTrials=ntrials;
			this.nPairs=npairs;
			this.barrier=new CyclicBarrier(npairs*2+1);  //21个为一个周期(主线程1个,生产线程10个,消费线程10个)
		}
		
		public void  test(){
			try {
				for(int i=0;i<nPairs;i++){
					pool.execute(new Producer(i,2*i+0.9));
					pool.execute(new Consumer());
				}
					barrier.await();//等待所有线程做好准备
					//Thread.sleep(5000);
					barrier.await();//等待所有线程最终完成
			    System.out.println("putsum:"+putSum.get());
			    System.out.println("takesum:"+takeSum.get());
			     
				System.out.println("ok");
			} catch (Exception e) {
					e.printStackTrace();
			}
		}
		
		class Producer implements Runnable{
			public  Producer(int id,double price){}; //测试构造函数
			@Override
			public void run() {
				try {
					int seed=(this.hashCode()^(int)System.nanoTime());
					int sum=0;
					System.out.println("product:开始等待1前"); //注意如果在await()后面写输出,那么输出的内容是无法保证在主线程的ok前面的,因为await后就是靠线程竞争了,没有顺序了
					barrier.await(); //等待一起开始
					for(int i=nTrials;i>0;--i){
						bb.put(seed);
						sum+=seed;
						seed=xorShift(seed);
					}
			
					putSum.getAndAdd(sum);
					System.out.println("product:开始等待2前"); //同上
					barrier.await(); //等待一起结束
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				
			}
			
		}
		
		class Consumer implements Runnable{
			@Override
			public void run() {
				try {
					System.out.println("consumer:等待1前");//输出位置同Producer
					barrier.await();
					Integer  sum=0;
					for(int i=nTrials;i>0;--i){
						sum+=bb.take();
					}
				
					takeSum.getAndAdd(sum);
					System.out.println("consumer:等待2前");//输出位置同Producer
					barrier.await();

				} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e);
				}
				
			}
		}
		 public static void main(String[] args) {
//			 Object object=new Object();
//				int seed=(object.hashCode()^(int)System.nanoTime());
//				for (int i = 0; i < 99999; i++) {
//					seed=PutTakeTest.xorShift(seed);
//				//	System.out.println(PutTakeTest.xorShift(seed));
//				}
			 
			 PutTakeTest pt=new  PutTakeTest(10, 10,10000);
			 pt.test();
				
		}
}


