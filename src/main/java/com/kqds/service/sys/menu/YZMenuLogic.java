package com.kqds.service.sys.menu;

import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZMenu;
import com.kqds.entity.sys.YZMenuCompartorJson;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.func.YZFuncLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YZMenuLogic extends BaseLogic {
  @Autowired
  private YZFuncLogic funcLogic;
  
  @Autowired
  private DaoSupport dao;
  
  public YZMenu queryMenu(String menuId) throws Exception {
    List<YZMenu> list = (List<YZMenu>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_MENU) + ".queryMenu", menuId);
    if (list != null && list.size() > 0)
      return list.get(0); 
    return null;
  }
  
  public int deleteBySeqIds(String seqids, HttpServletRequest request) throws Exception {
    List<String> idList = YZUtility.ConvertString2List(seqids);
    int count = ((Integer)this.dao.delete(String.valueOf(TableNameUtil.SYS_MENU) + ".deleteBySeqIds", idList)).intValue();
    SysLogUtil.log(SysLogUtil.DELETE, SysLogUtil.SYS_MENU, seqids, TableNameUtil.SYS_MENU, request);
    return count;
  }
  
  public JSONObject getMenuById(String menuId) throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_MENU) + ".getMenuById", menuId);
    if (list != null && list.size() > 0)
      return list.get(0); 
    return null;
  }
  
  public List<JSONObject> getAllMenuList() throws Exception {
    List<JSONObject> list = (List<JSONObject>)this.dao.findForList(String.valueOf(TableNameUtil.SYS_MENU) + ".getAllMenuList", null);
    return list;
  }
  
  public List<JSONObject> getMenuList(List<String> menuSet, JSONObject model) throws NumberFormatException, Exception {
    String menuStr = menuSet.toString();
    String parent = model.getString("menuId");
    List<JSONObject> clist = new ArrayList<>();
    for (String id : menuSet) {
      if (!id.matches(String.valueOf(parent) + "\\d{2}"))
        continue; 
      JSONObject menu = this.funcLogic.getFuncById(id);
      if (menu == null)
        continue; 
      menu.remove("icon");
      if (!menuStr.matches(".*[ ,\\[]" + menu.getString("menuId") + "\\d+.*")) {
        menu.put("isParent", Boolean.valueOf(false));
      } else {
        menu.put("isParent", Boolean.valueOf(true));
      } 
      clist.add(menu);
    } 
    YZMenuCompartorJson mcompare = new YZMenuCompartorJson();
    Collections.sort(clist, (Comparator<? super JSONObject>)mcompare);
    return clist;
  }
  
  public List<JSONObject> lazyLoadJsonTree4Manage(List<String> funcSet, List<JSONObject> menulist) throws Exception {
    for (int i = 0; i < menulist.size(); i++) {
      JSONObject currmenu = menulist.get(i);
      List<JSONObject> childmlist = getMenuList(funcSet, currmenu);
      for (JSONObject menuM : childmlist) {
        menuM.put("parentid", currmenu.getString("menuId"));
        menuM.put("menuName", menuM.getString("funcName"));
      } 
      menulist.addAll(childmlist);
      if (childmlist != null && childmlist.size() > 0)
        lazyLoadJsonTree4Manage(funcSet, childmlist); 
    } 
    return menulist;
  }
  
  public List<JSONObject> getMainMenu4Manage(YZPerson person, List<String> funclist) throws Exception {
    List<JSONObject> menulist = getAllMenuList();
    String menuStr = funclist.toString();
    for (JSONObject menu : menulist) {
      if (!menuStr.matches(".*[ ,\\[]" + menu.getString("menuId") + "\\d+.*")) {
        menu.put("isParent", Boolean.valueOf(false));
      } else {
        menu.put("isParent", Boolean.valueOf(true));
      } 
      menu.put("parentid", "0");
    } 
    return menulist;
  }
  
  public int countByMenuName(YZMenu dp) throws Exception {
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_MENU) + ".countByMenuName", dp)).intValue();
    return count;
  }
  
  public int countByMenuId(YZMenu dp) throws Exception {
    int count = ((Integer)this.dao.findForObject(String.valueOf(TableNameUtil.SYS_MENU) + ".countByMenuId", dp)).intValue();
    return count;
  }
  
  public String getSelectMenuTree(String menuId) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("[");
    getSelectMenuTreeJson(menuId, sb, 0);
    if (sb.charAt(sb.length() - 1) == ',')
      sb.deleteCharAt(sb.length() - 1); 
    sb.append("]");
    return sb.toString();
  }
  
  public void getSelectMenuTreeJson(String parentId, StringBuffer sb, int level) throws Exception {
    List<JSONObject> list = null;
    if (level == 0) {
      list = getAllMenuList();
    } else {
      list = this.funcLogic.getListByParentId(parentId);
    } 
    for (int i = 0; i < list.size(); i++) {
      String flag = "&nbsp;├";
      if (i == list.size() - 1)
        flag = "&nbsp;└"; 
      String tmp = "";
      for (int j = 0; j < level; j++)
        tmp = String.valueOf(tmp) + "&nbsp;│"; 
      flag = String.valueOf(tmp) + flag;
      Map dp = (Map)list.get(i);
      String menuId = (String)dp.get("menuId");
      String menuName = (String)dp.get("menuName");
      sb.append("{");
      sb.append("text:'" + flag + YZUtility.encodeSpecial(menuName) + "',");
      sb.append("value:'" + menuId + "'");
      sb.append("},");
      getSelectMenuTreeJson(menuId, sb, level + 1);
    } 
  }
}
