package com.elephant.framework.network.socket.netty;


import io.netty.channel.Channel;

import org.apache.log4j.Logger;

import com.elephant.framework.disruptor.IFEvent;
import com.elephant.framework.disruptor.SendableEvent;
import com.elephant.framework.factory.DisruptorsFactroy;
import com.elephant.framework.network.IFConnection;
import com.elephant.framework.network.msg.IFSendableMessage;
import com.elephant.framework.network.socket.IFSocketConnection;
import com.elephant.framework.network.socket.IFSocketServer;
import com.lmax.disruptor.EventTranslator;

public class NettySocketConnection implements IFSocketConnection {
	
	private final static Logger logger = Logger.getLogger(NettySocketConnection.class);
	
	private Channel channel;
	
	public NettySocketConnection(){
		
	}
	
	public NettySocketConnection(Channel channel){
		this.channel = channel;
	}
	
	
	@Override
	public String getIp() {
	   if(channel!=null)
		 return channel.remoteAddress().toString();
		return "";
	}

	@Override
	public void sendMessage(IFSendableMessage message) {
		write2Disruptor(message);
	}

	@Override
	public void close() {
		if(logger.isDebugEnabled()){
			logger.debug("关闭远程连接~~~~ ");
		}
		
		if(channel!=null)
			channel = null;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void write(IFSendableMessage message) {
		sendMessage(message);
	}

	@Override
	public void write2Client(IFSendableMessage message) {
		if(channel==null)
			return;
		
		if(logger.isDebugEnabled())
			logger.debug("channel:"+channel + " sendMsg: " + message);
		
		channel.write(message);
		
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
