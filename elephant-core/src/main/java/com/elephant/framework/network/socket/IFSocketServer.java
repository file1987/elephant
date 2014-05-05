package com.elephant.framework.network.socket;

import com.elephant.framework.network.IFServer;
/**
 * TCP/IP 服务器接口
 * @author file
 * @since 2014年4月26日 下午8:26:45
 * @version 1.0
 */
public interface IFSocketServer extends IFServer {
	
	public static final String RECEVICE_MESSAGE_KEY = "socket_recevice_message_key";
	
	public static final String SENDABLE_MESSAGE_KEY = "socket_sendable_message_key";
	
	public void registerDisruptor(int disruptorSize,int buffSize);
		
	

}
