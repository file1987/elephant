package com.elephant.framework.network.socket.netty;

import com.elephant.framework.network.codec.IFEncoder;
import com.elephant.framework.network.msg.IFSendableMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettySocketMsgEncoder extends MessageToByteEncoder<IFSendableMessage> {
	
	private IFEncoder encoder;
	public NettySocketMsgEncoder(IFEncoder encoder){
		this.encoder = encoder;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, IFSendableMessage msg, ByteBuf out) throws Exception {
		//写入bytebuf
		out.writeBytes(encoder.encoder(msg));
	}

}
