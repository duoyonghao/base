package com.kqds.controller.base.member;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
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

import com.kqds.entity.base.KqdsMember;
import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.service.base.member.KQDS_InitMemberLogic;
import com.kqds.util.sys.YZUtility;

@Controller
@RequestMapping("KQDS_InitMemberAct")
public class KQDS_InitMemberAct {
	
	private Logger logger = LoggerFactory.getLogger(KQDS_InitMemberAct.class);
	
	private static FormulaEvaluator evaluator;
	
	@Autowired
	private KQDS_InitMemberLogic memberLogic;
	
	@RequestMapping(value = "/batchSaveMember.act")
	public String batchSaveMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//excel文件路径
        String excelPath = "D:\\数据（lclj）excel\\会员卡数据.xlsx";
        try {
			// 打开指定位置的Excel文件
			FileInputStream file = new FileInputStream(new File(excelPath));
			Workbook workbook = new XSSFWorkbook(file);//.xlsx
//			Workbook workbook = new HSSFWorkbook(file);//.xls
			// 打开Excel中的第一个Sheet
			Sheet sheet = workbook.getSheetAt(0);
			int row = sheet.getLastRowNum() + 1;
			System.out.println("有效行数" + row);
			List<KqdsMember> list = new ArrayList<KqdsMember>();
			KqdsMember member = null;
			for(int i = 1; i < row; i++) {
				System.err.println(i);
				member = new KqdsMember();
				member.setSeqId(YZUtility.getUUID());
				if(null != sheet.getRow(i).getCell(0)) {
					sheet.getRow(i).getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					member.setCreateuser(sheet.getRow(i).getCell(0).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(1)) {
					sheet.getRow(i).getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					Calendar c = new GregorianCalendar(1900, 0, -1);
					Date d = c.getTime();
					String dateTime = sheet.getRow(i).getCell(1).getStringCellValue();
					Integer time = Integer.valueOf(dateTime);
					Date date = DateUtils.addDays(d, time); //42605是距离1900年1月1日的天数
					SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);//English格式
					//java.util.Date对象
					Date dateChina = sdf.parse(date.toString());//China格式
//			        String formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateChina);
					String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(dateChina);
					member.setCreatetime(formatStr);
				}
				if(null != sheet.getRow(i).getCell(2)) {
					sheet.getRow(i).getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					member.setUsercode(sheet.getRow(i).getCell(2).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(3)) {
					sheet.getRow(i).getCell(3).setCellType(Cell.CELL_TYPE_STRING);
					member.setUsername(sheet.getRow(i).getCell(3).getStringCellValue());
				}
				member.setDiscount(100);
				member.setMemberno(member.getUsercode());
				member.setMemberlevel("277");
				member.setMemberstatus("1");
				member.setMoney(BigDecimal.ZERO);
				member.setGivemoney(BigDecimal.ZERO);
				member.setPassword(member.getMemberno().substring(member.getMemberno().length() - 6, member.getMemberno().length()));
				member.setRemark("预交金卡");
				member.setOrganization("HUDX");
				member.setIcno(member.getUsercode());
				member.setIsbinding(1);
              list.add(member);
			}
			memberLogic.batchSaveMember(list);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	//获取单元格各类型值，返回字符串类型
    @SuppressWarnings("unused")
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
	        case Cell.CELL_TYPE_BOOLEAN: //布尔类型
	             cellValue = String.valueOf(cell.getBooleanCellValue()); 
	             break; 
	        case Cell.CELL_TYPE_NUMERIC: //数值类型
	             if (HSSFDateUtil.isCellDateFormatted(cell)) { //判断日期类型
	            	 
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
