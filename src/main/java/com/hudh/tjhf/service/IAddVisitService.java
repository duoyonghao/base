/**  
  *
  * @Title:  IAddVisitService.java   
  * @Package com.hudh.tjhf.service   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年11月2日 上午9:05:26   
  * @version V1.0  
  */ 
package com.hudh.tjhf.service;
import java.util.List;
import java.util.Map;

import com.hudh.tjhf.entity.VisitPlanTemplate;
import com.hudh.tjhf.entity.VisitTemplate;
import com.kqds.entity.sys.YZPara;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  IAddVisitService   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年11月2日 上午9:05:26   
  *      
  */
public interface IAddVisitService {
	

	/**   
	  * @Title: saveVisitTemalate   
	  * @Description: TODO(保存回访计划模板(部门分类))   
	  * @param: @param attribute      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年11月2日 上午11:32:03
	  */  
	void saveVisitTemalate(List<VisitTemplate> attribute) throws Exception;

	/**   
	  * @Title: saveVisitPlanTemalate   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param list      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年11月2日 下午4:14:09
	  */  
	void saveVisitPlanTemalate(List<VisitPlanTemplate> list) throws Exception;

	/**   
	  * @param map 
	 * @Title: findTemplate   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @return      
	  * @return: List<VisitTemplate>
	 * @throws Exception 
	  * @dateTime:2019年11月2日 下午4:28:25
	  */  
	List<VisitTemplate> findTemplate(Map<String, String> map) throws Exception;

	/**   
	  * @Title: findvisitPlanTemplate   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param managarId
	  * @param: @return      
	  * @return: List<VisitTemplate>
	 * @throws Exception 
	  * @dateTime:2019年11月2日 下午4:35:58
	  */  
	List<VisitTemplate> findvisitPlanTemplate(String managarId) throws Exception;
	/**
	 * 查询当前时间的回访记录
	 * <p>Title: findvisitByTime</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年11月6日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findvisitByTime(Map<String, String> map) throws Exception;
	/**
	 * 查询回访条数
	 * <p>Title: findvisitByTimeNum</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年11月6日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int findvisitByTimeNum(Map<String, String> map) throws Exception;
	/**
	 * 删除小计划
	 * <p>Title: deleteManagar</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年11月8日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int deleteManagarPlan(String managarId) throws Exception;
	/**
	 * 删除大计划
	 * <p>Title: deleteManagar</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年11月8日 
	 * @param seqId
	 * @return
	 * @throws Exception
	 */
	int deleteManagar(String seqId) throws Exception;
	/**
	 * 修改启用状态
	 * <p>Title: updateManagarStatus</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年11月8日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int updateManagarStatus(VisitTemplate visit) throws Exception;

	/**   
	  * @Title: findoperator   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param sysPosition
	  * @param: @return      
	  * @return: List<YZPara>
	  * @dateTime:2019年11月9日 下午3:12:40
	  */  
	List<JSONObject> findoperator(String sysPosition) throws Exception;

	/**   
	  * @Title: findvisitTemplate   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map
	  * @param: @return      
	  * @return: List<VisitTemplate>
	 * @throws Exception 
	  * @dateTime:2019年11月11日 上午10:19:47
	  */  
	List<JSONObject> findvisitTemplate(Map<String, String> map) throws Exception;

	
}
