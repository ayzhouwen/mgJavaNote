package com.nio.TcpServerdemo.noPool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class SelectorClient {
		//连接的主机
	private String  host;
	//主机的端口
	private int port;
	//选择器
	private Selector selector;
	//通道
	private SocketChannel socketChannel;
	
	public SelectorClient(String host,int port){
		this.host=host;
		this.port=port;
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void init() throws IOException{
		//打开一个选择器
		selector=Selector.open();
		//打开一个通道
		socketChannel=SocketChannel.open(new InetSocketAddress(host, port));
		//配置为非阻塞模式
		socketChannel.configureBlocking(false);
		//注册到选择器上
		socketChannel.register(selector, SelectionKey.OP_READ);
		//监听来自服务端的响应
		new SelectorThread(selector).start();
	}
	
	public void writeDataToServer(String message) throws IOException{
		ByteBuffer writeBuffer =ByteBuffer.wrap(message.getBytes("UTF-8"));
		socketChannel.write(writeBuffer);
		String[] args={};
		
	}
	
	public static void main(String[] args) throws IOException {
		SelectorClient client=new SelectorClient("192.168.0.130",6666);
		client.writeDataToServer("我是一个客户端"+System.currentTimeMillis());
	}
}
