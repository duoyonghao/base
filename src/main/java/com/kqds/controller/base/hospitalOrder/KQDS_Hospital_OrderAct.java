package com.kqds.controller.base.hospitalOrder;

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

import com.hudh.lclj.service.ILcljOperationNodeInforService;
import com.kqds.core.global.YZActionKeys;
import com.kqds.core.global.YZConst;
import com.kqds.entity.base.KqdsHospitalOrder;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.hospitalOrder.KQDS_Hospital_OrderLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.sys.SysParaUtil;

import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
@Controller
@RequestMapping("KQDS_Hospital_OrderAct")
public class KQDS_Hospital_OrderAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Hospital_OrderAct.class);
	@Autowired
	private KQDS_Hospital_OrderLogic logic;
	@Autowired
	private YZDictLogic dictLogic;
	@Autowired
	private YZPersonLogic personLogic;
	@Autowired
	private YZDeptLogic deptLogic;
	@Autowired
	private KQDS_REGLogic regLogic;
	
	@Autowired
	private ILcljOperationNodeInforService inforService;//临床路径节点业务数据service  2019-7-26 syp

	/**
	 * 查询当天最新的门诊预约，根据患者编号
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCurrDatetHosOrderInfo.act")
	public String getCurrDatetHosOrderInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			if (YZUtility.isNullorEmpty(usercode)) {
				throw new Exception("患者编号不能为空");
			}
			JSONObject hosorder = logic.getTopHosOrderCurrDay(usercode);// 获取患者当天最晚的预约记录
			JSONObject jobj = new JSONObject();
			jobj.put("data", hosorder);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
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
			/**
			 * 2019-7-26 syp start
			 * 临床路径手术预约时在预约表存路径编号和节点id实现预约时间同步改变
			 */
			String order_number = request.getParameter("order_number");//临床路径手术预约时在预约表存路径编号和节点id实现预约时间同步改变
			String nodeId = request.getParameter("nodeId");
			String orderTime = request.getParameter("starttime");//修改过后的预约时间
			/**
			 * end
			 */
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsHospitalOrder dp = new KqdsHospitalOrder();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dp.setStarttime(formatter.format(formatter.parse(dp.getStarttime())));
			dp.setEndtime(formatter.format(formatter.parse(dp.getEndtime())));

			String organization = dp.getOrganization();
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}

			// 测试过程中出现 存的usercode和Username匹配的情况，这里增加校验
			// ##############################################
			if (YZUtility.isNullorEmpty(dp.getUsercode())) {
				throw new Exception("usercode值不能为空");
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("usercode", dp.getUsercode());
			// map.put("isdelete", "0");
			List<KqdsUserdocument> en = (List<KqdsUserdocument>) logic.loadList(TableNameUtil.KQDS_USERDOCUMENT, map);

			if (en == null || en.size() == 0) {
				throw new Exception("患者不存在，usercode值为：" + dp.getUsercode());
			}

			// 预约医生，预约的部门要根据 预约医生来赋值！！！ 因为新建的时候，页面没有部门值传回后台
			// #############################################
			YZPerson yydoctor = (YZPerson) logic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, dp.getAskperson());

			if (yydoctor == null) {
				throw new Exception("预约的医生在系统中不存在，医生seqId：" + dp.getAskperson());
			}

			dp.setRegdept(yydoctor.getDeptId()); // 【重要！！！】

			// 目前预约页面，拖拽的时候，没有传username参数值
			if (YZUtility.isNullorEmpty(dp.getUsername())) {
				dp.setUsername(en.get(0).getUsername());
			} else {
				if (!dp.getUsername().equals(en.get(0).getUsername())) {
					throw new Exception("患者编号和患者名称不匹配，请联系系统管理员！<br>usercode：" + dp.getUsercode() + " username：" + en.get(0).getUsername());
				}
			}

			if (!YZUtility.isNullorEmpty(seqId)) {
				String startTime = dp.getStarttime();
				inforService.updateOrderTimeHospital(order_number, nodeId, startTime.substring(0, 16));//预约日历修改手术预约时间的同时在临床路径节点表中预约时间同时做修改
				logic.updateSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, dp);
				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_HOSPITAL_ORDER, dp, dp.getUsercode(), TableNameUtil.KQDS_HOSPITAL_ORDER, request);
				// 添加消息提示
				PushUtil.saveTx4ModifyHosOrder(dp, request);
			} else {
				String uuid = YZUtility.getUUID();
				dp.setSeqId(uuid);
				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setOrderno(uuid);// 预约编号
				dp.setOrderstatus(0); // 0 未上门 1 已上门
				// 根据askperson(医生) 查询科室
				dp.setOrganization(organization);// 门诊
													// 保存当前登录用户所在门诊
				dp.setRegdept(deptLogic.getDeptSeqIdByUserSeqIdAndOrganization(dp.getAskperson(),dp.getOrganization()));// 科室
				dp.setCjstatus(0);// 未成交
				List<JSONObject> list=new ArrayList<JSONObject>();
				if(!YZUtility.isNullorEmpty(order_number)&&!YZUtility.isNullorEmpty(nodeId)){
					list = logic.findHospitalOrderByOrdernumAndnodeId(order_number, nodeId);//此操作防止预约日历更改预约时间临床路径发生改变
				}
				if (list.size() == 0) {
					dp.setOrder_number(order_number);//临床路径手术预约时在预约表存路径编号和节点id实现预约时间同步改变
					dp.setNodeId(nodeId);//临床路径手术预约时在预约表存路径编号和节点id实现预约时间同步改变
				} else {
					dp.setOrder_number("");//临床路径手术预约时在预约表存路径编号和节点id实现预约时间同步改变
					dp.setNodeId("");//临床路径手术预约时在预约表存路径编号和节点id实现预约时间同步改变
				}
				Map<String, String> map2 = new HashMap<String, String>();
				String startTime = dp.getStarttime().substring(0, 10);
				map2.put("startTime", startTime+ConstUtil.TIME_START);
				map2.put("endTime", startTime+ConstUtil.TIME_END);
				map2.put("usercode", dp.getUsercode());
				map2.put("doctor", dp.getAskperson());
				//先挂号后预约
				/*JSONObject findRecord = regLogic.findRecord(map2);
				if(findRecord != null){
					dp.setOrderstatus(1);
				}*/
				logic.saveSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, dp);

				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_HOSPITAL_ORDER, dp, dp.getUsercode(), TableNameUtil.KQDS_HOSPITAL_ORDER, request);

				// 添加消息提示
				PushUtil.saveTx4NewHosOrder(dp, request);
			}

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 判断病人和医生 在选择的预约日期当天是否已存在预约 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkYy.act")
	public String checkYy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = formatter.format(formatter.parse(request.getParameter("starttime")));
			String endtime = formatter.format(formatter.parse(request.getParameter("endtime")));
			String askperson = request.getParameter("askperson");
			JSONObject jobj = new JSONObject();
			int count = logic.coutYYPerson(askperson, starttime, endtime, seqId);
			if (count > 0) {
				throw new Exception("该医生在选择的时间段中已存在预约");
			} else {
				jobj.put(YZActionKeys.JSON_RET_STATES, YZConst.RETURN_OK);
			}
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 判断病人和医生 在选择的预约日期当天是否已存在预约 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkYyUser.act")
	public String checkYyUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("seqId"); // id 不可能为空
			String usercode = request.getParameter("usercode");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = formatter.format(formatter.parse(request.getParameter("starttime")));
			String endtime = formatter.format(formatter.parse(request.getParameter("endtime")));
			String askperson = request.getParameter("askperson");
			JSONObject jobj = new JSONObject();
			List<JSONObject> list = logic.yycxUser(usercode, starttime, endtime);

			if (list == null || list.size() == 0) { // 不存在，则没有预约
				YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
				return null;
			}

			List<JSONObject> checklist = new ArrayList<JSONObject>();
			// id 不可能为空，不做非空判断
			for (JSONObject order : list) {
				if (!order.getString("seq_id").equals(seqId)) { // 不相等的时候，说明同一个时间段内有预约
					checklist.add(order);
				}
			}

			if (checklist.size() > 0) {
				String data = "该病人在选择的时间段中已存在预约<br>";
				for (JSONObject order : checklist) {
					String tmpStart = order.getString("starttime");
					String tmpEnd = order.getString("endtime");
					if (tmpStart.length() == 19) { // 2017-05-18 15:30:00
						tmpStart = tmpStart.substring(11, tmpStart.length() - 3);
					}
					if (tmpEnd.length() == 19) { // 2017-05-18 15:30:00
						tmpEnd = tmpEnd.substring(11, tmpEnd.length() - 3);
					}
					data += "预约时间：" + tmpStart + "~" + tmpEnd + "&nbsp;&nbsp;医生：" + personLogic.getNameStrBySeqIds(order.getString("askperson")) + "<br>";
				}
				throw new Exception(data);
			}
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 判断医生是否下班 或者休息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkYy2.act")
	public String checkYy2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String starttime = formatter.format(formatter.parse(request.getParameter("starttime")));
			String askperson = request.getParameter("askperson");
			Map<String, String> map = new HashMap<String, String>();
			map.put("starttime", starttime);
			map.put("askperson", askperson);

			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			map.put("organization", organization);
			// 如果大于0 说明在班 等于0说明不在班
			int count = logic.checkYy(map);

			JSONObject jobj = new JSONObject();
			jobj.put("data", count);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询该患者 当天是否存在预约
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkHz.act")
	public String checkHz(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// request.getParameter("starttime")的取值为 2017-5-18 16:0:0
			// 先将此值转换成日期对象，再格式化
			String starttime = formatter.format(formatter.parse(request.getParameter("starttime")));
			String usercode = request.getParameter("usercode");
			Map<String, String> map = new HashMap<String, String>();
			map.put("starttime", starttime);
			map.put("usercode", usercode);
			JSONObject jobj = new JSONObject();
			// 如果大于0 说明存在 等于0说明不存在
			List<JSONObject> en = logic.checkHz(map);
			String data = "";
			if (en != null) {
				jobj.put("nums", en.size());
				for (JSONObject order : en) {
					String tmpStart = order.getString("starttime");
					String tmpEnd = order.getString("endtime");
					if (tmpStart.length() == 19) { // 2017-05-18 15:30:00
						tmpStart = tmpStart.substring(11, tmpStart.length() - 3);
					}
					if (tmpEnd.length() == 19) { // 2017-05-18 15:30:00
						tmpEnd = tmpEnd.substring(11, tmpEnd.length() - 3);
					}
					data += "预约时间：" + tmpStart + "~" + tmpEnd + "&nbsp;&nbsp;医生：" + personLogic.getNameStrBySeqIds(order.getString("askperson")) + "<br>";
				}
			}
			jobj.put("data", data);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
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

			KqdsHospitalOrder en = (KqdsHospitalOrder) logic.loadObjSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, seqId);

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
			YZPerson person = SessionUtil.getLoginPerson(request);

			String seqId = request.getParameter("seqId");
			String delreason = request.getParameter("delreason");
			/**
			 * start
			 */
			String order_number = request.getParameter("order_number");//预约日历里面取消手术预约临床路径同步取消   2019-7-27  syp
			String nodeId = request.getParameter("nodeId");//预约日历里面取消手术预约临床路径同步取消   2019-7-27  syp
			/**
			 * end
			 */
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空或者null");
			}
			if (YZUtility.isNullorEmpty(delreason)) {
				throw new Exception("取消预约原因为空或者null");
			}

			KqdsHospitalOrder en = (KqdsHospitalOrder) logic.loadObjSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, seqId);
			en.setIsdelete(ConstUtil.IS_DELETE_1); // 伪删除
			en.setDelreason(delreason);
			en.setDelperson(person.getSeqId());
			inforService.cancelTimeHospital(order_number, nodeId); //预约日历里面取消手术预约临床路径同步取消   2019-7-27  syp
			logic.updateSingleUUID(TableNameUtil.KQDS_HOSPITAL_ORDER, en);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.CANCEL, BcjlUtil.KQDS_HOSPITAL_ORDER, en, en.getUsercode(), TableNameUtil.KQDS_HOSPITAL_ORDER, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询门诊预约列表，系统首页 和 接待中心-门诊预约列表调用 【只查询当前所在门诊，Organization直接从登录用户的session中获取】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectHospitalOrderList.act")
	public String selectHospitalOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sortName = request.getParameter("sortName");
		String sortOrder = request.getParameter("sortOrder");
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String querytype = request.getParameter("querytype");
			String searchValue = request.getParameter("searchValue");
			String visualstaff = SessionUtil.getVisualstaff(request);
			// 当前所在门诊
			String organization = ChainUtil.getCurrentOrganization(request);
			
			Map<String,String> map = new HashMap<String,String>();
			
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap()); 
			//List<JSONObject> json = new ArrayList<JSONObject>();
			JSONObject json = new JSONObject();
			if(sortName != null){
				map.put("sortName", sortName);
				map.put("sortOrder", sortOrder);
				json = logic.selectHospitalOrderList(TableNameUtil.KQDS_HOSPITAL_ORDER, person, querytype, searchValue, visualstaff, organization,map,bp);
			}else{				
				json = logic.selectHospitalOrderList(TableNameUtil.KQDS_HOSPITAL_ORDER, person, querytype, searchValue, visualstaff, organization,map,bp);
			}
			//YZUtility.RETURN_LIST(json, response, logger);
			YZUtility.RETURN_OBJ(json, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 预约中心-门诊预约查询 【只查当前门诊】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectNoPage.act")
	public String selectNoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sortName = request.getParameter("sortName");
		String sortOrder = request.getParameter("sortOrder");
		try {
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			// 封装参数到实体类
			Map<String, String> map = new HashMap<String, String>();

			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}

			String createtime = request.getParameter("createtime");
			if (!YZUtility.isNullorEmpty(createtime)) {
				map.put("createtime", createtime);
			}

			String regdept = request.getParameter("regdept");
			if (!YZUtility.isNullorEmpty(regdept)) {
				map.put("regdept", regdept);
			}
			String username = request.getParameter("username");
			if (!YZUtility.isNullorEmpty(username)) {
				map.put("username", username);
			}
			String askperson = request.getParameter("askperson");
			if (!YZUtility.isNullorEmpty(askperson)) {
				map.put("askperson", askperson);
			}
			String firstaskperson = request.getParameter("firstaskperson");
			if (!YZUtility.isNullorEmpty(firstaskperson)) {
				map.put("firstaskperson", firstaskperson);
			}
			String orderstatus = request.getParameter("orderstatus");
			if (!YZUtility.isNullorEmpty(orderstatus)) {
				map.put("orderstatus", orderstatus);
			}
			String starttime = request.getParameter("starttime");
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			String endtime = request.getParameter("endtime");
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			String yyxm = request.getParameter("yyxm");
			if (!YZUtility.isNullorEmpty(yyxm)) {
				map.put("yyxm", yyxm);
			}
			String isdelete = request.getParameter("isdelete");
			if (!YZUtility.isNullorEmpty(isdelete)) {
				map.put("isdelete", isdelete);
			}
			// 建档人
			String createuser = request.getParameter("createuser");
			if (!YZUtility.isNullorEmpty(createuser)) {
				map.put("createuser", createuser);
			}
			String cjr = request.getParameter("cjr");
			if (!YZUtility.isNullorEmpty(cjr)) {
				map.put("cjr", cjr);
			}
			// 查当前门诊的
			map.put("organization", organization);

			String visualstaffYyrl = request.getSession().getAttribute("visualstaffYyrl").toString();
			JSONObject json = null;
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			if(sortName!=null){
				map.put("sortName", sortName);
				map.put("sortOrder", sortOrder);
				json = logic.selectNoPage(TableNameUtil.KQDS_HOSPITAL_ORDER, map, visualstaffYyrl,bp,json);
			}else{	
				json = logic.selectNoPage(TableNameUtil.KQDS_HOSPITAL_ORDER, map, visualstaffYyrl,bp,json);
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
	 * 预约中心-门诊预约查询 【只查当前门诊】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectYyxxByUsercode.act")
	public String selectYyxxByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String usercode = request.getParameter("usercode");
			if (YZUtility.isNullorEmpty(usercode)) {
				throw new Exception("患者编号不能为空");
			}
			List<JSONObject> list = logic.selectYyxxByUsercode(TableNameUtil.KQDS_HOSPITAL_ORDER, usercode);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

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
			YZPerson person = SessionUtil.getLoginPerson(request);

			// 前台需要读取某范围的数据时，通过数据库数据查询，将该段时间的日历保存到一个xml文件中。
			Map<String, String> map = new HashMap<String, String>();
			String yyxm = request.getParameter("yyxm");// 预约项目
			String doctors = request.getParameter("doctors");// 医生集合
			String regdept = request.getParameter("regdept");// 科室
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			starttime = sdf.format(new Date(Long.valueOf(starttime)));
			endtime = sdf.format(new Date(Long.valueOf(endtime)));
			if (!YZUtility.isNullorEmpty(starttime) && !starttime.equals("null")) {
				map.put("starttime", starttime);
			}
			if (!YZUtility.isNullorEmpty(endtime) && !endtime.equals("null")) {
				map.put("endtime", endtime);
			}
			if (!YZUtility.isNullorEmpty(yyxm) && !yyxm.equals("null")) {
				map.put("orderitemtype", yyxm);
			}
			if (!YZUtility.isNullorEmpty(doctors) && !doctors.equals("null")) {
				map.put("askperson", doctors);
			}
			if (!YZUtility.isNullorEmpty(regdept) && !regdept.equals("null")) {
				map.put("regdept", regdept);
			}
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			String visualstaffYyrl = request.getSession().getAttribute("visualstaffYyrl").toString();
			List<JSONObject> list = logic.selectList(TableNameUtil.KQDS_HOSPITAL_ORDER, map, visualstaffYyrl, organization);
			for (int i = 0; i < list.size(); i++) {
				JSONObject hd = list.get(i);
				String orderitemtype = hd.getString("orderitemtypename");
				String createusername = hd.getString("createusername");
				String askname = hd.getString("askpersonname");
				String text = "<span style='color:black'>患者：" + hd.getString("username") + "<br>医生：" + askname + "" + "<br>项目分类：" + orderitemtype + "</span>";

				hd.put("text", text);
				hd.put("createusername", createusername);
				hd.put("id", hd.getString("seq_id"));
				hd.put("start_date", hd.getString("starttime"));
				hd.put("end_date", hd.getString("endtime"));
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
	 * 根据查询 条件 获取医生列表 第一次进一面 默认展示 权限下 所有有预约的人 ## 这里要考虑下，如果是非门诊部门的人员进入当前页面，具体应该怎么处理
	 * 17-6-21
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersonBydeptId.act")
	public String getPersonBydeptId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);

			// 前台需要读取某范围的数据时，通过数据库数据查询，将该段时间的日历保存到一个xml文件中。
			Map<String, String> map = new HashMap<String, String>();

			String deptId = request.getParameter("deptId");// 预约项目
			String seqId = request.getParameter("seqId");// 预约项目
			String yydate = request.getParameter("yydate");// 时间
			if (!YZUtility.isNullorEmpty(yydate)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				yydate = sdf.format(new Date(Long.valueOf(yydate)));
			}
			if (!YZUtility.isNullorEmpty(deptId)) {
				map.put("dept_id", deptId);
			}

			// #### 这里注意，如果不传部门ID的话，默认查询当前操作人所在的部门ID，会出现查询不到结果的情况
			if (YZUtility.isNullorEmpty(deptId) && (YZUtility.isNullorEmpty(seqId) || seqId.equals("null"))) {
				map.put("sdate", person.getSeqId());
				if (!YZUtility.isNullorEmpty(yydate)) {
					map.put("yydate", yydate);
				}
			}

			if (!YZUtility.isNullorEmpty(seqId) && !seqId.equals("null")) {
				map.put("seq_id", seqId);
			}
			String visualstaffYyrl = request.getSession().getAttribute("visualstaffYyrl").toString();

			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			List<String> perSeqIdList = new ArrayList<String>();
			List<JSONObject> list = logic.selectPerson(map, organization, visualstaffYyrl);
			String IS_OPEN_PAIBAN_ORDER_FUNC = SysParaUtil.getSysValueByName(request, SysParaUtil.IS_OPEN_PAIBAN_ORDER_FUNC);
			if (IS_OPEN_PAIBAN_ORDER_FUNC.equals("1")) {
				Map<String, String> map2 = new HashMap<String, String>();
				if (!YZUtility.isNullorEmpty(yydate)) {
					map2.put("starttime", yydate);
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					yydate = sdf.format(new Date());
					map2.put("starttime", yydate);
				}

				for (JSONObject perjson : list) {

					map2.put("askperson", perjson.getString("seqId"));
					map2.put("organization", organization);
					// 如果大于0 说明在班 等于0说明不在班
					int count = logic.checkYy(map2);
					// 排班人员
					JSONObject jobjpb = logic.orderstatusString(map2);
					// 没排班算休息
					if (jobjpb.isEmpty()) {
						jobjpb.put("personid", perjson.getString("seqId"));
						jobjpb.put("orderstatus", "");
					}
					// 值班 蓝色 休息红色
					String color = "blue";
					if (count == 0) {
						perSeqIdList.add(perjson.getString("seqId")); // 存储休息人员的seqId
						if (jobjpb.getString("personid").equals(perjson.getString("seqId"))) {
							color = "red";
						}
					}

					String orderstatus = jobjpb.getString("orderstatus");
					if (!YZUtility.isNullorEmpty(orderstatus)) {
						perjson.put("userName", perjson.getString("userName") + "<span style='color:" + color + ";font-size: 10px;'>【" + orderstatus + "】</span>");
					} else {
						perjson.put("userName", perjson.getString("userName"));
					}
				}
			}
			JSONObject jobj = new JSONObject();
			jobj.put("result", "ok");
			jobj.put("list", list);
			jobj.put("perSeqIdList", perSeqIdList);
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
			String ordertype = request.getParameter("ordertype");
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			// 获取starttime endtime之间的日期
			String[] datearray = YZUtility.getDateArray(starttime, endtime);
			Double[] smlarray = new Double[datearray.length];
			for (int i = 0; i < datearray.length; i++) {
				Double sml = logic.getSml(datearray[i], ordertype, ChainUtil.getCurrentOrganization(request));
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
	 * 预约上门率 表格-门诊
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
			String ordertype = request.getParameter("ordertype");
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			List<Object> list = new ArrayList<Object>();
			// 获取starttime endtime之间的日期
			String[] datearray = YZUtility.getDateArray(starttime, endtime);
			// 预约人数 数组
			int[] yyarray = new int[datearray.length];
			// 取消预约人数 数组
			int[] yyarrayqx = new int[datearray.length];
			// 实际预约人数 数组
			int[] yyarrayreal = new int[datearray.length];
			// 预约上门人数 数组
			int[] yysmarray = new int[datearray.length];
			// 上门率 数组
			String[] smlarray = new String[datearray.length];
			int yysum = 0;// 预约总人数
			int yysumqx = 0;// 取消预约总人数
			int yysumreal = 0;// 实际预约总人数
			int yysmsum = 0;// 预约总上门人数
			String yysmlsum = "";// 预约总上门率
			for (int i = 0; i < datearray.length; i++) {
				// 预约人数
				int yynum = logic.getCountYy(TableNameUtil.KQDS_HOSPITAL_ORDER, datearray[i], ordertype, "all", ChainUtil.getCurrentOrganization(request));
				yysum += yynum;
				// 取消预约人数
				int yynumqx = logic.getCountYy(TableNameUtil.KQDS_HOSPITAL_ORDER, datearray[i], ordertype, "del", ChainUtil.getCurrentOrganization(request));
				yysumqx += yynumqx;
				// 实际预约人数
				int yynumreal = yynum - yynumqx;
				yysumreal += yynumreal;
				// 预约上门人数
				int yysmnum = logic.getCountYysm(TableNameUtil.KQDS_HOSPITAL_ORDER, datearray[i], ordertype, ChainUtil.getCurrentOrganization(request));
				yysmsum += yysmnum;
				if (yysmnum == 0) {
					smlarray[i] = "0";
				} else {
					float num = ((float) yysmnum / yynumreal) * 100;
					DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
					smlarray[i] = df.format(num);
				}
				yyarray[i] = yynum;
				yyarrayqx[i] = yynumqx;
				yyarrayreal[i] = yynumreal;
				yysmarray[i] = yysmnum;
			}
			list.add(datearray);
			list.add(yyarray);
			list.add(yyarrayqx);
			list.add(yyarrayreal);
			list.add(yysmarray);
			list.add(smlarray);

			list.add(yysum);
			list.add(yysumqx);
			list.add(yysumreal);
			list.add(yysmsum);
			if (yysmsum == 0) {
				list.add("0");
			} else {
				float num = ((float) yysmsum / yysumreal) * 100;
				DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
				list.add(df.format(num));
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}

		return null;
	}
	
}