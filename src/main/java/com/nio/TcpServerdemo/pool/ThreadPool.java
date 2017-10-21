package com.nio.TcpServerdemo.pool;


import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    List idle=new LinkedList<>();
   public ThreadPool(int poolSize) {
       //填充线程池
       for (int i = 0; i < poolSize; i++) {
           WorkerThread thread = new WorkerThread(this);
           //设置线程名
           thread.setName("worker" + (i + 1));
           thread.start();
           idle.add(thread);

       }
   }
         public WorkerThread  getWorker(){
                WorkerThread worker=null;
                synchronized (idle){
                    if (idle.size()>0){
                        worker=(WorkerThread)idle.remove(0);
                    }
                }
                return worker;
            }

            public  void returnWorker(WorkerThread worker){
                    synchronized (idle){
                        idle.add(worker);
                    }
            }




}
