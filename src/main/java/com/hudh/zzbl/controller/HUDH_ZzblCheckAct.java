package com.hudh.zzbl.controller;

import com.hudh.zzbl.entity.ZzblCheck;
import com.hudh.zzbl.service.IZzblCheckService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/HUDH_ZzblCheckAct"})
public class HUDH_ZzblCheckAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_ZzblCheckAct.class);
  
  @Autowired
  private IZzblCheckService zzblCheckService;
  
  @RequestMapping({"/save.act"})
  public String save(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ZzblCheck dp = new ZzblCheck();
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    dp.setCreateuser(person.getUserId());
    dp.setOrganization(organization);
    try {
      this.zzblCheckService.insertZzblCheck(dp, request);
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
      List<JSONObject> json = this.zzblCheckService.findZzblOprationById(id);
      YZUtility.RETURN_LIST(json, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findAllZzblInfor.act"})
  public String findAllZzblInfor(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      List<JSONObject> list = this.zzblCheckService.findAllZzblInfor();
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateZzblOprationById.act"})
  public String updateZzblOprationById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("seqId");
    ZzblCheck dp = this.zzblCheckService.selectZzblCheckById(id);
    if (YZUtility.isNotNullOrEmpty(id))
      dp.setSEQ_ID(id); 
    try {
      this.zzblCheckService.updateZzblOprationById(dp, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
}
