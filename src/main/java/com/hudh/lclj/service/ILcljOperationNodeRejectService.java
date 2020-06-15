package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljOperateRejectRecord;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface ILcljOperationNodeRejectService {
  void insertOperationNodeReject(LcljOperateRejectRecord paramLcljOperateRejectRecord, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<JSONObject> findOperationNodeRejectByOrderNumber(String paramString) throws Exception;
}
