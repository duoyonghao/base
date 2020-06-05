package com.hudh.lclj.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.lclj.dao.LcljOperationNodeInforDao;
import com.hudh.lclj.entity.OperationNodeInfor;
import com.hudh.lclj.service.ILcljOperationNodeInforService;
import com.kqds.entity.sys.YZPerson;
import com.kqds.util.sys.SessionUtil;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.chain.ChainUtil;

import net.sf.json.JSONObject;

@Service
public class LcljOperationNodeInforServiceImpl implements ILcljOperationNodeInforService {
	
	@Autowired
	private LcljOperationNodeInforDao lcljOperationNodeInforDao;
	
	@Override
	public OperationNodeInfor insertOperationNodeInfor(OperationNodeInfor dp, HttpServletRequest request, String dataId) throws Exception {
		String operation_time = request.getParameter("operation_time");
		String wearTeeth = request.getParameter("wearTeeth");
		String review_time = request.getParameter("review_time");
		String visit_time = request.getParameter("visit_time");
		String preoperation_one_houres = request.getParameter("preoperation_one_houres");
		String preoperative_verification = request.getParameter("preoperative_verification");
		String abutment_station = request.getParameter("abutment_station");
		String healing_cap_station = request.getParameter("healing_cap_station");
		String postoperation_user_deugs = request.getParameter("postoperation_user_deugs");
		String connectingBridge = request.getParameter("connectingBridge");
		String collutory = request.getParameter("collutory");
		String small_teeth = request.getParameter("small_teeth");
		String announcements = request.getParameter("announcements");
		String opration_record = request.getParameter("opration_record");
		String remark = request.getParameter("remark");
		String assist_operation = request.getParameter("assist_operation");
		String observe_wound = request.getParameter("observe_wound");
		String check_wound = request.getParameter("check_wound");
		String is_stitches = request.getParameter("is_stitches");
		String panoramic_view_piece = request.getParameter("panoramic_view_piece");
		String next_hospital_time = request.getParameter("next_hospital_time");
		String twoDateStitchesTime = request.getParameter("twoDateStitchesTime");
		String twoOperationAttention = request.getParameter("twoOperationAttention");
		String completeTwoOperation = request.getParameter("completeTwoOperation");
		String ismodule = request.getParameter("ismodule");
		String transfer = request.getParameter("transfer");
		String basalCrowns = request.getParameter("basalCrowns");
		String colorimetric = request.getParameter("colorimetric");
		String localBridge = request.getParameter("localBridge");
		String try_in = request.getParameter("try_in");
		String askAboutUsage = request.getParameter("askAboutUsage");
		String check_surgical_healing = request.getParameter("check_surgical_healing");
		String confirmOcclusalRelationship = request.getParameter("confirmOcclusalRelationship");
		String makeTransitionDenture = request.getParameter("makeTransitionDenture");
		String tryInTransitionDenture = request.getParameter("tryInTransitionDenture");
		String jawrelationRecord = request.getParameter("jawrelationRecord");
		String tryBaseStationInnerCrown = request.getParameter("tryBaseStationInnerCrown");
		String secondGetModulus = request.getParameter("secondGetModulus");
		String toTryAgain = request.getParameter("toTryAgain");
		String complete_case_record = request.getParameter("complete_case_record");
		String checkPlantBoneCombine = request.getParameter("checkPlantBoneCombine");
		String wearTooth = request.getParameter("wearTooth");
		String isphotograph = request.getParameter("isphotograph");
		String checkCavityCleanSitu = request.getParameter("checkCavityCleanSitu");
		String antimicrobial_use = request.getParameter("antimicrobial_use");
		String tryHolderOrBasalCrowns = request.getParameter("tryHolderOrBasalCrowns");
		String askUserSituation = request.getParameter("askUserSituation");
		String resinSealing = request.getParameter("resinSealing");
		String checkOcclusion = request.getParameter("checkOcclusion");
		String postoperativeModulus = request.getParameter("postoperativeModulus");
		String checkPlantSituation = request.getParameter("checkPlantSituation");
		String modulusToMakePersonalizedTrays = request.getParameter("modulusToMakePersonalizedTrays");
		String ectolophUse = request.getParameter("ectolophUse");
		String accord = request.getParameter("accord");
		String checkWearToothSitu = request.getParameter("checkWearToothSitu");
		String satisfactionPatients = request.getParameter("satisfactionPatients");
		String doctorWrittenRecords = request.getParameter("doctorWrittenRecords");
		String anesthesiaMethod = request.getParameter("anesthesiaMethod");
		String nostitches = request.getParameter("nostitches");
		String intraoperativeMedication = request.getParameter("intraoperativeMedication");
		String enterLclj = request.getParameter("enterLclj");
		
		String operator = request.getParameter("operator");
		String finish_norm_first = request.getParameter("finish_norm_first");
		String finish_norm_second = request.getParameter("finish_norm_second");
		String finish_norm_third = request.getParameter("finish_norm_third");
		String finish_norm_fourth = request.getParameter("finish_norm_fourth");
		String finish_norm_fifth = request.getParameter("finish_norm_fifth");
		String before_Modulo_bite = request.getParameter("before_Modulo_bite");
		String after_Modulo_bite = request.getParameter("after_Modulo_bite");
		String fixation_tooth_bridge = request.getParameter("fixation_tooth_bridge");
		String decision_bite = request.getParameter("decision_bite");
		String upper_frame = request.getParameter("upper_frame");
		String is_loose = request.getParameter("is_loose");
		String health_education = request.getParameter("health_education");
		String neonychium = request.getParameter("neonychium");
		String clean_toothbridge = request.getParameter("clean_toothbridge");
		String outside_bone_grafting = request.getParameter("outside_bone_grafting");
		
		String extractionleftup_l = request.getParameter("extractionleftup_l");
		String extractionleftup_r = request.getParameter("extractionleftup_r");
		String extractionleftdown_l = request.getParameter("extractionleftdown_l");
		String extractionleftdown_r = request.getParameter("extractionleftdown_r");
		String extractionleftup1_l = request.getParameter("extractionleftup1_l");
		String extractionleftup1_r = request.getParameter("extractionleftup1_r");
		String extractionleftdown1_l = request.getParameter("extractionleftdown1_l");
		String extractionleftdown1_r = request.getParameter("extractionleftdown1_r");
		
		if (YZUtility.isNullorEmpty(operation_time) && YZUtility.isNullorEmpty(review_time) && YZUtility.isNullorEmpty(visit_time) && YZUtility.isNullorEmpty(preoperation_one_houres) && YZUtility.isNullorEmpty(preoperative_verification) && 
				YZUtility.isNullorEmpty(abutment_station) && YZUtility.isNullorEmpty(healing_cap_station)
				&& YZUtility.isNullorEmpty(postoperation_user_deugs) && YZUtility.isNullorEmpty(connectingBridge) && YZUtility.isNullorEmpty(collutory) && 
				YZUtility.isNullorEmpty(small_teeth) && YZUtility.isNullorEmpty(announcements) && YZUtility.isNullorEmpty(opration_record)
				&& YZUtility.isNullorEmpty(remark) && YZUtility.isNullorEmpty(assist_operation) && YZUtility.isNullorEmpty(observe_wound) && YZUtility.isNullorEmpty(check_wound) && YZUtility.isNullorEmpty(is_stitches) && 
				YZUtility.isNullorEmpty(panoramic_view_piece) && YZUtility.isNullorEmpty(next_hospital_time) && YZUtility.isNullorEmpty(twoDateStitchesTime) && YZUtility.isNullorEmpty(twoOperationAttention) && YZUtility.isNullorEmpty(completeTwoOperation) &&
				YZUtility.isNullorEmpty(ismodule) && YZUtility.isNullorEmpty(transfer) && YZUtility.isNullorEmpty(basalCrowns) && YZUtility.isNullorEmpty(colorimetric) && YZUtility.isNullorEmpty(localBridge) && YZUtility.isNullorEmpty(try_in) && 
				YZUtility.isNullorEmpty(askAboutUsage) && YZUtility.isNullorEmpty(check_surgical_healing) && YZUtility.isNullorEmpty(confirmOcclusalRelationship) && YZUtility.isNullorEmpty(makeTransitionDenture) && YZUtility.isNullorEmpty(tryInTransitionDenture) &&
				YZUtility.isNullorEmpty(jawrelationRecord) && YZUtility.isNullorEmpty(tryBaseStationInnerCrown) && YZUtility.isNullorEmpty(secondGetModulus) && YZUtility.isNullorEmpty(toTryAgain) && YZUtility.isNullorEmpty(complete_case_record) &&
				YZUtility.isNullorEmpty(checkPlantBoneCombine) && YZUtility.isNullorEmpty(wearTooth) && YZUtility.isNullorEmpty(isphotograph) && YZUtility.isNullorEmpty(checkCavityCleanSitu) && YZUtility.isNullorEmpty(antimicrobial_use) && 
				YZUtility.isNullorEmpty(tryHolderOrBasalCrowns) && YZUtility.isNullorEmpty(askUserSituation) && YZUtility.isNullorEmpty(resinSealing) && YZUtility.isNullorEmpty(checkOcclusion) && YZUtility.isNullorEmpty(postoperativeModulus) &&
				YZUtility.isNullorEmpty(checkPlantSituation) && YZUtility.isNullorEmpty(modulusToMakePersonalizedTrays) && YZUtility.isNullorEmpty(ectolophUse) && YZUtility.isNullorEmpty(accord) && YZUtility.isNullorEmpty(checkWearToothSitu) &&
				YZUtility.isNullorEmpty(satisfactionPatients) && YZUtility.isNullorEmpty(doctorWrittenRecords) && YZUtility.isNullorEmpty(anesthesiaMethod) && YZUtility.isNullorEmpty(nostitches) && YZUtility.isNullorEmpty(intraoperativeMedication) &&
				YZUtility.isNullorEmpty(enterLclj) && YZUtility.isNullorEmpty(operator) 
				&& YZUtility.isNullorEmpty(finish_norm_first) && YZUtility.isNullorEmpty(finish_norm_second) && YZUtility.isNullorEmpty(finish_norm_third) && YZUtility.isNullorEmpty(finish_norm_fourth) && YZUtility.isNullorEmpty(finish_norm_fifth)
				&& YZUtility.isNullorEmpty(before_Modulo_bite) && YZUtility.isNullorEmpty(after_Modulo_bite) && YZUtility.isNullorEmpty(fixation_tooth_bridge) && YZUtility.isNullorEmpty(decision_bite) && YZUtility.isNullorEmpty(upper_frame)
				&& YZUtility.isNullorEmpty(is_loose) && YZUtility.isNullorEmpty(health_education) && YZUtility.isNullorEmpty(neonychium) && YZUtility.isNullorEmpty(clean_toothbridge) && YZUtility.isNullorEmpty(outside_bone_grafting)
				&& YZUtility.isNullorEmpty(extractionleftup_l) && YZUtility.isNullorEmpty(extractionleftup_r) && YZUtility.isNullorEmpty(extractionleftdown_l) && YZUtility.isNullorEmpty(extractionleftdown_r) && YZUtility.isNullorEmpty(extractionleftup1_l)
				&& YZUtility.isNullorEmpty(extractionleftup1_r) && YZUtility.isNullorEmpty(extractionleftdown1_l) && YZUtility.isNullorEmpty(extractionleftdown1_r) && YZUtility.isNullorEmpty(wearTeeth)) {
			throw new Exception("需要填写的信息不能全部为空！");
		}
		dp.setOperator(operator);
		dp.setFinish_norm_first(finish_norm_first);
		dp.setFinish_norm_second(finish_norm_second);
		dp.setFinish_norm_third(finish_norm_third);
		dp.setFinish_norm_fourth(finish_norm_fourth);
		dp.setFinish_norm_fifth(finish_norm_fifth);
		dp.setBefore_Modulo_bite(before_Modulo_bite);
		dp.setAfter_Modulo_bite(after_Modulo_bite);
		dp.setFixation_tooth_bridge(fixation_tooth_bridge);
		dp.setDecision_bite(decision_bite);
		dp.setUpper_frame(upper_frame);
		dp.setIs_loose(is_loose);
		dp.setHealth_education(health_education);
		dp.setNeonychium(neonychium);
		dp.setClean_toothbridge(clean_toothbridge);
		dp.setOutside_bone_grafting(outside_bone_grafting);
		dp.setEnterLclj(enterLclj);
		dp.setIntraoperativeMedication(intraoperativeMedication);
		dp.setNostitches(nostitches);
		dp.setAnesthesiaMethod(anesthesiaMethod);
		dp.setDoctorWrittenRecords(doctorWrittenRecords);
		dp.setSatisfactionPatients(satisfactionPatients);
		dp.setCheckWearToothSitu(checkWearToothSitu);
		dp.setAccord(accord);
		dp.setEctolophUse(ectolophUse);
		dp.setModulusToMakePersonalizedTrays(modulusToMakePersonalizedTrays);
		dp.setCheckPlantSituation(checkPlantSituation);
		dp.setPostoperativeModulus(postoperativeModulus);
		dp.setCheckOcclusion(checkOcclusion);
		dp.setResinSealing(resinSealing);
		dp.setAskUserSituation(askUserSituation);
		dp.setTryHolderOrBasalCrowns(tryHolderOrBasalCrowns);
		dp.setVisit_time(visit_time);
		dp.setAntimicrobial_use(antimicrobial_use);
		dp.setCheckCavityCleanSitu(checkCavityCleanSitu);
		dp.setIsphotograph(isphotograph);
		dp.setWearTooth(wearTooth);
		dp.setCheckPlantBoneCombine(checkPlantBoneCombine);
		dp.setComplete_case_record(complete_case_record);
		dp.setToTryAgain(toTryAgain);
		dp.setSecondGetModulus(secondGetModulus);
		dp.setJawrelationRecord(jawrelationRecord);
		dp.setTryBaseStationInnerCrown(tryBaseStationInnerCrown);
		dp.setConfirmOcclusalRelationship(confirmOcclusalRelationship);
		dp.setMakeTransitionDenture(makeTransitionDenture);
		dp.setTryInTransitionDenture(tryInTransitionDenture);
		dp.setConnectingBridge(connectingBridge);
		dp.setCheck_surgical_healing(check_surgical_healing);
		dp.setAskAboutUsage(askAboutUsage);
		dp.setTry_in(try_in);
		dp.setLocalBridge(localBridge);
		dp.setColorimetric(colorimetric);
		dp.setBasalCrowns(basalCrowns);
		dp.setIsmodule(ismodule);
		dp.setTransfer(transfer);
		dp.setTwoDateStitchesTime(twoDateStitchesTime);
		dp.setTwoOperationAttention(twoOperationAttention);
		dp.setCompleteTwoOperation(completeTwoOperation);
		dp.setPanoramic_view_piece(panoramic_view_piece);
		dp.setNext_hospital_time(next_hospital_time);
		dp.setObserve_wound(observe_wound);
		dp.setIs_stitches(is_stitches);
		dp.setCheck_wound(check_wound);
		dp.setReview_time(review_time);
		YZPerson person = SessionUtil.getLoginPerson(request);
		String organization = ChainUtil.getCurrentOrganization(request); // 获取当前所在门诊
		dp.setCreateuser(person.getUserId());
		dp.setOrganization(organization);
		dp.setOperation_time(operation_time);
		dp.setPreoperation_one_houres(preoperation_one_houres);
		dp.setPreoperative_verification(preoperative_verification);
		dp.setAbutment_station(abutment_station);
		dp.setHealing_cap_station(healing_cap_station);
		dp.setPostoperation_user_deugs(postoperation_user_deugs);
		dp.setCollutory(collutory);
		dp.setSmall_teeth(small_teeth);
		dp.setAnnouncements(announcements);
		dp.setOpration_record(opration_record);
		dp.setRemark(remark);
		dp.setAssist_operation(assist_operation);
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
		dp.setSeqId(YZUtility.getUUID());
		dp.setWearTeeth(wearTeeth);
		//新增参数
		dp.setExtractionleftup_l(extractionleftup_l);
		dp.setExtractionleftup_r(extractionleftup_r);
		dp.setExtractionleftdown_l(extractionleftdown_l);
		dp.setExtractionleftdown_r(extractionleftdown_r);
		dp.setExtractionleftup1_l(extractionleftup1_l);
		dp.setExtractionleftup1_r(extractionleftup1_r);
		dp.setExtractionleftdown1_l(extractionleftdown1_l);
		dp.setExtractionleftdown1_r(extractionleftdown1_r);
		
//		JSONObject json = lcljOperationNodeInforDao.findOperationNodeInforByCancelTimeHospital(next_hospital_time, visit_time, dp.getOrder_number());
//		if (json != null) {
//				throw new Exception("改时间点已被" + json.getString("nodename") + "节点预约！请重新添加预约时间！");
//		}
//		if (dataId == null) {
			lcljOperationNodeInforDao.insertOperationNodeInfor(dp);
//		} else {
//			lcljOperationNodeInforDao.updateOperationNodeInforByOrderNumberAndNodeId(dp);//退回后点击提交更新当前节点信息
//		}
		return dp;
	}

	@Override
	public JSONObject selectOperationNodeInforById(String id) throws Exception {
		JSONObject json = lcljOperationNodeInforDao.selectOperationNodeInforById(id);
		return json;
	}

	@Override
	public List<JSONObject> selectOperationNodeInforAll() throws Exception {
		List<JSONObject> list = lcljOperationNodeInforDao.selectOperationNodeInforAll();
		return list;
	}

	@Override
	public void updateOperationNodeInforById(OperationNodeInfor dp) throws Exception {
		lcljOperationNodeInforDao.updateOperationNodeInforById(dp);
	}

	@Override
	public void deleteOperationNodeInforById(String id) throws Exception {
		lcljOperationNodeInforDao.deleteOperationNodeInforById(id);
	}

	@Override
	public JSONObject selectOperationNodeInforByOrdernumberAndNodeId(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return lcljOperationNodeInforDao.selectOperationNodeInforByOrdernumberAndNodeId(map);
	}

	/**   
	  * <p>Title: updateOrderTimeHospital</p>   
	  * <p>Description: </p>   
	  * @param order_number
	  * @param nodeId
	  * @param orderTime   
	 * @throws Exception 
	  * @see com.hudh.lclj.service.ILcljOperationNodeInforService#updateOrderTimeHospital(java.lang.String, java.lang.String, java.lang.String)   
	  */  
	@Override
	public void updateOrderTimeHospital(String order_number, String nodeId, String orderTime) throws Exception {
		// TODO Auto-generated method stub
		lcljOperationNodeInforDao.updateOrderTimeHospital(order_number, nodeId, orderTime);
	}

	/**   预约日历里面取消手术预约临床路径同步取消   2019-7-27  syp
	  * <p>Title: cancelTimeHospital</p>   
	  * <p>Description: </p>   
	  * @param order_number
	  * @param nodeId   
	 * @throws Exception 
	  * @see com.hudh.lclj.service.ILcljOperationNodeInforService#cancelTimeHospital(java.lang.String, java.lang.String)   
	  */  
	@Override
	public void cancelTimeHospital(String order_number, String nodeId) throws Exception {
		// TODO Auto-generated method stub
		lcljOperationNodeInforDao.cancelTimeHospital(order_number, nodeId);
	}
	
	
	
}
