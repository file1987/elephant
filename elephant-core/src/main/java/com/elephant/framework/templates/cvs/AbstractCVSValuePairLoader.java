package com.elephant.framework.templates.cvs;

import java.io.IOException;

import com.elephant.framework.templates.IFTemplatesValuePairLoader;
import com.elephant.framework.templates.ValuePair;
import com.elephant.framework.utils.CSVUtils;

public abstract class AbstractCVSValuePairLoader implements IFTemplatesValuePairLoader {

	@Override
	public ValuePair loaderValuePair() {
		try{
			return CSVUtils.readCsv2ValuePair(getFilePath());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void reloaderValuePair(ValuePair valuePair) {
		try {
			ValuePair _valuePair =  CSVUtils.readCsv2ValuePair(getFilePath());
			valuePair = _valuePair;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
