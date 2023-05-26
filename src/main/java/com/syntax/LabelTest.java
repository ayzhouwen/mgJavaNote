package com.syntax;

import lombok.extern.slf4j.Slf4j;

/**
 * java 标签语句测试
 */
@Slf4j
public class LabelTest {
    public static void main(String[] args) throws InterruptedException {
        abc:
        if(3>1){
            if(2>1){
                log.info("1");
                Thread.sleep(1000);
                break abc;
            }
            //如果上面有 break abc;下面这行不会输出
            log.info("测试break 标签 if");
        }



        outer: for (int i = 101; i < 150; i++) {
            for (int j = 2; j < i / 2; j++) {
                if (i % j == 0) {
                    continue outer;
                }
            }
            log.info(i + "  ");
        }
        log.info("===========================");
        outer2: for (int i = 101; i < 150; i++) {
            for (int j = 2; j < i / 2; j++) {
                if (i % j == 0) {
                    continue ;
                }
            }
            log.info(i + "  ");
        }

        log.info("===========================");
        outer3: for (int i = 101; i < 150; i++) {
            for (int j = 2; j < i / 2; j++) {
                if (i % j == 0) {
                    break outer3;
                }
            }
            log.info(i + "  ");
        }
    }
}
