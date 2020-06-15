package com.hudh.dept.service;

import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public abstract interface ISysDeptPrivService
{
  public abstract List<JSONObject> findDeptNameByButtonName(Map<String, String> paramMap)
    throws Exception;
  
  public abstract void insertSysDeptPriv(Map<String, String> paramMap)
    throws Exception;
  
  public abstract void deleteSysDeptPriv(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findDeptPrivByDeptPrivId(String paramString)
    throws Exception;
  
  public abstract int updateSysDeptPriv(Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findPersonByDeptId(Map<String, String> paramMap)
    throws Exception;
}
