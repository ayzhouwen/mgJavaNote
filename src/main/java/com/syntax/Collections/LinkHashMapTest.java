package com.syntax.Collections;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 关于linkhashmap 测试和有可能会出现的坑
 */
public class LinkHashMapTest {
    /**
     * 无参数构造函数情况下linkedHashMap的按插入顺序进行排序
     */
   public static  void test1(){
       Map linkedHashMap=new LinkedHashMap();
       linkedHashMap.put("ABC9",1);
       linkedHashMap.put("ABC8",2);
       linkedHashMap.put("ABC7",3);
       linkedHashMap.put("ABC6",4);
       linkedHashMap.forEach((k,v)->{
           System.out.println(k+":"+v);
       });


   }

    /**
     * 当指定排序order为true时,那么每次get都会将此元素放到链表的最后
     * 注意:在遍历linkedHashMap不能再使用  linkedHashMap.get("xxx")方法,否则会报错,
     */
    public static  void test2(){
        Map<String,Integer> linkedHashMap=new LinkedHashMap(100,0.75F,true);
        linkedHashMap.put("1",1);
        linkedHashMap.put("2",2);
        linkedHashMap.put("3",3);
        linkedHashMap.put("4",4);
        linkedHashMap.forEach((k,v)->{
            System.out.println(k+":"+v);
        });
        System.out.println("-------------");
       int a= linkedHashMap.get("1");
        System.out.println(a);
        linkedHashMap.forEach((k,v)->{
            System.out.println(k+":"+v);
        });
        linkedHashMap.put("5",5);
        linkedHashMap.put("6",6);
        a=linkedHashMap.get("5");
        linkedHashMap.forEach((k,v)->{
            System.out.println(k+":"+v);
            linkedHashMap.get(k);
        });
    }

    public static void main(String[] args) {
        //test1();
        test2();
    }
}
