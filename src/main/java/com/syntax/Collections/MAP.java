package com.syntax.Collections;

import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/24.
 */
public class MAP {
    //测试map是否相等
    public  static  void  testMapEQ(){
        Map<String,Object> hmap=new HashMap();
        Map<String,Object> hmap1=new HashMap();

        hmap.put("id",35);
        hmap.put("name","张三");

        hmap1.put("id",35);
        hmap1.put("name","张三");
        hmap1.put("name","张三");
        hmap1.put("age","张三");

        System.out.println(hmap==hmap1);
        System.out.println(hmap.equals(hmap1));


    };

    // map  测试返回空list后,list被重新赋值,map会不会重新引用,结论:不会

    public  static  void  nullList(){
        Map<String,List<String>> map=new HashedMap();
        List<String> list=  map.get("add");
        if (list==null){
            list=new ArrayList<>();
            list.add("haha1");
            list.add("haha2");
            list.add("haha3");
            list.add("haha4");
            map.put("add",list);
        }

        System.out.println(map);
    }
    public static void main(String[] args) {
        testMapEQ();
    }
}
