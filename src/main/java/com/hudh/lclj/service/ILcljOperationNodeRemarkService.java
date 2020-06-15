package com.hudh.lclj.service;

import com.hudh.lclj.entity.LcljOperationNodeRemark;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface ILcljOperationNodeRemarkService {
  void saveNodeRemark(LcljOperationNodeRemark paramLcljOperationNodeRemark, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  List<JSONObject> findNodeRemarkByNodeId(Map<String, String> paramMap) throws Exception;
}
