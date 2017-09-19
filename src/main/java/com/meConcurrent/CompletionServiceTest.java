package com.meConcurrent;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
 * 完成线程CompletionService返回值操作(按完成操作的线程的先后打印) 
 * @author tanfei 
 * @date 2012-02-03 
 */  
public class CompletionServiceTest {
			public static void main(String[] args) {
				ExecutorService threadPool=Executors.newFixedThreadPool(3);//创建固定线程池
				CompletionService<Integer> completionService=new ExecutorCompletionService<>(threadPool);
				for(int i=0;i<=10;i++){
					final int task=i; //任务编号
					completionService.submit(new Callable<Integer>() {  //执行任务
						
						@Override
						public Integer call() throws Exception {
							     System.out.println(Thread.currentThread().getName());
									Thread.sleep(new Random().nextInt(2000)); //随机产生小于5秒钟
									System.out.println("---------------");
									return task;
						}
					});
				}
				
				System.out.println("等待结果");
				
				for (int i = 0; i < 10.; i++) {
					try {
						System.out.println(completionService.take().get()); //按照执行完成的先后顺序打印返回结果
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				threadPool.shutdown();//关闭线程池
			}
}
