package com.kqds.core.util.auth;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

import com.kqds.core.global.YZConst;
import com.kqds.core.util.YZSecurityUtility;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 密码加密、解密、验证相关
 * 
 * @author Administrator
 * 
 */
public class YZAuthenticator {

	private static boolean IS_OPEN_ENCRY = false; // true 开启加密，false 关闭加密

	/**
	 * 新增
	 * 
	 * @param obj
	 * @param tablename
	 * @throws Exception
	 */
	public static void encryKqdsUserInfo4Add(Object obj, String tablename) throws Exception {
		if (!IS_OPEN_ENCRY) { // 如果关闭加密
			return;
		}

		// 电话录音

		// 微信预约

	}

	/**
	 * 修改
	 * 
	 * @param obj
	 * @param tablename
	 * @param conn
	 * @throws Exception
	 */
	public static void encryKqdsUserInfo4Edit(Object obj, String tablename) throws Exception {
		if (!IS_OPEN_ENCRY) { // 如果关闭加密
			return;
		}

	}

	/**
	 * 解密口腔大师用户信息
	 * 
	 * @param obj
	 * @param tablename
	 * @throws Exception
	 */
	public static Object decryKqdsUserInfo(Object obj, String tablename) throws Exception {
		if (!IS_OPEN_ENCRY) { // 如果关闭加密
			return obj;
		}

		return obj;
	}

	/**
	 * 解密口腔大师用户信息
	 * 
	 * @param obj
	 * @param tablename
	 * @throws Exception
	 */
	public static List<Object> decryKqdsUserInfo(List<Object> list, String tablename) throws Exception {
		if (!IS_OPEN_ENCRY) { // 如果关闭加密
			return list;
		}

		// 患者档案
		if (TableNameUtil.KQDS_USERDOCUMENT.equals(tablename.toUpperCase())) {
			List<Object> newList = new ArrayList<Object>();
			for (Object object : list) {
				Object newObject = decryKqdsUserInfo(object, tablename);
				newList.add(newObject);
			}
			return newList;
		}

		// 回访表
		if (TableNameUtil.KQDS_VISIT.equals(tablename.toUpperCase())) {
			List<Object> newList = new ArrayList<Object>();
			for (Object object : list) {
				Object newObject = decryKqdsUserInfo(object, tablename);
				newList.add(newObject);
			}
			return newList;
		}

		// 短信表
		if (TableNameUtil.KQDS_SMS.equals(tablename.toUpperCase())) {
			List<Object> newList = new ArrayList<Object>();
			for (Object object : list) {
				Object newObject = decryKqdsUserInfo(object, tablename);
				newList.add(newObject);
			}
			return newList;
		}

		// 微信预约

		// 电话录音

		return list;
	}

	/**
	 * 解密手机号码-- getJsonList使用
	 * 
	 * @param colName
	 * @param colValue
	 * @return
	 * @throws Exception
	 */
	public static String decryKqdsPhonenumber(String colName, String colValue) throws Exception {
		if (!IS_OPEN_ENCRY) { // 如果关闭加密
			return colValue;
		}
		String newValue = null;
		/**
		 * 患者档案表的 电话1、电话2 回访表的 电话 短信表的 电话
		 */
		if (("phonenumber1".equals(colName) || "phonenumber2".equals(colName) || "telphone".equals(colName) || "sendphone".equals(colName))
				&& YZUtility.isNotNullOrEmpty(colValue)) {
			newValue = ciphDecryptStr(colValue);
			return newValue;
		}
		return colValue;
	}

	/**
	 * 解密手机号码-- getBeanList使用
	 * 
	 * @param colName
	 * @param colValue
	 * @return
	 * @throws Exception
	 */
	public static Object decryKqdsPhonenumber(String colName, Object colValue) throws Exception {
		if (!IS_OPEN_ENCRY) { // 如果关闭加密
			return colValue;
		}
		/**
		 * 患者档案表的 电话1、电话2 回访表的 电话 短信表的 电话
		 */
		if ("phonenumber1".equals(colName) || "phonenumber2".equals(colName) || "telphone".equals(colName) || "sendphone".equals(colName)) {
			if (colValue == null) {
				return colValue;
			}
			String colValueStr = (String) colValue; // 转换成字符串
			if (YZUtility.isNotNullOrEmpty(colValueStr))
				colValueStr = ciphDecryptStr(colValueStr);
			return colValueStr;
		}
		return colValue;
	}

	/**
	 * 加密手机号码，用于查询条件
	 * 
	 * @param phonenumber
	 * @return
	 * @throws Exception
	 */
	public static String encryKqdsPhonenumber(String phonenumber) throws Exception {
		if (!IS_OPEN_ENCRY) { // 如果关闭加密
			return phonenumber;
		}
		if (YZUtility.isNullorEmpty(phonenumber)) {
			return phonenumber;
		}
		return ciphEncryptStr(phonenumber);
	}

	/**
	 * 如果开启加密开关，则Phonenumber无法Like，只能等于
	 * 
	 * @param phonenumber
	 * @return
	 * @throws Exception
	 */
	public static String phonenumberLike(String colname, String phonenumber) throws Exception {
		phonenumber = encryKqdsPhonenumber(phonenumber); // 是否加密，放在加密的方法里，不在这里做
		if (IS_OPEN_ENCRY) { // 开启加密，无法使用like模糊查询
			return " " + colname + " = '" + phonenumber + "' ";
		} else {
			return " " + colname + " like '%" + phonenumber + "%' ";
		}
	}

	/**
	 * 加密密码，生成密文
	 * 
	 * @param pass
	 * @return
	 * @throws Exception
	 */
	public static String ciphEncryptStr(String srcPass) throws Exception {
		Cipher cipher = YZSecurityUtility.getPassWordCipher(Cipher.ENCRYPT_MODE);
		byte[] passBytes = srcPass.getBytes(YZConst.DEFAULT_CODE);
		passBytes = cipher.doFinal(passBytes);
		return new BASE64Encoder().encode(passBytes);
	}

	/**
	 * 解密密码
	 * 
	 * @param encryptPass
	 * @return
	 * @throws Exception
	 */
	public static String ciphDecryptStr(String encryptPass) throws Exception {
		Cipher cipher = YZSecurityUtility.getPassWordCipher(Cipher.DECRYPT_MODE);
		byte[] passBytes = new BASE64Decoder().decodeBuffer(encryptPass);
		return new String(cipher.doFinal(passBytes), YZConst.DEFAULT_CODE);
	}
}
