package cn.gz.xchao.nettyInaction.chapter8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class BootStrapTest {
	public static void main(String[] args) {
		EventLoopGroup g = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(g).channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch)
							throws Exception {
						ch.pipeline()
								.addFirst(new ChannelInboundHandlerAdapter() {
									@Override
									public void channelActive(
											ChannelHandlerContext ctx)
											throws Exception {
										System.out.println("channel activity!");
									}

								});
					}
				});
		ChannelFuture f = b.connect(new InetSocketAddress("127.0.0.1", 1000));
		try {
			f.channel().closeFuture().sync();
			f.syncUninterruptibly();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			g.shutdownGracefully();
		}
	}
}
