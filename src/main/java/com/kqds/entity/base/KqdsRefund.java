package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsRefund {
	private String seqId;

	private String createuser;

	private String createtime;

	private String usercode;

	private String costno;

	private BigDecimal totalcost;

	private BigDecimal voidmoney;

	private BigDecimal shouldmoney;

	private BigDecimal arrearmoney;

	private BigDecimal totalarrmoney;

	private BigDecimal actualmoney;

	private BigDecimal discountmoney;

	private String doctor;

	private String nurse;

	private String techperson;

	private Integer status;

	private String remark;

	private String shtime;

	private String shuser;

	private String tkuser;

	private String tktime;

	private String username;

	private BigDecimal tkze;

	private String organization;

	private BigDecimal addyjj;//转换预交金金额

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

	public String getCostno() {
		return costno;
	}

	public void setCostno(String costno) {
		this.costno = costno == null ? null : costno.trim();
	}

	public BigDecimal getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(BigDecimal totalcost) {
		this.totalcost = totalcost;
	}

	public BigDecimal getVoidmoney() {
		return voidmoney;
	}

	public void setVoidmoney(BigDecimal voidmoney) {
		this.voidmoney = voidmoney;
	}

	public BigDecimal getShouldmoney() {
		return shouldmoney;
	}

	public void setShouldmoney(BigDecimal shouldmoney) {
		this.shouldmoney = shouldmoney;
	}

	public BigDecimal getArrearmoney() {
		return arrearmoney;
	}

	public void setArrearmoney(BigDecimal arrearmoney) {
		this.arrearmoney = arrearmoney;
	}

	public BigDecimal getTotalarrmoney() {
		return totalarrmoney;
	}

	public void setTotalarrmoney(BigDecimal totalarrmoney) {
		this.totalarrmoney = totalarrmoney;
	}

	public BigDecimal getActualmoney() {
		return actualmoney;
	}

	public void setActualmoney(BigDecimal actualmoney) {
		this.actualmoney = actualmoney;
	}

	public BigDecimal getDiscountmoney() {
		return discountmoney;
	}

	public void setDiscountmoney(BigDecimal discountmoney) {
		this.discountmoney = discountmoney;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor == null ? null : doctor.trim();
	}

	public String getNurse() {
		return nurse;
	}

	public void setNurse(String nurse) {
		this.nurse = nurse == null ? null : nurse.trim();
	}

	public String getTechperson() {
		return techperson;
	}

	public void setTechperson(String techperson) {
		this.techperson = techperson == null ? null : techperson.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getShtime() {
		return shtime;
	}

	public void setShtime(String shtime) {
		this.shtime = shtime == null ? null : shtime.trim();
	}

	public String getShuser() {
		return shuser;
	}

	public void setShuser(String shuser) {
		this.shuser = shuser == null ? null : shuser.trim();
	}

	public String getTkuser() {
		return tkuser;
	}

	public void setTkuser(String tkuser) {
		this.tkuser = tkuser == null ? null : tkuser.trim();
	}

	public String getTktime() {
		return tktime;
	}

	public void setTktime(String tktime) {
		this.tktime = tktime == null ? null : tktime.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public BigDecimal getTkze() {
		return tkze;
	}

	public void setTkze(BigDecimal tkze) {
		this.tkze = tkze;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public BigDecimal getAddyjj() {
		return addyjj;
	}

	public void setAddyjj(BigDecimal addyjj) {
		this.addyjj = addyjj;
	}
}