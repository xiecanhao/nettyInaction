package cn.gz.xchao.nettyInaction.chapter6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

public class ChannelOutboundHandlerTest2 extends ChannelOutboundHandlerAdapter {

	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		ReferenceCountUtil.release(msg);// 释放数据占用的资源
		promise.setSuccess();// 通知promise数据已经被处理
	}

}
