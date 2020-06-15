package com.hudh.lclj.service.impl;

import com.hudh.lclj.dao.FlowConfigDao;
import com.hudh.lclj.entity.LcljFlow;
import com.hudh.lclj.service.ILcljFlowConfigService;
import com.hudh.util.HUDHUtil;
import com.kqds.util.sys.YZUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljFlowConfigServiceImpl implements ILcljFlowConfigService {
  @Autowired
  private FlowConfigDao flowConfigDao;
  
  public void insertLcljFlow(LcljFlow lcljFlow) throws Exception {
    lcljFlow.setId(YZUtility.getUUID());
    lcljFlow.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    this.flowConfigDao.insertLcljFlow(lcljFlow);
  }
  
  public LcljFlow findLcljFlowById(String flowCode) throws Exception {
    return null;
  }
  
  public void deleteLcljFlowById(String flowCode) throws Exception {}
  
  public void updateLcljFlowById(LcljFlow lcljFlow) throws Exception {}
  
  public List<JSONObject> findAllLcljFlow(Map<String, String> dataMap) throws Exception {
    List<JSONObject> list = new ArrayList<>();
    list = this.flowConfigDao.findAllLcljFlowByParentId(dataMap);
    return list;
  }
  
  public List<JSONObject> findLcljFlowByDentalJaw(Map<String, String> map) throws Exception {
    List<JSONObject> List = this.flowConfigDao.findLcljFlowByDentalJaw(map);
    return List;
  }
  
  public List<JSONObject> findLcljFlowInforByDentalJaw(String dentalJaw) throws Exception {
    List<JSONObject> list = this.flowConfigDao.findLcljFlowInforByDentalJaw(dentalJaw);
    return list;
  }
}
