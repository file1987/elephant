package com.elephant.framework.network.codec;

import com.elephant.framework.network.msg.IFReceviceMessage;
/**
 * 解码接口
 * @author file
 * @since 2014年4月26日 下午8:14:20
 * @version 1.0
 */
public interface IFDecoder {
	/**
	 * 根据消息id和消息内容，生成收到消息，并解码内容
	 * @param data 消息内容
	 * @param msgId 消息id
	 * @return  消息（c2s）
	 * @throws Exception
	 */
	public IFReceviceMessage decoder(byte[] data,short msgId) throws Exception;

}
