package com.elephant.framework.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.Ostermiller.util.CSVParser;
import com.elephant.framework.network.telnet.netty.NettyTelnetServer;
import com.elephant.framework.templates.ValuePair;

public class CSVUtils {
	
	public static ValuePair  readCsv2ValuePair(String path,char delimiter) throws IOException{
		String[][] valuess = CSVParser.parse(new FileReader(path),delimiter);
		ValuePair valuePair = new ValuePair();
		for(String[] values:valuess){
			if(values.length < 2)
				continue;
			valuePair.set(values[0],values[1]);
		}
		return valuePair;
	}
	
	public static ValuePair  readCsv2ValuePair(String path) throws IOException{
		String[][] valuess = CSVParser.parse(new FileReader(path));
		ValuePair valuePair = new ValuePair();
		for(String[] values:valuess){
			if(values.length < 2)
				continue;
			valuePair.set(values[0],values[1]);
		}
		return valuePair;
	}
	
	public static Collection<ValuePair> readCsv2ValuePairs(String path,char delimiter) throws IOException{
		String[][] valuess = CSVParser.parse(new FileReader(path),delimiter);
		Collection<ValuePair> valuePairs = new ArrayList<ValuePair>();
		
		for(int i=1;i<valuess.length;i++){
			ValuePair valuePair = new ValuePair();
			for(int j=0;j<valuess[0].length;j++){
				valuePair.set(valuess[0][j],valuess[i][j]);
			}
			valuePairs.add(valuePair);
		}
		return valuePairs;
	}
	
	public static Collection<ValuePair> readCsv2ValuePairs(String path) throws IOException{
		String[][] valuess = CSVParser.parse(new FileReader(path));
		Collection<ValuePair> valuePairs = new ArrayList<ValuePair>();
		
		for(int i=1;i<valuess.length;i++){
			ValuePair valuePair = new ValuePair();
			for(int j=0;j<valuess[0].length;j++){
				valuePair.set(valuess[0][j],valuess[i][j]);
			}
			valuePairs.add(valuePair);
		}
		return valuePairs;
	}
	
	public static void main(String[] args) throws Exception {
		//readCsv2ValuePair("D://activity.csv");
		
		
		NettyTelnetServer telnetServer = new NettyTelnetServer(10000);
		telnetServer.startUp();
		
	}

}
