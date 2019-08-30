package com.titans.serialport.manage;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.titans.serialport.costant.SysParamConst;
import com.titans.serialport.utils.MyUtils;

public class SocketManager {
	private static Logger logger = Logger.getLogger(SocketManager.class);
	public Queue<String> queue = new ConcurrentLinkedQueue<String>();
	private static boolean finished = false;
	ServerSocketThread serverThread = null ;
	public void openServer(int port, SocketManager socketManager) {
		ServerSocket serverSocket;
		try {
			logger.debug("打开本地端口为"+port+"的网络监听服务");
			serverSocket = new ServerSocket(port);
			serverThread = new ServerSocketThread(serverSocket, socketManager);
			serverThread.start() ;
		} catch (IOException e) {
			logger.debug("打开本地端口为"+port+"的服务失败,端口被占用");
			e.printStackTrace();
		}
	}

	public void stopThread() {
		finished = true;
		serverThread.stopSocketServer(); 
	}
	public void startThread(){
		finished  = false ;
	}

	private class ServerSocketThread extends Thread {
		SocketManager socketManager = null;
		ServerSocket serverSocket = null;

		public ServerSocketThread(ServerSocket serverSocket, SocketManager socketManager) {
			this.serverSocket = serverSocket;
			this.socketManager = socketManager;
		}
		public void stopSocketServer(){
			try {
				if (serverSocket != null) {
					serverSocket.close();
					serverSocket = null;
					System.out.println("serverSocket");
				}
			}catch (IOException e) {
					
					e.printStackTrace();
			}
		}
		public void run() {
			// 创建绑定到特定端口的服务器Socket。
			Socket socket = null;// 需要接收的客户端Socket
			int count = 0;// 记录客户端数量
			SocketThread thread = null;
			try {
				while (!finished) {
						socket = serverSocket.accept();// 侦听并接受到此套接字的连接
						// 获取客户端的连接
						thread = new SocketThread(socket, socketManager);// 自己创建的线程类
						thread.start();// 启动线程
						count++;// 如果正确建立连接
						logger.debug("已有"+count+"个客户端建立链接");
						System.out.println("客户端数量：" + count);// 打印客户端数量
				}
			} catch (IOException e) {
				logger.debug("客户端建立链接失败或已关闭");
				return ;
			} finally {
				try {
					if (serverSocket != null) {
						serverSocket.close();
						serverSocket = null;
						System.out.println("serverSocket");
					}
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}

		}
	}

	private class SocketThread extends Thread {
		Socket socket = null;
		SocketManager socketManager = null;
		

		public SocketThread( Socket socket, SocketManager socketManager) {
			this.socket = socket;
			this.socketManager = socketManager;
		}

		@Override
		public void run() {
			InputStream in = null;
			try {
				in = socket.getInputStream();
				byte[] readBuffer = new byte[1024];
				int bytesNum = in.read(readBuffer);
				while (bytesNum > 0) {
					String hexstr = MyUtils.byteArray2HexString(readBuffer, bytesNum, true);
					MyUtils.regAllText(socketManager.queue, hexstr, SysParamConst.PROTOCOL_NET) ;
					bytesNum = in.read(readBuffer);
					if (finished) {
						break;
					}
				}

			} catch (Exception e) {
				logger.debug("读取客户端数据失败可能连接已经断开");
				logger.debug(e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (in != null) {
						in.close();
						System.out.println("in");
						in = null;
					}
					if (socket != null) {
						socket.close();
						System.out.println("socket");
						socket = null;
					}
				} catch (IOException e) {
					logger.debug("关闭客户端链接失败");
					e.printStackTrace();
				}
			}

		}
	}
}
