package com.kqds.util.sys;

/**
 * 全局变量类
 * 
 * @author Administrator
 * 
 */
public class ConstUtil {

	/** 文件路径分隔符 **/
	public final static String FILE_SEPERATOR = System.getProperty("file.separator");

	/**
	 * 系统登录用户session里的关键字
	 */
	public static final String LOGIN_USER = "LOGIN_USER";

	/**
	 * 当前登录人的用户角色
	 */
	public static String LOGIN_USER_PRIV = "LOGIN_USER_PRIV";

	public static String USER_STATUS_0 = "0"; // 空闲
	public static String USER_STATUS_1 = "1"; // 忙碌
	public static String USER_STATUS_2 = "2"; // 休息
	public static String USER_STATUS_3 = "3"; // 离开

	public static int USER_CODE_NUM_LEN = 6; // 空格
	public static int DEPT_CODE_NUM_LEN = 4; // 空格

	public static String EMPTY_SPACE = " "; // 空格

	public static String EMPTY = ""; // 空字符

	/** 请求进来的时候赋值，请求销毁的时候清空 **/
	public static String REQUEST_LOG_UUID = "REQUEST_LOG_UUID";

	// cost order 表的 status字段值含义
	public static String COST_ORDER_STATUS_0 = "0";// 费用计划
	public static String COST_ORDER_STATUS_1 = "1";// 等待结账
	public static String COST_ORDER_STATUS_2 = "2";// 已结账

	public static int COST_IS_BAKC_0 = 0;// 未退单
	public static int COST_IS_BAKC_1 = 1;// 已退单】

	public static int COST_IS_TK_0 = 0;// 不是退款
	public static int COST_IS_TK_1 = 1;// 是退款

	// kqds_costorder_detail 表的status字段值含义
	public static String COST_DETAIL_STATUS_0 = "0";// 不欠费【结账前】
	public static String COST_DETAIL_STATUS_1 = "1";// 欠费【结账前】
	// kqds_costorder_detail 表的issplit字段值含义
	public static String IS_SPLIT_0 = "0"; // 未拆分
	public static String IS_SPLIT_1 = "1"; // 已拆分

	// cost order detail 表的 qf字段值含义
	public static int QF_STATUS_0 = 0;// 不欠费【结账后】
	public static int QF_STATUS_1 = 1;// 欠费【结账后】

	// kqds_member_record表的type字段值含义
	public static String MEMBER_RECORD_TYPE_JF = "缴费";
	public static String MEMBER_RECORD_TYPE_TF = "退费";
	public static String MEMBER_RECORD_TYPE_CZ = "充值";
	public static String MEMBER_RECORD_TYPE_ZZ = "转赠";
	public static String MEMBER_RECORD_TYPE_SZ = "受赠";
	public static String MEMBER_RECORD_TYPE_MODIFY = "修改";
	public static String MEMBER_RECORD_TYPE_GS = "挂失";
	// 对应的描述
	public static String MEMBER_RECORD_TYPE_MODIFY_USERINFO = "修改会员资料";

	// KQDS_LLTJ_DETAIL 表的iszl字段值含义
	public static int ISZJL_0 = 0; // 未治疗
	public static int ISZJL_1 = 1; // 已治疗

	// kqds_giveitem_userecord 表的status字段值含义
	public static int GIVE_ITEM_USE_STATUS_0 = 0; // 未使用
	public static int GIVE_ITEM_USE_STATUS_1 = 1; // 已使用

	// kqds_hospital_order 表的cjstatus字段值含义
	public static String CJ_STATUS_0 = "0"; // 未成交
	public static String CJ_STATUS_1 = "1"; // 已成交
	public static String CJ_STATUS_0_DESC = "未成交";
	public static String CJ_STATUS_1_DESC = "已成交";

	// kqds_hospital_order 的isdelete字段值含义
	public static String YY_ORDER_STATUS_0 = "0";
	public static String YY_ORDER_STATUS_1 = "1";
	public static String YY_ORDER_STATUS_0_DESC = "正常";
	public static String YY_ORDER_STATUS_1_DESC = "已取消";

	// kqds_userdocument 表的doorstatus字段值含义
	public static String DOOR_STATUS_0 = "0";
	public static String DOOR_STATUS_1 = "1";
	public static String DOOR_STATUS_0_DESC = "未上门";
	public static String DOOR_STATUS_1_DESC = "已上门";

	// kqds_member表的memberstatus字段值含义
	public static String MEMBER_STATUS_0 = "0";
	public static String MEMBER_STATUS_1 = "1";
	public static String MEMBER_STATUS_2 = "2";
	public static String MEMBER_STATUS_3 = "3";
	public static String MEMBER_STATUS_0_DESC = "未激活";
	public static String MEMBER_STATUS_1_DESC = "正常";
	public static String MEMBER_STATUS_2_DESC = "已挂失";
	public static String MEMBER_STATUS_3_DESC = "已补办";

	public static String MEMBER_MONEY = "充值余额";
	public static String MEMBER_GIVEMONEY = "赠送余额";
	// 会员卡绑定IC卡状态
	public static String MEMBER_ISBINDING_0 = "0";// 未绑定
	public static String MEMBER_ISBINDING_1 = "1";// 已绑定
	// 付款方式

	public static String FKFS_YJJ2 = "预交金";
	public static String FKFS_YJJ = "预交金使用";
	public static String FKFS_ZS = "赠送使用";

	// 开单项目类型
	public static String COST_ITEM_1 = "1";// 预交金项目
	public static String COST_ITEM_2 = "2";// 挂号项目
	public static String COST_ITEM_3 = "3";// 治疗费项目
	public static String COST_ITEM_4 = "4";// 生成回访项目

	// 通用，不区分具体表
	public static int IS_DELETE_0 = 0; // 未删除
	public static int IS_DELETE_1 = 1; // 已删除

	// kqds_member_record_sh 表的 sqstatus字段值含义 1 申请中 2 同意 3拒绝 4已确认
	public static int MEMBER_SH_STATUS_1 = 1;
	public static int MEMBER_SH_STATUS_2 = 2;
	public static int MEMBER_SH_STATUS_3 = 3;
	public static int MEMBER_SH_STATUS_4 = 4;

	// 部门类型
	public static String DEPT_TYPE_0 = "0"; // 咨询
	public static String DEPT_TYPE_1 = "1"; // 医生
	public static String DEPT_TYPE_2 = "2"; // 网电
	public static String DEPT_TYPE_3 = "3"; // 营销
	public static String DEPT_TYPE_4 = "4"; // 护士
	public static String DEPT_TYPE_5 = "5"; // 业绩科室
	public static String DEPT_TYPE_6 = "6"; // 客服

	// 仓库 出入库类型
	public static String CK_IN_0 = "0"; // 采购入库
	public static String CK_IN_2 = "2"; // 换货入库
	public static String CK_IN_4 = "4"; // 其他入库
	public static String CK_IN_8 = "8"; // 调拨入库
	public static String CK_IN_9 = "9"; // 二次入库
	public static String CK_OUT_1 = "1"; // 领用出库
	public static String CK_OUT_3 = "3"; // 换货出库
	public static String CK_OUT_5 = "5"; // 退货出库
	public static String CK_OUT_7 = "7"; // 其他出库
	public static String CK_OUT_10 = "10"; // 其他出库

	// 是否门诊
	public static String IS_HOS_1 = "1"; // 是
	// 开启连锁
	public static String IS_OPEN_CHAIN_FUNC_1 = "1"; // 是

	// 开启试用
	public static String IS_OPEN_TRY_FUNC_1 = "1"; // 是

	// 加工单状态
	public static String JG_STATUS_0 = "0"; // 未发件
	public static String JG_STATUS_1 = "1"; // 已发件
	public static String JG_STATUS_2 = "2"; // 已回件
	public static String JG_STATUS_3 = "3"; // 戴走
	public static String JG_STATUS_4 = "4"; // 返工
	public static String JG_STATUS_5 = "5"; // 作废

	// KQDS_refundAct 退款状态
	public static int REFUND_STATUS_1 = 1; // 申请中
	public static int REFUND_STATUS_2 = 2; // 已同意
	public static int REFUND_STATUS_3 = 3; // 未同意
	public static int REFUND_STATUS_4 = 4; // 已退款\

	// receive status 接诊
	public static int RECEIVE_STATUS_0 = 0; // 未接诊
	public static int RECEIVE_STATUS_1 = 1; // 已接诊

	// 挂号
	public static int REG_STATUS_0 = 0; // 等待治疗
	public static int REG_STATUS_1 = 1; // 等待结账
	// 挂号时，业务逻辑判断
	public static int REG_CHECK_100 = 100;
	// 挂号是否被撤销
	public static String REG_DELETE_0 = "0"; // 未撤销
	public static String REG_DELETE_1 = "1"; // 已撤销
	public static String REG_CJ_STATUS_0 = "0"; // 未成交
	public static String REG_CJ_STATUS_1 = "1"; // 已成交

	// 顾客类型
	public static int USER_TYPE_0 = 0; // 门诊患者
	public static int USER_TYPE_1 = 1; // 网电患者

	// 积分
	public static String JFZJ = "1"; // 积分增加
	public static String JFJS = "2"; // 积分减少

	// 是否分页
	public static String PAGE_FLAG_0 = "0"; // 不分页
	public static String PAGE_FLAG_1 = "1"; // 分页

	// 时间格式，补全使用
	public static String TIME_START = " 00:00:00";
	public static String TIME_END = " 23:59:59";

	public static String HOUR_START = " 00:00";
	public static String HOUR_END = " 23:59";

	// 回访状态
	public static String VISIT_STATUS_0 = "0"; // 未回访
	public static String VISIT_STATUS_1 = "1"; // 已回访
	public static int VISIT_IS_FIRST_1 = 1; // 首次回访
	public static int VISIT_IS_AUTO_1 = 1; // 自动回访

	public static String ROOT_DIR;

	public static String ROOT_DIR_STATIC;

}
