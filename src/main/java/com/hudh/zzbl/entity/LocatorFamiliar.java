/**  
  *
  * @Title:  LocatorFamiliar.java   
  * @Package com.hudh.zzbl.entity   
  * @Description:    TODO(用一句话描述该文件做什么)   
  * @author: 海德堡联合空腔     
  * @date:   2019年7月11日 下午5:17:22   
  * @version V1.0  
  */ 
package com.hudh.zzbl.entity;

import java.io.Serializable;

/**  
  * 
  * @ClassName:  LocatorFamiliar   
  * @Description:TODO(locator 覆盖义齿(即刻负重) 知情书/把牙知情书  通用)   
  * @author: 海德堡联合口腔
  * @date:   2019年7月11日 下午5:17:22   
  *      
  */
public class LocatorFamiliar implements Serializable{

	/**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */   
	private static final long serialVersionUID = -8991506539607210316L;
	
	private String seqId;
	//临床历经Id
	private String lcljId;
	//临床路径编号
	private String lcljNum;
	//患者姓名
	private String name;
	//患者年龄
	private String age;
	//患者性别
	private String sex;
	//患者住址
	private String address;
	//患者电话
	private String phone;
	//药物过敏
	private String allergy;
	//诊断
	private String diagnose;
	//治疗部位
	private String treatmentParts;
	//治疗日期
	private String treatmentTime;
	//职业
	private String profession;
	//locator与拔牙区分标识
	private String classify;
	//医生签字
	private String doctorSignature;
	//签字时间
	private String  doctorTime;
	//患者签字
	private String patientSignature;
	//签字时间
	private String patientTime;
	//创建时间
	private String createtime;
	//备注
	private String remark;
	//备用字段1
	private String beiyong1;
	//备用字段2
	private String beiyong2;
	//备用字段3
	private String beiyong3;
	/**  
	  * @Title:  getSeqId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSeqId() {
		return seqId;
	}
	/**  
	  * @Title:  setSeqId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	/**  
	  * @Title:  getLcljId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLcljId() {
		return lcljId;
	}
	/**  
	  * @Title:  setLcljId <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLcljId(String lcljId) {
		this.lcljId = lcljId;
	}
	/**  
	  * @Title:  getLcljNum <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getLcljNum() {
		return lcljNum;
	}
	/**  
	  * @Title:  setLcljNum <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setLcljNum(String lcljNum) {
		this.lcljNum = lcljNum;
	}
	/**  
	  * @Title:  getName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getName() {
		return name;
	}
	/**  
	  * @Title:  setName <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setName(String name) {
		this.name = name;
	}
	/**  
	  * @Title:  getAge <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getAge() {
		return age;
	}
	/**  
	  * @Title:  setAge <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setAge(String age) {
		this.age = age;
	}
	/**  
	  * @Title:  getSex <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getSex() {
		return sex;
	}
	/**  
	  * @Title:  setSex <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**  
	  * @Title:  getAddress <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getAddress() {
		return address;
	}
	/**  
	  * @Title:  setAddress <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setAddress(String address) {
		this.address = address;
	}
	/**  
	  * @Title:  getPhone <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPhone() {
		return phone;
	}
	/**  
	  * @Title:  setPhone <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**  
	  * @Title:  getAllergy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getAllergy() {
		return allergy;
	}
	/**  
	  * @Title:  setAllergy <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}
	/**  
	  * @Title:  getDiagnose <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getDiagnose() {
		return diagnose;
	}
	/**  
	  * @Title:  setDiagnose <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}
	/**  
	  * @Title:  getTreatmentParts <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getTreatmentParts() {
		return treatmentParts;
	}
	/**  
	  * @Title:  setTreatmentParts <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setTreatmentParts(String treatmentParts) {
		this.treatmentParts = treatmentParts;
	}
	/**  
	  * @Title:  getTreatmentTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getTreatmentTime() {
		return treatmentTime;
	}
	/**  
	  * @Title:  setTreatmentTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setTreatmentTime(String treatmentTime) {
		this.treatmentTime = treatmentTime;
	}
	/**  
	  * @Title:  getProfession <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getProfession() {
		return profession;
	}
	/**  
	  * @Title:  setProfession <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setProfession(String profession) {
		this.profession = profession;
	}
	/**  
	  * @Title:  getClassify <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getClassify() {
		return classify;
	}
	/**  
	  * @Title:  setClassify <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setClassify(String classify) {
		this.classify = classify;
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
	  * @Title:  getPatientSignature <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPatientSignature() {
		return patientSignature;
	}
	/**  
	  * @Title:  setPatientSignature <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPatientSignature(String patientSignature) {
		this.patientSignature = patientSignature;
	}
	/**  
	  * @Title:  getPatientTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getPatientTime() {
		return patientTime;
	}
	/**  
	  * @Title:  setPatientTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setPatientTime(String patientTime) {
		this.patientTime = patientTime;
	}
	/**  
	  * @Title:  getCreatTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getCreattime() {
		return createtime;
	}
	/**  
	  * @Title:  setCreatTime <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setCreattime(String createtime) {
		this.createtime = createtime;
	}
	/**  
	  * @Title:  getRemak <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getRemark() {
		return remark;
	}
	/**  
	  * @Title:  setRemak <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setRemark(String remark) {
		this.remark = remark;
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
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see Object#toString()
	  */  
	@Override
	public String toString() {
		return "LocatorFamiliar [seqId=" + seqId + ", lcljId=" + lcljId + ", lcljNum=" + lcljNum + ", name=" + name
				+ ", age=" + age + ", sex=" + sex + ", address=" + address + ", phone=" + phone + ", allergy=" + allergy
				+ ", diagnose=" + diagnose + ", treatmentParts=" + treatmentParts + ", treatmentTime=" + treatmentTime
				+ ", profession=" + profession + ", classify=" + classify + ", doctorSignature=" + doctorSignature
				+ ", doctorTime=" + doctorTime + ", patientSignature=" + patientSignature + ", patientTime="
				+ patientTime + ", createtime=" + createtime + ", remark=" + remark + ", beiyong1=" + beiyong1
				+ ", beiyong2=" + beiyong2 + ", beiyong3=" + beiyong3 + "]";
	}
}
