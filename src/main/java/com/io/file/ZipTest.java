package com.io.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * zip 文件压缩和解压缩测试
 */
public class ZipTest {
    public static void main(String[] args) {

        List<File> fileList=new ArrayList<>();
        //添加img目录
        fileList.add(FileUtil.file("C:/Users/Administrator/Desktop/img"));
        //添加文件
        fileList.add( FileUtil.file("C:/Users/Administrator/Desktop/周文 2023.12.25-2023.12.29 工作周报.xls"));
        fileList.add( FileUtil.file("C:/Users/Administrator/Desktop/新建 XLSX 工作表.xlsx"));
        //打成压缩包，并且保证原来的目录结构比如压缩包里有img目录
        ZipUtil.zip(FileUtil.file("C:/Users/Administrator/Desktop/组件.zip"),
                Charset.forName("utf-8"),
                true, fileList.toArray(new File[0]));
        //解压缩
        System.out.println(ZipUtil.unzip("C:/Users/Administrator/Desktop/组件.zip"));
    }
}
