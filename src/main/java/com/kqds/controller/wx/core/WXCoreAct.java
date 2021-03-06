package com.kqds.controller.wx.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.service.wx.wx.WXServiceLogic;
import com.kqds.util.sys.YZUtility;

@Controller
public class WXCoreAct {

	private static Logger logger = LoggerFactory.getLogger(WXCoreAct.class);

	@Autowired
	private WXServiceLogic wxLogic;

	@RequestMapping(value = "/wechat")
	public void toDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			wxLogic.coreService(request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Throwable t) {
			t.printStackTrace();
			logger.error(t.getMessage(), t);
		}
	}

}
