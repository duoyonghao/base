package com.kqds.util.sys.spring;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class BeanUtil
{
  private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);
  private static WebApplicationContext webctx = null;
  private static Map<String, Object> beanMap = new HashMap();
  
  public static Object getBean(String beanName)
  {
    Object bean = beanMap.get(beanName);
    if (bean != null) {
      return bean;
    }
    try
    {
      if (webctx == null) {
        webctx = ContextLoader.getCurrentWebApplicationContext();
      }
      if (webctx == null) {
        throw new Exception("获取webctx失败！");
      }
      bean = webctx.getBean(beanName);
      if (bean == null) {
        throw new Exception("获取bean失败，bean名称：" + beanName + "！");
      }
    }
    catch (Exception e)
    {
      logger.error(e.getMessage());
      e.printStackTrace();
      

      beanMap.put(beanName, bean);
    }
    return bean;
  }
}
