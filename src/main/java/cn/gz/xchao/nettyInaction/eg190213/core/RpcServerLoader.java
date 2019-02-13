package cn.gz.xchao.nettyInaction.eg190213.core;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class RpcServerLoader {
	private volatile static RpcServerLoader rpcServerLoader;
	private final static String DELIMITER = ":";
	private RpcSerializeProtocol serializeProtocol = RpcSerializeProtovol.JDKSERIALIZE;

	// 方法返回到java虚拟机的可用的处理器数量
	private static final int parallel = Runtime.getRuntime()
			.availableProcessors() * 2;
	// netty nio线程池
	private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(parallel);
	private static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) RpcThreadPool
			.getExecutor(16, -1);
	private MessageSendHandler messageSendHandler = null;

	// 等待netty服务器链路简历通知信号
	private Lock lock = new ReentrantLock();
	private Condition signal = lock.newCondition();

	private RpcServerLoader() {
	}

	// 并发双重锁定
	public static RpcServerLoader getInstance() {
		if (rpcServerLoader == null) {
			synchronized (RpcServerLoader.class) {
				if (rpcServerLoader == null) {
					rpcServerLoader = new RpcServerLoader();
				}
			}
		}
		return rpcServerLoader;
	}

	public void load(String serverAddress,
			RpcSerializeProtocol serializeProtocol) {
		String[] ipAddr = serverAddress.split(RpcServerLoader.DELIMITER);
		if (ipAddr.length == 2) {
			String host = ipAddr[0];
			int port = Integer.parseInt(ipAddr[1]);
			final InetSocketAddress remoteAddr = new InetSocketAddress(host,
					port);

			threadPoolExecutor.submit(new MessageSendInitializeTask(
					eventLoopGroup, remoteAddr, this, serializeProtocol));
		}
	}

	public void setMessageSendHandler(MessageSendHandler messageInHandler) {
		try {
			lock.lock();
			this.messageSendHandler = messageInHandler;
			// 唤醒所有等待客户端RPC线程
			signal.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public MessageSendHandler getMessageSendhandler()
			throws InterruptedException {
		try {
			lock.lock();
			// netty服务端链路没有建立完毕，先挂起等待
			if (messageSendHandler == null) {
				signal.await();
			}
			return messageSendHandler;
		} finally {
			lock.unlock();
		}
	}

	public void unLoad() {
		messageSendHandler.close();
		threadPoolExecutor.shutdown();
		eventLoopGroup.shutdownGracefully();
	}

	public void setSerializeProtovol(RpcSerializeProtovol serializeProtocol) {
		this.serializeProtocol = serializeProtocol;
	}
}
