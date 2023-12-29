package com.stress;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 压力测试内存,需要配置启动内存-Xmx50m 4102267次后就会蹦会
 */
@Slf4j
public class StressMemory {
    public static void main(String[] args) {
            List<Byte> list=new ArrayList<>();
            for (int i = 0; i <10000*100000 ; i++) {
                try {
                    list.add((byte)1);
                }  catch (Exception e) {
                    log.error("异常{}：",list.size(),e);
                    throw e;
                }catch (Throwable e) {
                    log.error("崩溃{}：",list.size(),e);
                    throw e;
                }
            }
    }
}
