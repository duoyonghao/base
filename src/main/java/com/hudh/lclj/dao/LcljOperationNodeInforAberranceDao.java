package com.hudh.lclj.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.lclj.entity.LcljOperationNodeInforaBerrance;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;

@Service
public class LcljOperationNodeInforAberranceDao {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 保存每个节点变异的数据
	 * @param dp
	 * @throws Exception
	 */
	public void saveOperationNodeInforAberrance(LcljOperationNodeInforaBerrance dp ) throws Exception {
		dao.save("HUDH_LCLJ_OPERATION_NODE_INFOR_ABERRANCE.saveOperationNodeInforAberrance", dp);
	}
	
	/**
	 * 根据路径编号和节点Id查询变异数据
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findOperationNodeInforAberranceByOrderNumberAndNodeId(Map<String, String> dataMap) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_LCLJ_OPERATION_NODE_INFOR_ABERRANCE.findOperationNodeInforAberranceByOrderNumberAndNodeId", dataMap);
		return list;
	}
	
	/**
	 * 专门保存术前取模定咬合页面数据
	 * @param dp
	 * @throws Exception
	 */
	public void insertOperationNodeInforAberrance(LcljOperationNodeInforaBerrance dp) throws Exception {
		dao.save("HUDH_LCLJ_OPERATION_NODE_INFOR_ABERRANCE.insertOperationNodeInforAberrance", dp);
	}
}
