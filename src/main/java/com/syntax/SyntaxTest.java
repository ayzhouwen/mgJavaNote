package com.syntax;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.util.ByteUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author admin
 */
//临时随手代码测试类,可以忽略次类d
    @Slf4j
class SyntaxTest {

    //   private static final Unsafe unsafe = Unsafe.getUnsafe();
    public static void main(String[] args) throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, ScriptException, InterruptedException {

        // creating maps
        TreeMap<Integer, Person> treemap = new TreeMap<Integer, Person>();
        SortedMap<Integer, Person> treemapincl = new TreeMap<Integer, Person>();
        SortedMap<Integer, Person> treemapincl2 = new TreeMap<Integer, Person>();

        // populating tree map
        List<Person> list=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            Person person=new Person();
            person.setAge(i);
            list.add(person);
        }
        treemap.put(2, list.get(2));
        treemap.put(26, list.get(1));
        treemap.put(44, list.get(3));
        treemap.put(88, list.get(6));
        treemap.put(5, list.get(5));

        System.out.println("Getting tail map");
        treemapincl=treemap.headMap(17);
        System.out.println("Tail map values: "+treemapincl);

    }



}

@Data
class  Person implements Serializable {

    public  int age;
    public String name;

    public  Person next;
}

  enum Color{
        red("红色",1),yellow("黄色",2),green("绿色",3);
      // 成员变量
      private String name;
      private int index;

      public String getName() {
          return name;
      }

      public void setName(String name) {
          this.name = name;
      }

      public int getIndex() {
          return index;
      }

      public void setIndex(int index) {
          this.index = index;
      }

      // 构造方法
      private Color(String name, int index) {
          this.name = name;
          this.index = index;
      }
      // 普通方法
      public static String getName(int index) {
          for (Color c : Color.values()) {
              if (c.getIndex()== index) {
                  return c.name;
              }
          }
          return null;
      }

      //覆盖方法


      @Override
      public String toString() {
          return this.index+"_"+this.name;
      }


  }






