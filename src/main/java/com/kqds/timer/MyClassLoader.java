package com.kqds.timer;

import java.net.URL;
import java.net.URLDecoder;

public class MyClassLoader
  extends ClassLoader
{
  public static Class getClassByScn(String className)
  {
    Class myclass = null;
    try
    {
      myclass = Class.forName(className);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    return myclass;
  }
  
  public static String getPackPath(Object object)
  {
    if (object == null) {
      throw new IllegalArgumentException("参数不能为空！");
    }
    String clsName = object.getClass().getName();
    return clsName;
  }
  
  public static String getAppPath(Class cls)
  {
    if (cls == null) {
      throw new IllegalArgumentException("参数不能为空！");
    }
    ClassLoader loader = cls.getClassLoader();
    
    String clsName = cls.getName() + ".class";
    
    Package pack = cls.getPackage();
    String path = "";
    if (pack != null)
    {
      String packName = pack.getName();
      if ((packName.startsWith("java.")) || (packName.startsWith("javax."))) {
        throw new IllegalArgumentException("不要传送系统类！");
      }
      clsName = clsName.substring(packName.length() + 1);
      if (packName.indexOf(".") < 0)
      {
        path = packName + "/";
      }
      else
      {
        int start = 0;int end = 0;
        end = packName.indexOf(".");
        while (end != -1)
        {
          path = path + packName.substring(start, end) + "/";
          start = end + 1;
          end = packName.indexOf(".", start);
        }
        path = path + packName.substring(start) + "/";
      }
    }
    URL url = loader.getResource(path + clsName);
    
    String realPath = url.getPath();
    
    int pos = realPath.indexOf("file:");
    if (pos > -1) {
      realPath = realPath.substring(pos + 5);
    }
    pos = realPath.indexOf(path + clsName);
    realPath = realPath.substring(0, pos - 1);
    if (realPath.endsWith("!")) {
      realPath = realPath.substring(0, realPath.lastIndexOf("/"));
    }
    try
    {
      realPath = URLDecoder.decode(realPath, "utf-8");
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
    return realPath;
  }
}
