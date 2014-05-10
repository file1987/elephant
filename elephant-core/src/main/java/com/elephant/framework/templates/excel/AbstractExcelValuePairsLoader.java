package com.elephant.framework.templates.excel;

import java.util.Collection;

import com.elephant.framework.templates.AbstractDefaultValuePairsLoader;
import com.elephant.framework.templates.IFTemplatesValuePairsLoader;
import com.elephant.framework.templates.ValuePair;
import com.elephant.framework.utils.ExcelUtils;

public abstract class AbstractExcelValuePairsLoader extends AbstractDefaultValuePairsLoader  implements IFTemplatesValuePairsLoader{

	@Override
	public void reloadValuePair(ValuePair value) {
		Collection<ValuePair> _values = ExcelUtils.readExcel2ValuePairs(getFilePath());
		for(ValuePair valuePair:_values){
			if(value.get(getUnionKey()).equals(valuePair.get(getUnionKey()))){
				value = valuePair;
				break;
			}
		}
		
	}

	@Override
	public Collection<ValuePair> loaderData() {
		return ExcelUtils.readExcel2ValuePairs(getFilePath());
	}

	@Override
	public void reLoaderData(Collection<ValuePair> t) {
		t = ExcelUtils.readExcel2ValuePairs(getFilePath());
	}

}
