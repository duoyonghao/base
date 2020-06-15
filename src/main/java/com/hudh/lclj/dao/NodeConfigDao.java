package com.hudh.lclj.dao;

import com.hudh.lclj.entity.LcljNodeConfig;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodeConfigDao {
  @Autowired
  private DaoSupport dao;
  
  public int insertNodeConfig(LcljNodeConfig lcljNodeConfig) throws Exception {
    return ((Integer)this.dao.save("HUDH_LCLJ_NODE_CONFIG.insertNodeConfig", lcljNodeConfig)).intValue();
  }
  
  public void deleteNodeConfigByCodeAndNodeId(Map<String, String> dataMap) throws Exception {
    this.dao.delete("HUDH_LCLJ_NODE_CONFIG.deleteNodeConfigByCodeAndNodeId", dataMap);
  }
  
  public List<LcljNodeConfig> findAllNodeConfigByFlowCode(Map<String, String> dataMap) throws Exception {
    List<LcljNodeConfig> list = (List<LcljNodeConfig>)this.dao.findForList("HUDH_LCLJ_NODE_CONFIG.findAllNodeConfigByFlowCode", dataMap);
    return list;
  }
  
  public void batchSaveNodeConfig(List<LcljNodeConfig> list) throws Exception {
    this.dao.batchUpdate("HUDH_LCLJ_NODE_CONFIG.batchSaveNodeConfig", list);
  }
  
  public List<LcljNodeConfig> findLcljNodeConfig(String flowCode) throws Exception {
    List<LcljNodeConfig> list = (List<LcljNodeConfig>)this.dao.findForList("HUDH_LCLJ_NODE_CONFIG.findLcljNodeConfig", flowCode);
    return list;
  }
  
  public List<LcljNodeConfig> findLcljNodeName() throws Exception {
    List<LcljNodeConfig> list = (List<LcljNodeConfig>)this.dao.findForList("HUDH_LCLJ_NODE_CONFIG.findLcljNodeName", null);
    return list;
  }
}
