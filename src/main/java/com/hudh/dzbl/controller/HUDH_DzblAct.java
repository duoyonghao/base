package com.hudh.dzbl.controller;

import com.hudh.dzbl.entity.DzblDetail;
import com.hudh.dzbl.entity.DzblTemplate;
import com.hudh.dzbl.service.IDzblTemplateService;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.log.BcjlUtil;
import java.io.File;
import java.io.FileInputStream;
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

@Controller
@RequestMapping({"/HUDH_DzblAct"})
public class HUDH_DzblAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_DzblAct.class);
  
  @Autowired
  private IDzblTemplateService dzblTemplateService;
  
  @RequestMapping({"/saveBlTemple.act"})
  public void saveBlTemple(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String title = request.getParameter("title");
    String blfl = request.getParameter("blfl");
    String bc = request.getParameter("bc");
    String sstype = request.getParameter("sstype");
    String templeDetail = request.getParameter("templeDetail");
    String organization = ChainUtil.getCurrentOrganization(request);
    DzblTemplate dzblTemplate = new DzblTemplate();
    dzblTemplate.setTitle(title);
    dzblTemplate.setBlfl(blfl);
    dzblTemplate.setBc(bc);
    dzblTemplate.setOrganization(organization);
    dzblTemplate.setDetail(templeDetail);
    dzblTemplate.setSstype(sstype);
    try {
      this.dzblTemplateService.insertDzblTemplate(dzblTemplate);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
  }
  
  @RequestMapping({"/findDzblTemplateById.act"})
  public void findDzblTemplateById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String blId = request.getParameter("blId");
    try {
      JSONObject jo = this.dzblTemplateService.findDzblTemplateById(blId);
      YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
  }
  
  @RequestMapping({"/findTemplateByBlflAndBc.act"})
  public void findTemplateByBlflAndBc(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String blfl = request.getParameter("blfl");
    String bc = request.getParameter("bc");
    String sstype = request.getParameter("sstype");
    String organization = ChainUtil.getCurrentOrganization(request);
    try {
      JSONObject jo = this.dzblTemplateService.findTemplateByBlflAndBc(blfl, bc, sstype, organization);
      YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
  }
  
  @RequestMapping({"/findBcByBlfl.act"})
  public void findBcByBlfl(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String blfl = request.getParameter("blfl");
    String sstype = request.getParameter("sstype");
    try {
      List<JSONObject> list = this.dzblTemplateService.findBcByBlfl(blfl, sstype);
      JSONObject jo = new JSONObject();
      jo.put("list", list);
      YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
  }
  
  @RequestMapping({"/insertDzblDetail.act"})
  public String insertDzblDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String blfl = request.getParameter("blfl");
    String bc = request.getParameter("bc");
    String templeDetail = request.getParameter("templeDetail");
    String type = request.getParameter("type");
    String blcode = request.getParameter("blcode");
    String sstype = request.getParameter("sstype");
    String organization = ChainUtil.getCurrentOrganization(request);
    DzblDetail dzblDetail = new DzblDetail();
    String id = request.getParameter("id");
    dzblDetail.setBlfl(blfl);
    dzblDetail.setBc(bc);
    dzblDetail.setSstype(sstype);
    dzblDetail.setDetail(templeDetail);
    dzblDetail.setOrganization(organization);
    if (type.equals("1")) {
      dzblDetail.setStatus("1");
    } else {
      dzblDetail.setStatus("0");
    } 
    try {
      if (YZUtility.isNotNullOrEmpty(id)) {
        dzblDetail.setId(id);
        this.dzblTemplateService.updateDzblById(dzblDetail, request);
        BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_CHANGE_KEFU, dzblDetail, id, TableNameUtil.KQDS_CHANGE_KEFU, request);
      } else {
        dzblDetail.setBlcode(blcode);
        JSONObject jo = this.dzblTemplateService.findTemplateByBlflAndBc(blfl, bc, sstype, organization);
        if (jo != null) {
          dzblDetail.setTitle((String)jo.get("title"));
          this.dzblTemplateService.insertDzblDetail(dzblDetail, request);
          BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.KQDS_CHANGE_KEFU, dzblDetail, (String)jo.get("title"), TableNameUtil.KQDS_CHANGE_KEFU, request);
        } 
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findDzblByBlcode.act"})
  public void findDzblByBlcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String blCode = request.getParameter("blCode");
    try {
      List<JSONObject> list = null;
      if (YZUtility.isNotNullOrEmpty(blCode))
        list = this.dzblTemplateService.findDzblByBlcode(blCode); 
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
  }
  
  @RequestMapping({"/findPrintInfo.act"})
  public void findPrintInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String blId = request.getParameter("blId");
    String blCode = request.getParameter("blCode");
    try {
      JSONObject userDoc = null;
      JSONObject dzblInfo = null;
      if (YZUtility.isNotNullOrEmpty(blCode) && YZUtility.isNotNullOrEmpty(blId)) {
        userDoc = this.dzblTemplateService.findUserDocByBlCode(blCode);
        dzblInfo = this.dzblTemplateService.findDzblById(blId);
        String detail = dzblInfo.getString("detail");
        if (YZUtility.isNotNullOrEmpty(detail)) {
          detail = detail.replace("&nbsp;", "").replace("<u>", "").replace("</u>", "");
          dzblInfo.put("detail", detail);
        } 
      } 
      JSONObject jo = new JSONObject();
      jo.put("userDoc", userDoc);
      jo.put("dzblInfo", dzblInfo);
      YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
  }
  
  @RequestMapping({"/dzblPrint.act"})
  public String dzblPrint(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String blId = request.getParameter("blId");
    String blCode = request.getParameter("blCode");
    String ssTime = request.getParameter("ssTime");
    response.setContentType("application/pdf");
    try {
      String filePath = this.dzblTemplateService.dzblPrint(request, blId, blCode, ssTime);
      File f = new File(filePath);
      FileInputStream in = new FileInputStream(f);
      ServletOutputStream servletOutputStream = response.getOutputStream();
      byte[] b = new byte[8192];
      while (in.read(b) != -1)
        servletOutputStream.write(b); 
      servletOutputStream.flush();
      in.close();
      servletOutputStream.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  @RequestMapping({"/selectHasDzblOptAuth.act"})
  public void selectHasDzblOptAuth(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      JSONObject jo = new JSONObject();
      String isOk = this.dzblTemplateService.selectParaValueByName(request);
      jo.put("isOk", isOk);
      YZUtility.RETURN_OBJ(jo, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
  }
  
  @RequestMapping({"/findDzbl.act"})
  public String findDzbl(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String searchValue = request.getParameter("searchValue");
    try {
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      Map<String, String> map = new HashMap<>();
      String visualstaff = request.getSession().getAttribute(SessionUtil.visualstaffYyrl).toString();
      map.put("querytype", visualstaff);
      if (starttime != null && !starttime.equals(""))
        map.put("starttime", starttime); 
      if (starttime != null && !starttime.equals(""))
        map.put("endtime", endtime); 
      if (starttime != null && !starttime.equals(""))
        map.put("searchValue", searchValue); 
      JSONObject findDzbl = this.dzblTemplateService.findDzbl(map, bp);
      YZUtility.DEAL_SUCCESS(findDzbl, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
}
