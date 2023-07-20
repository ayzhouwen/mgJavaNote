package com.meConcurrent.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 最多能创建多少线程与Sleep不释放锁 测试
 *
 * 1.实际测试值是 可用物理内存/内核每个线程的最小占用空间，目前测试的centos7.5内核 100多kb
 * 在win10专业版+32g内存 测试下java堆内存512m 最多创建33万左右线程
 * 虚拟机 centos7.5+32g内存 java堆内存512m 最多创建13万左右线程
 * 调整Xss参数在centos中和win10中均不影响创建线程数量，发现在centos中如果把xss调的很大，java虚拟内存占用很大，不影响实际创建线程的数量

    2. Thread.sleep(1000*30);不释放锁，有可能会造成业务阻塞，但是会释放cpu
        Obj.wait(xxx)会释放锁，也会释放cpu

 */
@Slf4j
public class ThreadCreateNumTest {
    public static void main(String[] args) throws InterruptedException {
        Object lockObj=new Object();
        final int NUM_THREADS = 10000*1000;
        long startTime = System.nanoTime();

        for (int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread(() -> {
                while (true){
                    try {

//                            log.info("获取到锁");

//                            Thread.sleep(1000*30);
//                            lockObj.wait(1);
                        Thread.sleep(10000);


                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                // 一个空的任务
            });
            thread.start();
            log.info("启动第{}个线程",i);
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("创建 " + NUM_THREADS + " 个线程耗时: " + duration / 1000000 + " 毫秒");

        Thread.sleep(1000*30);
    }
}
