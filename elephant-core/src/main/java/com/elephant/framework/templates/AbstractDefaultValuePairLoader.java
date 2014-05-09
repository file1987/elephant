package com.elephant.framework.templates;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractDefaultValuePairLoader implements IFTemplatesLoader<ValuePair> {


	@Override
	public void toLuaFile(String path,String var) throws Exception {
		StringBuilder builder = new StringBuilder(" local  ").append(var).append(" = {\r\n");
		Map<String,Object>  set = loaderData().getSet();
		for(Entry<String, Object> entry:set.entrySet()){
			builder.append(entry.getKey()).append("=").append("\"").append(entry.getValue()).append("\"").append(",\r\n");
		}
		builder.append("}");
		PrintWriter printWriter = new PrintWriter(path);
		printWriter.write(builder.toString());
		printWriter.flush();
		printWriter.close();
	}

	@Override
	public void toJsonFile(String path) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(new File(path),loaderData().getSet());
	}

}
