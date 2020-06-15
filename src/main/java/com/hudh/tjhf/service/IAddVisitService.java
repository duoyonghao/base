package com.hudh.tjhf.service;

import com.hudh.tjhf.entity.VisitPlanTemplate;
import com.hudh.tjhf.entity.VisitTemplate;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public interface IAddVisitService {
  void saveVisitTemalate(List<VisitTemplate> paramList) throws Exception;
  
  void saveVisitPlanTemalate(List<VisitPlanTemplate> paramList) throws Exception;
  
  List<VisitTemplate> findTemplate(Map<String, String> paramMap) throws Exception;
  
  List<VisitTemplate> findvisitPlanTemplate(String paramString) throws Exception;
  
  List<JSONObject> findvisitByTime(Map<String, String> paramMap) throws Exception;
  
  int findvisitByTimeNum(Map<String, String> paramMap) throws Exception;
  
  int deleteManagarPlan(String paramString) throws Exception;
  
  int deleteManagar(String paramString) throws Exception;
  
  int updateManagarStatus(VisitTemplate paramVisitTemplate) throws Exception;
  
  List<JSONObject> findoperator(String paramString) throws Exception;
  
  List<JSONObject> findvisitTemplate(Map<String, String> paramMap) throws Exception;
}
