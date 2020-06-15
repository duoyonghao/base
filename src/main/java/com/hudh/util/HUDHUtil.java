package com.hudh.util;

import com.kqds.util.sys.YZUtility;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class HUDHUtil
{
  public static String getCurrentTime(String dataFormat)
  {
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
    return sdf.format(date);
  }
  
  public static List parseJsonToObjectList(String jsonStr, Class clazz)
  {
    if ((jsonStr != null) && (clazz != null))
    {
      JSONArray jsonArray = JSONArray.fromObject(jsonStr);
      List list = (List)JSONArray.toCollection(jsonArray, clazz);
      return list;
    }
    return null;
  }
  
  public static List parseJsonToObjectList(String jsonStr, Class clazz, Map<String, List<Class>> childClazzMap)
  {
    if ((jsonStr != null) && (clazz != null))
    {
      JSONArray jsonArray = JSONArray.fromObject(jsonStr);
      
      JsonConfig config = new JsonConfig();
      config.setRootClass(clazz);
      config.setClassMap(childClazzMap);
      
      List list = (List)JSONArray.toCollection(jsonArray, config);
      return list;
    }
    return null;
  }
  
  public static Object parseJsonToObject(String jsonStr, Class clazz)
  {
    if ((jsonStr != null) && (clazz != null))
    {
      JSONObject jsonObject = JSONObject.fromObject(jsonStr);
      Object obj = JSONObject.toBean(jsonObject, clazz);
      return obj;
    }
    return null;
  }
  
  public static int shiJianCha(String startTime, String endTime)
    throws ParseException
  {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if ((YZUtility.isNotNullOrEmpty(startTime)) && (YZUtility.isNotNullOrEmpty(endTime)))
    {
      Date begin = df.parse(startTime);
      Date end = df.parse(endTime);
      long between = (end.getTime() - begin.getTime()) / 1000L;
      int day = (int)(between / 86400L);
      return day;
    }
    return 0;
  }
}
