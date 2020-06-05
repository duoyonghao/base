package com.kqds.entity.base;

public class KqdsGiveitemUserecord {
	private String seqId;

	private String createuser;

	private String createtime;

	private String usercode;

	private String username;

	private String memberno;

	private String itemno;

	private String itemname;

	private String unit;

	private String unitprice;

	private String zsnum;

	private String synum;

	private String nownum;

	private String doctor;

	private Integer status;

	private String cztime;

	private String organization;

	private String nurse;

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

	public String getMemberno() {
		return memberno;
	}

	public void setMemberno(String memberno) {
		this.memberno = memberno == null ? null : memberno.trim();
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

	public String getZsnum() {
		return zsnum;
	}

	public void setZsnum(String zsnum) {
		this.zsnum = zsnum == null ? null : zsnum.trim();
	}

	public String getSynum() {
		return synum;
	}

	public void setSynum(String synum) {
		this.synum = synum == null ? null : synum.trim();
	}

	public String getNownum() {
		return nownum;
	}

	public void setNownum(String nownum) {
		this.nownum = nownum == null ? null : nownum.trim();
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor == null ? null : doctor.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCztime() {
		return cztime;
	}

	public void setCztime(String cztime) {
		this.cztime = cztime == null ? null : cztime.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public String getNurse() {
		return nurse;
	}

	public void setNurse(String nurse) {
		this.nurse = nurse == null ? null : nurse.trim();
	}
}