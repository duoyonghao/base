package com.kqds.controller.base.memberRecord;

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

import com.kqds.entity.base.KqdsMemberRecord;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.memberRecord.KQDS_Member_RecordLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.SortList;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("KQDS_Member_RecordAct")
public class KQDS_Member_RecordAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Member_RecordAct.class);
	@Autowired
	private KQDS_Member_RecordLogic logic;
	@Autowired
	private YZPersonLogic personLogic;

	@RequestMapping(value = "/methodModify.act")
	public String methodModify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			KqdsMemberRecord dp = new KqdsMemberRecord();
			BeanUtils.populate(dp, request.getParameterMap());
			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键为空");
			}
			KqdsMemberRecord record = (KqdsMemberRecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, seqId);
			if (record == null) {
				throw new Exception("会员卡操作记录不存在");
			}
			BigDecimal total1 = dp.getXjmoney().add(dp.getYhkmoney()).add(dp.getWxmoney()).add(dp.getMmdmoney()).add(dp.getZfbmoney()).add(dp.getBdfqmoney()).add(dp.getQtmoney());
			BigDecimal total2 = record.getXjmoney().add(record.getYhkmoney()).add(record.getWxmoney()).add(record.getMmdmoney()).add(record.getZfbmoney())
					.add(record.getBdfqmoney()).add(record.getQtmoney());
			if (KqdsBigDecimal.compareTo(total1, total2) != 0) {
				throw new Exception("页面总金额和数据库总金额不一致！");
			}
			record.setXjmoney(dp.getXjmoney());
			record.setYhkmoney(dp.getYhkmoney());
			record.setWxmoney(dp.getWxmoney());
			record.setMmdmoney(dp.getMmdmoney());
			record.setZfbmoney(dp.getZfbmoney());
			record.setBdfqmoney(dp.getBdfqmoney());
			record.setQtmoney(dp.getQtmoney());
			logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, dp);
			/** 记录日志，这儿特殊处理，存记录主键，为了能查询修改记录 **/
			BcjlUtil.LogBcjl(seqId, BcjlUtil.KQDS_MEMBER_RECORD, record, TableNameUtil.KQDS_MEMBER_RECORD, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
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
			Map<String, String> map = new HashMap<String, String>();
			String cardno = request.getParameter("cardno");
			if (!YZUtility.isNullorEmpty(cardno)) {
				map.put("cardno", cardno);
			}
			String queryInput = request.getParameter("cardqueryInputno");
			if (!YZUtility.isNullorEmpty(queryInput)) {
				map.put("memberno", queryInput);
			}
			List<JSONObject> list = logic.selectList(TableNameUtil.KQDS_MEMBER_RECORD, map);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 操作记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectListForCzjl.act")
	public String selectListForCzjl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String cardno = request.getParameter("cardno");
			if (!YZUtility.isNullorEmpty(cardno)) {
				map.put("cardno", cardno);
			}
			String queryInput = request.getParameter("cardqueryInputno");
			if (!YZUtility.isNullorEmpty(queryInput)) {
				map.put("memberno", queryInput);
			}
			// 查询操作记录 排除 受赠（不然 第一咨询 患者来源 等 会不一样）
			map.put("nosz", "1");
			List<JSONObject> list1 = logic.selectList(TableNameUtil.KQDS_MEMBER_RECORD, map);
			List<JSONObject> list2 = logic.selectListForCzjl(TableNameUtil.KQDS_MEMBER_RECORD, map);
			List<JSONObject> list = new ArrayList<JSONObject>();
			list.addAll(list1);
			list.addAll(list2);
			SortList<JSONObject> sortList = new SortList<JSONObject>();
			sortList.SortJSONObject(list, "createtime", "desc");
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 根据选择的操作类型查询 【暂且不做门诊条件过滤】
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectListByType.act")
	public String selectListByType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			Map<String, String> map = new HashMap<String, String>();
			String cardno = request.getParameter("cardno");
			if (!YZUtility.isNullorEmpty(cardno)) {
				map.put("cardno", cardno);
			}
			String queryInput = request.getParameter("cardqueryInputno");
			if (!YZUtility.isNullorEmpty(queryInput)) {
				map.put("memberno", queryInput);
			}
			String type = request.getParameter("type");
			if (!YZUtility.isNullorEmpty(type)) {
				map.put("type", type);
			}
			String queryinput = request.getParameter("queryinput");
			if (!YZUtility.isNullorEmpty(queryinput)) {
				map.put("queryinput", queryinput);
			}
			String starttime = request.getParameter("starttime");
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			String endtime = request.getParameter("endtime");
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			String visualstaff = SessionUtil.getVisualstaff(request);
			map.put("visualstaff", visualstaff);
			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			map.put("organization", organization);
			List<JSONObject> list = logic.selectListByType(TableNameUtil.KQDS_MEMBER_RECORD, map);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				ExportTable.exportBootStrapTable2Excel("会员卡记录", fieldArr, fieldnameArr, list, response, request);
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
	 * 打印页面，获取不同付款方式的金额
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/printSfxm.act")
	public String printSfxm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String recordId = request.getParameter("recordId");
			String table = request.getParameter("table");
			if (YZUtility.isNullorEmpty(recordId)) {
				throw new Exception("退费编号为null");
			}
			if (YZUtility.isNullorEmpty(table)) {
				throw new Exception("表为null");
			}
			JSONObject jobj = new JSONObject();
			JSONObject FKFS = logic.printSfxm(table, recordId, request);
			jobj.put("FKFS", FKFS);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 设定接诊咨询
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setAskperson.act")
	public String setAskperson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String seqId = request.getParameter("seqId");
			String askperson = request.getParameter("askperson");
			KqdsMemberRecord record = (KqdsMemberRecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, seqId);
			if (record == null) {
				throw new Exception("操作记录不存在");
			}
			// 原咨询
			String Oldaskperson = personLogic.getNameStrBySeqIds(record.getAskperson());
			record.setAskperson(askperson);
			logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, record);

			// 记录日志
			String Newaskperson = personLogic.getNameStrBySeqIds(askperson);
			String logText = "系统用户" + person.getUserName() + "进行了会员接诊咨询修改操作，原咨询：" + Oldaskperson + "，现咨询：" + Newaskperson + "，患者编号：" + record.getUsercode() + "，患者姓名："
					+ record.getUsername() + "。";
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.UPDATE_ASKPERSON + record.getSeqId(), BcjlUtil.KQDS_MEMBER_RECORD, logText, record.getUsercode(),
					TableNameUtil.KQDS_MEMBER_RECORD, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 查询
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
			KqdsMemberRecord en = (KqdsMemberRecord) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, seqId);
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
}