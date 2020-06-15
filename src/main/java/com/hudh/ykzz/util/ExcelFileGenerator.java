package com.hudh.ykzz.util;

import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelFileGenerator {
  private final int SPLIT_COUNT = 1500;
  
  private String[] fieldName = null;
  
  private List<?> fieldData = null;
  
  private HSSFWorkbook workBook = null;
  
  public ExcelFileGenerator(String fieldName, List<?> fieldData) {
    this.fieldName = fieldName.split("/");
    this.fieldData = fieldData;
  }
  
  public HSSFWorkbook createWorkbook() {
    this.workBook = new HSSFWorkbook();
    int rows = this.fieldData.size();
    int sheetNum = 0;
    int lastSheetRows = 0;
    if (rows % 1500 == 0) {
      sheetNum = rows / 1500;
      lastSheetRows = rows;
    } else {
      sheetNum = rows / 1500 + 1;
      lastSheetRows = rows % 1500;
    } 
    if (sheetNum == 0)
      sheetNum = 1; 
    for (int i = 1; i <= sheetNum; ) {
      HSSFSheet sheet = this.workBook.createSheet("Page " + i);
      HSSFRow headRow = sheet.createRow(0);
      for (int j = 0; j < this.fieldName.length; j++) {
        HSSFCell cell = headRow.createCell(j);
        cell.setCellType(1);
        sheet.setColumnWidth(j, 6000);
        HSSFCellStyle cellStyle = this.workBook.createCellStyle();
        HSSFFont font = this.workBook.createFont();
        font.setBoldweight((short)700);
        short color = 10;
        font.setColor(color);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(this.fieldName[j]);
      } 
      int k = 0;
      while (true) {
        if (k >= ((i == sheetNum) ? lastSheetRows : 1500)) {
          i++;
          continue;
        } 
        HSSFRow row = sheet.createRow((short)(k + 1));
        Object[] model = (Object[])this.fieldData.get((i - 1) * 1500 + k);
        for (int n = 0; n < model.length; n++)
          setValue(model[n], row, n); 
        k++;
      } 
    } 
    return this.workBook;
  }
  
  private void setValue(Object value, HSSFRow row, int n) {
    HSSFCell cell = row.createCell(n);
    if (value != null) {
      if (value instanceof String) {
        cell.setCellType(1);
        cell.setCellValue((String)value);
      } else if (value instanceof java.util.Date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        cell.setCellValue(df.format(value));
      } else if (value instanceof Boolean) {
        cell.setCellValue(((Boolean)value).booleanValue() ? "1" : "0");
      } else if (value instanceof Double) {
        cell.setCellValue(((Double)value).doubleValue());
      } else {
        cell.setCellValue(String.valueOf(value));
      } 
    } else {
      cell.setCellValue(" ");
    } 
  }
}
