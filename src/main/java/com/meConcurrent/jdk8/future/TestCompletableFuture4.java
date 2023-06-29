package com.meConcurrent.jdk8.future;

import cn.hutool.core.util.RandomUtil;
import com.util.MyDateUtil;
import com.util.MyThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.BiConsumer;

/**
 * 主要针对 CompletableFuture的异常测试
 * 总结：
 * （1） Future.get 方法会返回异常 ，Future.join 方法不返回异常(注意，如果业务不try catch 异常，
 * 那么最终执行逻辑还是会抛出异常 )，所以业务一定要 try catch try catch，以为这是JDK并发的通病（异常了会吞异常，或者直接终止所有流程） ！！！！！
 * （2） 尽量不要再循环内调用get,很容易造成串行，8种case（case2_1,case6_1不算）中只有 case8 自定义线程池可以get 并行，其他都会串行
 * （3） 标准写法是case6_1 和 case2_1 循环外用 get join 方法，不推荐case8。
 * （4） 在标准写法下case6_1 和 case2_1 ，主线程阻塞，默认线程池或者业务线程池进行逻辑处理，效果如普通线程池+countDownLatch，如果主线程不需要阻塞，可以不get或者join
 * （5）生产尽量自定义线程池，try finally中关闭线程池
 *
 */

@Slf4j
public class TestCompletableFuture4 {
    private  static ThreadPoolExecutor poolExecutor= new ThreadPoolExecutor(8, 128, 0,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
            new MyThreadFactory("zwtest呵呵哒"));
    public static void case1(){
        for (int i = 0; i <10000 ; i++) {

            int finalI = i;
            CompletableFuture.runAsync(()->{
                if (finalI ==5000){
                    throw new RuntimeException("处理异常1");
                }
                log.info("第"+ finalI +"次执行");
            });
        }
        log.info("我在这里1");
    }


    public static void case2(){
        for (int i = 0; i <10000 ; i++) {

            int finalI = i;

            try {
                CompletableFuture.runAsync(()->{
                    if (finalI ==5000){
                        throw new RuntimeException("处理异常2");
                    }
                    log.info("第"+ finalI +"次执行");
                }).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

        }
        log.info("我在这里2");
    }

    /**
     * 标准做法, 主线程阻塞，线程池异步执行业务，生产环境业务里要加try catch
     */
    public static void case2_1(){
        List<CompletableFuture<Void>> futureList=new ArrayList<>();
        for (int i = 0; i <10000 ; i++) {

            int finalI = i;
            futureList.add(CompletableFuture.runAsync(()->{
                    if (finalI ==5000){
                        throw new RuntimeException("处理异常2-1");
                    }
                    log.info("第"+ finalI +"次执行");
                }));
        }
        futureList.forEach(e->{
            e.join();
        });
        log.info("我在这里2-1");
    }



    public static void case3(){
        for (int i = 0; i <10000 ; i++) {
            int finalI = i;
                CompletableFuture.runAsync(()->{
                    if (finalI ==5000){
                        throw new RuntimeException("处理异常3");
                    }
                    log.info("第"+ finalI +"次执行");
                },poolExecutor);

        }
        log.info("我在这里3");
    }


    public static void case4(){
        for (int i = 0; i <10000 ; i++) {
            int finalI = i;
            try {
                CompletableFuture.runAsync(()->{
                    if (finalI ==5000){
                        throw new RuntimeException("处理异常4");
                    }
                    log.info("第"+ finalI +"次执行");
                },poolExecutor).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

        }
        log.info("我在这里4");
    }


    public static void case5(){
        for (int i = 0; i <10000 ; i++) {

            int finalI = i;
            CompletableFuture.supplyAsync(()->{
                if (finalI ==5000){
                    throw new RuntimeException("处理异常5");
                }
                log.info("第"+ finalI +"次执行");
                return null;
            });
        }
        log.info("我在这里5");
    }


    public static void case6(){
        for (int i = 0; i <10000 ; i++) {
            int finalI = i;
            try {
                CompletableFuture.supplyAsync(()->{
                    if (finalI ==5000){
                        throw new RuntimeException("处理异常6");
                    }
                    log.info("第"+ finalI +"次执行");
                    return null;
                }).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("我在这里6");
    }

    /**
     * 标准做法, 主线程阻塞，线程池异步执行业务，生产环境业务里要加try catch
     */
    public static void case6_1(){
        log.info("FK默认线程池数量"+System.getProperty( "java.util.concurrent.ForkJoinPool.common.parallelism"));
        List<CompletableFuture<String>> futureList=new ArrayList<>();
        for (int i = 0; i <100000 ; i++) {
            int finalI = i;
           futureList.add(CompletableFuture.supplyAsync(()->{
                    if (finalI ==5000){
                        throw new RuntimeException("处理异常6-1");
                    }
                    log.info("第"+ finalI +"次执行");
//               try {
//                   Thread.sleep(10);
//               } catch (InterruptedException e) {
//                   throw new RuntimeException(e);
//               }
               return finalI+"";
                }));
        }
        futureList.forEach(e->{
           e.join();
        });
        log.info("我在这里6-1");
    }

    public static void case7(){
        for (int i = 0; i <10000 ; i++) {
            int finalI = i;
                CompletableFuture.supplyAsync(()->{
                    if (finalI ==5000){
                        throw new RuntimeException("处理异常7");
                    }
                    log.info("第"+ finalI +"次执行");
                    return null;
                },poolExecutor);
        }
        log.info("我在这里7");
    }

    public static void case8(){
        for (int i = 0; i <100000 ; i++) {
            int finalI = i;
            try {
                CompletableFuture.supplyAsync(()->{
                    if (finalI ==5000){
                        throw new RuntimeException("处理异常8");
                    }
                    log.info("第"+ finalI +"次执行");
                    return null;
                },poolExecutor).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("我在这里8");
    }
    public static void main(String[] args) {
        try {
            TestCompletableFuture4.case2_1();
        } finally {

        }


//        TestCompletableFuture4.case6_1();
    }
}

