package cn.gz.xchao.nettyInaction.eg190213.model;

import java.io.Serializable;

/**
 * 服务应答结构
 * 
 * @since 2019年2月13日 上午9:32:16
 * @author xchao
 */
public class MessageResponse implements Serializable {
	/**  */
	private static final long serialVersionUID = 1L;

	private String messageId;
	private String error;
	private Object resultDesc;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Object getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(Object resultDesc) {
		this.resultDesc = resultDesc;
	}

	@Override
	public String toString() {
		return "MessageResponse [messageId=" + messageId + ", error=" + error
				+ ", resultDesc=" + resultDesc + "]";
	}

}
