package com.hudh.lclj.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hudh.lclj.entity.OperationNodeInfor;

import net.sf.json.JSONObject;

public interface ILcljOperationNodeInforService {
	
	/**
	 * 保存手术流程对应节点信息
	 * @throws Exception
	 */
	OperationNodeInfor insertOperationNodeInfor(OperationNodeInfor dp, HttpServletRequest request, String dataId) throws Exception;
	
	/**
	 * 根据id查询手术流程对应节点信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	JSONObject selectOperationNodeInforById(String id) throws Exception;
	
	/**
	 * 查询所有的手术节点信息
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> selectOperationNodeInforAll() throws Exception;
	
	/**
	 * 根据id更新手术流程信息
	 * @param id
	 * @throws Exception
	 */
	void updateOperationNodeInforById(OperationNodeInfor dp) throws Exception;
	
	/**
	 * 根据id删除手术流程信息
	 * @param id
	 * @throws Exception
	 */
	void deleteOperationNodeInforById(String id) throws Exception;
	
	/**
	 * 根据临床路径编号和节点id查询相应节点业务数据
	 * @param map
	 * @return
	 * @throws Exception
	 */
	JSONObject selectOperationNodeInforByOrdernumberAndNodeId(Map<String, String> map) throws Exception;
	
	/**
	 * 
	  * @Title: updateOrderTimeHospital   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param order_number
	  * @param: @param nodeId
	  * @param: @param orderTime
	  * @param: @throws Exception      
	  * @return: void
	  * @dateTime:2019年7月26日 上午10:38:50
	 */
	void updateOrderTimeHospital(String order_number,String nodeId,String orderTime) throws Exception;
	
	/**
	 * 预约日历里面取消手术预约临床路径同步取消   2019-7-27  syp
	  * @Title: cancelTimeHospital   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param order_number
	  * @param: @param nodeId
	  * @param: @throws Exception      
	  * @return: void
	  * @dateTime:2019年7月27日 上午11:11:52
	 */
	void cancelTimeHospital(String order_number,String nodeId) throws Exception;


	public List<JSONObject> findOperationNodeInforByCancelTimeHospital(Map<String,String> dataMap) throws Exception ;

}
