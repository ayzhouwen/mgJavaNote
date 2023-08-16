package com.meConcurrent.thread;

//生成素数线程取消测试
class VThread extends Thread {
	private   boolean stop;

	public void run() {
		int i = 0;
		System.out.println("start loop.");
	//	while(!getStop()) {
		while(!stop) {
			i++;
			// System.out.println(stop);
			if(i>999999999){
				System.out.println("挺");
				;
			}
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
public class TheadCancel {

	public static void main(String[] args) {
		//PrimeGenerator generator=new  PrimeGenerator();
		//new Thread(generator).start();
		
		//PrimeGeneratorThead  generator =new  PrimeGeneratorThead();
	//	generator.start();

		VThread thread =new VThread();
	//	thread.start();
		
		new Thread(new  Runnable() {
			public void run() {
				//thread.stopIt();
			}
		}).start();;

		//TheadCancel.open=1;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//generator.cancel();
	//	System.out.println(generator+","+generator.get());
	
		
		VisibilityTest.VisibilityThread v = new VisibilityTest.VisibilityThread();
	        //v.start();
	        thread.start();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//停顿1秒等待新启线程执行
			System.out.println("即将置stop值为true");
			//v.stopIt();
			thread.stopIt();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("finish main");
		//	System.out.println("main中通过getStop获取的stop值:" + v.getStop());
			System.out.println("main中通过getStop获取的stop值:" + thread.getStop());
		
		
	}
}

class  PrimeGeneratorThead extends Thread{
	private   boolean cancelled;
	private  boolean close;

	@Override
	public void run() {
			//BigInteger p=BigInteger.ONE;
			//while(!cancelled){
			//while(TheadCancel.open==0){
		int i = 0;
		System.out.println("start loop.");
			while(!close){
			
//					p=p.nextProbablePrime(); //返回下一个素数
//			
//						System.out.println("已获取"+primes.size());
//						primes.add(p);
			//	System.out.println(System.nanoTime());
				i++;
				System.out.println("a");
			}
			System.out.println("finish loop,i=" + i);
	}
	
	public void cancel(){close=true;}
	
	public boolean getStop(){
		return close;
	}
}
class PrimeGenerator implements Runnable{
	private   boolean cancelled;
	private  boolean close;

	@Override
	public void run() {
			//BigInteger p=BigInteger.ONE;
			//while(!cancelled){
			//while(TheadCancel.open==0){
			while(!close){
			
//					p=p.nextProbablePrime(); //返回下一个素数
//			
//						System.out.println("已获取"+primes.size());
//						primes.add(p);
			//	System.out.println(System.nanoTime());
				System.out.println("a");
			}
		
	}
	
	public void cancel(){cancelled=true; close=true;}
	

}