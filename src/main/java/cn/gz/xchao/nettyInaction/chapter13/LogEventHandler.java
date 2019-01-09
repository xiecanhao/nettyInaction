package cn.gz.xchao.nettyInaction.chapter13;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LogEvent event)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(event.getReceived());
		sb.append(" [");
		sb.append(event.getSource().toString());
		sb.append("] [");
		sb.append(event.getLogfile());
		sb.append("] : ");
		sb.append(event.getMsg());
		System.out.println(sb.toString());
	}

}
