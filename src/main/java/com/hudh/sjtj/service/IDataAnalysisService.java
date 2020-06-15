package com.hudh.sjtj.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface IDataAnalysisService {
  List<JSONObject> findBaseDataAskperson(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findCJStatisticsByMonth(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findCJStatisticsByDay(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findAllCJStatisticsByMonth(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findAllCJStatisticsByDay(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findTotalMoneyByMonth(Map<String, String> paramMap, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<JSONObject> findCjsCale(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findDepartment(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findImplant(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> finddoctor(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findCJStatisticsByYear(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findCJQuantityByAskpersonAndMonthInYear(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findAllCJStatisticsByYear(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> Devchannel(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> consumptionInterval(HttpServletRequest paramHttpServletRequest, Map<String, String> paramMap) throws Exception;
}
