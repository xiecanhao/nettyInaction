package cn.gz.xchao.nettyInaction.chapter10.decode;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 将收到的字节，按4个字节解码为一个int值
 * 
 * @since 2018年12月27日 下午2:55:56
 * @author xchao
 */
public class ToIntegerDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		if (in.readableBytes() >= 4) {
			out.add(in.readInt());
		}

	}

}
