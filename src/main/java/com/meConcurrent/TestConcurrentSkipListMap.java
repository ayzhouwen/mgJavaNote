package com.meConcurrent;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

//测试并发跳表类,跳表的key值可以排序,实现却比treeMap更简单
public class TestConcurrentSkipListMap {
	final  ConcurrentNavigableMap<Integer, String> cslm=new ConcurrentSkipListMap<Integer, String>() ;
	

		public static void main(String[] args) {
			Semaphore semaphore=new Semaphore(5);
			//semaphore.availablePermits()
		}
}

class Counter5{
	private AtomicInteger count=new AtomicInteger(0);
	
	public int increamentAndGet(){
		return count.incrementAndGet();
	}
	
	public int decreamentAndGet(){
		return count.decrementAndGet();
	}
	
}
