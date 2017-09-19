package com.meConcurrent;

//注意这里可见性偏重于指令排序,虽然我测试了10次都没发现问题,但是理论上是存在可能性的,
public class VolatileDemo {
	 private static int i = 0;
	    private volatile static boolean isStoped = false;
	     
	    public static void main(String[] args) {
	        new SubThread().start();
	        i = 1000; //如果指令重排序可能线程打印出的是0,但是我没有测试出效果
	        isStoped = true;
	    }
	 
	    private static class SubThread extends Thread{
	        public void run(){
	            while(!isStoped){
	                System.out.println(Thread.currentThread().getName());
	                Thread.yield();
	            }
	             
	            System.out.println(i);
	        }
	    }
}
