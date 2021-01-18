package com.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * 读取myconfig.json内容
 */
@Slf4j
public class ConfigUtil {
    public static String getConfigValue(String key){
        try {
            String json= FileUtil.readUtf8String(JarTool.getJarDir()+"/myconfig.json");
            return JSON.parseObject(json).getString(key);
        } catch (Exception e) {
           log.error("获取配置信息异常",e);
        }
        return null;
    }
}
