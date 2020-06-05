package com.kqds.entity.wx;

public class WXOrder {
	private String seqId;

	private String openid;

	private String orderdate;

	private String ordertime;

	private String organization;

	private String createtime;

	private Integer orderstatus;

	private String auditor;

	private String auditortime;

	private String auditorremark;

	private String cancelreason;

	private String canceltime;

	private String askitem;

	private String askcontent;

	private String phonenumber;

	private String confirmtime;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid == null ? null : openid.trim();
	}

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate == null ? null : orderdate.trim();
	}

	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime == null ? null : ordertime.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime == null ? null : createtime.trim();
	}

	public Integer getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Integer orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor == null ? null : auditor.trim();
	}

	public String getAuditortime() {
		return auditortime;
	}

	public void setAuditortime(String auditortime) {
		this.auditortime = auditortime == null ? null : auditortime.trim();
	}

	public String getAuditorremark() {
		return auditorremark;
	}

	public void setAuditorremark(String auditorremark) {
		this.auditorremark = auditorremark == null ? null : auditorremark.trim();
	}

	public String getCancelreason() {
		return cancelreason;
	}

	public void setCancelreason(String cancelreason) {
		this.cancelreason = cancelreason == null ? null : cancelreason.trim();
	}

	public String getCanceltime() {
		return canceltime;
	}

	public void setCanceltime(String canceltime) {
		this.canceltime = canceltime == null ? null : canceltime.trim();
	}

	public String getAskitem() {
		return askitem;
	}

	public void setAskitem(String askitem) {
		this.askitem = askitem == null ? null : askitem.trim();
	}

	public String getAskcontent() {
		return askcontent;
	}

	public void setAskcontent(String askcontent) {
		this.askcontent = askcontent == null ? null : askcontent.trim();
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber == null ? null : phonenumber.trim();
	}

	public String getConfirmtime() {
		return confirmtime;
	}

	public void setConfirmtime(String confirmtime) {
		this.confirmtime = confirmtime == null ? null : confirmtime.trim();
	}
}