package com.jvm.my;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * -Xms500M -Xmx500M
 * MinorGC 年轻代gc测试,主要测试什么时候会触发，测试结果:
 * Eden 满的时候才会触发年轻代gc
 *
 */
@Slf4j
public class JvmMinorGCTest {
    public static void main(String[] args) throws InterruptedException {
        byte [] bArray=null;
        //利用map使其内存不释放，否则老年代会回收
        Map<String,Object> map=new HashMap<>();
        while (true){
            bArray=new byte[1024*1024*5];
            log.info(bArray.toString());
            map.put(bArray.toString(),bArray);
            Thread.sleep(5000);
        }
    }
}
