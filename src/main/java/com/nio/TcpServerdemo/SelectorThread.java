package com.nio.TcpServerdemo;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class SelectorThread  extends Thread {
	private Selector selector;
	public  SelectorThread(Selector selector){
			this.selector=selector;
	}
	
	public void run(){
		try {
			  //获取Selector 注册的通道数
			int n=selector.select();
			while(n>0){
				// selector.selectedKeys()可以获取每个注册通道的key
				Iterator<SelectionKey> iterator=selector.selectedKeys().iterator();
				while(iterator.hasNext()){
					SelectionKey key=iterator.next();
					if (key.isReadable()) {
						SocketChannel channel=(SocketChannel)key.channel();
						ByteBuffer buffer =ByteBuffer.allocate(1024);
						channel.read(buffer);
						buffer.flip();
						String receiveMsg=Charset.forName("UTF-8").newDecoder().decode(buffer).toString();
						System.out.println("收到服务器消息:"+receiveMsg+"from:"+channel.getRemoteAddress());
						key.interestOps(SelectionKey.OP_READ);
					}
					//处理下一个事件
					iterator.remove();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
