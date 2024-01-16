package com.meConcurrent.jdk8.future;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 测试CompletableFuture在项目中的实际场景，主线程如何同步发送消息，那么等待TCP异步接收并获取结果，解除主线程的同步等待
 */

@Slf4j
public class TestCompletableFuture5 {
    private static final Map<String, CompletableFuture<IotMsgRep>> futureMap=new ConcurrentHashMap<>();
    public static void main(String[] args) throws ExecutionException {
        sendSyncMsg();
    }
    private static void  sendSyncMsg(){

        try {
            new Thread(()->{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                IotMsgRep  rep=new IotMsgRep();
                rep.setIotMsgId(1L);
                rep.setIotMsgCode(200);
                rep.setIotMsgBody("操作成功");
                futureMap.get("1").complete(rep);

            }).start();
            CompletableFuture<IotMsgRep> future= new CompletableFuture<>();
            //CompletableFuture<IotMsgRep> future= CompletableFuture.completedFuture(null);
            futureMap.put("1",future);
            IotMsgRep rep=future.get(5,TimeUnit.SECONDS);
            log.info("响应结果："+ JSON.toJSONString(rep));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


@Data
class IotMsgRep{
    /**
     * 消息自增Id
     */
    private Long iotMsgId;
    /**
     * 消息体
     */
    private Object iotMsgBody;
    /**
     * 命令发送结果
     */
    private Integer iotMsgCode;

    /**
     * 消息信息提示
     */
    private String iotMsgTip;
}

