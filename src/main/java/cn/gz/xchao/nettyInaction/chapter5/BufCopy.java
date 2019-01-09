package cn.gz.xchao.nettyInaction.chapter5;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class BufCopy {
	public static void main(String[] args) {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
		ByteBuf copy = buf.copy(0, 15);
		System.out.println(copy.toString(utf8));
		buf.setByte(0, (byte) 'J');
		assert buf.getByte(0) != copy.getByte(0);
	}
}
