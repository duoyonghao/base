package com.kqds.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.kqds.controller.base.hzjd.KQDS_UserDocumentAct;
import com.kqds.util.sys.YZUtility;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("KQDS_TestACT")
public class Test {
	private Logger logger = LoggerFactory.getLogger(KQDS_UserDocumentAct.class);
	@RequestMapping(value = "/test.act")
	public ModelAndView test(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/test.jsp");
		return mv;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/test_save.act")
	public String testSave(HttpServletRequest request,
			HttpServletResponse response){
		String name = request.getParameter("name");
		
		JSONObject jobj = new JSONObject();
		jobj.put("backMsg", "This is Back");
		try {
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
