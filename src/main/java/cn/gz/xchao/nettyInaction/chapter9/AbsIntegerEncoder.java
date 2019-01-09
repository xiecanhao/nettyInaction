package cn.gz.xchao.nettyInaction.chapter9;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) throws Exception {
		while (msg.readableBytes() >= 4) {
			// 读取一个int值，并取绝对值
			int value = msg.readInt();
			out.add(Math.abs(value));
			System.out.println("value:" + value);
		}

	}

}
