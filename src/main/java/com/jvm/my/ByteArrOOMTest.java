package com.jvm.my;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * jvm 运行参数 -Xms512M -Xmx512M
 * 关于Byte与byte数组申请时，占用空间测试和OOM时，java整个进程会不会宕掉，申请内存所在的线程会宕掉
 * 除非 try catch Throwable，
 *
 */
@Slf4j
public class ByteArrOOMTest {
    static  List<Byte[]> list=new ArrayList<>();
    public static void main(String[] args) throws InterruptedException {

        Thread thread =new Thread(()->{

            while (true){

                try {
                    Byte [] array=new Byte[1024*1024*20];
                    log.info("申请内存11:{},大小:{}",array.toString(),array.length);
                    list.add(array);

                } catch (Exception e) {
                    log.error("异常：",e);
                    throw new RuntimeException(e);
                }catch (Throwable error){
                    log.error("jvm错误：",error);
                }finally {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
        while (true){
            log.info("我还好");
            Thread.sleep(1000);
        }
    }


}


