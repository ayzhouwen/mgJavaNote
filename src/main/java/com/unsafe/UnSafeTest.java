package com.unsafe;

import com.util.UnSafeUtil;

/**
 * 针对UnSafe的一些测试
 */
public class UnSafeTest {
    /**
     * 操作堆外内存
     */
    public void memory() throws InterruptedException {
        long address = 0;
        //申请内存
        address = UnSafeUtil.unsafe.allocateMemory(1024 * 1024 * 1024 * 28L);
        System.out.println(address);
        //等待溢出
        while (true) {
            UnSafeUtil.unsafe.putInt(address, 9);
            address = address + 4;
//            Thread.sleep(0);
        }
//        Thread.sleep(1000*60);
    }


    /**
     * 内存屏障
     *
     * @throws InterruptedException
     */
    public void MemmoyBarrier() {
    }

    /**
     * cas
     */
    public void cas() {

    }


    //操作cas

    public static void main(String[] args) throws InterruptedException {
        UnSafeTest unSafeTest = new UnSafeTest();
        unSafeTest.memory();
    }
}
