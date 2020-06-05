/**  
  *
  * @Title:  HUDH_LcljVerificationSheetService.java   
  * @Package com.hudh.lclj.service   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年12月23日 下午6:47:08   
  * @version V1.0  
  */ 
package com.hudh.lclj.service;

import java.util.List;
import java.util.Map;

import com.hudh.lclj.entity.LcljApprovedMemo;
import com.kqds.entity.sys.YZPerson;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  HUDH_LcljVerificationSheetService   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年12月23日 下午6:47:08   
  *      
  */
public interface HUDH_LcljVerificationSheetService {

	/**   
	  * @Title: insert   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param bean      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年12月23日 下午7:10:03
	  */  
	void insert(LcljApprovedMemo bean) throws Exception;

	/**   
	  * @Title: Update   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param bean      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年12月23日 下午7:10:11
	  */  
	void Update(LcljApprovedMemo bean) throws Exception;

	/**   
	  * @Title: getCheckRecord   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param lcljId
	  * @param: @return      
	  * @return: List<JSONObject>
	 * @throws Exception 
	  * @dateTime:2019年12月23日 下午7:23:59
	  */  
	List<JSONObject> getCheckRecord(Map<String, String> map, YZPerson person) throws Exception;
	
	/**
	 * 查询未审核的记录
	 * @param map
	 * @param person
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> getAwaitCheckRecord(Map<String, String> map, YZPerson person) throws Exception;
	/**   
	  * @Title: getCheckRecord   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param lcljId
	  * @param: @return      
	  * @return: List<JSONObject>
	 * @throws Exception 
	  * @dateTime:2019年12月23日 下午7:23:59
	  */  
	Integer getCheckRecordNum(Map<String, String> map) throws Exception;

	/**   
	  * @Title: delCheckRecord   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param id      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年12月23日 下午7:30:06
	  */  
	void delCheckRecord(String id) throws Exception;
	
	/**
	 * 查询未审核记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Integer getAwaitVerifieNum(Map<String, String> map) throws Exception;

}
