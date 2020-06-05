package com.kqds.entity.base;

public class KqdsTreatitemTc {
	private String seqId;

	private String itemno;

	private String itemname;

	private String unit;

	private String unitprice;

	private String num;

	private String discount;

	private String subtotal;

	private String arrearmoney;

	private String paymoney;

	private String voidmoney;

	private Integer istsxm;

	private String organization;

	private String createuser;

	private String createtime;

	private String tcnameid;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
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

	public String getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(String unitprice) {
		this.unitprice = unitprice == null ? null : unitprice.trim();
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

	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal == null ? null : subtotal.trim();
	}

	public String getArrearmoney() {
		return arrearmoney;
	}

	public void setArrearmoney(String arrearmoney) {
		this.arrearmoney = arrearmoney == null ? null : arrearmoney.trim();
	}

	public String getPaymoney() {
		return paymoney;
	}

	public void setPaymoney(String paymoney) {
		this.paymoney = paymoney == null ? null : paymoney.trim();
	}

	public String getVoidmoney() {
		return voidmoney;
	}

	public void setVoidmoney(String voidmoney) {
		this.voidmoney = voidmoney == null ? null : voidmoney.trim();
	}

	public Integer getIstsxm() {
		return istsxm;
	}

	public void setIstsxm(Integer istsxm) {
		this.istsxm = istsxm;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
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

	public String getTcnameid() {
		return tcnameid;
	}

	public void setTcnameid(String tcnameid) {
		this.tcnameid = tcnameid == null ? null : tcnameid.trim();
	}
}