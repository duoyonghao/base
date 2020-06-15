package com.kqds.util.sys.sys;

import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public class SysParaUtil {
  public static String NOT_SHOW_KIND_DICTS = "NOT_SHOW_KIND_DICTS";
  
  public static String JZFL_CHUZHEN_SEQID = "JZFL_CHUZHEN_SEQID";
  
  public static String YYFL_CHUZHEN_SEQID = "YYFL_CHUZHEN_SEQID";
  
  public static String IS_OPEN_ZHONGZHI_BINGLI = "IS_OPEN_ZHONGZHI_BINGLI";
  
  public static String REG_SUCCESS_ITEM_SORT = "REG_SUCCESS_ITEM_SORT";
  
  public static String COST_SUCCESS_ITEM_SORT = "COST_SUCCESS_ITEM_SORT";
  
  public static String COST_SUCCESS_ITEM = "COST_SUCCESS_ITEM";
  
  public static String PRIV_ZHUGUAN_SEQID = "PRIV_ZHUGUAN_SEQID";
  
  public static String PRIV_ZONGJINGLI_SEQID = "PRIV_ZONGJINGLI_SEQID";
  
  public static String PRIV_DOCTOR_SEQID = "PRIV_DOCTOR_SEQID";
  
  public static String PRIV_YUANZHANG_SEQID = "PRIV_YUANZHANG_SEQID";
  
  public static String PRIV_WANGDIAN_SEQID = "PRIV_WANGDIAN_SEQID";
  
  public static String PRIV_SHICHANG_SEQID = "PRIV_SHICHANG_SEQID";
  
  public static String PRIV_SHOUFEI_SEQID = "PRIV_SHOUFEI_SEQID";
  
  public static String PRIV_ASK_SEQID = "PRIV_ASK_SEQID";
  
  public static String PRIV_CK_SEQID = "PRIV_CK_SEQID";
  
  public static String RIGHT_YCHF = "RIGHT_YCHF";
  
  public static String ZY_LYCK = "ZY_LYCK";
  
  public static String SYS_POSITION = "SYS_POSITION";
  
  public static String SMS_BIRTH_BEFORE = "SMS_BIRTH_BEFORE";
  
  public static String SMS_YYTX_BEFORE = "SMS_YYTX_BEFORE";
  
  public static String YZ_CONFINE_NUMBER = "YZ_CONFINE_NUMBER";
  
  public static String IS_OPEN_PAIBAN_ORDER_FUNC = "IS_OPEN_PAIBAN_ORDER_FUNC";
  
  public static String HYK_NO_PASSWORD = "HYK_NO_PASSWORD";
  
  public static String HYK_BINDING = "HYK_BINDING";
  
  public static String RECORD_FILE_DIR = "RECORD_FILE_DIR";
  
  public static String RECORD_WEBSITE_URL = "RECORD_WEBSITE_URL";
  
  public static String IS_OPEN_RECORD_FUNC = "IS_OPEN_RECORD_FUNC";
  
  public static String IS_OPEN_CHAIN_FUNC = "IS_OPEN_CHAIN_FUNC";
  
  public static String IS_OPEN_WECHAT = "IS_OPEN_WECHAT";
  
  public static String IS_OPEN_CHAIN_SELECT = "IS_OPEN_CHAIN_SELECT";
  
  public static String UPLOAD_URL = "UPLOAD_URL";
  
  public static String VIDEO_URL = "VIDEO_URL";
  
  public static String ORGANIZATION = "ORGANIZATION";
  
  public static final String MAX_USER_ACCOUNT = "maxUserAccount";
  
  public static final String IS_USE_USBKEY = "IS_USE_USBKEY";
  
  public static final String COST_INTEGRAL = "COST_INTEGRAL";
  
  public static final String BLCODE_START = "BLCODE_START";
  
  public static boolean isOpenBLCodeFunc(HttpServletRequest request) {
    String blcode_start = getSysValueByName(request, "BLCODE_START");
    if (YZUtility.isNullorEmpty(blcode_start))
      return false; 
    return true;
  }
  
  public static long REQ_COST_TIME = 2000L;
  
  public static String getSysValueByName(HttpServletRequest request, String paraName) {
    String paraValue = "";
    try {
      JSONObject sysPara = (JSONObject)request.getSession().getAttribute(SessionUtil.LOGIN_SYS_PARA);
      paraValue = sysPara.getString(paraName);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return paraValue;
  }
  
  public static String getFkfsNameByCostField(HttpServletRequest request, String paraName) {
    String paraValue = "";
    try {
      JSONObject sysPara = (JSONObject)request.getSession().getAttribute(SessionUtil.LOGIN_SYS_COSTFIELD);
      paraValue = sysPara.getString(paraName);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return paraValue;
  }
  
  public static String getFkfsNameByMemberField(HttpServletRequest request, String paraName) {
    String paraValue = "";
    try {
      JSONObject sysPara = (JSONObject)request.getSession().getAttribute(SessionUtil.LOGIN_SYS_MEMBERFIELD);
      paraValue = sysPara.getString(paraName);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return paraValue;
  }
}
