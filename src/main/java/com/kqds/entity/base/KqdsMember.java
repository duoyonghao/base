package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsMember {
	private String seqId;

	private String createuser;

	private String createtime;

	private String usercode;

	private String memberno;

	private String memberlevel;

	private String memberstatus;

	private BigDecimal money;

	private BigDecimal givemoney;

	private String password;

	private Integer discount;

	private String username;

	private String remark;

	private String organization;

	private String icno;

	private Integer isbinding;

	private String discountdate;

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

	public String getMemberno() {
		return memberno;
	}

	public void setMemberno(String memberno) {
		this.memberno = memberno == null ? null : memberno.trim();
	}

	public String getMemberlevel() {
		return memberlevel;
	}

	public void setMemberlevel(String memberlevel) {
		this.memberlevel = memberlevel == null ? null : memberlevel.trim();
	}

	public String getMemberstatus() {
		return memberstatus;
	}

	public void setMemberstatus(String memberstatus) {
		this.memberstatus = memberstatus == null ? null : memberstatus.trim();
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getGivemoney() {
		return givemoney;
	}

	public void setGivemoney(BigDecimal givemoney) {
		this.givemoney = givemoney;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public String getIcno() {
		return icno;
	}

	public void setIcno(String icno) {
		this.icno = icno == null ? null : icno.trim();
	}

	public Integer getIsbinding() {
		return isbinding;
	}

	public void setIsbinding(Integer isbinding) {
		this.isbinding = isbinding;
	}

	public String getDiscountdate() {
		return discountdate;
	}

	public void setDiscountdate(String discountdate) {
		this.discountdate = discountdate == null ? null : discountdate.trim();
	}
}