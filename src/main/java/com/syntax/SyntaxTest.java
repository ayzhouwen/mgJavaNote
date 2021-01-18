package com.syntax;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.util.ByteUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author admin
 */
//临时随手代码测试类,可以忽略次类d
    @Slf4j
class SyntaxTest {

    //   private static final Unsafe unsafe = Unsafe.getUnsafe();
    public static void main(String[] args) throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, ScriptException, InterruptedException {
        List<Person> list=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Person p=new Person();
            p.setAge(i);
            p.setName(RandomUtil.randomInt(1,5)+"");
            list.add(p);
        }
//        Map<String, List<Person>> groupmap =
//                list.stream().collect(Collectors.groupingBy(Person::getName));
//        log.info(JSON.toJSONString(groupmap ));

        log.info(JSON.toJSONString(list));
        list.remove(0);
        list.remove(0);
        list.remove(0);
        log.info(JSON.toJSONString(list));

       String json= FileUtil.readUtf8String("./myconfig.json");
        System.out.println(json);

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






