package com.hudh.zzbl.entity;

public class ZzblOperation {

	private String SEQ_ID;
	private String createtime;
	private String createuser;
	private String id;
	private String order_number;
	private String physical_assessment;
	private String consultation_opinion;
	private String stomatology_check;
	private String occlusion_situation;
	private String interarch_distance;
	private String repaire_type;
	private String periodontal_condition;
	private String teethMissLeftUp;
	private String teethMissLeftDown;
	private String teethMissRightUp;
	private String teethMissRightDown;
	private String reasonMissTeeth;
	private String dentalCaryLeftUp;
	private String dentalCaryLeftDown;
	private String dentalCaryRightUp;
	private String dentalCaryRightDown;
	private String timeMissTeeth;
	private String residualRootLeftUp;
	private String residualRootLeftDown;
	private String residualRootRightUp;
	private String residualRootRightDown;
	private String mucosaCondition;
	private String teethMoveOneLeftUp;
	private String teethMoveOneLeftDown;
	private String teethMoveOneRightUp;
	private String teethMoveOneRightDown;
	private String teethMoveTwoLeftUp;
	private String teethMoveTwoLeftDown;
	private String teethMoveTwoRightUp;
	private String teethMoveTwoRightDown;
	private String teethMoveThreeLeftUp;
	private String teethMoveThreeLeftDown;
	private String teethMoveThreeRightUp;
	private String teethMoveThreeRightDown;
	private String xrayShowsLeftUp;
	private String xrayShowsLeftDown;
	private String xrayShowsRightUp;
	private String xrayShowsRightDown;
	private String fromMaxillarySinusFloor;
	private String fromNasalFloor;
	private String fromInferiorDentalNerve;
	private String alveloarCrestWidths;
	private String alveloarBoneResorption;
	private String periodontal_lesion;
	private String diagnosis;
	private String others;
	private String treatmentPlan;
	private String extractionLeftUp;
	private String extractionLeftDown;
	private String extractionRightUp;
	private String extractionRightDown;
	private String implantAssistantSurgery;
	private String implantFirstTimeLU;
	private String implantFirstTimeLD;
	private String implantFirstTimeRU;
	private String implantFirstTimeRD;
	private String implantSecondTimeLU;
	private String implantSecondTimeLD;
	private String implantSecondTimeRU;
	private String implantSecondTimeRD;
	private String bonePowder;
	private String largeParticle;
	private String smallParticle;
	private String boneCollagen;
	private String tianBo;
	private String membrane;
	private String collagenMembBig;
	private String collagenMembSmall;
	private String titaniumMesh;
	private String repaireMethod;
	private String temporDentCondition;
	private String treatmentOtherOralLU;
	private String treatmentOtherOralLD;
	private String treatmentOtherOralRU;
	private String treatmentOtherOralRD;
	private String organization;
	private String username; //患者姓名
    private String sex; //患者性别
    private String age; //患者年龄
    private String remark;
    private String patientTime;
    private String doctorTime;
    private String implantSystem;
    private String treamentPlan;
    private String moduloDesign;
    private String doctorSignature;//医生签名
    private String patientsignature;//患者签名
    private String status;
    
	/**  
	  * @Title:  getStatus <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getStatus() {
		return status;
	}

	/**  
	  * @Title:  setStatus <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getModuloDesign() {
		return moduloDesign;
	}

	public void setModuloDesign(String moduloDesign) {
		this.moduloDesign = moduloDesign;
	}

	public String getTreamentPlan() {
		return treamentPlan;
	}

	public void setTreamentPlan(String treamentPlan) {
		this.treamentPlan = treamentPlan;
	}

	public String getImplantSystem() {
		return implantSystem;
	}

	public void setImplantSystem(String implantSystem) {
		this.implantSystem = implantSystem;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPatientTime() {
		return patientTime;
	}

	public void setPatientTime(String patientTime) {
		this.patientTime = patientTime;
	}

	public String getDoctorTime() {
		return doctorTime;
	}

	public void setDoctorTime(String doctorTime) {
		this.doctorTime = doctorTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getCreatetime() {
		return createtime;
	}

	/**  
	  * @Title:  getSEQ_ID <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSEQ_ID() {
		return SEQ_ID;
	}

	/**  
	  * @Title:  setSEQ_ID <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSEQ_ID(String SEQ_ID) {
		this.SEQ_ID = SEQ_ID;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getPhysical_assessment() {
		return physical_assessment;
	}

	public void setPhysical_assessment(String physical_assessment) {
		this.physical_assessment = physical_assessment;
	}

	public String getConsultation_opinion() {
		return consultation_opinion;
	}

	public void setConsultation_opinion(String consultation_opinion) {
		this.consultation_opinion = consultation_opinion;
	}

	public String getStomatology_check() {
		return stomatology_check;
	}

	public void setStomatology_check(String stomatology_check) {
		this.stomatology_check = stomatology_check;
	}

	public String getOcclusion_situation() {
		return occlusion_situation;
	}

	public void setOcclusion_situation(String occlusion_situation) {
		this.occlusion_situation = occlusion_situation;
	}

	public String getInterarch_distance() {
		return interarch_distance;
	}

	public void setInterarch_distance(String interarch_distance) {
		this.interarch_distance = interarch_distance;
	}

	public String getRepaire_type() {
		return repaire_type;
	}

	public void setRepaire_type(String repaire_type) {
		this.repaire_type = repaire_type;
	}

	public String getPeriodontal_condition() {
		return periodontal_condition;
	}

	public void setPeriodontal_condition(String periodontal_condition) {
		this.periodontal_condition = periodontal_condition;
	}

	public String getTeethMissLeftUp() {
		return teethMissLeftUp;
	}

	public void setTeethMissLeftUp(String teethMissLeftUp) {
		this.teethMissLeftUp = teethMissLeftUp;
	}

	public String getTeethMissLeftDown() {
		return teethMissLeftDown;
	}

	public void setTeethMissLeftDown(String teethMissLeftDown) {
		this.teethMissLeftDown = teethMissLeftDown;
	}

	public String getTeethMissRightUp() {
		return teethMissRightUp;
	}

	public void setTeethMissRightUp(String teethMissRightUp) {
		this.teethMissRightUp = teethMissRightUp;
	}

	public String getTeethMissRightDown() {
		return teethMissRightDown;
	}

	public void setTeethMissRightDown(String teethMissRightDown) {
		this.teethMissRightDown = teethMissRightDown;
	}

	public String getReasonMissTeeth() {
		return reasonMissTeeth;
	}

	public void setReasonMissTeeth(String reasonMissTeeth) {
		this.reasonMissTeeth = reasonMissTeeth;
	}

	public String getDentalCaryLeftUp() {
		return dentalCaryLeftUp;
	}

	public void setDentalCaryLeftUp(String dentalCaryLeftUp) {
		this.dentalCaryLeftUp = dentalCaryLeftUp;
	}

	public String getDentalCaryLeftDown() {
		return dentalCaryLeftDown;
	}

	public void setDentalCaryLeftDown(String dentalCaryLeftDown) {
		this.dentalCaryLeftDown = dentalCaryLeftDown;
	}

	public String getDentalCaryRightUp() {
		return dentalCaryRightUp;
	}

	public void setDentalCaryRightUp(String dentalCaryRightUp) {
		this.dentalCaryRightUp = dentalCaryRightUp;
	}

	public String getDentalCaryRightDown() {
		return dentalCaryRightDown;
	}

	public void setDentalCaryRightDown(String dentalCaryRightDown) {
		this.dentalCaryRightDown = dentalCaryRightDown;
	}

	public String getTimeMissTeeth() {
		return timeMissTeeth;
	}

	public void setTimeMissTeeth(String timeMissTeeth) {
		this.timeMissTeeth = timeMissTeeth;
	}

	public String getResidualRootLeftUp() {
		return residualRootLeftUp;
	}

	public void setResidualRootLeftUp(String residualRootLeftUp) {
		this.residualRootLeftUp = residualRootLeftUp;
	}

	public String getResidualRootLeftDown() {
		return residualRootLeftDown;
	}

	public void setResidualRootLeftDown(String residualRootLeftDown) {
		this.residualRootLeftDown = residualRootLeftDown;
	}

	public String getResidualRootRightUp() {
		return residualRootRightUp;
	}

	public void setResidualRootRightUp(String residualRootRightUp) {
		this.residualRootRightUp = residualRootRightUp;
	}

	public String getResidualRootRightDown() {
		return residualRootRightDown;
	}

	public void setResidualRootRightDown(String residualRootRightDown) {
		this.residualRootRightDown = residualRootRightDown;
	}

	public String getMucosaCondition() {
		return mucosaCondition;
	}

	public void setMucosaCondition(String mucosaCondition) {
		this.mucosaCondition = mucosaCondition;
	}

	public String getTeethMoveOneLeftUp() {
		return teethMoveOneLeftUp;
	}

	public void setTeethMoveOneLeftUp(String teethMoveOneLeftUp) {
		this.teethMoveOneLeftUp = teethMoveOneLeftUp;
	}

	public String getTeethMoveOneLeftDown() {
		return teethMoveOneLeftDown;
	}

	public void setTeethMoveOneLeftDown(String teethMoveOneLeftDown) {
		this.teethMoveOneLeftDown = teethMoveOneLeftDown;
	}

	public String getTeethMoveOneRightUp() {
		return teethMoveOneRightUp;
	}

	public void setTeethMoveOneRightUp(String teethMoveOneRightUp) {
		this.teethMoveOneRightUp = teethMoveOneRightUp;
	}

	public String getTeethMoveOneRightDown() {
		return teethMoveOneRightDown;
	}

	public void setTeethMoveOneRightDown(String teethMoveOneRightDown) {
		this.teethMoveOneRightDown = teethMoveOneRightDown;
	}

	public String getTeethMoveTwoLeftUp() {
		return teethMoveTwoLeftUp;
	}

	public void setTeethMoveTwoLeftUp(String teethMoveTwoLeftUp) {
		this.teethMoveTwoLeftUp = teethMoveTwoLeftUp;
	}

	public String getTeethMoveTwoLeftDown() {
		return teethMoveTwoLeftDown;
	}

	public void setTeethMoveTwoLeftDown(String teethMoveTwoLeftDown) {
		this.teethMoveTwoLeftDown = teethMoveTwoLeftDown;
	}

	public String getTeethMoveTwoRightUp() {
		return teethMoveTwoRightUp;
	}

	public void setTeethMoveTwoRightUp(String teethMoveTwoRightUp) {
		this.teethMoveTwoRightUp = teethMoveTwoRightUp;
	}

	public String getTeethMoveTwoRightDown() {
		return teethMoveTwoRightDown;
	}

	public void setTeethMoveTwoRightDown(String teethMoveTwoRightDown) {
		this.teethMoveTwoRightDown = teethMoveTwoRightDown;
	}

	public String getTeethMoveThreeLeftUp() {
		return teethMoveThreeLeftUp;
	}

	public void setTeethMoveThreeLeftUp(String teethMoveThreeLeftUp) {
		this.teethMoveThreeLeftUp = teethMoveThreeLeftUp;
	}

	public String getTeethMoveThreeLeftDown() {
		return teethMoveThreeLeftDown;
	}

	public void setTeethMoveThreeLeftDown(String teethMoveThreeLeftDown) {
		this.teethMoveThreeLeftDown = teethMoveThreeLeftDown;
	}

	public String getTeethMoveThreeRightUp() {
		return teethMoveThreeRightUp;
	}

	public void setTeethMoveThreeRightUp(String teethMoveThreeRightUp) {
		this.teethMoveThreeRightUp = teethMoveThreeRightUp;
	}

	public String getTeethMoveThreeRightDown() {
		return teethMoveThreeRightDown;
	}

	public void setTeethMoveThreeRightDown(String teethMoveThreeRightDown) {
		this.teethMoveThreeRightDown = teethMoveThreeRightDown;
	}

	public String getXrayShowsLeftUp() {
		return xrayShowsLeftUp;
	}

	public void setXrayShowsLeftUp(String xrayShowsLeftUp) {
		this.xrayShowsLeftUp = xrayShowsLeftUp;
	}

	public String getXrayShowsLeftDown() {
		return xrayShowsLeftDown;
	}

	public void setXrayShowsLeftDown(String xrayShowsLeftDown) {
		this.xrayShowsLeftDown = xrayShowsLeftDown;
	}

	public String getXrayShowsRightUp() {
		return xrayShowsRightUp;
	}

	public void setXrayShowsRightUp(String xrayShowsRightUp) {
		this.xrayShowsRightUp = xrayShowsRightUp;
	}

	public String getXrayShowsRightDown() {
		return xrayShowsRightDown;
	}

	public void setXrayShowsRightDown(String xrayShowsRightDown) {
		this.xrayShowsRightDown = xrayShowsRightDown;
	}

	public String getFromMaxillarySinusFloor() {
		return fromMaxillarySinusFloor;
	}

	public void setFromMaxillarySinusFloor(String fromMaxillarySinusFloor) {
		this.fromMaxillarySinusFloor = fromMaxillarySinusFloor;
	}

	public String getFromNasalFloor() {
		return fromNasalFloor;
	}

	public void setFromNasalFloor(String fromNasalFloor) {
		this.fromNasalFloor = fromNasalFloor;
	}

	public String getFromInferiorDentalNerve() {
		return fromInferiorDentalNerve;
	}

	public void setFromInferiorDentalNerve(String fromInferiorDentalNerve) {
		this.fromInferiorDentalNerve = fromInferiorDentalNerve;
	}

	public String getAlveloarCrestWidths() {
		return alveloarCrestWidths;
	}

	public void setAlveloarCrestWidths(String alveloarCrestWidths) {
		this.alveloarCrestWidths = alveloarCrestWidths;
	}

	public String getAlveloarBoneResorption() {
		return alveloarBoneResorption;
	}

	public void setAlveloarBoneResorption(String alveloarBoneResorption) {
		this.alveloarBoneResorption = alveloarBoneResorption;
	}

	public String getPeriodontal_lesion() {
		return periodontal_lesion;
	}

	public void setPeriodontal_lesion(String periodontal_lesion) {
		this.periodontal_lesion = periodontal_lesion;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getTreatmentPlan() {
		return treatmentPlan;
	}

	public void setTreatmentPlan(String treatmentPlan) {
		this.treatmentPlan = treatmentPlan;
	}

	public String getExtractionLeftUp() {
		return extractionLeftUp;
	}

	public void setExtractionLeftUp(String extractionLeftUp) {
		this.extractionLeftUp = extractionLeftUp;
	}

	public String getExtractionLeftDown() {
		return extractionLeftDown;
	}

	public void setExtractionLeftDown(String extractionLeftDown) {
		this.extractionLeftDown = extractionLeftDown;
	}

	public String getExtractionRightUp() {
		return extractionRightUp;
	}

	public void setExtractionRightUp(String extractionRightUp) {
		this.extractionRightUp = extractionRightUp;
	}

	public String getExtractionRightDown() {
		return extractionRightDown;
	}

	public void setExtractionRightDown(String extractionRightDown) {
		this.extractionRightDown = extractionRightDown;
	}

	public String getImplantAssistantSurgery() {
		return implantAssistantSurgery;
	}

	public void setImplantAssistantSurgery(String implantAssistantSurgery) {
		this.implantAssistantSurgery = implantAssistantSurgery;
	}

	public String getImplantFirstTimeLU() {
		return implantFirstTimeLU;
	}

	public void setImplantFirstTimeLU(String implantFirstTimeLU) {
		this.implantFirstTimeLU = implantFirstTimeLU;
	}

	public String getImplantFirstTimeLD() {
		return implantFirstTimeLD;
	}

	public void setImplantFirstTimeLD(String implantFirstTimeLD) {
		this.implantFirstTimeLD = implantFirstTimeLD;
	}

	public String getImplantFirstTimeRU() {
		return implantFirstTimeRU;
	}

	public void setImplantFirstTimeRU(String implantFirstTimeRU) {
		this.implantFirstTimeRU = implantFirstTimeRU;
	}

	public String getImplantFirstTimeRD() {
		return implantFirstTimeRD;
	}

	public void setImplantFirstTimeRD(String implantFirstTimeRD) {
		this.implantFirstTimeRD = implantFirstTimeRD;
	}

	public String getImplantSecondTimeLU() {
		return implantSecondTimeLU;
	}

	public void setImplantSecondTimeLU(String implantSecondTimeLU) {
		this.implantSecondTimeLU = implantSecondTimeLU;
	}

	public String getImplantSecondTimeLD() {
		return implantSecondTimeLD;
	}

	public void setImplantSecondTimeLD(String implantSecondTimeLD) {
		this.implantSecondTimeLD = implantSecondTimeLD;
	}

	public String getImplantSecondTimeRU() {
		return implantSecondTimeRU;
	}

	public void setImplantSecondTimeRU(String implantSecondTimeRU) {
		this.implantSecondTimeRU = implantSecondTimeRU;
	}

	public String getImplantSecondTimeRD() {
		return implantSecondTimeRD;
	}

	public void setImplantSecondTimeRD(String implantSecondTimeRD) {
		this.implantSecondTimeRD = implantSecondTimeRD;
	}

	public String getBonePowder() {
		return bonePowder;
	}

	public void setBonePowder(String bonePowder) {
		this.bonePowder = bonePowder;
	}

	public String getLargeParticle() {
		return largeParticle;
	}

	public void setLargeParticle(String largeParticle) {
		this.largeParticle = largeParticle;
	}

	public String getSmallParticle() {
		return smallParticle;
	}

	public void setSmallParticle(String smallParticle) {
		this.smallParticle = smallParticle;
	}

	public String getBoneCollagen() {
		return boneCollagen;
	}

	public void setBoneCollagen(String boneCollagen) {
		this.boneCollagen = boneCollagen;
	}

	public String getTianBo() {
		return tianBo;
	}

	public void setTianBo(String tianBo) {
		this.tianBo = tianBo;
	}

	public String getMembrane() {
		return membrane;
	}

	public void setMembrane(String membrane) {
		this.membrane = membrane;
	}

	public String getCollagenMembBig() {
		return collagenMembBig;
	}

	public void setCollagenMembBig(String collagenMembBig) {
		this.collagenMembBig = collagenMembBig;
	}

	public String getCollagenMembSmall() {
		return collagenMembSmall;
	}

	public void setCollagenMembSmall(String collagenMembSmall) {
		this.collagenMembSmall = collagenMembSmall;
	}

	public String getTitaniumMesh() {
		return titaniumMesh;
	}

	public void setTitaniumMesh(String titaniumMesh) {
		this.titaniumMesh = titaniumMesh;
	}

	public String getRepaireMethod() {
		return repaireMethod;
	}

	public void setRepaireMethod(String repaireMethod) {
		this.repaireMethod = repaireMethod;
	}

	public String getTemporDentCondition() {
		return temporDentCondition;
	}

	public void setTemporDentCondition(String temporDentCondition) {
		this.temporDentCondition = temporDentCondition;
	}

	public String getTreatmentOtherOralLU() {
		return treatmentOtherOralLU;
	}

	public void setTreatmentOtherOralLU(String treatmentOtherOralLU) {
		this.treatmentOtherOralLU = treatmentOtherOralLU;
	}

	public String getTreatmentOtherOralLD() {
		return treatmentOtherOralLD;
	}

	public void setTreatmentOtherOralLD(String treatmentOtherOralLD) {
		this.treatmentOtherOralLD = treatmentOtherOralLD;
	}

	public String getTreatmentOtherOralRU() {
		return treatmentOtherOralRU;
	}

	public void setTreatmentOtherOralRU(String treatmentOtherOralRU) {
		this.treatmentOtherOralRU = treatmentOtherOralRU;
	}

	public String getTreatmentOtherOralRD() {
		return treatmentOtherOralRD;
	}

	public void setTreatmentOtherOralRD(String treatmentOtherOralRD) {
		this.treatmentOtherOralRD = treatmentOtherOralRD;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getDoctorSignature() {
		return doctorSignature;
	}

	public void setDoctorSignature(String doctorSignature) {
		this.doctorSignature = doctorSignature;
	}

	public String getPatientsignature() {
		return patientsignature;
	}

	public void setPatientsignature(String patientsignature) {
		this.patientsignature = patientsignature;
	}

}
