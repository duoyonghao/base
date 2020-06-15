package com.hudh.lclj.controller;

import com.hudh.lclj.entity.LcljOperationNodeInforaBerrance;
import com.hudh.lclj.service.ILcljOperationNodeInforAberrance;
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
@RequestMapping({"/HUDH_LcljOperationNodeInforAberranceAct"})
public class HUDH_LcljOperationNodeInforAberranceAct
{
  private static Logger logger = LoggerFactory.getLogger(HUDH_LcljOperationNodeInforAberranceAct.class);
  @Autowired
  private ILcljOperationNodeInforAberrance aberranceService;
  
  @RequestMapping({"/saveOperationNodeInforAberrance.act"})
  public String saveOperationNodeInforAberrance(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    LcljOperationNodeInforaBerrance dp = new LcljOperationNodeInforaBerrance();
    try
    {
      this.aberranceService.LcljOperationNodeInforAberrance(dp, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/findOperationNodeInforAberranceByOrderNumberAndNodeId.act"})
  public String findOperationNodeInforAberranceByOrderNumberAndNodeId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    String order_number = request.getParameter("order_number");
    String nodeId = request.getParameter("nodeId");
    if (!YZUtility.isNullorEmpty(order_number)) {
      dataMap.put("order_number", order_number);
    }
    if (!YZUtility.isNullorEmpty(nodeId)) {
      dataMap.put("nodeId", nodeId);
    }
    try
    {
      List<JSONObject> list = this.aberranceService.findOperationNodeInforAberranceByOrderNumberAndNodeId(dataMap);
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/insertOperationNodeInforAberrance.act"})
  public String insertOperationNodeInforAberrance(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    LcljOperationNodeInforaBerrance dp = new LcljOperationNodeInforaBerrance();
    try
    {
      this.aberranceService.insertOperationNodeInforAberrance(dp, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
}
