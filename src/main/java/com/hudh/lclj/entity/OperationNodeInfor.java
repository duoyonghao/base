package com.hudh.lclj.entity;

import java.io.Serializable;

public class OperationNodeInfor implements Serializable {

	private String seqId;

	private String clinicalpathwayid;

	public String getClinicalpathwayid() {
		return clinicalpathwayid;
	}

	public void setClinicalpathwayid(String clinicalpathwayid) {
		this.clinicalpathwayid = clinicalpathwayid;
	}

	private String connectingBridge;
	
	public String getConnectingBridge() {
		return connectingBridge;
	}

	public void setConnectingBridge(String connectingBridge) {
		this.connectingBridge = connectingBridge;
	}

	private String operation_time;

	private String preoperation_one_houres;

	private String preoperative_verification;

	private String assist_operation;

	private String postoperation_user_deugs;

	private String collutory;

	private String abutment_station;

	private String healing_cap_station;

	private String opration_record;

	private String remark;

	private String announcements;

	private String small_teeth;

	private String antimicrobial_use;

	private String review_time;

	private String observe_wound;

	private String check_wound;

	private String is_stitches;

	private String next_hospital_time;

	private String complete_case_record;

	private String panoramic_view_piece;

	private String check_surgical_healing;

	private String twoOperationAttention;

	private String twoOperationTime;

	private String checkPlantSituation;

	private String checkPlantBoneCombine;

	private String completeTwoOperation;

	private String twoDateStitchesTime;

	private String transfer;

	private String ismodule;

	private String basalCrowns;

	private String colorimetric;

	private String localBridge;

	private String try_in;

	private String wearTooth;

	private String isphotograph;

	private String askAboutUsage;

	private String checkOcclusion;

	private String createuser;

	private String createtime;

	private String organization;
	
	private String visit_time;
	
	private String confirmOcclusalRelationship;
	
	private String makeTransitionDenture;
	
	private String tryInTransitionDenture;
	
	private String postoperativeModulus;
	
	private String doctorWrittenRecords;
	
	private String modulusToMakePersonalizedTrays;
	
	private String jawrelationRecord;
	
	private String tryBaseStationInnerCrown;
	
	private String ectolophUse;
	
	private String toTryAgain;
	
	private String temper;
	
	private String resinSealing;
	
	private String secondGetModulus;
	
	private String checkCavityCleanSitu;
	
	private String checkWearToothSitu;
	
	private String satisfactionPatients;
	
	private String tryHolderOrBasalCrowns;
	
	private String accord;
	
	/**
	 * 添加字段
	 * @return
	 */
	private String operator;
	
	private String finish_norm_first;
	
	private String finish_norm_second;
	
	private String finish_norm_third;
	
	private String finish_norm_fourth;
	
	private String finish_norm_fifth;
	
	private String before_Modulo_bite;
	
	private String after_Modulo_bite;
	
	private String fixation_tooth_bridge;
	
	private String decision_bite;
	
	private String upper_frame;
	
	private String is_loose;
	
	private String health_education;
	
	private String neonychium;
	
	private String clean_toothbridge;
	
	private String outside_bone_grafting;
	
	private String looseSituation;
	
	private String extractionleftup_l;
	private String extractionleftup_r;
	private String extractionleftdown_l;
	private String extractionleftdown_r;
	private String extractionleftup1_l;
	private String extractionleftup1_r;
	private String extractionleftdown1_l;
	private String extractionleftdown1_r;
	
	private String nodeName;//添加字段，记录节点名称
	private String wearTeeth;//添加字段，记录有无戴临时牙牙齿
	
	public String getWearTeeth() {
		return wearTeeth;
	}

	public void setWearTeeth(String wearTeeth) {
		this.wearTeeth = wearTeeth;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**  
	  * @Title:  getExtractionleftup_l <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getExtractionleftup_l() {
		return extractionleftup_l;
	}

	/**  
	  * @Title:  setExtractionleftup_l <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setExtractionleftup_l(String extractionleftup_l) {
		this.extractionleftup_l = extractionleftup_l;
	}

	/**  
	  * @Title:  getExtractionleftup_r <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getExtractionleftup_r() {
		return extractionleftup_r;
	}

	/**  
	  * @Title:  setExtractionleftup_r <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setExtractionleftup_r(String extractionleftup_r) {
		this.extractionleftup_r = extractionleftup_r;
	}

	/**  
	  * @Title:  getExtractionleftdown_l <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getExtractionleftdown_l() {
		return extractionleftdown_l;
	}

	/**  
	  * @Title:  setExtractionleftdown_l <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setExtractionleftdown_l(String extractionleftdown_l) {
		this.extractionleftdown_l = extractionleftdown_l;
	}

	/**  
	  * @Title:  getExtractionleftdown_r <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getExtractionleftdown_r() {
		return extractionleftdown_r;
	}

	/**  
	  * @Title:  setExtractionleftdown_r <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setExtractionleftdown_r(String extractionleftdown_r) {
		this.extractionleftdown_r = extractionleftdown_r;
	}

	/**  
	  * @Title:  getExtractionleftup1_l <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getExtractionleftup1_l() {
		return extractionleftup1_l;
	}

	/**  
	  * @Title:  setExtractionleftup1_l <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setExtractionleftup1_l(String extractionleftup1_l) {
		this.extractionleftup1_l = extractionleftup1_l;
	}

	/**  
	  * @Title:  getExtractionleftup1_r <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getExtractionleftup1_r() {
		return extractionleftup1_r;
	}

	/**  
	  * @Title:  setExtractionleftup1_r <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setExtractionleftup1_r(String extractionleftup1_r) {
		this.extractionleftup1_r = extractionleftup1_r;
	}

	/**  
	  * @Title:  getExtractionleftdown1_l <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getExtractionleftdown1_l() {
		return extractionleftdown1_l;
	}

	/**  
	  * @Title:  setExtractionleftdown1_l <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setExtractionleftdown1_l(String extractionleftdown1_l) {
		this.extractionleftdown1_l = extractionleftdown1_l;
	}

	/**  
	  * @Title:  getExtractionleftdown1_r <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getExtractionleftdown1_r() {
		return extractionleftdown1_r;
	}

	/**  
	  * @Title:  setExtractionleftdown1_r <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setExtractionleftdown1_r(String extractionleftdown1_r) {
		this.extractionleftdown1_r = extractionleftdown1_r;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getFinish_norm_first() {
		return finish_norm_first;
	}

	public void setFinish_norm_first(String finish_norm_first) {
		this.finish_norm_first = finish_norm_first;
	}

	public String getFinish_norm_second() {
		return finish_norm_second;
	}

	public void setFinish_norm_second(String finish_norm_second) {
		this.finish_norm_second = finish_norm_second;
	}

	public String getFinish_norm_third() {
		return finish_norm_third;
	}

	public void setFinish_norm_third(String finish_norm_third) {
		this.finish_norm_third = finish_norm_third;
	}

	public String getFinish_norm_fourth() {
		return finish_norm_fourth;
	}

	public void setFinish_norm_fourth(String finish_norm_fourth) {
		this.finish_norm_fourth = finish_norm_fourth;
	}

	public String getFinish_norm_fifth() {
		return finish_norm_fifth;
	}

	public void setFinish_norm_fifth(String finish_norm_fifth) {
		this.finish_norm_fifth = finish_norm_fifth;
	}

	public String getBefore_Modulo_bite() {
		return before_Modulo_bite;
	}

	public void setBefore_Modulo_bite(String before_Modulo_bite) {
		this.before_Modulo_bite = before_Modulo_bite;
	}

	public String getAfter_Modulo_bite() {
		return after_Modulo_bite;
	}

	public void setAfter_Modulo_bite(String after_Modulo_bite) {
		this.after_Modulo_bite = after_Modulo_bite;
	}

	public String getFixation_tooth_bridge() {
		return fixation_tooth_bridge;
	}

	public void setFixation_tooth_bridge(String fixation_tooth_bridge) {
		this.fixation_tooth_bridge = fixation_tooth_bridge;
	}

	public String getDecision_bite() {
		return decision_bite;
	}

	public void setDecision_bite(String decision_bite) {
		this.decision_bite = decision_bite;
	}

	public String getUpper_frame() {
		return upper_frame;
	}

	public void setUpper_frame(String upper_frame) {
		this.upper_frame = upper_frame;
	}

	public String getIs_loose() {
		return is_loose;
	}

	public void setIs_loose(String is_loose) {
		this.is_loose = is_loose;
	}

	public String getHealth_education() {
		return health_education;
	}

	public void setHealth_education(String health_education) {
		this.health_education = health_education;
	}

	public String getNeonychium() {
		return neonychium;
	}

	public void setNeonychium(String neonychium) {
		this.neonychium = neonychium;
	}

	public String getClean_toothbridge() {
		return clean_toothbridge;
	}

	public void setClean_toothbridge(String clean_toothbridge) {
		this.clean_toothbridge = clean_toothbridge;
	}

	public String getOutside_bone_grafting() {
		return outside_bone_grafting;
	}

	public void setOutside_bone_grafting(String outside_bone_grafting) {
		this.outside_bone_grafting = outside_bone_grafting;
	}

	public String getNostitches() {
		return nostitches;
	}

	public void setNostitches(String nostitches) {
		this.nostitches = nostitches;
	}

	private String nostitches;
	
	private String intraoperativeMedication;
	
	private String enterLclj;
	
	public String getEnterLclj() {
		return enterLclj;
	}

	public void setEnterLclj(String enterLclj) {
		this.enterLclj = enterLclj;
	}

	public String getIntraoperativeMedication() {
		return intraoperativeMedication;
	}

	public void setIntraoperativeMedication(String intraoperativeMedication) {
		this.intraoperativeMedication = intraoperativeMedication;
	}

	public String getAnesthesiaMethod() {
		return anesthesiaMethod;
	}

	public void setAnesthesiaMethod(String anesthesiaMethod) {
		this.anesthesiaMethod = anesthesiaMethod;
	}

	private String anesthesiaMethod;
	
	private String askUserSituation;
	
	private String order_number;//添加字段（临床路径编号）
	
	private String nodeId;//worklist表节点id
	
	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	
	public String getAskUserSituation() {
		return askUserSituation;
	}

	public void setAskUserSituation(String askUserSituation) {
		this.askUserSituation = askUserSituation;
	}

	public String getAccord() {
		return accord;
	}

	public void setAccord(String accord) {
		this.accord = accord;
	}

	public String getTryHolderOrBasalCrowns() {
		return tryHolderOrBasalCrowns;
	}

	public void setTryHolderOrBasalCrowns(String tryHolderOrBasalCrowns) {
		this.tryHolderOrBasalCrowns = tryHolderOrBasalCrowns;
	}

	public String getCheckWearToothSitu() {
		return checkWearToothSitu;
	}

	public void setCheckWearToothSitu(String checkWearToothSitu) {
		this.checkWearToothSitu = checkWearToothSitu;
	}

	public String getSatisfactionPatients() {
		return satisfactionPatients;
	}

	public void setSatisfactionPatients(String satisfactionPatients) {
		this.satisfactionPatients = satisfactionPatients;
	}

	public String getCheckCavityCleanSitu() {
		return checkCavityCleanSitu;
	}

	public void setCheckCavityCleanSitu(String checkCavityCleanSitu) {
		this.checkCavityCleanSitu = checkCavityCleanSitu;
	}

	public String getSecondGetModulus() {
		return secondGetModulus;
	}

	public void setSecondGetModulus(String secondGetModulus) {
		this.secondGetModulus = secondGetModulus;
	}

	public String getConfirmOcclusalRelationship() {
		return confirmOcclusalRelationship;
	}

	public void setConfirmOcclusalRelationship(String confirmOcclusalRelationship) {
		this.confirmOcclusalRelationship = confirmOcclusalRelationship;
	}

	public String getMakeTransitionDenture() {
		return makeTransitionDenture;
	}

	public void setMakeTransitionDenture(String makeTransitionDenture) {
		this.makeTransitionDenture = makeTransitionDenture;
	}

	public String getTryInTransitionDenture() {
		return tryInTransitionDenture;
	}

	public void setTryInTransitionDenture(String tryInTransitionDenture) {
		this.tryInTransitionDenture = tryInTransitionDenture;
	}

	public String getPostoperativeModulus() {
		return postoperativeModulus;
	}

	public void setPostoperativeModulus(String postoperativeModulus) {
		this.postoperativeModulus = postoperativeModulus;
	}

	public String getDoctorWrittenRecords() {
		return doctorWrittenRecords;
	}

	public void setDoctorWrittenRecords(String doctorWrittenRecords) {
		this.doctorWrittenRecords = doctorWrittenRecords;
	}

	public String getModulusToMakePersonalizedTrays() {
		return modulusToMakePersonalizedTrays;
	}

	public void setModulusToMakePersonalizedTrays(String modulusToMakePersonalizedTrays) {
		this.modulusToMakePersonalizedTrays = modulusToMakePersonalizedTrays;
	}

	public String getJawrelationRecord() {
		return jawrelationRecord;
	}

	public void setJawrelationRecord(String jawrelationRecord) {
		this.jawrelationRecord = jawrelationRecord;
	}

	public String getTryBaseStationInnerCrown() {
		return tryBaseStationInnerCrown;
	}

	public void setTryBaseStationInnerCrown(String tryBaseStationInnerCrown) {
		this.tryBaseStationInnerCrown = tryBaseStationInnerCrown;
	}

	public String getEctolophUse() {
		return ectolophUse;
	}

	public void setEctolophUse(String ectolophUse) {
		this.ectolophUse = ectolophUse;
	}

	public String getToTryAgain() {
		return toTryAgain;
	}

	public void setToTryAgain(String toTryAgain) {
		this.toTryAgain = toTryAgain;
	}

	public String getTemper() {
		return temper;
	}

	public void setTemper(String temper) {
		this.temper = temper;
	}

	public String getResinSealing() {
		return resinSealing;
	}

	public void setResinSealing(String resinSealing) {
		this.resinSealing = resinSealing;
	}

	public String getVisit_time() {
		return visit_time;
	}

	public void setVisit_time(String visit_time) {
		this.visit_time = visit_time;
	}

	private static final long serialVersionUID = 1L;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getOperation_time() {
		return operation_time;
	}

	public void setOperation_time(String operation_time) {
		this.operation_time = operation_time;
	}

	public String getPreoperation_one_houres() {
		return preoperation_one_houres;
	}

	public void setPreoperation_one_houres(String preoperation_one_houres) {
		this.preoperation_one_houres = preoperation_one_houres;
	}

	public String getPreoperative_verification() {
		return preoperative_verification;
	}

	public void setPreoperative_verification(String preoperative_verification) {
		this.preoperative_verification = preoperative_verification;
	}

	public String getAssist_operation() {
		return assist_operation;
	}

	public void setAssist_operation(String assist_operation) {
		this.assist_operation = assist_operation;
	}

	public String getPostoperation_user_deugs() {
		return postoperation_user_deugs;
	}

	public void setPostoperation_user_deugs(String postoperation_user_deugs) {
		this.postoperation_user_deugs = postoperation_user_deugs;
	}

	public String getCollutory() {
		return collutory;
	}

	public void setCollutory(String collutory) {
		this.collutory = collutory;
	}

	public String getAbutment_station() {
		return abutment_station;
	}

	public void setAbutment_station(String abutment_station) {
		this.abutment_station = abutment_station;
	}

	public String getHealing_cap_station() {
		return healing_cap_station;
	}

	public void setHealing_cap_station(String healing_cap_station) {
		this.healing_cap_station = healing_cap_station;
	}

	public String getOpration_record() {
		return opration_record;
	}

	public void setOpration_record(String opration_record) {
		this.opration_record = opration_record;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(String announcements) {
		this.announcements = announcements;
	}

	public String getSmall_teeth() {
		return small_teeth;
	}

	public void setSmall_teeth(String small_teeth) {
		this.small_teeth = small_teeth;
	}

	public String getAntimicrobial_use() {
		return antimicrobial_use;
	}

	public void setAntimicrobial_use(String antimicrobial_use) {
		this.antimicrobial_use = antimicrobial_use;
	}

	public String getReview_time() {
		return review_time;
	}

	public void setReview_time(String review_time) {
		this.review_time = review_time;
	}

	public String getObserve_wound() {
		return observe_wound;
	}

	public void setObserve_wound(String observe_wound) {
		this.observe_wound = observe_wound;
	}

	public String getCheck_wound() {
		return check_wound;
	}

	public void setCheck_wound(String check_wound) {
		this.check_wound = check_wound;
	}

	public String getIs_stitches() {
		return is_stitches;
	}

	public void setIs_stitches(String is_stitches) {
		this.is_stitches = is_stitches;
	}

	public String getNext_hospital_time() {
		return next_hospital_time;
	}

	public void setNext_hospital_time(String next_hospital_time) {
		this.next_hospital_time = next_hospital_time;
	}

	public String getComplete_case_record() {
		return complete_case_record;
	}

	public void setComplete_case_record(String complete_case_record) {
		this.complete_case_record = complete_case_record;
	}

	public String getPanoramic_view_piece() {
		return panoramic_view_piece;
	}

	public void setPanoramic_view_piece(String panoramic_view_piece) {
		this.panoramic_view_piece = panoramic_view_piece;
	}

	public String getCheck_surgical_healing() {
		return check_surgical_healing;
	}

	public void setCheck_surgical_healing(String check_surgical_healing) {
		this.check_surgical_healing = check_surgical_healing;
	}

	public String getTwoOperationAttention() {
		return twoOperationAttention;
	}

	public void setTwoOperationAttention(String twoOperationAttention) {
		this.twoOperationAttention = twoOperationAttention;
	}

	public String getTwoOperationTime() {
		return twoOperationTime;
	}

	public void setTwoOperationTime(String twoOperationTime) {
		this.twoOperationTime = twoOperationTime;
	}

	public String getCheckPlantSituation() {
		return checkPlantSituation;
	}

	public void setCheckPlantSituation(String checkPlantSituation) {
		this.checkPlantSituation = checkPlantSituation;
	}

	public String getCheckPlantBoneCombine() {
		return checkPlantBoneCombine;
	}

	public void setCheckPlantBoneCombine(String checkPlantBoneCombine) {
		this.checkPlantBoneCombine = checkPlantBoneCombine;
	}

	public String getCompleteTwoOperation() {
		return completeTwoOperation;
	}

	public void setCompleteTwoOperation(String completeTwoOperation) {
		this.completeTwoOperation = completeTwoOperation;
	}

	public String getTwoDateStitchesTime() {
		return twoDateStitchesTime;
	}

	public void setTwoDateStitchesTime(String twoDateStitchesTime) {
		this.twoDateStitchesTime = twoDateStitchesTime;
	}

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public String getIsmodule() {
		return ismodule;
	}

	public void setIsmodule(String ismodule) {
		this.ismodule = ismodule;
	}

	public String getBasalCrowns() {
		return basalCrowns;
	}

	public void setBasalCrowns(String basalCrowns) {
		this.basalCrowns = basalCrowns;
	}

	public String getColorimetric() {
		return colorimetric;
	}

	public void setColorimetric(String colorimetric) {
		this.colorimetric = colorimetric;
	}

	public String getLocalBridge() {
		return localBridge;
	}

	public void setLocalBridge(String localBridge) {
		this.localBridge = localBridge;
	}

	public String getTry_in() {
		return try_in;
	}

	public void setTry_in(String try_in) {
		this.try_in = try_in;
	}

	public String getWearTooth() {
		return wearTooth;
	}

	public void setWearTooth(String wearTooth) {
		this.wearTooth = wearTooth;
	}

	public String getIsphotograph() {
		return isphotograph;
	}

	public void setIsphotograph(String isphotograph) {
		this.isphotograph = isphotograph;
	}

	public String getAskAboutUsage() {
		return askAboutUsage;
	}

	public void setAskAboutUsage(String askAboutUsage) {
		this.askAboutUsage = askAboutUsage;
	}

	public String getCheckOcclusion() {
		return checkOcclusion;
	}

	public void setCheckOcclusion(String checkOcclusion) {
		this.checkOcclusion = checkOcclusion;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getLooseSituation() {
		return looseSituation;
	}

	public void setLooseSituation(String looseSituation) {
		this.looseSituation = looseSituation;
	}

	
}