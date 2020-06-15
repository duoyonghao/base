package com.hudh.tjhf.service;

import com.hudh.tjhf.entity.VisitPlanTemplate;
import com.hudh.tjhf.entity.VisitTemplate;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public abstract interface IAddVisitService
{
  public abstract void saveVisitTemalate(List<VisitTemplate> paramList)
    throws Exception;
  
  public abstract void saveVisitPlanTemalate(List<VisitPlanTemplate> paramList)
    throws Exception;
  
  public abstract List<VisitTemplate> findTemplate(Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<VisitTemplate> findvisitPlanTemplate(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findvisitByTime(Map<String, String> paramMap)
    throws Exception;
  
  public abstract int findvisitByTimeNum(Map<String, String> paramMap)
    throws Exception;
  
  public abstract int deleteManagarPlan(String paramString)
    throws Exception;
  
  public abstract int deleteManagar(String paramString)
    throws Exception;
  
  public abstract int updateManagarStatus(VisitTemplate paramVisitTemplate)
    throws Exception;
  
  public abstract List<JSONObject> findoperator(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findvisitTemplate(Map<String, String> paramMap)
    throws Exception;
}
