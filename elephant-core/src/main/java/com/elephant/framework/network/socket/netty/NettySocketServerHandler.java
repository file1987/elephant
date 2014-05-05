package com.elephant.framework.network.socket.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.elephant.framework.disruptor.IFEvent;
import com.elephant.framework.disruptor.ReceviceEvent;
import com.elephant.framework.factory.DisruptorsFactroy;
import com.elephant.framework.network.IFConnection;
import com.elephant.framework.network.msg.IFReceviceMessage;
import com.elephant.framework.network.socket.IFSocketServer;
import com.lmax.disruptor.EventTranslator;

public class NettySocketServerHandler extends SimpleChannelInboundHandler<IFReceviceMessage> {
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) 	throws Exception {
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		NettySocketConnection conn = new NettySocketConnection(ctx.channel());
		NettyConnectionFactory.attachConn(conn);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		NettyConnectionFactory.removeConn(ctx.channel());
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, final IFReceviceMessage msg) throws Exception {
		
		/**
		
		try{
			IFHandler handler = HandlerFactory.getHandler(msg.getMsgId());
			IFConnection conn = NettyConnectionFactory.getConn(ctx.channel());
			handler.onReceviceMsg(conn, msg);
		}finally{
			MessageFactory.recycle(msg);			
		}**/
		
		final IFConnection conn = NettyConnectionFactory.getConn(ctx.channel());
		
		DisruptorsFactroy.publishEvent(IFSocketServer.RECEVICE_MESSAGE_KEY, conn.hashCode(), new EventTranslator<IFEvent>() {
			
			@Override
			public void translateTo(IFEvent event, long sequence) {
				ReceviceEvent  _event = (ReceviceEvent) event;
				_event.setConn(conn);
				_event.setMessage(msg);
			}
		});
				
	}

}
