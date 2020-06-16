package com.kqds.controller.ck;

import com.kqds.controller.base.member.KQDS_InitMemberAct;
import com.kqds.entity.base.KqdsCkGoodsDetail;
import com.kqds.service.ck.KQDS_CK_InitGoodsCodeLogic;
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
@RequestMapping({"KQDS_CK_InitGoodCode"})
public class KQDS_CK_InitGoodCode {
  private Logger logger = LoggerFactory.getLogger(KQDS_InitMemberAct.class);
  
  private static FormulaEvaluator evaluator;
  
  @Autowired
  private KQDS_CK_InitGoodsCodeLogic logic;
  
  @RequestMapping({"/batchUpdateGoodsCode.act"})
  public String batchSaveMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(new FileInputStream(new File("E:\\gz\\ck.xlsx")));
      Sheet sheet = xSSFWorkbook.getSheetAt(0);
      int row = sheet.getLastRowNum() + 1;
      System.out.println("有效行数" + row);
      List<KqdsCkGoodsDetail> list = new ArrayList<>();
      for (int i = 0; i < row; i++) {
        System.err.println(i);
        KqdsCkGoodsDetail kDetail = new KqdsCkGoodsDetail();
        if (sheet.getRow(i).getCell(0) != null) {
          sheet.getRow(i).getCell(0).setCellType(1);
          kDetail.setSeqId(sheet.getRow(i).getCell(0).getStringCellValue());
        } 
        if (sheet.getRow(i).getCell(1) != null) {
          sheet.getRow(i).getCell(1).setCellType(1);
          kDetail.setGoodscode(sheet.getRow(i).getCell(1).getStringCellValue());
        } 
        list.add(kDetail);
        this.logic.batchUpdateGoodsCode(list);
      } 
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
