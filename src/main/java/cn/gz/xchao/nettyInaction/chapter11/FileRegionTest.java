package cn.gz.xchao.nettyInaction.chapter11;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;

public class FileRegionTest {
	public static void main(String[] args) throws FileNotFoundException {
		File file = null;
		FileInputStream in = new FileInputStream(file);
		FileRegion fr = new DefaultFileRegion(in.getChannel(), 0,
				file.length());
		Channel channel = null;
		channel.writeAndFlush(fr).addListener(new ChannelFutureListener() {

			public void operationComplete(ChannelFuture future)
					throws Exception {
				if (future.isSuccess()) {
					// TODO Auto-generated method stub

				}

			}
		});
	}
}
