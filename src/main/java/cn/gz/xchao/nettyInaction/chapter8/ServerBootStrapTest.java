package cn.gz.xchao.nettyInaction.chapter8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServerBootStrapTest {
	public static void main(String[] args) {
		EventLoopGroup g = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(g).channel(NioServerSocketChannel.class)
				.childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
					@Override
					protected void channelRead0(ChannelHandlerContext ctx,
							ByteBuf msg) throws Exception {
						System.out.println("received data.");
					}
				});
		ChannelFuture f = b.bind(new InetSocketAddress(10000));
		f.addListener(new ChannelFutureListener() {

			public void operationComplete(ChannelFuture future)
					throws Exception {
				if (future.isSuccess()) {
					System.out.println("bind success.");
				} else {
					System.err.println("bind failed!");
					future.cause().printStackTrace();
				}

			}
		});
	}
}
