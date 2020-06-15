package com.hudh.lclj.controller;

import com.alibaba.fastjson.JSON;
import com.hudh.lclj.entity.LcljNodeConfig;
import com.hudh.lclj.entity.LcljOptRecode;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.LcljWorklist;
import com.hudh.lclj.entity.OperatingRecord;
import com.hudh.lclj.service.IFlowOperateService;
import com.hudh.lclj.service.IFlowService;
import com.hudh.util.HUDHUtil;
import com.hudh.zzbl.service.DzblService;
import com.hudh.zzbl.service.IRepairSchemeConfirmService;
import com.hudh.zzbl.service.IZzblCheckService;
import com.hudh.zzbl.service.IZzblService;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPara;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.export.ExportBean;
import com.kqds.util.sys.export.ExportTable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/HUDH_FlowAct"})
public class HUDH_FlowAct {
  private Logger logger = LoggerFactory.getLogger(HUDH_FlowAct.class);
  
  @Autowired
  private IFlowService flowService;
  
  @Autowired
  private IZzblService zzblService;
  
  @Autowired
  private IZzblCheckService zzblCheckService;
  
  @Autowired
  private IRepairSchemeConfirmService rscService;
  
  @Autowired
  private DzblService dzblService;
  
  @Autowired
  private IFlowOperateService flowOperateService;
  
  @RequestMapping({"/saveLcljOrderTrack.act"})
  public String saveLcljOrderTrack(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String blcode = request.getParameter("blcode");
    String tooth_total = request.getParameter("tooth_total");
    String ssType = request.getParameter("ssType");
    String isBone = request.getParameter("isBone");
    String left_up = request.getParameter("left_up");
    String left_down = request.getParameter("left_down");
    String right_up = request.getParameter("right_up");
    String right_down = request.getParameter("right_down");
    String counsellor = request.getParameter("counsellor");
    String plant_physician = request.getParameter("plant_physician");
    String repair_physician = request.getParameter("repair_physician");
    String clinic_nurse = request.getParameter("clinic_nurse");
    String customer_service = request.getParameter("customer_service");
    String plant_system = request.getParameter("plant_system");
    String crown_material = request.getParameter("crown_material");
    String tooth_texture = request.getParameter("tooth_texture");
    String imageological_examination = request.getParameter("imageological_examination");
    String consultation = request.getParameter("consultation");
    String advisory = request.getParameter("advisory");
    String repair_left_up = request.getParameter("repair_left_up");
    String repair_left_down = request.getParameter("repair_left_down");
    String repair_right_up = request.getParameter("repair_right_up");
    String repair_right_down = request.getParameter("repair_right_down");
    String zz_tooth_total = request.getParameter("zz_tooth_total");
    String modeoperation = request.getParameter("modeoperation");
    String remark = request.getParameter("remark");
    String abutment_station = request.getParameter("abutment_station");
    String id = request.getParameter("id");
    String userdocument_id = request.getParameter("seqId");
    LcljOrderTrack lcljOrderTrack = new LcljOrderTrack();
    lcljOrderTrack.setBlcode(blcode);
    lcljOrderTrack.setTooth(tooth_total);
    lcljOrderTrack.setType(ssType);
    lcljOrderTrack.setBone(isBone);
    YZPerson person = SessionUtil.getLoginPerson(request);
    lcljOrderTrack.setCreatorid(person.getSeqId());
    lcljOrderTrack.setCreatorname(person.getUserName());
    lcljOrderTrack.setCounsellor(counsellor);
    lcljOrderTrack.setPlantPhysician(plant_physician);
    lcljOrderTrack.setClinicNurse(clinic_nurse);
    lcljOrderTrack.setRepairPphysician(repair_physician);
    lcljOrderTrack.setCustomerService(customer_service);
    lcljOrderTrack.setPlantSystem(plant_system);
    lcljOrderTrack.setCrownMaterial(crown_material);
    lcljOrderTrack.setTooth_texture(tooth_texture);
    lcljOrderTrack.setImageologicalExamination(imageological_examination);
    lcljOrderTrack.setConsultation(consultation);
    lcljOrderTrack.setAdvisory(advisory);
    lcljOrderTrack.setRepairLeftUp(repair_left_up);
    lcljOrderTrack.setRepairLeftDown(repair_left_down);
    lcljOrderTrack.setRepairRightUp(repair_right_up);
    lcljOrderTrack.setRepairRightDown(repair_right_down);
    lcljOrderTrack.setRepairToothTotal(zz_tooth_total);
    lcljOrderTrack.setModeoperation(modeoperation);
    lcljOrderTrack.setRemark(remark);
    lcljOrderTrack.setAbutment_station(abutment_station);
    lcljOrderTrack.setUserdocument_id(userdocument_id);
    if (YZUtility.isNotNullOrEmpty(left_up)) {
      left_up = left_up.substring(0, left_up.length() - 1);
      lcljOrderTrack.setLeftUp(left_up);
    } 
    if (YZUtility.isNotNullOrEmpty(left_down)) {
      left_down = left_down.substring(0, left_down.length() - 1);
      lcljOrderTrack.setLeftDown(left_down);
    } 
    if (YZUtility.isNotNullOrEmpty(right_up)) {
      right_up = right_up.substring(0, right_up.length() - 1);
      lcljOrderTrack.setRightUp(right_up);
    } 
    if (YZUtility.isNotNullOrEmpty(right_down)) {
      right_down = right_down.substring(0, right_down.length() - 1);
      lcljOrderTrack.setRightDown(right_down);
    } 
    if (YZUtility.isNotNullOrEmpty(repair_left_up)) {
      repair_left_up = repair_left_up.substring(0, repair_left_up.length() - 1);
      lcljOrderTrack.setRepairLeftUp(repair_left_up);
    } 
    if (YZUtility.isNotNullOrEmpty(repair_left_down)) {
      repair_left_down = repair_left_down.substring(0, repair_left_down.length() - 1);
      lcljOrderTrack.setRepairLeftDown(repair_left_down);
    } 
    if (YZUtility.isNotNullOrEmpty(repair_right_up)) {
      repair_right_up = repair_right_up.substring(0, repair_right_up.length() - 1);
      lcljOrderTrack.setRepairRightUp(repair_right_up);
    } 
    if (YZUtility.isNotNullOrEmpty(repair_right_down)) {
      repair_right_down = repair_right_down.substring(0, repair_right_down.length() - 1);
      lcljOrderTrack.setRepairRightDown(repair_right_down);
    } 
    try {
      this.flowService.saveLcljOrderTrackInfo(lcljOrderTrack, id, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/gotoNextNode.act"})
  public String gotoNextNode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String orderNumber = request.getParameter("orderNumber");
    String isShouShu = request.getParameter("isShouShu");
    try {
      this.flowOperateService.submitFlow(orderNumber, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
      if (isShouShu.equals("SHOUSHU")) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("orderNumber", orderNumber);
        String leftUp = request.getParameter("leftUp");
        String leftDown = request.getParameter("leftDown");
        String rightUp = request.getParameter("rightUp");
        String rightDown = request.getParameter("rightDown");
        String repairLeftUp = request.getParameter("repairLeftUp");
        String repairLeftDown = request.getParameter("repairLeftDown");
        String repairRightUp = request.getParameter("repairRightUp");
        String repairRightDown = request.getParameter("repairRightDown");
        String tooth = request.getParameter("toothTotal");
        String repairToothTotal = request.getParameter("repairToothTotal");
        if (YZUtility.isNotNullOrEmpty(leftUp))
          dataMap.put("leftUp", leftUp); 
        if (YZUtility.isNotNullOrEmpty(leftDown))
          dataMap.put("leftDown", leftDown); 
        if (YZUtility.isNotNullOrEmpty(rightUp))
          dataMap.put("rightUp", rightUp); 
        if (YZUtility.isNotNullOrEmpty(rightDown))
          dataMap.put("rightDown", rightDown); 
        if (YZUtility.isNotNullOrEmpty(repairLeftUp))
          dataMap.put("repairLeftUp", repairLeftUp); 
        if (YZUtility.isNotNullOrEmpty(repairLeftDown))
          dataMap.put("repairLeftDown", repairLeftDown); 
        if (YZUtility.isNotNullOrEmpty(repairRightUp))
          dataMap.put("repairRightUp", repairRightUp); 
        if (YZUtility.isNotNullOrEmpty(repairRightDown))
          dataMap.put("repairRightDown", repairRightDown); 
        if (YZUtility.isNotNullOrEmpty(tooth))
          dataMap.put("tooth", tooth); 
        if (YZUtility.isNotNullOrEmpty(repairToothTotal))
          dataMap.put("repairToothTotal", repairToothTotal); 
        this.flowService.updateOrderTrack(dataMap);
      } 
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/reject.act"})
  public String reject(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String orderNumber = request.getParameter("orderNumber");
    try {
      this.flowOperateService.rejectFlow(orderNumber, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/saveOptRecode.act"})
  public String saveOptRecode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String orderNumber = request.getParameter("orderNumber");
    String remarks = request.getParameter("remarks");
    String variation = request.getParameter("variation");
    LcljOptRecode lcljOptRecode = new LcljOptRecode();
    lcljOptRecode.setOrderNumber(orderNumber);
    lcljOptRecode.setRemarks(remarks);
    lcljOptRecode.setVariation(variation);
    lcljOptRecode.setCreator(person.getUserName());
    try {
      this.flowService.saveOptRecode(lcljOptRecode);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findOptRecodeList.act"})
  public String findOptRecodeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String orderNumber = request.getParameter("orderNumber");
    String searchFlowink = request.getParameter("searchFlowink");
    if (YZUtility.isNullorEmpty(searchFlowink))
      searchFlowink = null; 
    try {
      if (YZUtility.isNotNullOrEmpty(orderNumber)) {
        List<JSONObject> data = this.flowService.findOptRecodeList(orderNumber, searchFlowink);
        JSONObject jo = new JSONObject();
        jo.put("remarks", JSON.toJSON(data));
        YZPerson person = SessionUtil.getLoginPerson(request);
        List<JSONObject> list = this.flowService.findLcljAdminOrAgency(request);
        jo.put("hasPass", "NO");
        if (list != null)
          for (JSONObject object : list) {
            if (person.getUserId().equals(object.get("para_value")) || person.getSeqId().equals(object.get("para_value"))) {
              jo.put("hasPass", "YES");
              break;
            } 
          }  
        YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findRegListByBlcode.act"})
  public String findRegListByBlcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String orderNumber = request.getParameter("orderNumber");
    try {
      if (YZUtility.isNotNullOrEmpty(orderNumber)) {
        List<KqdsReg> data = this.flowService.findRegListByBlcode(orderNumber);
        JSONObject jo = new JSONObject();
        jo.put("regs", JSON.toJSON(data));
        YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @ResponseBody
  @RequestMapping({"/findOrderTrackInfo.act"})
  public String findOrderTrackInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String orderNumber = request.getParameter("orderNumber");
    try {
      if (YZUtility.isNotNullOrEmpty(orderNumber)) {
        JSONObject data = this.flowService.findOrderTrackInfo(orderNumber);
        String nodes = data.getString("flowinfo");
        List<LcljNodeConfig> nodeList = HUDHUtil.parseJsonToObjectList(nodes, LcljNodeConfig.class);
        List<LcljWorklist> hadhList = this.flowOperateService.findHadWorkList(orderNumber, request);
        Map<String, LcljNodeConfig> nodeListMap = new HashMap<>();
        for (LcljNodeConfig lcljNodeConfig : nodeList) {
          lcljNodeConfig.setStus("0");
          nodeListMap.put(lcljNodeConfig.getNodeId(), lcljNodeConfig);
        } 
        for (LcljWorklist lcljWorklist : hadhList) {
          String stusTemp = ((LcljNodeConfig)nodeListMap.get(lcljWorklist.getNodeId())).getStus();
          if (stusTemp != null && !stusTemp.equals("1"))
            ((LcljNodeConfig)nodeListMap.get(lcljWorklist.getNodeId())).setStus((String)lcljWorklist.getNodeStatus()); 
        } 
        data.put("nodes", JSON.toJSONString(nodeList));
        return JSON.toJSONString(data);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findOrderTrackInforByOrderNumber.act"})
  public String findOrderTrackInforByOrderNumber(HttpServletRequest request, HttpServletResponse response) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    String regdept = request.getParameter("regdept");
    String doctor = request.getParameter("doctor");
    String fzsj = request.getParameter("cjsj");
    String fzsj2 = request.getParameter("cjsj2");
    String organization = request.getParameter("organization");
    String usercodeorname = request.getParameter("usercodeorname");
    String username = request.getParameter("username");
    String userId = request.getParameter("userId");
    String starttime = request.getParameter("starttime");
    String endtime = request.getParameter("endtime");
    String phonenumber = request.getParameter("phonenumber");
    String ssType = request.getParameter("ssType");
    String isBone = request.getParameter("isBone");
    String counsellor = request.getParameter("counsellor");
    String plant_physician = request.getParameter("plant_physician");
    String repair_physician = request.getParameter("repair_physician");
    String nodename = request.getParameter("nodename");
    String sortName = request.getParameter("sortName");
    String sortOrder = request.getParameter("sortOrder");
    String visualstaff = SessionUtil.getVisualstaff(request);
    Map<String, String> map = new HashMap<>();
    JSONObject json = new JSONObject();
    if (YZUtility.isNotNullOrEmpty(nodename))
      map.put("nodename", nodename); 
    if (YZUtility.isNotNullOrEmpty(visualstaff))
      map.put("querytype", visualstaff); 
    if (!YZUtility.isNullorEmpty(usercodeorname))
      map.put("usercodeorname", usercodeorname); 
    if (!YZUtility.isNullorEmpty(regdept))
      map.put("regdept", regdept); 
    if (!YZUtility.isNullorEmpty(doctor))
      map.put("doctor", doctor); 
    if (!YZUtility.isNullorEmpty(fzsj))
      map.put("fzsj", fzsj); 
    if (!YZUtility.isNullorEmpty(fzsj2))
      map.put("fzsj2", fzsj2); 
    if (!YZUtility.isNullorEmpty(username))
      map.put("username", username); 
    if (!YZUtility.isNullorEmpty(userId))
      map.put("userId", userId); 
    if (!YZUtility.isNullorEmpty(starttime))
      map.put("starttime", starttime); 
    if (!YZUtility.isNullorEmpty(endtime))
      map.put("endtime", endtime); 
    if (!YZUtility.isNullorEmpty(phonenumber))
      map.put("phonenumber", phonenumber); 
    if (!YZUtility.isNullorEmpty(ssType))
      map.put("ssType", ssType); 
    if (!YZUtility.isNullorEmpty(isBone))
      map.put("isBone", isBone); 
    if (!YZUtility.isNullorEmpty(counsellor))
      map.put("counsellor", counsellor); 
    if (!YZUtility.isNullorEmpty(plant_physician))
      map.put("plant_physician", plant_physician); 
    if (!YZUtility.isNullorEmpty(repair_physician))
      map.put("repair_physician", repair_physician); 
    if (!YZUtility.isNullorEmpty(sortName)) {
      map.put("sortName", sortName);
      map.put("sortOrder", sortOrder);
    } 
    String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
    String fieldArr = (request.getParameter("fieldArr") == null) ? "" : request.getParameter("fieldArr");
    String fieldnameArr = (request.getParameter("fieldnameArr") == null) ? "" : request.getParameter("fieldnameArr");
    try {
      BootStrapPage bp = new BootStrapPage();
      BeanUtils.populate(bp, request.getParameterMap());
      JSONObject jsonO = this.flowService.findOrderTrackInforByOrderNumber(person, bp, map, organization, json);
      if (flag != null && flag.equals("exportTable")) {
        JSONObject data = this.flowService.findOrderTrackInforByOrderNumber(person, bp, map, organization, json);
        if (data != null) {
          ExportBean bean = ExportTable.initExcel4RsWrite("临床路径", fieldArr, fieldnameArr, response, request);
          bean = ExportTable.exportBootStrapTable2ExcelByResult(bean, (List)data.get("rows"), "lclj_findOrderTrackInforByOrderNumber");
          ExportTable.writeExcel4DownLoad("临床路径", bean.getWorkbook(), response);
        } 
        return null;
      } 
      YZUtility.DEAL_SUCCESS(jsonO, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findLcljAdmin.act"})
  public String findLcljAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      JSONObject jo = this.flowService.findLcljAdmin(request);
      YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findLcljAdminOrAgency.act"})
  public String findLcljAdminOrAgency(HttpServletRequest request, HttpServletResponse response) throws Exception {
    YZPerson person = SessionUtil.getLoginPerson(request);
    try {
      String currentUserId = person.getUserId();
      String currentUserSeqId = person.getSeqId();
      List<JSONObject> list = this.flowService.findLcljAdminOrAgency(request);
      JSONObject jo = new JSONObject();
      jo.put("hasPass", "NO");
      if (list != null)
        for (JSONObject object : list) {
          if (currentUserId.equals(object.get("para_value")) || currentUserSeqId.equals(object.get("para_value"))) {
            jo.put("hasPass", "YES");
            break;
          } 
        }  
      YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateAgencyUser.act"})
  public String updateAgencyUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String seqId = request.getParameter("seq_id");
    String paraValue = request.getParameter("paraValue");
    YZPara yzPara = new YZPara();
    yzPara.setSeqId(seqId);
    yzPara.setParaValue(paraValue);
    try {
      this.flowService.updateAgencyUser(yzPara);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateRemarkStus.act"})
  public String updateRemarkStus(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    String status = request.getParameter("status");
    try {
      this.flowService.updateRemarkStus(id, status);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findOrderTrackInforByConditionQuery.act"})
  public String findOrderTrackInforByConditionQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String regdept = request.getParameter("regdept");
    String doctor = request.getParameter("doctor");
    String usercodeorname = request.getParameter("usercodeorname");
    String fzsj = request.getParameter("fzsj");
    String fzsj2 = request.getParameter("fzsj2");
    String organization = request.getParameter("organization");
    YZPerson person = SessionUtil.getLoginPerson(request);
    Map<String, String> map = new HashMap<>();
    if (!YZUtility.isNullorEmpty(regdept))
      map.put("regdept", regdept); 
    if (!YZUtility.isNullorEmpty(doctor))
      map.put("doctor", doctor); 
    if (!YZUtility.isNullorEmpty(usercodeorname))
      map.put("usercodeorname", usercodeorname); 
    if (!YZUtility.isNullorEmpty(fzsj))
      map.put("fzsj", fzsj); 
    if (!YZUtility.isNullorEmpty(fzsj2))
      map.put("fzsj2", fzsj2); 
    try {
      JSONObject json = this.flowService.findOrderTrackInforByConditionQuery(person, map, organization);
      YZUtility.DEAL_SUCCESS(json, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateOrderTrackById.act"})
  public String updateOrderTrackById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    String sstime = request.getParameter("sstime");
    Map<String, String> map = new HashMap<>();
    if (!YZUtility.isNullorEmpty(id))
      map.put("id", id); 
    if (!YZUtility.isNullorEmpty(sstime))
      map.put("sstime", sstime); 
    try {
      this.flowService.updateOrderTrackById(map);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/deleteOrderTrackInforById.act"})
  public String deleteOrderTrackInforById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    try {
      this.flowService.deleteOrderTrackInforById(id);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/changeLcljOrderTrackBoneStatus.act"})
  public String changeLcljOrderTrackBoneStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String blcode = request.getParameter("blcode");
    String tooth_total = request.getParameter("tooth_total");
    String ssType = request.getParameter("ssType");
    String isBone = request.getParameter("isBone");
    String left_up = request.getParameter("left_up");
    String left_down = request.getParameter("left_down");
    String right_up = request.getParameter("right_up");
    String right_down = request.getParameter("right_down");
    String counsellor = request.getParameter("counsellor");
    String plant_physician = request.getParameter("plant_physician");
    String repair_physician = request.getParameter("repair_physician");
    String clinic_nurse = request.getParameter("clinic_nurse");
    String customer_service = request.getParameter("customer_service");
    String plant_system = request.getParameter("plant_system");
    String crown_material = request.getParameter("crown_material");
    String imageological_examination = request.getParameter("imageological_examination");
    String consultation = request.getParameter("consultation");
    String advisory = request.getParameter("advisory");
    String repair_left_up = request.getParameter("repair_left_up");
    String repair_left_down = request.getParameter("repair_left_down");
    String repair_right_up = request.getParameter("repair_right_up");
    String repair_right_down = request.getParameter("repair_right_down");
    String zz_tooth_total = request.getParameter("zz_tooth_total");
    String id = request.getParameter("id");
    LcljOrderTrack lcljOrderTrack = new LcljOrderTrack();
    lcljOrderTrack.setBlcode(blcode);
    lcljOrderTrack.setTooth(tooth_total);
    lcljOrderTrack.setType(ssType);
    lcljOrderTrack.setBone(isBone);
    YZPerson person = SessionUtil.getLoginPerson(request);
    lcljOrderTrack.setCreatorid(person.getSeqId());
    lcljOrderTrack.setCreatorname(person.getUserName());
    lcljOrderTrack.setCounsellor(counsellor);
    lcljOrderTrack.setPlantPhysician(plant_physician);
    lcljOrderTrack.setClinicNurse(clinic_nurse);
    lcljOrderTrack.setRepairPphysician(repair_physician);
    lcljOrderTrack.setCustomerService(customer_service);
    lcljOrderTrack.setPlantSystem(plant_system);
    lcljOrderTrack.setCrownMaterial(crown_material);
    lcljOrderTrack.setImageologicalExamination(imageological_examination);
    lcljOrderTrack.setConsultation(consultation);
    lcljOrderTrack.setAdvisory(advisory);
    lcljOrderTrack.setRepairLeftUp(repair_left_up);
    lcljOrderTrack.setRepairLeftDown(repair_left_down);
    lcljOrderTrack.setRepairRightUp(repair_right_up);
    lcljOrderTrack.setRepairRightDown(repair_right_down);
    lcljOrderTrack.setRepairToothTotal(zz_tooth_total);
    if (YZUtility.isNotNullOrEmpty(left_up)) {
      left_up = left_up.substring(0, left_up.length() - 1);
      lcljOrderTrack.setLeftUp(left_up);
    } 
    if (YZUtility.isNotNullOrEmpty(left_down)) {
      left_down = left_down.substring(0, left_down.length() - 1);
      lcljOrderTrack.setLeftDown(left_down);
    } 
    if (YZUtility.isNotNullOrEmpty(right_up)) {
      right_up = right_up.substring(0, right_up.length() - 1);
      lcljOrderTrack.setRightUp(right_up);
    } 
    if (YZUtility.isNotNullOrEmpty(right_down)) {
      right_down = right_down.substring(0, right_down.length() - 1);
      lcljOrderTrack.setRightDown(right_down);
    } 
    if (YZUtility.isNotNullOrEmpty(repair_left_up)) {
      repair_left_up = repair_left_up.substring(0, repair_left_up.length() - 1);
      lcljOrderTrack.setRepairLeftUp(repair_left_up);
    } 
    if (YZUtility.isNotNullOrEmpty(repair_left_down)) {
      repair_left_down = repair_left_down.substring(0, repair_left_down.length() - 1);
      lcljOrderTrack.setRepairLeftDown(repair_left_down);
    } 
    if (YZUtility.isNotNullOrEmpty(repair_right_up)) {
      repair_right_up = repair_right_up.substring(0, repair_right_up.length() - 1);
      lcljOrderTrack.setRepairRightUp(repair_right_up);
    } 
    if (YZUtility.isNotNullOrEmpty(repair_right_down)) {
      repair_right_down = repair_right_down.substring(0, repair_right_down.length() - 1);
      lcljOrderTrack.setRepairRightDown(repair_right_down);
    } 
    try {
      this.flowService.changeLcljOrderTrackBoneStatus(lcljOrderTrack, id);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findLcljOrderTrsackById.act"})
  public String findLcljOrderTrsackById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    try {
      JSONObject json = this.flowService.findLcljOrderTrsackById(id);
      YZUtility.DEAL_SUCCESS(json, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findHadWorkByOrderNumberAndNodeId.act"})
  public String findHadWorkByOrderNumberAndNodeId(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String ordNumber = request.getParameter("ordNumber");
    String nodeId = request.getParameter("nodeId");
    Map<String, String> dataMap = new HashMap<>();
    if (YZUtility.isNotNullOrEmpty(ordNumber))
      dataMap.put("ordNumber", ordNumber); 
    if (YZUtility.isNotNullOrEmpty(nodeId))
      dataMap.put("nodeId", nodeId); 
    try {
      LcljWorklist work = this.flowOperateService.findHadWorkByOrderNumberAndNodeId(dataMap);
      if (work == null)
        throw new Exception(" "); 
      JSONObject jo = new JSONObject();
      jo.put("selNodeWork", work);
      YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateOrderTrackNodes.act"})
  public String updateOrderTrackNodes(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String[] plant_system = request.getParameterValues("plant_system");
    StringBuffer sbplantSystem = new StringBuffer();
    for (int i = 0; i < plant_system.length; i++)
      sbplantSystem.append(String.valueOf(plant_system[i]) + ";"); 
    String flowCode = request.getParameter("flowcode");
    String type = request.getParameter("type");
    String dentalJaw = request.getParameter("dentalJaw");
    String articleType = request.getParameter("articleType");
    String flowType = request.getParameter("flowType");
    String id = request.getParameter("id");
    LcljOrderTrack lcljOrderTrack = this.flowService.findLcljOrderTrsackByseqId(id);
    lcljOrderTrack.setPlantSystem(sbplantSystem.toString());
    lcljOrderTrack.setFlowcode(flowCode);
    lcljOrderTrack.setDentalJaw(dentalJaw);
    lcljOrderTrack.setArticleType(articleType);
    lcljOrderTrack.setType(flowType);
    Map<String, String> dataMap = new HashMap<>();
    try {
      if (this.dzblService.findCaseHistoryById(id) != null) {
        if (this.zzblCheckService.findZzblOprationById(id) != null) {
          if (this.zzblService.findZzblOprationById(id) != null) {
            if (this.rscService.findRepairInforById(id) != null) {
              if (this.dzblService.findFamiliarBook(id) != null || this.dzblService.findLocatorFamiliares(id).size() > 0) {
                this.flowService.updateOrderTrackNodes(dataMap, flowCode, type, dentalJaw, lcljOrderTrack, request);
                YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
              } else {
                throw new Exception("知情同意书还没有填写，请填写完再进行提交！");
              } 
            } else {
              throw new Exception("修复方案确认单还没有填写，请填写完再进行提交！");
            } 
          } else {
            throw new Exception("诊疗方案还没有填写，请填写完再进行提交！");
          } 
        } else {
          throw new Exception("检查及诊断还没有填写，请填写完再进行提交！");
        } 
      } else {
        throw new Exception("主诉及既往病史还没有填写，请填写完再进行提交！");
      } 
    } catch (Exception ex) {
      YZUtility.DEAL_ERROR(ex.getMessage(), true, ex, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateLcljOrderTrackIsobsolete.act"})
  public String updateLcljOrderTrackIsobsolete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    if (YZUtility.isNullorEmpty(id))
      throw new Exception("获取临床路径数据失败"); 
    try {
      this.flowService.updateLcljOrderTrackIsobsolete(id, request);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateLcljOrderTrackById.act"})
  public String updateLcljOrderTrackById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    String counsellor = request.getParameter("counsellor");
    String plant_physician = request.getParameter("plant_physician");
    String repair_physician = request.getParameter("repair_physician");
    String clinic_nurse = request.getParameter("clinic_nurse");
    String customer_service = request.getParameter("customer_service");
    String imageological_examination = request.getParameter("imageological_examination");
    String consultation = request.getParameter("consultation");
    String tooth_texture = request.getParameter("tooth_texture");
    String abutment_station = request.getParameter("abutment_station");
    String remark = request.getParameter("remark");
    String advisory = request.getParameter("advisory");
    LcljOrderTrack lcljOrderTrack = new LcljOrderTrack();
    lcljOrderTrack.setId(id);
    lcljOrderTrack.setCounsellor(counsellor);
    lcljOrderTrack.setPlantPhysician(plant_physician);
    lcljOrderTrack.setRepairPphysician(repair_physician);
    lcljOrderTrack.setClinicNurse(clinic_nurse);
    lcljOrderTrack.setCustomerService(customer_service);
    lcljOrderTrack.setImageologicalExamination(imageological_examination);
    lcljOrderTrack.setConsultation(consultation);
    lcljOrderTrack.setRemark(remark);
    lcljOrderTrack.setAbutment_station(abutment_station);
    lcljOrderTrack.setTooth_texture(tooth_texture);
    lcljOrderTrack.setAdvisory(advisory);
    try {
      if (this.dzblService.findCaseHistoryById(id) != null) {
        if (this.zzblCheckService.findZzblOprationById(id) != null) {
          if (this.zzblService.findZzblOprationById(id).size() > 0) {
            if (this.rscService.findRepairInforById(id) != null) {
              if (this.dzblService.findFamiliarBook(id) != null || this.dzblService.findLocatorFamiliares(id).size() > 0) {
                this.flowService.updateLcljOrderTrackById(lcljOrderTrack);
                YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
              } else {
                throw new Exception("知情同意书还没有填写，请填写完再进行提交！");
              } 
            } else {
              throw new Exception("修复方案确认单还没有填写，请填写完再进行提交！");
            } 
          } else {
            throw new Exception("诊疗方案还没有填写，请填写完再进行提交！");
          } 
        } else {
          throw new Exception("检查及诊断还没有填写，请填写完再进行提交！");
        } 
      } else {
        throw new Exception("主诉及既往病史还没有填写，请填写完再进行提交！");
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/updateLcljOrderTrackCounsellorById.act"})
  public String updateLcljOrderTrackCounsellorById(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    String counsellor = request.getParameter("counsellor");
    String plant_physician = request.getParameter("plant_physician");
    String repair_physician = request.getParameter("repair_physician");
    String clinic_nurse = request.getParameter("clinic_nurse");
    String customer_service = request.getParameter("customer_service");
    String imageological_examination = request.getParameter("imageological_examination");
    String tooth_texture = request.getParameter("tooth_texture");
    String remark = request.getParameter("remark");
    LcljOrderTrack lcljOrderTrack = new LcljOrderTrack();
    lcljOrderTrack.setId(id);
    lcljOrderTrack.setCounsellor(counsellor);
    lcljOrderTrack.setPlantPhysician(plant_physician);
    lcljOrderTrack.setRepairPphysician(repair_physician);
    lcljOrderTrack.setClinicNurse(clinic_nurse);
    lcljOrderTrack.setCustomerService(customer_service);
    lcljOrderTrack.setRemark(remark);
    if (imageological_examination != null && !imageological_examination.equals(""))
      lcljOrderTrack.setImageologicalExamination(imageological_examination); 
    if (tooth_texture != null && !tooth_texture.equals(""))
      lcljOrderTrack.setTooth_texture(tooth_texture); 
    try {
      this.flowService.updateLcljOrderTrackById(lcljOrderTrack);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/editToothBit.act"})
  public String editToothBit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String lcljId = request.getParameter("lcljId");
    String left_up = request.getParameter("left_up");
    String left_down = request.getParameter("left_down");
    String right_up = request.getParameter("right_up");
    String right_down = request.getParameter("right_down");
    String repair_left_up = request.getParameter("repair_left_up");
    String repair_left_down = request.getParameter("repair_left_down");
    String repair_right_up = request.getParameter("repair_right_up");
    String repair_right_down = request.getParameter("repair_right_down");
    LcljOrderTrack lTrack = new LcljOrderTrack();
    if (lcljId != null && !lcljId.equals(""))
      lTrack.setId(lcljId); 
    if (left_up != null && !left_up.equals(""))
      lTrack.setLeftUp(left_up); 
    if (left_down != null && !left_down.equals(""))
      lTrack.setLeftDown(left_down); 
    if (right_up != null && !right_up.equals(""))
      lTrack.setRightUp(right_up); 
    if (right_down != null && !right_down.equals(""))
      lTrack.setRightDown(right_down); 
    if (repair_left_up != null && !repair_left_up.equals(""))
      lTrack.setRepairLeftUp(repair_left_up); 
    if (repair_left_down != null && !repair_left_down.equals(""))
      lTrack.setRepairLeftDown(repair_left_down); 
    if (repair_right_up != null && !repair_right_up.equals(""))
      lTrack.setRepairRightUp(repair_right_up); 
    if (repair_right_down != null && !repair_right_down.equals(""))
      lTrack.setRepairRightDown(repair_right_down); 
    try {
      Integer data = this.flowService.editToothBit(lTrack);
      if (data.intValue() > 0) {
        String id = YZUtility.getUUID();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        OperatingRecord oRecord = new OperatingRecord();
        oRecord.setId(id);
        oRecord.setLcljId(lcljId);
        oRecord.setName(request.getParameter("person"));
        oRecord.setCreateTime(dateFormat.format(date));
        this.flowService.saveOperatingRecord(oRecord);
        YZUtility.DEAL_SUCCESS(null, "临床路径牙位图修改成功!", response, this.logger);
      } else {
        YZUtility.DEAL_SUCCESS(null, "临床路径牙位图修改失败!", response, this.logger);
      } 
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(e.getMessage(), true, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findPatientInformation.act"})
  public String findPatientInformation(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String usercode = request.getParameter("usercode");
      String status = request.getParameter("status");
      String id = request.getParameter("id");
      String order_number = request.getParameter("order_number");
      JSONObject jo = this.flowService.findPatientInformation(usercode, status, id, order_number);
      YZUtility.DEAL_SUCCESS(jo, null, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
  
  @RequestMapping({"/findNodeName.act"})
  public String findNodeName(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      List<LcljNodeConfig> list = this.flowService.findNodeName();
      YZUtility.RETURN_LIST(list, response, this.logger);
    } catch (Exception e) {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    } 
    return null;
  }
}
