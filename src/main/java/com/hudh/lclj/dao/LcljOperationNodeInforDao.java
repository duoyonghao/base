package com.hudh.lclj.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.lclj.entity.LcljOperationNodeInforaBerrance;
import com.hudh.lclj.entity.OperationNodeInfor;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;

@Service
public class LcljOperationNodeInforDao {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 保存手术流程对应节点信息
	 * @throws Exception
	 */
	public void insertOperationNodeInfor(OperationNodeInfor dp) throws Exception {
		dao.save("HUDH_LCLJ_OPERATION_NODE_INFOR.insertOperationNodeInfor", dp);
	}
	
	/**
	 * 根据id查询手术流程对应节点信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public JSONObject selectOperationNodeInforById(String id) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject("HUDH_LCLJ_OPERATION_NODE_INFOR.selectOperationNodeInforById", id);
		return json;
	}
	
	/**
	 * 查询所有的手术节点信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectOperationNodeInforAll() throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_LCLJ_OPERATION_NODE_INFOR.selectOperationNodeInforAll", null);
		return list;
	}
	
	/**
	 * 根据id更新手术流程信息
	 */
	public void updateOperationNodeInforById(OperationNodeInfor dp) throws Exception {
		dao.update("HUDH_LCLJ_OPERATION_NODE_INFOR.updateOperationNodeInforById", dp);
	}
	
	/**
	 * 根据id删除手术流程信息
	 * @param id
	 * @throws Exception
	 */
	public void deleteOperationNodeInforById(String id) throws Exception {
		dao.delete("HUDH_LCLJ_OPERATION_NODE_INFOR.deleteOperationNodeInforById", id);
	}
	
	/**
	 * 根据临床路径编号和节点id查询相应节点业务数据
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public JSONObject selectOperationNodeInforByOrdernumberAndNodeId(Map<String, String> map) throws Exception {
		JSONObject json = (JSONObject) dao.findForObject("HUDH_LCLJ_OPERATION_NODE_INFOR.selectOperationNodeInforByOrdernumberAndNodeId", map);
		return json;
	}
	
	/**
	 * 根据orderNumber和nodeId更新手术具体流程节点信息
	 * @param dp
	 * @throws Exception
	 */
	public void updateOperationNodeInforByOrderNumberAndNodeId(OperationNodeInfor dp) throws Exception {
		dao.update("HUDH_LCLJ_OPERATION_NODE_INFOR.updateOperationNodeInforByOrderNumberAndNodeId", dp);
	}
	
	/**
	 * 2019-7-26 syp 更改下次来院时间  
	 * @Title: updateOrderTimeHospital   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param order_number
	  * @param: @param nodeId
	  * @param: @param orderTime
	  * @param: @throws Exception      
	  * @return: void
	  * @dateTime:2019年7月26日 上午10:32:10
	 */
	public void updateOrderTimeHospital(String order_number,String nodeId,String orderTime) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("order_number", order_number);
		dataMap.put("nodeId", nodeId);
		dataMap.put("orderTime", orderTime);
		dao.update("HUDH_LCLJ_OPERATION_NODE_INFOR.updateOrderTimeHospital", dataMap);
	}
	
	/**
	 * 预约日历里面取消手术预约临床路径同步取消   2019-7-27  syp
	  * @Title: cancelTimeHospital   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param order_number
	  * @param: @param nodeId
	  * @param: @throws Exception      
	  * @return: void
	  * @dateTime:2019年7月27日 上午11:08:23
	 */
	public void cancelTimeHospital(String order_number,String nodeId) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("order_number", order_number);
		dataMap.put("nodeId", nodeId);
		dao.update("HUDH_LCLJ_OPERATION_NODE_INFOR.cancelTimeHospital", dataMap);
	}
	
	/**
	 * 根据预约时间查询节点信息
	  * @Title: findOperationNodeInforByCancelTimeHospital   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param next_hospital_time
	  * @param: @param visit_time
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: JSONObject
	  * @dateTime:2019年8月4日 下午1:48:57
	 */
	@SuppressWarnings("unchecked")
	public JSONObject findOperationNodeInforByCancelTimeHospital(String next_hospital_time, String visit_time, String order_number) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("next_hospital_time", next_hospital_time);
		dataMap.put("visit_time", visit_time);
		dataMap.put("order_number", order_number);
		JSONObject jsonObject = (JSONObject) dao.findForObject("HUDH_LCLJ_OPERATION_NODE_INFOR.findOperationNodeInforByCancelTimeHospital", dataMap);
		return jsonObject;
	}
	
}