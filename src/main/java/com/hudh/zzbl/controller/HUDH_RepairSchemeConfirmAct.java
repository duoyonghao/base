package com.hudh.zzbl.controller;

import com.hudh.zzbl.entity.RepairSchemeConfirm;
import com.hudh.zzbl.service.IRepairSchemeConfirmService;
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
@RequestMapping({"/HUDH_RepairSchemeConfirmAct"})
public class HUDH_RepairSchemeConfirmAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_RepairSchemeConfirmAct.class);
  
  @Autowired
  private IRepairSchemeConfirmService repairSchemeConfirmService;
  
  @RequestMapping({"/saveRepairSchemeConfirmInfro.act"})
  public String saveRepairSchemeConfirmInfro(HttpServletRequest request, HttpServletResponse response) throws Exception {
    RepairSchemeConfirm dp = new RepairSchemeConfirm();
    YZPerson person = SessionUtil.getLoginPerson(request);
    String organization = ChainUtil.getCurrentOrganization(request);
    dp.setCreateuser(person.getUserId());
    dp.setOrganization(organization);
    try {
      this.repairSchemeConfirmService.saveRepairSchemeConfirmInfro(dp, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateRepairInforById.act"})
  public String updateRepairInforById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("seqId");
    JSONObject json = this.repairSchemeConfirmService.selectRepairInforById(id);
    RepairSchemeConfirm dp = (RepairSchemeConfirm)JSONObject.toBean(json, RepairSchemeConfirm.class);
    try {
      this.repairSchemeConfirmService.updateRepairInforById(dp, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findRepairInforById.act"})
  public String findRepairInforById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    try {
      List<JSONObject> json = this.repairSchemeConfirmService.findRepairInforById(id);
      YZUtility.RETURN_LIST(json, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findReapirInforAll.act"})
  public String findReapirInforAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      List<JSONObject> list = this.repairSchemeConfirmService.findReapirInforAll();
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
}
