package com.meConcurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicInteger {
volatile	int sumx=0;   //注意 volatile 并不能保证原子性,但可以强制代码顺序执行,
//AtomicInteger sumx=new  AtomicInteger() ;
	class jisuan  implements Runnable {
		public void run() {
			int count=0;
			while(count<1000){
				count++;
				sumx++;
				//sumx.incrementAndGet();
			}
		
			System.out.println(  Thread.currentThread().getName()+",ok" );
		}
	}
	
	public void sum() throws InterruptedException{
	for (int i = 0; i < 100; i++) {
		//sumx.set(0);;
		sumx=0;
		for(int j=0;j<5;j++){
			jisuan ju=new jisuan();
			Thread t=new Thread(ju);
			t.start();
		}
		
		Thread.sleep(250);
		System.err.println(sumx);
	}

	}
	
	public static void main(String[] args) throws InterruptedException {
		TestAtomicInteger ta=new TestAtomicInteger();
		ta.sum();
	
	}
}
