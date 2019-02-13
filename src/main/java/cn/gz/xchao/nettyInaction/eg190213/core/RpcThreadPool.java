package cn.gz.xchao.nettyInaction.eg190213.core;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池封装类
 * 
 * @since 2019年2月13日 上午9:47:31
 * @author xchao
 */
public class RpcThreadPool {
	// 独立出线程池主要是为了应对复杂的i/o操作的业务，不阻塞netty的handler线程而引入
	// 当然如果业务足够简单，把处理逻辑写入netty的handler(ChannelInboundHandlerAdapter)也未尝不可
	public static Executor getExecutor(int threads, int queues) {
		String name = "RpcThreadPool";
		return new ThreadPoolExecutor(threads, threads, 0,
				TimeUnit.MILLISECONDS,
				queues == 0 ? new SynchronousQueue<Runnable>()
						: (queues < 0 ? new LinkedBlockingQueue<Runnable>()
								: new LinkedBlockingQueue<Runnable>(queues)),
				new NameThreadFactory(name, true),
				new AbortPolicyWithReport(name));
	}
}
