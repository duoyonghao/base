package com.hudh.lclj.controller;

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

import com.hudh.lclj.entity.LcljOperationNodeInforaBerrance;
import com.hudh.lclj.service.ILcljOperationNodeInforAberrance;
import com.kqds.util.sys.YZUtility;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/HUDH_LcljOperationNodeInforAberranceAct")
public class HUDH_LcljOperationNodeInforAberranceAct {
	
	private static Logger logger = LoggerFactory.getLogger(HUDH_LcljOperationNodeInforAberranceAct.class);
	
	@Autowired
	private ILcljOperationNodeInforAberrance aberranceService;
	
	@RequestMapping(value = "/saveOperationNodeInforAberrance.act")
	public String saveOperationNodeInforAberrance(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		YZPerson person = SessionUtil.getLoginPerson(request);
//		String organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
//		String remark = request.getParameter("remark");
//		String operation_time = request.getParameter("operation_time");
//		String ismodule = request.getParameter("ismodule");
//		String preoperative_verification = request.getParameter("preoperative_verification");
//		String confirmOcclusalRelationship = request.getParameter("confirmOcclusalRelationship");
//		String makeTransitionDenture = request.getParameter("makeTransitionDenture");
//		String tryInTransitionDenture = request.getParameter("tryInTransitionDenture");
//		String orderNumber = request.getParameter("orderNumber");
//		String nodeId = request.getParameter("nodeId");
		LcljOperationNodeInforaBerrance dp = new LcljOperationNodeInforaBerrance();
//		dp.setCreateuser(person.getUserId());
//		dp.setOrganization(organization);
//		dp.setRemark(remark);
//		dp.setOperation_time(operation_time);
//		dp.setIsmodule(ismodule);
//		dp.setPreoperative_verification(preoperative_verification);
//		dp.setConfirmOcclusalRelationship(confirmOcclusalRelationship);
//		dp.setMakeTransitionDenture(makeTransitionDenture);
//		dp.setTryInTransitionDenture(tryInTransitionDenture);
//		dp.setOrder_number(orderNumber);
//		dp.setNodeId(nodeId);
		try {
			aberranceService.LcljOperationNodeInforAberrance(dp, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	
	@RequestMapping(value = "/findOperationNodeInforAberranceByOrderNumberAndNodeId.act")
	public String findOperationNodeInforAberranceByOrderNumberAndNodeId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		String order_number = request.getParameter("order_number");
		String nodeId = request.getParameter("nodeId");
		if (!YZUtility.isNullorEmpty(order_number)) {
			dataMap.put("order_number", order_number);
		}
		if (!YZUtility.isNullorEmpty(nodeId)) {
			dataMap.put("nodeId", nodeId);
		}
		try {
			List<JSONObject> list = aberranceService.findOperationNodeInforAberranceByOrderNumberAndNodeId(dataMap);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	@RequestMapping(value = "/insertOperationNodeInforAberrance.act")
	public String insertOperationNodeInforAberrance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LcljOperationNodeInforaBerrance dp = new LcljOperationNodeInforaBerrance();
		try {
			aberranceService.insertOperationNodeInforAberrance(dp, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}
