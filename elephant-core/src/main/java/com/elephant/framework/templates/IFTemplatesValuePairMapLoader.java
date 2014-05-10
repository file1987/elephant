package com.elephant.framework.templates;

import java.util.Collection;
import java.util.Map;
/**
 * 多个列表类型数据加载器接口
 * @author file
 * @since 2014年5月9日 下午5:53:18
 * @version 1.0
 */
public interface IFTemplatesValuePairMapLoader extends IFTemplatesLoader<Map<String,Collection<ValuePair>>> {
	/**
	 * 对一个列表数据进行重新加载		
	 * @param sheetName  列表key
	 */
	public void reloadValuePairs(Map<String, Collection<ValuePair>> valuePairMap,String sheetName);
	
	/**
	 * 对一个列表中的一条数据进行重新加载
	 * @param value 当前数据
	 * @param sheetName 列表key
	 */
	public void reloadValuePair(ValuePair value,String sheetName);
	
}
