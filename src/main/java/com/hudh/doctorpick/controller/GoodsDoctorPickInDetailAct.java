package com.hudh.doctorpick.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hudh.doctorpick.service.IGoodsDoctorPickInDetailService;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/GoodsDoctorPickInDetailAct")
public class GoodsDoctorPickInDetailAct {
	
	private static Logger logger = LoggerFactory.getLogger(GoodsDoctorPickInDetailAct.class);
	
	@Autowired
	private IGoodsDoctorPickInDetailService detailService;
	
	/**
	 * 根据单据编号查询明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDoctorPickInDetailByIncode.act")
	public String findDoctorPickInDetailByIncode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String incode = request.getParameter("incode");
		// 可见人员过滤
		String visualstaff = SessionUtil.getVisualstaff(request);
		try {
			List<JSONObject> list = detailService.findDoctorPickInDetailByIncode(incode, visualstaff);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据主键更新领料明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateGoodsDoctorPickInDetail.act")
	public String updateGoodsDoctorPickInDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seq_id = request.getParameter("seq_id");
		try {
			detailService.updateGoodsDoctorPickInDetail(seq_id);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据明细表主键删除记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/deleteDoctorPickInDetailBySeqId.act")
	public String deleteDoctorPickInDetailBySeqId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String indetailseqId = request.getParameter("indetailseqId");
		try {
			detailService.deleteDoctorPickInDetailBySeqId(indetailseqId);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据明细表主键查询明细记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/findDoctorPickInDetailById.act")
	public String findDoctorPickInDetailById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		try {
			JSONObject json = detailService.findDoctorPickInDetailById(id);
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}
