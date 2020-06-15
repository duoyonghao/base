package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljNodeConfig;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public abstract interface ILcljNodeConfigService
{
  public abstract void insertNodeConfig(LcljNodeConfig paramLcljNodeConfig)
    throws Exception;
  
  public abstract void deleteNodeConfigByCodeAndNodeId(Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findAllNodeConfigByFlowCode(Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<LcljNodeConfig> findAllNodeConfigByFlowCodeObj(Map<String, String> paramMap)
    throws Exception;
}
