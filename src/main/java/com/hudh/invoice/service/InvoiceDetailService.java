package com.hudh.invoice.service;

import com.hudh.invoice.entity.InvoiceDetail;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public abstract interface InvoiceDetailService
{
  public abstract int batchSaveInvoiceDetail(List<InvoiceDetail> paramList, String paramString1, YZPerson paramYZPerson, String paramString2)
    throws Exception;
  
  public abstract void batchupdateInvoiceDetail(List<InvoiceDetail> paramList, String paramString1, YZPerson paramYZPerson, String paramString2)
    throws Exception;
  
  public abstract void batchupdateInvoiceDetailStatus(List<InvoiceDetail> paramList, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract int updateDishonourInvoiceDetail(InvoiceDetail paramInvoiceDetail, String paramString, YZPerson paramYZPerson)
    throws Exception;
  
  public abstract List<JSONObject> selectInvoiceDetailByUsercode(String paramString)
    throws Exception;
  
  public abstract JSONObject selectInvoiceDetailValueByUsercode(String paramString)
    throws Exception;
  
  public abstract JSONObject selectInvoiceDetailValueByUsercodeAndDishonour(String paramString)
    throws Exception;
  
  public abstract void updateDishonourInvoiceDetailAll(String paramString, YZPerson paramYZPerson)
    throws Exception;
  
  public abstract void saveBatchInsert(List<List<String>> paramList, HttpServletRequest paramHttpServletRequest)
    throws Exception;
  
  public abstract JSONObject selectInvoiceDetailByTime(BootStrapPage paramBootStrapPage, Map<String, String> paramMap)
    throws Exception;
}
