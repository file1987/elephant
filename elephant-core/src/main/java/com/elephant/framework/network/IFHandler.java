package com.elephant.framework.network;

import com.elephant.framework.network.msg.IFReceviceMessage;
/**
 *  逻辑处理接口
 * @author file
 * @since 2014年4月26日 下午8:12:11
 * @version 1.0
 */
public interface IFHandler {
	/**
	 * 接收消息
	 * @param con client连接
	 * @param message 可接收消息
	 */
	public void onReceviceMsg(IFConnection con,IFReceviceMessage message);
		
}
