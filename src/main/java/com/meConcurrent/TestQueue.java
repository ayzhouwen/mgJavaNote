package com.meConcurrent;

import java.util.LinkedList;
//这是一道面试题,题目:下面的代码在绝大部分时间内都运行很正常,请问在什么情况下会出现问题?问题的根源在哪里
//错误代码示例:
public class TestQueue {
	LinkedList list=new LinkedList<>();
	public  void push(Object x){
				synchronized (list) {
					list.addLast(x);
					//list.notify();
					System.out.println("线程通知");
				}
	}
	
	public  Object pop() throws Exception{
		synchronized (list) {
			if (list.size()<=0) {
			//	list.wait();
				System.out.println("线程等待");
			}
			return list.removeLast();
		}
	}
	public static void main(String[] args) {
		TestQueue tQueue=new TestQueue();
		try {
			tQueue.pop();    //比如这样当size<=0时,会永远阻塞,所以一般用synchronized就别用synchronized对象,容易产生死锁
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tQueue.push( "爱你");
		
		System.out.println(tQueue);
		
	}
}
