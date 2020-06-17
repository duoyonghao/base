package com.kqds.entity.base;

public class KqdsRoom {
	private String seqId;

	private String roomid;

	private String starttime;

	private String endtime;

	private String doctor;

	private String nurse;

	private String usercode;

	private String remark;

	private String createtime;

	private String createuser;

	private String organization;

	private Integer isdelete;

	private String delreason;

	private String delperson;

	private String zzxt;

	private String ks;

	private String roomstatus;
	
	private String askperson;
	
	private String phasedoctor;
	
	private String tooth;

	public String getTooth() {
		return tooth;
	}

	public void setTooth(String tooth) {
		this.tooth = tooth;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid == null ? null : roomid.trim();
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime == null ? null : starttime.trim();
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime == null ? null : endtime.trim();
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

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode == null ? null : usercode.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime == null ? null : createtime.trim();
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser == null ? null : createuser.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public String getDelreason() {
		return delreason;
	}

	public void setDelreason(String delreason) {
		this.delreason = delreason == null ? null : delreason.trim();
	}

	public String getDelperson() {
		return delperson;
	}

	public void setDelperson(String delperson) {
		this.delperson = delperson == null ? null : delperson.trim();
	}

	public String getZzxt() {
		return zzxt;
	}

	public void setZzxt(String zzxt) {
		this.zzxt = zzxt == null ? null : zzxt.trim();
	}

	public String getKs() {
		return ks;
	}

	public void setKs(String ks) {
		this.ks = ks == null ? null : ks.trim();
	}

	public String getRoomstatus() {
		return roomstatus;
	}

	public void setRoomstatus(String roomstatus) {
		this.roomstatus = roomstatus == null ? null : roomstatus.trim();
	}

	public String getAskperson() {
		return askperson;
	}

	public void setAskperson(String askperson) {
		this.askperson = askperson;
	}

	public String getPhasedoctor() {
		return phasedoctor;
	}

	public void setPhasedoctor(String phasedoctor) {
		this.phasedoctor = phasedoctor;
	}
	
	
}