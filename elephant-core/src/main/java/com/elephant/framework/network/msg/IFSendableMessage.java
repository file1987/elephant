package com.elephant.framework.network.msg;

import com.elephant.framework.network.IFNetMessage;
/**
 * 可发送消息接口
 * @author file
 * @since 2014年4月26日 下午8:17:50
 * @version 1.0
 */
public interface IFSendableMessage extends IFNetMessage {
	/**
	 * 编码
	 */
	public  void  encoder();
	/**
	 * 获取其二进制内容
	 * @return
	 */
	public byte[]  getData();
	
	
	
}
