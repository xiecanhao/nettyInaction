package cn.gz.xchao.nettyInaction.chapter10.decode;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

public class SafeByteToMessageDecoder extends ByteToMessageDecoder {

	/** 最大字节控制 */
	private static final int MAX_SIZE = 1024;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		int readable = in.readableBytes();
		if (readable > MAX_SIZE) {
			in.skipBytes(readable);
			throw new TooLongFrameException("frame to big!");
		}

	}

}
