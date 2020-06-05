package com.kqds.entity.sys;

import java.util.Date;
//对应表 II.97表 sys_person
public class YZPerson {
	private String seqId;

	private String userId;

	private String userName;

	private String password;

	private String userPriv;

	private String deptId;

	private Integer userNo;

	private String notLogin;

	private Integer isLeave;

	private String createtime;

	private String createuser;

	private String sex;

	private Date lastVisitTime;

	private String myStatus;

	private String recordaccount;

	private String recordpwd;

	private String deptIdOther;

	private String userPrivOther;
	
	private String organization;//添加字段，保存门诊
	
	private String xy_dept;

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getUserPriv() {
		return userPriv;
	}

	public void setUserPriv(String userPriv) {
		this.userPriv = userPriv == null ? null : userPriv.trim();
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId == null ? null : deptId.trim();
	}

	public Integer getUserNo() {
		return userNo;
	}

	public void setUserNo(Integer userNo) {
		this.userNo = userNo;
	}

	public String getNotLogin() {
		return notLogin;
	}

	public void setNotLogin(String notLogin) {
		this.notLogin = notLogin == null ? null : notLogin.trim();
	}

	public Integer getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(Integer isLeave) {
		this.isLeave = isLeave;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex == null ? null : sex.trim();
	}

	public Date getLastVisitTime() {
		return lastVisitTime;
	}

	public void setLastVisitTime(Date lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}

	public String getMyStatus() {
		return myStatus;
	}

	public void setMyStatus(String myStatus) {
		this.myStatus = myStatus == null ? null : myStatus.trim();
	}

	public String getRecordaccount() {
		return recordaccount;
	}

	public void setRecordaccount(String recordaccount) {
		this.recordaccount = recordaccount == null ? null : recordaccount.trim();
	}

	public String getRecordpwd() {
		return recordpwd;
	}

	public void setRecordpwd(String recordpwd) {
		this.recordpwd = recordpwd == null ? null : recordpwd.trim();
	}

	public String getDeptIdOther() {
		return deptIdOther;
	}

	public void setDeptIdOther(String deptIdOther) {
		this.deptIdOther = deptIdOther == null ? null : deptIdOther.trim();
	}

	public String getUserPrivOther() {
		return userPrivOther;
	}

	public void setUserPrivOther(String userPrivOther) {
		this.userPrivOther = userPrivOther == null ? null : userPrivOther.trim();
	}

	/**   
	  * <p>Title: toString</p>   
	  * <p>Description: </p>   
	  * @return   
	  * @see java.lang.Object#toString()   
	  */  
	@Override
	public String toString() {
		return "YZPerson [seqId=" + seqId + ", userId=" + userId + ", userName=" + userName + ", password=" + password
				+ ", userPriv=" + userPriv + ", deptId=" + deptId + ", userNo=" + userNo + ", notLogin=" + notLogin
				+ ", isLeave=" + isLeave + ", createtime=" + createtime + ", createuser=" + createuser + ", sex=" + sex
				+ ", lastVisitTime=" + lastVisitTime + ", myStatus=" + myStatus + ", recordaccount=" + recordaccount
				+ ", recordpwd=" + recordpwd + ", deptIdOther=" + deptIdOther + ", userPrivOther=" + userPrivOther
				+ ", organization=" + organization + "]";
	}

	public String getXy_dept() {
		return xy_dept;
	}

	public void setXy_dept(String xy_dept) {
		this.xy_dept = xy_dept;
	}
}