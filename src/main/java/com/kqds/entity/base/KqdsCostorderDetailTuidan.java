package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsCostorderDetailTuidan {
	private String seqId;

	private String createuser;

	private String createtime;

	private String usercode;

	private String costno;

	private String itemno;

	private String itemname;

	private String unit;

	private BigDecimal unitprice;

	private String num;

	private String discount;

	private BigDecimal subtotal;

	private BigDecimal arrearmoney;

	private BigDecimal paymoney;

	private BigDecimal voidmoney;

	private String askperson;

	private String doctor;

	private String status;

	private String remark;

	private String regno;

	private String qfbh;

	private String kaifa;

	private BigDecimal payxj;

	private BigDecimal payyjj;

	private BigDecimal paybank;

	private BigDecimal payyb;

	private BigDecimal payother1;

	private BigDecimal payother2;

	private BigDecimal payother3;

	private Integer istsxm;

	private BigDecimal y2;

	private Integer isqfreal;

	private Integer istk;

	private BigDecimal payzfb;

	private BigDecimal paywx;

	private Integer issplit;

	private String organization;

	private BigDecimal paymmd;

	private BigDecimal paybdfq;

	private BigDecimal paydjq;

	private String backid;

	private Integer isyjjitem;

	private BigDecimal payintegral;

	private String zltime;

	private String czperson;

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

	public BigDecimal getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(BigDecimal unitprice) {
		this.unitprice = unitprice;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num == null ? null : num.trim();
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount == null ? null : discount.trim();
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getArrearmoney() {
		return arrearmoney;
	}

	public void setArrearmoney(BigDecimal arrearmoney) {
		this.arrearmoney = arrearmoney;
	}

	public BigDecimal getPaymoney() {
		return paymoney;
	}

	public void setPaymoney(BigDecimal paymoney) {
		this.paymoney = paymoney;
	}

	public BigDecimal getVoidmoney() {
		return voidmoney;
	}

	public void setVoidmoney(BigDecimal voidmoney) {
		this.voidmoney = voidmoney;
	}

	public String getAskperson() {
		return askperson;
	}

	public void setAskperson(String askperson) {
		this.askperson = askperson == null ? null : askperson.trim();
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor == null ? null : doctor.trim();
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

	public String getQfbh() {
		return qfbh;
	}

	public void setQfbh(String qfbh) {
		this.qfbh = qfbh == null ? null : qfbh.trim();
	}

	public String getKaifa() {
		return kaifa;
	}

	public void setKaifa(String kaifa) {
		this.kaifa = kaifa == null ? null : kaifa.trim();
	}

	public BigDecimal getPayxj() {
		return payxj;
	}

	public void setPayxj(BigDecimal payxj) {
		this.payxj = payxj;
	}

	public BigDecimal getPayyjj() {
		return payyjj;
	}

	public void setPayyjj(BigDecimal payyjj) {
		this.payyjj = payyjj;
	}

	public BigDecimal getPaybank() {
		return paybank;
	}

	public void setPaybank(BigDecimal paybank) {
		this.paybank = paybank;
	}

	public BigDecimal getPayyb() {
		return payyb;
	}

	public void setPayyb(BigDecimal payyb) {
		this.payyb = payyb;
	}

	public BigDecimal getPayother1() {
		return payother1;
	}

	public void setPayother1(BigDecimal payother1) {
		this.payother1 = payother1;
	}

	public BigDecimal getPayother2() {
		return payother2;
	}

	public void setPayother2(BigDecimal payother2) {
		this.payother2 = payother2;
	}

	public BigDecimal getPayother3() {
		return payother3;
	}

	public void setPayother3(BigDecimal payother3) {
		this.payother3 = payother3;
	}

	public Integer getIstsxm() {
		return istsxm;
	}

	public void setIstsxm(Integer istsxm) {
		this.istsxm = istsxm;
	}

	public BigDecimal getY2() {
		return y2;
	}

	public void setY2(BigDecimal y2) {
		this.y2 = y2;
	}

	public Integer getIsqfreal() {
		return isqfreal;
	}

	public void setIsqfreal(Integer isqfreal) {
		this.isqfreal = isqfreal;
	}

	public Integer getIstk() {
		return istk;
	}

	public void setIstk(Integer istk) {
		this.istk = istk;
	}

	public BigDecimal getPayzfb() {
		return payzfb;
	}

	public void setPayzfb(BigDecimal payzfb) {
		this.payzfb = payzfb;
	}

	public BigDecimal getPaywx() {
		return paywx;
	}

	public void setPaywx(BigDecimal paywx) {
		this.paywx = paywx;
	}

	public Integer getIssplit() {
		return issplit;
	}

	public void setIssplit(Integer issplit) {
		this.issplit = issplit;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public BigDecimal getPaymmd() {
		return paymmd;
	}

	public void setPaymmd(BigDecimal paymmd) {
		this.paymmd = paymmd;
	}

	public BigDecimal getPaybdfq() {
		return paybdfq;
	}

	public void setPaybdfq(BigDecimal paybdfq) {
		this.paybdfq = paybdfq;
	}

	public BigDecimal getPaydjq() {
		return paydjq;
	}

	public void setPaydjq(BigDecimal paydjq) {
		this.paydjq = paydjq;
	}

	public String getBackid() {
		return backid;
	}

	public void setBackid(String backid) {
		this.backid = backid == null ? null : backid.trim();
	}

	public Integer getIsyjjitem() {
		return isyjjitem;
	}

	public void setIsyjjitem(Integer isyjjitem) {
		this.isyjjitem = isyjjitem;
	}

	public BigDecimal getPayintegral() {
		return payintegral;
	}

	public void setPayintegral(BigDecimal payintegral) {
		this.payintegral = payintegral;
	}

	public String getZltime() {
		return zltime;
	}

	public void setZltime(String zltime) {
		this.zltime = zltime == null ? null : zltime.trim();
	}

	public String getCzperson() {
		return czperson;
	}

	public void setCzperson(String czperson) {
		this.czperson = czperson == null ? null : czperson.trim();
	}
}