package com.elephant.framework.network.socket.mima;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

public class MinaConnectionFactory {
	
	private static final Map<IoSession,MinaSocketConnection> connections = new HashMap<IoSession,MinaSocketConnection>();
	
	public static void attachConn(MinaSocketConnection conn){
		if(connections.containsKey(conn.getSession())){
			MinaSocketConnection _conn = connections.get(conn.getSession());
			_conn.close();
		}
		connections.put(conn.getSession(), conn);
	}
	
	public static void  removeConn(IoSession session){
		MinaSocketConnection conn =	connections.remove(session);
	   if(conn!=null)
		   conn.close();
	}
	
	public static MinaSocketConnection getConn(IoSession session){
		return connections.get(session);
	}
	
	
}
