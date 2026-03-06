package com.syntax;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import org.apache.commons.jexl3.*;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.convert.Convert.toBigDecimal;

/**
 * @author admin
 */
//临时随手代码测试类,可以忽略次类

public class SyntaxTest {
    private static List<Byte> byteList = new ArrayList<>();
    private static final Log log = LogFactory.get();

    public static void main(String[] args) throws ScriptException {
        Long id = JSON.parseObject("\"2016406451028504576\"", Long.class);
        String type = JSON.parseObject("\"I\"", String.class);
        log.info("id:{},type:{}", id, type);
    }

    public static BigDecimal calc(String formula, Map<String, BigDecimal> paramMap) {
        Object result = null; // 21.0
        try {
            JexlContext jc = new MapContext();
            paramMap.forEach((key, val) -> {
                jc.set(key, val);
            });
            // 替换%
            String formulaTmp = formula.replaceAll("%", "/100");
            JexlEngine jexl = new JexlBuilder().create();
            JexlExpression expr = jexl.createExpression(formulaTmp);
            JexlContext context = new MapContext();
            context.set("x", 4);
            context.set("数量", 2);
            result = expr.evaluate(context);
            System.out.println(result);
        } catch (Exception e) {
            // 解析错误信息，返回中文提示
            String chineseError = parseJexlError(e.getMessage());
            System.err.println(chineseError);
            throw new RuntimeException(e);
        }
        return toBigDecimal(result);

    }

    // 将 JEXL 错误翻译成中文
    /**
     * 将 JEXL 错误信息转换为中文
     */
    private static String parseJexlError(String errorMsg) {
        if (errorMsg.contains("parsing error in '$'")) {
            return "❌ 语法错误：变量名不能以 '$' 开头";
        } else if (errorMsg.contains("undefined variable")) {
            return "❌ 变量未定义：" + errorMsg.split("undefined variable")[1].trim();
        } else if (errorMsg.contains("method not found")) {
            return "❌ 方法调用失败：" + errorMsg.split("method not found")[1].trim();
        } else {
            return "❌ 表达式错误：" + errorMsg;
        }
    }


}




class A {
    private String AAAA;
    public static void ado(){
        System.out.println("A方法");
    }
}

class B extends A{
    public static void ado(){
        System.out.println("B方法");
    }
}
class DB {
    public Integer a1;
    public Integer a2;
    public Integer a3;
}









