package com.hudh.query.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/HUDH_SearchAct")
public class HUDH_SearchAct {
 @RequestMapping(value = "/toJdzxCenter.act")
 public ModelAndView toJdzxCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
  ModelAndView mv = new ModelAndView();
  mv.setViewName("/kqdsFront/index/query/jzcx_center.jsp");
  return mv;
 }
 
 @RequestMapping(value = "/toJdzxCenterQueryjz.act")
 public ModelAndView toJdzxCenterQueryjz(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String usercode = request.getParameter("usercode");
	String username = request.getParameter("username");
	ModelAndView mv = new ModelAndView();
	mv.addObject("usercode", usercode);
	mv.addObject("username", username);
	mv.setViewName("/kqdsFront/index/query/jzcx_center.jsp");
	return mv;
 } 
 
 @RequestMapping(value = "/toJdzxCenterxx.act")
 public ModelAndView toJdzxCenterxx(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 	String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("username", username);
	  mv.setViewName("/kqdsFront/index/query/xxcx_center.jsp");
	  return mv;
 }
 
 @RequestMapping(value = "/toJdzxCenterfy.act")
 public ModelAndView toJdzxCenterfy(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 	String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("username", username);
	  mv.setViewName("kqdsFront/index/query/fycx_center.jsp");
	  return mv;
 }
 
 @RequestMapping(value = "/toJdzxCentermx.act")
 public ModelAndView toJdzxCentermx(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 	String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("username", username);
	  mv.setViewName("kqdsFront/index/query/mxcx_center.jsp");
	  return mv;
 }
 
 @RequestMapping(value = "/toJdzxCenterdz.act")
 public ModelAndView toJdzxCenterdz(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 	String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("username", username);
	  mv.setViewName("kqdsFront/index/query/dz_query.jsp");
	  return mv;
 }
 
/* @RequestMapping(value = "/toJdzxCenterhf.act")
 public ModelAndView toJdzxCenterhf(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 	String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("username", username);
	  mv.setViewName("kqdsFront/index/query/user_visit.jsp");
	  return mv;
 }*/
 
 @RequestMapping(value = "/toCondition.act")
 public ModelAndView toCondition(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 String url = request.getParameter("url");
	 ModelAndView mv = new ModelAndView();
	 mv.addObject("url", url);
	 mv.setViewName("kqdsFront/index/query/queries.jsp");
	 return mv;
 }
}
