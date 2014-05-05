package com.elephant.framework.network.socket;

import java.util.concurrent.Executors;

import com.elephant.framework.disruptor.IFEvent;
import com.elephant.framework.disruptor.ReceviceEvent;
import com.elephant.framework.disruptor.SendableEvent;
import com.elephant.framework.factory.DisruptorEventFactory;
import com.elephant.framework.factory.DisruptorsFactroy;
import com.elephant.framework.factory.HandlerFactory;
import com.elephant.framework.factory.MessageFactory;
import com.elephant.framework.network.IFConnection;
import com.elephant.framework.network.IFHandler;
import com.elephant.framework.network.msg.IFReceviceMessage;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;

public abstract class AbstractSocketServer implements IFSocketServer {

	@Override
	public void registerDisruptor(int disruptorSize, int buffSize) {
		
		DisruptorsFactroy.registerDisruptor(RECEVICE_MESSAGE_KEY, Executors.newSingleThreadExecutor(), new EventFactory<IFEvent>() {
			
			@Override
			public IFEvent newInstance() {
				try {
					return DisruptorEventFactory.getReceviceEvent(RECEVICE_MESSAGE_KEY);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;  
			}
		}, new EventHandler<IFEvent>() {
			
			@Override
			public void onEvent(IFEvent event, long sequence, boolean endOfBatch) throws Exception {
				if (event instanceof ReceviceEvent) {
					ReceviceEvent receviceEvent = (ReceviceEvent) event;
					IFReceviceMessage _msg = receviceEvent.getMessage();
					try{
						IFHandler handler = HandlerFactory.getHandler(_msg.getMsgId());
						handler.onReceviceMsg(receviceEvent.getConn(), _msg);
					}finally{
						MessageFactory.recycle(_msg);
						DisruptorEventFactory.recycle(event);
					}
				}
				
			}
		});
		
		DisruptorsFactroy.registerDisruptor(SENDABLE_MESSAGE_KEY,  Executors.newSingleThreadExecutor(), new EventFactory<IFEvent>() {
			
			@Override
			public IFEvent newInstance() {
				try {
					return DisruptorEventFactory.getSendableEvent(SENDABLE_MESSAGE_KEY);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;  
			}
		}, new EventHandler<IFEvent>() {
			
			@Override
			public void onEvent(IFEvent event, long sequence, boolean endOfBatch) throws Exception {
				if (event instanceof SendableEvent) {
					SendableEvent sendableEvent = (SendableEvent) event;
					try{
						IFConnection conn = sendableEvent.getConn();
						if (conn instanceof IFSocketConnection) {
							IFSocketConnection _conn = (IFSocketConnection) conn;
							_conn.write2Client(sendableEvent.getMessage());
						}
					}finally{
						DisruptorEventFactory.recycle(event);
					}
				}
				
			}
		});
		
		DisruptorEventFactory.registerReceviceMessage(RECEVICE_MESSAGE_KEY, ReceviceEvent.class);
		DisruptorEventFactory.registerSendMessage(SENDABLE_MESSAGE_KEY, SendableEvent.class);

	}

}
