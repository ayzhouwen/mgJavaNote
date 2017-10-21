package com.nio.TcpServerdemo.pool;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class SelectorClientThread extends Thread {
	private Selector selector;
	public SelectorClientThread(Selector selector){
			this.selector=selector;
	}
	
	public void run(){
		Iterator<SelectionKey> iterator=null;
		try {
			  //获取Selector 注册的通道数

			while(true){
				int n=selector.select();
				if (n==0) {
					continue;
				}
				// selector.selectedKeys()可以获取每个注册通道的key
				 iterator=selector.selectedKeys().iterator();
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

					if (key.isWritable()){
						System.out.println("写入事件");
					}

					if (key.isConnectable()){
						System.out.println("连接事件");
					}
					//处理下一个事件
					iterator.remove();
				}
				
			}
		} catch (Exception e) {
			iterator.remove();
			e.printStackTrace();
		}
	}
}
