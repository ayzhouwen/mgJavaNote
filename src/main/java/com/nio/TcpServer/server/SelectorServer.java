package com.nio.TcpServer.server;


import com.nio.TcpServer.sessoin.ChannelSessoin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

public class SelectorServer {
		//服务器监听的端口
	private static final int PORT =6666;
	//处理数据的缓冲区
	private ByteBuffer RevBuffer =ByteBuffer.allocate(1024);
	//欢迎消息
	private static final String GREETING = "welcom to Eden";
	private 		int sumPack=0; //测试收到了多少数据包,目前不考虑并发

	public static void main(String[] args) {
		new SelectorServer().start(args);
	}
	
	public void start(String[] args){
		int  port=PORT;
		if (args.length==1) {
			port=Integer.valueOf(args[0]);
		}
		System.out.println("服务器监听端口:"+port);
		Iterator<SelectionKey> iterator = null;
		try {
			//创建一个ServerChannel
			ServerSocketChannel serverChannel =ServerSocketChannel.open();
			//获取通道关联的socket对象
			ServerSocket serverSocket=serverChannel.socket();
			//要绑定的地址
			SocketAddress address=new InetSocketAddress( port);
			//创建需要注册的选择器
			Selector selector =Selector.open();
			//把socket对象绑定到指定的地址
			serverSocket.bind(address);
			//配置为非阻塞模式
			serverChannel.configureBlocking(false);
			//注册通道到选择器
			//第二个参数表名serverChannel 感兴趣的事件是OP_ACCEPT的事件
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);


			//选择器不断循环从从选择器中选取已转备好的通道进行操作
			//选取之后,会对其感兴趣的事件进行处理,将感兴趣的事件
			//处理完毕后将key从集合中删除,表示该通道的事件已经处理完毕
			while(true){
				//这个操作可能会被阻塞,因为不知道注册在这个选择器上通道是否准备好了
				int n=selector.select(0);
				if (n==0) {
					continue;
				}
				
				//获取SelectionKey的迭代器对象
				iterator=selector.selectedKeys().iterator();
				while(iterator.hasNext()){
						//获取这个key关联的通道
						SelectionKey key=iterator.next();
						//判断感兴趣的事件类型
						if (key.isAcceptable()) {
							System.out.println("接受客户端连接:"+System.currentTimeMillis());
							//这里可以强制转换为ServerSocketChannel
							//因为这个选择器上目前只注册了一个类型的通道
							ServerSocketChannel server=(ServerSocketChannel)key.channel();
							//调用accept方法可以得到连接到此地址的客户端连接
							SocketChannel channel =server.accept();
							registerChannel(selector,channel,SelectionKey.OP_READ);


							//registerChannel(selector,channel,SelectionKey.OP_WRITE);
							//给客户端发型响应消息
							sayHello(channel);


						}
						
						//如果是可读类型的事件,则获取传输过来的数据
						if (key.isReadable()&&(key.readyOps() & (SelectionKey.OP_READ))!= 0) {
							ChannelSessoin  cs= (ChannelSessoin) key.attachment();
							cs.doIo(key);
						}

						//写事件一般不监听,即使监听那么也要尽快注销此事件,否则会死循环
//						if (key.isWritable()){
//							interestWrite(key,false);
//						}




						//将已经处理的key从集合总删除,否则下次循环时报错,因为server.accept();返回null
						iterator.remove();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			iterator.remove();
		}
		
	}

	//读取通道数据,未进行解码,会出现粘包问题
	public void readData(SelectionKey key) throws Exception{
				//获取key管理的channel对象
				SocketChannel channel=(SocketChannel) key.channel();
				//读取之前要清空缓冲区
				RevBuffer.clear();
					while (channel.read(RevBuffer)>0){
						RevBuffer.flip();
						String receiveMsg=Charset.forName("UTF-8").newDecoder().decode(RevBuffer).toString();
						System.out.println("接收到客户端消息:"+receiveMsg+" from:"+channel.getRemoteAddress());
						RevBuffer.clear();
					}
	}



	
	//向客户端发送响应消息
	private void sayHello(SocketChannel channel) throws IOException{
		RevBuffer.clear();
		RevBuffer.put(GREETING.getBytes());
		RevBuffer.flip();
		while(RevBuffer.hasRemaining()) {
			channel.write(RevBuffer);
		}



	}
	
	//注册客户端连接到选择器上
	private void  registerChannel(Selector selector ,SocketChannel channel,int opRead) throws IOException{
		if (channel==null) {
			return;
		}
		//设为非阻塞模式
		channel.configureBlocking(false);
		//注册该channel到选择器上
		SelectionKey key=		channel.register(selector, opRead);

		ChannelSessoin cs = new ChannelSessoin(channel, key);
		key.attach(cs);
	}

	//设置通道写事件是否感兴趣
	public  void interestWrite(SelectionKey key,boolean isInterest){
		int newInterest=key.interestOps();
		if (isInterest){
			newInterest|=SelectionKey.OP_WRITE;
		}else {
			newInterest &=~SelectionKey.OP_WRITE;
		}
		key.interestOps(newInterest);


	}
}
