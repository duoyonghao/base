package com.kqds.service.sys.system;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZMenu;
import com.kqds.entity.sys.YZMenuCompartor;
import com.kqds.entity.sys.YZMenuModel;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.sys.YZPriv;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.func.YZFuncLogic;
import com.kqds.service.sys.menu.YZMenuLogic;
import com.kqds.service.sys.online.YZOnlineLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YZSystemLogic extends BaseLogic {
  @Autowired
  private YZPersonLogic personLogic;
  
  @Autowired
  private YZOnlineLogic onLineLogic;
  
  @Autowired
  private YZMenuLogic menuLogic;
  
  @Autowired
  private YZFuncLogic funcLogic;
  
  @Autowired
  private DaoSupport dao;
  
  public List<YZMenuModel> lazyLoadJsonModel4zTree(List<String> menuSet, List<YZMenuModel> menulist) throws Exception {
    for (int i = 0; i < menulist.size(); i++) {
      YZMenuModel currmenu = menulist.get(i);
      List<YZMenuModel> childmlist = getMenuList(menuSet, currmenu);
      for (YZMenuModel yhMenuModel : childmlist)
        yhMenuModel.setParentid(currmenu.getMenuid()); 
      menulist.addAll(childmlist);
      if (childmlist != null && childmlist.size() > 0)
        lazyLoadJsonModel4zTree(menuSet, childmlist); 
    } 
    return menulist;
  }
  
  public List<YZMenuModel> getMainMenu(YZPerson person, List<String> funclist) throws Exception {
    List<YZMenuModel> menulist = new ArrayList<>();
    for (String id : funclist) {
      if (id.length() == 2) {
        YZMenu menu = this.menuLogic.queryMenu(id);
        if (menu == null)
          continue; 
        YZMenuModel medel = new YZMenuModel();
        medel.setSeqId(menu.getSeqId());
        medel.setMenuid(menu.getMenuId());
        medel.setMenuname(menu.getMenuName());
        medel.setIcon(menu.getImage());
        String menuStr = funclist.toString();
        if (!menuStr.matches(".*[ ,\\[]" + id + "\\d+.*")) {
          medel.setLeaf(0);
        } else {
          medel.setLeaf(1);
        } 
        menulist.add(medel);
      } 
    } 
    return menulist;
  }
  
  public List<YZMenuModel> lazyLoadJsonModel(List<String> funclist, List<YZMenuModel> menulist) throws Exception {
    for (int i = 0; i < menulist.size(); i++) {
      YZMenuModel currmenu = menulist.get(i);
      List<YZMenuModel> childmlist = getMenuList(funclist, currmenu);
      currmenu.setHasmenu(childmlist);
      if (currmenu.getHasmenu() != null && currmenu.getHasmenu().size() > 0)
        lazyLoadJsonModel(funclist, currmenu.getHasmenu()); 
    } 
    return menulist;
  }
  
  public List<YZMenuModel> getMenuList(List<String> funclist, YZMenuModel model) throws NumberFormatException, Exception {
    String funcStr = funclist.toString();
    String parent = model.getMenuid();
    List<YZMenuModel> clist = new ArrayList<>();
    for (String id : funclist) {
      if (!id.matches(String.valueOf(parent) + "\\d{2}"))
        continue; 
      YZMenuModel menu = this.funcLogic.queryFuncMoel(id);
      if (menu == null)
        continue; 
      if (!funcStr.matches(".*[ ,\\[]" + menu.getMenuid() + "\\d+.*")) {
        menu.setLeaf(0);
      } else {
        menu.setLeaf(1);
      } 
      clist.add(menu);
    } 
    YZMenuCompartor mcompare = new YZMenuCompartor();
    Collections.sort(clist, (Comparator<? super YZMenuModel>)mcompare);
    return clist;
  }
  
  public List<String> listUserMenu(YZPerson person) throws Exception {
    List<YZPriv> priv = new ArrayList<>();
    String userPriv = "";
    if (person != null)
      userPriv = person.getUserPriv(); 
    if (userPriv == null || "".equals(userPriv.trim()))
      userPriv = ""; 
    YZPriv up = (YZPriv)this.dao.loadObjSingleUUID(TableNameUtil.SYS_PRIV, userPriv.trim());
    if (up != null)
      priv.add(up); 
    HashSet<String> menuSet = new HashSet<>();
    for (YZPriv p : priv)
      menuSet.addAll(Arrays.asList(YZUtility.null2Empty(p.getFuncIdStr()).split(","))); 
    HashSet<String> addSet = new HashSet<>(menuSet);
    for (String s : menuSet) {
      s = s.trim();
      if (s.length() > 4) {
        addSet.add(s.substring(0, 4));
        continue;
      } 
      if (s.length() > 2)
        addSet.add(s.substring(0, 2)); 
    } 
    List<String> list = new ArrayList<>(addSet);
    Collections.sort(list, new Comparator<String>() {
          public int compare(String arg0, String arg1) {
            if (YZUtility.isNullorEmpty(arg0))
              arg0 = ""; 
            if (YZUtility.isNullorEmpty(arg1))
              arg1 = ""; 
            return arg0.compareTo(arg1);
          }
        });
    return list;
  }
  
  public void loginSuccess(YZPerson person, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    SessionUtil.setHosInfo4UserLogin(request, session, person);
    if (session.getAttribute("LOGIN_USER") == null) {
      SysLogUtil.logNoRequest("登录日志", SysLogUtil.SYS_PERSON, "登录成功", TableNameUtil.SYS_PERSON, person, YZUtility.getIpAddr(request), session);
      SessionUtil.setUserInfo4UserLogin(person, session, YZUtility.getIpAddr(request), request);
      this.onLineLogic.addOnline(person, String.valueOf(session.getAttribute(SessionUtil.SESSION_TOKEN)), session, request);
      SessionUtil.Session_MAP.put(person.getUserId(), session);
      person.setLastVisitTime(new Date());
      this.dao.updateSingleUUID(TableNameUtil.SYS_PERSON, person);
      visualStaffSet(person, session);
      SessionUtil.setSysPara4UserLogin(request);
      SessionUtil.getCostField(request);
      SessionUtil.getMemberField(request);
    } else {
      YZPerson loginPerson = (YZPerson)session.getAttribute("LOGIN_USER");
      if (loginPerson.getSeqId() != person.getSeqId()) {
        SessionUtil.invalidateSession(session);
        loginSuccess(person, request, response);
      } 
    } 
  }
  
  public void visualStaffSet(YZPerson person, HttpSession session) throws Exception {
    YZPriv privObj = (YZPriv)this.dao.loadObjSingleUUID(TableNameUtil.SYS_PRIV, person.getUserPriv());
    if (privObj == null)
      throw new Exception("该用户没有配置角色"); 
    session.setAttribute(ConstUtil.LOGIN_USER_PRIV, privObj);
    session.setAttribute(SessionUtil.visualstaffYyrl, getVisualstaffYyrl(privObj, person));
    session.setAttribute(SessionUtil.visualstaff, getVisualstaff(privObj, person));
  }
  
  public String getVisualstaffYyrl(YZPriv privObj, YZPerson person) throws Exception {
    String orderVisualPersons = privObj.getOrderVisualPerson();
    String orderVisualDepts = privObj.getOrderVisualDept();
    Set<String> orderPersonIdSet = new HashSet<>();
    if (person != null)
      orderPersonIdSet.add(person.getSeqId()); 
    if (!YZUtility.isNullorEmpty(orderVisualPersons)) {
      String[] pIdArray = orderVisualPersons.split(",");
      byte b;
      int i;
      String[] arrayOfString1;
      for (i = (arrayOfString1 = pIdArray).length, b = 0; b < i; ) {
        String personId = arrayOfString1[b];
        if (!YZUtility.isNullorEmpty(personId))
          orderPersonIdSet.add(personId); 
        b++;
      } 
    } 
    if (!YZUtility.isNullorEmpty(orderVisualDepts)) {
      List<JSONObject> plist = this.personLogic.getPersonIdListByDeptIds(orderVisualDepts);
      for (JSONObject p : plist)
        orderPersonIdSet.add(p.getString("seq_id")); 
    } 
    Iterator<String> orderIt = orderPersonIdSet.iterator();
    StringBuffer orderVisualPersonIdBf = new StringBuffer();
    while (orderIt.hasNext()) {
      String pid = orderIt.next();
      orderVisualPersonIdBf.append(pid).append(",");
    } 
    if (orderVisualPersonIdBf.length() > 0)
      orderVisualPersonIdBf.deleteCharAt(orderVisualPersonIdBf.length() - 1); 
    String orderVisualPersonIds4Query = YZUtility.ConvertStringIds4Query(orderVisualPersonIdBf.toString());
    return orderVisualPersonIds4Query;
  }
  
  public String getVisualstaff(YZPriv privObj, YZPerson person) throws Exception {
    String visualPersons = privObj.getVisualPerson();
    String visualDepts = privObj.getVisualDept();
    Set<String> personIdSet = new HashSet<>();
    if (person != null)
      personIdSet.add(person.getSeqId()); 
    if (!YZUtility.isNullorEmpty(visualPersons)) {
      String[] pIdArray = visualPersons.split(",");
      byte b;
      int i;
      String[] arrayOfString1;
      for (i = (arrayOfString1 = pIdArray).length, b = 0; b < i; ) {
        String personId = arrayOfString1[b];
        if (!YZUtility.isNullorEmpty(personId))
          personIdSet.add(personId); 
        b++;
      } 
    } 
    if (!YZUtility.isNullorEmpty(visualDepts)) {
      List<JSONObject> plist = this.personLogic.getPersonIdListByDeptIds(visualDepts);
      for (JSONObject p : plist)
        personIdSet.add(p.getString("seq_id")); 
    } 
    Iterator<String> it = personIdSet.iterator();
    StringBuffer visualPersonIdBf = new StringBuffer();
    while (it.hasNext()) {
      String pid = it.next();
      visualPersonIdBf.append(pid).append(",");
    } 
    if (visualPersonIdBf.length() > 0)
      visualPersonIdBf.deleteCharAt(visualPersonIdBf.length() - 1); 
    String visualPersonIds4Query = YZUtility.ConvertStringIds4Query(visualPersonIdBf.toString());
    return visualPersonIds4Query;
  }
}
