package cn.gz.xchao.nettyInaction.eg190213.core;

import java.lang.reflect.Proxy;

/**
 * rpc客户端执行模块
 * 
 * @since 2019年2月13日 上午10:14:08
 * @author xchao
 */
public class MessageSendExecutor {
	private RpcServerLoader loader = RpcServerLoader.getInstance();

	public MessageSendExecutor(String serverAddress) {
		loader.load(serverAddress);
	}

	public void stop() {
		loader.unLoad();
	}

	public static <T> T execute(Class<T> rpcInterface) {
		return (T) Proxy.newProxyInstance(rpcInterface.getClassLoader(),
				new Class<?>[] { rpcInterface },
				new MessageSendProxy<T>(rpcInterface));
	}
}
