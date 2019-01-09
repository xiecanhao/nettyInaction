package cn.gz.xchao.nettyInaction.chapter6;

import java.util.List;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

public class ChannelPipelineTest {
	public static void main(String[] args) {
		ChannelPipeline pipeline = null;
		ChannelHandler first = null;
		ChannelHandler second = null;
		ChannelHandler third = null;
		// handler的增加、删除、替换
		pipeline.addLast("handle1", first);
		pipeline.addLast("handle2", second);
		pipeline.addLast("handle3", third);

		pipeline.remove("handle1");
		pipeline.remove(second);
		pipeline.replace("handle3", "handle4", null);

		// 获取handler
		ChannelHandler hd = pipeline.get("handle4");
		ChannelHandlerContext ctx = pipeline.context("handler4");
		List<String> names = pipeline.names();

	}
}
