package com.jvm.my;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试字符串OOM
 */
@Slf4j
public class StrOOMTest {
    /**
     * 拼接字符串测试
     */
    public void joinStr(){
        String r="";
        for (int i = 0; i <1024*1024 ; i++) {
                r+=i+",";
//            log.info(r);
//            System.out.println(r);
        }
//        log.info(r);
        System.out.println(r+"*****");
    }

    /**
     * new字符串测试
     */
    public void newStr(){
        String r="";
        for (int i = 0; i <1024*1024 ; i++) {
            r=new String(r+i+",");
        }
        System.out.println(r+"*****");
    }

    public static void main(String[] args) {
        StrOOMTest strOOMTest=new StrOOMTest();
        strOOMTest.joinStr();
    }
}
