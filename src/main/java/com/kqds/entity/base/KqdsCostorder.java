package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsCostorder {
	private String seqId;

	private String createuser;

	private String createtime;

	private String usercode;

	private String recvinfono;

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

	private String status;

	private String remark;

	private String regno;

	private String sftime;

	private String sfuser;

	private Integer cjstatus;

	private Integer isprint;

	private String username;

	private BigDecimal y2;

	private Integer isreg;

	private String organization;

	private String costdept;

	private Integer isback;

	private String backremark;

	private String backtime;

	private String backuser;
	
	private Integer isdrugs;//添加字段
	
	private Integer issend;//添加字段
	
	private String repairdoctor;//添加字段  修复医生

	public String getRepairdoctor() {
		return repairdoctor;
	}

	public void setRepairdoctor(String repairdoctor) {
		this.repairdoctor = repairdoctor;
	}

	public Integer getIsdrugs() {
		return isdrugs;
	}

	public void setIsdrugs(Integer isdrugs) {
		this.isdrugs = isdrugs;
	}

	public Integer getIssend() {
		return issend;
	}

	public void setIssend(Integer issend) {
		this.issend = issend;
	}

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

	public String getRecvinfono() {
		return recvinfono;
	}

	public void setRecvinfono(String recvinfono) {
		this.recvinfono = recvinfono == null ? null : recvinfono.trim();
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno == null ? null : regno.trim();
	}

	public String getSftime() {
		return sftime;
	}

	public void setSftime(String sftime) {
		this.sftime = sftime == null ? null : sftime.trim();
	}

	public String getSfuser() {
		return sfuser;
	}

	public void setSfuser(String sfuser) {
		this.sfuser = sfuser == null ? null : sfuser.trim();
	}

	public Integer getCjstatus() {
		return cjstatus;
	}

	public void setCjstatus(Integer cjstatus) {
		this.cjstatus = cjstatus;
	}

	public Integer getIsprint() {
		return isprint;
	}

	public void setIsprint(Integer isprint) {
		this.isprint = isprint;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public BigDecimal getY2() {
		return y2;
	}

	public void setY2(BigDecimal y2) {
		this.y2 = y2;
	}

	public Integer getIsreg() {
		return isreg;
	}

	public void setIsreg(Integer isreg) {
		this.isreg = isreg;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public String getCostdept() {
		return costdept;
	}

	public void setCostdept(String costdept) {
		this.costdept = costdept == null ? null : costdept.trim();
	}

	public Integer getIsback() {
		return isback;
	}

	public void setIsback(Integer isback) {
		this.isback = isback;
	}

	public String getBackremark() {
		return backremark;
	}

	public void setBackremark(String backremark) {
		this.backremark = backremark == null ? null : backremark.trim();
	}

	public String getBacktime() {
		return backtime;
	}

	public void setBacktime(String backtime) {
		this.backtime = backtime == null ? null : backtime.trim();
	}

	public String getBackuser() {
		return backuser;
	}

	public void setBackuser(String backuser) {
		this.backuser = backuser == null ? null : backuser.trim();
	}
}