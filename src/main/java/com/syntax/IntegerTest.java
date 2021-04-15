package com.syntax;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IntegerTest {
    public static void main(String[] args) {
//        IntegerTrap1();
        IntegerTest test=new IntegerTest();
        test.TestObjChange();
    }

    /**
     *  Integer和Long 的坑,虽然有些老生常谈,但是是提示自己不要忘记,
     */
    public static void IntegerTrap1(){
        Integer a = 100;
        Integer b = 100;

        //-128到127直接走内存缓存,所以结果为true
        System.out.println(a == b);
        Integer i = 100001;
        Integer j = 100001;

        //不走缓存创建新对象,所以结果为false
        System.out.println(i == j);
        //暂时理解为拆箱操作,有时间跟踪class字节码
        System.out.println(i >= a);
        //拆箱操作,有时间跟踪class字节码
        System.out.println(i >= j);
    }


    /**
     * 测试将对象赋值给新的Integer后,改变原对象值,是否会一起变化
     */
    public   void  TestObjChange(){
        Person person=new Person();
        person.setAge(2500);
        person.setName("华为");
        Integer v=person.getAge();
        Integer w=v;
        String name= person.name;
        person.setAge(999);
        person.setName("小米");
        v=888;
        log.info("v:"+v.toString()+name);
        log.info(JSON.toJSONString(person));
        log.info("w:"+w);
    }

    @Data
    private class  Person{
        public  Integer age;
        public String name;
    }
}
