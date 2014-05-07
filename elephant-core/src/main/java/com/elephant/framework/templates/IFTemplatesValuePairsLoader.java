package com.elephant.framework.templates;

import java.util.Collection;

public interface IFTemplatesValuePairsLoader extends IFTemplatesLoader {
	
	public Collection<ValuePair> loadValuePairs();
	
	public void reloadValuePairs(Collection<ValuePair> values);
	
	public void reloadValuePair(ValuePair value,String key);
	
}
