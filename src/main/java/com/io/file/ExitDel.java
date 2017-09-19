package com.io.file;
//2017年4月7日21:27:11
//第一次听说还有文件延迟至jvm退出后删除这一说,很神奇

import java.io.File;
import java.io.IOException;


public class ExitDel {
	public void test() throws IOException{
	    File file=new File("D:"+File.separator+"zw"+File.separator+"HelloFile.txt");
	    if (file.exists()) {
			file.deleteOnExit(); //延迟到jvm退出执行
			file.createNewFile(); 
		}else {
			 System.out.println("不存在改文件");
		}
	}
	
	public void test1() throws IOException{
	    File file=new File("D:"+File.separator+"zw"+File.separator+"HelloFile.txt");
	    if (file.exists()) {
			file.delete();//直接删除
			file.createNewFile(); //创建新文件
		}else {
			 System.out.println("不存在改文件");
		}
	}
	
	public static void main(String[] args) throws IOException {
		ExitDel ed=new ExitDel();
		ed.test1();
	}
}
