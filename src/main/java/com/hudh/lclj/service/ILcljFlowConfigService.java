package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljFlow;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public abstract interface ILcljFlowConfigService
{
  public abstract void insertLcljFlow(LcljFlow paramLcljFlow)
    throws Exception;
  
  public abstract LcljFlow findLcljFlowById(String paramString)
    throws Exception;
  
  public abstract void deleteLcljFlowById(String paramString)
    throws Exception;
  
  public abstract void updateLcljFlowById(LcljFlow paramLcljFlow)
    throws Exception;
  
  public abstract List<JSONObject> findAllLcljFlow(Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findLcljFlowByDentalJaw(Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findLcljFlowInforByDentalJaw(String paramString)
    throws Exception;
}
