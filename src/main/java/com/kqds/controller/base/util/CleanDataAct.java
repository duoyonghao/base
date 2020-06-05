package com.kqds.controller.base.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.makeInvoice.KQDS_makeInvoiceLogic;
import com.kqds.service.base.outProcessingSheet.KQDS_OutProcessingSheetLogic;
import com.kqds.service.base.receiveinfo.KQDS_ReceiveInfoLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

/**
 * 彻底删除患者数据专用
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("CleanDataAct")
public class CleanDataAct {
	private static Logger logger = LoggerFactory.getLogger(CleanDataAct.class);
	@Autowired
	private KQDS_UserDocumentLogic logic;
	@Autowired
	private KQDS_makeInvoiceLogic Mlogic;
	@Autowired
	private KQDS_OutProcessingSheetLogic outLogic;
	@Autowired
	private KQDS_ReceiveInfoLogic recLogic;
	@Autowired
	private KQDS_REGLogic regLogic;

	/**
	 * 伪删除患者档案
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tmpDeleteUers.act")
	public String tmpDeleteUers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			String usercodes = request.getParameter("usercodes");

			if (YZUtility.isNullorEmpty(usercodes)) {
				throw new Exception("没有选择任何患者，无法执行伪删除操作。");
			}

			usercodes = YZUtility.ConvertStringIds4Query(usercodes); // 转换成查询格式

			StringBuffer sql = new StringBuffer();
			sql.append(" update KQDS_UserDocument ");
			sql.append(" set isdelete = 1 where 1=1 ");
			sql.append(" and usercode in (" + usercodes + ") ");

			int count = logic.deleteuser(usercodes);

			// 记录日志
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_USERDOCUMENT, sql.toString(), TableNameUtil.KQDS_USERDOCUMENT, request);

			String logText = "系统用户" + person.getUserId() + "伪删除：" + sql.toString() + "，成功删除：" + count + "条记录。";
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_USERDOCUMENT, logText, TableNameUtil.KQDS_USERDOCUMENT, request);

			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(null, true, ex, response, logger);
		}
		return null;
	}

	/**
	 * 彻底删除患者数据专用
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteUserAllData.act")
	public String deleteUserAllData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String usercodes = request.getParameter("usercodes");
		try {
			YZPerson person = SessionUtil.getLoginPerson(request);
			// 删除指定患者的所有业务数据
			deleteUserAllDataByUserCode(usercodes, request);
			StringBuffer logText = new StringBuffer();
			logText.append("用户").append(person.getUserId()).append("对编号为：").append(usercodes).append("的患者进行了所有业务数据删除操作！");
			BcjlUtil.LogBcjl(BcjlUtil.DELETE, BcjlUtil.KQDS_USERDOCUMENT, logText, TableNameUtil.KQDS_USERDOCUMENT, request);
			JSONObject retrunObj = new JSONObject();
			YZUtility.DEAL_SUCCESS(retrunObj, null, response, logger);
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), false, ex, response, logger);
		}

		return null;
	}

	/**
	 * 删除指定患者的所有业务数据
	 * 
	 * @param conn
	 * @param usercodes
	 * @param request
	 * @throws Exception
	 */
	private void deleteUserAllDataByUserCode(String usercodes, HttpServletRequest request) throws Exception {
		List<String> usercodeList = YZUtility.ConvertString2List(usercodes);
		StringBuffer cannotDeleteBf = new StringBuffer();
		for (String usercode : usercodeList) {
			int tmpCount = logic.checkuser(usercode);
			if (tmpCount > 0) {
				cannotDeleteBf.append(usercode).append(",");
			}
		}

		if (cannotDeleteBf.length() > 0) {
			String codeStr = cannotDeleteBf.toString();
			if (codeStr.endsWith(",")) {
				codeStr = codeStr.substring(0, codeStr.length() - 1); // 去掉后面的逗号
			}
			throw new Exception("存在转赠/受赠操作的患者无法删除，患者编号为：" + codeStr);
		}

		List<String> tableNameList = new ArrayList<String>();
		// tableNameList.add(TableNameUtil.KQDS_MEMBER_SCORE); // 还未启用
		// tableNameList.add(TableNameUtil.KQDS_SCORE_EXCHANGE); // 废弃
		// tableNameList.add(TableNameUtil.KQDS_BCJL); // ### 不删除 业务日志表
		tableNameList.add(TableNameUtil.KQDS_CHANGE_DOCTOR);
		tableNameList.add(TableNameUtil.KQDS_CHANGE_KEFU);
		tableNameList.add(TableNameUtil.KQDS_CHANGE_RECEIVE);
		tableNameList.add(TableNameUtil.KQDS_CHANGE_WD);
		tableNameList.add(TableNameUtil.KQDS_CHUFANG); // 处方
		tableNameList.add(TableNameUtil.KQDS_CHUFANG_DETAIL);

		tableNameList.add(TableNameUtil.KQDS_COSTORDER); // 费用
		tableNameList.add(TableNameUtil.KQDS_COSTORDER_DETAIL);
		tableNameList.add(TableNameUtil.KQDS_COSTORDER_TUIDAN);
		tableNameList.add(TableNameUtil.KQDS_COSTORDER_DETAIL_TUIDAN);
		tableNameList.add(TableNameUtil.KQDS_EXTENSION); // 推广计划
		tableNameList.add(TableNameUtil.KQDS_GIVEITEM_GIVERECORD); // 赠送相关
		tableNameList.add(TableNameUtil.KQDS_GIVEITEM_USERECORD);
		tableNameList.add(TableNameUtil.KQDS_HOSPITAL_ORDER);// 门诊预约
		tableNameList.add(TableNameUtil.KQDS_IMAGE_DATA); // 影像资料
		// -----------------------------------------------------------
		// 1)KQDS_JZQK 通过挂号记录删除
		tableNameList.add(TableNameUtil.KQDS_LLTJ); // 领料统计
		tableNameList.add(TableNameUtil.KQDS_LLTJ_DETAIL);
		// ----------------------------------------------------------- KQDS_LOG
		tableNameList.add(TableNameUtil.KQDS_MEDICALRECORD); // 病历
		tableNameList.add(TableNameUtil.KQDS_MEDICALRECORD_CZ);
		tableNameList.add(TableNameUtil.KQDS_MEDICALRECORD_FZ);
		// ----------------------------------------------------------- 种植病历暂时不处理
		tableNameList.add(TableNameUtil.KQDS_MEMBER); // 会员卡
		tableNameList.add(TableNameUtil.KQDS_MEMBER_RECORD);
		tableNameList.add(TableNameUtil.KQDS_MEMBER_RECORD_SH);
		tableNameList.add(TableNameUtil.KQDS_NET_ORDER); // 网电预约
		tableNameList.add(TableNameUtil.KQDS_NET_ORDER_RECORD);
		tableNameList.add(TableNameUtil.KQDS_OUTPROCESSING_SHEET); // 加工单
		// -----------------------------------------------------------
		// 2)加工单详情，要通过加工单删除
		// tableNameList.add(TableNameUtil.KQDS_PARTICIPANT); // 还未启用
		tableNameList.add(TableNameUtil.KQDS_PAYCOST); // 结账
		tableNameList.add(TableNameUtil.KQDS_RECEIVEINFO); // 咨询记录
		// tableNameList.add(TableNameUtil.KQDS_RECEIVEINFO_CONTENT);
		// 3)通过咨询记录主表进行删除
		tableNameList.add(TableNameUtil.KQDS_REFUND); // 退款
		tableNameList.add(TableNameUtil.KQDS_REFUND_DETAIL);
		tableNameList.add(TableNameUtil.KQDS_REG); // 挂号
		tableNameList.add(TableNameUtil.KQDS_ROOM); // 手术预约
		tableNameList.add(TableNameUtil.KQDS_SMS); // 短信
		tableNameList.add(TableNameUtil.KQDS_TOOTH_DOC); // 会诊信息
		tableNameList.add(TableNameUtil.KQDS_USERDOCUMENT); // 患者档案
		tableNameList.add(TableNameUtil.KQDS_VISIT); // 回访
		// ----------------------------------------------------------- 微信暂时不处理

		String userCodes4Del = YZUtility.ConvertStringIds4Query(usercodes);
		// 删除没有usercode字段的表
		deleteNoUserCodeTableRecord(userCodes4Del, request);
		for (String tableName : tableNameList) {
			Mlogic.deleteUserDoc(userCodes4Del, tableName);
		}
	}

	/**
	 * 删除表中没有患者编号的数据
	 * 
	 * @param userCodes4Del
	 * @param conn
	 * @param request
	 * @throws Exception
	 */
	private void deleteNoUserCodeTableRecord(String userCodes4Del, HttpServletRequest request) throws Exception {

		// ################################################### 咨询记录
		List<JSONObject> tmpList1 = outLogic.getNoUsercode(userCodes4Del);

		for (JSONObject jsonObject : tmpList1) {
			String seqId = jsonObject.getString("seq_id");
			outLogic.deleteBySheetno(seqId);
			// System.out.println("----------------删除 " + count +
			// "条 KQDS_RECEIVEINFO_CONTENT 记录 ");

		}

		// ################################################### 咨询记录
		List<JSONObject> tmpList2 = recLogic.getNoUsercode(userCodes4Del);
		for (JSONObject jsonObject : tmpList2) {
			String seqId = jsonObject.getString("seq_id");
			recLogic.deleteByrecno(seqId);
		}

		// ################################################### 就诊情况
		List<JSONObject> tmpList3 = regLogic.getNoUsercode(userCodes4Del);
		for (JSONObject jsonObject : tmpList3) {
			String seqId = jsonObject.getString("seq_id");
			regLogic.deleteByRegno(seqId);
		}
	}

}
