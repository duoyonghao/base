package com.kqds.entity.base;

public class KqdsChufangDetail {
	private String seqId;

	private String createuser;

	private String createtime;

	private String usercode;

	private String regno;

	private String costno;

	private String chufangno;

	private String itemno;

	private String itemname;

	private String unit;

	private String num;

	private String remark;

	private String cfusage;

	private String cfuselevel;

	private String cfusemethod;

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

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode == null ? null : usercode.trim();
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno == null ? null : regno.trim();
	}

	public String getCostno() {
		return costno;
	}

	public void setCostno(String costno) {
		this.costno = costno == null ? null : costno.trim();
	}

	public String getChufangno() {
		return chufangno;
	}

	public void setChufangno(String chufangno) {
		this.chufangno = chufangno == null ? null : chufangno.trim();
	}

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno == null ? null : itemno.trim();
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname == null ? null : itemname.trim();
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit == null ? null : unit.trim();
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num == null ? null : num.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getCfusage() {
		return cfusage;
	}

	public void setCfusage(String cfusage) {
		this.cfusage = cfusage == null ? null : cfusage.trim();
	}

	public String getCfuselevel() {
		return cfuselevel;
	}

	public void setCfuselevel(String cfuselevel) {
		this.cfuselevel = cfuselevel == null ? null : cfuselevel.trim();
	}

	public String getCfusemethod() {
		return cfusemethod;
	}

	public void setCfusemethod(String cfusemethod) {
		this.cfusemethod = cfusemethod == null ? null : cfusemethod.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}
}