package com.kqds.util.sys.export;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExportBean
{
  private SXSSFWorkbook workbook;
  private Sheet sheet;
  private int index;
  private String names;
  private String values;
  private Map<String, String> namevals;
  private HttpServletResponse response;
  private HttpServletRequest request;
  
  public int getIndex()
  {
    return this.index;
  }
  
  public void setIndex(int index)
  {
    this.index = index;
  }
  
  public Map<String, String> getNamevals()
  {
    return this.namevals;
  }
  
  public void setNamevals(Map<String, String> namevals)
  {
    this.namevals = namevals;
  }
  
  public String getNames()
  {
    return this.names;
  }
  
  public void setNames(String names)
  {
    this.names = names;
  }
  
  public String getValues()
  {
    return this.values;
  }
  
  public void setValues(String values)
  {
    this.values = values;
  }
  
  public HttpServletResponse getResponse()
  {
    return this.response;
  }
  
  public void setResponse(HttpServletResponse response)
  {
    this.response = response;
  }
  
  public HttpServletRequest getRequest()
  {
    return this.request;
  }
  
  public void setRequest(HttpServletRequest request)
  {
    this.request = request;
  }
  
  public SXSSFWorkbook getWorkbook()
  {
    return this.workbook;
  }
  
  public void setWorkbook(SXSSFWorkbook workbook)
  {
    this.workbook = workbook;
  }
  
  public Sheet getSheet()
  {
    return this.sheet;
  }
  
  public void setSheet(Sheet sheet)
  {
    this.sheet = sheet;
  }
}
