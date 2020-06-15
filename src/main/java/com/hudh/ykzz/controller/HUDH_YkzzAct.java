package com.hudh.ykzz.controller;

import com.hudh.ykzz.entity.YkzzDrugs;
import com.hudh.ykzz.service.IYkzzDrugsService;
import com.hudh.ykzz.util.CreateOrderNoUtil;
import com.kqds.core.global.YZSysProps;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.imports.ExcelTool;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileUploadBase;
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
@RequestMapping({"/HUDH_YkzzAct"})
public class HUDH_YkzzAct {
  private static Logger logger = LoggerFactory.getLogger(HUDH_YkzzAct.class);
  
  @Autowired
  private IYkzzDrugsService IYkzzDrugsService;
  
  @RequestMapping({"/insertDrugsInfor.act"})
  public String insertDrugsInfor(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String good_name = request.getParameter("good_name");
      String company = request.getParameter("company");
      String drug_retail_price = request.getParameter("drug_retail_price");
      String discount = request.getParameter("discount");
      String good_type = request.getParameter("good_type");
      String drugs_typeone = request.getParameter("drugs_typeone");
      String drugs_typetwo = request.getParameter("drugs_typetwo");
      String packing_num = request.getParameter("packing_num");
      String company_min = request.getParameter("company_min");
      String content_coe = request.getParameter("content_coe");
      String content_unit = request.getParameter("content_unit");
      String sta_item = request.getParameter("sta_item");
      String contry_code = request.getParameter("contry_code");
      String pharm_class = request.getParameter("pharm_class");
      String pharm_dos = request.getParameter("pharm_dos");
      String pharm_spec = request.getParameter("pharm_spec");
      String ant_gra = request.getParameter("ant_gra");
      String psy_drugs = request.getParameter("psy_drugs");
      String drug_identify = request.getParameter("drug_identify");
      String chemistry_name = request.getParameter("chemistry_name");
      String manu_id = request.getParameter("manu_id");
      String manufac_id = request.getParameter("manufac_id");
      String comments = request.getParameter("comments");
      String minnums = request.getParameter("minnums");
      String maxnums = request.getParameter("maxnums");
      String mingj = request.getParameter("mingj");
      String maxgj = request.getParameter("maxgj");
      String packing_unit = request.getParameter("packing_unit");
      String id = request.getParameter("id");
      String drugs_type = request.getParameter("drugs_type");
      String classify = request.getParameter("classify");
      String organization = ChainUtil.getCurrentOrganization(request);
      YkzzDrugs ykzzDrugs = new YkzzDrugs();
      BeanUtils.populate(ykzzDrugs, request.getParameterMap());
      if (!YZUtility.isNullorEmpty(id)) {
        this.IYkzzDrugsService.updateDrugsByPrimaryId(ykzzDrugs);
      } else {
        ykzzDrugs.setId(YZUtility.getUUID());
        ykzzDrugs.setComments(comments);
        ykzzDrugs.setPacking_unit(packing_unit);
        ykzzDrugs.setMaxgj(maxgj);
        ykzzDrugs.setMingj(mingj);
        ykzzDrugs.setMinnums(minnums);
        ykzzDrugs.setMaxnums(maxnums);
        ykzzDrugs.setGood_name(good_name);
        ykzzDrugs.setCompany(company);
        if (discount.equals("")) {
          ykzzDrugs.setDiscount("100.0");
        } else {
          ykzzDrugs.setDiscount(discount);
        } 
        ykzzDrugs.setGood_type(good_type);
        ykzzDrugs.setDrugs_typeone(drugs_typeone);
        ykzzDrugs.setDrugs_typetwo(drugs_typetwo);
        ykzzDrugs.setPacking_num(packing_num);
        ykzzDrugs.setCompany_min(company_min);
        ykzzDrugs.setContent_coe(content_coe);
        ykzzDrugs.setContent_unit(content_unit);
        ykzzDrugs.setSta_item(sta_item);
        ykzzDrugs.setClassify(classify);
        List<YkzzDrugs> list = this.IYkzzDrugsService.findDeugsByContryCode(contry_code, organization);
        for (int j = 0; j < list.size(); j++) {
          if (list != null)
            throw new Exception("药品国家编码已存在，不能重复添加！"); 
        } 
        ykzzDrugs.setContry_code(contry_code);
        ykzzDrugs.setPharm_class(pharm_class);
        ykzzDrugs.setPharm_dos(pharm_dos);
        ykzzDrugs.setPharm_spec(pharm_spec);
        ykzzDrugs.setAnt_gra(ant_gra);
        ykzzDrugs.setPsy_drugs(psy_drugs);
        ykzzDrugs.setDrugs_type(drugs_type);
        ykzzDrugs.setDrug_identify(drug_identify);
        ykzzDrugs.setChemistry_name(chemistry_name);
        ykzzDrugs.setManu_id(manu_id);
        ykzzDrugs.setManufac_id(manufac_id);
        ykzzDrugs.setDrug_total_num(Integer.valueOf(0));
        String str = "0.000";
        BigDecimal bd = new BigDecimal(str);
        bd = bd.setScale(3, 4);
        ykzzDrugs.setNuit_price(bd);
        ykzzDrugs.setNuit_price(bd);
        ykzzDrugs.setDrgs_total_money(bd);
        ykzzDrugs.setCreatetime(YZUtility.getCurDateTimeStr());
        YZPerson person = SessionUtil.getLoginPerson(request);
        ykzzDrugs.setCreator(person.getSeqId());
        ykzzDrugs.setOrganization(organization);
        CreateOrderNoUtil.getInstance();
        String orderNumber = CreateOrderNoUtil.getNextOrderNumber();
        ykzzDrugs.setOrder_no(orderNumber);
        ykzzDrugs.setUserflag("0");
        this.IYkzzDrugsService.insertDrugsinfor(ykzzDrugs, request);
      } 
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectDrugsByPrimaryId.act"})
  public String selectDrugsByPrimaryId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    try {
      JSONObject json = this.IYkzzDrugsService.selectDrugsByPrimaryId(id);
      YZUtility.DEAL_SUCCESS(json, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectAllDrugsInfor.act"})
  public String selectAllDrugsInfor(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String basetype = request.getParameter("basetype");
    String nexttype = request.getParameter("nexttype");
    String queryInput = request.getParameter("queryInput");
    String organization = ChainUtil.getCurrentOrganization(request);
    Map<String, String> map = new HashMap<>();
    if (!YZUtility.isNullorEmpty(basetype))
      map.put("basetype", basetype); 
    if (!YZUtility.isNullorEmpty(nexttype))
      map.put("nexttype", nexttype); 
    if (!YZUtility.isNullorEmpty(queryInput))
      map.put("queryInput", queryInput); 
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    try {
      List<JSONObject> list = this.IYkzzDrugsService.selectAllDrugsInfor(map);
      YZUtility.RETURN_LIST(list, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteDrugsByPrimaryId.act"})
  public String deleteDrugsByPrimaryId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    try {
      this.IYkzzDrugsService.deleteDrugsByPrimaryId(id);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateDrugsByPrimaryId.act"})
  public String updateDrugsByPrimaryId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/selectDrugsInforByConditionQuery.act"})
  public String selectDrugsInforByConditionQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
    String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
    String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
    String organization = ChainUtil.getCurrentOrganization(request);
    String basetype = request.getParameter("basetype");
    String nexttype = request.getParameter("nexttype");
    String queryInput = request.getParameter("queryInput");
    Map<String, String> map = new HashMap<>();
    if (!YZUtility.isNullorEmpty(basetype))
      map.put("basetype", basetype); 
    if (!YZUtility.isNullorEmpty(nexttype))
      map.put("nexttype", nexttype); 
    if (!YZUtility.isNullorEmpty(queryInput))
      map.put("queryInput", queryInput); 
    if (!YZUtility.isNullorEmpty(organization))
      map.put("organization", organization); 
    try {
      List<JSONObject> json = this.IYkzzDrugsService.selectDrugsInforByConditionQuery(map);
      if (flag != null && flag.equals("exportTable")) {
        ExportTable.exportBootStrapTable2Excel("药品信息", fieldArr, fieldnameArr, json, response, request);
        return null;
      } 
      YZUtility.RETURN_LIST(json, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"/excelDownloadStandardDrugsTemplateOut.act"})
  public void excelDownloadStandardDrugsTemplateOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
    File file = new File(String.valueOf(ConstUtil.ROOT_DIR) + "\\model\\药品导入模板.xls");
    response.reset();
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    try {
      response.setHeader("Content-Disposition", "attachment;filename=" + new String("药品导入模板.xls".getBytes(), "iso-8859-1"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } 
    ServletOutputStream out = response.getOutputStream();
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try {
      bis = new BufferedInputStream(new FileInputStream(file));
      bos = new BufferedOutputStream((OutputStream)out);
      byte[] buff = new byte[2048];
      int bytesRead;
      while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
        bos.write(buff, 0, bytesRead); 
    } catch (IOException e) {
      throw e;
    } finally {
      if (bis != null)
        bis.close(); 
      if (bos != null)
        bos.close(); 
    } 
  }
  
  @RequestMapping(value = {"/FileUploadAct"}, method = {RequestMethod.POST}, produces = {"application/json; charset=UTF-8"})
  public ModelAndView FileUploadAct(HttpServletRequest request, HttpServletResponse response, @RequestParam("files") MultipartFile[] files) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("/hudh/ykzz/upload/upload_excel.jsp");
    Map<String, String> map = new HashMap<>();
    JSONObject result = new JSONObject();
    List<List<String>> list = null;
    try {
//      if (files != null)
//        files.length;
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
        String fileName = file.getOriginalFilename();
        byte[] bytes = file.getBytes();
        File file1 = new File(String.valueOf(docsPath) + fileName);
        b++;
      } 
      list = ExcelTool.read(files[0].getInputStream(), true);
      this.IYkzzDrugsService.saveBatchInsert(list, request);
      String Msg = "批量导入EXCEL成功！";
      request.getSession().setAttribute("msg", Msg);
    } catch (FileUploadBase.SizeLimitExceededException ex) {
      request.getSession().setAttribute("msg", "文件上传失败：文件需要小于" + YZSysProps.getInt("maxUploadFileSize") + "M");
    } catch (Exception ex) {
      request.getSession().setAttribute("msg", ex.getMessage());
    } 
    return mv;
  }
  
  @RequestMapping({"/forbiddenDrugs.act"})
  public String forbiddenDrugs(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    try {
      this.IYkzzDrugsService.forbiddenDrugs(id);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
  
  @RequestMapping({"recoverDrugs.act"})
  public String recoverDrugs(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    try {
      this.IYkzzDrugsService.recoverDrugs(id);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
    } 
    return null;
  }
}
