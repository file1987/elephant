package com.elephant.framework.network.codec;

import com.elephant.framework.network.msg.IFSendableMessage;
/**
 * 编码接口
 * @author file
 * @since 2014年4月26日 下午8:15:28
 * @version 1.0
 */
public interface IFEncoder {
	/**
	 * 把消息内容和消息id编码成二进制
	 * @param message 可发送消息（s2c）
	 * @return
	 */
	public byte[] encoder(IFSendableMessage message);

}
