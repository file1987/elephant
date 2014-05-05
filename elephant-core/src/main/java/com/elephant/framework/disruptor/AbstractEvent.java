package com.elephant.framework.disruptor;

public abstract class AbstractEvent implements IFEvent {

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
