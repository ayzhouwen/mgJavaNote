package com.syntax;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.Serializable;
import java.util.EnumSet;

/**
 * @author admin
 */
//临时随手代码测试类,可以忽略次类
class SyntaxTest {

    //   private static final Unsafe unsafe = Unsafe.getUnsafe();
    public static void main(String[] args) throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, ScriptException {
//        Date now = DateUtil.date();
//        System.out.println( now);
//        Integer n=-3;
//        //下一天
//        System.out.println("明天"+ DateUtil.offsetDay(now,n));      ;
//        //下一周
//        System.out.println("下一周"+DateUtil.offsetWeek(now,n));
//        //下一个月
//        System.out.println("下一个月"+DateUtil.offsetMonth(now,n));
//        //下一个季度
//        System.out.println("下一个季度"+DateUtil.offsetMonth(now,3*n));
//        //下一年
//        System.out.println("下一年:"+DateUtil.offsetMinute(now,n));
//
//        System.out.println(DateUtil.parseDateTime("2018-1-13 00:00:01").isAfterOrEquals(DateUtil.parseDateTime("2018-1-13 10:00:00")));
//
//        System.out.println("2018-01-19 10:15:18abc".substring(0,10));
//        ReUtil.replaceAll("asdasd", "\\.", "/");

        System.out.println(Color.valueOf("green"));  ;
        System.out.println(EnumSet.of(Color.green));  ;
        System.out.println(Color.getName(1));  ;







    }



}

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






