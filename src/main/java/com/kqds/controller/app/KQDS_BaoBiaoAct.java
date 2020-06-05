package com.kqds.controller.app;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.service.app.KQDS_BaobiaoLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_BaoBiaoAct")
public class KQDS_BaoBiaoAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_BaoBiaoAct.class);
	@Autowired
	private KQDS_BaobiaoLogic logic;

	/**
	 * 业绩报表
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
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				if (starttime.length() == 4) {
					starttime = starttime + "-01-01" + ConstUtil.TIME_START;
				} else if (starttime.length() == 7) {
					starttime = starttime + "-01" + ConstUtil.TIME_START;
				} else {
					starttime = starttime + ConstUtil.TIME_START;
				}
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				if (endtime.length() == 4) {
					endtime = endtime + "-12-31" + ConstUtil.TIME_END;
				} else if (endtime.length() == 7) {
					endtime = endtime + "-01" + ConstUtil.TIME_START;
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(YZUtility.parseDate(endtime));
					calendar.add(Calendar.MONTH, 1);// 把日期往后增加一个月.整数往后推,负数往前移动
					endtime = YZUtility.getDateTimeStr(calendar.getTime());
				} else {
					endtime = endtime + ConstUtil.TIME_END;
				}
				map.put("endtime", endtime);
			}
			// 应收 欠费 缴费
			JSONObject yearitem = logic.getSkMoneyItem(map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
			BigDecimal yearskitem = new BigDecimal(yearitem.getString("skze"));
			BigDecimal yearskyjj = logic.getSkMoneyYjj(map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));
			BigDecimal yearsk = yearskitem.add(yearskyjj);
			yearitem.put("skze", yearsk);
			JSONObject jobj = new JSONObject();
			jobj.put("data", yearitem);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
}