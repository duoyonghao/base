package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsIntegralRecord {
	private String seqId;

	private String usercode;

	private BigDecimal jfmoney;

	private String jftype;

	private BigDecimal syjfmoney;

	private String createtime;

	private String organization;

	private String remark;

	private String createuser;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode == null ? null : usercode.trim();
	}

	public BigDecimal getJfmoney() {
		return jfmoney;
	}

	public void setJfmoney(BigDecimal jfmoney) {
		this.jfmoney = jfmoney;
	}

	public String getJftype() {
		return jftype;
	}

	public void setJftype(String jftype) {
		this.jftype = jftype == null ? null : jftype.trim();
	}

	public BigDecimal getSyjfmoney() {
		return syjfmoney;
	}

	public void setSyjfmoney(BigDecimal syjfmoney) {
		this.syjfmoney = syjfmoney;
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
}