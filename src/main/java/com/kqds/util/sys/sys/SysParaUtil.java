package com.kqds.util.sys.sys;

import javax.servlet.http.HttpServletRequest;

import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

/**
 * 此类为系统参数设置类，对应sys_para表
 * 
 * @author Administrator
 * 
 */
public class SysParaUtil {

	/*********************** 数据库相关 start *******************************/

	// 基础数据管理相关
	public static String NOT_SHOW_KIND_DICTS = "NOT_SHOW_KIND_DICTS";
	// 初诊
	public static String JZFL_CHUZHEN_SEQID = "JZFL_CHUZHEN_SEQID";
	public static String YYFL_CHUZHEN_SEQID = "YYFL_CHUZHEN_SEQID";

	public static String IS_OPEN_ZHONGZHI_BINGLI = "IS_OPEN_ZHONGZHI_BINGLI";

	// 成交判断相关
	public static String REG_SUCCESS_ITEM_SORT = "REG_SUCCESS_ITEM_SORT";
	public static String COST_SUCCESS_ITEM_SORT = "COST_SUCCESS_ITEM_SORT";
	public static String COST_SUCCESS_ITEM = "COST_SUCCESS_ITEM";

	// 角色相关
	public static String PRIV_ZHUGUAN_SEQID = "PRIV_ZHUGUAN_SEQID";
	public static String PRIV_ZONGJINGLI_SEQID = "PRIV_ZONGJINGLI_SEQID";
	public static String PRIV_DOCTOR_SEQID = "PRIV_DOCTOR_SEQID";
	public static String PRIV_YUANZHANG_SEQID = "PRIV_YUANZHANG_SEQID";
	public static String PRIV_WANGDIAN_SEQID = "PRIV_WANGDIAN_SEQID";
	public static String PRIV_SHICHANG_SEQID = "PRIV_SHICHANG_SEQID";
	public static String PRIV_SHOUFEI_SEQID = "PRIV_SHOUFEI_SEQID";
	public static String PRIV_ASK_SEQID = "PRIV_ASK_SEQID";
	public static String PRIV_CK_SEQID = "PRIV_CK_SEQID";
	public static String RIGHT_YCHF = "RIGHT_YCHF";
	public static String ZY_LYCK = "ZY_LYCK";
	public static String SYS_POSITION = "SYS_POSITION";

	// 提醒相关
	public static String SMS_BIRTH_BEFORE = "SMS_BIRTH_BEFORE";
	public static String SMS_YYTX_BEFORE = "SMS_YYTX_BEFORE";
	// 端口数限制
	public static String YZ_CONFINE_NUMBER = "YZ_CONFINE_NUMBER";

	public static String IS_OPEN_PAIBAN_ORDER_FUNC = "IS_OPEN_PAIBAN_ORDER_FUNC";
	public static String HYK_NO_PASSWORD = "HYK_NO_PASSWORD";// 会员卡免密支付
	public static String HYK_BINDING = "HYK_BINDING";// 会员卡绑卡

	/*********************** 数据库相关 END *******************************/

	/*********************** 配置文件相关 start *******************************/

	/**
	 * 录音文件存放路径
	 */
	public static String RECORD_FILE_DIR = "RECORD_FILE_DIR";

	/**
	 * 录音系统web访问路径
	 */
	public static String RECORD_WEBSITE_URL = "RECORD_WEBSITE_URL";

	/**
	 * 是否开启电话录音 0关闭 1开启
	 */
	public static String IS_OPEN_RECORD_FUNC = "IS_OPEN_RECORD_FUNC";

	/**
	 * 是否开启连锁 0 不开启 1开启
	 */
	public static String IS_OPEN_CHAIN_FUNC = "IS_OPEN_CHAIN_FUNC";

	/**
	 * 是否开启微信 0 不开启 1开启
	 */
	public static String IS_OPEN_WECHAT = "IS_OPEN_WECHAT";

	/**
	 * 试用版本 0 开启 1不开启 （试用版本开启 连锁功能则不开启）
	 */
	public static String IS_OPEN_CHAIN_SELECT = "IS_OPEN_CHAIN_SELECT";

	/**
	 * 图片上传地址
	 */
	public static String UPLOAD_URL = "UPLOAD_URL";
	/**
	 * 医患沟通视频地址
	 */
	public static String VIDEO_URL = "VIDEO_URL";

	/**
	 * 配置文件中，该字段存放当前门诊的门诊编号
	 */
	public static String ORGANIZATION = "ORGANIZATION";

	/**
	 * 最大用户数
	 */
	public static final String MAX_USER_ACCOUNT = "maxUserAccount";

	/**
	 * 是否使用 USB KEY
	 */
	public static final String IS_USE_USBKEY = "IS_USE_USBKEY";
	/**
	 * 10（参数值）元换1积分
	 */
	public static final String COST_INTEGRAL = "COST_INTEGRAL";

	/**
	 * 病历号启始值
	 */
	public static final String BLCODE_START = "BLCODE_START";

	/**
	 * 是否开启病历功能
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isOpenBLCodeFunc(HttpServletRequest request) {

		String blcode_start = SysParaUtil.getSysValueByName(request, BLCODE_START);

		if (YZUtility.isNullorEmpty(blcode_start)) {
			return false;
		}
		return true;
	}

	/*********************** 配置文件相关 END *******************************/

	// 超时时间
	public static long REQ_COST_TIME = 2000;

	public static String getSysValueByName(HttpServletRequest request, String paraName) {
		//
		String paraValue = "";
		try {
			// 数据库的连接
			// YZRequestDbConn requestDbConn = (YZRequestDbConn)
			// request.getAttribute(YZBeanKeys.REQUEST_DB_CONN_MGR);
			//
			JSONObject sysPara = (JSONObject) request.getSession().getAttribute(SessionUtil.LOGIN_SYS_PARA); // 系统配置
			paraValue = sysPara.getString(paraName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return paraValue;
	}

	public static String getFkfsNameByCostField(HttpServletRequest request, String paraName) {
		//
		String paraValue = "";
		try {
			// 数据库的连接
			JSONObject sysPara = (JSONObject) request.getSession().getAttribute(SessionUtil.LOGIN_SYS_COSTFIELD); // 系统配置
			paraValue = sysPara.getString(paraName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return paraValue;
	}

	public static String getFkfsNameByMemberField(HttpServletRequest request, String paraName) {
		//
		String paraValue = "";
		try {
			// 数据库的连接
			JSONObject sysPara = (JSONObject) request.getSession().getAttribute(SessionUtil.LOGIN_SYS_MEMBERFIELD); // 系统配置
			paraValue = sysPara.getString(paraName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return paraValue;
	}
}
