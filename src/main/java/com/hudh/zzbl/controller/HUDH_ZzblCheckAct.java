package com.hudh.zzbl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hudh.zzbl.entity.ZzblCheck;
import com.hudh.zzbl.service.IZzblCheckService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/HUDH_ZzblCheckAct")
public class HUDH_ZzblCheckAct {
	
	private Logger logger = LoggerFactory.getLogger(HUDH_ZzblCheckAct.class);
	
	@Autowired
	private IZzblCheckService zzblCheckService;
	
	/**
	 * 保存数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save.act")
	public String save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ZzblCheck dp = new ZzblCheck();
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
		dp.setCreateuser(person.getUserId());
		dp.setOrganization(organization);
		try {
			zzblCheckService.insertZzblCheck(dp, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findZzblOprationById.act")
	public JSONObject findZzblOprationById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		try {
			List<JSONObject> json = zzblCheckService.findZzblOprationById(id);
			YZUtility.RETURN_LIST(json, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 查询所有的病历信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findAllZzblInfor.act")
	public String findAllZzblInfor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<JSONObject> list = zzblCheckService.findAllZzblInfor();
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id更新病历信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateZzblOprationById.act")
	public String updateZzblOprationById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id  = request.getParameter("seqId");
		ZzblCheck dp = zzblCheckService.selectZzblCheckById(id);
		if(YZUtility.isNotNullOrEmpty(id)) {
			dp.setSEQ_ID(id);
		}
		try {
			zzblCheckService.updateZzblOprationById(dp, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

}
