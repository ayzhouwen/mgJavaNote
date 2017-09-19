package com.meConcurrent;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

//自定义开发组件
public class Mutex  implements Lock,Serializable {
//内部类,自定义同步器
	private static class Sync extends AbstractQueuedSynchronizer{
				//是否处于占用状态
		protected boolean isHeldExclusively() {
			return getState()==1;
		}
		
		//状态为0的时候获取锁
		public boolean tryAcquire(int acquires){
			 assert acquires == 1; // Otherwise unused
			 if (acquires !=1) {
				return false ;
			}
			 if (compareAndSetState(0,1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
             return false;
			
		}
		
		//释放锁,讲状态设置为0
		
		protected boolean tryRelease(int releases){
			assert releases == 1; // Otherwise unused

			 if (releases !=1) {
					return false ;
				}
			 
			 if (getState()==0) {
				 throw new IllegalMonitorStateException();
			}
			 setExclusiveOwnerThread(null);
			 setState(0);
			 return true;
		}
		//返回一个Condition ,每个Condition都包含了一个Condition队列
		Condition newCondition(){  return new  ConditionObject();}
	}
	//仅需要操作代理到sync上即可
	private final Sync sync=new Sync();
	@Override
	public void lock() {
		sync.acquire(1);
	}
	

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
		
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
			return sync.tryAcquireNanos(1, unit.toNanos(timeout));
	}

	@Override
	public void unlock() {
		sync.release(1);
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}
	public boolean isLocked(){
		return sync.isHeldExclusively();
	}
	
	public boolean hasQueuedThreads(){
		return sync.hasQueuedThreads();
	}
	

public static void main(String[] args) {
	 assert 0 == 1; 
	 System.out.println("执行ok");
}
}
