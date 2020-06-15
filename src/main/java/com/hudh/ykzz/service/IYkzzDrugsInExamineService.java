package com.hudh.ykzz.service;

import com.hudh.ykzz.entity.YkzzDrugsInExamine;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface IYkzzDrugsInExamineService
{
  public abstract int insertDrugsInExamine(YkzzDrugsInExamine paramYkzzDrugsInExamine, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract List<JSONObject> findDrugsInExamineByInId(String paramString)
    throws Exception;
  
  public abstract void deleteDrugsInExamineById(String paramString)
    throws Exception;
  
  public abstract void deleteDrugsInExamineByInId(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findDugsExamineDetail(Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findDugsReturnDetail(Map<String, String> paramMap)
    throws Exception;
}
