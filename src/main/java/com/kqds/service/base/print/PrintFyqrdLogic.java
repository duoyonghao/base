package com.kqds.service.base.print;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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

import net.sf.json.JSONObject;

/**
 * 费用确认单
 * 
 * @author Administrator
 * 
 */
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

	/**
	 * 添加cell到表格
	 * 
	 * @param table
	 * @param list
	 */
	private void addCell2Table(PdfPTable table, List<PdfPCell> list) {
		for (PdfPCell pdfPCell : list) {
			table.addCell(pdfPCell);
		}
	}

	/**
	 * 费用确认单
	 * 
	 * @param seqId
	 * @param conn
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String feiYongPdf4Print(String seqId, HttpServletRequest request) throws Exception {
		bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		FontChinese = new Font(bfChinese, 12, Font.NORMAL);

		String titles = "缴费单";
		String printType = request.getParameter("printType");
		if ("2".equals(printType)) {
			titles = "费用明细单";
		}
		String headerName = ChainUtil.getOrgName(request); // 打印抬头
		headerName += titles;

		String spaceStr = ""; // 为了标题太短时剧中显示
		if (headerName.length() < 24) {
			int count = (24 - headerName.length() / 2);
			for (int j = 0; j < count; j++) {
				spaceStr += "  "; // 设置空格
			}
		}
		headerName = spaceStr + headerName;

		String path = request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator + "print";
		File pathObj = new File(path);
		if (!pathObj.exists()) {
			pathObj.mkdirs();
		}
		String filePath = path + File.separator + YZUtility.getUUID() + ".pdf";

		Document document = new Document();

		// public static final Rectangle A4 = new RectangleReadOnly(595.0F,
		// 842.0F);
		// public static final Rectangle A5 = new RectangleReadOnly(420.0F,
		// 595.0F);

		document.setPageSize(new RectangleReadOnly(595.0F, 421.0F)); // 纵向

		// 说明：
		// 当创建一个矩形或设置边距时，你可能希望知道该用什么度量单位：厘米、英寸或象素，事实上，默认的度量系统以排版单位磅为基础得出其他单位的近似值，如1英寸=72磅，如果你想在A4页面的PDF中创建一个矩形，你需要计算以下数据：
		// 21 厘米 / 2.54 = 8.2677 英寸
		// 8.2677英寸* 72 = 595 磅
		// 29.7 厘米 / 2.54 = 11.6929 英寸
		// 11.6929英寸* 72 = 842 磅

		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

		// 设置页眉页脚
		PdfReportM1HeaderFooter headerFooter = new PdfReportM1HeaderFooter();// 就是上面那个类
		headerFooter.setHeader(headerName);
		writer.setPageEvent(headerFooter);

		// 打开文档
		document.open();

		PdfPTable table = null;

		KqdsCostorder costorder = (KqdsCostorder) dao.loadObjSingleUUID(TableNameUtil.KQDS_COSTORDER, seqId);
		if (costorder == null) {
			throw new Exception("费用单不存在，seqId：" + seqId);
		}
		String usercode = costorder.getUsercode();
		KqdsUserdocument userinfo = userLogic.getSingleUserByUsercode(usercode);
		if (userinfo == null) {
			throw new Exception("患者不存在，usercode：" + usercode);
		}
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("costno", seqId);

		List<KqdsCostorderDetail> detailList = (List<KqdsCostorderDetail>) dao.loadList(TableNameUtil.KQDS_COSTORDER_DETAIL, filter);

		if (detailList == null || detailList.size() == 0) {
			throw new Exception("费用明细单不存在，费用单seqId：" + seqId);
		}

		// 患者基本信息
		table = createUserInfoTable(userinfo, costorder);
		document.add(table);

		// 病历内容，在方法里面进行了 document.add(table)操作
		getFeiYongContent(costorder, detailList, document, request);

		table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 1 });
		// 1、Paragraph 和 Phrase 区别
		PdfPCell cell = new PdfPCell(new Phrase("客户签名：____________________", new Font(bfChinese, 12, Font.NORMAL)));
		cell.setMinimumHeight(30);
		cell.setBorder(0);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setPaddingRight(0);
		table.addCell(cell);

		document.add(table);

		// 5.关闭文档
		document.close();
		return filePath;

	}

	/**
	 * 获取患者基本信息表格
	 * 
	 * @param userinfo
	 * @param meid
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private PdfPTable createUserInfoTable(KqdsUserdocument userinfo, KqdsCostorder costorer) throws Exception {
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 0.75f, 1.2f, 0.75f, 2f, 0.75f, 2f, 0.75f, 1.2f });
		table.setSpacingBefore(5f);

		String doctor = costorer.getDoctor();
		String doctorName = "";
		if (!YZUtility.isNullorEmpty(doctor)) {
			doctorName = personLogic.getNameStrBySeqIds(doctor);
		}

		PdfPCell cell11 = new PdfPCell(new Phrase("姓名：", FontChinese));
		PdfPCell cell12 = new PdfPCell(new Phrase(userinfo.getUsername(), FontChinese));
		PdfPCell cell13 = new PdfPCell(new Phrase("卡号：", FontChinese));
		PdfPCell cell14 = new PdfPCell(new Phrase(costorer.getUsercode(), FontChinese));
		PdfPCell cell15 = new PdfPCell(new Phrase("时间：", FontChinese));
		PdfPCell cell16 = new PdfPCell(new Phrase(costorer.getCreatetime() + "", FontChinese));
		PdfPCell cell17 = new PdfPCell(new Phrase("医生：", FontChinese));
		PdfPCell cell18 = new PdfPCell(new Phrase(doctorName, FontChinese));

		cell11.setMinimumHeight(30); // 设置最小高度

		// 内容左对齐
		cell12.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell14.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell16.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell18.setHorizontalAlignment(Element.ALIGN_LEFT);

		cell11.setBorderWidthLeft(0);
		cell12.setBorderWidthLeft(0);
		cell13.setBorderWidthLeft(0);
		cell14.setBorderWidthLeft(0);
		cell15.setBorderWidthLeft(0);
		cell16.setBorderWidthLeft(0);
		cell17.setBorderWidthLeft(0);
		cell18.setBorderWidthLeft(0);

		cell11.setBorderWidthRight(0);
		cell12.setBorderWidthRight(0);
		cell13.setBorderWidthRight(0);
		cell14.setBorderWidthRight(0);
		cell15.setBorderWidthRight(0);
		cell16.setBorderWidthRight(0);
		cell17.setBorderWidthRight(0);
		cell18.setBorderWidthRight(0);

		cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell12.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell13.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell14.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell15.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell16.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell17.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell18.setVerticalAlignment(Element.ALIGN_MIDDLE);

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

	/*******************************************
	 * 初诊
	 *******************************************/

	/**
	 * 获取初诊病历内容
	 * 
	 * @param czInfo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private void getFeiYongContent(KqdsCostorder costOrder, List<KqdsCostorderDetail> detailList, Document document, HttpServletRequest request) throws Exception {
		List<PdfPCell> cellList = new ArrayList<PdfPCell>();

		/******************************** 表1 ****************************************/

		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 2f, 0.6f, 0.8f, 0.6f, 0.6f, 1, 1, 1 });
		table.setSpacingBefore(5f);

		PdfPCell cell11 = new PdfPCell(new Phrase("治疗项目", FontChinese));
		PdfPCell cell12 = new PdfPCell(new Phrase("单位", FontChinese));
		PdfPCell cell13 = new PdfPCell(new Phrase("单价", FontChinese));
		PdfPCell cell14 = new PdfPCell(new Phrase("数量", FontChinese));
		PdfPCell cell15 = new PdfPCell(new Phrase("折扣%", FontChinese));
		PdfPCell cell16 = new PdfPCell(new Phrase("应收", FontChinese));
		PdfPCell cell17 = new PdfPCell(new Phrase("欠费金额", FontChinese));
		PdfPCell cell18 = new PdfPCell(new Phrase("缴费金额", FontChinese));

		cell11.setBorder(0); // 去掉边框
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

			cell1.setBorder(0); // 去掉边框
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

		// 添加cell 到 table
		addCell2Table(table, cellList);

		/******************************** 表2 ****************************************/

		// 重新初始化list
		cellList = new ArrayList<PdfPCell>();
		PdfPTable table2 = new PdfPTable(6);
		table2.setWidthPercentage(100);
		table2.setWidths(new float[] { 0.5f, 1, 1, 1, 1, 1 });
		table2.setSpacingBefore(5f);

		PdfPCell cella1 = new PdfPCell(new Phrase("应付：", FontChinese));
		PdfPCell cella2 = new PdfPCell(new Phrase(costOrder.getShouldmoney().toString(), FontChinese));

		cella1.setHorizontalAlignment(Element.ALIGN_LEFT);
		cella2.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell cella3 = new PdfPCell(new Phrase("本次缴费：", FontChinese));
		PdfPCell cella4 = new PdfPCell(new Phrase(costOrder.getActualmoney().toString(), FontChinese));

		cella3.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cella4.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell cella5 = new PdfPCell(new Phrase("欠费：", FontChinese));
		PdfPCell cella6 = new PdfPCell(new Phrase(costOrder.getArrearmoney().toString(), FontChinese));

		cella5.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cella6.setHorizontalAlignment(Element.ALIGN_LEFT);

		cella1.setBorder(0); // 去掉边框
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

		// 添加cell 到 table
		addCell2Table(table2, cellList);
		/******************************** 表3 ****************************************/

		// 重新初始化list
		cellList = new ArrayList<PdfPCell>();

		JSONObject fkfsObj = orderdetailLogic.printSfxm(TableNameUtil.KQDS_COSTORDER_DETAIL, costOrder.getSeqId(), request);
		java.util.Iterator it = fkfsObj.keys();
		int num = 0;
		while (it.hasNext()) {

			if (num == 0) {
				PdfPCell cellFirst = new PdfPCell(new Phrase("其中：", FontChinese));
				cellFirst.setBorder(0);
				cellList.add(cellFirst);
			} else {

				if (num % 4 == 0) {
					PdfPCell cellFirst = new PdfPCell(new Phrase("", FontChinese));
					cellFirst.setBorder(0);
					cellList.add(cellFirst);
				}

			}

			String key = (String) it.next();
			String value = fkfsObj.getString(key);

			PdfPCell cellb1 = new PdfPCell(new Phrase(key + ":", FontChinese));
			PdfPCell cellb2 = new PdfPCell(new Phrase(value, FontChinese));
			cellb1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellb2.setHorizontalAlignment(Element.ALIGN_LEFT);

			cellb1.setBorder(0);
			cellb2.setBorder(0);

			cellList.add(cellb1);
			cellList.add(cellb2);

			num++;

		}

		int quyu = num % 4;
		int buchong = 4 - quyu; // 如果数量不够，则用空的补充

		for (int i = 0; i < buchong; i++) {
			PdfPCell cellb1 = new PdfPCell(new Phrase("", FontChinese));
			PdfPCell cellb2 = new PdfPCell(new Phrase("", FontChinese));

			cellb1.setBorder(0);
			cellb2.setBorder(0);

			cellList.add(cellb1);
			cellList.add(cellb2);
		}

		// 固定为4，为了避免只有一种方式时，页面不美观
		PdfPTable table3 = new PdfPTable(4 * 2 + 1);
		table3.setWidthPercentage(100);
		table3.setWidths(new float[] { 0.8f, 1.3f, 1, 1.3f, 1, 1.3f, 1, 1.3f, 1 });
		table3.setSpacingBefore(5f);

		// 添加cell 到 table
		addCell2Table(table3, cellList);

		/******************************** 表4 ****************************************/

		PdfPTable table4 = new PdfPTable(1);
		table4.setWidthPercentage(100);
		table4.setWidths(new float[] { 1 });
		table4.setSpacingBefore(5f);

		PdfPCell cellc = new PdfPCell(new Phrase("尊敬的客户，为了能向您提供优质的服务，请确认医生已向您详细介绍了本次治疗的项目及相关费用，并征得您的同意。", new Font(bfChinese, 10, Font.NORMAL)));
		cellc.setBorder(0);
		cellc.setHorizontalAlignment(Element.ALIGN_LEFT);
		table4.addCell(cellc);

		/******************************** 表5 ****************************************/

		PdfPTable table5 = new PdfPTable(2);
		table5.setWidthPercentage(100);
		table5.setWidths(new float[] { 1, 1 });
		table5.setSpacingBefore(10f);

		PdfPCell celld1 = new PdfPCell(new Phrase("地址：" + ChainUtil.getOrgAddress(request), FontChinese));
		celld1.setBorder(0);
		celld1.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell celld2 = new PdfPCell(new Phrase("热线：" + ChainUtil.getOrgTelNo(request), FontChinese));
		celld2.setBorder(0);
		celld2.setHorizontalAlignment(Element.ALIGN_LEFT);

		table5.addCell(celld1);
		table5.addCell(celld2);

		document.add(table);
		document.add(table2);
		document.add(table3);
		document.add(table4);
		document.add(table5);

	}

}
