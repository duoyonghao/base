package com.hudh.lclj.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.lclj.entity.LcljFlow;
import com.hudh.ykzz.entity.YkzzType;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;
@Service
public class FlowConfigDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增流程
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	public int insertLcljFlow(LcljFlow lcljFlow) throws Exception{
		return (int) dao.save("HUDH_LCLJ_FLOW_CONFIG.insertFlowConfig", lcljFlow);
	}
	
	/**
	 * 根据code查找流程
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public LcljFlow findLcljFlowById(String flowCode) throws Exception{
		LcljFlow lcljFlow = (LcljFlow) dao.findForObject("HUDH_LCLJ_FLOW_CONFIG.findFlowConfigByCode", flowCode);
		return lcljFlow;
	}
	
	/**
	 * 根据code删除流程
	 * @param id
	 * @throws Exception
	 */
	public void deleteLcljFlowById(String flowCode) throws Exception{
		dao.delete("HUDH_LCLJ_FLOW_CONFIG.deleteFlowConfigByCode", flowCode);
	}
	
	/**
	 * 根据code更新流程信息
	 * @param ykzzType
	 * @throws Exception
	 */
	public void updateLcljFlowById(LcljFlow lcljFlow) throws Exception{
		dao.update("HUDH_LCLJ_FLOW_CONFIG.updateFlowConfigByCode", lcljFlow);
	}
	
	/**
	 * 获取全部流程
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findAllLcljFlowByParentId(Map<String,String> dataMap) throws Exception{
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_LCLJ_FLOW_CONFIG.findAllFlowConfig", dataMap);
		return list;
	}
	
	/**
	 * 根据上下颌分类查询信息
	 * @param dentalJaw
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findLcljFlowByDentalJaw(Map<String, String> map) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_LCLJ_FLOW_CONFIG.findLcljFlowByDentalJaw", map);
		return list;
	}
	
	/**
	 * 根据dentalJaw分类查询
	 * @param dentalJaw
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> findLcljFlowInforByDentalJaw(String dentalJaw) throws Exception {
		List<JSONObject> list = (List<JSONObject>) dao.findForList("HUDH_LCLJ_FLOW_CONFIG.findLcljFlowInforByDentalJaw", dentalJaw);
		return list;
	}
}
