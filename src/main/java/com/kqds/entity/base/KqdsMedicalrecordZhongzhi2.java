package com.kqds.entity.base;

public class KqdsMedicalrecordZhongzhi2 {
	private String seqId;

	private String organization;

	private String meid;

	private String usercode;

	private String others;

	private String surgicalRecord;

	private String remark;

	private String createuser;

	private String createtime;

	private String preoperativeExamination;

	private String teethCondition;

	private String implantgumCondition;

	private String implantExposure;

	private String xrayExamination;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public String getMeid() {
		return meid;
	}

	public void setMeid(String meid) {
		this.meid = meid == null ? null : meid.trim();
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode == null ? null : usercode.trim();
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others == null ? null : others.trim();
	}

	public String getSurgicalRecord() {
		return surgicalRecord;
	}

	public void setSurgicalRecord(String surgicalRecord) {
		this.surgicalRecord = surgicalRecord == null ? null : surgicalRecord.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser == null ? null : createuser.trim();
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime == null ? null : createtime.trim();
	}

	public String getPreoperativeExamination() {
		return preoperativeExamination;
	}

	public void setPreoperativeExamination(String preoperativeExamination) {
		this.preoperativeExamination = preoperativeExamination == null ? null : preoperativeExamination.trim();
	}

	public String getTeethCondition() {
		return teethCondition;
	}

	public void setTeethCondition(String teethCondition) {
		this.teethCondition = teethCondition == null ? null : teethCondition.trim();
	}

	public String getImplantgumCondition() {
		return implantgumCondition;
	}

	public void setImplantgumCondition(String implantgumCondition) {
		this.implantgumCondition = implantgumCondition == null ? null : implantgumCondition.trim();
	}

	public String getImplantExposure() {
		return implantExposure;
	}

	public void setImplantExposure(String implantExposure) {
		this.implantExposure = implantExposure == null ? null : implantExposure.trim();
	}

	public String getXrayExamination() {
		return xrayExamination;
	}

	public void setXrayExamination(String xrayExamination) {
		this.xrayExamination = xrayExamination == null ? null : xrayExamination.trim();
	}
}