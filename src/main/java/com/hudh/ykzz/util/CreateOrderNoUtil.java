package com.hudh.ykzz.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hudh.ykzz.dao.IYkzzDrugsDao;
import com.hudh.ykzz.entity.YkzzDrugs;

public class CreateOrderNoUtil {
	
	@Autowired
	private static IYkzzDrugsDao iyYkzzDrugsDao;
	private static String orderNumber;
	
	private static final String orderNumber_Prefix = "DRUG"; //编号前缀字母
	private static final int orderNumber_length = 6; //编号数字长度
	private static final String beginNumber = "000001"; //初始数字编号
	
	private CreateOrderNoUtil(){};
	private static volatile CreateOrderNoUtil instance;
	
	/**
	 * 单例模式保证多线程下不会拿到重复的编号
	 * @return
	 */
	public static synchronized CreateOrderNoUtil getInstance(){
		if(null == instance) {
			instance = new CreateOrderNoUtil();
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			iyYkzzDrugsDao = wac.getBean(IYkzzDrugsDao.class);
		}
		return instance;
	}
	
	public static String getNextOrderNumber() throws Exception{
		String templeNumber;
		if(null == orderNumber) {
			YkzzDrugs ykzzDrugs = new YkzzDrugs();
			ykzzDrugs = iyYkzzDrugsDao.findNextOrderNumber();
			if(null == ykzzDrugs) {
				return orderNumber_Prefix + beginNumber;
			}
			templeNumber = ykzzDrugs.getOrder_no();
			templeNumber = templeNumber.replace(orderNumber_Prefix,"");
			orderNumber = orderNumber_Prefix + String.format("%0"+orderNumber_length+"d", Integer.parseInt(templeNumber) + 1); //数字部分加1并格式化到成指定长度+前缀
			return orderNumber;
		} else {
			orderNumber = orderNumber.replace(orderNumber_Prefix,"");
			orderNumber = orderNumber_Prefix + String.format("%0"+orderNumber_length+"d", Integer.parseInt(orderNumber) + 1);
			return orderNumber;
		}
	}

}
