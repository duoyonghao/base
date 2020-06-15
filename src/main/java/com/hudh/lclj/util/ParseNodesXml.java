package com.hudh.lclj.util;

import com.hudh.lclj.entity.LcljNode;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseNodesXml
{
  private static final Logger logger = LoggerFactory.getLogger(ParseNodesXml.class);
  private static Map<String, List<LcljNode>> dataMap;
  
  private static synchronized void loadXmls()
  {
    dataMap = new HashMap();
    try
    {
      String path = ParseNodesXml.class.getClassLoader().getResource("com/hudh/lclj/util/hudh_lclj_nodes.xml").getPath();
      SAXReader reader = new SAXReader();
      Document dom = reader.read(new File(path));
      Element ele_root = dom.getRootElement();
      List<Element> eles = ele_root.elements();
      for (Element el : eles)
      {
        List<Element> eles2 = el.elements();
        List<LcljNode> list = new ArrayList();
        LcljNode lcljNode = null;
        for (Element el2 : eles2)
        {
          lcljNode = new LcljNode();
          lcljNode.setNum(el2.attributeValue("num"));
          lcljNode.setName(el2.elementText("name"));
          lcljNode.setLimit(el2.elementText("limit"));
          lcljNode.setStus("0");
          list.add(lcljNode);
        }
        dataMap.put(el.getName(), list);
      }
    }
    catch (Exception e)
    {
      logger.error("文件获取异常！");
    }
  }
  
  public static List<LcljNode> getOperateMap(String key)
  {
    if (dataMap == null) {
      loadXmls();
    }
    return (List)dataMap.get(key);
  }
}
