package com.syntax.Collections;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

//这里主要是list的一些新特性
@Slf4j
public class List8 {
    //双重嵌套修改元素,都是给予引用的,list元素,和map元素改完后生效
    void editElement(){
        Map map=new ConcurrentHashMap();
        map.put("list",new ArrayList<>());
       List<MyNode> list = (List<MyNode>) map.get("list");
        MyNode m1=new MyNode(1,null);
        MyNode m2=new MyNode(2,null);
        MyNode m3=new MyNode(3,null);
        MyNode m4=new MyNode(4,null);
        m1.childrenList.add(m2);
        m3.childrenList.add(m4);
        list.add(m1);
        list.add(m3);

        //java8方式遍历修改
        list.forEach(e->{
            e.childrenList.forEach(sue->{
                sue.childrenList.add(new MyNode(5,null));
            });
        });

        //传统遍历修改
        for (MyNode e:list){
            for (MyNode su:e.childrenList){
                su.childrenList.add(new MyNode(6,null));
            }
        }

        //过滤搜索,如果在过滤中对元素进行修改,那么最后的值也会被修改
      MyNode find=  list.stream().filter(e->{
                //e.data=9999; 可以实现对元素的修改
           return e.childrenList.stream().filter(sue->2==sue.data).count()>0;
        }).findFirst().orElse(null);
        System.out.println(JSON.toJSONString(list));
        System.out.println(JSON.toJSONString(map));
        System.out.println(JSON.toJSONString(find));





    }

    /**
     * jdk8分组
     */
    void group(){
        List<Person> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Person p=new Person();
            p.setAge(i);
            p.setName(RandomUtil.randomInt(1,5)+"");
            list.add(p);
        }
        Map<String, List<Person>> groupmap =
                list.stream().collect(Collectors.groupingBy(Person::getName));
        log.info(JSON.toJSONString(groupmap ));
    }

    public static void main(String[] args) {
        List8 list8=new List8();
        list8.editElement();
    }


    @Data
    class  Person implements Serializable {

        public  int age;
        public String name;

    }
}

class  MyNode {
    public MyNode(Integer data, List <MyNode>   childrenList){
        this.data=data;
        if (CollUtil.isNotEmpty(childrenList)){
            this.childrenList=childrenList;
        }

    }
   public Integer data;
    public List <MyNode>  childrenList=new ArrayList<>();
}