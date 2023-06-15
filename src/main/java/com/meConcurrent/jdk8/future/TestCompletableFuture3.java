package com.meConcurrent.jdk8.future;

import com.util.MyDateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * CompletableFuture的并发性测试,默认的ForkJoinPool线程池，只有cpu*2的线程，没有空闲线程会丢队列里
 * 
 * 总结：CompletableFuture默认线程池ForkJoinPool 在计算任务很多情况下（比如大于1万），会优于ThreadPoolExecutor
 */

@Slf4j
public class TestCompletableFuture3 {
    /**
     * 计算次数
     */
    public static int size = 1;
    /**
     * 自定义线程池个数
     */
    static int threadNum=8;
    static Executor executor = Executors.newFixedThreadPool(threadNum);
    static Map map = new HashMap() {{
        put("a", 1);
        put("b", 2);
    }};

    /**
     * 默认线程池阻塞测试
     * @throws InterruptedException
     */
    static void defaultBlockPoolTest() throws InterruptedException {
        {
            CompletableFuture<Map<String, String>> cf = CompletableFuture.completedFuture(map);
            CountDownLatch countDownLatch = new CountDownLatch(size);
            long stime=System.currentTimeMillis();
            for (int i = 0; i < size; i++) {
                int finalI = i;
                cf.thenApplyAsync(t -> {
//                    log.info("thenApplyAsync{}准备阻塞", finalI);
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        countDownLatch.countDown();
                    }
                    return null;
                });

            }
            countDownLatch.await();
            log.info(MyDateUtil.execTime("默认线程池阻塞计算"+size+"次",stime));

        }
    }

    /**
     * 默认线程池非阻塞测试
     * @throws InterruptedException
     */
    static void defaultNoBlockPoolTest() throws InterruptedException {
        {
            CompletableFuture<Map<String, String>> cf = CompletableFuture.completedFuture(map);
            CountDownLatch countDownLatch = new CountDownLatch(size);
            long stime=System.currentTimeMillis();
            for (int i = 0; i < size; i++) {
                int finalI = i;
                cf.thenApplyAsync(t -> {
//                    log.info("thenApplyAsync{}准备阻塞", finalI);
                    countDownLatch.countDown();
                    return null;
                });

            }
            countDownLatch.await();
            log.info(MyDateUtil.execTime("默认线程池非阻塞计算"+size+"次",stime));

        }
    }

    /**
     * 指定线程池阻塞测试
     */
    static void customBlockPoolTest() throws InterruptedException {
        {
            CompletableFuture<Map<String, String>> cf = CompletableFuture.completedFuture(map);
            CountDownLatch countDownLatch = new CountDownLatch(size);
            long stime=System.currentTimeMillis();
            for (int i = 0; i < size; i++) {
                int finalI = i;
                cf.thenApplyAsync(t -> {
//                    log.info("thenApplyAsync{}准备阻塞", finalI);
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        countDownLatch.countDown();
                    }
                    return null;
                }, executor);

            }
            countDownLatch.await();
            log.info(MyDateUtil.execTime("自定义线程池阻塞计算"+size+"次",stime));
        }
    }

    /**
     * 指定线程池非阻塞测试
     */
    static void customNoBlockPoolTest() throws InterruptedException {
        {
            CompletableFuture<Map<String, String>> cf = CompletableFuture.completedFuture(map);
            CountDownLatch countDownLatch = new CountDownLatch(size);
            long stime=System.currentTimeMillis();
            for (int i = 0; i < size; i++) {
                int finalI = i;
                cf.thenApplyAsync(t -> {
//                    log.info("thenApplyAsync{}准备阻塞", finalI);
                    countDownLatch.countDown();
                    return null;
                }, executor);
            }
            countDownLatch.await();
            log.info(MyDateUtil.execTime("自定义线程池阻塞计算"+size+"次",stime));
        }
    }
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int num=8;
        for (int i = 0; i <num ; i++) {
            defaultBlockPoolTest();
            customBlockPoolTest();
            log.info("==============================================================");
            size*=10;
        }
        size=1;
        log.info("*****************************************************************");
        for (int i = 0; i <num ; i++) {
            defaultNoBlockPoolTest();
            customNoBlockPoolTest();
            log.info("==============================================================");
            size*=10;
        }
        //ForkJoinPool会自己释放线程，ThreadPoolExecutor必须关闭池后，才能释放线程
        ((ThreadPoolExecutor)executor).shutdown();
//        Thread.sleep(1000*120);
    }
}

//详细测试报告
//2023-05-26 10:59:40 [main]-> 默认线程池阻塞计算1次:58.0毫秒,0.06秒,0分钟
//2023-05-26 10:59:40 [main]-> 自定义线程池阻塞计算1次:1.0毫秒,0秒,0分钟
//2023-05-26 10:59:40 [main]-> ==============================================================
//2023-05-26 10:59:40 [main]-> 默认线程池阻塞计算10次:1.0毫秒,0秒,0分钟
//2023-05-26 10:59:40 [main]-> 自定义线程池阻塞计算10次:1.0毫秒,0秒,0分钟
//2023-05-26 10:59:40 [main]-> ==============================================================
//2023-05-26 10:59:40 [main]-> 默认线程池阻塞计算100次:0.0毫秒,0秒,0分钟
//2023-05-26 10:59:40 [main]-> 自定义线程池阻塞计算100次:1.0毫秒,0秒,0分钟
//2023-05-26 10:59:40 [main]-> ==============================================================
//2023-05-26 10:59:40 [main]-> 默认线程池阻塞计算1000次:6.0毫秒,0.01秒,0分钟
//2023-05-26 10:59:40 [main]-> 自定义线程池阻塞计算1000次:5.0毫秒,0.01秒,0分钟
//2023-05-26 10:59:40 [main]-> ==============================================================
//2023-05-26 10:59:40 [main]-> 默认线程池阻塞计算10000次:11.0毫秒,0.01秒,0分钟
//2023-05-26 10:59:40 [main]-> 自定义线程池阻塞计算10000次:17.0毫秒,0.02秒,0分钟
//2023-05-26 10:59:40 [main]-> ==============================================================
//2023-05-26 10:59:40 [main]-> 默认线程池阻塞计算100000次:76.0毫秒,0.08秒,0分钟
//2023-05-26 10:59:40 [main]-> 自定义线程池阻塞计算100000次:83.0毫秒,0.08秒,0分钟
//2023-05-26 10:59:40 [main]-> ==============================================================
//2023-05-26 10:59:40 [main]-> 默认线程池阻塞计算1000000次:322.0毫秒,0.32秒,0.01分钟
//2023-05-26 10:59:41 [main]-> 自定义线程池阻塞计算1000000次:744.0毫秒,0.74秒,0.01分钟
//2023-05-26 10:59:41 [main]-> ==============================================================
//2023-05-26 10:59:45 [main]-> 默认线程池阻塞计算10000000次:4421.0毫秒,4.42秒,0.07分钟
//2023-05-26 10:59:57 [main]-> 自定义线程池阻塞计算10000000次:11413.0毫秒,11.41秒,0.19分钟
//2023-05-26 10:59:57 [main]-> ==============================================================
//2023-05-26 10:59:57 [main]-> *****************************************************************
//2023-05-26 10:59:57 [main]-> 默认线程池非阻塞计算1次:0.0毫秒,0秒,0分钟
//2023-05-26 10:59:57 [main]-> 自定义线程池阻塞计算1次:0.0毫秒,0秒,0分钟
//2023-05-26 10:59:57 [main]-> ==============================================================
//2023-05-26 10:59:57 [main]-> 默认线程池非阻塞计算10次:0.0毫秒,0秒,0分钟
//2023-05-26 10:59:57 [main]-> 自定义线程池阻塞计算10次:0.0毫秒,0秒,0分钟
//2023-05-26 10:59:57 [main]-> ==============================================================
//2023-05-26 10:59:57 [main]-> 默认线程池非阻塞计算100次:0.0毫秒,0秒,0分钟
//2023-05-26 10:59:57 [main]-> 自定义线程池阻塞计算100次:1.0毫秒,0秒,0分钟
//2023-05-26 10:59:57 [main]-> ==============================================================
//2023-05-26 10:59:57 [main]-> 默认线程池非阻塞计算1000次:1.0毫秒,0秒,0分钟
//2023-05-26 10:59:57 [main]-> 自定义线程池阻塞计算1000次:0.0毫秒,0秒,0分钟
//2023-05-26 10:59:57 [main]-> ==============================================================
//2023-05-26 10:59:57 [main]-> 默认线程池非阻塞计算10000次:7.0毫秒,0.01秒,0分钟
//2023-05-26 10:59:57 [main]-> 自定义线程池阻塞计算10000次:3.0毫秒,0秒,0分钟
//2023-05-26 10:59:57 [main]-> ==============================================================
//2023-05-26 10:59:57 [main]-> 默认线程池非阻塞计算100000次:16.0毫秒,0.02秒,0分钟
//2023-05-26 10:59:57 [main]-> 自定义线程池阻塞计算100000次:19.0毫秒,0.02秒,0分钟
//2023-05-26 10:59:57 [main]-> ==============================================================
//2023-05-26 10:59:57 [main]-> 默认线程池非阻塞计算1000000次:136.0毫秒,0.14秒,0分钟
//2023-05-26 10:59:57 [main]-> 自定义线程池阻塞计算1000000次:163.0毫秒,0.16秒,0分钟
//2023-05-26 10:59:57 [main]-> ==============================================================
//2023-05-26 10:59:59 [main]-> 默认线程池非阻塞计算10000000次:1603.0毫秒,1.6秒,0.03分钟
//2023-05-26 11:00:01 [main]-> 自定义线程池阻塞计算10000000次:1932.0毫秒,1.93秒,0.03分钟
//2023-05-26 11:00:01 [main]-> ==============================================================
 