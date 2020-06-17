package com.hudh.invoice.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hudh.invoice.entity.InvoiceDetail;
import com.hudh.invoice.service.InvoiceDetailService;
import com.hudh.util.HUDHUtil;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.export.ExportTable;
import com.kqds.util.sys.imports.ExcelTool;
import com.kqds.util.sys.log.BcjlUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/HUDH_InvoiceDetailAct")
public class HUDH_InvoiceDetailAct {
	private Logger logger = LoggerFactory.getLogger(HUDH_InvoiceDetailAct.class);
	/**
	 * 发票存入接口
	 */
	@Autowired
	private InvoiceDetailService invoiceDetailService;
	/**
	  * @Title: insertInvoiceDetail   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年9月7日 下午2:14:19
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertInvoiceDetail.act")
	public String insertInvoiceDetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String usercode=request.getParameter("usercode");
			YZPerson person = SessionUtil.getLoginPerson(request);
			String organization = ChainUtil.getCurrentOrganization(request);
			String invoiceList=request.getParameter("invoiceList");
			invoiceList = java.net.URLDecoder.decode(invoiceList, "UTF-8");
			List<InvoiceDetail> list = HUDHUtil.parseJsonToObjectList(invoiceList,InvoiceDetail.class);
			int i=invoiceDetailService.batchSaveInvoiceDetail(list, usercode,person,organization);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.HUDH_INVOICE_DETAIL,invoiceList, usercode, TableNameUtil.HUDH_INVOICE_DETAIL, request);
			if(i>0){
				YZUtility.DEAL_SUCCESS(null, null, response, logger);
			}
		} catch (Exception ex) {
			YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, logger);
		}
		return null;
	}
	/**
	  * @Title: updateInvoiceDetail   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年9月7日 下午2:14:26
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateInvoiceDetail.act")
	public String updateInvoiceDetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String usercode=request.getParameter("usercode");
			YZPerson person = SessionUtil.getLoginPerson(request);
			String invoiceUpdateList=request.getParameter("invoiceUpdateList");
			invoiceUpdateList = java.net.URLDecoder.decode(invoiceUpdateList, "UTF-8");
			String organization = ChainUtil.getCurrentOrganization(request);
			List<InvoiceDetail> list = HUDHUtil.parseJsonToObjectList(invoiceUpdateList,InvoiceDetail.class);
			invoiceDetailService.batchupdateInvoiceDetail(list,usercode,person,organization);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.HUDH_INVOICE_DETAIL,invoiceUpdateList, usercode, TableNameUtil.HUDH_INVOICE_DETAIL, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, logger);
		}
		return null;
	}
	/**
	  * @Title: updateDishonourInvoiceDetail   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年9月7日 下午2:14:51
	 */
	@RequestMapping("/updateDishonourInvoiceDetail.act")
	public String updateDishonourInvoiceDetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String usercode=request.getParameter("usercode");
			YZPerson person = SessionUtil.getLoginPerson(request);
			String invoiceUpdateList=request.getParameter("dataRetreatObj");
			invoiceUpdateList = java.net.URLDecoder.decode(invoiceUpdateList, "UTF-8");
			InvoiceDetail list = (InvoiceDetail) HUDHUtil.parseJsonToObject(invoiceUpdateList,InvoiceDetail.class);
			invoiceDetailService.updateDishonourInvoiceDetail(list,usercode,person);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.HUDH_INVOICE_DETAIL,invoiceUpdateList, usercode, TableNameUtil.HUDH_INVOICE_DETAIL, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, logger);
		}
		return null;
	}
	/**
	  * @Title: updateDishonourInvoiceDetail   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年9月7日 下午2:14:51
	 */
	@RequestMapping("/updateDishonourInvoiceDetailAll.act")
	public String updateDishonourInvoiceDetailAll(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String usercode=request.getParameter("usercode");
			YZPerson person = SessionUtil.getLoginPerson(request);
			invoiceDetailService.updateDishonourInvoiceDetailAll(usercode,person);
			// 记录日志
			BcjlUtil.LogBcjlWithUserCode(BcjlUtil.NEW, BcjlUtil.HUDH_INVOICE_DETAIL,"", usercode, TableNameUtil.HUDH_INVOICE_DETAIL, request);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, logger);
		}
		return null;
	}
	/**
	  * @Title: selectInvoiceDetailByUsercode   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年9月7日 下午2:14:56
	 */
	@RequestMapping("/selectInvoiceDetailByUsercode.act")
	public String selectInvoiceDetailByUsercode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String usercode=request.getParameter("usercode");
			List<JSONObject> list=invoiceDetailService.selectInvoiceDetailByUsercode(usercode);
			YZUtility.RETURN_LIST(list, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	/**
	  * @Title: selectInvoiceDetailValueByUsercode   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年9月7日 下午2:15:01
	 */
	@RequestMapping("/selectInvoiceDetailValueByUsercode.act")
	public String selectInvoiceDetailValueByUsercode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String usercode=request.getParameter("usercode");
			JSONObject json1=invoiceDetailService.selectInvoiceDetailValueByUsercode(usercode);
			JSONObject json2=invoiceDetailService.selectInvoiceDetailValueByUsercodeAndDishonour(usercode);
			JSONObject json= new JSONObject();
			json.put("invoiceValue", json1.get("invoicevalue"));
			json.put("dishonourInvoiceValue", json2.get("dishonourinvoicevalue"));
			YZUtility.RETURN_OBJ(json, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	@RequestMapping(value = "/toInvoiceLeading.act")
	public ModelAndView toInvoiceLeading(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/kqdsFront/index/jdzx/invoice_leading.jsp");
		return mv;
	} 
	/**
	 * 读取Excel数据到数据库
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/FileUploadAct", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ModelAndView FileUploadAct(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value = "files") MultipartFile[] files) throws Exception {
		ModelAndView mv =new ModelAndView();
		mv.setViewName("/kqdsFront/index/jdzx/invoice_leading.jsp");
		
//		Map<String, String> map = new HashMap<String, String>();
//		JSONObject result = new JSONObject();
		List<List<String>> list = null;
		try {
			if (null == files || files.length <= 0) {
//				map.put("result", "failure");
//				map.put("reason", "文件不存在");
			}
			// 查询已上传的文件
			String fileName="";
			for (MultipartFile file : files) {
				File dir = null;
				String docsPath = request.getSession().getServletContext().getRealPath("WEB-INF/lsfile");
				dir = new File(docsPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				fileName = file.getOriginalFilename();
//				byte[] bytes = file.getBytes();
//				File filename = new File(docsPath + fileName);
			}
//			map.put("result", "failure");
//			map.put("reason", "文件上传失败");
			// 读取Excel数据到List中
			list = ExcelTool.read(files[0].getInputStream(), ExcelTool.isExcel2003(fileName));
			invoiceDetailService.saveBatchInsert(list, request);
//			map.put("reason", result.toString());
			String Msg ="批量导入EXCEL成功！";
            request.getSession().setAttribute("msg", Msg); 
		//} //catch (SizeLimitExceededException ex) {
			//System.out.println("##########");
			//request.getSession().setAttribute("msg","文件上传失败：文件需要小于" + YZSysProps.getInt(YZSysProps.MAX_UPLOAD_FILE_SIZE) + "M");
		} catch (Exception ex) {
			//System.out.println("##########");
			request.getSession().setAttribute("msg",ex.getMessage()); 
		}
//		return map.toString();
		return mv;
  	}
	/**
	 * 根据时间查询发票详情
	 * <p>Title: selectInvoiceDetailByTime</p>  
	 * <p>Description: </p>
	 * @author lwg  
	 * @date 2019年10月10日 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectInvoiceDetailByTime.act")
	public String selectInvoiceDetailByTime(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			// 初始化分页实体类
			BootStrapPage bp = new BootStrapPage();
			// 封装参数到实体类
			BeanUtils.populate(bp, request.getParameterMap());
			String organization=request.getParameter("organization");
			String searchValue=request.getParameter("searchValue");
			String invoicestarttime=request.getParameter("invoicestarttime");
			String invoiceendtime=request.getParameter("invoiceendtime");
			String invoicestartvalue=request.getParameter("invoicestartvalue");
			String invoiceendvalue=request.getParameter("invoiceendvalue");
			String sortName=request.getParameter("sortName");
			String sortOrder=request.getParameter("sortOrder");
			Map<String,String> map=new HashMap<String,String>();
			if (!YZUtility.isNullorEmpty(organization)) {
				map.put("organization", organization);
			}
			if (!YZUtility.isNullorEmpty(searchValue)) {
				map.put("searchValue", searchValue);
			}
			if (!YZUtility.isNullorEmpty(invoicestarttime)) {
				map.put("invoicestarttime", invoicestarttime);
			}
			if (!YZUtility.isNullorEmpty(invoiceendtime)) {
				map.put("invoiceendtime", invoiceendtime + ConstUtil.TIME_END);
			}
			if (!YZUtility.isNullorEmpty(invoicestartvalue)) {
				map.put("invoicestartvalue", invoicestartvalue);
			}
			if (!YZUtility.isNullorEmpty(invoiceendvalue)) {
				map.put("invoiceendvalue", invoiceendvalue);
			}
			if (!YZUtility.isNullorEmpty(sortName)) {
				map.put("sortName", sortName);
			}
			if (!YZUtility.isNullorEmpty(sortOrder)) {
				map.put("sortOrder", sortOrder);
			}
			// 导出参数
			String flag = request.getParameter("flag") == null ? "" : request.getParameter("flag");
			String fieldArr = request.getParameter("fieldArr") == null ? "" : request.getParameter("fieldArr");
			String fieldnameArr = request.getParameter("fieldnameArr") == null ? "" : request.getParameter("fieldnameArr");
			JSONObject data = invoiceDetailService.selectInvoiceDetailByTime(bp,map);
			/*-------导出excel---------*/
			if (flag != null && flag.equals("exportTable")) {
				//"number","cancellation","invoice_kind","invoice_code","duty_parayraph","usercode","drawer","taxpayer_number","invoice_time","invoice_month","invoice_value","invoice_detail","createtime","createuser","modifier","filemtime","invalid_person","invalid_time"];
				List<JSONObject> json = (List<JSONObject>) data.getJSONArray("rows");
				if(json.size()>0){
					for (int i = 0; i < json.size(); i++) {
						json.get(i).put("number", (i+1)+"");//序号
						if(json.get(i).get("dishonour").equals("1")){
							json.get(i).put("cancellation", "是");//作废
							json.get(i).put("invalid_person", json.get(i).getString("updateuser"));//作废人
							json.get(i).put("invalid_time", json.get(i).getString("updatetime"));//作废时间
						}else{
							json.get(i).put("cancellation", "否");//作废
							json.get(i).put("modifier", json.get(i).getString("updateuser"));//修改人
							json.get(i).put("filemtime",json.get(i).getString("updatetime"));//修改时间
						}
						json.get(i).put("invoice_kind", "普通发票");//发票种类
						json.get(i).put("invoice_code", "11001800304");//发票代码
						json.get(i).put("invoice_month", json.get(i).getString("invoice_time").substring(0, 7));//开票月份
					}
				}
				ExportTable.exportBootStrapTable2Excel("发票信息查询", fieldArr, fieldnameArr, json, response, request);
				return null;
			}
			YZUtility.DEAL_SUCCESS(data, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
}
