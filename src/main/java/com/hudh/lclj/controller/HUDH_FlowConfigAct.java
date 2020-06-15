package com.hudh.lclj.controller;

import com.hudh.lclj.entity.LcljFlow;
import com.hudh.lclj.entity.LcljNodeConfig;
import com.hudh.lclj.service.ILcljFlowConfigService;
import com.hudh.lclj.service.ILcljNodeConfigService;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/HUDH_FlowConfigAct"})
public class HUDH_FlowConfigAct
{
  private Logger logger = LoggerFactory.getLogger(HUDH_FlowConfigAct.class);
  @Autowired
  private ILcljFlowConfigService lcljFlowConfigService;
  @Autowired
  private ILcljNodeConfigService lcljNodeConfigService;
  
  @RequestMapping({"/toIndex.act"})
  public ModelAndView toIndex(HttpServletRequest request, HttpServletResponse response)
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/admin/lclj/index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toTop.act"})
  public ModelAndView toTop(HttpServletRequest request, HttpServletResponse response)
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/admin/lclj/top.jsp");
    return mv;
  }
  
  @RequestMapping({"/toLeft.act"})
  public ModelAndView toLeft(HttpServletRequest request, HttpServletResponse response)
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/admin/lclj/left.jsp");
    return mv;
  }
  
  @RequestMapping({"/toList.act"})
  public ModelAndView toList(HttpServletRequest request, HttpServletResponse response)
  {
    ModelAndView mv = new ModelAndView();
    String flowCode = request.getParameter("flowCode");
    String flowName = request.getParameter("flowName");
    mv.addObject("flowCode", flowCode);
    mv.addObject("flowName", flowName);
    mv.setViewName("/admin/lclj/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNewAdd.act"})
  public ModelAndView toNewMenu(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/admin/lclj/newAdd.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNewNodeAdd.act"})
  public ModelAndView toNewNodeAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    String flowCode = request.getParameter("flowCode");
    String flowName = request.getParameter("flowName");
    mv.addObject("flowCode", flowCode);
    mv.addObject("flowName", flowName);
    mv.setViewName("/admin/lclj/newNodeAdd.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertLcljFlow.act"})
  public String insertLcljFlow(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String flowName = request.getParameter("flowName");
    String flowCode = request.getParameter("flowCode");
    String remark = request.getParameter("remark");
    String num = request.getParameter("num");
    String organization = request.getParameter("organization");
    LcljFlow lcljFlow = new LcljFlow();
    lcljFlow.setFlowName(flowName);
    lcljFlow.setFlowCode(flowCode);
    lcljFlow.setNum(Integer.valueOf(num));
    lcljFlow.setOrganization(organization);
    lcljFlow.setRemark(remark);
    try
    {
      this.lcljFlowConfigService.insertLcljFlow(lcljFlow);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findAllLcljFlow.act"})
  public String findAllLcljFlow(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    String organization = ChainUtil.getCurrentOrganization(request);
    dataMap.put("organization", organization);
    try
    {
      List<JSONObject> list = this.lcljFlowConfigService.findAllLcljFlow(dataMap);
      JSONArray jsonArray = new JSONArray();
      if ((list != null) && (list.size() > 0))
      {
        JSONObject tempObj = new JSONObject();
        for (JSONObject obj : list)
        {
          tempObj = new JSONObject();
          tempObj.put("id", obj.get("flowcode"));
          tempObj.put("pId", "0");
          tempObj.put("name", obj.get("flowname"));
          jsonArray.add(tempObj);
        }
      }
      YZUtility.RETURN_LIST(jsonArray, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/insertNodeConfig.act"})
  public String insertNodeConfig(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String flowCode = request.getParameter("flowCode");
    String num = request.getParameter("num");
    String nodeId = request.getParameter("nodeId");
    String nodeName = request.getParameter("nodename");
    String authorType = request.getParameter("authorType");
    String author = request.getParameter("author");
    String viewUrl = request.getParameter("viewUrl");
    String nodeLimit = request.getParameter("nodeLimit");
    String limitType = request.getParameter("limitType");
    String organization = request.getParameter("organization");
    LcljNodeConfig lcljNodeConfig = new LcljNodeConfig();
    lcljNodeConfig.setFlowCode(flowCode);
    lcljNodeConfig.setNum(Integer.valueOf(num));
    lcljNodeConfig.setNodeId(nodeId);
    lcljNodeConfig.setNodeName(nodeName);
    lcljNodeConfig.setAuthorType(authorType);
    lcljNodeConfig.setAuthor(author);
    lcljNodeConfig.setViewUrl(viewUrl);
    lcljNodeConfig.setNodeLimit(nodeLimit);
    lcljNodeConfig.setLimitType(limitType);
    lcljNodeConfig.setOrganization(organization);
    try
    {
      this.lcljNodeConfigService.insertNodeConfig(lcljNodeConfig);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/deleteNodeConfigByCodeAndNodeId.act"})
  public String deleteNodeConfigByCodeAndNodeId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String flowCode = request.getParameter("flowCode");
    String nodeId = request.getParameter("nodeId");
    String organization = request.getParameter("organization");
    Map<String, String> dataMap = new HashMap();
    if (YZUtility.isNotNullOrEmpty(flowCode)) {
      dataMap.put("flowCode", flowCode);
    }
    if (YZUtility.isNotNullOrEmpty(nodeId)) {
      dataMap.put("nodeId", nodeId);
    }
    if (YZUtility.isNotNullOrEmpty(organization)) {
      dataMap.put("organization", organization);
    }
    try
    {
      this.lcljNodeConfigService.deleteNodeConfigByCodeAndNodeId(dataMap);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findAllNodeConfigByFlowCode.act"})
  public String findAllNodeConfigByFlowCode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    String organization = ChainUtil.getCurrentOrganization(request);
    dataMap.put("organization", organization);
    String flowCode = request.getParameter("flowCode");
    if (YZUtility.isNotNullOrEmpty(flowCode)) {
      dataMap.put("flowCode", flowCode);
    }
    try
    {
      List<JSONObject> list = this.lcljNodeConfigService.findAllNodeConfigByFlowCode(dataMap);
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST}, value={"/findLcljFlowByDentalJaw.act"})
  public String findLcljFlowByDentalJaw(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String dentalJaw = request.getParameter("dentalJaw");
    String flowType = request.getParameter("flowType");
    String articleType = request.getParameter("articleType");
    Map<String, String> map = new HashMap();
    map.put("dentalJaw", dentalJaw);
    map.put("flowType", flowType);
    map.put("articleType", articleType);
    try
    {
      List<JSONObject> list = this.lcljFlowConfigService.findLcljFlowByDentalJaw(map);
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findLcljFlowInforByDentalJaw.act"})
  public String findLcljFlowInforByDentalJaw(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String dentalJaw = request.getParameter("dentalJaw");
    try
    {
      List<JSONObject> list = this.lcljFlowConfigService.findLcljFlowInforByDentalJaw(dentalJaw);
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
}
