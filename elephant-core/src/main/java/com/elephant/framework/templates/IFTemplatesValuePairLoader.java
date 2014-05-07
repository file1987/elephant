package com.elephant.framework.templates;


public interface IFTemplatesValuePairLoader extends IFTemplatesLoader {
	
	public ValuePair loaderValuePair();
	
	public void reloaderValuePair(ValuePair valuePair);
}
