package com.hudh.lclj.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.hudh.util.HUDHStaticVar;
import com.hudh.util.HUDHUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;
@Service
public class FlowOperateServiceImpl implements IFlowOperateService {
	/**
	 * 流程操作标识
	 */
	public static final String FLOW_OPERATE_SUBMIT = "Submit";
	public static final String FLOW_OPERATE_REJECT = "reject";
	public static final String FLOW_OPERATE_CREATE = "Create";
	
	/**
	 * 审核状态
	 */
	public static final Integer FLOW_EXAMINE_NO = 0;
	public static final Integer FLOW_EXAMINE_YES = 1;
	
	/**
	 * 是否超期
	 */
	public static final Integer FLOW_OVERDUE_NO = 0;
	public static final Integer FLOW_OVERDUE_YES = 1;
	
	/**
	 * 流程节点id标识
	 */
	public static final String FLOW_STATUS_STAST ="Start"; //术前准备
	public static final String FLOW_STATUS_PERSURMOUSET ="PerSurMouset"; //术前取模定咬合
	public static final String FLOW_STATUS_AFTERSURMOUSET ="AfterSurMouset"; //术后取模定咬合
	public static final String FLOW_STATUS_SURTRE ="Surtre"; //手术治疗
	public static final String FLOW_STATUS_THIRDREVIEW ="ThirdReview"; //第三次复查
	public static final String FLOW_STATUS_SECPHASE ="SecPhase"; //二期
	public static final String FLOW_STATUS_SECPHASEREVIEW ="SecPhaseReview"; //二期复查
	public static final String FLOW_STATUS_TRYON ="Tryon"; //试戴
	public static final String FLOW_STATUS_SECMOUSET ="SecMouset"; //二次取模
	public static final String FLOW_STATUS_SECTRYON ="SecTryon"; //再次试戴
	public static final String FLOW_STATUS_END = "End"; //结束
	/**
	 * 临床路径跟踪dao
	 */
	@Autowired
	private LcljTrackDao lcljTrackDao;
	
	/**
	 * 流程操作dao
	 */
	@Autowired
	private LcljWorkListDao lcljWorkListDao;
	
	@Autowired
	public HUDH_LcljVerificationSheetServiceImpl logic;
	
	/**
	 * 节点操作dao
	 */
	@Autowired
	private NodeConfigDao nodeConfigDao;
	
	@Autowired
	private ILcljOperationNodeInforService iLcljOperationNodeInforService;
	
	@Autowired
	private ILcljOperationNodeRejectService rejectService;
	
	@SuppressWarnings("unchecked")
	@Override
	public void createFlow(LcljOrderTrack lcljOrderTrack,HttpServletRequest request) throws Exception {
		//获取全部节点
		String nodeJson = lcljOrderTrack.getNodes();
		List<LcljNodeConfig> nodeList = HUDHUtil.parseJsonToObjectList(nodeJson, LcljNodeConfig.class);
		
		//创建开始节点数据 为已办理状态
		LcljWorklist workObj = new LcljWorklist(); //开始节点数据，为已办理的状态
		
		if ((nodeList.get(1).getNodeName()).equals("手术治疗") || (nodeList.get(1).getNodeName()).equals("手术治疗取模")) {//如果有术前取模
			workObj.setId(YZUtility.getUUID());
			workObj.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_YWC); //第一个节点为已办理的状态
			workObj.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ); //流程状态为进行中
			workObj.setFlowInfo(lcljOrderTrack.getNodes());
			workObj.setNodeId(nodeList.get(0).getNodeId());
			workObj.setNodeName(nodeList.get(0).getNodeName());
			workObj.setOrderNumber(lcljOrderTrack.getOrderNumber());
			workObj.setViewUrl(nodeList.get(0).getViewUrl());
			workObj.setOperate(FLOW_OPERATE_CREATE);
			workObj.setExamine(FLOW_EXAMINE_YES); //开始节点默认审核通过
			workObj.setFlowCode(nodeList.get(0).getFlowCode());
			workObj.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
			workObj.setCreator(SessionUtil.getLoginPerson(request).getSeqId());
			workObj.setOverdue(FLOW_OVERDUE_NO);
			workObj.setOrganization(ChainUtil.getCurrentOrganization(request));
			lcljWorkListDao.insertWorkList(workObj);
			
		//创建时特殊处理流程
//		specialCreateFlowDeal(nodeList, workObj, lcljOrderTrack);
			
			//创建下一节点数据 为待办状态
			workObj = new LcljWorklist();
			workObj.setId(YZUtility.getUUID());
			workObj.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ); //第一个节点为进行中
			workObj.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ); //流程状态为进行中
			workObj.setFlowInfo(JSON.toJSONString(nodeList));
			workObj.setNodeId(nodeList.get(1).getNodeId());
			workObj.setNodeName(nodeList.get(1).getNodeName());
			workObj.setViewUrl(nodeList.get(1).getViewUrl());
			workObj.setFlowCode(nodeList.get(1).getFlowCode());
			workObj.setOrderNumber(lcljOrderTrack.getOrderNumber());
			workObj.setOrganization(ChainUtil.getCurrentOrganization(request));
			workObj.setExamine(FLOW_EXAMINE_NO);
			workObj.setOverdue(FLOW_OVERDUE_NO);
			lcljWorkListDao.insertWorkList(workObj);
		} else {
			workObj.setId(YZUtility.getUUID());
			workObj.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_YWC); //第一个节点为已办理的状态
			workObj.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ); //流程状态为进行中
			workObj.setFlowInfo(lcljOrderTrack.getNodes());
			workObj.setNodeId(nodeList.get(0).getNodeId());
			workObj.setNodeName(nodeList.get(0).getNodeName());
			workObj.setOrderNumber(lcljOrderTrack.getOrderNumber());
			workObj.setViewUrl(nodeList.get(0).getViewUrl());
			workObj.setOperate(FLOW_OPERATE_CREATE);
			workObj.setExamine(FLOW_EXAMINE_YES); //开始节点默认审核通过
			workObj.setFlowCode(nodeList.get(0).getFlowCode());
			workObj.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
			workObj.setCreator(SessionUtil.getLoginPerson(request).getSeqId());
			workObj.setOverdue(FLOW_OVERDUE_NO);
			workObj.setOrganization(ChainUtil.getCurrentOrganization(request));
			lcljWorkListDao.insertWorkList(workObj);
			
			workObj.setId(YZUtility.getUUID());
			workObj.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_YWC); //第二个节点为已办理的状态
			workObj.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ); //流程状态为进行中
			workObj.setFlowInfo(lcljOrderTrack.getNodes());
			workObj.setNodeId(nodeList.get(1).getNodeId());
			workObj.setNodeName(nodeList.get(1).getNodeName());
			workObj.setOrderNumber(lcljOrderTrack.getOrderNumber());
			workObj.setViewUrl(nodeList.get(1).getViewUrl());
			workObj.setOperate(FLOW_OPERATE_CREATE);
			workObj.setExamine(FLOW_EXAMINE_YES); //开始节点默认审核通过
			workObj.setFlowCode(nodeList.get(1).getFlowCode());
			workObj.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
			workObj.setCreator(SessionUtil.getLoginPerson(request).getSeqId());
			workObj.setOverdue(FLOW_OVERDUE_NO);
			workObj.setOrganization(ChainUtil.getCurrentOrganization(request));
			lcljWorkListDao.insertWorkList(workObj);
			
			//创建下一节点数据 为待办状态
			workObj = new LcljWorklist();
			workObj.setId(YZUtility.getUUID());
			workObj.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ); //第一个节点为进行中
			workObj.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ); //流程状态为进行中
			workObj.setFlowInfo(JSON.toJSONString(nodeList));
			workObj.setNodeId(nodeList.get(2).getNodeId());
			workObj.setNodeName(nodeList.get(2).getNodeName());
			workObj.setViewUrl(nodeList.get(2).getViewUrl());
			workObj.setFlowCode(nodeList.get(2).getFlowCode());
			workObj.setOrderNumber(lcljOrderTrack.getOrderNumber());
			workObj.setOrganization(ChainUtil.getCurrentOrganization(request));
			workObj.setExamine(FLOW_EXAMINE_NO);
			workObj.setOverdue(FLOW_OVERDUE_NO);
			lcljWorkListDao.insertWorkList(workObj);
		}
		// 保存数据路径表数据
//		lcljTrackDao.saveLcljOrderTrack(lcljOrderTrack);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void submitFlow(String orderNumber, HttpServletRequest request) throws Exception {
		String nodeLimit = request.getParameter("nodeLimit");
		OperationNodeInfor dp = new OperationNodeInfor();
		//根据orderNumber查询对应的待办信息
		LcljWorklist tempWork = new LcljWorklist();
		tempWork.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
		tempWork.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
		tempWork.setOrderNumber(orderNumber);
		List<LcljWorklist> workList = lcljWorkListDao.findWorkByOrderNumber(tempWork);
		if(null != workList && workList.size() > 0) {
			LcljWorklist currentWork = workList.get(0);
			
			//获取dataId（用于判断具体节点业务数据是正常提交还是退回再提交）
			String dataId = currentWork.getDataId();
			
			LcljNodeConfig nextNode = null;
			//获取全部的节点信息
			List<LcljNodeConfig> nodes = HUDHUtil.parseJsonToObjectList(currentWork.getFlowInfo(), LcljNodeConfig.class);
			Collections.sort(nodes);
			int size = nodes.size();
			for(int i=0;i<size;i++) {
				//查找下一个节点
				if(currentWork.getNodeId().equals(nodes.get(i).getNodeId())){
					nextNode = nodes.get(i+1);
				}
			}
			//修改待办为已办 创建下一节点待办
			if(null != nextNode) {
				dp.setOrder_number(currentWork.getOrderNumber());
				dp.setNodeId(currentWork.getNodeId());
				dp.setNodeName(currentWork.getNodeName());
				//保存操作的业务数据
				OperationNodeInfor operationNodeInfor = iLcljOperationNodeInforService.insertOperationNodeInfor(dp, request, dataId);
				
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
				lMemo.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
				lMemo.setNodeLimit(nodeLimit);
				lMemo.setOrderNumber(operationNodeInfor.getOrder_number());
				if(dataId == null) {
					lMemo.setIsRejectStatus("0");
				} else {
					lMemo.setIsRejectStatus("1");
				}
				//记录
				bachsaveRecord(lMemo);
				
				currentWork.setDataId(operationNodeInfor.getSeqId());
				currentWork.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_YWC);
				currentWork.setOperate(FLOW_OPERATE_SUBMIT);
				currentWork.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
				currentWork.setCreator(SessionUtil.getLoginPerson(request).getSeqId());
				lcljWorkListDao.updateWorkByOrderNumber(currentWork);
				//这里需要出来特殊的集中情况需要修改流程
//				String newNodes = this.specialFlowDeal(dp, currentWork, request);
				
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
				// 如果下一节点为结束节点  流程结束
				if(nextNode.getNodeId().equals(FLOW_STATUS_END)) {
					nextWork.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
					nextWork.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_YWC);
					nextWork.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
				}else {
//					String createtime=HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24);
//					//添加到期提醒时间 根据nodeLimit判断时间
//					String nodelimit=nextNode.getNodeLimit();
//					if(!"0".equals(nodelimit)){
//						int day=0;
//						if(nodelimit.contains("术后")){
//							//当节点名称为二期时需要在术后时间进行判断
//							//查询手术创建时间
//							Map<String,String> map=new HashMap<String,String>();
//							map.put("order_number",currentWork.getOrderNumber());
//							map.put("nodeid","Surtre");
//							createtime=lcljWorkListDao.findCreatetimeWorkListByNode(map);
//						}
//						//其他则可以根据createtime提交时间进行判断填写
//						//如果后缀是天
//						if(nodelimit.contains("月")){
//							//如果后缀是天
//							String[] nodelist = nodelimit.split("后");
//							for (String node : nodelist) {
//								if(node.contains("个")){
//									String[] nodelist1 = node.split("个");
//									day=Integer.parseInt(nodelist1[0])*30;
//								}
//							}
//						}else if(nodelimit.contains("周")){
//							//后缀是周
//							String[] nodelist = nodelimit.split("后");
//							for (String node : nodelist) {
//								if(node.contains("-")){
//									String[] nodelist1 = node.split("-");
//									day=Integer.parseInt(nodelist1[0])*7;
//								}
//							}
//						}else if(nodelimit.contains("天")){
//							//如果后缀是天
//							String[] nodelist = nodelimit.split("后");
//							for (String node : nodelist) {
//								if(node.contains("-")){
//									String[] nodelist1 = node.split("-");
//									day=Integer.parseInt(nodelist1[0]);
//								}
//							}
//						}
//						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//						Date date=sdf.parse(createtime);
//						Calendar calendar=Calendar.getInstance();
//						calendar.setTime(date);
//						calendar.add(Calendar.DATE, day);
//						Date date2=calendar.getTime();
//						String dateFormat=sdf.format(date2);
//						nextWork.setDateDue(dateFormat);
//						nextWork.setRepairPhysician(request.getParameter("repairPhysician"));
//					}
					nextWork.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
					nextWork.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
				}
				lcljWorkListDao.insertWorkList(nextWork);
			}
		}
	}

	public void bachsaveRecord(LcljApprovedMemo lMemo) throws Exception{
		logic.insert(lMemo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void rejectFlow(String orderNumber, HttpServletRequest request) throws Exception {
		LcljOperateRejectRecord dp = new LcljOperateRejectRecord();
		//根据orderNumber查询对应的待办信息
		LcljWorklist tempWork = new LcljWorklist();
		tempWork.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
		tempWork.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
		tempWork.setOrderNumber(orderNumber);
		List<LcljWorklist> workList = lcljWorkListDao.findWorkByOrderNumber(tempWork);
		if(null != workList && workList.size() > 0) {
			LcljWorklist currentWork = workList.get(0);
			LcljNodeConfig nextNode = null;
			//获取全部的节点信息
			List<LcljNodeConfig> nodes = HUDHUtil.parseJsonToObjectList(currentWork.getFlowInfo(), LcljNodeConfig.class);
			Collections.sort(nodes);
			int size = nodes.size();
			for(int i=0;i<size;i++) {
				//查找上一步的节点
				if(currentWork.getNodeId().equals(nodes.get(i).getNodeId())){
					nextNode = nodes.get(i-1);
				}
			}
			//修改待办为已办 创建下一节点待办
			if(null != nextNode) {
				currentWork.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_YWC);
				currentWork.setOperate(FLOW_OPERATE_REJECT);
				currentWork.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
				currentWork.setCreator(SessionUtil.getLoginPerson(request).getSeqId());
				lcljWorkListDao.updateWorkByOrderNumber(currentWork);
				
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
				//获取历史办理的业务数据
				nextWork.setDataId(selectHadWorkData(orderNumber, nextNode.getNodeId()));
				nextWork.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
				nextWork.setFlowStatus(StaticVar.HUDH_LCLJ_FLOWSTA_INTEGER_JXZ);
				lcljWorkListDao.insertWorkList(nextWork);
				dp.setNodeId(nextNode.getNodeId());
				dp.setNodeName(nextNode.getNodeName());
				dp.setOrderNumber(orderNumber);
				rejectService.insertOperationNodeReject(dp, request);//记录退回信息（操作人、时间等等）
			}
		}
	}

	@Override
	public List<LcljWorklist> findHadWorkList(String orderNumber, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		List<LcljWorklist> list = new ArrayList<LcljWorklist>();
		list = lcljWorkListDao.findHadWorkList(orderNumber);
		return list;
	}
	
	/**
	 * 退回时获取以前办理时保存的业务数据
	 * @return
	 * @throws Exception 
	 */
	private String selectHadWorkData(String orderNumber,String nodeId)
			throws Exception{
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("orderNumber", orderNumber);
		dataMap.put("nodeId", nodeId);
		String dataId = null;
		LcljWorklist lcljWorklist = lcljWorkListDao.selectHadWorkData(dataMap);
		if(null != lcljWorklist) {
			dataId = lcljWorklist.getDataId();
		}
		return dataId;
	}
	
	/**
	 * 根据业务数据需要特殊流程处理的情况
	 * @param dp
	 * @param tempWork
	 * @throws Exception 
	 */
	@SuppressWarnings({"unchecked" })
	private String specialFlowDeal(OperationNodeInfor dp,LcljWorklist currentWork ,HttpServletRequest request) 
			throws Exception{
		List<LcljNodeConfig> newNodeList = new ArrayList<LcljNodeConfig>();
		//获取原始的节点配置
		newNodeList = HUDHUtil.parseJsonToObjectList(currentWork.getFlowInfo(), LcljNodeConfig.class);
		/**
		 * 第一种情况当处于手术治疗节点  并且为无植骨流程   时判断所有未植骨的流程的辅助手术字段是否勾选了，
		 * 如果没有勾选不做任何处理
		 * 如果勾选项包含植骨则改为植骨的流程时间不做任何时间变动
		 * 如果没有勾选植骨，勾选了其他五项则改为植骨流程且流程中第三次修复的时间变为术后8-9个月
		 * 如果既勾选了植骨也勾选了上面五项之一或多项则改为植骨流程且流程中第三次修复的时间变为术后8-9个月（同只勾选了前五项）
		 */
		if(currentWork.getNodeId().equals(FLOW_STATUS_SURTRE)){
			String assistOperation = dp.getAssist_operation(); //辅助手术值
			if(YZUtility.isNotNullOrEmpty(assistOperation)) {
				//判断是否包含前五项
				boolean preFiveSel = assistOperation.indexOf("内提") != -1 ||
									 assistOperation.indexOf("外提") != -1 ||
								     assistOperation.indexOf("骨劈开") != -1 ||
									 assistOperation.indexOf("骨挤压") != -1 ||
									 assistOperation.indexOf("自体骨移植") != -1;
				boolean isSixBone = assistOperation.indexOf("植骨") != -1;
				if(currentWork.getFlowCode().equals(StaticVar.HUDH_LCLJ_SINGLEORMULTINOBONE)
						|| currentWork.getFlowCode().equals(StaticVar.HUDH_LCLJ_LOCATORNOBONE)
								|| currentWork.getFlowCode().equals(StaticVar.HUDH_LCLJ_ALLONXNOBONE)) {
					if(preFiveSel) {
						newNodeList = this.findNodesToBone(currentWork,currentWork.getFlowCode(), request);
						updateOrderTrackBone(currentWork.getOrderNumber()); //修改对应ordertrack表植骨字段的值
						//将第三次复查时间改为术后270天(9个月)
						newNodeList = this.changeNodeLimit(newNodeList,FLOW_STATUS_THIRDREVIEW , "270");
					}else if(isSixBone && !preFiveSel){
						newNodeList = this.findNodesToBone(currentWork,currentWork.getFlowCode(), request);
						updateOrderTrackBone(currentWork.getOrderNumber()); //修改对应ordertrack表植骨字段的值
					}
				}else if (currentWork.getFlowCode().equals(StaticVar.HUDH_LCLJ_SINGLEORMULTIBONE)
						|| currentWork.getFlowCode().equals(StaticVar.HUDH_LCLJ_LOCATORBONE)
						|| currentWork.getFlowCode().equals(StaticVar.HUDH_LCLJ_ALLONXBONE)){  //如果本身是植骨路径则只改变期限
					if(preFiveSel) {
						newNodeList = this.changeNodeLimit(newNodeList,FLOW_STATUS_THIRDREVIEW , "270");
					}
				}
				
			}
		}
		
		/**
		 * 第二种情况当处于手术治疗节点时基台放置单选愈合基台没有二期和二期复查 其他情况均有二期和二期复查
		 */
		if(currentWork.getNodeId().equals(FLOW_STATUS_SURTRE)){
			String abutmentStation = dp.getAbutment_station(); //基台放置值
			if(YZUtility.isNotNullOrEmpty(abutmentStation)){
				boolean isAbutment = abutmentStation.indexOf("复合基台") != -1 ||
						abutmentStation.indexOf("locator基台") != -1 ||
								abutmentStation.indexOf("螺丝") != -1;
				boolean idYhAbutment = abutmentStation.indexOf("愈合基台") != -1;
				if(idYhAbutment && !isAbutment) {
					newNodeList = this.deleteNodes(newNodeList, FLOW_STATUS_SECPHASE);
					newNodeList = this.deleteNodes(newNodeList, FLOW_STATUS_SECPHASEREVIEW);
				}
			}
		}
		
		/**
		 * 第三种情况 当处于手术治疗节点并勾选了连桥选项是需要处理  选连桥-有试戴 |不选连桥没有试戴相关环节 只有单颗多颗有这种情况需要处理
		 */
		if(currentWork.getNodeId().equals(FLOW_STATUS_SURTRE)) {
			String connectingBridge = dp.getConnectingBridge(); //没有勾选连桥的情况需要去掉试戴环节
			if(YZUtility.isNullorEmpty(connectingBridge)) {
				if(currentWork.getFlowCode().equals(StaticVar.HUDH_LCLJ_SINGLEORMULTINOBONE)
						|| currentWork.getFlowCode().equals(StaticVar.HUDH_LCLJ_SINGLEORMULTIBONE)){
					deleteNodes(newNodeList, FLOW_STATUS_TRYON); //单颗及多颗流程去掉试戴环节
				}
				/*if(currentWork.getFlowCode().equals(StaticVar.HUDH_LCLJ_LOCATORNOBONE)
						|| currentWork.getFlowCode().equals(StaticVar.HUDH_LCLJ_LOCATORBONE)
						|| currentWork.getFlowCode().equals(StaticVar.HUDH_LCLJ_ALLONXNOBONE)
						|| currentWork.getFlowCode().equals(StaticVar.HUDH_LCLJ_ALLONXBONE)){
					deleteNodes(newNodeList, FLOW_STATUS_SECMOUSET);  //Local及all-on-x去掉再次取模及戴牙环节
					deleteNodes(newNodeList, FLOW_STATUS_SECTRYON);
				}*/
			}
		}
		
		return JSON.toJSONString(newNodeList);
	}
	
	/**
	 * 创建流程时根据术前或术后修改locator和all-on-x流程
	 * @param dp
	 * @param tempWork
	 * @throws Exception 
	 */
	private void specialCreateFlowDeal(List<LcljNodeConfig> nodeList ,
			LcljWorklist workObj,LcljOrderTrack lcljOrderTrack) 
			throws Exception{
		if(workObj.getFlowCode().equals(StaticVar.HUDH_LCLJ_LOCATORNOBONE) || workObj.getFlowCode().equals(StaticVar.HUDH_LCLJ_LOCATORBONE)
				|| workObj.getFlowCode().equals(StaticVar.HUDH_LCLJ_ALLONXNOBONE) || workObj.getFlowCode().equals(StaticVar.HUDH_LCLJ_ALLONXBONE)
				) {
			String modeoperation = lcljOrderTrack.getModeoperation(); //获取术前还是术后取模 0：术前   1：术后
			if(YZUtility.isNotNullOrEmpty(modeoperation) && modeoperation.equals("0")) { //术前
				deleteNodes(nodeList, FLOW_STATUS_AFTERSURMOUSET);
			}else if(YZUtility.isNotNullOrEmpty(modeoperation) && modeoperation.equals("1")){ //术后
				deleteNodes(nodeList, FLOW_STATUS_PERSURMOUSET);
			}else {
				throw new Exception("操作失败");
			}
			
		}
	}
	
	/**
	 * 根据无植骨的flowCode获取对应的植骨下的所有节点信息
	 * @param flowCode
	 * @return
	 * @throws Exception 
	 */
	private List<LcljNodeConfig> findNodesToBone(LcljWorklist currentWork,String flowCode,HttpServletRequest request) 
			throws Exception{
		Map<String,String> dataMap = new HashMap<String,String>();
		List<LcljNodeConfig> list = new ArrayList<LcljNodeConfig>();
		dataMap.put("organization", ChainUtil.getCurrentOrganization(request));
		String orderNumber = currentWork.getOrderNumber();
		if(flowCode.equals(StaticVar.HUDH_LCLJ_SINGLEORMULTINOBONE)) {
			dataMap.put("flowCode", StaticVar.HUDH_LCLJ_SINGLEORMULTIBONE);
			list = nodeConfigDao.findAllNodeConfigByFlowCode(dataMap);
			currentWork.setFlowCode(StaticVar.HUDH_LCLJ_SINGLEORMULTIBONE);
		}
		if(flowCode.equals(StaticVar.HUDH_LCLJ_LOCATORNOBONE)) {
			dataMap.put("flowCode", StaticVar.HUDH_LCLJ_LOCATORBONE);
			list = nodeConfigDao.findAllNodeConfigByFlowCode(dataMap);
			currentWork.setFlowCode(StaticVar.HUDH_LCLJ_LOCATORBONE);
			//根据术前或术后去掉对应的取模节点
			List<JSONObject> orderTrackList = lcljTrackDao.findLcljOrderTrackByOrderNumber(orderNumber);
			String modeoperation = orderTrackList.get(0).getString("modeoperation");
			if(modeoperation.equals("0")) {
				deleteNodes(list, FLOW_STATUS_AFTERSURMOUSET);
			}else {
				deleteNodes(list, FLOW_STATUS_PERSURMOUSET);
			}
		}
		if(flowCode.equals(StaticVar.HUDH_LCLJ_ALLONXNOBONE)) {
			dataMap.put("flowCode", StaticVar.HUDH_LCLJ_ALLONXBONE);
			list = nodeConfigDao.findAllNodeConfigByFlowCode(dataMap);
			currentWork.setFlowCode(StaticVar.HUDH_LCLJ_ALLONXBONE);
			//根据术前或术后去掉对应的取模节点
			List<JSONObject> orderTrackList = lcljTrackDao.findLcljOrderTrackByOrderNumber(orderNumber);
			String modeoperation = orderTrackList.get(0).getString("modeoperation");
			if(modeoperation.equals("0")) {
				deleteNodes(list, FLOW_STATUS_AFTERSURMOUSET);
			}else {
				deleteNodes(list, FLOW_STATUS_PERSURMOUSET);
			}
		}
		return list;
	}
	
	/**
	 * 修改对应节点的时限
	 * @param nodeList
	 * @param nodeId
	 * @param nodeLimit
	 */
	private List<LcljNodeConfig> changeNodeLimit(List<LcljNodeConfig> nodeList,String nodeId,String nodeLimit){
		if(null != nodeList) {
			for(LcljNodeConfig node : nodeList) {
				if(node.getNodeId().equals(nodeId)) {
					node.setNodeLimit(nodeLimit);
					break;
				}
			}
		}
		return nodeList;
	}
	
	/**
	 * 删除节点
	 * @param nodeList
	 * @param nodeId
	 * @return
	 */
	private List<LcljNodeConfig> deleteNodes(List<LcljNodeConfig> nodeList,String nodeId){
		if(null != nodeList) {
			Iterator<LcljNodeConfig> it = nodeList.iterator();
			while(it.hasNext()){
				if((it.next()).getNodeId().equals(nodeId)) {
					it.remove();
				}
			}
		}
		return nodeList;
	}

	@Override
	public LcljWorklist findHadWorkByOrderNumberAndNodeId(Map<String, String> dataMap) throws Exception {
		List<LcljWorklist> list = new ArrayList<LcljWorklist>();
		list = lcljWorkListDao.findHadWorkByOrderNumberAndNodeId(dataMap);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 修改植骨状态后改变对应odertrack表的bone字段为植骨
	 * @param orderNumber
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void updateOrderTrackBone(String orderNumber) 
			throws Exception{
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("bone", StaticVar.HUDH_LCLJ_YES);
		dataMap.put("orderNumber", orderNumber);
		lcljTrackDao.updateOrderTrack(dataMap);
	}
}
