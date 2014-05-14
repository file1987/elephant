package com.elephant.framework.disruptor;
/**
 * 抽象事件
 * @author file
 * @since 2014年5月14日 下午5:48:50
 * @version 1.0
 */
public abstract class AbstractEvent implements IFEvent {
    //持有事件key
	private String key;
	
	@Override
	public String getKey() {
		return key;
	}

	@Override
	public void setKey(String key) {
		this.key = key;
	}

}
