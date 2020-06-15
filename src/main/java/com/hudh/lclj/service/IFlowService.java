package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljNodeConfig;
import com.hudh.lclj.entity.LcljOptRecode;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.OperatingRecord;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPara;
import com.kqds.entity.sys.YZPerson;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface IFlowService
{
  public abstract void saveLcljOrderTrackInfo(LcljOrderTrack paramLcljOrderTrack, String paramString, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract void gotoNextNode(String paramString)
    throws Exception;
  
  public abstract void saveOptRecode(LcljOptRecode paramLcljOptRecode)
    throws Exception;
  
  public abstract List<JSONObject> findOptRecodeList(String paramString1, String paramString2)
    throws Exception;
  
  public abstract JSONObject findOrderTrackInfo(String paramString)
    throws Exception;
  
  public abstract JSONObject findOrderTrackInforByOrderNumber(YZPerson paramYZPerson, BootStrapPage paramBootStrapPage, Map<String, String> paramMap, String paramString, JSONObject paramJSONObject)
    throws Exception;
  
  public abstract JSONObject findLcljAdmin(HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract List<JSONObject> findLcljAdminOrAgency(HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract void updateAgencyUser(YZPara paramYZPara)
    throws Exception;
  
  public abstract void updateRemarkStus(String paramString1, String paramString2)
    throws Exception;
  
  public abstract List<KqdsReg> findRegListByBlcode(String paramString)
    throws Exception;
  
  public abstract JSONObject findOrderTrackInforByConditionQuery(YZPerson paramYZPerson, Map<String, String> paramMap, String paramString)
    throws Exception;
  
  public abstract void reject(String paramString)
    throws Exception;
  
  public abstract void updateOrderTrack(Map<String, String> paramMap)
    throws Exception;
  
  public abstract void updateOrderTrackNodes(Map<String, String> paramMap, String paramString1, String paramString2, String paramString3, LcljOrderTrack paramLcljOrderTrack, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract void updateOrderTrackById(Map<String, String> paramMap)
    throws Exception;
  
  public abstract void deleteOrderTrackInforById(String paramString)
    throws Exception;
  
  public abstract LcljOrderTrack findOrderTrackInforById(String paramString)
    throws Exception;
  
  public abstract void changeLcljOrderTrackBoneStatus(LcljOrderTrack paramLcljOrderTrack, String paramString)
    throws Exception;
  
  public abstract JSONObject findLcljOrderTrsackById(String paramString)
    throws Exception;
  
  public abstract LcljOrderTrack findLcljOrderTrsackByseqId(String paramString)
    throws Exception;
  
  public abstract void updateLcljOrderTrackIsobsolete(String paramString, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract void updateLcljOrderTrackById(LcljOrderTrack paramLcljOrderTrack)
    throws Exception;
  
  public abstract Integer editToothBit(LcljOrderTrack paramLcljOrderTrack)
    throws Exception;
  
  public abstract void saveOperatingRecord(OperatingRecord paramOperatingRecord)
    throws Exception;
  
  public abstract JSONObject findPatientInformation(String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception;
  
  public abstract List<LcljNodeConfig> findNodeName()
    throws Exception;
}
