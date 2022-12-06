package com.syntax.Collections;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * LinkHashSet 测试
 */
@Slf4j
public class LinkHashSetTest {
    /**
     * HashSet插入无序演示
     */
    public static void test1(){
        Set<String> ipSet=new HashSet<>();
        ipSet.add(null);
        ipSet.add("1.168.53.22");
        ipSet.add("192.168.22.23");
        ipSet.add("5.168.53.24");
        ipSet.add("192.168.77.25");
        log.info("无序ipSet:"+ JSONUtil.toJsonStr(ipSet));
    }


    public static void test2(){
        Set<String> ipSet=new LinkedHashSet<>();
        ipSet.add(null);
        ipSet.add("1.168.53.22");
        ipSet.add("192.168.22.23");
        ipSet.add("5.168.53.24");
        ipSet.add("192.168.77.25");
        log.info("有序ipSet:"+ JSONUtil.toJsonStr(ipSet));
    }

    public static void main(String[] args) {
        LinkHashSetTest.test1();
        LinkHashSetTest.test2();
    }
}
