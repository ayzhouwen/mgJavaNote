package com.nio.TcpServer.client;

import com.nio.TcpServer.encode.PacketEncode;

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
		//socketChannel.socket().setSendBufferSize(1024*1024*10*10);
		new SelectorClientThread(selector).start();
	}
	//单次发包
	public void writeDataToServer(String message) throws IOException{
		ByteBuffer writeBuffer =ByteBuffer.wrap(message.getBytes("UTF-8"));
		socketChannel.write(writeBuffer);
		String[] args={};
		
	}


	//测试多次发包,并对数据进行编码后在发送到服务端
	public void manyEncodeWrite() throws IOException{
		int num=10000;
		String newStr;
		for (int i=0;i<num;i++){
			newStr=getLongStr().toString();//"第"+i+"次发送";
			ByteBuffer bf= PacketEncode.HeadBodyEncode(newStr);

			//主要大数据发送要根据返回值循环发送
			while(bf.hasRemaining()){
              int r=   socketChannel.write(bf);
                System.out.println("本次发送"+r+"个字节");
            }

		}
	}

	//模拟大数据
	public StringBuilder getLongStr(){
		StringBuilder sb=new StringBuilder();
		//sb.append("开始");
		for (int i=0;i<10000000;i++){
			sb.append("呵");
		}

		//sb.append("结束");
		return sb;
	}
	
	public static void main(String[] args) throws IOException {
		SelectorClient client=new SelectorClient("192.168.2.184",6666);
	//SelectorClient client=new SelectorClient("192.168.199.238",6666);
		//client.manyWrite("我是一个客户端");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		client.manyEncodeWrite();
	}
}
