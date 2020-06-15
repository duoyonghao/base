package com.kqds.util.sys.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelReader {
  private POIFSFileSystem fs;
  
  private HSSFWorkbook wb;
  
  private HSSFSheet sheet;
  
  private HSSFRow row;
  
  public String[] readExcelTitle(InputStream is) {
    try {
      this.fs = new POIFSFileSystem(is);
      this.wb = new HSSFWorkbook(this.fs);
    } catch (IOException e) {
      e.printStackTrace();
    } 
    this.sheet = this.wb.getSheetAt(0);
    this.row = this.sheet.getRow(0);
    int colNum = this.row.getPhysicalNumberOfCells();
    String[] title = new String[colNum];
    for (int i = 0; i < colNum; i++)
      title[i] = getStringCellValue(this.row.getCell((short)i)); 
    return title;
  }
  
  public Map<Integer, String> readExcelContent(InputStream is) {
    Map<Integer, String> content = new HashMap<>();
    String str = "";
    try {
      this.fs = new POIFSFileSystem(is);
      this.wb = new HSSFWorkbook(this.fs);
    } catch (IOException e) {
      e.printStackTrace();
    } 
    this.sheet = this.wb.getSheetAt(0);
    int rowNum = this.sheet.getLastRowNum();
    this.row = this.sheet.getRow(0);
    int colNum = this.row.getPhysicalNumberOfCells();
    for (int i = 1; i <= rowNum; i++) {
      this.row = this.sheet.getRow(i);
      int j = 0;
      while (j < colNum) {
        str = String.valueOf(str) + getStringCellValue(this.row.getCell((short)j)).trim() + "-";
        j++;
      } 
      content.put(Integer.valueOf(i), str);
      str = "";
    } 
    return content;
  }
  
  private String getStringCellValue(HSSFCell cell) {
    String strCell = "";
    switch (cell.getCellType()) {
      case 1:
        strCell = cell.getStringCellValue();
        break;
      case 0:
        strCell = String.valueOf(cell.getNumericCellValue());
        break;
      case 4:
        strCell = String.valueOf(cell.getBooleanCellValue());
        break;
      case 3:
        strCell = "";
        break;
      default:
        strCell = "";
        break;
    } 
    if (strCell.equals("") || strCell == null)
      return ""; 
    return strCell;
  }
  
  public static void main(String[] args) {
    try {
      ExcelReader excelReader = new ExcelReader();
      InputStream is2 = new FileInputStream("F://患者建档资料.xlsx");
      Map<Integer, String> map = excelReader.readExcelContent(is2);
      for (int i = 1; i <= map.size(); i++);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } 
  }
}
