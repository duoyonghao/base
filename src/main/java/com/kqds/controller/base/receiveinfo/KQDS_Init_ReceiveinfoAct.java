/**  
  *
  * @Title:  KQDS_Init_ReceiveinfoAct.java   
  * @Package com.kqds.controller.base.receiveinfo   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年11月26日 上午11:05:15   
  * @version V1.0  
  */ 
package com.kqds.controller.base.receiveinfo;

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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.entity.base.KqdsReceiveinfo;
import com.kqds.service.base.receiveinfo.KQDS_Init_ReceiveinfoLogic;
import com.kqds.util.sys.YZUtility;

/**  
  * 
  * @ClassName:  KQDS_Init_ReceiveinfoAct   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年11月26日 上午11:05:15   
  *      
  */
@Controller
@RequestMapping("KQDS_Init_ReceiveinfoAct")
public class KQDS_Init_ReceiveinfoAct {
	
	private Logger logger = LoggerFactory.getLogger(KQDS_Init_ReceiveinfoAct.class);
	
	private static FormulaEvaluator evaluator;
	
	@Autowired
	private KQDS_Init_ReceiveinfoLogic receiveLogic;
	
	@RequestMapping(value = "/batchSaveReceiveinfo.act")
	public String batchSaveReceiveinfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//excel文件路径
//      String excelPath = "D:\\数据（lclj）excel\\西直门咨询记录.xls";
        String excelPath = "D:\\线上数据库还原\\西直门\\西直门咨询记录.xls";
		try {
			// 打开指定位置的Excel文件
			FileInputStream file = new FileInputStream(new File(excelPath));
			Workbook workbook = new HSSFWorkbook(file);//.xls
			// 打开Excel中的第一个Sheet
			Sheet sheet = workbook.getSheetAt(0);
			int row = sheet.getLastRowNum() + 1;
			System.out.println("有效行数" + row);
			List<KqdsReceiveinfo> receList = new ArrayList<KqdsReceiveinfo>();
			KqdsReceiveinfo receiveinfo = null;
			for(int i = 1; i < row; i++) {
				System.err.println(i);
				receiveinfo = new KqdsReceiveinfo();
				if(null != sheet.getRow(i).getCell(0)) {
					sheet.getRow(i).getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					receiveinfo.setSeqId(sheet.getRow(i).getCell(0).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(2)) {
					sheet.getRow(i).getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					Calendar c = new GregorianCalendar(1900, 0, -1);
					Date d = c.getTime();
					String dateTime = sheet.getRow(i).getCell(2).getStringCellValue();
					Integer time = Integer.valueOf(dateTime);
					Date date = DateUtils.addDays(d, time); //42605是距离1900年1月1日的天数
					SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			        //java.util.Date对象
			        Date dateChina = sdf.parse(date.toString());
			        String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(dateChina);
					receiveinfo.setCreatetime(formatStr);
				}
				if(null != sheet.getRow(i).getCell(3)) {
					sheet.getRow(i).getCell(3).setCellType(Cell.CELL_TYPE_STRING);
					receiveinfo.setUsercode(sheet.getRow(i).getCell(3).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(4)) {
					sheet.getRow(i).getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					receiveinfo.setUsername(sheet.getRow(i).getCell(4).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(6)) {
					sheet.getRow(i).getCell(6).setCellType(Cell.CELL_TYPE_STRING);
					receiveinfo.setRegno(sheet.getRow(i).getCell(6).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(9)) {
					sheet.getRow(i).getCell(9).setCellType(Cell.CELL_TYPE_STRING);
					receiveinfo.setDetaildesc(sheet.getRow(i).getCell(9).getStringCellValue());
				}
				receiveinfo.setAskstatus(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(11))));//是否删除
				if(null != sheet.getRow(i).getCell(12)) {
					sheet.getRow(i).getCell(12).setCellType(Cell.CELL_TYPE_STRING);
					receiveinfo.setOrganization(sheet.getRow(i).getCell(12).getStringCellValue());
				}
				receList.add(receiveinfo);
			}
			receiveLogic.batchSaveReceiveinfo(receList);
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
