package com.nio.buffer;
//来源:http://blog.csdn.net/u011116672/article/details/51472373

import java.nio.CharBuffer;

//填充,翻转,释放
public class BufferFillDrain {
		//位置
	private static int index=0;
	//需要填充入缓冲区字符串数组
	private static String[] strings=new String[]{
			"Hello,this is a bugger",
			"I am learning buffer",
			"It is a wonderful world"
	};
	
	public static void main(String[] args) {
				//申请100字节的缓冲区
		CharBuffer buffer=CharBuffer.allocate(100);
				//如果缓存区没有达到上届则可以继续填充
		while(fillBuffer(buffer)){
			//缓冲区翻转
			//flip可以将上界设为当前position的位置
			//并将position重置为0
			buffer.flip();
			//从缓冲区释放数据
			drainBuffer(buffer);
			//清空缓冲区
			//调用clear方法可本质上干了两件事
			//一:将上届limit设为容量大小的位置
			//二:将position重置为0
			buffer.clear();
			
		}
	}
	
	private static void drainBuffer(CharBuffer buffer){
		//得到剩余的元素个数
		int count=buffer.remaining();
		for(int i=0;i<count;i++){
			System.out.println(i+"--->"+buffer.get());
		}
	}
	
	
	private static boolean fillBuffer(CharBuffer buffer){
		if (index >=strings.length) {
			return false;
		}
		
		//依次放入每个字符串数组
		String s=strings[index++];
		for(int i=0;i<s.length();i++){
			buffer.put(s.charAt(i));
		}
		return true;
	}
	
}
