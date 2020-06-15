package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljOperationNodeInforaBerrance;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface ILcljOperationNodeInforAberrance
{
  public abstract void LcljOperationNodeInforAberrance(LcljOperationNodeInforaBerrance paramLcljOperationNodeInforaBerrance, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract List<JSONObject> findOperationNodeInforAberranceByOrderNumberAndNodeId(Map<String, String> paramMap)
    throws Exception;
  
  public abstract void insertOperationNodeInforAberrance(LcljOperationNodeInforaBerrance paramLcljOperationNodeInforaBerrance, HttpServletRequest paramHttpServletRequest)
    throws Exception;
}
