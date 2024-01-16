package com.syntax;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author admin
 */
//临时随手代码测试类,可以忽略次类
@Slf4j
public class SyntaxTest {

    public static void main(String[] args) {
        // File file= ZipUtil.zip("C:/Users/Administrator/Desktop/img","C:/Users/Administrator/Desktop/组件.zip",true);
        System.out.println(StrUtil.subAfter("/asd/asd/ssssss/777.png","/",true));
        System.out.println(StrUtil.subBefore("/asd/asd/ssssss/777.png","/",true));
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("sync",false);
        log.info(jsonObject.toJSONString());
        jsonObject=JSON.parseObject(jsonObject.toJSONString());


    }

}











