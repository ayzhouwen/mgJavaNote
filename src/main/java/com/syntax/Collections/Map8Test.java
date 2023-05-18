package com.syntax.Collections;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jdk8 map 新特性测试
 * 参考：https://www.cnblogs.com/javastack/p/14537491.html
 */

@Slf4j
public class Map8Test {
    /**
     * 测试Compute方法
     */
    public  static  void testCompute(){

        List<String> animals = Arrays.asList("dog", "cat", "cat", "dog", "fish", "dog");
        Map<String, Integer> map = new HashMap<>();
        for(String animal : animals){
            //不使用新特性实现
//            Integer count = map.get(animal);
//            map.put(animal, count == null ? 1 : ++count);
            map.compute(animal, (k, v) -> v == null ? 1 : ++v);
        }
        log.info(map.toString());
    }

    public static void main(String[] args) {
        Map8Test.testCompute();
    }
}
