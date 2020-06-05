package com.kqds.controller.app;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.service.app.KQDS_IndexLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_IndexAct")
public class KQDS_IndexAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_IndexAct.class);
	@Autowired
	private KQDS_IndexLogic logic;

	/**
	 * 统计全院业绩情况展示 【同时只查一个门诊】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountIndex.act")
	public String selectCountIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String visualstaff = SessionUtil.getVisualstaff(request);
			// 本年实收、收款
			JSONObject yearitem = logic.getSkMoneyItem(visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), "year");

			BigDecimal yearskitem = new BigDecimal(yearitem.getString("skje"));
			BigDecimal yearskyjj = logic.getSkMoneyYjj(visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), "year");
			BigDecimal yearsk = yearskitem.add(yearskyjj);
			yearitem.put("skje", yearsk);
			// 本月实收、收款
			JSONObject monthitem = logic.getSkMoneyItem(visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), "month");

			BigDecimal monthskitem = new BigDecimal(monthitem.getString("skje"));
			BigDecimal monthskyjj = logic.getSkMoneyYjj(visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), "month");
			BigDecimal monthsk = monthskitem.add(monthskyjj);
			monthitem.put("skje", monthsk);
			// 当天实收、收款
			JSONObject dayitem = logic.getSkMoneyItem(visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), "day");

			BigDecimal dayskitem = new BigDecimal(dayitem.getString("skje"));
			BigDecimal dayskyjj = logic.getSkMoneyYjj(visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), "day");
			BigDecimal daysk = dayskitem.add(dayskyjj);
			dayitem.put("skje", daysk);
			// 今日工作（门诊预约、网络预约、今日患者、今日回访）
			int wdataCount = logic.getCountWd(TableNameUtil.KQDS_NET_ORDER, 0, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 网电
			int mdataCount = logic.getCountMz(TableNameUtil.KQDS_HOSPITAL_ORDER, 0, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 门诊
			int tdataCountj = logic.getCountReg(TableNameUtil.KQDS_REG, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 今日患者
			int vdataCount = logic.getCountV(TableNameUtil.KQDS_VISIT, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 今日回访
			int jrtotal = wdataCount + mdataCount + tdataCountj + vdataCount;
			// 明日预约（门诊预约、网电预约）
			int wdataCountNext = logic.getCountWd(TableNameUtil.KQDS_NET_ORDER, -1, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 网电
			int mdataCountNext = logic.getCountMz(TableNameUtil.KQDS_HOSPITAL_ORDER, -1, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 门诊
			int mrtotal = wdataCountNext + mdataCountNext;
			// 累计欠费（截至到今天的欠费情况）
			BigDecimal qfmoney = logic.selectRealQfMoney(visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
			JSONObject jobj = new JSONObject();
			jobj.put("year", yearitem);
			jobj.put("month", monthitem);
			jobj.put("day", dayitem);
			jobj.put("jrtotal", tdataCountj);// 今日工作：展示“今日患者”的数量；
			jobj.put("mrtotal", mrtotal);
			jobj.put("qfmoney", qfmoney);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
}