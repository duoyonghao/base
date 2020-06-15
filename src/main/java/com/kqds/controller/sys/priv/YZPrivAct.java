package com.kqds.controller.sys.priv;

import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.entity.sys.YZPriv;
import com.kqds.service.sys.priv.YZPrivLogic;
import com.kqds.service.sys.system.YZSystemLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.SysLogUtil;
import com.kqds.util.sys.sys.UserPrivUtil;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"YZPrivAct"})
public class YZPrivAct
{
  private static Logger logger = LoggerFactory.getLogger(YZPrivAct.class);
  @Autowired
  private YZPrivLogic privLogic;
  @Autowired
  private YZSystemLogic systemLogic;
  
  @RequestMapping({"/toIndex.act"})
  public String toIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return "/admin/priv/index.jsp";
  }
  
  @RequestMapping({"/toList.act"})
  public ModelAndView toList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/admin/priv/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEdit.act"})
  public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("organization", organization);
    mv.setViewName("/admin/priv/edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSetPriv.act"})
  public ModelAndView toSetPriv(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String priv_seqId = request.getParameter("priv_seqId");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("priv_seqId", priv_seqId);
    mv.addObject("organization", organization);
    mv.setViewName("/admin/priv/setpriv_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toCopy.act"})
  public ModelAndView toCopy(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/admin/priv/copy.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNewAdd.act"})
  public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/admin/priv/newAdd.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSetPrivList.act"})
  public ModelAndView toSetPrivList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String priv_seqId = request.getParameter("priv_seqId");
    String menuId = request.getParameter("menuId");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("priv_seqId", priv_seqId);
    mv.addObject("menuId", menuId);
    mv.addObject("organization", organization);
    mv.setViewName("/admin/priv/setpriv_list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSetPrivLeft.act"})
  public ModelAndView toSetPrivLeft(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String priv_seqId = request.getParameter("priv_seqId");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("priv_seqId", priv_seqId);
    mv.addObject("organization", organization);
    mv.setViewName("/admin/priv/setpriv_left.jsp");
    return mv;
  }
  
  @RequestMapping({"/copyPriv.act"})
  public String copyPriv(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      String seqId = request.getParameter("seqId");
      String privName = request.getParameter("privName");
      String organization = request.getParameter("organization");
      if (YZUtility.isNullorEmpty(organization)) {
        organization = "";
      }
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("主键为空或者null");
      }
      if (YZUtility.isNullorEmpty(privName)) {
        throw new Exception("角色名称不允许为空");
      }
      YZPriv forQuery = new YZPriv();
      forQuery.setPrivName(privName);
      int num = this.privLogic.countByPrivName(forQuery);
      if (num >= 1) {
        throw new Exception("角色名称不允许重复");
      }
      YZPriv tmpPriv = (YZPriv)this.privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, seqId);
      if (tmpPriv == null) {
        throw new Exception("角色不存在");
      }
      tmpPriv.setSeqId(YZUtility.getUUID());
      tmpPriv.setPrivName(privName);
      tmpPriv.setCreatetime(YZUtility.getCurDateTimeStr());
      tmpPriv.setCreateuser(person.getSeqId());
      tmpPriv.setOrganization(organization);
      this.privLogic.saveSingleUUID(TableNameUtil.SYS_PRIV, tmpPriv);
      

      SysLogUtil.log(SysLogUtil.COPY_ROLE, SysLogUtil.SYS_PRIV, tmpPriv, TableNameUtil.SYS_PRIV, request);
      
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      YZPriv dp = new YZPriv();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(dp.getPrivName())) {
        throw new Exception("角色名称不能为空");
      }
      if (!YZUtility.isNullorEmpty(seqId))
      {
        YZPriv tmpPriv = (YZPriv)this.privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, seqId);
        if (tmpPriv == null) {
          throw new Exception("角色不存在");
        }
        int num = this.privLogic.countByPrivName(dp);
        if (num >= 1) {
          throw new Exception("角色名称不允许重复");
        }
        dp.setFuncIdStr(tmpPriv.getFuncIdStr());
        dp.setFuncbutton(tmpPriv.getFuncbutton());
        dp.setCreatetime(tmpPriv.getCreatetime());
        dp.setCreateuser(tmpPriv.getCreateuser());
        
        this.privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, dp);
        
        SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.SYS_PRIV, dp, TableNameUtil.SYS_PRIV, request);
      }
      else
      {
        int num = this.privLogic.countByPrivName(dp);
        if (num >= 1) {
          throw new Exception("角色名称不允许重复");
        }
        dp.setSeqId(YZUtility.getUUID());
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        this.privLogic.saveSingleUUID(TableNameUtil.SYS_PRIV, dp);
        
        SysLogUtil.log(SysLogUtil.NEW, SysLogUtil.SYS_PRIV, dp, TableNameUtil.SYS_PRIV, request);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getSlectUserPriv.act"})
  public String getSlectUserPriv(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      List<JSONObject> list = this.privLogic.getSlectUserPriv(organization);
      JSONObject jobj = new JSONObject();
      jobj.put("retData", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getSlectUserPrivWithCommon.act"})
  public String getSlectUserPrivWithCommon(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      List<JSONObject> list = this.privLogic.getSlectUserPrivWithCommon(organization);
      JSONObject jobj = new JSONObject();
      jobj.put("retData", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/getSlectUserPrivNoOrg.act"})
  public String getSlectUserPrivNoOrg(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      List<JSONObject> list = this.privLogic.getSlectUserPriv(null);
      JSONObject jobj = new JSONObject();
      jobj.put("retData", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject pagejson = this.privLogic.selectPage(bp, ChainUtil.getOrganizationFromUrlCanNull(request));
      YZUtility.DEAL_SUCCESS(pagejson, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("主键不能为空");
      }
      JSONObject userPriv = this.privLogic.selectDetail(seqId, request);
      if (userPriv == null) {
        throw new Exception("角色不存在");
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", userPriv);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
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
      boolean ischild = this.privLogic.IsHaveChild(seqId);
      if (ischild) {
        throw new Exception("该记录已被用户主角色引用，不允许删除");
      }
      ischild = this.privLogic.IsHaveChildOther(seqId);
      if (ischild) {
        throw new Exception("该记录已被用户辅助角色引用，不允许删除");
      }
      this.privLogic.deleteBySeqIds(seqId, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/menuSetSave.act"})
  public String menuSetSave(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      String funcIdStr = request.getParameter("funcIdStr");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("主键为空或者null");
      }
      YZPriv tmpPriv = (YZPriv)this.privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, seqId);
      if (tmpPriv == null) {
        throw new Exception("角色不存在");
      }
      tmpPriv.setFuncIdStr(funcIdStr);
      this.privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, tmpPriv);
      
      SysLogUtil.log(SysLogUtil.SET_FUNC, SysLogUtil.SYS_PRIV, tmpPriv, TableNameUtil.SYS_PRIV, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/setButtonPriv.act"})
  public String setButtonPriv(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String privid = request.getParameter("privid");
      String buttonIds = request.getParameter("buttonid");
      String checked = request.getParameter("checked");
      
      YZPriv userpriv = (YZPriv)this.privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, privid);
      if (userpriv == null) {
        throw new Exception("角色不存在");
      }
      String qxButtonStr = userpriv.getFuncbutton();
      if (qxButtonStr == null) {
        qxButtonStr = "";
      }
      if (checked.equals("0"))
      {
        String[] buttionArray = buttonIds.split(",");
        for (String buttonid : buttionArray) {
          if (!YZUtility.isNullorEmpty(buttonid)) {
            if (qxButtonStr.startsWith(buttonid + ",")) {
              qxButtonStr = qxButtonStr.substring(buttonid.length() + 1, qxButtonStr.length());
            } else if (qxButtonStr.matches(".*,\\s*" + buttonid + "\\s*,.*")) {
              qxButtonStr = qxButtonStr.replace("," + buttonid + ",", ",");
            }
          }
        }
      }
      else
      {
        String[] buttionArray = buttonIds.split(",");
        for (String buttonid : buttionArray) {
          if (!YZUtility.isNullorEmpty(buttonid)) {
            if ((!qxButtonStr.startsWith(buttonid + ",")) && (!qxButtonStr.matches(".*,\\s*" + buttonid + "\\s*,.*"))) {
              if (qxButtonStr.endsWith(",")) {
                qxButtonStr = qxButtonStr + buttonid + ",";
              } else {
                qxButtonStr = qxButtonStr + "," + buttonid + ",";
              }
            }
          }
        }
      }
      userpriv.setFuncbutton(qxButtonStr);
      this.privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, userpriv);
      
      SysLogUtil.log(SysLogUtil.SET_BUTTON, SysLogUtil.SYS_PRIV, userpriv, TableNameUtil.SYS_PRIV, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/setPersonPriv.act"})
  public String setPersonPriv(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String privid = request.getParameter("privid");
      
      String maxDiscount = request.getParameter(UserPrivUtil.qxFlag0_maxDiscount);
      String canLookPhone = request.getParameter(UserPrivUtil.qxFlag1_canLookPhone);
      String canKd = request.getParameter(UserPrivUtil.qxFlag2_canKd);
      String canHj = request.getParameter(UserPrivUtil.qxFlag3_canHj);
      String canPaiban = request.getParameter(UserPrivUtil.qxFlag4_canPaiban);
      String canRoom = request.getParameter(UserPrivUtil.qxFlag5_canRoom);
      String canOrderOther = request.getParameter(UserPrivUtil.qxFlag6_canOrderOther);
      String canEditKf = request.getParameter(UserPrivUtil.qxFlag7_canEditKf);
      String canEditWd = request.getParameter(UserPrivUtil.qxFlag8_canEditWd);
      String canEditCost = request.getParameter(UserPrivUtil.qxFlag9_canEditCost);
      String canEditOrder = request.getParameter(UserPrivUtil.qxFlag10_canEditOrder);
      String iszj = request.getParameter(UserPrivUtil.qxFlag11_iszj);
      String canEditJdr = request.getParameter(UserPrivUtil.qxFlag12_canEditJdr);
      String canEditAskperson = request.getParameter(UserPrivUtil.qxFlag13_canEditAskperson);
      String canExportPhoneNumber = request.getParameter(UserPrivUtil.qxFlag14_canExportPhoneNumber);
      String canEditPhone = request.getParameter(UserPrivUtil.qxFlag15_canEditPhone);
      String canEditHzly = request.getParameter(UserPrivUtil.qxFlag16_canEditHzly);
      String canZheKouOnly = request.getParameter(UserPrivUtil.qxFlag17_canZheKouOnly);
      String canDelCk = request.getParameter(UserPrivUtil.qxFlag18_canDelCk);
      String maxVoidmoney = request.getParameter(UserPrivUtil.qxFlag19_maxVoidmoney);
      String canModRoom = request.getParameter(UserPrivUtil.qxFlag20_canModRoom);
      

      YZPriv userPriv = this.privLogic.getDetailBySeqId(privid);
      if (userPriv == null) {
        throw new Exception("角色不存在");
      }
      String[] privArray = this.privLogic.preCheckInit(userPriv, request);
      
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag0_maxDiscount, maxDiscount);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag1_canLookPhone, canLookPhone);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag2_canKd, canKd);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag3_canHj, canHj);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag4_canPaiban, canPaiban);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag5_canRoom, canRoom);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag6_canOrderOther, canOrderOther);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag7_canEditKf, canEditKf);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag8_canEditWd, canEditWd);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag9_canEditCost, canEditCost);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag10_canEditOrder, canEditOrder);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag11_iszj, iszj);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag12_canEditJdr, canEditJdr);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag13_canEditAskperson, canEditAskperson);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag14_canExportPhoneNumber, canExportPhoneNumber);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag15_canEditPhone, canEditPhone);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag16_canEditHzly, canEditHzly);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag17_canZheKouOnly, canZheKouOnly);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag18_canDelCk, canDelCk);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag19_maxVoidmoney, maxVoidmoney);
      this.privLogic.setPersonPrivByKey(privArray, UserPrivUtil.qxFlag20_canModRoom, canModRoom);
      StringBuffer newIdStr = new StringBuffer();
      for (String id : privArray)
      {
        if (YZUtility.isNullorEmpty(id)) {
          id = ConstUtil.EMPTY_SPACE;
        }
        newIdStr.append(id).append(",");
      }
      userPriv.setPrivIdStr(newIdStr.toString());
      

      this.privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, userPriv);
      
      SysLogUtil.log(SysLogUtil.SET_PERSON_PRIV, SysLogUtil.SYS_PRIV, userPriv, TableNameUtil.SYS_PRIV, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/setVisal.act"})
  public String setVisal(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String privid = request.getParameter("privid");
      String visualPerson = request.getParameter("visualPerson");
      String visualDept = request.getParameter("visualDept");
      
      YZPriv userpriv = (YZPriv)this.privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, privid);
      if (userpriv == null) {
        throw new Exception("角色不存在");
      }
      userpriv.setVisualPerson(visualPerson);
      userpriv.setVisualDept(visualDept);
      
      this.privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, userpriv);
      
      SysLogUtil.log(SysLogUtil.SET_VISUAL, SysLogUtil.SYS_PRIV, userpriv, TableNameUtil.SYS_PRIV, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/setOrderVisal.act"})
  public String setOrderVisal(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String privid = request.getParameter("privid");
      String orderVisualPerson = request.getParameter("orderVisualPerson");
      String orderVisualDept = request.getParameter("orderVisualDept");
      
      YZPriv userpriv = (YZPriv)this.privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, privid);
      if (userpriv == null) {
        throw new Exception("角色不存在");
      }
      userpriv.setOrderVisualPerson(orderVisualPerson);
      userpriv.setOrderVisualDept(orderVisualDept);
      this.privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, userpriv);
      
      SysLogUtil.log(SysLogUtil.SET_VISUAL_ORDER, SysLogUtil.SYS_PRIV, userpriv, TableNameUtil.SYS_PRIV, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/setVisitDept.act"})
  public String setVisitDept(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String privid = request.getParameter("privid");
      String visitDept = request.getParameter("visitDept");
      
      YZPriv userpriv = (YZPriv)this.privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, privid);
      if (userpriv == null) {
        throw new Exception("角色不存在");
      }
      userpriv.setVisitDept(visitDept);
      this.privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, userpriv);
      
      SysLogUtil.log(SysLogUtil.SET_VISUAL_ORDER, SysLogUtil.SYS_PRIV, userpriv, TableNameUtil.SYS_PRIV, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/setOneHouse.act"})
  public String setOneHouse(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String privid = request.getParameter("privid");
      String firstFloor = request.getParameter("firstfloor");
      String upstair = request.getParameter("upstair");
      
      YZPriv userpriv = (YZPriv)this.privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, privid);
      if (userpriv == null) {
        throw new Exception("角色不存在");
      }
      userpriv.setFirstFloor(firstFloor);
      userpriv.setUpstair(upstair);
      this.privLogic.updateSingleUUID(TableNameUtil.SYS_PRIV, userpriv);
      
      SysLogUtil.log(SysLogUtil.SET_VISUAL_ORDER, SysLogUtil.SYS_PRIV, userpriv, TableNameUtil.SYS_PRIV, request);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
  
  @RequestMapping({"/updateOnlineData.act"})
  public String updateOnlineData(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String privid = request.getParameter("privid");
      
      YZPriv userpriv = (YZPriv)this.privLogic.loadObjSingleUUID(TableNameUtil.SYS_PRIV, privid);
      if (userpriv == null) {
        throw new Exception("角色不存在");
      }
      String visualstaffYyrl = this.systemLogic.getVisualstaffYyrl(userpriv, null);
      String visualstaff = this.systemLogic.getVisualstaff(userpriv, null);
      for (Entry<String, HttpSession> entry : SessionUtil.Session_MAP.entrySet())
      {
        HttpSession session = (HttpSession)entry.getValue();
        if (session != null)
        {
          YZPerson person = (YZPerson)session.getAttribute("LOGIN_USER");
          if (person != null) {
            if (person.getUserPriv().equals(privid))
            {
              String currvisualstaffYyrl = null;
              if (YZUtility.isNullorEmpty(visualstaffYyrl)) {
                currvisualstaffYyrl = "'" + person.getSeqId() + "'";
              } else {
                currvisualstaffYyrl = visualstaffYyrl + ",'" + person.getSeqId() + "'";
              }
              String currvisualstaff = null;
              if (YZUtility.isNullorEmpty(visualstaff)) {
                currvisualstaff = "'" + person.getSeqId() + "'";
              } else {
                currvisualstaff = visualstaff + ",'" + person.getSeqId() + "'";
              }
              session.setAttribute(ConstUtil.LOGIN_USER_PRIV, userpriv);
              

              session.setAttribute(SessionUtil.visualstaffYyrl, currvisualstaffYyrl);
              

              session.setAttribute(SessionUtil.visualstaff, currvisualstaff);
            }
          }
        }
      }
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, logger);
    }
    return null;
  }
}
