package com.meConcurrent.volotile;

import lombok.extern.slf4j.Slf4j;

/**
 * 参考：static能保证可见性吗？
 * https://blog.csdn.net/qq_38167579/article/details/106803114
 * 总结:多线程场景下静态变量有逻辑计算必须 设置为volotile,
 * 实际测试情况:
 * 在MY_INT不使用volotile情况下,有几种办法来刷新工作内存变量(强烈还是推荐使用volotile)
 * 1.ChangeListener的run方法里循环中添加Thread.sleep(20),造成线程切换 让出cpu;
 * 2.ChangeListener的run方法里循环中添加 synchronized (this.getClass()){} 获得锁和释放锁 让出cpu,
 * 3.ChangeListener的run方法里循环中添加 Lock lock=new ReentrantLock(); lock.lock(); lock.unlock(); 获得锁和释放锁 让出cpu
 * 4.ChangeListener的run方法里循环中添加 File file = new File("F://text.txt"); io操作 让出cpu
 *
 *
 *
 */
@Slf4j
public class ThreadVolotileTest {
    // static 不能保证可见性,实际测试工作内存变量与同步
    private   static int MY_INT = 0;
    //
//    private volatile  static int MY_INT = 0;

    static void disable() {
        new ThreadVolotileTest.ChangeListener().start();
        new ThreadVolotileTest.ChangeMaker().start();
    }

    static class ChangeListener extends Thread {
        @Override
        public void run() {
            int local_value = MY_INT;
            while (local_value < 10000){
                if(local_value != MY_INT){
                   log.info("Got Change for MY_INT: " + MY_INT);
                    local_value = MY_INT;
                }
//                Thread.sleep(20);
//                synchronized (this.getClass()){}
//                Lock lock=new ReentrantLock(); lock.lock(); lock.unlock();
//                File file = new File("F://text.txt");
//                lock.lock(); lock.unlock();
            }
        }
    }

    static class ChangeMaker extends Thread {
        @Override
        public void run() {
            int local_value = MY_INT;
            while (MY_INT < 10000){
               log.info("Increment MY_INT to " + (local_value+1));
                MY_INT = ++local_value;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }

    public static void main(String[] args) {
        disable();
    }

}
