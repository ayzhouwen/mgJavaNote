package com.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 返回监控信息
 */
@Slf4j
public class MyMonitorUtil {
    /**
     * 返回线程池监控信息
     * @param poolName 线程池名
     * @return
*/
    public static String getThreadPoolMonitor(String poolName,ThreadPoolExecutor tpl){
        StringBuilder sb=new StringBuilder();
        try {
            if (tpl!=null){

                sb.append("[线程池监控:").append(poolName).append("=>当前线程数:").append(tpl.getPoolSize())
                        .append(",核心线程数:").append(tpl.getCorePoolSize())
                        .append(",正在执行的任务数量:").append(tpl.getActiveCount())
                        .append(",已完成任务数量:").append(tpl.getCompletedTaskCount())
                        .append(",任务总数:").append(tpl.getTaskCount())
                        .append(",队列里缓存的任务数量:").append(tpl.getQueue().size())
                        .append(",池中存在的最大线程数:").append(tpl.getLargestPoolSize())
                        .append(",最大允许的线程数:").append(tpl.getMaximumPoolSize())
                        .append(",线程空闲时间:").append(tpl.getKeepAliveTime(TimeUnit.MILLISECONDS))
                        .append(",线程池是否关闭:").append(tpl.isShutdown())
                        .append(",线程池是否终止:").append(tpl.isTerminated()).append("]");
            }
        } catch (Exception e) {
            log.error("获取线程池监控异常:",e);
        }
        String logstr=sb.toString();
        log.info(logstr);
        return logstr;
    }

}
