package com.kqds.entity.base;

public class KqdsReg {
	private String seqId;

	private String createuser;

	private String createtime;

	private String usercode;

	private String regway;

	private String regsort;

	private String recesort;

	private String regmoney;

	private String reggoal;

	private String regdept;

	private String doctor;

	private String askperson;

	private Integer ifmedrecord;

	private Integer ifcost;

	private String editreason;

	private String receiveno;

	private Integer status;

	private String username;

	private Integer del;

	private Integer cjstatus;

	private String remark;

	private String organization;

	private String beforeeditreason;
	
	private String repairdoctor;
	
	private String askpersondept;//添加字段、记录咨询部门
	
	private int jh;
	
	public String getAskpersondept() {
		return askpersondept;
	}

	public void setAskpersondept(String askpersondept) {
		this.askpersondept = askpersondept;
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

	public String getRegway() {
		return regway;
	}

	public void setRegway(String regway) {
		this.regway = regway == null ? null : regway.trim();
	}

	public String getRegsort() {
		return regsort;
	}

	public void setRegsort(String regsort) {
		this.regsort = regsort == null ? null : regsort.trim();
	}

	public String getRecesort() {
		return recesort;
	}

	public void setRecesort(String recesort) {
		this.recesort = recesort == null ? null : recesort.trim();
	}

	public String getRegmoney() {
		return regmoney;
	}

	public void setRegmoney(String regmoney) {
		this.regmoney = regmoney == null ? null : regmoney.trim();
	}

	public String getReggoal() {
		return reggoal;
	}

	public void setReggoal(String reggoal) {
		this.reggoal = reggoal == null ? null : reggoal.trim();
	}

	public String getRegdept() {
		return regdept;
	}

	public void setRegdept(String regdept) {
		this.regdept = regdept == null ? null : regdept.trim();
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor == null ? null : doctor.trim();
	}

	public String getAskperson() {
		return askperson;
	}

	public void setAskperson(String askperson) {
		this.askperson = askperson == null ? null : askperson.trim();
	}

	public Integer getIfmedrecord() {
		return ifmedrecord;
	}

	public void setIfmedrecord(Integer ifmedrecord) {
		this.ifmedrecord = ifmedrecord;
	}

	public Integer getIfcost() {
		return ifcost;
	}

	public void setIfcost(Integer ifcost) {
		this.ifcost = ifcost;
	}

	public String getEditreason() {
		return editreason;
	}

	public void setEditreason(String editreason) {
		this.editreason = editreason == null ? null : editreason.trim();
	}

	public String getReceiveno() {
		return receiveno;
	}

	public void setReceiveno(String receiveno) {
		this.receiveno = receiveno == null ? null : receiveno.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

	public Integer getCjstatus() {
		return cjstatus;
	}

	public void setCjstatus(Integer cjstatus) {
		this.cjstatus = cjstatus;
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

	public String getBeforeeditreason() {
		return beforeeditreason;
	}

	public void setBeforeeditreason(String beforeeditreason) {
		this.beforeeditreason = beforeeditreason == null ? null : beforeeditreason.trim();
	}

	public String getRepairdoctor() {
		return repairdoctor;
	}

	public void setRepairdoctor(String repairdoctor) {
		this.repairdoctor = repairdoctor;
	}

	public int getJh() {
		return jh;
	}

	public void setJh(int jh) {
		this.jh = jh;
	}
	
}