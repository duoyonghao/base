package com.hudh.lclj.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.enterprise.inject.New;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hudh.lclj.service.ILcljService;
import com.hudh.lclj.util.ParseNodesXml;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import com.hudh.lclj.entity.LcljOperateDetail;
import com.hudh.lclj.entity.LcljOrder;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.PreoperativeVerification;
/**
 * 临床路径操作Controller
 * @author XKY
 *
 */
@Controller
@RequestMapping("/HUDH_LCLJAct")
public class HUDH_LCLJAct {
	private Logger logger = LoggerFactory.getLogger(HUDH_LCLJAct.class);
	/**
	 * 临床路径数据保存接口
	 */
	@Autowired
	private ILcljService lcljService; 
	
	/**
	 * 保存临床路径信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveLcljOrder.act")
	public String saveLcljOrder(HttpServletRequest request,
			HttpServletResponse response)  throws Exception{
		String totalTooth = request.getParameter("totalTooth");
		String blcode = request.getParameter("blcode");
		if(!YZUtility.isNullorEmpty(blcode) && !YZUtility.isNullorEmpty(totalTooth)) {
			LcljOrder lcljOrder = new LcljOrder();
			lcljOrder.setTotalTooth(totalTooth);
			lcljOrder.setBlcode(blcode);
			try {
				lcljService.saveLcljOrder(lcljOrder);
				YZUtility.DEAL_SUCCESS(null,null, response, logger);
			} catch (Exception e) {
				YZUtility.DEAL_ERROR(null, false, e, response, logger);
			}
		}
		return null;
	}
	
	/**
	 * 根据病历号查询患者的临床路径信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findLcljOrderByBlcode.act")
	public String findLcljOrderByBlcode(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try {
			String blCode = request.getParameter("blCode");
			if(!YZUtility.isNullorEmpty(blCode)) {
				JSONObject lcljOrder = lcljService.findLcljOrderByBlcode(blCode);
				List<JSONObject> lcljTrackList = lcljService.findLcljOrderTrackListByOrderNumber(lcljOrder.get("order_number").toString());
				JSONObject returnMsg = new JSONObject();
				returnMsg.put("lcljOrder", JSON.toJSONString(lcljOrder));
				returnMsg.put("lcljTrackList", JSON.toJSONString(lcljTrackList));
				YZUtility.DEAL_SUCCESS(returnMsg, null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据病历号查询未完成患者的临床路径信息是否存在
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findLcljOrderByBlcodeAndStu.act")
	public String findLcljOrderByBlcodeAndStu(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try {
			String blCode = request.getParameter("blCode");
			String status = request.getParameter("status");
			if(!YZUtility.isNullorEmpty(blCode) && !YZUtility.isNullorEmpty(status)) {
				List<JSONObject> lcljOrderList = lcljService.findLcljOrderByBlcodeAndStu(blCode, status);
				JSONObject returnMsg = new JSONObject();
				returnMsg.put("lcljOrderList", lcljOrderList);
				YZUtility.DEAL_SUCCESS(returnMsg,null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 保存临床路径跟踪信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveLcljOrderTrack.act")
	public String saveLcljOrderTrack(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String tooth = request.getParameter("tooth"); //本次手术牙齿颗数
		String orderNumber = request.getParameter("orderNumber"); //编号
		String type = request.getParameter("type"); //跟踪方式
		String bone = request.getParameter("bone"); //是否植骨
		LcljOrderTrack lcljOrderTrack = new LcljOrderTrack();
		lcljOrderTrack.setTooth(tooth);
		lcljOrderTrack.setOrderNumber(orderNumber);
		lcljOrderTrack.setType(type);
		lcljOrderTrack.setBone(bone);
		try {
			//根据编号获取该编号下手术次数并 +1
			int ssTime =  lcljService.findLcljOrderTrackByOrderNumber(orderNumber) + 1;
			lcljOrderTrack.setSsTime(ssTime+"");
			//保存临床跟踪数据
			lcljService.saveLcljOrderTrackInfo(lcljOrderTrack);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 记录医生临床跟踪下每次操作完的记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateOperateNoteInfo.act")
	public String updateOperateNoteInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String orderTrackId = request.getParameter("orderTrackId"); //临床跟踪路径id
		String flowLink = request.getParameter("flowLink"); //当前所处临床环节
		String operateName = request.getParameter("operateName"); //当前操作项目的名称
		String remake = request.getParameter("remake"); //添加的备注信息
		try {
			lcljService.updateOperateNoteInfo(orderTrackId, flowLink, operateName, remake);
			YZUtility.DEAL_SUCCESS(null,null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 前台操作项目点完成修改当前操作项目的状态
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateOperateStatus.act")
	public String updateOperateStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String ordernumber = request.getParameter("ordernumber");
		String operateName = request.getParameter("operateName");
		try {
			lcljService.updateOperateStatus(ordernumber, operateName);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 更改具体手术流程操作项的状态
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateOperationFlowStatus.act")
	public String updateOperationFlowStatus(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String orderTrackId = request.getParameter("orderTrackId");
		String operateName = request.getParameter("operateName");
		String flowLink = request.getParameter("flowLink");
		try {
			lcljService.updateOperationFlowStatus(operateName, orderTrackId, flowLink);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据orderTrack表id获取一次手术的跟踪信息及操作详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findLcljOrderTrsackById.act")
	public String findLcljOrderTrsackById(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try {
			String orderTrackId = request.getParameter("orderTrackId");
			if(!YZUtility.isNullorEmpty(orderTrackId)) {
				JSONObject strOb = lcljService.findLcljOrderTrsackById(orderTrackId);
				YZUtility.DEAL_SUCCESS(strOb,null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 更改具体流程操作的状态
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/changeOperateStatus.act")
	public String changeOperateStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String operateName = request.getParameter("operateName");
			String flowLink = request.getParameter("flowLink");
			String orderTrackId = request.getParameter("orderTrackId");
			if(!YZUtility.isNullorEmpty(orderTrackId) && !YZUtility.isNullorEmpty(flowLink) && !YZUtility.isNullorEmpty(operateName)) {
				lcljService.changeOperateStatus(operateName, flowLink, orderTrackId);
				YZUtility.DEAL_SUCCESS(null, null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 查询具体流程下面具体操作的信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findOperateByTrackIdAndLink.act")
	public String findOperateByTrackIdAndLink(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String flowLink = request.getParameter("flowLink");
			String orderTrackId = request.getParameter("orderTrackId");
			String oprationName = request.getParameter("oprationName");
			if(!YZUtility.isNullorEmpty(orderTrackId) && !YZUtility.isNullorEmpty(flowLink)) {
				String operates = lcljService.findOperateByTrackIdAndLink(orderTrackId, flowLink,oprationName);
				JSONObject jo = new JSONObject();
				jo.put("operates", operates);
				YZUtility.DEAL_SUCCESS(jo, null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 根据临床跟踪表id获取环节下面操作项的备注信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findLcljOrderImplemenRemakeByTrackId.act")
	public String findLcljOrderImplemenRemakeByTrackId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderTrackId = request.getParameter("orderTrackId"); 
		String flowLink = request.getParameter("flowLink");
		String oprationName = request.getParameter("oprationName");
		try {
			List<LcljOperateDetail> jsonO = lcljService.findLcljOrderImplemenRemakeByTrackId(orderTrackId,flowLink,oprationName);
			if(null != jsonO && jsonO.size() > 0) {
				JSONObject jo = new JSONObject();
				jo.put("remake", JSON.toJSON(jsonO));
				YZUtility.DEAL_SUCCESS(jo,null, response, logger);
			}else {
				YZUtility.DEAL_SUCCESS(null,null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 *  更新一次完整临床路径的状态########################
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/updateOrderStatus.act")
	public String updateOrderStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderNumber = request.getParameter("orderNumber");
		try {
			lcljService.updateOrderStatus(orderNumber);
			YZUtility.DEAL_SUCCESS(null, null, response, logger);
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
	
	/**
	 * 查询当前患者有无正在做的未完成的手术
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping("/findHasOrderByBlcodeAndStu.act")
	public String findHasOrderByBlcodeAndStu(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String orderNumber = request.getParameter("orderNumber");
		try {
			if(!YZUtility.isNullorEmpty(orderNumber)) {
				int ssTime = lcljService.findHasOrderByBlcodeAndStu(orderNumber);
				JSONObject jsObject = new JSONObject();
				jsObject.put(ssTime, ssTime);
				YZUtility.DEAL_SUCCESS(jsObject,null, response, logger);
			}
		} catch (Exception e) {
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		
		return null;
	}*/
	
	/**
	 * 测试
	 * @throws Exception 
	 */
	@RequestMapping("/test.act")
	public String test(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		ParseNodesXml ParseNodesXml = new ParseNodesXml();
		ParseNodesXml.getOperateMap(null);
		return null;
	}
	
	/**
	  * @Title: savePreoperativeVerification   
	  * @Description: TODO(保存术前核查单)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年5月28日 上午10:57:52
	 */
	@RequestMapping(value = "/savePreoperativeVerification.act")
	public String savePreoperativeVerification(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	String SEQ_ID = YZUtility.getUUID();
	String lcljId = request.getParameter("id");
	String order_number = request.getParameter("order_number");
	String patient_name = request.getParameter("username");
	String patient_sex = request.getParameter("sex");
	String patient_age = request.getParameter("age");
	String blood_pressure = request.getParameter("blood_pressure");
	String pulse = request.getParameter("pulse");
	String blood_glucose = request.getParameter("blood_glucose");
	String cruor_function = request.getParameter("cruor_function");
	String infectious_diseases = request.getParameter("infectious_diseases");
	String consultation_opinion = request.getParameter("consultation_opinion");
	String pullout_tooth = request.getParameter("pullout_tooth");
	String plant_tooth = request.getParameter("plant_tooth");
	String assist_operation = request.getParameter("assist_operation");
	String plant_system = request.getParameter("plant_system");
	String nurseSignature = request.getParameter("nurseSignature");
	String doctorSignature = request.getParameter("doctorSignature");
	String signaturetime = request.getParameter("signaturetime");
	String operation_alltext = request.getParameter("operation_alltext");
	//添加字段
	String upleftToothBitOne = request.getParameter("upleftToothBitOne");
	String uperRightToothBitOne = request.getParameter("uperRightToothBitOne");
	String leftLowerToothBitOne = request.getParameter("leftLowerToothBitOne");
	String lowRightToothBitOne = request.getParameter("lowRightToothBitOne");
	String upleftToothBitTwo = request.getParameter("upleftToothBitTwo");
	String uperRightToothBitTwo = request.getParameter("uperRightToothBitTwo");
	String leftLowerToothBitTwo = request.getParameter("leftLowerToothBitTwo");
	String lowRightToothBitTwo = request.getParameter("lowRightToothBitTwo");
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	String createtime = df.format(new Date());
	
	PreoperativeVerification pVerification = new PreoperativeVerification();
	 pVerification.setSEQ_ID(SEQ_ID);
	 pVerification.setLclj_Id(lcljId);
	 pVerification.setOrder_number(order_number);
	 pVerification.setPatient_name(patient_name);
	 pVerification.setPatient_sex(patient_sex);
	 pVerification.setPatient_age(patient_age);
	 pVerification.setBlood_pressure(blood_pressure);
	 pVerification.setPulse(pulse);
	 pVerification.setBlood_glucose(blood_glucose);
	 pVerification.setCruor_function(cruor_function);
	 pVerification.setInfectious_diseases(infectious_diseases);
	 pVerification.setConsultation_opinion(consultation_opinion);
	 pVerification.setPullout_tooth(pullout_tooth);
	 pVerification.setPlant_tooth(plant_tooth);
	 pVerification.setAssist_operation(assist_operation);
	 pVerification.setPlant_system(plant_system);
	 pVerification.setNurseSignature(nurseSignature);
	 pVerification.setDoctorSignature(doctorSignature);
	 pVerification.setSignaturetime(signaturetime);
	 pVerification.setOperationAlltext(operation_alltext);
	 pVerification.setCreatetime(createtime);
	 
	 pVerification.setUpleftToothBitOne(upleftToothBitOne);
	 pVerification.setUperRightToothBitOne(uperRightToothBitOne);
	 pVerification.setUperRightToothBitTwo(uperRightToothBitTwo);
	 pVerification.setUpleftToothBitOne(upleftToothBitOne);
	 pVerification.setUpleftToothBitTwo(upleftToothBitTwo);
	 pVerification.setLeftLowerToothBitOne(leftLowerToothBitOne);
	 pVerification.setLeftLowerToothBitTwo(leftLowerToothBitTwo);
	 pVerification.setLowRightToothBitOne(lowRightToothBitOne);
	 pVerification.setLowRightToothBitTwo(lowRightToothBitTwo);
	 
   	 if(YZUtility.isNullorEmpty(lcljId) && YZUtility.isNullorEmpty(order_number)  && YZUtility.isNullorEmpty(patient_name) && YZUtility.isNullorEmpty(patient_sex)
   			 && YZUtility.isNullorEmpty(patient_age) && YZUtility.isNullorEmpty(blood_pressure) && YZUtility.isNullorEmpty(pulse)  && YZUtility.isNullorEmpty(blood_glucose)
   			 && YZUtility.isNullorEmpty(cruor_function) && YZUtility.isNullorEmpty(infectious_diseases) && YZUtility.isNullorEmpty(consultation_opinion)  &&YZUtility.isNullorEmpty(pullout_tooth)
   			 && YZUtility.isNullorEmpty(pullout_tooth) && YZUtility.isNullorEmpty(plant_tooth)  && YZUtility.isNullorEmpty(nurseSignature) && YZUtility.isNullorEmpty(doctorSignature) && 
   			 YZUtility.isNullorEmpty(signaturetime) && YZUtility.isNullorEmpty(operation_alltext) &&YZUtility.isNullorEmpty(assist_operation) && YZUtility.isNullorEmpty(plant_system)){
   		 throw new Exception("需要填写的信息不能全部为空！");
   	 }
   	 try {
   		JSONObject findPreoperativeVerification = lcljService.findPreoperativeVerification(lcljId);
   		if(findPreoperativeVerification != null){
   			YZUtility.DEAL_SUCCESS(findPreoperativeVerification, "已填写，请勿重复填写！", response, logger);
   		}else{  			
   			JSONObject preoperativeVerification = lcljService.savePreoperativeVerification(pVerification);
   			YZUtility.DEAL_SUCCESS(preoperativeVerification, "save ok!", response, logger);
   		}
	} catch (Exception e) {
		// TODO: handle exception
		YZUtility.DEAL_ERROR(null, false, e, response, logger);
	}
				return null;
	}
	
	/**
	  * @Title: findPreoperativeVerification   
	  * @Description: TODO(查询术前核查单)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return      
	  * @return: String
	 * @throws Exception 
	  * @dateTime:2019年5月28日 上午10:59:37
	 */
	@RequestMapping(value = "/findPreoperativeVerification.act")
	public String findPreoperativeVerification(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String lcljId = request.getParameter("id");
		try {
			JSONObject preoperativeVerification = lcljService.findPreoperativeVerification(lcljId);
			YZUtility.DEAL_SUCCESS(preoperativeVerification, "find Ok!", response, logger);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
				return null;
	}
	/**
	  * @Title: updatePreoperativeVerification   
	  * @Description: TODO(这里用一句话描述这个方法的作用)   
	  * @param: @param request
	  * @param: @param response
	  * @param: @return
	  * @param: @throws Exception      
	  * @return: String
	  * @dateTime:2019年7月13日 上午10:04:09
	 */
	public String updatePreoperativeVerification(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String SEQ_ID = YZUtility.getUUID();
		String lcljId = request.getParameter("id");
		String order_number = request.getParameter("order_number");
		String patient_name = request.getParameter("username");
		String patient_sex = request.getParameter("sex");
		String patient_age = request.getParameter("age");
		String blood_pressure = request.getParameter("blood_pressure");
		String pulse = request.getParameter("pulse");
		String blood_glucose = request.getParameter("blood_glucose");
		String cruor_function = request.getParameter("cruor_function");
		String infectious_diseases = request.getParameter("infectious_diseases");
		String consultation_opinion = request.getParameter("consultation_opinion");
		String pullout_tooth = request.getParameter("pullout_tooth");
		String plant_tooth = request.getParameter("plant_tooth");
		String assist_operation = request.getParameter("assist_operation");
		String plant_system = request.getParameter("plant_system");
		String nurseSignature = request.getParameter("nurseSignature");
		String doctorSignature = request.getParameter("doctorSignature");
		String signaturetime = request.getParameter("signaturetime");
		String operation_alltext = request.getParameter("operation_alltext");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String createtime = df.format(new Date());
		
		PreoperativeVerification pVerification = new PreoperativeVerification();
		if(!lcljId.equals(null) && !lcljId.equals("")){
			 pVerification.setLclj_Id(lcljId);
		}
		if(!order_number.equals(null) && !order_number.equals("")){
			 pVerification.setOrder_number(order_number);	
				}
		if(!patient_name.equals(null) && !patient_name.equals("")){
			 pVerification.setPatient_name(patient_name);
		}
		if(!patient_sex.equals(null) && !patient_sex.equals("")){
			 pVerification.setPatient_sex(patient_sex);
		}
		if(!patient_age.equals(null) && !patient_age.equals("")){
			 pVerification.setPatient_age(patient_age);
		}
		if(!blood_pressure.equals(null) && !blood_pressure.equals("")){
		     pVerification.setBlood_pressure(blood_pressure);
		}
		if(!pulse.equals(null) && !pulse.equals("")){
			 pVerification.setPulse(pulse);
		}
		if(!blood_glucose.equals(null) && !blood_glucose.equals("")){
			 pVerification.setBlood_glucose(blood_glucose);
			 
		}
		if(!cruor_function.equals(null) && !cruor_function.equals("")){
			 pVerification.setCruor_function(cruor_function);		
				}
		if(!infectious_diseases.equals(null) && !infectious_diseases.equals("")){
			 pVerification.setInfectious_diseases(infectious_diseases);
		}
		if(!consultation_opinion.equals(null) && !consultation_opinion.equals("")){
			 pVerification.setConsultation_opinion(consultation_opinion);
		}
		if(!pullout_tooth.equals(null) && !pullout_tooth.equals("")){
			 pVerification.setPullout_tooth(pullout_tooth);
		}
		if(!plant_tooth.equals(null) && !plant_tooth.equals("")){
			 pVerification.setPlant_tooth(plant_tooth);	
		}
		if(!assist_operation.equals(null) && !assist_operation.equals("")){
			 pVerification.setAssist_operation(assist_operation);
		}if(!plant_system.equals(null) && !plant_system.equals("")){
			 pVerification.setPlant_system(plant_system);
		}
		if(!nurseSignature.equals(null) && !nurseSignature.equals("")){
			 pVerification.setNurseSignature(nurseSignature);		
				}
		if(!doctorSignature.equals(null) && !doctorSignature.equals("")){
			 pVerification.setDoctorSignature(doctorSignature);
		}
		if(!signaturetime.equals(null) && !signaturetime.equals("")){
			 pVerification.setSignaturetime(signaturetime);
		}
		if(!operation_alltext.equals(null) && !operation_alltext.equals("")){
			 pVerification.setOperationAlltext(operation_alltext);
		}
		if(!createtime.equals(null) && !createtime.equals("")){
			pVerification.setCreatetime(createtime);
		}
		 try {
		
			YZUtility.DEAL_SUCCESS_VALID(true, response);
		} catch (Exception e) {
			// TODO: handle exception
			YZUtility.DEAL_ERROR(null, false, e, response, logger);
		}
		return null;
	}
}
