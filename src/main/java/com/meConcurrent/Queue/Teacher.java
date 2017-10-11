package com.meConcurrent.Queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;

public class Teacher  implements Runnable {
		private DelayQueue<Student> students;
		private ExecutorService exec;
	@Override
	public void run() {
		try{
			System.out.println("考试开始.....");
			while(!Thread.interrupted()){
				students.take().run();
			}
			
			System.out.println("考试结束.....");
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		
	}
	
	
	public Teacher(DelayQueue<Student> students,ExecutorService exec ){
		super();
		this.students=students;
		this.exec=exec;
	}

}
