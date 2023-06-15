package com.meConcurrent.jdk8.future;

import com.util.MyDateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

/**
 * CompletableFuture的一些零碎方法测试，
 * <p>
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

    /**
     * 杂七杂八小方法测试
     */
    public static void test() {

    }

    /**
     * cf Compose 测试
     */
    public static void composeTest() throws ExecutionException, InterruptedException {

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 * 2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "cf2";
        });
        cf2.get();
        cf2.thenApply(t -> {
            log.info("cf2.thenApply1:{}", t);
            return "哈哈哈1";
        }).thenApplyAsync(t -> {
            log.info("cf2.thenApply2:{}", t);
            return "哈哈哈2";
        }).thenCompose(t -> {
            log.info("cf2.thenCompose:{}", t);
            return CompletableFuture.supplyAsync(() -> {
                log.info("异步执行supplyAsync1");
                return "哈哈哈3";
            });
        }).thenApply(t -> {
            log.info("cf2.thenApply3:{}", t);
            return null;
        });
    }

    /**
     * combineTest 测试
     */
    public static void combineTest() throws ExecutionException, InterruptedException {
        long stime=System.currentTimeMillis();
        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int r=2;
            log.info("cf1执行完毕，结果:{}",r);
            return r;
        });

        CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int r=3;
            log.info("cf2执行完毕，结果:{}",r);
            return r;
        });

        CompletableFuture<Integer> cf3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int r=4;
            log.info("cf3执行完毕，结果:{}",r);
            return r;
        });

        try {
            MultiplyBiFunction mf=new MultiplyBiFunction();
            cf1.thenCombineAsync(cf2,mf).thenCombineAsync(cf3,mf).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

       log.info(MyDateUtil.execTime("总执行完毕：",stime));

    }

    /**
     * 相乘类,只有一个类使用直接拉姆达即可，不用使用此类
     */
   static class  MultiplyBiFunction implements BiFunction<Integer,Integer,Integer>{
        @Override
        public Integer apply(Integer e1, Integer e2) {
           Integer r=e1*e2;
           log.info("e1*e2:{}",r);
           return r;
        }
    }
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        composeTest();
        combineTest();
    }
}
