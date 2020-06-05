/**  
  *
  * @Title:  IDataAnalysisService.java   
  * @Package com.hudh.sjtj.service   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年9月23日 下午2:32:11   
  * @version V1.0  
  */ 
package com.hudh.sjtj.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  IDataAnalysisService   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年9月23日 下午2:32:11   
  *      
  */
public interface IDataAnalysisService {
	
	List<JSONObject> findBaseDataAskperson(HttpServletRequest request, Map<String, String> dataMap) throws Exception;
	/**
	 * 查询咨询基础数据月数据
	 * <p>Title: findCJStatisticsByMonth</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月3日 
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findCJStatisticsByMonth(HttpServletRequest request, Map<String, String> map) throws Exception;
	/**
	 * 查询咨询基础数据天数据
	 * <p>Title: findCJStatisticsByDay</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月3日 
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findCJStatisticsByDay(HttpServletRequest request, Map<String, String> map) throws Exception;
	/**
	 * 查询咨询月的员工数据
	 * <p>Title: findAllCJStatisticsByMonth</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月3日 
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findAllCJStatisticsByMonth(HttpServletRequest request, Map<String, String> map) throws Exception;
	/**
	 * 查询咨询天的员工数据
	 * <p>Title: findAllCJStatisticsByDay</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月3日 
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findAllCJStatisticsByDay(HttpServletRequest request, Map<String, String> map) throws Exception;
	
	List<JSONObject> findTotalMoneyByMonth(Map<String, String> dataMap, HttpServletRequest request) throws Exception;

	/**   
	  * @Title: findCjsCale   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	  * @dateTime:2019年9月27日 上午10:57:20
	  */  
	List<JSONObject> findCjsCale(HttpServletRequest request,Map<String, String> map) throws Exception;

	/**   
	  * @Title: findDepartment   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param map
	  * @param: @return      
	  * @return: JSONObject
	  * @dateTime:2019年9月28日 下午3:25:44
	  */  
	List<JSONObject> findDepartment(HttpServletRequest request, Map<String, String> map) throws Exception;

	/**   
	  * @Title: findImplant   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	  * @dateTime:2019年9月29日 下午2:50:01
	  */  
	List<JSONObject> findImplant(HttpServletRequest request, Map<String, String> map)throws Exception;

	/**   
	  * @Title: finddoctor   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	  * @dateTime:2019年9月29日 下午3:02:50
	  */  
	List<JSONObject> finddoctor(HttpServletRequest request, Map<String, String> map) throws Exception;
	/**
	 * 查询咨询基础数据年
	 * <p>Title: findCJStatisticsByYear</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月3日 
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findCJStatisticsByYear(HttpServletRequest request, Map<String, String> map) throws Exception;
	/**
	 * 查询以月为单位的明细数据
	 * <p>Title: findCJQuantityByAskpersonAndMonthInYear</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月4日 
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	List<JSONObject> findCJQuantityByAskpersonAndMonthInYear(HttpServletRequest request, Map<String, String> map) throws Exception;
	/**
	 * 查询以年为单位的明细数据
	 * <p>Title: findAllCJStatisticsByYear</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月5日 
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findAllCJStatisticsByYear(HttpServletRequest request, Map<String, String> map) throws Exception;
	/**   
	  * @Title: Devchannel   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	  * @dateTime:2019年10月5日 上午10:16:30
	  */  
	List<JSONObject> Devchannel(HttpServletRequest request, Map<String, String> map) throws Exception;
	/**   
	  * @Title: consumptionInterval   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param map
	  * @param: @return      
	  * @return: List<JSONObject>
	  * @dateTime:2019年10月6日 上午9:53:37
	  */  
	List<JSONObject> consumptionInterval(HttpServletRequest request, Map<String, String> map) throws Exception;

}
