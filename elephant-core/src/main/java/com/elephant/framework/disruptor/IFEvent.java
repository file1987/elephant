package com.elephant.framework.disruptor;
/**
 * 事件接口
 * @author file
 * @since 2014年5月6日 下午9:26:35
 * @version 1.0
 */
public interface IFEvent {
	
	public void clear();
	
	public String getKey();
	
	public void setKey(String Key);

}
