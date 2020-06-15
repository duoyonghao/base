package com.kqds.controller.base.member;

import com.kqds.entity.base.KqdsMember;
import com.kqds.service.base.member.KQDS_InitMemberLogic;
import com.kqds.util.sys.YZUtility;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
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
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"KQDS_InitMemberAct"})
public class KQDS_InitMemberAct
{
  private Logger logger = LoggerFactory.getLogger(KQDS_InitMemberAct.class);
  private static FormulaEvaluator evaluator;
  @Autowired
  private KQDS_InitMemberLogic memberLogic;
  
  @RequestMapping({"/batchSaveMember.act"})
  public String batchSaveMember(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String excelPath = "D:\\数据（lclj）excel\\会员卡数据.xlsx";
    try
    {
      FileInputStream file = new FileInputStream(new File(excelPath));
      Workbook workbook = new XSSFWorkbook(file);
      

      Sheet sheet = workbook.getSheetAt(0);
      int row = sheet.getLastRowNum() + 1;
      System.out.println("有效行数" + row);
      List<KqdsMember> list = new ArrayList();
      KqdsMember member = null;
      for (int i = 1; i < row; i++)
      {
        System.err.println(i);
        member = new KqdsMember();
        member.setSeqId(YZUtility.getUUID());
        if (sheet.getRow(i).getCell(0) != null)
        {
          sheet.getRow(i).getCell(0).setCellType(1);
          member.setCreateuser(sheet.getRow(i).getCell(0).getStringCellValue());
        }
        if (sheet.getRow(i).getCell(1) != null)
        {
          sheet.getRow(i).getCell(1).setCellType(1);
          Calendar c = new GregorianCalendar(1900, 0, -1);
          Date d = c.getTime();
          String dateTime = sheet.getRow(i).getCell(1).getStringCellValue();
          Integer time = Integer.valueOf(dateTime);
          Date date = DateUtils.addDays(d, time.intValue());
          SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
          
          Date dateChina = sdf.parse(date.toString());
          
          String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(dateChina);
          member.setCreatetime(formatStr);
        }
        if (sheet.getRow(i).getCell(2) != null)
        {
          sheet.getRow(i).getCell(2).setCellType(1);
          member.setUsercode(sheet.getRow(i).getCell(2).getStringCellValue());
        }
        if (sheet.getRow(i).getCell(3) != null)
        {
          sheet.getRow(i).getCell(3).setCellType(1);
          member.setUsername(sheet.getRow(i).getCell(3).getStringCellValue());
        }
        member.setDiscount(Integer.valueOf(100));
        member.setMemberno(member.getUsercode());
        member.setMemberlevel("277");
        member.setMemberstatus("1");
        member.setMoney(BigDecimal.ZERO);
        member.setGivemoney(BigDecimal.ZERO);
        member.setPassword(member.getMemberno().substring(member.getMemberno().length() - 6, member.getMemberno().length()));
        member.setRemark("预交金卡");
        member.setOrganization("HUDX");
        member.setIcno(member.getUsercode());
        member.setIsbinding(Integer.valueOf(1));
        list.add(member);
      }
      this.memberLogic.batchSaveMember(list);
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
