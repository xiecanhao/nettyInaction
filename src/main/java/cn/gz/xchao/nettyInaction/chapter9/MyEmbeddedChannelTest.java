package cn.gz.xchao.nettyInaction.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;

public class MyEmbeddedChannelTest {
	public static void main(String[] args) {
		ByteBuf buf = null;
		// buf = Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8);
		buf = Unpooled.copiedBuffer("测试通道。", CharsetUtil.UTF_8);
		EmbeddedChannel channel = new EmbeddedChannel(
				new FixedLengthFrameDecoder(3));
		channel.writeInbound(buf);
		ByteBuf read = null;
		while ((read = channel.readInbound()) != null) {
			System.out.println(read.toString(CharsetUtil.UTF_8));
		}
	}
}
