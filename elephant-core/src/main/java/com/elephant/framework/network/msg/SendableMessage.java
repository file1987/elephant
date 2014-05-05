package com.elephant.framework.network.msg;
/**
 * s2c消息基类 
 * @author file
 * @since 2014年4月26日 下午8:27:36
 * @version 1.0
 */
public class SendableMessage extends FAbstractSendableMessage {
	
	private short msgId;
	
	@Override
	public void setMsgId(short msgId) {
		this.msgId = msgId;
	}

	@Override
	public short getMsgId() {
		return msgId;
	}
	/**
	 * must be override
	 */
	@Override
	protected void encoderData() {
		//do nothing
		
	}
	
	/**
	 * must be override
	 */
	public void clear(){
		super.clear();
	}
}
