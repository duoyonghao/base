package com.kqds.controller.base.memberRecord;

import java.math.BigDecimal;
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

import com.kqds.core.global.YZActionKeys;
import com.kqds.entity.base.KqdsMember;
import com.kqds.entity.base.KqdsMemberRecord;
import com.kqds.entity.base.KqdsMemberRecordSh;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.memberRecord.KQDS_Member_Record_ShLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.base.PushUtil;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.log.BcjlUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;

import net.sf.json.JSONObject;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("KQDS_Member_Record_ShAct")
public class KQDS_Member_Record_ShAct {

	private static Logger logger = LoggerFactory.getLogger(KQDS_Member_Record_ShAct.class);
	@Autowired
	private YZPersonLogic personLogic;
	@Autowired
	private KQDS_Member_Record_ShLogic logic;

	/**
	 * 根据选择的操作类型查询
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
			// 取值
			String cardno = request.getParameter("cardno");
			String queryInput = request.getParameter("cardqueryInputno");
			String queryinput = request.getParameter("queryinput");
			String starttime = request.getParameter("starttime");
			String endtime = request.getParameter("endtime");
			String sqstatus = request.getParameter("sqstatus");
			Map<String, String> map = new HashMap<String, String>();
			if (!YZUtility.isNullorEmpty(cardno)) {
				map.put("cardno", cardno);
			}
			if (!YZUtility.isNullorEmpty(queryInput)) {
				map.put("memberno", queryInput);
			}
			if (!YZUtility.isNullorEmpty(queryinput)) {
				map.put("queryinput", queryinput);
			}
			if (!YZUtility.isNullorEmpty(starttime)) {
				map.put("starttime", starttime + ConstUtil.TIME_START);
			}
			if (!YZUtility.isNullorEmpty(endtime)) {
				map.put("endtime", endtime + ConstUtil.TIME_END);
			}
			if (!YZUtility.isNullorEmpty(sqstatus)) {
				map.put("sqstatus", sqstatus);
			}

			String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
			if (YZUtility.isNullorEmpty(organization)) {
				organization = ChainUtil.getCurrentOrganization(request);
			}
			map.put("organization", organization);
			List<JSONObject> list = (List<JSONObject>) logic.selectListByType(TableNameUtil.KQDS_MEMBER_RECORD_SH, map);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				for (JSONObject json : list) {
					int value = json.getInt("sqstatus");
					String html = "";
					if (ConstUtil.REFUND_STATUS_1 == value) {
						html = "申请中";
					} else if (ConstUtil.REFUND_STATUS_2 == value) {
						html = "已同意";
					} else if (ConstUtil.REFUND_STATUS_3 == value) {
						html = "未同意";
					} else if (ConstUtil.REFUND_STATUS_4 == value) {
						html = "已退款";
					}
					json.put("sqstatus", html);
				}
				ExportTable.exportBootStrapTable2Excel("预收退款", fieldArr, fieldnameArr, list, response, request);
				return null;
			}
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, false, ex, response, logger);
		}
		return null;
	}

	/**
	 * 申请单操作 审核 确认
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editState.act")
	public String editState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			KqdsMemberRecordSh dp = new KqdsMemberRecordSh();
			BeanUtils.populate(dp, request.getParameterMap());
			Map<String, String> map = new HashMap<String, String>();
			map.put("seq_id", dp.getSeqId());
			KqdsMemberRecordSh en = (KqdsMemberRecordSh) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD_SH, dp.getSeqId());
			en.setSqstatus(dp.getSqstatus());
			en.setSqremark(dp.getSqremark());
			logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD_SH, en);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.AUDIT, BcjlUtil.KQDS_MEMBER_RECORD_SH, en, en.getUsercode(), TableNameUtil.KQDS_MEMBER_RECORD_SH, request);

			// 给收费人添加提示信息 收费申请
			List<JSONObject> personlist = personLogic.getAllShowfeiPerson(ChainUtil.getCurrentOrganization(request), request);
			for (int i = 0; i < personlist.size(); i++) {
				PushUtil.saveTx4MemberSH(personlist.get(i), person, request);
			}
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 确认退款
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmRefund.act")
	public synchronized String confirmRefund(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			JSONObject czfs = new JSONObject();

			String seqId = request.getParameter("seqId");
			if (YZUtility.isNullorEmpty(seqId)) {
				throw new Exception("主键不能为空");
			}

			KqdsMemberRecordSh dp = (KqdsMemberRecordSh) logic.loadObjSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD_SH, seqId);
			if (ConstUtil.MEMBER_SH_STATUS_2 != dp.getSqstatus()) {
				if (ConstUtil.MEMBER_SH_STATUS_4 == dp.getSqstatus()) {
					throw new Exception("该退费单已退过费,请选择同意申请的退费单");
				} else {
					throw new Exception("请选择同意申请的退费单");
				}
			}
			String organization = ChainUtil.getCurrentOrganization(request);
			confirmRefundDeal(dp, person, organization, request);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.CONFIRM_REFUND, BcjlUtil.KQDS_MEMBER_RECORD_SH, dp, dp.getUsercode(), TableNameUtil.KQDS_MEMBER_RECORD_SH, request);
			PushUtil.saveTx4MemberConfirm(dp, person, request);
			JSONObject jobj = new JSONObject();
			jobj.put(YZActionKeys.JSON_RET_DATA, czfs);
			YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 预收退款验证
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/Yztuikuan.act")
	public String Yztuikuan(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			boolean flag = true;
			String memberno = request.getParameter("memberno");
			String tkgivemoneyStr = request.getParameter("tkgivemoney");
			String tkmoneyStr = request.getParameter("tkmoney");
			BigDecimal tkgivemoney = BigDecimal.ZERO;
			BigDecimal tkmoney = BigDecimal.ZERO;
			if (!YZUtility.isNullorEmpty(tkgivemoneyStr)) {
				tkgivemoney = new BigDecimal(tkgivemoneyStr);
			}
			if (!YZUtility.isNullorEmpty(tkmoneyStr)) {
				tkmoney = new BigDecimal(tkmoneyStr);
			}
			// 会员卡信息
			Map<String, String> map = new HashMap<String, String>();
			map.put("memberno", memberno);
			List<KqdsMember> listMember = (List<KqdsMember>) (List<KqdsMember>) logic.loadList(TableNameUtil.KQDS_MEMBER, map);
			if (listMember == null) {
				throw new Exception("不存在会员卡");
			}
			KqdsMember en = listMember.get(0);
			// 已同意的申请记录
			Map map2 = new HashMap<String, String>();
			map2.put("cardno", memberno);
			map2.put("sqstatus", ConstUtil.MEMBER_SH_STATUS_2);
			List<KqdsMemberRecordSh> listSq = (List<KqdsMemberRecordSh>) logic.loadList(TableNameUtil.KQDS_MEMBER_RECORD_SH, map2);
			if (listSq != null && listSq.size() > 0) {
				KqdsMemberRecordSh sq = listSq.get(0);
				tkgivemoney = KqdsBigDecimal.add(tkgivemoney, sq.getCgivemoney());
				tkmoney = KqdsBigDecimal.add(tkmoney, sq.getCmoney());
			}

			// 验证会员卡余额 是否>=退款金额（包含该会员卡所有已同意未确认的退款申请）
			BigDecimal yegivemoney = KqdsBigDecimal.add(tkgivemoney, en.getGivemoney());
			BigDecimal yemoney = KqdsBigDecimal.add(tkmoney, en.getMoney());
			if (KqdsBigDecimal.compareTo(yegivemoney, BigDecimal.ZERO) < 0) {
				throw new Exception("赠送金额不足");
			}
			if (KqdsBigDecimal.compareTo(yemoney, BigDecimal.ZERO) < 0) {
				throw new Exception("充值金额不足");
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
	 * 预交金扣款
	 */
	private void confirmRefundDeal(KqdsMemberRecordSh dp, YZPerson person, String organization, HttpServletRequest request) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("memberno", dp.getCardno());
		List<KqdsMember> listMember = (List<KqdsMember>) (List<KqdsMember>) logic.loadList(TableNameUtil.KQDS_MEMBER, map);
		if (listMember != null && listMember.size() < 1) {
			throw new Exception("不存在会员卡");
		}
		KqdsMember en = listMember.get(0);
		BigDecimal money = KqdsBigDecimal.add(en.getMoney(), dp.getCmoney());
		BigDecimal givemoney = KqdsBigDecimal.add(en.getGivemoney(), dp.getCgivemoney());
		if (KqdsBigDecimal.compareTo(givemoney, BigDecimal.ZERO) < 0 || KqdsBigDecimal.compareTo(money, BigDecimal.ZERO) < 0) {
			throw new Exception("会员卡余额不足");
		}
		en.setMoney(money);
		en.setGivemoney(givemoney);
		logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER, en);

		KqdsMemberRecord r = new KqdsMemberRecord();
		String uuid = YZUtility.getUUID();
		r.setSeqId(uuid);
		r.setUsercode(dp.getUsercode());
		r.setUsername(dp.getUsername());
		r.setCardno(en.getMemberno());
		String ctime = YZUtility.getCurDateTimeStr();
		r.setCreatetime(ctime);
		r.setCreateuser(person.getSeqId());
		r.setType(ConstUtil.MEMBER_RECORD_TYPE_TF);
		r.setTfremark(dp.getTfremark());
		// 保存接诊咨询 挂号分类
		r.setAskperson(dp.getAskperson());
		r.setRegsort(dp.getRegsort());
		r.setXjmoney(dp.getXjmoney());
		r.setYhkmoney(dp.getYhkmoney());
		r.setQtmoney(dp.getQtmoney());
		r.setZfbmoney(dp.getZfbmoney());
		r.setWxmoney(dp.getWxmoney());
		r.setMmdmoney(dp.getMmdmoney());
		r.setBdfqmoney(dp.getBdfqmoney());
		r.setCmoney(dp.getCmoney());// 充值金额
		r.setCgivemoney(dp.getCgivemoney());// 充值赠送金额
		r.setCtotal(dp.getCtotal());// 充值小计
		r.setYmoney(en.getMoney());// 本金余额
		r.setYgivemoney(en.getGivemoney());// 赠送余额
		r.setYtotal(KqdsBigDecimal.add(en.getMoney(), en.getGivemoney()));// 余额小计
		r.setOrganization(organization); // 【前端页面调用，以所在门诊为准】
		logic.saveSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD, r);
		// 记录日志
		BcjlUtil.LogBcjl(r.getSeqId(), BcjlUtil.KQDS_MEMBER_RECORD, r, TableNameUtil.KQDS_MEMBER_RECORD, request);

		dp.setSqstatus(4);
		dp.setYmoney(r.getYmoney());// 本金余额
		dp.setYgivemoney(r.getYgivemoney());// 赠送余额
		dp.setYtotal(r.getYtotal());// 余额小计
		dp.setType(r.getCreatetime());// type 存退款时间
		logic.updateSingleUUID(TableNameUtil.KQDS_MEMBER_RECORD_SH, dp);
	}
}