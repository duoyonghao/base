package com.kqds.util.sys.imports;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelTool {
  private static Logger logger = LoggerFactory.getLogger(ExcelTool.class);
  
  private static int totalRows = 0;
  
  private static int totalCells = 0;
  
  private static String errorInfo;
  
  private static int getTotalCells() {
    return totalCells;
  }
  
  public static boolean isExcel2003(String filePath) {
    return filePath.matches("^.+\\.(?i)(xls)$");
  }
  
  public static boolean isExcel2007(String filePath) {
    return filePath.matches("^.+\\.(?i)(xlsx)$");
  }
  
  private boolean validateExcel(String filePath) {
    if (filePath == null || (!isExcel2003(filePath) && !isExcel2007(filePath))) {
      errorInfo = "文件名不是excel格式";
      return false;
    } 
    File file = new File(filePath);
    if (file == null || !file.exists()) {
      errorInfo = "文件不存在";
      return false;
    } 
    return true;
  }
  
  public List<List<String>> read(String filePath) throws Exception {
    List<List<String>> dataLst = new ArrayList<>();
    InputStream is = null;
    try {
      if (!validateExcel(filePath))
        throw new Exception(errorInfo); 
      boolean isExcel2003 = true;
      if (isExcel2007(filePath))
        isExcel2003 = false; 
      File file = new File(filePath);
      is = new FileInputStream(file);
      dataLst = read(is, isExcel2003);
      is.close();
    } catch (Exception ex) {
      logger.error("ExcelTool read Error：" + ex.getMessage());
      throw ex;
    } finally {
      if (is != null)
        is.close(); 
    } 
    return dataLst;
  }
  
  public static List<List<String>> read(InputStream inputStream, boolean isExcel2003) throws IOException {
    List<List<String>> dataLst = null;
    try {
      XSSFWorkbook xSSFWorkbook;
      Workbook wb = null;
      if (isExcel2003) {
        HSSFWorkbook hSSFWorkbook = new HSSFWorkbook(inputStream);
      } else {
        xSSFWorkbook = new XSSFWorkbook(inputStream);
      } 
      dataLst = read((Workbook)xSSFWorkbook);
    } catch (IOException e) {
      logger.error("ExcelTool read Error ：" + e.getMessage());
      throw e;
    } 
    return dataLst;
  }
  
  private static List<List<String>> read(Workbook wb) {
    List<List<String>> dataLst = new ArrayList<>();
    Sheet sheet = wb.getSheetAt(0);
    totalRows = sheet.getPhysicalNumberOfRows();
    if (totalRows >= 1 && sheet.getRow(0) != null)
      totalCells = sheet.getRow(0).getPhysicalNumberOfCells(); 
    for (int r = 0; r < totalRows; r++) {
      Row row = sheet.getRow(r);
      if (row != null) {
        List<String> rowLst = new ArrayList<>();
        for (int c = 0; c < getTotalCells(); c++) {
          Cell cell = row.getCell(c);
          String cellValue = "";
          if (cell != null)
            switch (cell.getCellType()) {
              case 0:
                cellValue = (new StringBuilder(String.valueOf(cell.getNumericCellValue()))).toString();
                break;
              case 1:
                cellValue = cell.getStringCellValue();
                break;
              case 4:
                cellValue = (new StringBuilder(String.valueOf(cell.getBooleanCellValue()))).toString();
                break;
              case 2:
                cellValue = (new StringBuilder(String.valueOf(cell.getCellFormula()))).toString();
                break;
              case 3:
                cellValue = "";
                break;
              case 5:
                cellValue = "非法字符";
                break;
              default:
                cellValue = "未知类型";
                break;
            }  
          rowLst.add(cellValue);
        } 
        dataLst.add(rowLst);
      } 
    } 
    return dataLst;
  }
  
  public static void main(String[] args) throws Exception {
    ExcelTool poi = new ExcelTool();
    List<List<String>> list = poi.read("c:/模板.xls");
    if (list != null)
      for (int i = 0; i < list.size(); i++) {
        List<String> cellList = list.get(i);
        for (int j = 0; j < cellList.size(); j++);
      }  
  }
}
