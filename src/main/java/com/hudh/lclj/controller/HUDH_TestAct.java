package com.hudh.lclj.controller;

import com.hudh.lclj.dao.NodeConfigDao;
import com.hudh.lclj.entity.LcljNodeConfig;
import com.kqds.service.ck.KQDS_Ck_Goods_DetailLogic;
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
@RequestMapping({"/HUDH_TestAct"})
public class HUDH_TestAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_TestAct.class);
  
  @Autowired
  private NodeConfigDao nodeConfigDao;
  
  @Autowired
  private KQDS_Ck_Goods_DetailLogic logic;
  
  private static FormulaEvaluator evaluator;
  
  @RequestMapping({"initFlowNodes.act"})
  public String initFlowNodes(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String excelPath = "D:\\数据（lclj）excel\\新增临床路径.xlsx";
    try {
      FileInputStream file = new FileInputStream(new File(excelPath));
      XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(file);
      Sheet sheet = xSSFWorkbook.getSheetAt(0);
      int row = sheet.getLastRowNum() + 1;
      System.out.println("有效行数" + row);
      List<LcljNodeConfig> list = new ArrayList<>();
      LcljNodeConfig nodeConfig = null;
      for (int i = 1; i < row; i++) {
        System.out.println(i);
        nodeConfig = new LcljNodeConfig();
        nodeConfig.setId(YZUtility.getUUID());
        nodeConfig.setNum(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(0))));
        if (sheet.getRow(i).getCell(1) != null) {
          sheet.getRow(i).getCell(1).setCellType(1);
          nodeConfig.setNodeId(sheet.getRow(i).getCell(1).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(2) != null) {
          sheet.getRow(i).getCell(2).setCellType(1);
          nodeConfig.setNodeName(sheet.getRow(i).getCell(2).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(3) != null) {
          sheet.getRow(i).getCell(3).setCellType(1);
          nodeConfig.setAuthorType(sheet.getRow(i).getCell(3).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(4) != null) {
          sheet.getRow(i).getCell(4).setCellType(1);
          nodeConfig.setAuthor(sheet.getRow(i).getCell(4).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(5) != null) {
          sheet.getRow(i).getCell(5).setCellType(1);
          nodeConfig.setViewUrl(sheet.getRow(i).getCell(5).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(6) != null) {
          sheet.getRow(i).getCell(6).setCellType(1);
          nodeConfig.setOrganization(sheet.getRow(i).getCell(6).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(7) != null) {
          sheet.getRow(i).getCell(7).setCellType(1);
          nodeConfig.setCreatrtime(sheet.getRow(i).getCell(7).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(8) != null) {
          sheet.getRow(i).getCell(8).setCellType(1);
          nodeConfig.setNodeLimit(sheet.getRow(i).getCell(8).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(9) != null) {
          sheet.getRow(i).getCell(9).setCellType(1);
          nodeConfig.setLimitType(sheet.getRow(i).getCell(9).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(10) != null) {
          sheet.getRow(i).getCell(10).setCellType(1);
          nodeConfig.setFlowType(sheet.getRow(i).getCell(10).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(11) != null) {
          sheet.getRow(i).getCell(11).setCellType(1);
          nodeConfig.setFlowCode(sheet.getRow(i).getCell(11).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(12) != null) {
          sheet.getRow(i).getCell(12).setCellType(1);
          nodeConfig.setLimitBench(sheet.getRow(i).getCell(12).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(13) != null) {
          sheet.getRow(i).getCell(13).setCellType(1);
          nodeConfig.setArticleType(sheet.getRow(i).getCell(13).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(14) != null) {
          sheet.getRow(i).getCell(14).setCellType(1);
          nodeConfig.setDentalJaw(sheet.getRow(i).getCell(14).getStringCellValue());
        } 
        list.add(nodeConfig);
      } 
      this.nodeConfigDao.batchSaveNodeConfig(list);
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
