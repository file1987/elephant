package com.elephant.framework.templates;

import java.util.Collection;
import java.util.Map;

public interface IFTemplatesValuePairMapLoader extends IFTemplatesLoader {
	
	public Map<String,Collection<ValuePair>> loadValuePairMaps();
	
	public void reloadValuePairMap(Map<String,Collection<ValuePair>> valueMap);
	
	public void reloadValuePairs(Collection<ValuePair> values);
	
	public void reloadValuePair(ValuePair value,String sheetName,String key);
	
}
