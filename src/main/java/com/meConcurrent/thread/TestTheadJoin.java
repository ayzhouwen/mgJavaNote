package com.meConcurrent.thread;
//测试线程join方法
//a线程调用b.join()方法,a线程会阻塞,直到b线程执行完毕,注意join方法一般情况下不是本线程调用的,而是由其他线程调用的
//join的方法源码是判断显示是否存活,如果存活则阻塞当前线程!!!(注意是主调函数的线程)
public class TestTheadJoin {
	public  static void main(String[] args) {
//		try {
//			//Thread.currentThread().join();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		 String threadName=Thread.currentThread().getName();
			System.out.println(threadName+"statrt.");
			BThread bt=new BThread();
			AThread at=new AThread(bt);
			try {
				bt.start();
				Thread.sleep(2000);
				at.start();
				at.myjoin();//此时main线程会阻塞,知道at线程执行完毕
			} catch (Exception e) {
				System.out.println("Exception from main");
			}
			
			System.out.println(threadName+" end!");
	}
}

class BThread extends MyThead{
	public BThread(){
		super("[BThread] Thread");
	}
	
	public void run(){
		String threadName=Thread.currentThread().getName();
		System.out.println(threadName+"start.");
		try {
			for(int i=0;i<5;i++){
				System.out.println(threadName+" loop at"+i);
				Thread.sleep(2000);
			}
			System.out.println(threadName+"end.");
		} catch (Exception e) {
				System.out.println("Exception from "+threadName+".run");
		}

	}
}

class AThread extends MyThead{
	BThread bt;
	public  AThread(BThread bt){
		super("[AThread Thread]");
		this.bt=bt;
	}
	
	public void run(){
		String threadName=Thread.currentThread().getName();
		System.out.println(threadName+" start.");
		try {
			bt.myjoin();//此时at线程会阻塞,知道bt线程执行完毕
			System.out.println(threadName+" end.");
		} catch (Exception e) {
			System.out.println("Exception from "+threadName+" .run");
		}
	}
	
}

//由于join不能被重写,所以直接拷贝所有代码来验证join方法的执行逻辑

//1.首先是join方法是 synchronized wait()方法是获取Thread 的class对象的锁
//所以当前线程会阻塞
//当线程执行完毕后会在c++代码中执行类似notify all 的方法来释放锁,所以主线程又可以执行了,最后在判断while 循环,条件为FALSE,退出循环
class MyThead extends Thread{
	MyThead(String name){
		super(name);
	}
	
	boolean	t1(){
		System.out.println("到此一游"+System.currentTimeMillis());
		return true;
	}
    public final synchronized void myjoin() throws InterruptedException{
    	long millis=0;
        long base = System.currentTimeMillis();
        long now = 0;

        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (millis == 0) {
        	System.out.println("this:"+this.currentThread().getName());
            while (t1()&&this.isAlive()) {
                wait(0);
            }
        } else {
            while (isAlive()) {
                long delay = millis - now;
                if (delay <= 0) {
                    break;
                }
                wait(delay);
                now = System.currentTimeMillis() - base;
            }
        }
        System.out.println("join完毕");
    }
}