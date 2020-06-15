package com.kqds.util.sys.interceptor;

import java.lang.reflect.Field;

public class InterceptorTool
{
  public static <T> T getFieldValue(Object obj, String fieldName)
  {
    Object result = null;
    Field field = getField(obj, fieldName);
    if (field != null)
    {
      field.setAccessible(true);
      try
      {
        result = field.get(obj);
      }
      catch (IllegalArgumentException e)
      {
        e.printStackTrace();
      }
      catch (IllegalAccessException e)
      {
        e.printStackTrace();
      }
    }
    return result;
  }
  
  private static Field getField(Object obj, String fieldName)
  {
    Field field = null;
    for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
      try
      {
        field = clazz.getDeclaredField(fieldName);
      }
      catch (NoSuchFieldException localNoSuchFieldException) {}
    }
    return field;
  }
}
