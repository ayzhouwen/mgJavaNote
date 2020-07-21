package com.syntax.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * java 捕获异常测试,就算你捕获了Throwable,可以拦截到内存溢出,但是程序还是会停止
 */
public class ExceptionTest {
    public static void main(String[] args) {
        List list=new ArrayList();
        try {
            while (true){
                byte [] barr=new byte[1024*1024];
                list.add(barr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
