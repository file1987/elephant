package com.elephant.framework.network.socket;

import com.elephant.framework.network.IFConnection;
import com.elephant.framework.network.msg.IFSendableMessage;

public interface IFSocketConnection extends IFConnection {
	
	public void write(IFSendableMessage message);
	
	public void write2Client(IFSendableMessage message);
	
	public void write2Disruptor(IFSendableMessage message);
}
