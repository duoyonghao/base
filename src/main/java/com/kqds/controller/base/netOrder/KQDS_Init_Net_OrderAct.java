package com.kqds.controller.base.netOrder;

import com.kqds.entity.base.KqdsNetOrder;
import com.kqds.service.base.netOrder.KQDS_Init_Net_OrderLogic;
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
@RequestMapping({"KQDS_Init_Net_OrderAct"})
public class KQDS_Init_Net_OrderAct
{
  private static Logger logger = LoggerFactory.getLogger(KQDS_Init_Net_OrderAct.class);
  private static FormulaEvaluator evaluator;
  @Autowired
  private KQDS_Init_Net_OrderLogic orderLogic;
  
  @RequestMapping({"/batchSaveNetOrder.act"})
  public String batchSaveNetOrder(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String excelPath = "D:\\线上数据库还原\\西直门\\西直门网电记录.xls";
    try
    {
      FileInputStream file = new FileInputStream(new File(excelPath));
      Workbook workbook = new HSSFWorkbook(file);
      
      Sheet sheet = workbook.getSheetAt(0);
      int row = sheet.getLastRowNum() + 1;
      System.out.println("有效行数" + row);
      List<KqdsNetOrder> list = new ArrayList();
      KqdsNetOrder netOrder = null;
      for (int i = 1; i < row; i++)
      {
        System.err.println(i);
        netOrder = new KqdsNetOrder();
        if (sheet.getRow(i).getCell(0) != null)
        {
          sheet.getRow(i).getCell(0).setCellType(1);
          netOrder.setSeqId(sheet.getRow(i).getCell(0).getStringCellValue());
        }
        if (sheet.getRow(i).getCell(1) != null)
        {
          sheet.getRow(i).getCell(1).setCellType(1);
          netOrder.setCreateuser(sheet.getRow(i).getCell(1).getStringCellValue());
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
          netOrder.setCreatetime(formatStr);
        }
        if (sheet.getRow(i).getCell(3) != null)
        {
          sheet.getRow(i).getCell(3).setCellType(1);
          netOrder.setUsercode(sheet.getRow(i).getCell(3).getStringCellValue());
        }
        if (sheet.getRow(i).getCell(10) != null)
        {
          sheet.getRow(i).getCell(10).setCellType(1);
          netOrder.setAskcontent(sheet.getRow(i).getCell(10).getStringCellValue());
        }
        if (sheet.getRow(i).getCell(12) != null)
        {
          sheet.getRow(i).getCell(12).setCellType(1);
          netOrder.setOrganization(sheet.getRow(i).getCell(12).getStringCellValue());
        }
        if (sheet.getRow(i).getCell(20) != null)
        {
          sheet.getRow(i).getCell(20).setCellType(1);
          netOrder.setUsername(sheet.getRow(i).getCell(20).getStringCellValue());
        }
        netOrder.setIsdelete(Integer.valueOf(getCellValueByCell(sheet.getRow(i).getCell(26))));
        list.add(netOrder);
      }
      this.orderLogic.batchSaveNetOrder(list);
      YZUtility.DEAL_SUCCESS(null, null, response, logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, logger);
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
