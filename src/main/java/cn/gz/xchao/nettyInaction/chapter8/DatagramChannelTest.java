package cn.gz.xchao.nettyInaction.chapter8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.oio.OioSocketChannel;

public class DatagramChannelTest {
	public static void main(String[] args) {
		EventLoopGroup g = new OioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(g).channel(OioSocketChannel.class)
				.handler(new SimpleChannelInboundHandler<ByteBuf>() {

					@Override
					protected void channelRead0(ChannelHandlerContext ctx,
							ByteBuf msg) throws Exception {
						System.out.println("received data.");

					}
				});
		// TODO 调用bind方法，应为是面向无连接的
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
		try {
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO 无论如何，都要关闭group
			g.shutdownGracefully();

		}
	}
}
