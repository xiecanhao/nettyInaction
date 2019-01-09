package cn.gz.xchao.nettyInaction.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class AbsIntegerEncoderTest {
	public static void main(String[] args) {
		ByteBuf buf = Unpooled.buffer();
		for (int i = 0; i < 10; i++) {
			buf.writeInt(-1 * i);
		}
		EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
		channel.writeOutbound(buf);
		channel.flush();
		for (int i = 0; i < 10; i++) {
			System.out.println(channel.readOutbound());
		}
		// Integer num = null;
		// while ((num = channel.readOutbound()) != null) {
		// System.out.print(num);
		// }
		// System.out.println();
		// System.out.println(channel.readOutbound());
	}
}
