package cn.gz.xchao.nettyInaction.chapter7;

import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.util.concurrent.ScheduledFuture;

public class EventLoopDispatchTest {
	public static void main(String[] args) {
		Channel ch = null;
		ScheduledFuture<?> ft = ch.eventLoop().schedule(new Runnable() {
			public void run() {
				System.out.println("绑定后60秒执行");
			}
		}, 60, TimeUnit.SECONDS);
		ft = ch.eventLoop().scheduleAtFixedRate(new Runnable() {
			public void run() {
				System.out.println("60秒后执行，以后每隔60秒执行一次");
			}
		}, 60, 60, TimeUnit.SECONDS);
		ft.cancel(false);// 取消调度
	}
}
