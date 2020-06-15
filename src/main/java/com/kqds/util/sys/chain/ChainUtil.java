package com.kqds.util.sys.chain;

import com.kqds.core.global.YZSysProps;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.spring.BeanUtil;
import com.kqds.util.sys.sys.SysParaUtil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainUtil {
  private static Logger log = LoggerFactory.getLogger(ChainUtil.class);
  
  private static YZDeptLogic deptLogic = (YZDeptLogic)BeanUtil.getBean("deptLogic");
  
  public static List<YZDept> getHosList(String organization) throws Exception {
    List<YZDept> list = deptLogic.getHosList(organization);
    return list;
  }
  
  public static boolean isOpenChain() {
    String is_open_chain_func = YZSysProps.getProp(SysParaUtil.IS_OPEN_CHAIN_FUNC);
    if (YZUtility.isNullorEmpty(is_open_chain_func))
      return false; 
    if (ConstUtil.IS_OPEN_CHAIN_FUNC_1.equals(is_open_chain_func))
      return true; 
    return false;
  }
  
  public static boolean isOpenTry() {
    String is_open_chain_select = YZSysProps.getProp(SysParaUtil.IS_OPEN_CHAIN_SELECT);
    if (YZUtility.isNullorEmpty(is_open_chain_select))
      return false; 
    if (ConstUtil.IS_OPEN_TRY_FUNC_1.equals(is_open_chain_select))
      return true; 
    return false;
  }
  
  public static boolean isHasAttributeInClass(String fieldname, Class cls) {
    Field[] fields = cls.getDeclaredFields();
    boolean flag = false;
    for (int i = 0; i < fields.length; i++) {
      if (fields[i].getName().equals(fieldname)) {
        flag = true;
        break;
      } 
    } 
    return flag;
  }
  
  public static void main(String[] args) {
    String sql = "select * from a where 1=1 aa";
    String[] sqlArray = sql.split("1=1");
    sqlArray.length;
  }
  
  public static String addSqlHosCodeFilter4Delete(String sql, Class cls, HttpServletRequest request) throws Exception {
    return addSqlHosCodeFilter(sql, cls, request);
  }
  
  public static String addSqlHosCodeFilter4Query(String sql, Class cls, HttpServletRequest request) throws Exception {
    return addSqlHosCodeFilter(sql, cls, request);
  }
  
  public static String addSqlHosCodeFilter4Update(String sql, Class cls, HttpServletRequest request) throws Exception {
    return addSqlHosCodeFilter(sql, cls, request);
  }
  
  private static String addSqlHosCodeFilter(String sql, Class cls, HttpServletRequest request) throws Exception {
    String organization = request.getParameter("organization");
    if (YZUtility.isNullorEmpty(organization))
      return sql; 
    if (!isHasAttributeInClass("organization", cls))
      return sql; 
    if (!sql.contains("1=1"))
      return sql; 
    StringBuffer sqlNew = new StringBuffer("");
    String[] sqlArray = sql.split("1=1");
    if (sqlArray.length == 1) {
      sqlNew.append(sqlArray[0]);
      sqlNew.append(" 1=1 ");
      sqlNew.append(" and organization = '" + organization + "' ");
    } else if (sqlArray.length == 2) {
      sqlNew.append(sqlArray[0]);
      sqlNew.append(" 1=1 ");
      sqlNew.append(" and organization = '" + organization + "' ");
      sqlNew.append(sqlArray[1]);
    } else {
      throw new Exception("SQL语句错误，只允许出现一次 1=1");
    } 
    log.error("### SQL IN ### " + sql);
    log.error("### SQL OUT ### " + sqlNew.toString());
    return sqlNew.toString();
  }
  
  public static String addUrlChainFilter(String url, HttpServletRequest request) throws Exception {
    String organization = request.getParameter("organization");
    if (YZUtility.isNullorEmpty(organization))
      return url; 
    return String.valueOf(url) + "?organization=" + organization;
  }
  
  public static String getOrganizationFromUrl(HttpServletRequest request) throws Exception {
    String organization = request.getParameter("organization");
    if (YZUtility.isNullorEmpty(organization)) {
      log.error("###连锁模式下，organization参数值为空，请求Url为：" + request.getRequestURL());
      throw new Exception("###连锁模式下，organization参数值为空，请求Url为：" + request.getRequestURL());
    } 
    return organization;
  }
  
  public static String getOrganizationFromUrlCanNull(HttpServletRequest request) throws Exception {
    String organization = request.getParameter("organization");
    if (YZUtility.isNullorEmpty(organization))
      return ""; 
    return organization;
  }
  
  public static String getCurrentOrganization(HttpServletRequest request) throws Exception {
    String organization = (String)request.getSession().getAttribute(SessionUtil.LOGIN_ORGANIZATION);
    if (YZUtility.isNullorEmpty(organization))
      log.error("###连锁模式下，session中organization参数值为空。"); 
    return organization;
  }
  
  public static String getCurrentOrganization(HttpSession session) throws Exception {
    String organization = (String)session.getAttribute(SessionUtil.LOGIN_ORGANIZATION);
    if (YZUtility.isNullorEmpty(organization)) {
      log.error("###session中organization参数值为空。");
      throw new Exception("###session中organization参数值为空。");
    } 
    return organization;
  }
  
  public static String getCurrentOrganizationName(HttpServletRequest request) throws Exception {
    String organization = (String)request.getSession().getAttribute(SessionUtil.LOGIN_ORGANIZATION);
    if (YZUtility.isNullorEmpty(organization)) {
      log.error("###连锁模式下，session中organization参数值为空。");
      throw new Exception("###连锁模式下，session中organization参数值为空。");
    } 
    String organizationName = getOrganizationNameByOrgCode(organization, request);
    return organizationName;
  }
  
  public static String getOrganizationNameFromUrl(HttpServletRequest request) throws Exception {
    String organization = request.getParameter("organization");
    if (YZUtility.isNullorEmpty(organization))
      return "公用"; 
    String organizationName = getOrganizationNameByOrgCode(organization, request);
    return organizationName;
  }
  
  public static String getOrganizationNameByOrgCode(String organization, HttpServletRequest request) throws Exception {
    String organizationName = "";
    Map<String, String> filters = new HashMap<>();
    filters.put("DEPT_PARENT", "0");
    filters.put("DEPT_CODE", organization);
    List<YZDept> list = (ArrayList)deptLogic.loadList(TableNameUtil.SYS_DEPT, filters);
    if (list == null || list.size() == 0)
      throw new Exception("###连锁模式下，门诊名称获取失败，门诊编号为：" + organization); 
    if (list.size() > 1)
      throw new Exception("###连锁模式下，一级门诊编号出现多个，门诊编号为：" + organization); 
    organizationName = ((YZDept)list.get(0)).getDeptName();
    return organizationName;
  }
  
  public static YZDept getOrganizationByOrgCode(String organization, HttpServletRequest request) throws Exception {
    Map<String, String> filter = new HashMap<>();
    filter.put("DEPT_PARENT", "0");
    filter.put("DEPT_CODE", organization);
    List<YZDept> list = (ArrayList)deptLogic.loadList(TableNameUtil.SYS_DEPT, filter);
    if (list == null || list.size() == 0)
      throw new Exception("###连锁模式下，门诊名称获取失败，门诊编号为：" + organization); 
    if (list.size() > 1)
      throw new Exception("###连锁模式下，一级门诊编号出现多个，门诊编号为：" + organization); 
    return list.get(0);
  }
  
  public static String getOrganizationNameByPerson(YZPerson person, HttpServletRequest request) throws Exception {
    String deptId = person.getDeptId();
    YZDept dpt = deptLogic.getTopDept(deptId);
    if (!YZUtility.isNullorEmpty(dpt.getDeptByname()))
      return dpt.getDeptByname(); 
    return dpt.getDeptName();
  }
  
  public static String getOrganizationByPersonSeqId(String userSeqId, HttpServletRequest request) throws Exception {
    String organization = null;
    try {
      YZPerson person = (YZPerson)deptLogic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, userSeqId);
      if (person == null)
        throw new Exception("系统用户不存在"); 
      String deptId = person.getDeptId();
      YZDept dpt = deptLogic.getTopDept(deptId);
      if (dpt == null)
        throw new Exception("系统用户所在门诊不存在"); 
      organization = dpt.getDeptCode();
    } catch (Exception ex) {
      throw ex;
    } 
    return organization;
  }
  
  public static String getDeptNameByPerson(YZPerson person, HttpServletRequest request) throws Exception {
    String deptId = person.getDeptId();
    YZDept dpt = (YZDept)deptLogic.loadObjSingleUUID(TableNameUtil.SYS_DEPT, deptId);
    if (dpt != null)
      return dpt.getDeptName(); 
    return "";
  }
  
  public static String getOrgName(HttpServletRequest request) {
    return (String)request.getSession().getAttribute(SessionUtil.LOGIN_ORGANIZATION_NAME);
  }
  
  public static String getOrgPrintName(HttpServletRequest request) {
    return (String)request.getSession().getAttribute(SessionUtil.LOGIN_ORGANIZATION_PRINTNAME);
  }
  
  public static String getOrgAddress(HttpServletRequest request) {
    return (String)request.getSession().getAttribute(SessionUtil.LOGIN_ORGANIZATION_ADDRESS);
  }
  
  public static String getOrgTelNo(HttpServletRequest request) {
    return (String)request.getSession().getAttribute(SessionUtil.LOGIN_ORGANIZATION_TELNO);
  }
}
