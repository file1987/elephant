package com.elephant.framework.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.elephant.framework.disruptor.IFEvent;
import com.elephant.framework.disruptor.ReceviceEvent;
import com.elephant.framework.disruptor.SendableEvent;
import com.elephant.framework.pools.FObjectPool;
import com.elephant.framework.pools.FPoolableObjectFactory;

public final class DisruptorEventFactory {
	
	private static final Map<String,FObjectPool<IFEvent>>  events = new ConcurrentHashMap<String,FObjectPool<IFEvent>>();
	
	
	public static void unregisterAll(){
		events.clear();
	}
	
	
	public  static void registerSendMessage(String key,Class<? extends SendableEvent> eventClass){
		registerMessage(key, eventClass);
	}
	
	public static void registerReceviceMessage(String key,final Class<? extends ReceviceEvent> eventClass){
		registerMessage(key, eventClass);
	}
	
	public static void registerMessage(final String key,final Class<? extends IFEvent> eventClass){
		if(events.containsKey(key))
			throw new RuntimeException("该事件key已被注册了，请重新设置key！！ key:"+key +"  class:"+ events.get(key) );
		
		
		
		events.put(key, new FObjectPool<IFEvent>(new FPoolableObjectFactory<IFEvent>() {

			@Override
			public IFEvent makeObject() throws Exception {
				 IFEvent event = eventClass.newInstance();
				 event.setKey(key);
				return event;
			}

			@Override
			public void destroyObject(IFEvent obj) {
				obj.clear();
			}
		}));
	}
	
	public static ReceviceEvent getReceviceEvent(String key) throws Exception{
		IFEvent event = events.get(key).borrowObject();
		if(event instanceof ReceviceEvent)
			return (ReceviceEvent) event;
		else
			throw new RuntimeException("当前消息key注册的不是读取事件！key："+key +", Class:"+event.getClass());
	}
	
	public static SendableEvent getSendableEvent(String key) throws Exception{
		IFEvent event = events.get(key).borrowObject();
		if(event instanceof SendableEvent)
			return (SendableEvent) event;
		else
			throw new RuntimeException("当前消息key注册的不是可发送事件！key："+key +", Class:"+event.getClass());
	}
	
	public static void recycle(IFEvent event) throws Exception{
		events.get(event.getKey()).returnObject(event);
	}
	
	
	
}
