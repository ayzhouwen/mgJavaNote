package com.meConcurrent.Queue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

//优先级队列测试
public class PriorityQueueTest {
		private String name;
		private int population;
		public PriorityQueueTest(String name,int  population){
			this.name=name;
			this.population=population;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getPopulation() {
			return population;
		}
		public void setPopulation(int population) {
			this.population = population;
		}
		
		public String toString(){
			return getName()+" - "+getPopulation();
		}
		public static void main(String[] args) {
			Comparator<PriorityQueueTest>OrderIsdn=new Comparator<PriorityQueueTest>(){
				public int compare(PriorityQueueTest o1,PriorityQueueTest o2){
					int n1=o1.getPopulation();
					int n2=o2.getPopulation();
					if (n1>n2) {
						return 1;
					}else if (n1<n2) {
						return -1;
					} else  {
						return 0;
					}
				}
			};
			
			Queue<PriorityQueueTest>  priorityQueue=new PriorityQueue<PriorityQueueTest>(11,OrderIsdn);
			PriorityQueueTest t1=new  PriorityQueueTest("t1",2222);
			PriorityQueueTest t3=new  PriorityQueueTest("t3",444);
			PriorityQueueTest t2=new  PriorityQueueTest("t2",500);
			PriorityQueueTest t4=new  PriorityQueueTest("t4",999);
			priorityQueue.offer(t1);//offer和add 都一样
			priorityQueue.add(t2);
			priorityQueue.add(t3);
			priorityQueue.offer(t4);
			System.out.println(priorityQueue.peek());
			 System.out.println(priorityQueue.poll().toString());   //出队列的顺序是按照谁最小来出,而不是创建的顺序
	
		}
}
