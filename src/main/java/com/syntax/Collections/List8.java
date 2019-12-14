package com.syntax.Collections;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//这里主要是list的一些新特性
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

    public static void main(String[] args) {
        List8 list8=new List8();
        list8.editElement();
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