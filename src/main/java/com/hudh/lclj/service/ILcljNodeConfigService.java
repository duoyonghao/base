package com.hudh.lclj.service;

import java.util.List;
import java.util.Map;
import com.hudh.lclj.entity.LcljNodeConfig;
import net.sf.json.JSONObject;
public interface ILcljNodeConfigService {
	/**
	 * 新增流程节点
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	public void insertNodeConfig(LcljNodeConfig lcljNodeConfig) throws Exception ;
	
	/**
	 * 根据流程code和节点id删除节点
	 * @param id
	 * @throws Exception
	 */
	public void deleteNodeConfigByCodeAndNodeId(Map<String,String> dataMap) throws Exception ;
	
	/**
	 * 获取全部流程下的节点 返回JSONObject集合
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findAllNodeConfigByFlowCode(Map<String,String> dataMap) throws Exception ;
	
	/**
	 * 获取全部流程下的节点 返回对象集合
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<LcljNodeConfig> findAllNodeConfigByFlowCodeObj(Map<String,String> dataMap) throws Exception ;
}
