package com.kqds.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.kqds.util.sys.ConstUtil;

public class PropertiesInitListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		/** 全局变量赋值 **/
		ConstUtil.ROOT_DIR = event.getServletContext().getRealPath("/");
		ConstUtil.ROOT_DIR_STATIC = event.getServletContext().getRealPath("/static");

		/** 系统启动初始化 **/
		SystemInitUtil.sysStartInit();
	}

}
