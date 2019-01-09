package cn.gz.xchao.nettyInaction.chapter8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 服务端收到连接之后，再为此连接建立新的客户端连接
 * 
 * @since 2018年12月26日 下午5:23:30
 * @author xchao
 */
public class CSCBootstrapTest {
	public static void main(String[] args) {
		final EventLoopGroup g = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(g).channel(NioServerSocketChannel.class)
				.childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
					ChannelFuture connectFuture = null;

					@Override
					public void channelActive(ChannelHandlerContext ctx)
							throws Exception {
						// TODO 建立客户端连接，连接到其他第三方服务端
						Bootstrap cb = new Bootstrap();
						cb.channel(NioSocketChannel.class).handler(
								new SimpleChannelInboundHandler<ByteBuf>() {

									@Override
									protected void channelRead0(
											ChannelHandlerContext ctx,
											ByteBuf msg) throws Exception {
										System.out.println("received data.");

									}
								});
						super.channelActive(ctx);
						// TODO 与当前的服务端共享一个group
						connectFuture = cb.group(ctx.channel().eventLoop())
								.connect(new InetSocketAddress("127.0.0.1",
										20000));
					}

					@Override
					protected void channelRead0(ChannelHandlerContext ctx,
							ByteBuf msg) throws Exception {
						if (connectFuture.isDone()) {
							System.out.println("connect other...");
						}
					}
				});
		ChannelFuture f = b.bind(new InetSocketAddress(10000));
		f.addListener(new ChannelFutureListener() {

			public void operationComplete(ChannelFuture future)
					throws Exception {
				if (future.isSuccess()) {
					System.out.println("server bind success.");
				} else {
					System.err.println("server bind failed!");
					future.cause().printStackTrace();
				}
			}
		});
	}
}
