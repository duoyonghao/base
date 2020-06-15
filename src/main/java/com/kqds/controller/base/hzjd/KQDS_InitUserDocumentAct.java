package com.kqds.controller.base.hzjd;

import com.kqds.entity.base.KqdsUserdocument;
import com.kqds.service.base.hzjd.KQDS_InitUserdocumentLogic;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"KQDS_InitUserDocumentAct"})
public class KQDS_InitUserDocumentAct {
  private static FormulaEvaluator evaluator;
  
  @Autowired
  private KQDS_InitUserdocumentLogic documentLogic;
  
  @RequestMapping({"/initUserDocument.act"})
  public String initUserDocument(HttpServletRequest request, HttpServletResponse response) {
    String excelPath = "D:\\数据（lclj）excel\\西直门档案数据.xlsx";
    try {
      FileInputStream file = new FileInputStream(new File(excelPath));
      XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(file);
      Sheet sheet = xSSFWorkbook.getSheetAt(0);
      int row = sheet.getLastRowNum() + 1;
      System.out.println("有效行数" + row);
      List<KqdsUserdocument> list = new ArrayList<>();
      KqdsUserdocument userDoc = null;
      for (int i = 1; i < row; i++) {
        System.err.println(i);
        userDoc = new KqdsUserdocument();
        if (sheet.getRow(i).getCell(0) != null) {
          sheet.getRow(i).getCell(0).setCellType(1);
          userDoc.setSeqId(sheet.getRow(i).getCell(0).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(1) != null) {
          sheet.getRow(i).getCell(1).setCellType(1);
          userDoc.setUsercode(sheet.getRow(i).getCell(1).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(2) != null) {
          sheet.getRow(i).getCell(2).setCellType(1);
          userDoc.setUsername(sheet.getRow(i).getCell(2).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(3) != null) {
          sheet.getRow(i).getCell(3).setCellType(1);
          userDoc.setSex(sheet.getRow(i).getCell(3).getStringCellValue());
        } 
        userDoc.setAge(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(5))));
        if (sheet.getRow(i).getCell(6) != null) {
          sheet.getRow(i).getCell(6).setCellType(1);
          userDoc.setPhonenumber1(sheet.getRow(i).getCell(6).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(7) != null) {
          sheet.getRow(i).getCell(7).setCellType(1);
          userDoc.setPhonenumber2(sheet.getRow(i).getCell(7).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(14) != null) {
          sheet.getRow(i).getCell(14).setCellType(1);
          userDoc.setProvince(sheet.getRow(i).getCell(14).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(15) != null) {
          sheet.getRow(i).getCell(15).setCellType(1);
          userDoc.setCity(sheet.getRow(i).getCell(15).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(16) != null) {
          sheet.getRow(i).getCell(16).setCellType(1);
          userDoc.setAddress(sheet.getRow(i).getCell(16).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(18) != null) {
          sheet.getRow(i).getCell(18).setCellType(1);
          Calendar c = new GregorianCalendar(1900, 0, -1);
          Date d = c.getTime();
          String dateTime = sheet.getRow(i).getCell(18).getStringCellValue();
          Integer time = Integer.valueOf(dateTime);
          Date date = DateUtils.addDays(d, time.intValue());
          SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
          Date dateChina = sdf.parse(date.toString());
          String formatStr = (new SimpleDateFormat("yyyy-MM-dd")).format(dateChina);
          userDoc.setCreatetime(formatStr);
        } 
        if (sheet.getRow(i).getCell(19) != null) {
          sheet.getRow(i).getCell(19).setCellType(1);
          userDoc.setCreateuser(sheet.getRow(i).getCell(19).getStringCellValue());
        } 
        userDoc.setIsdelete(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(21))));
        if (sheet.getRow(i).getCell(22) != null) {
          sheet.getRow(i).getCell(22).setCellType(1);
          userDoc.setOrganization(sheet.getRow(i).getCell(22).getStringCellValue());
        } 
        list.add(userDoc);
      } 
      this.documentLogic.batchSaveUserDocument(list);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return null;
  }
  
  private static String getCellValueByCell(Cell cell) {
    if (cell == null || cell.toString().trim().equals(""))
      return ""; 
    String cellValue = "";
    int cellType = cell.getCellType();
    if (cellType == 2)
      cellType = evaluator.evaluate(cell).getCellType(); 
    switch (cellType) {
      case 1:
        cellValue = cell.getStringCellValue().trim();
        cellValue = StringUtils.isEmpty(cellValue) ? "" : cellValue;
        return cellValue;
      case 4:
        cellValue = String.valueOf(cell.getBooleanCellValue());
        return cellValue;
      case 0:
        if (!HSSFDateUtil.isCellDateFormatted(cell))
          cellValue = (new DecimalFormat("#.######")).format(cell.getNumericCellValue()); 
        return cellValue;
    } 
    cellValue = "";
    return cellValue;
  }
}
