package com.hudh.zzbl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hudh.zzbl.entity.RepairSchemeConfirm;
import com.hudh.zzbl.service.IRepairSchemeConfirmService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/HUDH_RepairSchemeConfirmAct")
public class HUDH_RepairSchemeConfirmAct {
	
	private Logger logger = LoggerFactory.getLogger(HUDH_RepairSchemeConfirmAct.class);
	
	@Autowired
	private IRepairSchemeConfirmService repairSchemeConfirmService;
	
	/**
	 * 保存修复确认单信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveRepairSchemeConfirmInfro.act")
	public String saveRepairSchemeConfirmInfro(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RepairSchemeConfirm dp = new RepairSchemeConfirm();
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
		dp.setCreateuser(person.getUserId());
		dp.setOrganization(organization);
		try {
			repairSchemeConfirmService.saveRepairSchemeConfirmInfro(dp, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 更具id更新信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateRepairInforById.act")
	public String updateRepairInforById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("seqId");
		JSONObject json = repairSchemeConfirmService.selectRepairInforById(id);
		RepairSchemeConfirm dp = (RepairSchemeConfirm) JSONObject.toBean(json, RepairSchemeConfirm.class);
		try {
			repairSchemeConfirmService.updateRepairInforById(dp, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id查新修复信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findRepairInforById.act")
	public String findRepairInforById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
	 	try {
			List<JSONObject> json = repairSchemeConfirmService.findRepairInforById(id);
			YZUtility.RETURN_LIST(json, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 查询所有信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findReapirInforAll.act")
	public String findReapirInforAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			List<JSONObject> list = repairSchemeConfirmService.findReapirInforAll();
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}
