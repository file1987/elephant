package com.elephant.framework.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import com.elephant.framework.disruptor.IFEvent;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;


public class DisruptorFactroy {
	
	private static final Map<String,Disruptor<IFEvent>> disruptors = new HashMap<String,Disruptor<IFEvent>>();
	
	@SuppressWarnings("unchecked")
	public static void registerDisruptor(String key,int buffSize,Executor executor,EventFactory<IFEvent> factory, EventHandler<IFEvent> handler){
		Disruptor<IFEvent> disruptor = new Disruptor<IFEvent>(factory,buffSize, executor,ProducerType.MULTI,new SleepingWaitStrategy());
		disruptor.handleEventsWith(handler);
		disruptor.start();
		disruptors.put(key, disruptor);
	}
	
	public static void unregisterAll(){
		for(Disruptor<IFEvent> disruptor:disruptors.values()){
			disruptor.shutdown();
		}
		disruptors.clear();
	}
	
	public static Disruptor<IFEvent> getDisruptor(String key){
		return disruptors.get(key);
	}
	
	
}
