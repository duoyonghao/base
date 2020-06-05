package com.kqds.entity.base;

public class KqdsBlkReview {
	private String seqId;

	private String organization;

	private String meid;

	private String healingTissue;

	private String remark;

	private String createuser;

	private String createtime;

	private String conditionImplants;

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

	public String getHealingTissue() {
		return healingTissue;
	}

	public void setHealingTissue(String healingTissue) {
		this.healingTissue = healingTissue == null ? null : healingTissue.trim();
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

	public String getConditionImplants() {
		return conditionImplants;
	}

	public void setConditionImplants(String conditionImplants) {
		this.conditionImplants = conditionImplants == null ? null : conditionImplants.trim();
	}
}