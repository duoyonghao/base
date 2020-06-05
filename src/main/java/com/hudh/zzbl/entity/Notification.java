package com.hudh.zzbl.entity;

public class Notification {
	String SEQ_ID;//id
	String LcljId;//临床路径id
	String LcljNum;//临床路径编号
	String usercode;//患者编号
	String username;//患者姓名
	String content;//告知内容
	String createtime;//创建时间
	String doctortime;//医生签字时间
	String patienttime;//患者签字时间
	String whether;//有无
	String createuser;
	String organization;
	String patientsignature;
	String doctorsignature;
	public String getCreateuser() {
		return createuser;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public String getWhether() {
		return whether;
	}
	public void setWhether(String whether) {
		this.whether = whether;
	}
	public String getDoctortime() {
		return doctortime;
	}
	public void setDoctortime(String doctortime) {
		this.doctortime = doctortime;
	}
	public String getPatienttime() {
		return patienttime;
	}
	public void setPatienttime(String patienttime) {
		this.patienttime = patienttime;
	}
	public String getSEQ_ID() {
		return SEQ_ID;
	}
	public void setSEQ_ID(String sEQ_ID) {
		SEQ_ID = sEQ_ID;
	}
	public String getLcljId() {
		return LcljId;
	}
	public void setLcljId(String lcljId) {
		LcljId = lcljId;
	}
	public String getLcljNum() {
		return LcljNum;
	}
	public void setLcljNum(String lcljNum) {
		LcljNum = lcljNum;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
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
}
