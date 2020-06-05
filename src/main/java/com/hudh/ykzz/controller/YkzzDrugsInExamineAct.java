package com.hudh.ykzz.controller;

import java.util.ArrayList;
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
import com.hudh.ykzz.entity.YkzzDrugsInExamine;
import com.hudh.ykzz.service.IYkzzDrugsInExamineService;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/HUDH_YkzzDrugsInExamineAct")
public class YkzzDrugsInExamineAct {
	private Logger logger = LoggerFactory.getLogger(YkzzDrugsInExamineAct.class);
	/**
	 * 入库审批接口
	 */
	@Autowired
	private IYkzzDrugsInExamineService ykzzDrugsInExamineService;
	
	/**
	 * 新增入库审批数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertDrugsInExamine.act")
	public String insertDrugsInExamine(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String drugsInId = request.getParameter("drugsInId"); //对应入库表id
		String packing = request.getParameter("packing"); //外包装是否破损异常  0：异常     1：通过
		String certificate = request.getParameter("certificate"); //合格证   0：异常     1：通过
		String report = request.getParameter("report"); //验收报告   0：异常     1：通过
		String checkDate = request.getParameter("checkDate"); //验收日期
		String remark = request.getParameter("remark"); //备注
		
		YkzzDrugsInExamine ykzzDrugsInExamine = new YkzzDrugsInExamine();
		ykzzDrugsInExamine.setDrugsInId(drugsInId);
		ykzzDrugsInExamine.setPacking(packing);
		ykzzDrugsInExamine.setCertificate(certificate);
		ykzzDrugsInExamine.setReport(report);
		ykzzDrugsInExamine.setCheckDate(checkDate);
		ykzzDrugsInExamine.setRemark(remark);
		try {
			ykzzDrugsInExamineService.insertDrugsInExamine(ykzzDrugsInExamine,request);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据入库表id查询对应的审批数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDrugsInExamineByInId.act")
	public String findDrugsInExamineByInId(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String drugsInId = request.getParameter("parentid");
		try {
			if(YZUtility.isNotNullOrEmpty(drugsInId)) {
				List<JSONObject> list  = new ArrayList<JSONObject>();
				list = ykzzDrugsInExamineService.findDrugsInExamineByInId(drugsInId);
				YZUtility.RETURN_LIST(list, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据id删除数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteDrugsInExamineById.act")
	public String deleteDrugsInExamineById(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		try {
			if(YZUtility.isNotNullOrEmpty(id)) {
				ykzzDrugsInExamineService.deleteDrugsInExamineById(id);
				YZUtility.DEAL_SUCCESS(null,null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}

	/**
	 * 根据入库表id删除信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteDrugsInExamineByInId.act")
	public String deleteDrugsInExamineByInId(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String drugsInId = request.getParameter("drugsInId");
		try {
			if(YZUtility.isNotNullOrEmpty(drugsInId)) {
				ykzzDrugsInExamineService.deleteDrugsInExamineByInId(drugsInId);
				YZUtility.DEAL_SUCCESS(null,null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 发药明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDugsExamineDetail.act")
	public String findDugsExamineDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String flag = request.getParameter("flag");
		String fieldArr = request.getParameter("fieldArr");
		String fieldnameArr = request.getParameter("fieldnameArr");
		Map<String,String> dataMap = new HashMap<String,String>();
	    String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		String itemno = request.getParameter("itemno");
		String itemname = request.getParameter("itemname");
		String organization = ChainUtil.getCurrentOrganization(request);
		if(YZUtility.isNotNullOrEmpty(starttime)) {
			dataMap.put("starttime", starttime+" 00:00:00");
		}
		
		if(YZUtility.isNotNullOrEmpty(organization)) {
			dataMap.put("organization", organization);
		}
		
		if(YZUtility.isNotNullOrEmpty(endtime)) {
			dataMap.put("endtime", endtime+" 23:59:59");
		}
		if(YZUtility.isNotNullOrEmpty(usercode)) {
			dataMap.put("usercode", usercode);
		}
		if(YZUtility.isNotNullOrEmpty(username)) {
			dataMap.put("username", username);
		}
		if(YZUtility.isNotNullOrEmpty(itemno)) {
			dataMap.put("itemno", itemno);
		}
		if(YZUtility.isNotNullOrEmpty(itemname)) {
			dataMap.put("itemname", itemname);
		}
		try {
			List<JSONObject> list  = new ArrayList<JSONObject>();
			list = ykzzDrugsInExamineService.findDugsExamineDetail(dataMap);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("药品出库明细", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	 * 退药根据入库表id查询对应的审批数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDugsReturnDetail.act")
	public String findDugsReturnDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String flag = request.getParameter("flag");
		String fieldArr = request.getParameter("fieldArr");
		String fieldnameArr = request.getParameter("fieldnameArr");
		Map<String,String> dataMap = new HashMap<String,String>();
	    String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		String itemno = request.getParameter("itemno");
		String itemname = request.getParameter("itemname");
		String organization = ChainUtil.getCurrentOrganization(request);
		if(YZUtility.isNotNullOrEmpty(organization)) {
			dataMap.put("organization", organization);
		}
		if(YZUtility.isNotNullOrEmpty(starttime)) {
			dataMap.put("starttime", starttime+" 00:00:00");
		}
		if(YZUtility.isNotNullOrEmpty(endtime)) {
			dataMap.put("endtime", endtime+" 23:59:59");
		}
		if(YZUtility.isNotNullOrEmpty(usercode)) {
			dataMap.put("usercode", usercode);
		}
		if(YZUtility.isNotNullOrEmpty(username)) {
			dataMap.put("username", username);
		}
		if(YZUtility.isNotNullOrEmpty(itemno)) {
			dataMap.put("itemno", itemno);
		}
		if(YZUtility.isNotNullOrEmpty(itemname)) {
			dataMap.put("itemname", itemname);
		}
		try {
			List<JSONObject> list  = new ArrayList<JSONObject>();
			list = ykzzDrugsInExamineService.findDugsReturnDetail(dataMap);
			
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("药品退药明细", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
}
