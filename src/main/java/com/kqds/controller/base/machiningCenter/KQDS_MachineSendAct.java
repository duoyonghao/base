package com.kqds.controller.base.machiningCenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.entity.base.KqdsMachineSend;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.machiningCenter.KQDS_MachineSendLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_MachineSendAct")
public class KQDS_MachineSendAct {
	
	private static Logger logger = LoggerFactory.getLogger(KQDS_MachineSendAct.class);
	
	@Autowired
	private KQDS_MachineSendLogic machineSendLogic;
	
	/**
	 * 保存发送信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveMachineSend.act")
	public String saveMachineSend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		String usercode = request.getParameter("usercode");
		String phoneNumber = request.getParameter("phoneNumber");
		String sendcontent = request.getParameter("sendcontent");
		String machineId = request.getParameter("machineId");
		String orderNumber = request.getParameter("orderNumber");
		String systemNumber = request.getParameter("systemNumber");
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String organization = ChainUtil.getCurrentOrganization(request);
			KqdsMachineSend dp = new KqdsMachineSend();
			dp.setSeqId(YZUtility.getUUID());
			dp.setCreatetime(YZUtility.getCurDateTimeStr());
			dp.setCreateuser(person.getSeqId());
			dp.setOrganization(organization);
			dp.setSendtime(YZUtility.getCurDateTimeStr());
			dp.setSendcontent(sendcontent);
			dp.setMachineId(machineId);
			dp.setOrderNumber(orderNumber);
			dp.setUsercode(usercode);
			dp.setUsername(username);
			dp.setPhoneNumber(phoneNumber);
			dp.setSystemNumber(systemNumber);
			machineSendLogic.saveMachineSend(dp);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 修改发送信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateMachineSendById.act")
	public String updateMachineSendById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			KqdsMachineSend dp = new KqdsMachineSend();
			BeanUtils.populate(dp, request.getParameterMap());
			machineSendLogic.updateMachineSendById(dp);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 查询加工单发送信息记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectMachineSend.act")
	public String selectMachineSend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> dataMap = new HashMap<String, String>();
		String username = request.getParameter("username");
		String usercode = request.getParameter("usercode");
		String phoneNumber = request.getParameter("phoneNumber");
		String orderNumber = request.getParameter("orderNumber");
		String machineId = request.getParameter("machineId");
		if (!YZUtility.isNullorEmpty(username)) {
			dataMap.put("username", username);
		}
		if (!YZUtility.isNullorEmpty(usercode)) {
			dataMap.put("usercode", usercode);
		}
		if (!YZUtility.isNullorEmpty(phoneNumber)) {
			dataMap.put("phoneNumber", phoneNumber);
		}
		if (!YZUtility.isNullorEmpty(orderNumber)) {
			dataMap.put("orderNumber", orderNumber);
		}
		if (!YZUtility.isNullorEmpty(machineId)) {
			dataMap.put("machineId", machineId);
		}
		try {
			List<JSONObject> list = machineSendLogic.selectMachineSend(dataMap);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}
