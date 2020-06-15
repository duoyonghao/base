package com.hudh.zzbl.entity;

import java.io.Serializable;

/**
 	* 人工种植牙知情同意书
    * @ClassName: FamiliarBook
    * @Description: TODO(储存知情书重要数据及医生患者的签字信息)
    * @author admin
    * @dateTime 2019年5月4日 上午10:32:33
    *
    */
public class FamiliarBook implements Serializable{

	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	    
	private static final long serialVersionUID = 1L;
	
	/**
	 * 知情书ID
	 */
	private String SEQ_ID;
	/**
	 * 临床路径ID
	 */
	private String LcljId;
	/**
	 * 节点ID
	 */
	private String LcljNum;
	/**
	 * 种植体系
	 */
	private String plantingSystem;
	/**
	 * 种植型号
	 */
	private String modelNumber;
	/**
	 * 首植左上牙位
	 */
	private String upleftToothBitOne;
	/**
	 * 首植右上牙位
	 */
	private String uperRightToothBitOne;
	/**
	 * 首植左下牙位
	 */
	private String leftLowerToothBitOne;
	/**
	 * 首植右下牙位
	 */
	private String lowRightToothBitOne;
	/**
	 * 二植左上牙位
	 */
	private String upleftToothBitTwo;
	/**
	 * 二植右上牙位
	 */
	private String uperRightToothBitTwo;
	/**
	 * 二植左下牙位
	 */
	private String leftLowerToothBitTwo;
	/**
	 * 二植右下牙位
	 */
	private String lowRightToothBitTwo;
	/**
	 * 种植辅助手术
	 */
	private String assistOperation;
	/**
	 * 备注
	 */
	private String remarks;
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
	 * 预留字段1
	 */
	private String reservedField1;
	/**
	 * 预留字段2
	 */
	private String reservedField2;
	/**
	 * 预留字段3
	 */
	private String reservedField3;
	
	private String removableDenture; //添加字段 活动义齿修复
	private String fixedDenture; //固定义齿修复
	private String plantingsystemselect;//种植体系
	private String repairselect;//修复体系
	public String getRemovableDenture() {
		return removableDenture;
	}
	public void setRemovableDenture(String removableDenture) {
		this.removableDenture = removableDenture;
	}
	public String getFixedDenture() {
		return fixedDenture;
	}
	public void setFixedDenture(String fixedDenture) {
		this.fixedDenture = fixedDenture;
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
	public void setSEQ_ID(String sEQ_ID) {
		SEQ_ID = sEQ_ID;
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
	  * @Title:  getPlantingSystem <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPlantingSystem() {
		return plantingSystem;
	}
	/**  
	  * @Title:  setPlantingSystem <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPlantingSystem(String plantingSystem) {
		this.plantingSystem = plantingSystem;
	}
	/**  
	  * @Title:  getModelNumber <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getModelNumber() {
		return modelNumber;
	}
	/**  
	  * @Title:  setModelNumber <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	/**  
	  * @Title:  getUpleftToothBitOne <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUpleftToothBitOne() {
		return upleftToothBitOne;
	}
	/**  
	  * @Title:  setUpleftToothBitOne <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUpleftToothBitOne(String upleftToothBitOne) {
		this.upleftToothBitOne = upleftToothBitOne;
	}
	/**  
	  * @Title:  getUperRightToothBitOne <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUperRightToothBitOne() {
		return uperRightToothBitOne;
	}
	/**  
	  * @Title:  setUperRightToothBitOne <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUperRightToothBitOne(String uperRightToothBitOne) {
		this.uperRightToothBitOne = uperRightToothBitOne;
	}
	/**  
	  * @Title:  getLeftLowerToothBitOne <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLeftLowerToothBitOne() {
		return leftLowerToothBitOne;
	}
	/**  
	  * @Title:  setLeftLowerToothBitOne <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLeftLowerToothBitOne(String leftLowerToothBitOne) {
		this.leftLowerToothBitOne = leftLowerToothBitOne;
	}
	/**  
	  * @Title:  getLowRightToothBitOne <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLowRightToothBitOne() {
		return lowRightToothBitOne;
	}
	/**  
	  * @Title:  setLowRightToothBitOne <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLowRightToothBitOne(String lowRightToothBitOne) {
		this.lowRightToothBitOne = lowRightToothBitOne;
	}
	/**  
	  * @Title:  getUpleftToothBitTwo <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUpleftToothBitTwo() {
		return upleftToothBitTwo;
	}
	/**  
	  * @Title:  setUpleftToothBitTwo <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUpleftToothBitTwo(String upleftToothBitTwo) {
		this.upleftToothBitTwo = upleftToothBitTwo;
	}
	/**  
	  * @Title:  getUperRightToothBitTwo <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getUperRightToothBitTwo() {
		return uperRightToothBitTwo;
	}
	/**  
	  * @Title:  setUperRightToothBitTwo <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setUperRightToothBitTwo(String uperRightToothBitTwo) {
		this.uperRightToothBitTwo = uperRightToothBitTwo;
	}
	/**  
	  * @Title:  getLeftLowerToothBitTwo <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLeftLowerToothBitTwo() {
		return leftLowerToothBitTwo;
	}
	/**  
	  * @Title:  setLeftLowerToothBitTwo <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLeftLowerToothBitTwo(String leftLowerToothBitTwo) {
		this.leftLowerToothBitTwo = leftLowerToothBitTwo;
	}
	/**  
	  * @Title:  getLowRightToothBitTwo <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLowRightToothBitTwo() {
		return lowRightToothBitTwo;
	}
	/**  
	  * @Title:  setLowRightToothBitTwo <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLowRightToothBitTwo(String lowRightToothBitTwo) {
		this.lowRightToothBitTwo = lowRightToothBitTwo;
	}
	/**  
	  * @Title:  getAssistOperation <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getAssistOperation() {
		return assistOperation;
	}
	/**  
	  * @Title:  setAssistOperation <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setAssistOperation(String assistOperation) {
		this.assistOperation = assistOperation;
	}
	/**  
	  * @Title:  getRemarks <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getRemarks() {
		return remarks;
	}
	/**  
	  * @Title:  setRemarks <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	  * @Title:  getReservedField1 <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getReservedField1() {
		return reservedField1;
	}
	/**  
	  * @Title:  setReservedField1 <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setReservedField1(String reservedField1) {
		this.reservedField1 = reservedField1;
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
	  * @Title:  getSerialversionuid <BR>  
	  * @Description: please write your description <BR>  
	  * @return: long <BR>  
	  */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**   
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see Object#toString()
	  */  
	@Override
	public String toString() {
		return "FamiliarBook [SEQ_ID=" + SEQ_ID + ", LcljId=" + LcljId + ", LcljNum=" + LcljNum + ", plantingSystem="
				+ plantingSystem + ", modelNumber=" + modelNumber + ", upleftToothBitOne=" + upleftToothBitOne
				+ ", uperRightToothBitOne=" + uperRightToothBitOne + ", leftLowerToothBitOne=" + leftLowerToothBitOne
				+ ", lowRightToothBitOne=" + lowRightToothBitOne + ", upleftToothBitTwo=" + upleftToothBitTwo
				+ ", uperRightToothBitTwo=" + uperRightToothBitTwo + ", leftLowerToothBitTwo=" + leftLowerToothBitTwo
				+ ", lowRightToothBitTwo=" + lowRightToothBitTwo + ", assistOperation=" + assistOperation + ", remarks="
				+ remarks + ", PatientSignature=" + PatientSignature + ", PatientTime=" + PatientTime
				+ ", doctorSignature=" + doctorSignature + ", doctorTime=" + doctorTime + ", createtime=" + createtime
				+ ", createuser=" + createuser + ", reservedField1=" + reservedField1 + ", reservedField2="
				+ reservedField2 + ", reservedField3=" + reservedField3 + "]";
	}
	/**  
	  * @Title:  getPlantingsystemselect <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPlantingsystemselect() {
		return plantingsystemselect;
	}
	/**  
	  * @Title:  setPlantingsystemselect <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */  
	public void setPlantingsystemselect(String plantingsystemselect) {
		this.plantingsystemselect = plantingsystemselect;
	}
	public String getRepairselect() {
		return repairselect;
	}
	public void setRepairselect(String repairselect) {
		this.repairselect = repairselect;
	}
	
}
