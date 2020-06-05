package com.kqds.entity.base;

public class KqdsCkGoodsInCheck {

	private String SEQ_ID;
	private String goodsinid;
	private String checkUserId;
	private String checkDate;
	private String remark;
	private String result;
	private String organization;
	
	private String packing; //外包装是否破损异常  0：异常     1：通过
	private String certificate; //合格证   0：异常     1：通过
	private String report; //验收报告   0：异常     1：通过

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getSEQ_ID() {
		return SEQ_ID;
	}

	public void setSEQ_ID(String sEQ_ID) {
		SEQ_ID = sEQ_ID;
	}

	public String getGoodsinid() {
		return goodsinid;
	}

	public void setGoodsinid(String goodsinid) {
		this.goodsinid = goodsinid;
	}

	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
