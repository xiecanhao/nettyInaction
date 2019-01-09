package cn.gz.xchao.nettyInaction.chapter4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.CharsetUtil;

/**
 * 增加写操作异步监听器
 * 
 * @since 2018年12月12日 下午4:22:32
 * @author xchao
 */
public class ChannelWriteListener {
	public void addWriteListener() {
		Channel channel = null;
		ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
		ChannelFuture cf = channel.writeAndFlush(buf);
		cf.addListener(new ChannelFutureListener() {

			public void operationComplete(ChannelFuture future)
					throws Exception {
				if (future.isSuccess()) {
					System.out.println("Write successful");
				} else {
					System.err.println("Write error");
					future.cause().printStackTrace();
				}
			}
		});
	}
}
