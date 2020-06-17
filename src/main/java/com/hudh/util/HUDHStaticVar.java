package com.hudh.util;

/**
 * 静态常量
 * @author XKY
 *
 */
public class HUDHStaticVar {
	//日期格式化时间
	public final static String DATE_FORMAT_YMDHMS24 = "yyyy-MM-dd HH:mm:ss";
	public final static String DATE_FORMAT_YMDHMS12 = "yyyy-MM-dd hh:mm:ss";
	
	//完成标识
	public final static String COMPLETE_STATE_YWC = "已完成";
	public final static String COMPLETE_STATE_WWC = "未完成";
	public final static String COMPLETE_STATE_JXZ = "进行中";
	
	public final static String zzAskPersonDeptId = "4b88b74c-9373-4b5f-9d53-3c115de7a7e4"; //种植咨询部门id
	public final static String zjAskPersonDeptId = "d915e83c-862e-40eb-a4a0-792e34db701e"; //正畸咨询部门id
	
	public final static String zzAskPersonDictId = "zz373"; //网电建档投放项目-种植选项字典对应id对应 KQDS_NET_ORDER表的acceptType字段
	public final static String zjAskPersonDictId = "zj634"; //网电建档投放项目-正畸选项字典对应id对应 KQDS_NET_ORDER表的acceptType字段
}
