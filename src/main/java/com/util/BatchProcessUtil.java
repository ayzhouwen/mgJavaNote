package com.util;

import cn.hutool.core.util.PageUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 利用hutool 的PageUtil实现批次处理逻辑demo，代码由chatgpt生成,有bug稍微修改
 * 注意：transToStartEnd 还有getEnd方法计算的太无脑，没有考虑总条数,代码需要注意一下
 */
@Slf4j
public class BatchProcessUtil {
    public static void main(String[] args) {
        int totalCount = 10;
        int pageSize = 3;

        // 计算总页数
        int totalPage = PageUtil.totalPage(totalCount, pageSize);

        for (int currentPage = 0; currentPage < totalPage; currentPage++) {
            // 计算分页范围
            int[] startEnd = PageUtil.transToStartEnd(currentPage, pageSize);

            int startIndex = startEnd[0];
            int endIndex = startEnd[1];

            // 模拟处理每个批次
            // 模拟处理每个批次的业务逻辑
            endIndex=Math.min(endIndex,totalCount);
            endIndex=endIndex-1;
            processBatch(startIndex, endIndex);

            System.out.println("处理批次：" + currentPage);
            System.out.println("起始记录索引: " + startIndex);
            System.out.println("结束记录索引: " + endIndex);
            System.out.println("--------------");
        }
    }

    private static void processBatch(int startIndex, int endIndex) {

        for (int i = startIndex; i <= endIndex; i++) {
            // 处理记录
            System.out.println("Processing record " + i);
        }
    }
}
