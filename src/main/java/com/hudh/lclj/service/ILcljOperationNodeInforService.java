package com.hudh.lclj.service;

import com.hudh.lclj.entity.OperationNodeInfor;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface ILcljOperationNodeInforService {
  OperationNodeInfor insertOperationNodeInfor(OperationNodeInfor paramOperationNodeInfor, HttpServletRequest paramHttpServletRequest, String paramString) throws Exception;
  
  JSONObject selectOperationNodeInforById(String paramString) throws Exception;
  
  List<JSONObject> selectOperationNodeInforAll() throws Exception;
  
  void updateOperationNodeInforById(OperationNodeInfor paramOperationNodeInfor) throws Exception;
  
  void deleteOperationNodeInforById(String paramString) throws Exception;
  
  JSONObject selectOperationNodeInforByOrdernumberAndNodeId(Map<String, String> paramMap) throws Exception;
  
  void updateOrderTimeHospital(String paramString1, String paramString2, String paramString3) throws Exception;
  
  void cancelTimeHospital(String paramString1, String paramString2) throws Exception;
}
