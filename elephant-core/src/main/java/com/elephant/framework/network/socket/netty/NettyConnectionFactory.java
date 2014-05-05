package com.elephant.framework.network.socket.netty;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class NettyConnectionFactory {
	
	private static final Map<Channel,NettySocketConnection> connections = new HashMap<Channel,NettySocketConnection>();
	
	public static void attachConn(NettySocketConnection conn){
		if(connections.containsKey(conn.getChannel())){
			NettySocketConnection _conn = connections.get(conn.getChannel());
			_conn.close();
		}
		connections.put(conn.getChannel(), conn);
	}
	
	public static void  removeConn(Channel channel){
	   NettySocketConnection conn =	connections.remove(channel);
	   if(conn!=null)
		   conn.close();
	}
	
	public static NettySocketConnection getConn(Channel channel){
		return connections.get(channel);
	}
	
	
}
