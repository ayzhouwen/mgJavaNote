package com.meConcurrent.thread;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 测试线程状态，在VisualVM中观察
 * 参考
 * https://blog.csdn.net/jcw321/article/details/103326528
 */

/**
 *
 * visualvm中线程几种状态的模拟
 * 主要涉及到
 * Running： 运行态，此时正在使用CPU的时间片
 * Sleeping：休眠状态，调用Thread.sleep(),不占用CPU
 * Wait:调用Object.wait、Thread.join等方法会进入这个状态
 * Park：调用LockSupport.park等方法会进入这个状态，底层调用的是
 * Monitor：通过synchronized抢占锁
 **/
public class ThreadStateSimulateTest {

    public static void main(String[] args) {
        // 模拟sleeping, Thread.sleep()会使线程进入Sleeping状态
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {}
            }
        }, "simulateSleeping");
        t.start();
        // 调用Thread.join的线程会进入Wait状态
        Thread t1 = new Thread(() -> {
            System.out.println("开始模拟wating");
            try {
                t.join();
            }catch (InterruptedException e) {}
        }, "simulateWaiting");
        t1.start();

        // LockSupport.park会让线程进入park状态（其实就是不涉及锁的情况下，直接让线程让出cpu）
        Thread t2 = new Thread(() -> {
            System.out.println("park方法直接让线程让出cpu");
            LockSupport.park();
        }, "simulateParking");
        t2.start();

        Thread t3 = new Thread(() -> {
            say();
        }, "simulateMonitor1");

        Thread t4 = new Thread(() -> {
            say();
        }, "simulateMonitor2");
        t3.start();
        t4.start();

        Thread t5 = new Thread(() -> {
            while(true) {}
        }, "simulateRunning");
        t5.start();
    }

    public synchronized static void say() {
        while(true) {
            try {
                // 下面两个操作都不会让出synchronized占用的锁资源
                System.out.println("synchronized:" + Thread.currentThread());
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(500));
                Thread.sleep(500);
            }catch (Exception e) {}
        }
    }


}