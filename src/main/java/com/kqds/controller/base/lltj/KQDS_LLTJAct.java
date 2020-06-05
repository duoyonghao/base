package com.kqds.controller.base.lltj;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsLltj;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.lltj.Kqds_LLTJLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("KQDS_LLTJAct")
public class KQDS_LLTJAct {
	private static Logger logger = LoggerFactory.getLogger(KQDS_LLTJAct.class);
	@Autowired
	private Kqds_LLTJLogic logic;

	@RequestMapping(value = "/toLingliao.act")
	public ModelAndView toLingliao(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String treatdetailno = request.getParameter("treatdetailno");
		String username = request.getParameter("username");
		String usercode = request.getParameter("usercode");
		String itemno = request.getParameter("itemno");
		String unit = request.getParameter("unit");
		ModelAndView mv = new ModelAndView();
		mv.addObject("treatdetailno", treatdetailno);
		mv.addObject("username", username);
		mv.addObject("usercode", usercode);
		mv.addObject("itemno", itemno);
		mv.addObject("unit", unit);
		mv.setViewName("/kqdsFront/lltj/lingliao.jsp");
		return mv;
	}

	@RequestMapping(value = "/toLltj.act")
	public ModelAndView toLltj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/lltj/lltj.jsp");
		return mv;
	}

	@RequestMapping(value = "/toLltjQuery.act")
	public ModelAndView toLltjQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/lltj/lltj_query.jsp");
		return mv;
	}

	/**
	 * 查询费用明细(拆分,领料时调用) ######领料统计专用方法 yangsen 17-4-24
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/chaiFen.act")
	public String chaiFen(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String queryinput = request.getParameter("queryinput");

			Map<String, String> map = new HashMap<String, String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = new Date();
			// 如果页面传入的时间查询条件为空，则默认查询当天的
			if (YZUtility.isNullorEmpty(starttime) && YZUtility.isNullorEmpty(endtime)) {
				starttime = sdf.format(d) + ConstUtil.TIME_START;
				endtime = sdf.format(d) + ConstUtil.TIME_END;
				map.put("starttime", starttime);
				map.put("endtime", endtime);
			} else { // 否则，根据条件进行查询
				if (!YZUtility.isNullorEmpty(starttime)) {
					starttime = starttime + ConstUtil.TIME_START;
					map.put("starttime", starttime);
				}
				if (!YZUtility.isNullorEmpty(endtime)) {
					endtime = endtime + ConstUtil.TIME_END;
					map.put("endtime", endtime);
				}
			}

			if (!YZUtility.isNullorEmpty(queryinput)) {
				map.put("queryinput", queryinput);
			}

			// 只查询未拆分的，拆分后要更新此字段
			map.put("issplit", ConstUtil.IS_SPLIT_0);

			// 与收费明细，明细查询共用 selectNoPage 方法
			List<JSONObject> list1 = logic.getCostDetailList4LltjChaifen(TableNameUtil.KQDS_COSTORDER_DETAIL, person, map, ChainUtil.getCurrentOrganization(request));

			for (int j = 0; j < list1.size(); j++) {
				JSONObject jo = list1.get(j);
				// 消费明细对象
				KqdsCostorderDetail costdetail = (KqdsCostorderDetail) JSONObject.toBean(jo, KqdsCostorderDetail.class);
				// 获取项目的数量
				int num = Integer.parseInt(costdetail.getNum());
				if (num > 0) {
					// 改收费项目需要拆分
					for (int i = 0; i < num; i++) {
						// 治疗明细对象
						KqdsLltj treatdetail = (KqdsLltj) JSONObject.toBean(jo, KqdsLltj.class);
						String uuid = YZUtility.getUUID();
						treatdetail.setSeqId(uuid);
						treatdetail.setCostdetailno(costdetail.getSeqId());
						treatdetail.setNum("1"); // 先把数量设置为1
						treatdetail.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
						logic.saveSingleUUID(TableNameUtil.KQDS_LLTJ, treatdetail);
					}

				}

				// 不管num 是大于0，等于0，还是小于量，读取过后，都更改状态为已拆分
				costdetail.setIssplit(1); // 0 未拆分 1已拆分
				logic.updateSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, costdetail);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询费用明细(拆分,领料时调用) ######领料统计专用方法 yangsen 17-4-24
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllTreatDetailList.act")
	public String getAllTreatDetailList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String costno = request.getParameter("costno");
			String cjStatus = request.getParameter("cjStatus");
			String qf = request.getParameter("qf");
			String basetype = request.getParameter("basetype");
			String nexttype = request.getParameter("nexttype");
			String queryinput = request.getParameter("queryinput");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String askperson = request.getParameter("askperson");
			String doctor = request.getParameter("doctor");
			String createuser = request.getParameter("createuser");
			String devchannel = request.getParameter("devchannel");
			String nexttype1 = request.getParameter("nexttype1");
			String recesort = request.getParameter("recesort");
			String regsort = request.getParameter("regsort");
			Map<String, String> map = new HashMap<String, String>();
			Date d = new Date();
			// 如果页面传入的时间查询条件为空，则默认查询当天的
			if (YZUtility.isNullorEmpty(starttime) && YZUtility.isNullorEmpty(endtime)) {
				starttime = sdf.format(d) + ConstUtil.TIME_START;
				endtime = sdf.format(d) + ConstUtil.TIME_END;
				map.put("starttime", starttime);
				map.put("endtime", endtime);
			} else { // 否则，根据条件进行查询
				if (!YZUtility.isNullorEmpty(starttime)) {
					starttime = starttime + ConstUtil.TIME_START;
					map.put("starttime", starttime);
				}
				if (!YZUtility.isNullorEmpty(endtime)) {
					endtime = endtime + ConstUtil.TIME_END;
					map.put("endtime", endtime);
				}
			}
			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			if (!YZUtility.isNullorEmpty(doctor)) {
				map.put("doctor", doctor);
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
			if (!YZUtility.isNullorEmpty(qf)) { // 为空，则不将qf放入查询map
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

			String visualstaff = SessionUtil.getVisualstaff(request);
			// 与收费明细，明细查询共用 selectNoPage 方法
			// 门诊条件过滤
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			List<JSONObject> list = logic.selectTreatDetailListNopage(person, map, visualstaff, organization);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 更新 treat_detail 的治疗状态为已治疗
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTreatStatus.act")
	public String updateTreatStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);
		try {
			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}
			KqdsLltj treatdetail = (KqdsLltj) logic.loadObjSingleUUID(TableNameUtil.KQDS_LLTJ, seqId);
			if (treatdetail == null) {
				throw new Exception("操作失败，记录不存在");
			}
			treatdetail.setIszl(1); // 设置为已治疗
			treatdetail.setZlperson(person.getSeqId());
			treatdetail.setZltime(YZUtility.getCurDateTimeStr());
			logic.updateSingleUUID(TableNameUtil.KQDS_LLTJ, treatdetail);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}

		return null;

	}

}