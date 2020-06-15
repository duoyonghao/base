package com.kqds.controller.sys.dept;

import com.kqds.entity.sys.YZDept;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZOrganization;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.sys.dept.YZDeptLogic;
import com.kqds.service.sys.organization.YZOrganizationLogic;
import com.kqds.service.sys.para.YZParaLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.SysLogUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@RequestMapping({"/YZDeptAct"})
public class YZDeptAct {
  private Logger logger = LoggerFactory.getLogger(YZDeptAct.class);
  
  @Autowired
  private YZOrganizationLogic organizationLogic;
  
  @Autowired
  private YZDeptLogic deptLogic;
  
  @Autowired
  private YZParaLogic paraLogic;
  
  @RequestMapping({"/toIndex.act"})
  public String toIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/admin/dept/index.jsp";
  }
  
  @RequestMapping({"/toUnitIndex.act"})
  public String toUnitIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/admin/unit/index.jsp";
  }
  
  @RequestMapping({"/toSelectMultiIndex.act"})
  public String toSelectMultiIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/admin/dept/multi_select/index.jsp";
  }
  
  @RequestMapping({"/toSelectTop.act"})
  public String toSelectTop(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/admin/dept/single_select/top.jsp";
  }
  
  @RequestMapping({"/toSelectLeft.act"})
  public String toSelectLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/admin/dept/single_select/left.jsp";
  }
  
  @RequestMapping({"/toSelectList.act"})
  public ModelAndView toSelectList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String deptId = request.getParameter("deptId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("deptId", deptId);
    mv.setViewName("/admin/dept/single_select/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSuccess.act"})
  public String toSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/inc/success.jsp";
  }
  
  @RequestMapping({"/toNewAdd.act"})
  public ModelAndView toNewMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String deptParent = request.getParameter("deptParent");
    String deptCode = request.getParameter("deptCode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("deptParent", deptParent);
    mv.addObject("deptCode", deptCode);
    mv.setViewName("/admin/dept/newAdd.jsp");
    return mv;
  }
  
  @RequestMapping({"/toTop.act"})
  public String toTop(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/admin/dept/top.jsp";
  }
  
  @RequestMapping({"/toLeft.act"})
  public String toLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/admin/dept/left.jsp";
  }
  
  @RequestMapping({"/toEdit.act"})
  public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String deptId = request.getParameter("deptId");
    String deptCode = request.getParameter("deptCode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("deptId", deptId);
    mv.addObject("deptCode", deptCode);
    mv.setViewName("/admin/dept/edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      YZDept dp = new YZDept();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(dp.getDeptName()))
        throw new Exception("部门名称不能为空"); 
      if (YZUtility.isNullorEmpty(dp.getDeptCode()))
        throw new Exception("部门编号不能为空"); 
      if (!YZUtility.isNullorEmpty(seqId)) {
        YZDept tmpDept = (YZDept)this.deptLogic.loadObjSingleUUID(TableNameUtil.SYS_DEPT, seqId);
        if (tmpDept == null)
          throw new Exception("部门不存在"); 
        int num = this.deptLogic.countByDeptNameCurrHos(dp);
        if (num >= 1)
          throw new Exception("同一个门诊，部门名称不允许重复"); 
        dp.setDeptParent(tmpDept.getDeptParent());
        dp.setCreatetime(tmpDept.getCreatetime());
        dp.setCreateuser(tmpDept.getCreateuser());
        this.deptLogic.updateSingleUUID(TableNameUtil.SYS_DEPT, dp);
        if ("0".equals(dp.getDeptParent()) && dp.getOrgflag().intValue() == 1)
          this.paraLogic.initParaByOrganization(dp.getDeptCode()); 
        SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.SYS_DEPT, dp, TableNameUtil.SYS_DEPT, request);
      } else {
        if ("0".equals(dp.getDeptParent())) {
          int i = this.deptLogic.countByDeptCode(dp);
          if (i >= 1)
            throw new Exception("一级部门，部门编号不允许重复"); 
          if (1 == dp.getOrgflag().intValue())
            this.paraLogic.initParaByOrganization(dp.getDeptCode()); 
        } 
        int num = this.deptLogic.countByDeptNameCurrHos(dp);
        if (num >= 1)
          throw new Exception("同一个门诊，部门名称不允许重复"); 
        dp.setSeqId(YZUtility.getUUID());
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        this.deptLogic.saveSingleUUID(TableNameUtil.SYS_DEPT, dp);
        SysLogUtil.log(SysLogUtil.NEW, SysLogUtil.SYS_DEPT, dp, TableNameUtil.SYS_DEPT, request);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectDetail.act"})
  public String selectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("主键不能为空"); 
      YZDept person = (YZDept)this.deptLogic.loadObjSingleUUID(TableNameUtil.SYS_DEPT, seqId);
      if (person == null)
        throw new Exception("部门不存在"); 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", JSONObject.fromObject(person).toString());
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
      boolean ischild = this.deptLogic.IsHaveChild(seqId);
      if (ischild)
        throw new Exception("当前部门存在子部门，不允许删除"); 
      ischild = this.deptLogic.IsHaveChildPerson(seqId);
      if (ischild)
        throw new Exception("当前部门存在用户，不允许删除"); 
      this.deptLogic.deleteBySeqIds(seqId, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getDeptTree4All.act"})
  public String getDeptTree4All(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String deptIds = request.getParameter("deptIds");
    List<JSONObject> treeList = new ArrayList<>();
    try {
      List<YZOrganization> orgList = this.organizationLogic.getOrganizationList();
      for (YZOrganization org : orgList) {
        JSONObject node = new JSONObject();
        node.put("id", "orgId");
        node.put("pId", "orgParentId");
        node.put("name", org.getUnitName());
        node.put("isParent", Boolean.valueOf(true));
        node.put("iconSkin", "home");
        node.put("nocheck", Boolean.valueOf(false));
        treeList.add(node);
      } 
      treeList = this.deptLogic.getDeptNodeList("0", treeList, deptIds);
      for (JSONObject job : treeList) {
        String id = job.getString("id");
        boolean check = YZUtility.isStrInArrayEach(id, deptIds);
        job.put("checked", Boolean.valueOf(check));
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", treeList);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getDeptTree.act"})
  public String getDeptTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String idStr = request.getParameter("id");
    String isSingle = request.getParameter("isSingle");
    String organization = null;
    if (!YZUtility.isNullorEmpty(isSingle) && "single".equals(isSingle))
      organization = ChainUtil.getCurrentOrganization(request); 
    if (ChainUtil.isOpenTry())
      organization = ChainUtil.getCurrentOrganization(request); 
    try {
      List<JSONObject> treeList = new ArrayList<>();
      if (idStr == null || "0".equals(idStr)) {
        List<YZOrganization> orgList = this.organizationLogic.getOrganizationList();
        for (YZOrganization org : orgList) {
          JSONObject node = new JSONObject();
          node.put("id", "orgId");
          node.put("pId", "orgParentId");
          node.put("name", org.getUnitName());
          node.put("isParent", Boolean.valueOf(true));
          node.put("iconSkin", "home");
          treeList.add(node);
        } 
      } else {
        if ("orgId".equals(idStr))
          idStr = "0"; 
        List<YZDept> deptList = this.deptLogic.getSubDeptListBySeqId(idStr, organization);
        for (YZDept dept : deptList) {
          boolean haveChild = this.deptLogic.IsHaveChild(dept.getSeqId());
          JSONObject node = new JSONObject();
          node.put("id", dept.getSeqId());
          node.put("pId", dept.getDeptParent());
          node.put("name", dept.getDeptName());
          node.put("isParent", Boolean.valueOf(haveChild));
          node.put("code", dept.getDeptCode());
          treeList.add(node);
        } 
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", treeList);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getDeptTreeOnlyOrg.act"})
  public String getDeptTreeOnlyOrg(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String idStr = request.getParameter("id");
    String isSingle = request.getParameter("isSingle");
    String depttype = request.getParameter("depttype");
    String organization = null;
    if (!YZUtility.isNullorEmpty(isSingle) && "single".equals(isSingle))
      organization = ChainUtil.getCurrentOrganization(request); 
    if (ChainUtil.isOpenTry())
      organization = ChainUtil.getCurrentOrganization(request); 
    organization = ChainUtil.getCurrentOrganization(request);
    try {
      List<JSONObject> treeList = new ArrayList<>();
      if (idStr == null || "0".equals(idStr)) {
        List<YZOrganization> orgList = this.organizationLogic.getOrganizationList();
        for (YZOrganization org : orgList) {
          JSONObject node = new JSONObject();
          node.put("id", "orgId");
          node.put("pId", "orgParentId");
          node.put("name", org.getUnitName());
          node.put("isParent", Boolean.valueOf(true));
          node.put("iconSkin", "home");
          treeList.add(node);
        } 
      } else {
        if ("orgId".equals(idStr))
          idStr = "0"; 
        List<YZDept> deptList = this.deptLogic.getSubOrgDeptListBySeqId(idStr, organization, depttype);
        for (YZDept dept : deptList) {
          boolean haveChild = this.deptLogic.IsHaveChild(dept.getSeqId());
          JSONObject node = new JSONObject();
          node.put("id", dept.getSeqId());
          node.put("pId", dept.getDeptParent());
          node.put("name", dept.getDeptName());
          node.put("isParent", Boolean.valueOf(haveChild));
          node.put("code", dept.getDeptCode());
          treeList.add(node);
        } 
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", treeList);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getSelectDeptTree.act"})
  public String getSelectDeptTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String deptTreeListStr = this.deptLogic.getSelectDeptTree("0", null);
      JSONObject jobj = new JSONObject();
      jobj.put("retData", deptTreeListStr);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getSelectOrganization.act"})
  public String getSelectOrganization(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      List<YZDept> deptTreeListStr = this.deptLogic.getSelectOrganization();
      JSONObject jobj = new JSONObject();
      jobj.put("retData", deptTreeListStr);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getDeptTreeByDeptIds.act"})
  public String getDeptTreeByDeptIds(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String idStr = request.getParameter("deptIds");
    String id = request.getParameter("id");
    try {
      List<JSONObject> treeList = new ArrayList<>();
      if (YZUtility.isNullorEmpty(id)) {
        List<YZOrganization> orgList = this.organizationLogic.getOrganizationList();
        for (YZOrganization org : orgList) {
          JSONObject node = new JSONObject();
          node.put("id", "orgId");
          node.put("pId", "orgParentId");
          node.put("name", org.getUnitName());
          node.put("isParent", Boolean.valueOf(true));
          node.put("iconSkin", "home");
          treeList.add(node);
        } 
      } else {
        List<YZDept> deptList = this.deptLogic.getDeptListBySeqIds(idStr);
        for (YZDept dept : deptList) {
          JSONObject node = new JSONObject();
          node.put("id", dept.getSeqId());
          node.put("pId", "orgId");
          node.put("name", dept.getDeptName());
          node.put("isParent", Boolean.valueOf(false));
          node.put("code", dept.getDeptCode());
          treeList.add(node);
        } 
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", treeList);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getDeptNameBySeqId.act"})
  public void getDeptNameBySeqId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      YZDept dept = (YZDept)this.deptLogic.loadObjSingleUUID(TableNameUtil.SYS_DEPT, seqId);
      if (dept == null)
        throw new Exception(" 部门不存在"); 
      String data = dept.getDeptName();
      JSONObject jobj = new JSONObject();
      jobj.put("data", data);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getTopDeptName.act"})
  public void getTopDeptName(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String deptcode = request.getParameter("deptcode");
      String data = this.deptLogic.getTopDeptName(deptcode);
      JSONObject jobj = new JSONObject();
      jobj.put("data", data);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getAllDeptByOrganization.act"})
  public void getAllDeptByOrganization(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
        organization = ChainUtil.getCurrentOrganization(request); 
      List<YZDept> list = this.deptLogic.getAllDeptByOrganization(organization);
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getDeptListByDeptType.act"})
  public String getDeptListByDeptType(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String typeIds = request.getParameter("typeIds");
    String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
    if (YZUtility.isNullorEmpty(organization))
      organization = ChainUtil.getCurrentOrganization(request); 
    try {
      List<YZDept> list = this.deptLogic.getDeptListByDeptType(typeIds, organization);
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getDeptNamesBySeqIds.act"})
  public String getDeptNamesBySeqIds(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqIds = request.getParameter("seqIds");
    try {
      String data = this.deptLogic.getDeptNamesBySeqIds(seqIds);
      JSONObject rtjson = new JSONObject();
      rtjson.put("data", data);
      YZUtility.DEAL_SUCCESS(rtjson, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findDeptByDeptType.act"})
  public String findDeptByDeptType(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    if (YZUtility.isNullorEmpty(organization))
      organization = ChainUtil.getCurrentOrganization(request); 
    String deptType = request.getParameter("deptType");
    try {
      List<JSONObject> list = new ArrayList<>();
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(deptType)) {
        map.put("deptType", deptType);
        if (deptType.equals("all")) {
          list = this.deptLogic.findAllDeptByDeptType(organization);
        } else {
          list = this.deptLogic.findDeptByDeptType(map);
        } 
      } 
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findDeptList.act"})
  public String findDeptList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String organization = ChainUtil.getCurrentOrganization(request);
      Map<String, String> map = new HashMap<>();
      if (organization != null)
        map.put("organization", organization); 
      List<YZDept> findDeptList = this.deptLogic.findDeptList(map);
      YZUtility.RETURN_LIST(findDeptList, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getListALL.act"})
  public void getListALL(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    try {
      Map<String, String> map = new HashMap<>();
      map.put("organization", organization);
      List<YZDict> list = null;
      list = this.deptLogic.getListALL(map);
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
}
