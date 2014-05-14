package com.elephant.framework.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.elephant.framework.disruptor.IFEvent;
import com.elephant.framework.disruptor.ReceviceEvent;
import com.elephant.framework.disruptor.SendableEvent;
import com.elephant.framework.pools.FObjectPool;
import com.elephant.framework.pools.FPoolableObjectFactory;
/**
 * DisruptorEvent 工厂
 * @author file
 * @since 2014年5月14日 下午5:58:07
 * @version 1.0
 */
public final class DisruptorEventFactory {
	
	private static final Map<String,FObjectPool<IFEvent>>  events = new ConcurrentHashMap<String,FObjectPool<IFEvent>>();
	
	/**
	 * 注销DisruptorEvent
	 */
	public static void unregisterAll(){
		events.clear();
	}
	
	/**
	 * 注册可发送消息事件
	 * @param key 事件key
	 * @param eventClass 事件class
	 */
	public  static void registerSendMessage(String key,Class<? extends SendableEvent> eventClass){
		registerMessage(key, eventClass);
	}
	/**
	 * 注册可接收消息事件
	 * @param key 事件key
	 * @param eventClass 事件class
	 */
	public static void registerReceviceMessage(String key,final Class<? extends ReceviceEvent> eventClass){
		registerMessage(key, eventClass);
	}
	
	/**
	 * 注册消息事件
	 * @param key 事件key
	 * @param eventClass 事件class
	 */
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
	/**
	 * 获取接收消息事件
	 * @param key 事件key
	 * @return
	 * @throws Exception
	 */
	public static ReceviceEvent getReceviceEvent(String key) throws Exception{
		IFEvent event = events.get(key).borrowObject();
		if(event instanceof ReceviceEvent)
			return (ReceviceEvent) event;
		else
			throw new RuntimeException("当前消息key注册的不是读取事件！key："+key +", Class:"+event.getClass());
	}
	/**
	 * 获取可发送消息事件
	 * @param key 事件key
	 * @return
	 * @throws Exception
	 */
	public static SendableEvent getSendableEvent(String key) throws Exception{
		IFEvent event = events.get(key).borrowObject();
		if(event instanceof SendableEvent)
			return (SendableEvent) event;
		else
			throw new RuntimeException("当前消息key注册的不是可发送事件！key："+key +", Class:"+event.getClass());
	}
	
	/**
	 * 回收消息事件
	 * @param event
	 * @throws Exception
	 */
	public static void recycle(IFEvent event) throws Exception{
		events.get(event.getKey()).returnObject(event);
	}
	
	
	
}
