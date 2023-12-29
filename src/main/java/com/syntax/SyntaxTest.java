package com.syntax;

import cn.hutool.core.util.PageUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * @author admin
 */
//临时随手代码测试类,可以忽略次类
@Slf4j
public class SyntaxTest {
    public static void main(String[] args)  {
        System.out.println(JSON.toJSONString(PageUtil.rainbow(5, 20, 6)));
    }

}











