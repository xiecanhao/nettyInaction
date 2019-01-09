package cn.gz.xchao.nettyInaction.chapter4;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;

public class ChannelSafe {
	public void checkSafe() {
		final Channel channel = null;
		final ByteBuf buf = Unpooled
				.copiedBuffer("your data", CharsetUtil.UTF_8).retain();
		Runnable writer = new Runnable() {
			public void run() {
				channel.writeAndFlush(buf.duplicate());
			}
		};
		Executor executor = Executors.newCachedThreadPool();
		// 提交给不同的线程也没关系
		executor.execute(writer);
		// executor2.execute(writer);
	}
}
