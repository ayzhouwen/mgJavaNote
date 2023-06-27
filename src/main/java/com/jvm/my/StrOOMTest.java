package com.jvm.my;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 测试字符串OOM -Xms128M -Xmx128M
 * <p>
 * 结论：jdk1.8 字符串实际存储在堆中，字符串常量池存储的是内存地址，
 * 只要不使用集合数组长期持有，那么无论是字符串疯狂拼接还是 字符串疯狂new,还是StringBuilder 疯狂append 都会自己释放
 * 执行1分钟3中方法测试结果
 * （1）strAppend
 * Eden Space (41.500M，41.500M): 36.253M，14195 collections，10.023s
 * old Gen (85.500M，85.500M): 66.623M, 0 collections.0s
 * cpu占用15%左右
 * （2）newStr
 * Eden Space (41.500M,21.500M): 14.536M，15344 collections，18.8235
 * old Gen (85.500M，85.500M): 51.254M,563 collections，4.296s
 * cpu占用30%左右
 * （3）joinStr
 * Eden Space (41.500M，20.500M): 11.226M.15830 collections，18.899s
 * old Gen (85.500M，85.500M): 58.794M,429 collections，3.271s
 * cpu占用26%左右
 *
 * 结论 拼接字符串性能排行： StringBuilder>joinStr>newStr
 *
 *
 */
@Slf4j
public class StrOOMTest {


    /*
     * 拼接字符串测试
     */
    public void joinStr() {
        String r = "";
        List<String> stringList = new ArrayList<>();
        Set<String> stringSet = new HashSet<>();
        Map<String, Object> map = new HashMap<>();
        String[] strArr = new String[1024 * 1024];
        for (int i = 0; i < 1024 * 1024 * 1024; i++) {
            r = r + IdUtil.fastSimpleUUID() + ",";
//                r=r.intern()+IdUtil.fastSimpleUUID().intern()+",".intern();
            log.debug(r);
            //如果执行此操作，启动后立即内存溢出
//            stringList.add(r);
//            stringSet.add(r);
//            map.put(r,1);
//            strArr[i]=r;
        }

        log.info(r + "*****");
    }

    /**
     * new字符串测试
     */
    public void newStr() {
        String r = "";
        List<String> stringList = new ArrayList<>();
        Set<String> stringSet = new HashSet<>();
        Map<String, Object> map = new HashMap<>();
        String[] strArr = new String[1024 * 1024];
        for (int i = 0; i < 1024 * 1024 * 1024; i++) {
            r = new String(r + IdUtil.fastSimpleUUID().intern() + ",");
//                r=r.intern()+IdUtil.fastSimpleUUID().intern()+",".intern();
            log.debug(r);
            //如果执行此操作，启动后立即内存溢出
//            stringList.add(r);
//            stringSet.add(r);
//            map.put(r,1);
//            strArr[i]=r;
        }

        log.info(r + "*****");
    }

    /**
     * StringBuilder 字符串append
     */
    public void strAppend() {
        StringBuilder r = new StringBuilder();
        List<String> stringList = new ArrayList<>();
        Set<String> stringSet = new HashSet<>();
        Map<String, Object> map = new HashMap<>();
        String[] strArr = new String[1024 * 1024];
        for (int i = 0; i < 1024 * 1024 * 1024; i++) {
            r.append(IdUtil.fastSimpleUUID()).append(",");
            log.debug(r.toString());
            //如果执行此操作，启动后立即内存溢出
//            stringList.add(r.toString());
//            stringSet.add(r);
//            map.put(r,1);
//            strArr[i]=r;
        }

        log.info(r + "*****");
    }

    public static void main(String[] args) {
        StrOOMTest strOOMTest = new StrOOMTest();
        strOOMTest.strAppend();
    }
}
