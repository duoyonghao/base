package com.hudh.zzbl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hudh.zzbl.entity.ZzblPlantTeethOperation;
import com.hudh.zzbl.service.IZzblPlantTeethOperationService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/HUDH_ZzblPlantTeethOperationAct")
public class HUDH_ZzblPlantTeethOperationAct {
	
	private Logger logger = LoggerFactory.getLogger(HUDH_ZzblPlantTeethOperationAct.class);
	
	@Autowired
	private IZzblPlantTeethOperationService operationService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save.act")
	public String save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ZzblPlantTeethOperation dp = new ZzblPlantTeethOperation();
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
		dp.setCreateuser(person.getUserId());
		dp.setOrganization(organization);
		try {
			operationService.insertZzblPlantTeethOperation(dp, request);
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
			JSONObject json = operationService.findZzblPlantTeethOperationById(id);
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

}
