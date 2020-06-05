package com.hudh.zzbl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/HUDH_ZzblViewAct")
public class HUDH_ZzblViewAct {
	
	@RequestMapping(value = "/toAnamnesis.act")
	public ModelAndView toAnamnesis(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName("");
		return mView;
	}
	
}
