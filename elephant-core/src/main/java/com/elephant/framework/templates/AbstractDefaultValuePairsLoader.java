package com.elephant.framework.templates;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractDefaultValuePairsLoader implements IFTemplatesLoader<Collection<ValuePair>> {


	@Override
	public void toLuaFile(String path,String var) throws Exception {
		StringBuilder builder = new StringBuilder(" local  ").append(var).append(" = {\r\n");
		Collection<ValuePair>  valuePairs = loaderData() ;
		for(ValuePair valuePair:valuePairs){
			builder.append("[").append(valuePair.get(getUnionKey())).append(" ] = { ");
			for(Entry<String, Object> entry:valuePair.getSet().entrySet()){
				builder.append(entry.getKey()).append("=").append("\"").append(entry.getValue()).append("\"").append(",");
			}
			builder.append("},\r\n");
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
		Collection<ValuePair>  valuePairs = loaderData() ;
		Collection<Map<String,Object>> maps = new ArrayList<Map<String,Object>>(valuePairs.size());
		for(ValuePair valuePair:valuePairs){
			maps.add(valuePair.getSet());
		}
		objectMapper.writeValue(new File(path),maps);
	}

}
