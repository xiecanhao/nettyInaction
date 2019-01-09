package cn.gz.xchao.nettyInaction.chapter8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

public class TransportWithAttrTest {
	public static void main(String[] args) {
		final AttributeKey<Integer> id = AttributeKey.newInstance("ID");
		EventLoopGroup g = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(g).channel(NioSocketChannel.class)
				.handler(new SimpleChannelInboundHandler<ByteBuf>() {

					@Override
					public void channelRegistered(ChannelHandlerContext ctx)
							throws Exception {
						Integer idValue = ctx.channel().attr(id).get();
						System.out.println(idValue);
					}

					@Override
					protected void channelRead0(ChannelHandlerContext ctx,
							ByteBuf msg) throws Exception {
						System.out.println("received data.");
					}

				});
		// TODO 在connect之前设置参数,设置到已创建的channel上
		b.option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
		b.attr(id, 123456);
		ChannelFuture f = b.connect(new InetSocketAddress("127.0.0.1", 10000));
		f.syncUninterruptibly();
	}
}
