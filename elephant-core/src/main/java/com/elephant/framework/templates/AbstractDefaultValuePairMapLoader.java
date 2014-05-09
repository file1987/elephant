package com.elephant.framework.templates;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractDefaultValuePairMapLoader implements IFTemplatesLoader<Map<String,Collection<ValuePair>>> {


	@Override
	public void toLuaFile(String path,String var) throws Exception {
		
		StringBuilder builder = new StringBuilder(" local  ").append(var).append(" = {\r\n");
		Map<String,Collection<ValuePair>>  valuePairs = loaderData() ;
		
		for(Entry<String,Collection<ValuePair>> valuePair:valuePairs.entrySet()){
			builder.append("[").append(valuePair.getKey()).append(" ] = { \r\n ");
			for(ValuePair _valuePair:valuePair.getValue()){
				builder.append("[").append(_valuePair.get(getUnionKey())).append(" ] = { ");
				for(Entry<String, Object> entry:_valuePair.getSet().entrySet()){
					builder.append(entry.getKey()).append("=").append("\"").append(entry.getValue()).append("\"").append(",");
				}
				builder.append("},\r\n");
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
		Map<String,Collection<ValuePair>>  valuePairs = loaderData() ;
		Map<String,Collection<Map<String,Object>>> maps = new HashMap<String, Collection<Map<String,Object>>>(valuePairs.size());
		for(Entry<String,Collection<ValuePair>> valuePair:valuePairs.entrySet()){
			Collection<Map<String,Object>> lists = new ArrayList<Map<String,Object>>(valuePair.getValue().size());
			for(ValuePair v : valuePair.getValue()){
				lists.add(v.getSet());
			}
			maps.put(valuePair.getKey(), lists);
		}
		objectMapper.writeValue(new File(path),maps);
	}

}
