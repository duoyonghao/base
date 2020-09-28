package com.kqds.service.base.print;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.base.KqdsMedicalrecordZhongzhi;
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
public class PrintZhongZhi1Logic extends BaseLogic {
	@Autowired
	private DaoSupport dao;

	private static BaseFont bfChinese = null;
	private static Font FontChinese = null;

	public PrintZhongZhi1Logic() {
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
	 * 获取患者基本信息表格
	 * 
	 * @param userinfo
	 * @param meid
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public PdfPTable createUserInfoTable(KqdsUserdocument userinfo, KqdsMedicalrecordZhongzhi zhongzhi1) throws Exception {
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 1.2f, 1, 1, 1, 1, 1, 1, 1.2f });
		table.setSpacingBefore(5f);

		PdfPCell cell11 = new PdfPCell(new Phrase("患者姓名：", FontChinese));
		PdfPCell cell12 = new PdfPCell(new Phrase(userinfo.getUsername(), FontChinese));
		PdfPCell cell13 = new PdfPCell(new Phrase("性别：", FontChinese));
		PdfPCell cell14 = new PdfPCell(new Phrase(userinfo.getSex(), FontChinese));
		PdfPCell cell15 = new PdfPCell(new Phrase("年龄：", FontChinese));
		PdfPCell cell16 = new PdfPCell(new Phrase(userinfo.getAge() + "", FontChinese));
		PdfPCell cell17 = new PdfPCell(new Phrase("病历号：", FontChinese));
		PdfPCell cell18 = new PdfPCell(new Phrase(userinfo.getUsercode(), FontChinese));

		cell11.setMinimumHeight(30); // 设置最小高度

		// 内容左对齐
		cell12.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell14.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell16.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell18.setHorizontalAlignment(Element.ALIGN_LEFT);

		// cell11.setBorderWidthLeft(0);
		// cell12.setBorderWidthLeft(0);
		// cell13.setBorderWidthLeft(0);
		// cell14.setBorderWidthLeft(0);
		// cell15.setBorderWidthLeft(0);
		// cell16.setBorderWidthLeft(0);
		// cell17.setBorderWidthLeft(0);
		// cell18.setBorderWidthLeft(0);
		//
		// cell11.setBorderWidthRight(0);
		// cell12.setBorderWidthRight(0);
		// cell13.setBorderWidthRight(0);
		// cell14.setBorderWidthRight(0);
		// cell15.setBorderWidthRight(0);
		// cell16.setBorderWidthRight(0);
		// cell17.setBorderWidthRight(0);
		// cell18.setBorderWidthRight(0);

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

		// 第二行
		PdfPCell cell21 = new PdfPCell(new Phrase("家庭住址：", FontChinese));
		PdfPCell cell22 = new PdfPCell(new Phrase(userinfo.getAddress(), FontChinese));
		cell22.setColspan(7);

		table.addCell(cell21);
		table.addCell(cell22);

		// 第三行
		PdfPCell cell31 = new PdfPCell(new Phrase("电话：", FontChinese));
		PdfPCell cell32 = new PdfPCell(new Phrase(userinfo.getPhonenumber1(), FontChinese));
		PdfPCell cell33 = new PdfPCell(new Phrase("Email：", FontChinese));
		PdfPCell cell34 = new PdfPCell(new Phrase(userinfo.getEmail(), FontChinese));
		cell32.setColspan(3);
		cell34.setColspan(3);

		table.addCell(cell31);
		table.addCell(cell32);
		table.addCell(cell33);
		table.addCell(cell34);

		PdfPCell cell41 = new PdfPCell(new Phrase("药物过敏：", FontChinese));
		PdfPCell cell42 = new PdfPCell(new Phrase(zhongzhi1.getAllergy(), FontChinese));
		cell42.setColspan(7);

		table.addCell(cell41);
		table.addCell(cell42);

		return table;
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
	public PdfPTable createTable1Info(KqdsUserdocument userinfo, KqdsMedicalrecordZhongzhi zhongzhi1) throws Exception {
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 1.2f, 1, 1, 1, 1, 1, 1, 1 });
		table.setSpacingBefore(5f);

		JSONObject json1 = new JSONObject();
		json1.put("name", "牙列情况：");
		json1.put("value", zhongzhi1.getDentition());
		List<PdfPCell> dentition = getValue(json1);
		addCell2Table(table, dentition);

		PdfPCell cell21 = new PdfPCell(new Phrase("软组织情况：", FontChinese));
		PdfPCell cell22 = new PdfPCell(new Phrase(zhongzhi1.getSoftTissue(), FontChinese));
		cell22.setColspan(7);

		// ######################
		table.addCell(cell21);
		table.addCell(cell22);
		// ######################

		PdfPCell cell31 = new PdfPCell(new Phrase("硬组织情况：", FontChinese));
		PdfPCell cell32 = new PdfPCell(new Phrase(zhongzhi1.getHardTissue(), FontChinese));
		cell32.setColspan(7);

		// ######################
		table.addCell(cell31);
		table.addCell(cell32);
		// ######################

		PdfPCell cell41 = new PdfPCell(new Phrase("CBCT检查：", FontChinese));
		cell41.setRowspan(3);
		PdfPCell cell42 = new PdfPCell(new Phrase("骨高度（mm）：", FontChinese));
		PdfPCell cell43 = new PdfPCell(new Phrase(zhongzhi1.getBoneHeight(), FontChinese));
		PdfPCell cell44 = new PdfPCell(new Phrase("骨宽度（mm）：", FontChinese));
		PdfPCell cell45 = new PdfPCell(new Phrase(zhongzhi1.getBoneWidth(), FontChinese));
		PdfPCell cell46 = new PdfPCell(new Phrase("骨密度hu）：", FontChinese));
		PdfPCell cell47 = new PdfPCell(new Phrase(zhongzhi1.getBoneDensity(), FontChinese));
		cell47.setColspan(2);

		// ######################
		table.addCell(cell41);
		table.addCell(cell42);
		table.addCell(cell43);
		table.addCell(cell44);
		table.addCell(cell45);
		table.addCell(cell46);
		table.addCell(cell47);
		// ######################

		PdfPCell cell51 = new PdfPCell(new Phrase("距离上颌窦高度 ：", FontChinese));
		cell51.setColspan(2);
		PdfPCell cell52 = new PdfPCell(new Phrase("左（mm）：", FontChinese));
		PdfPCell cell53 = new PdfPCell(new Phrase(zhongzhi1.getMaxillarySinusLeft(), FontChinese));
		PdfPCell cell54 = new PdfPCell(new Phrase("右（mm）：", FontChinese));
		PdfPCell cell55 = new PdfPCell(new Phrase(zhongzhi1.getMaxillarySinusRight(), FontChinese));
		cell55.setColspan(2);

		// ######################
		table.addCell(cell51);
		table.addCell(cell52);
		table.addCell(cell53);
		table.addCell(cell54);
		table.addCell(cell55);
		// ######################

		PdfPCell cell61 = new PdfPCell(new Phrase("距离下牙槽神经管高度：", FontChinese));
		cell61.setColspan(2);
		PdfPCell cell62 = new PdfPCell(new Phrase("左（mm）：", FontChinese));
		PdfPCell cell63 = new PdfPCell(new Phrase(zhongzhi1.getBoneNerveLeft(), FontChinese));
		PdfPCell cell64 = new PdfPCell(new Phrase("右（mm）：", FontChinese));
		PdfPCell cell65 = new PdfPCell(new Phrase(zhongzhi1.getBoneNerveRight(), FontChinese));
		cell65.setColspan(2);

		// ######################
		table.addCell(cell61);
		table.addCell(cell62);
		table.addCell(cell63);
		table.addCell(cell64);
		table.addCell(cell65);
		// ######################

		PdfPCell cell71 = new PdfPCell(new Phrase("全身情况：", FontChinese));
		cell71.setRowspan(6);
		PdfPCell cell72 = new PdfPCell(new Phrase("高血压：", FontChinese));
		PdfPCell cell73 = new PdfPCell(new Phrase(zhongzhi1.getHypertension(), FontChinese));
		PdfPCell cell74 = new PdfPCell(new Phrase(zhongzhi1.getHypertensionYear() + " 年", FontChinese));
		PdfPCell cell75 = new PdfPCell(new Phrase("是否服药?：", FontChinese));
		PdfPCell cell76 = new PdfPCell(new Phrase(zhongzhi1.getHypertensionMedication(), FontChinese));
		PdfPCell cell77 = new PdfPCell(new Phrase("是否控制?：", FontChinese));
		String hypertensionControl = "";
		if ("1".equals(zhongzhi1.getHypertensionControl())) {
			hypertensionControl = "是";
		}
		if ("0".equals(zhongzhi1.getHypertensionControl())) {
			hypertensionControl = "否";
		}
		PdfPCell cell78 = new PdfPCell(new Phrase(hypertensionControl, FontChinese));

		// ######################
		table.addCell(cell71);
		table.addCell(cell72);
		table.addCell(cell73);
		table.addCell(cell74);
		table.addCell(cell75);
		table.addCell(cell76);
		table.addCell(cell77);
		table.addCell(cell78);
		// ######################

		PdfPCell cell81 = new PdfPCell(new Phrase("心脏病：", FontChinese));
		PdfPCell cell82 = new PdfPCell(new Phrase(zhongzhi1.getCardiopathy(), FontChinese));
		PdfPCell cell83 = new PdfPCell(new Phrase(zhongzhi1.getCardiopathyYear() + " 年", FontChinese));

		String cardiopathyMedication = "";
		if ("1".equals(zhongzhi1.getCardiopathyMedication())) {
			cardiopathyMedication = "是";
		}
		if ("0".equals(zhongzhi1.getHypertensionControl())) {
			cardiopathyMedication = "否";
		}
		PdfPCell cell84 = new PdfPCell(new Phrase("是否常备药在身边?： " + cardiopathyMedication, FontChinese));
		cell84.setColspan(4);

		// ######################
		table.addCell(cell81);
		table.addCell(cell82);
		table.addCell(cell83);
		table.addCell(cell84);
		// ######################

		PdfPCell cell91 = new PdfPCell(new Phrase("糖尿病：", FontChinese));
		String diabetesIf = "";
		if ("1".equals(zhongzhi1.getDiabetesIf())) {
			diabetesIf = "有";
		}
		if ("0".equals(zhongzhi1.getDiabetesIf())) {
			diabetesIf = "无";
		}
		PdfPCell cell92 = new PdfPCell(new Phrase(diabetesIf, FontChinese));

		String diabetesHow = "";
		if ("1".equals(zhongzhi1.getDiabetesHow())) {
			diabetesHow = "饮食";
		}
		if ("2".equals(zhongzhi1.getDiabetesHow())) {
			diabetesHow = "口服药";
		}
		if ("3".equals(zhongzhi1.getDiabetesHow())) {
			diabetesHow = "针剂";
		}
		PdfPCell cell93 = new PdfPCell(new Phrase("怎么控制：" + diabetesHow, FontChinese));
		cell93.setColspan(3);

		String diabetesControl = "";
		if ("1".equals(zhongzhi1.getDiabetesControl())) {
			diabetesControl = "是";
		}
		if ("0".equals(zhongzhi1.getDiabetesControl())) {
			diabetesControl = "否";
		}
		PdfPCell cell94 = new PdfPCell(new Phrase("是否控制?：" + diabetesControl, FontChinese));
		cell94.setColspan(2);

		// ######################
		table.addCell(cell91);
		table.addCell(cell92);
		table.addCell(cell93);
		table.addCell(cell94);
		// ######################

		PdfPCell cell10 = new PdfPCell(new Phrase("传染性疾病：" + zhongzhi1.getInfectiousDisease(), FontChinese));
		cell10.setColspan(7);

		PdfPCell cell11 = new PdfPCell(new Phrase("代谢性疾病：" + zhongzhi1.getMetabolicDisease(), FontChinese));
		cell11.setColspan(7);

		PdfPCell cell12 = new PdfPCell(new Phrase("服药情况：" + zhongzhi1.getMedication(), FontChinese));
		cell12.setColspan(7);

		// ######################
		table.addCell(cell10);
		table.addCell(cell11);
		table.addCell(cell12);
		// ######################

		PdfPCell cell13_1 = new PdfPCell(new Phrase("其他：", FontChinese));
		PdfPCell cell13_2 = new PdfPCell(new Phrase(zhongzhi1.getOthers(), FontChinese));
		cell13_2.setColspan(7);

		PdfPCell cell14_1 = new PdfPCell(new Phrase("实验室检查：", FontChinese));
		PdfPCell cell14_2 = new PdfPCell(new Phrase(zhongzhi1.getLabExamination(), FontChinese));
		cell14_2.setColspan(7);

		// ######################
		table.addCell(cell13_1);
		table.addCell(cell13_2);
		table.addCell(cell14_1);
		table.addCell(cell14_2);
		// ######################

		return table;
	}

	/**
	 * 表二
	 * 
	 * @param userinfo
	 * @param meid
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public PdfPTable createTable2Info(KqdsUserdocument userinfo, KqdsMedicalrecordZhongzhi zhongzhi1) throws Exception {
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 1, 1, 1, 1, 1, 1, 1, 1 });
		table.setSpacingBefore(5f);

		PdfPCell cell11 = new PdfPCell(new Phrase("术前诊断：", FontChinese));
		PdfPCell cell12 = new PdfPCell(new Phrase(zhongzhi1.getPreoperativeDiagnosis(), FontChinese));
		cell12.setColspan(7);

		PdfPCell cell21 = new PdfPCell(new Phrase("手术中可能发生的问题及其对策：", FontChinese));
		PdfPCell cell22 = new PdfPCell(new Phrase(zhongzhi1.getPossibleProblems(), FontChinese));
		cell22.setColspan(7);

		PdfPCell cell31 = new PdfPCell(new Phrase("植骨情况：", FontChinese));
		PdfPCell cell32 = new PdfPCell(new Phrase(zhongzhi1.getBoneMembrane(), FontChinese));
		cell32.setColspan(7);

		PdfPCell cell41 = new PdfPCell(new Phrase("手术后应注意问题：", FontChinese));
		PdfPCell cell42 = new PdfPCell(new Phrase(zhongzhi1.getPostoperativeAttentions(), FontChinese));
		cell42.setColspan(7);

		PdfPCell cell51 = new PdfPCell(new Phrase("种植修复相关问题：", FontChinese));
		PdfPCell cell52 = new PdfPCell(new Phrase(zhongzhi1.getIssueImplantationRestoration(), FontChinese));
		cell52.setColspan(7);

		PdfPCell cell61 = new PdfPCell(new Phrase("种植治疗方案：", FontChinese));
		PdfPCell cell62 = new PdfPCell(new Phrase(zhongzhi1.getTreatmentPlan(), FontChinese));
		cell62.setColspan(7);

		PdfPCell cell71 = new PdfPCell(new Phrase("备注 remarks：", FontChinese));
		PdfPCell cell72 = new PdfPCell(new Phrase(zhongzhi1.getTreatmentRemark(), FontChinese));
		cell72.setColspan(7);

		// ######################
		table.addCell(cell11);
		table.addCell(cell12);
		table.addCell(cell21);
		table.addCell(cell22);
		table.addCell(cell31);
		table.addCell(cell32);
		table.addCell(cell41);
		table.addCell(cell42);
		table.addCell(cell51);
		table.addCell(cell52);
		table.addCell(cell61);
		table.addCell(cell62);
		table.addCell(cell71);
		table.addCell(cell72);
		// ######################

		return table;
	}

	/**
	 * 表三
	 * 
	 * @param userinfo
	 * @param meid
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public PdfPTable createTable3Info(KqdsUserdocument userinfo, KqdsMedicalrecordZhongzhi zhongzhi1) throws Exception {
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 1, 1, 1, 1, 1, 1, 1, 1 });
		table.setSpacingBefore(5f);

		PdfPCell cell11 = new PdfPCell(new Phrase("牙龈厚度（mm） ：", FontChinese));
		PdfPCell cell12 = new PdfPCell(new Phrase(zhongzhi1.getGumThickness(), FontChinese));
		cell12.setColspan(7);

		// ######################
		table.addCell(cell11);
		table.addCell(cell12);
		// ######################

		PdfPCell cell21 = new PdfPCell(new Phrase("牙槽嵴顶宽度（mm） ：", FontChinese));
		PdfPCell cell22 = new PdfPCell(new Phrase(zhongzhi1.getAlveolarCrestWidth(), FontChinese));
		cell22.setColspan(7);

		// ######################
		table.addCell(cell21);
		table.addCell(cell22);
		// ######################

		JSONObject json1 = new JSONObject();
		json1.put("name", "种植体植入情况：");
		json1.put("value", zhongzhi1.getDetailsOfImpants());
		List<PdfPCell> detailsOfImpants = getValue(json1);
		addCell2Table(table, detailsOfImpants);

		PdfPCell cell41 = new PdfPCell(new Phrase("条码粘贴：", FontChinese));
		PdfPCell cell42 = new PdfPCell(new Phrase(zhongzhi1.getBarcodeStick(), FontChinese));
		cell42.setColspan(7);

		// ######################
		table.addCell(cell41);
		table.addCell(cell42);
		// ######################

		PdfPCell cell51 = new PdfPCell(new Phrase("术后医嘱：", FontChinese));
		PdfPCell cell52 = new PdfPCell(new Phrase(zhongzhi1.getPostoperativeAdvice(), FontChinese));
		cell52.setColspan(7);

		// ######################
		table.addCell(cell51);
		table.addCell(cell52);
		// ######################

		PdfPCell cell61 = new PdfPCell(new Phrase("备注：", FontChinese));
		PdfPCell cell62 = new PdfPCell(new Phrase(zhongzhi1.getStageRemark(), FontChinese));
		cell62.setColspan(7);

		// ######################
		table.addCell(cell61);
		table.addCell(cell62);
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
