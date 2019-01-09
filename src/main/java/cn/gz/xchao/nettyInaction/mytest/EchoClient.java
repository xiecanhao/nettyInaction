package cn.gz.xchao.nettyInaction.mytest;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	private final String host;
	private final int port;

	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
		String host = "127.0.0.1";
		int port = 18888;
		new EchoClient(host, port).start();
	}

	private void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			// 指定EventLoopGroup，以处理客户端事件；需要适用于NIO的实现
			b.group(group).channel(NioSocketChannel.class)// 用于NIO传输的Channel类型
					.remoteAddress(new InetSocketAddress(host, port))
					.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(new EchoClientHandler());
						}

					});
			// 连接到远程节点，阻塞等待直到连接完成
			ChannelFuture f = b.connect().sync();
			// 阻塞，直到 Channel关闭
			f.channel().closeFuture().sync();
		} finally {
			// 关闭线程池，且释放所有的资源
			group.shutdownGracefully().sync();
		}
	}
}
