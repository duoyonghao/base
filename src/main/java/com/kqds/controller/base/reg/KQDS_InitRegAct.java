package com.kqds.controller.base.reg;

import com.kqds.entity.base.KqdsReg;
import com.kqds.service.base.reg.KQDS_InitRegLogic;
import com.kqds.util.sys.YZUtility;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
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
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"KQDS_InitRegAct"})
public class KQDS_InitRegAct
{
  private Logger logger = LoggerFactory.getLogger(KQDS_InitRegAct.class);
  private static FormulaEvaluator evaluator;
  @Autowired
  private KQDS_InitRegLogic regLogic;
  
  @RequestMapping({"/batchSaveReg.act"})
  public String batchSaveReg(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String excelPath = "D:\\线上数据库还原\\西直门\\西直门挂号记录.xls";
    try
    {
      FileInputStream file = new FileInputStream(new File(excelPath));
      Workbook workbook = new HSSFWorkbook(file);
      
      Sheet sheet = workbook.getSheetAt(0);
      int row = sheet.getLastRowNum() + 1;
      System.out.println("有效行数" + row);
      List<KqdsReg> regList = new ArrayList();
      KqdsReg kqdsReg = null;
      for (int i = 1; i < row; i++)
      {
        System.err.println(i);
        kqdsReg = new KqdsReg();
        kqdsReg.setSeqId(YZUtility.getUUID());
        if (sheet.getRow(i).getCell(2) != null)
        {
          sheet.getRow(i).getCell(2).setCellType(1);
          Calendar c = new GregorianCalendar(1900, 0, -1);
          Date d = c.getTime();
          String dateTime = sheet.getRow(i).getCell(2).getStringCellValue();
          Integer time = Integer.valueOf(dateTime);
          Date date = DateUtils.addDays(d, time.intValue());
          SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
          
          Date dateChina = sdf.parse(date.toString());
          String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(dateChina);
          kqdsReg.setCreatetime(formatStr);
        }
        if (sheet.getRow(i).getCell(3) != null)
        {
          sheet.getRow(i).getCell(3).setCellType(1);
          kqdsReg.setUsercode(sheet.getRow(i).getCell(3).getStringCellValue());
        }
        if (sheet.getRow(i).getCell(8) != null)
        {
          sheet.getRow(i).getCell(8).setCellType(1);
          kqdsReg.setRegmoney(sheet.getRow(i).getCell(8).getStringCellValue());
        }
        if (sheet.getRow(i).getCell(11) != null)
        {
          sheet.getRow(i).getCell(11).setCellType(1);
          kqdsReg.setAskperson(sheet.getRow(i).getCell(11).getStringCellValue());
        }
        kqdsReg.setDel(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(13))));
        if (sheet.getRow(i).getCell(17) != null)
        {
          sheet.getRow(i).getCell(17).setCellType(1);
          kqdsReg.setUsername(sheet.getRow(i).getCell(17).getStringCellValue());
        }
        kqdsReg.setDel(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(18))));
        kqdsReg.setCjstatus(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(19))));
        if (sheet.getRow(i).getCell(22) != null)
        {
          sheet.getRow(i).getCell(22).setCellType(1);
          kqdsReg.setOrganization(sheet.getRow(i).getCell(22).getStringCellValue());
        }
        regList.add(kqdsReg);
      }
      this.regLogic.batchSaveReg(regList);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  private static String getCellValueByCell(Cell cell)
  {
    if ((cell == null) || (cell.toString().trim().equals(""))) {
      return "";
    }
    String cellValue = "";
    int cellType = cell.getCellType();
    if (cellType == 2) {
      cellType = evaluator.evaluate(cell).getCellType();
    }
    switch (cellType)
    {
    case 1: 
      cellValue = cell.getStringCellValue().trim();
      cellValue = StringUtils.isEmpty(cellValue) ? "" : cellValue;
      break;
    case 4: 
      cellValue = String.valueOf(cell.getBooleanCellValue());
      break;
    case 0: 
      if (!HSSFDateUtil.isCellDateFormatted(cell)) {
        cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
      }
      break;
    case 2: 
    case 3: 
    default: 
      cellValue = "";
    }
    return cellValue;
  }
}
