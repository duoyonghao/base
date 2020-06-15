package com.kqds.core.global;

import com.kqds.util.sys.YZUtility;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class YZSysProps {
  private static Properties props = null;
  
  private static String osName = System.getProperty("os.name");
  
  public static final String ROOT_DIR = "rootDir";
  
  public static final String WEB_ROOT_DIR = "webRootDir";
  
  public static final String JSP_ROOT_DIR = "jspRootDir";
  
  public static final String MAX_UPLOAD_FILE_SIZE = "maxUploadFileSize";
  
  public static final String FILE_UPLOAD_TEMP_DIR = "fileUploadTempDir";
  
  public static final String ATTACH_FILES_PATH = "attachFilePath";
  
  public static String getProp(String key) {
    if (props == null)
      return ""; 
    String prop = props.getProperty(key);
    if (prop != null) {
      prop = prop.trim();
    } else {
      prop = "sqlserver";
    } 
    return prop;
  }
  
  public static String getString(String key) {
    return getProp(key);
  }
  
  public static int getInt(String key) {
    int rtInt = 0;
    try {
      String strValue = getString(key);
      if (!YZUtility.isNullorEmpty(strValue))
        rtInt = Integer.parseInt(strValue); 
    } catch (Exception exception) {}
    return rtInt;
  }
  
  public static long getLong(String key) {
    long rtLong = 0L;
    try {
      String strValue = getString(key);
      if (!YZUtility.isNullorEmpty(strValue))
        rtLong = Long.parseLong(strValue); 
    } catch (Exception exception) {}
    return rtLong;
  }
  
  public static void setProps(Properties props) {
    YZSysProps.props = props;
  }
  
  public static void updateProp(Object key, Object value) {
    props.put(key, value);
  }
  
  public static void addProps(Map aProps) {
    if (aProps == null)
      return; 
    Iterator<String> iKeys = aProps.keySet().iterator();
    while (iKeys.hasNext()) {
      String key = iKeys.next();
      String value = (String)aProps.get(key);
      props.put(key, value);
    } 
  }
  
  public static String getWebPath() {
    return String.valueOf(getString("rootDir")) + File.separator + getString("webRootDir") + File.separator + getString("jspRootDir");
  }
  
  public static String getWebInfPath() {
    return String.valueOf(getString("rootDir")) + File.separator + getString("webRootDir") + File.separator + getString("jspRootDir") + File.separator + "WEB-INF";
  }
  
  public static String getPath() {
    return getString("rootDir");
  }
  
  public static String getUploadCatchPath() {
    String cachPath = getString("fileUploadTempDir");
    if (cachPath == null)
      cachPath = String.valueOf(getString("rootDir")) + File.separator + "catch"; 
    if (cachPath.indexOf(":") == 1 || cachPath.indexOf("/") == 0)
      return cachPath; 
    cachPath = getString("rootDir" + File.separator + cachPath);
    return cachPath;
  }
  
  public static String getAttachPath() {
    String attachPath = getString("attachFilePath");
    if (attachPath == null)
      attachPath = String.valueOf(getString("rootDir")) + File.separator + "attach"; 
    if (attachPath.indexOf(":") == 1 || attachPath.indexOf("/") == 0)
      return attachPath; 
    attachPath = String.valueOf(getString("rootDir")) + File.separator + attachPath;
    return attachPath;
  }
  
  public static boolean isWindows() {
    return (YZUtility.null2Empty(osName).toLowerCase().indexOf("windows") >= 0);
  }
  
  public static boolean isLinux() {
    return (YZUtility.null2Empty(osName).toLowerCase().indexOf("linux") >= 0);
  }
}
