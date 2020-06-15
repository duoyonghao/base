package com.hudh.doctorpick.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/HUDH_Goods_DoctorPickViewAct"})
public class HUDH_Goods_DoctorPickViewAct {
  @RequestMapping({"/toDoctorPick.act"})
  public ModelAndView toDoctorPick(HttpServletRequest request) {
    ModelAndView mv = new ModelAndView();
    String costno1 = request.getParameter("costno1");
    String isdrugs1 = request.getParameter("isdrugs1");
    mv.setViewName("/hudh/doctorpick/doctor_in_goods.jsp");
    mv.addObject("costno1", costno1);
    mv.addObject("isdrugs1", isdrugs1);
    return mv;
  }
  
  @RequestMapping({"/toPickSearch.act"})
  public ModelAndView toPickSearch() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/doctorpick/doctor_goods_search.jsp");
    return mv;
  }
  
  @RequestMapping({"/toGoodsList.act"})
  public ModelAndView toGoodsList(HttpServletRequest request) {
    String deptpart = request.getParameter("deptpart");
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/doctorpick/doctor_goods_house.jsp");
    mv.addObject("deptpart", deptpart);
    return mv;
  }
  
  @RequestMapping({"/toEditPickGoodsDetail.act"})
  public ModelAndView toEditPickGoodsDetail() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/doctorpick/edit_in_goods_detail.jsp");
    return mv;
  }
  
  @RequestMapping({"/toGoodsPickSendBack.act"})
  public ModelAndView toGoodsPickSendBack(HttpServletRequest request) {
    String goodsuuid1 = request.getParameter("goodsuuid1");
    String id = request.getParameter("id");
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/doctorpick/goods_send_back.jsp");
    mv.addObject("goodsuuid1", goodsuuid1);
    mv.addObject("id", id);
    return mv;
  }
  
  @RequestMapping({"/toSendBackGoodsSearch.act"})
  public ModelAndView toSendBackGoodsSearch() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/doctorpick/sendBack_goods_search.jsp");
    return mv;
  }
}
