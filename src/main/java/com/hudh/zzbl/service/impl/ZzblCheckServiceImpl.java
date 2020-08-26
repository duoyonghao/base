package com.hudh.zzbl.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hudh.lclj.dao.LcljTrackDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hudh.zzbl.dao.ZzblCheckDao;
import com.hudh.zzbl.entity.ZzblCheck;
import com.hudh.zzbl.service.IZzblCheckService;
import com.kqds.util.sys.YZUtility;

import net.sf.json.JSONObject;

@Service
public class ZzblCheckServiceImpl implements IZzblCheckService {
	
	@Autowired
	private ZzblCheckDao zzblCheckDao;
	@Autowired
	private LcljTrackDao lcljTrackDao;
	@Override
	public void insertZzblCheck(ZzblCheck dp, HttpServletRequest request) throws Exception {
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
		String bonePowder = request.getParameter("bonePowder");
		String doctorTime = request.getParameter("doctor_signatoryTime");
		
		String alveloarBoneResorptionLeftUp = request.getParameter("alveloarBoneResorptionLeftUp");
		String alveloarBoneResorptionRightUp = request.getParameter("alveloarBoneResorptionRightUp");
		String alveloarBoneResorptionLeftDown = request.getParameter("alveloarBoneResorptionLeftDown");
		String alveloarBoneResorptionRightDown = request.getParameter("alveloarBoneResorptionRightDown");
		String periodontal_lesionLeftUp = request.getParameter("periodontal_lesionLeftUp");
		String periodontal_lesionRightUp = request.getParameter("periodontal_lesionRightUp");
		String periodontal_lesionLeftDown = request.getParameter("periodontal_lesionLeftDown");
		String periodontal_lesionRightDown = request.getParameter("periodontal_lesionRightDown");
		String doctor_signatory = request.getParameter("doctor_signatory");
		String username = request.getParameter("username");
	    String sex = request.getParameter("sex");
	    String age = request.getParameter("age");

		String diagnosisothers = request.getParameter("diagnosisothers");
	    dp.setAge(age);
	    dp.setUsername(username);
	    dp.setSex(sex);
		dp.setAlveloarBoneResorptionLeftUp(alveloarBoneResorptionLeftUp);
		dp.setAlveloarBoneResorptionLeftDown(alveloarBoneResorptionLeftDown);
		dp.setAlveloarBoneResorptionRightDown(alveloarBoneResorptionRightDown);
		dp.setAlveloarBoneResorptionRightUp(alveloarBoneResorptionRightUp);
		dp.setPeriodontal_lesionLeftDown(periodontal_lesionLeftDown);
		dp.setPeriodontal_lesionLeftUp(periodontal_lesionLeftUp);
		dp.setPeriodontal_lesionRightDown(periodontal_lesionRightDown);
		dp.setPeriodontal_lesionRightUp(periodontal_lesionRightUp);
		dp.setDoctor_signatory(doctor_signatory);//医生签名
		dp.setDoctorTime(doctorTime);
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
		dp.setDiagnosisothers(diagnosisothers);
		zzblCheckDao.insertZzblCheck(dp);
		Map<String,String> map=new HashMap<String,String>();
		map.put("form","examine");
		map.put("status","1");
		map.put("id",id);
		lcljTrackDao.updateFormStatus(map);
	}

	@Override
	public List<JSONObject> findZzblOprationById(String id) throws Exception {
		// TODO Auto-generated method stub
		return zzblCheckDao.findZzblOprationById(id);
	}

	@Override
	public void updateZzblOprationById(ZzblCheck dp, HttpServletRequest request) throws Exception {
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
		String bonePowder = request.getParameter("bonePowder");
		String doctorTime = request.getParameter("doctor_signatoryTime");
		
		String alveloarBoneResorptionLeftUp = request.getParameter("alveloarBoneResorptionLeftUp");
		String alveloarBoneResorptionRightUp = request.getParameter("alveloarBoneResorptionRightUp");
		String alveloarBoneResorptionLeftDown = request.getParameter("alveloarBoneResorptionLeftDown");
		String alveloarBoneResorptionRightDown = request.getParameter("alveloarBoneResorptionRightDown");
		String periodontal_lesionLeftUp = request.getParameter("periodontal_lesionLeftUp");
		String periodontal_lesionRightUp = request.getParameter("periodontal_lesionRightUp");
		String periodontal_lesionLeftDown = request.getParameter("periodontal_lesionLeftDown");
		String periodontal_lesionRightDown = request.getParameter("periodontal_lesionRightDown");
		String doctor_signatory = request.getParameter("doctor_signatory");
		String diagnosisothers = request.getParameter("diagnosisothers");
		dp.setAlveloarBoneResorptionLeftUp(alveloarBoneResorptionLeftUp);
		dp.setAlveloarBoneResorptionLeftDown(alveloarBoneResorptionLeftDown);
		dp.setAlveloarBoneResorptionRightDown(alveloarBoneResorptionRightDown);
		dp.setAlveloarBoneResorptionRightUp(alveloarBoneResorptionRightUp);
		dp.setPeriodontal_lesionLeftDown(periodontal_lesionLeftDown);
		dp.setPeriodontal_lesionLeftUp(periodontal_lesionLeftUp);
		dp.setPeriodontal_lesionRightDown(periodontal_lesionRightDown);
		dp.setPeriodontal_lesionRightUp(periodontal_lesionRightUp);
		dp.setDoctor_signatory(doctor_signatory);//医生签名
		dp.setDoctorTime(doctorTime);
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
		dp.setDiagnosisothers(diagnosisothers);
		zzblCheckDao.updateZzblOprationById(dp);
	}

	@Override
	public void deleteZzblInforById(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<JSONObject> findAllZzblInfor() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZzblCheck selectZzblCheckById(String id) throws Exception {
		// TODO Auto-generated method stub
		return zzblCheckDao.selectZzblCheckById(id);
	}

}
