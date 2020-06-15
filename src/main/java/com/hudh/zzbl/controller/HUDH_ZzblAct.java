package com.hudh.zzbl.controller;

import com.hudh.zzbl.entity.ZzblOperation;
import com.hudh.zzbl.service.IZzblService;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
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
@RequestMapping({"/HUDH_ZzblAct"})
public class HUDH_ZzblAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_ZzblAct.class);
  
  @Autowired
  private IZzblService zzblService;
  
  @Autowired
  private DaoSupport dao;
  
  @RequestMapping({"/save.act"})
  public String save(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ZzblOperation dp = new ZzblOperation();
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    dp.setCreateuser(person.getUserId());
    dp.setOrganization(organization);
    try {
      this.zzblService.save(dp, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findZzblOprationById.act"})
  public JSONObject findZzblOprationById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    try {
      List<ZzblOperation> json = this.zzblService.findZzblOprationById(id);
      YZUtility.RETURN_LIST(json, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findAllZzblInfor.act"})
  public String findAllZzblInfor(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      List<JSONObject> list = this.zzblService.findAllZzblInfor();
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateZzblOprationById.act"})
  public String updateZzblOprationById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    JSONObject jsonObject = this.zzblService.selectZzblOperationById(id);
    String seqId = jsonObject.getString("seq_id");
    ZzblOperation dp = (ZzblOperation)JSONObject.toBean(jsonObject, ZzblOperation.class);
    dp.setSEQ_ID(seqId);
    try {
      this.zzblService.updateZzblOprationById(dp, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectedAnamnesisInfor.act"})
  public String selectedAnamnesisInfor(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    String status = request.getParameter("status");
    try {
      Map<String, String> map = new HashMap<>();
      map.put("id", id);
      map.put("status", status);
      this.dao.update("HUDH_DZBL_AdviceNote.selectedAnamnesisInfor", map);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectedExamineDiagnoseInfor.act"})
  public String selectedExamineDiagnoseInfor(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    String status = request.getParameter("status");
    try {
      Map<String, String> map = new HashMap<>();
      map.put("id", id);
      map.put("status", status);
      this.dao.update("HUDH_ZZBL_CHECK.selectedExamineDiagnoseInfor", map);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectedScheme.act"})
  public String selectedScheme(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    String status = request.getParameter("status");
    try {
      Map<String, String> map = new HashMap<>();
      map.put("id", id);
      map.put("status", status);
      this.dao.update("HUDH_ZZBL_DETAIL.selectedScheme", map);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectedRepairProjectInfor.act"})
  public String selectedRepairProjectInfor(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    String status = request.getParameter("status");
    try {
      Map<String, String> map = new HashMap<>();
      map.put("id", id);
      map.put("status", status);
      this.dao.update("HUDH_ZZBL_REPAIR_DETAIL.selectedRepairProjectInfor", map);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
}
