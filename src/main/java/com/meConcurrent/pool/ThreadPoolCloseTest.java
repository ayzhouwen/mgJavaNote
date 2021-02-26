package com.meConcurrent.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 线程池关闭测试
 * 使用shutdownNow⽅法，可能会引起报错，使用shutdown方法可能会导致线程关闭不了。
 * 使用shutdownNow⽅法关闭线程池时，一定要对任务里进行异常捕获。
 *使用shuwdown方法关闭线程池时，一定要确保任务里不会有永久阻塞等待的逻辑，否则线程池就关闭不了。
 *一定要记得shutdownNow和shuwdown调用完，线程池并不是立刻就关闭了，要想等待线程池关闭，还需调用awaitTermination方法来阻塞等待。
 */
@Slf4j
public class ThreadPoolCloseTest {
   static ThreadPoolExecutor pool=new ThreadPoolExecutor(10,10,0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());
    public static void close(){



        for (int j = 0; j <1000 ; j++) {
            pool.execute(()->{
                for (int i = 0; i <20 ; i++) {
                    log.info(i+"");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }



    }

    public static void main(String[] args) {
        close();
        try {
            Thread.sleep(1000*30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("准备线程池关闭");
//        pool.shutdown();
        pool.shutdownNow();
        log.info("完成线程池关闭");
        try {
            Thread.sleep(1000*60*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
