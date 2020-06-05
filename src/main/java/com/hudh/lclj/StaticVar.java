package com.hudh.lclj;

public class StaticVar {
	
	//配置操作内容xml路径
	public final static String HUDH_LCLJ_FILEPATH = "com/hudh/lclj/util/hudh_lclj_nodes.xml";
	
	//不同临床路径类型
	public final static String HUDH_LCLJ_SINGLEORMULTINOBONE = "SingleOrMultiNoBone";//单颗及多颗无植骨
	public final static String HUDH_LCLJ_SINGLEORMULTIBONE = "SingleOrMultiBone";//单颗及多颗植骨
	public final static String HUDH_LCLJ_LOCATORNOBONE = "LocatorNoBone";//Locator无植骨 
	public final static String HUDH_LCLJ_LOCATORBONE = "LocatorBone";//Locator植骨 
	public final static String HUDH_LCLJ_ALLONXNOBONE = "AllonxNoBone";//All-on-x无植骨
	public final static String HUDH_LCLJ_ALLONXBONE = "AllonxBone";//All-on-x植骨
	
	//流程环节
	public final static String HUDH_LCLJ_FLOW_SS = "手术";
	public final static String HUDH_LCLJ_FLOW_SHGC = "术后观察";
	public final static String HUDH_LCLJ_FLOW_DY = "戴牙";
	
	//跟踪类型
	public final static String HUDH_LCLJ_DDK = "1"; //单颗多颗
	public final static String HUDH_LCLJ_LOCATOR = "2"; //Locator
	public final static String HUDH_LCLJ_ALLONX = "3"; //All-on-x
	public final static String HUDH_LCLJ_JKFZ = "4"; //即刻负重
	
	//是否
	public final static String HUDH_LCLJ_YES = "是";
	public final static String HUDH_LCLJ_NO = "否";
	
	public final static String HUDH_LCLJ_FLOWSTA_END = "结束";
	
	
	//流程节点状态办理状态 0：未完成  1：进行中   2： 已完成   3：超期
	public final static String HUDH_LCLJ_FLOWSTA_WWC = "0"; 
	public final static String HUDH_LCLJ_FLOWSTA_JXZ = "1";
	public final static String HUDH_LCLJ_FLOWSTA_YWC = "2";
	public final static String HUDH_LCLJ_FLOWSTA_CQ = "3";
	
	public final static Integer HUDH_LCLJ_FLOWSTA_INTEGER_WWC = 0; 
	public final static Integer HUDH_LCLJ_FLOWSTA_INTEGER_JXZ = 1;
	public final static Integer HUDH_LCLJ_FLOWSTA_INTEGER_YWC = 2;
	public final static Integer HUDH_LCLJ_FLOWSTA_INTEGER_CQ = 3;
	
	//临床路径管理员及代办人标识，用于从配置表获取当前临床路径备注审核人
	public final static String LCLJ_ADMIN = "LCLJ_ADMIN"; 
	public final static String LCLJ_AGENCY_USER = "LCLJ_AGENCY_USER";
	//药库管理员审批人员
	public final static String DRUGS_IN_ADMIN = "DRUGS_IN_ADMIN";
	//仓库管理员审批人员
	public final static String GOODS_IN_ADMIN = "GOODS_IN_ADMIN";
	public final static String SYS_POSITION = "SYS_POSITION";
	public final static String SYS_DEPT = "SYS_DEPT";
	//仓库管理员审批人员（西直门）
	public final static String GOODS_IN_ADMIN_XZM = "GOODS_IN_ADMIN_XZM";
	
	public final static String LCLJ_HASPASS_YES = "YES";
	public final static String LCLJ_HASPASS_NO = "NO";
	
	//添加药品信息一二级标识
//	public final static String HUDH_BASE_TYPE = "yp577";
//	public final static String HUDH_NEXT_TYPE = "yp193";
//	public final static String HUDH_STATUS = "1";
	
	//正式环境配置级别分类
	public final static String HUDH_BASE_TYPE = "yp610";
	public final static String HUDH_NEXT_TYPE = "yp70";
	public final static String HUDH_STATUS = "1";
	
	//正式环境配置药库级别分类
	public final static String HUDH_BASE_TYPE_XZM = "yp331";
	public final static String HUDH_NEXT_TYPE_XZM = "yp695";
	
}
