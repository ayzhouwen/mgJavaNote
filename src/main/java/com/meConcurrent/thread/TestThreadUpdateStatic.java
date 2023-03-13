package com.meConcurrent.thread;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试多线程更新静态变量,看看是否崩溃,所以要根据场景是否要加锁来解决并发安全问题,绝大部分业务场景都是需要加锁的
 * 结论:这种情况不会崩溃,currNode被更新了10次以后,10000个线程开始使用线程10最后一次更新的变量，详细变化看日志输出
 *
 * 日志最前几行
 * 2023-03-13 14:32:27.518 [Thread-3] INFO  com.meConcurrent.thread.UpdateVarRunable -已成功设置节点信息,线程id:192.168.1.3,内存地址:1807785935
 * 2023-03-13 14:32:27.518 [Thread-7] INFO  com.meConcurrent.thread.UpdateVarRunable -已成功设置节点信息,线程id:192.168.1.7,内存地址:467358906
 * 2023-03-13 14:32:27.518 [Thread-6] INFO  com.meConcurrent.thread.UpdateVarRunable -已成功设置节点信息,线程id:192.168.1.6,内存地址:2011450119
 * 2023-03-13 14:32:27.518 [Thread-5] INFO  com.meConcurrent.thread.UpdateVarRunable -已成功设置节点信息,线程id:192.168.1.5,内存地址:945257378
 * 2023-03-13 14:32:27.518 [Thread-10] INFO  com.meConcurrent.thread.UpdateVarRunable -已成功设置节点信息,线程id:192.168.1.10,内存地址:1556281520
 * 2023-03-13 14:32:27.518 [Thread-2] INFO  com.meConcurrent.thread.UpdateVarRunable -已成功设置节点信息,线程id:192.168.1.2,内存地址:769317857
 * 2023-03-13 14:32:27.519 [Thread-1] INFO  com.meConcurrent.thread.UpdateVarRunable -已成功设置节点信息,线程id:192.168.1.1,内存地址:1307788455
 * 2023-03-13 14:32:27.518 [Thread-9] INFO  com.meConcurrent.thread.UpdateVarRunable -已成功设置节点信息,线程id:192.168.1.9,内存地址:1317440058
 * 2023-03-13 14:32:27.519 [Thread-8] INFO  com.meConcurrent.thread.UpdateVarRunable -已成功设置节点信息,线程id:192.168.1.8,内存地址:1817097730
 * 2023-03-13 14:32:27.518 [Thread-0] INFO  com.meConcurrent.thread.UpdateVarRunable -已成功设置节点信息,线程id:192.168.1.0,内存地址:749491723
 * 2023-03-13 14:32:27.518 [Thread-4] INFO  com.meConcurrent.thread.UpdateVarRunable -已成功设置节点信息,线程id:192.168.1.4,内存地址:807156635
 *
 *
 * 日志最后几行
 * 2023-03-13 14:32:33.023 [Thread-9242] INFO  com.meConcurrent.thread.UpdateVarRunable -当前节点信息:{"ip":"192.168.1.10","port":3306},内存地址:1556281520
 * 2023-03-13 14:32:33.023 [Thread-7183] INFO  com.meConcurrent.thread.UpdateVarRunable -当前节点信息:{"ip":"192.168.1.10","port":3306},内存地址:1556281520
 * 2023-03-13 14:32:33.023 [Thread-5922] INFO  com.meConcurrent.thread.UpdateVarRunable -当前节点信息:{"ip":"192.168.1.10","port":3306},内存地址:1556281520
 * 2023-03-13 14:32:33.023 [Thread-387] INFO  com.meConcurrent.thread.UpdateVarRunable -当前节点信息:{"ip":"192.168.1.10","port":3306},内存地址:1556281520
 * 2023-03-13 14:32:33.023 [Thread-9770] INFO  com.meConcurrent.thread.UpdateVarRunable -当前节点信息:{"ip":"192.168.1.10","port":3306},内存地址:1556281520
 * 2023-03-13 14:32:33.023 [Thread-5998] INFO  com.meConcurrent.thread.UpdateVarRunable -当前节点信息:{"ip":"192.168.1.10","port":3306},内存地址:1556281520
 * 2023-03-13 14:32:33.023 [Thread-7594] INFO  com.meConcurrent.thread.UpdateVarRunable -当前节点信息:{"ip":"192.168.1.10","port":3306},内存地址:1556281520
 * 2023-03-13 14:32:33.023 [Thread-5599] INFO  com.meConcurrent.thread.UpdateVarRunable -当前节点信息:{"ip":"192.168.1.10","port":3306},内存地址:1556281520
 * 2023-03-13 14:32:33.023 [Thread-7885] INFO  com.meConcurrent.thread.UpdateVarRunable -当前节点信息:{"ip":"192.168.1.10","port":3306},内存地址:1556281520
 * 2023-03-13 14:32:33.023 [Thread-4991] INFO  com.meConcurrent.thread.UpdateVarRunable -当前节点信息:{"ip":"192.168.1.10","port":3306},内存地址:1556281520
 * 2023-03-13 14:32:33.023 [Thread-9456] INFO  com.meConcurrent.thread.UpdateVarRunable -当前节点信息:{"ip":"192.168.1.10","port":3306},内存地址:1556281520
 * 2023-03-13 14:32:33.023 [Thread-5087] INFO  com.meConcurrent.thread.UpdateVarRunable -当前节点信息:{"ip":"192.168.1.10","port":3306},内存地址:1556281520
 */
public class TestThreadUpdateStatic {
    /**
     * 当前节点
     */
    public static SysNode currNode;

    /**
     * 多线程更新静态变量
     */
    static  void    updateStaicVar(){
        for (int i = 0; i <10000 ; i++) {
            UpdateVarRunable updateVarRunable=new UpdateVarRunable("192.168.1."+i);
            Thread t=new Thread(updateVarRunable);
            t.start();
        }
    }

    public static void main(String[] args) {
        updateStaicVar();
    }

}
@Slf4j
class UpdateVarRunable implements Runnable {
    /**
     * 线程id
     */
    private String id;
    public  UpdateVarRunable(String id){
        this.id=id;
    }
    @Override
    public void run() {
        if (TestThreadUpdateStatic.currNode==null){
            SysNode node=new SysNode();
            node.setIp(this.id);
            node.setPort(3306);
            TestThreadUpdateStatic.currNode=node;
            log.info("已成功设置节点信息,线程id:{},内存地址:{}",id,System.identityHashCode(node));
        }

        for (int i = 0; i <100000 ; i++) {
            log.info("当前节点信息:{},内存地址:{}",JSONUtil.toJsonStr(TestThreadUpdateStatic.currNode),System.identityHashCode(TestThreadUpdateStatic.currNode));
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
