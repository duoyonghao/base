package com.hudh.ykzz.service;

import com.hudh.ykzz.entity.YkzzDrugsIn;
import com.hudh.ykzz.entity.YkzzDrugsInDetail;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.sys.YZPerson;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface IYkzzDrugsInService
{
  public abstract void insertDrugsIn(YkzzDrugsIn paramYkzzDrugsIn, String paramString, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract void drugsAddInStock(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findAllDrugsIn(Map<String, String> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findDetailByParendId(String paramString)
    throws Exception;
  
  public abstract String deleteDrugsIn(String paramString)
    throws Exception;
  
  public abstract void deleteDrugsInById(String paramString)
    throws Exception;
  
  public abstract void deleteDrugsInDetailByParendId(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findAllCostOrder(Map<String, Object> paramMap)
    throws Exception;
  
  public abstract List<JSONObject> findCostOrderDetailByCostno(String paramString)
    throws Exception;
  
  public abstract void grantDrugs(String paramString1, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract void returnDrugs(String paramString1, String paramString2, String paramString3, String paramString4, YZPerson paramYZPerson)
    throws Exception;
  
  public abstract void updateCheckStatus(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findDrugsInAdmin(HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract List<YkzzDrugsInDetail> findBatchnumByOrderno(String paramString)
    throws Exception;
  
  public abstract YkzzDrugsInDetail findYkzzDrugsInDatailByInDetail(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findCostOrderDetailById(String paramString)
    throws Exception;
  
  public abstract List<YkzzDrugsInDetail> findBatchnumByOrderno1(String paramString)
    throws Exception;
  
  public abstract String findByParendId(String paramString)
    throws Exception;
  
  public abstract int updateYkzzDrugsInDatailByParentId(YkzzDrugsInDetail paramYkzzDrugsInDetail)
    throws Exception;
  
  public abstract List<JSONObject> findCostOrderDetailReturnBySeqid(String paramString)
    throws Exception;
  
  public abstract KqdsCostorderDetail findCostOrderDetailByParentid(String paramString)
    throws Exception;
  
  public abstract KqdsCostorderDetail findCostOrderDetailBySeqid(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findCostOrderDetailByQfbh(String paramString)
    throws Exception;
  
  public abstract KqdsCostorderDetail findCostOrderDetailSubtotalByQfbh(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findYkzzDrugsInDetailByOrderno(String paramString)
    throws Exception;
}
