package com.kqds.entity.base;

public class KqdsOutprocessing {
	private String seqId;

	private String createuser;

	private String createtime;

	private String wjgxmbh;

	private String wjgmc;

	private String wjgfl;

	private String dj;

	private String dw;

	private String dysfxm;

	private String basetype;

	private String mrjgc;

	private String remark;

	private String nexttype;

	private String organization;

	private Integer useflag;

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

	public String getWjgxmbh() {
		return wjgxmbh;
	}

	public void setWjgxmbh(String wjgxmbh) {
		this.wjgxmbh = wjgxmbh == null ? null : wjgxmbh.trim();
	}

	public String getWjgmc() {
		return wjgmc;
	}

	public void setWjgmc(String wjgmc) {
		this.wjgmc = wjgmc == null ? null : wjgmc.trim();
	}

	public String getWjgfl() {
		return wjgfl;
	}

	public void setWjgfl(String wjgfl) {
		this.wjgfl = wjgfl == null ? null : wjgfl.trim();
	}

	public String getDj() {
		return dj;
	}

	public void setDj(String dj) {
		this.dj = dj == null ? null : dj.trim();
	}

	public String getDw() {
		return dw;
	}

	public void setDw(String dw) {
		this.dw = dw == null ? null : dw.trim();
	}

	public String getDysfxm() {
		return dysfxm;
	}

	public void setDysfxm(String dysfxm) {
		this.dysfxm = dysfxm == null ? null : dysfxm.trim();
	}

	public String getBasetype() {
		return basetype;
	}

	public void setBasetype(String basetype) {
		this.basetype = basetype == null ? null : basetype.trim();
	}

	public String getMrjgc() {
		return mrjgc;
	}

	public void setMrjgc(String mrjgc) {
		this.mrjgc = mrjgc == null ? null : mrjgc.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getNexttype() {
		return nexttype;
	}

	public void setNexttype(String nexttype) {
		this.nexttype = nexttype == null ? null : nexttype.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public Integer getUseflag() {
		return useflag;
	}

	public void setUseflag(Integer useflag) {
		this.useflag = useflag;
	}
}