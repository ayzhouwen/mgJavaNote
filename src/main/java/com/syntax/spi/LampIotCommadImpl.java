package com.syntax.spi;

/**
 * 灯命令实现
 */
public class LampIotCommadImpl implements IotCommandSPI {
    @Override
    public void executeCommand() {
        System.out.println("开始执行灯光命令");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("结束执行灯光命令");
    }
}
