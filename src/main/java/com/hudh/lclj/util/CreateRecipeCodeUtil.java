package com.hudh.lclj.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hudh.lclj.dao.LcljTrackDao;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.kqds.entity.base.KqdsChufang;
import com.kqds.service.base.chufang.KQDS_ChuFangLogic;

public class CreateRecipeCodeUtil {
	
	@Autowired
	private static KQDS_ChuFangLogic kqds_ChuFangLogic;
	private static String orderNumber;
	
	private static final String orderNumber_Prefix = "HUDHCF"; //编号前缀字母
	private static final int orderNumber_length = 6; //编号数字长度
	private static final String beginNumber = "000001"; //初始数字编号
	
	private CreateRecipeCodeUtil(){};
	private static volatile CreateRecipeCodeUtil instance;
	
	/**
	 * 单例模式保证多线程下不会拿到重复的编号
	 * @return
	 */
	public static synchronized CreateRecipeCodeUtil getInstance(){
		if(null == instance) {
			instance = new CreateRecipeCodeUtil();
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			kqds_ChuFangLogic = wac.getBean(KQDS_ChuFangLogic.class);
		}
		return instance;
	}
	
	public static String getNextOrderNumber() throws Exception{
		String templeNumber;
		if (null == orderNumber) {
			KqdsChufang kqdsChufang = new KqdsChufang();
			kqdsChufang = kqds_ChuFangLogic.findNextOrderNumber();
			if(null == kqdsChufang) {
				return orderNumber_Prefix + beginNumber;
			}
			templeNumber = kqdsChufang.getRecipecode();
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
