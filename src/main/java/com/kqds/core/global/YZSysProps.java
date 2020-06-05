package com.kqds.core.global;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.kqds.util.sys.YZUtility;

/**
 * 系统属性
 * 
 * @author jpt
 * @version 1.0
 * @date 2006-8-3
 */
@SuppressWarnings({ "rawtypes" })
public class YZSysProps {
	// 全局的配置属性信息，这样的信息应该尽量的少，因为需要常驻内存
	private static Properties props = null;
	// 操作系统名字
	private static String osName = System.getProperty("os.name");

	/**
	 * rootDir路径
	 */
	public static final String ROOT_DIR = "rootDir";
	/**
	 * webroot路径
	 */
	public static final String WEB_ROOT_DIR = "webRootDir";
	/**
	 * JSP路径
	 */
	public static final String JSP_ROOT_DIR = "jspRootDir";
	/**
	 * 最大的上传文件字节数
	 */
	public static final String MAX_UPLOAD_FILE_SIZE = "maxUploadFileSize";
	/**
	 * 文件上传临时目录
	 */
	public static final String FILE_UPLOAD_TEMP_DIR = "fileUploadTempDir";

	/**
	 * 上传附件的路径
	 */
	public static final String ATTACH_FILES_PATH = "attachFilePath";

	/**
	 * 取得系统配置属性
	 * 
	 * @param key
	 * @return
	 */
	public static String getProp(String key) {
		if (props == null) {
			return "";
		}
		String prop = (String) props.getProperty(key);
		if (prop != null) {
			prop = prop.trim();
		} else {
//			prop = "";//原有代码先注释掉  2019-4-30 13:05 shaoyp
			prop = "sqlserver";//添加代码
		}
		return prop;
	}

	/**
	 * 取得字符串类型的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		return getProp(key);
	}

	/**
	 * 取得整型值
	 * 
	 * @param key
	 * @return
	 */
	public static int getInt(String key) {
		int rtInt = 0;
		try {
			String strValue = getString(key);
			if (!YZUtility.isNullorEmpty(strValue)) {
				rtInt = Integer.parseInt(strValue);
			}
		} catch (Exception ex) {
		}
		return rtInt;
	}

	/**
	 * 取得长整型值
	 * 
	 * @param key
	 * @return
	 */
	public static long getLong(String key) {
		long rtLong = 0;
		try {
			String strValue = getString(key);
			if (!YZUtility.isNullorEmpty(strValue)) {
				rtLong = Long.parseLong(strValue);
			}
		} catch (Exception ex) {
		}
		return rtLong;
	}

	/**
	 * 设置系统配置属性
	 * 
	 * @param props
	 */
	public static void setProps(Properties props) {
		YZSysProps.props = props;
	}

	public static void updateProp(Object key, Object value) {
		YZSysProps.props.put(key, value);
	}

	/**
	 * 设置系统配置属性
	 * 
	 * @param props
	 */
	public static void addProps(Map aProps) {
		if (aProps == null) {
			return;
		}
		Iterator iKeys = aProps.keySet().iterator();
		while (iKeys.hasNext()) {
			String key = (String) iKeys.next();
			String value = (String) aProps.get(key);
			props.put(key, value);
		}
	}

	/**
	 * 取得WEB-INF物理路径
	 * 
	 * @return
	 */
	public static String getWebPath() {
		return getString(ROOT_DIR) + File.separator + getString(WEB_ROOT_DIR) + File.separator + getString(JSP_ROOT_DIR);
	}

	/**
	 * 取得WEB-INF物理路径
	 * 
	 * @return
	 */
	public static String getWebInfPath() {
		return getString(ROOT_DIR) + File.separator + getString(WEB_ROOT_DIR) + File.separator + getString(JSP_ROOT_DIR) + File.separator + "WEB-INF";
	}

	public static String getPath() {
		return getString(ROOT_DIR);
	}

	/**
	 * 取得附件上传缓存路径
	 * 
	 * @return
	 */
	public static String getUploadCatchPath() {
		String cachPath = getString(FILE_UPLOAD_TEMP_DIR);
		if (cachPath == null) {
			cachPath = getString(ROOT_DIR) + File.separator + "catch";
		}
		if (cachPath.indexOf(":") == 1 || cachPath.indexOf("/") == 0) {
			return cachPath;
		}
		// 相对路径
		cachPath = getString(ROOT_DIR + File.separator + cachPath);
		return cachPath;
	}

	/**
	 * 取得附件上传路径
	 * 
	 * @return
	 */
	public static String getAttachPath() {
		String attachPath = getString(ATTACH_FILES_PATH);
		if (attachPath == null) {
			attachPath = getString(ROOT_DIR) + File.separator + "attach";
		}
		if (attachPath.indexOf(":") == 1 || attachPath.indexOf("/") == 0) {
			return attachPath;
		}
		// 相对路径
		attachPath = getString(ROOT_DIR) + File.separator + attachPath;
		return attachPath;
	}

	public static boolean isWindows() {
		return YZUtility.null2Empty(osName).toLowerCase().indexOf("windows") >= 0;
	}

	public static boolean isLinux() {
		return YZUtility.null2Empty(osName).toLowerCase().indexOf("linux") >= 0;
	}
}
