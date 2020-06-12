package com.meConcurrent.future;

import com.util.MyDateUtil;

import java.util.concurrent.CompletableFuture;

//CompletableFuture 的 20 个例子
//https://zhuanlan.zhihu.com/p/34921166
public class CompletableFutureDemo {

    static void sleep(long ms) {
        //随机睡5秒内
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void completedFutureExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("message2");
        System.out.println(cf.isDone());
        System.out.println("message".equals(cf.getNow(null)));
    }

    static void runAsyncExample() {
        CompletableFuture cf = CompletableFuture.runAsync(() -> {
            System.out.println("是否为守护进程" + Thread.currentThread().isDaemon());
            sleep(2000);
        });
        System.out.println("任务是否完成:" + cf.isDone());
        sleep(5000);
        System.out.println("任务是否完成:" + cf.isDone());

    }

    //thenApplyAsync:异步 执行时间是最长任务,thenApply同步,顺序执行,执行时间是每个任务的累加时间
    static void thenApplyAsyncExample() {
        long stime = System.currentTimeMillis();
        CompletableFuture cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            System.out.println("cf1是否为守护进程" + Thread.currentThread().isDaemon());
            sleep(5000);
            return s.toUpperCase();
        });

        CompletableFuture cf2 = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            System.out.println("cf2是否为守护进程" + Thread.currentThread().isDaemon());
            sleep(3000);
            return s.toUpperCase();
        });
        System.out.println("cf1结果:" + cf.getNow(null));
        System.out.println("cf1.join():" + cf.join());

        System.out.println("cf2结果:" + cf2.getNow(null));
        System.out.println("cf2.join():" + cf2.join());

        System.out.println(MyDateUtil.execTime("thenApplyAsyncExample 耗时", stime));

    }

    public static void main(String[] args) {
        // completedFutureExample();
        // runAsyncExample();
        thenApplyAsyncExample();
    }
}
