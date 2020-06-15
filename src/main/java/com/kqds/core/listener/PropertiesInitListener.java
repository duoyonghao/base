package com.kqds.core.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PropertiesInitListener
  implements ServletContextListener
{
  public void contextDestroyed(ServletContextEvent arg0) {}
  
  public void contextInitialized(ServletContextEvent event)
  {
    com.kqds.util.sys.ConstUtil.ROOT_DIR = event.getServletContext().getRealPath("/");
    com.kqds.util.sys.ConstUtil.ROOT_DIR_STATIC = event.getServletContext().getRealPath("/static");
    

    SystemInitUtil.sysStartInit();
  }
}
