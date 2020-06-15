package com.hudh.ykzz.service;

import com.hudh.ykzz.entity.YkzzDrugs;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.hudh.ykzz.entity.YkzzDrugsOutDetail;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface IYkzzDrugsService
{
  public abstract int insertDrugsinfor(YkzzDrugs paramYkzzDrugs, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract List<YkzzDrugs> findDeugsByContryCode(String paramString1, String paramString2)
    throws Exception;
  
  public abstract JSONObject selectDrugsByPrimaryId(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> selectAllDrugsInfor(Map<String, String> paramMap)
    throws Exception;
  
  public abstract void deleteDrugsByPrimaryId(String paramString)
    throws Exception;
  
  public abstract void updateDrugsByPrimaryId(YkzzDrugs paramYkzzDrugs)
    throws Exception;
  
  public abstract List<JSONObject> selectDrugsInforByConditionQuery(Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<YkzzDrugs> selectDrugsByOrderNoStr(List<String> paramList)
    throws Exception;
  
  public abstract List<YkzzDrugs> selectDrugsByIdStr(List<YkzzDrugsInDetail> paramList)
    throws Exception;
  
  public abstract List<YkzzDrugs> selectDrugsOutByIdStr(List<YkzzDrugsOutDetail> paramList)
    throws Exception;
  
  public abstract void batchUpdateDrugsByPrimaryId(List<YkzzDrugs> paramList)
    throws Exception;
  
  public abstract void saveBatchInsert(List<List<String>> paramList, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract List<YkzzDrugs> getAllDrugsInfor(Map<String, String> paramMap)
    throws Exception;
  
  public abstract void forbiddenDrugs(String paramString)
    throws Exception;
  
  public abstract void recoverDrugs(String paramString)
    throws Exception;
}
