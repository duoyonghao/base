package com.hudh.invoice.service;

import com.hudh.invoice.entity.InvoiceDetail;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public interface InvoiceDetailService {
  int batchSaveInvoiceDetail(List<InvoiceDetail> paramList, String paramString1, YZPerson paramYZPerson, String paramString2) throws Exception;
  
  void batchupdateInvoiceDetail(List<InvoiceDetail> paramList, String paramString1, YZPerson paramYZPerson, String paramString2) throws Exception;
  
  void batchupdateInvoiceDetailStatus(List<InvoiceDetail> paramList, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  int updateDishonourInvoiceDetail(InvoiceDetail paramInvoiceDetail, String paramString, YZPerson paramYZPerson) throws Exception;
  
  List<JSONObject> selectInvoiceDetailByUsercode(String paramString) throws Exception;
  
  JSONObject selectInvoiceDetailValueByUsercode(String paramString) throws Exception;
  
  JSONObject selectInvoiceDetailValueByUsercodeAndDishonour(String paramString) throws Exception;
  
  void updateDishonourInvoiceDetailAll(String paramString, YZPerson paramYZPerson) throws Exception;
  
  void saveBatchInsert(List<List<String>> paramList, HttpServletRequest paramHttpServletRequest) throws Exception;
  
  JSONObject selectInvoiceDetailByTime(BootStrapPage paramBootStrapPage, Map<String, String> paramMap) throws Exception;
}
