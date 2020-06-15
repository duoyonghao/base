package com.hudh.lclj.controller;

import com.hudh.lclj.entity.OperationNodeInfor;
import com.hudh.lclj.service.ILcljOperationNodeInforService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
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
@RequestMapping({"/HUDH_LcljOperationNodeInforAct"})
public class HUDH_LcljOperationNodeInforAct
{
  private static Logger logger = LoggerFactory.getLogger(HUDH_LcljOperationNodeInforAct.class);
  @Autowired
  private ILcljOperationNodeInforService inforService;
  
  @RequestMapping({"/insertOperationNodeInfor.act"})
  public String insertOperationNodeInfor(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    String remark = request.getParameter("remark");
    String operation_time = request.getParameter("operation_time");
    String ismodule = request.getParameter("ismodule");
    String preoperative_verification = request.getParameter("preoperative_verification");
    String confirmOcclusalRelationship = request.getParameter("confirmOcclusalRelationship");
    String makeTransitionDenture = request.getParameter("makeTransitionDenture");
    String tryInTransitionDenture = request.getParameter("tryInTransitionDenture");
    String orderNumber = request.getParameter("orderNumber");
    OperationNodeInfor dp = new OperationNodeInfor();
    dp.setCreateuser(person.getUserId());
    dp.setOrganization(organization);
    dp.setRemark(remark);
    dp.setOperation_time(operation_time);
    dp.setIsmodule(ismodule);
    dp.setPreoperative_verification(preoperative_verification);
    dp.setConfirmOcclusalRelationship(confirmOcclusalRelationship);
    dp.setMakeTransitionDenture(makeTransitionDenture);
    dp.setTryInTransitionDenture(tryInTransitionDenture);
    dp.setNodeId("PerSurMouset");
    dp.setOrder_number(orderNumber);
    String dataId = null;
    try
    {
      this.inforService.insertOperationNodeInfor(dp, request, dataId);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectOperationNodeInforById.act"})
  public String selectOperationNodeInforById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      JSONObject json = this.inforService.selectOperationNodeInforById(id);
      YZUtility.DEAL_SUCCESS(json, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectOperationNodeInforAll.act"})
  public String selectOperationNodeInforAll(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      List<JSONObject> list = this.inforService.selectOperationNodeInforAll();
      YZUtility.RETURN_LIST(list, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/updateOperationNodeInforById.act"})
  public String updateOperationNodeInforById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    OperationNodeInfor dp = new OperationNodeInfor();
    try
    {
      this.inforService.updateOperationNodeInforById(dp);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/deleteOperationNodeInforById.act"})
  public String deleteOperationNodeInforById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      this.inforService.deleteOperationNodeInforById(id);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectOperationNodeInforByOrdernumberAndNodeId.act"})
  public String selectOperationNodeInforByOrdernumberAndNodeId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String orderNumber = request.getParameter("orderNumber");
    String nodeId = request.getParameter("nodeId");
    Map<String, String> map = new HashMap();
    if (YZUtility.isNotNullOrEmpty(orderNumber)) {
      map.put("orderNumber", orderNumber);
    }
    if (YZUtility.isNotNullOrEmpty(nodeId)) {
      map.put("nodeId", nodeId);
    }
    try
    {
      JSONObject jsonObject = this.inforService.selectOperationNodeInforByOrdernumberAndNodeId(map);
      YZUtility.DEAL_SUCCESS(jsonObject, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/saveOperationNodeInforAberrance.act"})
  public String saveOperationNodeInforAberrance(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    String remark = request.getParameter("remark");
    String operation_time = request.getParameter("operation_time");
    String ismodule = request.getParameter("ismodule");
    String preoperative_verification = request.getParameter("preoperative_verification");
    String confirmOcclusalRelationship = request.getParameter("confirmOcclusalRelationship");
    String makeTransitionDenture = request.getParameter("makeTransitionDenture");
    String tryInTransitionDenture = request.getParameter("tryInTransitionDenture");
    String orderNumber = request.getParameter("orderNumber");
    OperationNodeInfor dp = new OperationNodeInfor();
    dp.setCreateuser(person.getUserId());
    dp.setOrganization(organization);
    dp.setRemark(remark);
    dp.setOperation_time(operation_time);
    dp.setIsmodule(ismodule);
    dp.setPreoperative_verification(preoperative_verification);
    dp.setConfirmOcclusalRelationship(confirmOcclusalRelationship);
    dp.setMakeTransitionDenture(makeTransitionDenture);
    dp.setTryInTransitionDenture(tryInTransitionDenture);
    dp.setOrder_number(orderNumber);
    return null;
  }
  
  @RequestMapping({"/updateOrderTimeHospital.act"})
  public String updateOrderTimeHospital(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String order_number = request.getParameter("order_number");
    String nodeId = request.getParameter("nodeId");
    String orderTime = request.getParameter("orderTime");
    try
    {
      this.inforService.updateOrderTimeHospital(order_number, nodeId, orderTime);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
  
  public String cancelTimeHospital(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String order_number = request.getParameter("order_number");
    String nodeId = request.getParameter("nodeId");
    try
    {
      this.inforService.cancelTimeHospital(order_number, nodeId);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    }
    return null;
  }
}
