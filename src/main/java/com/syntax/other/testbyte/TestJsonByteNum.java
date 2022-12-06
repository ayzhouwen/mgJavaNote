package com.syntax.other.testbyte;


import cn.hutool.json.JSONUtil;

/**
 * 测试实际传输中数据类型对转json字符串字节大小的响应
 * 输出
 * req1:{"assetId":1234567890123456789,"collectItem":1,"cycleId":3,"limit":5,"offsetId":1234567890123456789,"reqId":2},字节数:110
 * req2:{"assetId":"1234567890123456789","collectItem":1,"cycleId":3,"limit":5,"offsetId":"1234567890123456789","reqId":2},字节数:114
 *结果在转json字符串的字节数与原先的数据类型差距很小,而且fastjson和hutool生成的json字符串字节数一致
 *
 **/
public class TestJsonByteNum {
    public static void main(String[] args) {
        StationCollectReq req=new StationCollectReq(1234567890123456789L,
                1,2,3,1234567890123456789L,5);

        StationCollectReq2 req2=new StationCollectReq2("1234567890123456789",
                1,2L,3L,"1234567890123456789",5);

        String str1= JSONUtil.toJsonStr(req);
        String str2= JSONUtil.toJsonStr(req2);
        System.out.println("req1:"+str1+",字节数:"+str1.getBytes().length);
        System.out.println("req2:"+str2+",字节数:"+str2.getBytes().length);

    }
}
