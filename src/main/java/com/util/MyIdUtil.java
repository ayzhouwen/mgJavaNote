package com.util;


import cn.hutool.core.util.IdUtil;

import java.util.List;

public class MyIdUtil {
    public static String getIncId(){
        return IdUtil.getSnowflake(1,1).nextIdStr();
    }

    /**
     * 拼接Id,在select in id中,如果in的元素数量超过1000,oracle会报错,
     * 所以将其拆分成 字段名 in(xxx) or 字段名 in (xxx) ...的形式
     * @param inList in元素
     * @param fieldName 字段名
     * @param inSzie 到多少条生成新的or
     * @return
     */
    public static String splitJoinId(List<String> inList, String fieldName ,int inSzie){
        StringBuffer sb = new StringBuffer();
        sb.append(fieldName).append(" in ( '");
        int len=inList.size();
        for (int i = 0; i < len ; i++) {

            if (i==0){
                if (len==1){
                    //只有一个元素不拼接逗号
                    sb.append(inList.get(i))
                            .append("'");
                }else {
                    //首元素后面加个逗号
                    sb.append(inList.get(i))
                            .append("',");
                }
            }else if (i%inSzie==0){

                if (i==len-1){
                    sb.append(" ) or ").append(fieldName).append(" in ( '").append(inList.get(i)).append("'");
                }else {
                    //新的or拼接开始了
                    sb.append(" ) or ").append(fieldName).append(" in ( '").append(inList.get(i))
                            .append("',");
                }
            }else if (i%inSzie==inSzie-1 || i==len-1){
                //每个拼接的or最后不添加逗号,并且拼接尾部的括号
                sb.append("'").append(inList.get(i)).append("'");
            }else {
                sb.append("'").append(inList.get(i)).append("'").append(",");
            }

            if (i==len-1){
                sb.append(")");
            }

        }
        String sql=sb.toString();
        return sql;
    }


}
