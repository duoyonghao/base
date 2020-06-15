package com.kqds.util.sys;

import com.kqds.core.global.YZSysProps;
import com.kqds.core.util.YZGuid;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.fkfs.YZFkfsLogic;
import com.kqds.service.sys.para.YZParaLogic;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.spring.BeanUtil;
import com.kqds.util.sys.sys.SysParaUtil;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionUtil {
  private static Logger logger = LoggerFactory.getLogger(SessionUtil.class);
  
  private static YZParaLogic paraLogic = (YZParaLogic)BeanUtil.getBean("paraLogic");
  
  private static YZFkfsLogic fkfsLogic = (YZFkfsLogic)BeanUtil.getBean("fkfsLogic");
  
  public static String ONLINE_INFO = "ONLINE_INFO";
  
  public static Map<String, HttpSession> Session_MAP = new ConcurrentHashMap<>();
  
  public static String LOGIN_ORGANIZATION = "LOGIN_ORGANIZATION";
  
  public static String LOGIN_ORGANIZATION_TELNO = "LOGIN_ORGANIZATION_TELNO";
  
  public static String LOGIN_ORGANIZATION_ADDRESS = "LOGIN_ORGANIZATION_ADDRESS";
  
  public static String LOGIN_ORGANIZATION_PRINTNAME = "LOGIN_ORGANIZATION_PRINTNAME";
  
  public static String LOGIN_ORGANIZATION_NAME = "LOGIN_ORGANIZATION_NAME";
  
  public static String visualstaffYyrl = "visualstaffYyrl";
  
  public static String visualstaff = "visualstaff";
  
  public static String LOGIN_SYS_PARA = "LOGIN_SYS_PARA";
  
  public static String LOGIN_SYS_COSTFIELD = "LOGIN_SYS_COSTFIELD";
  
  public static String LOGIN_SYS_MEMBERFIELD = "LOGIN_SYS_MEMBERFIELD";
  
  public static String SESSION_TOKEN = "SESSION_TOKEN";
  
  public static YZPerson getLoginPerson(HttpServletRequest request) {
    YZPerson person = (YZPerson)request.getSession().getAttribute("LOGIN_USER");
    return person;
  }
  
  public static void setSysPara4UserLogin(HttpServletRequest request) throws Exception {
    JSONObject sysPara = paraLogic.getSysPara(ChainUtil.getCurrentOrganization(request));
    request.getSession().setAttribute(LOGIN_SYS_PARA, sysPara);
  }
  
  public static void getCostField(HttpServletRequest request) throws Exception {
    JSONObject sysPara = fkfsLogic.getCostField();
    request.getSession().setAttribute(LOGIN_SYS_COSTFIELD, sysPara);
  }
  
  public static void getMemberField(HttpServletRequest request) throws Exception {
    JSONObject sysPara = fkfsLogic.getMemberField();
    request.getSession().setAttribute(LOGIN_SYS_MEMBERFIELD, sysPara);
  }
  
  public static String getVisualstaff(HttpServletRequest request) throws Exception {
    return request.getSession().getAttribute("visualstaff").toString();
  }
  
  public static void setUserInfo4UserLogin(YZPerson person, HttpSession session, String ip, HttpServletRequest request) throws Exception {
    String sessionToken = YZGuid.getRawGuid();
    session.setAttribute("LOGIN_USER", person);
    session.setAttribute(SESSION_TOKEN, sessionToken);
  }
  
  public static void setHosInfo4UserLogin(HttpServletRequest request, HttpSession session, YZPerson person) throws Exception {
    String organization = null;
    if (ChainUtil.isOpenChain()) {
      organization = request.getParameter("organization");
    } else {
      organization = YZSysProps.getString(SysParaUtil.ORGANIZATION);
    } 
    if (ChainUtil.isOpenTry())
      organization = ChainUtil.getOrganizationByPersonSeqId(person.getSeqId(), request); 
    session.setAttribute(LOGIN_ORGANIZATION, organization);
    YZDept dpt = ChainUtil.getOrganizationByOrgCode(organization, request);
    if (!YZUtility.isNullorEmpty(dpt.getTelNo())) {
      session.setAttribute(LOGIN_ORGANIZATION_TELNO, dpt.getTelNo());
    } else {
      session.setAttribute(LOGIN_ORGANIZATION_TELNO, "");
    } 
    if (!YZUtility.isNullorEmpty(dpt.getDeptAddress())) {
      session.setAttribute(LOGIN_ORGANIZATION_ADDRESS, dpt.getDeptAddress());
    } else {
      session.setAttribute(LOGIN_ORGANIZATION_ADDRESS, "");
    } 
    if (!YZUtility.isNullorEmpty(dpt.getPrintName())) {
      session.setAttribute(LOGIN_ORGANIZATION_PRINTNAME, dpt.getPrintName());
    } else {
      session.setAttribute(LOGIN_ORGANIZATION_PRINTNAME, dpt.getDeptName());
    } 
    session.setAttribute(LOGIN_ORGANIZATION_NAME, dpt.getDeptName());
  }
  
  public static void invalidateSession(HttpSession session) {
    try {
      if (session != null) {
        YZPerson person = (YZPerson)session.getAttribute("LOGIN_USER");
        Session_MAP.remove(person.getUserId());
        session.invalidate();
      } 
    } catch (Exception e) {
      logger.error(e.getMessage());
    } 
  }
  
  public static void removeUserFromOnlineMap(String userid) {
    Iterator<String> iterator = Session_MAP.keySet().iterator();
    while (iterator.hasNext()) {
      String key = iterator.next();
      if (userid.equals(key))
        iterator.remove(); 
    } 
  }
  
  public static boolean isValidSession(HttpSession session, String key) {
    if (session == null || key == null)
      return false; 
    if (session.getAttribute(key) == null)
      return false; 
    return true;
  }
}
