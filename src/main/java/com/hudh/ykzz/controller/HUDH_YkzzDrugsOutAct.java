package com.hudh.ykzz.controller;

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

import com.hudh.ykzz.entity.YkzzDrugsOut;
import com.hudh.ykzz.service.IYkzzDrugsOutService;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/HUDH_YkzzDrugsOutAct")
public class HUDH_YkzzDrugsOutAct {
	private Logger logger = LoggerFactory.getLogger(HUDH_YkzzDrugsOutAct.class);
	/**
	 * 药品出库接口
	 */
	@Autowired
	private IYkzzDrugsOutService ykzzDrugsOutService;
	
	/**
	 * 药品出库
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertDrugsOut.act")
	public String insertDrugsIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	    String drugsOutdetails = request.getParameter("paramDetail");
		String cktime = request.getParameter("cktime");
		String outtype = request.getParameter("outtype");
		String supplier = request.getParameter("supplier");
		String outcode = request.getParameter("outcode");
		String outremark = request.getParameter("outremark");
		String outdept = request.getParameter("outDept");
		String zhaiyao = request.getParameter("zhaiyao");
		
		YkzzDrugsOut ykzzDrugsOut = new YkzzDrugsOut();
		ykzzDrugsOut.setCktime(cktime);
		ykzzDrugsOut.setOuttype(outtype);
		ykzzDrugsOut.setSupplier(supplier);
		ykzzDrugsOut.setOutcode(outcode);
		ykzzDrugsOut.setOutremark(outremark);
		ykzzDrugsOut.setZhaiyao(zhaiyao);
		ykzzDrugsOut.setOutdept(outdept);
		
		try {
			ykzzDrugsOutService.insertDrugsOut(ykzzDrugsOut, drugsOutdetails, request);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 删除出库
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteDrugsOut.act")
	public String deleteDrugsOut(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		try {
			String backMg = ykzzDrugsOutService.deleteDrugsOut(id);
			YZUtility.DEAL_SUCCESS(null,backMg, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 查询全部出库信息或根据条件查找
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findAllDrugsOut.act")
	public void findAllDrugsOut(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		// 入库查询 页面参数
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String outtype = request.getParameter("intype");
		String supplier = request.getParameter("supplier");
		String outcode = request.getParameter("incode");
		
		String accurateOutcode = request.getParameter("accurateOutcode"); //精确查号单号
		String organization = ChainUtil.getCurrentOrganization(request);
		Map<String, String> map = new HashMap<String, String>();
		if (!YZUtility.isNullorEmpty(starttime)) {
			map.put("starttime", starttime);
		}
		if (!YZUtility.isNullorEmpty(endtime)) {
			map.put("endtime", endtime);
		}
		if (!YZUtility.isNullorEmpty(outtype)) {
			map.put("outtype", outtype);
		}
		if (!YZUtility.isNullorEmpty(supplier)) {
			map.put("supplier", supplier);
		}
		if (!YZUtility.isNullorEmpty(outcode)) {
			map.put("outcode", outcode);
		}
		if (!YZUtility.isNullorEmpty(accurateOutcode)) {
			map.put("accurateOutcode", accurateOutcode);
		}
		if (!YZUtility.isNullorEmpty(organization)) {
			map.put("organization", organization);
		}
		try {
			List<JSONObject> list = ykzzDrugsOutService.findAllDrugsOut(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	
	/**
	 * 根据parentid查询出库明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDetailByParendId.act")
	public void findDetailByParendId(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String parentid = request.getParameter("parentid");
		try {
			if(YZUtility.isNotNullOrEmpty(parentid)) {
				List<JSONObject> list = ykzzDrugsOutService.findDetailByParendId(parentid);
				YZUtility.RETURN_LIST(list, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
	
	@RequestMapping(value= "/getDrugsInBatchnum.act")
	public String getDrugsInBatchnum(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String order_no = request.getParameter("order_no");
		String outnum = request.getParameter("outnum");
		try {
			String batchnum = ykzzDrugsOutService.getDrugsInBatchnum(order_no, outnum);
			JSONObject json = new JSONObject();
			json.put("dataBatchnum", batchnum);
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, logger);
		}
		return null;
	}
}
