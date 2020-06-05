package com.hudh.lclj.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.ksll.entity.KsllCollorDetail;
import com.hudh.lclj.entity.LcljFlow;
import com.hudh.lclj.entity.LcljNodeConfig;
import com.hudh.ykzz.entity.YkzzType;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;
@Service
public class NodeConfigDao {
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增流程节点
	 * @param ykzzType
	 * @return
	 * @throws Exception
	 */
	public int insertNodeConfig(LcljNodeConfig lcljNodeConfig) throws Exception{
		return (int) dao.save("HUDH_LCLJ_NODE_CONFIG.insertNodeConfig", lcljNodeConfig);
	}
	
	/**
	 * 根据流程code和节点id删除节点
	 * @param id
	 * @throws Exception
	 */
	public void deleteNodeConfigByCodeAndNodeId(Map<String,String> dataMap) throws Exception{
		dao.delete("HUDH_LCLJ_NODE_CONFIG.deleteNodeConfigByCodeAndNodeId", dataMap);
	}
	
	/**
	 * 获取全部流程下的节点
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LcljNodeConfig> findAllNodeConfigByFlowCode(Map<String,String> dataMap) throws Exception{
		List<LcljNodeConfig> list = (List<LcljNodeConfig>) dao.findForList("HUDH_LCLJ_NODE_CONFIG.findAllNodeConfigByFlowCode", dataMap);
		return list;
	}
	

	/**
	 * 批量新增节点
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public void batchSaveNodeConfig(List<LcljNodeConfig> list) throws Exception{
		dao.batchUpdate("HUDH_LCLJ_NODE_CONFIG.batchSaveNodeConfig", list);
	}
	
	/**
	 * 获取特定流程下的节点
	 * @param flowCode
	 * @return
	 * @throws Exception
	 */
	public List<LcljNodeConfig> findLcljNodeConfig(String flowCode) throws Exception {
		List<LcljNodeConfig> list = (List<LcljNodeConfig>) dao.findForList("HUDH_LCLJ_NODE_CONFIG.findLcljNodeConfig", flowCode);
		return list;
	}
	
	/**
	 * 获取节点名称
	 * <p>Title: findLcljNodeName</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年4月27日 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LcljNodeConfig> findLcljNodeName() throws Exception {
		List<LcljNodeConfig> list = (List<LcljNodeConfig>) dao.findForList("HUDH_LCLJ_NODE_CONFIG.findLcljNodeName", null);
		return list;
	}
}
