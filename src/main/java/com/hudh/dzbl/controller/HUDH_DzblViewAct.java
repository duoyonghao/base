package com.hudh.dzbl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/HUDH_DzblViewAct"})
public class HUDH_DzblViewAct
{
  @RequestMapping({"/toDzblOptation.act"})
  public ModelAndView toDzblOptation(HttpServletRequest request, HttpServletResponse response)
  {
    String usercode = request.getParameter("usercode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("usercode", usercode);
    mv.setViewName("/hudh/dzbl/dzbloptation.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAddDzbl.act"})
  public ModelAndView toAddDzbl(HttpServletRequest request, HttpServletResponse response)
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/dzbl/add_dzbl.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBlDetail.act"})
  public ModelAndView toBlDetail(HttpServletRequest request, HttpServletResponse response)
  {
    ModelAndView mv = new ModelAndView();
    String blId = request.getParameter("blId");
    String blCode = request.getParameter("blCode");
    mv.addObject("blId", blId);
    mv.addObject("blCode", blCode);
    mv.setViewName("/hudh/dzbl/detail_dzbl.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCaseHistory.act"})
  public ModelAndView toCaseHistory(HttpServletRequest request, HttpServletResponse response)
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/dzbl/dzbl_ck.jsp");
    return mv;
  }
}
