package com.meConcurrent.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池异常测试，结论：execute内部拉姆达函数发生异常，不会干扰其他的任务，也不会影响主线程
 */
@Slf4j
public class PoolExcept2 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPoolExecutor= Executors.newFixedThreadPool(1);
        int size=30;
        CountDownLatch countDownLatch=new CountDownLatch(size);
        for (int i = 0; i <size ; i++) {
            int finalI = i;
            threadPoolExecutor.execute(()->{
                try {
                    if (finalI!=0){
                        throw  new RuntimeException("崩溃喽"+finalI);
                    }
                } finally {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    countDownLatch.countDown();
                }
                log.info(finalI+"");
            });
        }
        countDownLatch.await();
        threadPoolExecutor.shutdown();
    }
}
