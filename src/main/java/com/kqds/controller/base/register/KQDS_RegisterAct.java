package com.kqds.controller.base.register;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.entity.sys.YZRegister;
import com.kqds.service.sys.register.YZRegisterLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_RegisterAct")
public class KQDS_RegisterAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_RegisterAct.class);
	@Autowired
	private YZRegisterLogic logic;

	@RequestMapping(value = "/toDetailRegister.act")
	public ModelAndView toRoomSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/register/list_kqds_register.jsp");
		return mv;
	}

	/**
	 * 病程记录详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			YZRegister en = (YZRegister) logic.loadObjSingleUUID(TableNameUtil.SYS_REGISTER, seqId);
			if (en == null) {
				throw new Exception("数据不存在");
			}
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 分页查询 业务日志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @RequestMapping(value = "/selectPage.act") public String
	 * selectPage(HttpServletRequest request, HttpServletResponse response) throws
	 * Exception { try { // 初始化分页实体类 BootStrapPage bp = new BootStrapPage(); //
	 * 封装参数到实体类 BeanUtils.populate(bp, request.getParameterMap()); String dwmc =
	 * request.getParameter("dwmc"); String lxr = request.getParameter("lxr");
	 * String sjhm = request.getParameter("sjhm"); String starttime =
	 * request.getParameter("starttime"); String endtime =
	 * request.getParameter("endtime");
	 * 
	 * String flag = request.getParameter("flag") == null ? "" :
	 * request.getParameter("flag"); String fieldArr =
	 * request.getParameter("fieldArr") == null ? "" :
	 * request.getParameter("fieldArr"); String fieldnameArr =
	 * request.getParameter("fieldnameArr") == null ? "" :
	 * request.getParameter("fieldnameArr"); Map<String, String> map = new
	 * HashMap<String, String>(); if (!YZUtility.isNullorEmpty(dwmc)) {
	 * map.put("dwmc", dwmc); } if (!YZUtility.isNullorEmpty(lxr)) { map.put("lxr",
	 * lxr); } if (!YZUtility.isNullorEmpty(sjhm)) { map.put("sjhm", sjhm); } if
	 * (!YZUtility.isNullorEmpty(starttime)) { map.put("starttime", starttime +
	 * " 00:00:00"); } if (!YZUtility.isNullorEmpty(endtime)) { map.put("endtime",
	 * endtime + " 23:59:59"); } // 导出 if (!YZUtility.isNullorEmpty(flag)) {
	 * map.put("flag", flag); } JSONObject data =
	 * logic.selectWithPage(TableNameUtil.SYS_REGISTER, bp, map);
	 * -------导出excel--------- if (flag != null && flag.equals("exportTable")) {
	 * ExportTable.exportBootStrapTable2Excel("注册列表", fieldArr, fieldnameArr,
	 * (List<JSONObject>) data.getJSONArray("rows"), response, request); return
	 * null; } YZUtility.DEAL_SUCCESS(data, null, response, logger); } catch
	 * (Exception ex) { YZUtility.DEAL_ERROR(null, false, ex, response, logger); }
	 * return null; }
	 *//**
		 * 分页查询 业务日志
		 * 
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
	/*
	 * @RequestMapping(value = "/selectPage4SysLog.act") public String
	 * selectPage4SysLog(HttpServletRequest request, HttpServletResponse response)
	 * throws Exception { try { // 初始化分页实体类 BootStrapPage bp = new BootStrapPage();
	 * // 封装参数到实体类 BeanUtils.populate(bp, request.getParameterMap()); String
	 * username = request.getParameter("username"); String usercode =
	 * request.getParameter("usercode"); String createuser =
	 * request.getParameter("createuser"); Map<String, String> map = new
	 * HashMap<String, String>(); if (!YZUtility.isNullorEmpty(usercode)) {
	 * map.put("usercode", usercode); } if (!YZUtility.isNullorEmpty(username)) {
	 * map.put("username", username); } if (!YZUtility.isNullorEmpty(createuser)) {
	 * map.put("createuser", createuser); }
	 * 
	 * map.put("logtype", "1"); // 查询系统日志 map.put("organization",
	 * ChainUtil.getOrganizationFromUrl(request)); JSONObject data =
	 * logic.selectWithPage(TableNameUtil.KQDS_BCJL, bp, map);
	 * YZUtility.DEAL_SUCCESS(data, null, response, logger); } catch (Exception ex)
	 * { YZUtility.DEAL_ERROR(null, false, ex, response, logger); } return null; }
	 */

}