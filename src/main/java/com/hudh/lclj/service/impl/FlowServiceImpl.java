package com.hudh.lclj.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.hudh.lclj.StaticVar;
import com.hudh.lclj.dao.LcljOptRecodeDao;
import com.hudh.lclj.dao.LcljTrackDao;
import com.hudh.lclj.dao.NodeConfigDao;
import com.hudh.lclj.dao.SysParaDao;
import com.hudh.lclj.entity.LcljNode;
import com.hudh.lclj.entity.LcljNodeConfig;
import com.hudh.lclj.entity.LcljOptRecode;
import com.hudh.lclj.entity.LcljOrderTrack;
import com.hudh.lclj.entity.LcljOrderTrackDeleteRecord;
import com.hudh.lclj.entity.LcljWorkNode;
import com.hudh.lclj.entity.LcljWorklist;
import com.hudh.lclj.entity.OperatingRecord;
import com.hudh.lclj.service.IFlowOperateService;
import com.hudh.lclj.service.IFlowService;
import com.hudh.lclj.service.ILcljOrderTrackDeleteService;
import com.hudh.lclj.util.OrderNumberUtil;
import com.hudh.lclj.util.ParseNodesXml;
import com.hudh.lclj.util.SysDictUtil;
import com.hudh.lclj.util.SysPersonUtil;
import com.hudh.util.HUDHStaticVar;
import com.hudh.util.HUDHUtil;
import com.hudh.zzbl.dao.DzblDao;
import com.hudh.zzbl.dao.NotificationDao;
import com.hudh.zzbl.dao.RepairSchemeConfirmDao;
import com.hudh.zzbl.dao.ZzblCheckDao;
import com.hudh.zzbl.dao.ZzblDao;
import com.hudh.zzbl.entity.AdviceNote;
import com.hudh.zzbl.entity.FamiliarBook;
import com.hudh.zzbl.entity.Notification;
import com.hudh.zzbl.entity.RepairSchemeConfirm;
import com.hudh.zzbl.entity.ZzblCheck;
import com.hudh.zzbl.entity.ZzblOperation;
import com.kqds.entity.base.KqdsReg;
import com.kqds.entity.sys.BootStrapPage;
import com.kqds.entity.sys.YZDict;
import com.kqds.entity.sys.YZPara;
import com.kqds.entity.sys.YZPerson;
import com.kqds.service.base.hzjd.KQDS_UserDocumentLogic;
import com.kqds.service.base.reg.KQDS_REGLogic;
import com.kqds.service.sys.dict.YZDictLogic;
import com.kqds.util.sys.ConstUtil;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.TableNameUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;
import com.kqds.util.sys.other.CacheUtil;
import com.kqds.util.sys.sys.DictUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class FlowServiceImpl implements IFlowService {
	/**
	 * 临床路径跟踪dao
	 */
	@Autowired
	private LcljTrackDao lcljTrackDao;

	/**
	 * 备注信息Dao
	 */
	@Autowired
	private LcljOptRecodeDao lcljOptRecodeDao;
	
	/**
	 * 配置信息Dao
	 */
	@Autowired
	private SysParaDao sysParaDao;
	
	/**
	 * 病历dao
	 */
	@Autowired
	private ZzblDao zzblDao;
	
	/**
	 * 病历修复检查dao
	 */
	private ZzblCheckDao zzblCheckDao;
	
	/**
	 * 病历修复确认dao
	 */
	private RepairSchemeConfirmDao rscDao;
	
	/**
	 * 种植病历DAO
	 */
	private DzblDao dzblDao;
	
	@Autowired
	private ILcljOrderTrackDeleteService trackDeleteService;//临床路径删除dao
	
	/**
	 * 流程操作service
	 */
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
	//告知通知单
	@Autowired
	private NotificationDao notificationDao;

	@SuppressWarnings("static-access")
	@Transactional(rollbackFor = { Exception.class })
	@Override
	public void saveLcljOrderTrackInfo(LcljOrderTrack lcljOrderTrack, String id,HttpServletRequest request) throws Exception {
		String seqId = request.getParameter("seqId");
		String iscreateLclj = request.getParameter("iscreateLclj");
		
		String id1 = YZUtility.getUUID();
		// 填充跟踪表数据
		lcljOrderTrack.setId(id1);
		lcljOrderTrack.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		lcljOrderTrack.setLastTime(lcljOrderTrack.getCreatetime());
		lcljOrderTrack.setIsobsolete("0");
		lcljOrderTrack.setSsStatus(HUDHStaticVar.COMPLETE_STATE_JXZ);
		// 获取下一个编号
		String orderNumber = OrderNumberUtil.getInstance().getNextOrderNumber();// 获取下一个编号
		lcljOrderTrack.setOrderNumber(orderNumber);
		// 获取流程所有节点和初始节点步骤
//		this.createOperateInfoByConfig(lcljOrderTrack,request);
		// 保存数据路径表数据
		lcljTrackDao.saveLcljOrderTrack(lcljOrderTrack);
		userDocumentLogic.isCreateLclj(seqId,iscreateLclj);//在患者档案表添加标识，判断该患者已创建临床路径
		
//		flowOperateService.createFlow(lcljOrderTrack, request);
	}


	/**
	 * 根据xml配置获取流程操作的所有节点信息
	 * 
	 * @param lcljOrderTrack
	 */
	private void createOperateInfo(LcljOrderTrack lcljOrderTrack) {
		List<LcljNode> operateList = new ArrayList<LcljNode>();
		String type = lcljOrderTrack.getType();
		String bone = lcljOrderTrack.getBone();
		if (type.equals(StaticVar.HUDH_LCLJ_DDK) && bone.equals(StaticVar.HUDH_LCLJ_NO)) { // 单颗多颗---无植骨
			operateList = ParseNodesXml.getOperateMap(StaticVar.HUDH_LCLJ_SINGLEORMULTINOBONE);
		} else if (type.equals(StaticVar.HUDH_LCLJ_DDK) && bone.equals(StaticVar.HUDH_LCLJ_YES)) { // 单颗多颗---植骨
			operateList = ParseNodesXml.getOperateMap(StaticVar.HUDH_LCLJ_SINGLEORMULTIBONE);
		} else if (type.equals(StaticVar.HUDH_LCLJ_LOCATOR) && bone.equals(StaticVar.HUDH_LCLJ_NO)) { // Locator---无植骨
			operateList = ParseNodesXml.getOperateMap(StaticVar.HUDH_LCLJ_LOCATORNOBONE);
		} else if (type.equals(StaticVar.HUDH_LCLJ_LOCATOR) && bone.equals(StaticVar.HUDH_LCLJ_YES)) { // Locator---植骨
			operateList = ParseNodesXml.getOperateMap(StaticVar.HUDH_LCLJ_LOCATORBONE);
		} else if (type.equals(StaticVar.HUDH_LCLJ_ALLONX) && bone.equals(StaticVar.HUDH_LCLJ_NO)) { // All-ob-x---无植骨
			operateList = ParseNodesXml.getOperateMap(StaticVar.HUDH_LCLJ_ALLONXNOBONE);
		} else if (type.equals(StaticVar.HUDH_LCLJ_ALLONX) && bone.equals(StaticVar.HUDH_LCLJ_YES)) { // All-ob-x---植骨
			operateList = ParseNodesXml.getOperateMap(StaticVar.HUDH_LCLJ_ALLONXBONE);
		}

		if (null != operateList) {
			// 对operateList排序取num最小的一条作为初始节点
			Collections.sort(operateList, new Comparator<LcljNode>() {
				@Override
				public int compare(LcljNode o1, LcljNode o2) {
					int i = Integer.valueOf(o1.getNum()) - Integer.valueOf(o2.getNum());
					return i;
				}
			});
			lcljOrderTrack.setFlowLink(operateList.get(1).getName()); //当前节点设置成第二个节点
			operateList.get(0).setStus(StaticVar.HUDH_LCLJ_FLOWSTA_YWC); //将第一个节点状态改成已完成-术前检查节点
			operateList.get(1).setStus(StaticVar.HUDH_LCLJ_FLOWSTA_JXZ); //将第一个节点状态改成进行中
			//将更新完后的节点信息保存到库中
			lcljOrderTrack.setNodes(JSON.toJSONString(operateList));
		}
	}

	/**
	 * 根据管理端配置获取流程节点信息
	 * 
	 * @param type
	 * @param lcljOrderTrack
	 * @param request
	 * @param dentalJaw
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private void createOperateInfoByConfig(LcljOrderTrack lcljOrderTrack,String type,String flowCode,String dentalJaw,HttpServletRequest request) 
			throws Exception {
//		List<LcljNodeConfig> operateList = new ArrayList<LcljNodeConfig>();
//		String type = lcljOrderTrack.getType();
//		String bone = lcljOrderTrack.getBone();
		List<LcljNodeConfig> list = nodeConfigDao.findLcljNodeConfig(flowCode);
		if (null != list) {
			/*lcljOrderTrack.setFlowLink(operateList.get(1).getName()); //当前节点设置成第二个节点
			operateList.get(0).setStus(StaticVar.HUDH_LCLJ_FLOWSTA_YWC); //将第一个节点状态改成已完成-术前检查节点
			operateList.get(1).setStus(StaticVar.HUDH_LCLJ_FLOWSTA_JXZ); //将第一个节点状态改成进行中
*/			//将更新完后的节点信息保存到库中
			Collections.sort(list);
			lcljOrderTrack.setNodes(JSON.toJSONString(list));
		}
		
//		Map<String,String> dataMap = new HashMap<String,String>();
//		dataMap.put("organization", ChainUtil.getCurrentOrganization(request)); //门诊
//		if (type.equals(StaticVar.HUDH_LCLJ_DDK) && bone.equals(StaticVar.HUDH_LCLJ_NO)) { // 单颗多颗---无植骨
//			dataMap.put("flowCode", StaticVar.HUDH_LCLJ_SINGLEORMULTINOBONE);
//		} else if (type.equals(StaticVar.HUDH_LCLJ_DDK) && bone.equals(StaticVar.HUDH_LCLJ_YES)) { // 单颗多颗---植骨
//			dataMap.put("flowCode", StaticVar.HUDH_LCLJ_SINGLEORMULTIBONE);
//		} else if (type.equals(StaticVar.HUDH_LCLJ_LOCATOR) && bone.equals(StaticVar.HUDH_LCLJ_NO)) { // Locator---无植骨
//			dataMap.put("flowCode", StaticVar.HUDH_LCLJ_LOCATORNOBONE);
//		} else if (type.equals(StaticVar.HUDH_LCLJ_LOCATOR) && bone.equals(StaticVar.HUDH_LCLJ_YES)) { // Locator---植骨
//			dataMap.put("flowCode", StaticVar.HUDH_LCLJ_LOCATORBONE);
//		} else if (type.equals(StaticVar.HUDH_LCLJ_ALLONX) && bone.equals(StaticVar.HUDH_LCLJ_NO)) { // All-ob-x---无植骨
//			dataMap.put("flowCode", StaticVar.HUDH_LCLJ_ALLONXNOBONE);
//		} else if (type.equals(StaticVar.HUDH_LCLJ_ALLONX) && bone.equals(StaticVar.HUDH_LCLJ_YES)) { // All-ob-x---植骨
//			dataMap.put("flowCode", StaticVar.HUDH_LCLJ_ALLONXBONE);
//		}
//		operateList = lcljNodeConfigService.findAllNodeConfigByFlowCodeObj(dataMap);
//		if (null != operateList) {
//			/*lcljOrderTrack.setFlowLink(operateList.get(1).getName()); //当前节点设置成第二个节点
//			operateList.get(0).setStus(StaticVar.HUDH_LCLJ_FLOWSTA_YWC); //将第一个节点状态改成已完成-术前检查节点
//			operateList.get(1).setStus(StaticVar.HUDH_LCLJ_FLOWSTA_JXZ); //将第一个节点状态改成进行中
//*/			//将更新完后的节点信息保存到库中
//			Collections.sort(operateList);
//			lcljOrderTrack.setNodes(JSON.toJSONString(operateList));
//		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject findOrderTrackInfo(String orderNumber) throws Exception {
		// TODO Auto-generated method stub
		JSONObject lcljOrderTrack = this.findLcljOrderTrackAndHzInfo(orderNumber);
		
		if(null != lcljOrderTrack) {
			//判断当前节点是否超期
			String currentNode = (String)lcljOrderTrack.getString("flow_link");
			List<LcljNode> nodeList = HUDHUtil.parseJsonToObjectList(lcljOrderTrack.getString("nodes"), LcljNode.class);
			for(LcljNode node : nodeList) {
				if(currentNode.equals(node.getName())) {
					int limit = Integer.valueOf(node.getLimit()); //获取时限
					String lastTime = lcljOrderTrack.getString("last_time");
					//获取时间差
					int day = HUDHUtil.shiJianCha(lastTime, HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
					if(limit >= 0 && day > limit) { //如果超期 配置小于0不考虑超期
						node.setStus(StaticVar.HUDH_LCLJ_FLOWSTA_CQ);
						//更新返回值
						lcljOrderTrack.put("nodes", JSON.toJSON(nodeList));
						//执行数据库更新
						Map<String,String> dataMap = new HashMap<String,String>();
						dataMap.put("orderNumber", orderNumber);
						dataMap.put("nodes", JSON.toJSONString(nodeList));
						lcljTrackDao.updateOrderTrack(dataMap);
					}
					break;
				}
			}
//			String currentNode = lcljOrderTrack.getString("nodename");//当前节点名称  syp 2019-8-2
//			List<LcljWorkNode> nodeList = HUDHUtil.parseJsonToObjectList(lcljOrderTrack.getString("nodes"), LcljWorkNode.class);
//			for(LcljWorkNode node : nodeList) {
//				if(currentNode.equals(node.getNodeName())) {
//					int limit = Integer.valueOf(node.getNodeLimit()); //获取时限
//					String lastTime = lcljOrderTrack.getString("last_time");
//					//获取时间差
//					int day = HUDHUtil.shiJianCha(lastTime, HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
//					if(limit >= 0 && day > limit) { //如果超期 配置小于0不考虑超期
//						node.setNodeStatus(StaticVar.HUDH_LCLJ_FLOWSTA_CQ);
//						//更新返回值
//						lcljOrderTrack.put("nodes", JSON.toJSON(nodeList));
//						//执行数据库更新
//						Map<String,String> dataMap = new HashMap<String,String>();
//						dataMap.put("orderNumber", orderNumber);
//						dataMap.put("nodes", JSON.toJSONString(nodeList));
//						lcljTrackDao.updateOrderTrack(dataMap);
//					}
//					break;
//				}
//			}
		}
		return lcljOrderTrack;
	}
	
	@Override
	public void gotoNextNode(String orderNumber) throws Exception {
		// 根据编号获取手术信息
		JSONObject lcljOrderTrack = this.findLcljOrderTrackByOrderNumber(orderNumber);
		// 获取手术下对应的所有节点及当前所处节点信息
		String nodes = null;
		String currentNode = null;
		if (null != lcljOrderTrack) {
			nodes = JSON.toJSONString(lcljOrderTrack.get("nodes"));
			currentNode = (String) lcljOrderTrack.get("flow_link");
		}
		// 获取当前步骤下一节点
		String nextNode = this.findNextNode(nodes, currentNode);
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("nextNode", nextNode);
		dataMap.put("orderNumber", orderNumber);
		dataMap.put("lastTime", HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24)); //更新本次提交时间
		nodes = this.updateNodeStatus(nodes, currentNode, nextNode); //更新当前节点和下一节点状态
		dataMap.put("nodes", nodes);
		// 判断下一节点是否是结束,同时更新流程状态
		if (StaticVar.HUDH_LCLJ_FLOWSTA_END.equals(nextNode)) {
			dataMap.put("flowStatus", HUDHStaticVar.COMPLETE_STATE_YWC);
		}
		lcljTrackDao.updateOrderTrack(dataMap);
		this.addNodeOperateLog();
	}

	/**
	 * 根据编号获取手术对象
	 * 
	 * @param orderNumber
	 * @throws Exception
	 */
	private JSONObject findLcljOrderTrackByOrderNumber(String orderNumber) throws Exception {
		List<JSONObject> ssInfo = lcljTrackDao.findLcljOrderTrackByOrderNumber(orderNumber);
		if (null != ssInfo && ssInfo.size() > 0) {
			return ssInfo.get(0);
		}
		return null;

	}
	
	/**
	 * 提交后将当前状态改为已完成，下一节点改为进行中状态,但不改变超期的状态
	 * @param nodes
	 */
	@SuppressWarnings("unchecked")
	private String updateNodeStatus(String nodes,String currentNode,String nextNode){
		List<LcljNode> nodeList = HUDHUtil.parseJsonToObjectList(nodes, LcljNode.class);
		for(LcljNode node : nodeList) {
			if(node.getName().equals(currentNode) && !node.getStus().equals(StaticVar.HUDH_LCLJ_FLOWSTA_CQ))	{
				node.setStus(StaticVar.HUDH_LCLJ_FLOWSTA_YWC);
			}else if(node.getName().equals(nextNode)) {
				node.setStus(StaticVar.HUDH_LCLJ_FLOWSTA_JXZ);
			}
		}
		return JSON.toJSONString(nodeList);
	}
	
	/**
	 * 退回后将当前状态改为已完成，下一节点改为进行中状态,但不改变超期的状态
	 * @param nodes
	 */
	@SuppressWarnings("unchecked")
	private String updateRejectNodeStatus(String nodes,String currentNode,String lastNode){
		List<LcljNode> nodeList = HUDHUtil.parseJsonToObjectList(nodes, LcljNode.class);
		for(LcljNode node : nodeList) {
			if(node.getName().equals(currentNode) && !node.getStus().equals(StaticVar.HUDH_LCLJ_FLOWSTA_CQ))	{
				node.setStus(StaticVar.HUDH_LCLJ_FLOWSTA_WWC);
			}else if(node.getName().equals(lastNode)) {
				node.setStus(StaticVar.HUDH_LCLJ_FLOWSTA_JXZ);
			}
		}
		return JSON.toJSONString(nodeList);
	}
	
	/**
	 * 根据编号获取手术对象及患者信息
	 * 
	 * @param orderNumber
	 * @throws Exception
	 */
	private JSONObject findLcljOrderTrackAndHzInfo(String orderNumber) throws Exception {
		List<JSONObject> ssInfo = lcljTrackDao.findLcljOrderTrackAndHzInfo(orderNumber);
		if (null != ssInfo && ssInfo.size() > 0) {
			return ssInfo.get(0);
		}
		return null;

	}

	/**
	 * 获取 下一个流程环节
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String findNextNode(String nodes, String currentNode) {
		String nextNode = null;
		List<LcljNode> nodeList = HUDHUtil.parseJsonToObjectList(nodes, LcljNode.class);
		if (null != nodes && nodeList.size() > 0) {
			// 对operateList排序去按顺序取下一个节点
			Collections.sort(nodeList, new Comparator<LcljNode>() {
				@Override
				public int compare(LcljNode o1, LcljNode o2) {
					int i = Integer.valueOf(o1.getNum()) - Integer.valueOf(o2.getNum());
					return i;
				}
			});

			int length = nodeList.size();
			for (int i = 0; i < length; i++) {
				if (nodeList.get(i).getName().equals(currentNode)) {
					nextNode = nodeList.get(i + 1).getName();
					break;
				}
			}
		}
		return nextNode;
	}
	
	/**
	 * 获取 上一个流程环节
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String findLastNode(String nodes, String currentNode) {
		String nextNode = null;
		List<LcljNode> nodeList = HUDHUtil.parseJsonToObjectList(nodes, LcljNode.class);
		if (null != nodes && nodeList.size() > 0) {
			// 对operateList排序去按顺序取下一个节点
			Collections.sort(nodeList, new Comparator<LcljNode>() {
				@Override
				public int compare(LcljNode o1, LcljNode o2) {
					int i = Integer.valueOf(o1.getNum()) - Integer.valueOf(o2.getNum());
					return i;
				}
			});

			int length = nodeList.size();
			for (int i = 0; i < length; i++) {
				if (nodeList.get(i).getName().equals(currentNode)) {
					nextNode = nodeList.get(i - 1).getName();
					break;
				}
			}
		}
		return nextNode;
	}
	
	@Override
	public void saveOptRecode(LcljOptRecode lcljOptRecode) throws Exception {
		JSONObject lcljOrderTrack = this.findLcljOrderTrackByOrderNumber(lcljOptRecode.getOrderNumber());
		// 获取当前手术操作环节
		String flowLink = (String) lcljOrderTrack.getString("flow_link");

		lcljOptRecode.setId(YZUtility.getUUID());
		lcljOptRecode.setFlowLink(flowLink);
		lcljOptRecode.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		lcljOptRecode.setStatus("0");
		lcljOptRecodeDao.saveLcljOptRecode(lcljOptRecode);
		this.addNodeOperateLog();
	}

	@Override
	public List<JSONObject> findOptRecodeList(String orderNumber,String searchFlowink) throws Exception {
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("orderNumber", orderNumber);
		dataMap.put("searchFlowink", searchFlowink);
		return lcljOptRecodeDao.findLcljOptRecodeList(dataMap);
	}

	/**
	 * 记录点击下一步操作及添加备注信息的日志
	 */
	private void addNodeOperateLog(){
		System.out.println("这里记录点击下一步操作及添加备注信息的日志");
	}

	@Override
	public JSONObject findOrderTrackInforByOrderNumber(YZPerson person, BootStrapPage bp, Map<String, String> map, String organization, JSONObject json) throws Exception {
		JSONObject jsonO = null;
		if((person.getDeptId()).equals("45df337f-8687-4c0b-aa29-95e8b4a59d5d")) {
			jsonO = lcljTrackDao.findOrderTrackInforByOrderNumber(map, bp, json);
		} else if ((person.getDeptId()).equals("4b88b74c-9373-4b5f-9d53-3c115de7a7e4")) {
			jsonO = lcljTrackDao.findOrderTrackInforByOrderNumber(map, bp, json);
		} else if ((person.getDeptId()).equals("015d89d6-7b32-47ec-9557-233407c7fc71")) {
			jsonO = lcljTrackDao.findOrderTrackInforByOrderNumber(map, bp, json);
		} else if ((person.getDeptId()).equals("72d1324f-22a2-41a7-9739-8b64c50e7b97")) {
			jsonO = lcljTrackDao.findOrderTrackInforByOrderNumber(map, bp, json);
		} else if ((person.getDeptId()).equals("3b47a915-977b-4799-acf6-540d525722f4")) {
			jsonO = lcljTrackDao.findOrderTrackInforByOrderNumber(map, bp, json);
		} else if ((person.getDeptId()).equals("b4f9dc9e-d2e0-44e1-ba37-eecaddcbf93d")) {
			jsonO = lcljTrackDao.findOrderTrackInforByOrderNumber(map, bp, json);
		} else if ((person.getDeptId()).equals("4fbf1126-f689-4d59-8fc7-93b444eea141")) {
			jsonO = lcljTrackDao.findOrderTrackInforByOrderNumber(map, bp, json);
		} else {
			jsonO = lcljTrackDao.findOrderTrackInforByOrderNumberCjstatus(map, bp, json);
		}
//		JSONObject jsonO = lcljTrackDao.findOrderTrackInforByOrderNumber(map, bp, json);
//		return jsonO;
//		JSONArray array = jsonO.getJSONArray("rows");
//		Object o = array.get(0);
//		JSONObject jso = JSONObject.fromObject(o);
//		boolean flag = jso.containsKey("cjstatus");
//		if(flag) {
//			for (Object object : array) {
//				JSONObject jo = JSONObject.fromObject(object);
//				String status = jo.getString("cjstatus");
//				if(status.equals("0")) {
//					array.remove(object);
//				}
//			}
//		}
		return jsonO;
	}

	@Override
	public JSONObject findLcljAdmin(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		/*Map<String,Map<String,String>> dataMap = new HashMap<String,Map<String,String>>();
		Map<String,String> tempMap = new HashMap<String,String>();
		tempMap.put("para_name", StaticVar.LCLJ_ADMIN);
		dataMap.put("params",tempMap);
		List<YZPara> list = sysParaDao.findLcljOrderByBlcode(dataMap);
		if(null != list && list.size() > 0) {
			return list.get(0).getParaValue();
		}*/
		String organization = ChainUtil.getCurrentOrganization(request);
		List<String> list = new ArrayList<String>();
		list.add(StaticVar.LCLJ_ADMIN);
		list.add(StaticVar.LCLJ_AGENCY_USER);
		List<JSONObject> jsonO = sysParaDao.getParaValueListByName(list, request, organization);
		JSONObject jo = new JSONObject();
		YZPerson person = SessionUtil.getLoginPerson(request);
		if(null != jsonO && jsonO.size() > 0) {
			for(JSONObject object : jsonO) {
				if(object.get("para_name").equals(StaticVar.LCLJ_ADMIN)) {
					jo.put("lcljAmdin", person.getUserId().equals(object.get("para_value")) ? 
				    		StaticVar.LCLJ_HASPASS_YES:StaticVar.LCLJ_HASPASS_NO);
				}else if(object.get("para_name").equals(StaticVar.LCLJ_AGENCY_USER)){
					jo.put("agencyUserId", object.get("para_value"));
					Map<String,Map<String,String>> dataMap = new HashMap<String,Map<String,String>>();
					Map<String,String> tempMap = new HashMap<String,String>();
					tempMap.put("seq_id", (String) object.get("para_value"));
					dataMap.put("params", tempMap);
					List<YZPerson> persions = sysParaDao.selectUserBySeqId(dataMap);
					if(null != persions && persions.size() > 0) {
						jo.put("agencyUserName", persions.get(0).getUserName());
					}
					jo.put("agencyUser_seq_id", object.get("seq_id"));
				}
			}
		}
		return jo;
	}

	@Override
	public List<JSONObject> findLcljAdminOrAgency(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		String organization = ChainUtil.getCurrentOrganization(request);
		List<String> list = new ArrayList<String>();
		list.add(StaticVar.LCLJ_ADMIN);
		list.add(StaticVar.LCLJ_AGENCY_USER);
		List<JSONObject> jsonO = sysParaDao.getParaValueListByName(list, request, organization);
		return jsonO;
	}

	@Override
	public void updateAgencyUser(YZPara yzPara) throws Exception {
		// TODO Auto-generated method stub
		sysParaDao.updateAgencyUser(yzPara);
	}

	@Override
	public void updateRemarkStus(String id,String status) throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("id", id);
		dataMap.put("status", status);
		lcljOptRecodeDao.updateRemarkStus(dataMap);
	}
	
	@Override
	public List<KqdsReg> findRegListByBlcode(String orderNumber) throws Exception{
		//根据手术编号获取患者病历号
		List<JSONObject> flowTrackList = lcljTrackDao.findLcljOrderTrackByOrderNumber(orderNumber);
		if(null != flowTrackList && flowTrackList.size() > 0) {
			String blCode = (String) flowTrackList.get(0).get("blcode");
			//Map<String,Map<String,String>> dataMap = new HashMap<String,Map<String,String>>();
			Map<String,String> tempMap = new HashMap<String,String>();
			tempMap.put("usercode", blCode);
			//dataMap.put("params", tempMap);
			List<KqdsReg> list = new ArrayList<KqdsReg>();
			list = lcljTrackDao.findRegListByBlcode(tempMap);
			//将就诊分类和挂号分类换成对应的中文描述
			/*for(KqdsReg kqdsReg : list) {
				kqdsReg.setRecesort(SysDictUtil.getInstance().getSysDictList().get(kqdsReg.getRecesort()).getDictName());//就诊分类
				kqdsReg.setRegsort(SysDictUtil.getInstance().getSysDictList().get(kqdsReg.getRegsort()).getDictName());//挂号分类
				if(kqdsReg.getDoctor()!=null && !kqdsReg.getDoctor().equals("")){					
					kqdsReg.setDoctor(SysPersonUtil.getInstance().getSysPersonList().get(kqdsReg.getDoctor()).getUserName());//种植医生
				}
				if(!kqdsReg.getRepairdoctor().equals("") && !kqdsReg.getRepairdoctor().equals(null)){					
					kqdsReg.setRepairdoctor(SysPersonUtil.getInstance().getSysPersonList().get(kqdsReg.getRepairdoctor()).getUserName());//修复医生
				}
			}*/
			return list;
		}
		return null;
		
	}

	@Override
	public void reject(String orderNumber) throws Exception {
		// TODO Auto-generated method stub
		// 根据编号获取手术信息
		JSONObject lcljOrderTrack = this.findLcljOrderTrackByOrderNumber(orderNumber);
		// 获取手术下对应的所有节点及当前所处节点信息
		String nodes = null;
		String currentNode = null;
		if (null != lcljOrderTrack) {
			nodes = JSON.toJSONString(lcljOrderTrack.get("nodes"));
			currentNode = (String) lcljOrderTrack.get("flow_link");
		}
		// 获取当前步骤上一节点
		String lastNode = this.findLastNode(nodes, currentNode);
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("nextNode", lastNode);
		dataMap.put("orderNumber", orderNumber);
		dataMap.put("lastTime", HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24)); //更新本次提交时间
		nodes = this.updateRejectNodeStatus(nodes, currentNode, lastNode); //更新当前节点和上一节点状态
		dataMap.put("nodes", nodes);
		// 判断下一节点是否是结束,同时更新流程状态
		if (StaticVar.HUDH_LCLJ_FLOWSTA_END.equals(lastNode)) {
			dataMap.put("flowStatus", HUDHStaticVar.COMPLETE_STATE_YWC);
		}
		lcljTrackDao.updateOrderTrack(dataMap);
		this.addNodeOperateLog();
	}

	@Override
	public JSONObject findOrderTrackInforByConditionQuery(YZPerson person, Map<String, String> map,
			String organization) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = lcljTrackDao.findOrderTrackInforByConditionQuery(person, map, organization);
		return json;
	}

	@Override
	public void updateOrderTrack(Map<String, String> dataMap) throws Exception {
		// TODO Auto-generated method stub
		if(null != dataMap && dataMap.size() >= 0) {
			lcljTrackDao.updateOrderTrack(dataMap);
		}
	}

	@Override
	public void updateOrderTrackById(Map<String, String> dataMap) throws Exception {
		// TODO Auto-generated method stub
		lcljTrackDao.updateOrderTrackById(dataMap);
	}

	@Override
	public void deleteOrderTrackInforById(String id) throws Exception {
		// TODO Auto-generated method stub
		lcljTrackDao.deleteOrderTrackInforById(id);
	}

	@Override
	public LcljOrderTrack findOrderTrackInforById(String id) throws Exception {
		// TODO Auto-generated method stub
		lcljTrackDao.findOrderTrackInforById(id);
		return null;
	}

	@SuppressWarnings("static-access")
	@Override
	public void changeLcljOrderTrackBoneStatus(LcljOrderTrack lcljOrderTrack, String id) throws Exception {
		// TODO Auto-generated method stub
		String id1 = YZUtility.getUUID();
		// 填充跟踪表数据
		lcljOrderTrack.setId(id1);
		lcljOrderTrack.setCreatetime(HUDHUtil.getCurrentTime(HUDHStaticVar.DATE_FORMAT_YMDHMS24));
		lcljOrderTrack.setLastTime(lcljOrderTrack.getCreatetime());
		lcljOrderTrack.setIsobsolete("0");
		lcljOrderTrack.setSsStatus(HUDHStaticVar.COMPLETE_STATE_JXZ);
		// 获取下一个编号
		String orderNumber = OrderNumberUtil.getInstance().getNextOrderNumber();// 获取下一个编号
		lcljOrderTrack.setOrderNumber(orderNumber);
		// 获取流程所有节点和初始节点步骤
		this.createOperateInfo(lcljOrderTrack);
		LcljOrderTrack lTrack = lcljTrackDao.findOrderTrackInforById(id);
		if (lTrack.getBone().equals("是")) {
			lcljTrackDao.updateLcljOrderTrackIsobsolete(id);
			lcljTrackDao.changeLcljOrderTrackBoneStatus(lcljOrderTrack);
		} else if (lTrack.getBone().equals("否")) {
			lcljTrackDao.updateLcljOrderTrackIsobsolete(id);
			lcljTrackDao.changeLcljOrderTrackBoneStatus(lcljOrderTrack);
		}
	}

	@Override
	public JSONObject findLcljOrderTrsackById(String id) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = lcljTrackDao.findLcljOrderTrsackById(id);
		return json;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateOrderTrackNodes(Map<String, String> dataMap,String flowCode,String type,String dentalJaw,LcljOrderTrack lcljOrderTrack,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		// 获取流程所有节点和初始节点步骤
		this.createOperateInfoByConfig(lcljOrderTrack,type,flowCode,dentalJaw,request);
		flowOperateService.createFlow(lcljOrderTrack, request);
		lcljTrackDao.updateOrderTrackNodes(lcljOrderTrack);
	}

	@Override
	public LcljOrderTrack findLcljOrderTrsackByseqId(String id) throws Exception {
		// TODO Auto-generated method stub
		LcljOrderTrack lclj = lcljTrackDao.findLcljOrderTrsackByseqId(id);
		return lclj;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateLcljOrderTrackIsobsolete(String id, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		LcljOrderTrackDeleteRecord dp = new LcljOrderTrackDeleteRecord();
		lcljTrackDao.updateLcljOrderTrackIsobsolete(id);
		trackDeleteService.save(dp, request);
	}

	@SuppressWarnings("unused")
	@Override
	public void updateLcljOrderTrackById(LcljOrderTrack lcljOrderTrack) throws Exception {
		String id = lcljOrderTrack.getId();
		lcljTrackDao.updateLcljOrderTrackById(lcljOrderTrack);
	}

	/**   
	  * <p>Title: editToothBit</p>   
	  * <p>Description: </p>   
	  * @param lTrack
	  * @return   
	 * @throws Exception 
	  * @see com.hudh.lclj.service.IFlowService#editToothBit(com.hudh.lclj.entity.LcljOrderTrack)   
	  */  
	@Override
	public Integer editToothBit(LcljOrderTrack lTrack) throws Exception {
		// TODO Auto-generated method stub
		return lcljTrackDao.editToothBit(lTrack);
	}

	/**   
	  * <p>Title: saveOperatingRecord</p>   
	  * <p>Description: </p>   
	  * @param oRecord   
	 * @throws Exception 
	  * @see com.hudh.lclj.service.IFlowService#saveOperatingRecord(com.hudh.lclj.entity.OperatingRecord)   
	  */  
	@Override
	public void saveOperatingRecord(OperatingRecord oRecord) throws Exception {
		// TODO Auto-generated method stub
		lcljTrackDao.saveOperatingRecord(oRecord);
	}


	@SuppressWarnings("unchecked")
	@Override
	public JSONObject findPatientInformation(String usercode,String status,String id,String order_number) throws Exception {
		JSONObject job=lcljTrackDao.findPatientInformation(usercode);
		String proviceName = job.getString("provincename");
		if(proviceName.equals("")) {
			job.replace("provincename", job.getString("province_name"));
		}
		String citname = job.getString("cityname");
		if(citname.equals("")) {
			job.replace("cityname", job.getString("city_name"));
		}
		String towname = job.getString("townname");
		if(towname.equals("")) {
			job.replace("townname", job.getString("area_name"));
		}
		if(status.equals("1")){
			Map<String,String> map = new HashMap<String,String>();
			map.put("lcljId", id);
			map.put("lcljNum", order_number);
			Notification notification = notificationDao.findNotificationByLcljId(map);
			job.put("whether", notification.getWhether());
			job.put("content", notification.getContent());
			job.put("doctortime", notification.getDoctortime());
			job.put("patienttime", notification.getPatienttime());
			job.put("doctorsignature", notification.getDoctorsignature());
			job.put("patientsignature", notification.getPatientsignature());
		}
		return job;
	}
	@Override
	public List<LcljNodeConfig> findNodeName()throws Exception{
		List<LcljNodeConfig> list = nodeConfigDao.findLcljNodeName();
		return list;
	}
}
