package com.elephant.framework.network.codec;

import org.apache.log4j.Logger;

import com.elephant.framework.factory.MessageFactory;
import com.elephant.framework.network.msg.IFSendableMessage;
/**
 * 默认编码器
 * @author file
 * @since 2014年4月26日 下午8:17:30
 * @version 1.0
 */
public class FDefaultEncoder implements IFEncoder {
	private static final Logger logger = Logger.getLogger(FDefaultEncoder.class);
	@Override
	public byte[] encoder(IFSendableMessage message) {
		message.encoder();
		byte[] data = message.getData();
		message.clear();
		try {
			MessageFactory.recycle(message);
		} catch (Exception e) {
			logger.error("回收消息出错！" +message,e );
		}
		return data;
	}

}
