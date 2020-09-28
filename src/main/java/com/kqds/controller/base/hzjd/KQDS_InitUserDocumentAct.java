/**  
  *
  * @Title:  KQDS_InitUserDocumentAct.java   
  * @Package com.kqds.controller.base.hzjd   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年11月23日 下午2:28:44   
  * @version V1.0  
  */ 
package com.kqds.controller.base.hzjd;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.service.base.hzjd.KQDS_InitUserdocumentLogic;

/**  
  * 
  * @ClassName:  KQDS_InitUserDocumentAct   
  * @Description:TODO(这里用一句话描述这个类的作用)   
  * @author: 海德堡联合口腔
  * @date:   2019年11月23日 下午2:28:44   
  *      
  */
@Controller
@RequestMapping("KQDS_InitUserDocumentAct")
public class KQDS_InitUserDocumentAct {
	
	private static FormulaEvaluator evaluator;
	
	@Autowired
	private KQDS_InitUserdocumentLogic documentLogic;
	
	@RequestMapping(value = "/initUserDocument.act")
	public String initUserDocument(HttpServletRequest request, HttpServletResponse response) {
		//excel文件路径
        String excelPath = "D:\\数据（lclj）excel\\西直门档案数据.xlsx";
        try {
			// 打开指定位置的Excel文件
			FileInputStream file = new FileInputStream(new File(excelPath));
			Workbook workbook = new XSSFWorkbook(file);//.xlsx
//			Workbook workbook = new HSSFWorkbook(file);//.xls
			// 打开Excel中的第一个Sheet
			Sheet sheet = workbook.getSheetAt(0);
			int row = sheet.getLastRowNum() + 1;
			System.out.println("有效行数" + row);
			List<KqdsUserdocument> list = new ArrayList<KqdsUserdocument>();
			KqdsUserdocument userDoc = null;
			for(int i = 1; i < row; i++) {
				System.err.println(i);
			    userDoc = new KqdsUserdocument();
//				userDoc.setSeqId(YZUtility.getUUID());
//              userDoc.setSeqId(String.valueOf(i));
				if(null != sheet.getRow(i).getCell(0)) {
					sheet.getRow(i).getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					userDoc.setSeqId(sheet.getRow(i).getCell(0).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(1)) {
					sheet.getRow(i).getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					userDoc.setUsercode(sheet.getRow(i).getCell(1).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(2)) {
					sheet.getRow(i).getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					userDoc.setUsername(sheet.getRow(i).getCell(2).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(3)) {
					sheet.getRow(i).getCell(3).setCellType(Cell.CELL_TYPE_STRING);
					userDoc.setSex(sheet.getRow(i).getCell(3).getStringCellValue());
				}
//				if(!(sheet.getRow(i).getCell(5)).equals("")) {
					userDoc.setAge(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(5))));
//				}
				if(null != sheet.getRow(i).getCell(6)) {
					sheet.getRow(i).getCell(6).setCellType(Cell.CELL_TYPE_STRING);
					userDoc.setPhonenumber1(sheet.getRow(i).getCell(6).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(7)) {
					sheet.getRow(i).getCell(7).setCellType(Cell.CELL_TYPE_STRING);
					userDoc.setPhonenumber2(sheet.getRow(i).getCell(7).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(14)) {
					sheet.getRow(i).getCell(14).setCellType(Cell.CELL_TYPE_STRING);
					userDoc.setProvince(sheet.getRow(i).getCell(14).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(15)) {
					sheet.getRow(i).getCell(15).setCellType(Cell.CELL_TYPE_STRING);
					userDoc.setCity(sheet.getRow(i).getCell(15).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(16)) {
					sheet.getRow(i).getCell(16).setCellType(Cell.CELL_TYPE_STRING);
					userDoc.setAddress(sheet.getRow(i).getCell(16).getStringCellValue());
				}
				if(null != sheet.getRow(i).getCell(18)) {
					sheet.getRow(i).getCell(18).setCellType(Cell.CELL_TYPE_STRING);
					Calendar c = new GregorianCalendar(1900, 0, -1);
					Date d = c.getTime();
					String dateTime = sheet.getRow(i).getCell(18).getStringCellValue();
					Integer time = Integer.valueOf(dateTime);
					Date date = DateUtils.addDays(d, time); //42605是距离1900年1月1日的天数
			        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);//English格式
			        //java.util.Date对象
			        Date dateChina = sdf.parse(date.toString());//China格式
//			        String formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateChina);
			        String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(dateChina);
					userDoc.setCreatetime(formatStr);
				}
				if(null != sheet.getRow(i).getCell(19)) {
					sheet.getRow(i).getCell(19).setCellType(Cell.CELL_TYPE_STRING);
					userDoc.setCreateuser(sheet.getRow(i).getCell(19).getStringCellValue());
				}
				userDoc.setIsdelete(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(21))));//是否删除
				if(null != sheet.getRow(i).getCell(22)) {
					sheet.getRow(i).getCell(22).setCellType(Cell.CELL_TYPE_STRING);
					userDoc.setOrganization(sheet.getRow(i).getCell(22).getStringCellValue());
				}
//              if(null != sheet.getRow(i).getCell(0)) {
//                  sheet.getRow(i).getCell(0).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setSeqId(sheet.getRow(i).getCell(0).getStringCellValue()); //
//              }
//              if(null != sheet.getRow(i).getCell(1)) {
//                  sheet.getRow(i).getCell(1).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setBlcode(sheet.getRow(i).getCell(1).getStringCellValue()); //
//              }
//              if(null != sheet.getRow(i).getCell(2)) {
//                  sheet.getRow(i).getCell(2).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setUsername(sheet.getRow(i).getCell(2).getStringCellValue());
//              }
//              if(null != sheet.getRow(i).getCell(3)) {
//                  sheet.getRow(i).getCell(3).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setSex(sheet.getRow(i).getCell(3).getStringCellValue());
//              }
//              if(null != sheet.getRow(i).getCell(4)) {
//                  sheet.getRow(i).getCell(4).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setBirthday(sheet.getRow(i).getCell(4).getStringCellValue());
//              }
//              userDoc.setAge(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(5))));//年龄
//              if(null != sheet.getRow(i).getCell(8)) {
//                  sheet.getRow(i).getCell(8).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setPhonenumber1(sheet.getRow(i).getCell(8).getStringCellValue());
//              }
//              if(null != sheet.getRow(i).getCell(9)) {
//                  sheet.getRow(i).getCell(9).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setPhonenumber2(sheet.getRow(i).getCell(9).getStringCellValue());
//              }
//              if(null != sheet.getRow(i).getCell(12)) {
//                  sheet.getRow(i).getCell(12).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setIntroducer(sheet.getRow(i).getCell(12).getStringCellValue());
//              }
//              if(null != sheet.getRow(i).getCell(16)) {
//                  sheet.getRow(i).getCell(16).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setFirstword(sheet.getRow(i).getCell(16).getStringCellValue());
//              }
//              if(null != sheet.getRow(i).getCell(17)) {
//                  sheet.getRow(i).getCell(17).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setProvince(sheet.getRow(i).getCell(17).getStringCellValue());
//              }
//              if(null != sheet.getRow(i).getCell(18)) {
//                  sheet.getRow(i).getCell(18).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setCity(sheet.getRow(i).getCell(18).getStringCellValue());
//              }
//              if(null != sheet.getRow(i).getCell(19)) {
//                  sheet.getRow(i).getCell(19).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setAddress(sheet.getRow(i).getCell(19).getStringCellValue());
//              }
//              if(null != sheet.getRow(i).getCell(20)) {
//                  sheet.getRow(i).getCell(20).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setDevchannel(sheet.getRow(i).getCell(20).getStringCellValue());
//              }
//              if(null != sheet.getRow(i).getCell(21)) {
//                  sheet.getRow(i).getCell(21).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setCreatetime(sheet.getRow(i).getCell(21).getStringCellValue());
//              }
//              if(null != sheet.getRow(i).getCell(22)) {
//                  sheet.getRow(i).getCell(22).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setCreateuser(sheet.getRow(i).getCell(22).getStringCellValue());
//              }
//              if(null != sheet.getRow(i).getCell(23)) {
//                  sheet.getRow(i).getCell(23).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setCreateuser(sheet.getRow(i).getCell(23).getStringCellValue());
//              }
//              if(null != sheet.getRow(i).getCell(24)) {
//                  sheet.getRow(i).getCell(24).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setAskperson(sheet.getRow(i).getCell(24).getStringCellValue());
//              }
//              userDoc.setIsdelete(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(24))));//是否删除
//              if(null != sheet.getRow(i).getCell(25)) {
//                  sheet.getRow(i).getCell(25).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setOrganization(sheet.getRow(i).getCell(25).getStringCellValue());
//              }
//              if(null != sheet.getRow(i).getCell(29)) {
//                  sheet.getRow(i).getCell(29).setCellType(Cell.CELL_TYPE_STRING);
//                  userDoc.setRemark(sheet.getRow(i).getCell(29).getStringCellValue());
//              }
              list.add(userDoc);
			}
			documentLogic.batchSaveUserDocument(list);
		} catch (Exception e) {
			e.printStackTrace();
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
