/**  
  *
  * @Title:  PreoperativeVerification.java   
  * @Package com.hudh.lclj.entity   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年5月28日 上午8:58:08   
  * @version V1.0  
  */ 
package com.hudh.lclj.entity;

import java.io.Serializable;

/**  
  * 
  * @ClassName:  PreoperativeVerification   
  * @Description:TODO(术前核查)   
  * @author: 海德堡联合口腔
  * @date:   2019年5月28日 上午8:58:08   
  *      
  */
public class PreoperativeVerification implements Serializable{

	/**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	private static final long serialVersionUID = 8216833106695226786L;

						/**
						  * 术前核查单Id
						  */
						private String SEQ_ID;
						/**
						 * 临床路径Id
						 */
						private String lclj_Id;
						/**
						 * 病历编号
						 */
						private String order_number;
						/**
						 * 患者姓名
						 */
						private  String	patient_name;
						/**
						 * 患者性别
						 */
						private String patient_sex;
						/**
						 * 患者年龄
						 */
						private String patient_age;
						/**
						 * 血压
						 */
						private String blood_pressure;
						/**
						 * 脉搏
						 */
						private String pulse;
						/**
						 * 血糖
						 */
			        	private String blood_glucose;
			        	/**
			        	 * 凝血功能
			        	 */
			        	private String cruor_function;
			        	/**
			        	 * 感染性疾病
			        	 */
			        	private String infectious_diseases;
			        	/**
			        	 * 会诊意见
			        	 */
			        	 private String consultation_opinion;
			        	 /**
			        	  * 拔牙位
			        	  */
			        	 private String pullout_tooth;
			        	 /**
			        	  * 种植牙位
			        	  */
			        	 private String plant_tooth;
			        	 /**
			        	  * 辅助手术
			        	  */
			        	 private String assist_operation;
			        	 /**
			        	  * 种植系统
			        	  */
			        	 private String plant_system;
			        	 /**
			        	  * 手术护士签名
			        	  */
			        	 private String nurseSignature;
			        	 /**
			        	  * 手术医生签名
			        	  */
			        	 private String doctorSignature;
			        	 /**
			        	  * 签名日期
			        	  */
			        	 private String signaturetime;
			        	 /**
			        	  * 修改后全部文字
			        	  */
			        	 private String operationAlltext;
			        	 
			        	 /**
			        	  * 备用字段
			        	  */
			        	 private String beiyong1;
			        	 /**
			        	  * 备用字段
			        	  */
			        	 private String beiyong2;
			        	 /**
			        	  * 备用字段
			        	  */
			        	 private String beiyong3;
			        	 
			        	 /**
			        	  * 创建时间
			        	  */
			        	 private String createtime;
			        	 //添加字段
			        	 private String upleftToothBitOne;
			        	 private String uperRightToothBitOne;
			        	 private String leftLowerToothBitOne;
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
						  * @Title:  getSerialversionuid <BR>  
						  * @Description: please write your description <BR>  
						  * @return: long <BR>  
						  */
						public static long getSerialversionuid() {
							return serialVersionUID;
						}

						private String lowRightToothBitOne;
			        	 private String upleftToothBitTwo;
			        	 private String uperRightToothBitTwo;
			        	 private String leftLowerToothBitTwo;
			        	 private String lowRightToothBitTwo;

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
						  * @Title:  getLclj_Id <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getLclj_Id() {
							return lclj_Id;
						}

						/**  
						  * @Title:  setLclj_Id <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setLclj_Id(String lclj_Id) {
							this.lclj_Id = lclj_Id;
						}

						/**  
						  * @Title:  getOrder_number <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getOrder_number() {
							return order_number;
						}

						/**  
						  * @Title:  setOrder_number <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setOrder_number(String order_number) {
							this.order_number = order_number;
						}

						/**  
						  * @Title:  getPatient_name <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getPatient_name() {
							return patient_name;
						}

						/**  
						  * @Title:  setPatient_name <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setPatient_name(String patient_name) {
							this.patient_name = patient_name;
						}

						/**  
						  * @Title:  getPatient_sex <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getPatient_sex() {
							return patient_sex;
						}

						/**  
						  * @Title:  setPatient_sex <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setPatient_sex(String patient_sex) {
							this.patient_sex = patient_sex;
						}

						/**  
						  * @Title:  getPatient_age <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getPatient_age() {
							return patient_age;
						}

						/**  
						  * @Title:  setPatient_age <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setPatient_age(String patient_age) {
							this.patient_age = patient_age;
						}

						/**  
						  * @Title:  getBlood_pressure <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getBlood_pressure() {
							return blood_pressure;
						}

						/**  
						  * @Title:  setBlood_pressure <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setBlood_pressure(String blood_pressure) {
							this.blood_pressure = blood_pressure;
						}

						/**  
						  * @Title:  getPulse <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getPulse() {
							return pulse;
						}

						/**  
						  * @Title:  setPulse <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setPulse(String pulse) {
							this.pulse = pulse;
						}

						/**  
						  * @Title:  getBlood_glucose <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getBlood_glucose() {
							return blood_glucose;
						}

						/**  
						  * @Title:  setBlood_glucose <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setBlood_glucose(String blood_glucose) {
							this.blood_glucose = blood_glucose;
						}

						/**  
						  * @Title:  getCruor_function <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getCruor_function() {
							return cruor_function;
						}

						/**  
						  * @Title:  setCruor_function <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setCruor_function(String cruor_function) {
							this.cruor_function = cruor_function;
						}

						/**  
						  * @Title:  getInfectious_diseases <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getInfectious_diseases() {
							return infectious_diseases;
						}

						/**  
						  * @Title:  setInfectious_diseases <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setInfectious_diseases(String infectious_diseases) {
							this.infectious_diseases = infectious_diseases;
						}

						/**  
						  * @Title:  getConsultation_opinion <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getConsultation_opinion() {
							return consultation_opinion;
						}

						/**  
						  * @Title:  setConsultation_opinion <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setConsultation_opinion(String consultation_opinion) {
							this.consultation_opinion = consultation_opinion;
						}

						/**  
						  * @Title:  getPullout_tooth <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getPullout_tooth() {
							return pullout_tooth;
						}

						/**  
						  * @Title:  setPullout_tooth <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setPullout_tooth(String pullout_tooth) {
							this.pullout_tooth = pullout_tooth;
						}

						/**  
						  * @Title:  getPlant_tooth <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getPlant_tooth() {
							return plant_tooth;
						}

						/**  
						  * @Title:  setPlant_tooth <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setPlant_tooth(String plant_tooth) {
							this.plant_tooth = plant_tooth;
						}

						/**  
						  * @Title:  getAssist_operation <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getAssist_operation() {
							return assist_operation;
						}

						/**  
						  * @Title:  setAssist_operation <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setAssist_operation(String assist_operation) {
							this.assist_operation = assist_operation;
						}

						/**  
						  * @Title:  getPlant_system <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getPlant_system() {
							return plant_system;
						}

						/**  
						  * @Title:  setPlant_system <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setPlant_system(String plant_system) {
							this.plant_system = plant_system;
						}

						/**  
						  * @Title:  getNurseSignature <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getNurseSignature() {
							return nurseSignature;
						}

						/**  
						  * @Title:  setNurseSignature <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setNurseSignature(String nurseSignature) {
							this.nurseSignature = nurseSignature;
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
						  * @Title:  getSignaturetime <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getSignaturetime() {
							return signaturetime;
						}

						/**  
						  * @Title:  setSignaturetime <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setSignaturetime(String signaturetime) {
							this.signaturetime = signaturetime;
						}

						/**  
						  * @Title:  getOperationAlltext <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getOperationAlltext() {
							return operationAlltext;
						}

						/**  
						  * @Title:  setOperationAlltext <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setOperationAlltext(String operationAlltext) {
							this.operationAlltext = operationAlltext;
						}

						/**  
						  * @Title:  getBeiyong1 <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getBeiyong1() {
							return beiyong1;
						}

						/**  
						  * @Title:  setBeiyong1 <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setBeiyong1(String beiyong1) {
							this.beiyong1 = beiyong1;
						}

						/**  
						  * @Title:  getBeiyong2 <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getBeiyong2() {
							return beiyong2;
						}

						/**  
						  * @Title:  setBeiyong2 <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setBeiyong2(String beiyong2) {
							this.beiyong2 = beiyong2;
						}

						/**  
						  * @Title:  getBeiyong3 <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public String getBeiyong3() {
							return beiyong3;
						}

						/**  
						  * @Title:  setBeiyong3 <BR>  
						  * @Description: please write your description <BR>  
						  * @return: String <BR>  
						  */
						public void setBeiyong3(String beiyong3) {
							this.beiyong3 = beiyong3;
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
						  * <p>Title: toString</p>   
						  * <p>Description: </p>   
						  * @return   
						  * @see Object#toString()
						  */  
						@Override
						public String toString() {
							return "PreoperativeVerification [SEQ_ID=" + SEQ_ID + ", lclj_Id=" + lclj_Id
									+ ", order_number=" + order_number + ", patient_name=" + patient_name
									+ ", patient_sex=" + patient_sex + ", patient_age=" + patient_age
									+ ", blood_pressure=" + blood_pressure + ", pulse=" + pulse + ", blood_glucose="
									+ blood_glucose + ", cruor_function=" + cruor_function + ", infectious_diseases="
									+ infectious_diseases + ", consultation_opinion=" + consultation_opinion
									+ ", pullout_tooth=" + pullout_tooth + ", plant_tooth=" + plant_tooth
									+ ", assist_operation=" + assist_operation + ", plant_system=" + plant_system
									+ ", nurseSignature=" + nurseSignature + ", doctorSignature=" + doctorSignature
									+ ", signaturetime=" + signaturetime + ", operationAlltext=" + operationAlltext
									+ ", beiyong1=" + beiyong1 + ", beiyong2=" + beiyong2 + ", beiyong3=" + beiyong3
									+ ", createtime=" + createtime + "]";
						}
			        	
}
