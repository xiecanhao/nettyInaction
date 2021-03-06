package cn.gz.xchao.nettyInaction.mytest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf in = (ByteBuf) msg;
		System.out.println("Server received:" + in.toString(CharsetUtil.UTF_8));
		// 调用下个channelhandle的channelRead方法
		ctx.fireChannelRead(msg);
		// 将收到的消息写给发送者，不冲刷出站消息
		// ctx.write(in);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
			throws Exception {
		System.out.println("Server channelReadComplete");
		ctx.fireChannelReadComplete();
		// 将未决消息冲刷到远程节点，并关闭该Channel
		// ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
		// .addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// 打印异常栈跟踪
		cause.printStackTrace();
		// 关闭channel
		ctx.close();
	}

}
