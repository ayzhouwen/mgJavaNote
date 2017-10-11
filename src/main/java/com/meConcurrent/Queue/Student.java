package com.meConcurrent.Queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Student implements Runnable,Delayed{
private String  name;
private long submitTime;//交卷时间
private long workTime; //考试时间
	
	@Override
	public int compareTo(Delayed o) {
		Student that=(Student)o;
		return submitTime>that.submitTime?1:(submitTime<that.submitTime?-1:0);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(submitTime-System.nanoTime(), unit.NANOSECONDS);
	}

	@Override
	public void run() {
		System.out.println(name+"交卷,用时"+workTime/100+"分钟");
	}
	
	public static class EndExam extends Student{
			private ExecutorService exec;
			public EndExam(int submitTime,ExecutorService exec){
				super(null,submitTime);
				this.exec=exec;
			}
			 @Override    
		        public void run() {    
		            exec.shutdownNow();    
		        }    
	}
	public Student(){}
	
	public Student(String name,long submitTime){
		super();
		this.name=name;
		workTime=submitTime;
		//都转换为ns
		this.submitTime=TimeUnit.NANOSECONDS.convert(submitTime, TimeUnit.MILLISECONDS)+System.nanoTime();
	}
public static void main(String[] args) {
	
	for(int i=0;i<1000;i++){
		System.out.println("毫秒i"+i+","+System.currentTimeMillis());
		System.out.println("i"+i+","+System.nanoTime());
	}
	
}
}
