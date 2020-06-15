package com.hudh.lclj.util;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseOperateXml
{
  private static final Logger logger = LoggerFactory.getLogger(ParseOperateXml.class);
  private static Map<String, Map<String, String>> dataMap;
  
  private static synchronized void loadXmls()
  {
    if (dataMap == null)
    {
      dataMap = new HashMap();
      try
      {
        String path = ParseOperateXml.class.getClassLoader().getResource("com/hudh/lclj/util/hudh_lclj_nodes.xml").getPath();
        SAXReader reader = new SAXReader();
        Document dom = reader.read(new File(path));
        Element ele_root = dom.getRootElement();
        List<Element> eles = ele_root.elements();
        for (Element el : eles)
        {
          List<Element> eles2 = el.elements();
          Map<String, String> tempMap = new HashMap();
          for (Element el2 : eles2) {
            tempMap.put(el2.getName(), el2.getText());
          }
          dataMap.put(el.getName(), tempMap);
        }
      }
      catch (Exception e)
      {
        logger.error("文件获取异常！");
      }
    }
  }
  
  public static Map<String, String> getOperateMap(String key)
  {
    if (dataMap == null) {
      loadXmls();
    }
    return (Map)dataMap.get(key);
  }
}
