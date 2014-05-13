package com.elephant.framework.templates.xml;

import com.elephant.framework.templates.AbstractDefaultValuePairLoader;
import com.elephant.framework.templates.ValuePair;
import com.elephant.framework.utils.XmlUtils;

public abstract class AbstractXmlValuePairLoader extends AbstractDefaultValuePairLoader {

	@Override
	public ValuePair loaderData() {
		return XmlUtils.readXml2ValuePair(getFilePath());
	}

	@Override
	public void reLoaderData(ValuePair valuePair) {
		valuePair = XmlUtils.readXml2ValuePair(getFilePath());
	}

}
