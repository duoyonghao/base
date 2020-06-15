package com.hudh.lclj.controller;

import com.alibaba.fastjson.JSON;
import com.hudh.lclj.entity.LcljOperateDetail;
import com.hudh.lclj.entity.LcljOrder;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.PreoperativeVerification;
import com.hudh.lclj.service.ILcljService;
import com.hudh.lclj.util.ParseNodesXml;
import com.kqds.util.sys.YZUtility;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/HUDH_LCLJAct"})
public class HUDH_LCLJAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_LCLJAct.class);
  
  @Autowired
  private ILcljService lcljService;
  
  @RequestMapping({"/saveLcljOrder.act"})
  public String saveLcljOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String totalTooth = request.getParameter("totalTooth");
    String blcode = request.getParameter("blcode");
    if (!YZUtility.isNullorEmpty(blcode) && !YZUtility.isNullorEmpty(totalTooth)) {
      LcljOrder lcljOrder = new LcljOrder();
      lcljOrder.setTotalTooth(totalTooth);
      lcljOrder.setBlcode(blcode);
      try {
        this.lcljService.saveLcljOrder(lcljOrder);
        YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
      } catch (Exception e) {
        YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
      } 
    } 
    return null;
  }
  
  @RequestMapping({"/findLcljOrderByBlcode.act"})
  public String findLcljOrderByBlcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String blCode = request.getParameter("blCode");
      if (!YZUtility.isNullorEmpty(blCode)) {
        JSONObject lcljOrder = this.lcljService.findLcljOrderByBlcode(blCode);
        List<JSONObject> lcljTrackList = this.lcljService.findLcljOrderTrackListByOrderNumber(lcljOrder.get("order_number").toString());
        JSONObject returnMsg = new JSONObject();
        returnMsg.put("lcljOrder", JSON.toJSONString(lcljOrder));
        returnMsg.put("lcljTrackList", JSON.toJSONString(lcljTrackList));
        YZUtility.DEAL_SUCCESS(returnMsg, null, response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findLcljOrderByBlcodeAndStu.act"})
  public String findLcljOrderByBlcodeAndStu(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String blCode = request.getParameter("blCode");
      String status = request.getParameter("status");
      if (!YZUtility.isNullorEmpty(blCode) && !YZUtility.isNullorEmpty(status)) {
        List<JSONObject> lcljOrderList = this.lcljService.findLcljOrderByBlcodeAndStu(blCode, status);
        JSONObject returnMsg = new JSONObject();
        returnMsg.put("lcljOrderList", lcljOrderList);
        YZUtility.DEAL_SUCCESS(returnMsg, null, response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/saveLcljOrderTrack.act"})
  public String saveLcljOrderTrack(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String tooth = request.getParameter("tooth");
    String orderNumber = request.getParameter("orderNumber");
    String type = request.getParameter("type");
    String bone = request.getParameter("bone");
    LcljOrderTrack lcljOrderTrack = new LcljOrderTrack();
    lcljOrderTrack.setTooth(tooth);
    lcljOrderTrack.setOrderNumber(orderNumber);
    lcljOrderTrack.setType(type);
    lcljOrderTrack.setBone(bone);
    try {
      int ssTime = this.lcljService.findLcljOrderTrackByOrderNumber(orderNumber) + 1;
      lcljOrderTrack.setSsTime((new StringBuilder(String.valueOf(ssTime))).toString());
      this.lcljService.saveLcljOrderTrackInfo(lcljOrderTrack);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateOperateNoteInfo.act"})
  public String updateOperateNoteInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String orderTrackId = request.getParameter("orderTrackId");
    String flowLink = request.getParameter("flowLink");
    String operateName = request.getParameter("operateName");
    String remake = request.getParameter("remake");
    try {
      this.lcljService.updateOperateNoteInfo(orderTrackId, flowLink, operateName, remake);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateOperateStatus.act"})
  public String updateOperateStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String ordernumber = request.getParameter("ordernumber");
    String operateName = request.getParameter("operateName");
    try {
      this.lcljService.updateOperateStatus(ordernumber, operateName);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateOperationFlowStatus.act"})
  public String updateOperationFlowStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String orderTrackId = request.getParameter("orderTrackId");
    String operateName = request.getParameter("operateName");
    String flowLink = request.getParameter("flowLink");
    try {
      this.lcljService.updateOperationFlowStatus(operateName, orderTrackId, flowLink);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findLcljOrderTrsackById.act"})
  public String findLcljOrderTrsackById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String orderTrackId = request.getParameter("orderTrackId");
      if (!YZUtility.isNullorEmpty(orderTrackId)) {
        JSONObject strOb = this.lcljService.findLcljOrderTrsackById(orderTrackId);
        YZUtility.DEAL_SUCCESS(strOb, null, response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/changeOperateStatus.act"})
  public String changeOperateStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String operateName = request.getParameter("operateName");
      String flowLink = request.getParameter("flowLink");
      String orderTrackId = request.getParameter("orderTrackId");
      if (!YZUtility.isNullorEmpty(orderTrackId) && !YZUtility.isNullorEmpty(flowLink) && !YZUtility.isNullorEmpty(operateName)) {
        this.lcljService.changeOperateStatus(operateName, flowLink, orderTrackId);
        YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findOperateByTrackIdAndLink.act"})
  public String findOperateByTrackIdAndLink(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String flowLink = request.getParameter("flowLink");
      String orderTrackId = request.getParameter("orderTrackId");
      String oprationName = request.getParameter("oprationName");
      if (!YZUtility.isNullorEmpty(orderTrackId) && !YZUtility.isNullorEmpty(flowLink)) {
        String operates = this.lcljService.findOperateByTrackIdAndLink(orderTrackId, flowLink, oprationName);
        JSONObject jo = new JSONObject();
        jo.put("operates", operates);
        YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findLcljOrderImplemenRemakeByTrackId.act"})
  public String findLcljOrderImplemenRemakeByTrackId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String orderTrackId = request.getParameter("orderTrackId");
    String flowLink = request.getParameter("flowLink");
    String oprationName = request.getParameter("oprationName");
    try {
      List<LcljOperateDetail> jsonO = this.lcljService.findLcljOrderImplemenRemakeByTrackId(orderTrackId, flowLink, oprationName);
      if (jsonO != null && jsonO.size() > 0) {
        JSONObject jo = new JSONObject();
        jo.put("remake", JSON.toJSON(jsonO));
        YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
      } else {
        YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateOrderStatus.act"})
  public String updateOrderStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String orderNumber = request.getParameter("orderNumber");
    try {
      this.lcljService.updateOrderStatus(orderNumber);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/test.act"})
  public String test(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ParseNodesXml ParseNodesXml = new ParseNodesXml();
    ParseNodesXml.getOperateMap(null);
    return null;
  }
  
  @RequestMapping({"/savePreoperativeVerification.act"})
  public String savePreoperativeVerification(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
    String upleftToothBitOne = request.getParameter("upleftToothBitOne");
    String uperRightToothBitOne = request.getParameter("uperRightToothBitOne");
    String leftLowerToothBitOne = request.getParameter("leftLowerToothBitOne");
    String lowRightToothBitOne = request.getParameter("lowRightToothBitOne");
    String upleftToothBitTwo = request.getParameter("upleftToothBitTwo");
    String uperRightToothBitTwo = request.getParameter("uperRightToothBitTwo");
    String leftLowerToothBitTwo = request.getParameter("leftLowerToothBitTwo");
    String lowRightToothBitTwo = request.getParameter("lowRightToothBitTwo");
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
    if (YZUtility.isNullorEmpty(lcljId) && YZUtility.isNullorEmpty(order_number) && YZUtility.isNullorEmpty(patient_name) && YZUtility.isNullorEmpty(patient_sex) && 
      YZUtility.isNullorEmpty(patient_age) && YZUtility.isNullorEmpty(blood_pressure) && YZUtility.isNullorEmpty(pulse) && YZUtility.isNullorEmpty(blood_glucose) && 
      YZUtility.isNullorEmpty(cruor_function) && YZUtility.isNullorEmpty(infectious_diseases) && YZUtility.isNullorEmpty(consultation_opinion) && YZUtility.isNullorEmpty(pullout_tooth) && 
      YZUtility.isNullorEmpty(pullout_tooth) && YZUtility.isNullorEmpty(plant_tooth) && YZUtility.isNullorEmpty(nurseSignature) && YZUtility.isNullorEmpty(doctorSignature) && 
      YZUtility.isNullorEmpty(signaturetime) && YZUtility.isNullorEmpty(operation_alltext) && YZUtility.isNullorEmpty(assist_operation) && YZUtility.isNullorEmpty(plant_system))
      throw new Exception("需要填写的信息不能全部为空！"); 
    try {
      JSONObject findPreoperativeVerification = this.lcljService.findPreoperativeVerification(lcljId);
      if (findPreoperativeVerification != null) {
        YZUtility.DEAL_SUCCESS(findPreoperativeVerification, "已填写，请勿重复填写！", response, this.logger);
      } else {
        JSONObject preoperativeVerification = this.lcljService.savePreoperativeVerification(pVerification);
        YZUtility.DEAL_SUCCESS(preoperativeVerification, "save ok!", response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findPreoperativeVerification.act"})
  public String findPreoperativeVerification(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String lcljId = request.getParameter("id");
    try {
      JSONObject preoperativeVerification = this.lcljService.findPreoperativeVerification(lcljId);
      YZUtility.DEAL_SUCCESS(preoperativeVerification, "find Ok!", response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  public String updatePreoperativeVerification(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String createtime = df.format(new Date());
    PreoperativeVerification pVerification = new PreoperativeVerification();
    if (!lcljId.equals(null) && !lcljId.equals(""))
      pVerification.setLclj_Id(lcljId); 
    if (!order_number.equals(null) && !order_number.equals(""))
      pVerification.setOrder_number(order_number); 
    if (!patient_name.equals(null) && !patient_name.equals(""))
      pVerification.setPatient_name(patient_name); 
    if (!patient_sex.equals(null) && !patient_sex.equals(""))
      pVerification.setPatient_sex(patient_sex); 
    if (!patient_age.equals(null) && !patient_age.equals(""))
      pVerification.setPatient_age(patient_age); 
    if (!blood_pressure.equals(null) && !blood_pressure.equals(""))
      pVerification.setBlood_pressure(blood_pressure); 
    if (!pulse.equals(null) && !pulse.equals(""))
      pVerification.setPulse(pulse); 
    if (!blood_glucose.equals(null) && !blood_glucose.equals(""))
      pVerification.setBlood_glucose(blood_glucose); 
    if (!cruor_function.equals(null) && !cruor_function.equals(""))
      pVerification.setCruor_function(cruor_function); 
    if (!infectious_diseases.equals(null) && !infectious_diseases.equals(""))
      pVerification.setInfectious_diseases(infectious_diseases); 
    if (!consultation_opinion.equals(null) && !consultation_opinion.equals(""))
      pVerification.setConsultation_opinion(consultation_opinion); 
    if (!pullout_tooth.equals(null) && !pullout_tooth.equals(""))
      pVerification.setPullout_tooth(pullout_tooth); 
    if (!plant_tooth.equals(null) && !plant_tooth.equals(""))
      pVerification.setPlant_tooth(plant_tooth); 
    if (!assist_operation.equals(null) && !assist_operation.equals(""))
      pVerification.setAssist_operation(assist_operation); 
    if (!plant_system.equals(null) && !plant_system.equals(""))
      pVerification.setPlant_system(plant_system); 
    if (!nurseSignature.equals(null) && !nurseSignature.equals(""))
      pVerification.setNurseSignature(nurseSignature); 
    if (!doctorSignature.equals(null) && !doctorSignature.equals(""))
      pVerification.setDoctorSignature(doctorSignature); 
    if (!signaturetime.equals(null) && !signaturetime.equals(""))
      pVerification.setSignaturetime(signaturetime); 
    if (!operation_alltext.equals(null) && !operation_alltext.equals(""))
      pVerification.setOperationAlltext(operation_alltext); 
    if (!createtime.equals(null) && !createtime.equals(""))
      pVerification.setCreatetime(createtime); 
    try {
      YZUtility.DEAL_SUCCESS_VALID(true, response);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
}
