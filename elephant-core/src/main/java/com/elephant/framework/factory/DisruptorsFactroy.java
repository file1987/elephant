package com.elephant.framework.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import com.elephant.framework.disruptor.IFEvent;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * Disruptors 工厂
 * @author file
 * @since 2014年5月6日 下午9:26:15
 * @version 1.0
 */
public class DisruptorsFactroy {
	
	private static final Map<String,List<Disruptor<IFEvent>>> disruptorsMap = new HashMap<String,List<Disruptor<IFEvent>>>();
	/**
	 * 注册Disruptors
	 * @param key  事件key
	 * @param disruptorSize 
	 * @param buffSize
	 * @param executor
	 * @param factory
	 * @param handler
	 */
	@SuppressWarnings("unchecked")
	public static void registerDisruptor(String key,int disruptorSize,int buffSize,Executor executor,EventFactory<IFEvent> factory, EventHandler<IFEvent> handler){		
		List<Disruptor<IFEvent>>  disruptors = disruptorsMap.get(key);
		if(disruptors==null){
			disruptors = new ArrayList<Disruptor<IFEvent>>();
		}
		for(int i=0;i<disruptorSize;i++){
			Disruptor<IFEvent> disruptor = new Disruptor<IFEvent>(factory,buffSize, executor,ProducerType.MULTI,new SleepingWaitStrategy());
			disruptor.handleEventsWith(handler);
			disruptor.start();			
			disruptors.add(disruptor);
		}
		disruptorsMap.put(key, disruptors);
	}
	/**
	 * 注册Disruptors
	 * @param key   事件key
	 * @param executor
	 * @param factory
	 * @param handler
	 */
	public static void registerDisruptor(String key,Executor executor,EventFactory<IFEvent> factory, EventHandler<IFEvent> handler){		
		registerDisruptor(key, Runtime.getRuntime().availableProcessors(), (int) Math.pow(2, 15), executor, factory, handler);
	}
	
	/**
	 * 注销Disruptors
	 */
	public static void unregisterAll(){
		for(List<Disruptor<IFEvent>> disruptors:disruptorsMap.values()){
			for(Disruptor<IFEvent> disruptor:disruptors){
				disruptor.shutdown();	
			}
		}
		disruptorsMap.clear();
	}
	/**
	 * 获取Disruptor
	 * @param key 事件key
	 * @param hashCode hashcode
	 * @return
	 */
	public static Disruptor<IFEvent> getDisruptor(String key,long hashCode){
		List<Disruptor<IFEvent>>  disruptors = disruptorsMap.get(key);
		int size = disruptors.size();
		int mod = (int) (hashCode % size);
		mod = mod < 0 || mod >= size ? 0 : mod;
		return disruptors.get(mod);
	}
	/**
	 * 发布事件
	 * @param key
	 * @param hashCode
	 * @param eventTranslator
	 */
	public static void publishEvent(String key,long hashCode,EventTranslator<IFEvent> eventTranslator){
		publishEvent(getDisruptor(key, hashCode),eventTranslator);
	}
	/**
	 * 发布事件
	 * @param disruptor
	 * @param eventTranslator
	 */
	public static void publishEvent(Disruptor<IFEvent> disruptor,EventTranslator<IFEvent> eventTranslator){
		disruptor.publishEvent(eventTranslator);
	}
	
	
	
}
