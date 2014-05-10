package com.elephant.framework.templates.excel;

import java.util.Collection;
import java.util.Map;

import com.elephant.framework.templates.AbstractDefaultValuePairMapLoader;
import com.elephant.framework.templates.IFTemplatesValuePairMapLoader;
import com.elephant.framework.templates.ValuePair;
import com.elephant.framework.utils.ExcelUtils;

public abstract class AbstractExcelValuePairMapLoader extends AbstractDefaultValuePairMapLoader implements IFTemplatesValuePairMapLoader {

	@Override
	public Map<String, Collection<ValuePair>> loaderData() {
		return ExcelUtils.readExcel2ValuePairMap(getFilePath());
	}

	@Override
	public void reLoaderData(Map<String, Collection<ValuePair>> t) {
		t = ExcelUtils.readExcel2ValuePairMap(getFilePath());
	}

	@Override
	public void reloadValuePairs(Map<String, Collection<ValuePair>> valuePairMap,String sheetName) {
		valuePairMap.put(sheetName, ExcelUtils.readExcel2ValuePairs(getFilePath(), sheetName));		
	}

	@Override
	public void reloadValuePair(ValuePair value, String sheetName) {
		
		Collection<ValuePair> valuePairs = ExcelUtils.readExcel2ValuePairs(getFilePath(), sheetName);
		for(ValuePair valuePair:valuePairs){
			if(value.get(getUnionKey()).equals(valuePair.get(getUnionKey()))){
				value = valuePair;
				break;
			}
		}
		
	}
	


}
