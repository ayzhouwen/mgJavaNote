package com.syntax.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 注意 META-INF/services下的接口名文件和文件里的类必须写对,多个类需要换行
 */
public class SPITest {
    public static void main(String[] args) {
        ServiceLoader<IotCommandSPI> load=ServiceLoader.load(IotCommandSPI.class);
        Iterator<IotCommandSPI> iterator=load.iterator();
        while (iterator.hasNext()){
            IotCommandSPI e=iterator.next();
            e.executeCommand();
        }
    }
}
