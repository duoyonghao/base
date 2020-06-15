package com.hudh.ksll.service;

import com.hudh.ksll.entity.KsllReplaceMent;
import com.kqds.entity.sys.YZPerson;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface IKsllReplaceMentService
{
  public abstract void insertReplacement(KsllReplaceMent paramKsllReplaceMent, String paramString, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract List<JSONObject> findAllReplacement(Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findAllReplacementNoDelete(Map<String, String> paramMap)
    throws Exception;
  
  public abstract void updateReplacementStatus(Map<String, String> paramMap, List<JSONObject> paramList, YZPerson paramYZPerson, String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findDetailByParendId(Map<String, String> paramMap)
    throws Exception;
  
  public abstract void updaeCKStock(String paramString1, String paramString2, List<JSONObject> paramList, YZPerson paramYZPerson, String paramString3)
    throws Exception;
  
  public abstract void updateKsllGoodNums(String paramString1, String paramString2)
    throws Exception;
  
  public abstract int selectReturnNumByGoodscode(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findCkReplaceMentDetailByParendId(String paramString)
    throws Exception;
}
