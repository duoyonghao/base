package com.kqds.entity.base;

public class KqdsChangeReceive {
	private String seqId;

	private String createuser;

	private String createtime;

	private String oldaskperson;

	private String toaskperson;

	private String remark;

	private String usercode;

	private String username;

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

	public String getOldaskperson() {
		return oldaskperson;
	}

	public void setOldaskperson(String oldaskperson) {
		this.oldaskperson = oldaskperson == null ? null : oldaskperson.trim();
	}

	public String getToaskperson() {
		return toaskperson;
	}

	public void setToaskperson(String toaskperson) {
		this.toaskperson = toaskperson == null ? null : toaskperson.trim();
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}
}