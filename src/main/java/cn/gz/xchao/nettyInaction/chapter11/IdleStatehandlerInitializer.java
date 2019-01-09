package cn.gz.xchao.nettyInaction.chapter11;

import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

/**
 * 心跳检测
 * 
 * @since 2018年10月25日 下午3:36:13
 * @author xchao
 */
public class IdleStatehandlerInitializer extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// 该handler连接空闲时间太长，就会发出一个IdleStateEvent时间，可以重写handler的userEventTriggered方法来处理该事件
		pipeline.addLast(new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS));
		pipeline.addLast(new HeartbeatHandler());
	}

	public static final class HeartbeatHandler
			extends ChannelInboundHandlerAdapter {
		private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled
				.unreleasableBuffer(Unpooled.copiedBuffer("HEARTBEAT",
						CharsetUtil.ISO_8859_1));

		@Override
		public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
				throws Exception {
			if (evt instanceof IdleStateEvent) {
				ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
						.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
			} else {
				super.userEventTriggered(ctx, evt);
			}
		}

	}

}
