package com.meConcurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * 来源：https://mp.weixin.qq.com/s/WTqdSz-lc5zzelJgk4Co8g
 * 程序运行后，threadB会一直卡住，最好的解决方案 initFlag 加上 volatile关键字，写入操作，所有线程可见
 * 其他的骚方法（不建议）：
 * while (!initFlag) {} 中添加sleep，锁,synchronized(this){}，io阻塞，能造成线程切换的函数或者关键字
 */
@Slf4j
public class VolatileTest {
    private     boolean initFlag = false;

    public static void main(String[] args) throws InterruptedException {
        VolatileTest sample = new VolatileTest();
        Thread threadA = new Thread(sample::refresh, "threadA");

        Thread threadB = new Thread(sample::load, "threadB");

        threadB.start();
        Thread.sleep(2000);
        threadA.start();
    }

    public void refresh() {
        this.initFlag = true;
        log.info("线程：" + Thread.currentThread().getName() + ":修改共享变量initFlag");
    }

    public   void load() {
        int i = 0;
        while (!initFlag) {
            synchronized(this){

            }
        }
        log.info("线程：" + Thread.currentThread().getName() + "当前线程嗅探到initFlag的状态的改变" + i);
    }
}
