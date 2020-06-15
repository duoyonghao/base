package com.kqds.controller.base.costOrderDetail;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.memberRecord.KQDS_Member_RecordLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportBean;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("KQDS_CostOrder_DetailAct")
public class KQDS_CostOrder_DetailAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_CostOrder_DetailAct.class);
	@Autowired
	private KQDS_CostOrder_DetailLogic logic;
	@Autowired
	private KQDS_Member_RecordLogic logicmember;
	@Autowired
	private YZPersonLogic personLogic;

	@RequestMapping(value = "/toQfqkWin.act")
	public ModelAndView toQfqkWin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/coatOrder/qfqkWin.jsp");
		return mv;
	}

	@RequestMapping(value = "/toCostDetail_Tuidan.act")
	public ModelAndView toCostDetail_Tuidan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/coatOrder/cost_detail_tuidan.jsp");
		return mv;
	}

	@RequestMapping(value = "/toCostDetail.act")
	public ModelAndView toCostDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/index/center/cost_detail.jsp");
		return mv;
	}

	@RequestMapping(value = "/toCostDetail2.act")
	public ModelAndView toCostDetail2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String costno = request.getParameter("costno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("costno", costno);
		mv.setViewName("/kqdsFront/coatOrder/cost_detail.jsp");
		return mv;
	}

	@RequestMapping(value = "/toMxcxCenter.act")
	public ModelAndView toMxcxCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/jdzx/mxcx_center.jsp");
		return mv;
	}

	/**
	 * 医疗中心-已治疗
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update.act")
	public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsCostorderDetail dp = new KqdsCostorderDetail();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键不能为空");
			}
			KqdsCostorderDetail en = (KqdsCostorderDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, seqId);
			if (en == null) {
				throw new Exception("数据不存在");
			}
			if (!YZUtility.isNullorEmpty(dp.getZltime()) && YZUtility.isNullorEmpty(dp.getKaifa())) {
				en.setKaifa("已治疗");
				en.setZltime(dp.getZltime());
			}
			if (YZUtility.isNullorEmpty(dp.getZltime()) && !YZUtility.isNullorEmpty(dp.getKaifa())) {
				if (dp.getKaifa().equals("已治疗")) {
					en.setKaifa("已治疗");
					en.setZltime(YZUtility.getCurDateTimeStr());
				}
				if (!dp.getKaifa().equals("已治疗") && !YZUtility.isNullorEmpty(en.getZltime())) {
					en.setKaifa("未治疗");
					en.setZltime("");
				}
			}

			en.setCzperson(person.getUserName());
			logic.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, en);
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE_STATUS, BcjlUtil.KQDS_COSTORDER_DETAIL, en, en.getUsercode(), TableNameUtil.KQDS_COSTORDER_DETAIL, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询费用明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAll.act")
	public String getAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String starttime = request.getParameter("sfstarttime");
			String endtime = request.getParameter("sfendtime");
			if(YZUtility.isNullorEmpty(starttime)){
				starttime = request.getParameter("starttime");
			}
			if(YZUtility.isNullorEmpty(endtime)){
				endtime = request.getParameter("endtime");
			}
			String costno = request.getParameter("costno");
			String basetype = request.getParameter("basetype");
			String nexttype = request.getParameter("xfnexttype");
			String queryinput = request.getParameter("queryinput");
			String regdept = request.getParameter("regdept");
			String askperson = request.getParameter("askpersonSearch");
			String doctor = request.getParameter("doctor");
			String nurse = request.getParameter("nurse");
			String createuser = request.getParameter("createuser");
			String devchannel = request.getParameter("devchannel");
			String nexttype1 = request.getParameter("nexttype1");
			String recesort = request.getParameter("recesort");
			String regsort = request.getParameter("regsort");
			String remark = request.getParameter("remark");
			String isyjjitem = request.getParameter("isyjjitem");
			String zlstatus = request.getParameter("zlstatus");
			String zlstarttime = request.getParameter("zlstarttime");
			String zlendtime = request.getParameter("zlendtime");
			String itemname = request.getParameter("itemname"); // 消费项目
			String sortName = request.getParameter("sortName");
			String sortOrder = request.getParameter("sortOrder");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			Map<String, String> map = new HashMap<String, String>();

			// 消费项目
			if (!YZUtility.isNullorEmpty(itemname)) {
				map.put("itemname", itemname);
			}

			// 根据所选门诊进行查询
			String organization = ChainUtil.getOrganizationFromUrl(request);
			if (YZUtility.isNullorEmpty(organization)) {
				// 根据所选门诊进行查询
				map.put("organization", ChainUtil.getCurrentOrganization(request));
			} else {
				map.put("organization", organization);
			}

			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(zlstarttime)) {
				zlstarttime = zlstarttime + ConstUtil.TIME_START;
				map.put("zlstarttime", zlstarttime);
			}
			if (!YZUtility.isNullorEmpty(zlendtime)) {
				zlendtime = zlendtime + ConstUtil.TIME_END;
				map.put("zlendtime", zlendtime);
			}
			if (!YZUtility.isNullorEmpty(zlstatus)) {
				map.put("zlstatus", zlstatus);
			}
			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			if (!YZUtility.isNullorEmpty(doctor)) {
				map.put("doctor", doctor);
			}
			if (!YZUtility.isNullorEmpty(nurse)) {
				map.put("nurse", nurse);
			}
			if (!YZUtility.isNullorEmpty(createuser)) {
				map.put("createuser", createuser);
			}
			if (!YZUtility.isNullorEmpty(devchannel)) {
				map.put("devchannel", devchannel);
			}
			if (!YZUtility.isNullorEmpty(nexttype1)) {
				map.put("nexttype1", nexttype1);
			}
			if (!YZUtility.isNullorEmpty(recesort)) {
				map.put("recesort", recesort);
			}
			if (!YZUtility.isNullorEmpty(regsort)) {
				map.put("regsort", regsort);
			}
			if (!YZUtility.isNullorEmpty(costno)) {
				map.put("costno", costno);
			}
			if (!YZUtility.isNullorEmpty(regdept)) {
				map.put("regdept", regdept);
			}
			if (!YZUtility.isNullorEmpty(basetype)) {
				map.put("basetype", basetype);
			}
			if (!YZUtility.isNullorEmpty(nexttype)) {
				map.put("nexttype", nexttype);
			}
			if (!YZUtility.isNullorEmpty(queryinput)) {
				map.put("queryinput", queryinput);
			}
			if (!YZUtility.isNullorEmpty(remark)) {
				map.put("remark", remark);
			}
			if (!YZUtility.isNullorEmpty(isyjjitem)) {
				map.put("isyjjitem", isyjjitem);
			}
			if(!YZUtility.isNullorEmpty(sortName)){
				map.put("sortName", sortName);
				map.put("sortOrder", sortOrder);
			}
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> list = new ArrayList<JSONObject>();
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				JSONObject data = logic.selectNoPage(bp, TableNameUtil.KQDS_COSTORDER_DETAIL, person, map, visualstaff, ChainUtil.getOrganizationFromUrl(request));
				JSONObject recordData=logicmember.selectNoPageBySfmx(bp, TableNameUtil.KQDS_MEMBER_RECORD, person, map, visualstaff, ChainUtil.getOrganizationFromUrl(request));
				List<JSONObject> listorder = (List<JSONObject>) data.getJSONArray("rows");
				List<JSONObject> listmember = (List<JSONObject>) recordData.getJSONArray("rows");
				list.addAll(listorder);
				list.addAll(listmember);
				if (list.size()>0) {
					ExportBean bean = ExportTable.initExcel4RsWrite("费用明细", fieldArr, fieldnameArr, response, request);
					bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, list, "costOrderDetail_selectNoPage");
					ExportTable.writeExcel4DownLoad("费用明细", bean.getWorkbook(), response);
				}
				return null;
			}
			JSONObject data = logic.selectNoPage(bp, TableNameUtil.KQDS_COSTORDER_DETAIL, person, map, visualstaff, ChainUtil.getOrganizationFromUrl(request));
			JSONObject recordData=logicmember.selectNoPageBySfmx(bp, TableNameUtil.KQDS_MEMBER_RECORD, person, map, visualstaff, ChainUtil.getOrganizationFromUrl(request));
			JSONObject jobjall = new JSONObject();
			int orderTotal = data.getInt("total");
			int memberTotal = recordData.getInt("total");
			List<JSONObject> listorder = (List<JSONObject>) data.getJSONArray("rows");
			List<JSONObject> listmember = (List<JSONObject>) recordData.getJSONArray("rows");
			if(memberTotal<=bp.getOffset()&&orderTotal>bp.getOffset()){
				list.addAll(listorder);
			}else if(memberTotal>bp.getOffset()&&orderTotal<=bp.getOffset()){
				list.addAll(listmember);
			}else{
				list.addAll(listorder);
				list.addAll(listmember);
			}
			/** HUDH 修改 **/
			jobjall.put("rows", list);

			if (orderTotal> memberTotal) {
				jobjall.put("total", orderTotal);
			} else {
				jobjall.put("total", memberTotal);
			}
			if (bp.getOffset() == 0 && YZUtility.isNullorEmpty(flag)) {
				BigDecimal subtotal = BigDecimal.ZERO;//小计:
				BigDecimal voidmoney = BigDecimal.ZERO;//免除:
				BigDecimal ys = BigDecimal.ZERO;//应收:
				BigDecimal y2 = BigDecimal.ZERO;//欠款:
				BigDecimal paymoney = BigDecimal.ZERO;//收款:
				BigDecimal payother2 = BigDecimal.ZERO;//赠送使用:
				BigDecimal paydjq = BigDecimal.ZERO;
				BigDecimal payintegral = BigDecimal.ZERO;//积分:
				BigDecimal cmoney = BigDecimal.ZERO;//预交金退费:
				if (orderTotal> 0) {
					subtotal = subtotal.add(new BigDecimal(data.getString("subtotal")));
					voidmoney = voidmoney.add(new BigDecimal(data.getString("voidmoney")));
					ys = ys.add(new BigDecimal(data.getString("ys")));
					y2 = y2.add(new BigDecimal(data.getString("y2")));
					paymoney = paymoney.add(new BigDecimal(data.getString("paymoney")));
					payother2 = payother2.add(new BigDecimal(data.getString("payother2")));
					paydjq = paydjq.add(new BigDecimal(data.getString("paydjq")));
					payintegral = payintegral.add(new BigDecimal(data.getString("payintegral")));
				}
				if (memberTotal> 0) {
					cmoney = cmoney.add(new BigDecimal(recordData.getString("cmoney")));
					subtotal = subtotal.add(new BigDecimal(recordData.getString("cmoney")));
					paymoney = paymoney.add(new BigDecimal(recordData.getString("cmoney")));
					ys = ys.add(new BigDecimal(recordData.getString("cmoney")));
				}
				jobjall.put("subtotal", subtotal);
				jobjall.put("voidmoney", voidmoney);
				jobjall.put("ys", ys);
				jobjall.put("y2", y2);
				jobjall.put("paymoney", paymoney);
				jobjall.put("payother2", payother2);
				jobjall.put("paydjq", paydjq);
				jobjall.put("payintegral", payintegral);
				jobjall.put("cmoney", cmoney);
			}
			YZUtility.DEAL_SUCCESS(jobjall, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询退单明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllTuidan.act")
	public String getAllTuidan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String costno = request.getParameter("costno");
			String cjStatus = request.getParameter("cjStatus");
			String qf = request.getParameter("qf");
			String basetype = request.getParameter("basetype");
			String nexttype = request.getParameter("nexttype");
			String queryinput = request.getParameter("queryinput");

			String askperson = request.getParameter("askperson");
			String doctor = request.getParameter("doctor");
			String nurse = request.getParameter("nurse");
			String createuser = request.getParameter("createuser");
			String devchannel = request.getParameter("devchannel");
			String nexttype1 = request.getParameter("nexttype1");
			String recesort = request.getParameter("recesort");
			String regsort = request.getParameter("regsort");
			String remark = request.getParameter("remark");
			String backuser = request.getParameter("backuser");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			Map<String, String> map = new HashMap<String, String>();

			// 根据所选门诊进行查询
			String organization = ChainUtil.getOrganizationFromUrl(request);
			if (YZUtility.isNullorEmpty(organization)) {
				// 根据所选门诊进行查询
				map.put("organization", ChainUtil.getCurrentOrganization(request));
			} else {
				map.put("organization", organization);
			}

			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			if (!YZUtility.isNullorEmpty(doctor)) {
				map.put("doctor", doctor);
			}
			if (!YZUtility.isNullorEmpty(nurse)) {
				map.put("nurse", nurse);
			}
			if (!YZUtility.isNullorEmpty(createuser)) {
				map.put("createuser", createuser);
			}
			if (!YZUtility.isNullorEmpty(devchannel)) {
				map.put("devchannel", devchannel);
			}
			if (!YZUtility.isNullorEmpty(nexttype1)) {
				map.put("nexttype1", nexttype1);
			}
			if (!YZUtility.isNullorEmpty(recesort)) {
				map.put("recesort", recesort);
			}
			if (!YZUtility.isNullorEmpty(regsort)) {
				map.put("regsort", regsort);
			}
			if (!YZUtility.isNullorEmpty(costno)) {
				map.put("costno", costno);
			}
			if (!YZUtility.isNullorEmpty(cjStatus)) {
				map.put("cjStatus", cjStatus);
			}
			if (!YZUtility.isNullorEmpty(qf)) {
				map.put("qf", qf);
			}
			if (!YZUtility.isNullorEmpty(basetype)) {
				map.put("basetype", basetype);
			}
			if (!YZUtility.isNullorEmpty(nexttype)) {
				map.put("nexttype", nexttype);
			}
			if (!YZUtility.isNullorEmpty(queryinput)) {
				map.put("queryinput", queryinput);
			}
			if (!YZUtility.isNullorEmpty(remark)) {
				map.put("remark", remark);
			}
			if (!YZUtility.isNullorEmpty(backuser)) {
				map.put("backuser", backuser);
			}
			String visualstaff = SessionUtil.getVisualstaff(request);
			JSONObject data = logic.selectNoPage(bp, "KQDS_COSTORDER_DETAIL_TUIDAN", person, map, visualstaff, ChainUtil.getOrganizationFromUrl(request));
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("费用明细", fieldArr, fieldnameArr, (List<JSONObject>) data.getJSONArray("rows"), response, request);
				return null;
			}

			YZUtility.RETURN_LIST((List<JSONObject>) data.getJSONArray("rows"), response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询费用明细含预收
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/orderAndMember.act")
	public String orderAndMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String basetype = request.getParameter("basetype");
			String nexttype = request.getParameter("nexttype");
			String itemname = request.getParameter("itemname");
			String queryinput = request.getParameter("queryinput");
			String persontype = request.getParameter("persontype");// 0 全部 1 咨询
																	// 2 网电 3营销
			String regdept = request.getParameter("regdept");
			String doctor = request.getParameter("doctor");
			String askperson = request.getParameter("askperson");
			String createuser = request.getParameter("createuser");
			String kfr = request.getParameter("kfr");
			String devchannel = request.getParameter("devchannel");
			String nexttype1 = request.getParameter("nexttype1");
			String shouli = request.getParameter("shouli");
			String gongju = request.getParameter("gongju");
			String remark = request.getParameter("remark");
			String introducer = request.getParameter("introducer");
			String zlstatus = request.getParameter("zlstatus");
			String zlstarttime = request.getParameter("zlstarttime");
			String zlendtime = request.getParameter("zlendtime");
			String sortName = request.getParameter("sortName");
			String sortOrder = request.getParameter("sortOrder");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			Map<String, String> map = new HashMap<String, String>();

			// 根据所选门诊进行查询
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request); // 只有网电的今日患者，日收款明细时，存在为null的情况
			if (!YZUtility.isNullorEmpty(organization)) {
				map.put("organization", organization);
			}

			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(zlstarttime)) {
				zlstarttime = zlstarttime + ConstUtil.TIME_START;
				map.put("zlstarttime", zlstarttime);
			}
			if (!YZUtility.isNullorEmpty(zlendtime)) {
				zlendtime = zlendtime + ConstUtil.TIME_END;
				map.put("zlendtime", zlendtime);
			}
			if (!YZUtility.isNullorEmpty(zlstatus)) {
				map.put("zlstatus", zlstatus);
			}
			if (!YZUtility.isNullorEmpty(regdept)) {
				map.put("regdept", regdept);
			}
			if (!YZUtility.isNullorEmpty(doctor)) {
				map.put("doctor", doctor);
			}
			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			if (!YZUtility.isNullorEmpty(createuser)) {
				map.put("createuser", createuser);
			}
			if (!YZUtility.isNullorEmpty(kfr)) {
				map.put("kfr", kfr);
			}
			if (!YZUtility.isNullorEmpty(devchannel)) {
				map.put("devchannel", devchannel);
			}
			if (!YZUtility.isNullorEmpty(nexttype1)) {
				map.put("nexttype1", nexttype1);
			}
			if (!YZUtility.isNullorEmpty(shouli)) {
				map.put("shouli", shouli);
			}
			if (!YZUtility.isNullorEmpty(gongju)) {
				map.put("gongju", gongju);
			}
			if (!YZUtility.isNullorEmpty(basetype)) {
				map.put("basetype", basetype);
			}
			if (!YZUtility.isNullorEmpty(nexttype)) {
				map.put("nexttype", nexttype);
			}
			if (!YZUtility.isNullorEmpty(queryinput)) {
				map.put("queryinput", queryinput);
			}
			if (!YZUtility.isNullorEmpty(introducer)) {
				map.put("introducer", introducer);
			}
			if (!YZUtility.isNullorEmpty(itemname)) {
				map.put("itemname", itemname);
			}
			// 查询persontype 对应的人员
			String visualstaffwd = "";
			if (!YZUtility.isNullorEmpty(persontype)) {
				map.put("persontype", persontype);
				visualstaffwd = personLogic.getPerIdsByDeptTypeNoOrg(persontype);
			}
			if (!YZUtility.isNullorEmpty(remark)) {
				map.put("remark", remark);
			}
			// 活动记录
			String usercodes = request.getParameter("usercodes");
			if (!YZUtility.isNullorEmpty(usercodes)) {
				map.put("usercodes", usercodes);
			}
			if(!YZUtility.isNullorEmpty(sortName)){
				map.put("sortName", sortName);
				map.put("sortName1", sortName);
				map.put("sortOrder", sortOrder);
			}
			String visualstaff = SessionUtil.getVisualstaff(request);

			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				JSONObject resut1 = logic.selectNoPageOrder(bp, TableNameUtil.KQDS_COSTORDER_DETAIL, person, map, visualstaff, visualstaffwd);
				JSONObject resut2 = logicmember.selectListOrder(bp, TableNameUtil.KQDS_MEMBER_RECORD, map, visualstaff, visualstaffwd);
				if (resut1 != null && resut2 != null) {
					ExportBean bean = ExportTable.initExcel4RsWrite("费用明细", fieldArr, fieldnameArr, response, request);
					bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List<JSONObject>) resut1.get("rows"), "selectNoPageOrder");
					bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List<JSONObject>) resut2.get("rows"), "selectListOrder");
					ExportTable.writeExcel4DownLoad("费用明细", bean.getWorkbook(), response);
				}
				return null;
			}
			// 生成新的对象
			JSONObject jobjall = new JSONObject();
			JSONObject dataorder = new JSONObject();
			JSONObject datamember = new JSONObject();
			/** HUDH 修改  解决分页 报表中心-综合查询-收款明细分页显示不全的问题**/
			List<JSONObject> list = new ArrayList<JSONObject>();
			dataorder = logic.selectNoPageOrder(bp, TableNameUtil.KQDS_COSTORDER_DETAIL, person, map, visualstaff, visualstaffwd);
			if(YZUtility.isNullorEmpty(basetype)||!YZUtility.isNullorEmpty(basetype)&&basetype.equals("预交金")){
				datamember = logicmember.selectListOrder(bp, TableNameUtil.KQDS_MEMBER_RECORD, map, visualstaff, visualstaffwd);
			}else{
				datamember.put("total", 0);
				datamember.put("rows", list);
			}
			int orderTotal = dataorder.getInt("total");
			int memberTotal = datamember.getInt("total");
			List<JSONObject> listorder = (List<JSONObject>) dataorder.getJSONArray("rows");
			List<JSONObject> listmember = (List<JSONObject>) datamember.getJSONArray("rows");
			if(memberTotal<=bp.getOffset()&&orderTotal>bp.getOffset()){
				list.addAll(listorder);
			}else if(memberTotal>bp.getOffset()&&orderTotal<=bp.getOffset()){
				list.addAll(listmember);
			}else{
				list.addAll(listorder);
				list.addAll(listmember);
			}
			/** HUDH 修改 **/
			jobjall.put("rows", list);

			if (dataorder.getInt("total") > datamember.getInt("total")) {
				jobjall.put("total", dataorder.getInt("total"));
			} else {
				jobjall.put("total", datamember.getInt("total"));
			}
			if (bp.getOffset() == 0 && YZUtility.isNullorEmpty(flag)) {
				BigDecimal realmoney = BigDecimal.ZERO;
				BigDecimal payxj = BigDecimal.ZERO;
				BigDecimal paybank = BigDecimal.ZERO;
				BigDecimal payyb = BigDecimal.ZERO;
				BigDecimal paywx = BigDecimal.ZERO;
				BigDecimal payzfb = BigDecimal.ZERO;
				BigDecimal paymmd = BigDecimal.ZERO;
				BigDecimal paybdfq = BigDecimal.ZERO;
				BigDecimal payother1 = BigDecimal.ZERO;
				if (dataorder.getInt("total") > 0) {
					realmoney = realmoney.add(new BigDecimal(dataorder.getString("realmoney")));
					payxj = payxj.add(new BigDecimal(dataorder.getString("payxj")));
					paybank = paybank.add(new BigDecimal(dataorder.getString("paybank")));
					payyb = payyb.add(new BigDecimal(dataorder.getString("payyb")));
					paywx = paywx.add(new BigDecimal(dataorder.getString("paywx")));
					payzfb = payzfb.add(new BigDecimal(dataorder.getString("payzfb")));
					paymmd = paymmd.add(new BigDecimal(dataorder.getString("paymmd")));
					paybdfq = paybdfq.add(new BigDecimal(dataorder.getString("paybdfq")));
					payother1 = payother1.add(new BigDecimal(dataorder.getString("payother1")));
				}
				if (datamember.getInt("total") > 0) {
					realmoney = realmoney.add(new BigDecimal(datamember.getString("realmoney")));
					payxj = payxj.add(new BigDecimal(datamember.getString("payxj")));
					paybank = paybank.add(new BigDecimal(datamember.getString("paybank")));
					payyb = payyb.add(new BigDecimal(datamember.getString("payyb")));
					paywx = paywx.add(new BigDecimal(datamember.getString("paywx")));
					payzfb = payzfb.add(new BigDecimal(datamember.getString("payzfb")));
					paymmd = paymmd.add(new BigDecimal(datamember.getString("paymmd")));
					paybdfq = paybdfq.add(new BigDecimal(datamember.getString("paybdfq")));
					payother1 = payother1.add(new BigDecimal(datamember.getString("payother1")));
				}
				jobjall.put("realmoney", realmoney);
				jobjall.put("payxj", payxj);
				jobjall.put("paybank", paybank);
				jobjall.put("payyb", payyb);
				jobjall.put("paywx", paywx);
				jobjall.put("payzfb", payzfb);
				jobjall.put("paymmd", paymmd);
				jobjall.put("paybdfq", paybdfq);
				jobjall.put("payother1", payother1);
			}
			YZUtility.DEAL_SUCCESS(jobjall, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 
	 * 日收款统计【接待中心、咨询中心】
	 * 
	 */
	@RequestMapping(value = "/getRsktj.act")
	public String getRsktj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String skr = request.getParameter("skr");
			String askperson = request.getParameter("askperson");
			String wdperson = request.getParameter("wdperson");
			String yxperson = request.getParameter("yxperson");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			// 只判断是否为null 因为默认第一次参数为空 说明查询全部的
			if (skr != null) {// 收款人查询
				map.put("skr", skr);
			}
			if (askperson != null) {// 咨询查询
				map.put("askperson", askperson);
			}
			if (wdperson != null) {// 网电查询
				map.put("wdperson", wdperson);
			}
			if (yxperson != null) {// 营销查询
				map.put("yxperson", yxperson);
			}
			/**
			 * 咨询的日收款查询，直接取当前门诊，不从页面传值 接待中心 和 网电中心的，从页面传值
			 */
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}

			String visualstaff = SessionUtil.getVisualstaff(request);
			List list = logic.selectRsktj(TableNameUtil.KQDS_COSTORDER_DETAIL, person, map, visualstaff, organization, request);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("日收款查询", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 
	 * 日收款统计【网电中心】
	 * 
	 */
	@RequestMapping(value = "/getRsktj4WD.act")
	public String getRsktj4WD(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String skr = request.getParameter("skr");
			String askperson = request.getParameter("askperson");
			String wdperson = request.getParameter("wdperson");
			String yxperson = request.getParameter("yxperson");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			// 只判断是否为null 因为默认第一次参数为空 说明查询全部的
			if (skr != null) {// 收款人查询
				map.put("skr", skr);
			}
			if (askperson != null) {// 咨询查询
				map.put("askperson", askperson);
			}
			if (wdperson != null) {// 网电查询
				map.put("wdperson", wdperson);
			}
			if (yxperson != null) {// 营销查询
				map.put("yxperson", yxperson);
			}

			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}

			String visualstaff = SessionUtil.getVisualstaff(request);
			List list = logic.selectRsktj(TableNameUtil.KQDS_COSTORDER_DETAIL, person, map, visualstaff, organization, request); // 网电中心
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("日收款查询", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除收费项目
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteObj.act")
	public String deleteObj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");

			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}

			KqdsCostorderDetail en = (KqdsCostorderDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, seqId);
			// 如果删除欠费项目， 只清空costno。以便下次开单可以重新获取欠费项目
			// 如果不是欠费项目 直接删除
			if (ConstUtil.QF_STATUS_1 == en.getIsqfreal()) {// 欠费
				en.setCostno("");
				logic.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, en);
			} else {
				logic.deleteSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, seqId);
			}
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DELETE, BcjlUtil.KQDS_COSTORDER_DETAIL, en, en.getUsercode(), TableNameUtil.KQDS_COSTORDER_DETAIL, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 详情返回
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");

			KqdsCostorderDetail en = (KqdsCostorderDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, seqId);
			if (en == null) {
				throw new Exception("数据不存在");
			}

			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询欠费项目 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectQfDetail.act")
	public String selectQfDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			String usercode = request.getParameter("usercode");
			// 封装参数到实体类
			Map<String, String> map = new HashMap<String, String>();
			map.put("usercode", usercode);
			List<JSONObject> list = logic.selectWithPageLzjl2(TableNameUtil.KQDS_COSTORDER, map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 不分页查询 【根据costno 和regno 进行查询，查询条件不需要再增加 Organization】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectNoPage.act")
	public String selectNoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String costno = request.getParameter("costno");
			String regno = request.getParameter("regno");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(costno)) {
				map.put("costno", costno);
			}
			if (!YZUtility.isNullorEmpty(regno)) {
				map.put("regno", regno);
			}
			List<JSONObject> list = logic.selectWithPage(TableNameUtil.KQDS_COSTORDER_DETAIL, map, "");
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 根据费用单主键，查询费用明细，专用于费用相关的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectNoPage4Cost.act")
	public String selectNoPage4Cost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String costno = request.getParameter("costno");
			String regno = request.getParameter("regno");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(costno)) {
				map.put("costno", costno);
			}
			if (!YZUtility.isNullorEmpty(regno)) {
				map.put("regno", regno);
			}
			List<JSONObject> list = logic.selectWithPage(TableNameUtil.KQDS_COSTORDER_DETAIL, map, "");
			for (JSONObject detail : list) {
				/**
				 * 这儿的逻辑是：如果修改费用单时，消费项目存在欠费编号，但是数据库中并不存在真实的欠费记录，说明这个费用单是在 之前开的，已经过期了。
				 */
				String qfbh = detail.getString("qfbh");
				String createtime = detail.getString("createtime");
				int expireflag = logic.judgeIFExpire(createtime, qfbh);
				detail.put("expireflag", expireflag); // 0 未过期 1过期
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 费用明细查询 区分是否 开发 【根据costno 进行查询，查询条件不需要再增加 Organization】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/NoselectPage.act")
	public String NoselectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			String costno = request.getParameter("costno");
			String usercode = request.getParameter("usercode");
			// 不为空，则查询不是开发的项目
			// String type = request.getParameter("type");
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(costno)) {
				map.put("costno", costno);
			}
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			List<JSONObject> list = logic.selectWithPageLzjl(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 科室权责业绩图表展示 【同时只查一个门诊】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountBB.act")
	public String selectCountBB(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			List<KqdsCostorderDetail> list = new ArrayList<KqdsCostorderDetail>();
			map.put("organization", ChainUtil.getOrganizationFromUrl(request));
			// 分页查询
			list = logic.getCountTj(TableNameUtil.KQDS_COSTORDER_DETAIL, map, ChainUtil.getOrganizationFromUrl(request));
			JSONObject jobj = new JSONObject();
			jobj.put("rows", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 统计全院业绩情况展示 【同时只查一个门诊】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountBBQylr.act")
	public String selectCountBBQylr(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			int year = Integer.parseInt(starttime.substring(0, 4));// 年
			int month = Integer.parseInt(starttime.substring(starttime.length() - 2, starttime.length()));// 月
			int days = YZUtility.getDaysByYearMonth(year, month); // 本月的天数
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			List<Object> listxz = new ArrayList<Object>();// 新诊
			List<Object> listAll = new ArrayList<Object>();// 总来人
			// 应收数组
			listAll = logic.getCountQylrAll(TableNameUtil.KQDS_REG, year, month, days, ChainUtil.getOrganizationFromUrl(request));
			// 实收数组
			listxz = logic.getCountQylrNew(TableNameUtil.KQDS_REG, year, month, days, ChainUtil.getOrganizationFromUrl(request));
			// 应收
			BigDecimal all = logic.getCountQylrAll(TableNameUtil.KQDS_REG, year, month, ChainUtil.getOrganizationFromUrl(request));
			// 实收
			BigDecimal xz = logic.getCountQylrNew(TableNameUtil.KQDS_REG, year, month, ChainUtil.getOrganizationFromUrl(request));
			JSONObject jobj = new JSONObject();
			jobj.put("listAll", listAll);
			jobj.put("listxz", listxz);
			jobj.put("all", all);
			jobj.put("xz", xz);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 根据用户编号查询所有开单的项目 查询条件不需要再增加 Organization
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOrderListByUsercode.act")
	public String getOrderListByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			String usercode = request.getParameter("usercode");
			String doctorno = request.getParameter("doctorno");
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			if (!YZUtility.isNullorEmpty(doctorno)) {
				map.put("doctor", doctorno);
			}
			map.put("status", ConstUtil.COST_ORDER_STATUS_2);
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> list = logic.selectWithPage(TableNameUtil.KQDS_COSTORDER_DETAIL, map, visualstaff);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 退费金额验证 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkTf.act")
	public String checkTf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String itemno = request.getParameter("itemno");
			String usercode = request.getParameter("usercode");
			String qfbh = request.getParameter("qfbh");
			String detailId = request.getParameter("detailId");

			JSONObject objall = new JSONObject();
			// 已收费
			JSONObject obj = logic.checkTf(usercode, itemno, qfbh, detailId);

			// 已退款申请
			JSONObject objrefund = logic.checkTfRefund(usercode, itemno, qfbh, detailId);
			objall.put("symoney", new BigDecimal(obj.getString("paymoney")).subtract(new BigDecimal(objrefund.getString("tkmoney"))));
			PrintWriter pw = response.getWriter();
			pw.println(objall.toString());
			pw.flush();
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询退费单赠送金额 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchOrderZs.act")
	public String searchOrderZs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String costno = request.getParameter("costno");
			BigDecimal obj = logic.searchOrderZs(TableNameUtil.KQDS_COSTORDER_DETAIL, costno);
			PrintWriter pw = response.getWriter();
			pw.println(KqdsBigDecimal.round(obj, 2));
			pw.flush();
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 打印页面，获取不同付款方式的金额 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/printSfxm.act")
	public String printSfxm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String costno = request.getParameter("costno");
			JSONObject jobj = new JSONObject();
			JSONObject FKFS = logic.printSfxm(TableNameUtil.KQDS_COSTORDER_DETAIL, costno, request);
			jobj.put("FKFS", FKFS);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	// 查询usercode查询患者剩余总欠费
	@RequestMapping(value = "/searchZqfByusercode.act")
	public String searchZqfByusercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			String sftime = request.getParameter("sftime");
			BigDecimal obj = logic.selectQfAll(usercode, sftime);
			PrintWriter pw = response.getWriter();
			pw.println(obj.toString());
			pw.flush();
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}