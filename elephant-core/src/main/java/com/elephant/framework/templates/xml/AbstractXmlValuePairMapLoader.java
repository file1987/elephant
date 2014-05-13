package com.elephant.framework.templates.xml;

import java.util.Collection;
import java.util.Map;

import com.elephant.framework.templates.AbstractDefaultValuePairMapLoader;
import com.elephant.framework.templates.IFTemplatesValuePairMapLoader;
import com.elephant.framework.templates.ValuePair;
import com.elephant.framework.utils.XmlUtils;

public abstract class AbstractXmlValuePairMapLoader extends AbstractDefaultValuePairMapLoader implements IFTemplatesValuePairMapLoader {

	@Override
	public Map<String, Collection<ValuePair>> loaderData() {
		return XmlUtils.readXml2ValuePairMap(getFilePath());
	}

	@Override
	public void reLoaderData(Map<String, Collection<ValuePair>> t) {
		t = XmlUtils.readXml2ValuePairMap(getFilePath());
	}

	@Override
	public void reloadValuePairs(Map<String, Collection<ValuePair>> valuePairMap,String sheetName) {
		valuePairMap.put(sheetName, XmlUtils.readXml2ValuePairs(getFilePath(), sheetName));		
	}

	@Override
	public void reloadValuePair(ValuePair value, String sheetName) {
		
		Collection<ValuePair> valuePairs = XmlUtils.readXml2ValuePairs(getFilePath(), sheetName);
		for(ValuePair valuePair:valuePairs){
			if(value.get(getUnionKey()).equals(valuePair.get(getUnionKey()))){
				value = valuePair;
				break;
			}
		}
		
	}
	


}
