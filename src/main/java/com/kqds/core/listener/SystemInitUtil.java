package com.kqds.core.listener;

import com.kqds.core.global.YZSysProps;
import com.kqds.core.load.YZConfigLoader;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.usb.SentinelkeyUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemInitUtil {
  private static Logger log = LoggerFactory.getLogger(YZConfigLoader.class);
  
  public static void sysStartInit() {
    String dbparamFile = String.valueOf(ConstUtil.ROOT_DIR) + "WEB-INF" + File.separator + "classes/config" + File.separator + "dbparam.properties";
    String sysConfFile = String.valueOf(ConstUtil.ROOT_DIR) + "WEB-INF" + File.separator + "classes/config" + File.separator + "sysconfig.properties";
    String kaipiao = String.valueOf(ConstUtil.ROOT_DIR) + "WEB-INF" + File.separator + "classes/config" + File.separator + "kaipiao.properties";
    String sms = String.valueOf(ConstUtil.ROOT_DIR) + "WEB-INF" + File.separator + "classes/config" + File.separator + "sms.properties";
    String weixin = String.valueOf(ConstUtil.ROOT_DIR) + "WEB-INF" + File.separator + "classes/config" + File.separator + "weixin.properties";
    String record = String.valueOf(ConstUtil.ROOT_DIR) + "WEB-INF" + File.separator + "classes/config" + File.separator + "record.properties";
    String app = String.valueOf(ConstUtil.ROOT_DIR) + "WEB-INF" + File.separator + "classes/config" + File.separator + "app.properties";
    List<String> propFileList = new ArrayList<>();
    propFileList.add(dbparamFile);
    propFileList.add(sysConfFile);
    propFileList.add(kaipiao);
    propFileList.add(sms);
    propFileList.add(weixin);
    propFileList.add(record);
    propFileList.add(app);
    YZSysProps.setProps(YZConfigLoader.loadSysProps(propFileList));
    String usekey = YZSysProps.getProp("IS_USE_USBKEY");
    if (!YZUtility.isNullorEmpty(usekey) && !"0".equals(usekey)) {
      String dogData = SentinelkeyUtil.readFormKey();
      log.error("系统端口数为：" + dogData);
      if (!YZUtility.isNullorEmpty(dogData)) {
        Map<String, String> datamap = new HashMap<>();
        datamap.put("maxUserAccount", dogData);
        YZSysProps.addProps(datamap);
      } 
    } 
  }
}
