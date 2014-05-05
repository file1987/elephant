package com.elephant.framework.network.msg;

import com.elephant.framework.network.IFNetMessage;
/**
 * 可接收消息接口
 * @author file
 * @since 2014年4月26日 下午8:28:49
 * @version 1.0
 */
public interface IFReceviceMessage extends IFNetMessage{
	
	/**
	 * 读取二进制数据
	 * @param data
	 */
	public void readData(byte[] data);
	
	/**
	 * 解码
	 */
	public void decoder();
	
	
}
