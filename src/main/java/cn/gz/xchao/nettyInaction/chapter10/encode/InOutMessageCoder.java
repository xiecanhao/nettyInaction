package cn.gz.xchao.nettyInaction.chapter10.encode;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

public class InOutMessageCoder extends MessageToMessageCodec<Integer, String> {

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void decode(ChannelHandlerContext ctx, Integer msg,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub

	}

}
