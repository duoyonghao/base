package com.hudh.invoice.dao;

import com.hudh.invoice.entity.InvoiceDetail;
import com.kqds.dao.DaoSupport;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceDetailDao {
  @Autowired
  private DaoSupport dao;
  
  public void batchSaveInvoiceDetail(List<InvoiceDetail> list) throws Exception {
    this.dao.batchUpdate("HUDH_INVOICE_DETAIL.insertInvoiceDetail", list);
  }
  
  public void batchupdateInvoiceDetail(List<InvoiceDetail> list) throws Exception {
    this.dao.batchUpdate("HUDH_INVOICE_DETAIL.updateInvoiceDetail", list);
  }
  
  public List<JSONObject> selectInvoiceDetailByUsercode(String usercode) throws Exception {
    return (List<JSONObject>)this.dao.findForList("HUDH_INVOICE_DETAIL.selectInvoiceDetailByUsercode", usercode);
  }
  
  public int updateDishonourInvoiceDetail(InvoiceDetail invoiceDetail) throws Exception {
    int i = ((Integer)this.dao.update("HUDH_INVOICE_DETAIL.updateDishonourInvoiceDetail", invoiceDetail)).intValue();
    return i;
  }
  
  public JSONObject selectInvoiceDetailValueByUsercode(String usercode) throws Exception {
    return (JSONObject)this.dao.findForObject("HUDH_INVOICE_DETAIL.selectInvoiceDetailValueByUsercode", usercode);
  }
  
  public JSONObject selectInvoiceDetailValueByUsercodeAndDishonour(String usercode) throws Exception {
    return (JSONObject)this.dao.findForObject("HUDH_INVOICE_DETAIL.selectInvoiceDetailValueByUsercodeAndDishonour", usercode);
  }
  
  public List<JSONObject> findInvoiceDetailByuserCodeAndstatus(Map<String, String> map) throws Exception {
    return (List<JSONObject>)this.dao.findForList("HUDH_INVOICE_DETAIL.findInvoiceDetailByuserCodeAndstatus", map);
  }
  
  public void updateDishonourInvoiceDetailAll(InvoiceDetail invoiceDetail) throws Exception {
    this.dao.update("HUDH_INVOICE_DETAIL.updateDishonourInvoiceDetailAll", invoiceDetail);
  }
  
  public List<JSONObject> selectInvoiceDetailByTime(Map<String, String> map) throws Exception {
    return (List<JSONObject>)this.dao.findForList("HUDH_INVOICE_DETAIL.selectInvoiceDetailByTime", map);
  }
  
  public List<JSONObject> selectInvoiceValueByTime(Map<String, String> map) throws Exception {
    return (List<JSONObject>)this.dao.findForList("HUDH_INVOICE_DETAIL.selectInvoiceValueByTime", map);
  }
}
