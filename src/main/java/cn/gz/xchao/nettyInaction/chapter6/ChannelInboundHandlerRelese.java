package cn.gz.xchao.nettyInaction.chapter6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ChannelInboundHandlerRelese extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// 当某个 ChannelInboundHandler 的实现重写 channelRead()方法时，它将负责显式地释放与池化的
		// ByteBuf 实例相关的内存。
		super.channelRead(ctx, msg);
		// 丢弃已接收的信息
		ReferenceCountUtil.release(msg);
	}

}
