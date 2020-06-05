package com.hudh.dzbl.controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hudh.dzbl.entity.DzblTemplate;
import com.hudh.dzbl.service.IDzblTemplateInitService;
import com.kqds.util.sys.YZUtility;

@Controller
@RequestMapping("HUDH_DzblTemplateInitAct")
public class HUDH_DzblTemplateInitAct {
	
	private Logger logger = LoggerFactory.getLogger(HUDH_DzblTemplateInitAct.class);
	
	@Autowired
	private IDzblTemplateInitService initService;
	
	private static FormulaEvaluator evaluator;
	
	@RequestMapping(value = "/initDzblTemplate.act")
	public String initDzblTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//excel文件路径
//      String excelPath = "D:\\数据（lclj）excel\\西直门咨询记录.xls";
        String excelPath = "D:\\线上数据库还原\\西院电子病历导入.xlsx";
		try {
			// 打开指定位置的Excel文件
			FileInputStream file = new FileInputStream(new File(excelPath));
			Workbook workbook = new XSSFWorkbook(file);//.xlsx
			// 打开Excel中的第一个Sheet
			Sheet sheet = workbook.getSheetAt(0);
			int row = sheet.getLastRowNum() + 1;
			System.out.println("有效行数" + row);
			List<DzblTemplate> templateList = new ArrayList<DzblTemplate>();
			DzblTemplate dzTemplate = null;
			for(int i = 1; i < row; i++) {
				System.err.println(i);
				dzTemplate = new DzblTemplate();
				if(null != sheet.getRow(i).getCell(0)) {
					sheet.getRow(i).getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					dzTemplate.setId(sheet.getRow(i).getCell(0).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(1)) {
					sheet.getRow(i).getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					dzTemplate.setTitle(sheet.getRow(i).getCell(1).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(2)) {
					sheet.getRow(i).getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					dzTemplate.setBlfl(sheet.getRow(i).getCell(2).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(3)) {
					sheet.getRow(i).getCell(3).setCellType(Cell.CELL_TYPE_STRING);
					dzTemplate.setBc(sheet.getRow(i).getCell(3).getStringCellValue());
				}
//				if(null != sheet.getRow(i).getCell(4)) {
//					sheet.getRow(i).getCell(4).setCellType(Cell.CELL_TYPE_STRING);
//					Calendar c = new GregorianCalendar(1900, 0, -1);
//					Date d = c.getTime();
//					String dateTime = sheet.getRow(i).getCell(4).getStringCellValue();
//					Integer time = Integer.valueOf(dateTime);
//					Date date = DateUtils.addDays(d, time); //42605是距离1900年1月1日的天数
//					SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
//			        //java.util.Date对象
//			        Date dateChina = sdf.parse(date.toString());
//			        String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(dateChina);
//			        dzTemplate.setCreatetime(formatStr);
//				}
				dzTemplate.setCreatetime(YZUtility.getCurDateTimeStr());
				dzTemplate.setModifytime(YZUtility.getCurDateTimeStr());
//				if(null != sheet.getRow(i).getCell(5)) {
//					sheet.getRow(i).getCell(5).setCellType(Cell.CELL_TYPE_STRING);
//					Calendar c = new GregorianCalendar(1900, 0, -1);
//					Date d = c.getTime();
//					String dateTime = sheet.getRow(i).getCell(5).getStringCellValue();
//					Integer time = Integer.valueOf(dateTime);
//					Date date = DateUtils.addDays(d, time); //42605是距离1900年1月1日的天数
//					SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
//					//java.util.Date对象
//					Date dateChina = sdf.parse(date.toString());
//					String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(dateChina);
//					dzTemplate.setCreatetime(formatStr);
//				}
				if(null != sheet.getRow(i).getCell(6)) {
					sheet.getRow(i).getCell(6).setCellType(Cell.CELL_TYPE_STRING);
					dzTemplate.setSstype(sheet.getRow(i).getCell(6).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(7)) {
					sheet.getRow(i).getCell(7).setCellType(Cell.CELL_TYPE_STRING);
					dzTemplate.setOrganization(sheet.getRow(i).getCell( 7).getStringCellValue());
				}
//				dzTemplate.setAskstatus(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(11))));//是否删除
				if(null != sheet.getRow(i).getCell( 8)) {
					sheet.getRow(i).getCell(8).setCellType(Cell.CELL_TYPE_STRING);
					dzTemplate.setDetail(sheet.getRow(i).getCell(8).getStringCellValue());
				}
				templateList.add(dzTemplate);
			}
			initService.initDzblTemplate(templateList);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	//获取单元格各类型值，返回字符串类型
    private static String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell == null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        int cellType = cell.getCellType();
        if(cellType == Cell.CELL_TYPE_FORMULA){ //表达式类型
            cellType = evaluator.evaluate(cell).getCellType();
        }
         
        switch (cellType) {
	        case Cell.CELL_TYPE_STRING: //字符串类型
	             cellValue = cell.getStringCellValue().trim();
	             cellValue = StringUtils.isEmpty(cellValue) ? "" : cellValue; 
	             break;
	        case Cell.CELL_TYPE_BOOLEAN:  //布尔类型
	             cellValue = String.valueOf(cell.getBooleanCellValue()); 
	             break; 
	        case Cell.CELL_TYPE_NUMERIC: //数值类型
	             if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
	            	 
	             } else {  //否
	                 cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue()); 
	             } 
	             break;
	        default: //其它类型，取空串吧
	             cellValue = "";
	             break;
        }
        return cellValue;
    }

}
