package com.hudh.lclj.service.impl;

import com.alibaba.fastjson.JSON;
import com.hudh.lclj.StaticVar;
import com.hudh.lclj.dao.LcljTrackDao;
import com.hudh.lclj.dao.LcljWorkListDao;
import com.hudh.lclj.dao.NodeConfigDao;
import com.hudh.lclj.entity.LcljApprovedMemo;
import com.hudh.lclj.entity.LcljNodeConfig;
import com.hudh.lclj.entity.LcljOperateRejectRecord;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.LcljWorklist;
import com.hudh.lclj.entity.OperationNodeInfor;
import com.hudh.lclj.service.IFlowOperateService;
import com.hudh.lclj.service.ILcljOperationNodeInforService;
import com.hudh.lclj.service.ILcljOperationNodeRejectService;
import com.hudh.util.HUDHUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowOperateServiceImpl implements IFlowOperateService {
  public static final String FLOW_OPERATE_SUBMIT = "Submit";
  
  public static final String FLOW_OPERATE_REJECT = "reject";
  
  public static final String FLOW_OPERATE_CREATE = "Create";
  
  public static final Integer FLOW_EXAMINE_NO = Integer.valueOf(0);
  
  public static final Integer FLOW_EXAMINE_YES = Integer.valueOf(1);
  
  public static final Integer FLOW_OVERDUE_NO = Integer.valueOf(0);
  
  public static final Integer FLOW_OVERDUE_YES = Integer.valueOf(1);
  
  public static final String FLOW_STATUS_STAST = "Start";
  
  public static final String FLOW_STATUS_PERSURMOUSET = "PerSurMouset";
  
  public static final String FLOW_STATUS_AFTERSURMOUSET = "AfterSurMouset";
  
  public static final String FLOW_STATUS_SURTRE = "Surtre";
  
  public static final String FLOW_STATUS_THIRDREVIEW = "ThirdReview";
  
  public static final String FLOW_STATUS_SECPHASE = "SecPhase";
  
  public static final String FLOW_STATUS_SECPHASEREVIEW = "SecPhaseReview";
  
  public static final String FLOW_STATUS_TRYON = "Tryon";
  
  public static final String FLOW_STATUS_SECMOUSET = "SecMouset";
  
  public static final String FLOW_STATUS_SECTRYON = "SecTryon";
  
  public static final String FLOW_STATUS_END = "End";
  
  @Autowired
  private LcljTrackDao lcljTrackDao;
  
  @Autowired
  private LcljWorkListDao lcljWorkListDao;
  
  @Autowired
  public HUDH_LcljVerificationSheetServiceImpl logic;
  
  @Autowired
  private NodeConfigDao nodeConfigDao;
  
  @Autowired
  private ILcljOperationNodeInforService iLcljOperationNodeInforService;
  
  @Autowired
  private ILcljOperationNodeRejectService rejectService;
  
  public void createFlow(LcljOrderTrack lcljOrderTrack, HttpServletRequest request) throws Exception {
    String nodeJson = lcljOrderTrack.getNodes();
    List<LcljNodeConfig> nodeList = HUDHUtil.parseJsonToObjectList(nodeJson, LcljNodeConfig.class);
    LcljWorklist workObj = new LcljWorklist();
    if (((LcljNodeConfig)nodeList.get(1)).getNodeName().equals("手术治疗") || ((LcljNodeConfig)nodeList.get(1)).getNodeName().equals("手术治疗取模")) {
      workObj.setId(YZUtility.getUUID());
      workObj.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_YWC);
      workObj.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
      workObj.setFlowInfo(lcljOrderTrack.getNodes());
      workObj.setNodeId(((LcljNodeConfig)nodeList.get(0)).getNodeId());
      workObj.setNodeName(((LcljNodeConfig)nodeList.get(0)).getNodeName());
      workObj.setOrderNumber(lcljOrderTrack.getOrderNumber());
      workObj.setViewUrl(((LcljNodeConfig)nodeList.get(0)).getViewUrl());
      workObj.setOperate("Create");
      workObj.setExamine(FLOW_EXAMINE_YES);
      workObj.setFlowCode(((LcljNodeConfig)nodeList.get(0)).getFlowCode());
      workObj.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
      workObj.setCreator(SessionUtil.getLoginPerson(request).getSeqId());
      workObj.setOverdue(FLOW_OVERDUE_NO);
      workObj.setOrganization(ChainUtil.getCurrentOrganization(request));
      this.lcljWorkListDao.insertWorkList(workObj);
      workObj = new LcljWorklist();
      workObj.setId(YZUtility.getUUID());
      workObj.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
      workObj.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
      workObj.setFlowInfo(JSON.toJSONString(nodeList));
      workObj.setNodeId(((LcljNodeConfig)nodeList.get(1)).getNodeId());
      workObj.setNodeName(((LcljNodeConfig)nodeList.get(1)).getNodeName());
      workObj.setViewUrl(((LcljNodeConfig)nodeList.get(1)).getViewUrl());
      workObj.setFlowCode(((LcljNodeConfig)nodeList.get(1)).getFlowCode());
      workObj.setOrderNumber(lcljOrderTrack.getOrderNumber());
      workObj.setOrganization(ChainUtil.getCurrentOrganization(request));
      workObj.setExamine(FLOW_EXAMINE_NO);
      workObj.setOverdue(FLOW_OVERDUE_NO);
      this.lcljWorkListDao.insertWorkList(workObj);
    } else {
      workObj.setId(YZUtility.getUUID());
      workObj.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_YWC);
      workObj.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
      workObj.setFlowInfo(lcljOrderTrack.getNodes());
      workObj.setNodeId(((LcljNodeConfig)nodeList.get(0)).getNodeId());
      workObj.setNodeName(((LcljNodeConfig)nodeList.get(0)).getNodeName());
      workObj.setOrderNumber(lcljOrderTrack.getOrderNumber());
      workObj.setViewUrl(((LcljNodeConfig)nodeList.get(0)).getViewUrl());
      workObj.setOperate("Create");
      workObj.setExamine(FLOW_EXAMINE_YES);
      workObj.setFlowCode(((LcljNodeConfig)nodeList.get(0)).getFlowCode());
      workObj.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
      workObj.setCreator(SessionUtil.getLoginPerson(request).getSeqId());
      workObj.setOverdue(FLOW_OVERDUE_NO);
      workObj.setOrganization(ChainUtil.getCurrentOrganization(request));
      this.lcljWorkListDao.insertWorkList(workObj);
      workObj.setId(YZUtility.getUUID());
      workObj.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_YWC);
      workObj.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
      workObj.setFlowInfo(lcljOrderTrack.getNodes());
      workObj.setNodeId(((LcljNodeConfig)nodeList.get(1)).getNodeId());
      workObj.setNodeName(((LcljNodeConfig)nodeList.get(1)).getNodeName());
      workObj.setOrderNumber(lcljOrderTrack.getOrderNumber());
      workObj.setViewUrl(((LcljNodeConfig)nodeList.get(1)).getViewUrl());
      workObj.setOperate("Create");
      workObj.setExamine(FLOW_EXAMINE_YES);
      workObj.setFlowCode(((LcljNodeConfig)nodeList.get(1)).getFlowCode());
      workObj.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
      workObj.setCreator(SessionUtil.getLoginPerson(request).getSeqId());
      workObj.setOverdue(FLOW_OVERDUE_NO);
      workObj.setOrganization(ChainUtil.getCurrentOrganization(request));
      this.lcljWorkListDao.insertWorkList(workObj);
      workObj = new LcljWorklist();
      workObj.setId(YZUtility.getUUID());
      workObj.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
      workObj.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
      workObj.setFlowInfo(JSON.toJSONString(nodeList));
      workObj.setNodeId(((LcljNodeConfig)nodeList.get(2)).getNodeId());
      workObj.setNodeName(((LcljNodeConfig)nodeList.get(2)).getNodeName());
      workObj.setViewUrl(((LcljNodeConfig)nodeList.get(2)).getViewUrl());
      workObj.setFlowCode(((LcljNodeConfig)nodeList.get(2)).getFlowCode());
      workObj.setOrderNumber(lcljOrderTrack.getOrderNumber());
      workObj.setOrganization(ChainUtil.getCurrentOrganization(request));
      workObj.setExamine(FLOW_EXAMINE_NO);
      workObj.setOverdue(FLOW_OVERDUE_NO);
      this.lcljWorkListDao.insertWorkList(workObj);
    } 
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public void submitFlow(String orderNumber, HttpServletRequest request) throws Exception {
    String nodeLimit = request.getParameter("nodeLimit");
    OperationNodeInfor dp = new OperationNodeInfor();
    LcljWorklist tempWork = new LcljWorklist();
    tempWork.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
    tempWork.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
    tempWork.setOrderNumber(orderNumber);
    List<LcljWorklist> workList = this.lcljWorkListDao.findWorkByOrderNumber(tempWork);
    if (workList != null && workList.size() > 0) {
      LcljWorklist currentWork = workList.get(0);
      String dataId = currentWork.getDataId();
      LcljNodeConfig nextNode = null;
      List<LcljNodeConfig> nodes = HUDHUtil.parseJsonToObjectList(currentWork.getFlowInfo(), LcljNodeConfig.class);
      Collections.sort(nodes);
      int size = nodes.size();
      for (int i = 0; i < size; i++) {
        if (currentWork.getNodeId().equals(((LcljNodeConfig)nodes.get(i)).getNodeId()))
          nextNode = nodes.get(i + 1); 
      } 
      if (nextNode != null) {
        dp.setOrder_number(currentWork.getOrderNumber());
        dp.setNodeId(currentWork.getNodeId());
        dp.setNodeName(currentWork.getNodeName());
        OperationNodeInfor operationNodeInfor = this.iLcljOperationNodeInforService.insertOperationNodeInfor(dp, request, dataId);
        LcljApprovedMemo lMemo = new LcljApprovedMemo();
        lMemo.setSeqId(YZUtility.getUUID());
        lMemo.setLcljId(request.getParameter("lcljId"));
        lMemo.setNodeId(operationNodeInfor.getNodeId());
        lMemo.setNodeName(operationNodeInfor.getNodeName());
        lMemo.setNodetime(operationNodeInfor.getCreatetime());
        lMemo.setNexttime(operationNodeInfor.getNext_hospital_time());
        lMemo.setStatus("0");
        lMemo.setRegtime(request.getParameter("lastRegistrationCreatetime"));
        lMemo.setRegsort(request.getParameter("lastRegistrationRegway"));
        lMemo.setRecesort(request.getParameter("lastRegistrationRecesort"));
        lMemo.setRegdoctor(request.getParameter("lastRegistrationDoctor"));
        lMemo.setOrganization(operationNodeInfor.getOrganization());
        lMemo.setCreateuser(request.getParameter("personId"));
        lMemo.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        lMemo.setNodeLimit(nodeLimit);
        lMemo.setOrderNumber(operationNodeInfor.getOrder_number());
        if (dataId == null) {
          lMemo.setIsRejectStatus("0");
        } else {
          lMemo.setIsRejectStatus("1");
        } 
        bachsaveRecord(lMemo);
        currentWork.setDataId(operationNodeInfor.getSeqId());
        currentWork.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_YWC);
        currentWork.setOperate("Submit");
        currentWork.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        currentWork.setCreator(SessionUtil.getLoginPerson(request).getSeqId());
        this.lcljWorkListDao.updateWorkByOrderNumber(currentWork);
        LcljWorklist nextWork = new LcljWorklist();
        nextWork = currentWork;
        nextWork.setId(YZUtility.getUUID());
        nextWork.setNodeId(nextNode.getNodeId());
        nextWork.setNodeName(nextNode.getNodeName());
        nextWork.setViewUrl(nextNode.getViewUrl());
        nextWork.setOperate(null);
        nextWork.setExamine(FLOW_EXAMINE_NO);
        nextWork.setOverdue(FLOW_OVERDUE_NO);
        nextWork.setCreatetime(null);
        nextWork.setCreator(null);
        nextWork.setDataId(null);
        nextWork.setFlowInfo(currentWork.getFlowInfo());
        if (nextNode.getNodeId().equals("End")) {
          nextWork.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
          nextWork.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_YWC);
          nextWork.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        } else {
          nextWork.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
          nextWork.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
        } 
        this.lcljWorkListDao.insertWorkList(nextWork);
      } 
    } 
  }
  
  public void bachsaveRecord(LcljApprovedMemo lMemo) throws Exception {
    this.logic.insert(lMemo);
  }
  
  @Transactional(rollbackFor = {Exception.class})
  public void rejectFlow(String orderNumber, HttpServletRequest request) throws Exception {
    LcljOperateRejectRecord dp = new LcljOperateRejectRecord();
    LcljWorklist tempWork = new LcljWorklist();
    tempWork.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
    tempWork.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
    tempWork.setOrderNumber(orderNumber);
    List<LcljWorklist> workList = this.lcljWorkListDao.findWorkByOrderNumber(tempWork);
    if (workList != null && workList.size() > 0) {
      LcljWorklist currentWork = workList.get(0);
      LcljNodeConfig nextNode = null;
      List<LcljNodeConfig> nodes = HUDHUtil.parseJsonToObjectList(currentWork.getFlowInfo(), LcljNodeConfig.class);
      Collections.sort(nodes);
      int size = nodes.size();
      for (int i = 0; i < size; i++) {
        if (currentWork.getNodeId().equals(((LcljNodeConfig)nodes.get(i)).getNodeId()))
          nextNode = nodes.get(i - 1); 
      } 
      if (nextNode != null) {
        currentWork.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_YWC);
        currentWork.setOperate("reject");
        currentWork.setCreatetime(HUDHUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        currentWork.setCreator(SessionUtil.getLoginPerson(request).getSeqId());
        this.lcljWorkListDao.updateWorkByOrderNumber(currentWork);
        LcljWorklist nextWork = new LcljWorklist();
        nextWork = currentWork;
        nextWork.setId(YZUtility.getUUID());
        nextWork.setNodeId(nextNode.getNodeId());
        nextWork.setNodeName(nextNode.getNodeName());
        nextWork.setViewUrl(nextNode.getViewUrl());
        nextWork.setOperate(null);
        nextWork.setExamine(FLOW_EXAMINE_NO);
        nextWork.setOverdue(FLOW_OVERDUE_NO);
        nextWork.setCreatetime(null);
        nextWork.setCreator(null);
        nextWork.setDataId(selectHadWorkData(orderNumber, nextNode.getNodeId()));
        nextWork.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
        nextWork.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
        this.lcljWorkListDao.insertWorkList(nextWork);
        dp.setNodeId(nextNode.getNodeId());
        dp.setNodeName(nextNode.getNodeName());
        dp.setOrderNumber(orderNumber);
        this.rejectService.insertOperationNodeReject(dp, request);
      } 
    } 
  }
  
  public List<LcljWorklist> findHadWorkList(String orderNumber, HttpServletRequest request) throws Exception {
    List<LcljWorklist> list = new ArrayList<>();
    list = this.lcljWorkListDao.findHadWorkList(orderNumber);
    return list;
  }
  
  private String selectHadWorkData(String orderNumber, String nodeId) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("orderNumber", orderNumber);
    dataMap.put("nodeId", nodeId);
    String dataId = null;
    LcljWorklist lcljWorklist = this.lcljWorkListDao.selectHadWorkData(dataMap);
    if (lcljWorklist != null)
      dataId = lcljWorklist.getDataId(); 
    return dataId;
  }
  
  private String specialFlowDeal(OperationNodeInfor dp, LcljWorklist currentWork, HttpServletRequest request) throws Exception {
    List<LcljNodeConfig> newNodeList = new ArrayList<>();
    newNodeList = HUDHUtil.parseJsonToObjectList(currentWork.getFlowInfo(), LcljNodeConfig.class);
    if (currentWork.getNodeId().equals("Surtre")) {
      String assistOperation = dp.getAssist_operation();
      if (YZUtility.isNotNullOrEmpty(assistOperation)) {
        boolean preFiveSel = !(assistOperation.indexOf("内提") == -1 && 
          assistOperation.indexOf("外提") == -1 && 
          assistOperation.indexOf("骨劈开") == -1 && 
          assistOperation.indexOf("骨挤压") == -1 && 
          assistOperation.indexOf("自体骨移植") == -1);
        boolean isSixBone = (assistOperation.indexOf("植骨") != -1);
        if (currentWork.getFlowCode().equals("SingleOrMultiNoBone") || 
          currentWork.getFlowCode().equals("LocatorNoBone") || 
          currentWork.getFlowCode().equals("AllonxNoBone")) {
          if (preFiveSel) {
            newNodeList = findNodesToBone(currentWork, currentWork.getFlowCode(), request);
            updateOrderTrackBone(currentWork.getOrderNumber());
            newNodeList = changeNodeLimit(newNodeList, "ThirdReview", "270");
          } else if (isSixBone && !preFiveSel) {
            newNodeList = findNodesToBone(currentWork, currentWork.getFlowCode(), request);
            updateOrderTrackBone(currentWork.getOrderNumber());
          } 
        } else if ((currentWork.getFlowCode().equals("SingleOrMultiBone") || 
          currentWork.getFlowCode().equals("LocatorBone") || 
          currentWork.getFlowCode().equals("AllonxBone")) && 
          preFiveSel) {
          newNodeList = changeNodeLimit(newNodeList, "ThirdReview", "270");
        } 
      } 
    } 
    if (currentWork.getNodeId().equals("Surtre")) {
      String abutmentStation = dp.getAbutment_station();
      if (YZUtility.isNotNullOrEmpty(abutmentStation)) {
        boolean isAbutment = !(abutmentStation.indexOf("复合基台") == -1 && 
          abutmentStation.indexOf("locator基台") == -1 && 
          abutmentStation.indexOf("螺丝") == -1);
        boolean idYhAbutment = (abutmentStation.indexOf("愈合基台") != -1);
        if (idYhAbutment && !isAbutment) {
          newNodeList = deleteNodes(newNodeList, "SecPhase");
          newNodeList = deleteNodes(newNodeList, "SecPhaseReview");
        } 
      } 
    } 
    if (currentWork.getNodeId().equals("Surtre")) {
      String connectingBridge = dp.getConnectingBridge();
      if (YZUtility.isNullorEmpty(connectingBridge) && (
        currentWork.getFlowCode().equals("SingleOrMultiNoBone") || 
        currentWork.getFlowCode().equals("SingleOrMultiBone")))
        deleteNodes(newNodeList, "Tryon"); 
    } 
    return JSON.toJSONString(newNodeList);
  }
  
  private void specialCreateFlowDeal(List<LcljNodeConfig> nodeList, LcljWorklist workObj, LcljOrderTrack lcljOrderTrack) throws Exception {
    if (workObj.getFlowCode().equals("LocatorNoBone") || workObj.getFlowCode().equals("LocatorBone") || 
      workObj.getFlowCode().equals("AllonxNoBone") || workObj.getFlowCode().equals("AllonxBone")) {
      String modeoperation = lcljOrderTrack.getModeoperation();
      if (YZUtility.isNotNullOrEmpty(modeoperation) && modeoperation.equals("0")) {
        deleteNodes(nodeList, "AfterSurMouset");
      } else if (YZUtility.isNotNullOrEmpty(modeoperation) && modeoperation.equals("1")) {
        deleteNodes(nodeList, "PerSurMouset");
      } else {
        throw new Exception("操作失败");
      } 
    } 
  }
  
  private List<LcljNodeConfig> findNodesToBone(LcljWorklist currentWork, String flowCode, HttpServletRequest request) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    List<LcljNodeConfig> list = new ArrayList<>();
    dataMap.put("organization", ChainUtil.getCurrentOrganization(request));
    String orderNumber = currentWork.getOrderNumber();
    if (flowCode.equals("SingleOrMultiNoBone")) {
      dataMap.put("flowCode", "SingleOrMultiBone");
      list = this.nodeConfigDao.findAllNodeConfigByFlowCode(dataMap);
      currentWork.setFlowCode("SingleOrMultiBone");
    } 
    if (flowCode.equals("LocatorNoBone")) {
      dataMap.put("flowCode", "LocatorBone");
      list = this.nodeConfigDao.findAllNodeConfigByFlowCode(dataMap);
      currentWork.setFlowCode("LocatorBone");
      List<JSONObject> orderTrackList = this.lcljTrackDao.findLcljOrderTrackByOrderNumber(orderNumber);
      String modeoperation = ((JSONObject)orderTrackList.get(0)).getString("modeoperation");
      if (modeoperation.equals("0")) {
        deleteNodes(list, "AfterSurMouset");
      } else {
        deleteNodes(list, "PerSurMouset");
      } 
    } 
    if (flowCode.equals("AllonxNoBone")) {
      dataMap.put("flowCode", "AllonxBone");
      list = this.nodeConfigDao.findAllNodeConfigByFlowCode(dataMap);
      currentWork.setFlowCode("AllonxBone");
      List<JSONObject> orderTrackList = this.lcljTrackDao.findLcljOrderTrackByOrderNumber(orderNumber);
      String modeoperation = ((JSONObject)orderTrackList.get(0)).getString("modeoperation");
      if (modeoperation.equals("0")) {
        deleteNodes(list, "AfterSurMouset");
      } else {
        deleteNodes(list, "PerSurMouset");
      } 
    } 
    return list;
  }
  
  private List<LcljNodeConfig> changeNodeLimit(List<LcljNodeConfig> nodeList, String nodeId, String nodeLimit) {
    if (nodeList != null)
      for (LcljNodeConfig node : nodeList) {
        if (node.getNodeId().equals(nodeId)) {
          node.setNodeLimit(nodeLimit);
          break;
        } 
      }  
    return nodeList;
  }
  
  private List<LcljNodeConfig> deleteNodes(List<LcljNodeConfig> nodeList, String nodeId) {
    if (nodeList != null) {
      Iterator<LcljNodeConfig> it = nodeList.iterator();
      while (it.hasNext()) {
        if (((LcljNodeConfig)it.next()).getNodeId().equals(nodeId))
          it.remove(); 
      } 
    } 
    return nodeList;
  }
  
  public LcljWorklist findHadWorkByOrderNumberAndNodeId(Map<String, String> dataMap) throws Exception {
    List<LcljWorklist> list = new ArrayList<>();
    list = this.lcljWorkListDao.findHadWorkByOrderNumberAndNodeId(dataMap);
    if (list.size() > 0)
      return list.get(0); 
    return null;
  }
  
  private void updateOrderTrackBone(String orderNumber) throws Exception {
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("bone", "是");
    dataMap.put("orderNumber", orderNumber);
    this.lcljTrackDao.updateOrderTrack(dataMap);
  }
}
