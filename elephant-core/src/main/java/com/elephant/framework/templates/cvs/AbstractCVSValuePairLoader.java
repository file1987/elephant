package com.elephant.framework.templates.cvs;

import java.io.IOException;

import com.elephant.framework.exception.ReadDataException;
import com.elephant.framework.templates.AbstractDefaultValuePairLoader;
import com.elephant.framework.templates.ValuePair;
import com.elephant.framework.utils.CSVUtils;

public abstract class AbstractCVSValuePairLoader extends AbstractDefaultValuePairLoader {

	@Override
	public ValuePair loaderData() {
		try{
			return CSVUtils.readCsv2ValuePair(getFilePath());
		}catch(Exception e){
			throw new ReadDataException(e);
		}
	}

	@Override
	public void reLoaderData(ValuePair valuePair) {
		try {
			ValuePair _valuePair =  CSVUtils.readCsv2ValuePair(getFilePath());
			valuePair = _valuePair;
		} catch (IOException e) {
			throw new ReadDataException(e);
		}
	}

}
