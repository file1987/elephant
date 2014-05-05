package com.elephant.framework.network;

import com.elephant.framework.network.msg.IFSendableMessage;
/**
 * 客户端连接接口
 * @author file
 * @since 2014年4月26日 下午8:13:33
 * @version 1.0
 */
public interface IFConnection {
	/**
	 * 客户端ip
	 * @return
	 */
	public String getIp();
	/**
	 * 往客户端发送消息
	 * @param message
	 */
	public void sendMessage(IFSendableMessage message);
	/**
	 * 关闭连接
	 */
	public void close();
		
	
}
