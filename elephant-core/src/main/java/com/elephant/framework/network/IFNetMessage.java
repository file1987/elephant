package com.elephant.framework.network;
/**
 * 网络消息接口
 * @author file
 * @since 2014年4月26日 下午8:12:03
 * @version 1.0
 */
public interface IFNetMessage {
	
	/**
	 * 设置消息id
	 * @param msgId
	 */
	public void setMsgId(short msgId);
	/**
	 * 获取消息id
	 * @return
	 */
	public short getMsgId();
	/**
	 * 清除消息内容（由于消息是对象池管理的）
	 */
	public void clear();	
	
}
