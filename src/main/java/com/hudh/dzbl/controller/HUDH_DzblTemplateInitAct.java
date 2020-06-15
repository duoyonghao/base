package com.hudh.dzbl.controller;

import com.hudh.dzbl.entity.DzblTemplate;
import com.hudh.dzbl.service.IDzblTemplateInitService;
import com.kqds.util.sys.YZUtility;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"HUDH_DzblTemplateInitAct"})
public class HUDH_DzblTemplateInitAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_DzblTemplateInitAct.class);
  
  @Autowired
  private IDzblTemplateInitService initService;
  
  private static FormulaEvaluator evaluator;
  
  @RequestMapping({"/initDzblTemplate.act"})
  public String initDzblTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String excelPath = "D:\\线上数据库还原\\西院电子病历导入.xlsx";
    try {
      FileInputStream file = new FileInputStream(new File(excelPath));
      XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(file);
      Sheet sheet = xSSFWorkbook.getSheetAt(0);
      int row = sheet.getLastRowNum() + 1;
      System.out.println("有效行数" + row);
      List<DzblTemplate> templateList = new ArrayList<>();
      DzblTemplate dzTemplate = null;
      for (int i = 1; i < row; i++) {
        System.err.println(i);
        dzTemplate = new DzblTemplate();
        if (sheet.getRow(i).getCell(0) != null) {
          sheet.getRow(i).getCell(0).setCellType(1);
          dzTemplate.setId(sheet.getRow(i).getCell(0).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(1) != null) {
          sheet.getRow(i).getCell(1).setCellType(1);
          dzTemplate.setTitle(sheet.getRow(i).getCell(1).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(2) != null) {
          sheet.getRow(i).getCell(2).setCellType(1);
          dzTemplate.setBlfl(sheet.getRow(i).getCell(2).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(3) != null) {
          sheet.getRow(i).getCell(3).setCellType(1);
          dzTemplate.setBc(sheet.getRow(i).getCell(3).getStringCellValue());
        } 
        dzTemplate.setCreatetime(YZUtility.getCurDateTimeStr());
        dzTemplate.setModifytime(YZUtility.getCurDateTimeStr());
        if (sheet.getRow(i).getCell(6) != null) {
          sheet.getRow(i).getCell(6).setCellType(1);
          dzTemplate.setSstype(sheet.getRow(i).getCell(6).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(7) != null) {
          sheet.getRow(i).getCell(7).setCellType(1);
          dzTemplate.setOrganization(sheet.getRow(i).getCell(7).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(8) != null) {
          sheet.getRow(i).getCell(8).setCellType(1);
          dzTemplate.setDetail(sheet.getRow(i).getCell(8).getStringCellValue());
        } 
        templateList.add(dzTemplate);
      } 
      this.initService.initDzblTemplate(templateList);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
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
