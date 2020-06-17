package com.kqds.controller.base.paiban;

import java.text.SimpleDateFormat;
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

import com.kqds.entity.base.KqdsPaiban;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.paiban.KQDS_PaibanLogic;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_PaibanAct")
public class KQDS_PaibanAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_PaibanAct.class);
	@Autowired
	private KQDS_PaibanLogic logic;
	@Autowired
	private YZDeptLogic deptLogic;
	@Autowired
	private YZPersonLogic personLogic;

	@RequestMapping(value = "/toPaiBan.act")
	public ModelAndView toPaiBan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String personid = request.getParameter("personid");
		String regdept = request.getParameter("regdept");
		String stepnum = request.getParameter("stepnum");
		ModelAndView mv = new ModelAndView();
		mv.addObject("personid", personid);
		mv.addObject("regdept", regdept);
		mv.addObject("stepnum", stepnum);
		mv.setViewName("/kqdsFront/yyzx/paiban.jsp");
		return mv;
	}

	@RequestMapping(value = "/toPaiBanExport.act")
	public ModelAndView toPaiBanExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String personid = request.getParameter("personid");
		String regdept = request.getParameter("regdept");
		ModelAndView mv = new ModelAndView();
		mv.addObject("personid", personid);
		mv.addObject("regdept", regdept);
		mv.setViewName("/kqdsFront/yyzx/paiban_export.jsp");
		return mv;
	}

	/**
	 * 入库，修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsPaiban dp = new KqdsPaiban();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dp.setStartdate(formatter.format(formatter.parse(dp.getStartdate())));
			dp.setEnddate(formatter.format(formatter.parse(dp.getEnddate())));
			if (!YZUtility.isNullorEmpty(seqId)) {
				logic.updateSingleUUID(TableNameUtil.KQDS_PAIBAN, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.MODIFY, BcjlUtil.KQDS_PAIBAN, dp, TableNameUtil.KQDS_PAIBAN, request);
			} else {
				String uuid = YZUtility.getUUID();
				dp.setSeqId(uuid);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setDeptid(deptLogic.getDeptSeqIdByUserSeqId(dp.getPersonid()));// 科室
				dp.setOrganization(ChainUtil.getCurrentOrganization(request)); // 【前端页面调用，以所在门诊为准】
				logic.saveSingleUUID(TableNameUtil.KQDS_PAIBAN, dp);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.NEW, BcjlUtil.KQDS_PAIBAN, dp, TableNameUtil.KQDS_PAIBAN, request);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 删除
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

			KqdsPaiban en = (KqdsPaiban) logic.loadObjSingleUUID(TableNameUtil.KQDS_PAIBAN, seqId);
			if (en != null) {
				logic.deleteSingleUUID(TableNameUtil.KQDS_PAIBAN, seqId);
				// 记录日志
				BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_PAIBAN, en, TableNameUtil.KQDS_PAIBAN, request);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据日期判断当前日历上展示的人，在该日期上是否值班
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*
	 * public String getRestPersonListByDate(HttpServletRequest request,
	 * HttpServletResponse response) throws Exception { Connection dbConn = null;
	 * 
	 * try {
	 * 
	 * 
	 * 
	 * Map<String, String> map = new HashMap<String, String>(); String perIds =
	 * request.getParameter("perIds");// 医生集合
	 * 
	 * long time = Long.parseLong(request.getParameter("starttime")); Date date =
	 * new Date(time);
	 * 
	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); String starttime =
	 * sdf.format(date);
	 * 
	 * if (perIds.endsWith(",")) { // 去掉末尾的逗号 perIds = perIds.substring(0,
	 * perIds.length() - 1); }
	 * 
	 * String[] perIdArray = perIds.split(",");
	 * 
	 * List<Integer> indexList = new ArrayList<Integer>(); List<String> perSeqIdList
	 * = new ArrayList<String>(); List<String> orderStatusList = new
	 * ArrayList<String>();
	 * 
	 * String organization = ChainUtil.getOrganizationFromUrlCanNull(request); if
	 * (YZUtility.isNullorEmpty(organization)) { organization =
	 * ChainUtil.getCurrentOrganization(request); }
	 * 
	 * for (int i = 0; i < perIdArray.length; i++) { map.put("starttime",
	 * starttime); map.put("askperson", perIdArray[i]);
	 * 
	 * // 如果大于0 说明在班 等于0说明不在班 int count = logich.checkYy(map, organization);
	 * indexList.add(i); // 这里面存的值是 索引值，和 预约界面的 人员索引对应， 存储的是不在班人员 if (count == 0) {
	 * perSeqIdList.add(perIdArray[i]); // 存储休息人员的seqId } String orderstatus =
	 * logich.orderstatusString(map);
	 * orderStatusList.add(orderstatus);//存储不在班人员的排班类型 }
	 * 
	 * JSONObject jobj = new JSONObject(); jobj.put("perIndexList", indexList);
	 * jobj.put("perSeqIdList", perSeqIdList); jobj.put("orderStatusList",
	 * orderStatusList); YZUtility.DEAL_SUCCESS(jobj, null, response, logger); }
	 * catch (Exception ex) { YZUtility.DEAL_ERROR(null, false, ex, response,
	 * logger); } return null; }
	 */

	/**
	 * 日历查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showXML.act")
	public String showXML(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 前台需要读取某范围的数据时，通过数据库数据查询，将该段时间的日历保存到一个xml文件中。
			Map<String, String> map = new HashMap<String, String>();
			String orderstatus = request.getParameter("orderstatus");// 预约项目
			String personid = request.getParameter("personid");// 医生集合
			String regdept = request.getParameter("regdept");// 科室

			String starttime = request.getParameter("starttime");// 科室
			String endtime = request.getParameter("endtime");// 科室
			starttime = YZUtility.parseDate2(starttime);
			endtime = YZUtility.parseDate2(endtime);

			if (!YZUtility.isNullorEmpty(starttime) && !starttime.equals("null")) {
				map.put("starttime", starttime); // 2017-05-26 00:00:00
			}
			if (!YZUtility.isNullorEmpty(endtime) && !endtime.equals("null")) {
				if (endtime.length() == 19) { // 2017-05-26 00:00:00
					endtime = endtime.substring(0, 10) + ConstUtil.TIME_END;
				}
				map.put("endtime", endtime);
			}

			if (!YZUtility.isNullorEmpty(orderstatus) && !orderstatus.equals("null")) {
				map.put("orderstatus", orderstatus);
			}
			if (!YZUtility.isNullorEmpty(personid) && !personid.equals("null")) {
				map.put("personid", personid);
			}
			if (!YZUtility.isNullorEmpty(regdept) && !regdept.equals("null")) {
				map.put("deptid", regdept);
			}
			List<JSONObject> list = logic.selectList(TableNameUtil.KQDS_PAIBAN, map, ChainUtil.getCurrentOrganization(request));
			for (int i = 0; i < list.size(); i++) {
				JSONObject hd = list.get(i);

				StringBuffer bf = new StringBuffer();
				bf.append("<br>人员：");
				bf.append(hd.getString("personidname"));
				bf.append("<br>排班类型：");
				bf.append(hd.getString("orderstatus").equals("休息") ? "<span style='color:red'>休息</span>" : hd.getString("orderstatus"));
				bf.append("<br>创建人：");
				bf.append(hd.getString("createusername"));

				hd.put("text", bf.toString());
				hd.put("id", hd.getString("seq_id"));
				hd.put("start_date", hd.getString("startdate"));
				hd.put("end_date", hd.getString("enddate"));
				hd.put("pername", hd.getString("personidname"));
			}
			JSONObject jobj = new JSONObject();
			jobj.put("result", "ok");
			jobj.put("path", list.toString());
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 排班模板导出
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excelStandardTemplateOut.act")
	public String excelStandardTemplateOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String deptId = request.getParameter("deptId");
			String seqId = request.getParameter("seqId");
			String startdate = request.getParameter("startdate");
			String enddate = request.getParameter("enddate");

			List<JSONObject> perlist = personLogic.getPersonList(request, deptId, seqId); // 人员列表集合
			ExportTable.exportExcel4PaiBan("排班模板", perlist, startdate, enddate, response);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 排班 默认第一次进入页面查询 权限可见人员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersoPaiban.act")
	public String getPersoPaiban(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String deptId = request.getParameter("deptId");
			String seqId = request.getParameter("seqId");
			List<JSONObject> list = personLogic.getPersonList(request, deptId, seqId); // 人员列表集合
			JSONObject jobj = new JSONObject();
			jobj.put("result", "ok");
			jobj.put("list", list);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 判断当前时间是否排过班 排过班的 不可新建 只能修改
	 */
	@RequestMapping(value = "/checkPaiban.act")
	public String checkPaiban(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String starttime = formatter.format(formatter.parse(request.getParameter("starttime")));
			String personid = request.getParameter("personid");
			Map<String, String> map = new HashMap<String, String>();
			map.put("starttime", starttime);
			map.put("personid", personid);
			JSONObject jobj = new JSONObject();
			// 如果大于0 说明在班 等于0说明不在班
			int count = logic.checkPaiban(map, ChainUtil.getCurrentOrganization(request));

			jobj.put("data", count);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}