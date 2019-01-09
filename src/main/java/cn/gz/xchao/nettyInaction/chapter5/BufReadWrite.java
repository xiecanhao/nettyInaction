package cn.gz.xchao.nettyInaction.chapter5;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;

public class BufReadWrite {
	public static void main(String[] args) {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
		System.out.println("capacity:" + buf.capacity());
		System.out.println(buf.readableBytes());
		System.out.println((char) buf.readByte());
		System.out.println(buf.readableBytes());
		System.out.println("maxCapacity:" + buf.maxCapacity());
		int readerIndex = buf.readerIndex();
		int writerIndex = buf.writerIndex();
		buf.writeByte((byte) '?');
		System.out.println(buf.toString(utf8));
		assert readerIndex == buf.readerIndex();
		assert writerIndex != buf.writerIndex();
		System.out.println(buf.refCnt());

		// 释放资源
		ReferenceCountUtil.release(buf);
	}
}
