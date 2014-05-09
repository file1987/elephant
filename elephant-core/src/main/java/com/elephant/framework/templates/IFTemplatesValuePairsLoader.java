package com.elephant.framework.templates;

import java.util.Collection;
/**
 * 列表类型数据加载器接口
 * @author file
 * @since 2014年5月9日 下午5:57:00
 * @version 1.0
 */
public interface IFTemplatesValuePairsLoader extends IFTemplatesLoader<Collection<ValuePair>> {
	/**
	 * 对当前列表某条数据进行重新加载		
	 * @param value 当前数据
	 */
	public void reloadValuePair(ValuePair value);
	
}
