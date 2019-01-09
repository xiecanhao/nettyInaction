package cn.gz.xchao.nettyInaction.chapter10.encode;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

/**
 * 复合编码、解码器，在encode写上编码操作，在decode写上解码操作
 * 
 * 
 * @since 2018年12月27日 下午4:06:40
 * @author xchao
 */
public class InOutCoder extends ByteToMessageCodec<ByteBuf> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out)
			throws Exception {
		// TODO 编码操作

	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// TODO 解码操作

	}

}
