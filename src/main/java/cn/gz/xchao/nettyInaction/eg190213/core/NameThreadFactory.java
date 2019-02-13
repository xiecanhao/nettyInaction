package cn.gz.xchao.nettyInaction.eg190213.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂
 * 
 * @since 2019年2月13日 上午9:40:25
 * @author xchao
 */
public class NameThreadFactory implements ThreadFactory {
	private static final AtomicInteger threadNumber = new AtomicInteger(1);

	private final AtomicInteger mThreadNum = new AtomicInteger(1);

	private final String prefix;

	private final boolean daemoThread;

	private final ThreadGroup threadGroup;

	public NameThreadFactory() {
		this("rpcserver-threadpool-" + threadNumber.getAndIncrement(), false);
	}

	public NameThreadFactory(String prefix) {
		this(prefix, false);
	}

	public NameThreadFactory(String prefix, boolean daemo) {
		this.prefix = prefix + "-thread-";
		daemoThread = daemo;
		SecurityManager s = System.getSecurityManager();
		threadGroup = (s == null) ? Thread.currentThread().getThreadGroup()
				: s.getThreadGroup();
	}

	@Override
	public Thread newThread(Runnable r) {
		String name = prefix + mThreadNum.getAndIncrement();
		Thread ret = new Thread(threadGroup, r, name, 0);
		ret.setDaemon(daemoThread);
		return ret;
	}

	public ThreadGroup getThreadGroup() {
		return threadGroup;
	}

}
