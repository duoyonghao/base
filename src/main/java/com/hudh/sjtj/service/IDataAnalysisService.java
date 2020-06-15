package com.hudh.sjtj.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface IDataAnalysisService
{
  public abstract List<JSONObject> findBaseDataAskperson(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findCJStatisticsByMonth(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findCJStatisticsByDay(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findAllCJStatisticsByMonth(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findAllCJStatisticsByDay(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findTotalMoneyByMonth(Map<String, String> paramMap, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract List<JSONObject> findCjsCale(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findDepartment(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findImplant(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> finddoctor(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findCJStatisticsByYear(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findCJQuantityByAskpersonAndMonthInYear(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findAllCJStatisticsByYear(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> Devchannel(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> consumptionInterval(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap)
    throws Exception;
}
