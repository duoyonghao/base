package com.kqds.entity.base;

public class KqdsParticipant {
	private String seqId;

	private String createuser;

	private String createtime;

	private String participant;

	private String relation;

	private String remark;

	private String usercode;

	private String participantcode;

	private String organization;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
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

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant == null ? null : participant.trim();
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation == null ? null : relation.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode == null ? null : usercode.trim();
	}

	public String getParticipantcode() {
		return participantcode;
	}

	public void setParticipantcode(String participantcode) {
		this.participantcode = participantcode == null ? null : participantcode.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}
}