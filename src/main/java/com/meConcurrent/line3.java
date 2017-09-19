package com.meConcurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//测试countDown,只能实现单次屏障,注意跟CyclicBarrier区别
public class line3 implements Runnable{
    private CountDownLatch cd;
    private int id;
    public line3(CountDownLatch cd,int id){
        this.cd=cd;
        this.id=id;
    }
    public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(id*1000);
                cd.countDown();
                System.out.println("countDown "+id);
                cd.await();
                 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("line "+id+" Dead");
    }
    public static void main(String[] args) {
        final CountDownLatch cd=new CountDownLatch(10);
        final ExecutorService es=Executors.newCachedThreadPool();
         
        for (int i = 0; i < 9; i++) {
            es.execute(new line3(cd,i));
        }
        es.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(9*1000);
                    cd.countDown();
                    System.out.println("countDownxx "+9);
                    cd.await();
                    for (int i = 10; i < 20; i++) {//这里id为10-20区别于前10个进程，但是等待的时间稍有点长
                        es.execute(new line3(cd,i));
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        es.shutdown();
        System.out.println("main Dead");
    }
}
