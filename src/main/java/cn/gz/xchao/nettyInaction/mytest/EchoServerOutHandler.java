package cn.gz.xchao.nettyInaction.mytest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class EchoServerOutHandler extends ChannelOutboundHandlerAdapter {

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		System.out.println("EchoServerOutHandler read");
		super.read(ctx);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		System.out.println("EchoServerOutHandler write");
		super.write(ctx, msg, promise);
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		System.out.println("EchoServerOutHandler flush");
		super.flush(ctx);
	}

}
