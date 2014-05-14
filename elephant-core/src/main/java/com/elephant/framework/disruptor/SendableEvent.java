package com.elephant.framework.disruptor;

import com.elephant.framework.network.IFConnection;
import com.elephant.framework.network.msg.IFSendableMessage;
/**
 * 发送信息 事件
 * @author file
 * @since 2014年5月14日 下午5:49:23
 * @version 1.0
 */
public class SendableEvent extends AbstractEvent {
	/**
	 * 可发送信息
	 */
	private IFSendableMessage message;
	/**
	 * 连接
	 */
	private IFConnection conn;
	
	public IFSendableMessage getMessage() {
		return message;
	}
	
	public void setMessage(final IFSendableMessage message) {
		this.message = message;
	}
	
	public IFConnection getConn() {
		return conn;
	}
	
	public void setConn(final IFConnection conn) {
		this.conn = conn;
	}

	@Override
	public void clear() {
		message = null;
		conn = null;
		
	}

	
}
