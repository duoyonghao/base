package com.hudh.lclj.controller;

import com.hudh.lclj.entity.LcljApprovedMemo;
import com.hudh.lclj.service.HUDH_LcljVerificationSheetService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
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
@RequestMapping({"HUDH_LcljCheckRecordAct"})
public class HUDH_LcljVerificationAct {
  private static Logger logger = LoggerFactory.getLogger(HUDH_LcljVerificationAct.class);
  
  @Autowired
  private HUDH_LcljVerificationSheetService logic;
  
  @RequestMapping({"/toRemindAssessor.act"})
  public ModelAndView toRemindAssessor(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("hudh/lclj/assessors_remind.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAwaitRemindAssessor.act"})
  public ModelAndView toAwaitRemindAssessor(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("hudh/lclj/await_assessors_remind.jsp");
    return mv;
  }
  
  @RequestMapping({"/toAssessor.act"})
  public ModelAndView toAssessor(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("hudh/lclj/assessors.jsp");
    return mv;
  }
  
  @RequestMapping({"/insertOrUpdate.act"})
  public String insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String lcljid = request.getParameter("lcljid");
    try {
      LcljApprovedMemo bean = new LcljApprovedMemo();
      BeanUtils.populate(bean, request.getParameterMap());
      bean.setLcljId(lcljid);
      if (!YZUtility.isNullorEmpty(bean.getSeqId())) {
        this.logic.Update(bean);
      } else {
        bean.setSeqId(YZUtility.getUUID());
        this.logic.insert(bean);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getCheckRecord.act"})
  public String getCheckRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String LcljId = request.getParameter("LcljId");
    String createuser = request.getParameter("createuser");
    String status = request.getParameter("status");
    YZPerson person = SessionUtil.getLoginPerson(request);
    String visualstaff = SessionUtil.getVisualstaff(request);
    try {
      Map<String, String> map = new HashMap<>();
      if (YZUtility.isNotNullOrEmpty(visualstaff))
        map.put("querytype", visualstaff); 
      if (!YZUtility.isNullorEmpty(LcljId)) {
        map.put("LcljId", LcljId);
      } else if (!YZUtility.isNullorEmpty(createuser)) {
        map.put("createuser", createuser);
      } else {
        throw new Exception("请选择要操作的记录");
      } 
      if (!YZUtility.isNullorEmpty(status))
        map.put("status", status); 
      List<JSONObject> list = this.logic.getCheckRecord(map, person);
      for (JSONObject jsonO : list) {
        String nodename = jsonO.getString("nodename");
        String nodelimit = jsonO.getString("nodelimit");
        jsonO.put("name", String.valueOf(nodename) + "(" + nodelimit + ")");
      } 
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getAwaitCheckRecord.act"})
  public String getAwaitCheckRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String LcljId = request.getParameter("LcljId");
    String createuser = request.getParameter("createuser");
    String status = request.getParameter("status");
    YZPerson person = SessionUtil.getLoginPerson(request);
    String visualstaff = SessionUtil.getVisualstaff(request);
    try {
      Map<String, String> map = new HashMap<>();
      if (YZUtility.isNotNullOrEmpty(visualstaff))
        map.put("querytype", visualstaff); 
      if (!YZUtility.isNullorEmpty(LcljId)) {
        map.put("LcljId", LcljId);
      } else if (!YZUtility.isNullorEmpty(createuser)) {
        map.put("createuser", createuser);
      } else {
        throw new Exception("请选择要操作的记录");
      } 
      map.put("status", "0");
      List<JSONObject> list = this.logic.getAwaitCheckRecord(map, person);
      for (JSONObject jsonO : list) {
        String nodename = jsonO.getString("nodename");
        String nodelimit = jsonO.getString("nodelimit");
        jsonO.put("name", String.valueOf(nodename) + "(" + nodelimit + ")");
      } 
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getCheckRecordNum.act"})
  public String getCheckRecordNum(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String createuser = request.getParameter("createuser");
    String status = request.getParameter("status");
    try {
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(createuser))
        map.put("createuser", createuser); 
      if (!YZUtility.isNullorEmpty(status))
        map.put("status", status); 
      int count = this.logic.getCheckRecordNum(map).intValue();
      JSONObject json = new JSONObject();
      json.put("count", Integer.valueOf(count));
      YZUtility.DEAL_SUCCESS(json, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/getAwaitVerifieNum.act"})
  public String getAwaitVerifieNum(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String createuser = request.getParameter("createuser");
    String status = request.getParameter("status");
    try {
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(createuser))
        map.put("createuser", null); 
      if (!YZUtility.isNullorEmpty(status))
        map.put("status", "0"); 
      int count = this.logic.getAwaitVerifieNum(map).intValue();
      JSONObject json = new JSONObject();
      json.put("count", Integer.valueOf(count));
      YZUtility.DEAL_SUCCESS(json, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/delCheckRecord.act"})
  public String delCheckRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    try {
      if (YZUtility.isNullorEmpty(id))
        throw new Exception("请选择需要删除的记录"); 
      this.logic.delCheckRecord(id);
      YZUtility.DEAL_SUCCESS(null, id, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(id, false, e, response, logger);
    } 
    return null;
  }
}
