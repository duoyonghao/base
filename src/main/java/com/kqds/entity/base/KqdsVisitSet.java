package com.kqds.entity.base;

public class KqdsVisitSet {
	private String seqId;

	private String hffl;

	private Integer jgday;

	private String remark;

	private String purpose;

	private String createuser;

	private String createtime;

	private String organization;

	private String userpriv;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getHffl() {
		return hffl;
	}

	public void setHffl(String hffl) {
		this.hffl = hffl == null ? null : hffl.trim();
	}

	public Integer getJgday() {
		return jgday;
	}

	public void setJgday(Integer jgday) {
		this.jgday = jgday;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose == null ? null : purpose.trim();
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

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public String getUserpriv() {
		return userpriv;
	}

	public void setUserpriv(String userpriv) {
		this.userpriv = userpriv == null ? null : userpriv.trim();
	}
}