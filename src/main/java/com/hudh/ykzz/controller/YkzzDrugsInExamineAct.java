package com.hudh.ykzz.controller;

import com.hudh.ykzz.entity.YkzzDrugsInExamine;
import com.hudh.ykzz.service.IYkzzDrugsInExamineService;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/HUDH_YkzzDrugsInExamineAct"})
public class YkzzDrugsInExamineAct {
  private Logger logger = LoggerFactory.getLogger(YkzzDrugsInExamineAct.class);
  
  @Autowired
  private IYkzzDrugsInExamineService ykzzDrugsInExamineService;
  
  @RequestMapping({"/insertDrugsInExamine.act"})
  public String insertDrugsInExamine(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String drugsInId = request.getParameter("drugsInId");
    String packing = request.getParameter("packing");
    String certificate = request.getParameter("certificate");
    String report = request.getParameter("report");
    String checkDate = request.getParameter("checkDate");
    String remark = request.getParameter("remark");
    YkzzDrugsInExamine ykzzDrugsInExamine = new YkzzDrugsInExamine();
    ykzzDrugsInExamine.setDrugsInId(drugsInId);
    ykzzDrugsInExamine.setPacking(packing);
    ykzzDrugsInExamine.setCertificate(certificate);
    ykzzDrugsInExamine.setReport(report);
    ykzzDrugsInExamine.setCheckDate(checkDate);
    ykzzDrugsInExamine.setRemark(remark);
    try {
      this.ykzzDrugsInExamineService.insertDrugsInExamine(ykzzDrugsInExamine, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findDrugsInExamineByInId.act"})
  public String findDrugsInExamineByInId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String drugsInId = request.getParameter("parentid");
    try {
      if (YZUtility.isNotNullOrEmpty(drugsInId)) {
        List<JSONObject> list = new ArrayList<>();
        list = this.ykzzDrugsInExamineService.findDrugsInExamineByInId(drugsInId);
        YZUtility.RETURN_LIST(list, response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteDrugsInExamineById.act"})
  public String deleteDrugsInExamineById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    try {
      if (YZUtility.isNotNullOrEmpty(id)) {
        this.ykzzDrugsInExamineService.deleteDrugsInExamineById(id);
        YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteDrugsInExamineByInId.act"})
  public String deleteDrugsInExamineByInId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String drugsInId = request.getParameter("drugsInId");
    try {
      if (YZUtility.isNotNullOrEmpty(drugsInId)) {
        this.ykzzDrugsInExamineService.deleteDrugsInExamineByInId(drugsInId);
        YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findDugsExamineDetail.act"})
  public String findDugsExamineDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String flag = request.getParameter("flag");
    String fieldArr = request.getParameter("fieldArr");
    String fieldnameArr = request.getParameter("fieldnameArr");
    Map<String, String> dataMap = new HashMap<>();
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String usercode = request.getParameter("usercode");
    String username = request.getParameter("username");
    String itemno = request.getParameter("itemno");
    String itemname = request.getParameter("itemname");
    String organization = ChainUtil.getCurrentOrganization(request);
    if (YZUtility.isNotNullOrEmpty(starttime))
      dataMap.put("starttime", String.valueOf(starttime) + " 00:00:00"); 
    if (YZUtility.isNotNullOrEmpty(organization))
      dataMap.put("organization", organization); 
    if (YZUtility.isNotNullOrEmpty(endtime))
      dataMap.put("endtime", String.valueOf(endtime) + " 23:59:59"); 
    if (YZUtility.isNotNullOrEmpty(usercode))
      dataMap.put("usercode", usercode); 
    if (YZUtility.isNotNullOrEmpty(username))
      dataMap.put("username", username); 
    if (YZUtility.isNotNullOrEmpty(itemno))
      dataMap.put("itemno", itemno); 
    if (YZUtility.isNotNullOrEmpty(itemname))
      dataMap.put("itemname", itemname); 
    try {
      List<JSONObject> list = new ArrayList<>();
      list = this.ykzzDrugsInExamineService.findDugsExamineDetail(dataMap);
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("药品出库明细", fieldArr, fieldnameArr, list, response, request);
        return null;
      } 
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findDugsReturnDetail.act"})
  public String findDugsReturnDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String flag = request.getParameter("flag");
    String fieldArr = request.getParameter("fieldArr");
    String fieldnameArr = request.getParameter("fieldnameArr");
    Map<String, String> dataMap = new HashMap<>();
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String usercode = request.getParameter("usercode");
    String username = request.getParameter("username");
    String itemno = request.getParameter("itemno");
    String itemname = request.getParameter("itemname");
    String organization = ChainUtil.getCurrentOrganization(request);
    if (YZUtility.isNotNullOrEmpty(organization))
      dataMap.put("organization", organization); 
    if (YZUtility.isNotNullOrEmpty(starttime))
      dataMap.put("starttime", String.valueOf(starttime) + " 00:00:00"); 
    if (YZUtility.isNotNullOrEmpty(endtime))
      dataMap.put("endtime", String.valueOf(endtime) + " 23:59:59"); 
    if (YZUtility.isNotNullOrEmpty(usercode))
      dataMap.put("usercode", usercode); 
    if (YZUtility.isNotNullOrEmpty(username))
      dataMap.put("username", username); 
    if (YZUtility.isNotNullOrEmpty(itemno))
      dataMap.put("itemno", itemno); 
    if (YZUtility.isNotNullOrEmpty(itemname))
      dataMap.put("itemname", itemname); 
    try {
      List<JSONObject> list = new ArrayList<>();
      list = this.ykzzDrugsInExamineService.findDugsReturnDetail(dataMap);
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("药品退药明细", fieldArr, fieldnameArr, list, response, request);
        return null;
      } 
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
}
