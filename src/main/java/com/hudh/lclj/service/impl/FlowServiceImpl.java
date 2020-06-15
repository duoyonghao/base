package com.hudh.lclj.service.impl;

import com.alibaba.fastjson.JSON;
import com.hudh.lclj.dao.LcljOptRecodeDao;
import com.hudh.lclj.dao.LcljTrackDao;
import com.hudh.lclj.dao.NodeConfigDao;
import com.hudh.lclj.dao.SysParaDao;
import com.hudh.lclj.entity.LcljNode;
import com.hudh.lclj.entity.LcljNodeConfig;
import com.hudh.lclj.entity.LcljOptRecode;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.LcljOrderTrackDeleteRecord;
import com.hudh.lclj.entity.OperatingRecord;
import com.hudh.lclj.service.IFlowOperateService;
import com.hudh.lclj.service.IFlowService;
import com.hudh.lclj.service.ILcljOrderTrackDeleteService;
import com.hudh.lclj.util.OrderNumberUtil;
import com.hudh.lclj.util.ParseNodesXml;
import com.hudh.util.HUDHUtil;
import com.hudh.zzbl.dao.DzblDao;
import com.hudh.zzbl.dao.NotificationDao;
import com.hudh.zzbl.dao.RepairSchemeConfirmDao;
import com.hudh.zzbl.dao.ZzblCheckDao;
import com.hudh.zzbl.dao.ZzblDao;
import com.hudh.zzbl.entity.Notification;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZPara;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowServiceImpl implements IFlowService {
  @Autowired
  private LcljTrackDao lcljTrackDao;
  
  @Autowired
  private LcljOptRecodeDao lcljOptRecodeDao;
  
  @Autowired
  private SysParaDao sysParaDao;
  
  @Autowired
  private ZzblDao zzblDao;
  
  private ZzblCheckDao zzblCheckDao;
  
  private RepairSchemeConfirmDao rscDao;
  
  private DzblDao dzblDao;
  
  @Autowired
  private ILcljOrderTrackDeleteService trackDeleteService;
  
  @Autowired
  private IFlowOperateService flowOperateService;
  
  @Autowired
  private NodeConfigDao nodeConfigDao;
  
  @Autowired
  private KQDS_UserDocumentLogic userDocumentLogic;
  
  @Autowired
  private KQDS_REGLogic logic;
  
  @Autowired
  private YZDictLogic dictLogic;
  
  @Autowired
  private NotificationDao notificationDao;
  
  @Transactional(rollbackFor = {Exception.class})
  public void saveLcljOrderTrackInfo(LcljOrderTrack lcljOrderTrack, String id, HttpServletRequest request) throws Exception {
    String seqId = request.getParameter("seqId");
    String iscreateLclj = request.getParameter("iscreateLclj");
    String id1 = YZUtility.getUUID();
    lcljOrderTrack.setId(id1);
    lcljOrderTrack.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    lcljOrderTrack.setLastTime(lcljOrderTrack.getCreatetime());
    lcljOrderTrack.setIsobsolete("0");
    lcljOrderTrack.setSsStatus("进行中");
    OrderNumberUtil.getInstance();
    String orderNumber = OrderNumberUtil.getNextOrderNumber();
    lcljOrderTrack.setOrderNumber(orderNumber);
    this.lcljTrackDao.saveLcljOrderTrack(lcljOrderTrack);
    this.userDocumentLogic.isCreateLclj(seqId, iscreateLclj);
  }
  
  private void createOperateInfo(LcljOrderTrack lcljOrderTrack) {
    List<LcljNode> operateList = new ArrayList<>();
    String type = lcljOrderTrack.getType();
    String bone = lcljOrderTrack.getBone();
    if (type.equals("1") && bone.equals("否")) {
      operateList = ParseNodesXml.getOperateMap("SingleOrMultiNoBone");
    } else if (type.equals("1") && bone.equals("是")) {
      operateList = ParseNodesXml.getOperateMap("SingleOrMultiBone");
    } else if (type.equals("2") && bone.equals("否")) {
      operateList = ParseNodesXml.getOperateMap("LocatorNoBone");
    } else if (type.equals("2") && bone.equals("是")) {
      operateList = ParseNodesXml.getOperateMap("LocatorBone");
    } else if (type.equals("3") && bone.equals("否")) {
      operateList = ParseNodesXml.getOperateMap("AllonxNoBone");
    } else if (type.equals("3") && bone.equals("是")) {
      operateList = ParseNodesXml.getOperateMap("AllonxBone");
    } 
    if (operateList != null) {
      Collections.sort(operateList, new Comparator<LcljNode>() {
            public int compare(LcljNode o1, LcljNode o2) {
              int i = Integer.valueOf(o1.getNum()).intValue() - Integer.valueOf(o2.getNum()).intValue();
              return i;
            }
          });
      lcljOrderTrack.setFlowLink(((LcljNode)operateList.get(1)).getName());
      ((LcljNode)operateList.get(0)).setStus("2");
      ((LcljNode)operateList.get(1)).setStus("1");
      lcljOrderTrack.setNodes(JSON.toJSONString(operateList));
    } 
  }
  
  private void createOperateInfoByConfig(LcljOrderTrack lcljOrderTrack, String type, String flowCode, String dentalJaw, HttpServletRequest request) throws Exception {
    List<LcljNodeConfig> list = this.nodeConfigDao.findLcljNodeConfig(flowCode);
    if (list != null) {
      Collections.sort(list);
      lcljOrderTrack.setNodes(JSON.toJSONString(list));
    } 
  }
  
  public JSONObject findOrderTrackInfo(String orderNumber) throws Exception {
    JSONObject lcljOrderTrack = findLcljOrderTrackAndHzInfo(orderNumber);
    if (lcljOrderTrack != null) {
      String currentNode = lcljOrderTrack.getString("flow_link");
      List<LcljNode> nodeList = HUDHUtil.parseJsonToObjectList(lcljOrderTrack.getString("nodes"), LcljNode.class);
      for (LcljNode node : nodeList) {
        if (currentNode.equals(node.getName())) {
          int limit = Integer.valueOf(node.getLimit()).intValue();
          String lastTime = lcljOrderTrack.getString("last_time");
          int day = HUDHUtil.shiJianCha(lastTime, HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
          if (limit >= 0 && day > limit) {
            node.setStus("3");
            lcljOrderTrack.put("nodes", JSON.toJSON(nodeList));
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("orderNumber", orderNumber);
            dataMap.put("nodes", JSON.toJSONString(nodeList));
            this.lcljTrackDao.updateOrderTrack(dataMap);
          } 
          break;
        } 
      } 
    } 
    return lcljOrderTrack;
  }
  
  public void gotoNextNode(String orderNumber) throws Exception {
    JSONObject lcljOrderTrack = findLcljOrderTrackByOrderNumber(orderNumber);
    String nodes = null;
    String currentNode = null;
    if (lcljOrderTrack != null) {
      nodes = JSON.toJSONString(lcljOrderTrack.get("nodes"));
      currentNode = (String)lcljOrderTrack.get("flow_link");
    } 
    String nextNode = findNextNode(nodes, currentNode);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("nextNode", nextNode);
    dataMap.put("orderNumber", orderNumber);
    dataMap.put("lastTime", HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    nodes = updateNodeStatus(nodes, currentNode, nextNode);
    dataMap.put("nodes", nodes);
    if ("结束".equals(nextNode))
      dataMap.put("flowStatus", "已完成"); 
    this.lcljTrackDao.updateOrderTrack(dataMap);
    addNodeOperateLog();
  }
  
  private JSONObject findLcljOrderTrackByOrderNumber(String orderNumber) throws Exception {
    List<JSONObject> ssInfo = this.lcljTrackDao.findLcljOrderTrackByOrderNumber(orderNumber);
    if (ssInfo != null && ssInfo.size() > 0)
      return ssInfo.get(0); 
    return null;
  }
  
  private String updateNodeStatus(String nodes, String currentNode, String nextNode) {
    List<LcljNode> nodeList = HUDHUtil.parseJsonToObjectList(nodes, LcljNode.class);
    for (LcljNode node : nodeList) {
      if (node.getName().equals(currentNode) && !node.getStus().equals("3")) {
        node.setStus("2");
        continue;
      } 
      if (node.getName().equals(nextNode))
        node.setStus("1"); 
    } 
    return JSON.toJSONString(nodeList);
  }
  
  private String updateRejectNodeStatus(String nodes, String currentNode, String lastNode) {
    List<LcljNode> nodeList = HUDHUtil.parseJsonToObjectList(nodes, LcljNode.class);
    for (LcljNode node : nodeList) {
      if (node.getName().equals(currentNode) && !node.getStus().equals("3")) {
        node.setStus("0");
        continue;
      } 
      if (node.getName().equals(lastNode))
        node.setStus("1"); 
    } 
    return JSON.toJSONString(nodeList);
  }
  
  private JSONObject findLcljOrderTrackAndHzInfo(String orderNumber) throws Exception {
    List<JSONObject> ssInfo = this.lcljTrackDao.findLcljOrderTrackAndHzInfo(orderNumber);
    if (ssInfo != null && ssInfo.size() > 0)
      return ssInfo.get(0); 
    return null;
  }
  
  private String findNextNode(String nodes, String currentNode) {
    String nextNode = null;
    List<LcljNode> nodeList = HUDHUtil.parseJsonToObjectList(nodes, LcljNode.class);
    if (nodes != null && nodeList.size() > 0) {
      Collections.sort(nodeList, new Comparator<LcljNode>() {
            public int compare(LcljNode o1, LcljNode o2) {
              int i = Integer.valueOf(o1.getNum()).intValue() - Integer.valueOf(o2.getNum()).intValue();
              return i;
            }
          });
      int length = nodeList.size();
      for (int i = 0; i < length; i++) {
        if (((LcljNode)nodeList.get(i)).getName().equals(currentNode)) {
          nextNode = ((LcljNode)nodeList.get(i + 1)).getName();
          break;
        } 
      } 
    } 
    return nextNode;
  }
  
  private String findLastNode(String nodes, String currentNode) {
    String nextNode = null;
    List<LcljNode> nodeList = HUDHUtil.parseJsonToObjectList(nodes, LcljNode.class);
    if (nodes != null && nodeList.size() > 0) {
      Collections.sort(nodeList, new Comparator<LcljNode>() {
            public int compare(LcljNode o1, LcljNode o2) {
              int i = Integer.valueOf(o1.getNum()).intValue() - Integer.valueOf(o2.getNum()).intValue();
              return i;
            }
          });
      int length = nodeList.size();
      for (int i = 0; i < length; i++) {
        if (((LcljNode)nodeList.get(i)).getName().equals(currentNode)) {
          nextNode = ((LcljNode)nodeList.get(i - 1)).getName();
          break;
        } 
      } 
    } 
    return nextNode;
  }
  
  public void saveOptRecode(LcljOptRecode lcljOptRecode) throws Exception {
    JSONObject lcljOrderTrack = findLcljOrderTrackByOrderNumber(lcljOptRecode.getOrderNumber());
    String flowLink = lcljOrderTrack.getString("flow_link");
    lcljOptRecode.setId(YZUtility.getUUID());
    lcljOptRecode.setFlowLink(flowLink);
    lcljOptRecode.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    lcljOptRecode.setStatus("0");
    this.lcljOptRecodeDao.saveLcljOptRecode(lcljOptRecode);
    addNodeOperateLog();
  }
  
  public List<JSONObject> findOptRecodeList(String orderNumber, String searchFlowink) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("orderNumber", orderNumber);
    dataMap.put("searchFlowink", searchFlowink);
    return this.lcljOptRecodeDao.findLcljOptRecodeList(dataMap);
  }
  
  private void addNodeOperateLog() {
    System.out.println("这里记录点击下一步操作及添加备注信息的日志");
  }
  
  public JSONObject findOrderTrackInforByOrderNumber(YZPerson person, BootStrapPage bp, Map<String, String> map, String organization, JSONObject json) throws Exception {
    JSONObject jsonO = null;
    if (person.getDeptId().equals("45df337f-8687-4c0b-aa29-95e8b4a59d5d")) {
      jsonO = this.lcljTrackDao.findOrderTrackInforByOrderNumber(map, bp, json);
    } else if (person.getDeptId().equals("4b88b74c-9373-4b5f-9d53-3c115de7a7e4")) {
      jsonO = this.lcljTrackDao.findOrderTrackInforByOrderNumber(map, bp, json);
    } else if (person.getDeptId().equals("015d89d6-7b32-47ec-9557-233407c7fc71")) {
      jsonO = this.lcljTrackDao.findOrderTrackInforByOrderNumber(map, bp, json);
    } else if (person.getDeptId().equals("72d1324f-22a2-41a7-9739-8b64c50e7b97")) {
      jsonO = this.lcljTrackDao.findOrderTrackInforByOrderNumber(map, bp, json);
    } else if (person.getDeptId().equals("3b47a915-977b-4799-acf6-540d525722f4")) {
      jsonO = this.lcljTrackDao.findOrderTrackInforByOrderNumber(map, bp, json);
    } else if (person.getDeptId().equals("b4f9dc9e-d2e0-44e1-ba37-eecaddcbf93d")) {
      jsonO = this.lcljTrackDao.findOrderTrackInforByOrderNumber(map, bp, json);
    } else if (person.getDeptId().equals("4fbf1126-f689-4d59-8fc7-93b444eea141")) {
      jsonO = this.lcljTrackDao.findOrderTrackInforByOrderNumber(map, bp, json);
    } else {
      jsonO = this.lcljTrackDao.findOrderTrackInforByOrderNumberCjstatus(map, bp, json);
    } 
    return jsonO;
  }
  
  public JSONObject findLcljAdmin(HttpServletRequest request) throws Exception {
    String organization = ChainUtil.getCurrentOrganization(request);
    List<String> list = new ArrayList<>();
    list.add("LCLJ_ADMIN");
    list.add("LCLJ_AGENCY_USER");
    List<JSONObject> jsonO = this.sysParaDao.getParaValueListByName(list, request, organization);
    JSONObject jo = new JSONObject();
    YZPerson person = SessionUtil.getLoginPerson(request);
    if (jsonO != null && jsonO.size() > 0)
      for (JSONObject object : jsonO) {
        if (object.get("para_name").equals("LCLJ_ADMIN")) {
          jo.put("lcljAmdin", person.getUserId().equals(object.get("para_value")) ? 
              "YES" : "NO");
          continue;
        } 
        if (object.get("para_name").equals("LCLJ_AGENCY_USER")) {
          jo.put("agencyUserId", object.get("para_value"));
          Map<String, Map<String, String>> dataMap = new HashMap<>();
          Map<String, String> tempMap = new HashMap<>();
          tempMap.put("seq_id", (String)object.get("para_value"));
          dataMap.put("params", tempMap);
          List<YZPerson> persions = this.sysParaDao.selectUserBySeqId(dataMap);
          if (persions != null && persions.size() > 0)
            jo.put("agencyUserName", ((YZPerson)persions.get(0)).getUserName()); 
          jo.put("agencyUser_seq_id", object.get("seq_id"));
        } 
      }  
    return jo;
  }
  
  public List<JSONObject> findLcljAdminOrAgency(HttpServletRequest request) throws Exception {
    String organization = ChainUtil.getCurrentOrganization(request);
    List<String> list = new ArrayList<>();
    list.add("LCLJ_ADMIN");
    list.add("LCLJ_AGENCY_USER");
    List<JSONObject> jsonO = this.sysParaDao.getParaValueListByName(list, request, organization);
    return jsonO;
  }
  
  public void updateAgencyUser(YZPara yzPara) throws Exception {
    this.sysParaDao.updateAgencyUser(yzPara);
  }
  
  public void updateRemarkStus(String id, String status) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("id", id);
    dataMap.put("status", status);
    this.lcljOptRecodeDao.updateRemarkStus(dataMap);
  }
  
  public List<KqdsReg> findRegListByBlcode(String orderNumber) throws Exception {
    List<JSONObject> flowTrackList = this.lcljTrackDao.findLcljOrderTrackByOrderNumber(orderNumber);
    if (flowTrackList != null && flowTrackList.size() > 0) {
      String blCode = (String)((JSONObject)flowTrackList.get(0)).get("blcode");
      Map<String, String> tempMap = new HashMap<>();
      tempMap.put("usercode", blCode);
      List<KqdsReg> list = new ArrayList<>();
      list = this.lcljTrackDao.findRegListByBlcode(tempMap);
      return list;
    } 
    return null;
  }
  
  public void reject(String orderNumber) throws Exception {
    JSONObject lcljOrderTrack = findLcljOrderTrackByOrderNumber(orderNumber);
    String nodes = null;
    String currentNode = null;
    if (lcljOrderTrack != null) {
      nodes = JSON.toJSONString(lcljOrderTrack.get("nodes"));
      currentNode = (String)lcljOrderTrack.get("flow_link");
    } 
    String lastNode = findLastNode(nodes, currentNode);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("nextNode", lastNode);
    dataMap.put("orderNumber", orderNumber);
    dataMap.put("lastTime", HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    nodes = updateRejectNodeStatus(nodes, currentNode, lastNode);
    dataMap.put("nodes", nodes);
    if ("结束".equals(lastNode))
      dataMap.put("flowStatus", "已完成"); 
    this.lcljTrackDao.updateOrderTrack(dataMap);
    addNodeOperateLog();
  }
  
  public JSONObject findOrderTrackInforByConditionQuery(YZPerson person, Map<String, String> map, String organization) throws Exception {
    JSONObject json = this.lcljTrackDao.findOrderTrackInforByConditionQuery(person, map, organization);
    return json;
  }
  
  public void updateOrderTrack(Map<String, String> dataMap) throws Exception {
    if (dataMap != null && dataMap.size() >= 0)
      this.lcljTrackDao.updateOrderTrack(dataMap); 
  }
  
  public void updateOrderTrackById(Map<String, String> dataMap) throws Exception {
    this.lcljTrackDao.updateOrderTrackById(dataMap);
  }
  
  public void deleteOrderTrackInforById(String id) throws Exception {
    this.lcljTrackDao.deleteOrderTrackInforById(id);
  }
  
  public LcljOrderTrack findOrderTrackInforById(String id) throws Exception {
    this.lcljTrackDao.findOrderTrackInforById(id);
    return null;
  }
  
  public void changeLcljOrderTrackBoneStatus(LcljOrderTrack lcljOrderTrack, String id) throws Exception {
    String id1 = YZUtility.getUUID();
    lcljOrderTrack.setId(id1);
    lcljOrderTrack.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    lcljOrderTrack.setLastTime(lcljOrderTrack.getCreatetime());
    lcljOrderTrack.setIsobsolete("0");
    lcljOrderTrack.setSsStatus("进行中");
    OrderNumberUtil.getInstance();
    String orderNumber = OrderNumberUtil.getNextOrderNumber();
    lcljOrderTrack.setOrderNumber(orderNumber);
    createOperateInfo(lcljOrderTrack);
    LcljOrderTrack lTrack = this.lcljTrackDao.findOrderTrackInforById(id);
    if (lTrack.getBone().equals("是")) {
      this.lcljTrackDao.updateLcljOrderTrackIsobsolete(id);
      this.lcljTrackDao.changeLcljOrderTrackBoneStatus(lcljOrderTrack);
    } else if (lTrack.getBone().equals("否")) {
      this.lcljTrackDao.updateLcljOrderTrackIsobsolete(id);
      this.lcljTrackDao.changeLcljOrderTrackBoneStatus(lcljOrderTrack);
    } 
  }
  
  public JSONObject findLcljOrderTrsackById(String id) throws Exception {
    JSONObject json = this.lcljTrackDao.findLcljOrderTrsackById(id);
    return json;
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public void updateOrderTrackNodes(Map<String, String> dataMap, String flowCode, String type, String dentalJaw, LcljOrderTrack lcljOrderTrack, HttpServletRequest request) throws Exception {
    createOperateInfoByConfig(lcljOrderTrack, type, flowCode, dentalJaw, request);
    this.flowOperateService.createFlow(lcljOrderTrack, request);
    this.lcljTrackDao.updateOrderTrackNodes(lcljOrderTrack);
  }
  
  public LcljOrderTrack findLcljOrderTrsackByseqId(String id) throws Exception {
    LcljOrderTrack lclj = this.lcljTrackDao.findLcljOrderTrsackByseqId(id);
    return lclj;
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public void updateLcljOrderTrackIsobsolete(String id, HttpServletRequest request) throws Exception {
    LcljOrderTrackDeleteRecord dp = new LcljOrderTrackDeleteRecord();
    this.lcljTrackDao.updateLcljOrderTrackIsobsolete(id);
    this.trackDeleteService.save(dp, request);
  }
  
  public void updateLcljOrderTrackById(LcljOrderTrack lcljOrderTrack) throws Exception {
    String id = lcljOrderTrack.getId();
    this.lcljTrackDao.updateLcljOrderTrackById(lcljOrderTrack);
  }
  
  public Integer editToothBit(LcljOrderTrack lTrack) throws Exception {
    return this.lcljTrackDao.editToothBit(lTrack);
  }
  
  public void saveOperatingRecord(OperatingRecord oRecord) throws Exception {
    this.lcljTrackDao.saveOperatingRecord(oRecord);
  }
  
  public JSONObject findPatientInformation(String usercode, String status, String id, String order_number) throws Exception {
    JSONObject job = this.lcljTrackDao.findPatientInformation(usercode);
    String proviceName = job.getString("provincename");
    if (proviceName.equals(""))
      job.replace("provincename", job.getString("province_name")); 
    String citname = job.getString("cityname");
    if (citname.equals(""))
      job.replace("cityname", job.getString("city_name")); 
    String towname = job.getString("townname");
    if (towname.equals(""))
      job.replace("townname", job.getString("area_name")); 
    if (status.equals("1")) {
      Map<String, String> map = new HashMap<>();
      map.put("lcljId", id);
      map.put("lcljNum", order_number);
      Notification notification = this.notificationDao.findNotificationByLcljId(map);
      job.put("whether", notification.getWhether());
      job.put("content", notification.getContent());
      job.put("doctortime", notification.getDoctortime());
      job.put("patienttime", notification.getPatienttime());
    } 
    return job;
  }
  
  public List<LcljNodeConfig> findNodeName() throws Exception {
    List<LcljNodeConfig> list = this.nodeConfigDao.findLcljNodeName();
    return list;
  }
}
