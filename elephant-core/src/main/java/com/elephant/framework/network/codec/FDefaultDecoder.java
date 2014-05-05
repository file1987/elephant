package com.elephant.framework.network.codec;

import com.elephant.framework.factory.MessageFactory;
import com.elephant.framework.network.msg.IFReceviceMessage;
/**
 * 默认解码器
 * @author file
 * @since 2014年4月26日 下午8:17:04
 * @version 1.0
 */
public class FDefaultDecoder implements IFDecoder {

	@Override
	public IFReceviceMessage decoder(byte[] data,short msgId)  throws Exception{
		
		IFReceviceMessage revMsg = MessageFactory.getReceviceMessage(msgId);
		//读取bytes
		revMsg.readData(data);
		//消息解码 
		revMsg.decoder();
		
		return revMsg;
	}

}
