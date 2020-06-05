package com.kqds.entity.base;

public class KqdsHzLabelAssociated {
	
	private String seqId;//id
	private String labeId;//标签id
	private String patientId;
	private String usercode;//患者id
	private String userName;//患者名称
	private String createUser;//创建人id
	private String createTime;//创建时间
	private String remark;//备注
	private int status; //标签状态1：有价目表 0:无价目表
	private int isdelete;//是否删除 1：删除
	private String updateTime;//修改时间
	private String modifier;//修改人
	private String reservedo;//预留字段
	private String reserveds;//预留字段
	private String reservedt;//预留字段
	public String getSeqId() {
		return seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	public String getLabeId() {
		return labeId;
	}
	public void setLabeId(String labeId) {
		this.labeId = labeId;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public String getReservedo() {
		return reservedo;
	}
	public void setReservedo(String reservedo) {
		this.reservedo = reservedo;
	}
	public String getReserveds() {
		return reserveds;
	}
	public void setReserveds(String reserveds) {
		this.reserveds = reserveds;
	}
	public String getReservedt() {
		return reservedt;
	}
	public void setReservedt(String reservedt) {
		this.reservedt = reservedt;
	}
	


}
