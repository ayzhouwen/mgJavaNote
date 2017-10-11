package com.syntax.Collections;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashSet;

/**
 * Created by Administrator on 2017/9/2.
 */
public class HashSetTest {
    //hashset 元素的size和里面的内容一样才行,如果里面是对象,必须实现了eq和hashcode方法,
    public static void testJson(){
        A a=new A(1,"2");
        A a1=new A(1,"2");
        A a2=new A(1,"2");
        A a3=new A(1,"2");
        //JSONObject jsonObject=new  JSONObject();  jsonObject.put("a","a");  jsonObject.put("b","b");
        //JSONObject jsonObject1=new  JSONObject();  jsonObject1.put("a","a");  jsonObject1.put("b","b"); jsonObject1.put("c","c");
        JSONObject jsonObject=new  JSONObject();  jsonObject.put("a",a);  jsonObject.put("b",a1);
        JSONObject jsonObject1=new  JSONObject();  jsonObject1.put("a",a2);  jsonObject1.put("b",a3);
        JSONArray list=new JSONArray(); list.add(jsonObject);list.add(jsonObject1);
        HashSet hashSet=new HashSet(list);
        System.out.println(hashSet);
    }
    public static void main(String[] args) {
        testJson();
    }

}

class  A {
    public  int a;
    public String b;
    public  A(int a,String b){
        this.a=a;
        this.b=b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==this){
            return  true;
        }
        A a=(A)obj;
        if (a.a==this.a&&a.b.equals(this.b)){
            return  true;
        }
        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        int result=0;
        result=this.a*31+this.b.hashCode();
        return result;
    }
}
