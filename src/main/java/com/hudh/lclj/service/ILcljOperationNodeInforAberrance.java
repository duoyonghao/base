package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljOperationNodeInforaBerrance;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface ILcljOperationNodeInforAberrance {
  void LcljOperationNodeInforAberrance(LcljOperationNodeInforaBerrance paramLcljOperationNodeInforaBerrance, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<JSONObject> findOperationNodeInforAberranceByOrderNumberAndNodeId(Map<String, String> paramMap) throws Exception;
  
  void insertOperationNodeInforAberrance(LcljOperationNodeInforaBerrance paramLcljOperationNodeInforaBerrance, HttpServletRequest paramHttpServletRequest) throws Exception;
}
