package cn.gz.xchao.nettyInaction.chapter9;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class FixedLengthFrameDecoderTest {
	public static void main(String[] args) {
		new FixedLengthFrameDecoderTest().testFramesDecoded2();
	}

	@Test
	public void testFramesDecoded() {
		ByteBuf buf = Unpooled.buffer();
		for (int i = 0; i < 9; i++) {
			buf.writeByte(i);
		}
		ByteBuf input = buf.duplicate();
		EmbeddedChannel channel = new EmbeddedChannel(
				new FixedLengthFrameDecoder(3));
		// write bytes
		assertTrue(channel.writeInbound(input.retain()));
		assertTrue(channel.finish());

		// read messages
		ByteBuf read = channel.readInbound();
		assertEquals(buf.readSlice(3), read);
		read.release();

		read = channel.readInbound();
		assertEquals(buf.readSlice(3), read);
		read.release();

		read = channel.readInbound();
		assertEquals(buf.readSlice(3), read);
		read.release();

		assertNull(channel.readInbound());
		buf.release();
	}

	@Test
	public void testFramesDecoded2() {
		ByteBuf buf = Unpooled.buffer();
		for (int i = 0; i < 9; i++) {
			buf.writeByte(i);
		}
		ByteBuf input = buf.duplicate();

		EmbeddedChannel channel = new EmbeddedChannel(
				new FixedLengthFrameDecoder(3));
		assertFalse(channel.writeInbound(input.readBytes(2)));
		assertTrue(channel.writeInbound(input.readBytes(7)));

		assertTrue(channel.finish());
		ByteBuf read = channel.readInbound();
		for (int i = 0; i < read.capacity(); i++) {
			byte b = read.getByte(i);
			System.out.print(b);
		}
		System.out.println();
		assertEquals(buf.readSlice(3), read);
		read.release();

		read = channel.readInbound();
		for (int i = 0; i < read.capacity(); i++) {
			byte b = read.getByte(i);
			System.out.print(b);
		}
		System.out.println();
		assertEquals(buf.readSlice(3), read);
		read.release();

		read = channel.readInbound();
		for (int i = 0; i < read.capacity(); i++) {
			byte b = read.getByte(i);
			System.out.print(b);
		}
		System.out.println();
		assertEquals(buf.readSlice(3), read);
		read.release();

		assertNull(channel.readInbound());
		buf.release();
	}
}
