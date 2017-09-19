package com.meConcurrent.concurrentTest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//使用lock实现的计数信号量
public class SemaphoreOnLock {
	private final Lock lock=new ReentrantLock();
	//条件谓词:permitAvailable(permits>0)
	private final Condition permitAvailable =lock.newCondition();
	
	private int permits;
	public SemaphoreOnLock(int initialPermits) {
		lock.lock();
		try {
			permits=initialPermits;
		} finally {
			lock.unlock();
		}
	}
	//阻塞,直到permitsavailable
	public void acquire() throws InterruptedException{
		lock.lock();
		try {
			while (permits<=0) {
				permitAvailable.await();
				--permits;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			lock.unlock();
		}
	}
	
	public void release(){
		lock.lock();
		try {
			++permits;
			permitAvailable.signal();
		} finally {
			lock.unlock();
		}
	}
}
