package cn.gz.xchao.nettyInaction.chapter6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChannelInboundHandlerRelese2
		extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// 会主动释放资源，原因：父类的read方法调用了本方法之后，执行了释放操作

	}

}
