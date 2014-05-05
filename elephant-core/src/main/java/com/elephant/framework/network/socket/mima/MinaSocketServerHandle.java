package com.elephant.framework.network.socket.mima;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.elephant.framework.disruptor.IFEvent;
import com.elephant.framework.disruptor.ReceviceEvent;
import com.elephant.framework.factory.DisruptorsFactroy;
import com.elephant.framework.network.IFConnection;
import com.elephant.framework.network.msg.IFReceviceMessage;
import com.elephant.framework.network.msg.IFSendableMessage;
import com.elephant.framework.network.socket.IFSocketServer;
import com.lmax.disruptor.EventTranslator;

public class MinaSocketServerHandle implements IoHandler {

	@Override
	public void exceptionCaught(IoSession arg0, Throwable t) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageReceived(IoSession session, final Object msg) throws Exception {
		if(msg instanceof IFReceviceMessage){
			
			final IFConnection conn = MinaConnectionFactory.getConn(session);
			
			DisruptorsFactroy.publishEvent(IFSocketServer.RECEVICE_MESSAGE_KEY, conn.hashCode(), new EventTranslator<IFEvent>() {
				
				@Override
				public void translateTo(IFEvent event, long sequence) {
					ReceviceEvent  _event = (ReceviceEvent) event;
					_event.setConn(conn);
					_event.setMessage((IFReceviceMessage)msg);
				}
			});
		}
		
		

	}

	@Override
	public void messageSent(IoSession session, Object msg) throws Exception {
		if(msg instanceof IFSendableMessage){
			IFConnection conn = MinaConnectionFactory.getConn(session);
			conn.sendMessage((IFSendableMessage) msg);
		}

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		MinaConnectionFactory.removeConn(session);

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		

	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		MinaSocketConnection conn = new MinaSocketConnection(session);
		MinaConnectionFactory.attachConn(conn);

	}

}
