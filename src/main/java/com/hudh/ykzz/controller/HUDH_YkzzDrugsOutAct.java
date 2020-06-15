package com.hudh.ykzz.controller;

import com.hudh.ykzz.entity.YkzzDrugsOut;
import com.hudh.ykzz.service.IYkzzDrugsOutService;
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
@RequestMapping({"/HUDH_YkzzDrugsOutAct"})
public class HUDH_YkzzDrugsOutAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_YkzzDrugsOutAct.class);
  
  @Autowired
  private IYkzzDrugsOutService ykzzDrugsOutService;
  
  @RequestMapping({"/insertDrugsOut.act"})
  public String insertDrugsIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String drugsOutdetails = request.getParameter("paramDetail");
    String cktime = request.getParameter("cktime");
    String outtype = request.getParameter("outtype");
    String supplier = request.getParameter("supplier");
    String outcode = request.getParameter("outcode");
    String outremark = request.getParameter("outremark");
    String outdept = request.getParameter("outDept");
    String zhaiyao = request.getParameter("zhaiyao");
    YkzzDrugsOut ykzzDrugsOut = new YkzzDrugsOut();
    ykzzDrugsOut.setCktime(cktime);
    ykzzDrugsOut.setOuttype(outtype);
    ykzzDrugsOut.setSupplier(supplier);
    ykzzDrugsOut.setOutcode(outcode);
    ykzzDrugsOut.setOutremark(outremark);
    ykzzDrugsOut.setZhaiyao(zhaiyao);
    ykzzDrugsOut.setOutdept(outdept);
    try {
      this.ykzzDrugsOutService.insertDrugsOut(ykzzDrugsOut, drugsOutdetails, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteDrugsOut.act"})
  public String deleteDrugsOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    try {
      String backMg = this.ykzzDrugsOutService.deleteDrugsOut(id);
      YZUtility.DEAL_SUCCESS(null, backMg, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findAllDrugsOut.act"})
  public void findAllDrugsOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String outtype = request.getParameter("intype");
    String supplier = request.getParameter("supplier");
    String outcode = request.getParameter("incode");
    String accurateOutcode = request.getParameter("accurateOutcode");
    String organization = ChainUtil.getCurrentOrganization(request);
    Map<String, String> map = new HashMap<>();
    if (!YZUtility.isNullorEmpty(starttime))
      map.put("starttime", starttime); 
    if (!YZUtility.isNullorEmpty(endtime))
      map.put("endtime", endtime); 
    if (!YZUtility.isNullorEmpty(outtype))
      map.put("outtype", outtype); 
    if (!YZUtility.isNullorEmpty(supplier))
      map.put("supplier", supplier); 
    if (!YZUtility.isNullorEmpty(outcode))
      map.put("outcode", outcode); 
    if (!YZUtility.isNullorEmpty(accurateOutcode))
      map.put("accurateOutcode", accurateOutcode); 
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    try {
      List<JSONObject> list = this.ykzzDrugsOutService.findAllDrugsOut(map);
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
  }
  
  @RequestMapping({"/findDetailByParendId.act"})
  public void findDetailByParendId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String parentid = request.getParameter("parentid");
    try {
      if (YZUtility.isNotNullOrEmpty(parentid)) {
        List<JSONObject> list = this.ykzzDrugsOutService.findDetailByParendId(parentid);
        YZUtility.RETURN_LIST(list, response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
  }
  
  @RequestMapping({"/getDrugsInBatchnum.act"})
  public String getDrugsInBatchnum(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String order_no = request.getParameter("order_no");
    String outnum = request.getParameter("outnum");
    try {
      String batchnum = this.ykzzDrugsOutService.getDrugsInBatchnum(order_no, outnum);
      JSONObject json = new JSONObject();
      json.put("dataBatchnum", batchnum);
      YZUtility.DEAL_SUCCESS(json, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, this.logger);
    } 
    return null;
  }
}
