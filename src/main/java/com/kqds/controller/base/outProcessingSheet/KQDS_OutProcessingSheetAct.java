package com.kqds.controller.base.outProcessingSheet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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

import com.kqds.entity.base.KqdsOutprocessingSheet;
import com.kqds.entity.base.KqdsOutprocessingSheetDetail;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.outProcessingSheet.KQDS_OutProcessingSheetLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.base.code.JGCodeUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("KQDS_OutProcessingSheetAct")
public class KQDS_OutProcessingSheetAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_OutProcessingSheetAct.class);
	@Autowired
	private KQDS_OutProcessingSheetLogic logic;

	@RequestMapping(value = "/toJgmx.act")
	public ModelAndView toJgmx(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/jgzx/jgmx.jsp");
		return mv;
	}

	@RequestMapping(value = "/toAdd.act")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/jiagong/jg_add.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/toAddWorksheet.act")
	public ModelAndView toAddWorksheet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/jiagong/ycjg_add.jsp");
		return mv;
	}
	
	/*2019/12/23 lutian*/
	@RequestMapping(value = "/toYcjgShow.act")
	public ModelAndView toYcjgShow(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		ModelAndView mv = new ModelAndView();
		mv.addObject("id", id);
		mv.setViewName("/kqdsFront/jiagong/ycjg_show.jsp");
		return mv;
	}
	/*202/4/24 licc*/
	@RequestMapping(value = "/toUserCost.act")
	public ModelAndView toUserCost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/jiagong/userCost.jsp");
		return mv;
	}	
	/*2019/12/31 lutian*/
	@RequestMapping(value = "/toYcjgEdit.act")
	public ModelAndView toYcjgEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		ModelAndView mv = new ModelAndView();
		mv.addObject("id", id);
		mv.setViewName("/kqdsFront/jiagong/ycjg_edit.jsp");
		return mv;
	}
	/*2019/12/17 lutian*/
	@RequestMapping(value = "/toToothMap.act")
	public ModelAndView toToothMap(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.setViewName("/kqdsFront/jiagong/toothMap.jsp");
		return mv;
	}

	@RequestMapping(value = "/toEdit.act")
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String sheetno = request.getParameter("sheetno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("sheetno", sheetno);
		mv.setViewName("/kqdsFront/jiagong/jg_edit.jsp");
		return mv;
	}

	@RequestMapping(value = "/toDetail.act")
	public ModelAndView toDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String sheetno = request.getParameter("sheetno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("sheetno", sheetno);
		mv.setViewName("/kqdsFront/jiagong/jg_detail.jsp");
		return mv;
	}

	@RequestMapping(value = "/toKdxmWin.act")
	public ModelAndView toKdxmWin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercode = request.getParameter("usercode");
		String doctorno = request.getParameter("doctorno");
		ModelAndView mv = new ModelAndView();
		mv.addObject("usercode", usercode);
		mv.addObject("doctorno", doctorno);
		mv.setViewName("/kqdsFront/coatOrder/kdxmWin.jsp");
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
			KqdsOutprocessingSheet dp = new KqdsOutprocessingSheet();
			BeanUtils.populate(dp, request.getParameterMap());
			JSONObject jobj = new JSONObject();

			String seqId = request.getParameter("seqId");
			if (!YZUtility.isNullorEmpty(seqId)) {
				String type = request.getParameter("type");
				String yaoqiu = request.getParameter("yaoqiu");
				String processingfactory = request.getParameter("processingfactory");
				dp.setType(type);
				dp.setYaoqiu(yaoqiu);
				if (!YZUtility.isNullorEmpty(processingfactory)) {
					dp.setProcessingfactory(processingfactory);
				} else {
					dp.setProcessingfactory("");
				}
				if (!YZUtility.isNullorEmpty(yaoqiu)) {
					dp.setYaoqiu(yaoqiu);
				} else {
					dp.setYaoqiu("");
				}
				logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);
				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.MODIFY, BcjlUtil.KQDS_OUTPROCESSING_SHEET, dp, dp.getUsercode(), TableNameUtil.KQDS_OUTPROCESSING_SHEET, request);

				// 修改项目 (删除原来所有的 保存本次修改页面上所有的加工项目)
				Map<String, String> mapd = new HashMap<String, String>();
				mapd.put("outprocessingsheetno", dp.getSeqId());
				List<KqdsOutprocessingSheetDetail> details = (List<KqdsOutprocessingSheetDetail>) logic.loadList(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL, mapd);
				if (details.size() > 0) {
					for (KqdsOutprocessingSheetDetail d : details) {
						logic.deleteSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL, d.getSeqId());
					}
				}
				// 循环保存本次的
				String params = request.getParameter("params");
				params = java.net.URLDecoder.decode(params, "UTF-8");
				JSONArray jArray = JSONArray.fromObject(params);
				Collection collection = JSONArray.toCollection(jArray, KqdsOutprocessingSheetDetail.class);
				Iterator it = collection.iterator();
				while (it.hasNext()) {
					KqdsOutprocessingSheetDetail detail = (KqdsOutprocessingSheetDetail) it.next();
					String detailid = YZUtility.getUUID();
					detail.setSeqId(detailid);
					detail.setOutprocessingsheetno(dp.getSeqId());// 加工单号
					detail.setProcessingfactory(dp.getProcessingfactory()); // 加工厂
					detail.setCreateuser(person.getSeqId());
					detail.setCreatetime(YZUtility.getCurDateTimeStr());
					detail.setOrganization(ChainUtil.getCurrentOrganization(request));// ###
																						// 【前端调用，以当前所在门诊为主】
					logic.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL, detail);
				}

				KqdsOutprocessingSheet en = (KqdsOutprocessingSheet) logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, seqId);
				// 外加工人员负责修改加工单，修改时，提醒一横
				PushUtil.saveTx4OutProcessingSheetEdit(en, request);
			} else {

				String uuid = JGCodeUtil.getSheetNo(YZUtility.SHEET_NO_LENGTH, request);
				dp.setSeqId(uuid);
				dp.setOutprocessingsheetno(uuid);

				dp.setCreatetime(YZUtility.getCurDateTimeStr());
				dp.setCreateuser(person.getSeqId());
				dp.setDoctorno(person.getSeqId());
				dp.setOrganization(ChainUtil.getCurrentOrganization(request));
				dp.setStatus(0 + "");
				dp.setOrganization(ChainUtil.getCurrentOrganization(request));// ###
																				// 【前端调用，以当前所在门诊为主】
				logic.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);

				// 记录日志
				BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_OUTPROCESSING_SHEET, dp, dp.getUsercode(), TableNameUtil.KQDS_OUTPROCESSING_SHEET, request);

				// 循环保存项目
				String params = request.getParameter("params");
				params = java.net.URLDecoder.decode(params, "UTF-8");
				JSONArray jArray = JSONArray.fromObject(params);
				Collection collection = JSONArray.toCollection(jArray, KqdsOutprocessingSheetDetail.class);
				Iterator it = collection.iterator();
				while (it.hasNext()) {
					KqdsOutprocessingSheetDetail detail = (KqdsOutprocessingSheetDetail) it.next();
					String detailid = YZUtility.getUUID();
					detail.setSeqId(detailid);
					detail.setOutprocessingsheetno(dp.getSeqId());// 加工单号
					detail.setProcessingfactory(dp.getProcessingfactory()); // 加工厂
					detail.setCreateuser(person.getSeqId());
					detail.setCreatetime(YZUtility.getCurDateTimeStr());
					detail.setOrganization(ChainUtil.getCurrentOrganization(request));
					logic.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL, detail);
				}

				// 新建加工单，提供加工人员
				List<JSONObject> personlist = logic.getAllJiagongPerson(ChainUtil.getCurrentOrganization(request));// 查询所有加工人员
				for (int i = 0; i < personlist.size(); i++) {
					/** 提醒 **/
					PushUtil.saveTx4OutProcessingSheetNew(dp, personlist.get(i), request);
				}

				// 返回seqId
				jobj.put("sheetno", uuid);
			}

			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 修改加工单状态(前台)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatesheetstatus1.act")
	public String updatesheetstatus1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsOutprocessingSheet dp = new KqdsOutprocessingSheet();
			// BeanUtils.populate(dp,request.getParameterMap());
			String seqId = request.getParameter("seqId");
			String status = request.getParameter("status");
			dp.setSeqId(seqId);
			dp.setStatus(status);
			if (status.equals(ConstUtil.JG_STATUS_1)) {
				// 发件
				dp.setFajianperson(person.getSeqId());// 发件人
				dp.setFajiantime(YZUtility.getCurDateTimeStr());// 发件时间
				logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);
			} else if (status.equals(ConstUtil.JG_STATUS_2)) {
				dp.setHuijianperson(person.getSeqId());// 回件人
				dp.setHuijiantime(YZUtility.getCurDateTimeStr());// 回件时间
				logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);
			} else if (status.equals(ConstUtil.JG_STATUS_3)) {
				dp.setPickupperson(person.getSeqId());// 戴走人
				dp.setPickuptime(YZUtility.getCurDateTimeStr());// 戴走时间
				logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);
			} else if (status.equals(ConstUtil.JG_STATUS_4)) {
				// 返工 1 修改原加工单状态为返工
				dp.setFangongperson(person.getSeqId());// 返工人
				dp.setFangongtime(YZUtility.getCurDateTimeStr());// 返工时间
				logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);

				// 2 创建一条新的加工单 内容和原加工单相同
				String menzhen = ChainUtil.getCurrentOrganization(request);// 门诊
				// 生成加工单号
				String uuid = JGCodeUtil.getSheetNo(YZUtility.SHEET_NO_LENGTH, request);

				// 先验证单号是否已存在 存在则重新获取单号 直到不存在
				KqdsOutprocessingSheet dp1 = (KqdsOutprocessingSheet) logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, seqId);

				dp1.setSeqId(uuid);
				dp1.setOutprocessingsheetno(uuid);

				dp1.setOrganization(menzhen);
				// dp1.setDoctorno();
				// dp1.setCreateuser(person.getSeqId()); // 医生的值保持不变
				dp1.setFajianperson(null);// 发件人
				dp1.setFajiantime(null);// 发件时间
				dp1.setHuijianperson(null);// 回件人
				dp1.setHuijiantime(null);// 回件时间
				dp1.setPickupperson(null);// 戴走人
				dp1.setPickuptime(null);// 戴走时间
				dp1.setFangongperson(null);// 返工人
				dp1.setFangongtime(null);// 返工时间
				dp1.setStatus(0 + "");
				dp1.setCreatetime(YZUtility.getCurDateTimeStr());
				dp1.setOrganization(ChainUtil.getCurrentOrganization(request));// ###
																				// 【前端调用，以当前所在门诊为主】
				logic.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp1);

				// 保存新的加工单之后 复制老加工单下所有项目到新加工单
				Map<String, String> map = new HashMap<String, String>();
				map.put("outprocessingsheetno", seqId);
				List<KqdsOutprocessingSheetDetail> details = (List<KqdsOutprocessingSheetDetail>) logic.loadList(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL, map);
				if (details.size() > 0) {
					for (int i = 0; i < details.size(); i++) {
						KqdsOutprocessingSheetDetail d = details.get(i);
						String uuid2 = YZUtility.getUUID();
						d.setSeqId(uuid2);
						d.setOutprocessingsheetno(dp1.getSeqId());
						d.setOrganization(ChainUtil.getCurrentOrganization(request));// ###
						logic.saveSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET_DETAIL, d);
					}
				}
				/** 提醒 **/
				PushUtil.saveTx4OutProcessingSheetFG(dp1, request);
			} else if (status.equals(ConstUtil.JG_STATUS_5)) {
				dp.setZuofeiperson(person.getSeqId());// 作废人
				dp.setZuofeitime(YZUtility.getCurDateTimeStr());// 作废时间
				logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);
			} else {
				logic.updateSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, dp);
			}

			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE_STATUS, BcjlUtil.KQDS_OUTPROCESSING_SHEET, dp, dp.getUsercode(), TableNameUtil.KQDS_OUTPROCESSING_SHEET, request);

			// 根据状态 添加对应的加工提示消息
			if (status.equals(ConstUtil.JG_STATUS_1)) {
				// 发件时，提醒医生
				KqdsOutprocessingSheet jiagogndan = (KqdsOutprocessingSheet) logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, seqId);
				if (jiagogndan != null) {
					/** 提醒 **/
					PushUtil.saveTx4OutProcessingSheetSubmit(jiagogndan, request);
				}

			} else if (status.equals(ConstUtil.JG_STATUS_2)) {
				// 查询详情
				KqdsOutprocessingSheet en = (KqdsOutprocessingSheet) logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, seqId);
				// 加工人员回件 提示创建加工单的医生注意接收
				/** 提醒 **/
				PushUtil.saveTx4OutProcessingSheetBack(en, request);
			} else if (status.equals(ConstUtil.JG_STATUS_3)) {
				// 取走 不提示
			} else if (status.equals(ConstUtil.JG_STATUS_4)) {
				// 返工 上面的判断中添加 提示医生新创建加工单的单号
			} else if (status.equals(ConstUtil.JG_STATUS_5)) {
				// 作废不提示
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

			KqdsOutprocessingSheet en = (KqdsOutprocessingSheet) logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, seqId);
			logic.deleteSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, seqId);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.DELETE, BcjlUtil.KQDS_OUTPROCESSING_SHEET, en, en.getUsercode(), TableNameUtil.KQDS_OUTPROCESSING_SHEET, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
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

			KqdsOutprocessingSheet en = (KqdsOutprocessingSheet) logic.loadObjSingleUUID(TableNameUtil.KQDS_OUTPROCESSING_SHEET, seqId);

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
	 * 不分页查询 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectList.act")
	public String selectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			String usercode = request.getParameter("usercode");
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();

			if (!YZUtility.isNullorEmpty(usercode)) {
				map.put("usercode", usercode);
			} else {
				// 使用可见人员配置
				String visualstaff = SessionUtil.getVisualstaff(request);
				map.put("doctorno", visualstaff);
			}
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");

			String num = request.getParameter("num");
			String org = request.getParameter("org");
			String doctor = request.getParameter("doctor");
			String queryInput = request.getParameter("queryInput");
			String status = request.getParameter("status");

			String fjtime1 = request.getParameter("fjtime1");
			String fjtime2 = request.getParameter("fjtime2");
			String hjtime1 = request.getParameter("hjtime1");
			String hjtime2 = request.getParameter("hjtime2");
			String dztime1 = request.getParameter("dztime1");
			String dztime2 = request.getParameter("dztime2");
			String fgtime1 = request.getParameter("fgtime1");
			String fgtime2 = request.getParameter("fgtime2");

			String createtime1 = request.getParameter("createtime1");
			String createtime2 = request.getParameter("createtime2");

			if (num == null) {
				num = "1";// 如果num为NULL 加载改患者所有的单子
			}
			if (!YZUtility.isNullorEmpty(fjtime1) && !fjtime1.equals("null") && !fjtime1.equals("undefined")) {
				map.put("fjtime1", fjtime1 + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(fjtime2) && !fjtime2.equals("null") && !fjtime2.equals("undefined")) {
				map.put("fjtime2", fjtime2 + ConstUtil.TIME_END);
			}

			if (!YZUtility.isNullorEmpty(hjtime1) && !hjtime1.equals("null") && !hjtime1.equals("undefined")) {
				map.put("hjtime1", hjtime1 + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(hjtime2) && !hjtime2.equals("null") && !hjtime2.equals("undefined")) {
				map.put("hjtime2", hjtime2 + ConstUtil.TIME_END);
			}

			if (!YZUtility.isNullorEmpty(dztime1) && !dztime1.equals("null") && !dztime1.equals("undefined")) {
				map.put("dztime1", dztime1 + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(dztime2) && !dztime2.equals("null") && !dztime2.equals("undefined")) {
				map.put("dztime2", dztime2 + ConstUtil.TIME_END);
			}

			if (!YZUtility.isNullorEmpty(fgtime1) && !fgtime1.equals("null") && !fgtime1.equals("undefined")) {
				map.put("fgtime1", fgtime1 + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(fgtime2) && !fgtime2.equals("null") && !fgtime2.equals("undefined")) {
				map.put("fgtime2", fgtime2 + ConstUtil.TIME_END);
			}

			// 创建时间
			if (!YZUtility.isNullorEmpty(createtime1) && !createtime1.equals("null") && !createtime1.equals("undefined")) {
				map.put("createtime1", createtime1 + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(createtime2) && !createtime2.equals("null") && !createtime2.equals("undefined")) {
				map.put("createtime2", createtime2 + ConstUtil.TIME_END);
			}

			if (!YZUtility.isNullorEmpty(org) && !org.equals("null") && !org.equals("undefined")) {
				map.put("org", org);
			}
			if (!YZUtility.isNullorEmpty(doctor) && !doctor.equals("null") && !doctor.equals("undefined")) {
				map.put("doctor", doctor);
			}
			if (!YZUtility.isNullorEmpty(queryInput) && !queryInput.equals("null") && !queryInput.equals("undefined")) {
				map.put("queryInput", queryInput);
			}
			if (!YZUtility.isNullorEmpty(status) && !status.equals("null") && !status.equals("undefined")) {
				map.put("status", status);
			}
			String sno = request.getParameter("sno");
			if (!YZUtility.isNullorEmpty(sno) && !sno.equals("null") && !sno.equals("undefined")) {
				map.put("sno", sno);
			}
			String unit = request.getParameter("unit");
			if (!YZUtility.isNullorEmpty(unit) && !unit.equals("null") && !unit.equals("undefined")) {
				map.put("processingfactory", unit);
			}
			List<JSONObject> list = logic.selectListByQuery(TableNameUtil.KQDS_OUTPROCESSING_SHEET, map, num);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("加工中心列表", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 不分页查询 根据Usercode进行查询 【不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectPageWjg.act")
	public String selectPageWjg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			String usercode = request.getParameter("usercode");
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			map.put("usercode", usercode);
			List<JSONObject> list = logic.selectWithPageLzjl(TableNameUtil.KQDS_OUTPROCESSING_SHEET, map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

}