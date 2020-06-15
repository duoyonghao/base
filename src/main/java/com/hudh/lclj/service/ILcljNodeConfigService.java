package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljNodeConfig;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public interface ILcljNodeConfigService {
  void insertNodeConfig(LcljNodeConfig paramLcljNodeConfig) throws Exception;
  
  void deleteNodeConfigByCodeAndNodeId(Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findAllNodeConfigByFlowCode(Map<String, String> paramMap) throws Exception;
  
  List<LcljNodeConfig> findAllNodeConfigByFlowCodeObj(Map<String, String> paramMap) throws Exception;
}
