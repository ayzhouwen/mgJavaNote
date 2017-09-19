package com.meConcurrent;

import java.util.concurrent.ConcurrentHashMap;

public class TestConcurrentMap {
	public  static 	long stime, etime;
	public static void main(String[] args) {
		ConcurrentHashMap<Integer , Integer> wordMap=new ConcurrentHashMap();
		wordMap.put(1, 3);
		Integer i =wordMap.get(1);
		 System.out.println("开始时间:"+System.currentTimeMillis());
		 
	
		//putIfAbsent 如果key存在则返回 以前的v,不进行put,否则返回null 
		// System.out.println(wordMap.putIfAbsent(1, 7));  
//		 System.out.println(wordMap);
	
		//Increase irun=new Increase(wordMap);
		SafeIncrease irun=new SafeIncrease(wordMap);
		Thread t=new Thread(irun);
		Thread t1=new Thread(irun);
		
		t.start();
		t1.start();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(wordMap);
		//检测是否有线程不安全的值
//		for (Integer key : wordMap.keySet()) {
//			   			if (wordMap.get(key)<2) {
//							System.out.println(key);
//						}
//	 }
	}
}

//线程不安全的
class  Increase implements Runnable{
	ConcurrentHashMap<Integer , Integer> wordMap;
	
	Increase(ConcurrentHashMap wordMap ){
		this.wordMap=wordMap;
	}
	@Override
	public void run() {

		for(int i=0;i<100000000;i++){
			Integer oldv=wordMap.get(i);
			Integer	newValue = (oldv == null) ? 1 : oldv + 1;
			wordMap.put(i, newValue);
			
		}
		System.out.println(Thread.currentThread().getName()+"结束:"+System.currentTimeMillis());
		
	}
	
}

//线程安全
class  SafeIncrease implements Runnable{
	ConcurrentHashMap<Integer , Integer> wordMap;
	
	SafeIncrease(ConcurrentHashMap wordMap ){
		this.wordMap=wordMap;
	}
	@Override
	public void run() {

		for(int i=0;i<10000000;i++){
			Integer	newValue;
         int k=1;
			while (true){
				k++;
				if (k>2) {
					System.out.println(k+"次");
				}
				Integer oldv=wordMap.get(i);
				if (oldv==null) {
					newValue = 1;
					if (wordMap.putIfAbsent(i, newValue)==null) {
						break;
					}
					
				
					
				}else {
					newValue=oldv+1;
					if (	wordMap.replace(i, oldv, newValue)) {
						break;
					}
				
				}
			}
			
		}
		System.out.println(Thread.currentThread().getName()+"结束"+System.currentTimeMillis());
		
	}
	
}



