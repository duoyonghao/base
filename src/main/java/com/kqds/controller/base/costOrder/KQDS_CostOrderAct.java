package com.kqds.controller.base.costOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hudh.util.HUDHUtil;
import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsCostorderPriceList;
import com.kqds.entity.base.KqdsHzLabelAssociated;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.costOrder.KQDS_CostOrderLogic;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.costOrderPriceList.KQDS_CostOrderPriceListLogic;
import com.kqds.service.base.hzLabel.KQDS_HzLabelAssociatedLogic;
import com.kqds.service.base.label.KQDS_hz_labelLogic;
import com.kqds.service.base.memberRecord.KQDS_Member_RecordLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SQLUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportBean;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked" })
@Controller
@RequestMapping("KQDS_CostOrderAct")
public class KQDS_CostOrderAct {
	private static Logger logger = LoggerFactory.getLogger(KQDS_CostOrderAct.class);
	@Autowired
	private KQDS_CostOrderLogic logic;
	@Autowired
	private KQDS_CostOrder_DetailLogic logicd;
	@Autowired
	private KQDS_UserDocumentLogic userLogic;
	@Autowired
	private KQDS_CostOrderPriceListLogic priceListLogic;
	@Autowired
	private KQDS_HzLabelAssociatedLogic hzLabelAssociatedLogic;
	@Autowired
	private YZPersonLogic personLogic;
	@Autowired
	private KQDS_Member_RecordLogic recordLogic;
	@RequestMapping(value = "/toDetail_AddCost.act")
	public ModelAndView toDetail_AddCost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String zhekou = request.getParameter("zhekou");
		String costno = request.getParameter("costno");
		String isback = request.getParameter("isback");
		String regno = request.getParameter("regno");
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("zhekou", zhekou);
		mv.addObject("costno", costno);
		mv.addObject("isback", isback);
		mv.addObject("regno", regno);
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/coatOrder/add_cost.jsp");
		return mv;
	}

	@RequestMapping(value = "/toDetail_AddCostTest.act")
	public ModelAndView toDetail_AddCostTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String regno = request.getParameter("regno");
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("regno", regno);
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/coatOrder/add_cost4test.jsp");
		return mv;
	}

	@RequestMapping(value = "/toFycxCenter.act")
	public ModelAndView toFycxCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("/kqdsFront/index/jdzx/fycx_center.jsp");
		return mv;
	}

	/**
	 * 
	 * <p>Title: toBbConsumerTrends</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年4月11日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toBbConsumerTrends.act")
	public ModelAndView toBbConsumerTrends(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/bbzx/bb_consumer_trends.jsp");
		return mv;
	}
	/**
	 * 查询患者费用趋势
	 * <p>Title: consumerTrends</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2020年4月11日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/consumerTrends.act")
	public String consumerTrends(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String organization =request.getParameter("organization");
			String starttime=request.getParameter("starttime");
			String endtime=request.getParameter("endtime");
			String czstarttime=request.getParameter("czstarttime");
			String czendtime=request.getParameter("czendtime");
			Map<String,String> map=new HashMap<String,String>();
			map.put("organization", organization);
			map.put("starttime", starttime);
			map.put("endtime", endtime);
			map.put("czstarttime", czstarttime);
			map.put("czendtime", czendtime);
			@SuppressWarnings("rawtypes")
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			JSONObject object = logic.findCostOrderByUserdocument(map,bp);
			YZUtility.RETURN_OBJ(object, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	
	
	/**
	 * 验证费用单是否可以退单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/yzTuiDan.act")
	public String yzTuiDan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String costno = request.getParameter("costno");
			String usercode = request.getParameter("usercode");
			if (YZUtility.isNullorEmpty(costno)) { // 新增
				throw new Exception("请选择费用单！");
			}
			boolean flag = logic.ifCanTuiDan(costno);
			boolean flagJF = logic.ifCanTuiDanJF(costno, usercode, request);
			JSONObject jobj = new JSONObject();
			jobj.put("data", flag); // true 可以退单
			jobj.put("dataJF", flagJF); // true 可以退单 false 提示该费用单产生的积分已被使用
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 验证费用单是否可以退单（预交金单）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/yzTuiDanYjj.act")
	public String yzTuiDanYjj(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 验证该费用单结账后，该患者是否使用卡号为usercode的会员卡 结过账
			String costno = request.getParameter("costno");
			if (YZUtility.isNullorEmpty(costno)) { // 新增
				throw new Exception("请选择费用单！");
			}
			boolean flag = logic.ifCanTuiDanYJJ(costno);
			JSONObject jobj = new JSONObject();
			jobj.put("data", flag); // true 可以退单
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 验证费用单是否是 退单结账
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/yzTuiDanSf.act")
	public String yzTuiDanSf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			boolean flag = true;
			// 退单的费用单才能在此结账
			String costno = request.getParameter("costno");
			if (YZUtility.isNullorEmpty(costno)) { // 新增
				throw new Exception("请选择费用单！");
			}
			KqdsCostorder cost = (KqdsCostorder) logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, costno);
			if (cost == null) {
				throw new Exception("费用单不存在！");
			}
			if (cost.getIsback() != 1) {
				throw new Exception("请选择退单的费用单结账！");
			}
			if (!cost.getStatus().equals(ConstUtil.COST_ORDER_STATUS_1)) {
				throw new Exception("请选择等待结账的费用单！");
			}
			JSONObject jobj = new JSONObject();
			jobj.put("data", flag); // true 可以退单
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 退单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tuiDan.act")
	public String tuiDan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 退单处理 分2中情况 （1.正常收费单 2.预交金单）
		// 注意：预交金单 不需要修改患者的实收金额 ；会员卡需要退回金额
		try {
			String costno = request.getParameter("costno");
			String backremark = request.getParameter("backremark");
			if (YZUtility.isNullorEmpty(costno)) { // 新增
				throw new Exception("请选择费用单！");
			}
			if (YZUtility.isNullorEmpty(backremark)) {
				throw new Exception("请选择退单原因！");
			}

			YZPerson person = SessionUtil.getLoginPerson(request);

			String backid = YZUtility.getUUID();

			Map<String, String> map = new HashMap<String, String>();
			map.put("costno", costno);

			int isyjjitem = 0;// 是否是预交金单 0 不是 1 是
			List<KqdsCostorderDetail> detallist = (List<KqdsCostorderDetail>) logicd.loadList(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
			if (detallist == null || detallist.size() == 0) {
				throw new Exception("数据异常：消费项目不存在");
			}
			// 费用单
			KqdsCostorder cost = (KqdsCostorder) logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, costno);
			// 档案表
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("usercode", cost.getUsercode());
			List<KqdsUserdocument> userlist = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map2);
			if (userlist == null) {
				throw new Exception("患者不存在！");
			}
			KqdsUserdocument user = userlist.get(0);

			/** 判断是否是预交金单 **/
			for (KqdsCostorderDetail detail : detallist) {
				if (1 == detail.getIsyjjitem()) {
					isyjjitem = 1;
					break;
				}
			}

			if (1 == isyjjitem) {
				logic.ifCanTuiDanYJJ(costno); // 前后台都验证是否可退单，不可以退会抛异常
			} else {
				logic.ifCanTuiDan(costno); // 避免多人操作时，系统错误
				setIntegralMoney(costno, user, person.getSeqId(), request);
			}

			BigDecimal yjj = BigDecimal.ZERO; // 预交金
			BigDecimal zs = BigDecimal.ZERO; // 赠送
			logic.tuiDan(detallist, backid, yjj, zs, cost, backremark, person, costno, isyjjitem, map, request);
			JSONObject jobj = new JSONObject();
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 积分减少
	 * 
	 * @param usercode
	 * @param dbConn
	 * @param Actualmoney
	 * @param doctor
	 * @param money22
	 * @throws Exception
	 */
	private void setIntegralMoney(String costno, KqdsUserdocument u, String perId, HttpServletRequest request) throws Exception {
		BigDecimal ssmoney = userLogic.getSsjeOne(costno);
		BigDecimal integralback = userLogic.getJFjeOne(costno);

		BigDecimal costIntegral = new BigDecimal(SysParaUtil.getSysValueByName(request, SysParaUtil.COST_INTEGRAL));
		u.setTotalpay(u.getTotalpay().subtract(ssmoney));
		logic.setIntegralMoney(integralback, costIntegral, ssmoney, u, perId, request);
	}

	/**
	 * 查询费用单-首页工作量
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAll4IndexGzl.act")
	public String getAll4IndexGzl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 门诊连锁
			String isSelectCurrentOrg = request.getParameter("isSelectCurrentOrg");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");

			String organization = null;
			if (!YZUtility.isNullorEmpty(isSelectCurrentOrg) && "1".equals(isSelectCurrentOrg)) {
				organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
			} else {
				organization = request.getParameter("organization"); // 根据页面传值进行过滤
			}

			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			map.put("organization", organization);
			String visualstaff = SessionUtil.getVisualstaff(request);
			map.put("visualstaff", visualstaff);
			List<JSONObject> list = logic.selectNoPage4IndexGzl(map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	/**
	 * 
	 * 已结账（首页工作量，报表中心综合查询，费用查询按钮）
	 * 【该方法被调用的地方比较多，organization包括当前门诊和非当前门诊，根据前台Url传参标识进行对应处理】
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getAllyjz.act")
	public String getAllyjz(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String querytype = request.getParameter("querytype");// 如果不为空 则查全部
			String regno = request.getParameter("regno");
			String usercode = request.getParameter("usercode");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String cjStatus = request.getParameter("cjStatus");

			String askperson = request.getParameter("askperson");
			String regdept = request.getParameter("regdept");
			String doctor = request.getParameter("doctor");
			String nurse = request.getParameter("nurse");
			String createuser = request.getParameter("createuser");
			String devchannel = request.getParameter("devchannel");
			String nexttype = request.getParameter("nexttype");
			String recesort = request.getParameter("recesort");
			String regsort = request.getParameter("regsort");
			String remark = request.getParameter("remark");
			String isyjjitem = request.getParameter("isyjjitem");

			// 门诊连锁
			String isSelectCurrentOrg = request.getParameter("isSelectCurrentOrg");
			// 接待中心 搜索值
			String searchValue = request.getParameter("searchValue");
			String organization = null;
			if (!YZUtility.isNullorEmpty(isSelectCurrentOrg) && "1".equals(isSelectCurrentOrg)) {
				organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
			} else {
				organization = request.getParameter("organization"); // 根据页面传值进行过滤
			}

			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(organization)) {
				map.put("organization", organization);
			}
			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			if (!YZUtility.isNullorEmpty(regdept)) {
				map.put("regdept", regdept);
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
			if (!YZUtility.isNullorEmpty(nexttype)) {
				map.put("nexttype", nexttype);
			}
			if (!YZUtility.isNullorEmpty(recesort)) {
				map.put("recesort", recesort);
			}
			if (!YZUtility.isNullorEmpty(regsort)) {
				map.put("regsort", regsort);
			}
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(cjStatus)) {
				map.put("cjStatus", cjStatus);
			}
			if (!YZUtility.isNullorEmpty(regno)) {
				map.put("regno", regno);
			}
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			String zhcx_seqId = request.getParameter("zhcx_seqId");
			if (!YZUtility.isNullorEmpty(zhcx_seqId)) {
				map.put("seq_id", zhcx_seqId);
			}
			String queryinput = request.getParameter("queryinput");
			if (!YZUtility.isNullorEmpty(queryinput)) {
				map.put("queryinput", queryinput);
			}
			if (!YZUtility.isNullorEmpty(remark)) {
				map.put("remark", remark);
			}
			if (!YZUtility.isNullorEmpty(searchValue)) {
				map.put("searchValue", searchValue);
			}
			if (!YZUtility.isNullorEmpty(isyjjitem)) {
				map.put("isyjjitem", isyjjitem);
			}
			// 是否分页
			String ispaging = request.getParameter("ispaging");
			// 是否分页 1 分页 0 不分页
			if (!YZUtility.isNullorEmpty(ispaging) && YZUtility.isNullorEmpty(flag)) {
				map.put("ispaging", ispaging);
			} else {
				map.put("ispaging", ConstUtil.PAGE_FLAG_0);
			}
			String visualstaff = request.getSession().getAttribute(SessionUtil.visualstaff).toString();

			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				// System.out.println("start............");
				// long st1 = System.currentTimeMillis();
				JSONObject data = logic.selectNoPage(bp, person, map, visualstaff, querytype);
				if (data != null) {
					ExportBean bean = ExportTable.initExcel4RsWrite("费用查询", fieldArr, fieldnameArr, response, request);
					bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List<JSONObject>) data.get("rows"), "costOrder_selectNoPage");
					ExportTable.writeExcel4DownLoad("费用查询", bean.getWorkbook(), response);
				}
				// System.out.println("end............" +
				// (System.currentTimeMillis() - st1));
				return null;
			}

			JSONObject data = logic.selectNoPageYjz(bp, person, map, visualstaff, querytype);
			/*-------导出excel---------*/
			// if (flag != null && flag.equals("exportTable")) {
			// ExportTable.exportBootStrapTable2Excel("费用查询", fieldArr,
			// fieldnameArr, (List<JSONObject>) data.getJSONArray("rows"),
			// response, request);
			// return null;
			// }
			if (!YZUtility.isNullorEmpty(ispaging)) {
				YZUtility.DEAL_SUCCESS(data, null, response, logger);
			} else {
				YZUtility.RETURN_LIST((List<JSONObject>) data.getJSONArray("rows"), response, logger);
			}
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 
	 * 查询费用单（首页工作量，报表中心综合查询，费用查询按钮）
	 * 【该方法被调用的地方比较多，organization包括当前门诊和非当前门诊，根据前台Url传参标识进行对应处理】
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getAll.act")
	public String getAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String querytype = request.getParameter("querytype");// 如果不为空 则查全部
			String regno = request.getParameter("regno");
			String usercode = request.getParameter("usercode");
			String starttime = request.getParameter("sfstarttime");
			String endtime = request.getParameter("sfendtime");
			if(YZUtility.isNullorEmpty(starttime)){
				starttime = request.getParameter("starttime");
			}
			if(YZUtility.isNullorEmpty(endtime)){
				endtime = request.getParameter("endtime");
			}
			String cjStatus = request.getParameter("cjStatus");

			String askperson = request.getParameter("askpersonSearch");
			String regdept = request.getParameter("regdept");
			String doctor = request.getParameter("doctor");
			String nurse = request.getParameter("nurse");
			String createuser = request.getParameter("createuser");
			String devchannel = request.getParameter("devchannel");
			String nexttype = request.getParameter("nexttype");
			String recesort = request.getParameter("recesort");
			String regsort = request.getParameter("regsort");
			String remark = request.getParameter("remark");
			String isyjjitem = request.getParameter("isyjjitem");
			String sortName = request.getParameter("sortName");
			String sortOrder = request.getParameter("sortOrder");
			// 门诊连锁
			String isSelectCurrentOrg = request.getParameter("isSelectCurrentOrg");
			// 接待中心 搜索值
			String searchValue = request.getParameter("searchValue");
			String organization = null;
			if (!YZUtility.isNullorEmpty(isSelectCurrentOrg) && "1".equals(isSelectCurrentOrg)) {
				organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
			} else {
				organization = request.getParameter("organization"); // 根据页面传值进行过滤
			}

			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(organization)) {
				map.put("organization", organization);
			}
			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			if (!YZUtility.isNullorEmpty(regdept)) {
				map.put("regdept", regdept);
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
			if (!YZUtility.isNullorEmpty(nexttype)) {
				map.put("nexttype", nexttype);
			}
			if (!YZUtility.isNullorEmpty(recesort)) {
				map.put("recesort", recesort);
			}
			if (!YZUtility.isNullorEmpty(regsort)) {
				map.put("regsort", regsort);
			}
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(cjStatus)) {
				map.put("cjStatus", cjStatus);
			}
			if (!YZUtility.isNullorEmpty(regno)) {
				map.put("regno", regno);
			}
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			String zhcx_seqId = request.getParameter("zhcx_seqId");
			if (!YZUtility.isNullorEmpty(zhcx_seqId)) {
				map.put("seq_id", zhcx_seqId);
			}
			String queryinput = request.getParameter("queryinput");
			if (!YZUtility.isNullorEmpty(queryinput)) {
				map.put("queryinput", queryinput);
			}
			if (!YZUtility.isNullorEmpty(remark)) {
				map.put("remark", remark);
			}
			if (!YZUtility.isNullorEmpty(searchValue)) {
				map.put("searchValue", searchValue);
			}
			if (!YZUtility.isNullorEmpty(isyjjitem)) {
				map.put("isyjjitem", isyjjitem);
			}
			if(!YZUtility.isNullorEmpty(sortName)){
				map.put("sortName", sortName);
				map.put("sortOrder", sortOrder);
			}
			String visualstaff = request.getSession().getAttribute(SessionUtil.visualstaff).toString();

			/*-------导出excel---------*/
			List<JSONObject> list = new ArrayList<JSONObject>();
			if (flag != null && flag.equals("exportTable")) {
				JSONObject data = logic.selectNoPage(bp, person, map, visualstaff, querytype);
				JSONObject recordData = recordLogic.selectListRefund(bp, person, map, visualstaff, querytype);
				List<JSONObject> listorder = (List<JSONObject>) data.getJSONArray("rows");
				List<JSONObject> listmember = (List<JSONObject>) recordData.getJSONArray("rows");
				list.addAll(listorder);
				list.addAll(listmember);
				if (list.size()>0) {
					ExportBean bean = ExportTable.initExcel4RsWrite("费用查询", fieldArr, fieldnameArr, response, request);
					bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, list, "costOrder_selectNoPage");
					ExportTable.writeExcel4DownLoad("费用查询", bean.getWorkbook(), response);
				}
				return null;
			}
			
			JSONObject data = logic.selectNoPage(bp, person, map, visualstaff, querytype);
			JSONObject recordData = recordLogic.selectListRefund(bp, person, map, visualstaff, querytype);
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
				BigDecimal totalcost = BigDecimal.ZERO;//小计:
				BigDecimal voidmoney = BigDecimal.ZERO;//免除:
				BigDecimal shouldmoney = BigDecimal.ZERO;//应收:
				BigDecimal actualmoney = BigDecimal.ZERO;//缴费:
				BigDecimal y2 = BigDecimal.ZERO;//欠款:
				BigDecimal yjjshiyong = BigDecimal.ZERO;//预交金使用:
				BigDecimal yjjchongzhi = BigDecimal.ZERO;//预交金充值:
				BigDecimal cmoney = BigDecimal.ZERO;//预交金退费:
				if (orderTotal> 0) {
					totalcost = totalcost.add(new BigDecimal(data.getString("totalcost")));
					voidmoney = voidmoney.add(new BigDecimal(data.getString("voidmoney")));
					shouldmoney = shouldmoney.add(new BigDecimal(data.getString("shouldmoney")));
					actualmoney = actualmoney.add(new BigDecimal(data.getString("actualmoney")));
					y2 = y2.add(new BigDecimal(data.getString("y2")));
					yjjshiyong = yjjshiyong.add(new BigDecimal(data.getString("yjjshiyong")));
					yjjchongzhi = yjjchongzhi.add(new BigDecimal(data.getString("yjjchongzhi")));

				}
				if (memberTotal> 0) {
					cmoney = cmoney.add(new BigDecimal(recordData.getString("cmoney")));
					totalcost = totalcost.add(new BigDecimal(recordData.getString("cmoney")));
					shouldmoney = shouldmoney.add(new BigDecimal(recordData.getString("cmoney")));
					actualmoney = actualmoney.add(new BigDecimal(recordData.getString("cmoney")));
				}
				jobjall.put("totalcost", totalcost);
				jobjall.put("voidmoney", voidmoney);
				jobjall.put("shouldmoney", shouldmoney);
				jobjall.put("actualmoney", actualmoney);
				jobjall.put("y2", y2);
				jobjall.put("yjjshiyong", yjjshiyong);
				jobjall.put("yjjchongzhi", yjjchongzhi);
				jobjall.put("cmoney", cmoney);
			}
			YZUtility.DEAL_SUCCESS(jobjall, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 患者总消费查询（首页工作量，报表中心欠费查询） 【查询所有，不做门诊条件过滤】
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
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String askperson = request.getParameter("askperson");
			String createuser = request.getParameter("createuser");
			String devchannel = request.getParameter("devchannel");
			String nexttype = request.getParameter("nexttype");
			String sortName = request.getParameter("sortName");
			String sortOrder = request.getParameter("sortOrder");
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			Map<String, String> map = new HashMap<String, String>();

			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			if (!YZUtility.isNullorEmpty(createuser)) {
				map.put("createuser", createuser);
			}
			if (!YZUtility.isNullorEmpty(devchannel)) {
				map.put("devchannel", devchannel);
			}
			if (!YZUtility.isNullorEmpty(nexttype)) {
				map.put("nexttype", nexttype);
			}
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_END;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			String zhcx_seqId = request.getParameter("zhcx_seqId");
			if (!YZUtility.isNullorEmpty(zhcx_seqId)) {
				map.put("seq_id", zhcx_seqId);
			}
			String queryinput = request.getParameter("queryinput");
			if (!YZUtility.isNullorEmpty(queryinput)) {
				map.put("queryinput", queryinput);
			}
			if (!YZUtility.isNullorEmpty(sortName)) {
				map.put("sortName", sortName);
				map.put("sortOrder", sortOrder);
			}
			
			// 是否分页
			//String ispaging = request.getParameter("ispaging");
			// 是否分页 1 分页 0 不分页
//			if (!YZUtility.isNullorEmpty(ispaging) && YZUtility.isNullorEmpty(flag)) {
//				map.put("ispaging", ispaging);
//			} else {
//				map.put("ispaging", ConstUtil.PAGE_FLAG_0);
//			}
			String visualstaff = SessionUtil.getVisualstaff(request);
			map.put("visualstaff", visualstaff);
			map.put("organization", ChainUtil.getOrganizationFromUrl(request));
			JSONObject data = logic.qfSearch(bp, person, map, request);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("欠费查询", fieldArr, fieldnameArr, (List<JSONObject>) data.getJSONArray("rows"), response, request);
				return null;
			}
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
//			if (!YZUtility.isNullorEmpty(ispaging)) {
//			} else {
//				YZUtility.RETURN_LIST((List<JSONObject>) data.getJSONArray("rows"), response, logger);
//			}
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
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
			String organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
			List<YZPerson> listPserson = personLogic.getPersonListByDeptIds("015d89d6-7b32-47ec-9557-233407c7fc71,72d1324f-22a2-41a7-9739-8b64c50e7b97,3b47a915-977b-4799-acf6-540d525722f4,b4f9dc9e-d2e0-44e1-ba37-eecaddcbf93d,4b88b74c-9373-4b5f-9d53-3c115de7a7e4,d915e83c-862e-40eb-a4a0-792e34db701e", "0", organization);//咨询
			KqdsCostorder dp = new KqdsCostorder();
			BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
			BeanUtils.populate(dp, request.getParameterMap());
//			if ((dp.getIsdrugs()).equals("1")) {
//				for (int i = 0; i < listPserson.size(); i++) {
//					if ((listPserson.get(i).getUserName()).equals(person.getUserName())) {
//						throw new Exception("对不起，您没有权限开药品费用单！");
//					}
//				}
//			}
			String seqId = request.getParameter("seqId");
			String repairdoctor = request.getParameter("repair");//修复医生
			// String type = request.getParameter("type"); // 0 新增收费单，1 修改
			// String qfId = request.getParameter("qfId");// 欠费Id
			String askperson = request.getParameter("askperson");// 咨询
			String deleteitem = request.getParameter("deleteitem");// 修改费用单时删除的
			String uuid = logic.insertOrUpdate(dp, person, askperson, deleteitem, seqId, request, listPserson, repairdoctor);
			JSONObject jobj = new JSONObject();
			jobj.put("data", uuid);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询是否存在欠款 【查询所有，不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getQk.act")
	public String getQk(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		Map<String, String> map = new HashMap<String, String>();
		map.put("usercode", usercode);
		// map.put("status", ConstUtil.COST_DETAIL_STATUS_1);// 查询该患者 是否存在欠费项目
		map.put("isqfreal", ConstUtil.QF_STATUS_1 + "");// 查询该患者 是否存在欠费项目
		List<KqdsCostorderDetail> en = (List<KqdsCostorderDetail>) logic.loadList(TableNameUtil.KQDS_COSTORDER_DETAIL, map);
		YZUtility.RETURN_LIST(en, response, logger);
		return null;
	}

	/**
	 * 查看该患者是否存在已确认划价 未缴费的记录 存在 则不能开新单子 【查询所有，不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectNopay.act")
	public String selectNopay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			Map<String, String> map = new HashMap<String, String>();
			map.put("usercode", usercode);
			map.put("status", ConstUtil.COST_ORDER_STATUS_1); // 等待结账
			List<KqdsCostorder> en = (List<KqdsCostorder>) logic.loadList(TableNameUtil.KQDS_COSTORDER, map);
			JSONObject jobj = new JSONObject();
			jobj.put("data", en);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
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
			logic.deleteObj(seqId, request);

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 费用单详情
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
			KqdsCostorder en = (KqdsCostorder) logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, seqId);
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
	 * 根据regno 查询 开单项目 【查询所有，不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectByregno.act")
	public String selectByregno(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String regno = request.getParameter("regno");
			Map<String, String> map = new HashMap<String, String>();
			map.put("regno", regno);
			List<KqdsCostorder> en = (List<KqdsCostorder>) logic.loadList(TableNameUtil.KQDS_COSTORDER, map);
			List<KqdsCostorder> en2 = new ArrayList<KqdsCostorder>();
			for (KqdsCostorder order : en) {
				if (ConstUtil.COST_ORDER_STATUS_1.equals(order.getStatus()) || ConstUtil.COST_ORDER_STATUS_2.equals(order.getStatus())) {
					en2.add(order);
				}
			}
			JSONObject jobj = new JSONObject();
			jobj.put("data", en2);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 接待中心-等待结账列表调用 【只查询当前所在门诊，Organization直接从登录用户的session中获取】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListByStatus.act")
	public String getListByStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String status = request.getParameter("status");
			String querytype = request.getParameter("querytype");
			String searchValue = request.getParameter("searchValue");
			Map<String, String> map = new HashMap<String, String>();
			map.put("status", status);
			if (!YZUtility.isNullorEmpty(searchValue)) {
				map.put("searchValue", searchValue);
			}
			String visualstaff = SessionUtil.getVisualstaff(request);
			// 当前所在门诊
			String organization = ChainUtil.getCurrentOrganization(request);
			map.put("organization", organization);
			List<JSONObject> list = logic.getListByStatus(map, person, querytype, visualstaff, request);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据regno获取费用记录 【查询所有，不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByRegno.act")
	public String getByRegno(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String regno = request.getParameter("regno");
			Map<String, String> map = new HashMap<String, String>();
			map.put("regno", regno);
			List<KqdsCostorder> en = (List<KqdsCostorder>) logic.loadList(TableNameUtil.KQDS_COSTORDER, map);
			JSONObject jobj = new JSONObject();
			jobj.put("data", en.get(0));
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 费用列表 【查询所有，不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByRegnoNopage.act")
	public String getByRegnoNopage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String regno = request.getParameter("regno");
			String usercode = request.getParameter("usercode");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String cjStatus = request.getParameter("cjStatus");
			String status = request.getParameter("status");
			String qf = request.getParameter("qf");
			String searchValue = request.getParameter("searchValue");
			String istk = request.getParameter("istk");
			String access = request.getParameter("access");
			String isyjjitem = request.getParameter("isyjjitem");

			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(starttime)) {
				starttime = starttime + ConstUtil.TIME_START;
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				endtime = endtime + ConstUtil.TIME_END;
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(cjStatus)) {
				map.put("cjStatus", cjStatus);
			}
			if (!YZUtility.isNullorEmpty(status)) {
				map.put("status", status);
			}
			if (!YZUtility.isNullorEmpty(qf)) {
				if (qf.equals("1")) {// 欠费
					map.put("noqf", qf);
				} else {
					map.put("isqf", qf);
				}
			}
			if (!YZUtility.isNullorEmpty(regno)) {
				map.put("regno", regno);
			}
			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			}
			String zhcx_seqId = request.getParameter("zhcx_seqId");
			if (!YZUtility.isNullorEmpty(zhcx_seqId)) {
				map.put("seq_id", zhcx_seqId);
			}
			String queryinput = request.getParameter("queryinput");
			if (!YZUtility.isNullorEmpty(queryinput)) {
				map.put("queryinput", queryinput);
			}
			if (!YZUtility.isNullorEmpty(searchValue)) {
				map.put("searchValue", searchValue);
			}
			if (!YZUtility.isNullorEmpty(istk)) {
				if (istk.equals(ConstUtil.COST_IS_TK_0)) {// 不是退款
					map.put("istk", "and " + SQLUtil.convertDecimal("cost.shouldmoney", 18, 2) + " >=0");
				} else if (istk.equals(ConstUtil.COST_IS_TK_1)) {
					map.put("istk", " and " + SQLUtil.convertDecimal("cost.shouldmoney", 18, 2) + " <0");
				}
			}
			if (!YZUtility.isNullorEmpty(access) && !access.equals("1")) {// 不需要可见人员过滤，查询全部费用
																			// access=1
				map.put("access", access);
			}
			if (!YZUtility.isNullorEmpty(isyjjitem)) {
				map.put("isyjjitem", isyjjitem);
			}
			String visualstaff = SessionUtil.getVisualstaff(request);
			List<JSONObject> list = logic.selectWithPageNopage(TableNameUtil.KQDS_COSTORDER, map, visualstaff);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}
	
	// 结账时修改缴费金额
	@RequestMapping(value = "/editPaymoney.act")
	public String editPaymoney(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String costno = request.getParameter("costno");
			String detailid = request.getParameter("detailid");
			String oldPaymoney = YZUtility.isNullorEmpty(request.getParameter("oldpaymoney")) ? "0" : request.getParameter("oldpaymoney");
			String newPaymoney = YZUtility.isNullorEmpty(request.getParameter("paymoney")) ? "0" : request.getParameter("paymoney");
			String newArrearmoney = YZUtility.isNullorEmpty(request.getParameter("arrearmoney")) ? "0" : request.getParameter("arrearmoney");
			String remark = request.getParameter("remark");
			KqdsCostorder cost = (KqdsCostorder) logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, costno);
			BigDecimal newActualMoney = KqdsBigDecimal.sub(cost.getActualmoney(), new BigDecimal(oldPaymoney)); // 新的缴费金额=原缴费-旧的项目缴费
																												// +
																												// 现在的项目缴费
			newActualMoney = KqdsBigDecimal.add(newActualMoney, new BigDecimal(newPaymoney));
			cost.setActualmoney(newActualMoney);
			// cost.setTotalarrmoney(totalarrmoneyAll);
			BigDecimal arrearmoneyAll = KqdsBigDecimal.sub(cost.getShouldmoney(), newActualMoney); // 欠费=应收-缴费
			cost.setArrearmoney(arrearmoneyAll);

			KqdsCostorderDetail detail = (KqdsCostorderDetail) logic.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER_DETAIL, detailid);
			detail.setRemark(remark);
			detail.setArrearmoney(new BigDecimal(newArrearmoney));
			detail.setPaymoney(new BigDecimal(newPaymoney));
			BigDecimal olddetailY2Str = detail.getY2(); // 明细的old y2 要先取出来
			// 新增、修改费用单时，更新消费项目的相关信息
			BigDecimal itemQF = logic.dealCostItem(detail, request);
			// 原order Y2 减去 原detail Y2 加 现detail Y2
			BigDecimal oldorderY2 = KqdsBigDecimal.sub(cost.getY2(), olddetailY2Str);
			cost.setY2(KqdsBigDecimal.add(oldorderY2, itemQF));
			logic.editPaymoney(cost, detail);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCodeAndRegno(BcjlUtil.MODIFY_MONEY, BcjlUtil.KQDS_COSTORDER_DETAIL, detail, detail.getUsercode(), detail.getRegno(), true,
					TableNameUtil.KQDS_COSTORDER_DETAIL, request);
			BcjlUtil.LogBcjlWithUserCodeAndRegno(BcjlUtil.MODIFY_MONEY, BcjlUtil.KQDS_COSTORDER, cost, cost.getUsercode(), cost.getRegno(), true, TableNameUtil.KQDS_COSTORDER,
					request);
			JSONObject jobj = new JSONObject();
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	/**
	 * 2019-08-17 lwg
	 * 添加价目表数据及添加标签
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unused"})
	@RequestMapping(value="/savePriceList.act")
	public String savePriceList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String parentid=request.getParameter("parentid");
			String seqid=request.getParameter("seqid");
			String priveListDetails = request.getParameter("paramDetail");
			String userName=request.getParameter("userName");
			String usercode=request.getParameter("usercode");
			String status=request.getParameter("status");
			String remark=request.getParameter("remark");
			//添加关联表数据
			KqdsHzLabelAssociated kqdsHzLabelAssociated = new KqdsHzLabelAssociated();
			KqdsHzLabelAssociated kqdsHzLabelAssociated1 = new KqdsHzLabelAssociated();
			//根据患者id查询关联表id 有则修改无则新增
			Map<String, String> map = new HashMap<String,String>();
			map.put("usercode", usercode);
			map.put("status", status);
			String hzLabelAssciatedSeqId=hzLabelAssociatedLogic.selectKqdsHzLabelAssociatedByUserId(map);
			if(!YZUtility.isNullorEmpty(hzLabelAssciatedSeqId)){
				//批量更改关联表状态
				kqdsHzLabelAssociated1.setSeqId(hzLabelAssciatedSeqId);
				kqdsHzLabelAssociated1.setUpdateTime(YZUtility.getCurDateTimeStr());
				kqdsHzLabelAssociated1.setModifier(person.getSeqId());
				int j=hzLabelAssociatedLogic.updateKqdsHzLabelAssociated(kqdsHzLabelAssociated1);
				//根据parentid修改费用状态
				KqdsCostorderPriceList kqdsCostorderPriceList=new KqdsCostorderPriceList();
				kqdsCostorderPriceList.setModifier(person.getSeqId());
				kqdsCostorderPriceList.setUpdateTime(YZUtility.getCurDateTimeStr());
				kqdsCostorderPriceList.setParentId(hzLabelAssciatedSeqId);
				int x=priceListLogic.updatePriceList(kqdsCostorderPriceList);
			}
			hzLabelAssciatedSeqId=YZUtility.getUUID();
			kqdsHzLabelAssociated.setSeqId(hzLabelAssciatedSeqId);
			kqdsHzLabelAssociated.setLabeId(seqid);
			kqdsHzLabelAssociated.setUsercode(usercode);//患者编号
			kqdsHzLabelAssociated.setUserName(userName);//患者姓名
			kqdsHzLabelAssociated.setCreateTime(YZUtility.getCurDateTimeStr());
			kqdsHzLabelAssociated.setCreateUser(person.getSeqId());
			kqdsHzLabelAssociated.setRemark(remark);
			kqdsHzLabelAssociated.setStatus(Integer.valueOf(status));
			kqdsHzLabelAssociated.setIsdelete(0);
			int j=hzLabelAssociatedLogic.insertKqdsHzLabelAssociated(kqdsHzLabelAssociated);
			if(j>0){
				//添加费用明细数据
				priveListDetails = java.net.URLDecoder.decode(priveListDetails, "UTF-8");
				List<KqdsCostorderPriceList> list = HUDHUtil.parseJsonToObjectList(priveListDetails, KqdsCostorderPriceList.class);
				for (KqdsCostorderPriceList kqdsCostorderPriceList : list) {
					kqdsCostorderPriceList.setCreateuser(person.getSeqId());
					kqdsCostorderPriceList.setCreatetime(YZUtility.getCurDateTimeStr());
					kqdsCostorderPriceList.setSeqId(YZUtility.getUUID());
					kqdsCostorderPriceList.setParentId(hzLabelAssciatedSeqId);
					kqdsCostorderPriceList.setIsdelete(0);
					kqdsCostorderPriceList.setUsercode(usercode);
				}
				priceListLogic.insertPriceList(list);
				JSONObject jobj = new JSONObject();
				YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, logger);
		}
		return null;
	}
	/**
	 * 根据患者标签信息查询价目表详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/selectPriceListByLabeId.act")
	public ModelAndView selectPriceListByLabeId(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//价目表明细
		//String priveListDetails=request.getParameter("developArr");
		//患者编号
		String usercode=request.getParameter("usercode");
		//父类标签id
		String parentId=request.getParameter("parentId");
		//父类标签名称
		String parentName=request.getParameter("parentName");
		//标签id
		String labeId=request.getParameter("labeId");
		//状态码 标识项目
		String status=request.getParameter("status");
		//父页面layer弹窗索引    2019/8/25 lutian
		String frameSelfindex=request.getParameter("frameSelfindex");
		//三级标签页面layer弹窗索引    2019/8/27 lutian
		String labelLayerIndex=request.getParameter("labelLayerIndex");
//		if(priveListDetails.indexOf("[")==0){
//			priveListDetails=priveListDetails.substring(2,priveListDetails.length()-2);
//		}
		ModelAndView mv = new ModelAndView();
		Map<String,String> map = new HashMap<String,String>(); 
			if(!YZUtility.isNullorEmpty(usercode)){
				map.put("usercode", usercode);
			}
			if(!YZUtility.isNullorEmpty(labeId)){
				map.put("labeId", labeId);
			}
			if(!YZUtility.isNullorEmpty(status)){
				map.put("status", status);
			}
			//根据患者编号和标签id查询费用明细
			if(!YZUtility.isNullorEmpty(usercode)&&!YZUtility.isNullorEmpty(labeId)){
				List<JSONObject> list = hzLabelAssociatedLogic.selectPriceListByLabeId(map);
				JSONArray jsonArray = JSONArray.fromObject(list);
				if(list!=null&&list.size()>0){
					mv.addObject("list", jsonArray);
				}
			}else{
				mv.addObject("list", null);
			}
//		}else{
//			priveListDetails = java.net.URLDecoder.decode(priveListDetails, "UTF-8");
//			List<KqdsCostorderPriceList> list = HUDHUtil.parseJsonToObjectList(priveListDetails, KqdsCostorderPriceList.class);
//			JSONArray jsonArray = JSONArray.fromObject(list);
//			if(list!=null&&list.size()>0){
//				mv.addObject("list", jsonArray);
//			}
//		} 
		mv.addObject("parentId",parentId);
		mv.addObject("parentName", parentName);
		mv.addObject("status",status);
		mv.addObject("frameSelfindex",frameSelfindex);//父页面layer弹窗索引    2019/8/25 lutian
		mv.addObject("labelLayerIndex",labelLayerIndex);//三级标签页面layer弹窗索引    2019/8/27 lutian
		mv.setViewName("/kqdsFront/coatOrder/add_cost_price.jsp");
		return mv;
	}
	/**
	 * 根据患者标签信息查询价目表详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/selectPriceListByLabeIdTwo.act")
	public ModelAndView selectPriceListByLabeIdTwo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//价目表明细
		//String priveListDetails=request.getParameter("developArr");
		//患者编号
		String usercode=request.getParameter("usercode");
		//父类标签id
		String parentId=request.getParameter("parentId");
		//父类标签名称
		String parentName=request.getParameter("parentName");
		//标签id
		String labeId=request.getParameter("labeId");
		//状态码 标识项目
		String status=request.getParameter("status");
		//三级标签页面layer弹窗索引    2019/8/27 lutian
		String labelLayerIndex=request.getParameter("labelLayerIndex");
//		if(priveListDetails.indexOf("[")==0){
//			priveListDetails=priveListDetails.substring(2,priveListDetails.length()-2);
//		}
		ModelAndView mv = new ModelAndView();
		Map<String,String> map = new HashMap<String,String>(); 
			if(!YZUtility.isNullorEmpty(usercode)){
				map.put("usercode", usercode);
			}
			if(!YZUtility.isNullorEmpty(labeId)){
				map.put("labeId", labeId);
			}
			if(!YZUtility.isNullorEmpty(status)){
				map.put("status", status);
			}
			//根据患者编号和标签id查询费用明细
			if(!YZUtility.isNullorEmpty(usercode)&&!YZUtility.isNullorEmpty(labeId)){
				List<JSONObject> list = hzLabelAssociatedLogic.selectPriceListByLabeId(map);
				JSONArray jsonArray = JSONArray.fromObject(list);
				if(list!=null&&list.size()>0){
					mv.addObject("list", jsonArray);
				}
			}else{
				mv.addObject("list", null);
			}
//		}else{
//			priveListDetails = java.net.URLDecoder.decode(priveListDetails, "UTF-8");
//			List<KqdsCostorderPriceList> list = HUDHUtil.parseJsonToObjectList(priveListDetails, KqdsCostorderPriceList.class);
//			JSONArray jsonArray = JSONArray.fromObject(list);
//			if(list!=null&&list.size()>0){
//				mv.addObject("list", jsonArray);
//			}
//		} 
		mv.addObject("parentId",parentId);
		mv.addObject("parentName", parentName);
		mv.addObject("status",status);
		mv.addObject("labelLayerIndex",labelLayerIndex);//三级标签页面layer弹窗索引    2019/8/27 lutian
		mv.setViewName("/kqdsFront/coatOrder/add_cost_price2.jsp");
		return mv;
	}
	
	
}