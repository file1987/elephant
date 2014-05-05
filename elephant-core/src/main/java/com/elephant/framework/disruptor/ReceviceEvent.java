package com.elephant.framework.disruptor;

import com.elephant.framework.network.IFConnection;
import com.elephant.framework.network.msg.IFReceviceMessage;

public class ReceviceEvent extends AbstractEvent {
	
	private IFReceviceMessage message;
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
