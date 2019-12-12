package com.meConcurrent.pool;

import java.util.concurrent.*;

//这个测试引用《java高并发程序设计》中关于114页 中线程池吃掉异常
public class PoolExcept {

}

class DivTask implements Runnable{
    int a,b;
    public DivTask(int a, int b){
        this.a=a;
        this.b=b;
    }
    @Override
    public void run() {
        double re=a/b;
        System.out.println(re);
        if (b==3){
            System.out.println("异常丢了");
            throw  new RuntimeException("异常丢了");  //如果直接pools.submit真的不打印异常牛逼啊,换成pools.execute或者future等待pools的submit结果
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor pools=new ThreadPoolExecutor(0,Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        for (int i=0;i<5;i++){
            //pools.execute(new DivTask(100,i));
            Future future=pools.submit(new DivTask(100,i));
            future.get();
        }
    }
}