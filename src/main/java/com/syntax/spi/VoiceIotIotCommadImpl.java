package com.syntax.spi;

/***
 * 声音命令
 */
public class VoiceIotIotCommadImpl implements IotCommandSPI {
    @Override
    public void executeCommand() {
        System.out.println("开始执行声音命令");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("结束执行声音命令");
    }
}
