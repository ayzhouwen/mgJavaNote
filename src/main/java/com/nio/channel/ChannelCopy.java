package com.nio.channel;
//http://blog.csdn.net/u011116672/article/details/51527373

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

//下面通过从通道拷贝数据带缓冲区为例，对通道的基本使用做一个简单演示：
//次方法会阻塞,追踪代码在本地read初,也不知道作者何意
public class ChannelCopy {

		public static void main(String[] args) throws IOException {
			

			//创建一个源通道
			ReadableByteChannel  source=Channels.newChannel(System.in);
			//创建一个目标通道
			WritableByteChannel  dest =Channels.newChannel(System.out);
		
			channelCopy(source, dest);
			//关闭通道
			source.close();
			dest.close();
		}
		
		//通道拷贝
		private static void channelCopy(ReadableByteChannel  source,WritableByteChannel  dest) throws IOException{
						ByteBuffer buffer= ByteBuffer.allocate(16*1024);
						while (source.read(buffer)!=-1){
						             //翻转缓冲区
							buffer.flip();
							//将缓冲区还有数据的话就写到目标通道中
							while (buffer.hasRemaining()) {
								dest.write(buffer);
							}
							//清空缓冲区
							buffer.clear();
						
						}
		}
		
		
		//通道拷贝另一种方式
		private static void channelCopy2(ReadableByteChannel  source,WritableByteChannel  dest) throws IOException{
						ByteBuffer buffer= ByteBuffer.allocate(16*1024);
						while (source.read(buffer)!=-1){
						             //翻转缓冲区
							buffer.flip();
							//将缓冲区的数据写到目标通道
							dest.write(buffer);
							//清空缓冲区
							buffer.compact();
						}
						
						 // 翻转缓冲区
				        buffer.flip();
				        // 将剩余的数据写入目标缓冲区
				        while (buffer.hasRemaining()){
				            dest.write(buffer);
				        }
		}
}
