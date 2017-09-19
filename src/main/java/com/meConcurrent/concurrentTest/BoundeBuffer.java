package com.meConcurrent.concurrentTest;

import java.util.concurrent.Semaphore;

//测试并发程序中使用的简单的缓冲队列 (实际项目中应该使用ArrayBlockingQueue等)
//Semaphore函数底层也是使用的aqs
//release增大 aqs状态监控状态值,即时availableItems初始状态为0,那么也会加1
//acquire 减少aqs监控状态值
public class BoundeBuffer<E> {
	private final Semaphore availableItems; //相当于当前数量
	private final Semaphore availableSpaces;//相当于当前队列中的空位
	private final E[] items;//队列
	private int putPosition=0,takePosition=0;
	public BoundeBuffer(int capacity){
		availableItems=new Semaphore(0);
		availableSpaces=new Semaphore(capacity);
		items=(E[])new Object[capacity];  //很不错的方法,在内部直接生成泛型数组
	}
	public boolean isEmpty(){
		return availableItems.availablePermits()==0;
	}
	
	public boolean isFull(){
		return availableSpaces.availablePermits()==0;
	}
	
	private synchronized void doInsert(E x){

		int i=putPosition;
		items[i]=x;
		//System.out.println("put"+i+":"+items[i]);
		putPosition=(++i==items.length)?0:i;

		
	}
	private synchronized  E   doExtract(){
		int i=takePosition;
	    E x=items[i];
	    items[i]=null; //清除无效的内存引用,类似清除无效的引用计数,切记切记万分重要
	   // System.out.println("take"+i+":"+x);
 		takePosition=(++i==items.length)?0:i;
 		return x;
	}
	public void  put(E x) throws InterruptedException{
		
		availableSpaces.acquire(); 
		doInsert(x);
	   availableItems.release();
	}
	
	public E take() throws InterruptedException{
		availableItems.acquire();
		E item=doExtract();
		availableSpaces.release();
		return item;
	}
	
	public static void main(String[] args) throws InterruptedException {
		BoundeBuffer<Long> buffer=new BoundeBuffer(5);
		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						long l=System.currentTimeMillis();
						//System.out.println("put:"+l);
						buffer.put(l);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}).start();
		
		}
		
		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//System.out.println("take:"+buffer.take());
						buffer.take();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}).start();
		
		}
	}
	
	
}
