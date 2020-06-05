package com.kqds.entity.base;

public class KqdsJzqk {
	private String seqId;

	private String regno;

	private String reggoal;

	private String jzmd;

	private String remark;

	private String doctor;

	private String createtime;

	private String createuser;

	private String organization;

	private String fzdata;

	private String dzdata;

	private String dzregno;

	private Integer istx;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno == null ? null : regno.trim();
	}

	public String getReggoal() {
		return reggoal;
	}

	public void setReggoal(String reggoal) {
		this.reggoal = reggoal == null ? null : reggoal.trim();
	}

	public String getJzmd() {
		return jzmd;
	}

	public void setJzmd(String jzmd) {
		this.jzmd = jzmd == null ? null : jzmd.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor == null ? null : doctor.trim();
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

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public String getFzdata() {
		return fzdata;
	}

	public void setFzdata(String fzdata) {
		this.fzdata = fzdata == null ? null : fzdata.trim();
	}

	public String getDzdata() {
		return dzdata;
	}

	public void setDzdata(String dzdata) {
		this.dzdata = dzdata == null ? null : dzdata.trim();
	}

	public String getDzregno() {
		return dzregno;
	}

	public void setDzregno(String dzregno) {
		this.dzregno = dzregno == null ? null : dzregno.trim();
	}

	public Integer getIstx() {
		return istx;
	}

	public void setIstx(Integer istx) {
		this.istx = istx;
	}
}