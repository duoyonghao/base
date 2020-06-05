package com.kqds.util.sys.export;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.UserPrivUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExportTable {

	/** 0、导出bootstrap table to excel **/

	/** 1、导出排班模板 **/

	/** 2、下载商品导入模板 **/

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合
	 * @param response
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation" })
	public static void exportBootStrapTable2Excel(String title, String jsonNameString, String jsonValueString, List<JSONObject> dataset, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		Map<String, String> valueMap = getMapJson(jsonNameString, jsonValueString);
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		// style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		// style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		// style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		int mapvalueindex = 0;
		for (String value : valueMap.values()) {
			HSSFCell cell = row.createCell(mapvalueindex);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(value);
			cell.setCellValue(text);
			mapvalueindex++;
		}
		// 0 不可以导出手机号码 1可以导出
		String canExportPhoneNumber = UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag14_canExportPhoneNumber, request);
		// 遍历集合数据，产生数据行
		Iterator<JSONObject> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			JSONObject t = it.next();
			int mapkeyindex = 0;
			for (String field : valueMap.keySet()) {
				HSSFCell cell = row.createCell(mapkeyindex);
				cell.setCellStyle(style2);
				try {
					// 数据类型都当作字符串简单处理
					String textValue = "";
					if (t.getString(field) != null && !t.getString(field).equals("null") && !field.toLowerCase().contains("phonenumber".toLowerCase())) {// 导出时不显示手机号码
						textValue = t.getString(field).toString();
					} else {
						if ("1".equals(canExportPhoneNumber)) { // admin可以导出手机号码
							textValue = t.getString(field).toString();
						}
					}
					cell.setCellValue(textValue);
				} catch (Exception e) {
					if (!field.toLowerCase().contains("rownumber".toLowerCase())) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(index);
					}
				} finally {
					// 清理资源
				}
				mapkeyindex++;
			}
		}
		// 导出Excel弹出下载框
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			workbook.write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String((title + ".xls").getBytes(), "iso-8859-1"));
			ServletOutputStream out = response.getOutputStream();
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;

			try {
				bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(out);
				byte[] buff = new byte[2048];
				int bytesRead;
				// Simple read/write loop.
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出排班模板
	 * 
	 * @param title
	 * @param jsonNameString
	 * @param jsonValueString
	 * @param dataset
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation" })
	public static void exportExcel4PaiBan(String title, List<JSONObject> perlist, String startdate, String enddate, HttpServletResponse response) throws Exception {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");

		// 产生日期行
		List<String> datelist = YZUtility.getAllDate(startdate, enddate);
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(style);
		HSSFRichTextString text = new HSSFRichTextString("用户\\日期");
		cell.setCellValue(text);
		for (int i = 0; i < datelist.size(); i++) {
			cell = row.createCell(i + 1);
			cell.setCellStyle(style);
			text = new HSSFRichTextString(datelist.get(i));
			cell.setCellValue(text);
		} // 产生日期行 END

		for (int i = 0; i < perlist.size(); i++) {
			row = sheet.createRow(i + 1); // 从第二行开始
			cell = row.createCell(0);
			cell.setCellStyle(style);
			text = new HSSFRichTextString(perlist.get(i).getString("user_id"));
			cell.setCellValue(text);

			for (int j = 0; j < datelist.size(); j++) {
				cell = row.createCell(j + 1);
				cell.setCellStyle(style);
				text = new HSSFRichTextString("值班");
				cell.setCellValue(text);
			}
		}

		// 导出Excel弹出下载框
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			workbook.write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String((title + ".xls").getBytes(), "iso-8859-1"));
			ServletOutputStream out = response.getOutputStream();
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;

			try {
				bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(out);
				byte[] buff = new byte[2048];
				int bytesRead;
				// Simple read/write loop.
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Map<String, String> getMapJson(String jsonNameString, String jsonValueString) throws Exception {
		if (jsonNameString == null) {
			throw new Exception("表头不存在");
		}
		if (jsonValueString == null) {
			throw new Exception("表头不存在");
		}
		JSONArray jsonname = JSONArray.fromObject(jsonNameString);
		JSONArray jsonvalue = JSONArray.fromObject(jsonValueString);
		Map<String, String> valueMap = new LinkedHashMap<String, String>();
		if (jsonname.size() > 0) {
			for (int i = 0; i < jsonname.size(); i++) {
				String key = jsonname.getString(i);
				String value = jsonvalue.getString(i);
				valueMap.put(key, value);
			}
		}
		return valueMap;
	}

	/**
	 * 这个方法针对数据大的情况下使用，避免内存溢出
	 * 
	 * @param title
	 * @param jsonNameString
	 * @param jsonValueString
	 * @param dataset
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	public static ExportBean exportBootStrapTable2ExcelByResult(ExportBean bean, List<JSONObject> result, String methodName) throws Exception {
		Map<String, String> valueMap = bean.getNamevals();
		// 0 不可以导出手机号码 1可以导出
		String canExportPhoneNumber = UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag14_canExportPhoneNumber, bean.getRequest());
		// int rowaccess = 1000;// 内存中缓存记录行数

		// 生成并设置另一个样式
		CellStyle style2 = bean.getWorkbook().createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		Font font2 = bean.getWorkbook().createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		/***********************************************************
		 * ResultSet相关
		 ********************************/
		// 遍历集合数据，产生数据行
		// ResultSet rs = result.getRs();
		// DataTableEntity dataTable = null;
		// 处理ResultSet结构体信息
		/*
		 * if (rs != null) { ResultSetMetaData rsMetaData = rs.getMetaData(); int
		 * columnCount = rsMetaData.getColumnCount(); dataTable = new
		 * DataTableEntity(columnCount); // 获取字段名称，类型 for (int i = 0; i < columnCount;
		 * i++) { String columnName = null;// #### if (DBUtil.isMysql()) { columnName =
		 * rsMetaData.getColumnLabel(i + 1).toLowerCase(); } else { columnName =
		 * rsMetaData.getColumnName(i + 1).toLowerCase(); } int columnType =
		 * rsMetaData.getColumnType(i + 1); dataTable.setColumnName(columnName, i);
		 * dataTable.setColumnType(columnType, i); } }
		 */

		// 会员中心
		List<String> membernoList = new ArrayList<String>();

		Row row = null;
		int index = bean.getIndex();
		for (int i = 0; i < result.size(); i++) {
			index++;
			JSONObject obj = result.get(i);
			/*
			 * for (int i = 0; i < dataTable.getColumnCount(); i++) { String field =
			 * dataTable.getColumnName(i); if ("barcode".equals(field)) { continue; } //
			 * 获取字段值 Object objColumnValue = YZORMSelect.getColumnValByIndex(rs,
			 * dataTable.getColumnType(i), i + 1); // 根据数据库字段类型，获取对应的值，resulSet是从1开始的 //
			 * 解密手机号码 objColumnValue = YZAuthenticator.decryKqdsPhonenumber(field,
			 * objColumnValue);
			 * 
			 * String value = objColumnValue + ""; obj.put(field, value); if
			 * (field.contains("_")) { String field2 = YZStringFormat.unformat(field);
			 * obj.put(field2, value); } }
			 */

			/**
			 * 会员中心
			 */
			if ("member_center".equals(methodName)) {
				String memberstatus = obj.getString("memberstatus");
				if (ConstUtil.MEMBER_STATUS_0.equals(memberstatus)) {
					memberstatus = ConstUtil.MEMBER_STATUS_0_DESC;
				} else if (ConstUtil.MEMBER_STATUS_1.equals(memberstatus)) {
					memberstatus = ConstUtil.MEMBER_STATUS_1_DESC;
				} else if (ConstUtil.MEMBER_STATUS_2.equals(memberstatus)) {
					memberstatus = ConstUtil.MEMBER_STATUS_2_DESC;
				} else if (ConstUtil.MEMBER_STATUS_3.equals(memberstatus)) {
					memberstatus = ConstUtil.MEMBER_STATUS_3_DESC;
				}
				obj.put("memberstatus", memberstatus);
				float money = 0;
				if (!YZUtility.isNullorEmpty(obj.getString("money"))) {
					money = Float.parseFloat(obj.getString("money"));
				}
				float givemoney = 0;
				if (!YZUtility.isNullorEmpty(obj.getString("givemoney"))) {
					givemoney = Float.parseFloat(obj.getString("givemoney"));
				}
				obj.put("totalmoney", givemoney + money);

				// 去除重复记录
				String memberno = obj.getString("memberno");
				if (membernoList == null || membernoList.size() == 0) {
					membernoList.add(memberno);
				} else {
					for (String mno : membernoList) {
						if (memberno.equals(mno)) { // 如果之前出现过的会员卡，就不继续写入excel了
							break;
						}
					}
				}
			}

			/**
			 * 患者信息查询
			 */
			if ("userdoc_selectNoPage".equals(methodName)) {
				String ywhf = obj.getString("ywhf");
				if ("0".equals(ywhf)) {
					ywhf = "无回访";
				} else {
					ywhf = "有回访";
				}
				obj.put("ywhf", ywhf);
				String doorstatus = obj.getString("doorstatus");
				if ("1".equals(doorstatus)) {
					doorstatus = "已上门";
				} else {
					doorstatus = "未上门";
				}
				obj.put("doorstatusname", doorstatus);
			}

			/**
			 * 报表中心，费用查询
			 */
			if ("costOrder_selectNoPage".equals(methodName)) {
				String cjstatus = obj.getString("cjstatus");
				if ("0".equals(cjstatus)) {
					obj.put("cjstatus", "未成交");
				} else {
					obj.put("cjstatus", "已成交");
				}
			}

			/**
			 * 报表中心，费用明细
			 */
			if ("costOrderDetail_selectNoPage".equals(methodName)) {
				obj.put("classname", obj.getString("dict_name"));
				obj.put("nextname", obj.getString("dict_name_2"));
				obj.put("cjstatus", obj.getString("cjstatus").equals("0") ? "未成交" : "已成交");
				obj.put("type", obj.getString("type").equals("0") ? "否" : "是");
				obj.put("istsxm", obj.getString("istsxm").equals("0") ? "否" : "是");
				// 应收
				boolean flag = true;
				if (KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("y2")), new BigDecimal(0)) <= 0 && !YZUtility.isNullorEmpty(obj.getString("qfbh"))
						&& KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("subtotal")), BigDecimal.ZERO) == 0
						&& KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("voidmoney")), BigDecimal.ZERO) == 0) {
					flag = false;
				}
				String ys = "0.00";
				String paymoney = YZUtility.isNullorEmpty(obj.getString("paymoney")) ? "0.00" : obj.getString("paymoney");
				String arrearmoney = YZUtility.isNullorEmpty(obj.getString("arrearmoney")) ? "0.00" : obj.getString("arrearmoney");
				if (flag || obj.getString("istk").equals("1")) {
					ys = new BigDecimal(paymoney).add(new BigDecimal(arrearmoney)).toString();
				}
				obj.put("ys", ys);
				String payother2 = YZUtility.isNullorEmpty(obj.getString("payother2")) ? "0.00" : obj.getString("payother2");
				String paydjq = YZUtility.isNullorEmpty(obj.getString("paydjq")) ? "0.00" : obj.getString("paydjq");
				String payintegral = YZUtility.isNullorEmpty(obj.getString("payintegral")) ? "0.00" : obj.getString("payintegral");
				obj.put("paymoney", new BigDecimal(paymoney).subtract(new BigDecimal(payother2)).subtract(new BigDecimal(paydjq)).subtract(new BigDecimal(payintegral)).toString());
			}

			/**
			 * 报表中心-收款明细
			 */
			if ("selectNoPageOrder".equals(methodName)) { // 报表中心收款明细的两个方法，分别处理
				// 治疗项目一、二级分类
				obj.put("classname", obj.getString("dict_name"));
				obj.put("nextname", obj.getString("dict_name_2"));
				obj.put("cjstatus", obj.getString("cjstatus").equals("0") ? "未成交" : "已成交");
				obj.put("type", obj.getString("type").equals("0") ? "否" : "是");
				obj.put("istsxm", obj.getString("istsxm").equals("0") ? "否" : "是");
				// 应收
				boolean flag = true;
				if (KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("y2")), new BigDecimal(0)) <= 0 && !YZUtility.isNullorEmpty(obj.getString("qfbh"))
						&& KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("subtotal")), BigDecimal.ZERO) == 0
						&& KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("voidmoney")), BigDecimal.ZERO) == 0) {
					flag = false;
				}
				BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("paymoney")) ? "0.00" : obj.getString("paymoney"));
				BigDecimal arrearmoney = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("arrearmoney")) ? "0.00" : obj.getString("arrearmoney"));

				BigDecimal ys = BigDecimal.ZERO;
				if (flag || obj.getString("istk").equals("1")) {
					ys = paymoney.add(arrearmoney);
				}
				obj.put("ys", ys.toString());

				BigDecimal payother2 = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("payother2")) ? "0.00" : obj.getString("payother2"));
				BigDecimal paydjq = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("paydjq")) ? "0.00" : obj.getString("paydjq"));
				BigDecimal payintegral = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("payintegral")) ? "0.00" : obj.getString("payintegral"));
				obj.put("paymoney", paymoney.subtract(payother2).subtract(paydjq).subtract(payintegral));
				String payyjj = YZUtility.isNullorEmpty(obj.getString("payyjj")) ? "0.00" : obj.getString("payyjj");
				obj.put("realmoney", new BigDecimal(obj.getString("paymoney")).subtract(new BigDecimal(payyjj)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				obj.put("cmoney", "0.00");
				obj.put("cgivemoney", "0.00");
				obj.put("ctotal", "0.00");
			}

			/**
			 * 报表中心-收款明细
			 */
			if ("selectListOrder".equals(methodName)) {
				// 治疗项目一、二级分类
				obj.put("classname", "预交金");
				obj.put("nextname", obj.getString("type"));
				obj.put("sftime", obj.getString("createtime"));
				if (obj.has("sfr")) {
					obj.put("sfuser", obj.getString("sfr"));
				} else {
					obj.put("sfuser", "");
					// System.out.println(obj.getString("createtime") +
					// obj.getString("usercode"));
				}
				/*obj.put("regsort", "");
				obj.put("recesort", "");*/
				obj.put("kduser", "");
				obj.put("kdtime", "");
				obj.put("cjstatus", "");
				obj.put("nurse", "");
				obj.put("techperson", "");
				obj.put("remark", "");
				// 患者档案表
				if (obj.has("kfr")) {
					obj.put("developer", obj.getString("kfr"));
				} else {
					obj.put("developer", "");
					// System.out.println(obj.getString("createtime") +
					// obj.getString("usercode"));
				}
				if (obj.has("jsr")) {
					obj.put("introducer", obj.getString("jsr"));
				} else {
					obj.put("introducer", "");
					// System.out.println(obj.getString("createtime") +
					// obj.getString("usercode"));
				}
				obj.put("type", "");
				if (obj.has("content")) {
					obj.put("remark", obj.getString("content"));
				} else {
					obj.put("remark", "");
					// System.out.println(obj.getString("createtime") +
					// obj.getString("usercode"));
				}
				// 消费明细表
				obj.put("seqIdMember", obj.getString("seq_id"));
				obj.put("costno", "");
				obj.put("itemname", "");
				obj.put("itemno", "");
				obj.put("unit", "");
				obj.put("unitprice", "");
				obj.put("detailremark", "");
				obj.put("num", "");
				obj.put("discount", "");
				obj.put("realmoney", YZUtility.isNullorEmpty(obj.getString("cmoney")) ? "0.00" : new BigDecimal(obj.getString("cmoney")));
				obj.put("cmoney", YZUtility.isNullorEmpty(obj.getString("cmoney")) ? "0.00" : new BigDecimal(obj.getString("cmoney")));
				obj.put("cgivemoney", YZUtility.isNullorEmpty(obj.getString("cgivemoney")) ? "0.00" : new BigDecimal(obj.getString("cgivemoney")));
				obj.put("ctotal", YZUtility.isNullorEmpty(obj.getString("ctotal")) ? "0.00" : new BigDecimal(obj.getString("ctotal")));
				obj.put("subtotal", "");
				obj.put("arrearmoney", "");
				obj.put("ys", "");
				obj.put("paymoney", "");
				obj.put("voidmoney", "");
				/*obj.put("doctor", "");*/
				obj.put("status", "");
				obj.put("regno", "");
				obj.put("paydjq", "");
				obj.put("payintegral", "");
				if (obj.has("xjmoney")) {
					obj.put("payxj", YZUtility.isNullorEmpty(obj.getString("xjmoney")) ? "0.00" : new BigDecimal(obj.getString("xjmoney")));
				} else {
					obj.put("payxj", "");
					// System.out.println(obj.getString("createtime") +
					// obj.getString("usercode"));
				}
				if (obj.has("yhkmoney")) {
					obj.put("paybank", YZUtility.isNullorEmpty(obj.getString("yhkmoney")) ? "0.00" : new BigDecimal(obj.getString("yhkmoney")));
				} else {
					obj.put("paybank", "");
					// System.out.println(obj.getString("createtime") +
					// obj.getString("usercode"));
				}
				if (obj.has("wxmoney")) {
					obj.put("paywx", YZUtility.isNullorEmpty(obj.getString("wxmoney")) ? "0.00" : new BigDecimal(obj.getString("wxmoney")));
				} else {
					obj.put("paywx", "");
					// System.out.println(obj.getString("createtime") +
					// obj.getString("usercode"));
				}
				if (obj.has("zfbmoney")) {
					obj.put("payzfb", YZUtility.isNullorEmpty(obj.getString("zfbmoney")) ? "0.00" : new BigDecimal(obj.getString("zfbmoney")));
				} else {
					obj.put("payzfb", "");
					// System.out.println(obj.getString("createtime") +
					// obj.getString("usercode"));
				}
				if (obj.has("mmdmoney")) {
					obj.put("paymmd", YZUtility.isNullorEmpty(obj.getString("mmdmoney")) ? "0.00" : new BigDecimal(obj.getString("mmdmoney")));
				} else {
					obj.put("paymmd", "");
					// System.out.println(obj.getString("createtime") +
					// obj.getString("usercode"));
				}
				if (obj.has("bdfqmoney")) {
					obj.put("paybdfq", YZUtility.isNullorEmpty(obj.getString("bdfqmoney")) ? "0.00" : new BigDecimal(obj.getString("bdfqmoney")));
				} else {
					obj.put("paybdfq", "");
					// System.out.println(obj.getString("createtime") +
					// obj.getString("usercode"));
				}
				if (obj.has("qtmoney")) {
					obj.put("payother1", YZUtility.isNullorEmpty(obj.getString("qtmoney")) ? "0.00" : new BigDecimal(obj.getString("qtmoney")));
				} else {
					obj.put("payother1", "");
					// System.out.println(obj.getString("createtime") +
					// obj.getString("usercode"));
				}
				obj.put("payyjj", "");
				obj.put("payyb", "");
				obj.put("payother2", "");
				obj.put("payother3", "");
				obj.put("istsxm", "");
				obj.put("y2", "");
				obj.put("istk", "");
				obj.put("issplit", "");
			}

			/***********************************************************
			 * Excel生成相关
			 ********************************/
			row = bean.getSheet().createRow(index);
			int mapkeyindex = 0;
			for (String field : valueMap.keySet()) {
				Cell cell = row.createCell(mapkeyindex);
				cell.setCellStyle(style2);
				try {
					// 数据类型都当作字符串简单处理
					String textValue = "";
					if (obj.getString(field) != null && !obj.getString(field).equals("null") && !field.toLowerCase().contains("phonenumber".toLowerCase())) {// 导出时不显示手机号码
						textValue = obj.getString(field).toString();
					} else {
						if ("1".equals(canExportPhoneNumber)) { // admin可以导出手机号码
							textValue = obj.getString(field).toString();
						}
					}
					cell.setCellValue(textValue);
				} catch (Exception e) {
					if (!field.toLowerCase().contains("rownumber".toLowerCase())) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(index);
					}
				}
				mapkeyindex++;

				// 每当行数达到设置的值就刷新数据到硬盘,以清理内存
				/*
				 * if (index % rowaccess == 0) { ((SXSSFSheet) bean.getSheet()).flushRows(); }
				 */
			}
			/***********************************************************
			 * Excel生成相关 END
			 ********************************/
		}

		bean.setIndex(index);

		return bean;

		/***********************************************************
		 * ResultSet相关 END
		 ********************************/

	}

	public static ExportBean initExcel4RsWrite(String title, String jsonNameString, String jsonValueString, HttpServletResponse response, HttpServletRequest request)
			throws Exception {

		Map<String, String> valueMap = getMapJson(jsonNameString, jsonValueString);
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		// 生成一个表格
		Sheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		CellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		Font font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		// 声明一个画图的顶级管理器 #### 注释后面加
		// Drawing patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		// HSSFComment comment = patriarch.createCellComment(new ClientAnchor(0,
		// 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		// comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		// comment.setAuthor("leno");

		// 产生表格标题行
		Row row = sheet.createRow(0);
		int mapvalueindex = 0;
		for (String value : valueMap.values()) {
			Cell cell = row.createCell(mapvalueindex);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(value);
			cell.setCellValue(text);
			mapvalueindex++;
		}

		ExportBean bean = new ExportBean();
		bean.setNames(jsonNameString);
		bean.setRequest(request);
		bean.setResponse(response);
		bean.setSheet(sheet);
		bean.setValues(jsonValueString);
		bean.setWorkbook(workbook);
		bean.setNamevals(valueMap);

		return bean;
	}

	public static void writeExcel4DownLoad(String title, SXSSFWorkbook workbook, HttpServletResponse response) {
		// 导出Excel弹出下载框
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			workbook.write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String((title + ".xlsx").getBytes(), "iso-8859-1"));
			ServletOutputStream out = response.getOutputStream();
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;

			try {
				bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(out);
				byte[] buff = new byte[2048];
				int bytesRead;
				// Simple read/write loop.
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
