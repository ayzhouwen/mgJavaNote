package com.meConcurrent;

//测试线程池满时,拒绝策略

//参考:https://www.jianshu.com/p/aa420c7df275

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
// ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
// ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
// ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
public class TestRejectPolicy {
    class MyRunable implements Runnable{

        public  MyRunable(int index){
            this.index=index;
        }
        private int index;
        @Override
        public void run() {
            System.out.println("当先线程编号:"+index+",线程名:"+Thread.currentThread().getName());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    void test1(){
        int THREADS_SIZE=1;
        int CAPACITY=1;
        //创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool=new ThreadPoolExecutor(THREADS_SIZE,THREADS_SIZE,0,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(CAPACITY));
        // 设置线程池的拒绝策略为"丢弃"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        //新建10个任务,并将它们添加到线程池中


        for (int i=0;i<10;i++){
            MyRunable mr=new MyRunable(i);
            pool.execute(mr);
        }
    }


    void test2(){
        int THREADS_SIZE=1;
        int CAPACITY=1;
        //创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool=new ThreadPoolExecutor(THREADS_SIZE,THREADS_SIZE,0,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(CAPACITY));
        // 设置线程池的拒绝策略为"丢弃"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        //新建10个任务,并将它们添加到线程池中


        for (int i=0;i<10;i++){
            MyRunable mr=new MyRunable(i);
            pool.execute(mr);
        }
    }



    void test3(){
        int THREADS_SIZE=1;
        int CAPACITY=1;
        //创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool=new ThreadPoolExecutor(THREADS_SIZE,THREADS_SIZE,0,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(CAPACITY));
        // 设置线程池的拒绝策略为"丢弃"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        //新建10个任务,并将它们添加到线程池中


        for (int i=0;i<10;i++){
            MyRunable mr=new MyRunable(i);
            pool.execute(mr);
        }
    }


        //这种线程池满了的话,那么由调用线程池的线程执行
    void test4(){
        int THREADS_SIZE=1;
        int CAPACITY=1;
        //创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool=new ThreadPoolExecutor(THREADS_SIZE,THREADS_SIZE,0,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(CAPACITY));
        // 设置线程池的拒绝策略为"丢弃"
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        //新建10个任务,并将它们添加到线程池中


        for (int i=0;i<10;i++){
            MyRunable mr=new MyRunable(i);
            pool.execute(mr);
        }
    }




    public static void main(String[] args) {
        TestRejectPolicy testRejectPolicy=new TestRejectPolicy();
        testRejectPolicy.test4();
    }
}
