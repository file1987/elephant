package com.elephant.framework.network.msg;
/**
 * C2S 基类
 * @author file
 * @since 2014年4月26日 下午8:28:30
 * @version 1.0
 */
public class ReceviceMessage extends FAbstractReceviceMessage {
	
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
	protected void decoderData() {
		// do nothing
		
	}
	
	/**
	 * must be override
	 */
	public void clear(){
		super.clear();
	}
	

}
