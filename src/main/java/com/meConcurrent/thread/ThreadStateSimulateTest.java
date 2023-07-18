package com.meConcurrent.thread;


import com.socket.tcp.demo1.TimeServer;
import com.util.JarTool;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试线程状态，在VisualVM中观察
 * 参考
 * https://blog.csdn.net/jcw321/article/details/103326528
 */

/**
 *
 * visualvm中线程几种状态的模拟
 * 主要涉及到
 * Running： 运行态，此时不一定正在使用CPU的时间片，比如 serverSocket.accept() 和in.readLine()等,
 * 虽然前面两个处于运行状态，但是是等待系统io资源，并没有占用cpu，可以简单的理解为处于阻塞状态
 * Sleeping：休眠状态，调用Thread.sleep(),不占用CPU 线程状态 TIMED_WAITING
 * Wait:调用Object.wait、Thread.join LockSupport.park 等方法会进入这个状态
 * Park：调用LockSupport.park等方法会进入这个状态，底层调用的是
 * Monitor：通过synchronized抢占锁 ,对应的jvm线程状态是 Blocking
 **/
@Slf4j
public class ThreadStateSimulateTest {

    private static  ReentrantLock lock=new ReentrantLock();

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
            lockSay();
        }, "lockWaiting");
        Thread t6 = new Thread(() -> {
            lockSay();
        }, "lockWaiting");
        t5.start();
        t6.start();

        Thread t7=new Thread(()->{
            TimeServer.main(null);
        },"socketBroker");
        t7.start();
        Thread t8=new Thread(()->{
            while(true){
                try {
                    URL url = new URL("https://www.baidu.com");
                    InputStream inputStream = url.openStream();
                    // 这里模拟I/O操作，阻塞线程
                    byte[] buffer = new byte[1024];
                    inputStream.read(buffer);
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"socketBroker2");
        t8.start();



        Thread t9 = new Thread(() -> {

                while (true){
                    try {
                    FileInputStream inputStream = new FileInputStream(JarTool.getJarDir()+"/myconfig.json");
                    // 这里模拟I/O操作，阻塞线程
                    byte[] buffer = new byte[1024];
                    inputStream.read(buffer);
                    inputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


        },"fileBroker1");

        t9.start();

        Thread t10 = new Thread(() -> {

         while (true){
             log.info("输出");
         }

        },"fileBroker2");



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

    /**
     *  lock.lock()方法底层调用的是LockSupport.park ，所以对应线程状态是Waiting
     */
    public  static void lockSay() {
        //开启锁，但是故意不释放
        lock.lock();
        while(true) {
            try {

                System.out.println("lock:" + Thread.currentThread());
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(500));
                Thread.sleep(500);
            }catch (Exception e) {}
        }
    }


}