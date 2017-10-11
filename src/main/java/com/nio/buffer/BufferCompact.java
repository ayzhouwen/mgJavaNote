package com.nio.buffer;
//http://blog.csdn.net/u011116672/article/details/51472373

import java.nio.CharBuffer;

//使用compact方法释放一部分数据(也叫压缩)
//总结compact就是从potion开始到最末尾存储数据的位置,向前移动到0初,最后potion为移动元素集合的最大值,limite为最大值
public class BufferCompact {
		public static void main(String[] args) {
				String s="hello";
				CharBuffer buffer=CharBuffer.allocate(10);
				for (int i = 0; i < s.length(); i++) {
					buffer.put(s.charAt(i));
				}
				
				//将第一个位置的字符h换为M,并填入一个新的字符w
				buffer.put(0,'M').put('w');
				//现在缓冲区的元素是Mellow,position的位置是6,limit和capcaity都是10
				//调用flip方法得到有效元素位置,其实就是position位置
				buffer.flip();
				//将M和e两个字符释放掉
				System.out.println("release char "+buffer.get());
				System.out.println("release char "+buffer.get());
				//调用compact方法可以回收M和e字符占用的空间
				//调用compact后的position位置是4
				//0-3位置的字符一次是llow,4-5位的字符依次是ow
				//虽然4,5位置有字符,但是调用put方法后会被重写
				//并且调用compact后limit又回到了capacity的位置,也就是10
				buffer.compact();
				//将4-6位置的字符修改为'a,b'
				buffer.put('a').put('b');
				//输出缓冲区的元素,根据上面的分析,最后输出应该是llowab
				//先调用flip方法将potion设置为0,这样才能读到完整2的字符
				buffer.flip();
				while(buffer.hasRemaining()){
					System.out.println("print output"+buffer.get());
				}
				
				
		}
}
