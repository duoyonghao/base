package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljOperateDetail;
import com.hudh.lclj.entity.LcljOrder;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.PreoperativeVerification;
import java.util.List;
import net.sf.json.JSONObject;

public abstract interface ILcljService
{
  public abstract int saveLcljOrder(LcljOrder paramLcljOrder)
    throws Exception;
  
  public abstract JSONObject findLcljOrderByBlcode(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findLcljOrderByBlcodeAndStu(String paramString1, String paramString2)
    throws Exception;
  
  public abstract LcljOrder findLcljOrderByOrderNumber(String paramString)
    throws Exception;
  
  public abstract void saveLcljOrderTrackInfo(LcljOrderTrack paramLcljOrderTrack)
    throws Exception;
  
  public abstract int findLcljOrderTrackByOrderNumber(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> findLcljOrderTrackListByOrderNumber(String paramString)
    throws Exception;
  
  public abstract void updateOperateNoteInfo(String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception;
  
  public abstract int findHasOrderByBlcodeAndStu(String paramString)
    throws Exception;
  
  public abstract JSONObject findLcljOrderTrsackById(String paramString)
    throws Exception;
  
  public abstract void updateOperateStatus(String paramString1, String paramString2)
    throws Exception;
  
  public abstract void updateOperationFlowStatus(String paramString1, String paramString2, String paramString3)
    throws Exception;
  
  public abstract void changeOperateStatus(String paramString1, String paramString2, String paramString3)
    throws Exception;
  
  public abstract void updateOrderStatus(String paramString)
    throws Exception;
  
  public abstract String findOperateByTrackIdAndLink(String paramString1, String paramString2, String paramString3)
    throws Exception;
  
  public abstract List<LcljOperateDetail> findLcljOrderImplemenRemakeByTrackId(String paramString1, String paramString2, String paramString3)
    throws Exception;
  
  public abstract JSONObject savePreoperativeVerification(PreoperativeVerification paramPreoperativeVerification)
    throws Exception;
  
  public abstract JSONObject findPreoperativeVerification(String paramString)
    throws Exception;
}
