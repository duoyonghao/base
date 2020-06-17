package com.hudh.dzbl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/HUDH_DzblViewAct")
public class HUDH_DzblViewAct {
	/**
	 * 电子病历主页面跳转
	 */
	@RequestMapping("/toDzblOptation.act")
	public ModelAndView toDzblOptation(HttpServletRequest request,HttpServletResponse response){
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/hudh/dzbl/dzbloptation.jsp");
		return mv;
	}
	
	/**
	 * 新建病历模板
	 */
	@RequestMapping("/toAddDzbl.act")
	public ModelAndView toAddDzbl(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/dzbl/add_dzbl.jsp");
		return mv;
	}
	
	/**
	 * 打印详情展示
	 */
	@RequestMapping("/toBlDetail.act")
	public ModelAndView toBlDetail(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		String blId = request.getParameter("blId"); //当前需打印数据id
		String blCode = request.getParameter("blCode"); //当前患者的病例号
		mv.addObject("blId",blId);
		mv.addObject("blCode",blCode);
		mv.setViewName("/hudh/dzbl/detail_dzbl.jsp");
		return mv;
	}
	
	/**
	  * @Title: toCaseHistory   
	  * @Description: TODO(电子病历查看)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: ModelAndView
	  * @dateTime:2020年1月13日 上午10:21:17
	 */
	@RequestMapping("/toCaseHistory.act")
	public ModelAndView toCaseHistory(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/hudh/dzbl/dzbl_ck.jsp");
		return mv;
	}
	
}
