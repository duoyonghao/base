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

public interface IFlowService {
  void saveLcljOrderTrackInfo(LcljOrderTrack paramLcljOrderTrack, String paramString, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  void gotoNextNode(String paramString) throws Exception;
  
  void saveOptRecode(LcljOptRecode paramLcljOptRecode) throws Exception;
  
  List<JSONObject> findOptRecodeList(String paramString1, String paramString2) throws Exception;
  
  JSONObject findOrderTrackInfo(String paramString) throws Exception;
  
  JSONObject findOrderTrackInforByOrderNumber(YZPerson paramYZPerson, BootStrapPage paramBootStrapPage, Map<String, String> paramMap, String paramString, JSONObject paramJSONObject) throws Exception;
  
  JSONObject findLcljAdmin(HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<JSONObject> findLcljAdminOrAgency(HttpServletRequest paramHttpServletRequest) throws Exception;
  
  void updateAgencyUser(YZPara paramYZPara) throws Exception;
  
  void updateRemarkStus(String paramString1, String paramString2) throws Exception;
  
  List<KqdsReg> findRegListByBlcode(String paramString) throws Exception;
  
  JSONObject findOrderTrackInforByConditionQuery(YZPerson paramYZPerson, Map<String, String> paramMap, String paramString) throws Exception;
  
  void reject(String paramString) throws Exception;
  
  void updateOrderTrack(Map<String, String> paramMap) throws Exception;
  
  void updateOrderTrackNodes(Map<String, String> paramMap, String paramString1, String paramString2, String paramString3, LcljOrderTrack paramLcljOrderTrack, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  void updateOrderTrackById(Map<String, String> paramMap) throws Exception;
  
  void deleteOrderTrackInforById(String paramString) throws Exception;
  
  LcljOrderTrack findOrderTrackInforById(String paramString) throws Exception;
  
  void changeLcljOrderTrackBoneStatus(LcljOrderTrack paramLcljOrderTrack, String paramString) throws Exception;
  
  JSONObject findLcljOrderTrsackById(String paramString) throws Exception;
  
  LcljOrderTrack findLcljOrderTrsackByseqId(String paramString) throws Exception;
  
  void updateLcljOrderTrackIsobsolete(String paramString, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  void updateLcljOrderTrackById(LcljOrderTrack paramLcljOrderTrack) throws Exception;
  
  Integer editToothBit(LcljOrderTrack paramLcljOrderTrack) throws Exception;
  
  void saveOperatingRecord(OperatingRecord paramOperatingRecord) throws Exception;
  
  JSONObject findPatientInformation(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List<LcljNodeConfig> findNodeName() throws Exception;
}
