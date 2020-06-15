package com.kqds.controller.sys.func;

import com.kqds.entity.sys.YZFunc;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.func.YZFuncLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.log.SysLogUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping({"YZFuncAct"})
public class YZFuncAct {
  private Logger logger = LoggerFactory.getLogger(YZFuncAct.class);
  
  @Autowired
  private YZFuncLogic funcLogic;
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      YZFunc dp = new YZFunc();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      String funcName = request.getParameter("funcName");
      String funcCode = request.getParameter("funcCode");
      if (YZUtility.isNullorEmpty(funcName))
        throw new Exception("菜单名称不能为空"); 
      if (YZUtility.isNullorEmpty(funcCode))
        throw new Exception("菜单路径不能为空"); 
      if (!YZUtility.isNullorEmpty(seqId)) {
        YZFunc tmp = (YZFunc)this.funcLogic.loadObjSingleUUID(TableNameUtil.SYS_FUNC, seqId);
        if (tmp == null)
          throw new Exception("主菜单不存在"); 
        String tmpMenuId = tmp.getMenuId();
        String menuParent = tmpMenuId.substring(0, tmpMenuId.length() - 2);
        int num = this.funcLogic.countByMenuName(dp, menuParent, funcName);
        if (num >= 1)
          throw new Exception("同一级菜单，菜单名不允许重复！"); 
        dp.setMenuId(tmp.getMenuId());
        dp.setCreatetime(tmp.getCreatetime());
        dp.setCreateuser(tmp.getCreateuser());
        this.funcLogic.updateSingleUUID(TableNameUtil.SYS_FUNC, dp);
        SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.SYS_FUNC, dp, TableNameUtil.SYS_FUNC, request);
      } else {
        String menuCode = request.getParameter("menuCode");
        String menuParent = request.getParameter("menuParent");
        if (YZUtility.isNullorEmpty(menuCode))
          throw new Exception("子菜单代码不能为空"); 
        int num = this.funcLogic.countByMenuId(dp, menuParent, menuCode);
        if (num >= 1)
          throw new Exception("子菜单编码已存在,请重新填写！"); 
        num = this.funcLogic.countByMenuName(dp, menuParent, funcName);
        if (num >= 1)
          throw new Exception("同一级菜单，菜单名不允许重复！"); 
        dp.setSeqId(YZUtility.getUUID());
        dp.setMenuId(String.valueOf(menuParent) + menuCode);
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        this.funcLogic.saveSingleUUID(TableNameUtil.SYS_FUNC, dp);
        SysLogUtil.log(SysLogUtil.NEW, SysLogUtil.SYS_FUNC, dp, TableNameUtil.SYS_FUNC, request);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/toEditFunc.act"})
  public ModelAndView toEditFunc(@RequestParam("funcId") String funcId) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.addObject("funcId", funcId);
    mv.setViewName("/admin/func/edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/selectDetailByMenuId.act"})
  public String selectDetailByMenuId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String menuId = request.getParameter("menuId");
      if (YZUtility.isNullorEmpty(menuId))
        throw new Exception("菜单编号不能为空"); 
      JSONObject menu = this.funcLogic.getFuncById(menuId);
      if (menu == null)
        throw new Exception("菜单不存在"); 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", menu.toString());
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/delete.act"})
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("主键为空或者null"); 
      YZFunc tmp = (YZFunc)this.funcLogic.loadObjSingleUUID(TableNameUtil.SYS_FUNC, seqId);
      if (tmp == null)
        throw new Exception("子菜单不存在"); 
      boolean ischild = this.funcLogic.IsHaveChild(tmp.getMenuId());
      if (ischild)
        throw new Exception("当前菜单存在子菜单，不允许删除"); 
      this.funcLogic.deleteBySeqIds(seqId, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, this.logger);
    } 
    return null;
  }
}
