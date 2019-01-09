package cn.gz.xchao.nettyInaction.chapter6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ResourceLeakDetector;

@Sharable
public class ChannelOutboundMethodTest extends ChannelOutboundHandlerAdapter {

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		super.close(ctx, promise);
		// 设置内存泄漏时，打印日志
		ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.SIMPLE);
	}

}
