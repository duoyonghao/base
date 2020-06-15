package com.kqds.util.sys.other;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@DependsOn({"springContextUtils"})
@Component
public class CacheContextUtil implements ApplicationContextAware {
  private static ApplicationContext commonApplicationContext = (ApplicationContext)new ClassPathXmlApplicationContext("spring-config.xml");
  
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    commonApplicationContext = applicationContext;
  }
  
  public static Object getBean(String beanId) {
    return commonApplicationContext.getBean(beanId);
  }
  
  public static <T> T getBean(String beanId, Class<T> clazz) {
    return (T)commonApplicationContext.getBean(beanId, clazz);
  }
}
