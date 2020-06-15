package com.hudh.dzbl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hudh.dzbl.dao.DzblDetailDao;
import com.hudh.dzbl.dao.DzblTemplateDao;
import com.hudh.dzbl.entity.DzblDetail;
import com.hudh.dzbl.entity.DzblTemplate;
import com.hudh.dzbl.service.IDzblTemplateService;
import com.hudh.lclj.dao.SysParaDao;
import com.hudh.util.HUDHUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPara;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.print.PdfReportM1HeaderFooter;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DzblTemplateServiceImpl
  implements IDzblTemplateService
{
  @Autowired
  private DzblTemplateDao dzblTemplateDao;
  @Autowired
  private DaoSupport dao;
  @Autowired
  private SysParaDao sysParaDao;
  @Autowired
  private DzblDetailDao dzblDetailDao;
  private static final String DZBL_OPT_AUTH = "DZBL_OPT_AUTH";
  private static BaseFont bfChinese = null;
  private static Font FontChinese = null;
  
  public void insertDzblTemplate(DzblTemplate dzblTemplate)
    throws Exception
  {
    dzblTemplate.setId(YZUtility.getUUID());
    dzblTemplate.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    dzblTemplate.setModifytime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    

    String detail = dzblTemplate.getDetail();
    detail = detail.replace(" ", "&nbsp;");
    detail = detail.replace("{}", "<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>");
    dzblTemplate.setDetail(detail);
    

    JSONObject jo = findTemplateByBlflAndBc(dzblTemplate.getBlfl(), dzblTemplate.getBc(), dzblTemplate.getSstype(), dzblTemplate.getOrganization());
    if ((jo != null) && (YZUtility.isNotNullOrEmpty(jo.getString("id"))))
    {
      dzblTemplate.setId(jo.getString("id"));
      updateDzblTempleById(dzblTemplate);
    }
    else
    {
      this.dzblTemplateDao.insertDzblTemplate(dzblTemplate);
    }
  }
  
  public JSONObject findTemplateByBlflAndBc(String blfl, String bc, String sstype, String organization)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    dataMap.put("blfl", blfl);
    dataMap.put("bc", bc);
    dataMap.put("sstype", sstype);
    dataMap.put("organization", organization);
    List<JSONObject> list = this.dzblTemplateDao.findDzblTemplate(dataMap);
    if ((list != null) && (list.size() > 0)) {
      return (JSONObject)list.get(0);
    }
    return null;
  }
  
  public List<JSONObject> findBcByBlfl(String blfl, String sstype)
    throws Exception
  {
    Map<String, String> dataMap = new HashMap();
    dataMap.put("blfl", blfl);
    dataMap.put("sstype", sstype);
    List<JSONObject> list = this.dzblTemplateDao.findBcByBlfl(dataMap);
    if ((list != null) && (list.size() > 0)) {
      return list;
    }
    return null;
  }
  
  public void insertDzblDetail(DzblDetail dzblDetail, HttpServletRequest request)
    throws Exception
  {
    dzblDetail.setId(YZUtility.getUUID());
    dzblDetail.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    YZPerson person = SessionUtil.getLoginPerson(request);
    String currntUserId = SessionUtil.getLoginPerson(request).getSeqId();
    dzblDetail.setCreator(currntUserId);
    this.dzblDetailDao.insertDzblDetail(dzblDetail);
  }
  
  public List<JSONObject> findDzblByBlcode(String blCode)
    throws Exception
  {
    List<JSONObject> list = new ArrayList();
    list = this.dzblDetailDao.findDzblByBlcode(blCode);
    return list;
  }
  
  public void updateDzblById(DzblDetail dzblDetail, HttpServletRequest request)
    throws Exception
  {
    dzblDetail.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    YZPerson person = SessionUtil.getLoginPerson(request);
    String currntUserId = SessionUtil.getLoginPerson(request).getSeqId();
    dzblDetail.setCreator(currntUserId);
    this.dzblDetailDao.updateDzblById(dzblDetail);
  }
  
  public String dzblPrint(HttpServletRequest request, String blId, String blCode, String ssTime)
    throws Exception
  {
    bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
    FontChinese = new Font(bfChinese, 12.0F, 0);
    String path = request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator + "print";
    File pathObj = new File(path);
    if (!pathObj.exists()) {
      pathObj.mkdirs();
    }
    String filePath = path + File.separator + YZUtility.getUUID() + ".pdf";
    
    Document document = new Document(PageSize.A4);
    



    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
    

    PdfReportM1HeaderFooter headerFooter = new PdfReportM1HeaderFooter();
    writer.setPageEvent(headerFooter);
    

    document.open();
    
    PdfPTable table = null;
    

    JSONObject userDoc = findUserDocByBlCode(blCode);
    JSONObject dzbiDetail = findDzblById(blId);
    

    table = createUserInfoTable(userDoc, dzbiDetail, ssTime);
    
    document.add(table);
    


    table = new PdfPTable(1);
    table.setWidthPercentage(100.0F);
    table.setWidths(new int[] { 1 });
    PdfPCell cell = new PdfPCell(new Phrase(dzbiDetail.getString("title"), new Font(bfChinese, 15.0F, 1)));
    cell.setMinimumHeight(30.0F);
    cell.setBorder(0);
    cell.setVerticalAlignment(5);
    cell.setHorizontalAlignment(1);
    table.addCell(cell);
    document.add(table);
    

    table = new PdfPTable(1);
    table.setWidthPercentage(100.0F);
    table.setWidths(new int[] { 1 });
    cell = new PdfPCell(new Phrase(dzbiDetail.getString("detail")
      .replace("&nbsp;", "")
      .replace("<u>", "")
      .replace("</u>", "")
      .replace("<br>", "\n")
      .replace("<br/>", "\n")
      .replace("<div>", "\n")
      .replace("</div>", ""), 
      new Font(bfChinese, 12.0F, 0)));
    cell.setMinimumHeight(80.0F);
    cell.setBorder(0);
    cell.setVerticalAlignment(5);
    cell.setHorizontalAlignment(0);
    cell.setLeading(20.0F, 0.0F);
    table.addCell(cell);
    document.add(table);
    

    table = new PdfPTable(2);
    table.setWidthPercentage(100.0F);
    table.setWidths(new int[] { 50, 50 });
    PdfPCell cell2 = new PdfPCell(new Phrase("医生签名：", new Font(bfChinese, 12.0F, 0)));
    
    PdfPCell cell3 = new PdfPCell(new Phrase("日期：", new Font(bfChinese, 12.0F, 0)));
    
    cell2.setHorizontalAlignment(2);
    cell3.setHorizontalAlignment(1);
    
    cell2.setBorder(0);
    cell3.setBorder(0);
    
    cell2.setVerticalAlignment(5);
    cell3.setVerticalAlignment(5);
    
    cell2.setPaddingTop(20.0F);
    cell3.setPaddingTop(20.0F);
    
    table.addCell(cell2);
    table.addCell(cell3);
    
    document.add(table);
    
    document.close();
    return filePath;
  }
  
  private PdfPTable createUserInfoTable(JSONObject userDoc, JSONObject dzbiDetail, String ssTime)
    throws DocumentException
  {
    PdfPTable table = new PdfPTable(8);
    table.setWidthPercentage(100.0F);
    table.setWidths(new float[] { 0.65F, 1.2F, 0.65F, 1.2F, 0.65F, 0.7F, 1.0F, 2.0F });
    table.setSpacingBefore(5.0F);
    
    PdfPCell cell11 = new PdfPCell(new Phrase("姓名：", FontChinese));
    PdfPCell cell12 = new PdfPCell(new Phrase(userDoc.getString("username"), FontChinese));
    PdfPCell cell13 = new PdfPCell(new Phrase("性别：", FontChinese));
    PdfPCell cell14 = new PdfPCell(new Phrase(userDoc.getString("sex"), FontChinese));
    PdfPCell cell15 = new PdfPCell(new Phrase("年龄：", FontChinese));
    PdfPCell cell16 = new PdfPCell(new Phrase(userDoc.getString("age"), FontChinese));
    PdfPCell cell17 = new PdfPCell(new Phrase("日期：", FontChinese));
    
    ssTime = YZUtility.isNotNullOrEmpty(dzbiDetail.getString("createtime")) ? 
      dzbiDetail.getString("createtime").substring(0, 10) : YZUtility.isNotNullOrEmpty(ssTime) ? ssTime : "";
    PdfPCell cell18 = new PdfPCell(new Phrase(ssTime, FontChinese));
    

    cell12.setHorizontalAlignment(0);
    cell14.setHorizontalAlignment(0);
    cell16.setHorizontalAlignment(0);
    cell18.setHorizontalAlignment(0);
    
    cell11.setBorder(0);
    cell12.setBorder(0);
    cell13.setBorder(0);
    cell14.setBorder(0);
    cell15.setBorder(0);
    cell16.setBorder(0);
    cell17.setBorder(0);
    cell18.setBorder(0);
    
    table.addCell(cell11);
    table.addCell(cell12);
    table.addCell(cell13);
    table.addCell(cell14);
    table.addCell(cell15);
    table.addCell(cell16);
    table.addCell(cell17);
    table.addCell(cell18);
    
    return table;
  }
  
  public JSONObject findUserDocByBlCode(String blCode)
    throws Exception
  {
    JSONObject jo = this.dzblDetailDao.getBaseUserInfoByUsercode(blCode);
    if (jo != null) {
      return jo;
    }
    return null;
  }
  
  public JSONObject findDzblById(String id)
    throws Exception
  {
    JSONObject jo = this.dzblDetailDao.findDzblById(id);
    if (jo != null)
    {
      jo.put("detail", jo.getString("detail").replace("&nbsp;", " "));
      return jo;
    }
    return null;
  }
  
  public JSONObject findDzblTemplateById(String id)
    throws Exception
  {
    JSONObject jo = this.dzblTemplateDao.findDzblTemplateById(id);
    if (jo != null) {
      return jo;
    }
    return null;
  }
  
  public void updateDzblTempleById(DzblTemplate dzblTemplate)
    throws Exception
  {
    this.dzblTemplateDao.updateDzblTempleById(dzblTemplate);
  }
  
  public String selectParaValueByName(HttpServletRequest request)
    throws Exception
  {
    String organization = ChainUtil.getCurrentOrganization(request);
    Map<String, Map<String, String>> dataMap = new HashMap();
    Map<String, String> tempMap = new HashMap();
    tempMap.put("para_name", "DZBL_OPT_AUTH");
    tempMap.put("organization", organization);
    dataMap.put("params", tempMap);
    List<YZPara> paraList = this.sysParaDao.selectParaValueByName(dataMap);
    

    YZPerson person = SessionUtil.getLoginPerson(request);
    String perPriv = person.getUserPriv();
    int length;
    int i;
    for (Iterator localIterator = paraList.iterator(); localIterator.hasNext(); i < length)
    {
      YZPara yzPara = (YZPara)localIterator.next();
      String[] yzParaArray = yzPara.getParaValue().split(",");
      length = yzParaArray.length;
      i = 0; continue;
      if (yzParaArray[i].equals(perPriv)) {
        return "true";
      }
      i++;
    }
    return "false";
  }
  
  public JSONObject findDzbl(Map<String, String> map, BootStrapPage bp)
    throws Exception
  {
    PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
    List<JSONObject> list = (List)this.dao.findForList(TableNameUtil.HUDH_DZBL_DETAIL + ".findDzbl", map);
    PageInfo<JSONObject> pageInfo = new PageInfo(list);
    JSONObject jobj = new JSONObject();
    jobj.put("total", Long.valueOf(pageInfo.getTotal()));
    jobj.put("rows", list);
    return jobj;
  }
}
