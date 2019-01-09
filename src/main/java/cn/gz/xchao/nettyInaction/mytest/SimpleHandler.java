package cn.gz.xchao.nettyInaction.mytest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SimpleHandler extends SimpleChannelInboundHandler<Data> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Data msg)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
