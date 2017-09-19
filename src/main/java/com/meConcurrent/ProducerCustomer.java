package com.meConcurrent;
//在取消队列中使用致命药丸

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class ProducerCustomer {
	private BlockingQueue<String> queue=new ArrayBlockingQueue<String>(99999999);
	public static void main(String[] args) {
		ProducerCustomer tp=new ProducerCustomer();
			for(int i=0;i<1;i++){
				Producer p=new Producer(tp.queue,"生产"+i);
				p.start();
			}
			
			for(int i=0;i<1;i++){  //不要小看for循环,消费线程>=生产线程时,可以很好的
				Customer p=new Customer(tp.queue,"消费"+i);
				p.start();
			}
	}
}


class Producer extends Thread{
	private BlockingQueue<String> queue;
	Producer( BlockingQueue<String> queue,String name ){
		this.queue=queue;
		currentThread().setName(name);
	}
	@Override
	public void run() {
		Random ra =new Random();
		while(!currentThread().isInterrupted()){
			try {
				String t=System.currentTimeMillis()+"";
				
				//Thread.sleep(ra.nextInt(1)+1);
			//	Thread.sleep(50);
				queue.put(t);
				//System.out.println("生产:"+currentThread().getName()+","+t);
			} catch (InterruptedException e) {
				System.out.println("生产"+currentThread().getName()+"被主线程强行终止");
				currentThread().interrupt();
				e.printStackTrace();
			}
		} 

	}
}

class Customer extends  Thread{
	private BlockingQueue<String> queue;
	Customer( BlockingQueue<String> queue,String name ){
		this.queue=queue;
		currentThread().setName(name);
	}
	
	@Override
	public void run() {
		Random ra =new Random();
		while(!currentThread().isInterrupted()){
			try {
				
			//Thread.sleep(ra.nextInt(0)+1);
				//Thread.sleep(50);
				 String  t= queue.take();
				//System.out.println("消费"+currentThread().getName()+","+t);
				// t=null;
			} catch (InterruptedException e) {
			System.out.println("消费"+currentThread().getName()+"被主线程强行终止");
				currentThread().interrupt();
				e.printStackTrace();
			}
		} 
	}
}
