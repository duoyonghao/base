/**  
  *
  * @Title:  KQDS_InitRegAct.java   
  * @Package com.kqds.controller.base.reg   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年11月23日 下午4:43:08   
  * @version V1.0  
  */ 
package com.kqds.controller.base.reg;

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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.entity.base.KqdsReg;
import com.kqds.service.base.reg.KQDS_InitRegLogic;
import com.kqds.util.sys.YZUtility;

/**  
  * 
  * @ClassName:  KQDS_InitRegAct   
  * @Description:TODO(批量导入数据)   
  * @author: 海德堡联合口腔
  * @date:   2019年11月23日 下午4:43:08   
  *      
  */
@Controller
@RequestMapping("KQDS_InitRegAct")
public class KQDS_InitRegAct {
	
	private Logger logger = LoggerFactory.getLogger(KQDS_InitRegAct.class);
	
	private static FormulaEvaluator evaluator;
	
	@Autowired
	private KQDS_InitRegLogic regLogic;
	
	@RequestMapping(value = "/batchSaveReg.act")
	public String batchSaveReg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//excel文件路径
//      String excelPath = "D:\\数据（lclj）excel\\西直门挂号记录.xls";
		String excelPath = "D:\\线上数据库还原\\西直门\\西直门挂号记录.xls";
		try {
			// 打开指定位置的Excel文件
			FileInputStream file = new FileInputStream(new File(excelPath));
			Workbook workbook = new HSSFWorkbook(file);
			// 打开Excel中的第一个Sheet
			Sheet sheet = workbook.getSheetAt(0);
			int row = sheet.getLastRowNum() + 1;
			System.out.println("有效行数" + row);
			List<KqdsReg> regList = new ArrayList<KqdsReg>();
			KqdsReg kqdsReg = null;
			for (int i = 1; i < row; i++) {
				System.err.println(i);
				kqdsReg = new KqdsReg();
				kqdsReg.setSeqId(YZUtility.getUUID());
				if(null != sheet.getRow(i).getCell(2)) {
					sheet.getRow(i).getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					Calendar c = new GregorianCalendar(1900,0,-1);
					Date d = c.getTime();
					String dateTime = sheet.getRow(i).getCell(2).getStringCellValue();
					Integer time = Integer.valueOf(dateTime);
					Date date = DateUtils.addDays(d, time); //42605是距离1900年1月1日的天数
					SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			        //java.util.Date对象
			        Date dateChina = sdf.parse(date.toString());
			        String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(dateChina);
					kqdsReg.setCreatetime(formatStr);
				}
				if(null != sheet.getRow(i).getCell(3)) {
					sheet.getRow(i).getCell(3).setCellType(Cell.CELL_TYPE_STRING);
					kqdsReg.setUsercode(sheet.getRow(i).getCell(3).getStringCellValue());
				}
				/*if(null != sheet.getRow(i).getCell(6)) {
					sheet.getRow(i).getCell(6).setCellType(Cell.CELL_TYPE_STRING);
					kqdsReg.setRecesort(sheet.getRow(i).getCell(6).getStringCellValue());
				}*/
				if(null != sheet.getRow(i).getCell(8)) {
					sheet.getRow(i).getCell(8).setCellType(Cell.CELL_TYPE_STRING);
					kqdsReg.setRegmoney(sheet.getRow(i).getCell(8).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(11)) {
					sheet.getRow(i).getCell(11).setCellType(Cell.CELL_TYPE_STRING);
					kqdsReg.setAskperson(sheet.getRow(i).getCell(11).getStringCellValue());
				}
				kqdsReg.setDel(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(13))));//是否消费
				if(null != sheet.getRow(i).getCell(17)) {
					sheet.getRow(i).getCell(17).setCellType(Cell.CELL_TYPE_STRING);
					kqdsReg.setUsername(sheet.getRow(i).getCell(17).getStringCellValue());
				}
				kqdsReg.setDel(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(18))));//是否删除
				kqdsReg.setCjstatus(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(19))));//成交状态
				if(null != sheet.getRow(i).getCell(22)) {
					sheet.getRow(i).getCell(22).setCellType(Cell.CELL_TYPE_STRING);
					kqdsReg.setOrganization(sheet.getRow(i).getCell(22).getStringCellValue());
				}
				regList.add(kqdsReg);
			}
			regLogic.batchSaveReg(regList);
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
