package com.hudh.zzbl.entity;

import java.io.Serializable;

/**
  * 主诉及既往病史
  * @ClassName: AdviceNote
  * @Description: TODO(主诉及既往病史:用于研讨)
  * @author duoyh
  * @dateTime 2019年5月1日 下午1:47:40
  *
  */
public class AdviceNote implements Serializable{

	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	    
	private static final long serialVersionUID = 1L;
	
	/**
	 * 病历ID
	 */
	private String SEQ_ID;
	/**
	 * 临床路径编号---
	 */
	private String LcljNum;
	/**
	 * 临床路径ID---
	 */
	private String LcljId;
	/**
	 * 患者姓名
	 */
	private String username;
	/**
	 * 缺牙时间
	 */
	private String agomphosTime;
	/**
	 * 牙症状 : 1.牙缺失  2.牙松动   3.牙腐烂   4.牙折断
	 */
	private String symptom;
	/**
	 * 要求种植修复时间
	 */
	private String plantTime;
	/**
	 * 是否有高血压： 1.是  0.否 
	 */
	private String isHypertension;
	/**
	 * 患高血压年限
	 */
	private String hypertension;
	/**
	 * 是否有在服药： 1.是  0.否 
	 */
	private String isTakeMedicie;
	/**
	 * 是否控制： 1.是  0.否 
	 */
	private String isControl;
	/**
	 * 当前血压
	 */
	private String pressure;
	/**
	 * 是否患有心脏病（心绞痛、心衰）： 1.是  0.否 
	 */
	private String IsHearDiease;
	/**
	 * 患心脏病年限
	 */
	private String hearDiease;
	/**
	 * 是否有长期备药在身边： 1.是  0.否 
	 */
	private String isPrepareMedication;
	/**
	 * 是否患有糖尿病： 1.是  0.否 
	 */
	private String isDiabetes;
	/**
	 * 患糖尿病年限
	 */
	private String diabetes;
	/**
	 * 控制饮食的方式： 1.饮食 2.口服药 3.针剂
	 */
	private String dietControl;
	/**
	 * 是否控制： 1.是  0.否 
	 */
	private String isDietControl;
	/**
	 * 近6个月是否有做过心瓣膜置换术或发生过心梗： 1.是  0.否 
	 */
	private String isInfarction;
	/**
	 * 发病或手术时限   单位：月
	 */
	private String infarction;
	/**
	 * 凝血功能不足性疾病（血友病、再障、血小板减少症、急性白血病、粒细胞缺乏症）： 1.是  0.否 
	 */
	private String IsBloodCoagulation;
	/**
	 * 患病年限    单位：年
	 */
	private String bloodCoagulation;
	/**
	 * 服用抗凝药物或其他可以导致凝血功能障碍的药物（药名）： 1.是  0.否 
	 */
	private String isAntiFreezing;
	/**
	 * 所服药名
	 */
	private String antiFreezing;
	/**
	 * 服药年限    单位：年
	 */
	private String antiFreezingTime;
	/**
	 * 是否患有乙肝： 1.是  0.否 
	 */
	private String isHepatitisB;
	/**
	 * 患病年限   单位：年
	 */
	private String hepatitisB;
	/**
	 * 是否患有丙肝： 1.是  0.否
	 */
	private String IsHepatitisC;
	/**
	 * 患病年限   单位：年
	 */
	private String hepatitisC;
	/**
	 * 是否患有HIV： 1.是  0.否
	 */
	private String IsHIV;
	/**
	 * 患病年限    单位：年
	 */
	private String Hiv;
	/**
	 * 是否患有梅毒： 1.是  0.否
	 */
	private String isYphilis;
	/**
	 * 患病年限      单位：年
	 */
	private String syphilis;
	/**
	 * 是否患癌症（恶性肿瘤）： 1.是  0.否
	 */
	private String IsCancer;
	/**
	 * 患病年限     单位：年
	 */
	private String cancer;
	/**
	 * 是否有过颌面部放疗术 ： 1.是  0.否
	 */
	private String IsMaxillofacial;
	/**
	 * 间隔年限      单位：年
	 */
	private String maxillofacial;
	/**
	 * 是否患有感染急性炎症 ：1.是  0.否	 
	 */
	private String isInflammation;
	/**
	 * 患病年限
	 */
	private String inflammation;
	/**
	 * 是否有骨质酥松用药 1.是  0.否
	 */
	private String IsPharmacy;
	
	/**
	 * 用药方式 0.静脉 1.口服
	 */
	private String treaTment;
	/**
	 * 用药年限
	 */
	private String pharmacy;
	/**
	 * 是否吸毒 1.是  0.否
	 */
	private String isDrugAbuse;
	/**
	 * 吸毒年限
	 */
	private String drugAbuse;
	/**
	 * 是否患有心理、精神障碍： 1.是  0.否
	 */
	private String isPsychosis;
	/**
	 * 患病年限
	 */
	private String psychosis;
	/**
	 * 是否患有皮肤黏膜病变： 1.是 0.否
	 */
	private String IsMucousMembrane;
	/**
	 * 患病年限
	 */
	private String mucousMembrane;
	/**
	 * 长期应用糖皮质激素  ： 1.是  0.否
	 */
	private String IsGlucocorticoids;
	/**
	 * 使用激素年限
	 */
	private String glucocorticoids;
	/**
	 * 是否患有其他疾病   ： 1.是  0.否
	 */
	private String isOtherDiseases;
	/**
	 * 患病年限
	 */
	private String otherDiseases;
	/**
	 * 是否药物过敏    ： 1.是  0.否
	 */
	private String isDrugAllergy;
	/**
	 * 过敏史时间
	 */
	private String allergicLength;
	/**
	 * 过敏药物
	 */
	private String drugAllergy;
	/**
	 * 是否怀孕    ： 1.是  0.否
	 */
	private String isPregnancy;
	/**
	 * 受孕时间 单位：月
	 */
	private String pregnancy;
	/**
	 * 正在服用药物
	 */
	private String onMedication;
	/**
	 * 生活习惯
	 */
	private String habit;
	/**
	 * 吸烟年限
	 */
	private String  smokeTime;
	/**
	 * 抽烟数量    支/日
	 */
	private String  smokeNum;	
	/**
	 * 饮酒年限
	 */
	private String  drinkTime;	
	/**
	 * 饮酒量    1.少量 2.中量 3.大量
	 */
	private String  drinkScale;	
	/**
	 * 磨牙年数
	 */
	private String  odontoprisis;	
	/**
	 * 磨牙频率 1.频繁 2.偶尔
	 */
	private String  odontoprisisDegree;	
	/**
	 * 咀嚼习惯 
	 * 单1/单2： 单侧  1.左  2.右
	 * 双： 双侧
	 */
	private String chewingHabits;
	/**
	 * 其他
	 */
	private String Others;
	/**
	 * 什么原因导致牙缺失？ 龋齿/牙周炎/外伤……
	 * 最近一次拔牙距今时间
	 */
	private String LastToothExtractionTime;
	/**
	 * 是否做义齿修复 1.是  0.否
	 */
	private String  HaveYouHadDenture;	
	/**
	 * 选择做义齿是为了什么:
	 * 1. 希望把义齿作为固定的
	 * 2.改善义齿固位稳定
	 * 3.改善义齿咀嚼效率 
	 * 4.改善义齿美观效果
	 * 5.改善发音
	 */
	private String ReasonsImplantDentures;
	/**
	 * 患者签字
	 */
	private String PatientSignature;
	/**
	 * 患者签字时间
	 */
	private String PatientTime;
	/**
	 * 医生签字
	 */
	private String doctorSignature;
	/**
	 * 医生签字时间
	 */
	private String doctorTime;
	/**
	 * 创建时间
	 */
	private String createtime;
	/**
	 * 创建人
	 */
	private String createuser;
	/**
	 * 选中状态
	 */
	private String status;
	/**
	 * 高血压常用药物
	 */
	private String hypertensionmedicine;
	/**
	 * 心脏病常用药
	 */
	private String heardieasemedicine;
	/**
	 * 糖尿病口服常用药
	 */
	private String diabetesoralmedicine;
	/**
	 * 糖尿病注射常用药
	 */
	private String diabetesinjectionmedicine;
	/**
	 * 服用抗凝药物常用药物
	 */
	private String antifreezingmedicine;
	/**
	 * 骨质疏松常用药物
	 */
	private String pharmacymedicine;
	/**
	 * 长期应用糖皮质激素常用药
	 */
	private String glucocorticoidsmedicine;
	/**  
	  * @Title:  getHypertensionmedicine <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getHypertensionmedicine() {
		return hypertensionmedicine;
	}
	/**  
	  * @Title:  setHypertensionmedicine <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setHypertensionmedicine(String hypertensionmedicine) {
		this.hypertensionmedicine = hypertensionmedicine;
	}
	/**  
	  * @Title:  getHeardieasemedicine <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getHeardieasemedicine() {
		return heardieasemedicine;
	}
	/**  
	  * @Title:  setHeardieasemedicine <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setHeardieasemedicine(String heardieasemedicine) {
		this.heardieasemedicine = heardieasemedicine;
	}
	/**  
	  * @Title:  getDiabetesoralmedicine <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDiabetesoralmedicine() {
		return diabetesoralmedicine;
	}
	/**  
	  * @Title:  setDiabetesoralmedicine <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDiabetesoralmedicine(String diabetesoralmedicine) {
		this.diabetesoralmedicine = diabetesoralmedicine;
	}
	/**  
	  * @Title:  getDiabetesinjectionmedicine <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDiabetesinjectionmedicine() {
		return diabetesinjectionmedicine;
	}
	/**  
	  * @Title:  setDiabetesinjectionmedicine <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDiabetesinjectionmedicine(String diabetesinjectionmedicine) {
		this.diabetesinjectionmedicine = diabetesinjectionmedicine;
	}
	/**  
	  * @Title:  getAntifreezingmedicine <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getAntifreezingmedicine() {
		return antifreezingmedicine;
	}
	/**  
	  * @Title:  setAntifreezingmedicine <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setAntifreezingmedicine(String antifreezingmedicine) {
		this.antifreezingmedicine = antifreezingmedicine;
	}
	/**  
	  * @Title:  getPharmacymedicine <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPharmacymedicine() {
		return pharmacymedicine;
	}
	/**  
	  * @Title:  setPharmacymedicine <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPharmacymedicine(String pharmacymedicine) {
		this.pharmacymedicine = pharmacymedicine;
	}
	/**  
	  * @Title:  getGlucocorticoidsmedicine <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getGlucocorticoidsmedicine() {
		return glucocorticoidsmedicine;
	}
	/**  
	  * @Title:  setGlucocorticoidsmedicine <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setGlucocorticoidsmedicine(String glucocorticoidsmedicine) {
		this.glucocorticoidsmedicine = glucocorticoidsmedicine;
	}
	/**
	 * 预留字段2
	 */
	private String reservedField2;
	/**
	 * 预留字段3
	 */
	private String reservedField3;
	/**  
	  * @Title:  getSEQ_ID <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	/**  
	  * @Title:  getLcljNum <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLcljNum() {
		return LcljNum;
	}
	/**  
	  * @Title:  setLcljNum <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLcljNum(String lcljNum) {
		LcljNum = lcljNum;
	}
	/**  
	  * @Title:  getLcljId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLcljId() {
		return LcljId;
	}
	/**  
	  * @Title:  setLcljId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLcljId(String lcljId) {
		LcljId = lcljId;
	}
	/**  
	  * @Title:  getUsername <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUsername() {
		return username;
	}
	/**  
	  * @Title:  setUsername <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUsername(String username) {
		this.username = username;
	}
	/**  
	  * @Title:  getAgomphosTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getAgomphosTime() {
		return agomphosTime;
	}
	/**  
	  * @Title:  setAgomphosTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setAgomphosTime(String agomphosTime) {
		this.agomphosTime = agomphosTime;
	}
	/**  
	  * @Title:  getSymptom <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSymptom() {
		return symptom;
	}
	/**  
	  * @Title:  setSymptom <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
	/**  
	  * @Title:  getPlantTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPlantTime() {
		return plantTime;
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
	/**  
	  * @Title:  setPlantTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPlantTime(String plantTime) {
		this.plantTime = plantTime;
	}
	/**  
	  * @Title:  getIsHypertension <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsHypertension() {
		return isHypertension;
	}
	/**  
	  * @Title:  setIsHypertension <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsHypertension(String isHypertension) {
		this.isHypertension = isHypertension;
	}
	/**  
	  * @Title:  getHypertension <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getHypertension() {
		return hypertension;
	}
	/**  
	  * @Title:  setHypertension <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setHypertension(String hypertension) {
		this.hypertension = hypertension;
	}
	/**  
	  * @Title:  getIsTakeMedicie <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsTakeMedicie() {
		return isTakeMedicie;
	}
	/**  
	  * @Title:  setIsTakeMedicie <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsTakeMedicie(String isTakeMedicie) {
		this.isTakeMedicie = isTakeMedicie;
	}
	/**  
	  * @Title:  getIsControl <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsControl() {
		return isControl;
	}
	/**  
	  * @Title:  setIsControl <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsControl(String isControl) {
		this.isControl = isControl;
	}
	/**  
	  * @Title:  getPressure <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPressure() {
		return pressure;
	}
	/**  
	  * @Title:  setPressure <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}
	/**  
	  * @Title:  getIsHearDiease <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsHearDiease() {
		return IsHearDiease;
	}
	/**  
	  * @Title:  setIsHearDiease <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsHearDiease(String isHearDiease) {
		IsHearDiease = isHearDiease;
	}
	/**  
	  * @Title:  getHearDiease <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getHearDiease() {
		return hearDiease;
	}
	/**  
	  * @Title:  setHearDiease <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setHearDiease(String hearDiease) {
		this.hearDiease = hearDiease;
	}
	/**  
	  * @Title:  getIsPrepareMedication <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsPrepareMedication() {
		return isPrepareMedication;
	}
	/**  
	  * @Title:  setIsPrepareMedication <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsPrepareMedication(String isPrepareMedication) {
		this.isPrepareMedication = isPrepareMedication;
	}
	/**  
	  * @Title:  getIsDiabetes <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsDiabetes() {
		return isDiabetes;
	}
	/**  
	  * @Title:  setIsDiabetes <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsDiabetes(String isDiabetes) {
		this.isDiabetes = isDiabetes;
	}
	/**  
	  * @Title:  getDiabetes <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDiabetes() {
		return diabetes;
	}
	/**  
	  * @Title:  setDiabetes <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDiabetes(String diabetes) {
		this.diabetes = diabetes;
	}
	/**  
	  * @Title:  getDietControl <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDietControl() {
		return dietControl;
	}
	/**  
	  * @Title:  setDietControl <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDietControl(String dietControl) {
		this.dietControl = dietControl;
	}
	/**  
	  * @Title:  getIsDietControl <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsDietControl() {
		return isDietControl;
	}
	/**  
	  * @Title:  setIsDietControl <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsDietControl(String isDietControl) {
		this.isDietControl = isDietControl;
	}
	/**  
	  * @Title:  getIsInfarction <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsInfarction() {
		return isInfarction;
	}
	/**  
	  * @Title:  setIsInfarction <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsInfarction(String isInfarction) {
		this.isInfarction = isInfarction;
	}
	/**  
	  * @Title:  getInfarction <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getInfarction() {
		return infarction;
	}
	/**  
	  * @Title:  setInfarction <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setInfarction(String infarction) {
		this.infarction = infarction;
	}
	/**  
	  * @Title:  getIsBloodCoagulation <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsBloodCoagulation() {
		return IsBloodCoagulation;
	}
	/**  
	  * @Title:  setIsBloodCoagulation <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsBloodCoagulation(String isBloodCoagulation) {
		IsBloodCoagulation = isBloodCoagulation;
	}
	/**  
	  * @Title:  getBloodCoagulation <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getBloodCoagulation() {
		return bloodCoagulation;
	}
	/**  
	  * @Title:  setBloodCoagulation <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setBloodCoagulation(String bloodCoagulation) {
		this.bloodCoagulation = bloodCoagulation;
	}
	/**  
	  * @Title:  getIsAntiFreezing <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsAntiFreezing() {
		return isAntiFreezing;
	}
	/**  
	  * @Title:  setIsAntiFreezing <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsAntiFreezing(String isAntiFreezing) {
		this.isAntiFreezing = isAntiFreezing;
	}
	/**  
	  * @Title:  getAntiFreezing <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getAntiFreezing() {
		return antiFreezing;
	}
	/**  
	  * @Title:  setAntiFreezing <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setAntiFreezing(String antiFreezing) {
		this.antiFreezing = antiFreezing;
	}
	/**  
	  * @Title:  getAntiFreezingTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getAntiFreezingTime() {
		return antiFreezingTime;
	}
	/**  
	  * @Title:  setAntiFreezingTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setAntiFreezingTime(String antiFreezingTime) {
		this.antiFreezingTime = antiFreezingTime;
	}
	/**  
	  * @Title:  getIsHepatitisB <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsHepatitisB() {
		return isHepatitisB;
	}
	/**  
	  * @Title:  setIsHepatitisB <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsHepatitisB(String isHepatitisB) {
		this.isHepatitisB = isHepatitisB;
	}
	/**  
	  * @Title:  getHepatitisB <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getHepatitisB() {
		return hepatitisB;
	}
	/**  
	  * @Title:  setHepatitisB <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setHepatitisB(String hepatitisB) {
		this.hepatitisB = hepatitisB;
	}
	/**  
	  * @Title:  getIsHepatitisC <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsHepatitisC() {
		return IsHepatitisC;
	}
	/**  
	  * @Title:  setIsHepatitisC <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsHepatitisC(String isHepatitisC) {
		IsHepatitisC = isHepatitisC;
	}
	/**  
	  * @Title:  getHepatitisC <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getHepatitisC() {
		return hepatitisC;
	}
	/**  
	  * @Title:  setHepatitisC <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setHepatitisC(String hepatitisC) {
		this.hepatitisC = hepatitisC;
	}
	/**  
	  * @Title:  getIsHIV <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsHIV() {
		return IsHIV;
	}
	/**  
	  * @Title:  setIsHIV <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsHIV(String isHIV) {
		IsHIV = isHIV;
	}
	/**  
	  * @Title:  getHiv <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getHiv() {
		return Hiv;
	}
	/**  
	  * @Title:  setHiv <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setHiv(String hiv) {
		Hiv = hiv;
	}
	/**  
	  * @Title:  getIsYphilis <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsYphilis() {
		return isYphilis;
	}
	/**  
	  * @Title:  setIsYphilis <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsYphilis(String isYphilis) {
		this.isYphilis = isYphilis;
	}
	/**  
	  * @Title:  getSyphilis <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSyphilis() {
		return syphilis;
	}
	/**  
	  * @Title:  setSyphilis <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSyphilis(String syphilis) {
		this.syphilis = syphilis;
	}
	/**  
	  * @Title:  getIsCancer <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsCancer() {
		return IsCancer;
	}
	/**  
	  * @Title:  setIsCancer <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsCancer(String isCancer) {
		IsCancer = isCancer;
	}
	/**  
	  * @Title:  getCancer <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCancer() {
		return cancer;
	}
	/**  
	  * @Title:  setCancer <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCancer(String cancer) {
		this.cancer = cancer;
	}
	/**  
	  * @Title:  getIsMaxillofacial <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsMaxillofacial() {
		return IsMaxillofacial;
	}
	/**  
	  * @Title:  setIsMaxillofacial <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsMaxillofacial(String isMaxillofacial) {
		IsMaxillofacial = isMaxillofacial;
	}
	/**  
	  * @Title:  getMaxillofacial <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getMaxillofacial() {
		return maxillofacial;
	}
	/**  
	  * @Title:  setMaxillofacial <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setMaxillofacial(String maxillofacial) {
		this.maxillofacial = maxillofacial;
	}
	/**  
	  * @Title:  getIsInflammation <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsInflammation() {
		return isInflammation;
	}
	/**  
	  * @Title:  setIsInflammation <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsInflammation(String isInflammation) {
		this.isInflammation = isInflammation;
	}
	/**  
	  * @Title:  getInflammation <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getInflammation() {
		return inflammation;
	}
	/**  
	  * @Title:  setInflammation <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setInflammation(String inflammation) {
		this.inflammation = inflammation;
	}
	/**  
	  * @Title:  getIsPharmacy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsPharmacy() {
		return IsPharmacy;
	}
	/**  
	  * @Title:  setIsPharmacy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsPharmacy(String isPharmacy) {
		IsPharmacy = isPharmacy;
	}
	/**  
	  * @Title:  getTreaTment <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getTreaTment() {
		return treaTment;
	}
	/**  
	  * @Title:  setTreaTment <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setTreaTment(String treaTment) {
		this.treaTment = treaTment;
	}
	/**  
	  * @Title:  getPharmacy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPharmacy() {
		return pharmacy;
	}
	/**  
	  * @Title:  setPharmacy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPharmacy(String pharmacy) {
		this.pharmacy = pharmacy;
	}
	/**  
	  * @Title:  getIsDrugAbuse <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsDrugAbuse() {
		return isDrugAbuse;
	}
	/**  
	  * @Title:  setIsDrugAbuse <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsDrugAbuse(String isDrugAbuse) {
		this.isDrugAbuse = isDrugAbuse;
	}
	/**  
	  * @Title:  getDrugAbuse <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDrugAbuse() {
		return drugAbuse;
	}
	/**  
	  * @Title:  setDrugAbuse <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDrugAbuse(String drugAbuse) {
		this.drugAbuse = drugAbuse;
	}
	/**  
	  * @Title:  getIsPsychosis <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsPsychosis() {
		return isPsychosis;
	}
	/**  
	  * @Title:  setIsPsychosis <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsPsychosis(String isPsychosis) {
		this.isPsychosis = isPsychosis;
	}
	/**  
	  * @Title:  getPsychosis <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPsychosis() {
		return psychosis;
	}
	/**  
	  * @Title:  setPsychosis <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPsychosis(String psychosis) {
		this.psychosis = psychosis;
	}
	/**  
	  * @Title:  getIsMucousMembrane <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsMucousMembrane() {
		return IsMucousMembrane;
	}
	/**  
	  * @Title:  setIsMucousMembrane <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsMucousMembrane(String isMucousMembrane) {
		IsMucousMembrane = isMucousMembrane;
	}
	/**  
	  * @Title:  getMucousMembrane <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getMucousMembrane() {
		return mucousMembrane;
	}
	/**  
	  * @Title:  setMucousMembrane <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setMucousMembrane(String mucousMembrane) {
		this.mucousMembrane = mucousMembrane;
	}
	/**  
	  * @Title:  getIsGlucocorticoids <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsGlucocorticoids() {
		return IsGlucocorticoids;
	}
	/**  
	  * @Title:  setIsGlucocorticoids <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsGlucocorticoids(String isGlucocorticoids) {
		IsGlucocorticoids = isGlucocorticoids;
	}
	/**  
	  * @Title:  getGlucocorticoids <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getGlucocorticoids() {
		return glucocorticoids;
	}
	/**  
	  * @Title:  setGlucocorticoids <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setGlucocorticoids(String glucocorticoids) {
		this.glucocorticoids = glucocorticoids;
	}
	/**  
	  * @Title:  getIsOtherDiseases <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsOtherDiseases() {
		return isOtherDiseases;
	}
	/**  
	  * @Title:  setIsOtherDiseases <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsOtherDiseases(String isOtherDiseases) {
		this.isOtherDiseases = isOtherDiseases;
	}
	/**  
	  * @Title:  getOtherDiseases <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOtherDiseases() {
		return otherDiseases;
	}
	/**  
	  * @Title:  setOtherDiseases <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOtherDiseases(String otherDiseases) {
		this.otherDiseases = otherDiseases;
	}
	/**  
	  * @Title:  getIsDrugAllergy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsDrugAllergy() {
		return isDrugAllergy;
	}
	/**  
	  * @Title:  setIsDrugAllergy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsDrugAllergy(String isDrugAllergy) {
		this.isDrugAllergy = isDrugAllergy;
	}
	/**  
	  * @Title:  getAllergicLength <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getAllergicLength() {
		return allergicLength;
	}
	/**  
	  * @Title:  setAllergicLength <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setAllergicLength(String allergicLength) {
		this.allergicLength = allergicLength;
	}
	/**  
	  * @Title:  getDrugAllergy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDrugAllergy() {
		return drugAllergy;
	}
	/**  
	  * @Title:  setDrugAllergy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDrugAllergy(String drugAllergy) {
		this.drugAllergy = drugAllergy;
	}
	/**  
	  * @Title:  getIsPregnancy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsPregnancy() {
		return isPregnancy;
	}
	/**  
	  * @Title:  setIsPregnancy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsPregnancy(String isPregnancy) {
		this.isPregnancy = isPregnancy;
	}
	/**  
	  * @Title:  getPregnancy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPregnancy() {
		return pregnancy;
	}
	/**  
	  * @Title:  setPregnancy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPregnancy(String pregnancy) {
		this.pregnancy = pregnancy;
	}
	/**  
	  * @Title:  getOnMedication <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOnMedication() {
		return onMedication;
	}
	/**  
	  * @Title:  setOnMedication <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOnMedication(String onMedication) {
		this.onMedication = onMedication;
	}
	/**  
	  * @Title:  getHabit <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getHabit() {
		return habit;
	}
	/**  
	  * @Title:  setHabit <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setHabit(String habit) {
		this.habit = habit;
	}
	/**  
	  * @Title:  getSmokeTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSmokeTime() {
		return smokeTime;
	}
	/**  
	  * @Title:  setSmokeTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSmokeTime(String smokeTime) {
		this.smokeTime = smokeTime;
	}
	/**  
	  * @Title:  getSmokeNum <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSmokeNum() {
		return smokeNum;
	}
	/**  
	  * @Title:  setSmokeNum <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSmokeNum(String smokeNum) {
		this.smokeNum = smokeNum;
	}
	/**  
	  * @Title:  getDrinkTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDrinkTime() {
		return drinkTime;
	}
	/**  
	  * @Title:  setDrinkTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDrinkTime(String drinkTime) {
		this.drinkTime = drinkTime;
	}
	/**  
	  * @Title:  getDrinkScale <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDrinkScale() {
		return drinkScale;
	}
	/**  
	  * @Title:  setDrinkScale <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDrinkScale(String drinkScale) {
		this.drinkScale = drinkScale;
	}
	/**  
	  * @Title:  getOdontoprisis <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOdontoprisis() {
		return odontoprisis;
	}
	/**  
	  * @Title:  setOdontoprisis <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOdontoprisis(String odontoprisis) {
		this.odontoprisis = odontoprisis;
	}
	/**  
	  * @Title:  getOdontoprisisDegree <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOdontoprisisDegree() {
		return odontoprisisDegree;
	}
	/**  
	  * @Title:  setOdontoprisisDegree <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOdontoprisisDegree(String odontoprisisDegree) {
		this.odontoprisisDegree = odontoprisisDegree;
	}
	/**  
	  * @Title:  getChewingHabits <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getChewingHabits() {
		return chewingHabits;
	}
	/**  
	  * @Title:  setChewingHabits <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setChewingHabits(String chewingHabits) {
		this.chewingHabits = chewingHabits;
	}
	/**  
	  * @Title:  getOthers <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getOthers() {
		return Others;
	}
	/**  
	  * @Title:  setOthers <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setOthers(String others) {
		Others = others;
	}
	/**  
	  * @Title:  getLastToothExtractionTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLastToothExtractionTime() {
		return LastToothExtractionTime;
	}
	/**  
	  * @Title:  setLastToothExtractionTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLastToothExtractionTime(String lastToothExtractionTime) {
		LastToothExtractionTime = lastToothExtractionTime;
	}
	/**  
	  * @Title:  getHaveYouHadDenture <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getHaveYouHadDenture() {
		return HaveYouHadDenture;
	}
	/**  
	  * @Title:  setHaveYouHadDenture <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setHaveYouHadDenture(String haveYouHadDenture) {
		HaveYouHadDenture = haveYouHadDenture;
	}
	/**  
	  * @Title:  getReasonsImplantDentures <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getReasonsImplantDentures() {
		return ReasonsImplantDentures;
	}
	/**  
	  * @Title:  setReasonsImplantDentures <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setReasonsImplantDentures(String reasonsImplantDentures) {
		ReasonsImplantDentures = reasonsImplantDentures;
	}
	/**  
	  * @Title:  getPatientSignature <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPatientSignature() {
		return PatientSignature;
	}
	/**  
	  * @Title:  setPatientSignature <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPatientSignature(String patientSignature) {
		PatientSignature = patientSignature;
	}
	/**  
	  * @Title:  getPatientTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPatientTime() {
		return PatientTime;
	}
	/**  
	  * @Title:  setPatientTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPatientTime(String patientTime) {
		PatientTime = patientTime;
	}
	/**  
	  * @Title:  getDoctorSignature <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDoctorSignature() {
		return doctorSignature;
	}
	/**  
	  * @Title:  setDoctorSignature <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDoctorSignature(String doctorSignature) {
		this.doctorSignature = doctorSignature;
	}
	/**  
	  * @Title:  getDoctorTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDoctorTime() {
		return doctorTime;
	}
	/**  
	  * @Title:  setDoctorTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDoctorTime(String doctorTime) {
		this.doctorTime = doctorTime;
	}
	/**  
	  * @Title:  getCreatetime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCreatetime() {
		return createtime;
	}
	/**  
	  * @Title:  setCreatetime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	/**  
	  * @Title:  getCreateuser <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCreateuser() {
		return createuser;
	}
	/**  
	  * @Title:  setCreateuser <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	/**  
	  * @Title:  getReservedField2 <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getReservedField2() {
		return reservedField2;
	}
	/**  
	  * @Title:  setReservedField2 <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setReservedField2(String reservedField2) {
		this.reservedField2 = reservedField2;
	}
	/**  
	  * @Title:  getReservedField3 <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getReservedField3() {
		return reservedField3;
	}
	/**  
	  * @Title:  setReservedField3 <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setReservedField3(String reservedField3) {
		this.reservedField3 = reservedField3;
	}
	/**   
	  * <p>Title: hashCode</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see java.lang.Object#hashCode()   
	  */  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((HaveYouHadDenture == null) ? 0 : HaveYouHadDenture.hashCode());
		result = prime * result + ((Hiv == null) ? 0 : Hiv.hashCode());
		result = prime * result + ((IsBloodCoagulation == null) ? 0 : IsBloodCoagulation.hashCode());
		result = prime * result + ((IsCancer == null) ? 0 : IsCancer.hashCode());
		result = prime * result + ((IsGlucocorticoids == null) ? 0 : IsGlucocorticoids.hashCode());
		result = prime * result + ((IsHIV == null) ? 0 : IsHIV.hashCode());
		result = prime * result + ((IsHearDiease == null) ? 0 : IsHearDiease.hashCode());
		result = prime * result + ((IsHepatitisC == null) ? 0 : IsHepatitisC.hashCode());
		result = prime * result + ((IsMaxillofacial == null) ? 0 : IsMaxillofacial.hashCode());
		result = prime * result + ((IsMucousMembrane == null) ? 0 : IsMucousMembrane.hashCode());
		result = prime * result + ((IsPharmacy == null) ? 0 : IsPharmacy.hashCode());
		result = prime * result + ((LastToothExtractionTime == null) ? 0 : LastToothExtractionTime.hashCode());
		result = prime * result + ((LcljId == null) ? 0 : LcljId.hashCode());
		result = prime * result + ((LcljNum == null) ? 0 : LcljNum.hashCode());
		result = prime * result + ((Others == null) ? 0 : Others.hashCode());
		result = prime * result + ((PatientSignature == null) ? 0 : PatientSignature.hashCode());
		result = prime * result + ((PatientTime == null) ? 0 : PatientTime.hashCode());
		result = prime * result + ((ReasonsImplantDentures == null) ? 0 : ReasonsImplantDentures.hashCode());
		result = prime * result + ((SEQ_ID == null) ? 0 : SEQ_ID.hashCode());
		result = prime * result + ((agomphosTime == null) ? 0 : agomphosTime.hashCode());
		result = prime * result + ((allergicLength == null) ? 0 : allergicLength.hashCode());
		result = prime * result + ((antiFreezing == null) ? 0 : antiFreezing.hashCode());
		result = prime * result + ((antiFreezingTime == null) ? 0 : antiFreezingTime.hashCode());
		result = prime * result + ((bloodCoagulation == null) ? 0 : bloodCoagulation.hashCode());
		result = prime * result + ((cancer == null) ? 0 : cancer.hashCode());
		result = prime * result + ((chewingHabits == null) ? 0 : chewingHabits.hashCode());
		result = prime * result + ((createtime == null) ? 0 : createtime.hashCode());
		result = prime * result + ((createuser == null) ? 0 : createuser.hashCode());
		result = prime * result + ((diabetes == null) ? 0 : diabetes.hashCode());
		result = prime * result + ((dietControl == null) ? 0 : dietControl.hashCode());
		result = prime * result + ((doctorSignature == null) ? 0 : doctorSignature.hashCode());
		result = prime * result + ((doctorTime == null) ? 0 : doctorTime.hashCode());
		result = prime * result + ((drinkScale == null) ? 0 : drinkScale.hashCode());
		result = prime * result + ((drinkTime == null) ? 0 : drinkTime.hashCode());
		result = prime * result + ((drugAbuse == null) ? 0 : drugAbuse.hashCode());
		result = prime * result + ((drugAllergy == null) ? 0 : drugAllergy.hashCode());
		result = prime * result + ((glucocorticoids == null) ? 0 : glucocorticoids.hashCode());
		result = prime * result + ((habit == null) ? 0 : habit.hashCode());
		result = prime * result + ((hearDiease == null) ? 0 : hearDiease.hashCode());
		result = prime * result + ((hepatitisB == null) ? 0 : hepatitisB.hashCode());
		result = prime * result + ((hepatitisC == null) ? 0 : hepatitisC.hashCode());
		result = prime * result + ((hypertension == null) ? 0 : hypertension.hashCode());
		result = prime * result + ((infarction == null) ? 0 : infarction.hashCode());
		result = prime * result + ((inflammation == null) ? 0 : inflammation.hashCode());
		result = prime * result + ((isAntiFreezing == null) ? 0 : isAntiFreezing.hashCode());
		result = prime * result + ((isControl == null) ? 0 : isControl.hashCode());
		result = prime * result + ((isDiabetes == null) ? 0 : isDiabetes.hashCode());
		result = prime * result + ((isDietControl == null) ? 0 : isDietControl.hashCode());
		result = prime * result + ((isDrugAbuse == null) ? 0 : isDrugAbuse.hashCode());
		result = prime * result + ((isDrugAllergy == null) ? 0 : isDrugAllergy.hashCode());
		result = prime * result + ((isHepatitisB == null) ? 0 : isHepatitisB.hashCode());
		result = prime * result + ((isHypertension == null) ? 0 : isHypertension.hashCode());
		result = prime * result + ((isInfarction == null) ? 0 : isInfarction.hashCode());
		result = prime * result + ((isInflammation == null) ? 0 : isInflammation.hashCode());
		result = prime * result + ((isOtherDiseases == null) ? 0 : isOtherDiseases.hashCode());
		result = prime * result + ((isPregnancy == null) ? 0 : isPregnancy.hashCode());
		result = prime * result + ((isPrepareMedication == null) ? 0 : isPrepareMedication.hashCode());
		result = prime * result + ((isPsychosis == null) ? 0 : isPsychosis.hashCode());
		result = prime * result + ((isTakeMedicie == null) ? 0 : isTakeMedicie.hashCode());
		result = prime * result + ((isYphilis == null) ? 0 : isYphilis.hashCode());
		result = prime * result + ((maxillofacial == null) ? 0 : maxillofacial.hashCode());
		result = prime * result + ((mucousMembrane == null) ? 0 : mucousMembrane.hashCode());
		result = prime * result + ((odontoprisis == null) ? 0 : odontoprisis.hashCode());
		result = prime * result + ((odontoprisisDegree == null) ? 0 : odontoprisisDegree.hashCode());
		result = prime * result + ((onMedication == null) ? 0 : onMedication.hashCode());
		result = prime * result + ((otherDiseases == null) ? 0 : otherDiseases.hashCode());
		result = prime * result + ((pharmacy == null) ? 0 : pharmacy.hashCode());
		result = prime * result + ((plantTime == null) ? 0 : plantTime.hashCode());
		result = prime * result + ((pregnancy == null) ? 0 : pregnancy.hashCode());
		result = prime * result + ((pressure == null) ? 0 : pressure.hashCode());
		result = prime * result + ((psychosis == null) ? 0 : psychosis.hashCode());
		result = prime * result + ((reservedField2 == null) ? 0 : reservedField2.hashCode());
		result = prime * result + ((reservedField3 == null) ? 0 : reservedField3.hashCode());
		result = prime * result + ((smokeNum == null) ? 0 : smokeNum.hashCode());
		result = prime * result + ((smokeTime == null) ? 0 : smokeTime.hashCode());
		result = prime * result + ((symptom == null) ? 0 : symptom.hashCode());
		result = prime * result + ((syphilis == null) ? 0 : syphilis.hashCode());
		result = prime * result + ((treaTment == null) ? 0 : treaTment.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	/**   
	  * <p>Title: equals</p>   
	  * <p>Description: </p>   
	  * @param obj
	  * @return   
	  * @see java.lang.Object#equals(java.lang.Object)   
	  */  
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdviceNote other = (AdviceNote) obj;
		if (HaveYouHadDenture == null) {
			if (other.HaveYouHadDenture != null)
				return false;
		} else if (!HaveYouHadDenture.equals(other.HaveYouHadDenture))
			return false;
		if (Hiv == null) {
			if (other.Hiv != null)
				return false;
		} else if (!Hiv.equals(other.Hiv))
			return false;
		if (IsBloodCoagulation == null) {
			if (other.IsBloodCoagulation != null)
				return false;
		} else if (!IsBloodCoagulation.equals(other.IsBloodCoagulation))
			return false;
		if (IsCancer == null) {
			if (other.IsCancer != null)
				return false;
		} else if (!IsCancer.equals(other.IsCancer))
			return false;
		if (IsGlucocorticoids == null) {
			if (other.IsGlucocorticoids != null)
				return false;
		} else if (!IsGlucocorticoids.equals(other.IsGlucocorticoids))
			return false;
		if (IsHIV == null) {
			if (other.IsHIV != null)
				return false;
		} else if (!IsHIV.equals(other.IsHIV))
			return false;
		if (IsHearDiease == null) {
			if (other.IsHearDiease != null)
				return false;
		} else if (!IsHearDiease.equals(other.IsHearDiease))
			return false;
		if (IsHepatitisC == null) {
			if (other.IsHepatitisC != null)
				return false;
		} else if (!IsHepatitisC.equals(other.IsHepatitisC))
			return false;
		if (IsMaxillofacial == null) {
			if (other.IsMaxillofacial != null)
				return false;
		} else if (!IsMaxillofacial.equals(other.IsMaxillofacial))
			return false;
		if (IsMucousMembrane == null) {
			if (other.IsMucousMembrane != null)
				return false;
		} else if (!IsMucousMembrane.equals(other.IsMucousMembrane))
			return false;
		if (IsPharmacy == null) {
			if (other.IsPharmacy != null)
				return false;
		} else if (!IsPharmacy.equals(other.IsPharmacy))
			return false;
		if (LastToothExtractionTime == null) {
			if (other.LastToothExtractionTime != null)
				return false;
		} else if (!LastToothExtractionTime.equals(other.LastToothExtractionTime))
			return false;
		if (LcljId == null) {
			if (other.LcljId != null)
				return false;
		} else if (!LcljId.equals(other.LcljId))
			return false;
		if (LcljNum == null) {
			if (other.LcljNum != null)
				return false;
		} else if (!LcljNum.equals(other.LcljNum))
			return false;
		if (Others == null) {
			if (other.Others != null)
				return false;
		} else if (!Others.equals(other.Others))
			return false;
		if (PatientSignature == null) {
			if (other.PatientSignature != null)
				return false;
		} else if (!PatientSignature.equals(other.PatientSignature))
			return false;
		if (PatientTime == null) {
			if (other.PatientTime != null)
				return false;
		} else if (!PatientTime.equals(other.PatientTime))
			return false;
		if (ReasonsImplantDentures == null) {
			if (other.ReasonsImplantDentures != null)
				return false;
		} else if (!ReasonsImplantDentures.equals(other.ReasonsImplantDentures))
			return false;
		if (SEQ_ID == null) {
			if (other.SEQ_ID != null)
				return false;
		} else if (!SEQ_ID.equals(other.SEQ_ID))
			return false;
		if (agomphosTime == null) {
			if (other.agomphosTime != null)
				return false;
		} else if (!agomphosTime.equals(other.agomphosTime))
			return false;
		if (allergicLength == null) {
			if (other.allergicLength != null)
				return false;
		} else if (!allergicLength.equals(other.allergicLength))
			return false;
		if (antiFreezing == null) {
			if (other.antiFreezing != null)
				return false;
		} else if (!antiFreezing.equals(other.antiFreezing))
			return false;
		if (antiFreezingTime == null) {
			if (other.antiFreezingTime != null)
				return false;
		} else if (!antiFreezingTime.equals(other.antiFreezingTime))
			return false;
		if (bloodCoagulation == null) {
			if (other.bloodCoagulation != null)
				return false;
		} else if (!bloodCoagulation.equals(other.bloodCoagulation))
			return false;
		if (cancer == null) {
			if (other.cancer != null)
				return false;
		} else if (!cancer.equals(other.cancer))
			return false;
		if (chewingHabits == null) {
			if (other.chewingHabits != null)
				return false;
		} else if (!chewingHabits.equals(other.chewingHabits))
			return false;
		if (createtime == null) {
			if (other.createtime != null)
				return false;
		} else if (!createtime.equals(other.createtime))
			return false;
		if (createuser == null) {
			if (other.createuser != null)
				return false;
		} else if (!createuser.equals(other.createuser))
			return false;
		if (diabetes == null) {
			if (other.diabetes != null)
				return false;
		} else if (!diabetes.equals(other.diabetes))
			return false;
		if (dietControl == null) {
			if (other.dietControl != null)
				return false;
		} else if (!dietControl.equals(other.dietControl))
			return false;
		if (doctorSignature == null) {
			if (other.doctorSignature != null)
				return false;
		} else if (!doctorSignature.equals(other.doctorSignature))
			return false;
		if (doctorTime == null) {
			if (other.doctorTime != null)
				return false;
		} else if (!doctorTime.equals(other.doctorTime))
			return false;
		if (drinkScale == null) {
			if (other.drinkScale != null)
				return false;
		} else if (!drinkScale.equals(other.drinkScale))
			return false;
		if (drinkTime == null) {
			if (other.drinkTime != null)
				return false;
		} else if (!drinkTime.equals(other.drinkTime))
			return false;
		if (drugAbuse == null) {
			if (other.drugAbuse != null)
				return false;
		} else if (!drugAbuse.equals(other.drugAbuse))
			return false;
		if (drugAllergy == null) {
			if (other.drugAllergy != null)
				return false;
		} else if (!drugAllergy.equals(other.drugAllergy))
			return false;
		if (glucocorticoids == null) {
			if (other.glucocorticoids != null)
				return false;
		} else if (!glucocorticoids.equals(other.glucocorticoids))
			return false;
		if (habit == null) {
			if (other.habit != null)
				return false;
		} else if (!habit.equals(other.habit))
			return false;
		if (hearDiease == null) {
			if (other.hearDiease != null)
				return false;
		} else if (!hearDiease.equals(other.hearDiease))
			return false;
		if (hepatitisB == null) {
			if (other.hepatitisB != null)
				return false;
		} else if (!hepatitisB.equals(other.hepatitisB))
			return false;
		if (hepatitisC == null) {
			if (other.hepatitisC != null)
				return false;
		} else if (!hepatitisC.equals(other.hepatitisC))
			return false;
		if (hypertension == null) {
			if (other.hypertension != null)
				return false;
		} else if (!hypertension.equals(other.hypertension))
			return false;
		if (infarction == null) {
			if (other.infarction != null)
				return false;
		} else if (!infarction.equals(other.infarction))
			return false;
		if (inflammation == null) {
			if (other.inflammation != null)
				return false;
		} else if (!inflammation.equals(other.inflammation))
			return false;
		if (isAntiFreezing == null) {
			if (other.isAntiFreezing != null)
				return false;
		} else if (!isAntiFreezing.equals(other.isAntiFreezing))
			return false;
		if (isControl == null) {
			if (other.isControl != null)
				return false;
		} else if (!isControl.equals(other.isControl))
			return false;
		if (isDiabetes == null) {
			if (other.isDiabetes != null)
				return false;
		} else if (!isDiabetes.equals(other.isDiabetes))
			return false;
		if (isDietControl == null) {
			if (other.isDietControl != null)
				return false;
		} else if (!isDietControl.equals(other.isDietControl))
			return false;
		if (isDrugAbuse == null) {
			if (other.isDrugAbuse != null)
				return false;
		} else if (!isDrugAbuse.equals(other.isDrugAbuse))
			return false;
		if (isDrugAllergy == null) {
			if (other.isDrugAllergy != null)
				return false;
		} else if (!isDrugAllergy.equals(other.isDrugAllergy))
			return false;
		if (isHepatitisB == null) {
			if (other.isHepatitisB != null)
				return false;
		} else if (!isHepatitisB.equals(other.isHepatitisB))
			return false;
		if (isHypertension == null) {
			if (other.isHypertension != null)
				return false;
		} else if (!isHypertension.equals(other.isHypertension))
			return false;
		if (isInfarction == null) {
			if (other.isInfarction != null)
				return false;
		} else if (!isInfarction.equals(other.isInfarction))
			return false;
		if (isInflammation == null) {
			if (other.isInflammation != null)
				return false;
		} else if (!isInflammation.equals(other.isInflammation))
			return false;
		if (isOtherDiseases == null) {
			if (other.isOtherDiseases != null)
				return false;
		} else if (!isOtherDiseases.equals(other.isOtherDiseases))
			return false;
		if (isPregnancy == null) {
			if (other.isPregnancy != null)
				return false;
		} else if (!isPregnancy.equals(other.isPregnancy))
			return false;
		if (isPrepareMedication == null) {
			if (other.isPrepareMedication != null)
				return false;
		} else if (!isPrepareMedication.equals(other.isPrepareMedication))
			return false;
		if (isPsychosis == null) {
			if (other.isPsychosis != null)
				return false;
		} else if (!isPsychosis.equals(other.isPsychosis))
			return false;
		if (isTakeMedicie == null) {
			if (other.isTakeMedicie != null)
				return false;
		} else if (!isTakeMedicie.equals(other.isTakeMedicie))
			return false;
		if (isYphilis == null) {
			if (other.isYphilis != null)
				return false;
		} else if (!isYphilis.equals(other.isYphilis))
			return false;
		if (maxillofacial == null) {
			if (other.maxillofacial != null)
				return false;
		} else if (!maxillofacial.equals(other.maxillofacial))
			return false;
		if (mucousMembrane == null) {
			if (other.mucousMembrane != null)
				return false;
		} else if (!mucousMembrane.equals(other.mucousMembrane))
			return false;
		if (odontoprisis == null) {
			if (other.odontoprisis != null)
				return false;
		} else if (!odontoprisis.equals(other.odontoprisis))
			return false;
		if (odontoprisisDegree == null) {
			if (other.odontoprisisDegree != null)
				return false;
		} else if (!odontoprisisDegree.equals(other.odontoprisisDegree))
			return false;
		if (onMedication == null) {
			if (other.onMedication != null)
				return false;
		} else if (!onMedication.equals(other.onMedication))
			return false;
		if (otherDiseases == null) {
			if (other.otherDiseases != null)
				return false;
		} else if (!otherDiseases.equals(other.otherDiseases))
			return false;
		if (pharmacy == null) {
			if (other.pharmacy != null)
				return false;
		} else if (!pharmacy.equals(other.pharmacy))
			return false;
		if (plantTime == null) {
			if (other.plantTime != null)
				return false;
		} else if (!plantTime.equals(other.plantTime))
			return false;
		if (pregnancy == null) {
			if (other.pregnancy != null)
				return false;
		} else if (!pregnancy.equals(other.pregnancy))
			return false;
		if (pressure == null) {
			if (other.pressure != null)
				return false;
		} else if (!pressure.equals(other.pressure))
			return false;
		if (psychosis == null) {
			if (other.psychosis != null)
				return false;
		} else if (!psychosis.equals(other.psychosis))
			return false;
		if (reservedField2 == null) {
			if (other.reservedField2 != null)
				return false;
		} else if (!reservedField2.equals(other.reservedField2))
			return false;
		if (reservedField3 == null) {
			if (other.reservedField3 != null)
				return false;
		} else if (!reservedField3.equals(other.reservedField3))
			return false;
		if (smokeNum == null) {
			if (other.smokeNum != null)
				return false;
		} else if (!smokeNum.equals(other.smokeNum))
			return false;
		if (smokeTime == null) {
			if (other.smokeTime != null)
				return false;
		} else if (!smokeTime.equals(other.smokeTime))
			return false;
		if (symptom == null) {
			if (other.symptom != null)
				return false;
		} else if (!symptom.equals(other.symptom))
			return false;
		if (syphilis == null) {
			if (other.syphilis != null)
				return false;
		} else if (!syphilis.equals(other.syphilis))
			return false;
		if (treaTment == null) {
			if (other.treaTment != null)
				return false;
		} else if (!treaTment.equals(other.treaTment))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	/**   
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see java.lang.Object#toString()   
	  */  
	@Override
	public String toString() {
		return "AdviceNote [SEQ_ID=" + SEQ_ID + ", LcljNum=" + LcljNum + ", LcljId=" + LcljId + ", username=" + username
				+ ", agomphosTime=" + agomphosTime + ", symptom=" + symptom + ", plantTime=" + plantTime
				+ ", isHypertension=" + isHypertension + ", hypertension=" + hypertension + ", isTakeMedicie="
				+ isTakeMedicie + ", isControl=" + isControl + ", pressure=" + pressure + ", IsHearDiease="
				+ IsHearDiease + ", hearDiease=" + hearDiease + ", isPrepareMedication=" + isPrepareMedication
				+ ", isDiabetes=" + isDiabetes + ", diabetes=" + diabetes + ", dietControl=" + dietControl
				+ ", isDietControl=" + isDietControl + ", isInfarction=" + isInfarction + ", infarction=" + infarction
				+ ", IsBloodCoagulation=" + IsBloodCoagulation + ", bloodCoagulation=" + bloodCoagulation
				+ ", isAntiFreezing=" + isAntiFreezing + ", antiFreezing=" + antiFreezing + ", antiFreezingTime="
				+ antiFreezingTime + ", isHepatitisB=" + isHepatitisB + ", hepatitisB=" + hepatitisB + ", IsHepatitisC="
				+ IsHepatitisC + ", hepatitisC=" + hepatitisC + ", IsHIV=" + IsHIV + ", Hiv=" + Hiv + ", isYphilis="
				+ isYphilis + ", syphilis=" + syphilis + ", IsCancer=" + IsCancer + ", cancer=" + cancer
				+ ", IsMaxillofacial=" + IsMaxillofacial + ", maxillofacial=" + maxillofacial + ", isInflammation="
				+ isInflammation + ", inflammation=" + inflammation + ", IsPharmacy=" + IsPharmacy + ", treaTment="
				+ treaTment + ", pharmacy=" + pharmacy + ", isDrugAbuse=" + isDrugAbuse + ", drugAbuse=" + drugAbuse
				+ ", isPsychosis=" + isPsychosis + ", psychosis=" + psychosis + ", IsMucousMembrane=" + IsMucousMembrane
				+ ", mucousMembrane=" + mucousMembrane + ", IsGlucocorticoids=" + IsGlucocorticoids
				+ ", glucocorticoids=" + glucocorticoids + ", isOtherDiseases=" + isOtherDiseases + ", otherDiseases="
				+ otherDiseases + ", isDrugAllergy=" + isDrugAllergy + ", allergicLength=" + allergicLength
				+ ", drugAllergy=" + drugAllergy + ", isPregnancy=" + isPregnancy + ", pregnancy=" + pregnancy
				+ ", onMedication=" + onMedication + ", habit=" + habit + ", smokeTime=" + smokeTime + ", smokeNum="
				+ smokeNum + ", drinkTime=" + drinkTime + ", drinkScale=" + drinkScale + ", odontoprisis="
				+ odontoprisis + ", odontoprisisDegree=" + odontoprisisDegree + ", chewingHabits=" + chewingHabits
				+ ", Others=" + Others + ", LastToothExtractionTime=" + LastToothExtractionTime + ", HaveYouHadDenture="
				+ HaveYouHadDenture + ", ReasonsImplantDentures=" + ReasonsImplantDentures + ", PatientSignature="
				+ PatientSignature + ", PatientTime=" + PatientTime + ", doctorSignature=" + doctorSignature
				+ ", doctorTime=" + doctorTime + ", createtime=" + createtime + ", createuser=" + createuser
				+ ", reservedField2=" + reservedField2 + ", reservedField3="
				+ reservedField3 + "]";
	}
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
}
