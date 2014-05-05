package com.elephant.framework.network;

import com.elephant.framework.network.msg.IFReceviceMessage;
/**
 *  逻辑处理接口
 * @author file
 * @since 2014年4月26日 下午8:12:11
 * @version 1.0
 */
public interface IFHandler {
	
	public void onReceviceMsg(IFConnection con,IFReceviceMessage message);
		
}
