package com.hudh.lclj.dao;

import com.hudh.lclj.entity.LcljFlow;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlowConfigDao
{
  @Autowired
  private DaoSupport dao;
  
  public int insertLcljFlow(LcljFlow lcljFlow)
    throws Exception
  {
    return ((Integer)this.dao.save("HUDH_LCLJ_FLOW_CONFIG.insertFlowConfig", lcljFlow)).intValue();
  }
  
  public LcljFlow findLcljFlowById(String flowCode)
    throws Exception
  {
    LcljFlow lcljFlow = (LcljFlow)this.dao.findForObject("HUDH_LCLJ_FLOW_CONFIG.findFlowConfigByCode", flowCode);
    return lcljFlow;
  }
  
  public void deleteLcljFlowById(String flowCode)
    throws Exception
  {
    this.dao.delete("HUDH_LCLJ_FLOW_CONFIG.deleteFlowConfigByCode", flowCode);
  }
  
  public void updateLcljFlowById(LcljFlow lcljFlow)
    throws Exception
  {
    this.dao.update("HUDH_LCLJ_FLOW_CONFIG.updateFlowConfigByCode", lcljFlow);
  }
  
  public List<JSONObject> findAllLcljFlowByParentId(Map<String, String> dataMap)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_LCLJ_FLOW_CONFIG.findAllFlowConfig", dataMap);
    return list;
  }
  
  public List<JSONObject> findLcljFlowByDentalJaw(Map<String, String> map)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_LCLJ_FLOW_CONFIG.findLcljFlowByDentalJaw", map);
    return list;
  }
  
  public List<JSONObject> findLcljFlowInforByDentalJaw(String dentalJaw)
    throws Exception
  {
    List<JSONObject> list = (List)this.dao.findForList("HUDH_LCLJ_FLOW_CONFIG.findLcljFlowInforByDentalJaw", dentalJaw);
    return list;
  }
}
