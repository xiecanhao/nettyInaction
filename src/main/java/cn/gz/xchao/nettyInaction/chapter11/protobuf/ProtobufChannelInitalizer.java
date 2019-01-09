package cn.gz.xchao.nettyInaction.chapter11.protobuf;

import com.google.protobuf.MessageLite;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

public class ProtobufChannelInitalizer extends ChannelInitializer<Channel> {
	private final MessageLite lite;

	public ProtobufChannelInitalizer(MessageLite lite) {
		super();
		this.lite = lite;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new ProtobufVarint32FrameDecoder());
		pipeline.addLast(new ProtobufEncoder());
		pipeline.addLast(new ProtobufDecoder(lite));
		pipeline.addLast(new ObjectHandler());
	}

	public static final class ObjectHandler
			extends SimpleChannelInboundHandler<Object> {

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			// TODO Auto-generated method stub

		}

	}

}
