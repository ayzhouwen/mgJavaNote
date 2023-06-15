package com.meConcurrent.jdk8;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

/**
 * 并行集合处理总结
 * 1.list.parallelStream()可以实现CountDownLatch效果，即可以实现list全部并行处理完后，主线程才继续往下走，此时主线程是参与计算的
 * 2.list.parallelStream() 一定要 try catch 否则一个线程崩了，所有都崩了
 * 3.采用自定义的forkJoinPool线程池去提交任务，主线程不会参与计算。
 * forkJoinPool线程池采用submit异步提交任务，通过get方法阻塞主线程，直到任务执行完成，再调用shutdown方法关闭线程池。
 * 即使在forkJoinPool.submit
 */
@Slf4j
public class ParallelStreamTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //全局设置ForkJoinPool默认公共线程数数量,一般不这样搞
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "1");
        int size=5000;
        List<String> list=new ArrayList<>();
        for (int i = 0; i <size ; i++) {
            list.add("host"+i);
        }

        //实验计数用，生产代码不需要CountDownLatch
        CountDownLatch countDownLatch=new CountDownLatch(size);
        ForkJoinPool forkJoinPool1 = new ForkJoinPool(16);
        forkJoinPool1.submit(()->{
            //如果parallelStream改为streamn 那么forkJoinPool只拿出一条线程来执行
            list.parallelStream().forEach(e->{
                try {
                    if (!e.equals("host1111111")){
                        throw new RuntimeException("崩溃喽");
                    }
                    log.info(e);

                } catch (Exception ex) {
                    log.error("处理异常：",ex);
                }finally {
                    countDownLatch.countDown();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            });
        }).get();
        //注意主线程在get方法就开始阻塞了
        countDownLatch.await();
        forkJoinPool1.shutdown();
        log.info("执行到这里了===========================");
        Thread.sleep(1000*60);
    }
}
