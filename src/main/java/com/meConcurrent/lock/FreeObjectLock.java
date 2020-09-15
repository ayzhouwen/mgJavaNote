package com.meConcurrent.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * 问题:测试线程中断能否结束对象锁等待
 * 结果:外部线程中断某个线程会让他结束中断,程序机选往下执行
 */
@Slf4j
public class FreeObjectLock {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread thread = new Thread(() -> {
            synchronized (lock) {
                try {
                    log.info("等待锁释放");
                    lock.wait();
                } catch (InterruptedException e) {
                    log.error("中断异常", e);
                }

            }
            log.info("线程结束");
        });

        thread.start();

        Thread.sleep(5000);
        //正常发消息结束线程等待
//        synchronized (lock) {
//            lock.notifyAll();
//        }

        //线程中断,也可以让其结束等待
        thread.interrupt();
        Thread.sleep(3000);

    }
}
