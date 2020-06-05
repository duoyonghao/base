/**  
  *
  * @Title:  ILcljOperationNodeRemarkService.java   
  * @Package com.hudh.lclj.service   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年8月31日 上午11:05:46   
  * @version V1.0  
  */ 
package com.hudh.lclj.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hudh.lclj.entity.LcljOperationNodeRemark;

import net.sf.json.JSONObject;

/**  
  * 
  * @ClassName:  ILcljOperationNodeRemarkService   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年8月31日 上午11:05:46   
  *      
  */
public interface ILcljOperationNodeRemarkService {
	
	/**
	 * 保存节点备注信息
	  * @Title: saveNodeRemark   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dp
	  * @param: @throws Exception      
	  * @return: void
	  * @dateTime:2019年8月31日 上午11:08:24
	 */
	void saveNodeRemark(LcljOperationNodeRemark dp, HttpServletRequest request) throws Exception;
	
	/**
	 * 根据节点ID和临床路径编号查询备注信息
	  * @Title: findNodeRemarkByNodeId   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param dataMap
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年8月31日 下午2:42:23
	 */
	List<JSONObject> findNodeRemarkByNodeId(Map<String, String> dataMap) throws Exception;

}
