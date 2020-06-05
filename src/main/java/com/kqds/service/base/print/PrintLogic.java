package com.kqds.service.base.print;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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

import net.sf.json.JSONObject;

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

	/**
	 * 设置cell宽度
	 * 
	 * @param table
	 * @param list
	 */
	private void setCellBorder(List<PdfPCell> list, int columnNum) {
		/*
		 * int i = 0;
		 * 
		 * for (PdfPCell pdfPCell : list) {
		 * 
		 * // pdfPCell.disableBorderSide(2); // 隐藏下右边框 // pdfPCell.disableBorderSide(8);
		 * 
		 * if (list.size() - (i + 1) < columnNum) { // 最后一行 //
		 * pdfPCell.setBorderWidthBottom(0.2f); }
		 * 
		 * if (i % columnNum == 1) { // pdfPCell.setBorderWidthRight(0.2f); }
		 * 
		 * i++; }
		 */
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
	 * 病历初诊打印
	 * 
	 * @param seqId
	 * @param conn
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String bingLiPdf4Print(String seqId, HttpServletRequest request) throws Exception {
		bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		FontChinese = new Font(bfChinese, 12, Font.NORMAL);

		String path = request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator + "print";
		File pathObj = new File(path);

		if (!pathObj.exists()) {
			pathObj.mkdirs();
		}

		String filePath = path + File.separator + YZUtility.getUUID() + ".pdf";
		// 1.新建document对象
		Document document = new Document(PageSize.A4);
		/**
		 * 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。 创建 PdfWriter 对象
		 * 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
		 */
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

		// 设置页眉页脚
		PdfReportM1HeaderFooter headerFooter = new PdfReportM1HeaderFooter();// 就是上面那个类
		writer.setPageEvent(headerFooter);

		// 3.打开文档
		document.open();

		PdfPTable table = null;

		KqdsMedicalrecord medical = (KqdsMedicalrecord) dao.loadObjSingleUUID(TableNameUtil.KQDS_MEDICALRECORD, seqId);
		if (medical == null) {
			throw new Exception("病历不存在，seqId：" + seqId);
		}
		String usercode = medical.getUsercode();
		KqdsUserdocument userinfo = userLogic.getSingleUserByUsercode(usercode);
		if (userinfo == null) {
			throw new Exception("患者不存在，usercode：" + usercode);
		}
		YZDept org = ChainUtil.getOrganizationByOrgCode(medical.getOrganization(), request);

		String headerName = org.getPrintName(); // 打印抬头
		String meid = medical.getSeqId(); // 病历号
		int mtype = medical.getMtype(); // 0 初诊 1 复诊
		if (0 == mtype) {
			headerName += "新诊病历";
			KqdsMedicalrecordCz czInfo = medicalLogic.getCZBingLiByMeid(meid);
			if (czInfo == null) {
				throw new Exception("初诊病历不存在，病历号：" + meid);
			}
			// 抬头表格
			// PdfPTable table = getHeaderTable(headerName);
			// document.add(table);
			headerFooter.setHeader(headerName);

			// 患者基本信息
			table = createUserInfoTable(userinfo, medical);
			document.add(table);
			// 病历内容
			table = getCzContent(czInfo, medical.getUsertype());
			document.add(table);
		} else {
			headerName += "复诊病历";
			KqdsMedicalrecordFz fzInfo = medicalLogic.getFZBingLiByMeid(meid);
			if (fzInfo == null) {
				throw new Exception("复诊病历不存在，病历号：" + meid);
			}
			// 抬头表格
			// table = getHeaderTable(headerName);
			// document.add(table);
			headerFooter.setHeader(headerName);
			// 患者基本信息
			table = createUserInfoTable(userinfo, medical);
			document.add(table);
			// 病历内容
			table = getFzContent(fzInfo, medical.getUsertype());
			document.add(table);
		}

		table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 1 });
		// 1、Paragraph 和 Phrase 区别
		PdfPCell cell = new PdfPCell(new Phrase("医生：", new Font(bfChinese, 12, Font.NORMAL)));
		cell.setMinimumHeight(30);
		cell.setBorder(0);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setPaddingRight(100);
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
	private PdfPTable createUserInfoTable(KqdsUserdocument userinfo, KqdsMedicalrecord medical) throws Exception {
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 0.65f, 1.2f, 0.65f, 1.2f, 0.65f, 0.7f, 1f, 2f });
		table.setSpacingBefore(5f);

		PdfPCell cell11 = new PdfPCell(new Phrase("姓名：", FontChinese));
		PdfPCell cell12 = new PdfPCell(new Phrase(userinfo.getUsername(), FontChinese));
		PdfPCell cell13 = new PdfPCell(new Phrase("性别：", FontChinese));
		PdfPCell cell14 = new PdfPCell(new Phrase(userinfo.getSex(), FontChinese));
		PdfPCell cell15 = new PdfPCell(new Phrase("年龄：", FontChinese));
		PdfPCell cell16 = new PdfPCell(new Phrase(userinfo.getAge() + "", FontChinese));
		PdfPCell cell17 = new PdfPCell(new Phrase("患者编号：", FontChinese));
		PdfPCell cell18 = new PdfPCell(new Phrase(userinfo.getUsercode(), FontChinese));

		String doctor = medical.getDoctor();
		String doctorName = "";
		if (!YZUtility.isNullorEmpty(doctor)) {
			doctorName = personLogic.getNameStrBySeqIds(doctor);
		}

		String nurse = medical.getNurse();
		String nurseName = "";
		if (!YZUtility.isNullorEmpty(nurse)) {
			nurseName = personLogic.getNameStrBySeqIds(nurse);
		}

		PdfPCell cell21 = new PdfPCell(new Phrase("医生：", FontChinese));
		PdfPCell cell22 = new PdfPCell(new Phrase(doctorName, FontChinese));
		PdfPCell cell23 = new PdfPCell(new Phrase("护士：", FontChinese));
		PdfPCell cell24 = new PdfPCell(new Phrase(nurseName, FontChinese));
		PdfPCell cell25 = new PdfPCell(new Phrase("", FontChinese));
		PdfPCell cell26 = new PdfPCell(new Phrase("", FontChinese));
		PdfPCell cell27 = new PdfPCell(new Phrase("填写时间：", FontChinese));
		PdfPCell cell28 = new PdfPCell(new Phrase(medical.getCreatetime(), FontChinese));

		// 内容左对齐
		cell12.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell14.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell16.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell18.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell22.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell24.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell26.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell28.setHorizontalAlignment(Element.ALIGN_LEFT);

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

	/*******************************************
	 * 初诊
	 *******************************************/

	/**
	 * 获取初诊病历内容
	 * 
	 * @param czInfo
	 * @return
	 * @throws DocumentException
	 */
	private PdfPTable getCzContent(KqdsMedicalrecordCz czInfo, int usertype) throws DocumentException {

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 1.2f, 0.5f, 2, 2, 5 });
		table.setSpacingBefore(10f);

		List<PdfPCell> cellList = new ArrayList<PdfPCell>();
		List<PdfPCell> listt = getCzTop(czInfo);
		List<PdfPCell> listm = getCzMid(czInfo, usertype);
		List<PdfPCell> listb = getCzBottom(czInfo);

		cellList.addAll(listt);
		cellList.addAll(listm);
		cellList.addAll(listb);

		addCell2Table(table, cellList);

		return table;
	}

	/**
	 * 获取初诊病历内容上部分
	 * 
	 * @param cellList
	 * @return
	 * @throws DocumentException
	 */
	private List<PdfPCell> getCzTop(KqdsMedicalrecordCz czInfo) throws DocumentException {

		List<PdfPCell> cellList = new ArrayList<PdfPCell>();

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

		// 合并单元格
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

		// 设置表格边框
		setCellBorder(cellList, 2);

		return cellList;
	}

	/**
	 * 获取初诊病历中部内容
	 * 
	 * @param czInfo
	 * @param usertype
	 *            0 成人 1儿童
	 * @return
	 * @throws DocumentException
	 */
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
		List<JSONObject> list = new ArrayList<JSONObject>();

		list.add(json2);
		list.add(json1);
		list.add(json3);
		list.add(json4);
		list.add(json5);

		return getContent(list, usertype);
	}

	/**
	 * 获取牙位
	 * 
	 * @param ywarr
	 * @param usertype
	 * @param num
	 * @return
	 */
	public String getYaWei(String ywarr, int usertype) {
		String crup1 = "";
		String[] crup1Arr = ywarr.split(",");
		for (int k = 0; k < crup1Arr.length; k++) {
			if (usertype == 0) {
				crup1 += crup1Arr[k] + ",";
			} else {
				crup1 += 4 + crup1Arr[k] + ",";
			}
		}
		if (crup1.indexOf(",") > 0) {
			crup1 = crup1.substring(0, crup1.length() - 1);
		}
		return crup1;
	}

	/**
	 * 获取病历底部内容
	 * 
	 * @param czInfo
	 * @return
	 * @throws DocumentException
	 */
	private List<PdfPCell> getCzBottom(KqdsMedicalrecordCz czInfo) throws DocumentException {

		List<PdfPCell> cellList = new ArrayList<PdfPCell>();

		PdfPCell cell11 = new PdfPCell(new Phrase("医嘱", FontChinese));
		PdfPCell cell12 = new PdfPCell(new Phrase(czInfo.getCzyz(), FontChinese));

		PdfPCell cell21 = new PdfPCell(new Phrase("备注", FontChinese));
		PdfPCell cell22 = new PdfPCell(new Phrase(czInfo.getCzremark(), FontChinese));

		cellList.add(cell11);
		cellList.add(cell12);
		cellList.add(cell21);
		cellList.add(cell22);
		// 合并单元格
		cell12.setColspan(4);
		cell22.setColspan(4);

		// 设置表格边框
		// 特殊处理
		setCellBorder(cellList, 2);
		// cell12.setBorderWidthTop(0);

		return cellList;
	}

	/*******************************************
	 * 复诊
	 *******************************************/

	/**
	 * 获取复诊病历内容
	 * 
	 * @param fzInfo
	 * @return
	 * @throws DocumentException
	 */
	private PdfPTable getFzContent(KqdsMedicalrecordFz fzInfo, int usertype) throws DocumentException {

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 1.2f, 0.5f, 2, 2, 5 });
		table.setSpacingBefore(10f);

		List<PdfPCell> cellList = new ArrayList<PdfPCell>();
		List<PdfPCell> listt = getFzTop(fzInfo);
		List<PdfPCell> listm = getFzMid(fzInfo, usertype);
		List<PdfPCell> listb = getFzBottom(fzInfo);

		cellList.addAll(listt);
		cellList.addAll(listm);
		cellList.addAll(listb);

		addCell2Table(table, cellList);

		return table;
	}

	/**
	 * 获取复诊病历内容上部分
	 * 
	 * @param cellList
	 * @return
	 * @throws DocumentException
	 */
	private List<PdfPCell> getFzTop(KqdsMedicalrecordFz fzInfo) throws DocumentException {

		List<PdfPCell> cellList = new ArrayList<PdfPCell>();

		PdfPCell cell11 = new PdfPCell(new Phrase("主诉", FontChinese));
		PdfPCell cell12 = new PdfPCell(new Phrase(fzInfo.getFzzs(), FontChinese));

		/*
		 * PdfPCell cell21 = new PdfPCell(new Phrase("检验结果", FontChinese)); PdfPCell
		 * cell22 = new PdfPCell(new Phrase(fzInfo.getFzjyjg(), FontChinese));
		 */

		// 合并单元格
		cell12.setColspan(4);
		// cell22.setColspan(4);

		cellList.add(cell11);
		cellList.add(cell12);
		// cellList.add(cell21);
		// cellList.add(cell22);

		// 设置表格边框
		setCellBorder(cellList, 2);

		return cellList;
	}

	/**
	 * 获取复诊病历中部内容
	 * 
	 * @param fzInfo
	 * @param usertype
	 *            0 成人 1儿童
	 * @return
	 * @throws DocumentException
	 */
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
		List<JSONObject> list = new ArrayList<JSONObject>();
		list.add(json1);
		list.add(json2);
		list.add(json4);
		list.add(json3);
		return getContent(list, usertype);
	}

	/**
	 * 获取病历底部内容
	 * 
	 * @param fzInfo
	 * @return
	 * @throws DocumentException
	 */
	private List<PdfPCell> getFzBottom(KqdsMedicalrecordFz fzInfo) throws DocumentException {

		List<PdfPCell> cellList = new ArrayList<PdfPCell>();

		PdfPCell cell11 = new PdfPCell(new Phrase("医嘱", FontChinese));
		PdfPCell cell12 = new PdfPCell(new Phrase(fzInfo.getFzyz(), FontChinese));

		PdfPCell cell21 = new PdfPCell(new Phrase("备注", FontChinese));
		PdfPCell cell22 = new PdfPCell(new Phrase(fzInfo.getFzremark(), FontChinese));

		cellList.add(cell11);
		cellList.add(cell12);
		cellList.add(cell21);
		cellList.add(cell22);
		// 合并单元格
		cell12.setColspan(4);
		cell22.setColspan(4);

		// 设置表格边框
		// 特殊处理
		setCellBorder(cellList, 2);
		// cell12.setBorderWidthTop(0);

		return cellList;
	}

	/*************************************
	 * 通用
	 ******************************************************/

	public List<PdfPCell> getContent(List<JSONObject> list, int usertype) {

		List<PdfPCell> cellList = new ArrayList<PdfPCell>();

		for (int j = 0; j < list.size(); j++) {
			JSONObject job = list.get(j);
			String name = job.getString("name");
			String value = job.getString("value");

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
						crup1 = getYaWei(ywarr[0], usertype);
					}
					if (!"".equals(ywarr[1])) {
						crup2 = getYaWei(ywarr[1], usertype);
					}
					if (!"".equals(ywarr[2])) {
						crdown1 = getYaWei(ywarr[2], usertype);
					}
					if (!"".equals(ywarr[3])) {
						crdown2 = getYaWei(ywarr[3], usertype);
					}
					/***************************** 赋值 **********************************/
					if (k == 0) { // 第一行
						PdfPCell cell11 = new PdfPCell(new Phrase(name, FontChinese));
						cell11.setRowspan(cell1Num * 2);
						cellList.add(cell11);
					}

					PdfPCell cell12 = new PdfPCell(new Phrase((k + 1) + "、", FontChinese));
					cell12.setRowspan(2);

					PdfPCell cell13 = new PdfPCell(new Phrase(crup1, FontChinese));
					PdfPCell cell14 = new PdfPCell(new Phrase(crup2, FontChinese));
					PdfPCell cell15 = new PdfPCell(new Phrase(content, FontChinese));
					cell15.setRowspan(2);

					PdfPCell cell23 = new PdfPCell(new Phrase(crdown1, FontChinese));
					PdfPCell cell24 = new PdfPCell(new Phrase(crdown2, FontChinese));

					cell13.setMinimumHeight(20);
					cell14.setMinimumHeight(20);
					cell23.setMinimumHeight(20);
					cell24.setMinimumHeight(20);

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
