package com.hudh.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 常用工具类
 * @author XKY
 *
 */
public class HUDHUtil {
	
	/**
	 * 返回固定格式当前日期时间
	 * @param dataFormat 日期格式
	 * @return
	 */
	public static String getCurrentTime(String dataFormat){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
		return sdf.format(date);
	}
	
	/**
	 * 将Json字符串转成Java对象集合
	 */
	@SuppressWarnings("rawtypes")
	public static List parseJsonToObjectList(String jsonStr, Class clazz){
		if(null != jsonStr && null != clazz) {
			JSONArray jsonArray = JSONArray.fromObject(jsonStr);
			List list= (List) JSONArray.toCollection(jsonArray, clazz);
			return list;
		}
		return null;
	}
	
	/**
	 * 将Json字符串转成Java对象集合(包含对象集合子属性)
	 */
	@SuppressWarnings("rawtypes")
	public static List parseJsonToObjectList(String jsonStr, Class clazz,Map<String,List<Class>> childClazzMap){
		if(null != jsonStr && null != clazz) {
			JSONArray jsonArray = JSONArray.fromObject(jsonStr);
			
			JsonConfig config = new JsonConfig();
			config.setRootClass(clazz);
			config.setClassMap(childClazzMap);
			
			List list= (List) JSONArray.toCollection(jsonArray, config);
			return list;
		}
		return null;
	}
	
	/**
	 * 将Json字符串转成Java对象
	 */
	@SuppressWarnings("rawtypes")
	public static Object parseJsonToObject(String jsonStr, Class clazz){
		if(null != jsonStr && null != clazz) {
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			Object obj = (Object)JSONObject.toBean(jsonObject, clazz);
			return obj;
		}
		return null;
	}
	/**
	 * 计算2个日期的时间差 传入格式 yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException 
	 */
	public static int shiJianCha(String startTime,String endTime) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat(HUDHStaticVar.DATE_FORMAT_YMDHMS24);
		if(YZUtility.isNotNullOrEmpty(startTime) && YZUtility.isNotNullOrEmpty(endTime)) {
			Date begin=(Date) df.parse(startTime);   
			Date end = (Date) df.parse(endTime);   
			long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒   
			int  day=(int) (between/(24*3600)); 
			return day;
		}
		return 0;
	}
}