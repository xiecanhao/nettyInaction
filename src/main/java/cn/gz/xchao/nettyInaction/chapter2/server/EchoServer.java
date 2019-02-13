package cn.gz.xchao.nettyInaction.chapter2.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
	private final int port;

	public EchoServer(int port) {
		super();
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
		// if (args.length != 1) {
		// System.err.println(
		// "Usage:" + EchoServer.class.getSimpleName() + "<port>");
		// return;
		// }
		// int port = Integer.parseInt(args[0]);
		int port = 8888;
		new EchoServer(port).start();
	}

	private void start() throws Exception {
		final EchoServerHandler serverhandler = new EchoServerHandler();
		// 创建EventLoopGroup
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			// 创建ServerBootStrap
			ServerBootstrap b = new ServerBootstrap();
			b.group(group).channel(NioServerSocketChannel.class)// 指定传输的channel
					.localAddress(new InetSocketAddress(port))
					// 添加一个EchoServerHandle到子Channel的ChannelPipeline
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(serverhandler);
						}

					});
			// 异步地绑定服务器；调用sync()方法阻塞等待到绑定完成
			ChannelFuture f = b.bind().sync();
			// 获取Channel的CloseFuture，并且阻塞当前线程直到它完成
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}
}
