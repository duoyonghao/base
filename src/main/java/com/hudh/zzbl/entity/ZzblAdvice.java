package com.hudh.zzbl.entity;

import java.io.Serializable;

/**
 * 主诉及既往病史
 *
 * @author duoyh
 * @ClassName: AdviceNote
 * @Description: TODO(主诉及既往病史 : 用于研讨)
 * @dateTime 2019年5月1日 下午1:47:40
 */
public class ZzblAdvice implements Serializable {


    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private String SEQ_ID;
    /**
     * 病历id
     */
    private String lcljId;
    /**
     * 病历编号
     */
    private String lcljNum;
    /**
     * 患者编号
     */
    private String patient_num;
    /**
     * 牙齿症状选中
     */
    private String symptom;
    /**
     * 牙缺失左上牙位图
     */
    private String toothloseMapUpLeft;
    /**
     * 牙缺失左下牙位图
     */
    private String toothloseMapDownLeft;
    /**
     * 牙缺失右上牙位图
     */
    private String toothloseMapUpRight;
    /**
     * 牙缺失右下牙位图
     */
    private String toothloseMapDownRight;
    /**
     * 牙松动左上牙位图
     */
    private String toothlessMapUpLeft;
    /**
     * 牙松动左下牙位图
     */
    private String toothlessMapDownLeft;
    /**
     * 牙松动右上牙位图
     */
    private String toothlessMapUpRight;
    /**
     * 牙松动右下牙位图
     */
    private String toothlessMapDownRight;
    /**
     * 龋齿左上牙位图
     */
    private String toothdecayedMapUpLeft;
    /**
     * 龋齿左下牙位图
     */
    private String toothdecayedMapDownLeft;
    /**
     * 龋齿右上牙位图
     */
    private String toothdecayedMapUpRight;
    /**
     * 龋齿右下牙位图
     */
    private String toothdecayedMapDownRight;
    /**
     * 牙折断左上牙位图
     */
    private String toothsnapMapUpLeft;
    /**
     * 牙折断左下牙位图
     */
    private String toothsnapMapDownLeft;
    /**
     * 牙折断右上牙位图
     */
    private String toothsnapMapUpRight;
    /**
     * 牙折断右下牙位图
     */
    private String toothsnapMapDownRight;
    /**
     * 牙缺失时间
     */
    private String toothlosetime;
    /**
     * 牙松动时间
     */
    private String toothlesstime;
    /**
     * 龋齿时间
     */
    private String toothdecayedtime;
    /**
     * 牙折断时间
     */
    private String toothsnaptime;
    /**
     * 是否要求种植修复
     */
    private String implantRestoration;
    /**
     * 是否有高血压
     */
    private String isHypertension;
    /**
     * 患高血压年限
     */
    private String hypertension;
    /**
     * 是否有在服药
     */
    private String isTakeMedicie;
    /**
     * 当前血压
     */
    private String pressure;
    /**
     * 高血压常用药物
     */
    private String hypertensionmedicine;
    /**
     * 是否患有心脏病（心绞痛、心衰）
     */
    private String isHearDiease;
    /**
     * 患心脏病年限
     */
    private String hearDiease;
    /**
     * 是否有长期备药在身边
     */
    private String isPrepareMedication;
    /**
     * 心脏病常用药
     */
    private String heardieasemedicine;
    /**
     * 是否患有糖尿病
     */
    private String isDiabetes;
    /**
     * 患糖尿病年限
     */
    private String diabetes;
    /**
     * 控制饮食的方式
     */
    private String dietControl;
    /**
     * 糖尿病口服常用药
     */
    private String diabetesoralmedicine;
    /**
     * 糖尿病注射常用药
     */
    private String diabetesinjectionmedicine;
    /**
     * 六个月内做过心脏手术
     */
    private String isheartoperation;
    /**
     * 六个月内发生心梗
     */
    private String isheartinfarction;
    /**
     * 凝血功能不足性疾病
     */
    private String isBloodCoagulation;
    /**
     * 患病年限
     */
    private String bloodCoagulation;
    /**
     * 服用抗凝药物或其他可以导致凝血功能障碍的药物
     */
    private String isAntiFreezing;
    /**
     * 服用抗凝药物常用药物
     */
    private String antifreezingmedicine;
    /**
     * 服药年限
     */
    private String antiFreezingTime;
    /**
     * 是否患有乙肝
     */
    private String isHepatitisB;
    /**
     * 患病年限
     */
    private String hepatitisB;
    /**
     * 是否患有丙肝
     */
    private String isHepatitisC;
    /**
     * 患病年限
     */
    private String hepatitisC;
    /**
     * 是否患有HIV
     */
    private String isHIV;
    /**
     * 患病年限
     */
    private String hiv;
    /**
     * 是否恶性肿瘤（癌症）
     */
    private String isCancer;
    /**
     * 患病年限
     */
    private String cancer;
    /**
     * 是否患有梅毒
     */
    private String isYphilis;
    /**
     * 患病年限
     */
    private String syphilis;
    /**
     * 是否有过颌面部放疗术
     */
    private String isMaxillofacial;
    /**
     * 间隔年限
     */
    private String maxillofacial;
    /**
     * 是否患有感染急性炎症
     */
    private String isInflammation;
    /**
     * 患病年限
     */
    private String inflammation;
    /**
     * 是否吸毒
     */
    private String isDrugAbuse;
    /**
     * 吸毒年限
     */
    private String drugAbuse;
    /**
     * 是否患有心理、精神障碍
     */
    private String isPsychosis;
    /**
     * 患病年限
     */
    private String psychosis;
    /**
     * 是否患有皮肤黏膜病变
     */
    private String isMucousMembrane;
    /**
     * 患病年限
     */
    private String mucousMembrane;
    /**
     * 是否有骨质酥松用药
     */
    private String isPharmacy;
    /**
     * 注射用药时间
     */
    private String pharmacy;
    /**
     * 口服用药时间
     */
    private String takeorallytime;
    /**
     * 骨质疏松常用药物
     */
    private String pharmacymedicine;
    /**
     * 长期应用糖皮质激素
     */
    private String isGlucocorticoids;
    /**
     * 使用激素年限
     */
    private String glucocorticoids;
    /**
     * 长期应用糖皮质激素常用药
     */
    private String glucocorticoidsmedicine;
    /**
     * 是否患有其他疾病
     */
    private String isOtherDiseases;
    /**
     * 其他疾病输入
     */
    private String otherdiseasestext;
    /**
     * 患病年限
     */
    private String otherDiseases;
    /**
     * 是否药物过敏
     */
    private String isDrugAllergy;
    /**
     * 过敏时间
     */
    private String allergicLength;
    /**
     * 输入过敏药物
     */
    private String drugallergy;
    /**
     * 是否怀孕
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
     * 正在服用药物的时间
     */
    private String onmedicationtime;
    /**
     * 是否吸烟
     */
    private String issmoke;
    /**
     * 抽烟时间
     */
    private String smoketime;
    /**
     * 抽烟数量 支/日
     */
    private String smokeNum;
    /**
     * 是否饮酒
     */
    private String isdrink;
    /**
     * 饮酒年限
     */
    private String drinkTime;
    /**
     * 饮酒量    1.少量 2.中量 3.大量
     */
    private String drinkScale;
    /**
     * 是否磨牙
     */
    private String isodontoprisis;
    /**
     * 磨牙时间
     */
    private String odontoprisis;
    /**
     * 磨牙频率 1.频繁 2.偶尔
     */
    private String odontoprisisDegree;
    /**
     * 咀嚼习惯
     */
    private String chewingHabits;
    /**
     * 咀嚼习惯时间
     */
    private String chewinghabitstime;
    /**
     * 其他习惯输入
     */
    private String others;
    /**
     * 洁牙习惯
     */
    private String isteethclean;
    /**
     * 频次
     */
    private String teethcleannum;
    /**
     * 最近一次洁牙时间
     */
    private String planttime;
    /**
     * 患者签字时间
     */
    private String patientTime;
    /**
     * 医生签字时间
     */
    private String doctorTime;
    /**
     * 患者签字
     */
    private String patientsignature;
    /**
     * 医生签字
     */
    private String doctorsignature;

    /**
     * 门诊
     */
    private String organization;
    /**
     * 创建人
     */
    private String createuser;
    /**
     * 创建时间
     */
    private String createtime;
    /**
     * 状态
     */
    private String status;
    /**
     * 修改人
     */
    private String updateuser;
    /**
     * 修改时间
     */
    private String updatetime;
    /**
     * 口干
     */
    private String isdry;
    /**
     * 最近一次拔牙
     */
    private String ispullouttooth;
    /**
     * 最近一次拔牙时间
     */
    private String pullouttooth;

    public String getIsdry() {
        return isdry;
    }

    public void setIsdry(String isdry) {
        this.isdry = isdry;
    }

    public String getIspullouttooth() {
        return ispullouttooth;
    }

    public void setIspullouttooth(String ispullouttooth) {
        this.ispullouttooth = ispullouttooth;
    }

    public String getPullouttooth() {
        return pullouttooth;
    }

    public void setPullouttooth(String pullouttooth) {
        this.pullouttooth = pullouttooth;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getSEQ_ID() {
        return SEQ_ID;
    }

    public void setSEQ_ID(String SEQ_ID) {
        this.SEQ_ID = SEQ_ID;
    }

    public String getPatient_num() {
        return patient_num;
    }

    public void setPatient_num(String patient_num) {
        this.patient_num = patient_num;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getToothloseMapUpLeft() {
        return toothloseMapUpLeft;
    }

    public void setToothloseMapUpLeft(String toothloseMapUpLeft) {
        this.toothloseMapUpLeft = toothloseMapUpLeft;
    }

    public String getToothloseMapDownLeft() {
        return toothloseMapDownLeft;
    }

    public void setToothloseMapDownLeft(String toothloseMapDownLeft) {
        this.toothloseMapDownLeft = toothloseMapDownLeft;
    }

    public String getToothloseMapUpRight() {
        return toothloseMapUpRight;
    }

    public void setToothloseMapUpRight(String toothloseMapUpRight) {
        this.toothloseMapUpRight = toothloseMapUpRight;
    }

    public String getToothloseMapDownRight() {
        return toothloseMapDownRight;
    }

    public void setToothloseMapDownRight(String toothloseMapDownRight) {
        this.toothloseMapDownRight = toothloseMapDownRight;
    }

    public String getToothlessMapUpLeft() {
        return toothlessMapUpLeft;
    }

    public void setToothlessMapUpLeft(String toothlessMapUpLeft) {
        this.toothlessMapUpLeft = toothlessMapUpLeft;
    }

    public String getToothlessMapDownLeft() {
        return toothlessMapDownLeft;
    }

    public void setToothlessMapDownLeft(String toothlessMapDownLeft) {
        this.toothlessMapDownLeft = toothlessMapDownLeft;
    }

    public String getToothlessMapUpRight() {
        return toothlessMapUpRight;
    }

    public void setToothlessMapUpRight(String toothlessMapUpRight) {
        this.toothlessMapUpRight = toothlessMapUpRight;
    }

    public String getToothlessMapDownRight() {
        return toothlessMapDownRight;
    }

    public void setToothlessMapDownRight(String toothlessMapDownRight) {
        this.toothlessMapDownRight = toothlessMapDownRight;
    }

    public String getToothdecayedMapUpLeft() {
        return toothdecayedMapUpLeft;
    }

    public void setToothdecayedMapUpLeft(String toothdecayedMapUpLeft) {
        this.toothdecayedMapUpLeft = toothdecayedMapUpLeft;
    }

    public String getToothdecayedMapDownLeft() {
        return toothdecayedMapDownLeft;
    }

    public void setToothdecayedMapDownLeft(String toothdecayedMapDownLeft) {
        this.toothdecayedMapDownLeft = toothdecayedMapDownLeft;
    }

    public String getToothdecayedMapUpRight() {
        return toothdecayedMapUpRight;
    }

    public void setToothdecayedMapUpRight(String toothdecayedMapUpRight) {
        this.toothdecayedMapUpRight = toothdecayedMapUpRight;
    }

    public String getToothdecayedMapDownRight() {
        return toothdecayedMapDownRight;
    }

    public void setToothdecayedMapDownRight(String toothdecayedMapDownRight) {
        this.toothdecayedMapDownRight = toothdecayedMapDownRight;
    }

    public String getToothsnapMapUpLeft() {
        return toothsnapMapUpLeft;
    }

    public void setToothsnapMapUpLeft(String toothsnapMapUpLeft) {
        this.toothsnapMapUpLeft = toothsnapMapUpLeft;
    }

    public String getToothsnapMapDownLeft() {
        return toothsnapMapDownLeft;
    }

    public void setToothsnapMapDownLeft(String toothsnapMapDownLeft) {
        this.toothsnapMapDownLeft = toothsnapMapDownLeft;
    }

    public String getToothsnapMapUpRight() {
        return toothsnapMapUpRight;
    }

    public void setToothsnapMapUpRight(String toothsnapMapUpRight) {
        this.toothsnapMapUpRight = toothsnapMapUpRight;
    }

    public String getToothsnapMapDownRight() {
        return toothsnapMapDownRight;
    }

    public void setToothsnapMapDownRight(String toothsnapMapDownRight) {
        this.toothsnapMapDownRight = toothsnapMapDownRight;
    }

    public String getToothlosetime() {
        return toothlosetime;
    }

    public void setToothlosetime(String toothlosetime) {
        this.toothlosetime = toothlosetime;
    }

    public String getToothlesstime() {
        return toothlesstime;
    }

    public void setToothlesstime(String toothlesstime) {
        this.toothlesstime = toothlesstime;
    }

    public String getToothdecayedtime() {
        return toothdecayedtime;
    }

    public void setToothdecayedtime(String toothdecayedtime) {
        this.toothdecayedtime = toothdecayedtime;
    }

    public String getToothsnaptime() {
        return toothsnaptime;
    }

    public void setToothsnaptime(String toothsnaptime) {
        this.toothsnaptime = toothsnaptime;
    }

    public String getImplantRestoration() {
        return implantRestoration;
    }

    public void setImplantRestoration(String implantRestoration) {
        this.implantRestoration = implantRestoration;
    }

    public String getIsHypertension() {
        return isHypertension;
    }

    public void setIsHypertension(String isHypertension) {
        this.isHypertension = isHypertension;
    }

    public String getHypertension() {
        return hypertension;
    }

    public void setHypertension(String hypertension) {
        this.hypertension = hypertension;
    }

    public String getIsTakeMedicie() {
        return isTakeMedicie;
    }

    public void setIsTakeMedicie(String isTakeMedicie) {
        this.isTakeMedicie = isTakeMedicie;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHypertensionmedicine() {
        return hypertensionmedicine;
    }

    public void setHypertensionmedicine(String hypertensionmedicine) {
        this.hypertensionmedicine = hypertensionmedicine;
    }

    public String getHearDiease() {
        return hearDiease;
    }

    public void setHearDiease(String hearDiease) {
        this.hearDiease = hearDiease;
    }

    public String getIsPrepareMedication() {
        return isPrepareMedication;
    }

    public void setIsPrepareMedication(String isPrepareMedication) {
        this.isPrepareMedication = isPrepareMedication;
    }

    public String getHeardieasemedicine() {
        return heardieasemedicine;
    }

    public void setHeardieasemedicine(String heardieasemedicine) {
        this.heardieasemedicine = heardieasemedicine;
    }

    public String getIsDiabetes() {
        return isDiabetes;
    }

    public void setIsDiabetes(String isDiabetes) {
        this.isDiabetes = isDiabetes;
    }

    public String getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(String diabetes) {
        this.diabetes = diabetes;
    }

    public String getDietControl() {
        return dietControl;
    }

    public void setDietControl(String dietControl) {
        this.dietControl = dietControl;
    }

    public String getDiabetesoralmedicine() {
        return diabetesoralmedicine;
    }

    public void setDiabetesoralmedicine(String diabetesoralmedicine) {
        this.diabetesoralmedicine = diabetesoralmedicine;
    }

    public String getDiabetesinjectionmedicine() {
        return diabetesinjectionmedicine;
    }

    public void setDiabetesinjectionmedicine(String diabetesinjectionmedicine) {
        this.diabetesinjectionmedicine = diabetesinjectionmedicine;
    }

    public String getIsheartoperation() {
        return isheartoperation;
    }

    public void setIsheartoperation(String isheartoperation) {
        this.isheartoperation = isheartoperation;
    }

    public String getIsheartinfarction() {
        return isheartinfarction;
    }

    public void setIsheartinfarction(String isheartinfarction) {
        this.isheartinfarction = isheartinfarction;
    }

    public String getBloodCoagulation() {
        return bloodCoagulation;
    }

    public void setBloodCoagulation(String bloodCoagulation) {
        this.bloodCoagulation = bloodCoagulation;
    }

    public String getIsAntiFreezing() {
        return isAntiFreezing;
    }

    public void setIsAntiFreezing(String isAntiFreezing) {
        this.isAntiFreezing = isAntiFreezing;
    }

    public String getAntifreezingmedicine() {
        return antifreezingmedicine;
    }

    public void setAntifreezingmedicine(String antifreezingmedicine) {
        this.antifreezingmedicine = antifreezingmedicine;
    }

    public String getAntiFreezingTime() {
        return antiFreezingTime;
    }

    public void setAntiFreezingTime(String antiFreezingTime) {
        this.antiFreezingTime = antiFreezingTime;
    }

    public String getIsHepatitisB() {
        return isHepatitisB;
    }

    public void setIsHepatitisB(String isHepatitisB) {
        this.isHepatitisB = isHepatitisB;
    }

    public String getHepatitisB() {
        return hepatitisB;
    }

    public void setHepatitisB(String hepatitisB) {
        this.hepatitisB = hepatitisB;
    }

    public String getHepatitisC() {
        return hepatitisC;
    }

    public void setHepatitisC(String hepatitisC) {
        this.hepatitisC = hepatitisC;
    }

    public String getIsCancer() {
        return isCancer;
    }

    public void setIsCancer(String isCancer) {
        this.isCancer = isCancer;
    }

    public String getCancer() {
        return cancer;
    }

    public void setCancer(String cancer) {
        this.cancer = cancer;
    }

    public String getIsYphilis() {
        return isYphilis;
    }

    public void setIsYphilis(String isYphilis) {
        this.isYphilis = isYphilis;
    }

    public String getSyphilis() {
        return syphilis;
    }

    public void setSyphilis(String syphilis) {
        this.syphilis = syphilis;
    }

    public String getMaxillofacial() {
        return maxillofacial;
    }

    public void setMaxillofacial(String maxillofacial) {
        this.maxillofacial = maxillofacial;
    }

    public String getIsInflammation() {
        return isInflammation;
    }

    public void setIsInflammation(String isInflammation) {
        this.isInflammation = isInflammation;
    }

    public String getInflammation() {
        return inflammation;
    }

    public void setInflammation(String inflammation) {
        this.inflammation = inflammation;
    }

    public String getIsDrugAbuse() {
        return isDrugAbuse;
    }

    public void setIsDrugAbuse(String isDrugAbuse) {
        this.isDrugAbuse = isDrugAbuse;
    }

    public String getDrugAbuse() {
        return drugAbuse;
    }

    public void setDrugAbuse(String drugAbuse) {
        this.drugAbuse = drugAbuse;
    }

    public String getIsPsychosis() {
        return isPsychosis;
    }

    public void setIsPsychosis(String isPsychosis) {
        this.isPsychosis = isPsychosis;
    }

    public String getPsychosis() {
        return psychosis;
    }

    public void setPsychosis(String psychosis) {
        this.psychosis = psychosis;
    }

    public String getMucousMembrane() {
        return mucousMembrane;
    }

    public void setMucousMembrane(String mucousMembrane) {
        this.mucousMembrane = mucousMembrane;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public String getTakeorallytime() {
        return takeorallytime;
    }

    public void setTakeorallytime(String takeorallytime) {
        this.takeorallytime = takeorallytime;
    }

    public String getPharmacymedicine() {
        return pharmacymedicine;
    }

    public void setPharmacymedicine(String pharmacymedicine) {
        this.pharmacymedicine = pharmacymedicine;
    }

    public String getGlucocorticoids() {
        return glucocorticoids;
    }

    public void setGlucocorticoids(String glucocorticoids) {
        this.glucocorticoids = glucocorticoids;
    }

    public String getGlucocorticoidsmedicine() {
        return glucocorticoidsmedicine;
    }

    public void setGlucocorticoidsmedicine(String glucocorticoidsmedicine) {
        this.glucocorticoidsmedicine = glucocorticoidsmedicine;
    }

    public String getIsOtherDiseases() {
        return isOtherDiseases;
    }

    public void setIsOtherDiseases(String isOtherDiseases) {
        this.isOtherDiseases = isOtherDiseases;
    }

    public String getOtherdiseasestext() {
        return otherdiseasestext;
    }

    public void setOtherdiseasestext(String otherdiseasestext) {
        this.otherdiseasestext = otherdiseasestext;
    }

    public String getOtherDiseases() {
        return otherDiseases;
    }

    public void setOtherDiseases(String otherDiseases) {
        this.otherDiseases = otherDiseases;
    }

    public String getIsDrugAllergy() {
        return isDrugAllergy;
    }

    public void setIsDrugAllergy(String isDrugAllergy) {
        this.isDrugAllergy = isDrugAllergy;
    }

    public String getAllergicLength() {
        return allergicLength;
    }

    public void setAllergicLength(String allergicLength) {
        this.allergicLength = allergicLength;
    }

    public String getDrugallergy() {
        return drugallergy;
    }

    public void setDrugallergy(String drugallergy) {
        this.drugallergy = drugallergy;
    }

    public String getIsPregnancy() {
        return isPregnancy;
    }

    public void setIsPregnancy(String isPregnancy) {
        this.isPregnancy = isPregnancy;
    }

    public String getPregnancy() {
        return pregnancy;
    }

    public void setPregnancy(String pregnancy) {
        this.pregnancy = pregnancy;
    }

    public String getOnMedication() {
        return onMedication;
    }

    public void setOnMedication(String onMedication) {
        this.onMedication = onMedication;
    }

    public String getOnmedicationtime() {
        return onmedicationtime;
    }

    public void setOnmedicationtime(String onmedicationtime) {
        this.onmedicationtime = onmedicationtime;
    }

    public String getIssmoke() {
        return issmoke;
    }

    public void setIssmoke(String issmoke) {
        this.issmoke = issmoke;
    }

    public String getSmoketime() {
        return smoketime;
    }

    public void setSmoketime(String smoketime) {
        this.smoketime = smoketime;
    }

    public String getSmokeNum() {
        return smokeNum;
    }

    public void setSmokeNum(String smokeNum) {
        this.smokeNum = smokeNum;
    }

    public String getIsdrink() {
        return isdrink;
    }

    public void setIsdrink(String isdrink) {
        this.isdrink = isdrink;
    }

    public String getDrinkTime() {
        return drinkTime;
    }

    public void setDrinkTime(String drinkTime) {
        this.drinkTime = drinkTime;
    }

    public String getDrinkScale() {
        return drinkScale;
    }

    public void setDrinkScale(String drinkScale) {
        this.drinkScale = drinkScale;
    }

    public String getIsodontoprisis() {
        return isodontoprisis;
    }

    public void setIsodontoprisis(String isodontoprisis) {
        this.isodontoprisis = isodontoprisis;
    }

    public String getOdontoprisis() {
        return odontoprisis;
    }

    public void setOdontoprisis(String odontoprisis) {
        this.odontoprisis = odontoprisis;
    }

    public String getOdontoprisisDegree() {
        return odontoprisisDegree;
    }

    public void setOdontoprisisDegree(String odontoprisisDegree) {
        this.odontoprisisDegree = odontoprisisDegree;
    }

    public String getChewingHabits() {
        return chewingHabits;
    }

    public void setChewingHabits(String chewingHabits) {
        this.chewingHabits = chewingHabits;
    }

    public String getChewinghabitstime() {
        return chewinghabitstime;
    }

    public void setChewinghabitstime(String chewinghabitstime) {
        this.chewinghabitstime = chewinghabitstime;
    }

    public String getIsteethclean() {
        return isteethclean;
    }

    public void setIsteethclean(String isteethclean) {
        this.isteethclean = isteethclean;
    }

    public String getTeethcleannum() {
        return teethcleannum;
    }

    public void setTeethcleannum(String teethcleannum) {
        this.teethcleannum = teethcleannum;
    }

    public String getPlanttime() {
        return planttime;
    }

    public void setPlanttime(String planttime) {
        this.planttime = planttime;
    }


    public String getDoctorTime() {
        return doctorTime;
    }

    public void setDoctorTime(String doctorTime) {
        this.doctorTime = doctorTime;
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

    public String getLcljId() {
        return lcljId;
    }

    public void setLcljId(String lcljId) {
        this.lcljId = lcljId;
    }

    public String getLcljNum() {
        return lcljNum;
    }

    public void setLcljNum(String lcljNum) {
        this.lcljNum = lcljNum;
    }

    public String getIsBloodCoagulation() {
        return isBloodCoagulation;
    }

    public void setIsBloodCoagulation(String isBloodCoagulation) {
        this.isBloodCoagulation = isBloodCoagulation;
    }

    public String getIsHepatitisC() {
        return isHepatitisC;
    }

    public void setIsHepatitisC(String isHepatitisC) {
        this.isHepatitisC = isHepatitisC;
    }

    public String getIsHIV() {
        return isHIV;
    }

    public void setIsHIV(String isHIV) {
        this.isHIV = isHIV;
    }

    public String getHiv() {
        return hiv;
    }

    public void setHiv(String hiv) {
        this.hiv = hiv;
    }

    public String getIsMaxillofacial() {
        return isMaxillofacial;
    }

    public void setIsMaxillofacial(String isMaxillofacial) {
        this.isMaxillofacial = isMaxillofacial;
    }

    public String getIsMucousMembrane() {
        return isMucousMembrane;
    }

    public void setIsMucousMembrane(String isMucousMembrane) {
        this.isMucousMembrane = isMucousMembrane;
    }

    public String getIsPharmacy() {
        return isPharmacy;
    }

    public void setIsPharmacy(String isPharmacy) {
        this.isPharmacy = isPharmacy;
    }

    public String getIsGlucocorticoids() {
        return isGlucocorticoids;
    }

    public void setIsGlucocorticoids(String isGlucocorticoids) {
        this.isGlucocorticoids = isGlucocorticoids;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getIsHearDiease() {
        return isHearDiease;
    }

    public void setIsHearDiease(String isHearDiease) {
        this.isHearDiease = isHearDiease;
    }

    public String getPatientTime() {
        return patientTime;
    }

    public void setPatientTime(String patientTime) {
        this.patientTime = patientTime;
    }

    @Override
    public String toString() {
        return "ZzblAdvice{" +
                "SEQ_ID='" + SEQ_ID + '\'' +
                ", lcljId='" + lcljId + '\'' +
                ", lcljNum='" + lcljNum + '\'' +
                ", patient_num='" + patient_num + '\'' +
                ", symptom='" + symptom + '\'' +
                ", toothloseMapUpLeft='" + toothloseMapUpLeft + '\'' +
                ", toothloseMapDownLeft='" + toothloseMapDownLeft + '\'' +
                ", toothloseMapUpRight='" + toothloseMapUpRight + '\'' +
                ", toothloseMapDownRight='" + toothloseMapDownRight + '\'' +
                ", toothlessMapUpLeft='" + toothlessMapUpLeft + '\'' +
                ", toothlessMapDownLeft='" + toothlessMapDownLeft + '\'' +
                ", toothlessMapUpRight='" + toothlessMapUpRight + '\'' +
                ", toothlessMapDownRight='" + toothlessMapDownRight + '\'' +
                ", toothdecayedMapUpLeft='" + toothdecayedMapUpLeft + '\'' +
                ", toothdecayedMapDownLeft='" + toothdecayedMapDownLeft + '\'' +
                ", toothdecayedMapUpRight='" + toothdecayedMapUpRight + '\'' +
                ", toothdecayedMapDownRight='" + toothdecayedMapDownRight + '\'' +
                ", toothsnapMapUpLeft='" + toothsnapMapUpLeft + '\'' +
                ", toothsnapMapDownLeft='" + toothsnapMapDownLeft + '\'' +
                ", toothsnapMapUpRight='" + toothsnapMapUpRight + '\'' +
                ", toothsnapMapDownRight='" + toothsnapMapDownRight + '\'' +
                ", toothlosetime='" + toothlosetime + '\'' +
                ", toothlesstime='" + toothlesstime + '\'' +
                ", toothdecayedtime='" + toothdecayedtime + '\'' +
                ", toothsnaptime='" + toothsnaptime + '\'' +
                ", implantRestoration='" + implantRestoration + '\'' +
                ", isHypertension='" + isHypertension + '\'' +
                ", hypertension='" + hypertension + '\'' +
                ", isTakeMedicie='" + isTakeMedicie + '\'' +
                ", pressure='" + pressure + '\'' +
                ", hypertensionmedicine='" + hypertensionmedicine + '\'' +
                ", isHearDiease='" + isHearDiease + '\'' +
                ", hearDiease='" + hearDiease + '\'' +
                ", isPrepareMedication='" + isPrepareMedication + '\'' +
                ", heardieasemedicine='" + heardieasemedicine + '\'' +
                ", isDiabetes='" + isDiabetes + '\'' +
                ", diabetes='" + diabetes + '\'' +
                ", dietControl='" + dietControl + '\'' +
                ", diabetesoralmedicine='" + diabetesoralmedicine + '\'' +
                ", diabetesinjectionmedicine='" + diabetesinjectionmedicine + '\'' +
                ", isheartoperation='" + isheartoperation + '\'' +
                ", isheartinfarction='" + isheartinfarction + '\'' +
                ", isBloodCoagulation='" + isBloodCoagulation + '\'' +
                ", bloodCoagulation='" + bloodCoagulation + '\'' +
                ", isAntiFreezing='" + isAntiFreezing + '\'' +
                ", antifreezingmedicine='" + antifreezingmedicine + '\'' +
                ", antiFreezingTime='" + antiFreezingTime + '\'' +
                ", isHepatitisB='" + isHepatitisB + '\'' +
                ", hepatitisB='" + hepatitisB + '\'' +
                ", isHepatitisC='" + isHepatitisC + '\'' +
                ", hepatitisC='" + hepatitisC + '\'' +
                ", isHIV='" + isHIV + '\'' +
                ", hiv='" + hiv + '\'' +
                ", isCancer='" + isCancer + '\'' +
                ", cancer='" + cancer + '\'' +
                ", isYphilis='" + isYphilis + '\'' +
                ", syphilis='" + syphilis + '\'' +
                ", isMaxillofacial='" + isMaxillofacial + '\'' +
                ", maxillofacial='" + maxillofacial + '\'' +
                ", isInflammation='" + isInflammation + '\'' +
                ", inflammation='" + inflammation + '\'' +
                ", isDrugAbuse='" + isDrugAbuse + '\'' +
                ", drugAbuse='" + drugAbuse + '\'' +
                ", isPsychosis='" + isPsychosis + '\'' +
                ", psychosis='" + psychosis + '\'' +
                ", isMucousMembrane='" + isMucousMembrane + '\'' +
                ", mucousMembrane='" + mucousMembrane + '\'' +
                ", isPharmacy='" + isPharmacy + '\'' +
                ", pharmacy='" + pharmacy + '\'' +
                ", takeorallytime='" + takeorallytime + '\'' +
                ", pharmacymedicine='" + pharmacymedicine + '\'' +
                ", isGlucocorticoids='" + isGlucocorticoids + '\'' +
                ", glucocorticoids='" + glucocorticoids + '\'' +
                ", glucocorticoidsmedicine='" + glucocorticoidsmedicine + '\'' +
                ", isOtherDiseases='" + isOtherDiseases + '\'' +
                ", otherdiseasestext='" + otherdiseasestext + '\'' +
                ", otherDiseases='" + otherDiseases + '\'' +
                ", isDrugAllergy='" + isDrugAllergy + '\'' +
                ", allergicLength='" + allergicLength + '\'' +
                ", drugallergy='" + drugallergy + '\'' +
                ", isPregnancy='" + isPregnancy + '\'' +
                ", pregnancy='" + pregnancy + '\'' +
                ", onMedication='" + onMedication + '\'' +
                ", onmedicationtime='" + onmedicationtime + '\'' +
                ", issmoke='" + issmoke + '\'' +
                ", smoketime='" + smoketime + '\'' +
                ", smokeNum='" + smokeNum + '\'' +
                ", isdrink='" + isdrink + '\'' +
                ", drinkTime='" + drinkTime + '\'' +
                ", drinkScale='" + drinkScale + '\'' +
                ", isodontoprisis='" + isodontoprisis + '\'' +
                ", odontoprisis='" + odontoprisis + '\'' +
                ", odontoprisisDegree='" + odontoprisisDegree + '\'' +
                ", chewingHabits='" + chewingHabits + '\'' +
                ", chewinghabitstime='" + chewinghabitstime + '\'' +
                ", others='" + others + '\'' +
                ", isteethclean='" + isteethclean + '\'' +
                ", teethcleannum='" + teethcleannum + '\'' +
                ", planttime='" + planttime + '\'' +
                ", patientTime='" + patientTime + '\'' +
                ", doctorTime='" + doctorTime + '\'' +
                ", patientsignature='" + patientsignature + '\'' +
                ", doctorsignature='" + doctorsignature + '\'' +
                ", organization='" + organization + '\'' +
                ", createuser='" + createuser + '\'' +
                ", createtime='" + createtime + '\'' +
                ", status='" + status + '\'' +
                ", updateuser='" + updateuser + '\'' +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }
}
