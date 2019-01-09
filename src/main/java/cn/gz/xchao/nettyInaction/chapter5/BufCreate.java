package cn.gz.xchao.nettyInaction.chapter5;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class BufCreate {
	public static void main(String[] args) {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
		// buf切片
		ByteBuf sliced = buf.slice(0, 15);
		System.out.println(sliced.toString(utf8));
		buf.setByte(0, (byte) 'J');
		assert buf.getByte(0) == sliced.getByte(0);
	}

	public static void bufCreateWay() {
		Charset utf8 = Charset.forName("UTF-8");
		// 下列返回的buf是一个引用，拥有自己的索引readIndex和writeIndex，但与原来的buf共享数据空间
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
		buf.duplicate();// 返回当前buf的拷贝
		buf.slice();//
		buf.slice(0, 15);// 返回当前buf的一个部分数据拷贝
		Unpooled.unmodifiableBuffer(buf);
		buf.order(ByteOrder.nativeOrder());
		buf.readSlice(15);

		buf = Unpooled.buffer(16);
		buf = Unpooled.directBuffer(16);
		buf = Unpooled.compositeBuffer(16);
		buf = Unpooled.wrappedBuffer(buf);
	}
}
