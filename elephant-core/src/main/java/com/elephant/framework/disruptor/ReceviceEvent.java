package com.elephant.framework.disruptor;

import com.elephant.framework.network.IFConnection;
import com.elephant.framework.network.msg.IFReceviceMessage;
/**
 * 接收信息事件
 * @author file
 * @since 2014年5月14日 下午5:50:17
 * @version 1.0
 */
public class ReceviceEvent extends AbstractEvent {
	/**
	 * 可接收信息
	 */
	private IFReceviceMessage message;
	/**
	 * 连接
	 */
	private IFConnection conn;
	
	public IFReceviceMessage getMessage() {
		return message;
	}
	
	public void setMessage(final IFReceviceMessage message) {
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
