package com.elephant.framework.disruptor;

import com.elephant.framework.network.IFConnection;
import com.elephant.framework.network.msg.IFSendableMessage;

public class SendableEvent extends AbstractEvent {
	
	private IFSendableMessage message;
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
