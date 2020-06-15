package com.kqds.controller.sys.system;

import com.kqds.core.system.adapter.YZLoginAdapter;
import com.kqds.core.system.validator.YZConfineNumberValidator;
import com.kqds.core.system.validator.YZExistUserValidator;
import com.kqds.core.system.validator.YZForbidLoginValidator;
import com.kqds.core.system.validator.YZIsLeaveUserValidator;
import com.kqds.core.system.validator.YZLoginValidator;
import com.kqds.core.system.validator.YZPasswordValidator;
import com.kqds.core.system.validator.YZRepeatLoginValidator;
import com.kqds.core.util.auth.YZPassEncrypt;
import com.kqds.entity.sys.QueryFirstLog;
import com.kqds.entity.sys.YZButton;
import com.kqds.entity.sys.YZMenuModel;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.sys.YZPriv;
import com.kqds.service.sys.button.YZButtonLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.service.sys.system.YZSystemLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/YZSystemAct"})
public class YZSystemAct {
  private Logger logger = LoggerFactory.getLogger(YZSystemAct.class);
  
  @Autowired
  private YZSystemLogic sysLogic;
  
  @Autowired
  private YZButtonLogic buttonLogic;
  
  @Autowired
  private YZPersonLogic personLogic;
  
  @RequestMapping({"/toLogIndex.act"})
  public ModelAndView toLogIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/admin/log/index.jsp");
    return mv;
  }
  
  @RequestMapping({"/doLoginIn.act"})
  public String doLoginIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String repeatlogin = request.getParameter("repeatlogin");
      String userName = request.getParameter("userName");
      String organization = request.getParameter("organization");
      YZPerson person = null;
      QueryFirstLog queryLog = null;
      if (YZUtility.isNullorEmpty(userName)) {
        userName = "";
      } else {
        person = this.personLogic.queryPerson(userName, request);
        queryLog = this.personLogic.queryFirstLog(userName);
      } 
      YZLoginAdapter loginAdapter = new YZLoginAdapter(request, person);
      if (!loginAdapter.validate((YZLoginValidator)new YZExistUserValidator())) {
        YZUtility.RETURN_OBJ(loginAdapter.getRetObj(), response, this.logger);
        return null;
      } 
      if (!person.getOrganization().contains(organization)) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("retState", "2");
        jSONObject.put("code", Integer.valueOf(2));
        jSONObject.put("msg", "门诊错误");
        YZUtility.RETURN_OBJ(jSONObject, response, this.logger);
        return null;
      } 
      if (!loginAdapter.validate((YZLoginValidator)new YZConfineNumberValidator())) {
        YZUtility.RETURN_OBJ(loginAdapter.getRetObj(), response, this.logger);
        return null;
      } 
      if (!loginAdapter.validate((YZLoginValidator)new YZIsLeaveUserValidator())) {
        YZUtility.RETURN_OBJ(loginAdapter.getRetObj(), response, this.logger);
        return null;
      } 
      if (!loginAdapter.validate((YZLoginValidator)new YZForbidLoginValidator())) {
        YZUtility.RETURN_OBJ(loginAdapter.getRetObj(), response, this.logger);
        return null;
      } 
      if (!loginAdapter.validate((YZLoginValidator)new YZPasswordValidator())) {
        YZUtility.RETURN_OBJ(loginAdapter.getRetObj(), response, this.logger);
        return null;
      } 
      if (YZUtility.isNullorEmpty(repeatlogin)) {
        if (!loginAdapter.validate((YZLoginValidator)new YZRepeatLoginValidator())) {
          YZUtility.RETURN_OBJ(loginAdapter.getRetObj(), response, this.logger);
          return null;
        } 
      } else {
        HttpSession session = (HttpSession)SessionUtil.Session_MAP.get(person.getUserId());
        SessionUtil.invalidateSession(session);
      } 
      if (queryLog == null) {
        QueryFirstLog queryFirstLog = new QueryFirstLog();
        queryFirstLog.setSeqId(YZUtility.getUUID());
        queryFirstLog.setUserId(person.getUserId());
        queryFirstLog.setUserName(person.getUserName());
        queryFirstLog.setOrganization(organization);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        queryFirstLog.setTime(df.format(new Date()));
        this.personLogic.saveQueryLog(queryFirstLog);
      } 
      this.sysLogic.loginSuccess(person, request, response);
      JSONObject successRt = new JSONObject();
      successRt.put("retState", "0");
      successRt.put("code", Integer.valueOf(0));
      successRt.put("msg", "登录成功");
      YZUtility.RETURN_OBJ(successRt, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR("登录失败", false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/QueryFirstLog.act"})
  public String QueryFirstLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String userName = request.getParameter("userName");
    try {
      YZPerson person = this.personLogic.queryPerson(userName, request);
      QueryFirstLog queryLog = this.personLogic.queryFirstLog(userName);
      if (queryLog != null && YZPassEncrypt.isValidPas("123456", person.getPassword())) {
        YZUtility.DEAL_SUCCESS(null, "true", response, this.logger);
      } else {
        YZUtility.DEAL_SUCCESS(null, "false", response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/doLogout.act"})
  public String doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
    HttpSession session = request.getSession(false);
    SessionUtil.invalidateSession(session);
    response.sendRedirect(String.valueOf(request.getContextPath()) + "/login.jsp");
    return null;
  }
  
  @RequestMapping({"/loginAdmin.act"})
  public String loginAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = (YZPerson)request.getSession().getAttribute("LOGIN_USER");
      List<String> funclist = this.sysLogic.listUserMenu(person);
      List<YZMenuModel> menulist = this.sysLogic.getMainMenu(person, funclist);
      menulist = this.sysLogic.lazyLoadJsonModel(funclist, menulist);
      request.setAttribute("menuList", menulist);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return "/admin/index/index.jsp";
  }
  
  @RequestMapping({"/loginIndexFront.act"})
  public String loginIndexFront(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = (YZPerson)request.getSession().getAttribute("LOGIN_USER");
      List<String> funclist = this.sysLogic.listUserMenu(person);
      List<YZMenuModel> menulist = this.sysLogic.getMainMenu(person, funclist);
      menulist = this.sysLogic.lazyLoadJsonModel(funclist, menulist);
      request.setAttribute("menuList", menulist);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return "/kqdsFront/index.jsp";
  }
  
  @RequestMapping({"/getButtonList.act"})
  public String getButtonList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String menuId = request.getParameter("menuId");
    try {
      List<YZButton> btnlist = this.buttonLogic.getButtonList(menuId);
      YZPriv userPriv = (YZPriv)request.getSession().getAttribute(ConstUtil.LOGIN_USER_PRIV);
      String currUserButtons = userPriv.getFuncbutton();
      if (currUserButtons == null)
        return null; 
      currUserButtons = currUserButtons.trim();
      if ("".equals(currUserButtons))
        return null; 
      if (!currUserButtons.endsWith(","))
        currUserButtons = String.valueOf(currUserButtons) + ","; 
      if (!currUserButtons.startsWith(","))
        currUserButtons = "," + currUserButtons; 
      List<YZButton> list = new ArrayList<>();
      for (YZButton btn : btnlist) {
        if (currUserButtons.matches(".*,\\s*" + btn.getSeqId() + "\\s*,.*"))
          list.add(btn); 
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
}
