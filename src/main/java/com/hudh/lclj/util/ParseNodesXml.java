package com.hudh.lclj.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import com.hudh.lclj.StaticVar;
import com.hudh.lclj.entity.LcljNode;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 解析xml获取流程节点
 * @author XKY
 *
 */
public class ParseNodesXml {
	private static final Logger logger = LoggerFactory.getLogger(ParseNodesXml.class);
	private static Map<String,List<LcljNode>> dataMap;
	
	@SuppressWarnings("unchecked")
	synchronized private static void loadXmls() {
//		if(null == list) {
			dataMap = new HashMap<String,List<LcljNode>>();
			try {
				String path = ParseNodesXml.class.getClassLoader().getResource(StaticVar.HUDH_LCLJ_FILEPATH).getPath();
				SAXReader reader = new SAXReader();              
				Document dom = reader.read(new File(path));
				Element ele_root=dom.getRootElement();//获取根节点
				List<Element> eles = ele_root.elements();
				for(Element el : eles) {
					List<Element> eles2 =  el.elements();
					List<LcljNode> list = new ArrayList<LcljNode>();
					LcljNode lcljNode = null;
					for(Element el2 : eles2){
						lcljNode = new LcljNode();
						lcljNode.setNum(el2.attributeValue("num"));
						lcljNode.setName(el2.elementText("name"));
						lcljNode.setLimit(el2.elementText("limit"));
						lcljNode.setStus(StaticVar.HUDH_LCLJ_FLOWSTA_WWC);
						list.add(lcljNode);
					}
					dataMap.put(el.getName(), list);
				}
			} catch (Exception e) {
				logger.error("文件获取异常！");
			}
//		}	
		
	}
	
	public static List<LcljNode> getOperateMap(String key){
		if(null == dataMap) {
			loadXmls();
		}
		return dataMap.get(key);
    }
	
}
