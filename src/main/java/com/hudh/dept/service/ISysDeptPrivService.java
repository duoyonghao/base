package com.hudh.dept.service;

import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public interface ISysDeptPrivService {
  List<JSONObject> findDeptNameByButtonName(Map<String, String> paramMap) throws Exception;
  
  void insertSysDeptPriv(Map<String, String> paramMap) throws Exception;
  
  void deleteSysDeptPriv(String paramString) throws Exception;
  
  List<JSONObject> findDeptPrivByDeptPrivId(String paramString) throws Exception;
  
  int updateSysDeptPriv(Map<String, String> paramMap) throws Exception;
  
  List<JSONObject> findPersonByDeptId(Map<String, String> paramMap) throws Exception;
}
