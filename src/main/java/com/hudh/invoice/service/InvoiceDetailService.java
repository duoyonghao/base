package com.hudh.invoice.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hudh.invoice.entity.InvoiceDetail;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;

import net.sf.json.JSONObject;


public interface InvoiceDetailService {
	/**
	  * @Title: batchSaveInvoiceDetail   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param list
	  * @param: @param usercode
	  * @param: @param person
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: int
	  * @dateTime:2019年9月7日 下午2:13:25
	 */
	int batchSaveInvoiceDetail(List<InvoiceDetail> list,String usercode,YZPerson person,String organization) throws Exception;
	/**
	  * @Title: batchupdateInvoiceDetail   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param list
	  * @param: @param usercode
	  * @param: @param person
	  * @param: @throws Exception      
	  * @return: void
	  * @dateTime:2019年9月7日 下午2:13:33
	 */
	void batchupdateInvoiceDetail(List<InvoiceDetail> list,String usercode,YZPerson person,String organization) throws Exception;
	/**
	  * @Title: batchupdateInvoiceDetailStatus   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param list
	  * @param: @param request
	  * @param: @throws Exception      
	  * @return: void
	  * @dateTime:2019年9月7日 下午2:13:39
	 */
	void batchupdateInvoiceDetailStatus(List<InvoiceDetail> list,HttpServletRequest request) throws Exception;
	/**
	  * @Title: updateDishonourInvoiceDetail   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param invoiceDetail
	  * @param: @param usercode
	  * @param: @param person
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: int
	  * @dateTime:2019年9月7日 下午2:13:46
	 */
	int updateDishonourInvoiceDetail(InvoiceDetail invoiceDetail,String usercode,YZPerson person) throws Exception;
	/**
	  * @Title: selectInvoiceDetailByUsercode   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param usercode
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: List<JSONObject>
	  * @dateTime:2019年9月7日 下午2:13:56
	 */
	List<JSONObject> selectInvoiceDetailByUsercode(String usercode) throws Exception;
	/**
	  * @Title: selectInvoiceDetailValueByUsercode   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param usercode
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: JSONObject
	  * @dateTime:2019年9月7日 下午2:14:01
	 */
	JSONObject selectInvoiceDetailValueByUsercode(String usercode) throws Exception;
	/**
	  * @Title: selectInvoiceDetailValueByUsercodeAndDishonour   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param usercode
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: JSONObject
	  * @dateTime:2019年9月7日 下午2:14:05
	 */
	JSONObject selectInvoiceDetailValueByUsercodeAndDishonour(String usercode) throws Exception;
	/**   
	  * @Title: updateDishonourInvoiceDetailAll   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param usercode
	  * @param: @param person      
	  * @return: void
	 * @throws Exception 
	  * @dateTime:2019年9月7日 下午4:42:07
	  */  
	void updateDishonourInvoiceDetailAll(String usercode, YZPerson person) throws Exception;
	
	void saveBatchInsert(List<List<String>> list, HttpServletRequest request) throws Exception;
	/**
	 * 根据时间查询发票详情
	 * <p>Title: selectInvoiceDetailByTime</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月11日 
	 * @param bp
	 * @param map
	 * @return
	 * @throws Exception
	 */
	JSONObject selectInvoiceDetailByTime(BootStrapPage bp, Map<String, String> map) throws Exception;
}
