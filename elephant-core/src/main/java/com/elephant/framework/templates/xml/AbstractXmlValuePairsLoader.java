package com.elephant.framework.templates.xml;

import java.util.Collection;

import com.elephant.framework.templates.AbstractDefaultValuePairsLoader;
import com.elephant.framework.templates.IFTemplatesValuePairsLoader;
import com.elephant.framework.templates.ValuePair;
import com.elephant.framework.utils.XmlUtils;

public abstract class AbstractXmlValuePairsLoader extends AbstractDefaultValuePairsLoader  implements IFTemplatesValuePairsLoader{

	@Override
	public void reloadValuePair(ValuePair value) {
		Collection<ValuePair> _values = XmlUtils.readXml2ValuePairs(getFilePath());
		for(ValuePair valuePair:_values){
			if(value.get(getUnionKey()).equals(valuePair.get(getUnionKey()))){
				value = valuePair;
				break;
			}
		}
		
	}

	@Override
	public Collection<ValuePair> loaderData() {
		return XmlUtils.readXml2ValuePairs(getFilePath());
	}

	@Override
	public void reLoaderData(Collection<ValuePair> t) {
		t = XmlUtils.readXml2ValuePairs(getFilePath());
	}

}
