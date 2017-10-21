package com.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

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
	//wrap array等测试
	public  static  void test3(){
			char [] myArray=new  char[100];
		CharBuffer charBuffer =CharBuffer.wrap(myArray);
		charBuffer.put("中国");

		char [] myArray1=new  char[100];
		CharBuffer charBuffer1=CharBuffer.wrap(myArray1,10,5); //数据范围为10~14; 超出长度报错
		charBuffer1.put("abcde");
		myArray1[14]='f';

		char []  carr=charBuffer1.array();
		System.out.println(carr==myArray1);

		CharBuffer charBuffer3= CharBuffer.allocate(5);
		charBuffer3.put("hello");
		System.out.println("array:"+charBuffer3.hasArray()); //返回true
		char []  selCarr=charBuffer3.array(); //正常返回

		System.out.println("arrayOffset:"+charBuffer.arrayOffset()+","+charBuffer1.arrayOffset()+","+charBuffer3.arrayOffset());

	}

	//put测试
	public  static  void test4(){
		ByteBuffer longBuffer = ByteBuffer.allocate (8);
		longBuffer.order(ByteOrder.BIG_ENDIAN);
		longBuffer.putLong (0, 385);
		longBuffer.position (4);
		longBuffer.slice();
	}
	
	public static void main(String[] args) {
		test4();
	}
}
