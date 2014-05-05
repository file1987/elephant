package com.elephant.framework.pools;

import org.apache.commons.pool.BasePoolableObjectFactory;

public abstract class FPoolableObjectFactory<T> extends BasePoolableObjectFactory<T> {
	
	
	@Override
	public abstract T makeObject() throws Exception;
	
	@Override
	public abstract void destroyObject(T obj) throws Exception;  
}
