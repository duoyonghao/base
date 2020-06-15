package com.hudh.ykzz.service;

import com.hudh.ykzz.entity.YkzzDrugsOut;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface IYkzzDrugsOutService
{
  public abstract void insertDrugsOut(YkzzDrugsOut paramYkzzDrugsOut, String paramString, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract List<JSONObject> findAllDrugsOut(Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findDetailByParendId(String paramString)
    throws Exception;
  
  public abstract String deleteDrugsOut(String paramString)
    throws Exception;
  
  public abstract void deleteDrugsOutById(String paramString)
    throws Exception;
  
  public abstract void deleteDrugsOutDetailByParendId(String paramString)
    throws Exception;
  
  public abstract String getDrugsInBatchnum(String paramString1, String paramString2)
    throws Exception;
  
  public abstract String findOutNumByOrderno(String paramString1, String paramString2)
    throws Exception;
  
  public abstract String findOutNumsByAll()
    throws Exception;
  
  public abstract String findBatchnumSaveOutNumsByOrdernoAndBatchnum(String paramString1, String paramString2)
    throws Exception;
  
  public abstract String findOutNumsByBatchnumSave()
    throws Exception;
}
