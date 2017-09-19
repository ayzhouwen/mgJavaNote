package com.meConcurrent;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;



//Condition用于唤醒或休眠一个在重入锁中的线程
public class TestCondition {
	
	public static void main(String[] args) {
		TestCondition t=new  TestCondition();
		t.test1();
	}
	
	public void test1(){
		final ReentrantLock  reentrantLock =new ReentrantLock();
		final Condition  condition = reentrantLock.newCondition();
		//lamada表达式
		Thread  thread=new Thread((Runnable) () ->{ 
			int i=0;
			while(true){
		
				reentrantLock.lock();
	
				i++;
				try {
					Thread.sleep(1000);
					System.out.println("不给你锁,线程2你就等着吧");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (i%3==0) {
					System.out.println("线程2给你锁吧");
			        try {
			        	condition.signalAll();
						condition.await();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				reentrantLock.unlock();
				
			}
			
		},"waitThread1");
	
		
		  
	    Thread thread1 = new Thread((Runnable) () -> {
	            reentrantLock.lock();
	            System.out.println("我拿到锁了");
	            try {
	                Thread.sleep(3000);
	            }
	            catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            condition.signalAll();
	            System.out.println("我发了一个信号！！");
	            reentrantLock.unlock();
	    }, "signalThread");
	    
	  
	    
	    
	    Thread thread2 = new Thread((Runnable) () -> {
     int i=0;
	    	while (true) {
	    		i++;
	            try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            reentrantLock.lock();
            System.out.println("线程2:我拿到锁了");
            try {
                Thread.sleep(200);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if(i%5==0){
            	try {
            		condition.signalAll();
					condition.await();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            reentrantLock.unlock();
      
            
			}
    }, "sleepTheadx");
    
    thread2.start();
    thread1.start();
	thread.start();
	}
}
