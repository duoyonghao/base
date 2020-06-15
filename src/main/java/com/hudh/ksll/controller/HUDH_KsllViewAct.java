package com.hudh.ksll.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/HUDH_KSllViewAct"})
public class HUDH_KsllViewAct
{
  @RequestMapping({"/toKsllIndex.act"})
  public ModelAndView toKsllIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/ksll/ksll_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toKsllGoods.act"})
  public ModelAndView toKsllGoods(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String deptpart = request.getParameter("deptpart");
    ModelAndView mv = new ModelAndView();
    mv.addObject("deptpart", deptpart);
    mv.setViewName("/hudh/ksll/ksll_goods.jsp");
    return mv;
  }
  
  @RequestMapping({"/toGoodsHouse.act"})
  public ModelAndView toGoodsHouse(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/ksll/goods_house.jsp");
    return mv;
  }
  
  @RequestMapping({"/toKsllCostColor.act"})
  public ModelAndView toKsllCostColor(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/ksll/ksll_cost_color.jsp");
    return mv;
  }
  
  @RequestMapping({"/toKsllOutGoods.act"})
  public ModelAndView toKsllOutGoods(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    String ksllColorId = request.getParameter("ksllColorId");
    mv.addObject("ksllColorId", ksllColorId);
    mv.setViewName("/hudh/ksll/ksll_out_goods.jsp");
    return mv;
  }
  
  @RequestMapping({"/toKsllOutGoods2.act"})
  public ModelAndView toKsllOutGoods2(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    String ksllColorId = request.getParameter("ksllColorId");
    mv.addObject("ksllColorId", ksllColorId);
    mv.setViewName("/hudh/ksll2/ksll_out_goods.jsp");
    return mv;
  }
  
  @RequestMapping({"/toKsllRecode.act"})
  public ModelAndView toKsllRecode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/ksll/ksll_recode.jsp");
    return mv;
  }
  
  @RequestMapping({"/toKsllReplacement.act"})
  public ModelAndView toKsllReplacement(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/ksll/ksll_replacement.jsp");
    return mv;
  }
  
  @RequestMapping({"/toReplacementHouse.act"})
  public ModelAndView toReplacementHouse(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    String deptpart = request.getParameter("deptpart");
    String sshouse = request.getParameter("sshouse");
    mv.addObject("deptpart", deptpart);
    mv.addObject("sshouse", sshouse);
    mv.setViewName("/hudh/ksll/replacement_house.jsp");
    return mv;
  }
  
  @RequestMapping({"/toKsllCKReplacement.act"})
  public ModelAndView toKsllCKReplacement(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/ksll/ksll_ck_replacement.jsp");
    return mv;
  }
  
  @RequestMapping({"/toKsllReplacementRecode.act"})
  public ModelAndView toKsllReplacementRecode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/ksll/ksll_replacement_recode.jsp");
    return mv;
  }
  
  @RequestMapping({"/toKsllConsum.act"})
  public ModelAndView toKsllCKConsum(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/ksll/ksll_consum.jsp");
    return mv;
  }
  
  @RequestMapping({"/toKsllConsumRecode.act"})
  public ModelAndView toKsllConsumRecode(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/ksll/ksll_consum_recode.jsp");
    return mv;
  }
}
