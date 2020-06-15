package com.kqds.controller.base.machiningCenter;

import com.kqds.entity.base.KqdsMachiningType;
import com.kqds.service.base.machiningCenter.KQDS_MACHININGTypeLogic;
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
@RequestMapping({"KQDS_MACHININGTypeAct"})
public class KQDS_MACHININGTypeAct {
  private static Logger logger = LoggerFactory.getLogger(KQDS_MACHININGTypeAct.class);
  
  @Autowired
  private KQDS_MACHININGTypeLogic logic;
  
  @RequestMapping({"/toJgIndex.act"})
  public ModelAndView toJgIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/jgzx/jg_index.jsp");
    return mv;
  }
  
  @RequestMapping({"/toJgAddGenre.act"})
  public ModelAndView toJgAddGenre(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/jiagong/jg_reparierenManage.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAddProcess.act"})
  public ModelAndView toSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.setViewName("/kqdsFront/jiagong/add_process.jsp");
    return mv;
  }
  
  @RequestMapping({"/toOneClassify.act"})
  public ModelAndView toCkHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/jiagong/first_classify.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAddFirstclassify.act"})
  public ModelAndView toAddFirstclassify(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    String typename = request.getParameter("typename");
    ModelAndView mv = new ModelAndView();
    mv.addObject("seqId", seqId);
    mv.addObject("typename", typename);
    mv.setViewName("/kqdsFront/jiagong/add_firstclassify.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String organization = ChainUtil.getCurrentOrganization(request);
    try {
      KqdsMachiningType dp = new KqdsMachiningType();
      BeanUtils.populate(dp, request.getParameterMap());
      if (dp.getSeqId() != null && !dp.getSeqId().equals("")) {
        this.logic.updateSingleUUID("KQDS_MACHINING_TYPE", dp);
        SysLogUtil.log(SysLogUtil.MODIFY, SysLogUtil.MODIFY, dp, TableNameUtil.KQDS_OUTPROCESSING_TYPE, request);
      } else {
        dp.setSeqId(YZUtility.getUUID());
        String parentId = dp.getParentId();
        if (parentId == null)
          dp.setParentId("1"); 
        if (dp.getIsCategory() == null)
          dp.setIsCategory("0"); 
        dp.setCreatetime(YZUtility.getCurDateTimeStr());
        dp.setOrganization(organization);
        dp.setUseflag(Integer.valueOf(1));
        this.logic.saveSingleUUID("KQDS_MACHINING_TYPE", dp);
        SysLogUtil.log(SysLogUtil.NEW, SysLogUtil.NEW, dp, "KQDS_MACHINING_TYPE", request);
      } 
      YZUtility.DEAL_SUCCESS_VALID(true, response);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getPrimaryCategories.act"})
  public String getPrimaryCategories(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentId = request.getParameter("parentId");
    try {
      List<JSONObject> listAll = new ArrayList<>();
      Map<String, String> map = new HashMap<>();
      if (YZUtility.isNullorEmpty(parentId)) {
        map.put("parentId", "1");
        listAll = this.logic.getListAll(map);
      } 
      YZUtility.RETURN_LIST(listAll, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/initPrimary.act"})
  public String initPrimary(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentId = request.getParameter("parentId");
    String lv = request.getParameter("lv");
    Map<String, String> map = new HashMap<>();
    List<JSONObject> listTree = new ArrayList<>();
    try {
      if (YZUtility.isNullorEmpty(parentId)) {
        map.put("parentId", "1");
        map.put("isCategory", "1");
        List<JSONObject> listAll = this.logic.getCategory(map);
        if (listAll != null && listAll.size() > 0)
          for (JSONObject jsonObject : listAll) {
            JSONObject json = new JSONObject();
            json.put("id", jsonObject.get("seq_id"));
            json.put("name", jsonObject.get("typename"));
            json.put("open", "false");
            List<JSONObject> getnextType = getnextType(jsonObject.getString("seq_id"));
            if (getnextType != null && getnextType.size() > 0) {
              json.put("isParent", "true");
            } else {
              json.put("isParent", "false");
            } 
            listTree.add(json);
          }  
      } else if ("1".equals(lv)) {
        map.put("parentId", parentId);
        map.put("isCategory", "1");
        List<JSONObject> listAll = this.logic.getCategory(map);
        if (listAll != null && listAll.size() > 0)
          for (JSONObject jsonObject : listAll) {
            JSONObject json = new JSONObject();
            json.put("id", jsonObject.get("seq_id"));
            json.put("name", jsonObject.get("typename"));
            json.put("pId", jsonObject.get("seq_id"));
            List<JSONObject> getnextType = getnextType(jsonObject.getString("seq_id"));
            if (getnextType != null && getnextType.size() > 0) {
              json.put("isParent", "true");
            } else {
              json.put("isParent", "false");
            } 
            listTree.add(json);
          }  
      } else {
        map.put("parentId", parentId);
        map.put("isCategory", "1");
        List<JSONObject> listAll = this.logic.getCategory(map);
        if (listAll != null && listAll.size() > 0)
          for (JSONObject jsonObject : listAll) {
            JSONObject json = new JSONObject();
            json.put("id", jsonObject.get("seq_id"));
            json.put("pId", "0");
            json.put("name", jsonObject.get("typename"));
            json.put("nocheck", "true");
            List<JSONObject> getnextType = getnextType(jsonObject.getString("seq_id"));
            if (getnextType != null && getnextType.size() > 0) {
              json.put("isParent", "true");
            } else {
              json.put("isParent", "false");
            } 
            listTree.add(json);
          }  
      } 
      JSONObject jobj = new JSONObject();
      jobj.put("retData", listTree);
      YZUtility.DEAL_SUCCESS(jobj, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  public List<JSONObject> getnextType(String parentId) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("parentId", parentId);
    map.put("isCategory", "1");
    return this.logic.getCategory(map);
  }
  
  @RequestMapping({"/findnextType.act"})
  public String findnextType(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentId = request.getParameter("parentId");
    Map<String, String> map = new HashMap<>();
    try {
      if (YZUtility.isNotNullOrEmpty(parentId))
        map.put("parentId", parentId); 
      List<JSONObject> list = this.logic.findnextType(map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findPrimary.act"})
  public String findPrimary(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentId = request.getParameter("parentId");
    try {
      Map<String, String> map = new HashMap<>();
      List<JSONObject> list = new ArrayList<>();
      if (YZUtility.isNullorEmpty(parentId)) {
        map.put("parentId", "");
        map.put("isCategory", "0");
        list = this.logic.getListAll(map);
      } else {
        map.put("parentId", parentId);
        map.put("isCategory", "0");
        list = this.logic.getListAll(map);
      } 
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findPrimaryByParentId.act"})
  public String findPrimaryByParentId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentId = request.getParameter("parentId");
    try {
      Map<String, String> map = new HashMap<>();
      if (YZUtility.isNullorEmpty(parentId)) {
        map.put("parentId", "1");
      } else {
        map.put("parentId", parentId);
      } 
      List<JSONObject> list = this.logic.getListAll(map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/delPrimaryBySeqId.act"})
  public String delPrimaryBySeqId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seqId");
    try {
      this.logic.delPrimaryBySeqId(seqId);
      YZUtility.DEAL_SUCCESS_VALID(true, response);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(seqId, false, e, response, logger);
    } 
    return null;
  }
}
