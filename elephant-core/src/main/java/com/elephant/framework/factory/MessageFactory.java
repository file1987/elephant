package com.elephant.framework.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.elephant.framework.network.IFNetMessage;
import com.elephant.framework.network.msg.IFReceviceMessage;
import com.elephant.framework.network.msg.IFSendableMessage;
import com.elephant.framework.pools.FObjectPool;
import com.elephant.framework.pools.FPoolableObjectFactory;
/**
 * 消息工厂（注册器）
 * @author file
 * @since 2014年5月6日 下午9:25:08
 * @version 1.0
 */
public final class MessageFactory {
	
	private static final Map<Short,FObjectPool<IFNetMessage>>  messages = new ConcurrentHashMap<Short,FObjectPool<IFNetMessage>>();
	
	/**
	 * 注销所有消息
	 */
	public static void unregisterAll(){
		messages.clear();
	}
	
	/**
	 * 注册发送信息
	 * @param msgId 消息id
	 * @param msgClass 消息class
	 */
	public  static void registerSendMessage(short msgId,Class<? extends IFSendableMessage> msgClass){
		registerMessage(msgId, msgClass);
	}
	/**
	 * 注册接收信息
	 * @param msgId 消息id
	 * @param msgClass 消息class
	 */
	public static void registerReceviceMessage(short msgId,final Class<? extends IFReceviceMessage> msgClass){
		registerMessage(msgId, msgClass);
	}
	/**
	 * 注册消息
	 * @param msgId  消息id
	 * @param msgClass 消息class
	 */
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
	/**
	 * 获取可发送消息
	 * @param msgId 消息id
	 * @return 可发送消息
	 * @throws Exception
	 */
	public static IFSendableMessage getSendMessage(short msgId) throws Exception{
		IFNetMessage message = messages.get(msgId).borrowObject();
		if(message instanceof IFSendableMessage)
			return (IFSendableMessage) message;
		else
			throw new RuntimeException("当前消息id注册的不是可发送(C2S)的消息！msgId："+msgId +", messageClass:"+message.getClass());
	}
	/**
	 * 获取接收消息
	 * @param msgId 消息id
	 * @return 接收消息
	 * @throws Exception
	 */
	public static IFReceviceMessage getReceviceMessage(short msgId) throws Exception{
		IFNetMessage message = messages.get(msgId).borrowObject();
		if(message instanceof IFReceviceMessage)
			return (IFReceviceMessage) message;
		else
			throw new RuntimeException("当前消息id注册的不是 可接收(S2C)的消息！msgId："+msgId +", messageClass:"+message.getClass());
	}
	
	/**
	 * 回收消息
	 * @param message 消息
	 * @throws Exception
	 */
	public static void recycle(IFNetMessage message) throws Exception{
		messages.get(message.getMsgId()).returnObject(message);
	}
	
	
	
}
