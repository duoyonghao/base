package com.hudh.ksll.service;

import com.hudh.ksll.entity.KsllCollor;
import com.kqds.entity.base.KqdsCkHouse;
import com.kqds.entity.sys.BootStrapPage;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

public abstract interface IKsllColorService
{
  public abstract List<JSONObject> findAllCKDept(Map<String, String> paramMap)
    throws Exception;
  
  public abstract void saveKsllData(KsllCollor paramKsllCollor, String paramString, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract List<JSONObject> findAllKsllColor(Map<String, String> paramMap)
    throws Exception;
  
  public abstract JSONObject findKsllColorAndDetails(String paramString)
    throws Exception;
  
  public abstract JSONObject findKsllColorAndDetail(String paramString)
    throws Exception;
  
  public abstract List<KqdsCkHouse> findAllCKHouse(Map<String, String> paramMap)
    throws Exception;
  
  public abstract void ksllOutGoods(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception;
  
  public abstract List<JSONObject> findKsllColorDetailByparentid(String paramString)
    throws Exception;
  
  public abstract void updateNumsById(Map<String, String> paramMap)
    throws Exception;
  
  public abstract void deleteKsllNotCK(String paramString)
    throws Exception;
  
  public abstract Map<String, JSONObject> findAllCKGood()
    throws Exception;
  
  public abstract String findCKGoodSshouse(String paramString1, String paramString2)
    throws Exception;
  
  public abstract List<JSONObject> findAllKsllColorGoods(Map<String, String> paramMap)
    throws Exception;
  
  public abstract JSONObject findAllKsllColorGoods(Map<String, String> paramMap, BootStrapPage paramBootStrapPage)
    throws Exception;
  
  public abstract Map<String, JSONObject> findAllCkGoodsByGoodscode(String paramString1, String paramString2)
    throws Exception;
  
  public abstract List<JSONObject> selectAllGoodPhByGoodCode(Map<String, String> paramMap)
    throws Exception;
  
  public abstract Map<String, JSONObject> findAllKsllColorGoodsByGoodscode(Map<String, String> paramMap)
    throws Exception;
}
