package com.kqds.service.base.print;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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

/**
 * 种植一期病历
 * 
 * @author Administrator
 * 
 */
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
	private PrintZhongZhi1Logic zhongzhi1Logic;// 种植一期
	@Autowired
	private PrintZhongZhi2Logic zhongzhi2Logic;// 种植一期
	@Autowired
	private PrintReviewLogic reviewLogic; // 复查病历
	@Autowired
	private PrintRestLogic restLogic; // 修复病历

	public PrintZhongZhiLogic() {
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private KqdsMedicalrecordZhongzhi getZhongzhi1Obj(String meid) throws Exception {

		Map map = new HashMap();
		map.put("meid", meid); // 病历号
		List<KqdsMedicalrecordZhongzhi> list = (List<KqdsMedicalrecordZhongzhi>) dao.loadList(TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI, map);

		if (list != null && list.size() > 1) { // 确保只能查出来一条记录
			throw new Exception("数据异常，一个编号对应多个初诊记录");
		}
		return list.get(0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private KqdsMedicalrecordReview getReviewObj(String meid) throws Exception {

		Map map = new HashMap();
		map.put("meid", meid); // 病历号
		List<KqdsMedicalrecordReview> list = (List<KqdsMedicalrecordReview>) dao.loadList(TableNameUtil.KQDS_MEDICALRECORD_REVIEW, map);

		if (list != null && list.size() > 1) { // 确保只能查出来一条记录
			throw new Exception("数据异常，一个编号对应多个初诊记录");
		}
		return list.get(0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private KqdsMedicalrecordZhongzhi2 getZhongzhi2Obj(String meid) throws Exception {

		Map map = new HashMap();
		map.put("meid", meid); // 病历号
		List<KqdsMedicalrecordZhongzhi2> list = (List<KqdsMedicalrecordZhongzhi2>) dao.loadList(TableNameUtil.KQDS_MEDICALRECORD_ZHONGZHI2, map);

		if (list != null && list.size() > 1) { // 确保只能查出来一条记录
			throw new Exception("数据异常，一个编号对应多个初诊记录");
		}
		return list.get(0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private KqdsMedicalrecordRestoration getRestObj(String meid) throws Exception {

		Map map = new HashMap();
		map.put("meid", meid); // 病历号
		List<KqdsMedicalrecordRestoration> list = (List<KqdsMedicalrecordRestoration>) dao.loadList(TableNameUtil.KQDS_MEDICALRECORD_RESTORATION, map);

		if (list != null && list.size() > 1) { // 确保只能查出来一条记录
			throw new Exception("数据异常，一个编号对应多个初诊记录");
		}
		return list.get(0);
	}

	public String print(String seqId, HttpServletRequest request) throws Exception {
		bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		FontChinese = new Font(bfChinese, 12, Font.NORMAL);

		String headerName = ChainUtil.getOrgName(request); // 打印抬头
		String path = request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator + "print";
		File pathObj = new File(path);
		if (!pathObj.exists()) {
			pathObj.mkdirs();
		}
		String filePath = path + File.separator + YZUtility.getUUID() + ".pdf";

		Document document = new Document();
		document.setPageSize(new RectangleReadOnly(595.0F, 421.0F)); // 纵向
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

		// 设置页眉页脚
		PdfReportM1HeaderFooter headerFooter = new PdfReportM1HeaderFooter();// 就是上面那个类
		headerFooter.setHeader(headerName);
		writer.setPageEvent(headerFooter);

		// 打开文档
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

		String meid = medical.getSeqId(); // 病历号
		int mtype = medical.getMtype(); // 0 初诊 1 复诊

		if (2 == mtype) {
			headerName += "种植一期病历";

			KqdsMedicalrecordZhongzhi zhongzhi1 = getZhongzhi1Obj(meid);
			if (zhongzhi1 == null) {
				throw new Exception("种植一期病历不存在，病历号：" + meid);
			}
			headerFooter.setHeader(headerName);

			// 患者基本信息
			table = zhongzhi1Logic.createUserInfoTable(userinfo, zhongzhi1);
			document.add(table);
			// 表一
			table = zhongzhi1Logic.createTable1Info(userinfo, zhongzhi1);
			document.add(table);

			// 表二
			table = zhongzhi1Logic.createTable2Info(userinfo, zhongzhi1);
			document.add(table);

			// 表三
			table = zhongzhi1Logic.createTable3Info(userinfo, zhongzhi1);
			document.add(table);

			// 病历内容
			// table = getCzContent(czInfo, medical.getUsertype());
			// document.add(table);
		}

		if (3 == mtype) {
			headerName += "种植复查病历";
			KqdsMedicalrecordReview review = getReviewObj(meid);
			if (review == null) {
				throw new Exception("种植复查病历不存在，病历号：" + meid);
			}
			headerFooter.setHeader(headerName);

			// 表一
			table = reviewLogic.createTable1Info(userinfo, review);
			document.add(table);
		}

		if (4 == mtype) {
			headerName += "种植二期病历";
			KqdsMedicalrecordZhongzhi2 zhongzhi2 = getZhongzhi2Obj(meid);
			if (zhongzhi2 == null) {
				throw new Exception("种植二期病历不存在，病历号：" + meid);
			}
			headerFooter.setHeader(headerName);

			table = zhongzhi2Logic.createTable1Info(userinfo, zhongzhi2);
			document.add(table);
		}

		if (5 == mtype) {
			headerName += "种植修复病历";
			KqdsMedicalrecordRestoration rest = getRestObj(meid);
			if (rest == null) {
				throw new Exception("种植修复病历不存在，病历号：" + meid);
			}
			headerFooter.setHeader(headerName);

			table = restLogic.createTable1Info(userinfo, rest);
			document.add(table);
		}

		table = new PdfPTable(8);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 1, 1, 1, 1, 1, 1, 1, 1 });

		String doctorName = "";
		if (!YZUtility.isNullorEmpty(medical.getDoctor())) {
			YZPerson p = personLogic.getPersonBySeqId(medical.getDoctor());
			if (p != null) {
				doctorName = p.getUserName();
			}
		}
		String assistantName = "";
		if (!YZUtility.isNullorEmpty(medical.getAssistant())) {
			YZPerson p = personLogic.getPersonBySeqId(medical.getAssistant());
			if (p != null) {
				assistantName = p.getUserName();
			}
		}
		String nurseName = "";
		if (!YZUtility.isNullorEmpty(medical.getNurse())) {
			YZPerson p = personLogic.getPersonBySeqId(medical.getNurse());
			if (p != null) {
				nurseName = p.getUserName();
			}
		}

		PdfPCell cell11 = new PdfPCell(new Phrase("", FontChinese));
		cell11.setColspan(2);

		PdfPCell cell13 = new PdfPCell(new Phrase("医生：", FontChinese));
		PdfPCell cell14 = new PdfPCell(new Phrase(doctorName, FontChinese));

		PdfPCell cell15 = new PdfPCell(new Phrase("助理：", FontChinese));
		PdfPCell cell16 = new PdfPCell(new Phrase(assistantName, FontChinese));

		PdfPCell cell17 = new PdfPCell(new Phrase("护士：", FontChinese));
		PdfPCell cell18 = new PdfPCell(new Phrase(nurseName, FontChinese));

		cell11.setMinimumHeight(30);
		// cell.setBorder(0);
		// cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		// cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		// cell.setPaddingRight(100);
		table.addCell(cell11);
		table.addCell(cell13);
		table.addCell(cell14);
		table.addCell(cell15);
		table.addCell(cell16);
		table.addCell(cell17);
		table.addCell(cell18);

		document.add(table);

		// 5.关闭文档
		document.close();
		return filePath;

	}

}
