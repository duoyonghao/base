package com.hudh.zzbl.controller;

import com.hudh.zzbl.entity.AdviceNote;
import com.hudh.zzbl.entity.FamiliarBook;
import com.hudh.zzbl.entity.LocatorFamiliar;
import com.hudh.zzbl.service.DzblService;
import com.kqds.util.sys.YZUtility;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/HUDH_ZzblAskAct"})
public class HUDH_ZzblAskAct
{
  private Logger logger = LoggerFactory.getLogger(HUDH_ZzblAct.class);
  @Autowired
  private DzblService dzblService;
  
  @RequestMapping({"/saveCaseHistory.act"})
  public String saveCaseHistory(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String seq_id = YZUtility.getUUID();
    String LcljNum = request.getParameter("LcljNum");
    String userName = request.getParameter("username");
    String LcljId = request.getParameter("LcljId");
    String agomphosTime = request.getParameter("agomphosTime");
    String symptom = request.getParameter("symptom");
    String plantTime = request.getParameter("plantTime");
    String isHypertension = request.getParameter("isHypertension");
    String hypertension = request.getParameter("hypertension");
    String isTakeMedicie = request.getParameter("isTakeMedicie");
    String isControl = request.getParameter("isControl");
    String pressure = request.getParameter("pressure");
    String IsHearDiease = request.getParameter("IsHearDiease");
    String hearDiease = request.getParameter("hearDiease");
    String isPrepareMedication = request.getParameter("isPrepareMedication");
    String isDiabetes = request.getParameter("isDiabetes");
    String diabetes = request.getParameter("diabetes");
    String dietControl = request.getParameter("dietControl");
    String isDietControl = request.getParameter("isDietControl");
    String isInfarction = request.getParameter("isInfarction");
    String infarction = request.getParameter("infarction");
    String IsBloodCoagulation = request.getParameter("IsBloodCoagulation");
    String bloodCoagulation = request.getParameter("bloodCoagulation");
    String isAntiFreezing = request.getParameter("isAntiFreezing");
    String antiFreezing = request.getParameter("antiFreezing");
    String antiFreezingTime = request.getParameter("antiFreezingTime");
    String isHepatitisB = request.getParameter("isHepatitisB");
    String hepatitisB = request.getParameter("hepatitisB");
    String IsHepatitisC = request.getParameter("IsHepatitisC");
    String hepatitisC = request.getParameter("hepatitisC");
    String IsHIV = request.getParameter("IsHIV");
    String Hiv = request.getParameter("Hiv");
    String isYphilis = request.getParameter("isYphilis");
    String syphilis = request.getParameter("syphilis");
    String IsCancer = request.getParameter("IsCancer");
    String cancer = request.getParameter("cancer");
    String IsMaxillofacial = request.getParameter("IsMaxillofacial");
    String maxillofacial = request.getParameter("maxillofacial");
    String isInflammation = request.getParameter("isInflammation");
    String inflammation = request.getParameter("inflammation");
    String IsPharmacy = request.getParameter("IsPharmacy");
    String Treatment = request.getParameter("Treatment");
    String pharmacy = request.getParameter("pharmacy");
    String isDrugAbuse = request.getParameter("isDrugAbuse");
    String drugAbuse = request.getParameter("drugAbuse");
    String isPsychosis = request.getParameter("isPsychosis");
    String psychosis = request.getParameter("psychosis");
    String IsMucousMembrane = request.getParameter("IsMucousMembrane");
    String mucousMembrane = request.getParameter("mucousMembrane");
    String IsGlucocorticoids = request.getParameter("IsGlucocorticoids");
    String glucocorticoids = request.getParameter("glucocorticoids");
    String isOtherDiseases = request.getParameter("isOtherDiseases");
    String otherDiseases = request.getParameter("otherDiseases");
    String isDrugAllergy = request.getParameter("isDrugAllergy");
    String allergicLength = request.getParameter("allergicLength");
    String drugAllergy = request.getParameter("drugAllergy");
    String isPregnancy = request.getParameter("isPregnancy");
    String pregnancy = request.getParameter("pregnancy");
    String onMedication = request.getParameter("onMedication");
    String habit = request.getParameter("habit");
    String smokeTime = request.getParameter("smokeTime");
    String smokeNum = request.getParameter("smokeNum");
    String drinkTime = request.getParameter("drinkTime");
    String drinkScale = request.getParameter("drinkScale");
    String odontoprisisDegree = request.getParameter("odontoprisisDegree");
    String odontoprisis = request.getParameter("odontoprisis");
    String chewingHabits = request.getParameter("chewingHabits");
    String Others = request.getParameter("Others");
    String LastToothExtractionTime = request.getParameter("LastToothExtractionTime");
    String HaveYouHadDenture = request.getParameter("HaveYouHadDenture");
    String ReasonsImplantDentures = request.getParameter("ReasonsImplantDentures");
    String PatientSignature = request.getParameter("PatientSignature");
    String PatientTime = request.getParameter("PatientTime");
    String doctorSignature = request.getParameter("doctorSignature");
    String doctorTime = request.getParameter("doctorTime");
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String createtime = df.format(new Date());
    
    AdviceNote adviceNote = new AdviceNote();
    
    adviceNote.setSEQ_ID(seq_id);
    adviceNote.setLcljNum(LcljNum);
    adviceNote.setLcljId(LcljId);
    adviceNote.setUsername(userName);
    adviceNote.setAgomphosTime(agomphosTime);
    adviceNote.setSymptom(symptom);
    adviceNote.setPlantTime(plantTime);
    adviceNote.setIsHypertension(isHypertension);
    adviceNote.setHypertension(hypertension);
    adviceNote.setIsTakeMedicie(isTakeMedicie);
    adviceNote.setIsControl(isControl);
    adviceNote.setPressure(pressure);
    adviceNote.setIsHearDiease(IsHearDiease);
    adviceNote.setHearDiease(hearDiease);
    adviceNote.setIsPrepareMedication(isPrepareMedication);
    adviceNote.setIsDiabetes(isDiabetes);
    adviceNote.setDiabetes(diabetes);
    adviceNote.setDietControl(dietControl);
    adviceNote.setIsDietControl(isDietControl);
    adviceNote.setIsInfarction(isInfarction);
    adviceNote.setInfarction(infarction);
    adviceNote.setIsBloodCoagulation(IsBloodCoagulation);
    adviceNote.setBloodCoagulation(bloodCoagulation);
    adviceNote.setAntiFreezing(antiFreezing);
    adviceNote.setAntiFreezingTime(antiFreezingTime);
    adviceNote.setIsAntiFreezing(isAntiFreezing);
    adviceNote.setIsHepatitisB(isHepatitisB);
    adviceNote.setHepatitisB(hepatitisB);
    adviceNote.setIsHepatitisC(IsHepatitisC);
    adviceNote.setHepatitisC(hepatitisC);
    adviceNote.setIsHIV(IsHIV);
    adviceNote.setHiv(Hiv);
    adviceNote.setIsYphilis(isYphilis);
    adviceNote.setSyphilis(syphilis);
    adviceNote.setIsCancer(IsCancer);
    adviceNote.setCancer(cancer);
    adviceNote.setIsMaxillofacial(IsMaxillofacial);
    adviceNote.setMaxillofacial(maxillofacial);
    adviceNote.setIsInflammation(isInflammation);
    adviceNote.setInflammation(inflammation);
    adviceNote.setIsPharmacy(IsPharmacy);
    adviceNote.setTreaTment(Treatment);
    adviceNote.setPharmacy(pharmacy);
    adviceNote.setIsDrugAbuse(isDrugAbuse);
    adviceNote.setDrugAbuse(drugAbuse);
    adviceNote.setIsPsychosis(isPsychosis);
    adviceNote.setPsychosis(psychosis);
    adviceNote.setIsMucousMembrane(IsMucousMembrane);
    adviceNote.setMucousMembrane(mucousMembrane);
    adviceNote.setIsGlucocorticoids(IsGlucocorticoids);
    adviceNote.setGlucocorticoids(glucocorticoids);
    adviceNote.setIsOtherDiseases(isOtherDiseases);
    adviceNote.setOtherDiseases(otherDiseases);
    adviceNote.setIsDrugAllergy(isDrugAllergy);
    adviceNote.setAllergicLength(allergicLength);
    adviceNote.setDrugAllergy(drugAllergy);
    adviceNote.setIsPharmacy(IsPharmacy);
    adviceNote.setPharmacy(pharmacy);
    adviceNote.setIsPregnancy(isPregnancy);
    adviceNote.setPregnancy(pregnancy);
    adviceNote.setOnMedication(onMedication);
    adviceNote.setHabit(habit);
    adviceNote.setSmokeNum(smokeNum);
    adviceNote.setSmokeTime(smokeTime);
    adviceNote.setDrinkTime(drinkTime);
    adviceNote.setDrinkScale(drinkScale);
    adviceNote.setOdontoprisisDegree(odontoprisisDegree);
    adviceNote.setOdontoprisis(odontoprisisDegree);
    adviceNote.setChewingHabits(chewingHabits);
    adviceNote.setOthers(Others);
    adviceNote.setLastToothExtractionTime(LastToothExtractionTime);
    adviceNote.setHaveYouHadDenture(HaveYouHadDenture);
    adviceNote.setReasonsImplantDentures(ReasonsImplantDentures);
    adviceNote.setPatientSignature(PatientSignature);
    adviceNote.setPatientTime(PatientTime);
    adviceNote.setDoctorSignature(doctorSignature);
    adviceNote.setDoctorTime(doctorTime);
    adviceNote.setCreatetime(createtime);
    adviceNote.setOdontoprisis(odontoprisis);
    try
    {
      this.dzblService.saveCaseHistory(adviceNote);
      YZUtility.DEAL_SUCCESS(null, "true", response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findCaseHistoryById.act"})
  public JSONObject findCaseHistoryById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      JSONObject caseHistory = this.dzblService.findCaseHistoryById(id);
      YZUtility.DEAL_SUCCESS(caseHistory, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/updateCaseHistoryById.act"})
  public String updateCaseHistoryById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    String LcljNum = request.getParameter("LcljNum");
    String userName = request.getParameter("username");
    String LcljId = request.getParameter("LcljId");
    String agomphosTime = request.getParameter("agomphosTime");
    String symptom = request.getParameter("symptom");
    String plantTime = request.getParameter("plantTime");
    String isHypertension = request.getParameter("isHypertension");
    String hypertension = request.getParameter("hypertension");
    String isTakeMedicie = request.getParameter("isTakeMedicie");
    String isControl = request.getParameter("isControl");
    String pressure = request.getParameter("pressure");
    String IsHearDiease = request.getParameter("IsHearDiease");
    String hearDiease = request.getParameter("hearDiease");
    String isPrepareMedication = request.getParameter("isPrepareMedication");
    String isDiabetes = request.getParameter("isDiabetes");
    String diabetes = request.getParameter("diabetes");
    String dietControl = request.getParameter("dietControl");
    String isDietControl = request.getParameter("isDietControl");
    String isInfarction = request.getParameter("isInfarction");
    String infarction = request.getParameter("infarction");
    String IsBloodCoagulation = request.getParameter("IsBloodCoagulation");
    String bloodCoagulation = request.getParameter("bloodCoagulation");
    String isAntiFreezing = request.getParameter("isAntiFreezing");
    String antiFreezing = request.getParameter("antiFreezing");
    String antiFreezingTime = request.getParameter("antiFreezingTime");
    String isHepatitisB = request.getParameter("isHepatitisB");
    String hepatitisB = request.getParameter("hepatitisB");
    String IsHepatitisC = request.getParameter("IsHepatitisC");
    String hepatitisC = request.getParameter("hepatitisC");
    String IsHIV = request.getParameter("IsHIV");
    String Hiv = request.getParameter("Hiv");
    String isYphilis = request.getParameter("isYphilis");
    String syphilis = request.getParameter("syphilis");
    String IsCancer = request.getParameter("IsCancer");
    String cancer = request.getParameter("cancer");
    String IsMaxillofacial = request.getParameter("IsMaxillofacial");
    String maxillofacial = request.getParameter("maxillofacial");
    String isInflammation = request.getParameter("isInflammation");
    String inflammation = request.getParameter("inflammation");
    String IsPharmacy = request.getParameter("IsPharmacy");
    String Treatment = request.getParameter("Treatment");
    String pharmacy = request.getParameter("pharmacy");
    String isDrugAbuse = request.getParameter("isDrugAbuse");
    String drugAbuse = request.getParameter("drugAbuse");
    String isPsychosis = request.getParameter("isPsychosis");
    String psychosis = request.getParameter("psychosis");
    String IsMucousMembrane = request.getParameter("IsMucousMembrane");
    String mucousMembrane = request.getParameter("mucousMembrane");
    String IsGlucocorticoids = request.getParameter("IsGlucocorticoids");
    String glucocorticoids = request.getParameter("glucocorticoids");
    String isOtherDiseases = request.getParameter("isOtherDiseases");
    String otherDiseases = request.getParameter("otherDiseases");
    String isDrugAllergy = request.getParameter("isDrugAllergy");
    String allergicLength = request.getParameter("allergicLength");
    String drugAllergy = request.getParameter("drugAllergy");
    String isPregnancy = request.getParameter("isPregnancy");
    String pregnancy = request.getParameter("pregnancy");
    String onMedication = request.getParameter("onMedication");
    String habit = request.getParameter("habit");
    String smokeTime = request.getParameter("smokeTime");
    String smokeNum = request.getParameter("smokeNum");
    String drinkTime = request.getParameter("drinkTime");
    String drinkScale = request.getParameter("drinkScale");
    String odontoprisisDegree = request.getParameter("odontoprisisDegree");
    String odontoprisis = request.getParameter("odontoprisis");
    String chewingHabits = request.getParameter("chewingHabits");
    String Others = request.getParameter("Others");
    String LastToothExtractionTime = request.getParameter("LastToothExtractionTime");
    String HaveYouHadDenture = request.getParameter("HaveYouHadDenture");
    String ReasonsImplantDentures = request.getParameter("ReasonsImplantDentures");
    String PatientSignature = request.getParameter("PatientSignature");
    String PatientTime = request.getParameter("PatientTime");
    String doctorSignature = request.getParameter("doctorSignature");
    String doctorTime = request.getParameter("doctorTime");
    AdviceNote adviceNote = new AdviceNote();
    adviceNote.setSEQ_ID(id);
    adviceNote.setLcljNum(LcljNum);
    adviceNote.setLcljId(LcljId);
    adviceNote.setUsername(userName);
    adviceNote.setAgomphosTime(agomphosTime);
    adviceNote.setSymptom(symptom);
    adviceNote.setPlantTime(plantTime);
    adviceNote.setIsHypertension(isHypertension);
    adviceNote.setHypertension(hypertension);
    adviceNote.setIsTakeMedicie(isTakeMedicie);
    adviceNote.setIsControl(isControl);
    adviceNote.setPressure(pressure);
    adviceNote.setIsHearDiease(IsHearDiease);
    adviceNote.setHearDiease(hearDiease);
    adviceNote.setIsPrepareMedication(isPrepareMedication);
    adviceNote.setIsDiabetes(isDiabetes);
    adviceNote.setDiabetes(diabetes);
    adviceNote.setDietControl(dietControl);
    adviceNote.setIsDietControl(isDietControl);
    adviceNote.setIsInfarction(isInfarction);
    adviceNote.setInfarction(infarction);
    adviceNote.setIsBloodCoagulation(IsBloodCoagulation);
    adviceNote.setBloodCoagulation(bloodCoagulation);
    adviceNote.setAntiFreezing(antiFreezing);
    adviceNote.setAntiFreezingTime(antiFreezingTime);
    adviceNote.setIsAntiFreezing(isAntiFreezing);
    adviceNote.setIsHepatitisB(isHepatitisB);
    adviceNote.setHepatitisB(hepatitisB);
    adviceNote.setIsHepatitisC(IsHepatitisC);
    adviceNote.setHepatitisC(hepatitisC);
    adviceNote.setIsHIV(IsHIV);
    adviceNote.setHiv(Hiv);
    adviceNote.setIsYphilis(isYphilis);
    adviceNote.setSyphilis(syphilis);
    adviceNote.setIsCancer(IsCancer);
    adviceNote.setCancer(cancer);
    adviceNote.setIsMaxillofacial(IsMaxillofacial);
    adviceNote.setMaxillofacial(maxillofacial);
    adviceNote.setIsInflammation(isInflammation);
    adviceNote.setInflammation(inflammation);
    adviceNote.setIsPharmacy(IsPharmacy);
    adviceNote.setTreaTment(Treatment);
    adviceNote.setPharmacy(pharmacy);
    adviceNote.setIsDrugAbuse(isDrugAbuse);
    adviceNote.setDrugAbuse(drugAbuse);
    adviceNote.setIsPsychosis(isPsychosis);
    adviceNote.setPsychosis(psychosis);
    adviceNote.setIsMucousMembrane(IsMucousMembrane);
    adviceNote.setMucousMembrane(mucousMembrane);
    adviceNote.setIsGlucocorticoids(IsGlucocorticoids);
    adviceNote.setGlucocorticoids(glucocorticoids);
    adviceNote.setIsOtherDiseases(isOtherDiseases);
    adviceNote.setOtherDiseases(otherDiseases);
    adviceNote.setIsDrugAllergy(isDrugAllergy);
    adviceNote.setAllergicLength(allergicLength);
    adviceNote.setDrugAllergy(drugAllergy);
    adviceNote.setIsPharmacy(IsPharmacy);
    adviceNote.setPharmacy(pharmacy);
    adviceNote.setIsPregnancy(isPregnancy);
    adviceNote.setPregnancy(pregnancy);
    adviceNote.setOnMedication(onMedication);
    adviceNote.setHabit(habit);
    adviceNote.setSmokeNum(smokeNum);
    adviceNote.setSmokeTime(smokeTime);
    adviceNote.setDrinkTime(drinkTime);
    adviceNote.setDrinkScale(drinkScale);
    adviceNote.setOdontoprisisDegree(odontoprisisDegree);
    adviceNote.setOdontoprisis(odontoprisisDegree);
    adviceNote.setChewingHabits(chewingHabits);
    adviceNote.setOthers(Others);
    adviceNote.setLastToothExtractionTime(LastToothExtractionTime);
    adviceNote.setHaveYouHadDenture(HaveYouHadDenture);
    adviceNote.setReasonsImplantDentures(ReasonsImplantDentures);
    adviceNote.setPatientSignature(PatientSignature);
    adviceNote.setPatientTime(PatientTime);
    adviceNote.setDoctorSignature(doctorSignature);
    adviceNote.setDoctorTime(doctorTime);
    adviceNote.setCreatetime(YZUtility.getCurDateTimeStr());
    adviceNote.setOdontoprisis(odontoprisis);
    try
    {
      this.dzblService.updateCaseHistoryById(adviceNote);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/deleteCaseHistoryById.act"})
  public String deleteCaseHistoryById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      this.dzblService.deleteCaseHistory(id);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/saveFamiliarBook.act"})
  public String saveFamiliarBook(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String SEQ_ID = YZUtility.getUUID();
    String LcljId = request.getParameter("LcljId");
    String LcljNum = request.getParameter("LcljNum");
    String plantingSystem = request.getParameter("plantingSystem");
    String modelNumber = request.getParameter("modelNumber");
    String upleftToothBitOne = request.getParameter("upleftToothBitOne");
    String uperRightToothBitOne = request.getParameter("uperRightToothBitOne");
    String leftLowerToothBitOne = request.getParameter("leftLowerToothBitOne");
    String lowRightToothBitOne = request.getParameter("lowRightToothBitOne");
    String upleftToothBitTwo = request.getParameter("upleftToothBitTwo");
    String uperRightToothBitTwo = request.getParameter("uperRightToothBitTwo");
    String leftLowerToothBitTwo = request.getParameter("leftLowerToothBitTwo");
    String lowRightToothBitTwo = request.getParameter("lowRightToothBitTwo");
    String assistOperation = request.getParameter("assistOperation");
    String remarks = request.getParameter("remarks");
    String PatientSignature = request.getParameter("PatientSignature");
    String PatientTime = request.getParameter("patientTime");
    String doctorSignature = request.getParameter("doctorSignature");
    String doctorTime = request.getParameter("doctorTime");
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String createtime = df.format(new Date());
    String fixedDenture = request.getParameter("fixedDenture");
    String removableDenture = request.getParameter("removableDenture");
    FamiliarBook familiarBook = new FamiliarBook();
    familiarBook.setSEQ_ID(SEQ_ID);
    if (YZUtility.isNotNullOrEmpty(fixedDenture)) {
      familiarBook.setFixedDenture(fixedDenture);
    }
    if (YZUtility.isNotNullOrEmpty(removableDenture)) {
      familiarBook.setRemovableDenture(removableDenture);
    }
    if ((LcljId != null) && (!LcljId.equals(""))) {
      familiarBook.setLcljId(LcljId);
    }
    if ((LcljNum != null) && (!LcljNum.equals(""))) {
      familiarBook.setLcljNum(LcljNum);
    }
    if ((plantingSystem != null) && (!plantingSystem.equals(""))) {
      familiarBook.setPlantingSystem(plantingSystem);
    }
    if ((modelNumber != null) && (!modelNumber.equals(""))) {
      familiarBook.setModelNumber(modelNumber);
    }
    if ((upleftToothBitOne != null) && (!upleftToothBitOne.equals(""))) {
      familiarBook.setUpleftToothBitOne(upleftToothBitOne);
    }
    if ((uperRightToothBitOne != null) && (!uperRightToothBitOne.equals(""))) {
      familiarBook.setUperRightToothBitOne(uperRightToothBitOne);
    }
    if ((leftLowerToothBitOne != null) && (!leftLowerToothBitOne.equals(""))) {
      familiarBook.setLeftLowerToothBitOne(leftLowerToothBitOne);
    }
    if ((lowRightToothBitOne != null) && (!lowRightToothBitOne.equals(""))) {
      familiarBook.setLowRightToothBitOne(lowRightToothBitOne);
    }
    if ((upleftToothBitTwo != null) && (!upleftToothBitTwo.equals(""))) {
      familiarBook.setUpleftToothBitTwo(upleftToothBitTwo);
    }
    if ((uperRightToothBitTwo != null) && (!uperRightToothBitTwo.equals(""))) {
      familiarBook.setUperRightToothBitTwo(uperRightToothBitTwo);
    }
    if ((leftLowerToothBitTwo != null) && (!leftLowerToothBitTwo.equals(""))) {
      familiarBook.setLeftLowerToothBitTwo(leftLowerToothBitTwo);
    }
    if ((lowRightToothBitTwo != null) && (!lowRightToothBitTwo.equals(""))) {
      familiarBook.setLowRightToothBitTwo(lowRightToothBitTwo);
    }
    if ((assistOperation != null) && (!assistOperation.equals(""))) {
      familiarBook.setAssistOperation(assistOperation);
    }
    if ((remarks != null) && (!remarks.equals(""))) {
      familiarBook.setRemarks(remarks);
    }
    if ((PatientSignature != null) && (!PatientSignature.equals(""))) {
      familiarBook.setPatientSignature(PatientSignature);
    }
    if (PatientTime != "") {
      familiarBook.setPatientTime(PatientTime);
    }
    if ((doctorSignature != null) && (!doctorSignature.equals(""))) {
      familiarBook.setDoctorSignature(doctorSignature);
    }
    if (doctorTime != "") {
      familiarBook.setDoctorTime(doctorTime);
    }
    familiarBook.setCreatetime(createtime);
    try
    {
      JSONObject json = this.dzblService.findFamiliarBook(LcljId);
      if (json == null) {
        this.dzblService.saveFamiliarBook(familiarBook);
      } else {
        throw new Exception("已保存种植同意书…………");
      }
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findFamiliarBookById.act"})
  public JSONObject findFamiliarBookById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      JSONObject json = this.dzblService.findFamiliarBook(id);
      YZUtility.DEAL_SUCCESS(json, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/updateFamiliarBookById.act"})
  public String updateFamiliarBookById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("seqId");
    

    String plantingSystem = request.getParameter("plantingSystem");
    String modelNumber = request.getParameter("modelNumber");
    String upleftToothBitOne = request.getParameter("upleftToothBitOne");
    String uperRightToothBitOne = request.getParameter("uperRightToothBitOne");
    String leftLowerToothBitOne = request.getParameter("leftLowerToothBitOne");
    String lowRightToothBitOne = request.getParameter("lowRightToothBitOne");
    String upleftToothBitTwo = request.getParameter("upleftToothBitTwo");
    String uperRightToothBitTwo = request.getParameter("uperRightToothBitTwo");
    String leftLowerToothBitTwo = request.getParameter("leftLowerToothBitTwo");
    String lowRightToothBitTwo = request.getParameter("lowRightToothBitTwo");
    String assistOperation = request.getParameter("assistOperation");
    String remarks = request.getParameter("remarks");
    String PatientSignature = request.getParameter("PatientSignature");
    String patientTime = request.getParameter("patientTime");
    String doctorSignature = request.getParameter("doctorSignature");
    String doctorTime = request.getParameter("doctorTime");
    String createtime = request.getParameter("createtime");
    String fixedDenture = request.getParameter("fixedDenture");
    String removableDenture = request.getParameter("removableDenture");
    FamiliarBook familiarBook = new FamiliarBook();
    familiarBook.setSEQ_ID(id);
    if (YZUtility.isNotNullOrEmpty(removableDenture)) {
      familiarBook.setRemovableDenture(removableDenture);
    }
    if (YZUtility.isNotNullOrEmpty(fixedDenture)) {
      familiarBook.setFixedDenture(fixedDenture);
    }
    if ((!plantingSystem.equals(null)) && (!plantingSystem.equals(""))) {
      familiarBook.setPlantingSystem(plantingSystem);
    }
    if ((!modelNumber.equals(null)) && (!modelNumber.equals(""))) {
      familiarBook.setModelNumber(modelNumber);
    }
    familiarBook.setUpleftToothBitOne(upleftToothBitOne);
    familiarBook.setUperRightToothBitOne(uperRightToothBitOne);
    familiarBook.setLeftLowerToothBitOne(leftLowerToothBitOne);
    familiarBook.setLowRightToothBitOne(lowRightToothBitOne);
    familiarBook.setUpleftToothBitTwo(upleftToothBitTwo);
    familiarBook.setUperRightToothBitTwo(uperRightToothBitTwo);
    familiarBook.setLeftLowerToothBitTwo(leftLowerToothBitTwo);
    familiarBook.setLowRightToothBitTwo(lowRightToothBitTwo);
    if ((!assistOperation.equals(null)) && (!assistOperation.equals(""))) {
      familiarBook.setAssistOperation(assistOperation);
    }
    if ((!remarks.equals(null)) && (!remarks.equals(""))) {
      familiarBook.setRemarks(remarks);
    }
    if ((!PatientSignature.equals(null)) && (!plantingSystem.equals(""))) {
      familiarBook.setPatientSignature(PatientSignature);
    }
    if ((!patientTime.equals(null)) && (!patientTime.equals(""))) {
      familiarBook.setPatientTime(patientTime);
    }
    if ((!doctorSignature.equals(null)) && (!doctorSignature.equals(""))) {
      familiarBook.setDoctorSignature(doctorSignature);
    }
    if ((!doctorTime.equals(null)) && (!doctorTime.equals(""))) {
      familiarBook.setDoctorTime(doctorTime);
    }
    if ((!createtime.equals(null)) && (!createtime.equals(""))) {
      familiarBook.setCreatetime(createtime);
    }
    try
    {
      this.dzblService.updateFamiliarBook(familiarBook);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/deleteFamiliarBookById.act"})
  public String deleteFamiliarBookById(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    try
    {
      this.dzblService.deleteFamiliarBook(id);
      YZUtility.DEAL_SUCCESS(null, null, response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/saveLocatorFamiliar.act"})
  public String saveLocatorFamiliar(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String uuid = YZUtility.getUUID();
    String lcljId = request.getParameter("LcljId");
    String lcljNum = request.getParameter("LcljNum");
    String name = request.getParameter("name");
    String age = request.getParameter("age");
    String sex = request.getParameter("sex");
    String address = request.getParameter("address");
    String phone = request.getParameter("phone");
    String allergy = request.getParameter("allergy");
    String diagnose = request.getParameter("diagnose");
    String treatmentParts = request.getParameter("treatmentParts");
    String treatmentTime = request.getParameter("treatmentTime");
    String profession = request.getParameter("profession");
    String classify = request.getParameter("classify");
    String doctorSignature = request.getParameter("doctorSignature");
    String doctorTime = request.getParameter("doctorTime");
    String patientSignature = request.getParameter("patientSignature");
    String patientTime = request.getParameter("patientTime");
    String creatTime = request.getParameter("createtime");
    
    LocatorFamiliar locatorFamiliar = new LocatorFamiliar();
    locatorFamiliar.setSeqId(uuid);
    locatorFamiliar.setLcljId(lcljId);
    locatorFamiliar.setLcljNum(lcljNum);
    locatorFamiliar.setName(name);
    locatorFamiliar.setAge(age);
    locatorFamiliar.setSex(sex);
    locatorFamiliar.setAddress(address);
    locatorFamiliar.setPhone(phone);
    locatorFamiliar.setAllergy(allergy);
    locatorFamiliar.setDiagnose(diagnose);
    locatorFamiliar.setTreatmentParts(treatmentParts);
    locatorFamiliar.setTreatmentTime(treatmentTime);
    locatorFamiliar.setProfession(profession);
    locatorFamiliar.setClassify(classify);
    locatorFamiliar.setDoctorSignature(doctorSignature);
    locatorFamiliar.setDoctorTime(doctorTime);
    locatorFamiliar.setPatientSignature(patientSignature);
    locatorFamiliar.setPatientTime(patientTime);
    locatorFamiliar.setCreattime(creatTime);
    Map<String, Object> map = new HashMap();
    map.put("LcljId", lcljId);
    map.put("classify", classify);
    try
    {
      JSONObject findLocatorFamiliar = this.dzblService.findLocatorFamiliar(map);
      if (findLocatorFamiliar != null)
      {
        if ((findLocatorFamiliar != null) && (findLocatorFamiliar.get("classify").equals("0"))) {
          throw new Exception("Locator种植知情书已存在…………………………");
        }
        throw new Exception("拔牙知情书已存在…………………………");
      }
      int object = this.dzblService.saveLocatorFamiliar(locatorFamiliar).intValue();
      YZUtility.DEAL_SUCCESS(null, "save ok!", response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/findLocatorFamiliar.act"})
  public String findLocatorFamiliar(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String lcljId = request.getParameter("id");
    String classify = request.getParameter("classify");
    Map<String, Object> map = new HashMap();
    map.put("lcljId", lcljId);
    map.put("classify", classify);
    try
    {
      JSONObject findLocatorFamiliar = this.dzblService.findLocatorFamiliar(map);
      YZUtility.DEAL_SUCCESS(findLocatorFamiliar, "select ok!", response, this.logger);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
  
  @RequestMapping({"/updateLocatorFamiliar.act"})
  public String updateLocatorFamiliar(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = request.getParameter("id");
    String lcljId = request.getParameter("LcljId");
    String lcljNum = request.getParameter("LcljNum");
    String name = request.getParameter("name");
    String age = request.getParameter("age");
    String sex = request.getParameter("sex");
    String address = request.getParameter("address");
    String phone = request.getParameter("phone");
    String allergy = request.getParameter("allergy");
    String diagnose = request.getParameter("diagnose");
    String treatmentParts = request.getParameter("treatmentParts");
    String treatmentTime = request.getParameter("treatmentTime");
    String profession = request.getParameter("profession");
    String classify = request.getParameter("classify");
    String doctorSignature = request.getParameter("doctorSignature");
    String doctorTime = request.getParameter("doctorTime");
    String patientSignature = request.getParameter("patientSignature");
    String patientTime = request.getParameter("patientTime");
    String creatTime = request.getParameter("createtime");
    
    LocatorFamiliar locatorFamiliar = new LocatorFamiliar();
    locatorFamiliar.setSeqId(id);
    if ((lcljId != null) && (!lcljId.equals(""))) {
      locatorFamiliar.setLcljId(lcljId);
    }
    if ((lcljNum != null) && (!lcljNum.equals(""))) {
      locatorFamiliar.setLcljNum(lcljNum);
    }
    if ((name != null) && (!name.equals(""))) {
      locatorFamiliar.setName(name);
    }
    if ((age != null) && (!age.equals(""))) {
      locatorFamiliar.setAge(age);
    }
    if ((sex != null) && (!sex.equals(""))) {
      locatorFamiliar.setSex(sex);
    }
    if ((address != null) && (!address.equals(""))) {
      locatorFamiliar.setAddress(address);
    }
    if ((phone != null) && (!phone.equals(""))) {
      locatorFamiliar.setPhone(phone);
    }
    if ((allergy != null) && (!allergy.equals(""))) {
      locatorFamiliar.setAllergy(allergy);
    }
    if ((diagnose != null) && (!diagnose.equals(""))) {
      locatorFamiliar.setDiagnose(diagnose);
    }
    if ((treatmentParts != null) && (!treatmentParts.equals(""))) {
      locatorFamiliar.setTreatmentParts(treatmentParts);
    }
    if ((treatmentTime != null) && (!treatmentTime.equals(""))) {
      locatorFamiliar.setTreatmentTime(treatmentTime);
    }
    if ((profession != null) && (!profession.equals(""))) {
      locatorFamiliar.setProfession(profession);
    }
    if ((classify != null) && (!classify.equals(""))) {
      locatorFamiliar.setClassify(classify);
    }
    if ((doctorSignature != null) && (!doctorSignature.equals(""))) {
      locatorFamiliar.setDoctorSignature(doctorSignature);
    }
    if ((doctorTime != null) && (!doctorTime.equals(""))) {
      locatorFamiliar.setDoctorTime(doctorTime);
    }
    if ((patientSignature != null) && (!patientSignature.equals(""))) {
      locatorFamiliar.setPatientSignature(patientSignature);
    }
    if ((patientTime != null) && (!patientTime.equals(""))) {
      locatorFamiliar.setPatientTime(patientTime);
    }
    if ((creatTime != null) && (!creatTime.equals(""))) {
      locatorFamiliar.setCreattime(creatTime);
    }
    try
    {
      this.dzblService.updateLocatorFamiliar(locatorFamiliar);
      YZUtility.DEAL_SUCCESS_VALID(true, response);
    }
    catch (Exception e)
    {
      YZUtility.DEAL_ERROR(null, false, e, response, this.logger);
    }
    return null;
  }
}
