package com.elephant.framework.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.elephant.framework.network.IFNetMessage;
import com.elephant.framework.network.msg.IFReceviceMessage;
import com.elephant.framework.network.msg.IFSendableMessage;
import com.elephant.framework.pools.FObjectPool;
import com.elephant.framework.pools.FPoolableObjectFactory;

public final class MessageFactory {
	
	private static final Map<Short,FObjectPool<IFNetMessage>>  messages = new ConcurrentHashMap<Short,FObjectPool<IFNetMessage>>();
	
	
	public static void unregisterAll(){
		messages.clear();
	}
	
	
	public  static void registerSendMessage(short msgId,Class<? extends IFSendableMessage> msgClass){
		registerMessage(msgId, msgClass);
	}
	
	public static void registerReceviceMessage(short msgId,final Class<? extends IFReceviceMessage> msgClass){
		registerMessage(msgId, msgClass);
	}
	
	public static void registerMessage(final short msgId,final Class<? extends IFNetMessage> msgClass){
		if(messages.containsKey(msgId))
			throw new RuntimeException("该消息Id已注册了可发送消息！！ msgId:"+msgId +"  class:"+ messages.get(msgId) );
		
		
		
		messages.put(msgId, new FObjectPool<IFNetMessage>(new FPoolableObjectFactory<IFNetMessage>() {

			@Override
			public IFNetMessage makeObject() throws Exception {
				IFNetMessage message = msgClass.newInstance();
				message.setMsgId(msgId);
				return message;
			}

			@Override
			public void destroyObject(IFNetMessage obj) {
				obj.clear();
			}
		}));
	}
	
	public static IFSendableMessage getSendMessage(short msgId) throws Exception{
		IFNetMessage message = messages.get(msgId).borrowObject();
		if(message instanceof IFSendableMessage)
			return (IFSendableMessage) message;
		else
			throw new RuntimeException("当前消息id注册的不是可发送(C2S)的消息！msgId："+msgId +", messageClass:"+message.getClass());
	}
	
	public static IFReceviceMessage getReceviceMessage(short msgId) throws Exception{
		IFNetMessage message = messages.get(msgId).borrowObject();
		if(message instanceof IFReceviceMessage)
			return (IFReceviceMessage) message;
		else
			throw new RuntimeException("当前消息id注册的不是 可接收(S2C)的消息！msgId："+msgId +", messageClass:"+message.getClass());
	}
	
	public static void recycle(IFNetMessage message) throws Exception{
		messages.get(message.getMsgId()).returnObject(message);
	}
	
	
	
}
