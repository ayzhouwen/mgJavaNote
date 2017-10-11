package com.nio.buffer;

import java.nio.ByteBuffer;

public class ByteBufferTest {

	private static ByteBuffer buffer;
	private static ByteBuffer byteBuffer;

	//flip与clear测试
	public  static void test1(){
		ByteBuffer buffer=ByteBuffer.allocate(88);
		String value="权";
		buffer.put(value.getBytes());
		buffer.flip();
		byte [] vArray=new byte[buffer.remaining()];
		buffer.get(vArray);
		String decodeValue=new String(vArray);
		
	}
	//翻转
	public static  void test2(){
		ByteBuffer buffer = ByteBuffer.allocate(5);
		buffer.put("H".getBytes()).put((byte)'e').put((byte)'1');
		buffer.flip();
		byte [] arr=  new byte[buffer.remaining()];
		buffer.get(arr);
		buffer.reset();

	}

	public  static  void test3(){

	}
	
	public static void main(String[] args) {
		test2();
	}
}
