package com.kqds.util.sys.connection;

public class DataSourceSwitch
{
  private static final ThreadLocal contextHolder = new ThreadLocal();
  
  public static void setDataSourceType(String dataSourceType)
  {
    contextHolder.set(dataSourceType);
  }
  
  public static void reset()
  {
    contextHolder.set("KQDS");
  }
  
  public static String getDataSourceType()
  {
    if (contextHolder.get() == null) {
      contextHolder.set("KQDS");
    }
    return (String)contextHolder.get();
  }
  
  public static void clearDataSourceType()
  {
    contextHolder.remove();
  }
}
