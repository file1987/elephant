package com.elephant.framework.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.elephant.framework.exception.ReadDataException;
import com.elephant.framework.templates.ValuePair;

public final class XmlUtils {

	public static ValuePair readXml2ValuePair(String path) {
		ValuePair valuePair = new ValuePair();
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(path));
			Element root = document.getRootElement();
			Element childs = root.element("childs");
			if (childs == null)
				childs = root;
			Element child = childs.element("child");
			if (child == null)
				throw new ReadDataException("xml 数据模板不正确！");
			for (@SuppressWarnings("unchecked")
			Iterator<Element> it = child.elementIterator(); it.hasNext();) {
				Element prop = it.next();
				valuePair.set(prop.attributeValue("key"),prop.attributeValue("value"));
			}
			return valuePair;
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new ReadDataException(e);
		}
	}

	
	@SuppressWarnings("unchecked")
	public static Collection<ValuePair> readXml2ValuePairs(String path,String id) {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(path));
			Element root = document.getRootElement();
			for(Iterator<Element> childsIt=root.elementIterator();childsIt.hasNext();){
				Element childs = childsIt.next();
				String _id = childs.attributeValue("id");
				if(_id==null)
					throw new ReadDataException("xml 数据模板不正确！请在childs元素加个id属性！");
				if(_id.equals(id)){				
					Collection<ValuePair> valuePairs = new ArrayList<ValuePair>();
					for(Iterator<Element> childIt=childs.elementIterator();childIt.hasNext();){
						Element child = childIt.next();
						ValuePair valuePair = new ValuePair();
						for (Iterator<Element> it = child.elementIterator(); it.hasNext();) {
							Element prop = it.next();
							valuePair.set(prop.attributeValue("key"),prop.attributeValue("value"));
						}
						if(valuePair.getSet().isEmpty())
							throw new ReadDataException("xml 数据模板不正确！"+child.getName()+ " 元素位置不正确，正确模板结构：<root><childs id=\"xxxx\"><child><prop></prop>....<child>...</childs>...</root>");
						valuePairs.add(valuePair);
					}
					if(valuePairs.isEmpty()){
						throw new ReadDataException("xml 数据模板不正确！"+childs.getName()+ " 元素位置不正确，正确模板结构：<root><childs id=\"xxxx\"><child><prop></prop>....<child>...</childs>...</root>");
					}
					return valuePairs;
				}
			}
			return null;
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new ReadDataException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<ValuePair> readXml2ValuePairs(String path) {
		Collection<ValuePair> valuePairs = new ArrayList<ValuePair>();
		
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(path));
			Element root = document.getRootElement();
			boolean isChilds = true;
			List<Element> childs = root.elements("childs");
			if(childs==null||childs.isEmpty()){
				childs = root.elements("child");
				isChilds = false;
			}
			if(isChilds&&childs.size()==1){
				childs = childs.get(0).elements("child");
				isChilds = false;
			}
			if (childs == null)
				throw new ReadDataException("xml 数据模板不正确！");
			for (Element child:childs) {
				if(isChilds)
					child = child.element("child");
				if (child == null)
					throw new ReadDataException("xml 数据模板不正确！");
				ValuePair valuePair = new ValuePair();
				for (Iterator<Element> it = child.elementIterator(); it.hasNext();) {
					Element prop = it.next();
					valuePair.set(prop.attributeValue("key"),prop.attributeValue("value"));
				}
				valuePairs.add(valuePair);
			}
			
			return valuePairs;
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new ReadDataException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Collection<ValuePair>> readXml2ValuePairMap(String path) {

		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(path));
			Element root = document.getRootElement();
			Map<String, Collection<ValuePair>> maps = new HashMap<String, Collection<ValuePair>>(root.nodeCount());
			for(Iterator<Element> childsIt=root.elementIterator();childsIt.hasNext();){
				Element childs = childsIt.next();
				String id = childs.attributeValue("id");
				if(id==null)
					throw new ReadDataException("xml 数据模板不正确！请在childs元素加个id属性！");
				Collection<ValuePair> valuePairs = new ArrayList<ValuePair>();
				for(Iterator<Element> childIt=childs.elementIterator();childIt.hasNext();){
					Element child = childIt.next();
					ValuePair valuePair = new ValuePair();
					for (Iterator<Element> it = child.elementIterator(); it.hasNext();) {
						Element prop = it.next();
						valuePair.set(prop.attributeValue("key"),prop.attributeValue("value"));
					}
					if(valuePair.getSet().isEmpty())
						throw new ReadDataException("xml 数据模板不正确！"+child.getName()+ " 元素位置不正确，正确模板结构：<root><childs id=\"xxxx\"><child><prop></prop>....<child>...</childs>...</root>");
					valuePairs.add(valuePair);
				}
				if(valuePairs.isEmpty()){
					throw new ReadDataException("xml 数据模板不正确！"+childs.getName()+ " 元素位置不正确，正确模板结构：<root><childs id=\"xxxx\"><child><prop></prop>....<child>...</childs>...</root>");
				}
				maps.put(id, valuePairs);
			}
			return maps;
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new ReadDataException(e);
		}
	}

}
