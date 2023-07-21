package com.meConcurrent;

//测试线程池满时,拒绝策略

//参考:https://www.jianshu.com/p/aa420c7df275

import java.util.concurrent.*;

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

    /**
     * 测试由于决绝策略带来的线程阻塞问题，
     * 参考：https://mp.weixin.qq.com/s/C4SKZ_K5QsApdH9LooPffA
     * 这个代码在生产环境中需要几处修改
     * 1.不能使用CallerRunsPolicy ,因为极端情况可能会更糟糕，比如业务代码bug导致来一个线程阻塞一个
     * 2.无论什么阻塞get，都要加上超时，这是最后兜底的操作
     * 3.这种情况 线程池在 try finally{ pool.shutdownNow()} 比较好，能较好的关闭线程池
     */
    void test5(){
        // 一个核心线程，队列最大为1，最大线程数也是1.拒绝策略是DiscardPolicy
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.AbortPolicy());

        Future f1 = executorService.submit(()-> {
            System.out.println("提交任务1");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Future f2 = executorService.submit(()->{
            System.out.println("提交任务2");
        });

        Future f3 = executorService.submit(()->{
            System.out.println("提交任务3");
        });

        try {
            System.out.println("任务1完成 " + f1.get());// 等待任务1执行完毕
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("任务2完成" + f2.get());// 等待任务2执行完毕
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("任务3完成" + f3.get());// 等待任务3执行完毕
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        executorService.shutdown();// 关闭线程池，阻塞直到所有任务执行完毕
    }




    public static void main(String[] args) {
        TestRejectPolicy testRejectPolicy=new TestRejectPolicy();
        testRejectPolicy.test5();
    }
}
