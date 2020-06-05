package com.kqds.util.sys.log;

import javax.servlet.http.HttpServletRequest;

import com.kqds.entity.base.KqdsBcjl;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.bcjl.KQDS_BCJLLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.spring.BeanUtil;

import net.sf.json.JSONObject;

/**
 * 病程记录，流转日志公共类
 * 
 * @author Administrator
 * 
 */
public class BcjlUtil {

	private static KQDS_BCJLLogic bcjlLogic = (KQDS_BCJLLogic) BeanUtil.getBean("bcjlLogic");

	public static String NEW = "新增";
	public static String MODIFY = "编辑";
	public static String UPDATE = "更新";
	public static String DELETE = "删除";
	public static String DISABLE = "禁用";
	public static String ENABLE = "启用";
	public static String SUBMIT = "提交";
	public static String APPLY = "提交申请";
	public static String AUDIT = "审核通过";
	public static String CONFIRM_REFUND = "确认退款";
	public static String MODIFY_MONEY = "更改缴费金额";
	public static String MODIFY_JDR = "修改建档人";
	public static String UPDATE_STATUS = "更新状态";
	public static String UPDATE_ASKPERSON = "修改咨询";
	public static String AGREE = "同意";
	public static String DISAGREE = "不同意";
	public static String LOSS = "挂失";
	public static String RECHARGE = "充值";
	public static String PAY = "缴费";
	public static String REFUND = "退费";
	public static String DONATION = "转赠";
	public static String MERGE = "合并";
	public static String CANCEL = "取消";
	public static String AUDITOR = "审核";
	public static String SET_ASKPERSON = "设定咨询";
	public static String SAVE_TC = "另存套餐";
	public static String KQDS_ACTIVITY_RECORD = "市场活动记录";
	// KQDS_BCJL
	// KQDS_BLK
	public static String KQDS_BLCT = "病历词条";
	public static String KQDS_BLK_CZ = "病历库-初诊";
	public static String KQDS_BLK_FZ = "病历库-复诊";
	public static String KQDS_BLK_RESTORATION = "病历库-修复";
	public static String KQDS_BLK_REVIEW = "病历库-复查";
	public static String KQDS_BLK_ZHONGZHI = "病历库-种植1期";
	public static String KQDS_BLK_ZHONGZHI2 = "病历-种植2期";
	public static String KQDS_CHANGE_DOCTOR = "转诊医生";
	public static String KQDS_CHANGE_KEFU = "指定客服";
	public static String KQDS_CHANGE_JDR = "指定建档人";
	public static String KQDS_CHANGE_RECEIVE = "转诊咨询";
	public static String KQDS_CHANGE_WD = "指定关联人";
	public static String KQDS_CHUFANG = "处方";
	// KQDS_CHUFANG_DETAIL
	/******************* 仓库相关 ***********************/
	public static String KQDS_CK_DEPT = "仓库部门";
	public static String KQDS_CK_GOODS = "库存";
	public static String KQDS_CK_GOODS_DETAIL = "物品规格";
	public static String KQDS_CK_GOODS_IN = "入库";
	public static String KQDS_CK_GOODS_IN_DETAIL = "入库明细";
	public static String KQDS_CK_GOODS_OUT = "出库";
	public static String KQDS_CK_GOODS_OUT_DETAIL = "出库明细";
	public static String KQDS_CK_GOODS_TYPE = "物品类别";
	public static String KQDS_CK_HOUSE = "仓库";
	public static String KQDS_CK_SUPPLIER = "供应商";
	/******************* 仓库相关 ***********************/
	// public static String KQDS_COSTORDER = "费用单";
	public static String KQDS_COSTORDER = "费用单";
	public static String KQDS_COSTORDER_DETAIL = "收费项目";
	public static String KQDS_COSTORDER_TUIDAN = "退单";
	public static String KQDS_COSTORDER_DETAIL_TUIDAN = "退单详情";
	/** 异业记录 **/
	public static String KQDS_DEINDUSTRYRECORD = "异业记录";
	public static String KQDS_EXTENSION = "推广计划";
	public static String KQDS_EXTENSION_TYPE = "推广计划类别";
	public static String KQDS_GIVEITEM = "赠送项目";
	public static String KQDS_GIVEITEM_TC = "赠送套餐";
	public static String KQDS_GIVEITEM_GIVERECORD = "赠送记录";
	public static String KQDS_GIVEITEM_USERECORD = "使用记录";
	public static String KQDS_HOSPITAL_ORDER = "门诊预约";
	public static String KQDS_IMAGE_DATA = "影像资料";
	public static String KQDS_INFORMATION = "信息库";
	public static String KQDS_JZMD_TYPE = "就诊目的";
	public static String KQDS_JZQK = "就诊情况";
	public static String KQDS_LLTJ = "领料统计";
	public static String KQDS_LLTJ_DETAIL = "领料统计明细";
	// KQDS_LOG
	public static String KQDS_MEDIA_RECORD = "媒体记录";
	public static String KQDS_Hz_LabellabeAndPatient = "修改标签";
	// KQDS_MEDICALRECORD
	public static String KQDS_MEDICALRECORD_CZ = "初诊病历";
	public static String KQDS_MEDICALRECORD_FZ = "复诊病历";
	public static String KQDS_MEDICALRECORD_RESTORATION = "病历-修复";
	public static String KQDS_MEDICALRECORD_REVIEW = "病历-复查";
	public static String KQDS_MEDICALRECORD_ZHONGZHI = "病历-种植1期";
	public static String KQDS_MEDICALRECORD_ZHONGZHI2 = "病历-种植2期";
	public static String KQDS_MEMBER = "会员卡";
	public static String KQDS_MEMBER_BIND = "会员卡- 绑定IC卡";
	public static String KQDS_MEMBER_RECORD = "会员卡-操作记录";
	public static String KQDS_MEMBER_RECORD_SH = "会员卡-审核记录";
	public static String KQDS_NET_ORDER = "网电预约";
	public static String KQDS_NET_ORDER_RECORD = "网电预约-备注";
	public static String KQDS_MEMBER_MODIFY_PWD = "修改密码";
	public static String KQDS_MACHINING = "加工单流转状态";
	public static String KQDS_OUTPROCESSING = "加工项目";
	public static String KQDS_OUTPROCESSING_TYPE = "加工项目类别";
	public static String KQDS_OUTPROCESSING_UNIT = "加工厂";
	public static String KQDS_OUTPROCESSING_SHEET = "加工单";
	public static String KQDS_OUTPROCESSING_SHEET_DETAIL = "加工单明细";
	public static String KQDS_PAIBAN = "排班记录";
	public static String KQDS_PAIBAN_TYPE = "排班类别";
	public static String KQDS_PARTICIPANT = "亲友关系";
	public static String KQDS_PAYCOST = "收费";
	public static String KQDS_PRINT_SET = "打印设置";
	public static String KQDS_PUSH = "推送信息";
	public static String KQDS_RECEIVEINFO = "接诊信息";
	public static String KQDS_RECEIVEINFO_CONTENT = "接诊信息-备注";
	public static String KQDS_REFUND = "退费信息";
	public static String KQDS_REFUND_DETAIL = "退费明细";
	public static String KQDS_REG = "挂号";
	public static String KQDS_ROOM = "手术室预约";
	public static String KQDS_TOOTH_DOC = "会诊信息";
	public static String KQDS_TREATITEM = "收费项目";
	public static String KQDS_TREATITEM_TC = "收费套餐";
	public static String KQDS_TREATITEM_TC_TYPE = "收费套餐分类";
	public static String KQDS_USERDOCUMENT = "患者档案";
	public static String KQDS_VISIT = "回访";
	public static String KQDS_VISIT_SET = "回访设置";
	public static String KQDS_SMS = "短信模板";
	public static String KQDS_SMS_MODEL = "短信";
	
	public static String HUDH_LCLJ_BASE = "病历基本信息";
	
	/**药库相关**/
	public static String HUDH_YKZZ_DRUGS = "药库";
	public static String HUDH_INVOICE_DETAIL = "发票";
	/** 微信相关 **/
	public static String WX_ORDER = "微信预约";
	public static String WX_USERDOCUMENT = "微信注册";
	public static String WX_NEWS = "图文消息";
	public static String WX_NEWSITEM = "图文消息详情";
	public static String WX_TEMPLATEMSG = "消息模板";
	public static String WX_TEMPLATEITEM = "模板消息";
	public static String WX_QUICKMSG = "快捷回复";

	public static String HUDH_operationNote = "手术记录";

	public static String HUDH_SpecialityCheck = "专科检查";

	public static String HUDH_Acography = "治疗记录";

	/**
	 * 日志记录规则： 1、主子表一对一，记录子表内容，因为子表内容更具体 2、主子表一对多，记录主表内容
	 * 
	 * 3、更新的时候，不记录日志内容
	 */

	/**
	 * 记录患者病程日志
	 * 
	 * @throws Exception
	 */
	public static void LogBcjl(String operName, String bcmc, Object jlcontent, String tableName, HttpServletRequest request) throws Exception {
		LogBcjlWithUserCodeAndRegno(operName, bcmc, jlcontent, null, null, true, tableName, request);
	}

	/**
	 * 参数有患者编号
	 * 
	 * @param operName
	 * @param jlname
	 * @param jlcontent
	 * @param request
	 * @throws Exception
	 */
	public static void LogBcjlWithUserCode(String operName, String bcmc, Object jlcontent, String userCode, String tableName, HttpServletRequest request) throws Exception {
		LogBcjlWithUserCodeAndRegno(operName, bcmc, jlcontent, userCode, null, true, tableName, request);
	}

	/**
	 * 参数有患者编号和挂号编号
	 * 
	 * @param operName
	 * @param jlname
	 * @param jlcontent
	 * @param request
	 * @throws Exception
	 */
	public static void LogBcjlWithUserCodeAndRegno(String operName, String bcmc, Object jlcontent, String userCode, String regno, boolean ifShow, String tableName,
			HttpServletRequest request) throws Exception {
		YZPerson person = SessionUtil.getLoginPerson(request);

		String contentStr = null; // 日志内容
		if (jlcontent == null) {
			jlcontent = "";
		} else if (jlcontent instanceof String) {
			contentStr = (String) jlcontent;
		} else if (jlcontent instanceof JSONObject || jlcontent instanceof StringBuffer) {
			contentStr = jlcontent.toString();
		} else {
			contentStr = JSONObject.fromObject(jlcontent).toString();
		}

		String ifshow = "0"; // 0 显示
		if (!ifShow) {
			ifshow = "1"; // 1 不显示
		}

		// 非空判断
		String personId = null;
		String deptId = null;
		if (person != null) {
			personId = person.getSeqId();
			deptId = person.getDeptId();
		}

		String sessiontokenStr = null;
		Object sessiontoken = request.getSession().getAttribute(SessionUtil.SESSION_TOKEN);
		if (sessiontoken != null) {
			sessiontokenStr = String.valueOf(sessiontoken);
		}

		Object requesttoken = request.getAttribute(ConstUtil.REQUEST_LOG_UUID);
		String requesttokenStr = "";
		if (requesttoken != null) {
			requesttokenStr = String.valueOf(requesttoken);
		}

		KqdsBcjl dp = new KqdsBcjl();
		String uuid = YZUtility.getUUID();
		dp.setSeqId(uuid);
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
		dp.setCreateuser(personId);
		dp.setJlname(operName);
		dp.setDeptid(deptId);
		dp.setContent(contentStr);
		dp.setUsercode(userCode);
		dp.setIfshow(ifshow);
		dp.setRegno(regno);
		dp.setBcmc(bcmc);
		dp.setLogtype(0); // 业务日志
		dp.setIp(YZUtility.getIpAddr(request));// ip
		dp.setTablename(tableName);
		dp.setOrganization(ChainUtil.getCurrentOrganization(request));
		dp.setSessiontoken(sessiontokenStr); // sessionToken
		dp.setRequesttoken(requesttokenStr);
		bcjlLogic.saveSingleUUID(TableNameUtil.KQDS_BCJL, dp);

	}

}
