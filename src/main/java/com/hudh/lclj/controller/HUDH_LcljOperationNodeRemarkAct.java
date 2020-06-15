package com.hudh.lclj.controller;

import com.hudh.lclj.entity.LcljOperationNodeRemark;
import com.hudh.lclj.service.ILcljOperationNodeRemarkService;
import com.kqds.util.sys.YZUtility;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/HUDH_LcljOperationNodeRemarkAct"})
public class HUDH_LcljOperationNodeRemarkAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_LcljOperationNodeRemarkAct.class);
  
  @Autowired
  private ILcljOperationNodeRemarkService remarkService;
  
  @RequestMapping({"/saveNodeRemark.act"})
  public String saveNodeRemark(HttpServletRequest request, HttpServletResponse response) throws Exception {
    LcljOperationNodeRemark dp = new LcljOperationNodeRemark();
    try {
      this.remarkService.saveNodeRemark(dp, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findNodeRemarkByNodeId.act"})
  public String findNodeRemarkByNodeId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String order_number = request.getParameter("order_number");
    String nodeId = request.getParameter("nodeId");
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("order_number", order_number);
    dataMap.put("nodeId", nodeId);
    try {
      List<JSONObject> list = this.remarkService.findNodeRemarkByNodeId(dataMap);
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
}
