package com.kqds.util.wx;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MessageUtil
{
  public static final String EVENT_TYPE_CREATE_DOC = "createdoc";
  public static final String EVENT_TYPE_OPEN_TALK = "opentalk";
  public static final String EVENT_TYPE_CLOSE_TALK = "colsetalk";
  public static final String RESP_MESSAGE_TYPE_TEXT = "text";
  public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
  public static final String RESP_MESSAGE_TYPE_NEWS = "news";
  public static final String REQ_MESSAGE_TYPE_TEXT = "text";
  public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
  public static final String REQ_MESSAGE_TYPE_LINK = "link";
  public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
  public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
  public static final String REQ_MESSAGE_TYPE_EVENT = "event";
  public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
  public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
  public static final String EVENT_TYPE_CLICK = "CLICK";
  public static final String EVENT_TYPE_VIEW = "VIEW";
  public static final String EVENT_TYPE_SCAN = "SCAN";
  
  public static Map<String, String> parseReuest4Map(HttpServletRequest request)
  {
    Map properties = request.getParameterMap();
    
    Map returnMap = new HashMap();
    Iterator entries = properties.entrySet().iterator();
    
    String name = "";
    String value = "";
    while (entries.hasNext())
    {
      Entry entry = (Entry)entries.next();
      name = (String)entry.getKey();
      Object valueObj = entry.getValue();
      if (valueObj == null)
      {
        value = "";
      }
      else if ((valueObj instanceof String[]))
      {
        String[] values = (String[])valueObj;
        for (int i = 0; i < values.length; i++) {
          value = values[i] + ",";
        }
        value = value.substring(0, value.length() - 1);
      }
      else
      {
        value = valueObj.toString();
      }
      returnMap.put(name, value);
    }
    return returnMap;
  }
  
  public static Map<String, String> parseXml(HttpServletRequest request)
    throws Exception
  {
    Map<String, String> map = new HashMap();
    

    InputStream inputStream = request.getInputStream();
    
    SAXReader reader = new SAXReader();
    Document document = reader.read(inputStream);
    
    Element root = document.getRootElement();
    
    List<Element> elementList = root.elements();
    for (Element e : elementList) {
      map.put(e.getName(), e.getText());
    }
    inputStream.close();
    inputStream = null;
    
    return map;
  }
}
