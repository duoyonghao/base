/**  
  *
  * @Title:  KQDS_Init_Net_OrderAct.java   
  * @Package com.kqds.controller.base.netOrder   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年11月25日 下午6:31:43   
  * @version V1.0  
  */ 
package com.kqds.controller.base.netOrder;

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

import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.service.base.netOrder.KQDS_Init_Net_OrderLogic;
import com.kqds.util.sys.YZUtility;

/**  
  * 
  * @ClassName:  KQDS_Init_Net_OrderAct   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年11月25日 下午6:31:43   
  *      
  */
@Controller
@RequestMapping("KQDS_Init_Net_OrderAct")
public class KQDS_Init_Net_OrderAct {
	
	private static Logger logger = LoggerFactory.getLogger(KQDS_Init_Net_OrderAct.class);
	
	private static FormulaEvaluator evaluator;
	
	@Autowired
	private KQDS_Init_Net_OrderLogic orderLogic;
	
	@RequestMapping(value = "/batchSaveNetOrder.act")
	public String batchSaveNetOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//excel文件路径
//      String excelPath = "D:\\数据（lclj）excel\\西直门网电记录.xls";
        String excelPath = "D:\\线上数据库还原\\西直门\\西直门网电记录.xls";
		try {
			// 打开指定位置的Excel文件
			FileInputStream file = new FileInputStream(new File(excelPath));
			Workbook workbook = new HSSFWorkbook(file);//.xls
			// 打开Excel中的第一个Sheet
			Sheet sheet = workbook.getSheetAt(0);
			int row = sheet.getLastRowNum() + 1;
			System.out.println("有效行数" + row);
			List<KqdsNetOrder> list = new ArrayList<KqdsNetOrder>();
			KqdsNetOrder netOrder = null;
			for(int i = 1; i < row; i++) {
				System.err.println(i);
				netOrder = new KqdsNetOrder();
				if(null != sheet.getRow(i).getCell(0)) {
					sheet.getRow(i).getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					netOrder.setSeqId(sheet.getRow(i).getCell(0).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(1)) {
					sheet.getRow(i).getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					netOrder.setCreateuser(sheet.getRow(i).getCell(1).getStringCellValue());
				}
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
			        netOrder.setCreatetime(formatStr);
				}
				if(null != sheet.getRow(i).getCell(3)) {
					sheet.getRow(i).getCell(3).setCellType(Cell.CELL_TYPE_STRING);
					netOrder.setUsercode(sheet.getRow(i).getCell(3).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(10)) {
					sheet.getRow(i).getCell(10).setCellType(Cell.CELL_TYPE_STRING);
					netOrder.setAskcontent(sheet.getRow(i).getCell(10).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(12)) {
					sheet.getRow(i).getCell(12).setCellType(Cell.CELL_TYPE_STRING);
					netOrder.setOrganization(sheet.getRow(i).getCell(12).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(20)) {
					sheet.getRow(i).getCell(20).setCellType(Cell.CELL_TYPE_STRING);
					netOrder.setUsername(sheet.getRow(i).getCell(20).getStringCellValue());
				}
				netOrder.setIsdelete(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(26))));//是否删除
				list.add(netOrder);
			}
			orderLogic.batchSaveNetOrder(list);
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
