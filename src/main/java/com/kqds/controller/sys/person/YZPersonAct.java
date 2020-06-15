package com.kqds.controller.sys.person;

import com.kqds.core.global.YZSysProps;
import com.kqds.core.util.auth.YZPassEncrypt;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZOrganization;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.online.YZOnlineLogic;
import com.kqds.service.sys.organization.YZOrganizationLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.SysLogUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"YZPersonAct"})
public class YZPersonAct
{
  private Logger logger = LoggerFactory.getLogger(YZPersonAct.class);
  @Autowired
  private YZOrganizationLogic organizationLogic;
  @Autowired
  private YZPersonLogic personLogic;
  @Autowired
  private YZDeptLogic deptLogic;
  @Autowired
  private YZOnlineLogic onlineLogic;
  
  @RequestMapping({"/toIndex.act"})
  public String toIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return "/admin/person/index.jsp";
  }
  
  @RequestMapping({"/toParallelTree.act"})
  public String toParallelTree(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return "/kqdsFront/online/parallelTree.jsp";
  }
  
  @RequestMapping({"/toEditPwd.act"})
  public String toEditPwd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return "/admin/person/setpwd.jsp";
  }
  
  @RequestMapping({"/toLogin.act"})
  public ModelAndView toLogin(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String isapp = request.getParameter("isapp");
    ModelAndView mv = new ModelAndView();
    mv.addObject("isapp", isapp);
    mv.setViewName("login.jsp");
    return mv;
  }
  
  @RequestMapping({"/toHzSelectIndex.act"})
  public String toHzSelectIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return "/admin/person/hz_select/index.jsp";
  }
  
  @RequestMapping({"/toHzSelectLeft.act"})
  public String toSelectLeft(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return "/admin/person/hz_select/left.jsp";
  }
  
  @RequestMapping({"/toHzSelectList.act"})
  public ModelAndView toSelectList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/admin/person/hz_select/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toIdIndex.act"})
  public ModelAndView toIdIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String deptIds = request.getParameter("deptIds");
    ModelAndView mv = new ModelAndView();
    mv.addObject("deptIds", deptIds);
    mv.setViewName("/admin/person/id_select/index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toIdTop.act"})
  public String toIdTop(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return "/admin/person/id_select/top.jsp";
  }
  
  @RequestMapping({"/toIdLeft.act"})
  public ModelAndView toIdLeft(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String deptIds = request.getParameter("deptIds");
    ModelAndView mv = new ModelAndView();
    mv.addObject("deptIds", deptIds);
    mv.setViewName("/admin/person/id_select/left.jsp");
    return mv;
  }
  
  @RequestMapping({"/toIdList.act"})
  public ModelAndView toIdList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/admin/person/id_select/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSingleIndex.act"})
  public ModelAndView toSingleIndex(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String isSingle = request.getParameter("isSingle");
    String showleave = request.getParameter("showleave");
    String depttype = request.getParameter("depttype");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("isSingle", isSingle);
    mv.addObject("showleave", showleave);
    mv.addObject("depttype", depttype);
    mv.addObject("organization", organization);
    mv.setViewName("/admin/person/single_select/index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSingleTop.act"})
  public String toSingleTop(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return "/admin/person/single_select/top.jsp";
  }
  
  @RequestMapping({"/toSingleLeft.act"})
  public ModelAndView toSingleLeft(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String isSingle = request.getParameter("isSingle");
    String showleave = request.getParameter("showleave");
    String depttype = request.getParameter("depttype");
    ModelAndView mv = new ModelAndView();
    mv.addObject("isSingle", isSingle);
    mv.addObject("showleave", showleave);
    mv.addObject("depttype", depttype);
    mv.setViewName("/admin/person/single_select/left.jsp");
    return mv;
  }
  
  @RequestMapping({"/toMultiLeft.act"})
  public ModelAndView toMultiLeft(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String isFilterByVisualStaff = request.getParameter("isFilterByVisualStaff");
    ModelAndView mv = new ModelAndView();
    mv.addObject("isFilterByVisualStaff", isFilterByVisualStaff);
    mv.setViewName("/admin/person/multi_select/left.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSingleList.act"})
  public ModelAndView toSingleList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String deptId = request.getParameter("deptId");
    String showleave = request.getParameter("showleave");
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("deptId", deptId);
    mv.addObject("showleave", showleave);
    mv.addObject("seqId", seqId);
    mv.setViewName("/admin/person/single_select/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toTop.act"})
  public String toTop(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return "/admin/person/top.jsp";
  }
  
  @RequestMapping({"/toLeft.act"})
  public String toLeft(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return "/admin/person/left.jsp";
  }
  
  @RequestMapping({"/toList.act"})
  public ModelAndView toList(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String deptId = request.getParameter("deptId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("deptId", deptId);
    mv.setViewName("/admin/person/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toEdit.act"})
  public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String personId = request.getParameter("personId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("personId", personId);
    mv.setViewName("/admin/person/edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNewAdd.act"})
  public ModelAndView toNewAdd(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String deptId = request.getParameter("deptId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("deptId", deptId);
    mv.setViewName("/admin/person/newAdd.jsp");
    return mv;
  }
  
  @RequestMapping({"/getMaxOrderno.act"})
  public void getMaxOrderno(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String deptId = request.getParameter("deptId");
      String data = this.personLogic.getMaxOrderno(deptId);
      JSONObject jobj = new JSONObject();
      jobj.put("data", data);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String organization = ChainUtil.getCurrentOrganization(request);
      YZPerson dp = new YZPerson();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(dp.getUserId())) {
        throw new Exception("用户名不能为空");
      }
      if (YZUtility.isNullorEmpty(dp.getDeptId())) {
        throw new Exception("部门不能为空");
      }
      if (!YZUtility.isNullorEmpty(seqId))
      {
        YZPerson tmpPerson = (YZPerson)this.personLogic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, seqId);
        if (tmpPerson == null) {
          throw new Exception("用户不存在");
        }
        int num = this.personLogic.countByUserNameCurrHos(dp.getUserName(), dp.getDeptId(), seqId);
        if (num >= 1) {
          throw new Exception("同一个门诊，姓名不允许重复");
        }
        dp.setPassword(tmpPerson.getPassword());
        dp.setCreatetime(tmpPerson.getCreatetime());
        dp.setCreateuser(tmpPerson.getCreateuser());
        dp.setUserId(tmpPerson.getUserId());
        
        this.personLogic.updateSingleUUID(TableNameUtil.SYS_PERSON, dp);
        
        SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.SYS_PERSON, dp, TableNameUtil.SYS_PERSON, request);
      }
      else
      {
        int num = this.personLogic.countByUserId(dp.getUserId());
        if (num >= 1) {
          throw new Exception("用户名重复, 用户名不能重复");
        }
        num = this.personLogic.countByUserNameCurrHos(dp.getUserName(), dp.getDeptId(), null);
        if (num >= 1) {
          throw new Exception("同一个门诊，姓名不允许重复");
        }
        dp.setPassword(YZPassEncrypt.encryptPass(dp.getPassword()));
        dp.setSeqId(YZUtility.getUUID());
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        dp.setOrganization(organization);
        this.personLogic.saveSingleUUID(TableNameUtil.SYS_PERSON, dp);
        
        SysLogUtil.log(SysLogUtil.NEW, SysLogUtil.SYS_PERSON, dp, TableNameUtil.SYS_PERSON, request);
      }
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getPersonTree.act"})
  public String getPersonTree(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = null;
    String idStr = request.getParameter("id");
    try
    {
      List<JSONObject> treeList = new ArrayList();
      if ((idStr == null) || ("0".equals(idStr)))
      {
        List<YZOrganization> orgList = this.organizationLogic.getOrganizationList();
        for (YZOrganization org : orgList)
        {
          JSONObject node = new JSONObject();
          node.put("id", "orgId");
          node.put("pId", "orgParentId");
          node.put("name", org.getUnitName());
          node.put("isParent", Boolean.valueOf(true));
          node.put("iconSkin", "home");
          
          treeList.add(node);
        }
      }
      else
      {
        if ("orgId".equals(idStr)) {
          idStr = "0";
        }
        List<YZPerson> persons = this.personLogic.getUserListByDeptSeqId(idStr);
        JSONObject node;
        for (YZPerson p : persons)
        {
          node = new JSONObject();
          node.put("id", "person" + p.getSeqId());
          node.put("pId", idStr);
          node.put("name", p.getUserId());
          

          node.put("isParent", Boolean.valueOf(false));
          node.put("iconSkin", "person");
          treeList.add(node);
        }
        if (ChainUtil.isOpenTry()) {
          organization = ChainUtil.getCurrentOrganization(request);
        }
        List<YZDept> deptList = this.deptLogic.getSubDeptListBySeqId(idStr, organization);
        for (YZDept dept : deptList)
        {
          boolean haveChild = this.deptLogic.IsHaveChildPerson(dept.getSeqId());
          JSONObject node = new JSONObject();
          node.put("id", dept.getSeqId());
          node.put("pId", dept.getDeptParent());
          node.put("name", dept.getDeptName());
          node.put("isParent", Boolean.valueOf(haveChild));
          treeList.add(node);
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", treeList);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getPersonTreeByPriv.act"})
  public String getPersonTreeByPriv(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String idStr = request.getParameter("id");
    try
    {
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> treeList = new ArrayList();
      if ((idStr == null) || ("0".equals(idStr)))
      {
        List<YZDept> deptList = this.deptLogic.getSubDeptListBySeqId("0", ChainUtil.getCurrentOrganization(request));
        for (YZDept dept : deptList)
        {
          Map<String, String> map = new HashMap();
          map.put("deptparentid", dept.getSeqId());
          int count = this.personLogic.countByUserDocument(map, visualstaff);
          if (count > 0)
          {
            JSONObject node = new JSONObject();
            node.put("id", dept.getSeqId());
            node.put("pId", dept.getDeptParent());
            node.put("name", dept.getDeptName());
            node.put("isParent", Boolean.valueOf(true));
            treeList.add(node);
          }
        }
      }
      else
      {
        List<YZPerson> persons = this.personLogic.getUserListByDeptSeqId(idStr);
        JSONObject node;
        for (YZPerson p : persons)
        {
          node = new JSONObject();
          node.put("id", "person" + p.getSeqId());
          node.put("pId", idStr);
          node.put("name", p.getUserId());
          

          node.put("isParent", Boolean.valueOf(false));
          node.put("iconSkin", "person");
          
          Map<String, String> map = new HashMap();
          map.put("perid", p.getSeqId());
          int count = this.personLogic.countByUserDocument(map, visualstaff);
          if (count > 0) {
            treeList.add(node);
          }
        }
        List<YZDept> deptList = this.deptLogic.getSubDeptListBySeqId(idStr, null);
        for (YZDept dept : deptList)
        {
          Map<String, String> map = new HashMap();
          map.put("deptid", dept.getSeqId());
          int count = this.personLogic.countByUserDocument(map, visualstaff);
          if (count > 0)
          {
            JSONObject node = new JSONObject();
            node.put("id", dept.getSeqId());
            node.put("pId", dept.getDeptParent());
            node.put("name", dept.getDeptName());
            node.put("isParent", Boolean.valueOf(true));
            treeList.add(node);
          }
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", treeList);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getPersonTree4MultiSelect.act"})
  public String getPersonTree4MultiSelect(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String idStr = request.getParameter("id");
    String personIds = request.getParameter("personIds");
    try
    {
      List<JSONObject> treeList = new ArrayList();
      if ((idStr == null) || ("0".equals(idStr)))
      {
        List<YZOrganization> orgList = this.organizationLogic.getOrganizationList();
        for (YZOrganization org : orgList)
        {
          JSONObject node = new JSONObject();
          node.put("id", "orgId");
          node.put("pId", "orgParentId");
          node.put("name", org.getUnitName());
          node.put("isParent", Boolean.valueOf(true));
          node.put("iconSkin", "home");
          node.put("nocheck", Boolean.valueOf(true));
          
          treeList.add(node);
        }
      }
      else
      {
        if ("orgId".equals(idStr)) {
          idStr = "0";
        }
        List<YZPerson> persons = this.personLogic.getUserListByDeptSeqId(idStr);
        JSONObject node;
        for (YZPerson p : persons)
        {
          node = new JSONObject();
          node.put("id", "person" + p.getSeqId());
          node.put("pId", idStr);
          node.put("name", p.getUserId());
          

          node.put("isParent", Boolean.valueOf(false));
          node.put("iconSkin", "person");
          node.put("nocheck", Boolean.valueOf(false));
          boolean isCheck = YZUtility.isStrInArrayEach(p.getSeqId(), personIds);
          node.put("checked", Boolean.valueOf(isCheck));
          
          treeList.add(node);
        }
        List<YZDept> deptList = this.deptLogic.getSubDeptListBySeqId(idStr, null);
        for (YZDept dept : deptList)
        {
          boolean haveChild = this.deptLogic.IsHaveChildPerson(dept.getSeqId());
          JSONObject node = new JSONObject();
          node.put("id", dept.getSeqId());
          node.put("pId", dept.getDeptParent());
          node.put("name", dept.getDeptName());
          node.put("isParent", Boolean.valueOf(haveChild));
          node.put("nocheck", Boolean.valueOf(true));
          treeList.add(node);
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", treeList);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String deptId = request.getParameter("deptId");
      String username = request.getParameter("username");
      
      String queryDeptIds = "";
      if (YZUtility.isNullorEmpty(deptId))
      {
        queryDeptIds = this.deptLogic.getAllDeptIds();
      }
      else
      {
        YZDept dept = (YZDept)this.personLogic.loadObjSingleUUID(TableNameUtil.SYS_DEPT, deptId);
        if (dept == null) {
          throw new Exception("部门不存在");
        }
        if ("0".equals(dept.getDeptParent())) {
          queryDeptIds = this.deptLogic.getCurrHosDeptIds(dept);
        } else {
          queryDeptIds = deptId;
        }
      }
      BootStrapPage bp = new BootStrapPage();
      
      BeanUtils.populate(bp, request.getParameterMap());
      
      JSONObject jobj = this.personLogic.selectPage(bp, queryDeptIds, username);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/selectNoPage.act"})
  public String selectNoPage(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String deptId = request.getParameter("deptId");
      String seqId = request.getParameter("seqId");
      String showleave = request.getParameter("showleave");
      if ((YZUtility.isNullorEmpty(showleave)) || (showleave.equals("undefined"))) {
        showleave = "0";
      }
      List<JSONObject> list = this.personLogic.selectNoPage(deptId, seqId, showleave);
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
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
      YZPerson person = (YZPerson)this.personLogic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, seqId);
      if (person == null) {
        throw new Exception("系统用户不存在");
      }
      JSONObject jobj = new JSONObject();
      jobj.put("retData", JSONObject.fromObject(person).toString());
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
      this.personLogic.deleteBySeqIds(seqId, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/clearPassword.act"})
  public String clearPassword(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("主键为空或者null");
      }
      this.personLogic.clearPassword(seqId, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, true, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/setPassword.act"})
  public String setPassword(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      
      String oldpwd = request.getParameter("oldpwd");
      String newpwd1 = request.getParameter("newpwd1");
      String newpwd2 = request.getParameter("newpwd2");
      if (person == null)
      {
        String username = request.getParameter("username");
        person = this.personLogic.getPersonByUserId(username);
      }
      if (person == null) {
        throw new Exception("用户不存在");
      }
      if (!newpwd1.equals(newpwd2)) {
        throw new Exception("两次输入的新密码不一致");
      }
      if (!YZPassEncrypt.isValidPas(oldpwd, person.getPassword())) {
        throw new Exception("输入的原密码错误");
      }
      String pwdNew = YZPassEncrypt.encryptPass(newpwd1);
      person.setPassword(pwdNew);
      this.personLogic.updateSingleUUID(TableNameUtil.SYS_PERSON, person);
      
      SysLogUtil.log("修改密码", SysLogUtil.SYS_PERSON, person, TableNameUtil.SYS_PERSON, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getUserListByDeptId.act"})
  public String getUserListByDeptId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String sDeptId = request.getParameter("deptId");
    String showleave = request.getParameter("showleave");
    String organization = request.getParameter("organization");
    if (YZUtility.isNullorEmpty(organization)) {
      organization = ChainUtil.getCurrentOrganization(request);
    }
    if ((YZUtility.isNullorEmpty(showleave)) || (showleave.equals("undefined"))) {
      showleave = "0";
    }
    try
    {
      List<YZPerson> list = this.personLogic.getPersonListByDeptIds(sDeptId, showleave, organization);
      List<JSONObject> newlist = new ArrayList();
      for (YZPerson p : list)
      {
        JSONObject map = new JSONObject();
        map.put("userId", p.getSeqId());
        map.put("userName", p.getUserName());
        newlist.add(map);
      }
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getDeptByUserSeqId.act"})
  public String getDeptByUserSeqId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    YZPerson person = null;
    try
    {
      person = this.personLogic.getPersonBySeqId(seqId);
      
      JSONObject jobj = new JSONObject();
      jobj.put("retData", person.getDeptId());
      jobj.put("xy_dept", person.getXy_dept());
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getPersonListByRoleAndVisual.act"})
  public void getPersonListByRoleAndVisual(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      YZPerson person = SessionUtil.getLoginPerson(request);
      String userPriv = request.getParameter("userPriv");
      String visualstaff = SessionUtil.getVisualstaff(request);
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      if (YZUtility.isNullorEmpty(userPriv)) {
        throw new Exception("角色不能为空");
      }
      List<JSONObject> list = this.personLogic.getPersonListByRoleAndVisual(userPriv, visualstaff, organization);
      
      List<JSONObject> listper = new ArrayList();
      for (JSONObject per : list) {
        if ((visualstaff.contains(per.getString("seq_id"))) || (person.getSeqId().equals(per.getString("seq_id")))) {
          listper.add(per);
        }
      }
      JSONObject jobj = new JSONObject();
      jobj.put("list", listper);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/getPersonByRole.act"})
  public void getPersonByRole(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String userPriv = request.getParameter("userPriv");
      List<YZPerson> list = this.personLogic.getPersonListByRole(userPriv, ChainUtil.getCurrentOrganization(request));
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/getPersonListByDeptType.act"})
  public void getPersonListByDeptType(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String depttype = request.getParameter("depttype");
      String organization = request.getParameter("organization");
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      List<JSONObject> list = this.personLogic.getPersonListByDeptType(depttype, organization);
      List<JSONObject> list1 = new ArrayList();
      if (organization.equals("HUDX")) {
        list1 = this.personLogic.getPersonListByDeptType1(depttype, organization);
      }
      if (list1.size() > 0) {
        list.addAll(list1);
      }
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/getPersonListByDeptTypeAndVisual.act"})
  public void getPersonListByDeptTypeAndVisual(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String depttype = request.getParameter("depttype");
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      String visualstaff = SessionUtil.getVisualstaff(request);
      List<JSONObject> list = this.personLogic.getPersonListByDeptTypeAndVisual(depttype, visualstaff, organization);
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/yzUser.act"})
  public String yzUser(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String ids = request.getParameter("ids");
    try
    {
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization)) {
        organization = ChainUtil.getCurrentOrganization(request);
      }
      boolean data = this.personLogic.yzUserBySeqIds(ids, organization);
      JSONObject jobj = new JSONObject();
      jobj.put("data", Boolean.valueOf(data));
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getPersonNameStrBySeqIds.act"})
  public String getPersonNameStrBySeqIds(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String ids = request.getParameter("ids");
    try
    {
      String data = this.personLogic.getNameStrBySeqIds(ids);
      JSONObject jobj = new JSONObject();
      jobj.put("data", data);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getMaxuser.act"})
  public String getMaxuser(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      int maxUserAccount = Integer.parseInt(YZSysProps.getProp("maxUserAccount"));
      int count = this.onlineLogic.queryUserCount();
      String data = "";
      if (maxUserAccount <= count) {
        data = "1";
      } else {
        data = "0";
      }
      JSONObject rtjson = new JSONObject();
      rtjson.put("data", data);
      YZUtility.DEAL_SUCCESS(rtjson, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/getUserListByDeptType.act"})
  public String getUserListByDeptType(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String deptType = request.getParameter("deptType");
    if (YZUtility.isNullorEmpty(deptType))
    {
      YZUtility.DEAL_ERROR("部门类型不能传空值", false, null, response, this.logger);
      return null;
    }
    deptType = YZUtility.ConvertStringIds4Query(deptType);
    
    String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
    if (YZUtility.isNullorEmpty(organization)) {
      organization = ChainUtil.getCurrentOrganization(request);
    }
    try
    {
      List<JSONObject> deptList = this.deptLogic.getDeptSeqIdByTypeAndOrg(organization, deptType);
      
      StringBuffer deptIdStrs = new StringBuffer();
      for (JSONObject dept : deptList)
      {
        String seq_id = dept.getString("seq_id");
        if (!YZUtility.isNullorEmpty(seq_id)) {
          deptIdStrs.append(seq_id).append(",");
        }
      }
      if (deptIdStrs.length() > 0) {
        deptIdStrs.deleteCharAt(deptIdStrs.length() - 1);
      }
      if (YZUtility.isNullorEmpty(deptIdStrs.toString()))
      {
        YZUtility.DEAL_ERROR("不存在与传入部门类型匹配的部门", false, null, response, this.logger);
        return null;
      }
      List<JSONObject> list = this.personLogic.getPersonIdListByDeptIds(deptIdStrs.toString());
      YZUtility.RETURN_LIST(list, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
    return null;
  }
  
  public void getOrganizationByPersonSeqId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    try
    {
      if (YZUtility.isNullorEmpty(seqId)) {
        throw new Exception("seqId值为空");
      }
      YZPerson person = (YZPerson)this.personLogic.loadObjSingleUUID(TableNameUtil.SYS_PERSON, seqId);
      if (person == null) {
        throw new Exception("系统用户不存在");
      }
      String deptId = person.getDeptId();
      
      YZDept dpt = this.deptLogic.getTopDept(deptId);
      if (dpt == null) {
        throw new Exception("系统用户所在门诊不存在");
      }
      JSONObject jobj = new JSONObject();
      jobj.put("data", dpt.getDeptCode());
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    }
    catch (Exception ex)
    {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    }
  }
  
  @RequestMapping({"/excelStandardTemplateOut.act"})
  public void excelStandardTemplateOut(HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    File f = new File(ConstUtil.ROOT_DIR + "\\model\\部门用户模板.xls");
    
    response.reset();
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    try
    {
      response.setHeader("Content-Disposition", "attachment;filename=" + new String("部门用户模板.xls".getBytes(), "iso-8859-1"));
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    ServletOutputStream out = response.getOutputStream();
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try
    {
      bis = new BufferedInputStream(new FileInputStream(f));
      bos = new BufferedOutputStream(out);
      byte[] buff = new byte[2048];
      int bytesRead;
      while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
      {
        int bytesRead;
        bos.write(buff, 0, bytesRead);
      }
    }
    catch (IOException e)
    {
      throw e;
    }
    finally
    {
      if (bis != null) {
        bis.close();
      }
      if (bos != null) {
        bos.close();
      }
    }
  }
  
  @RequestMapping({"/findDeptnameByPerson.act"})
  public String findDeptnameByPerson(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String seqId = person.getDeptId();
    try
    {
      JSONObject deptName = this.deptLogic.findDeptNameBySeqId(seqId);
      YZUtility.DEAL_SUCCESS(deptName, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findVisualPersonnel.act"})
  public String findVisualPersonnel(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String deptId = request.getParameter("deptId");
    try
    {
      List<JSONObject> findVisualPersonnel = this.personLogic.findVisualPersonnel(deptId);
      YZUtility.RETURN_LIST(findVisualPersonnel, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/updateOrganization.act"})
  public String updateOrganization(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String organization = request.getParameter("organization");
    String seqid = request.getParameter("seqid");
    try
    {
      Map<String, String> map = new HashMap();
      if (YZUtility.isNotNullOrEmpty(seqid)) {
        map.put("seqid", seqid);
      }
      if (YZUtility.isNotNullOrEmpty(organization)) {
        map.put("organization", organization);
      }
      int i = this.personLogic.updateOrganization(map);
      if (i > 0) {
        YZUtility.DEAL_SUCCESS(null, "修改成功", response, this.logger);
      } else {
        YZUtility.DEAL_ERROR("修改失败", false, null, response, this.logger);
      }
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findIsLeaveBySeqId.act"})
  public String findIsLeaveBySeqId(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seqId = request.getParameter("seqId");
    try
    {
      String str = this.personLogic.findPersonIsleaveBySeqId(seqId);
      YZUtility.DEAL_SUCCESS(null, str, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
}
