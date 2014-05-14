package com.elephant.framework.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.elephant.framework.network.IFHandler;
/**
 * handler工厂（handler注册器）
 * @author file
 * @since 2014年5月6日 下午9:25:44
 * @version 1.0
 */
public final class HandlerFactory {
	
	private static final Map<Short,IFHandler>  handlers = new ConcurrentHashMap<Short,IFHandler>();
	/**
	 * 注册handler
	 * @param msgId 消息id
	 * @param handler handler
	 * @throws Exception
	 */
	public  static void registerHandler(short msgId,Class<? extends IFHandler> handler) throws Exception{
		if(handlers.containsKey(msgId))
			throw new RuntimeException("该消息Id已注册了handler！！ msgId:"+msgId );
		
		handlers.put(msgId,handler.newInstance());
	}
	/**
	 * 获取handler
	 * @param msgId 消息id
	 * @return
	 */
	public static IFHandler getHandler(short msgId){
		
		IFHandler handler = handlers.get(msgId);
		if(handler==null)
			throw new RuntimeException("该消息Id未注册handler！！ msgId:"+msgId);
		
		return handler;
	}
}
