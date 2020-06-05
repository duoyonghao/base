package com.hudh.lclj.service;

import java.util.List;
import java.util.Map;
import com.hudh.lclj.entity.LcljFlow;

import net.sf.json.JSONObject;
public interface ILcljFlowConfigService {
	/**
	 * 新增流程
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	public void insertLcljFlow(LcljFlow lcljFlow) throws Exception ;
	
	/**
	 * 根据code查找流程
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public LcljFlow findLcljFlowById(String flowCode) throws Exception ;
	
	/**
	 * 根据code删除流程
	 * @param id
	 * @throws Exception
	 */
	public void deleteLcljFlowById(String flowCode) throws Exception ;
	
	/**
	 * 根据code更新流程信息
	 * @param ykzzType
	 * @throws Exception
	 */
	public void updateLcljFlowById(LcljFlow lcljFlow) throws Exception ;
	
	/**
	 * 获取全部流程
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> findAllLcljFlow(Map<String,String> dataMap) throws Exception ;
	
	/**
	 * 根据上下颌分类查询信息
	 * @param dentalJaw
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findLcljFlowByDentalJaw(Map<String, String> map) throws Exception;
	
	/**
	 * 根据dentalJaw分类查询信息
	 * @param dentalJaw
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> findLcljFlowInforByDentalJaw(String dentalJaw) throws Exception;
}
