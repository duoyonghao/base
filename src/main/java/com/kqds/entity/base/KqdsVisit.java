package com.kqds.entity.base;

public class KqdsVisit {
	private String seqId;

	private String createuser;

	private String createtime;

	private String visittime;

	private String visitor;

	private String usercode;

	private String username;

	private String sex;

	private String telphone;

	private String hffl;

	private String finishtime;

	private String hfyd;

	private String organization;

	private String myd;

	private String postperson;

	private String hfresult;

	private String status;

	private String regno;

	private String remark;

	private String purpose;

	private Integer isauto;

	private Integer isfirstday;
	
	private String accepttype;//受理类型
	
	private String visittype;//回访分类
	
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

	public String getVisittime() {
		return visittime;
	}

	public void setVisittime(String visittime) {
		this.visittime = visittime == null ? null : visittime.trim();
	}

	public String getVisitor() {
		return visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor == null ? null : visitor.trim();
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex == null ? null : sex.trim();
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone == null ? null : telphone.trim();
	}

	public String getHffl() {
		return hffl;
	}

	public void setHffl(String hffl) {
		this.hffl = hffl == null ? null : hffl.trim();
	}

	public String getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(String finishtime) {
		this.finishtime = finishtime == null ? null : finishtime.trim();
	}

	public String getHfyd() {
		return hfyd;
	}

	public void setHfyd(String hfyd) {
		this.hfyd = hfyd == null ? null : hfyd.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public String getMyd() {
		return myd;
	}

	public void setMyd(String myd) {
		this.myd = myd == null ? null : myd.trim();
	}

	public String getPostperson() {
		return postperson;
	}

	public void setPostperson(String postperson) {
		this.postperson = postperson == null ? null : postperson.trim();
	}

	public String getHfresult() {
		return hfresult;
	}

	public void setHfresult(String hfresult) {
		this.hfresult = hfresult == null ? null : hfresult.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno == null ? null : regno.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose == null ? null : purpose.trim();
	}

	public Integer getIsauto() {
		return isauto;
	}

	public void setIsauto(Integer isauto) {
		this.isauto = isauto;
	}

	public Integer getIsfirstday() {
		return isfirstday;
	}

	public void setIsfirstday(Integer isfirstday) {
		this.isfirstday = isfirstday;
	}

	public String getAccepttype() {
		return accepttype;
	}

	public void setAccepttype(String accepttype) {
		this.accepttype = accepttype;
	}

	public String getVisittype() {
		return visittype;
	}

	public void setVisittype(String visittype) {
		this.visittype = visittype;
	}
}