package com.elephant.framework.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.elephant.framework.network.IFHandler;

public final class HandlerFactory {
	
	private static final Map<Short,IFHandler>  handlers = new ConcurrentHashMap<Short,IFHandler>();
	
	public  static void registerHandler(short msgId,Class<? extends IFHandler> handler) throws Exception{
		if(handlers.containsKey(msgId))
			throw new RuntimeException("该消息Id已注册了handler！！ msgId:"+msgId );
		
		handlers.put(msgId,handler.newInstance());
	}
	
	public static IFHandler getHandler(short msgId){
		
		IFHandler handler = handlers.get(msgId);
		if(handler==null)
			throw new RuntimeException("该消息Id未注册handler！！ msgId:"+msgId);
		
		return handler;
	}
}
