package com.elephant.framework.templates.cvs;

import java.io.IOException;
import java.util.Collection;

import com.elephant.framework.templates.AbstractDefaultValuePairsLoader;
import com.elephant.framework.templates.IFTemplatesValuePairsLoader;
import com.elephant.framework.templates.ValuePair;
import com.elephant.framework.utils.CSVUtils;

public abstract class AbstractCVSValuePairsLoader extends AbstractDefaultValuePairsLoader implements IFTemplatesValuePairsLoader {


	@Override
	public Collection<ValuePair> loaderData() {
		try {
			return CSVUtils.readCsv2ValuePairs(getFilePath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void reLoaderData(Collection<ValuePair> values) {
		try {
			Collection<ValuePair> _values = CSVUtils.readCsv2ValuePairs(getFilePath());
			values = _values;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void reloadValuePair(ValuePair value) {
		try {
			Collection<ValuePair> _values = CSVUtils.readCsv2ValuePairs(getFilePath());
			for(ValuePair valuePair:_values){
				if(value.get(getUnionKey()).equals(valuePair.get(getUnionKey()))){
					value = valuePair;
					break;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
