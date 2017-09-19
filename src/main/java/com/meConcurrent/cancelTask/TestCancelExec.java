package com.meConcurrent.cancelTask;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.meConcurrent.cancelTask.SocketUSingTask.CancellingExecutor;
import com.sun.org.apache.regexp.internal.recompile;




class SocketUSingTaskImple extends  SocketUSingTask{

	@Override
	public Object call() throws Exception {

		int count=0;
		while(true&&!Thread.currentThread().isInterrupted()){
			count++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
			System.out.println(Thread.currentThread().getName()+":执行"+count+"次");
		}
			
		return null;
	}
	
}
public class TestCancelExec {
		public static void main(String[] args) throws InterruptedException {
			CancellingExecutor ce=new CancellingExecutor(1, 1, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
			//ThreadPoolExecutor tpe =(ThreadPoolExecutor) Executors.newFixedThreadPool(1);
			//CancellingExecutor ce=(CancellingExecutor) Executors.newFixedThreadPool(1);
			SocketUSingTaskImple ct=new  SocketUSingTaskImple();
		    Future<Integer> k=     ce.submit(ct);
	
	
			Thread.sleep(5000);
			System.out.println(Thread.currentThread().getName()+":准备停止服务");	;
			//ce.newTaskFor(callable)
			k.cancel(true);
		//	ce.shutdown();
		}
}
