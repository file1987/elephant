package com.elephant.framework.network.socket.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.elephant.framework.network.socket.AbstractSocketServer;

public class NettySocketServer extends AbstractSocketServer{
	
	private int port;
	private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	private final EventLoopGroup workerGroup = new NioEventLoopGroup();
	
	public NettySocketServer(){
		this(9000);
	}
	
	public NettySocketServer(int port){
		this.port = port;
	}

	
	public void startUp() throws Exception{		
		ServerBootstrap boot = new ServerBootstrap();
		boot.group(bossGroup, workerGroup)
		    .channel(NioServerSocketChannel.class)
		    .childHandler(new NettySocketChannelInitializer());
		boot.bind(port).sync();		
	}
	
	@Override
	public void shutdown() throws Exception {
		bossGroup.shutdownGracefully().sync();
		workerGroup.shutdownGracefully().sync();
		
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public void setPort(int port) {
		this.port = port;
		
	}
	
	
	
	
	
	
}
