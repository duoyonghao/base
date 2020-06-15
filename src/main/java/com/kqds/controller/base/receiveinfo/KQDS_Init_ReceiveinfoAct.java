package com.kqds.controller.base.receiveinfo;

import com.kqds.entity.base.KqdsReceiveinfo;
import com.kqds.service.base.receiveinfo.KQDS_Init_ReceiveinfoLogic;
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
@RequestMapping({"KQDS_Init_ReceiveinfoAct"})
public class KQDS_Init_ReceiveinfoAct
{
  private Logger logger = LoggerFactory.getLogger(KQDS_Init_ReceiveinfoAct.class);
  private static FormulaEvaluator evaluator;
  @Autowired
  private KQDS_Init_ReceiveinfoLogic receiveLogic;
  
  @RequestMapping({"/batchSaveReceiveinfo.act"})
  public String batchSaveReceiveinfo(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String excelPath = "D:\\线上数据库还原\\西直门\\西直门咨询记录.xls";
    try
    {
      FileInputStream file = new FileInputStream(new File(excelPath));
      Workbook workbook = new HSSFWorkbook(file);
      
      Sheet sheet = workbook.getSheetAt(0);
      int row = sheet.getLastRowNum() + 1;
      System.out.println("有效行数" + row);
      List<KqdsReceiveinfo> receList = new ArrayList();
      KqdsReceiveinfo receiveinfo = null;
      for (int i = 1; i < row; i++)
      {
        System.err.println(i);
        receiveinfo = new KqdsReceiveinfo();
        if (sheet.getRow(i).getCell(0) != null)
        {
          sheet.getRow(i).getCell(0).setCellType(1);
          receiveinfo.setSeqId(sheet.getRow(i).getCell(0).getStringCellValue());
        }
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
          receiveinfo.setCreatetime(formatStr);
        }
        if (sheet.getRow(i).getCell(3) != null)
        {
          sheet.getRow(i).getCell(3).setCellType(1);
          receiveinfo.setUsercode(sheet.getRow(i).getCell(3).getStringCellValue());
        }
        if (sheet.getRow(i).getCell(4) != null)
        {
          sheet.getRow(i).getCell(4).setCellType(1);
          receiveinfo.setUsername(sheet.getRow(i).getCell(4).getStringCellValue());
        }
        if (sheet.getRow(i).getCell(6) != null)
        {
          sheet.getRow(i).getCell(6).setCellType(1);
          receiveinfo.setRegno(sheet.getRow(i).getCell(6).getStringCellValue());
        }
        if (sheet.getRow(i).getCell(9) != null)
        {
          sheet.getRow(i).getCell(9).setCellType(1);
          receiveinfo.setDetaildesc(sheet.getRow(i).getCell(9).getStringCellValue());
        }
        receiveinfo.setAskstatus(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(11))));
        if (sheet.getRow(i).getCell(12) != null)
        {
          sheet.getRow(i).getCell(12).setCellType(1);
          receiveinfo.setOrganization(sheet.getRow(i).getCell(12).getStringCellValue());
        }
        receList.add(receiveinfo);
      }
      this.receiveLogic.batchSaveReceiveinfo(receList);
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
