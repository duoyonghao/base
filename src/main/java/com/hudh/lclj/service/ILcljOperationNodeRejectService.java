/**  
  *
  * @Title:  ILcljOperationNodeRejectService.java   
  * @Package com.hudh.lclj.service   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年6月27日 下午2:39:26   
  * @version V1.0  
  */ 
package com.hudh.lclj.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hudh.lclj.entity.LcljOperateRejectRecord;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  ILcljOperationNodeRejectService   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年6月27日 下午2:39:26   
  *      
  */
public interface ILcljOperationNodeRejectService {
	
	void insertOperationNodeReject(LcljOperateRejectRecord dp, HttpServletRequest request) throws Exception;
	
	List<JSONObject> findOperationNodeRejectByOrderNumber(String orderNumber) throws Exception;
}
