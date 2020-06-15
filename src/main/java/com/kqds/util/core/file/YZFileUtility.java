package com.kqds.util.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YZFileUtility
{
  private static void loadLine2Array(String fileName, int startLine, int endLine, List rtList, String charSet)
    throws Exception
  {
    InputStream ins = null;
    try
    {
      File file = new File(fileName);
      if (!file.exists()) {
        return;
      }
      ins = new FileInputStream(file);
      loadLine2Array(ins, startLine, endLine, rtList, charSet);
    }
    catch (Exception ex)
    {
      throw ex;
    }
    finally
    {
      try
      {
        if (ins != null) {
          ins.close();
        }
      }
      catch (Exception localException2) {}
    }
    try
    {
      if (ins != null) {
        ins.close();
      }
    }
    catch (Exception localException3) {}
  }
  
  private static void loadLine2Array(String fileName, List rtList)
    throws Exception
  {
    loadLine2Array(fileName, 0, 2147483647, rtList, "UTF-8");
  }
  
  private static void loadLine2Array(InputStream in, int startLine, int endLine, List rtList, String charSet)
    throws Exception
  {
    LineNumberReader reader = new LineNumberReader(new InputStreamReader(in, charSet));
    String str = null;
    for (int i = 0; (str = reader.readLine()) != null; i++) {
      if (i >= startLine)
      {
        if (i > endLine) {
          break;
        }
        rtList.add(str);
      }
    }
  }
  
  public static void load2Map(String fileName, Map rtMap)
    throws Exception
  {
    ArrayList propList = new ArrayList();
    
    loadLine2Array(fileName, propList);
    for (int i = 0; i < propList.size(); i++)
    {
      String line = propList.get(i).toString().trim();
      if ((line.length() >= 1) && (!line.startsWith("#")))
      {
        int tmpIndex = line.indexOf("=");
        if (tmpIndex >= 0)
        {
          String name = line.substring(0, tmpIndex).trim();
          String value = line.substring(tmpIndex + 1).trim();
          rtMap.put(name, value);
        }
      }
    }
  }
  
  public static String getFileName(String filePath)
  {
    if (filePath == null) {
      return null;
    }
    int startIndex = 0;
    if (filePath.lastIndexOf("\\") >= 0) {
      startIndex = filePath.lastIndexOf("\\") + 1;
    } else if (filePath.lastIndexOf("/") >= 0) {
      startIndex = filePath.lastIndexOf("/") + 1;
    }
    return filePath.substring(startIndex, filePath.length());
  }
}
