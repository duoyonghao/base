package com.hudh.ykzz.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class ExcelUtil {
  public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
  
  public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
  
  public static final String EMPTY = "";
  
  public static final String POINT = ".";
  
  public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
  
  public static String getPostfix(String path) {
    if (path == null || "".equals(path.trim()))
      return ""; 
    if (path.contains("."))
      return path.substring(path.lastIndexOf(".") + 1, path.length()); 
    return "";
  }
  
  public static String getHValue(HSSFCell hssfCell) {
    if (hssfCell.getCellType() == 4)
      return String.valueOf(hssfCell.getBooleanCellValue()); 
    if (hssfCell.getCellType() == 0) {
      String cellValue = "";
      if (HSSFDateUtil.isCellDateFormatted((Cell)hssfCell)) {
        Date date = HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue());
        cellValue = sdf.format(date);
      } else {
        DecimalFormat df = new DecimalFormat("#.##");
        cellValue = df.format(hssfCell.getNumericCellValue());
        String strArr = cellValue.substring(cellValue.lastIndexOf(".") + 1, cellValue.length());
        if (strArr.equals("00"))
          cellValue = cellValue.substring(0, cellValue.lastIndexOf(".")); 
      } 
      return cellValue;
    } 
    return String.valueOf(hssfCell.getStringCellValue());
  }
  
  public static String getXValue(XSSFCell xssfCell) {
    if (xssfCell.getCellType() == 4)
      return String.valueOf(xssfCell.getBooleanCellValue()); 
    if (xssfCell.getCellType() == 0) {
      String cellValue = "";
      if (XSSFDateUtil.isCellDateFormatted((Cell)xssfCell)) {
        Date date = XSSFDateUtil.getJavaDate(xssfCell.getNumericCellValue());
        cellValue = sdf.format(date);
      } else {
        DecimalFormat df = new DecimalFormat("#.##");
        cellValue = df.format(xssfCell.getNumericCellValue());
        String strArr = cellValue.substring(cellValue.lastIndexOf(".") + 1, cellValue.length());
        if (strArr.equals("00"))
          cellValue = cellValue.substring(0, cellValue.lastIndexOf(".")); 
      } 
      return cellValue;
    } 
    return String.valueOf(xssfCell.getStringCellValue());
  }
}
