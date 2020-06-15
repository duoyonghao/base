package com.kqds.core.load;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class YZConfigLoader
{
  private static Properties props = new Properties();
  
  public static Properties loadSysProps(String sysPropsFile)
  {
    return loadSysProps(new File(sysPropsFile));
  }
  
  public static Properties loadSysProps(File sysPropsFile)
  {
    if (!sysPropsFile.exists()) {
      return props;
    }
    InputStream inProps = null;
    try
    {
      inProps = new BufferedInputStream(new FileInputStream(sysPropsFile));
      props.load(inProps);
    }
    catch (IOException localIOException)
    {
      try
      {
        if (inProps != null) {
          inProps.close();
        }
      }
      catch (IOException localIOException1) {}
    }
    finally
    {
      try
      {
        if (inProps != null) {
          inProps.close();
        }
      }
      catch (IOException localIOException2) {}
    }
    return props;
  }
  
  public static Properties loadSysProps(List<String> fileList)
  {
    for (String filePath : fileList) {
      loadSysProps(filePath);
    }
    return props;
  }
}
