package com.kqds.core.listener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kqds.core.global.YZSysProps;
import com.kqds.core.load.YZConfigLoader;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.sys.SysParaUtil;
import com.kqds.util.sys.usb.SentinelkeyUtil;

public class SystemInitUtil {
	private static Logger log = LoggerFactory.getLogger(YZConfigLoader.class);

	/**
	 * 系统启动初始化
	 */
	public static void sysStartInit() {
		// 将properties文件加载到全局变量
		String dbparamFile = ConstUtil.ROOT_DIR + "WEB-INF" + File.separator + "classes/config" + File.separator + "dbparam.properties";
		String sysConfFile = ConstUtil.ROOT_DIR + "WEB-INF" + File.separator + "classes/config" + File.separator + "sysconfig.properties";
		String kaipiao = ConstUtil.ROOT_DIR + "WEB-INF" + File.separator + "classes/config" + File.separator + "kaipiao.properties";
		String sms = ConstUtil.ROOT_DIR + "WEB-INF" + File.separator + "classes/config" + File.separator + "sms.properties";
		String weixin = ConstUtil.ROOT_DIR + "WEB-INF" + File.separator + "classes/config" + File.separator + "weixin.properties";
		String record = ConstUtil.ROOT_DIR + "WEB-INF" + File.separator + "classes/config" + File.separator + "record.properties";
		String app = ConstUtil.ROOT_DIR + "WEB-INF" + File.separator + "classes/config" + File.separator + "app.properties";

		List<String> propFileList = new ArrayList<String>();
		propFileList.add(dbparamFile);
		propFileList.add(sysConfFile);
		propFileList.add(kaipiao);
		propFileList.add(sms);
		propFileList.add(weixin);
		propFileList.add(record);
		propFileList.add(app);
		YZSysProps.setProps(YZConfigLoader.loadSysProps(propFileList));

		// 加密狗中的端口数设定
		String usekey = YZSysProps.getProp(SysParaUtil.IS_USE_USBKEY);
		if (!YZUtility.isNullorEmpty(usekey) && !("0").equals(usekey)) {
			// 使用加密狗
			String dogData = SentinelkeyUtil.readFormKey();
			log.error("系统端口数为：" + dogData);
			if (!YZUtility.isNullorEmpty(dogData)) {
				Map<String, String> datamap = new HashMap<String, String>();
				datamap.put(SysParaUtil.MAX_USER_ACCOUNT, dogData);
				YZSysProps.addProps(datamap);
			}
		}
	}

}
