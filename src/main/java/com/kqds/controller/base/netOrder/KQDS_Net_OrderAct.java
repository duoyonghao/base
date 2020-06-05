
package com.kqds.controller.base.netOrder;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.entity.base.KqdsNetOrderRecord;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.extension.KQDS_ExtensionLogic;
import com.kqds.service.base.hospitalOrder.KQDS_Hospital_OrderLogic;
import com.kqds.service.base.netOrder.KQDS_Net_OrderLogic;
import com.kqds.service.base.paycost.Kqds_PayCostLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.base.room.KQDS_RoomLogic;
import com.kqds.service.base.visit.KQDS_VisitLogic;
import com.kqds.service.ck.KQDS_Ck_Goods_DetailLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("KQDS_Net_OrderAct")
public class KQDS_Net_OrderAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Net_OrderAct.class);
	@Autowired
	private YZPersonLogic personLogic;
	@Autowired
	private KQDS_Net_OrderLogic logic;
	@Autowired
	private KQDS_Hospital_OrderLogic hlogic;
	@Autowired
	private KQDS_REGLogic rlogic;
	@Autowired
	private KQDS_CostOrderLogic clogic;
	@Autowired
	private Kqds_PayCostLogic plogic;
	@Autowired
	private KQDS_VisitLogic vlogic;
	@Autowired
	private KQDS_RoomLogic roomlogic;
	@Autowired
	private KQDS_ExtensionLogic tglogic;
	@Autowired
	private KQDS_Ck_Goods_DetailLogic cklogic;

	@RequestMapping(value = "/toWdyySearch.act")
	public ModelAndView toWdyySearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("isyx", 1);
		mv.setViewName("/kqdsFront/index/kfzx/wdyySearch.jsp");
		return mv;
	}

	@RequestMapping(value = "/toWdyySearch2.act")
	public ModelAndView toWdyySearch2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("isyx", 2);
		mv.setViewName("/kqdsFront/index/kfzx/wdyySearch.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNetorderInsert.act")
	public ModelAndView toNetorderInsert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/yyzx/netorder_insert.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNetorderSearchWd.act")
	public ModelAndView toNetorderSearchWd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("username", username);
		mv.setViewName("/kqdsFront/yyzx/netOrderSearchWd.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNetorderSearchMz.act")
	public ModelAndView toNetorderSearchMz(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("username", username);
		mv.setViewName("/kqdsFront/yyzx/netOrderSearchMz.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNetorderSearch.act")
	public ModelAndView toNetorderSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("username", username);
		mv.setViewName("/kqdsFront/yyzx/netOrderSearch.jsp");
		return mv;
	}
	@RequestMapping(value = "/toYyzxSearchMz.act")
	public ModelAndView toYyzxSearchMz(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("username", username);
		mv.setViewName("/kqdsFront/yyzx/yyzxSearchMz.jsp");
		return mv;
	}

	@RequestMapping(value = "/toYyzxSearchWd.act")
	public ModelAndView toYyzxSearchWd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("username", username);
		mv.setViewName("/kqdsFront/yyzx/yyzxSearchWd.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNetorderInsertOrupdate.act")
	public ModelAndView toNetorderInsertOrupdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String source = request.getParameter("source");
		ModelAndView mv = new ModelAndView();
		mv.addObject("source", source);
		mv.setViewName("/kqdsFront/yyzx/netorder_insertOrUpdate.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNetorder2.act")
	public ModelAndView toNetorder2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/yyzx/netorder.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNetorderList.act")
	public ModelAndView toNetorderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/yyzx/netorderlist.jsp");
		return mv;
	}

	@RequestMapping(value = "/toYyzx2.act")
	public ModelAndView toYyzx2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");
		String order_number = request.getParameter("order_number");
		String nodeId = request.getParameter("nodeId");
		String yyxm = request.getParameter("yyxm");
		String doctors = request.getParameter("doctors");
		String regdept = request.getParameter("regdept");
		String organization = request.getParameter("organization");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("username", username);
		mv.addObject("yyxm", yyxm);
		mv.addObject("doctors", doctors);
		mv.addObject("regdept", regdept);
		mv.addObject("organization", organization);
		mv.addObject("order_number", order_number);
		mv.addObject("nodeId", nodeId);
		mv.setViewName("/kqdsFront/yyzx/yyzx2.jsp");
		return mv;
	}

	@RequestMapping(value = "/toNetorder.act")
	public ModelAndView toNetorder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/index/center/netorder.jsp");
		return mv;
	}

	@RequestMapping(value = "/toYyzxSearch.act")
	public ModelAndView toYyzxSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/yyzx/yyzxSearch.jsp");
		return mv;
	}

	/**
	 * 获取患者的第一条网电记录，用于网电建档后，编辑档案时展示使用
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectFirstByUsercode.act")
	public String selectFirstByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			String usercode = request.getParameter("usercode");

			JSONObject wdinfo = logic.selectFirstByUsercode(usercode);
			JSONObject jobj = new JSONObject();
			jobj.put("wdinfo", wdinfo);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/****************************************
	 * 网电中心、客服中心、营销中心
	 * 
	 * @throws SQLException
	 **************************************/

	/****************************************
	 * 网电中心、客服中心、营销中心 END
	 **************************************/

	/**
	 * 查询该患者 当天是否存在预约，同一个患者当天不允许预约两次 【同一个患者的编辑操作，不算做重复预约】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkNetOrderCount.act")
	public String checkNetOrderCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			String ordertime = request.getParameter("ordertime");
			String seqId = request.getParameter("seqId");

			int count = 0;
			if (!YZUtility.isNullorEmpty(usercode) && !YZUtility.isNullorEmpty(ordertime)) {
				ordertime = ordertime.substring(0, 10); // 取到日期 2017-04-09
				List<JSONObject> list = logic.checkNetOrderCount(seqId, usercode, ordertime);

				if (list.size() >= 1) {
					throw new Exception("同一个患者当天只能网电预约一次，患者编号：" + usercode + "，预约日期：" + ordertime + "。");
				}
			}

			JSONObject jobj = new JSONObject();
			jobj.put("orderCount", count);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 入库，修改
	 */
	@RequestMapping(value = "/insertOrUpdate.act")
	public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsNetOrder dp = new KqdsNetOrder();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			if (!YZUtility.isNullorEmpty(seqId)) {
				logic.updateSingleUUID(TableNameUtil.KQDS_NET_ORDER, dp);

				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_NET_ORDER, dp, dp.getUsercode(), TableNameUtil.KQDS_NET_ORDER, request);
			} else {
				String uuid = YZUtility.getUUID();
				seqId = uuid;
				dp.setSeqId(uuid);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());

				/**
				 * ###### 网电，可以跨店预约，以页面传入进来的门诊编号为主!!
				 */
				dp.setOrganization(ChainUtil.getOrganizationFromUrl(request));
				dp.setDoorstatus(0); // 上门状态 0 未上门 1已上门
				dp.setCjstatus(0);// 未成交

				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("usercode", dp.getUsercode());
				List<KqdsUserdocument> user = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map1);
				dp.setAcceptdate(user.get(0).getCreatetime());
				dp.setOrganization(organization); // ###
													// !!!
													// 网电可以跨店预约，所以以页面传入organization为准
				logic.saveSingleUUID(TableNameUtil.KQDS_NET_ORDER, dp);

				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_NET_ORDER, dp, dp.getUsercode(), TableNameUtil.KQDS_NET_ORDER, request);

			}

			// 新增修改网电咨询备注的记录-- 【接待中心会填此值】
			if (!YZUtility.isNullorEmpty(dp.getZxremark())) {
				KqdsNetOrderRecord r = new KqdsNetOrderRecord();
				r.setSeqId(YZUtility.getUUID());
				r.setCreatetime(YZUtility.getCurDateTimeStr());
				r.setCreateuser(person.getSeqId());
				r.setContent(dp.getZxremark());
				r.setUsercode(dp.getUsercode());
				r.setUsername(dp.getUsername());
				r.setNerorderseqid(seqId);
				r.setOrganization(organization); // ###
													// !!!
													// 网电可以跨店预约，所以以页面传入organization为准
				logic.saveSingleUUID(TableNameUtil.KQDS_NET_ORDER_RECORD, r);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/*
	 * 详情返回
	 */
	@RequestMapping(value = "/selectDetail.act")
	public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");

			KqdsNetOrder en = (KqdsNetOrder) logic.loadObjSingleUUID(TableNameUtil.KQDS_NET_ORDER, seqId);
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
	 * 分页查询-网电建档调用 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectPage.act")
	public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String usercode = request.getParameter("usercode");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			String username = request.getParameter("username");
			if (!YZUtility.isNullorEmpty(username)) {
				map.put("username", username);
			}
			String organization = request.getParameter("organization");
			if (!YZUtility.isNullorEmpty(organization)) {
				map.put("organization", organization);
			}
			String regdept = request.getParameter("regdept");
			if (!YZUtility.isNullorEmpty(regdept)) {
				map.put("regdept", regdept);
			}
			String askperson = request.getParameter("askperson");
			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			String orderstatus = request.getParameter("doorstatus");
			if (!YZUtility.isNullorEmpty(orderstatus)) {
				map.put("doorstatus", orderstatus);
			}
			String starttime = request.getParameter("starttime");
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime);
			}
			String endtime = request.getParameter("endtime");
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime);
			}
			String createtime = request.getParameter("createtime");
			if (!YZUtility.isNullorEmpty(createtime)) {
				map.put("createtime", createtime);
			}
			JSONObject jobj = logic.selectWithPage(TableNameUtil.KQDS_NET_ORDER, bp, map);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询网电记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectNoPage.act")
	public String selectNoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			String organization = request.getParameter("organization"); // ###
																		// 门诊条件过滤
			if (!YZUtility.isNullorEmpty(organization)) {
				map.put("organization", organization);
			}
			String regdept = request.getParameter("regdept");
			if (!YZUtility.isNullorEmpty(regdept)) {
				map.put("regdept", regdept);
			}
			String askperson = request.getParameter("askperson");
			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			String orderstatus = request.getParameter("doorstatus");
			if (!YZUtility.isNullorEmpty(orderstatus)) {
				map.put("doorstatus", orderstatus);
			}
			String starttime = request.getParameter("starttime");
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime);
			}
			String endtime = request.getParameter("endtime");
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime);
			}
			String createtime = request.getParameter("createtime");
			if (!YZUtility.isNullorEmpty(createtime)) {
				map.put("createtime", createtime);
			}
			List<JSONObject> list = logic.selectWithNopage(TableNameUtil.KQDS_NET_ORDER, map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 接待中心-网点预约列表调用 【只查询当前所在门诊，Organization直接从登录用户的session中获取】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectNetOrderList.act")
	public String selectNetOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String querytype = request.getParameter("querytype");
			String searchValue = request.getParameter("searchValue");
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);

			// 当前所在门诊
			String organization = ChainUtil.getCurrentOrganization(request);
			String sortName=request.getParameter("sortName");
			String sortOrder=request.getParameter("sortOrder");
			Map<String,String> map = new HashMap<String,String>();
			if(!YZUtility.isNullorEmpty(querytype)){
				map.put("querytype", querytype);
			}
			if(!YZUtility.isNullorEmpty(searchValue)){
				map.put("searchValue", searchValue);
			}
			if(!YZUtility.isNullorEmpty(organization)){
				map.put("organization", organization);
			}
			if(!YZUtility.isNullorEmpty(visualstaff)){
				map.put("visualstaff", visualstaff);
			}
			if(!YZUtility.isNullorEmpty(sortName)){
				map.put("sortName", sortName);
				map.put("sortOrder", sortOrder);
			}
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			JSONObject list = logic.selectNetOrderList(TableNameUtil.KQDS_NET_ORDER, person, map,bp);
			//JSONObject list1 = new JSONObject();
			YZUtility.DEAL_SUCCESS(list,null, response, logger);
			//YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 根据用户编号查询网电记录 不分页 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectListNoPage.act")
	public String selectListNoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			List<JSONObject> list = logic.selectListNoPage(TableNameUtil.KQDS_NET_ORDER, usercode);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectNoPageYYZX.act")
	public String selectNoPageYYZX(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sortName = request.getParameter("sortName");
		String sortOrder = request.getParameter("sortOrder");
		try {
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			// 封装参数到实体类
			String usercode = request.getParameter("usercode");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			String username = request.getParameter("username");
			if (!YZUtility.isNullorEmpty(username)) {
				map.put("username", username);
			}
			String regdept = request.getParameter("regdept");
			if (!YZUtility.isNullorEmpty(regdept)) {
				map.put("regdept", regdept);
			}
			String askperson = request.getParameter("askperson");
			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			String orderstatus = request.getParameter("doorstatus");
			if (!YZUtility.isNullorEmpty(orderstatus)) {
				map.put("doorstatus", orderstatus);
			}
			String starttime = request.getParameter("starttime");
			if (!YZUtility.isNullorEmpty(starttime)) {
				// 如果是精确到时分秒的，则去掉后面两位秒数值
				if (starttime.length() == 19) {
					starttime = starttime.substring(0, starttime.length() - 3);
				}
				// 如果是精确到天的，则加上 时分
				if (starttime.length() == 10) {
					starttime = starttime + ConstUtil.HOUR_START;
				}
				map.put("starttime", starttime);
			}
			String endtime = request.getParameter("endtime");
			if (!YZUtility.isNullorEmpty(endtime)) {
				// 如果是精确到时分秒的，则去掉后面两位秒数值
				if (endtime.length() == 19) {
					endtime = endtime.substring(0, endtime.length() - 3);
				}
				// 如果是精确到天的，则加上 时分
				if (endtime.length() == 10) {
					endtime = endtime + ConstUtil.HOUR_END;
				}
				map.put("endtime", endtime);
			}
			String createtime = request.getParameter("createtime");
			if (!YZUtility.isNullorEmpty(createtime)) {
				map.put("createtime", createtime);
			}
			String xiangmu = request.getParameter("xiangmu");
			if (!YZUtility.isNullorEmpty(xiangmu)) {
				map.put("xiangmu", xiangmu);
			}
			// 建档人
			String createuser = request.getParameter("createuser");
			if (!YZUtility.isNullorEmpty(createuser)) {
				map.put("createuser", createuser);
			}
			// 建档人
			String isdelete = request.getParameter("isdelete");
			if (!YZUtility.isNullorEmpty(isdelete)) {
				map.put("isdelete", isdelete);
			}
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap()); 
			JSONObject json = new JSONObject();
			// 可见人员过滤
//			String visualstaff = SessionUtil.getVisualstaff(request);
			String visualstaff = request.getSession().getAttribute(SessionUtil.visualstaffYyrl).toString();
			if(sortName !=null){
				map.put("sortName", sortName);
				map.put("sortOrder", sortOrder);
				json = logic.selectNoPageYyzx(TableNameUtil.KQDS_NET_ORDER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request),bp,json); // 允许organization不传值
			}else{		
				json = logic.selectNoPageYyzx(TableNameUtil.KQDS_NET_ORDER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request),bp,json); // 允许organization不传值
			}
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("预约信息", fieldArr, fieldnameArr, (List<JSONObject>)json.get("rows"), response, request);
				return null;
			}
			YZUtility.DEAL_SUCCESS(json, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	
	/**
	 * 添加查询，用于正畸部门查看网电信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/selectNoPageYyzxNet.act")
	public String selectNoPageYyzxNet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String sortName = request.getParameter("sortName");
			String sortOrder = request.getParameter("sortOrder");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			// 封装参数到实体类
			String usercode = request.getParameter("usercode");
			YZPerson person = SessionUtil.getLoginPerson(request);
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			String username = request.getParameter("username");
			if (!YZUtility.isNullorEmpty(username)) {
				map.put("username", username);
			}
			String regdept = request.getParameter("regdept");
			if (!YZUtility.isNullorEmpty(regdept)) {
				map.put("regdept", regdept);
			}
			String askperson = request.getParameter("askperson");
			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			String doorstatus = request.getParameter("doorstatus");
			if (!YZUtility.isNullorEmpty(doorstatus)) {
				map.put("doorstatus", doorstatus);
			}
			String starttime = request.getParameter("starttime");
			if (!YZUtility.isNullorEmpty(starttime)) {
				// 如果是精确到时分秒的，则去掉后面两位秒数值
				if (starttime.length() == 19) {
					starttime = starttime.substring(0, starttime.length() - 3);
				}
				// 如果是精确到天的，则加上 时分
				if (starttime.length() == 10) {
					starttime = starttime + ConstUtil.HOUR_START;
				}
				map.put("starttime", starttime);
			}
			String endtime = request.getParameter("endtime");
			if (!YZUtility.isNullorEmpty(endtime)) {
				// 如果是精确到时分秒的，则去掉后面两位秒数值
				if (endtime.length() == 19) {
					endtime = endtime.substring(0, endtime.length() - 3);
				}
				// 如果是精确到天的，则加上 时分
				if (endtime.length() == 10) {
					endtime = endtime + ConstUtil.HOUR_END;
				}
				map.put("endtime", endtime);
			}
			String createtime = request.getParameter("createtime");
			if (!YZUtility.isNullorEmpty(createtime)) {
				map.put("createtime", createtime);
			}
			String xiangmu = request.getParameter("xiangmu");
			if (!YZUtility.isNullorEmpty(xiangmu)) {
				map.put("xiangmu", xiangmu);
			}
			// 建档人
			String createuser = request.getParameter("createuser");
			if (!YZUtility.isNullorEmpty(createuser)) {
				map.put("createuser", createuser);
			}
			// 建档人
			String isdelete = request.getParameter("isdelete");
			if (!YZUtility.isNullorEmpty(isdelete)) {
				map.put("isdelete", isdelete);
			}
			// 可见人员过滤
//			String visualstaff = SessionUtil.getVisualstaff(request);
			String visualstaff = request.getSession().getAttribute(SessionUtil.visualstaffYyrl).toString();
			// 初始化分页实体类
			@SuppressWarnings("rawtypes")
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			JSONObject list = new JSONObject();
			if(sortName!=null){
				map.put("sortName", sortName);
				map.put("sortOrder", sortOrder);
				list = logic.selectNoPageYyzxNet(TableNameUtil.KQDS_NET_ORDER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), person,bp); // 允许organization不传值
			}else{		
				list = logic.selectNoPageYyzxNet(TableNameUtil.KQDS_NET_ORDER, map, visualstaff, ChainUtil.getOrganizationFromUrlCanNull(request), person,bp); // 允许organization不传值
			}
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("预约信息", fieldArr, fieldnameArr, (List<JSONObject>) list.get("rows"), response, request);
				return null;
			}
			YZUtility.DEAL_SUCCESS(list, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 网电预约-今日预约 只看本部门的（网电部门）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectNoPageYYZXWd.act")
	public String selectNoPageYYZXWd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String sortName=request.getParameter("sortName");
			String sortOrder=request.getParameter("sortOrder");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			// 封装参数到实体类
			String usercode = request.getParameter("usercode");
			String datetype = request.getParameter("datetype");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			String username = request.getParameter("username");
			if (!YZUtility.isNullorEmpty(username)) {
				map.put("username", username);
			}
			String organization = request.getParameter("organization");
			if (!YZUtility.isNullorEmpty(organization)) {
				map.put("organization", organization);
			}
			String regdept = request.getParameter("regdept");
			if (!YZUtility.isNullorEmpty(regdept)) {
				map.put("regdept", regdept);
			}
			String askperson = request.getParameter("askperson");
			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			String orderstatus = request.getParameter("doorstatus");
			if (!YZUtility.isNullorEmpty(orderstatus)) {
				map.put("doorstatus", orderstatus);
			}
			String starttime = request.getParameter("starttime");
			if (!YZUtility.isNullorEmpty(starttime)) {
				// 如果是精确到时分秒的，则去掉后面两位秒数值
				if (starttime.length() == 19) {
					starttime = starttime.substring(0, starttime.length() - 3);
				}
				// 如果是精确到天的，则加上 时分
				if (starttime.length() == 10) {
					starttime = starttime + ConstUtil.HOUR_START;
				}
				map.put("starttime", starttime);
			}
			String endtime = request.getParameter("endtime");
			if (!YZUtility.isNullorEmpty(endtime)) {
				// 如果是精确到时分秒的，则去掉后面两位秒数值
				if (endtime.length() == 19) {
					endtime = endtime.substring(0, endtime.length() - 3);
				}
				// 如果是精确到天的，则加上 时分
				if (endtime.length() == 10) {
					endtime = endtime + ConstUtil.HOUR_END;
				}
				map.put("endtime", endtime);
			}
			String createtime = request.getParameter("createtime");
			if (!YZUtility.isNullorEmpty(createtime)) {
				map.put("createtime", createtime);
			}
			String xiangmu = request.getParameter("xiangmu");
			if (!YZUtility.isNullorEmpty(xiangmu)) {
				map.put("xiangmu", xiangmu);
			}
			// 建档人
			String createuser = request.getParameter("createuser");
			if (!YZUtility.isNullorEmpty(createuser)) {
				map.put("createuser", createuser);
			}
			if(!YZUtility.isNullorEmpty(sortName)){
				map.put("sortName", sortName);
			}
			if(!YZUtility.isNullorEmpty(sortOrder)){
				map.put("sortOrder", sortOrder);
			}
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String visualstaff = SessionUtil.getVisualstaff(request);
			JSONObject list = logic.selectNoPageYyzxWd(TableNameUtil.KQDS_NET_ORDER, map, datetype, ChainUtil.getOrganizationFromUrlCanNull(request), visualstaff,bp); // 允许organization不传值
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("预约信息", fieldArr, fieldnameArr, list.getJSONArray("rows"), response, request);
				return null;
			}
			//YZUtility.RETURN_LIST(list, response, logger);
			YZUtility.DEAL_SUCCESS(list,null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询各类条数 【只查询当前所在门诊，Organization直接从登录用户的session中获取】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCountIndex.act")
	public String getCountIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String querytype = request.getParameter("querytype");
			String searchValue = request.getParameter("searchValue");
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);
			// 当前所在门诊
			String organization = ChainUtil.getCurrentOrganization(request);

			Map<String,String> map = new HashMap<String,String>();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String time = df.format(new Date());
			map.put("starttime",time + ConstUtil.TIME_START);
			map.put("endtime", time + ConstUtil.TIME_END);
			map.put("organization", person.getOrganization());
			int vdataCount = vlogic.getCountIndex(TableNameUtil.KQDS_VISIT, person, querytype, searchValue, visualstaff, organization);// 今日回访
			int mdataCount = hlogic.getCountIndex(querytype, searchValue, visualstaff, organization);// 门诊
			int ssdataCount = roomlogic.getCountIndex(TableNameUtil.KQDS_ROOM, person,querytype,searchValue, visualstaff, organization);// 手术
			int tgjhCount = tglogic.getCountIndex(visualstaff, organization);// 推广计划
			int ckbjCount = cklogic.getCountIndex(organization);// 库存报警
			
			//int wdataCount = logic.getCountIndex(TableNameUtil.KQDS_NET_ORDER, person, querytype, searchValue, visualstaff, organization);// 网电
			//int tdataCountd = rlogic.getCountIndex(TableNameUtil.KQDS_REG, person, 0, querytype, searchValue, visualstaff, organization, request);// 等待治疗
			//int tdataCountj = rlogic.getCountIndex(TableNameUtil.KQDS_REG, person, 5, querytype, searchValue, visualstaff, organization, request);// 今日患者
			//int ddataCount = clogic.getCountIndex(person, querytype, searchValue, visualstaff, organization, request);// 等待结账
			//int ydataCount = plogic.getCountIndex(TableNameUtil.KQDS_COSTORDER, person, querytype, searchValue, visualstaff, organization);// 已结账
			
			
			//int roomdataCount = roomlogic.getCountIndex(TableNameUtil.KQDS_ROOM, person,querytype,searchValue, visualstaff, organization);// 手术室
			
			// 查询今日工作量条数
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = new Date();
			String starttime = sdf.format(d) + ConstUtil.TIME_START;
			String endtime = sdf.format(d) + ConstUtil.TIME_END;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("starttime", starttime);
			map1.put("endtime", endtime);
			int glist = clogic.selectNoPage4IndexGzlNum(map1);
			JSONObject jobj = new JSONObject();
			//jobj.put("wdataCount", wdataCount);
			jobj.put("mdataCount", mdataCount);
			jobj.put("ssdataCount", ssdataCount);
			//jobj.put("tdataCountd", tdataCountd);
			//jobj.put("tdataCountj", tdataCountj);
			//jobj.put("ddataCount", ddataCount);
			//jobj.put("ydataCount", ydataCount);
			jobj.put("vdataCount", vdataCount);
			jobj.put("gdataCount", glist);
			jobj.put("roomdataCount", ssdataCount);
			jobj.put("tgjhCount", tgjhCount);
			jobj.put("ckbjCount", ckbjCount);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	/**
	 * 查询各类条数 【只查询当前所在门诊，Organization直接从登录用户的session中获取】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCountIndex1.act")
	public String getCountIndex1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			// 初始化分页实体类
			//BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			//BeanUtils.populate(bp, request.getParameterMap());
			String querytype = request.getParameter("querytype");
			String searchValue = request.getParameter("searchValue");
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);
			// 当前所在门诊
			String organization = ChainUtil.getCurrentOrganization(request);

			Map<String,String> map = new HashMap<String,String>();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String time = df.format(new Date());
			map.put("starttime",time + ConstUtil.TIME_START);
			map.put("endtime", time + ConstUtil.TIME_END);
			map.put("organization", person.getOrganization());
			
			int wdataCount = logic.getCountIndex(TableNameUtil.KQDS_NET_ORDER, person, querytype, searchValue, visualstaff, organization);// 网电
			int ssdataCount = roomlogic.getCountIndex(TableNameUtil.KQDS_ROOM, person,querytype,searchValue, visualstaff, organization);// 手术
			int mdataCount = hlogic.getCountIndex(querytype, searchValue, visualstaff, organization);// 门诊
			int tdataCountd = rlogic.getCountIndex(TableNameUtil.KQDS_REG, person, 0, querytype, searchValue, visualstaff, organization, request);// 等待治疗
			int tdataCountj = rlogic.getCountIndex(TableNameUtil.KQDS_REG, person, 5, querytype, searchValue, visualstaff, organization, request);// 今日患者
			int ddataCount = clogic.getCountIndex(person, querytype, searchValue, visualstaff, organization, request);// 等待结账
			int ydataCount = plogic.getCountIndex(TableNameUtil.KQDS_COSTORDER, person, querytype, searchValue, visualstaff, organization);// 已结账
			JSONObject jobj = new JSONObject();
			jobj.put("wdataCount", wdataCount);
			jobj.put("mdataCount", mdataCount);
			jobj.put("ssdataCount", ssdataCount);
			jobj.put("tdataCountd", tdataCountd);
			jobj.put("tdataCountj", tdataCountj);
			jobj.put("ddataCount", ddataCount);
			jobj.put("ydataCount", ydataCount);
			jobj.put("roomdataCount", ssdataCount);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	/**
	 * 报表 -- 预约上门率
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountYySml.act")
	public String selectCountYySml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			// 获取starttime endtime之间的日期
			String[] datearray = YZUtility.getBetweenDatesArray(starttime, endtime);
			// 预约项目分类 字典
			Double[] smlarray = new Double[datearray.length];
			for (int i = 0; i < datearray.length; i++) {
				Double sml = logic.getSml(datearray[i]);
				smlarray[i] = sml;
			}
			JSONObject jobj = new JSONObject();
			jobj.put("datearray", datearray);
			jobj.put("smlarray", smlarray);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);

		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 预约上门率 表格
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectCountYySmlTable.act")
	public String selectCountYySmlTable(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			List<Object> list = new ArrayList<Object>();
			// 获取starttime endtime之间的日期
			String[] datearray = YZUtility.getBetweenDatesArray(starttime, endtime);
			// 预约人数 数组
			int[] yyarray = new int[datearray.length];
			// 预约上门人数 数组
			int[] yysmarray = new int[datearray.length];
			// 上门率 数组
			String[] smlarray = new String[datearray.length];
			int yysum = 0;// 预约总人数
			int yysmsum = 0;// 预约总上门人数
			// String yysmlsum = "";//预约总上门率
			for (int i = 0; i < datearray.length; i++) {
				// 预约人数
				int yynum = logic.getCountYy(TableNameUtil.KQDS_NET_ORDER, datearray[i]);
				yysum += yynum;
				// 预约上门人数
				int yysmnum = logic.getCountYysm(TableNameUtil.KQDS_NET_ORDER, datearray[i]);
				yysmsum += yysmnum;
				if (yysmnum == 0) {
					smlarray[i] = "0";
				} else {
					float num = ((float) yysmnum / yynum) * 100;
					DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
					smlarray[i] = df.format(num);
				}
				yyarray[i] = yynum;
				yysmarray[i] = yysmnum;
			}
			list.add(datearray);
			list.add(yyarray);
			list.add(yysmarray);
			list.add(smlarray);

			list.add(yysum);
			list.add(yysmsum);
			if (yysmsum == 0) {
				list.add("0");
			} else {
				float num = ((float) yysmsum / yysum) * 100;
				DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
				list.add(df.format(num));
			}

			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 暂时没有被引用！！！！
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWdOrdertj.act")
	public String getWdOrdertj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String wdperson = request.getParameter("wdperson");
			String yxperson = request.getParameter("yxperson");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			Map<String, String> map = new HashMap<String, String>();
			String depttype = "";// 2 网电 3 营销
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			if (wdperson != null) {// 网电查询
				map.put("wdperson", wdperson);
				depttype = ConstUtil.DEPT_TYPE_2;
			}
			if (yxperson != null) {// 营销查询
				map.put("wdperson", yxperson);
				depttype = ConstUtil.DEPT_TYPE_3;
			}
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			// 可见人员过滤
			String visualstaff = SessionUtil.getVisualstaff(request);
			// 网电人员
			List<JSONObject> listPerson = personLogic.getPersonListByDeptTypeAndVisual(depttype, visualstaff, organization);
			List<JSONObject> list = new ArrayList<JSONObject>();
			for (int i = 0; i < listPerson.size(); i++) {
				JSONObject objper = listPerson.get(i);
				JSONObject obj = new JSONObject();
				obj.put("xh", i + 1);
				obj.put("username", objper.get("userName"));
				map.put("wdperson", (String) objper.get("seqId"));
				// 录单量
				int ldnums = logic.getCountJd(map);
				obj.put("ldnums", ldnums);
				// 预约人数
				int yynums = logic.getCountYy(map);
				obj.put("yynums", yynums);
				// 预约率 = 预约人数/录单量
				Float yyl = (float) 0;
				if (ldnums != 0) {
					yyl = (float) yynums * 100 / ldnums;
				}
				obj.put("yyl", YZUtility.FloatToFixed2(yyl) + "%");
				// 到院人数
				int yysmnums = logic.getCountYysm(map);
				obj.put("yysmnums", yysmnums);
				// 到诊率 = 到院人数/录单量
				Float dzl = (float) 0;
				if (ldnums != 0) {
					dzl = (float) yysmnums * 100 / ldnums;
				}
				obj.put("dzl", YZUtility.FloatToFixed2(dzl) + "%");
				// 收款金额 = 项目收款 + 预交金收款
				// 项目收款 = 实收 - 预交金
				// 预交金收款 =
				String skje = logic.getYysr(map);
				String skjeYjj = logic.getYysrYjj(map);
				String sk = YZUtility.FloatToFixed2(Float.parseFloat(skje) + Float.parseFloat(skjeYjj));
				obj.put("skje", sk);
				// 成交人数
				int cjnums = logic.getCountCj(map);
				obj.put("cjnums", cjnums);
				// 成交率 = 成交人数/到院人数
				Float cjl = (float) 0;
				if (yysmnums != 0) {
					cjl = (float) cjnums * 100 / yysmnums;
				}
				obj.put("cjl", YZUtility.FloatToFixed2(cjl) + "%");
				// 人均消费 = 收款金额/到院人数
				Float rjxf = (float) 0;
				if (yysmnums != 0) {
					rjxf = (float) Float.parseFloat(sk) / yysmnums;
				}
				obj.put("rjxf", YZUtility.FloatToFixed2(rjxf));
				list.add(obj);
			}
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("网电预约统计", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	
	@RequestMapping("/updateIsDeleteById.act")
	public void updateIsDeleteById(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String seqId = request.getParameter("seqId");
		try {
			logic.updateIsdeleteById(seqId, 1);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
	}
}