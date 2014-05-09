package com.elephant.framework.templates;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractDefaultLoader<T> implements IFTemplatesLoader<T> {

	
	@Override
	public void toLuaFile(String path,String var) throws Exception {
	}

	@Override
	public void toJsonFile(String path) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(new File(path),loaderData());
	}

}
