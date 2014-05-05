package com.elephant.framework.network.socket.mima;

import org.apache.mina.core.session.IoSession;

import com.elephant.framework.disruptor.IFEvent;
import com.elephant.framework.disruptor.SendableEvent;
import com.elephant.framework.factory.DisruptorsFactroy;
import com.elephant.framework.network.IFConnection;
import com.elephant.framework.network.msg.IFSendableMessage;
import com.elephant.framework.network.socket.IFSocketConnection;
import com.elephant.framework.network.socket.IFSocketServer;
import com.lmax.disruptor.EventTranslator;

public class MinaSocketConnection implements IFSocketConnection {
	
	
	private IoSession session;
	
	public MinaSocketConnection(IoSession session){
		this.session = session;
	}
	
	@Override
	public String getIp() {
		if(session!=null)
			return session.getRemoteAddress().toString();
		return "";
	}

	@Override
	public void sendMessage(IFSendableMessage message) {
		write2Disruptor(message);
	}

	@Override
	public void close() {
		if(session!=null&&session.isConnected())
			session.close(false);
		session = null;
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	@Override
	public void write(IFSendableMessage message) {
		sendMessage(message);
		
	}

	@Override
	public void write2Client(IFSendableMessage message) {
		if(session!=null&&session.isConnected())
		    session.write(message);
		
	}

	@Override
	public void write2Disruptor(final IFSendableMessage message) {
		final IFConnection _conn = this;		
		DisruptorsFactroy.publishEvent(IFSocketServer.SENDABLE_MESSAGE_KEY, this.hashCode(), new EventTranslator<IFEvent>() {
			@Override
			public void translateTo(IFEvent event, long sequence) {
				SendableEvent  _event = (SendableEvent) event;
				_event.setConn(_conn);
				_event.setMessage(message);
			}
		});
		
		
	}

}
