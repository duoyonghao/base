package com.kqds.util.sys.sys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kqds.entity.sys.YZPriv;
import com.kqds.service.sys.priv.YZPrivLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.spring.BeanUtil;

/**
 * 此类为角色权限相关类
 * 
 * @author Administrator
 * 
 */
public class UserPrivUtil {

	public static YZPrivLogic logic = (YZPrivLogic) BeanUtil.getBean("userPrivLogic");
	public static List<String> userQxNameList = new ArrayList<String>();

	public static String ADMIN_SEQ_ID = "1"; // 管理员角色主键

	public static String qxFlag0_maxDiscount = "maxDiscount";
	public static String qxFlag1_canLookPhone = "canLookPhone";
	public static String qxFlag2_canKd = "canKd";
	public static String qxFlag3_canHj = "canHj";
	public static String qxFlag4_canPaiban = "canPaiban";
	public static String qxFlag5_canRoom = "canRoom";
	public static String qxFlag6_canOrderOther = "canOrderOther";
	public static String qxFlag7_canEditKf = "canEditKf";
	public static String qxFlag8_canEditWd = "canEditWd";
	public static String qxFlag9_canEditCost = "canEditCost";
	public static String qxFlag10_canEditOrder = "canEditOrder";
	public static String qxFlag11_iszj = "iszj";
	public static String qxFlag12_canEditJdr = "canEditJdr"; // 可以修改建档人
	public static String qxFlag13_canEditAskperson = "canEditAskperson"; // 可以修改充值咨询
	public static String qxFlag14_canExportPhoneNumber = "canExportPhoneNumber"; // 可以导出手机号码
	public static String qxFlag15_canEditPhone = "canEditPhone"; // 可以修改手机号码
	public static String qxFlag16_canEditHzly = "canEditHzly"; // 可以修改患者来源
	public static String qxFlag17_canZheKouOnly = "canZheKouOnly"; // 只能修改折扣
	public static String qxFlag18_canDelCk = "canDelCk";// 允许删除仓库出入库记录
	public static String qxFlag19_maxVoidmoney = "maxVoidmoney";// 最大免除金额
	public static String qxFlag20_canModRoom = "canModRoom";// 是否允许修改他人手术室预约

	static {
		// 顺序涉及到取值，不允许调整
		userQxNameList.add(qxFlag0_maxDiscount); // 最大折扣
		userQxNameList.add(qxFlag1_canLookPhone);// 禁止查看手机号 0可以 1不可以
		userQxNameList.add(qxFlag2_canKd);// 禁止开单 0可以 1不可以
		userQxNameList.add(qxFlag3_canHj);// 禁止回件 0可以 1不可以
		userQxNameList.add(qxFlag4_canPaiban);// 是否可以排班 0不可以 1 可以
		userQxNameList.add(qxFlag5_canRoom); // 允许预约手术室 0不可以 1 可以
		userQxNameList.add(qxFlag6_canOrderOther); // 是否具备给他人预约患者的权限 0不可以 1 可以
		userQxNameList.add(qxFlag7_canEditKf);// 禁止修改开发人 0可以 1不可以
		userQxNameList.add(qxFlag8_canEditWd);// 允许修改网电信息 1允许0不允许
		userQxNameList.add(qxFlag9_canEditCost); // 是否允许修改他人费用单
		userQxNameList.add(qxFlag10_canEditOrder);
		userQxNameList.add(qxFlag11_iszj); // 是否专家 0不是 1 是
		// ### 后面根据业务需求，继续添加，不用增加字段
		userQxNameList.add(qxFlag12_canEditJdr);
		userQxNameList.add(qxFlag13_canEditAskperson);
		userQxNameList.add(qxFlag14_canExportPhoneNumber);
		userQxNameList.add(qxFlag15_canEditPhone);
		userQxNameList.add(qxFlag16_canEditHzly);
		userQxNameList.add(qxFlag17_canZheKouOnly);
		userQxNameList.add(qxFlag18_canDelCk);
		userQxNameList.add(qxFlag19_maxVoidmoney);
		userQxNameList.add(qxFlag20_canModRoom);
	}

	public static String getPrivValueByKey(String privKey, HttpServletRequest request) throws Exception {
		int index = 0;
		for (String key : userQxNameList) {
			if (key.equals(privKey)) {
				break;
			}
			index++;
		}
		YZPriv userPriv = (YZPriv) request.getSession().getAttribute(ConstUtil.LOGIN_USER_PRIV); // 从session中获取
		String[] privArray = logic.preCheckInit(userPriv, request);
		String privValue = privArray[index];
		privValue = privValue.trim();
		if (UserPrivUtil.qxFlag19_maxVoidmoney.equals(privKey) && YZUtility.isNullorEmpty(privValue)) {
			privValue = "0";
		}

		return privValue; // 返回
	}

	public static void main(String[] args) {
		// String qxstr = ", , , , , , , , , , , , ,";
		// String[] t = qxstr.split(",");
		// System.out.println(t.length);
	}

}
