package com.kqds.controller.sys.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.service.sys.register.YZRegisterLogic;

@Controller
@RequestMapping("YZRegisterAct")
public class YZRegisterAct {
	private Logger logger = LoggerFactory.getLogger(YZRegisterAct.class);

	private YZRegisterLogic logic = new YZRegisterLogic();

	/**
	 * 验证手机号是否已存在
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*
	 * public String checkphonenumber(HttpServletRequest request,
	 * HttpServletResponse response) throws Exception {
	 * 
	 * String resultString = ""; boolean result = true; try {
	 * 
	 * String sjhm = request.getParameter("sjhm"); if
	 * (YZUtility.isNullorEmpty(sjhm)) { result = false; } else { int num =
	 * logic.checksjhm(sjhm, TableNameUtil.SYS_REGISTER); if (num > 0) { result =
	 * false; } } resultString = "{\"valid\":" + result + "}"; PrintWriter pw =
	 * response.getWriter(); pw.println(resultString); pw.flush(); } catch
	 * (Exception ex) { YZUtility.DEAL_ERROR(null, false, ex, response, logger); }
	 * 
	 * return null; }
	 *//**
		 * 用户注册
		 * 
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
	/*
	 * public String insert(HttpServletRequest request, HttpServletResponse
	 * response) throws Exception {
	 * 
	 * try { ;
	 * 
	 * 
	 * YZRegister dp = new YZRegister(); BeanUtils.populate(dp,
	 * request.getParameterMap());
	 * 
	 * dp.setSeqId(YZUtility.getUUID());
	 * dp.setCreatetime(YZUtility.getCurDateTimeStr());
	 * logic.saveSingleUUID(TableNameUtil.SYS_REGISTER, dp); // 初始化注册信息 //
	 * registerInit(dbConn,dp,request); // 记录日志 SysLogUtil.log(SysLogUtil.NEW,
	 * SysLogUtil.SYS_REGISTER, dp, TableNameUtil.SYS_REGISTER, request);
	 * YZUtility.DEAL_SUCCESS(null, null, response, logger); } catch (Exception ex)
	 * { YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger); } return
	 * null; }
	 *//**
		 * 获取短信验证码
		 * 
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
	/*
	 * public String getSenderCode(HttpServletRequest request, HttpServletResponse
	 * response) throws Exception { String sjhm = request.getParameter("sjhm"); try
	 * { String code = HttpSenderTest.getSender(sjhm); JSONObject jobj = new
	 * JSONObject(); jobj.put("data", code); YZUtility.DEAL_SUCCESS(jobj, null,
	 * response, logger); } catch (Exception ex) {
	 * YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger); } return
	 * null; }
	 */
}
