package com.meConcurrent.concurrentTest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//缓存队列使用Lock+condition实现
public class ConditionBoundedBuffer<T> {
	protected final Lock lock = new ReentrantLock();
	// 条件谓词:notFull (count<items.length)
	private final Condition notFull = lock.newCondition();
	// 条件谓词:notEmpty(count>0)
	private final Condition notEmpty = lock.newCondition();

	private int Buffer_size = 10;
	private final T[] items = (T[]) new Object[Buffer_size];

	private int tail, head, count;

	// 阻塞,直到notfull
	public void put(T x) throws InterruptedException {
		lock.lock();
		try {
			while (count == items.length) {
				notFull.await();
			}
			
			items[tail] = x;
			if (++tail==items.length) {
				tail=0;
			}
			count++;
			notEmpty.notify();
		} finally {
			lock.unlock();
		}
	}
	
	//阻塞,直到notempty
	public T take() throws InterruptedException{
		lock.lock();
		try {
			while (count==0) {
					notEmpty.await();
			}
			T x=items[head];
			items[head]=null;//释放
			if (++head==items.length) {
				head=0;
			}
			--count;
			notFull.signal();
			return x;
		} catch (Exception e) {
			lock.unlock();
		}
		return null;
	}
}
