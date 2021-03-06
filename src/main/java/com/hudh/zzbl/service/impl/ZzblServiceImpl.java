package com.hudh.zzbl.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hudh.lclj.dao.LcljTrackDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.zzbl.dao.ZzblDao;
import com.hudh.zzbl.entity.ZzblOperation;
import com.hudh.zzbl.service.IZzblService;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class ZzblServiceImpl implements IZzblService {
	
	@Autowired
	private ZzblDao zzblDao;
	@Autowired
	private LcljTrackDao lcljTrackDao;
	@Override
	public void save(ZzblOperation dp, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		dp.setSEQ_ID(YZUtility.getUUID());
		dp.setCreatetime(YZUtility.getCurDateTimeStr());
		String id = request.getParameter("id");
		String order_number = request.getParameter("order_number");
		String physical_assessment = request.getParameter("physical_assessment");
		String consultation_opinion = request.getParameter("consultation_opinion");
		String stomatology_check = request.getParameter("stomatology_check");
		String occlusion_situation = request.getParameter("occlusion_situation");
		String interarch_distance = request.getParameter("interarch_distance");
		String repaire_type = request.getParameter("repaire_type");
		String periodontal_condition = request.getParameter("periodontal_condition");
		String teethMissLeftUp = request.getParameter("teethMissLeftUp");
		String teethMissLeftDown = request.getParameter("teethMissLeftDown");
		String teethMissRightUp = request.getParameter("teethMissRightUp");
		String teethMissRightDown = request.getParameter("teethMissRightDown");
		String reasonMissTeeth = request.getParameter("reasonMissTeeth");
		String dentalCaryLeftUp = request.getParameter("dentalCaryLeftUp");
		String dentalCaryLeftDown = request.getParameter("dentalCaryLeftDown");
		String dentalCaryRightUp = request.getParameter("dentalCaryRightUp");
		String dentalCaryRightDown = request.getParameter("dentalCaryRightDown");
		String timeMissTeeth = request.getParameter("timeMissTeeth");
		String residualRootLeftUp = request.getParameter("residualRootLeftUp");
		String residualRootLeftDown = request.getParameter("residualRootLeftDown");
		String residualRootRightUp = request.getParameter("residualRootRightUp");
		String residualRootRightDown = request.getParameter("residualRootRightDown");
		String mucosaCondition = request.getParameter("mucosaCondition");
		String teethMoveOneLeftUp = request.getParameter("teethMoveOneLeftUp");
		String teethMoveOneLeftDown = request.getParameter("teethMoveOneLeftDown");
		String teethMoveOneRightUp = request.getParameter("teethMoveOneRightUp");
		String teethMoveOneRightDown = request.getParameter("teethMoveOneRightDown");
		String teethMoveTwoLeftUp = request.getParameter("teethMoveTwoLeftUp");
		String teethMoveTwoLeftDown = request.getParameter("teethMoveTwoLeftDown");
		String teethMoveTwoRightUp = request.getParameter("teethMoveTwoRightUp");
		String teethMoveTwoRightDown = request.getParameter("teethMoveTwoRightDown");
		String teethMoveThreeLeftUp = request.getParameter("teethMoveThreeLeftUp");
		String teethMoveThreeLeftDown = request.getParameter("teethMoveThreeLeftDown");
		String teethMoveThreeRightUp = request.getParameter("teethMoveThreeRightUp");
		String teethMoveThreeRightDown = request.getParameter("teethMoveThreeRightDown");
		String xrayShowsLeftUp = request.getParameter("xrayShowsLeftUp");
		String xrayShowsLeftDown = request.getParameter("xrayShowsLeftDown");
		String xrayShowsRightUp = request.getParameter("xrayShowsRightUp");
		String xrayShowsRightDown = request.getParameter("xrayShowsRightDown");
		String fromMaxillarySinusFloor = request.getParameter("fromMaxillarySinusFloor");
		String fromNasalFloor = request.getParameter("fromNasalFloor");
		String fromInferiorDentalNerve = request.getParameter("fromInferiorDentalNerve");
		String alveloarCrestWidths = request.getParameter("alveloarCrestWidths");
		String alveloarBoneResorption = request.getParameter("alveloarBoneResorption");
		String periodontal_lesion = request.getParameter("periodontal_lesion");
		String diagnosis = request.getParameter("diagnosis");
		String others = request.getParameter("others");
		String treatmentPlan = request.getParameter("treatmentPlan");
		String extractionLeftUp = request.getParameter("extractionLeftUp");
		String extractionLeftDown = request.getParameter("extractionLeftDown");
		String extractionRightUp = request.getParameter("extractionRightUp");
		String extractionRightDown = request.getParameter("extractionRightDown");
		String implantAssistantSurgery = request.getParameter("implantAssistantSurgery");
		String implantFirstTimeLU = request.getParameter("implantFirstTimeLU");
		String implantFirstTimeLD = request.getParameter("implantFirstTimeLD");
		String implantFirstTimeRU = request.getParameter("implantFirstTimeRU");
		String implantFirstTimeRD = request.getParameter("implantFirstTimeRD");
		String implantSecondTimeLU = request.getParameter("implantSecondTimeLU");
		String implantSecondTimeLD = request.getParameter("implantSecondTimeLD");
		String implantSecondTimeRU = request.getParameter("implantSecondTimeRU");
		String implantSecondTimeRD = request.getParameter("implantSecondTimeRD");
		String bonePowder = request.getParameter("bonePowder");
		String largeParticle = request.getParameter("largeParticle");
		String smallParticle = request.getParameter("smallParticle");
		String boneCollagen = request.getParameter("boneCollagen");
		String tianBo = request.getParameter("tianBo");
		String membrane = request.getParameter("membrane");
		String collagenMembBig = request.getParameter("collagenMembBig");
		String collagenMembSmall = request.getParameter("collagenMembSmall");
		String titaniumMesh = request.getParameter("titaniumMesh");
		String repaireMethod = request.getParameter("repaireMethod");
		String temporDentCondition = request.getParameter("temporDentCondition");
		String treatmentOtherOralLU = request.getParameter("treatmentOtherOralLU");
		String treatmentOtherOralLD = request.getParameter("treatmentOtherOralLD");
		String treatmentOtherOralRU = request.getParameter("treatmentOtherOralRU");
		String treatmentOtherOralRD = request.getParameter("treatmentOtherOralRD");
		String username = request.getParameter("username");
	    String sex = request.getParameter("sex");
	    String age = request.getParameter("age");
	    String patientTime = request.getParameter("PatientTime");
	    String doctorTime = request.getParameter("doctorTime");
	    String remark = request.getParameter("remarks");
	    String treamentPlan = request.getParameter("treamentPlan");
	    String implantSystem = request.getParameter("implantSystem");
	    String moduloDesign = request.getParameter("moduloDesign");

	    String status = request.getParameter("status");
	    String doctorSignature = request.getParameter("doctorSignature");
	    String patientsignature = request.getParameter("patientsignature");

		String secondextractionleftup = request.getParameter("secondextractionleftup");
		String secondextractionrightup = request.getParameter("secondextractionrightup");
		String secondextractionleftdown = request.getParameter("secondextractionleftdown");
		String secondextractionrightdown = request.getParameter("secondextractionrightdown");
		String implantsystemotherstext = request.getParameter("implantsystemotherstext");

	    dp.setModuloDesign(moduloDesign);
	    dp.setImplantSystem(implantSystem);
	    dp.setTreamentPlan(treamentPlan);
	    dp.setRemark(remark);
	    dp.setDoctorTime(doctorTime);
	    dp.setPatientTime(patientTime);
	    dp.setAge(age);
	    dp.setUsername(username);
	    dp.setSex(sex);
		dp.setId(id);
		dp.setOrder_number(order_number);
		dp.setPhysical_assessment(physical_assessment);
		dp.setConsultation_opinion(consultation_opinion);
		dp.setStomatology_check(stomatology_check);
		dp.setOcclusion_situation(occlusion_situation);
		dp.setInterarch_distance(interarch_distance);
		dp.setRepaire_type(repaire_type);
		dp.setPeriodontal_condition(periodontal_condition);
		dp.setTeethMissLeftUp(teethMissLeftUp);
		dp.setTeethMissLeftDown(teethMissLeftDown);
		dp.setTeethMissRightUp(teethMissRightUp);
		dp.setTeethMissRightDown(teethMissRightDown);
		dp.setReasonMissTeeth(reasonMissTeeth);
		dp.setDentalCaryLeftUp(dentalCaryLeftUp);
		dp.setDentalCaryLeftDown(dentalCaryLeftDown);
		dp.setDentalCaryRightUp(dentalCaryRightUp);
		dp.setDentalCaryRightDown(dentalCaryRightDown);
		dp.setTimeMissTeeth(timeMissTeeth);
		dp.setResidualRootLeftUp(residualRootLeftUp);
		dp.setResidualRootLeftDown(residualRootLeftDown);
		dp.setResidualRootRightUp(residualRootRightUp);
		dp.setResidualRootRightDown(residualRootRightDown);
		dp.setMucosaCondition(mucosaCondition);
		dp.setTeethMoveOneLeftUp(teethMoveOneLeftUp);
		dp.setTeethMoveOneLeftDown(teethMoveOneLeftDown);
		dp.setTeethMoveOneRightUp(teethMoveOneRightUp);
		dp.setTeethMoveOneRightUp(teethMoveOneRightUp);
		dp.setTeethMoveOneRightDown(teethMoveOneRightDown);
		dp.setTeethMoveTwoLeftUp(teethMoveTwoLeftUp);
		dp.setTeethMoveTwoLeftDown(teethMoveTwoLeftDown);
		dp.setTeethMoveTwoRightUp(teethMoveTwoRightUp);
		dp.setTeethMoveTwoRightDown(teethMoveTwoRightDown);
		dp.setTeethMoveThreeLeftUp(teethMoveThreeLeftUp);
		dp.setTeethMoveThreeLeftDown(teethMoveThreeLeftDown);
		dp.setTeethMoveThreeRightUp(teethMoveThreeRightUp);
		dp.setTeethMoveThreeRightDown(teethMoveThreeRightDown);
		dp.setXrayShowsLeftUp(xrayShowsLeftUp);
		dp.setXrayShowsLeftDown(xrayShowsLeftDown);
		dp.setXrayShowsLeftDown(xrayShowsLeftDown);
		dp.setXrayShowsRightUp(xrayShowsRightUp);
		dp.setXrayShowsRightDown(xrayShowsRightDown);
		dp.setFromInferiorDentalNerve(fromInferiorDentalNerve);
		dp.setFromMaxillarySinusFloor(fromMaxillarySinusFloor);
		dp.setFromNasalFloor(fromNasalFloor);
		dp.setAlveloarBoneResorption(alveloarBoneResorption);
		dp.setAlveloarCrestWidths(alveloarCrestWidths);
		dp.setPeriodontal_condition(periodontal_condition);
		dp.setPeriodontal_lesion(periodontal_lesion);
		dp.setBonePowder(bonePowder);
		dp.setDiagnosis(diagnosis);
		dp.setOthers(others);
		dp.setTreatmentOtherOralLU(treatmentOtherOralLU);
		dp.setTreatmentOtherOralLD(treatmentOtherOralLD);
		dp.setTreatmentOtherOralRU(treatmentOtherOralRU);
		dp.setTreatmentOtherOralRD(treatmentOtherOralRD);
		dp.setTreatmentPlan(treatmentPlan);
		dp.setExtractionLeftUp(extractionLeftUp);
		dp.setExtractionLeftDown(extractionLeftDown);
		dp.setExtractionRightUp(extractionRightUp);
		dp.setExtractionRightDown(extractionRightDown);
		dp.setImplantAssistantSurgery(implantAssistantSurgery);
		dp.setImplantFirstTimeLU(implantFirstTimeLU);
		dp.setImplantFirstTimeLD(implantFirstTimeLD);
		dp.setImplantFirstTimeRU(implantFirstTimeRU);
		dp.setImplantFirstTimeRD(implantFirstTimeRD);
		dp.setImplantAssistantSurgery(implantAssistantSurgery);
		dp.setImplantFirstTimeLU(implantFirstTimeLU);
		dp.setImplantFirstTimeLD(implantFirstTimeLD);
		dp.setImplantFirstTimeRU(implantFirstTimeRU);
		dp.setImplantFirstTimeRD(implantFirstTimeRD);
		dp.setImplantSecondTimeLU(implantSecondTimeLU);
		dp.setImplantSecondTimeLD(implantSecondTimeLD);
		dp.setImplantSecondTimeRU(implantSecondTimeRU);
		dp.setImplantSecondTimeRD(implantSecondTimeRD);
		dp.setLargeParticle(largeParticle);
		dp.setSmallParticle(smallParticle);
		dp.setCollagenMembSmall(collagenMembSmall);
		dp.setBonePowder(bonePowder);
		dp.setTianBo(tianBo);
		dp.setBoneCollagen(boneCollagen);
		dp.setCollagenMembBig(collagenMembBig);
		dp.setCollagenMembSmall(collagenMembSmall);
		dp.setMembrane(membrane);
		dp.setTitaniumMesh(titaniumMesh);
		dp.setRepaireMethod(repaireMethod);
		dp.setTemporDentCondition(temporDentCondition);
		dp.setStatus(status);
		dp.setDoctorSignature(doctorSignature);
		dp.setPatientsignature(patientsignature);
		dp.setSecondextractionleftup(secondextractionleftup);
		dp.setSecondextractionrightup(secondextractionrightup);
		dp.setSecondextractionleftdown(secondextractionleftdown);
		dp.setSecondextractionrightdown(secondextractionrightdown);
		dp.setImplantsystemotherstext(implantsystemotherstext);
		zzblDao.save(dp);
		Map<String,String> map=new HashMap<String,String>();
		map.put("form","diagnosis");
		map.put("status","1");
		map.put("id",id);
		lcljTrackDao.updateFormStatus(map);
	}

	@Override
	public List<ZzblOperation> findZzblOprationById(String id) throws Exception {
		// TODO Auto-generated method stub
		return zzblDao.findZzblOprationById(id);
		
	}

	@Override
	public void updateZzblOprationById(ZzblOperation dp, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		String physical_assessment = request.getParameter("physical_assessment");
		String consultation_opinion = request.getParameter("consultation_opinion");
		String stomatology_check = request.getParameter("stomatology_check");
		String occlusion_situation = request.getParameter("occlusion_situation");
		String interarch_distance = request.getParameter("interarch_distance");
		String repaire_type = request.getParameter("repaire_type");
		String periodontal_condition = request.getParameter("periodontal_condition");
		String teethMissLeftUp = request.getParameter("teethMissLeftUp");
		String teethMissLeftDown = request.getParameter("teethMissLeftDown");
		String teethMissRightUp = request.getParameter("teethMissRightUp");
		String teethMissRightDown = request.getParameter("teethMissRightDown");
		String reasonMissTeeth = request.getParameter("reasonMissTeeth");
		String dentalCaryLeftUp = request.getParameter("dentalCaryLeftUp");
		String dentalCaryLeftDown = request.getParameter("dentalCaryLeftDown");
		String dentalCaryRightUp = request.getParameter("dentalCaryRightUp");
		String dentalCaryRightDown = request.getParameter("dentalCaryRightDown");
		String timeMissTeeth = request.getParameter("timeMissTeeth");
		String residualRootLeftUp = request.getParameter("residualRootLeftUp");
		String residualRootLeftDown = request.getParameter("residualRootLeftDown");
		String residualRootRightUp = request.getParameter("residualRootRightUp");
		String residualRootRightDown = request.getParameter("residualRootRightDown");
		String mucosaCondition = request.getParameter("mucosaCondition");
		String teethMoveOneLeftUp = request.getParameter("teethMoveOneLeftUp");
		String teethMoveOneLeftDown = request.getParameter("teethMoveOneLeftDown");
		String teethMoveOneRightUp = request.getParameter("teethMoveOneRightUp");
		String teethMoveOneRightDown = request.getParameter("teethMoveOneRightDown");
		String teethMoveTwoLeftUp = request.getParameter("teethMoveTwoLeftUp");
		String teethMoveTwoLeftDown = request.getParameter("teethMoveTwoLeftDown");
		String teethMoveTwoRightUp = request.getParameter("teethMoveTwoRightUp");
		String teethMoveTwoRightDown = request.getParameter("teethMoveTwoRightDown");
		String teethMoveThreeLeftUp = request.getParameter("teethMoveThreeLeftUp");
		String teethMoveThreeLeftDown = request.getParameter("teethMoveThreeLeftDown");
		String teethMoveThreeRightUp = request.getParameter("teethMoveThreeRightUp");
		String teethMoveThreeRightDown = request.getParameter("teethMoveThreeRightDown");
		String xrayShowsLeftUp = request.getParameter("xrayShowsLeftUp");
		String xrayShowsLeftDown = request.getParameter("xrayShowsLeftDown");
		String xrayShowsRightUp = request.getParameter("xrayShowsRightUp");
		String xrayShowsRightDown = request.getParameter("xrayShowsRightDown");
		String fromMaxillarySinusFloor = request.getParameter("fromMaxillarySinusFloor");
		String fromNasalFloor = request.getParameter("fromNasalFloor");
		String fromInferiorDentalNerve = request.getParameter("fromInferiorDentalNerve");
		String alveloarCrestWidths = request.getParameter("alveloarCrestWidths");
		String alveloarBoneResorption = request.getParameter("alveloarBoneResorption");
		String periodontal_lesion = request.getParameter("periodontal_lesion");
		String diagnosis = request.getParameter("diagnosis");
		String others = request.getParameter("others");
		String treatmentPlan = request.getParameter("treatmentPlan");
		String extractionLeftUp = request.getParameter("extractionLeftUp");
		String extractionLeftDown = request.getParameter("extractionLeftDown");
		String extractionRightUp = request.getParameter("extractionRightUp");
		String extractionRightDown = request.getParameter("extractionRightDown");
		String implantAssistantSurgery = request.getParameter("implantAssistantSurgery");
		String implantFirstTimeLU = request.getParameter("implantFirstTimeLU");
		String implantFirstTimeLD = request.getParameter("implantFirstTimeLD");
		String implantFirstTimeRU = request.getParameter("implantFirstTimeRU");
		String implantFirstTimeRD = request.getParameter("implantFirstTimeRD");
		String implantSecondTimeLU = request.getParameter("implantSecondTimeLU");
		String implantSecondTimeLD = request.getParameter("implantSecondTimeLD");
		String implantSecondTimeRU = request.getParameter("implantSecondTimeRU");
		String implantSecondTimeRD = request.getParameter("implantSecondTimeRD");
		String bonePowder = request.getParameter("bonePowder");
		String largeParticle = request.getParameter("largeParticle");
		String smallParticle = request.getParameter("smallParticle");
		String boneCollagen = request.getParameter("boneCollagen");
		String tianBo = request.getParameter("tianBo");
		String membrane = request.getParameter("membrane");
		String collagenMembBig = request.getParameter("collagenMembBig");
		String collagenMembSmall = request.getParameter("collagenMembSmall");
		String titaniumMesh = request.getParameter("titaniumMesh");
		String repaireMethod = request.getParameter("repaireMethod");
		String temporDentCondition = request.getParameter("temporDentCondition");
		String treatmentOtherOralLU = request.getParameter("treatmentOtherOralLU");
		String treatmentOtherOralLD = request.getParameter("treatmentOtherOralLD");
		String treatmentOtherOralRU = request.getParameter("treatmentOtherOralRU");
		String treatmentOtherOralRD = request.getParameter("treatmentOtherOralRD");
		String username = request.getParameter("username");
	    String sex = request.getParameter("sex");
	    String age = request.getParameter("age");
	    String patientTime = request.getParameter("PatientTime");
	    String doctorTime = request.getParameter("doctorTime");
	    String remark = request.getParameter("remarks");
	    String treamentPlan = request.getParameter("treamentPlan");
	    String implantSystem = request.getParameter("implantSystem");
	    String moduloDesign = request.getParameter("moduloDesign");
	    String status = request.getParameter("status");
	    String doctorSignature = request.getParameter("doctorSignature");
	    String patientsignature = request.getParameter("patientsignature");
		String secondextractionleftup = request.getParameter("secondextractionleftup");
		String secondextractionrightup = request.getParameter("secondextractionrightup");
		String secondextractionleftdown = request.getParameter("secondextractionleftdown");
		String secondextractionrightdown = request.getParameter("secondextractionrightdown");
		String implantsystemotherstext = request.getParameter("implantsystemotherstext");

	    dp.setModuloDesign(moduloDesign);
	    dp.setImplantSystem(implantSystem);
	    dp.setTreamentPlan(treamentPlan);
	    dp.setRemark(remark);
	    dp.setDoctorTime(doctorTime);
	    dp.setPatientTime(patientTime);
	    dp.setAge(age);
	    dp.setUsername(username);
	    dp.setSex(sex);
		dp.setPhysical_assessment(physical_assessment);
		dp.setConsultation_opinion(consultation_opinion);
		dp.setStomatology_check(stomatology_check);
		dp.setOcclusion_situation(occlusion_situation);
		dp.setInterarch_distance(interarch_distance);
		dp.setRepaire_type(repaire_type);
		dp.setPeriodontal_condition(periodontal_condition);
		dp.setTeethMissLeftUp(teethMissLeftUp);
		dp.setTeethMissLeftDown(teethMissLeftDown);
		dp.setTeethMissRightUp(teethMissRightUp);
		dp.setTeethMissRightDown(teethMissRightDown);
		dp.setReasonMissTeeth(reasonMissTeeth);
		dp.setDentalCaryLeftUp(dentalCaryLeftUp);
		dp.setDentalCaryLeftDown(dentalCaryLeftDown);
		dp.setDentalCaryRightUp(dentalCaryRightUp);
		dp.setDentalCaryRightDown(dentalCaryRightDown);
		dp.setTimeMissTeeth(timeMissTeeth);
		dp.setResidualRootLeftUp(residualRootLeftUp);
		dp.setResidualRootLeftDown(residualRootLeftDown);
		dp.setResidualRootRightUp(residualRootRightUp);
		dp.setResidualRootRightDown(residualRootRightDown);
		dp.setMucosaCondition(mucosaCondition);
		dp.setTeethMoveOneLeftUp(teethMoveOneLeftUp);
		dp.setTeethMoveOneLeftDown(teethMoveOneLeftDown);
		dp.setTeethMoveOneRightUp(teethMoveOneRightUp);
		dp.setTeethMoveOneRightUp(teethMoveOneRightUp);
		dp.setTeethMoveOneRightDown(teethMoveOneRightDown);
		dp.setTeethMoveTwoLeftUp(teethMoveTwoLeftUp);
		dp.setTeethMoveTwoLeftDown(teethMoveTwoLeftDown);
		dp.setTeethMoveTwoRightUp(teethMoveTwoRightUp);
		dp.setTeethMoveTwoRightDown(teethMoveTwoRightDown);
		dp.setTeethMoveThreeLeftUp(teethMoveThreeLeftUp);
		dp.setTeethMoveThreeLeftDown(teethMoveThreeLeftDown);
		dp.setTeethMoveThreeRightUp(teethMoveThreeRightUp);
		dp.setTeethMoveThreeRightDown(teethMoveThreeRightDown);
		dp.setXrayShowsLeftUp(xrayShowsLeftUp);
		dp.setXrayShowsLeftDown(xrayShowsLeftDown);
		dp.setXrayShowsLeftDown(xrayShowsLeftDown);
		dp.setXrayShowsRightUp(xrayShowsRightUp);
		dp.setXrayShowsRightDown(xrayShowsRightDown);
		dp.setFromInferiorDentalNerve(fromInferiorDentalNerve);
		dp.setFromMaxillarySinusFloor(fromMaxillarySinusFloor);
		dp.setFromNasalFloor(fromNasalFloor);
		dp.setAlveloarBoneResorption(alveloarBoneResorption);
		dp.setAlveloarCrestWidths(alveloarCrestWidths);
		dp.setPeriodontal_condition(periodontal_condition);
		dp.setPeriodontal_lesion(periodontal_lesion);
		dp.setBonePowder(bonePowder);
		dp.setDiagnosis(diagnosis);
		dp.setOthers(others);
		dp.setTreatmentOtherOralLU(treatmentOtherOralLU);
		dp.setTreatmentOtherOralLD(treatmentOtherOralLD);
		dp.setTreatmentOtherOralRU(treatmentOtherOralRU);
		dp.setTreatmentOtherOralRD(treatmentOtherOralRD);
		dp.setTreatmentPlan(treatmentPlan);
		dp.setExtractionLeftUp(extractionLeftUp);
		dp.setExtractionLeftDown(extractionLeftDown);
		dp.setExtractionRightUp(extractionRightUp);
		dp.setExtractionRightDown(extractionRightDown);
		dp.setImplantAssistantSurgery(implantAssistantSurgery);
		dp.setImplantFirstTimeLU(implantFirstTimeLU);
		dp.setImplantFirstTimeLD(implantFirstTimeLD);
		dp.setImplantFirstTimeRU(implantFirstTimeRU);
		dp.setImplantFirstTimeRD(implantFirstTimeRD);
		dp.setImplantAssistantSurgery(implantAssistantSurgery);
		dp.setImplantFirstTimeLU(implantFirstTimeLU);
		dp.setImplantFirstTimeLD(implantFirstTimeLD);
		dp.setImplantFirstTimeRU(implantFirstTimeRU);
		dp.setImplantFirstTimeRD(implantFirstTimeRD);
		dp.setImplantSecondTimeLU(implantSecondTimeLU);
		dp.setImplantSecondTimeLD(implantSecondTimeLD);
		dp.setImplantSecondTimeRU(implantSecondTimeRU);
		dp.setImplantSecondTimeRD(implantSecondTimeRD);
		dp.setLargeParticle(largeParticle);
		dp.setSmallParticle(smallParticle);
		dp.setCollagenMembSmall(collagenMembSmall);
		dp.setBonePowder(bonePowder);
		dp.setTianBo(tianBo);
		dp.setBoneCollagen(boneCollagen);
		dp.setCollagenMembBig(collagenMembBig);
		dp.setCollagenMembSmall(collagenMembSmall);
		dp.setMembrane(membrane);
		dp.setTitaniumMesh(titaniumMesh);
		dp.setRepaireMethod(repaireMethod);
		dp.setTemporDentCondition(temporDentCondition);
		dp.setStatus(status);
		dp.setPatientsignature(patientsignature);
		dp.setDoctorSignature(doctorSignature);
		dp.setSecondextractionleftup(secondextractionleftup);
		dp.setSecondextractionrightup(secondextractionrightup);
		dp.setSecondextractionleftdown(secondextractionleftdown);
		dp.setSecondextractionrightdown(secondextractionrightdown);
		dp.setImplantsystemotherstext(implantsystemotherstext);
		zzblDao.updateZzblOprationById(dp);
	}

	@Override
	public void deleteZzblInforById(String id) throws Exception {
		// TODO Auto-generated method stub
		zzblDao.deleteZzblInforById(id);
	}

	@Override
	public List<JSONObject> findAllZzblInfor() throws Exception {
		// TODO Auto-generated method stub
		List<JSONObject> list = zzblDao.findAllZzblInfor();
		return list;
	}

	@Override
	public JSONObject selectZzblOperationById(String id) throws Exception {
		// TODO Auto-generated method stub
		return zzblDao.selectZzblOperationById(id);
	}

}
