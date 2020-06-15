package com.kqds.service.base.print;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsMedicalrecord;
import com.kqds.entity.base.KqdsMedicalrecordCz;
import com.kqds.entity.base.KqdsMedicalrecordFz;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.YZDept;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.medicalRecord.KQDS_MedicalRecordLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  private static BaseFont bfChinese = null;
  
  private static Font FontChinese = null;
  
  @Autowired
  private YZPersonLogic personLogic;
  
  @Autowired
  private KQDS_UserDocumentLogic userLogic;
  
  @Autowired
  private KQDS_MedicalRecordLogic medicalLogic;
  
  private void setCellBorder(List<PdfPCell> list, int columnNum) {}
  
  private void addCell2Table(PdfPTable table, List<PdfPCell> list) {
    for (PdfPCell pdfPCell : list)
      table.addCell(pdfPCell); 
  }
  
  public String bingLiPdf4Print(String seqId, HttpServletRequest request) throws Exception {
    bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
    FontChinese = new Font(bfChinese, 12.0F, 0);
    String path = String.valueOf(request.getSession().getServletContext().getRealPath("/")) + "upload" + File.separator + "print";
    File pathObj = new File(path);
    if (!pathObj.exists())
      pathObj.mkdirs(); 
    String filePath = String.valueOf(path) + File.separator + YZUtility.getUUID() + ".pdf";
    Document document = new Document(PageSize.A4);
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
    PdfReportM1HeaderFooter headerFooter = new PdfReportM1HeaderFooter();
    writer.setPageEvent((PdfPageEvent)headerFooter);
    document.open();
    PdfPTable table = null;
    KqdsMedicalrecord medical = (KqdsMedicalrecord)this.dao.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, seqId);
    if (medical == null)
      throw new Exception("病历不存在，seqId：" + seqId); 
    String usercode = medical.getUsercode();
    KqdsUserdocument userinfo = this.userLogic.getSingleUserByUsercode(usercode);
    if (userinfo == null)
      throw new Exception("患者不存在，usercode：" + usercode); 
    YZDept org = ChainUtil.getOrganizationByOrgCode(medical.getOrganization(), request);
    String headerName = org.getPrintName();
    String meid = medical.getSeqId();
    int mtype = medical.getMtype().intValue();
    if (mtype == 0) {
      headerName = String.valueOf(headerName) + "新诊病历";
      KqdsMedicalrecordCz czInfo = this.medicalLogic.getCZBingLiByMeid(meid);
      if (czInfo == null)
        throw new Exception("初诊病历不存在，病历号：" + meid); 
      headerFooter.setHeader(headerName);
      table = createUserInfoTable(userinfo, medical);
      document.add((Element)table);
      table = getCzContent(czInfo, medical.getUsertype().intValue());
      document.add((Element)table);
    } else {
      headerName = String.valueOf(headerName) + "复诊病历";
      KqdsMedicalrecordFz fzInfo = this.medicalLogic.getFZBingLiByMeid(meid);
      if (fzInfo == null)
        throw new Exception("复诊病历不存在，病历号：" + meid); 
      headerFooter.setHeader(headerName);
      table = createUserInfoTable(userinfo, medical);
      document.add((Element)table);
      table = getFzContent(fzInfo, medical.getUsertype().intValue());
      document.add((Element)table);
    } 
    table = new PdfPTable(1);
    table.setWidthPercentage(100.0F);
    table.setWidths(new int[] { 1 });
    PdfPCell cell = new PdfPCell(new Phrase("医生：", new Font(bfChinese, 12.0F, 0)));
    cell.setMinimumHeight(30.0F);
    cell.setBorder(0);
    cell.setVerticalAlignment(5);
    cell.setHorizontalAlignment(2);
    cell.setPaddingRight(100.0F);
    table.addCell(cell);
    document.add((Element)table);
    document.close();
    return filePath;
  }
  
  private PdfPTable createUserInfoTable(KqdsUserdocument userinfo, KqdsMedicalrecord medical) throws Exception {
    PdfPTable table = new PdfPTable(8);
    table.setWidthPercentage(100.0F);
    table.setWidths(new float[] { 0.65F, 1.2F, 0.65F, 1.2F, 0.65F, 0.7F, 1.0F, 2.0F });
    table.setSpacingBefore(5.0F);
    PdfPCell cell11 = new PdfPCell(new Phrase("姓名：", FontChinese));
    PdfPCell cell12 = new PdfPCell(new Phrase(userinfo.getUsername(), FontChinese));
    PdfPCell cell13 = new PdfPCell(new Phrase("性别：", FontChinese));
    PdfPCell cell14 = new PdfPCell(new Phrase(userinfo.getSex(), FontChinese));
    PdfPCell cell15 = new PdfPCell(new Phrase("年龄：", FontChinese));
    PdfPCell cell16 = new PdfPCell(new Phrase((String)userinfo.getAge(), FontChinese));
    PdfPCell cell17 = new PdfPCell(new Phrase("患者编号：", FontChinese));
    PdfPCell cell18 = new PdfPCell(new Phrase(userinfo.getUsercode(), FontChinese));
    String doctor = medical.getDoctor();
    String doctorName = "";
    if (!YZUtility.isNullorEmpty(doctor))
      doctorName = this.personLogic.getNameStrBySeqIds(doctor); 
    String nurse = medical.getNurse();
    String nurseName = "";
    if (!YZUtility.isNullorEmpty(nurse))
      nurseName = this.personLogic.getNameStrBySeqIds(nurse); 
    PdfPCell cell21 = new PdfPCell(new Phrase("医生：", FontChinese));
    PdfPCell cell22 = new PdfPCell(new Phrase(doctorName, FontChinese));
    PdfPCell cell23 = new PdfPCell(new Phrase("护士：", FontChinese));
    PdfPCell cell24 = new PdfPCell(new Phrase(nurseName, FontChinese));
    PdfPCell cell25 = new PdfPCell(new Phrase("", FontChinese));
    PdfPCell cell26 = new PdfPCell(new Phrase("", FontChinese));
    PdfPCell cell27 = new PdfPCell(new Phrase("填写时间：", FontChinese));
    PdfPCell cell28 = new PdfPCell(new Phrase(medical.getCreatetime(), FontChinese));
    cell12.setHorizontalAlignment(0);
    cell14.setHorizontalAlignment(0);
    cell16.setHorizontalAlignment(0);
    cell18.setHorizontalAlignment(0);
    cell22.setHorizontalAlignment(0);
    cell24.setHorizontalAlignment(0);
    cell26.setHorizontalAlignment(0);
    cell28.setHorizontalAlignment(0);
    cell11.setBorder(0);
    cell12.setBorder(0);
    cell13.setBorder(0);
    cell14.setBorder(0);
    cell15.setBorder(0);
    cell16.setBorder(0);
    cell17.setBorder(0);
    cell18.setBorder(0);
    cell21.setBorder(0);
    cell22.setBorder(0);
    cell23.setBorder(0);
    cell24.setBorder(0);
    cell25.setBorder(0);
    cell26.setBorder(0);
    cell27.setBorder(0);
    cell28.setBorder(0);
    table.addCell(cell11);
    table.addCell(cell12);
    table.addCell(cell13);
    table.addCell(cell14);
    table.addCell(cell15);
    table.addCell(cell16);
    table.addCell(cell17);
    table.addCell(cell18);
    table.addCell(cell21);
    table.addCell(cell22);
    table.addCell(cell23);
    table.addCell(cell24);
    table.addCell(cell25);
    table.addCell(cell26);
    table.addCell(cell27);
    table.addCell(cell28);
    return table;
  }
  
  private PdfPTable getCzContent(KqdsMedicalrecordCz czInfo, int usertype) throws DocumentException {
    PdfPTable table = new PdfPTable(5);
    table.setWidthPercentage(100.0F);
    table.setWidths(new float[] { 1.2F, 0.5F, 2.0F, 2.0F, 5.0F });
    table.setSpacingBefore(10.0F);
    List<PdfPCell> cellList = new ArrayList<>();
    List<PdfPCell> listt = getCzTop(czInfo);
    List<PdfPCell> listm = getCzMid(czInfo, usertype);
    List<PdfPCell> listb = getCzBottom(czInfo);
    cellList.addAll(listt);
    cellList.addAll(listm);
    cellList.addAll(listb);
    addCell2Table(table, cellList);
    return table;
  }
  
  private List<PdfPCell> getCzTop(KqdsMedicalrecordCz czInfo) throws DocumentException {
    List<PdfPCell> cellList = new ArrayList<>();
    PdfPCell cell11 = new PdfPCell(new Phrase("主诉", FontChinese));
    PdfPCell cell12 = new PdfPCell(new Phrase(czInfo.getCzzs(), FontChinese));
    PdfPCell cell21 = new PdfPCell(new Phrase("现病史", FontChinese));
    PdfPCell cell22 = new PdfPCell(new Phrase(czInfo.getCzxbs(), FontChinese));
    PdfPCell cell31 = new PdfPCell(new Phrase("既往史", FontChinese));
    PdfPCell cell32 = new PdfPCell(new Phrase(czInfo.getCzjws(), FontChinese));
    PdfPCell cell41 = new PdfPCell(new Phrase("过敏史", FontChinese));
    PdfPCell cell42 = new PdfPCell(new Phrase(czInfo.getCzgms(), FontChinese));
    PdfPCell cell51 = new PdfPCell(new Phrase("家族史", FontChinese));
    PdfPCell cell52 = new PdfPCell(new Phrase(czInfo.getCzjzs(), FontChinese));
    PdfPCell cell61 = new PdfPCell(new Phrase("检验结果", FontChinese));
    PdfPCell cell62 = new PdfPCell(new Phrase(czInfo.getCzjyjg(), FontChinese));
    cell12.setColspan(4);
    cell22.setColspan(4);
    cell32.setColspan(4);
    cell42.setColspan(4);
    cell52.setColspan(4);
    cell62.setColspan(4);
    cellList.add(cell11);
    cellList.add(cell12);
    cellList.add(cell21);
    cellList.add(cell22);
    cellList.add(cell31);
    cellList.add(cell32);
    cellList.add(cell41);
    cellList.add(cell42);
    cellList.add(cell51);
    cellList.add(cell52);
    cellList.add(cell61);
    cellList.add(cell62);
    setCellBorder(cellList, 2);
    return cellList;
  }
  
  private List<PdfPCell> getCzMid(KqdsMedicalrecordCz czInfo, int usertype) throws DocumentException {
    JSONObject json2 = new JSONObject();
    json2.put("name", "检查");
    json2.put("value", czInfo.getCztgjc());
    JSONObject json1 = new JSONObject();
    json1.put("name", "辅助检查");
    json1.put("value", czInfo.getCzfzjc());
    JSONObject json3 = new JSONObject();
    json3.put("name", "诊断");
    json3.put("value", czInfo.getCzzd());
    JSONObject json4 = new JSONObject();
    json4.put("name", "治疗方案");
    json4.put("value", czInfo.getCzzlfa());
    JSONObject json5 = new JSONObject();
    json5.put("name", "处理");
    json5.put("value", czInfo.getCzcl());
    List<JSONObject> list = new ArrayList<>();
    list.add(json2);
    list.add(json1);
    list.add(json3);
    list.add(json4);
    list.add(json5);
    return getContent(list, usertype);
  }
  
  public String getYaWei(String ywarr, int usertype) {
    String crup1 = "";
    String[] crup1Arr = ywarr.split(",");
    for (int k = 0; k < crup1Arr.length; k++) {
      if (usertype == 0) {
        crup1 = String.valueOf(crup1) + crup1Arr[k] + ",";
      } else {
        crup1 = String.valueOf(crup1) + '\004' + crup1Arr[k] + ",";
      } 
    } 
    if (crup1.indexOf(",") > 0)
      crup1 = crup1.substring(0, crup1.length() - 1); 
    return crup1;
  }
  
  private List<PdfPCell> getCzBottom(KqdsMedicalrecordCz czInfo) throws DocumentException {
    List<PdfPCell> cellList = new ArrayList<>();
    PdfPCell cell11 = new PdfPCell(new Phrase("医嘱", FontChinese));
    PdfPCell cell12 = new PdfPCell(new Phrase(czInfo.getCzyz(), FontChinese));
    PdfPCell cell21 = new PdfPCell(new Phrase("备注", FontChinese));
    PdfPCell cell22 = new PdfPCell(new Phrase(czInfo.getCzremark(), FontChinese));
    cellList.add(cell11);
    cellList.add(cell12);
    cellList.add(cell21);
    cellList.add(cell22);
    cell12.setColspan(4);
    cell22.setColspan(4);
    setCellBorder(cellList, 2);
    return cellList;
  }
  
  private PdfPTable getFzContent(KqdsMedicalrecordFz fzInfo, int usertype) throws DocumentException {
    PdfPTable table = new PdfPTable(5);
    table.setWidthPercentage(100.0F);
    table.setWidths(new float[] { 1.2F, 0.5F, 2.0F, 2.0F, 5.0F });
    table.setSpacingBefore(10.0F);
    List<PdfPCell> cellList = new ArrayList<>();
    List<PdfPCell> listt = getFzTop(fzInfo);
    List<PdfPCell> listm = getFzMid(fzInfo, usertype);
    List<PdfPCell> listb = getFzBottom(fzInfo);
    cellList.addAll(listt);
    cellList.addAll(listm);
    cellList.addAll(listb);
    addCell2Table(table, cellList);
    return table;
  }
  
  private List<PdfPCell> getFzTop(KqdsMedicalrecordFz fzInfo) throws DocumentException {
    List<PdfPCell> cellList = new ArrayList<>();
    PdfPCell cell11 = new PdfPCell(new Phrase("主诉", FontChinese));
    PdfPCell cell12 = new PdfPCell(new Phrase(fzInfo.getFzzs(), FontChinese));
    cell12.setColspan(4);
    cellList.add(cell11);
    cellList.add(cell12);
    setCellBorder(cellList, 2);
    return cellList;
  }
  
  private List<PdfPCell> getFzMid(KqdsMedicalrecordFz fzInfo, int usertype) throws DocumentException {
    JSONObject json1 = new JSONObject();
    json1.put("name", "检查");
    json1.put("value", fzInfo.getFzjc());
    JSONObject json2 = new JSONObject();
    json2.put("name", "诊断");
    json2.put("value", fzInfo.getFzzd());
    JSONObject json3 = new JSONObject();
    json3.put("name", "处理");
    json3.put("value", fzInfo.getFzcl());
    JSONObject json4 = new JSONObject();
    json4.put("name", "治疗方案");
    json4.put("value", fzInfo.getFzzlfa());
    List<JSONObject> list = new ArrayList<>();
    list.add(json1);
    list.add(json2);
    list.add(json4);
    list.add(json3);
    return getContent(list, usertype);
  }
  
  private List<PdfPCell> getFzBottom(KqdsMedicalrecordFz fzInfo) throws DocumentException {
    List<PdfPCell> cellList = new ArrayList<>();
    PdfPCell cell11 = new PdfPCell(new Phrase("医嘱", FontChinese));
    PdfPCell cell12 = new PdfPCell(new Phrase(fzInfo.getFzyz(), FontChinese));
    PdfPCell cell21 = new PdfPCell(new Phrase("备注", FontChinese));
    PdfPCell cell22 = new PdfPCell(new Phrase(fzInfo.getFzremark(), FontChinese));
    cellList.add(cell11);
    cellList.add(cell12);
    cellList.add(cell21);
    cellList.add(cell22);
    cell12.setColspan(4);
    cell22.setColspan(4);
    setCellBorder(cellList, 2);
    return cellList;
  }
  
  public List<PdfPCell> getContent(List<JSONObject> list, int usertype) {
    List<PdfPCell> cellList = new ArrayList<>();
    for (int j = 0; j < list.size(); j++) {
      JSONObject job = list.get(j);
      String name = job.getString("name");
      String value = job.getString("value");
      String[] arr = value.split("\\|\\|\\|");
      int cell1Num = arr.length;
      for (int k = 0; k < arr.length; k++) {
        if (arr[k].length() > 0) {
          String[] arrone = arr[k].split("\\|\\|");
          String[] ywarr = new String[4];
          String[] ywarrTmp = arrone[0].split(";");
          if (ywarrTmp.length < 1) {
            ywarr[0] = "";
          } else {
            ywarr[0] = ywarrTmp[0];
          } 
          if (ywarrTmp.length < 2) {
            ywarr[1] = "";
          } else {
            ywarr[1] = ywarrTmp[1];
          } 
          if (ywarrTmp.length < 3) {
            ywarr[2] = "";
          } else {
            ywarr[2] = ywarrTmp[2];
          } 
          if (ywarrTmp.length < 4) {
            ywarr[3] = "";
          } else {
            ywarr[3] = ywarrTmp[3];
          } 
          String content = arrone[1];
          if (content == null)
            content = ""; 
          String crup1 = "", crup2 = "", crdown1 = "", crdown2 = "";
          if (!"".equals(ywarr[0]))
            crup1 = getYaWei(ywarr[0], usertype); 
          if (!"".equals(ywarr[1]))
            crup2 = getYaWei(ywarr[1], usertype); 
          if (!"".equals(ywarr[2]))
            crdown1 = getYaWei(ywarr[2], usertype); 
          if (!"".equals(ywarr[3]))
            crdown2 = getYaWei(ywarr[3], usertype); 
          if (k == 0) {
            PdfPCell cell11 = new PdfPCell(new Phrase(name, FontChinese));
            cell11.setRowspan(cell1Num * 2);
            cellList.add(cell11);
          } 
          PdfPCell cell12 = new PdfPCell(new Phrase(String.valueOf(k + 1) + "、", FontChinese));
          cell12.setRowspan(2);
          PdfPCell cell13 = new PdfPCell(new Phrase(crup1, FontChinese));
          PdfPCell cell14 = new PdfPCell(new Phrase(crup2, FontChinese));
          PdfPCell cell15 = new PdfPCell(new Phrase(content, FontChinese));
          cell15.setRowspan(2);
          PdfPCell cell23 = new PdfPCell(new Phrase(crdown1, FontChinese));
          PdfPCell cell24 = new PdfPCell(new Phrase(crdown2, FontChinese));
          cell13.setMinimumHeight(20.0F);
          cell14.setMinimumHeight(20.0F);
          cell23.setMinimumHeight(20.0F);
          cell24.setMinimumHeight(20.0F);
          cellList.add(cell12);
          cellList.add(cell13);
          cellList.add(cell14);
          cellList.add(cell15);
          cellList.add(cell23);
          cellList.add(cell24);
        } 
      } 
    } 
    return cellList;
  }
}
