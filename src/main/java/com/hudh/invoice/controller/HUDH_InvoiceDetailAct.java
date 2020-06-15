package com.hudh.invoice.controller;

import com.hudh.invoice.entity.InvoiceDetail;
import com.hudh.invoice.service.InvoiceDetailService;
import com.hudh.util.HUDHUtil;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.imports.ExcelTool;
import com.kqds.util.sys.log.BcjlUtil;
import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/HUDH_InvoiceDetailAct"})
public class HUDH_InvoiceDetailAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_InvoiceDetailAct.class);
  
  @Autowired
  private InvoiceDetailService invoiceDetailService;
  
  @RequestMapping({"/insertInvoiceDetail.act"})
  public String insertInvoiceDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      YZPerson person = SessionUtil.getLoginPerson(request);
      String organization = ChainUtil.getCurrentOrganization(request);
      String invoiceList = request.getParameter("invoiceList");
      invoiceList = URLDecoder.decode(invoiceList, "UTF-8");
      List<InvoiceDetail> list = HUDHUtil.parseJsonToObjectList(invoiceList, InvoiceDetail.class);
      int i = this.invoiceDetailService.batchSaveInvoiceDetail(list, usercode, person, organization);
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.HUDH_INVOICE_DETAIL, invoiceList, usercode, TableNameUtil.HUDH_INVOICE_DETAIL, request);
      if (i > 0)
        YZUtility.DEAL_SUCCESS(null, null, response, this.logger); 
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateInvoiceDetail.act"})
  public String updateInvoiceDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      YZPerson person = SessionUtil.getLoginPerson(request);
      String invoiceUpdateList = request.getParameter("invoiceUpdateList");
      invoiceUpdateList = URLDecoder.decode(invoiceUpdateList, "UTF-8");
      String organization = ChainUtil.getCurrentOrganization(request);
      List<InvoiceDetail> list = HUDHUtil.parseJsonToObjectList(invoiceUpdateList, InvoiceDetail.class);
      this.invoiceDetailService.batchupdateInvoiceDetail(list, usercode, person, organization);
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.HUDH_INVOICE_DETAIL, invoiceUpdateList, usercode, TableNameUtil.HUDH_INVOICE_DETAIL, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateDishonourInvoiceDetail.act"})
  public String updateDishonourInvoiceDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      YZPerson person = SessionUtil.getLoginPerson(request);
      String invoiceUpdateList = request.getParameter("dataRetreatObj");
      invoiceUpdateList = URLDecoder.decode(invoiceUpdateList, "UTF-8");
      InvoiceDetail list = (InvoiceDetail)HUDHUtil.parseJsonToObject(invoiceUpdateList, InvoiceDetail.class);
      this.invoiceDetailService.updateDishonourInvoiceDetail(list, usercode, person);
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.HUDH_INVOICE_DETAIL, invoiceUpdateList, usercode, TableNameUtil.HUDH_INVOICE_DETAIL, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateDishonourInvoiceDetailAll.act"})
  public String updateDishonourInvoiceDetailAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      YZPerson person = SessionUtil.getLoginPerson(request);
      this.invoiceDetailService.updateDishonourInvoiceDetailAll(usercode, person);
      BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.HUDH_INVOICE_DETAIL, "", usercode, TableNameUtil.HUDH_INVOICE_DETAIL, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectInvoiceDetailByUsercode.act"})
  public String selectInvoiceDetailByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      List<JSONObject> list = this.invoiceDetailService.selectInvoiceDetailByUsercode(usercode);
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectInvoiceDetailValueByUsercode.act"})
  public String selectInvoiceDetailValueByUsercode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      JSONObject json1 = this.invoiceDetailService.selectInvoiceDetailValueByUsercode(usercode);
      JSONObject json2 = this.invoiceDetailService.selectInvoiceDetailValueByUsercodeAndDishonour(usercode);
      JSONObject json = new JSONObject();
      json.put("invoiceValue", json1.get("invoicevalue"));
      json.put("dishonourInvoiceValue", json2.get("dishonourinvoicevalue"));
      YZUtility.RETURN_OBJ(json, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/toInvoiceLeading.act"})
  public ModelAndView toInvoiceLeading(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/jdzx/invoice_leading.jsp");
    return mv;
  }
  
  @RequestMapping(value = {"/FileUploadAct"}, method = {RequestMethod.POST}, produces = {"application/json; charset=UTF-8"})
  public ModelAndView FileUploadAct(HttpServletRequest request, HttpServletResponse response, @RequestParam("files") MultipartFile[] files) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/kqdsFront/index/jdzx/invoice_leading.jsp");
    List<List<String>> list = null;
    try {
//      if (files != null)
//        files.length;
      String fileName = "";
      byte b;
      int i;
      MultipartFile[] arrayOfMultipartFile;
      for (i = (arrayOfMultipartFile = files).length, b = 0; b < i; ) {
        MultipartFile file = arrayOfMultipartFile[b];
        File dir = null;
        String docsPath = request.getSession().getServletContext().getRealPath("WEB-INF/lsfile");
        dir = new File(docsPath);
        if (!dir.exists())
          dir.mkdirs(); 
        fileName = file.getOriginalFilename();
        b++;
      } 
      list = ExcelTool.read(files[0].getInputStream(), ExcelTool.isExcel2003(fileName));
      this.invoiceDetailService.saveBatchInsert(list, request);
      String Msg = "批量导入EXCEL成功！";
      request.getSession().setAttribute("msg", Msg);
    } catch (Exception ex) {
      request.getSession().setAttribute("msg", ex.getMessage());
    } 
    return mv;
  }
  
  @RequestMapping({"/selectInvoiceDetailByTime.act"})
  public String selectInvoiceDetailByTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      String organization = request.getParameter("organization");
      String searchValue = request.getParameter("searchValue");
      String invoicestarttime = request.getParameter("invoicestarttime");
      String invoiceendtime = request.getParameter("invoiceendtime");
      String invoicestartvalue = request.getParameter("invoicestartvalue");
      String invoiceendvalue = request.getParameter("invoiceendvalue");
      String sortName = request.getParameter("sortName");
      String sortOrder = request.getParameter("sortOrder");
      Map<String, String> map = new HashMap<>();
      if (!YZUtility.isNullorEmpty(organization))
        map.put("organization", organization); 
      if (!YZUtility.isNullorEmpty(searchValue))
        map.put("searchValue", searchValue); 
      if (!YZUtility.isNullorEmpty(invoicestarttime))
        map.put("invoicestarttime", invoicestarttime); 
      if (!YZUtility.isNullorEmpty(invoiceendtime))
        map.put("invoiceendtime", String.valueOf(invoiceendtime) + ConstUtil.TIME_END); 
      if (!YZUtility.isNullorEmpty(invoicestartvalue))
        map.put("invoicestartvalue", invoicestartvalue); 
      if (!YZUtility.isNullorEmpty(invoiceendvalue))
        map.put("invoiceendvalue", invoiceendvalue); 
      if (!YZUtility.isNullorEmpty(sortName))
        map.put("sortName", sortName); 
      if (!YZUtility.isNullorEmpty(sortOrder))
        map.put("sortOrder", sortOrder); 
      String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
      String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
      String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
      JSONObject data = this.invoiceDetailService.selectInvoiceDetailByTime(bp, map);
      if (flag != null && flag.equals("exportTable")) {
        JSONArray<JSONObject> jSONArray = data.getJSONArray("rows");
        if (jSONArray.size() > 0)
          for (int i = 0; i < jSONArray.size(); i++) {
            ((JSONObject)jSONArray.get(i)).put("number", (new StringBuilder(String.valueOf(i + 1))).toString());
            if (((JSONObject)jSONArray.get(i)).get("dishonour").equals("1")) {
              ((JSONObject)jSONArray.get(i)).put("cancellation", "是");
              ((JSONObject)jSONArray.get(i)).put("invalid_person", ((JSONObject)jSONArray.get(i)).getString("updateuser"));
              ((JSONObject)jSONArray.get(i)).put("invalid_time", ((JSONObject)jSONArray.get(i)).getString("updatetime"));
            } else {
              ((JSONObject)jSONArray.get(i)).put("cancellation", "否");
              ((JSONObject)jSONArray.get(i)).put("modifier", ((JSONObject)jSONArray.get(i)).getString("updateuser"));
              ((JSONObject)jSONArray.get(i)).put("filemtime", ((JSONObject)jSONArray.get(i)).getString("updatetime"));
            } 
            ((JSONObject)jSONArray.get(i)).put("invoice_kind", "普通发票");
            ((JSONObject)jSONArray.get(i)).put("invoice_code", "11001800304");
            ((JSONObject)jSONArray.get(i)).put("invoice_month", ((JSONObject)jSONArray.get(i)).getString("invoice_time").substring(0, 7));
          }  
        ExportTable.exportBootStrapTable2Excel("发票信息查询", fieldArr, fieldnameArr, (List)jSONArray, response, request);
        return null;
      } 
      YZUtility.DEAL_SUCCESS(data, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
}
