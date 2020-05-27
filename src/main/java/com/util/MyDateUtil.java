package com.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 时间工具
 */
public class MyDateUtil {
    /**
     * ldt时间获取毫秒数字符串
     * @param ldt
     * @return
     */
    public static String ltdToMillisecondStr(LocalDateTime ldt) {
        //获取毫秒数
        Long milliSecond = ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return milliSecond.toString();
    }

    /**
     * ldt时间获取毫秒数
     * @param ldt
     * @return
     */
    public static Long ltdToMillisecondLong(LocalDateTime ldt) {
        //获取毫秒数
        Long milliSecond = ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return milliSecond;
    }

    /**
     * 输出执行时间
     * @param name
     * @param starttime
     */
    public static String execTime(String name,Long starttime){
        Double time= Convert.toDouble(DateUtil.spendMs(starttime));
        String result=name+":"+
                + time+"毫秒,"+ NumberUtil.decimalFormat("#.##",time/1000)+"秒,"+ NumberUtil.decimalFormat("#.##",time/1000/60)+"分钟";
        return result;
    }
}
