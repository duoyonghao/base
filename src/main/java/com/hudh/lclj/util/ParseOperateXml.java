package com.hudh.lclj.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import com.hudh.lclj.StaticVar;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 解析xml获取不同临床路径操作项
 * @author XKY
 *
 */
public class ParseOperateXml {
	private static final Logger logger = LoggerFactory.getLogger(ParseOperateXml.class);
	private static Map<String,Map<String,String>> dataMap;
	
	synchronized private static void loadXmls() {
		if(null == dataMap) {
			dataMap = new HashMap<String,Map<String,String>>();
			try {
				String path = ParseOperateXml.class.getClassLoader().getResource(StaticVar.HUDH_LCLJ_FILEPATH).getPath();
				SAXReader reader = new SAXReader();              
				Document dom = reader.read(new File(path));
				Element ele_root=dom.getRootElement();//获取根节点
				List<Element> eles = ele_root.elements();
				for(Element el : eles) {
					List<Element> eles2 =  el.elements();
					Map<String,String> tempMap = new HashMap<String,String>();
					for(Element el2 : eles2){
						tempMap.put(el2.getName(), el2.getText());
					}
					dataMap.put(el.getName(), tempMap);
				}
			} catch (Exception e) {
				logger.error("文件获取异常！");
			}
		}	
		
	}
	
	public static Map<String,String> getOperateMap(String key){
		if(null == dataMap) {
			loadXmls();
		}
		return dataMap.get(key);
    }
	
}
