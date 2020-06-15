package com.hudh.ykzz.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ReadExcel
{
  public int totalRows;
  public static int totalCells;
  
  public List<ArrayList<String>> readExcel(MultipartFile file)
    throws IOException
  {
    if ((file == null) || ("".equals(file.getOriginalFilename().trim()))) {
      return null;
    }
    String postfix = ExcelUtil.getPostfix(file.getOriginalFilename());
    if (!"".equals(postfix))
    {
      if ("xls".equals(postfix)) {
        return readXls(file);
      }
      if ("xlsx".equals(postfix)) {
        return readXlsx(file);
      }
      return null;
    }
    return null;
  }
  
  public List<ArrayList<String>> readXlsx(MultipartFile file)
  {
    List<ArrayList<String>> list = new ArrayList();
    
    InputStream input = null;
    XSSFWorkbook wb = null;
    ArrayList<String> rowList = null;
    try
    {
      input = file.getInputStream();
      
      wb = new XSSFWorkbook(input);
      for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++)
      {
        XSSFSheet xssfSheet = wb.getSheetAt(numSheet);
        if (xssfSheet != null)
        {
          this.totalRows = xssfSheet.getLastRowNum();
          for (int rowNum = 1; rowNum <= this.totalRows; rowNum++)
          {
            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow != null)
            {
              rowList = new ArrayList();
              totalCells = xssfRow.getLastCellNum();
              for (int c = 0; c <= totalCells + 1; c++)
              {
                XSSFCell cell = xssfRow.getCell(c);
                if (cell == null) {
                  rowList.add("");
                } else {
                  rowList.add(ExcelUtil.getXValue(cell).trim());
                }
              }
              list.add(rowList);
            }
          }
        }
      }
      return list;
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        input.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return null;
  }
  
  public List<ArrayList<String>> readXls(MultipartFile file)
  {
    List<ArrayList<String>> list = new ArrayList();
    
    InputStream input = null;
    HSSFWorkbook wb = null;
    ArrayList<String> rowList = null;
    try
    {
      input = file.getInputStream();
      
      wb = new HSSFWorkbook(input);
      for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++)
      {
        HSSFSheet hssfSheet = wb.getSheetAt(numSheet);
        if (hssfSheet != null)
        {
          this.totalRows = hssfSheet.getLastRowNum();
          for (int rowNum = 1; rowNum <= this.totalRows; rowNum++)
          {
            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow != null)
            {
              rowList = new ArrayList();
              totalCells = hssfRow.getLastCellNum();
              for (short c = 0; c <= totalCells + 1; c = (short)(c + 1))
              {
                HSSFCell cell = hssfRow.getCell(c);
                




                String cellValue = "";
                if (cell != null) {
                  switch (cell.getCellType())
                  {
                  case 0: 
                    cellValue = cell.getNumericCellValue();
                    break;
                  case 1: 
                    cellValue = cell.getStringCellValue();
                    break;
                  case 4: 
                    cellValue = cell.getBooleanCellValue();
                    break;
                  case 2: 
                    cellValue = cell.getCellFormula();
                    break;
                  case 3: 
                    cellValue = "";
                    break;
                  case 5: 
                    cellValue = "非法字符";
                    break;
                  default: 
                    cellValue = "未知类型";
                  }
                }
                rowList.add(cellValue);
              }
              list.add(rowList);
            }
          }
        }
      }
      return list;
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        input.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return null;
  }
}
