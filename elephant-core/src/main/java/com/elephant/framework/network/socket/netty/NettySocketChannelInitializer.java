package com.elephant.framework.network.socket.netty;

import com.elephant.framework.network.codec.FDefaultDecoder;
import com.elephant.framework.network.codec.FDefaultEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;


public class NettySocketChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel sc) throws Exception {
		sc.pipeline()
		   //解码器
		  .addLast("decoder", new NettySocketBytesDecoder(new FDefaultDecoder()))
		   //编码器
		  .addLast("encoder", new NettySocketMsgEncoder(new FDefaultEncoder()))
		   //business logic
		  .addLast("handler",new NettySocketServerHandler());
		
	}

}
