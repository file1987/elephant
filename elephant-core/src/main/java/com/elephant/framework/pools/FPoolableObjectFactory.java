package com.elephant.framework.pools;

import org.apache.commons.pool.BasePoolableObjectFactory;
/**
 * 对象池工厂
 * @author file
 * @since 2014年5月6日 下午9:23:59
 * @version 1.0
 * @param <T>
 */
public abstract class FPoolableObjectFactory<T> extends BasePoolableObjectFactory<T> {
	
	
	@Override
	public abstract T makeObject() throws Exception;
	
	@Override
	public abstract void destroyObject(T obj) throws Exception;  
}
