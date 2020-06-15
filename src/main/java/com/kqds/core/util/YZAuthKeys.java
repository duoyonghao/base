package com.kqds.core.util;

import java.util.Map;

public class YZAuthKeys {

	/**
	 * 取得加密密码
	 * 
	 * @param params
	 *            用于取得密码的参数，用于扩展用
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static char[] getPassword(Map params) {
		return "BLMDfSiSEUSeRnwxL89HnBbCUgBsYBjDbvHJGA==".toCharArray();
	}

	/**
	 * 取得Salt
	 * 
	 * @param params
	 *            用于取得Salt的参数，用于扩展用
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static byte[] getSalt(Map params) {
		byte[] salt = new byte[] { (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8 };
		return salt;
	}

	/**
	 * 取得迭代次数
	 * 
	 * @param params
	 *            用于取得迭代次数的参数，用于扩展用
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static int getItCnt(Map params) {
		return 3;
	}
}
