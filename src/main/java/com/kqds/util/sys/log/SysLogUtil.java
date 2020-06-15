package com.kqds.util.sys.log;

import com.kqds.entity.base.KqdsBcjl;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.app.KQDS_LogLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.spring.BeanUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;

public class SysLogUtil {
  private static KQDS_LogLogic logLogic = (KQDS_LogLogic)BeanUtil.getBean("logLogic");
  
  public static String NEW = "新增";
  
  public static String MODIFY = "编辑";
  
  public static String UPDATE = "更新";
  
  public static String DELETE = "删除";
  
  public static String UPDATE_STATUS = "更新状态";
  
  public static String COPY_ROLE = "复制角色";
  
  public static String SET_FUNC = "设置菜单权限";
  
  public static String SET_BUTTON = "设置按钮权限";
  
  public static String SET_PERSON_PRIV = "设置用户权限";
  
  public static String SET_VISUAL = "设置可见权限";
  
  public static String SET_VISUAL_ORDER = "设置预约可见权限";
  
  public static final String LOGIN = "登录日志";
  
  public static final String LOGIN_PASSWORD_ERROR = "登录密码错误";
  
  public static final String LOGIN_USERID_ERROR = "错误用户名";
  
  public static final String MORE_THAN_CONFINE = "超过最大端口数";
  
  public static final String EMPTY_PASSWORD = "清空密码";
  
  public static final String EIDT_PASSWORD = "修改密码";
  
  public static final String LOGIN_OUT = "退出系统";
  
  public static final String LOGIN_OUT_TIMEOUT = "超时退出系统";
  
  public static final String IS_LEAVE = "已经离职";
  
  public static final String KQDS_BLCT = "病历词条";
  
  public static String SYS_DEPT = "部门";
  
  public static String SYS_DICT = "数据字典";
  
  public static String SYS_BUTTON = "按钮";
  
  public static String SYS_FUNC = "功能菜单";
  
  public static String SYS_MENU = "主菜单";
  
  public static String SYS_ORGANIZATION = "单位";
  
  public static String SYS_PARA = "系统配置";
  
  public static String SYS_PERSON = "系统用户";
  
  public static String SYS_PRIV = "系统角色";
  
  public static String SYS_FKFS = "付款方式";
  
  public static String SYS_REGISTER = "试用注册";
  
  public static void log(String operName, String bcmc, Object jlcontent, String tableName, HttpServletRequest request) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    Object requesttoken = request.getAttribute(ConstUtil.REQUEST_LOG_UUID);
    String requesttokenStr = "";
    if (requesttoken != null)
      requesttokenStr = String.valueOf(requesttoken); 
    KqdsBcjl dp = new KqdsBcjl();
    dp.setIfshow("1");
    dp.setLogtype(Integer.valueOf(1));
    dp.setUsercode(null);
    dp.setRegno(null);
    dp.setRequesttoken(requesttokenStr);
    dp.setOrganization(ChainUtil.getCurrentOrganization(request));
    insertIntoDB(operName, bcmc, jlcontent, tableName, YZUtility.getIpAddr(request), person, request.getSession(), dp);
  }
  
  public static void logNoRequest(String operName, String bcmc, Object jlcontent, String tableName, YZPerson person, String ip, HttpSession session) throws Exception {
    KqdsBcjl dp = new KqdsBcjl();
    dp.setIfshow("1");
    dp.setLogtype(Integer.valueOf(1));
    dp.setUsercode(null);
    dp.setRegno(null);
    dp.setOrganization(ChainUtil.getCurrentOrganization(session));
    insertIntoDB(operName, bcmc, jlcontent, tableName, ip, person, session, dp);
  }
  
  private static void insertIntoDB(String operName, String bcmc, Object jlcontent, String tableName, String ip, YZPerson person, HttpSession session, KqdsBcjl dp) throws Exception {
    String contentStr = null;
    if (jlcontent == null) {
      jlcontent = "";
    } else if (jlcontent instanceof String) {
      contentStr = (String)jlcontent;
    } else if (jlcontent instanceof JSONObject || jlcontent instanceof StringBuffer) {
      contentStr = jlcontent.toString();
    } else {
      contentStr = JSONObject.fromObject(jlcontent).toString();
    } 
    dp.setContent(contentStr);
    String personId = null;
    String deptId = null;
    if (person != null) {
      personId = person.getSeqId();
      deptId = person.getDeptId();
    } 
    String sessiontokenStr = null;
    Object sessiontoken = session.getAttribute(SessionUtil.SESSION_TOKEN);
    if (sessiontoken != null)
      sessiontokenStr = String.valueOf(sessiontoken); 
    dp.setCreateuser(personId);
    dp.setDeptid(deptId);
    dp.setSessiontoken(sessiontokenStr);
    dp.setJlname(operName);
    dp.setBcmc(bcmc);
    dp.setTablename(tableName);
    dp.setIp(ip);
    dp.setSeqId(YZUtility.getUUID());
    dp.setCreatetime(YZUtility.getCurDateTimeStr());
    logLogic.saveSingleUUID(TableNameUtil.KQDS_BCJL, dp);
  }
}
