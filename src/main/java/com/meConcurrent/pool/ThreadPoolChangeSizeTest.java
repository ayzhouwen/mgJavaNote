package com.meConcurrent.pool;

import cn.hutool.core.date.DateUtil;
import com.util.MyMonitorUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池扩容与缩容测试
 */
@Slf4j
public class ThreadPoolChangeSizeTest {
    /**
     * 模拟的线程池数量由10到100,即使将setCorePoolSize 设置为1000,但是必须重新提交任务时
     * 才会,创建新的线程,所以一定要使用有界队列和最大线程数一定要大于核心线程数
     */
    public static void expand(){
        //
        ThreadPoolExecutor pools=new ThreadPoolExecutor(10,100,
                0L, TimeUnit.SECONDS,new LinkedBlockingDeque<>(200));
        for (int i = 0; i <1000 ; i++) {
            try {
                pools.execute(()->{
                  while (true){
                   log.info(DateUtil.now());
                      try {
                          Thread.sleep(1000L);
                      } catch (Exception e) {
                          throw new RuntimeException(e);
                      }
                  }
                });
            } catch (Exception e) {
                log.error("处理任务异常:",e);
            }
        }
       MyMonitorUtil.getThreadPoolMonitor("测试",pools);
        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //增大核心数,线程池提交新任务时,会创建
        pools.setCorePoolSize(1000);

        pools.allowCoreThreadTimeOut(true);

        MyMonitorUtil.getThreadPoolMonitor("扩容后",pools);

    }

    public static void main(String[] args) {
        expand();
    }
}
