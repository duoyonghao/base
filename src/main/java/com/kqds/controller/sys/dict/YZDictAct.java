package com.kqds.controller.sys.dict;

import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.jzmdType.KQDS_Jzmd_TypeLogic;
import com.kqds.service.base.util.UtilLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.SysLogUtil;
import com.kqds.util.sys.sys.DictUtil;
import java.util.ArrayList;
import java.util.List;
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
@RequestMapping({"YZDictAct"})
public class YZDictAct {
  private Logger logger = LoggerFactory.getLogger(YZDictAct.class);
  
  @Autowired
  private YZDictLogic dictLogic;
  
  @Autowired
  private UtilLogic utilLogic;
  
  @Autowired
  private KQDS_Jzmd_TypeLogic jzmdTypeLogic;
  
  @RequestMapping({"/toIndex.act"})
  public ModelAndView toIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/admin/dict/index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toSelectMultiIndex.act"})
  public String toSelectMultiIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/admin/dict/multi_select/index.jsp";
  }
  
  @RequestMapping({"/toTop.act"})
  public String toContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/admin/dict/top.jsp";
  }
  
  @RequestMapping({"/toLeft.act"})
  public String toLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/admin/dict/left.jsp";
  }
  
  @RequestMapping({"/toList.act"})
  public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentCode = request.getParameter("parentCode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("parentCode", parentCode);
    mv.setViewName("/admin/dict/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBlct.act"})
  public String toBlct(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/kqds/blct/index.jsp";
  }
  
  @RequestMapping({"/toDxfl.act"})
  public String toDxfl(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/kqds/dxfl/index.jsp";
  }
  
  @RequestMapping({"/toJzmd.act"})
  public String toJzmd(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/kqds/jzmd/index.jsp";
  }
  
  @RequestMapping({"/toBlfl.act"})
  public String toBlfl(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/kqds/blfl/index.jsp";
  }
  
  @RequestMapping({"/toHzly.act"})
  public String toHzly(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/kqds/hzly/index.jsp";
  }
  
  @RequestMapping({"/toTreatItemType.act"})
  public String toTreatItemType(HttpServletRequest request, HttpServletResponse response) throws Exception {
    return "/kqds/treatItemType/index.jsp";
  }
  
  @RequestMapping({"/toEdit.act"})
  public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/admin/dict/edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toNewAdd.act"})
  public ModelAndView toNewMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentCode = request.getParameter("parentCode");
    ModelAndView mv = new ModelAndView();
    mv.addObject("parentCode", parentCode);
    mv.setViewName("/admin/dict/newAdd.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBlflSubIndex.act"})
  public ModelAndView toBlflSubIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/blfl/subindex.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBlflList.act"})
  public ModelAndView toBlflList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentCode = request.getParameter("parentCode");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("parentCode", parentCode);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/blfl/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBlflLeft.act"})
  public ModelAndView toBlflLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/blfl/left.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBlflEdit.act"})
  public ModelAndView toBlflEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqds/blfl/edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toBlflNewAdd.act"})
  public ModelAndView toBlflNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentCode = request.getParameter("parentCode");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("parentCode", parentCode);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/blfl/newAdd.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDxflSubIndex.act"})
  public ModelAndView toDxflSubIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/dxfl/subindex.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDxflList.act"})
  public ModelAndView toDxflList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentCode = request.getParameter("parentCode");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("parentCode", parentCode);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/dxfl/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDxflLeft.act"})
  public ModelAndView toDxflLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/dxfl/left.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDxflEdit.act"})
  public ModelAndView toDxflEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqds/dxfl/edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toDxflNewAdd.act"})
  public ModelAndView toDxflNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentCode = request.getParameter("parentCode");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("parentCode", parentCode);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/dxfl/newAdd.jsp");
    return mv;
  }
  
  @RequestMapping({"/toHzlySubIndex.act"})
  public ModelAndView toHzlySubIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/hzly/subindex.jsp");
    return mv;
  }
  
  @RequestMapping({"/toHzlyList.act"})
  public ModelAndView toHzlyList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentCode = request.getParameter("parentCode");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("parentCode", parentCode);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/hzly/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toHzlyLeft.act"})
  public ModelAndView toHzlyLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/hzly/left.jsp");
    return mv;
  }
  
  @RequestMapping({"/toHzlyEdit.act"})
  public ModelAndView toHzlyEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqds/hzly/edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toHzlyNewAdd.act"})
  public ModelAndView toHzlyNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentCode = request.getParameter("parentCode");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("parentCode", parentCode);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/hzly/newAdd.jsp");
    return mv;
  }
  
  @RequestMapping({"/toJzmdSubIndex.act"})
  public ModelAndView toJzmdSubIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/jzmd/subindex.jsp");
    return mv;
  }
  
  @RequestMapping({"/toJzmdList.act"})
  public ModelAndView toJzmdList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentCode = request.getParameter("parentCode");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("parentCode", parentCode);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/jzmd/list.jsp");
    return mv;
  }
  
  @RequestMapping({"/toJzmdLeft.act"})
  public ModelAndView toJzmdLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/jzmd/left.jsp");
    return mv;
  }
  
  @RequestMapping({"/toJzmdEdit.act"})
  public ModelAndView toJzmdEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqds/jzmd/edit.jsp");
    return mv;
  }
  
  @RequestMapping({"/toJzmdNewAdd.act"})
  public ModelAndView toJzmdNewAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentCode = request.getParameter("parentCode");
    String organization = request.getParameter("organization");
    ModelAndView mv = new ModelAndView();
    mv.addObject("parentCode", parentCode);
    mv.addObject("organization", organization);
    mv.setViewName("/kqds/jzmd/newAdd.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZPerson person = SessionUtil.getLoginPerson(request);
      YZDict dp = new YZDict();
      BeanUtils.populate(dp, request.getParameterMap());
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(dp.getParentCode()))
        throw new Exception("上级编号不能为空"); 
      if (YZUtility.isNullorEmpty(dp.getDictName()))
        throw new Exception("字典名称不能为空"); 
      if (YZUtility.isNullorEmpty(dp.getOrganization()))
        dp.setOrganization(""); 
      if (!YZUtility.isNullorEmpty(seqId)) {
        YZDict tmp = (YZDict)this.dictLogic.loadObjSingleUUID(TableNameUtil.SYS_DICT, seqId);
        if (tmp == null)
          throw new Exception("记录不存在"); 
        dp.setParentCode(tmp.getParentCode());
        dp.setDictCode(tmp.getDictCode());
        dp.setUseflag(tmp.getUseflag());
        dp.setCreatetime(tmp.getCreatetime());
        dp.setCreateuser(tmp.getCreateuser());
        this.dictLogic.updateSingleUUID(TableNameUtil.SYS_DICT, dp);
        SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.SYS_DICT, dp, TableNameUtil.SYS_DICT, request);
      } else {
        String uuid = this.dictLogic.getUniDictCodeByName(dp.getDictName());
        dp.setSeqId(uuid);
        dp.setDictCode(uuid);
        dp.setUseflag(Integer.valueOf(0));
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setCreateuser(person.getSeqId());
        this.dictLogic.saveSingleUUID(TableNameUtil.SYS_DICT, dp);
        SysLogUtil.log(SysLogUtil.NEW, SysLogUtil.SYS_DICT, dp, TableNameUtil.SYS_DICT, request);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getDictTree.act"})
  public String getDictTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String idStr = request.getParameter("id");
    if (YZUtility.isNullorEmpty(idStr))
      idStr = "0"; 
    try {
      List<JSONObject> list = this.dictLogic.getSubListByParentCode(idStr, request);
      for (JSONObject obj : list) {
        int count = this.dictLogic.countByParentCode(obj.getString("dictCode"));
        String dictName = obj.getString("dictName");
        if (count > 0) {
          dictName = String.valueOf(dictName) + "【" + count + "】";
          obj.put("icon", String.valueOf(request.getContextPath()) + "/static/image/admin/dict/folder.gif");
        } else {
          obj.put("icon", String.valueOf(request.getContextPath()) + "/static/image/admin/dict/file.jpg");
        } 
        obj.put("id", obj.getString("dictCode"));
        obj.put("pId", obj.getString("parentCode"));
        obj.put("name", dictName);
        obj.put("isParent", Boolean.valueOf(false));
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getDictTree4TreatItemType.act"})
  public String getDictTree4TreatItemType(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String idStr = request.getParameter("id");
    String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
    if (YZUtility.isNullorEmpty(idStr))
      idStr = "COSTITEM_SORT"; 
    try {
      List<JSONObject> list = this.dictLogic.getSubListByParentCodeAndOrg(idStr, organization);
      for (JSONObject obj : list) {
        boolean haveChild = this.dictLogic.IsHaveChild(obj.getString("dictCode"), organization);
        int count = this.dictLogic.countByParentCode(obj.getString("dictCode"), organization);
        String dictName = obj.getString("dictName");
        if (count > 0)
          dictName = String.valueOf(dictName) + "【" + count + "】"; 
        obj.put("id", obj.getString("dictCode"));
        obj.put("pId", obj.getString("parentCode"));
        obj.put("name", dictName);
        obj.put("isParent", Boolean.valueOf(haveChild));
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getDictTree4HZLY.act"})
  public String getDictTree4HZLY(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String idStr = request.getParameter("id");
    String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
    if (YZUtility.isNullorEmpty(idStr))
      idStr = DictUtil.HZLY; 
    try {
      List<JSONObject> list = this.dictLogic.getSubListByParentCodeAndOrg(idStr, organization);
      for (JSONObject obj : list) {
        boolean haveChild = this.dictLogic.IsHaveChild(obj.getString("dictCode"), organization);
        int count = this.dictLogic.countByParentCode(obj.getString("dictCode"), organization);
        String dictName = obj.getString("dictName");
        if (count > 0)
          dictName = String.valueOf(dictName) + "【" + count + "】"; 
        obj.put("id", obj.getString("dictCode"));
        obj.put("pId", obj.getString("parentCode"));
        obj.put("name", dictName);
        obj.put("isParent", Boolean.valueOf(haveChild));
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getDictTree4Blct.act"})
  public String getDictTree4Blct(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String idStr = request.getParameter("id");
    String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
    if (YZUtility.isNullorEmpty(idStr))
      idStr = "blct124"; 
    try {
      List<JSONObject> list = this.dictLogic.getSubListByParentCodeAndOrg(idStr, organization);
      for (JSONObject obj : list) {
        boolean haveChild = this.dictLogic.IsHaveChild(obj.getString("dictCode"), organization);
        int count = this.dictLogic.countByParentCode(obj.getString("dictCode"), organization);
        String dictName = obj.getString("dictName");
        if (count > 0)
          dictName = String.valueOf(dictName) + "【" + count + "】"; 
        obj.put("id", obj.getString("dictCode"));
        obj.put("pId", obj.getString("parentCode"));
        obj.put("name", dictName);
        obj.put("isParent", Boolean.valueOf(haveChild));
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getDictTree4BLFL.act"})
  public String getDictTree4BLFL(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String idStr = request.getParameter("id");
    String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
    if (YZUtility.isNullorEmpty(idStr))
      idStr = "BLFL"; 
    try {
      List<JSONObject> list = this.dictLogic.getSubListByParentCodeAndOrg(idStr, organization);
      for (JSONObject obj : list) {
        boolean haveChild = this.dictLogic.IsHaveChild(obj.getString("dictCode"), organization);
        int count = this.dictLogic.countByParentCode(obj.getString("dictCode"), organization);
        String dictName = obj.getString("dictName");
        if (count > 0)
          dictName = String.valueOf(dictName) + "【" + count + "】"; 
        obj.put("id", obj.getString("dictCode"));
        obj.put("pId", obj.getString("parentCode"));
        obj.put("name", dictName);
        obj.put("isParent", Boolean.valueOf(haveChild));
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getDictTree4JZMD.act"})
  public String getDictTree4JZMD(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String idStr = request.getParameter("id");
    String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
    if (YZUtility.isNullorEmpty(idStr))
      idStr = "JZMD"; 
    try {
      List<JSONObject> list = this.dictLogic.getSubListByParentCodeAndOrg(idStr, organization);
      for (JSONObject obj : list) {
        boolean haveChild = this.dictLogic.IsHaveChild(obj.getString("dictCode"), organization);
        int count = this.dictLogic.countByParentCode(obj.getString("dictCode"), organization);
        String dictName = obj.getString("dictName");
        if (count > 0)
          dictName = String.valueOf(dictName) + "【" + count + "】"; 
        obj.put("id", obj.getString("dictCode"));
        obj.put("pId", obj.getString("parentCode"));
        obj.put("name", dictName);
        obj.put("isParent", Boolean.valueOf(haveChild));
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getDictTree4DXFL.act"})
  public String getDictTree4DXFL(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String idStr = request.getParameter("id");
    String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
    if (YZUtility.isNullorEmpty(idStr))
      idStr = "DXFL"; 
    try {
      List<JSONObject> list = this.dictLogic.getSubListByParentCodeAndOrg(idStr, organization);
      for (JSONObject obj : list) {
        boolean haveChild = this.dictLogic.IsHaveChild(obj.getString("dictCode"), organization);
        int count = this.dictLogic.countByParentCode(obj.getString("dictCode"), organization);
        String dictName = obj.getString("dictName");
        if (count > 0)
          dictName = String.valueOf(dictName) + "【" + count + "】"; 
        obj.put("id", obj.getString("dictCode"));
        obj.put("pId", obj.getString("parentCode"));
        obj.put("name", dictName);
        obj.put("isParent", Boolean.valueOf(haveChild));
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getDictTree4All.act"})
  public String getDictTree4All(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = ChainUtil.getCurrentOrganization(request);
    String dictIds = request.getParameter("dictIds");
    String parentCode = request.getParameter("parentCode");
    if (YZUtility.isNullorEmpty(parentCode))
      parentCode = "0"; 
    List<JSONObject> treeList = new ArrayList<>();
    try {
      treeList = this.dictLogic.getDictNodeList(parentCode, treeList, dictIds, organization);
      for (JSONObject job : treeList) {
        String id = job.getString("id");
        boolean check = YZUtility.isStrInArrayEach(id, dictIds);
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
  
  @RequestMapping({"/selectPage.act"})
  public String selectPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      YZUtility.isNullorEmpty(organization);
      String parentCode = request.getParameter("parentCode");
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject jobj = this.dictLogic.selectPage(bp, parentCode, organization);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateFlagBySeqIds.act"})
  public String updateFlagBySeqIds(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      String flag = request.getParameter("flag");
      if (!"0".equals(flag) && !"1".equals(flag))
        throw new Exception("状态标识不正确"); 
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("主键为空或者null"); 
      this.dictLogic.updateFlagBySeqIds(seqId, flag, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, true, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteBySeqId.act"})
  public String deleteBySeqId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String seqId = request.getParameter("seqId");
      if (YZUtility.isNullorEmpty(seqId))
        throw new Exception("主键为空或者null"); 
      YZDict dict = (YZDict)this.dictLogic.loadObjSingleUUID(TableNameUtil.SYS_DICT, seqId);
      if (dict == null)
        throw new Exception("数据不存在"); 
      YZDict topDict = this.dictLogic.getTopDict(dict);
      if (DictUtil.HFFL.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_VISIT_SET, "hffl", dict.getSeqId());
        if (count > 0)
          throw new Exception("回访分类存在回访设置，无法删除！"); 
        count = this.utilLogic.selectCount(TableNameUtil.KQDS_VISIT, "hffl", dict.getSeqId());
        if (count > 0)
          throw new Exception("该回访分类下存在回访记录，无法删除！"); 
      } 
      if (DictUtil.XXKFL.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_INFORMATION, "type", dict.getSeqId());
        if (count > 0)
          throw new Exception("该分类下存在信息记录，无法删除！"); 
      } 
      if (DictUtil.ZZXT.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_ROOM, "zzxt", dict.getSeqId());
        if (count > 0)
          throw new Exception("该分类下存在预约手术记录，无法删除！"); 
      } 
      if (DictUtil.SHOUSS.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_ROOM, "roomid", dict.getSeqId());
        if (count > 0)
          throw new Exception("该手术室存在预约手术记录，无法删除！"); 
      } 
      if (DictUtil.YYFL.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_HOSPITAL_ORDER, "ordertype", dict.getSeqId());
        if (count > 0)
          throw new Exception("该预约分类存在预约记录，无法删除！"); 
      } 
      if (DictUtil.YYXM.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_HOSPITAL_ORDER, "orderitemtype", dict.getSeqId());
        if (count > 0)
          throw new Exception("该预约项目分类存在预约记录，无法删除！"); 
      } 
      if (DictUtil.QZKFXM.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_RECEIVEINFO, "dev_item", dict.getSeqId());
        if (count > 0)
          throw new Exception("该分类下存在咨询记录，无法删除！"); 
      } 
      if (DictUtil.WCJYY.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_RECEIVEINFO, "failreason1", dict.getSeqId());
        if (count > 0)
          throw new Exception("该分类下存在咨询记录，无法删除！"); 
      } 
      if (DictUtil.CHUFANG_YF.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_CHUFANG_DETAIL, "cfusage", dict.getSeqId());
        if (count > 0)
          throw new Exception("该分类下存在处方单，无法删除！"); 
      } 
      if (DictUtil.CHUFANG_YYTJ.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_CHUFANG_DETAIL, "cfusemethod", dict.getSeqId());
        if (count > 0)
          throw new Exception("该分类下存在处方单，无法删除！"); 
      } 
      if (DictUtil.SLLX.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_NET_ORDER, "accepttype", dict.getSeqId());
        if (count > 0)
          throw new Exception("该受理类型下存在网电预约记录，无法删除！"); 
      } 
      if (DictUtil.SLGJ.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_NET_ORDER, "accepttool", dict.getSeqId());
        if (count > 0)
          throw new Exception("该受理工具下存在网电预约记录，无法删除！"); 
      } 
      if (DictUtil.ZXXM.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_NET_ORDER, "askitem", dict.getSeqId());
        if (count > 0)
          throw new Exception("该咨询项目下存在网电预约记录，无法删除！"); 
      } 
      if (DictUtil.HYKFL.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_MEMBER, "memberlevel", dict.getSeqId());
        if (count > 0)
          throw new Exception("该级别下存在会员卡，无法删除！"); 
      } 
      if (DictUtil.GHFS.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_REG, "regsort", dict.getSeqId());
        if (count > 0)
          throw new Exception("该挂号分类存在挂号记录，无法删除！"); 
      } 
      if (DictUtil.JZFL.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_REG, "recesort", dict.getSeqId());
        if (count > 0)
          throw new Exception("该就诊分类存在挂号记录，无法删除！"); 
      } 
      if (DictUtil.JOB.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_USERDOCUMENT, "profession", dict.getSeqId());
        if (count > 0)
          throw new Exception("该分类下存在患者档案，无法删除！"); 
      } 
      if (DictUtil.WECHAT_NEWSSORT.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.WX_NEWS, "newstype", dict.getSeqId());
        if (count > 0)
          throw new Exception("该分类下存在图文信息，无法删除！"); 
      } 
      if (DictUtil.BLKFL.equals(topDict.getDictCode())) {
        int count = this.utilLogic.selectCount(TableNameUtil.KQDS_BLK, "blkfl", dict.getSeqId());
        if (count > 0)
          throw new Exception("该分类下存在病历模板，无法删除！"); 
      } 
      if (DictUtil.BLFL.equals(topDict.getDictCode()))
        if (DictUtil.BLFL.equals(dict.getParentCode())) {
          int count = this.utilLogic.selectCount(TableNameUtil.KQDS_MEDICALRECORD, "blfl", dict.getSeqId());
          if (count > 0)
            throw new Exception("该分类下存在病历，无法删除！"); 
        } else {
          int count = this.utilLogic.selectCount(TableNameUtil.KQDS_MEDICALRECORD, "bc", dict.getSeqId());
          if (count > 0)
            throw new Exception("该病程下存在病历，无法删除！"); 
        }  
      if (DictUtil.DXFL.equals(topDict.getDictCode()))
        if (DictUtil.DXFL.equals(dict.getParentCode())) {
          int count = this.utilLogic.selectCount(TableNameUtil.KQDS_SMS_MODEL, "smstype", dict.getSeqId());
          if (count > 0)
            throw new Exception("该分类下存在短信模板，无法删除！"); 
        } else {
          int count = this.utilLogic.selectCount(TableNameUtil.KQDS_SMS_MODEL, "smsnexttype", dict.getSeqId());
          if (count > 0)
            throw new Exception("该分类下存在短信模板，无法删除！"); 
        }  
      if (DictUtil.JZMD.equals(topDict.getDictCode()))
        if (DictUtil.JZMD.equals(dict.getParentCode())) {
          int count = this.utilLogic.selectCount(TableNameUtil.KQDS_JZMD_TYPE, "jzmd", dict.getSeqId());
          if (count > 0)
            throw new Exception("该分类下存在就诊目的设置，无法删除！"); 
          count = this.utilLogic.selectCount(TableNameUtil.KQDS_JZQK, "reggoal", dict.getSeqId());
          if (count > 0)
            throw new Exception("该分类下存在就诊情况，无法删除！"); 
        } else {
          int count = this.jzmdTypeLogic.selectCountLocal(dict.getSeqId());
          if (count > 0)
            throw new Exception("该分类下存在就诊目的设置，无法删除！"); 
          count = this.utilLogic.selectCount(TableNameUtil.KQDS_JZQK, "jzmd", dict.getSeqId());
          if (count > 0)
            throw new Exception("该分类下存在就诊情况，无法删除！"); 
        }  
      if (DictUtil.HZLY.equals(topDict.getDictCode()))
        if (DictUtil.HZLY.equals(dict.getParentCode())) {
          int count = this.utilLogic.selectCount(TableNameUtil.KQDS_USERDOCUMENT, "devchannel", dict.getSeqId());
          if (count > 0)
            throw new Exception("该分类下存在患者档案，无法删除！"); 
        } else {
          int count = this.utilLogic.selectCount(TableNameUtil.KQDS_USERDOCUMENT, "nexttype", dict.getSeqId());
          if (count > 0)
            throw new Exception("该分类下存在患者档案，无法删除！"); 
        }  
      if (DictUtil.COSTITEM_SORT.equals(topDict.getDictCode()))
        if (DictUtil.COSTITEM_SORT.equals(dict.getParentCode())) {
          int count = this.utilLogic.selectCount(TableNameUtil.KQDS_TREATITEM, "basetype", dict.getDictCode());
          if (count > 0)
            throw new Exception("该分类下存在收费项目，无法删除！"); 
        } else {
          int count = this.utilLogic.selectCount(TableNameUtil.KQDS_TREATITEM, "nexttype", dict.getSeqId());
          if (count > 0)
            throw new Exception("该分类下存在收费项目，无法删除！"); 
        }  
      if (DictUtil.GHFL.equals(topDict.getDictCode()))
        throw new Exception("挂号方式不允许删除！"); 
      if (DictUtil.YXFL.equals(topDict.getDictCode()))
        throw new Exception("影像分类不允许删除！"); 
      boolean isHaveChild = this.dictLogic.IsHaveChild(dict.getDictCode());
      if (isHaveChild)
        throw new Exception("请先删除子节点"); 
      this.dictLogic.deleteBySeqIds(seqId, request);
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
      YZDict person = (YZDict)this.dictLogic.loadObjSingleUUID(TableNameUtil.SYS_DICT, seqId);
      if (person == null)
        throw new Exception("记录不存在"); 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", JSONObject.fromObject(person).toString());
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getListByParentCode.act"})
  public void getListByParentCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String organization = request.getParameter("organization");
      if (YZUtility.isNullorEmpty(organization))
        organization = ChainUtil.getCurrentOrganization(request); 
      String parentCode = request.getParameter("parentCode");
      List<YZDict> list = null;
      list = this.dictLogic.getListByParentCode(parentCode, organization);
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getListByParentCodeIscustomer.act"})
  public void getListByParentCodeIscustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String deptId = person.getDeptId();
    String iscustomer = null;
    if (deptId.equals("aeafb25a-ae9f-4d22-8230-3725cb942435")) {
      iscustomer = "1";
    } else if (deptId.equals("658b52d6-a42a-4b3d-9a19-bd0bc77210c5")) {
      iscustomer = "1";
    } else if (deptId.equals("abcea651-b674-42fd-a9e1-0dbd08f61525")) {
      iscustomer = "1";
    } else if (deptId.equals("41f199cd-e4be-4cfc-b553-555d13605976")) {
      iscustomer = "1";
    } else if (deptId.equals("e6066145-dd3b-4bf9-b2aa-d63b45375930")) {
      iscustomer = "1";
    } else if (deptId.equals("355168c8-de68-4a7b-aa41-0b33a4c31073")) {
      iscustomer = "1";
    } 
    try {
      String parentCode = request.getParameter("parentCode");
      List<YZDict> list = null;
      list = this.dictLogic.getListByParentCodeIscustomer(parentCode, ChainUtil.getCurrentOrganization(request), iscustomer);
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getListByParentCodeOrg.act"})
  public void getListByParentCodeOrg(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String parentCode = request.getParameter("parentCode");
      List<YZDict> list = null;
      list = this.dictLogic.getListByParentCode(parentCode, ChainUtil.getOrganizationFromUrlCanNull(request));
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getListByParentCodeALL.act"})
  public void getListByParentCodeALL(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String parentCode = request.getParameter("parentCode");
      List<YZDict> list = null;
      list = this.dictLogic.getListByParentCodeALL(parentCode, ChainUtil.getOrganizationFromUrl(request));
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getSubListByParentSeqId.act"})
  public void getSubListByParentSeqId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String parentSeqId = request.getParameter("parentSeqId");
      if (YZUtility.isNullorEmpty(parentSeqId))
        throw new Exception("父级主键不能为空"); 
      YZDict parentDict = (YZDict)this.dictLogic.loadObjSingleUUID(TableNameUtil.SYS_DICT, parentSeqId);
      if (parentDict == null)
        throw new Exception("父级分类不存在"); 
      String isAdd = request.getParameter("isAdd");
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
        organization = ChainUtil.getCurrentOrganization(request); 
      List<YZDict> list = this.dictLogic.getSubListByParentCode(parentDict.getDictCode(), organization, isAdd);
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getSubListByParentSeqIdNoOrg.act"})
  public void getSubListByParentSeqIdNoOrg(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String parentSeqId = request.getParameter("parentSeqId");
      if (YZUtility.isNullorEmpty(parentSeqId))
        throw new Exception("父级主键不能为空"); 
      YZDict parentDict = (YZDict)this.dictLogic.loadObjSingleUUID(TableNameUtil.SYS_DICT, parentSeqId);
      if (parentDict == null)
        throw new Exception("父级分类不存在"); 
      String isAdd = request.getParameter("isAdd");
      List<YZDict> list = this.dictLogic.getSubListByParentCodeNoOrg(parentDict.getDictCode(), isAdd);
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getSubListByParentCode.act"})
  public void getSubListByParentCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String parentCode = request.getParameter("parentCode");
      String isAdd = request.getParameter("isAdd");
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      if (YZUtility.isNullorEmpty(organization))
        organization = ChainUtil.getCurrentOrganization(request); 
      List<YZDict> list = this.dictLogic.getSubListByParentCode(parentCode, organization, isAdd);
      JSONObject jobj = new JSONObject();
      jobj.put("list", list);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getDetailByDictCode.act"})
  public String getDetailByDictCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String dictCode = request.getParameter("dictCode");
      if (YZUtility.isNullorEmpty(dictCode))
        throw new Exception("编号不能为空"); 
      YZDict dict = this.dictLogic.getDetailByDictCode(dictCode);
      JSONObject jobj = new JSONObject();
      jobj.put("retData", JSONObject.fromObject(dict).toString());
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getDictNamesBySeqIds.act"})
  public void getDictNamesBySeqIds(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String ids = request.getParameter("ids");
      String data = "";
      if (!YZUtility.isNullorEmpty(ids)) {
        ids = YZUtility.ConvertStringIds4Query(ids);
        data = this.dictLogic.getDictNamesBySeqIds(ids);
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("data", data);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getDictNamesByDictCodes.act"})
  public void getDictNamesByDictCodes(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String ids = request.getParameter("dictCodes");
      String data = "";
      if (!YZUtility.isNullorEmpty(ids)) {
        ids = YZUtility.ConvertStringIds4Query(ids);
        data = this.dictLogic.getDictNamesByDictCodes(ids);
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("data", data);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getDictNameById.act"})
  public void getDictNameById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String dicIdStr = request.getParameter("dicId");
      String data = this.dictLogic.getDictNameBySeqId(dicIdStr);
      JSONObject jobj = new JSONObject();
      jobj.put("data", data);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getMaxOrderno.act"})
  public void getMaxOrderno(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String parentCode = request.getParameter("parentCode");
      String data = this.dictLogic.getMaxOrderno(parentCode);
      JSONObject jobj = new JSONObject();
      jobj.put("data", data);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getLITree.act"})
  public void getLITree(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentCode = request.getParameter("parentCode");
    List<JSONObject> listtree = new ArrayList<>();
    try {
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      List<JSONObject> list = this.dictLogic.getSubListByParentCodeAndOrg(parentCode, organization);
      for (int i = 0; i < list.size(); i++) {
        JSONObject base = list.get(i);
        JSONObject obj = new JSONObject();
        obj.put("id", base.getString("seqId"));
        obj.put("pId", parentCode);
        obj.put("name", base.getString("dictName"));
        obj.put("nocheck", "true");
        obj.put("isParent", "false");
        listtree.add(obj);
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", listtree);
      YZUtility.DEAL_SUCCESS(jobj, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getHFFLSeqid.act"})
  public void getHFFLSeqid(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String parentCode = request.getParameter("parentCode");
      String organization = ChainUtil.getOrganizationFromUrlCanNull(request);
      List<JSONObject> list = this.dictLogic.getSubListByParentCodeAndOrg(parentCode, organization);
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(null, false, ex, response, this.logger);
    } 
  }
}
