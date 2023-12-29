package com.stress;

import com.util.MyDateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 压力测试日志写入
 */
@Slf4j
public class StressLog {
    public static void main(String[] args) {
        //一个线程就可以，因为写日志时同步写的不是异步的
        for (int t = 0; t <1 ; t++) {
            new Thread(()->{
                try {
                    for (int j = 0; j <100 ; j++) {
                        Thread.sleep(1000*3);
                        long start=System.currentTimeMillis();
                        int size=1000000;
                        for (int i = 0; i <size ; i++) {
                            log.info("完成写入:"+i);
                        }
                        log.info(MyDateUtil.execTime("执行"+size+"次",start));
                    }


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }


    }
}
