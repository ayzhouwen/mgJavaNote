package com.threeparty.httpclient;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;

import java.io.UnsupportedEncodingException;

/**
 *
 */
public class TestHttpClient {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String id="阈";
        System.out.println("utf-8:"+ HexUtil.encodeHexStr(id));
        System.out.println("gbk:"+HexUtil.encodeHexStr(id, CharsetUtil.CHARSET_GBK));
        System.out.println("iso-8859-1:"+ HexUtil.encodeHexStr(id, CharsetUtil.CHARSET_ISO_8859_1)); //iso-8859不能直接getBytes("ISO-8859-1")

        //用udf-8编码后转为iso-8859-1 ,类似于编译成中间码
        String encode8859=new String(id.getBytes("utf-8"),"ISO-8859-1");
        System.out.println(encode8859);
        System.out.println(HexUtil.encodeHexStr(encode8859));

        //对中间码进行解码
        String decode8859=new String(encode8859.getBytes("ISO-8859-1"),"utf-8");
        System.out.println(decode8859);
        System.out.println(HexUtil.encodeHexStr(decode8859));


        //识别编码
        boolean iso88591 = java.nio.charset.Charset.forName("ISO-8859-1").newEncoder().canEncode(decode8859);
        System.out.println(iso88591);
        iso88591 = java.nio.charset.Charset.forName("ISO-8859-1").newEncoder().canEncode(encode8859);
        System.out.println(iso88591);
    }
}
