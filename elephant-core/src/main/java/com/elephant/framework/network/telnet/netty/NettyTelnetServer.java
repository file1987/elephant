package com.elephant.framework.network.telnet.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.elephant.framework.network.telnet.IFTelnetServer;

public class NettyTelnetServer implements IFTelnetServer {
	
	private int port;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	
	public NettyTelnetServer(){
		this(10000);
	}
	
	public NettyTelnetServer(int port){
		this.port = port;
	}
	
	
	@Override
	public void startUp() throws Exception {
		bossGroup = new NioEventLoopGroup(1);
		workerGroup = new NioEventLoopGroup();
		 ServerBootstrap b = new ServerBootstrap();
         b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .childHandler(new TelnetServerInitializer());

         b.bind(port).sync();
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
