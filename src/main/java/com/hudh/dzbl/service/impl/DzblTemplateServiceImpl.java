package com.hudh.dzbl.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hudh.dzbl.dao.DzblTemplateDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hudh.dzbl.dao.DzblDetailDao;
import com.hudh.dzbl.entity.DzblDetail;
import com.hudh.dzbl.entity.DzblTemplate;
import com.hudh.dzbl.service.IDzblTemplateService;
import com.hudh.lclj.dao.SysParaDao;
import com.hudh.util.HUDHStaticVar;
import com.hudh.util.HUDHUtil;
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
import com.kqds.core.system.validator.YZIsLeaveUserValidator;
import com.kqds.dao.DAO;
import com.kqds.dao.DaoSupport;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPara;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.print.PdfReportM1HeaderFooter;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;
@Service
public class DzblTemplateServiceImpl implements IDzblTemplateService{
	/**
	 * 病例模板
	 */
	@Autowired
	private DzblTemplateDao dzblTemplateDao;
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 系统配置dao
	 */
	@Autowired
	private SysParaDao sysParaDao;
	/**
	 * 病例信息
	 */
	@Autowired
	private DzblDetailDao dzblDetailDao;
	private final static String DZBL_OPT_AUTH = "DZBL_OPT_AUTH"; //电子病历操作权限配置对应sys_para表name值
	
	
	private static BaseFont bfChinese = null;
	private static Font FontChinese = null;
	@Override
	public void insertDzblTemplate(DzblTemplate dzblTemplate) throws Exception {
		// TODO Auto-generated method stub
		dzblTemplate.setId(YZUtility.getUUID());
		dzblTemplate.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		dzblTemplate.setModifytime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		
		//处理detail字段
		String detail = dzblTemplate.getDetail();
		detail = detail.replace(" ", "&nbsp;");//将模板内容中的空格替换成&nbsp;
		detail = detail.replace("{}", "<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>"); //配置模板是需要显示下横线则用{}代替此处进行替换
		dzblTemplate.setDetail(detail);
		
		//查找当前病程下是否存在模板
		JSONObject jo = this.findTemplateByBlflAndBc(dzblTemplate.getBlfl(),dzblTemplate.getBc(),dzblTemplate.getSstype(),dzblTemplate.getOrganization());
		if(null != jo && YZUtility.isNotNullOrEmpty(jo.getString("id"))) {
			dzblTemplate.setId(jo.getString("id"));
			this.updateDzblTempleById(dzblTemplate);
		}else {
			dzblTemplateDao.insertDzblTemplate(dzblTemplate);
		}
		
		
	}

	@Override
	public JSONObject findTemplateByBlflAndBc(String blfl, String bc,String sstype,String organization) throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> dataMap = new HashMap<String, String>();
		dataMap.put("blfl", blfl);
		dataMap.put("bc", bc);
		dataMap.put("sstype", sstype);
		dataMap.put("organization", organization);
		List<JSONObject> list = dzblTemplateDao.findDzblTemplate(dataMap);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<JSONObject> findBcByBlfl(String blfl,String sstype) throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("blfl", blfl);
		dataMap.put("sstype", sstype);
		List<JSONObject> list = dzblTemplateDao.findBcByBlfl(dataMap);
		if(null != list &&list.size() > 0){
			return list;
		}
		return null;
	}
	
	@Override
	public void insertDzblDetail(DzblDetail dzblDetail,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		dzblDetail.setId(YZUtility.getUUID());
		dzblDetail.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		YZPerson person = SessionUtil.getLoginPerson(request);
		String currntUserId = SessionUtil.getLoginPerson(request).getSeqId();
		dzblDetail.setCreator(currntUserId);
		dzblDetailDao.insertDzblDetail(dzblDetail);
	}

	@Override
	public List<JSONObject> findDzblByBlcode(String blCode) throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = new ArrayList<JSONObject>();
		list = dzblDetailDao.findDzblByBlcode(blCode);
		return list;
	}

	@Override
	public void updateDzblById(DzblDetail dzblDetail,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		dzblDetail.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		YZPerson person = SessionUtil.getLoginPerson(request);
		String currntUserId = SessionUtil.getLoginPerson(request).getSeqId();
		dzblDetail.setCreator(currntUserId);
		dzblDetailDao.updateDzblById(dzblDetail);
	}

	@Override
	public String dzblPrint(HttpServletRequest request,String blId,String blCode ,String ssTime) throws Exception {
		// TODO Auto-generated method stub
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
		
		//获取所需数据
		JSONObject userDoc = this.findUserDocByBlCode(blCode); //患者详情
		JSONObject dzbiDetail = this.findDzblById(blId); //病历详情
		
		// 患者基本信息
		table = createUserInfoTable(userDoc,dzbiDetail,ssTime);
		
		document.add(table);

		
		//添加title
		table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 1 });
		PdfPCell cell = new PdfPCell(new Phrase(dzbiDetail.getString("title"), new Font(bfChinese, 15, Font.BOLD)));
		cell.setMinimumHeight(30);
		cell.setBorder(0);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		document.add(table);
		
		//添加主体内容
		table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 1 });
		cell = new PdfPCell(new Phrase(dzbiDetail.getString("detail")
				.replace("&nbsp;", "")
				.replace("<u>", "")
				.replace("</u>", "")
				.replace("<br>", "\n")
				.replace("<br/>", "\n")
				.replace("<div>", "\n")
				.replace("</div>", ""), 
				new Font(bfChinese, 12, Font.NORMAL))); //出来前段编辑后回车新加内容自动嵌套div,textarea与div回车符不一致
		cell.setMinimumHeight(80);
		cell.setBorder(0);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setLeading(20f, 0); //fixedLeading表示行间距的值，multipliedLeading表示 字体大小的倍数
		table.addCell(cell);
		document.add(table);
		
		//添加底部手术医生签名和日期
		table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 50,50});
		PdfPCell cell2 = new PdfPCell(new Phrase("医生签名：", new Font(bfChinese, 12, Font.NORMAL)));
//		PdfPCell cell2 = new PdfPCell(new Phrase("手术医生签名：", new Font(bfChinese, 12, Font.NORMAL)));
		PdfPCell cell3 = new PdfPCell(new Phrase("日期：", new Font(bfChinese, 12, Font.NORMAL)));
		// 内容左对齐
		cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		cell2.setBorder(0);
		cell3.setBorder(0);
		
		cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		cell2.setPaddingTop(20);
		cell3.setPaddingTop(20);
		
		table.addCell(cell2);
		table.addCell(cell3);
		
		document.add(table);
		// 5.关闭文档
		document.close();
		return filePath;
	}

	private PdfPTable createUserInfoTable(JSONObject userDoc,JSONObject dzbiDetail,String ssTime) throws DocumentException {
		// TODO Auto-generated method stub
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 0.65f, 1.2f, 0.65f, 1.2f, 0.65f, 0.7f, 1f, 2f });
		table.setSpacingBefore(5f);

		PdfPCell cell11 = new PdfPCell(new Phrase("姓名：", FontChinese));
		PdfPCell cell12 = new PdfPCell(new Phrase(userDoc.getString("username"), FontChinese));
		PdfPCell cell13 = new PdfPCell(new Phrase("性别：", FontChinese));
		PdfPCell cell14 = new PdfPCell(new Phrase(userDoc.getString("sex"), FontChinese));
		PdfPCell cell15 = new PdfPCell(new Phrase("年龄：", FontChinese));
		PdfPCell cell16 = new PdfPCell(new Phrase( userDoc.getString("age"), FontChinese));
		PdfPCell cell17 = new PdfPCell(new Phrase("日期：", FontChinese));
//		PdfPCell cell17 = new PdfPCell(new Phrase("手术日期：", FontChinese));
		ssTime = YZUtility.isNotNullOrEmpty(ssTime) ? ssTime :(YZUtility.isNotNullOrEmpty(dzbiDetail.getString("createtime"))?
				dzbiDetail.getString("createtime").substring(0, 10):"");
		PdfPCell cell18 = new PdfPCell(new Phrase(ssTime, FontChinese));
		
		// 内容左对齐
		cell12.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell14.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell16.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell18.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		cell11.setBorder(0);
		cell12.setBorder(0);
		cell13.setBorder(0);
		cell14.setBorder(0);
		cell15.setBorder(0);
		cell16.setBorder(0);
		cell17.setBorder(0);
		cell18.setBorder(0);
		
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

	@Override
	public JSONObject findUserDocByBlCode(String blCode) throws Exception {
		// TODO Auto-generated method stub
		JSONObject jo = dzblDetailDao.getBaseUserInfoByUsercode(blCode);
		if(null != jo) {
			return jo;
		}
		return null;
	}

	@Override
	public JSONObject findDzblById(String id) throws Exception {
		// TODO Auto-generated method stub
		JSONObject jo = dzblDetailDao.findDzblById(id);
		if(null != jo) {
			jo.put("detail", jo.getString("detail").replace("&nbsp;", " ")); //将&nbsp;替换成空格
			return jo;
		}
		return null;
	}

	@Override
	public JSONObject findDzblTemplateById(String id) throws Exception {
		// TODO Auto-generated method stub
		JSONObject jo = dzblTemplateDao.findDzblTemplateById(id);
		if(null != jo) {
			return jo;
		}
		return null;
	}

	@Override
	public void updateDzblTempleById(DzblTemplate dzblTemplate) throws Exception {
		// TODO Auto-generated method stub
		dzblTemplateDao.updateDzblTempleById(dzblTemplate);
	}

	@Override
	public String selectParaValueByName(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		String organization = ChainUtil.getCurrentOrganization(request);
		Map<String,Map<String,String>> dataMap = new HashMap<String,Map<String,String>>();
		Map<String,String> tempMap = new HashMap<String,String>();
		tempMap.put("para_name", DZBL_OPT_AUTH);
		tempMap.put("organization", organization);
		dataMap.put("params", tempMap);
		List<YZPara> paraList = sysParaDao.selectParaValueByName(dataMap);
		
		//获取当前登录人员角色
		YZPerson person = SessionUtil.getLoginPerson(request);
		String perPriv = person.getUserPriv();
		for(YZPara yzPara : paraList) { //一般只有一个值
			String[] yzParaArray = yzPara.getParaValue().split(",");
			int length = yzParaArray.length;
			for(int i=0;i<length;i++) {
				if(yzParaArray[i].equals(perPriv)) {
					return "true";
				}
			}
		}
		return "false";
	}

	/**   
	  * <p>Title: findDzbl</p>   
	  * <p>Description: </p>   
	  * @param map
	  * @return
	  * @throws Exception   
	  * @see com.hudh.dzbl.service.IDzblTemplateService#findDzbl(java.util.Map)   
	  */  
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONObject findDzbl(Map<String, String> map, BootStrapPage bp) throws Exception {
		// TODO Auto-generated method stub
		// 分页插件
		PageHelper.offsetPage(bp.getOffset(), bp.getLimit());
		List<JSONObject> list = (List<JSONObject>) dao.findForList(TableNameUtil.HUDH_DZBL_DETAIL + ".findDzbl", map);
		PageInfo<JSONObject> pageInfo = new PageInfo<JSONObject>(list);
		JSONObject jobj = new JSONObject();
		jobj.put("total", pageInfo.getTotal());
		jobj.put("rows", list);
		return jobj;
	}
}
