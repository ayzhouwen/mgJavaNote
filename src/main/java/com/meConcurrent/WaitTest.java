package com.meConcurrent;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/9.
 */
//http://blog.csdn.net/hel_wor/article/details/51195204
    //关于wait等待异常的问题,java.lang.IllegalMonitorStateException
public class WaitTest {
    private  static ArrayList<Integer> list=new ArrayList<>();

    public static void main(String[] args) {
        for (int i=0;i<10;i++){
            Runnable runnable =new Runnable() {
                @Override
                public void run() {
                    synchronized (list){
                        while (list.size()==0){
                            try {
                                System.out.println("Thread-"+Thread.currentThread().getId()+"-竞争资源失败,挂起");
                                list.wait();
                                System.out.println("Thread-"+Thread.currentThread().getId()+"-被唤醒,转呗二次检测");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                break;
                            }

                        }
                        System.out.println("Thread-"+Thread.currentThread().getId()+"-获得真实的先验条件,运行后续逻辑");
                        System.out.println(list.get(0));
                        list.remove(0);


                    }
                }
            };

            Thread thread=new Thread(runnable);
            thread.start();
        }

      Runnable runnable2=new Runnable() {
          @Override
          public void run() {
                synchronized (list){
                    list.add(6666);
                    list.notifyAll(); //this.notifyAll();这会报错,因为main线程没有持有list锁对象
                }
          }
      };
        Thread thead2=new Thread(runnable2);
        thead2.start();
    }
}
