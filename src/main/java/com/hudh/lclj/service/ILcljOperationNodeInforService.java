package com.hudh.lclj.service;

import com.hudh.lclj.entity.OperationNodeInfor;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface ILcljOperationNodeInforService
{
  public abstract OperationNodeInfor insertOperationNodeInfor(OperationNodeInfor paramOperationNodeInfor, HttpServletRequest paramHttpServletRequest, String paramString)
    throws Exception;
  
  public abstract JSONObject selectOperationNodeInforById(String paramString)
    throws Exception;
  
  public abstract List<JSONObject> selectOperationNodeInforAll()
    throws Exception;
  
  public abstract void updateOperationNodeInforById(OperationNodeInfor paramOperationNodeInfor)
    throws Exception;
  
  public abstract void deleteOperationNodeInforById(String paramString)
    throws Exception;
  
  public abstract JSONObject selectOperationNodeInforByOrdernumberAndNodeId(Map<String, String> paramMap)
    throws Exception;
  
  public abstract void updateOrderTimeHospital(String paramString1, String paramString2, String paramString3)
    throws Exception;
  
  public abstract void cancelTimeHospital(String paramString1, String paramString2)
    throws Exception;
}
