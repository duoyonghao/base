package com.kqds.service.base.print;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;

public class PdfReportM1HeaderFooter extends PdfPageEventHelper {
  public String header = "";
  
  public int presentFontSize = 12;
  
  public Rectangle pageSize = PageSize.A4;
  
  public PdfTemplate total;
  
  public BaseFont bf = null;
  
  public Font fontDetail = null;
  
  public PdfReportM1HeaderFooter() {}
  
  public PdfReportM1HeaderFooter(String yeMei, int presentFontSize, Rectangle pageSize) {
    this.header = yeMei;
    this.presentFontSize = presentFontSize;
    this.pageSize = pageSize;
  }
  
  public void setHeader(String header) {
    this.header = header;
  }
  
  public void setPresentFontSize(int presentFontSize) {
    this.presentFontSize = presentFontSize;
  }
  
  public void onOpenDocument(PdfWriter writer, Document document) {
    this.total = writer.getDirectContent().createTemplate(50.0F, 50.0F);
  }
  
  public void onEndPage(PdfWriter writer, Document document) {
    try {
      if (this.bf == null)
        this.bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false); 
      if (this.fontDetail == null)
        this.fontDetail = new Font(this.bf, this.presentFontSize, 0); 
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    float len_header = this.bf.getWidthPoint(this.header, 15.0F);
    ColumnText.showTextAligned(writer.getDirectContent(), 0, new Phrase(this.header, new Font(this.bf, 15.0F, 1)), (
        document.rightMargin() + document.right() + document.leftMargin() - document.left() - len_header) / 2.0F + 10.0F, document.top() + 10.0F, 0.0F);
    int pageS = writer.getPageNumber();
    String foot1 = "第 " + pageS + " 页 /共";
    Phrase footer = new Phrase(foot1, this.fontDetail);
    float len = this.bf.getWidthPoint(foot1, this.presentFontSize);
    PdfContentByte cb = writer.getDirectContent();
    ColumnText.showTextAligned(cb, 1, footer, (document.rightMargin() + document.right() + document.leftMargin() - document.left() - len) / 2.0F + 20.0F, 
        document.bottom() - 20.0F, 0.0F);
    cb.addTemplate(this.total, (document.rightMargin() + document.right() + document.leftMargin() - document.left()) / 2.0F + 20.0F, document.bottom() - 20.0F);
  }
  
  public void onCloseDocument(PdfWriter writer, Document document) {
    this.total.beginText();
    this.total.setFontAndSize(this.bf, this.presentFontSize);
    String foot2 = " " + (writer.getPageNumber() - 1) + " 页";
    this.total.showText(foot2);
    this.total.endText();
    this.total.closePath();
  }
}
