package com.meConcurrent;

class VisibilityThread extends Thread {
	private  volatile boolean stop; // 不加volatile 会死循环

	public void run() {
		int i = 0;
		System.out.println("start loop.");
	//	while(!getStop()) {
		while(!stop) {
			i++;
		}
		System.out.println("finish loop,i=" + i);
	}

	public void stopIt() {
		stop = true;
	}

	public boolean getStop(){
		return stop;
	}
}


class VisibilityRunable implements Runnable {
	private  volatile boolean stop;

	public void run() {
		int i = 0;
		System.out.println("start loop.");
	//	while(!getStop()) {
		while(!stop) {
			i++;
			//System.out.println(System.nanoTime());
			System.out.println("a");
		}
		System.out.println("finish loop,i=" + i);
	}

	public void stopIt() {
		stop = true;
	}

	public boolean getStop(){
		return stop;
	}
}
public class VisibilityTest {
	public static void main(String[] args) throws Exception {
		 VisibilityThread v = new VisibilityThread();
		  v.start();
		  
//		VisibilityRunable v=new VisibilityRunable();
//	   Thread t=new Thread(v);
//		t.start();
       
		Thread.sleep(2000);//停顿1秒等待新启线程执行
		System.out.println("即将置stop值为true");
		v.stopIt();
		Thread.sleep(1000);
		System.out.println("finish main");
		System.out.println("main中通过getStop获取的stop值:" + v.getStop());
	}
}