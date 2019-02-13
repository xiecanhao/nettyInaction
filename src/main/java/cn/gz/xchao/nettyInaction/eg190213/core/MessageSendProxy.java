package cn.gz.xchao.nettyInaction.eg190213.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

import cn.gz.xchao.nettyInaction.eg190213.model.MessageRequest;

/**
 * rpc客户端消息处理
 * 
 * @param <T>
 * @since 2019年2月13日 上午10:33:11
 * @author xchao
 */
public class MessageSendProxy<T> implements InvocationHandler {

	private Class<T> cls;

	public MessageSendProxy(Class<T> cls) {
		this.cls = cls;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		MessageRequest request = new MessageRequest();
		request.setMessageId(UUID.randomUUID().toString());
		request.setClassName(method.getDeclaringClass().getName());
		request.setMethodName(method.getName());
		request.setTypeParameters(method.getParameterTypes());
		request.setParametersVal(args);

		MessageSendhandler handler = RpcServerLoader.getInstance()
				.getMessageSendHandler();
		MessageCallBack callBack = handler.sendRequest(request);
		return callBack.start();
	}

}
