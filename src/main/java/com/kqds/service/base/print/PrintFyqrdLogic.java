package com.kqds.service.base.print;

import com.itextpdf.text.Document;
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
import com.kqds.entity.base.KqdsCostorder;
import com.kqds.entity.base.KqdsCostorderDetail;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.service.base.costOrderDetail.KQDS_CostOrder_DetailLogic;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.sys.base.BaseLogic;
import com.kqds.service.sys.person.YZPersonLogic;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintFyqrdLogic extends BaseLogic {
  @Autowired
  private DaoSupport dao;
  
  private static BaseFont bfChinese = null;
  
  private static Font FontChinese = null;
  
  @Autowired
  private YZPersonLogic personLogic;
  
  @Autowired
  private KQDS_CostOrder_DetailLogic orderdetailLogic;
  
  @Autowired
  private KQDS_UserDocumentLogic userLogic;
  
  private void addCell2Table(PdfPTable table, List<PdfPCell> list) {
    for (PdfPCell pdfPCell : list)
      table.addCell(pdfPCell); 
  }
  
  public String feiYongPdf4Print(String seqId, HttpServletRequest request) throws Exception {
    bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
    FontChinese = new Font(bfChinese, 12.0F, 0);
    String titles = "缴费单";
    String printType = request.getParameter("printType");
    if ("2".equals(printType))
      titles = "费用明细单"; 
    String headerName = ChainUtil.getOrgName(request);
    headerName = String.valueOf(headerName) + titles;
    String spaceStr = "";
    if (headerName.length() < 24) {
      int count = 24 - headerName.length() / 2;
      for (int j = 0; j < count; j++)
        spaceStr = String.valueOf(spaceStr) + "  "; 
    } 
    headerName = String.valueOf(spaceStr) + headerName;
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
    KqdsCostorder costorder = (KqdsCostorder)this.dao.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, seqId);
    if (costorder == null)
      throw new Exception("费用单不存在，seqId：" + seqId); 
    String usercode = costorder.getUsercode();
    KqdsUserdocument userinfo = this.userLogic.getSingleUserByUsercode(usercode);
    if (userinfo == null)
      throw new Exception("患者不存在，usercode：" + usercode); 
    Map<String, String> filter = new HashMap<>();
    filter.put("costno", seqId);
    List<KqdsCostorderDetail> detailList = (List<KqdsCostorderDetail>)this.dao.loadList(TableNameUtil.KQDS_COSTORDER_DETAIL, filter);
    if (detailList == null || detailList.size() == 0)
      throw new Exception("费用明细单不存在，费用单seqId：" + seqId); 
    table = createUserInfoTable(userinfo, costorder);
    document.add((Element)table);
    getFeiYongContent(costorder, detailList, document, request);
    table = new PdfPTable(1);
    table.setWidthPercentage(100.0F);
    table.setWidths(new int[] { 1 });
    PdfPCell cell = new PdfPCell(new Phrase("客户签名：____________________", new Font(bfChinese, 12.0F, 0)));
    cell.setMinimumHeight(30.0F);
    cell.setBorder(0);
    cell.setVerticalAlignment(5);
    cell.setHorizontalAlignment(2);
    cell.setPaddingRight(0.0F);
    table.addCell(cell);
    document.add((Element)table);
    document.close();
    return filePath;
  }
  
  private PdfPTable createUserInfoTable(KqdsUserdocument userinfo, KqdsCostorder costorer) throws Exception {
    PdfPTable table = new PdfPTable(8);
    table.setWidthPercentage(100.0F);
    table.setWidths(new float[] { 0.75F, 1.2F, 0.75F, 2.0F, 0.75F, 2.0F, 0.75F, 1.2F });
    table.setSpacingBefore(5.0F);
    String doctor = costorer.getDoctor();
    String doctorName = "";
    if (!YZUtility.isNullorEmpty(doctor))
      doctorName = this.personLogic.getNameStrBySeqIds(doctor); 
    PdfPCell cell11 = new PdfPCell(new Phrase("姓名：", FontChinese));
    PdfPCell cell12 = new PdfPCell(new Phrase(userinfo.getUsername(), FontChinese));
    PdfPCell cell13 = new PdfPCell(new Phrase("卡号：", FontChinese));
    PdfPCell cell14 = new PdfPCell(new Phrase(costorer.getUsercode(), FontChinese));
    PdfPCell cell15 = new PdfPCell(new Phrase("时间：", FontChinese));
    PdfPCell cell16 = new PdfPCell(new Phrase((new StringBuilder(String.valueOf(costorer.getCreatetime()))).toString(), FontChinese));
    PdfPCell cell17 = new PdfPCell(new Phrase("医生：", FontChinese));
    PdfPCell cell18 = new PdfPCell(new Phrase(doctorName, FontChinese));
    cell11.setMinimumHeight(30.0F);
    cell12.setHorizontalAlignment(0);
    cell14.setHorizontalAlignment(0);
    cell16.setHorizontalAlignment(0);
    cell18.setHorizontalAlignment(0);
    cell11.setBorderWidthLeft(0.0F);
    cell12.setBorderWidthLeft(0.0F);
    cell13.setBorderWidthLeft(0.0F);
    cell14.setBorderWidthLeft(0.0F);
    cell15.setBorderWidthLeft(0.0F);
    cell16.setBorderWidthLeft(0.0F);
    cell17.setBorderWidthLeft(0.0F);
    cell18.setBorderWidthLeft(0.0F);
    cell11.setBorderWidthRight(0.0F);
    cell12.setBorderWidthRight(0.0F);
    cell13.setBorderWidthRight(0.0F);
    cell14.setBorderWidthRight(0.0F);
    cell15.setBorderWidthRight(0.0F);
    cell16.setBorderWidthRight(0.0F);
    cell17.setBorderWidthRight(0.0F);
    cell18.setBorderWidthRight(0.0F);
    cell11.setVerticalAlignment(5);
    cell12.setVerticalAlignment(5);
    cell13.setVerticalAlignment(5);
    cell14.setVerticalAlignment(5);
    cell15.setVerticalAlignment(5);
    cell16.setVerticalAlignment(5);
    cell17.setVerticalAlignment(5);
    cell18.setVerticalAlignment(5);
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
  
  private void getFeiYongContent(KqdsCostorder costOrder, List<KqdsCostorderDetail> detailList, Document document, HttpServletRequest request) throws Exception {
    List<PdfPCell> cellList = new ArrayList<>();
    PdfPTable table = new PdfPTable(8);
    table.setWidthPercentage(100.0F);
    table.setWidths(new float[] { 2.0F, 0.6F, 0.8F, 0.6F, 0.6F, 1.0F, 1.0F, 1.0F });
    table.setSpacingBefore(5.0F);
    PdfPCell cell11 = new PdfPCell(new Phrase("治疗项目", FontChinese));
    PdfPCell cell12 = new PdfPCell(new Phrase("单位", FontChinese));
    PdfPCell cell13 = new PdfPCell(new Phrase("单价", FontChinese));
    PdfPCell cell14 = new PdfPCell(new Phrase("数量", FontChinese));
    PdfPCell cell15 = new PdfPCell(new Phrase("折扣%", FontChinese));
    PdfPCell cell16 = new PdfPCell(new Phrase("应收", FontChinese));
    PdfPCell cell17 = new PdfPCell(new Phrase("欠费金额", FontChinese));
    PdfPCell cell18 = new PdfPCell(new Phrase("缴费金额", FontChinese));
    cell11.setBorder(0);
    cell12.setBorder(0);
    cell13.setBorder(0);
    cell14.setBorder(0);
    cell15.setBorder(0);
    cell16.setBorder(0);
    cell17.setBorder(0);
    cell18.setBorder(0);
    cellList.add(cell11);
    cellList.add(cell12);
    cellList.add(cell13);
    cellList.add(cell14);
    cellList.add(cell15);
    cellList.add(cell16);
    cellList.add(cell17);
    cellList.add(cell18);
    for (KqdsCostorderDetail kqds_CostOrder_Detail : detailList) {
      BigDecimal ys = KqdsBigDecimal.sub(kqds_CostOrder_Detail.getSubtotal(), kqds_CostOrder_Detail.getVoidmoney());
      PdfPCell cell1 = new PdfPCell(new Phrase(kqds_CostOrder_Detail.getItemname(), FontChinese));
      PdfPCell cell2 = new PdfPCell(new Phrase(kqds_CostOrder_Detail.getUnit(), FontChinese));
      PdfPCell cell3 = new PdfPCell(new Phrase(kqds_CostOrder_Detail.getUnitprice().toString(), FontChinese));
      PdfPCell cell4 = new PdfPCell(new Phrase(kqds_CostOrder_Detail.getNum(), FontChinese));
      PdfPCell cell5 = new PdfPCell(new Phrase(kqds_CostOrder_Detail.getDiscount(), FontChinese));
      PdfPCell cell6 = new PdfPCell(new Phrase(ys.toString(), FontChinese));
      PdfPCell cell7 = new PdfPCell(new Phrase(kqds_CostOrder_Detail.getY2().toString(), FontChinese));
      PdfPCell cell8 = new PdfPCell(new Phrase(kqds_CostOrder_Detail.getPaymoney().toString(), FontChinese));
      cell1.setBorder(0);
      cell2.setBorder(0);
      cell3.setBorder(0);
      cell4.setBorder(0);
      cell5.setBorder(0);
      cell6.setBorder(0);
      cell7.setBorder(0);
      cell8.setBorder(0);
      cellList.add(cell1);
      cellList.add(cell2);
      cellList.add(cell3);
      cellList.add(cell4);
      cellList.add(cell5);
      cellList.add(cell6);
      cellList.add(cell7);
      cellList.add(cell8);
    } 
    addCell2Table(table, cellList);
    cellList = new ArrayList<>();
    PdfPTable table2 = new PdfPTable(6);
    table2.setWidthPercentage(100.0F);
    table2.setWidths(new float[] { 0.5F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F });
    table2.setSpacingBefore(5.0F);
    PdfPCell cella1 = new PdfPCell(new Phrase("应付：", FontChinese));
    PdfPCell cella2 = new PdfPCell(new Phrase(costOrder.getShouldmoney().toString(), FontChinese));
    cella1.setHorizontalAlignment(0);
    cella2.setHorizontalAlignment(0);
    PdfPCell cella3 = new PdfPCell(new Phrase("本次缴费：", FontChinese));
    PdfPCell cella4 = new PdfPCell(new Phrase(costOrder.getActualmoney().toString(), FontChinese));
    cella3.setHorizontalAlignment(2);
    cella4.setHorizontalAlignment(0);
    PdfPCell cella5 = new PdfPCell(new Phrase("欠费：", FontChinese));
    PdfPCell cella6 = new PdfPCell(new Phrase(costOrder.getArrearmoney().toString(), FontChinese));
    cella5.setHorizontalAlignment(2);
    cella6.setHorizontalAlignment(0);
    cella1.setBorder(0);
    cella2.setBorder(0);
    cella3.setBorder(0);
    cella4.setBorder(0);
    cella5.setBorder(0);
    cella6.setBorder(0);
    cellList.add(cella1);
    cellList.add(cella2);
    cellList.add(cella3);
    cellList.add(cella4);
    cellList.add(cella5);
    cellList.add(cella6);
    addCell2Table(table2, cellList);
    cellList = new ArrayList<>();
    JSONObject fkfsObj = this.orderdetailLogic.printSfxm(TableNameUtil.KQDS_COSTORDER_DETAIL, costOrder.getSeqId(), request);
    Iterator<String> it = fkfsObj.keys();
    int num = 0;
    while (it.hasNext()) {
      if (num == 0) {
        PdfPCell cellFirst = new PdfPCell(new Phrase("其中：", FontChinese));
        cellFirst.setBorder(0);
        cellList.add(cellFirst);
      } else if (num % 4 == 0) {
        PdfPCell cellFirst = new PdfPCell(new Phrase("", FontChinese));
        cellFirst.setBorder(0);
        cellList.add(cellFirst);
      } 
      String key = it.next();
      String value = fkfsObj.getString(key);
      PdfPCell cellb1 = new PdfPCell(new Phrase(String.valueOf(key) + ":", FontChinese));
      PdfPCell cellb2 = new PdfPCell(new Phrase(value, FontChinese));
      cellb1.setHorizontalAlignment(2);
      cellb2.setHorizontalAlignment(0);
      cellb1.setBorder(0);
      cellb2.setBorder(0);
      cellList.add(cellb1);
      cellList.add(cellb2);
      num++;
    } 
    int quyu = num % 4;
    int buchong = 4 - quyu;
    for (int i = 0; i < buchong; i++) {
      PdfPCell cellb1 = new PdfPCell(new Phrase("", FontChinese));
      PdfPCell cellb2 = new PdfPCell(new Phrase("", FontChinese));
      cellb1.setBorder(0);
      cellb2.setBorder(0);
      cellList.add(cellb1);
      cellList.add(cellb2);
    } 
    PdfPTable table3 = new PdfPTable(9);
    table3.setWidthPercentage(100.0F);
    table3.setWidths(new float[] { 0.8F, 1.3F, 1.0F, 1.3F, 1.0F, 1.3F, 1.0F, 1.3F, 1.0F });
    table3.setSpacingBefore(5.0F);
    addCell2Table(table3, cellList);
    PdfPTable table4 = new PdfPTable(1);
    table4.setWidthPercentage(100.0F);
    table4.setWidths(new float[] { 1.0F });
    table4.setSpacingBefore(5.0F);
    PdfPCell cellc = new PdfPCell(new Phrase("尊敬的客户，为了能向您提供优质的服务，请确认医生已向您详细介绍了本次治疗的项目及相关费用，并征得您的同意。", new Font(bfChinese, 10.0F, 0)));
    cellc.setBorder(0);
    cellc.setHorizontalAlignment(0);
    table4.addCell(cellc);
    PdfPTable table5 = new PdfPTable(2);
    table5.setWidthPercentage(100.0F);
    table5.setWidths(new float[] { 1.0F, 1.0F });
    table5.setSpacingBefore(10.0F);
    PdfPCell celld1 = new PdfPCell(new Phrase("地址：" + ChainUtil.getOrgAddress(request), FontChinese));
    celld1.setBorder(0);
    celld1.setHorizontalAlignment(0);
    PdfPCell celld2 = new PdfPCell(new Phrase("热线：" + ChainUtil.getOrgTelNo(request), FontChinese));
    celld2.setBorder(0);
    celld2.setHorizontalAlignment(0);
    table5.addCell(celld1);
    table5.addCell(celld2);
    document.add((Element)table);
    document.add((Element)table2);
    document.add((Element)table3);
    document.add((Element)table4);
    document.add((Element)table5);
  }
}
