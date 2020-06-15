package com.hudh.lclj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/HUDH_FlowViewAct"})
public class HUDH_FlowViewAct
{
  @RequestMapping({"/toLcljOpreation.act"})
  public ModelAndView toLcljOpreation(HttpServletRequest request, HttpServletResponse response)
  {
    String id = request.getParameter("id");
    ModelAndView mv = new ModelAndView();
    mv.addObject("id", id);
    mv.setViewName("/hudh/lclj/add_oprationInfor.jsp");
    return mv;
  }
  
  @RequestMapping({"/toConsultOperationPrepare.act"})
  public ModelAndView toConsultOperationPrepare(HttpServletRequest request, HttpServletResponse response)
  {
    String id = request.getParameter("id");
    ModelAndView mv = new ModelAndView();
    mv.addObject("id", id);
    mv.setViewName("/hudh/lclj/consultOperationPrepare.jsp");
    return mv;
  }
  
  @RequestMapping({"/toOperationRejectRecord.act"})
  public ModelAndView toOperationRejectRecord(HttpServletRequest request, HttpServletResponse response)
  {
    String orderNumber = request.getParameter("orderNumber");
    ModelAndView mv = new ModelAndView();
    mv.addObject("orderNumber", orderNumber);
    mv.setViewName("/hudh/lclj/rejectRecord.jsp");
    return mv;
  }
  
  @RequestMapping({"/toLcljInformation.act"})
  public ModelAndView toLcljInformation(HttpServletRequest request, HttpServletResponse response)
  {
    ModelAndView mv = new ModelAndView();
    String usercode = request.getParameter("usercode");
    String orderno = request.getParameter("orderno");
    String netorderid = request.getParameter("netorderid");
    String username = request.getParameter("username");
    mv.addObject("usercode", usercode);
    mv.addObject("orderno", orderno);
    mv.addObject("netorderid", netorderid);
    mv.addObject("username", username);
    mv.setViewName("/hudh/lclj/lclj_patient.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAddRemark.act"})
  public ModelAndView toAddRemark()
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/lclj/lclj_remark.jsp");
    return mv;
  }
}
