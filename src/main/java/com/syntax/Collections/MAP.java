package com.syntax.Collections;

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
        Map<String,List<String>> map=new HashMap();
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

    //测试Map key 的类型long是否和Long一致
    //结果long和Long 在为key时,只要数值一样,使用上就没问题
    public static  void testLongMap(){
        Map<Long,Object> longmap=new HashMap<>();
        Long L1=new Long(9999);
        long L2=9999;
        longmap.put(L1,1);
        longmap.put(L2,2);
        //此时map里只有一个元素
        System.out.println(longmap.size());
        System.out.println(longmap.get(L1)==longmap.get(L2));
    }
    public static void main(String[] args) {
        //testMapEQ();
        testLongMap();
    }
}
