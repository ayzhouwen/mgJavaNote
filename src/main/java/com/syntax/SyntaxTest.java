package com.syntax;

import com.syntax.exception.PingCollectException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author admin
 */
//临时随手代码测试类,可以忽略次类d
    @Slf4j
class SyntaxTest {

    public static void main(String[] args)  {
            String a=null;
            String b=null;
            log.info("成功获取锁名称:"+a+",当前锁实例id:"+b);
            log.info("成功获取锁名称:{}当前锁实例id:{}",a,b);
            if (2>1){
                throw  new PingCollectException("RPC执行错误",PingCollectException.RPC_ERRO);
            }

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






