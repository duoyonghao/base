package com.kqds.core.util.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class YZDigestUtility {
	private static final char[] HEX_ARRAY = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	// 取得摘要的实例

	private static MessageDigest md = null;
	static {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
		}
	}

	/**
	 * 获取MD5摘要
	 * 
	 * @param inputData
	 * @return
	 */
	synchronized public static byte[] md5(byte[] inputData) {
		if (md == null) {
			return null;
		}
		md.reset();
		md.update(inputData);
		return md.digest();
	}

	/**
	 * 生成MD5摘要
	 * 
	 * @param srcBytes
	 * @return
	 */
	synchronized public static String md5Hex(byte[] srcBytes) {
		byte[] md5Array = md5(srcBytes);
		StringBuffer rtStr = new StringBuffer();
		for (int i = 0; i < md5Array.length; i++) {
			byte high = (byte) ((md5Array[i] >> 4) & 0x0F);
			byte low = (byte) (md5Array[i] & 0x0F);
			rtStr.append(HEX_ARRAY[high]);
			rtStr.append(HEX_ARRAY[low]);
		}
		return rtStr.toString();
	}

}
