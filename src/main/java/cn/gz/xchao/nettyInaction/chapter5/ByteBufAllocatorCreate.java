package cn.gz.xchao.nettyInaction.chapter5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class ByteBufAllocatorCreate {
	public static void main(String[] args) {
		ByteBufAllocator bba = null;

		// channel创建
		Channel channel = null;
		bba = channel.alloc();

		// ctx创建
		ChannelHandlerContext ctx = null;
		bba = ctx.alloc();

		bba = PooledByteBufAllocator.DEFAULT;

		bba = UnpooledByteBufAllocator.DEFAULT;

		ByteBuf buf = bba.buffer();// 返回一个基于堆/直接内存的buf
		buf = bba.heapBuffer();// 返回一个基于堆的buf
		buf = bba.directBuffer();// 返回一个基于直接内存的buf
		buf = bba.compositeBuffer();// 返回一个基于堆/直接内存的CompositeByteBuf
		System.out.println(buf.refCnt());// 返回buf的引用数

		ByteBufUtil.hexDump(buf);// 将一个buf的内容，用16进制打印出来
		ByteBufUtil.equals(buf, buf);// 对比两个buf是否相等
	}
}
