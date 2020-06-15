package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljOperateDetail;
import com.hudh.lclj.entity.LcljOrder;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.PreoperativeVerification;
import java.util.List;
import net.sf.json.JSONObject;

public interface ILcljService {
  int saveLcljOrder(LcljOrder paramLcljOrder) throws Exception;
  
  JSONObject findLcljOrderByBlcode(String paramString) throws Exception;
  
  List<JSONObject> findLcljOrderByBlcodeAndStu(String paramString1, String paramString2) throws Exception;
  
  LcljOrder findLcljOrderByOrderNumber(String paramString) throws Exception;
  
  void saveLcljOrderTrackInfo(LcljOrderTrack paramLcljOrderTrack) throws Exception;
  
  int findLcljOrderTrackByOrderNumber(String paramString) throws Exception;
  
  List<JSONObject> findLcljOrderTrackListByOrderNumber(String paramString) throws Exception;
  
  void updateOperateNoteInfo(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  int findHasOrderByBlcodeAndStu(String paramString) throws Exception;
  
  JSONObject findLcljOrderTrsackById(String paramString) throws Exception;
  
  void updateOperateStatus(String paramString1, String paramString2) throws Exception;
  
  void updateOperationFlowStatus(String paramString1, String paramString2, String paramString3) throws Exception;
  
  void changeOperateStatus(String paramString1, String paramString2, String paramString3) throws Exception;
  
  void updateOrderStatus(String paramString) throws Exception;
  
  String findOperateByTrackIdAndLink(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List<LcljOperateDetail> findLcljOrderImplemenRemakeByTrackId(String paramString1, String paramString2, String paramString3) throws Exception;
  
  JSONObject savePreoperativeVerification(PreoperativeVerification paramPreoperativeVerification) throws Exception;
  
  JSONObject findPreoperativeVerification(String paramString) throws Exception;
}
