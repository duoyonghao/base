package com.kqds.entity.sys;

import java.util.Comparator;

/**
 * 菜单展现根据排序号 升序排序
 * 
 * @author Administrator
 * 
 */
public class YZMenuCompartor implements Comparator<YZMenuModel> {
	@Override
	public int compare(YZMenuModel m1, YZMenuModel m2) {
		return m1.getOrderno().compareTo(m2.getOrderno());

	}

	// @Override
	// public int compare(JSONObject m1, JSONObject m2) {
	// Integer o1 = 0;
	// Integer o2 = 0;
	// if (!YZUtility.isNullorEmpty(m1.get("orderno").toString())) {
	// o1 = m1.getInt("orderno");
	// }
	//
	// if (!YZUtility.isNullorEmpty(m2.get("orderno").toString())) {
	// o2 = m2.getInt("orderno");
	// }
	//
	// return o1.compareTo(o2);
	//
	// }
}