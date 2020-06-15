package com.kqds.controller.sys.menu;

import com.kqds.entity.sys.YZMenu;
import com.kqds.entity.sys.YZMenuModel;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.sys.YZPriv;
import com.kqds.service.sys.func.YZFuncLogic;
import com.kqds.service.sys.menu.YZMenuLogic;
import com.kqds.service.sys.priv.YZPrivLogic;
import com.kqds.service.sys.system.YZSystemLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"YZMenuAct"})
public class YZMenuAct
{
  private Logger logger = LoggerFactory.getLogger(YZMenuAct.class);
  @Autowired
  private YZFuncLogic funcLogic;
  @Autowired
  private YZSystemLogic sysLogic;
  @Autowired
  private YZPrivLogic privLogic;
  @Autowired
  private YZMenuLogic menuLogic;
  
  @RequestMapping({"/toWelcome.act"})
  public String toWelcome(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return "/admin/index/welcome.jsp";
  }
  
  @RequestMapping({"/toIndex.act"})
  public String toIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return "/admin/menu/index.jsp";
  }
  
  @RequestMapping({"/toTop.act"})
  public String toContent(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return "/admin/menu/top.jsp";
  }
  
  @RequestMapping({"/toLeft.act"})
  public String toLeft(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return "/admin/menu/left.jsp";
  }
  
  @RequestMapping({"/toNewFunc.act"})
  public ModelAndView toNewFunc(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String parentMenuId = request.getParameter("parentMenuId");
    String parentMenuName = request.getParameter("parentMenuName");
    ModelAndView mv = new ModelAndView();
    mv.addObject("parentMenuId", parentMenuId);
    mv.addObject("parentMenuName", parentMenuName);
    mv.setViewName("/admin/menu/newFunc.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNewMenu.act"})
  public ModelAndView toNewMenu(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/admin/menu/newMenu.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEditMenu.act"})
  public ModelAndView toEditMenu(@RequestParam("menuId") String menuId)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.addObject("menuId", menuId);
    mv.setViewName("/admin/menu/edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdateMenu.act"})
  public String insertOrUpdateMenu(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      YZMenu dp = new YZMenu();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(dp.getMenuId())) {
        throw new Exception("主菜单分类代码不能为空");
      }
      if (YZUtility.isNullorEmpty(dp.getMenuName())) {
        throw new Exception("菜单名称不能为空");
      }
      int num = this.menuLogic.countByMenuName(dp);
      if (num >= 1) {
        throw new Exception("菜单名称已存在, 请重新填写");
      }
      num = this.menuLogic.countByMenuId(dp);
      if (num >= 1) {
        throw new Exception("主菜单分类代码已存在, 请重新填写");
      }
      if (!YZUtility.isNullorEmpty(seqId))
      {
        YZMenu tmp = (YZMenu)this.funcLogic.loadObjSingleUUID(TableNameUtil.SYS_MENU, seqId);
        if (tmp == null) {
          throw new Exception("主菜单不存在");
        }
        dp.setImage(tmp.getImage());
        dp.setCreatetime(tmp.getCreatetime());
        dp.setCreateuser(tmp.getCreateuser());
        
        this.menuLogic.updateSingleUUID(TableNameUtil.SYS_MENU, dp);
        
        SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.SYS_MENU, dp, TableNameUtil.SYS_MENU, request);
      }
      else
      {
        dp.setSeqId(YZUtility.getUUID());
        dp.setImage("sys.gif");
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        this.menuLogic.saveSingleUUID(TableNameUtil.SYS_MENU, dp);
        
        SysLogUtil.log(SysLogUtil.NEW, SysLogUtil.SYS_MENU, dp, TableNameUtil.SYS_MENU, request);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getAllMenu4TreeManage.act"})
  public void getAllMenu4TreeManage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = (YZPerson)request.getSession().getAttribute("LOGIN_USER");
      

      List<String> funclist = this.funcLogic.listAllMenu4Tree();
      List<JSONObject> menulist = this.menuLogic.getMainMenu4Manage(person, funclist);
      
      menulist = this.menuLogic.lazyLoadJsonTree4Manage(funclist, menulist);
      for (JSONObject menu : menulist)
      {
        menu.put("id", menu.getString("menuId"));
        menu.put("pId", menu.getString("parentid"));
        menu.put("name", menu.getString("menuName"));
      }
      JSONObject jobj = new JSONObject();
      jobj.put("menuList", menulist);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/getAllMenu4TreeData.act"})
  public void getAllMenu4TreeData(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = (YZPerson)request.getSession().getAttribute("LOGIN_USER");
      
      String privSeqId = request.getParameter("privSeqId");
      if (YZUtility.isNullorEmpty(privSeqId)) {
        throw new Exception("页面传值，角色主键为空");
      }
      YZPriv up = this.privLogic.getDetailBySeqId(privSeqId);
      if (up == null) {
        throw new Exception("角色不存在");
      }
      String personFuncStr = up.getFuncIdStr();
      if (personFuncStr == null) {
        personFuncStr = "";
      }
      List<String> funclist = this.funcLogic.listAllMenu4Tree();
      List<YZMenuModel> menulist = this.sysLogic.getMainMenu(person, funclist);
      
      menulist = this.sysLogic.lazyLoadJsonModel4zTree(funclist, menulist);
      
      List<Map<String, String>> rtnlist = new ArrayList();
      for (YZMenuModel yhMenuModel : menulist)
      {
        boolean ischeck = YZUtility.isStrInArrayEach(yhMenuModel.getMenuid(), personFuncStr);
        
        Map node = new HashMap();
        node.put("id", yhMenuModel.getMenuid());
        node.put("pId", yhMenuModel.getParentid());
        node.put("name", yhMenuModel.getMenuname());
        node.put("checked", Boolean.valueOf(ischeck));
        rtnlist.add(node);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("menuList", rtnlist);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/selectDetailByMenuId.act"})
  public String selectDetailByMenuId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String menuId = request.getParameter("menuId");
      if (YZUtility.isNullorEmpty(menuId)) {
        throw new Exception("主菜单分类代码不能为空");
      }
      JSONObject menu = this.menuLogic.getMenuById(menuId);
      if (menu == null) {
        throw new Exception("菜单不存在");
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", menu.toString());
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/delete.act"})
  public String delete(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("主键为空或者null");
      }
      YZMenu tmp = (YZMenu)this.funcLogic.loadObjSingleUUID(TableNameUtil.SYS_MENU, seqId);
      if (tmp == null) {
        throw new Exception("主菜单不存在");
      }
      boolean ischild = this.funcLogic.IsHaveChild(tmp.getMenuId());
      if (ischild) {
        throw new Exception("当前菜单存在子菜单，不允许删除");
      }
      this.menuLogic.deleteBySeqIds(seqId, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getSelectMenuTree.act"})
  public String getSelectMenuTree(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String deptTreeListStr = this.menuLogic.getSelectMenuTree("0");
      JSONObject jobj = new JSONObject();
      jobj.put("retData", deptTreeListStr);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
}
