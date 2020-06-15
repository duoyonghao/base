package com.kqds.util.sys.export;

import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.KqdsBigDecimal;
import com.kqds.util.sys.sys.UserPrivUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExportTable
{
  public static void exportBootStrapTable2Excel(String title, String jsonNameString, String jsonValueString, List<JSONObject> dataset, HttpServletResponse response, HttpServletRequest request)
    throws Exception
  {
    Map<String, String> valueMap = getMapJson(jsonNameString, jsonValueString);
    
    HSSFWorkbook workbook = new HSSFWorkbook();
    
    HSSFSheet sheet = workbook.createSheet(title);
    
    sheet.setDefaultColumnWidth((short)15);
    
    HSSFCellStyle style = workbook.createCellStyle();
    






    style.setAlignment((short)2);
    
    HSSFFont font = workbook.createFont();
    font.setColor((short)20);
    font.setFontHeightInPoints((short)12);
    font.setBoldweight((short)700);
    
    style.setFont(font);
    
    HSSFCellStyle style2 = workbook.createCellStyle();
    





    style2.setAlignment((short)2);
    style2.setVerticalAlignment((short)1);
    
    HSSFFont font2 = workbook.createFont();
    font2.setBoldweight((short)400);
    
    style2.setFont(font2);
    

    HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
    
    HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short)4, 2, (short)6, 5));
    
    comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
    
    comment.setAuthor("leno");
    

    HSSFRow row = sheet.createRow(0);
    int mapvalueindex = 0;
    for (String value : valueMap.values())
    {
      HSSFCell cell = row.createCell(mapvalueindex);
      cell.setCellStyle(style);
      HSSFRichTextString text = new HSSFRichTextString(value);
      cell.setCellValue(text);
      mapvalueindex++;
    }
    String canExportPhoneNumber = UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag14_canExportPhoneNumber, request);
    
    Object it = dataset.iterator();
    int index = 0;
    Iterator localIterator2;
    for (; ((Iterator)it).hasNext(); localIterator2.hasNext())
    {
      index++;
      row = sheet.createRow(index);
      JSONObject t = (JSONObject)((Iterator)it).next();
      int mapkeyindex = 0;
      localIterator2 = valueMap.keySet().iterator(); continue;String field = (String)localIterator2.next();
      HSSFCell cell = row.createCell(mapkeyindex);
      cell.setCellStyle(style2);
      try
      {
        String textValue = "";
        if ((t.getString(field) != null) && (!t.getString(field).equals("null")) && (!field.toLowerCase().contains("phonenumber".toLowerCase()))) {
          textValue = t.getString(field).toString();
        } else if ("1".equals(canExportPhoneNumber)) {
          textValue = t.getString(field).toString();
        }
        cell.setCellValue(textValue);
      }
      catch (Exception e)
      {
        if (!field.toLowerCase().contains("rownumber".toLowerCase())) {
          cell.setCellValue("");
        } else {
          cell.setCellValue(index);
        }
      }
      mapkeyindex++;
    }
    try
    {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      workbook.write(os);
      byte[] content = os.toByteArray();
      InputStream is = new ByteArrayInputStream(content);
      
      response.reset();
      response.setContentType("application/vnd.ms-excel;charset=utf-8");
      response.setHeader("Content-Disposition", "attachment;filename=" + new String(new StringBuilder(String.valueOf(title)).append(".xls").toString().getBytes(), "iso-8859-1"));
      ServletOutputStream out = response.getOutputStream();
      BufferedInputStream bis = null;
      BufferedOutputStream bos = null;
      try
      {
        bis = new BufferedInputStream(is);
        bos = new BufferedOutputStream(out);
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
        {
          int bytesRead;
          bos.write(buff, 0, bytesRead);
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      finally
      {
        if (bis != null) {
          bis.close();
        }
        if (bos != null) {
          bos.close();
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static void exportExcel4PaiBan(String title, List<JSONObject> perlist, String startdate, String enddate, HttpServletResponse response)
    throws Exception
  {
    HSSFWorkbook workbook = new HSSFWorkbook();
    
    HSSFSheet sheet = workbook.createSheet(title);
    
    sheet.setDefaultColumnWidth((short)15);
    
    HSSFCellStyle style = workbook.createCellStyle();
    
    style.setAlignment((short)2);
    
    HSSFFont font = workbook.createFont();
    font.setColor((short)20);
    font.setFontHeightInPoints((short)12);
    font.setBoldweight((short)700);
    
    style.setFont(font);
    
    HSSFCellStyle style2 = workbook.createCellStyle();
    style2.setAlignment((short)2);
    style2.setVerticalAlignment((short)1);
    
    HSSFFont font2 = workbook.createFont();
    font2.setBoldweight((short)400);
    
    style2.setFont(font2);
    

    HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
    
    HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short)4, 2, (short)6, 5));
    
    comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
    
    comment.setAuthor("leno");
    

    List<String> datelist = YZUtility.getAllDate(startdate, enddate);
    HSSFRow row = sheet.createRow(0);
    HSSFCell cell = row.createCell(0);
    cell.setCellStyle(style);
    HSSFRichTextString text = new HSSFRichTextString("用户\\日期");
    cell.setCellValue(text);
    for (int i = 0; i < datelist.size(); i++)
    {
      cell = row.createCell(i + 1);
      cell.setCellStyle(style);
      text = new HSSFRichTextString((String)datelist.get(i));
      cell.setCellValue(text);
    }
    for (int i = 0; i < perlist.size(); i++)
    {
      row = sheet.createRow(i + 1);
      cell = row.createCell(0);
      cell.setCellStyle(style);
      text = new HSSFRichTextString(((JSONObject)perlist.get(i)).getString("user_id"));
      cell.setCellValue(text);
      for (int j = 0; j < datelist.size(); j++)
      {
        cell = row.createCell(j + 1);
        cell.setCellStyle(style);
        text = new HSSFRichTextString("值班");
        cell.setCellValue(text);
      }
    }
    try
    {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      workbook.write(os);
      byte[] content = os.toByteArray();
      InputStream is = new ByteArrayInputStream(content);
      
      response.reset();
      response.setContentType("application/vnd.ms-excel;charset=utf-8");
      response.setHeader("Content-Disposition", "attachment;filename=" + new String(new StringBuilder(String.valueOf(title)).append(".xls").toString().getBytes(), "iso-8859-1"));
      ServletOutputStream out = response.getOutputStream();
      BufferedInputStream bis = null;
      BufferedOutputStream bos = null;
      try
      {
        bis = new BufferedInputStream(is);
        bos = new BufferedOutputStream(out);
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
        {
          int bytesRead;
          bos.write(buff, 0, bytesRead);
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      finally
      {
        if (bis != null) {
          bis.close();
        }
        if (bos != null) {
          bos.close();
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  private static Map<String, String> getMapJson(String jsonNameString, String jsonValueString)
    throws Exception
  {
    if (jsonNameString == null) {
      throw new Exception("表头不存在");
    }
    if (jsonValueString == null) {
      throw new Exception("表头不存在");
    }
    JSONArray jsonname = JSONArray.fromObject(jsonNameString);
    JSONArray jsonvalue = JSONArray.fromObject(jsonValueString);
    Map<String, String> valueMap = new LinkedHashMap();
    if (jsonname.size() > 0) {
      for (int i = 0; i < jsonname.size(); i++)
      {
        String key = jsonname.getString(i);
        String value = jsonvalue.getString(i);
        valueMap.put(key, value);
      }
    }
    return valueMap;
  }
  
  public static ExportBean exportBootStrapTable2ExcelByResult(ExportBean bean, List<JSONObject> result, String methodName)
    throws Exception
  {
    Map<String, String> valueMap = bean.getNamevals();
    
    String canExportPhoneNumber = UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag14_canExportPhoneNumber, bean.getRequest());
    


    CellStyle style2 = bean.getWorkbook().createCellStyle();
    style2.setAlignment((short)2);
    style2.setVerticalAlignment((short)1);
    
    Font font2 = bean.getWorkbook().createFont();
    font2.setBoldweight((short)400);
    
    style2.setFont(font2);
    



















    List<String> membernoList = new ArrayList();
    
    Row row = null;
    int index = bean.getIndex();
    for (int i = 0; i < result.size(); i++)
    {
      index++;
      JSONObject obj = (JSONObject)result.get(i);
      if ("member_center".equals(methodName))
      {
        String memberstatus = obj.getString("memberstatus");
        if (ConstUtil.MEMBER_STATUS_0.equals(memberstatus)) {
          memberstatus = ConstUtil.MEMBER_STATUS_0_DESC;
        } else if (ConstUtil.MEMBER_STATUS_1.equals(memberstatus)) {
          memberstatus = ConstUtil.MEMBER_STATUS_1_DESC;
        } else if (ConstUtil.MEMBER_STATUS_2.equals(memberstatus)) {
          memberstatus = ConstUtil.MEMBER_STATUS_2_DESC;
        } else if (ConstUtil.MEMBER_STATUS_3.equals(memberstatus)) {
          memberstatus = ConstUtil.MEMBER_STATUS_3_DESC;
        }
        obj.put("memberstatus", memberstatus);
        float money = 0.0F;
        if (!YZUtility.isNullorEmpty(obj.getString("money"))) {
          money = Float.parseFloat(obj.getString("money"));
        }
        float givemoney = 0.0F;
        if (!YZUtility.isNullorEmpty(obj.getString("givemoney"))) {
          givemoney = Float.parseFloat(obj.getString("givemoney"));
        }
        obj.put("totalmoney", Float.valueOf(givemoney + money));
        

        String memberno = obj.getString("memberno");
        if ((membernoList == null) || (membernoList.size() == 0)) {
          membernoList.add(memberno);
        } else {
          for (String mno : membernoList) {
            if (memberno.equals(mno)) {
              break;
            }
          }
        }
      }
      if ("userdoc_selectNoPage".equals(methodName))
      {
        String ywhf = obj.getString("ywhf");
        if ("0".equals(ywhf)) {
          ywhf = "无回访";
        } else {
          ywhf = "有回访";
        }
        obj.put("ywhf", ywhf);
        String doorstatus = obj.getString("doorstatus");
        if ("1".equals(doorstatus)) {
          doorstatus = "已上门";
        } else {
          doorstatus = "未上门";
        }
        obj.put("doorstatusname", doorstatus);
      }
      if ("costOrder_selectNoPage".equals(methodName))
      {
        String cjstatus = obj.getString("cjstatus");
        if ("0".equals(cjstatus)) {
          obj.put("cjstatus", "未成交");
        } else {
          obj.put("cjstatus", "已成交");
        }
      }
      if ("costOrderDetail_selectNoPage".equals(methodName))
      {
        obj.put("classname", obj.getString("dict_name"));
        obj.put("nextname", obj.getString("dict_name_2"));
        obj.put("cjstatus", obj.getString("cjstatus").equals("0") ? "未成交" : "已成交");
        obj.put("type", obj.getString("type").equals("0") ? "否" : "是");
        obj.put("istsxm", obj.getString("istsxm").equals("0") ? "否" : "是");
        
        boolean flag = true;
        if ((KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("y2")), new BigDecimal(0)) <= 0) && (!YZUtility.isNullorEmpty(obj.getString("qfbh"))) && 
          (KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("subtotal")), BigDecimal.ZERO) == 0) && 
          (KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("voidmoney")), BigDecimal.ZERO) == 0)) {
          flag = false;
        }
        String ys = "0.00";
        String paymoney = YZUtility.isNullorEmpty(obj.getString("paymoney")) ? "0.00" : obj.getString("paymoney");
        String arrearmoney = YZUtility.isNullorEmpty(obj.getString("arrearmoney")) ? "0.00" : obj.getString("arrearmoney");
        if ((flag) || (obj.getString("istk").equals("1"))) {
          ys = new BigDecimal(paymoney).add(new BigDecimal(arrearmoney)).toString();
        }
        obj.put("ys", ys);
        String payother2 = YZUtility.isNullorEmpty(obj.getString("payother2")) ? "0.00" : obj.getString("payother2");
        String paydjq = YZUtility.isNullorEmpty(obj.getString("paydjq")) ? "0.00" : obj.getString("paydjq");
        String payintegral = YZUtility.isNullorEmpty(obj.getString("payintegral")) ? "0.00" : obj.getString("payintegral");
        obj.put("paymoney", new BigDecimal(paymoney).subtract(new BigDecimal(payother2)).subtract(new BigDecimal(paydjq)).subtract(new BigDecimal(payintegral)).toString());
      }
      BigDecimal arrearmoney;
      if ("selectNoPageOrder".equals(methodName))
      {
        obj.put("classname", obj.getString("dict_name"));
        obj.put("nextname", obj.getString("dict_name_2"));
        obj.put("cjstatus", obj.getString("cjstatus").equals("0") ? "未成交" : "已成交");
        obj.put("type", obj.getString("type").equals("0") ? "否" : "是");
        obj.put("istsxm", obj.getString("istsxm").equals("0") ? "否" : "是");
        
        boolean flag = true;
        if ((KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("y2")), new BigDecimal(0)) <= 0) && (!YZUtility.isNullorEmpty(obj.getString("qfbh"))) && 
          (KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("subtotal")), BigDecimal.ZERO) == 0) && 
          (KqdsBigDecimal.compareTo(new BigDecimal(obj.getString("voidmoney")), BigDecimal.ZERO) == 0)) {
          flag = false;
        }
        BigDecimal paymoney = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("paymoney")) ? "0.00" : obj.getString("paymoney"));
        arrearmoney = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("arrearmoney")) ? "0.00" : obj.getString("arrearmoney"));
        
        BigDecimal ys = BigDecimal.ZERO;
        if ((flag) || (obj.getString("istk").equals("1"))) {
          ys = paymoney.add(arrearmoney);
        }
        obj.put("ys", ys.toString());
        
        BigDecimal payother2 = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("payother2")) ? "0.00" : obj.getString("payother2"));
        BigDecimal paydjq = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("paydjq")) ? "0.00" : obj.getString("paydjq"));
        BigDecimal payintegral = new BigDecimal(YZUtility.isNullorEmpty(obj.getString("payintegral")) ? "0.00" : obj.getString("payintegral"));
        obj.put("paymoney", paymoney.subtract(payother2).subtract(paydjq).subtract(payintegral));
        String payyjj = YZUtility.isNullorEmpty(obj.getString("payyjj")) ? "0.00" : obj.getString("payyjj");
        obj.put("realmoney", new BigDecimal(obj.getString("paymoney")).subtract(new BigDecimal(payyjj)).setScale(2, 4).toString());
        obj.put("cmoney", "0.00");
        obj.put("cgivemoney", "0.00");
        obj.put("ctotal", "0.00");
      }
      if ("selectListOrder".equals(methodName))
      {
        obj.put("classname", "预交金");
        obj.put("nextname", obj.getString("type"));
        obj.put("sftime", obj.getString("createtime"));
        if (obj.has("sfr")) {
          obj.put("sfuser", obj.getString("sfr"));
        } else {
          obj.put("sfuser", "");
        }
        obj.put("kduser", "");
        obj.put("kdtime", "");
        obj.put("cjstatus", "");
        obj.put("nurse", "");
        obj.put("techperson", "");
        obj.put("remark", "");
        if (obj.has("kfr")) {
          obj.put("developer", obj.getString("kfr"));
        } else {
          obj.put("developer", "");
        }
        if (obj.has("jsr")) {
          obj.put("introducer", obj.getString("jsr"));
        } else {
          obj.put("introducer", "");
        }
        obj.put("type", "");
        if (obj.has("content")) {
          obj.put("remark", obj.getString("content"));
        } else {
          obj.put("remark", "");
        }
        obj.put("seqIdMember", obj.getString("seq_id"));
        obj.put("costno", "");
        obj.put("itemname", "");
        obj.put("itemno", "");
        obj.put("unit", "");
        obj.put("unitprice", "");
        obj.put("detailremark", "");
        obj.put("num", "");
        obj.put("discount", "");
        obj.put("realmoney", YZUtility.isNullorEmpty(obj.getString("cmoney")) ? "0.00" : new BigDecimal(obj.getString("cmoney")));
        obj.put("cmoney", YZUtility.isNullorEmpty(obj.getString("cmoney")) ? "0.00" : new BigDecimal(obj.getString("cmoney")));
        obj.put("cgivemoney", YZUtility.isNullorEmpty(obj.getString("cgivemoney")) ? "0.00" : new BigDecimal(obj.getString("cgivemoney")));
        obj.put("ctotal", YZUtility.isNullorEmpty(obj.getString("ctotal")) ? "0.00" : new BigDecimal(obj.getString("ctotal")));
        obj.put("subtotal", "");
        obj.put("arrearmoney", "");
        obj.put("ys", "");
        obj.put("paymoney", "");
        obj.put("voidmoney", "");
        
        obj.put("status", "");
        obj.put("regno", "");
        obj.put("paydjq", "");
        obj.put("payintegral", "");
        if (obj.has("xjmoney")) {
          obj.put("payxj", YZUtility.isNullorEmpty(obj.getString("xjmoney")) ? "0.00" : new BigDecimal(obj.getString("xjmoney")));
        } else {
          obj.put("payxj", "");
        }
        if (obj.has("yhkmoney")) {
          obj.put("paybank", YZUtility.isNullorEmpty(obj.getString("yhkmoney")) ? "0.00" : new BigDecimal(obj.getString("yhkmoney")));
        } else {
          obj.put("paybank", "");
        }
        if (obj.has("wxmoney")) {
          obj.put("paywx", YZUtility.isNullorEmpty(obj.getString("wxmoney")) ? "0.00" : new BigDecimal(obj.getString("wxmoney")));
        } else {
          obj.put("paywx", "");
        }
        if (obj.has("zfbmoney")) {
          obj.put("payzfb", YZUtility.isNullorEmpty(obj.getString("zfbmoney")) ? "0.00" : new BigDecimal(obj.getString("zfbmoney")));
        } else {
          obj.put("payzfb", "");
        }
        if (obj.has("mmdmoney")) {
          obj.put("paymmd", YZUtility.isNullorEmpty(obj.getString("mmdmoney")) ? "0.00" : new BigDecimal(obj.getString("mmdmoney")));
        } else {
          obj.put("paymmd", "");
        }
        if (obj.has("bdfqmoney")) {
          obj.put("paybdfq", YZUtility.isNullorEmpty(obj.getString("bdfqmoney")) ? "0.00" : new BigDecimal(obj.getString("bdfqmoney")));
        } else {
          obj.put("paybdfq", "");
        }
        if (obj.has("qtmoney")) {
          obj.put("payother1", YZUtility.isNullorEmpty(obj.getString("qtmoney")) ? "0.00" : new BigDecimal(obj.getString("qtmoney")));
        } else {
          obj.put("payother1", "");
        }
        obj.put("payyjj", "");
        obj.put("payyb", "");
        obj.put("payother2", "");
        obj.put("payother3", "");
        obj.put("istsxm", "");
        obj.put("y2", "");
        obj.put("istk", "");
        obj.put("issplit", "");
      }
      row = bean.getSheet().createRow(index);
      int mapkeyindex = 0;
      for (String field : valueMap.keySet())
      {
        Cell cell = row.createCell(mapkeyindex);
        cell.setCellStyle(style2);
        try
        {
          String textValue = "";
          if ((obj.getString(field) != null) && (!obj.getString(field).equals("null")) && (!field.toLowerCase().contains("phonenumber".toLowerCase()))) {
            textValue = obj.getString(field).toString();
          } else if ("1".equals(canExportPhoneNumber)) {
            textValue = obj.getString(field).toString();
          }
          cell.setCellValue(textValue);
        }
        catch (Exception e)
        {
          if (!field.toLowerCase().contains("rownumber".toLowerCase())) {
            cell.setCellValue("");
          } else {
            cell.setCellValue(index);
          }
        }
        mapkeyindex++;
      }
    }
    bean.setIndex(index);
    
    return bean;
  }
  
  public static ExportBean initExcel4RsWrite(String title, String jsonNameString, String jsonValueString, HttpServletResponse response, HttpServletRequest request)
    throws Exception
  {
    Map<String, String> valueMap = getMapJson(jsonNameString, jsonValueString);
    
    SXSSFWorkbook workbook = new SXSSFWorkbook();
    
    Sheet sheet = workbook.createSheet(title);
    
    sheet.setDefaultColumnWidth(15);
    
    CellStyle style = workbook.createCellStyle();
    
    style.setAlignment((short)2);
    
    Font font = workbook.createFont();
    font.setColor((short)20);
    font.setFontHeightInPoints((short)12);
    font.setBoldweight((short)700);
    
    style.setFont(font);
    











    Row row = sheet.createRow(0);
    int mapvalueindex = 0;
    for (String value : valueMap.values())
    {
      Cell cell = row.createCell(mapvalueindex);
      cell.setCellStyle(style);
      HSSFRichTextString text = new HSSFRichTextString(value);
      cell.setCellValue(text);
      mapvalueindex++;
    }
    ExportBean bean = new ExportBean();
    bean.setNames(jsonNameString);
    bean.setRequest(request);
    bean.setResponse(response);
    bean.setSheet(sheet);
    bean.setValues(jsonValueString);
    bean.setWorkbook(workbook);
    bean.setNamevals(valueMap);
    
    return bean;
  }
  
  public static void writeExcel4DownLoad(String title, SXSSFWorkbook workbook, HttpServletResponse response)
  {
    try
    {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      workbook.write(os);
      byte[] content = os.toByteArray();
      InputStream is = new ByteArrayInputStream(content);
      
      response.reset();
      response.setContentType("application/vnd.ms-excel;charset=utf-8");
      response.setHeader("Content-Disposition", "attachment;filename=" + new String(new StringBuilder(String.valueOf(title)).append(".xlsx").toString().getBytes(), "iso-8859-1"));
      ServletOutputStream out = response.getOutputStream();
      BufferedInputStream bis = null;
      BufferedOutputStream bos = null;
      try
      {
        bis = new BufferedInputStream(is);
        bos = new BufferedOutputStream(out);
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
        {
          int bytesRead;
          bos.write(buff, 0, bytesRead);
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      finally
      {
        if (bis != null) {
          bis.close();
        }
        if (bos != null) {
          bos.close();
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
