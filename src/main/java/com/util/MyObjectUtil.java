package com.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;

/**
 * 对象工具
 */
public class MyObjectUtil {

    /**
     * 验证两个对象是否相等,仅支持string 和数值类型
     * 字符串比较时,null与"",一样
     *
     * @param obj1
     * @param obj2
     * @return
     */
   public static boolean equal(Object obj1, Object obj2){
        String str1= Convert.toStr(obj1);
        String str2= Convert.toStr(obj2);

        if (StrUtil.isEmpty(str1)){
            str1="";
        }
        if (StrUtil.isEmpty(str2)){
            str2="";
        }

        return StrUtil.equals(str1, str2);
    }


}
