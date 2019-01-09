package cn.gz.xchao.nettyInaction.chapter10.encode;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class IntegerToStringEncoder extends MessageToMessageEncoder<Integer> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Integer msg,
			List<Object> out) throws Exception {
		out.add(String.valueOf(msg));
	}

}
