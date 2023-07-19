package com.meConcurrent.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 主要测试大量线程切换场景,代码由chatgpt生成
 * Linux 下 pidstat -w -p 进程PID 1 10 执行这条命令 cswch/s 都是0，可能是切换时间太短，软件无法跟踪，加上-t 参数可以看到线程切换
 * Linux 下 vmstat 字段 中r（运行队列中的进程数量）字段和cs（线程切换数量）字段明显偏大
 * linux 下 top 命令 平均负载很高,us和sy很高
 *
 */
public class ThreadSwitchSimulation implements Runnable {
    private volatile boolean running = true;

    public void run() {
        while (running) {
            // Perform some computation or task
            // ...

            // Yield the current thread to simulate thread switching
            Thread.yield();
        }
    }

    public void stop() {
        running = false;
    }

    public static void main(String[] args) {
        int numThreads = 100; // Number of threads to simulate
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            ThreadSwitchSimulation simulation = new ThreadSwitchSimulation();
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }

        // Simulate for a certain duration
        try {
            Thread.sleep(50000); // Simulate for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
