package com.meConcurrent.Queue;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exam {
		static  final int studentSize=45;
		public static void main(String[] args) {
			Random r=new Random();
			DelayQueue<Student> students =new DelayQueue<Student>();
			ExecutorService exec=Executors.newCachedThreadPool();
			for(int i=0;i<studentSize;i++){
				students.put(new Student("学生"+(i+1), 3000+r.nextInt(9000)));
			}
			students.put(new Student.EndExam(1200, exec)); //1200为考试结束时间
			exec.execute(new Teacher(students, exec));
			
		}
}
