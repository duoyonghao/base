package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljFlow;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public interface ILcljFlowConfigService {
  void insertLcljFlow(LcljFlow paramLcljFlow) throws Exception;
  
  LcljFlow findLcljFlowById(String paramString) throws Exception;
  
  void deleteLcljFlowById(String paramString) throws Exception;
  
  void updateLcljFlowById(LcljFlow paramLcljFlow) throws Exception;
  
  List<JSONObject> findAllLcljFlow(Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findLcljFlowByDentalJaw(Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findLcljFlowInforByDentalJaw(String paramString) throws Exception;
}
