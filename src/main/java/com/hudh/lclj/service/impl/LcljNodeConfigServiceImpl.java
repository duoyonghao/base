package com.hudh.lclj.service.impl;

import com.alibaba.fastjson.JSON;
import com.hudh.lclj.dao.NodeConfigDao;
import com.hudh.lclj.entity.LcljNodeConfig;
import com.hudh.lclj.service.ILcljNodeConfigService;
import com.hudh.util.HUDHUtil;
import com.kqds.util.sys.YZUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LcljNodeConfigServiceImpl implements ILcljNodeConfigService {
  @Autowired
  private NodeConfigDao nodeConfigDao;
  
  public void insertNodeConfig(LcljNodeConfig lcljNodeConfig) throws Exception {
    lcljNodeConfig.setId(YZUtility.getUUID());
    lcljNodeConfig.setCreatrtime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    this.nodeConfigDao.insertNodeConfig(lcljNodeConfig);
  }
  
  public void deleteNodeConfigByCodeAndNodeId(Map<String, String> dataMap) throws Exception {}
  
  public List<JSONObject> findAllNodeConfigByFlowCode(Map<String, String> dataMap) throws Exception {
    List<LcljNodeConfig> list = new ArrayList<>();
    list = this.nodeConfigDao.findAllNodeConfigByFlowCode(dataMap);
    return HUDHUtil.parseJsonToObjectList(JSON.toJSONString(list), JSONObject.class);
  }
  
  public List<LcljNodeConfig> findAllNodeConfigByFlowCodeObj(Map<String, String> dataMap) throws Exception {
    List<LcljNodeConfig> list = new ArrayList<>();
    list = this.nodeConfigDao.findAllNodeConfigByFlowCode(dataMap);
    return list;
  }
}
