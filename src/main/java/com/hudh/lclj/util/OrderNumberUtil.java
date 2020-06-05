package com.hudh.lclj.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import com.hudh.lclj.dao.LcljDao;
import com.hudh.lclj.dao.LcljTrackDao;
import com.hudh.lclj.entity.LcljOrder;
import com.hudh.lclj.entity.LcljOrderTrack;
/**
 * 获取下一个临床路径编号
 * @author XKY
 *
 */
public class OrderNumberUtil {
	/**
	 * 临床路径数据保存dao
	 */
	@Autowired
	private static LcljTrackDao lcljTrackDao;
	private static String orderNumber;
	
	private static final String orderNumber_Prefix = "ss"; //编号前缀字母
	private static final int orderNumber_length = 6; //编号数字长度
	private static final String beginNumber = "000001"; //初始数字编号
	
	private OrderNumberUtil(){};
	private static volatile OrderNumberUtil instance;
	
	/**
	 * 单例模式保证多线程下不会拿到重复的编号
	 * @return
	 */
	public static synchronized OrderNumberUtil getInstance(){
		if(null == instance) {
			System.out.println("获取编号对象...");
			instance = new OrderNumberUtil();
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			lcljTrackDao = wac.getBean(LcljTrackDao.class);
		}
		return instance;
	}
	
	public static String getNextOrderNumber() throws Exception{
		synchronized (instance) {
			String templeNumber;
			if(null == orderNumber) {
				LcljOrderTrack lcljOrderTrack = new LcljOrderTrack();
				lcljOrderTrack = lcljTrackDao.findNextOrderNumber();
				if(null == lcljOrderTrack) {
					return orderNumber_Prefix + beginNumber;
				}
				templeNumber = lcljOrderTrack.getOrderNumber();
				templeNumber = templeNumber.replace(orderNumber_Prefix,"");
				orderNumber = orderNumber_Prefix + String.format("%0"+orderNumber_length+"d", Integer.parseInt(templeNumber) + 1); //数字部分加1并格式化到成指定长度+前缀
				return orderNumber;
			}else {
				orderNumber = orderNumber.replace(orderNumber_Prefix,"");
				orderNumber = orderNumber_Prefix + String.format("%0"+orderNumber_length+"d", Integer.parseInt(orderNumber) + 1);
				return orderNumber;
			}
        }
	}
}
