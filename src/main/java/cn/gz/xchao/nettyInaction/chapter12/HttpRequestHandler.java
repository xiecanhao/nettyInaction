package cn.gz.xchao.nettyInaction.chapter12;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

public class HttpRequestHandler
		extends SimpleChannelInboundHandler<FullHttpRequest> {

	private final String wsUri;
	private static final File INDEX;

	static {
		URL location = HttpRequestHandler.class.getProtectionDomain()
				.getCodeSource().getLocation();
		try {
			String path = location.toURI() + "index.html";
			path = !path.contains("file:") ? path : path.substring(5);
			INDEX = new File(path);
		} catch (Exception e) {
			throw new IllegalStateException("Unable to locate index.html", e);
		}
	}

	public HttpRequestHandler(String wsUri) {
		this.wsUri = wsUri;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
			FullHttpRequest request) throws Exception {
		if (wsUri.equalsIgnoreCase(request.getUri())) {
			// 如果请求了Websocket协议升级，则增加引用计数，并传递给下一个channelInboundHandler
			ctx.fireChannelRead(request.retain());
		} else {
			// 处理100Continue请求以符合http1.1规范
			if (HttpHeaders.is100ContinueExpected(request)) {
				send100Continue(ctx);
			}
			// 读取index.html
			RandomAccessFile file = new RandomAccessFile(INDEX, "r");
			HttpResponse response = new DefaultHttpResponse(
					request.getProtocolVersion(), HttpResponseStatus.OK);
			response.headers().set(HttpHeaders.Names.CONTENT_TYPE,
					"text/plain; charset=UTF-8");
			boolean keepAlive = HttpHeaders.isKeepAlive(request);
			// 如果请求了keep-alive，则添加所需要的http头信息
			if (keepAlive) {
				response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,
						file.length());
				response.headers().set(HttpHeaders.Names.CONNECTION,
						HttpHeaders.Values.KEEP_ALIVE);
			}
			// 将httpResponse写到客户端
			ctx.write(response);
			// 将index.html写到客户端
			if (ctx.pipeline().get(SslHandler.class) == null) {
				ctx.write(new DefaultFileRegion(file.getChannel(), 0,
						file.length()));
			} else {
				ctx.write(new ChunkedNioFile(file.getChannel()));
			}
			// 写lastHttpContent并冲刷到客户端
			ChannelFuture future = ctx
					.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
			// 如果没请求keep-alive，则请求完之后关闭channel
			if (!keepAlive) {
				future.addListener(ChannelFutureListener.CLOSE);
			}
		}

	}

	private static void send100Continue(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
		ctx.writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
