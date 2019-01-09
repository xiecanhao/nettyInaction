package cn.gz.xchao.nettyInaction.chapter13;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class LogEventBroadcaster {
	private final EventLoopGroup group;
	private final Bootstrap bootstrap;
	private final File file;

	public LogEventBroadcaster(InetSocketAddress address, File file) {
		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(group).channel(NioDatagramChannel.class)
				.option(ChannelOption.SO_BROADCAST, true)
				.handler(new LogEventEncoder(address));
		this.file = file;
	}

	public void run() throws Exception {
		Channel ch = bootstrap.bind(0).sync().channel();
		long pointer = 0;
		for (;;) {
			long len = file.length();
			// System.out.println("run");
			if (len < pointer) {
				// file was reset
				pointer = len;
			} else if (len > pointer) {
				// content was add
				RandomAccessFile raf = new RandomAccessFile(file, "r");
				raf.seek(pointer);
				String line;
				while ((line = raf.readLine()) != null) {
					ch.writeAndFlush(new LogEvent(null, -1,
							file.getAbsolutePath(), line));
				}
				pointer = raf.getFilePointer();
				raf.close();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.interrupted();
				break;
			}
		}
	}

	public void stop() {
		group.shutdownGracefully();
	}

	public static void main(String[] args) throws Exception {
		// if (args.length != 2) {
		// throw new IllegalArgumentException();
		// }
		args = new String[2];
		args[0] = "20001";
		args[1] = "D:\\aaa.txt";
		LogEventBroadcaster broadcaster = new LogEventBroadcaster(
				new InetSocketAddress("255.255.255.255",
						Integer.parseInt(args[0])),
				new File(args[1]));
		try {
			broadcaster.run();
		} finally {
			broadcaster.stop();
		}
	}
}
