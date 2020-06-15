package com.kqds.controller.app;

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

import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.app.KQDS_WorkLogic;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_WorkAct")
public class KQDS_WorkAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_WorkAct.class);
	@Autowired
	private KQDS_WorkLogic logic;
	@Autowired
	private KQDS_CostOrderLogic costlogic;

	/**
	 * 今日工作
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountWork.act")
	public String selectCountWork(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String visualstaff = SessionUtil.getVisualstaff(request);
			// 今日工作（门诊预约、网络预约、今日患者、今日回访）
			List<JSONObject> wdata = logic.getWdList(TableNameUtil.KQDS_NET_ORDER, 0, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 网电
			List<JSONObject> mdata = logic.getMzList(TableNameUtil.KQDS_HOSPITAL_ORDER, 0, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 门诊
			int regdata = logic.getRegCount(TableNameUtil.KQDS_REG, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 今日患者
			List<JSONObject> vdata = logic.getHfList(TableNameUtil.KQDS_VISIT, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 今日回访

			// 门诊预约
			JSONObject mz = new JSONObject();
			int mztotal = 0, mzsmcount = 0;
			if (mdata != null && mdata.size() > 0) {
				mztotal = mdata.size();
				for (JSONObject mzobj : mdata) {
					if (mzobj.getInt("orderstatus") == 1) {
						mzsmcount++;
					}
				}
			}
			mz.put("total", mztotal);
			mz.put("smcount", mzsmcount);

			// 网电预约
			JSONObject wd = new JSONObject();
			int wdtotal = 0, wdsmcount = 0;
			if (wdata != null && wdata.size() > 0) {
				wdtotal = wdata.size();
				for (JSONObject wdobj : wdata) {
					if (wdobj.getInt("doorstatus") == 1) {
						wdsmcount++;
					}
				}
			}
			wd.put("total", wdtotal);
			wd.put("smcount", wdsmcount);

			// 今日回访
			JSONObject hf = new JSONObject();
			int vtotal = 0, vwcount = 0;
			if (vdata != null && vdata.size() > 0) {
				vtotal = vdata.size();
				for (JSONObject vobj : vdata) {
					if (vobj.getInt("status") == 0) {
						vwcount++;
					}
				}
			}
			hf.put("total", vtotal);
			hf.put("vwcount", vwcount);

			// 今日患者
			JSONObject reg = new JSONObject();
			reg.put("regtotal", regdata);

			JSONObject jobj = new JSONObject();
			jobj.put("mzdata", mz);
			jobj.put("wddata", wd);
			jobj.put("vdata", hf);
			jobj.put("regdata", reg);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 今日工作（门诊预约）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectMzList.act")
	public String selectMzList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String askperson = request.getParameter("askperson");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String search = request.getParameter("search");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(search)) {
				map.put("search", search);
			}
			// 是否分页
			String ispaging = request.getParameter("ispaging");
			// 是否分页 1 分页 0 不分页
			if (!YZUtility.isNullorEmpty(ispaging)) {
				String pageSize = request.getParameter("pageSize");
				// 当前页
				String nowpage = request.getParameter("nowpage");
				map.put("ispaging", ispaging);
				map.put("pageSize", pageSize);
				map.put("nowpage", nowpage);
			} else {
				map.put("ispaging", ConstUtil.PAGE_FLAG_0);
			}
			String visualstaff = SessionUtil.getVisualstaff(request);
			JSONObject mdata = logic.getMzListPage(TableNameUtil.KQDS_HOSPITAL_ORDER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 门诊
			YZUtility.DEAL_SUCCESS(mdata, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 今日工作（门诊预约）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectMzListToday.act")
	public String selectMzListToday(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> mdata = logic.getMzList(TableNameUtil.KQDS_HOSPITAL_ORDER, 0, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 门诊
			JSONObject jobj = new JSONObject();
			jobj.put("data", mdata);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 今日工作（网电预约）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectWdListToday.act")
	public String selectWdListToday(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> wdata = logic.getWdList(TableNameUtil.KQDS_NET_ORDER, 0, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 网电
			JSONObject jobj = new JSONObject();
			jobj.put("data", wdata);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 今日工作（今日患者）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectRegList.act")
	public String selectRegList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String visualstaff = SessionUtil.getVisualstaff(request);
			String regsort = request.getParameter("regsort");
			String recesort = request.getParameter("recesort");
			String search = request.getParameter("search");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(regsort)) {
				map.put("regsort", regsort);
			}
			if (!YZUtility.isNullorEmpty(recesort)) {
				map.put("recesort", recesort);
			}
			if (!YZUtility.isNullorEmpty(search)) {
				map.put("search", search);
			}
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			String czSeqId = SysParaUtil.getSysValueByName(request, SysParaUtil.JZFL_CHUZHEN_SEQID);
			map.put("czSeqId", czSeqId);
			// 是否分页
			String ispaging = request.getParameter("ispaging");
			// 是否分页 1 分页 0 不分页
			if (!YZUtility.isNullorEmpty(ispaging)) {
				String pageSize = request.getParameter("pageSize");
				// 当前页
				String nowpage = request.getParameter("nowpage");
				map.put("ispaging", ispaging);
				map.put("pageSize", pageSize);
				map.put("nowpage", nowpage);
			} else {
				map.put("ispaging", ConstUtil.PAGE_FLAG_0);
			}
			JSONObject regdata = logic.getRegList(TableNameUtil.KQDS_REG, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 今日患者
			YZUtility.DEAL_SUCCESS(regdata, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 患者信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectUserInfo.act")
	public String selectUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			JSONObject regdata = logic.selectUserInfo(TableNameUtil.KQDS_USERDOCUMENT, usercode);// 今日患者
			JSONObject jobj = new JSONObject();
			jobj.put("data", regdata);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 今日工作（今日回访）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectHfToDayList.act")
	public String selectHfToDayList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> vdata = logic.getHfList(TableNameUtil.KQDS_VISIT, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 今日回访
			JSONObject jobj = new JSONObject();
			jobj.put("data", vdata);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 回访查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectHfList.act")
	public String selectHfList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			// 是否分页
			String ispaging = request.getParameter("ispaging");
			// 是否分页 1 分页 0 不分页
			if (!YZUtility.isNullorEmpty(ispaging)) {
				String pageSize = request.getParameter("pageSize");
				// 当前页
				String nowpage = request.getParameter("nowpage");
				map.put("ispaging", ispaging);
				map.put("pageSize", pageSize);
				map.put("nowpage", nowpage);
			} else {
				map.put("ispaging", ConstUtil.PAGE_FLAG_0);
			}
			String visualstaff = SessionUtil.getVisualstaff(request);
			JSONObject vdata = logic.getHfListPage(TableNameUtil.KQDS_VISIT, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 今日回访
			YZUtility.DEAL_SUCCESS(vdata, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 明日预约
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountWorkTomorrow.act")
	public String selectCountWorkTomorrow(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String visualstaff = SessionUtil.getVisualstaff(request);
			// 明日预约（门诊预约、网络预约）
			List<JSONObject> wdata = logic.getWdList(TableNameUtil.KQDS_NET_ORDER, -1, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 网电
			List<JSONObject> mdata = logic.getMzList(TableNameUtil.KQDS_HOSPITAL_ORDER, -1, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 门诊
			// 门诊预约
			int mztotal = 0;
			if (mdata != null && mdata.size() > 0) {
				mztotal = mdata.size();
			}

			// 网电预约
			int wdtotal = 0;
			if (wdata != null && wdata.size() > 0) {
				wdtotal = wdata.size();
			}

			JSONObject jobj = new JSONObject();
			jobj.put("mzdata", mztotal);
			jobj.put("wddata", wdtotal);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 明日（门诊预约）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectMzListTomorrow.act")
	public String selectMzListTomorrow(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> mdata = logic.getMzList(TableNameUtil.KQDS_HOSPITAL_ORDER, -1, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 门诊
			JSONObject jobj = new JSONObject();
			jobj.put("data", mdata);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 今日工作（网电预约）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectWdListTomorrow.act")
	public String selectWdListTomorrow(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> wdata = logic.getWdList(TableNameUtil.KQDS_NET_ORDER, -1, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request));// 网电
			JSONObject jobj = new JSONObject();
			jobj.put("data", wdata);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 欠费查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/qfSearch.act")
	public String qfSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			BootStrapPage bp = new BootStrapPage();
			Map<String, String> map = new HashMap<String, String>();
			map.put("endtime", YZUtility.getCurDateTimeStr());
			String visualstaff = SessionUtil.getVisualstaff(request);
			map.put("visualstaff", visualstaff);
			map.put("organization", ChainUtil.getOrganizationFromUrlCanNull(request));
			JSONObject data = costlogic.qfSearch(bp, person, map, request);
			data.put("nowdate", YZUtility.getCurDateTimeStr());
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 患者网电预约记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectWdYyForUser.act")
	public String selectWdYyForUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			List<JSONObject> wdata = logic.getWdListByUsercode(TableNameUtil.KQDS_NET_ORDER, usercode, ChainUtil.getOrganizationFromUrlCanNull(request));// 网电

			JSONObject jobj = new JSONObject();
			jobj.put("data", wdata);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 患者门诊预约记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectMzYyForUser.act")
	public String selectMzYyForUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			List<JSONObject> mdata = logic.getMzListByUsercode(TableNameUtil.KQDS_HOSPITAL_ORDER, usercode, ChainUtil.getOrganizationFromUrlCanNull(request));// 门诊

			JSONObject jobj = new JSONObject();
			jobj.put("data", mdata);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 患者回访记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectHfForUser.act")
	public String selectHfForUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			List<JSONObject> mdata = logic.getHfListByUsercode(TableNameUtil.KQDS_VISIT, usercode, ChainUtil.getOrganizationFromUrlCanNull(request));// 门诊

			JSONObject jobj = new JSONObject();
			jobj.put("data", mdata);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 患者费用详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCostForUser.act")
	public String selectCostForUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			List<JSONObject> mdata = logic.getCostListByUsercode(TableNameUtil.KQDS_COSTORDER, usercode, ChainUtil.getOrganizationFromUrlCanNull(request));// 门诊

			JSONObject jobj = new JSONObject();
			jobj.put("data", mdata);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
}