/**
 * @Fields 手术--术前核查单（医护及医患） : (描述这个文件表示什么)
 */
package com.hudh.lclj.entity;

import java.io.Serializable;

/**
 * @version V1.0
 * @Title: Verification
 * @author: dyh
 * @date: 2020-06-12 14:25
 */
public class LcljVerification implements Serializable {

    //主键Id
    private String seqId;
    //患者Id
    private String userId;
    //临床Id
    private String lcljId;
    //临床编号
    private String orderNumber;
    //手术医生
    private String patientDoctor;
    //是否已服用抗菌药
    private String antibacterialMedicine;
    //是否碘伏过敏
    private String iodineAllergy;
    //是否麻醉药物过敏
    private String anestheticAllergy;
    //拔牙位左上
    private String uplefttoothbitone;
    //拔牙位右上
    private String uperrighttoothbitone;
    //拔牙位左下
    private String leftlowertoothbitone;
    //拔牙位右下
    private String lowrighttoothbitone;
    //种植牙位左上
    private String uplefttoothbittwo;
    //种植牙位右上
    private String uperrighttoothbittwo;
    //种植牙位左下
    private String leftlowertoothbittwo;
    //种植牙位右下
    private String lowrighttoothbittwo;
    //辅助手术
    private String assistOperation;
    //种植系统
    private String plantSystem;
    //患者签字时间
    private String patienttime;
    //护士签字时间
    private String nursetimeone;
    //手术时间
    private String operationtime;
    //血压
    private String bloodPressure;
    //脉搏
    private String pulse;
    //血糖
    private String bloodGlucose;
    //凝血功能
    private String cruorFunction;
    //白细胞数
    private String whitebloodCell;
    //中性粒细胞
    private String neutrophileCell;
    //红细胞数
    private String redbloodCell;
    //血红蛋白
    private String oxyphorase;
    //术前用药
    private String premedicate;
    //用药小时
    private String takemedicineHour;
    //用药分钟
    private String takemedicineMinutes;
    //用药剂量
    private String takemedicineMeasure;
    //会诊意见
    private String consultationOpinion;
    //护士签字时间
    private String nursesignaturetime;
    //医生签字时间
    private String doctorsignaturetime;

    private String createuser;

    private String createtime;

    private String organization;

    //医生签字
    private String doctorSignature;
    //护士签字1
    private String nurseSignature1;
    //护士签字2
    private String nurseSignature2;
    //患者签字
    private String patientSignature;
    /**
     * 获取 主键Id
     *
     * @return seqId 主键Id
     */
    public String getSeqId() {
        return this.seqId;
    }

    /**
     * 设置 主键Id
     *
     * @param seqId 主键Id
     */
    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    /**
     * 获取 患者Id
     *
     * @return userId 患者Id
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * 设置 患者Id
     *
     * @param userId 患者Id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取 临床Id
     *
     * @return lcljId 临床Id
     */
    public String getLcljId() {
        return this.lcljId;
    }

    /**
     * 设置 临床Id
     *
     * @param lcljId 临床Id
     */
    public void setLcljId(String lcljId) {
        this.lcljId = lcljId;
    }

    /**
     * 获取 临床编号
     *
     * @return orderNumber 临床编号
     */
    public String getOrderNumber() {
        return this.orderNumber;
    }

    /**
     * 设置 临床编号
     *
     * @param orderNumber 临床编号
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * 获取 手术医生
     *
     * @return patientDoctor 手术医生
     */
    public String getPatientDoctor() {
        return this.patientDoctor;
    }

    /**
     * 设置 手术医生
     *
     * @param patientDoctor 手术医生
     */
    public void setPatientDoctor(String patientDoctor) {
        this.patientDoctor = patientDoctor;
    }

    /**
     * 获取 是否已服用抗菌药
     *
     * @return antibacterialMedicine 是否已服用抗菌药
     */
    public String getAntibacterialMedicine() {
        return this.antibacterialMedicine;
    }

    /**
     * 设置 是否已服用抗菌药
     *
     * @param antibacterialMedicine 是否已服用抗菌药
     */
    public void setAntibacterialMedicine(String antibacterialMedicine) {
        this.antibacterialMedicine = antibacterialMedicine;
    }

    /**
     * 获取 是否碘伏过敏
     *
     * @return iodineAllergy 是否碘伏过敏
     */
    public String getIodineAllergy() {
        return this.iodineAllergy;
    }

    /**
     * 设置 是否碘伏过敏
     *
     * @param iodineAllergy 是否碘伏过敏
     */
    public void setIodineAllergy(String iodineAllergy) {
        this.iodineAllergy = iodineAllergy;
    }

    /**
     * 获取 是否麻醉药物过敏
     *
     * @return anestheticAllergy 是否麻醉药物过敏
     */
    public String getAnestheticAllergy() {
        return this.anestheticAllergy;
    }

    /**
     * 设置 是否麻醉药物过敏
     *
     * @param anestheticAllergy 是否麻醉药物过敏
     */
    public void setAnestheticAllergy(String anestheticAllergy) {
        this.anestheticAllergy = anestheticAllergy;
    }

    /**
     * 获取 拔牙位左上
     *
     * @return uplefttoothbitone 拔牙位左上
     */
    public String getUplefttoothbitone() {
        return this.uplefttoothbitone;
    }

    /**
     * 设置 拔牙位左上
     *
     * @param uplefttoothbitone 拔牙位左上
     */
    public void setUplefttoothbitone(String uplefttoothbitone) {
        this.uplefttoothbitone = uplefttoothbitone;
    }

    /**
     * 获取 拔牙位右上
     *
     * @return uperrighttoothbitone 拔牙位右上
     */
    public String getUperrighttoothbitone() {
        return this.uperrighttoothbitone;
    }

    /**
     * 设置 拔牙位右上
     *
     * @param uperrighttoothbitone 拔牙位右上
     */
    public void setUperrighttoothbitone(String uperrighttoothbitone) {
        this.uperrighttoothbitone = uperrighttoothbitone;
    }

    /**
     * 获取 拔牙位左下
     *
     * @return leftlowertoothbitone 拔牙位左下
     */
    public String getLeftlowertoothbitone() {
        return this.leftlowertoothbitone;
    }

    /**
     * 设置 拔牙位左下
     *
     * @param leftlowertoothbitone 拔牙位左下
     */
    public void setLeftlowertoothbitone(String leftlowertoothbitone) {
        this.leftlowertoothbitone = leftlowertoothbitone;
    }

    /**
     * 获取 拔牙位右下
     *
     * @return lowrighttoothbitone 拔牙位右下
     */
    public String getLowrighttoothbitone() {
        return this.lowrighttoothbitone;
    }

    /**
     * 设置 拔牙位右下
     *
     * @param lowrighttoothbitone 拔牙位右下
     */
    public void setLowrighttoothbitone(String lowrighttoothbitone) {
        this.lowrighttoothbitone = lowrighttoothbitone;
    }

    /**
     * 获取 种植牙位左上
     *
     * @return uplefttoothbittwo 种植牙位左上
     */
    public String getUplefttoothbittwo() {
        return this.uplefttoothbittwo;
    }

    /**
     * 设置 种植牙位左上
     *
     * @param uplefttoothbittwo 种植牙位左上
     */
    public void setUplefttoothbittwo(String uplefttoothbittwo) {
        this.uplefttoothbittwo = uplefttoothbittwo;
    }

    /**
     * 获取 种植牙位右上
     *
     * @return uperrighttoothbittwo 种植牙位右上
     */
    public String getUperrighttoothbittwo() {
        return this.uperrighttoothbittwo;
    }

    /**
     * 设置 种植牙位右上
     *
     * @param uperrighttoothbittwo 种植牙位右上
     */
    public void setUperrighttoothbittwo(String uperrighttoothbittwo) {
        this.uperrighttoothbittwo = uperrighttoothbittwo;
    }

    /**
     * 获取 种植牙位左下
     *
     * @return leftlowertoothbittwo 种植牙位左下
     */
    public String getLeftlowertoothbittwo() {
        return this.leftlowertoothbittwo;
    }

    /**
     * 设置 种植牙位左下
     *
     * @param leftlowertoothbittwo 种植牙位左下
     */
    public void setLeftlowertoothbittwo(String leftlowertoothbittwo) {
        this.leftlowertoothbittwo = leftlowertoothbittwo;
    }

    /**
     * 获取 种植牙位右下
     *
     * @return lowrighttoothbittwo 种植牙位右下
     */
    public String getLowrighttoothbittwo() {
        return this.lowrighttoothbittwo;
    }

    /**
     * 设置 种植牙位右下
     *
     * @param lowrighttoothbittwo 种植牙位右下
     */
    public void setLowrighttoothbittwo(String lowrighttoothbittwo) {
        this.lowrighttoothbittwo = lowrighttoothbittwo;
    }

    /**
     * 获取 辅助手术
     *
     * @return assistOperation 辅助手术
     */
    public String getAssistOperation() {
        return this.assistOperation;
    }

    /**
     * 设置 辅助手术
     *
     * @param assistOperation 辅助手术
     */
    public void setAssistOperation(String assistOperation) {
        this.assistOperation = assistOperation;
    }

    /**
     * 获取 种植系统
     *
     * @return plantSystem 种植系统
     */
    public String getPlantSystem() {
        return this.plantSystem;
    }

    /**
     * 设置 种植系统
     *
     * @param plantSystem 种植系统
     */
    public void setPlantSystem(String plantSystem) {
        this.plantSystem = plantSystem;
    }

    /**
     * 获取 患者签字时间
     *
     * @return patienttime 患者签字时间
     */
    public String getPatienttime() {
        return this.patienttime;
    }

    /**
     * 设置 患者签字时间
     *
     * @param patienttime 患者签字时间
     */
    public void setPatienttime(String patienttime) {
        this.patienttime = patienttime;
    }

    /**
     * 获取 护士签字时间
     *
     * @return nursetimeone 护士签字时间
     */
    public String getNursetimeone() {
        return this.nursetimeone;
    }

    /**
     * 设置 护士签字时间
     *
     * @param nursetimeone 护士签字时间
     */
    public void setNursetimeone(String nursetimeone) {
        this.nursetimeone = nursetimeone;
    }

    /**
     * 获取 手术时间
     *
     * @return operationtime 手术时间
     */
    public String getOperationtime() {
        return this.operationtime;
    }

    /**
     * 设置 手术时间
     *
     * @param operationtime 手术时间
     */
    public void setOperationtime(String operationtime) {
        this.operationtime = operationtime;
    }

    /**
     * 获取 血压
     *
     * @return bloodPressure 血压
     */
    public String getBloodPressure() {
        return this.bloodPressure;
    }

    /**
     * 设置 血压
     *
     * @param bloodPressure 血压
     */
    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    /**
     * 获取 脉搏
     *
     * @return pulse 脉搏
     */
    public String getPulse() {
        return this.pulse;
    }

    /**
     * 设置 脉搏
     *
     * @param pulse 脉搏
     */
    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    /**
     * 获取 血糖
     *
     * @return bloodGlucose 血糖
     */
    public String getBloodGlucose() {
        return this.bloodGlucose;
    }

    /**
     * 设置 血糖
     *
     * @param bloodGlucose 血糖
     */
    public void setBloodGlucose(String bloodGlucose) {
        this.bloodGlucose = bloodGlucose;
    }

    /**
     * 获取 凝血功能
     *
     * @return cruorFunction 凝血功能
     */
    public String getCruorFunction() {
        return this.cruorFunction;
    }

    /**
     * 设置 凝血功能
     *
     * @param cruorFunction 凝血功能
     */
    public void setCruorFunction(String cruorFunction) {
        this.cruorFunction = cruorFunction;
    }

    /**
     * 获取 白细胞数
     *
     * @return whitebloodCell 白细胞数
     */
    public String getWhitebloodCell() {
        return this.whitebloodCell;
    }

    /**
     * 设置 白细胞数
     *
     * @param whitebloodCell 白细胞数
     */
    public void setWhitebloodCell(String whitebloodCell) {
        this.whitebloodCell = whitebloodCell;
    }

    /**
     * 获取 中性粒细胞
     *
     * @return neutrophileCell 中性粒细胞
     */
    public String getNeutrophileCell() {
        return this.neutrophileCell;
    }

    /**
     * 设置 中性粒细胞
     *
     * @param neutrophileCell 中性粒细胞
     */
    public void setNeutrophileCell(String neutrophileCell) {
        this.neutrophileCell = neutrophileCell;
    }

    /**
     * 获取 红细胞数
     *
     * @return redbloodCell 红细胞数
     */
    public String getRedbloodCell() {
        return this.redbloodCell;
    }

    /**
     * 设置 红细胞数
     *
     * @param redbloodCell 红细胞数
     */
    public void setRedbloodCell(String redbloodCell) {
        this.redbloodCell = redbloodCell;
    }

    /**
     * 获取 血红蛋白
     *
     * @return oxyphorase 血红蛋白
     */
    public String getOxyphorase() {
        return this.oxyphorase;
    }

    /**
     * 设置 血红蛋白
     *
     * @param oxyphorase 血红蛋白
     */
    public void setOxyphorase(String oxyphorase) {
        this.oxyphorase = oxyphorase;
    }

    /**
     * 获取 术前用药
     *
     * @return premedicate 术前用药
     */
    public String getPremedicate() {
        return this.premedicate;
    }

    /**
     * 设置 术前用药
     *
     * @param premedicate 术前用药
     */
    public void setPremedicate(String premedicate) {
        this.premedicate = premedicate;
    }

    /**
     * 获取 用药小时
     *
     * @return takemedicineHour 用药小时
     */
    public String getTakemedicineHour() {
        return this.takemedicineHour;
    }

    /**
     * 设置 用药小时
     *
     * @param takemedicineHour 用药小时
     */
    public void setTakemedicineHour(String takemedicineHour) {
        this.takemedicineHour = takemedicineHour;
    }

    /**
     * 获取 用药分钟
     *
     * @return takemedicineMinutes 用药分钟
     */
    public String getTakemedicineMinutes() {
        return this.takemedicineMinutes;
    }

    /**
     * 设置 用药分钟
     *
     * @param takemedicineMinutes 用药分钟
     */
    public void setTakemedicineMinutes(String takemedicineMinutes) {
        this.takemedicineMinutes = takemedicineMinutes;
    }

    /**
     * 获取 用药剂量
     *
     * @return takemedicineMeasure 用药剂量
     */
    public String getTakemedicineMeasure() {
        return this.takemedicineMeasure;
    }

    /**
     * 设置 用药剂量
     *
     * @param takemedicineMeasure 用药剂量
     */
    public void setTakemedicineMeasure(String takemedicineMeasure) {
        this.takemedicineMeasure = takemedicineMeasure;
    }

    /**
     * 获取 会诊意见
     *
     * @return consultationOpinion 会诊意见
     */
    public String getConsultationOpinion() {
        return this.consultationOpinion;
    }

    /**
     * 设置 会诊意见
     *
     * @param consultationOpinion 会诊意见
     */
    public void setConsultationOpinion(String consultationOpinion) {
        this.consultationOpinion = consultationOpinion;
    }

    /**
     * 获取 护士签字时间
     *
     * @return nursesignaturetime 护士签字时间
     */
    public String getNursesignaturetime() {
        return this.nursesignaturetime;
    }

    /**
     * 设置 护士签字时间
     *
     * @param nursesignaturetime 护士签字时间
     */
    public void setNursesignaturetime(String nursesignaturetime) {
        this.nursesignaturetime = nursesignaturetime;
    }

    /**
     * 获取 医生签字时间
     *
     * @return doctorsignaturetime 医生签字时间
     */
    public String getDoctorsignaturetime() {
        return this.doctorsignaturetime;
    }

    /**
     * 设置 医生签字时间
     *
     * @param doctorsignaturetime 医生签字时间
     */
    public void setDoctorsignaturetime(String doctorsignaturetime) {
        this.doctorsignaturetime = doctorsignaturetime;
    }

    /**
     * 获取
     *
     * @return createuser
     */
    public String getCreateuser() {
        return this.createuser;
    }

    /**
     * 设置
     *
     * @param createuser
     */
    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    /**
     * 获取
     *
     * @return createtime
     */
    public String getCreatetime() {
        return this.createtime;
    }

    /**
     * 设置
     *
     * @param createtime
     */
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取
     *
     * @return Organization
     */
    public String getOrganization() {
        return this.organization;
    }

    /**
     * 设置
     *
     * @param Organization
     */
    public void setOrganization(String Organization) {
        this.organization = Organization;
    }

    public String getDoctorSignature() {
        return doctorSignature;
    }

    public void setDoctorSignature(String doctorSignature) {
        this.doctorSignature = doctorSignature;
    }

    public String getNurseSignature1() {
        return nurseSignature1;
    }

    public void setNurseSignature1(String nurseSignature1) {
        this.nurseSignature1 = nurseSignature1;
    }

    public String getNurseSignature2() {
        return nurseSignature2;
    }

    public void setNurseSignature2(String nurseSignature2) {
        this.nurseSignature2 = nurseSignature2;
    }

    public String getPatientSignature() {
        return patientSignature;
    }

    public void setPatientSignature(String patientSignature) {
        this.patientSignature = patientSignature;
    }

    @Override
    public String toString() {
        return "LcljVerification{" +
                "seqId='" + seqId + '\'' +
                ", userId='" + userId + '\'' +
                ", lcljId='" + lcljId + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", patientDoctor='" + patientDoctor + '\'' +
                ", antibacterialMedicine='" + antibacterialMedicine + '\'' +
                ", iodineAllergy='" + iodineAllergy + '\'' +
                ", anestheticAllergy='" + anestheticAllergy + '\'' +
                ", uplefttoothbitone='" + uplefttoothbitone + '\'' +
                ", uperrighttoothbitone='" + uperrighttoothbitone + '\'' +
                ", leftlowertoothbitone='" + leftlowertoothbitone + '\'' +
                ", lowrighttoothbitone='" + lowrighttoothbitone + '\'' +
                ", uplefttoothbittwo='" + uplefttoothbittwo + '\'' +
                ", uperrighttoothbittwo='" + uperrighttoothbittwo + '\'' +
                ", leftlowertoothbittwo='" + leftlowertoothbittwo + '\'' +
                ", lowrighttoothbittwo='" + lowrighttoothbittwo + '\'' +
                ", assistOperation='" + assistOperation + '\'' +
                ", plantSystem='" + plantSystem + '\'' +
                ", patienttime='" + patienttime + '\'' +
                ", nursetimeone='" + nursetimeone + '\'' +
                ", operationtime='" + operationtime + '\'' +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", pulse='" + pulse + '\'' +
                ", bloodGlucose='" + bloodGlucose + '\'' +
                ", cruorFunction='" + cruorFunction + '\'' +
                ", whitebloodCell='" + whitebloodCell + '\'' +
                ", neutrophileCell='" + neutrophileCell + '\'' +
                ", redbloodCell='" + redbloodCell + '\'' +
                ", oxyphorase='" + oxyphorase + '\'' +
                ", premedicate='" + premedicate + '\'' +
                ", takemedicineHour='" + takemedicineHour + '\'' +
                ", takemedicineMinutes='" + takemedicineMinutes + '\'' +
                ", takemedicineMeasure='" + takemedicineMeasure + '\'' +
                ", consultationOpinion='" + consultationOpinion + '\'' +
                ", nursesignaturetime='" + nursesignaturetime + '\'' +
                ", doctorsignaturetime='" + doctorsignaturetime + '\'' +
                ", createuser='" + createuser + '\'' +
                ", createtime='" + createtime + '\'' +
                ", organization='" + organization + '\'' +
                ", doctorSignature='" + doctorSignature + '\'' +
                ", nurseSignature1='" + nurseSignature1 + '\'' +
                ", nurseSignature2='" + nurseSignature2 + '\'' +
                ", patientSignature='" + patientSignature + '\'' +
                '}';
    }
}