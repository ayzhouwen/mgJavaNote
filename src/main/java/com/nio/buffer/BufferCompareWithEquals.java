package com.nio.buffer;

import java.nio.CharBuffer;

//http://blog.csdn.net/u011116672/article/details/51472373
//使用equals方法比较两个缓冲区是否相等
public class BufferCompareWithEquals {
		public static void main(String[] args) {
				String s1="two com";
				String s2="com";
				CharBuffer buffer1=CharBuffer.allocate(10);
				CharBuffer buffer2=CharBuffer.allocate(10);
				 System.out.println(buffer1.compareTo(buffer2));
				for(int i=0;i<s1.length();i++){
					buffer1.put(s1.charAt(i));
					buffer2.put(s1.charAt(i));
				}
				buffer2.put('s');
				buffer2.put("虎");
				 System.out.println(buffer1.compareTo(buffer2));
				//调用flip方法后limit 为7
				buffer1.flip();
				//继续往缓冲区放入一个字符,当前position的位置是1
				buffer1.put('A');
				//将当前位置设置为4,也就是c开始的位置
				buffer1.position(4);
				
				for(int i=0;i<s2.length();i++){
					buffer2.put(s2.charAt(i));
				}
				//limit位置是3,position的位置是0
				buffer2.flip();
				
				//现在执行put方法会把位置0的字符c替换为'B'
				buffer2.limit(4);
				buffer2.put(3,'B');
				buffer2.position(0);
				buffer2.limit(3);
				
				  // 使用equals方法比较两个缓冲区是否相等
		        // 必须满足三个条件才能返回true
		        // 1）两个缓冲区的对象的类型必须相同
		        // 2）缓冲区的元素不必完全相同，但是如果position到limit之间的元素不相同，也返回false
		        // 3）每个缓冲区get方法得到元素序列必须一致
				
				 System.out.println(buffer1.equals(buffer2));
				 System.out.println(buffer1.compareTo(buffer2));
				 
				 // 还可以使用compareTo方法比较两个缓冲区，但是如果两个缓冲区的元素的类型不一致，equals方法返回false
			        // 而compareTo方法则直接抛出ClassCastException的异常
				
				
				
				
		}
}
