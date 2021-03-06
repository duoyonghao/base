package com.hudh.zzbl.entity;

public class ZzblCheck {

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
	private String patientTime;
	private String doctorTime;
	private String organization;
	private String bonePowder;
	
	private String alveloarBoneResorptionLeftUp;
	private String alveloarBoneResorptionRightUp;
	private String alveloarBoneResorptionLeftDown;
	private String alveloarBoneResorptionRightDown;
	private String periodontal_lesionLeftUp;
	private String periodontal_lesionRightUp;
	private String periodontal_lesionLeftDown;
	private String periodontal_lesionRightDown;
	private String doctor_signatory;
	
	private String remark;//备注
	private String patientsignature;//患者签名
	private String doctorsignature;//医生签名
	private String moduloDesign;//术前取模，手术模板设计
	
	private String username; //患者姓名
    private String sex; //患者性别
    private String age; //患者年龄
    private String status; //选中状态
	private String diagnosisothers;//诊断其他输入
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

	public String getRemark() {
		return remark;
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

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPatientsignature() {
		return patientsignature;
	}

	public void setPatientsignature(String patientsignature) {
		this.patientsignature = patientsignature;
	}

	public String getDoctorsignature() {
		return doctorsignature;
	}

	public void setDoctorsignature(String doctorsignature) {
		this.doctorsignature = doctorsignature;
	}

	public String getModuloDesign() {
		return moduloDesign;
	}

	public void setModuloDesign(String moduloDesign) {
		this.moduloDesign = moduloDesign;
	}

	public String getBonePowder() {
		return bonePowder;
	}

	public String getAlveloarBoneResorptionLeftUp() {
		return alveloarBoneResorptionLeftUp;
	}

	public void setAlveloarBoneResorptionLeftUp(String alveloarBoneResorptionLeftUp) {
		this.alveloarBoneResorptionLeftUp = alveloarBoneResorptionLeftUp;
	}

	public String getAlveloarBoneResorptionRightUp() {
		return alveloarBoneResorptionRightUp;
	}

	public void setAlveloarBoneResorptionRightUp(String alveloarBoneResorptionRightUp) {
		this.alveloarBoneResorptionRightUp = alveloarBoneResorptionRightUp;
	}

	public String getAlveloarBoneResorptionLeftDown() {
		return alveloarBoneResorptionLeftDown;
	}

	public void setAlveloarBoneResorptionLeftDown(String alveloarBoneResorptionLeftDown) {
		this.alveloarBoneResorptionLeftDown = alveloarBoneResorptionLeftDown;
	}

	public String getAlveloarBoneResorptionRightDown() {
		return alveloarBoneResorptionRightDown;
	}

	public void setAlveloarBoneResorptionRightDown(String alveloarBoneResorptionRightDown) {
		this.alveloarBoneResorptionRightDown = alveloarBoneResorptionRightDown;
	}

	public String getPeriodontal_lesionLeftUp() {
		return periodontal_lesionLeftUp;
	}

	public void setPeriodontal_lesionLeftUp(String periodontal_lesionLeftUp) {
		this.periodontal_lesionLeftUp = periodontal_lesionLeftUp;
	}

	public String getPeriodontal_lesionRightUp() {
		return periodontal_lesionRightUp;
	}

	public void setPeriodontal_lesionRightUp(String periodontal_lesionRightUp) {
		this.periodontal_lesionRightUp = periodontal_lesionRightUp;
	}

	public String getPeriodontal_lesionLeftDown() {
		return periodontal_lesionLeftDown;
	}

	public void setPeriodontal_lesionLeftDown(String periodontal_lesionLeftDown) {
		this.periodontal_lesionLeftDown = periodontal_lesionLeftDown;
	}

	public String getPeriodontal_lesionRightDown() {
		return periodontal_lesionRightDown;
	}

	public void setPeriodontal_lesionRightDown(String periodontal_lesionRightDown) {
		this.periodontal_lesionRightDown = periodontal_lesionRightDown;
	}

	public String getDoctor_signatory() {
		return doctor_signatory;
	}

	public void setDoctor_signatory(String doctor_signatory) {
		this.doctor_signatory = doctor_signatory;
	}

	public void setBonePowder(String bonePowder) {
		this.bonePowder = bonePowder;
	}

	public String getSEQ_ID() {
		return SEQ_ID;
	}

	public void setSEQ_ID(String sEQ_ID) {
		SEQ_ID = sEQ_ID;
	}

	public String getCreatetime() {
		return createtime;
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

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getDiagnosisothers() {
		return diagnosisothers;
	}

	public void setDiagnosisothers(String diagnosisothers) {
		this.diagnosisothers = diagnosisothers;
	}
}
