package cn.gz.xchao.nettyInaction.mytest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class EchoServerOutHandler2 extends ChannelOutboundHandlerAdapter {
	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		System.out.println("EchoServerOutHandler2 read");
		super.read(ctx);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		System.out.println("EchoServerOutHandler2 write");
		super.write(ctx, msg, promise);
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		System.out.println("EchoServerOutHandler2 flush");
		super.flush(ctx);
	}
}
