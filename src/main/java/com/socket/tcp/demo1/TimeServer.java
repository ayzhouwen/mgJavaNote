package com.socket.tcp.demo1;

import cn.hutool.core.convert.Convert;
import com.util.ConfigUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
/**
 * 注意客户端消息里边要包含回车才有有消息回应
 */
public class TimeServer {
	private static final int MAX_THREADS = 2048;
	private static ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			int port = Convert.toInt(ConfigUtil.getConfigValue("tcpServerPort"));
			String bindIp = Convert.toStr(ConfigUtil.getConfigValue("tcpServerIp"));
			InetAddress bindAddress = InetAddress.getByName(bindIp);

			serverSocket = new ServerSocket(port, 2048, bindAddress);
			log.info("服务启动成功 - 监听端口: {}", port);
			log.info("绑定地址: {}", serverSocket.getInetAddress());

			// 添加关闭钩子
			ServerSocket finalServerSocket = serverSocket;
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				try {
					if (finalServerSocket != null && !finalServerSocket.isClosed()) {
						finalServerSocket.close();
						log.info("服务器socket已关闭");
					}
					threadPool.shutdown();
					log.info("线程池已关闭");
				} catch (IOException e) {
					log.error("关闭资源异常", e);
				}
			}));

			while (!Thread.currentThread().isInterrupted()) {
				try {
					Socket clientSocket = serverSocket.accept();
					log.info("接受客户端连接: {}", clientSocket.getRemoteSocketAddress());

					threadPool.execute(new TimeServerHandler(clientSocket));

				} catch (IOException e) {
					if (!serverSocket.isClosed()) {
						log.error("接受客户端连接失败", e);
					}
				}
			}

		} catch (Exception e) {
			log.error("服务器启动失败", e);
		} finally {
			try {
				if (serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				log.error("关闭serverSocket失败", e);
			}
		}
	}
}