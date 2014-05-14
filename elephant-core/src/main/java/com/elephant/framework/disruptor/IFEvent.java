package com.elephant.framework.disruptor;
/**
 * 事件接口（用于对象池处理）
 * @author file
 * @since 2014年5月6日 下午9:26:35
 * @version 1.0
 */
public interface IFEvent {
	/**
	 * 清理数据
	 */
	public void clear();
	/**
	 * 获取事件key
	 * @return
	 */
	public String getKey();
	/**
	 * 设置事件key
	 * @param Key
	 */
	public void setKey(String Key);

}
