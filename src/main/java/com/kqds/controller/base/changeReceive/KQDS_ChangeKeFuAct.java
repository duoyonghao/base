package com.kqds.controller.base.changeReceive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kqds.service.base.changeReceive.KQDS_changeKeFuLogic;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.export.ExportTable;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_ChangeKeFuAct")
public class KQDS_ChangeKeFuAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_ChangeKeFuAct.class);
	@Autowired
	private KQDS_changeKeFuLogic logic;

	@RequestMapping(value = "/setKefu.act")
	public ModelAndView setKefu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuid = request.getParameter("menuid");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuid", menuid);
		mv.addObject("organization", organization);
		mv.setViewName("/kqdsFront/index/kfzx/xc_kefu_set.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toSetKefu.act")
	public ModelAndView toSetKefu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/kfzx/kefu_set.jsp");
		return mv;
	}

	@RequestMapping(value = "/toHisKefu.act")
	public ModelAndView toHisKefu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/kfzx/kefu_his.jsp");
		return mv;
	}

	@RequestMapping(value = "/toKefuIndex.act")
	public ModelAndView toKefuIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/kfzx/kefu_index.jsp");
		return mv;
	}

	/**
	 * 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectNoPage.act")
	public String selectNoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String queryinput = request.getParameter("queryinput");
			String toper = request.getParameter("toper");
			String oldper = request.getParameter("oldper");
			String createuser = request.getParameter("createuser"); // 指定人

			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(queryinput)) {
				map.put("queryinput", queryinput);
			}
			if (!YZUtility.isNullorEmpty(toper)) { // 当前客服
				map.put("toper", toper);
			}
			if (!YZUtility.isNullorEmpty(oldper)) {
				map.put("oldper", oldper);
			}

			if (!YZUtility.isNullorEmpty(createuser)) {
				map.put("createuser", createuser);
			}

			List<JSONObject> list = logic.selectWithPage("KQDS_CHANGE_KEFU", map);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("指定客服列表", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}