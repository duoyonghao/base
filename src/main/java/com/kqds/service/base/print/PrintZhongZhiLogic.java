package com.kqds.service.base.print;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsMedicalrecord;
import com.kqds.entity.base.KqdsMedicalrecordRestoration;
import com.kqds.entity.base.KqdsMedicalrecordReview;
import com.kqds.entity.base.KqdsMedicalrecordZhongzhi;
import com.kqds.entity.base.KqdsMedicalrecordZhongzhi2;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintZhongZhiLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  private static BaseFont bfChinese = null;
  
  private static Font FontChinese = null;
  
  @Autowired
  private YZPersonLogic personLogic;
  
  @Autowired
  private KQDS_UserDocumentLogic userLogic;
  
  @Autowired
  private PrintZhongZhi1Logic zhongzhi1Logic;
  
  @Autowired
  private PrintZhongZhi2Logic zhongzhi2Logic;
  
  @Autowired
  private PrintReviewLogic reviewLogic;
  
  @Autowired
  private PrintRestLogic restLogic;
  
  public PrintZhongZhiLogic() {
    try {
      bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
      FontChinese = new Font(bfChinese, 12.0F, 0);
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  private KqdsMedicalrecordZhongzhi getZhongzhi1Obj(String meid) throws Exception {
    Map<Object, Object> map = new HashMap<>();
    map.put("meid", meid);
    List<KqdsMedicalrecordZhongzhi> list = (List<KqdsMedicalrecordZhongzhi>)this.dao.loadList(TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI, map);
    if (list != null && list.size() > 1)
      throw new Exception("数据异常，一个编号对应多个初诊记录"); 
    return list.get(0);
  }
  
  private KqdsMedicalrecordReview getReviewObj(String meid) throws Exception {
    Map<Object, Object> map = new HashMap<>();
    map.put("meid", meid);
    List<KqdsMedicalrecordReview> list = (List<KqdsMedicalrecordReview>)this.dao.loadList(TableNameUtil.KQDS_MEDICALRECORD_REVIEW, map);
    if (list != null && list.size() > 1)
      throw new Exception("数据异常，一个编号对应多个初诊记录"); 
    return list.get(0);
  }
  
  private KqdsMedicalrecordZhongzhi2 getZhongzhi2Obj(String meid) throws Exception {
    Map<Object, Object> map = new HashMap<>();
    map.put("meid", meid);
    List<KqdsMedicalrecordZhongzhi2> list = (List<KqdsMedicalrecordZhongzhi2>)this.dao.loadList(TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI2, map);
    if (list != null && list.size() > 1)
      throw new Exception("数据异常，一个编号对应多个初诊记录"); 
    return list.get(0);
  }
  
  private KqdsMedicalrecordRestoration getRestObj(String meid) throws Exception {
    Map<Object, Object> map = new HashMap<>();
    map.put("meid", meid);
    List<KqdsMedicalrecordRestoration> list = (List<KqdsMedicalrecordRestoration>)this.dao.loadList(TableNameUtil.KQDS_MEDICALRECORD_RESTORATION, map);
    if (list != null && list.size() > 1)
      throw new Exception("数据异常，一个编号对应多个初诊记录"); 
    return list.get(0);
  }
  
  public String print(String seqId, HttpServletRequest request) throws Exception {
    bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
    FontChinese = new Font(bfChinese, 12.0F, 0);
    String headerName = ChainUtil.getOrgName(request);
    String path = String.valueOf(request.getSession().getServletContext().getRealPath("/")) + "upload" + File.separator + "print";
    File pathObj = new File(path);
    if (!pathObj.exists())
      pathObj.mkdirs(); 
    String filePath = String.valueOf(path) + File.separator + YZUtility.getUUID() + ".pdf";
    Document document = new Document();
    document.setPageSize((Rectangle)new RectangleReadOnly(595.0F, 421.0F));
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
    PdfReportM1HeaderFooter headerFooter = new PdfReportM1HeaderFooter();
    headerFooter.setHeader(headerName);
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
    String meid = medical.getSeqId();
    int mtype = medical.getMtype().intValue();
    if (2 == mtype) {
      headerName = String.valueOf(headerName) + "种植一期病历";
      KqdsMedicalrecordZhongzhi zhongzhi1 = getZhongzhi1Obj(meid);
      if (zhongzhi1 == null)
        throw new Exception("种植一期病历不存在，病历号：" + meid); 
      headerFooter.setHeader(headerName);
      table = this.zhongzhi1Logic.createUserInfoTable(userinfo, zhongzhi1);
      document.add((Element)table);
      table = this.zhongzhi1Logic.createTable1Info(userinfo, zhongzhi1);
      document.add((Element)table);
      table = this.zhongzhi1Logic.createTable2Info(userinfo, zhongzhi1);
      document.add((Element)table);
      table = this.zhongzhi1Logic.createTable3Info(userinfo, zhongzhi1);
      document.add((Element)table);
    } 
    if (3 == mtype) {
      headerName = String.valueOf(headerName) + "种植复查病历";
      KqdsMedicalrecordReview review = getReviewObj(meid);
      if (review == null)
        throw new Exception("种植复查病历不存在，病历号：" + meid); 
      headerFooter.setHeader(headerName);
      table = this.reviewLogic.createTable1Info(userinfo, review);
      document.add((Element)table);
    } 
    if (4 == mtype) {
      headerName = String.valueOf(headerName) + "种植二期病历";
      KqdsMedicalrecordZhongzhi2 zhongzhi2 = getZhongzhi2Obj(meid);
      if (zhongzhi2 == null)
        throw new Exception("种植二期病历不存在，病历号：" + meid); 
      headerFooter.setHeader(headerName);
      table = this.zhongzhi2Logic.createTable1Info(userinfo, zhongzhi2);
      document.add((Element)table);
    } 
    if (5 == mtype) {
      headerName = String.valueOf(headerName) + "种植修复病历";
      KqdsMedicalrecordRestoration rest = getRestObj(meid);
      if (rest == null)
        throw new Exception("种植修复病历不存在，病历号：" + meid); 
      headerFooter.setHeader(headerName);
      table = this.restLogic.createTable1Info(userinfo, rest);
      document.add((Element)table);
    } 
    table = new PdfPTable(8);
    table.setWidthPercentage(100.0F);
    table.setWidths(new int[] { 1, 1, 1, 1, 1, 1, 1, 1 });
    String doctorName = "";
    if (!YZUtility.isNullorEmpty(medical.getDoctor())) {
      YZPerson p = this.personLogic.getPersonBySeqId(medical.getDoctor());
      if (p != null)
        doctorName = p.getUserName(); 
    } 
    String assistantName = "";
    if (!YZUtility.isNullorEmpty(medical.getAssistant())) {
      YZPerson p = this.personLogic.getPersonBySeqId(medical.getAssistant());
      if (p != null)
        assistantName = p.getUserName(); 
    } 
    String nurseName = "";
    if (!YZUtility.isNullorEmpty(medical.getNurse())) {
      YZPerson p = this.personLogic.getPersonBySeqId(medical.getNurse());
      if (p != null)
        nurseName = p.getUserName(); 
    } 
    PdfPCell cell11 = new PdfPCell(new Phrase("", FontChinese));
    cell11.setColspan(2);
    PdfPCell cell13 = new PdfPCell(new Phrase("医生：", FontChinese));
    PdfPCell cell14 = new PdfPCell(new Phrase(doctorName, FontChinese));
    PdfPCell cell15 = new PdfPCell(new Phrase("助理：", FontChinese));
    PdfPCell cell16 = new PdfPCell(new Phrase(assistantName, FontChinese));
    PdfPCell cell17 = new PdfPCell(new Phrase("护士：", FontChinese));
    PdfPCell cell18 = new PdfPCell(new Phrase(nurseName, FontChinese));
    cell11.setMinimumHeight(30.0F);
    table.addCell(cell11);
    table.addCell(cell13);
    table.addCell(cell14);
    table.addCell(cell15);
    table.addCell(cell16);
    table.addCell(cell17);
    table.addCell(cell18);
    document.add((Element)table);
    document.close();
    return filePath;
  }
}
