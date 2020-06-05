package com.kqds.service.base.print;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsMedicalrecordZhongzhi2;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.service.sys.base.BaseLogic;

import net.sf.json.JSONObject;

/**
 * 种植一期病历
 * 
 * @author Administrator
 * 
 */
@Service
public class PrintZhongZhi2Logic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	private static BaseFont bfChinese = null;
	private static Font FontChinese = null;

	public PrintZhongZhi2Logic() {
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			FontChinese = new Font(bfChinese, 12, Font.NORMAL);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	 * 表一
	 * 
	 * @param userinfo
	 * @param meid
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public PdfPTable createTable1Info(KqdsUserdocument userinfo, KqdsMedicalrecordZhongzhi2 zhongzhi2) throws Exception {
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 1.2f, 1, 1, 1, 1, 1, 1, 1 });
		table.setSpacingBefore(5f);

		JSONObject json1 = new JSONObject();
		json1.put("name", "术前检查：");
		json1.put("value", zhongzhi2.getPreoperativeExamination());
		List<PdfPCell> preoperativeExamination = getValue(json1);
		addCell2Table(table, preoperativeExamination);

		JSONObject json2 = new JSONObject();
		json2.put("name", "邻牙情况：");
		json2.put("value", zhongzhi2.getTeethCondition());
		List<PdfPCell> teethCondition = getValue(json2);
		addCell2Table(table, teethCondition);

		JSONObject json3 = new JSONObject();
		json3.put("name", "种植区牙龈情况：");
		json3.put("value", zhongzhi2.getImplantgumCondition());
		List<PdfPCell> implantgumCondition = getValue(json3);
		addCell2Table(table, implantgumCondition);

		JSONObject json4 = new JSONObject();
		json4.put("name", "种植体暴露：");
		json4.put("value", zhongzhi2.getImplantExposure());
		List<PdfPCell> implantExposure = getValue(json4);
		addCell2Table(table, implantExposure);

		JSONObject json5 = new JSONObject();
		json5.put("name", "种植体X体线检查：");
		json5.put("value", zhongzhi2.getXrayExamination());
		List<PdfPCell> xrayExamination = getValue(json5);
		addCell2Table(table, xrayExamination);

		PdfPCell cell21 = new PdfPCell(new Phrase("其他：", FontChinese));
		PdfPCell cell22 = new PdfPCell(new Phrase(zhongzhi2.getOthers(), FontChinese));
		cell22.setColspan(7);

		// ######################
		table.addCell(cell21);
		table.addCell(cell22);
		// ######################

		PdfPCell cell31 = new PdfPCell(new Phrase("手术记录：", FontChinese));
		PdfPCell cell32 = new PdfPCell(new Phrase(zhongzhi2.getSurgicalRecord(), FontChinese));
		cell32.setColspan(7);

		// ######################
		table.addCell(cell31);
		table.addCell(cell32);
		// ######################

		PdfPCell cell41 = new PdfPCell(new Phrase("备注：", FontChinese));
		PdfPCell cell42 = new PdfPCell(new Phrase(zhongzhi2.getRemark(), FontChinese));
		cell42.setColspan(7);

		// ######################
		table.addCell(cell41);
		table.addCell(cell42);
		// ######################

		return table;
	}

	public List<PdfPCell> getValue(JSONObject job) {

		String name = job.getString("name");
		String value = job.getString("value");

		List<PdfPCell> cellList = new ArrayList<PdfPCell>();

		String[] arr = value.split("\\|\\|\\|"); // 1;2;4;3||撒东窗事发大概|||1;2;2;4||打折的v风格|||
		int cell1Num = arr.length; // 这个是活动的，比如 检查项有2行

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
				if (content == null) {
					content = "";
				}

				// 根据 usertype 0 成人 1 儿童 给牙位加象限值
				String crup1 = "", crup2 = "", crdown1 = "", crdown2 = "";
				if (!"".equals(ywarr[0])) {
					crup1 = ywarr[0];
				}
				if (!"".equals(ywarr[1])) {
					crup2 = ywarr[1];
				}
				if (!"".equals(ywarr[2])) {
					crdown1 = ywarr[2];
				}
				if (!"".equals(ywarr[3])) {
					crdown2 = ywarr[3];
				}
				/***************************** 赋值 **********************************/
				if (k == 0) { // 第一行
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

				cell13.setMinimumHeight(20);
				cell14.setMinimumHeight(20);
				cell23.setMinimumHeight(20);
				cell24.setMinimumHeight(20);

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
