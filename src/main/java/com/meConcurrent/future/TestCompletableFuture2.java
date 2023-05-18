package com.meConcurrent.future;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture的一些零碎方法测试，
 *
 * 1.future只有计算过了值，对应的回调方法才会执行，计算值有两种方法可以计算
     * 1）直接调用 CompletableFuture.completedFuture(值);调用后以后绑定的回调函数都会直接执行，相当于串行模型
     * 2）先new一个CompletableFuture 然后在某处调用 future.complete方法，调用后future会执行所有之前已经绑定的回调函数，以后再次绑定回调函数，会直接执行
     * 2.future带方法名Async是异步执行，会使用ForkJoinPool.commonPool线程池或者指定线程池去运行回调函数,否则是同步执行，用当前线程同步调用回调函数
 * 3.如果执行了future.completeExceptionally(new RuntimeException("错误了"));
 * 那么会有下面注意点
     * 1) future.thenApplyAsync不会执行
     * 2) future.get() 会有异常抛出,看场景是否需要进行try catch
     * 3)future.whenComplete 方法不受影响 ,方法内甚至可以输出异常
 * 4.future.thenCompose 这个方法参数是拉姆达,结果是返回一个新的thenCompose
 * 5.future.complete 方法第一次调用后,他会返回true,再次调用就会返回false
 * 6.CompletableFuture<Void> 代表无返回值，只做处理,比如future.thenAccept 可以无限future.thenAccept 链式调用，但是都不能有返回值
 */
@Slf4j
public class TestCompletableFuture2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Map map=new HashMap(){{put("a",1);put("b",2);}};
        //第一种回调执行方法：
        CompletableFuture<Map<String, String>> replicationFuture = CompletableFuture.completedFuture(map);
        //第二种回调执行方法
//        CompletableFuture<Map<String, String>> replicationFuture = new CompletableFuture<>();
        // replicationFuture.complete(map);
        replicationFuture.thenApply(t->{
            log.info("thenApply1:"+t);
            return null;
        });
        replicationFuture.handle((t,u)->{
            log.info("handle1:"+t);
            return null;
        });
        replicationFuture.thenAccept(r->{
            log.info("thenAccept:"+r);
        });
        replicationFuture.thenAcceptAsync(r->{
            log.info("异步线程执行thenAcceptAsync1:"+r);
        });
        replicationFuture.thenAcceptAsync(r->{
            log.info("异步线程执行thenAcceptAsync2:"+r);
        });
    }
}
