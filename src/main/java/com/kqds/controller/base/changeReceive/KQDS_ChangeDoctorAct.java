package com.kqds.controller.base.changeReceive;

import java.math.BigDecimal;
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

import com.kqds.entity.base.KqdsChangeDoctor;
import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsPaycost;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.changeReceive.KQDS_changeDoctorLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;

import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("KQDS_ChangeDoctorAct")
public class KQDS_ChangeDoctorAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_ChangeDoctorAct.class);
	@Autowired
	private KQDS_changeDoctorLogic logic;

	@RequestMapping(value = "/toZz_Index.act")
	public ModelAndView toZz_Index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String costno = request.getParameter("costno");
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("costno", costno);
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/reg/zzDoctor/zz_index.jsp");
		return mv;
	}

	/**
	 * 转诊医生
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/zzDoctor.act")
	public String zzDoctor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			String doctor = request.getParameter("doctor");
			String oldDoctor = request.getParameter("oldDoctor");
			String regno = request.getParameter("regno");
			String costno = request.getParameter("costno");
			String remark = request.getParameter("remark");
			String usercode = request.getParameter("usercode");
			String username = request.getParameter("username");
			// 保存转诊记录
			KqdsChangeDoctor dp = new KqdsChangeDoctor();
			dp.setSeqId(YZUtility.getUUID());
			dp.setCreatetime(YZUtility.getCurDateTimeStr());
			dp.setCreateuser(person.getSeqId());
			dp.setOlddoctor(oldDoctor);
			dp.setTodoctor(doctor);
			dp.setRemark(remark);
			dp.setRegno(regno);
			dp.setUsercode(usercode);
			dp.setUsername(username);
			dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
			logic.saveSingleUUID(TableNameUtil.KQDS_CHANGE_DOCTOR, dp);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_CHANGE_DOCTOR, dp, dp.getUsercode(), TableNameUtil.KQDS_CHANGE_DOCTOR, request);

			Map<String, String> map = new HashMap<String, String>();
			map.put("doctor", oldDoctor);
			map.put("regno", regno);
			map.put("costno", costno);
			// ------------------------------------- 这里是否需要门诊过滤，需要明确 yangsen
			// 17-6-17
			// 修改费用明细表中的医生
			List<KqdsCostorderDetail> detaillist = (List<KqdsCostorderDetail>) logic.loadList(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
			for (KqdsCostorderDetail detail : detaillist) {
				if (KqdsBigDecimal.compareTo(detail.getY2(), BigDecimal.ZERO) > 0) {
					// 存在欠款的 转给新医生 ，包括以后的还款 都要转给新医生
					editQfTodoctor(detail, doctor, request);
				} else if (KqdsBigDecimal.compareTo(detail.getY2(), BigDecimal.ZERO) <= 0 && detail.getQfbh() != null && !"".equals(detail.getQfbh())) {// 还款
					// 存在还款的 不转给新医生
				} else {
					detail.setDoctor(doctor);
					logic.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detail);
				}
			}

			// 修改费用单表中的医生
			List<KqdsCostorder> orderlist = (List<KqdsCostorder>) logic.loadList(TableNameUtil.KQDS_COSTORDER, map);
			for (KqdsCostorder detail : orderlist) {
				detail.setDoctor(doctor);
				logic.updateSingleUUID(TableNameUtil.KQDS_COSTORDER, detail);
			}
			// 修改结账表中的医生
			List<KqdsPaycost> paylist = (List<KqdsPaycost>) logic.loadList(TableNameUtil.KQDS_PAYCOST, map);
			for (KqdsPaycost detail : paylist) {
				detail.setDoctor(doctor);
				logic.updateSingleUUID(TableNameUtil.KQDS_PAYCOST, detail);
			}
			YZUtility.DEAL_SUCCESS(null, "转诊成功", response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 存在欠款的 转给新医生 ，包括以后的还款 都要转给新医生
	 * 
	 * @param detail
	 * @param dbConn
	 * @param doctor
	 * @throws Exception
	 */
	@RequestMapping(value = "/editQfTodoctor.act")
	private void editQfTodoctor(KqdsCostorderDetail detail, String doctor, HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("qfbh", detail.getQfbh());
		// 修改费用明细表中的医生
		List<KqdsCostorderDetail> detaillist = (List<KqdsCostorderDetail>) logic.loadList(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
		for (KqdsCostorderDetail detailnew : detaillist) {
			detailnew.setDoctor(doctor);
			logic.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detailnew);
		}
	}

	/**
	 * ------------------------------------- 【不做门诊条件过滤】
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
			String olddoctor = request.getParameter("olddoctor");
			String todoctor = request.getParameter("todoctor");
			String RegNo = request.getParameter("RegNo");
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
			if (!YZUtility.isNullorEmpty(olddoctor)) {
				map.put("olddoctor", olddoctor);
			}
			if (!YZUtility.isNullorEmpty(todoctor)) {
				map.put("todoctor", todoctor);
			}
			if (!YZUtility.isNullorEmpty(RegNo)) {
				map.put("RegNo", RegNo);
			}

			List<JSONObject> list = logic.selectWithPage(TableNameUtil.KQDS_CHANGE_DOCTOR, map);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("转诊医生列表", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}