package com.elephant.framework.pools;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.pool.BaseObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;

public class FObjectPool<T> extends BaseObjectPool<T>{
	
	private  final Queue<T> pools = new ConcurrentLinkedQueue<T>();
	private  final PoolableObjectFactory<T>  factory;
	private  static final int DEFAULT_MAX_SIZE = 1000;
	private  int totalNum = 0; 
	private int  maxSize = 0;
	private int borrowNum =0;
	private int returnNUm = 0;
	
	public FObjectPool(PoolableObjectFactory<T>  factory){
		this(DEFAULT_MAX_SIZE,factory);
	}
	
	public FObjectPool(int maxSize,PoolableObjectFactory<T>  factory){
		this.maxSize = maxSize;
		this.factory = factory;
	}
	
	
	@Override
	public T borrowObject() throws Exception {
		T obj = pools.poll();
	    if(obj==null){	
	        obj = factory.makeObject();
	        totalNum ++ ;
	    }
	    borrowNum++;
		return obj;
	}

	@Override
	public void returnObject(T obj) throws Exception {
		
		if(obj==null)
			return ;
		
		if(pools.size() >= maxSize){
			return ;
		}
		
		if(pools.contains(obj)){
			throw new RuntimeException("当前对象已在池中，无需返还~~");
		}
		factory.destroyObject(obj);
		pools.offer(obj);
		returnNUm ++;	
	}
	
	@Override
	public void invalidateObject(T obj) throws Exception {
		
	}

}
