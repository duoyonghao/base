package com.hudh.invoice.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.invoice.entity.InvoiceDetail;
import com.kqds.dao.DaoSupport;

import net.sf.json.JSONObject;
@Service
public class InvoiceDetailDao {
	@Autowired
	private DaoSupport dao;
	/**
	 * 2019-09-03 lwg
	 * 批量保存发票数据
	 * @param list
	 * @throws Exception
	 */
	public void batchSaveInvoiceDetail(List<InvoiceDetail> list) throws Exception{
		dao.batchUpdate("HUDH_INVOICE_DETAIL.insertInvoiceDetail", list);
	}
	/**
	 * 2019-09-03 lwg
	 * 批量修改发票数据
	 * @param list
	 * @throws Exception
	 */
	public void batchupdateInvoiceDetail(List<InvoiceDetail> list) throws Exception{
		dao.batchUpdate("HUDH_INVOICE_DETAIL.updateInvoiceDetail", list);
	}
	/**
	 * 2019-09-03 lwg
	 * 根据患者编号查询发票详情
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectInvoiceDetailByUsercode(String usercode) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_INVOICE_DETAIL.selectInvoiceDetailByUsercode", usercode);
	}
	
	/**
	 * 2019-09-03 lwg
	 * 修改发票退票数据
	 * @param invoiceDetail
	 * @return i
	 * @throws Exception
	 */
	public int updateDishonourInvoiceDetail(InvoiceDetail invoiceDetail) throws Exception{
		int i=(int) dao.update("HUDH_INVOICE_DETAIL.updateDishonourInvoiceDetail", invoiceDetail);
		return i;
	}
	
	/**
	 * 2019-09-03 lwg
	 * 根据患者编号查询开票详情
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectInvoiceDetailValueByUsercode(String usercode) throws Exception{
		return (JSONObject) dao.findForObject("HUDH_INVOICE_DETAIL.selectInvoiceDetailValueByUsercode", usercode);
	}
	
	/**
	 * 2019-09-03 lwg
	 * 根据患者编号查询退票详情
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject selectInvoiceDetailValueByUsercodeAndDishonour(String usercode) throws Exception{
		return (JSONObject) dao.findForObject("HUDH_INVOICE_DETAIL.selectInvoiceDetailValueByUsercodeAndDishonour", usercode);
	}
	/**   
	  * @Title: findInvoiceDetailByuserCodeAndstatus   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param map      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年9月7日 下午4:02:25
	  */  
	@SuppressWarnings("unchecked")
	public List<JSONObject> findInvoiceDetailByuserCodeAndstatus(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>) dao.findForList("HUDH_INVOICE_DETAIL.findInvoiceDetailByuserCodeAndstatus", map);	
	}
	/**   
	  * @Title: updateDishonourInvoiceDetailAll   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param invoiceDetail      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年9月7日 下午4:49:52
	  */  
	public void updateDishonourInvoiceDetailAll(InvoiceDetail invoiceDetail) throws Exception {
		// TODO Auto-generated method stub
		dao.update("HUDH_INVOICE_DETAIL.updateDishonourInvoiceDetailAll", invoiceDetail);
	}
	/**
	 * 根据时间查询发票详情
	 * <p>Title: selectInvoiceDetailByTime</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月10日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectInvoiceDetailByTime(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return (List<JSONObject>) dao.findForList("HUDH_INVOICE_DETAIL.selectInvoiceDetailByTime", map);	
	}
	/**
	 * 根据时间查询金额
	 * <p>Title: selectInvoiceValueByTime</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月11日 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<JSONObject> selectInvoiceValueByTime(Map<String, String> map) throws Exception{
		return (List<JSONObject>) dao.findForList("HUDH_INVOICE_DETAIL.selectInvoiceValueByTime", map);
	}
	
}
