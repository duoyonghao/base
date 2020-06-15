package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljOperateRejectRecord;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface ILcljOperationNodeRejectService
{
  public abstract void insertOperationNodeReject(LcljOperateRejectRecord paramLcljOperateRejectRecord, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract List<JSONObject> findOperationNodeRejectByOrderNumber(String paramString)
    throws Exception;
}
