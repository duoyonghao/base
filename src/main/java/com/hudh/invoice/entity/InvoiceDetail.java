package com.hudh.invoice.entity;

import java.math.BigDecimal;

public class InvoiceDetail {
	private String seqId;
	private String dutyParayraph;//票号
	private String invoiceTime;//开票时间
	private BigDecimal invoiceValue; //金额
	private String drawer;//开票人
	private String taxpayerNumber;//纳税人识别号
	private String createtime;//创建时间
	private String createuser;//创建人
	private String usercode;//患者编号
	private String invoiceDetail;//发票明细
	private String organization;
	private String updatetime;
	private String updateuser;
	private int status;
	private int dishonour;
	public String getSeqId() {
		return seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	public String getDutyParayraph() {
		return dutyParayraph;
	}
	public void setDutyParayraph(String dutyParayraph) {
		this.dutyParayraph = dutyParayraph;
	}
	public String getInvoiceTime() {
		return invoiceTime;
	}
	public void setInvoiceTime(String invoiceTime) {
		this.invoiceTime = invoiceTime;
	}
	public BigDecimal getInvoiceValue() {
		return invoiceValue;
	}
	public void setInvoiceValue(BigDecimal invoiceValue) {
		this.invoiceValue = invoiceValue;
	}
	public String getDrawer() {
		return drawer;
	}
	public void setDrawer(String drawer) {
		this.drawer = drawer;
	}
	public String getTaxpayerNumber() {
		return taxpayerNumber;
	}
	public void setTaxpayerNumber(String taxpayerNumber) {
		this.taxpayerNumber = taxpayerNumber;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getInvoiceDetail() {
		return invoiceDetail;
	}
	public void setInvoiceDetail(String invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdateuser() {
		return updateuser;
	}
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDishonour() {
		return dishonour;
	}
	public void setDishonour(int dishonour) {
		this.dishonour = dishonour;
	}
	
	
}
