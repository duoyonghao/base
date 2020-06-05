package com.kqds.entity.base;

public class KqdsSmsModel {
	private String seqId;

	private String smstype;

	private String createtime;

	private String createuser;

	private String smsname;

	private String smsnexttype;

	private String organization;

	private Integer isdsmodel;

	private String sortno;

	private String smscontent;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getSmstype() {
		return smstype;
	}

	public void setSmstype(String smstype) {
		this.smstype = smstype == null ? null : smstype.trim();
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime == null ? null : createtime.trim();
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser == null ? null : createuser.trim();
	}

	public String getSmsname() {
		return smsname;
	}

	public void setSmsname(String smsname) {
		this.smsname = smsname == null ? null : smsname.trim();
	}

	public String getSmsnexttype() {
		return smsnexttype;
	}

	public void setSmsnexttype(String smsnexttype) {
		this.smsnexttype = smsnexttype == null ? null : smsnexttype.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public Integer getIsdsmodel() {
		return isdsmodel;
	}

	public void setIsdsmodel(Integer isdsmodel) {
		this.isdsmodel = isdsmodel;
	}

	public String getSortno() {
		return sortno;
	}

	public void setSortno(String sortno) {
		this.sortno = sortno == null ? null : sortno.trim();
	}

	public String getSmscontent() {
		return smscontent;
	}

	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent == null ? null : smscontent.trim();
	}
}