package com.elephant.framework.templates.excel;

import com.elephant.framework.templates.AbstractDefaultValuePairLoader;
import com.elephant.framework.templates.ValuePair;
import com.elephant.framework.utils.ExcelUtils;

public abstract class AbstractExcelValuePairLoader extends AbstractDefaultValuePairLoader {

	@Override
	public ValuePair loaderData() {
		return ExcelUtils.readExcel2ValuePair(getFilePath());
	}

	@Override
	public void reLoaderData(ValuePair valuePair) {
		valuePair = ExcelUtils.readExcel2ValuePair(getFilePath());
	}

}
