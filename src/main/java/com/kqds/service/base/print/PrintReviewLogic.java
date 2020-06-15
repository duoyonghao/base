package com.kqds.service.base.print;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsMedicalrecordReview;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.service.sys.base.BaseLogic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintReviewLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  private static BaseFont bfChinese = null;
  
  private static Font FontChinese = null;
  
  public PrintReviewLogic() {
    try {
      bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
      FontChinese = new Font(bfChinese, 12.0F, 0);
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  private void addCell2Table(PdfPTable table, List<PdfPCell> list) {
    for (PdfPCell pdfPCell : list)
      table.addCell(pdfPCell); 
  }
  
  public PdfPTable createTable1Info(KqdsUserdocument userinfo, KqdsMedicalrecordReview review) throws Exception {
    PdfPTable table = new PdfPTable(8);
    table.setWidthPercentage(100.0F);
    table.setWidths(new float[] { 1.2F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F });
    table.setSpacingBefore(5.0F);
    JSONObject json1 = new JSONObject();
    json1.put("name", "种植体情况：");
    json1.put("value", review.getConditionImplants());
    List<PdfPCell> conditionImplants = getValue(json1);
    addCell2Table(table, conditionImplants);
    PdfPCell cell21 = new PdfPCell(new Phrase("软组织愈合情况：", FontChinese));
    PdfPCell cell22 = new PdfPCell(new Phrase(review.getHealingTissue(), FontChinese));
    cell22.setColspan(7);
    table.addCell(cell21);
    table.addCell(cell22);
    PdfPCell cell31 = new PdfPCell(new Phrase("备注：", FontChinese));
    PdfPCell cell32 = new PdfPCell(new Phrase(review.getRemark(), FontChinese));
    cell32.setColspan(7);
    table.addCell(cell31);
    table.addCell(cell32);
    return table;
  }
  
  public List<PdfPCell> getValue(JSONObject job) {
    String name = job.getString("name");
    String value = job.getString("value");
    List<PdfPCell> cellList = new ArrayList<>();
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
          crup1 = ywarr[0]; 
        if (!"".equals(ywarr[1]))
          crup2 = ywarr[1]; 
        if (!"".equals(ywarr[2]))
          crdown1 = ywarr[2]; 
        if (!"".equals(ywarr[3]))
          crdown2 = ywarr[3]; 
        if (k == 0) {
          PdfPCell cell11 = new PdfPCell(new Phrase(name, FontChinese));
          cell11.setRowspan(cell1Num * 2);
          cellList.add(cell11);
        } 
        PdfPCell cell13 = new PdfPCell(new Phrase(crup1, FontChinese));
        PdfPCell cell14 = new PdfPCell(new Phrase(crup2, FontChinese));
        PdfPCell cell15 = new PdfPCell(new Phrase(content, FontChinese));
        cell15.setRowspan(2);
        cell15.setColspan(5);
        PdfPCell cell23 = new PdfPCell(new Phrase(crdown1, FontChinese));
        PdfPCell cell24 = new PdfPCell(new Phrase(crdown2, FontChinese));
        cell13.setMinimumHeight(20.0F);
        cell14.setMinimumHeight(20.0F);
        cell23.setMinimumHeight(20.0F);
        cell24.setMinimumHeight(20.0F);
        cellList.add(cell13);
        cellList.add(cell14);
        cellList.add(cell15);
        cellList.add(cell23);
        cellList.add(cell24);
      } 
    } 
    return cellList;
  }
}
