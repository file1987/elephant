package com.elephant.framework.network.socket.mima;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.elephant.framework.network.codec.FDefaultDecoder;
import com.elephant.framework.network.codec.FDefaultEncoder;
import com.elephant.framework.network.socket.AbstractSocketServer;


public class MinaSocketServer extends AbstractSocketServer {
	
	private int port;
	private final IoAcceptor acceptor;
	public MinaSocketServer(){
		this(9000);
	}
	
	public MinaSocketServer(int port){
		this.port = port;
		acceptor = new NioSocketAcceptor();
	}
	
	public void startUp() throws IOException{
        acceptor.getFilterChain().addLast("decoder", new MinaSocketBytesDecoder(new FDefaultDecoder()));
        acceptor.getFilterChain().addLast("encoder", new MinaSocketMsgEncoder(new FDefaultEncoder()));
        acceptor.setHandler(new MinaSocketServerHandle());
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10 );
        acceptor.bind(new InetSocketAddress(port));
	}
	
	
	@Override
	public void shutdown() throws Exception {
		acceptor.dispose();
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
