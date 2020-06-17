package com.hudh.lclj.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hudh.lclj.entity.LcljOperationNodeInforaBerrance;

import net.sf.json.JSONObject;

public interface ILcljOperationNodeInforAberrance {
	
	/**
	 * 保存流程节点变异数据
	 * @param dp
	 * @throws Exception
	 */
	void LcljOperationNodeInforAberrance(LcljOperationNodeInforaBerrance dp, HttpServletRequest request) throws Exception;
	
	/**
	 * 根据orderNumber和nodeId更新手术具体流程节点信息
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findOperationNodeInforAberranceByOrderNumberAndNodeId(Map<String, String> dataMap) throws Exception;
	
	/**
	 * 专门保存术前取模定咬合页面数据
	 * @param dp
	 * @throws Exception
	 */
	void insertOperationNodeInforAberrance(LcljOperationNodeInforaBerrance dp, HttpServletRequest request) throws Exception;
	
}
