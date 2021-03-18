package com.meConcurrent.lock;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 字符串作为锁测试
 *
 */

@Slf4j
public class StringLockTest {

    private ExecutorService pool = Executors.newFixedThreadPool(16);
    //累计和
    private static Integer sum =0;
    //并发计算次数
    private static  int sumCount=50000;
    private static CountDownLatch countDownLatch=new CountDownLatch(sumCount);

    public void  test1(){


        for (int i = 0; i <sumCount ; i++) {
            Req req=new Req(new String("abc"));
            pool.execute(new MyRunable(req));
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("sum 最终合计值:"+sum);
        pool.shutdownNow();


    }
    @Data
    class  Req {
        private String name;
        public Req(String name){
            this.name=name;
        }
    }

    class MyRunable implements Runnable{
        private Req req;
        public MyRunable(Req req){
            this.req=req;
        }

        @Override
        public void run() {
            //字符串作为锁key时,一定要加上intern,即使name字段是new 出来的,调用此方法后,会从常量池里取,从而保证线程安全
            synchronized (req.name.intern()){
                sum++;
                log.info(sum +"");
                countDownLatch.countDown();
            }
        }
    }

    public static void main(String[] args) {
        StringLockTest test=new  StringLockTest();
        test.test1();
    }
}
