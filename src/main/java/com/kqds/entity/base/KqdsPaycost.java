package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsPaycost {
	private String seqId;

	private String createuser;

	private String createtime;

	private String usercode;

	private String username;

	private Integer sex;

	private String doctor;

	private String nurse;

	private String techperson;

	private String askperson;

	private String costno;

	private BigDecimal totalcost;

	private BigDecimal voidmoney;

	private BigDecimal shouldmoney;

	private BigDecimal actualmoney;

	private BigDecimal arrearmoney;

	private BigDecimal discountmoney;

	private String paytype1;

	private BigDecimal money1;

	private String paytype2;

	private BigDecimal money2;

	private String regno;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
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

	public String getAskperson() {
		return askperson;
	}

	public void setAskperson(String askperson) {
		this.askperson = askperson == null ? null : askperson.trim();
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

	public BigDecimal getActualmoney() {
		return actualmoney;
	}

	public void setActualmoney(BigDecimal actualmoney) {
		this.actualmoney = actualmoney;
	}

	public BigDecimal getArrearmoney() {
		return arrearmoney;
	}

	public void setArrearmoney(BigDecimal arrearmoney) {
		this.arrearmoney = arrearmoney;
	}

	public BigDecimal getDiscountmoney() {
		return discountmoney;
	}

	public void setDiscountmoney(BigDecimal discountmoney) {
		this.discountmoney = discountmoney;
	}

	public String getPaytype1() {
		return paytype1;
	}

	public void setPaytype1(String paytype1) {
		this.paytype1 = paytype1 == null ? null : paytype1.trim();
	}

	public BigDecimal getMoney1() {
		return money1;
	}

	public void setMoney1(BigDecimal money1) {
		this.money1 = money1;
	}

	public String getPaytype2() {
		return paytype2;
	}

	public void setPaytype2(String paytype2) {
		this.paytype2 = paytype2 == null ? null : paytype2.trim();
	}

	public BigDecimal getMoney2() {
		return money2;
	}

	public void setMoney2(BigDecimal money2) {
		this.money2 = money2;
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno == null ? null : regno.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}
}