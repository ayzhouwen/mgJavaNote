package com.util;

import cn.hutool.core.io.FileUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 读取myconfig.json内容
 */
@Slf4j
public class ConfigUtil {

    public static String getConfigValue(String key){
        try {
             String fileFath=JarTool.getJarDir()+"/myconfig.json";
            log.info("当前配置文件路径:{}",fileFath);
            String json= FileUtil.readUtf8String(fileFath);
            return JSONObject.parseObject(json).getString(key);
        } catch (Exception e) {
           log.error("获取配置信息异常",e);
        }
        return null;
    }
}
