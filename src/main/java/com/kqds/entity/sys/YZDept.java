package com.kqds.entity.sys;

public class YZDept {
	private String seqId;

	private String deptName;

	private String telNo;

	private String faxNo;

	private String deptNo;

	private String deptParent;

	private String deptCode;

	private String deptType;

	private String deptByname;

	private String printName;

	private Integer orgflag;

	private String deptAddress;

	private String createtime;

	private String createuser;

	private String manager;

	private String leader1;

	private String leader2;

	private String deptFunc;
	
	private String isvisit;
	
	private String ismore;//是否多地点行医部门（0：不是多点行医部门、1：是多点行医的部门）

	private String dept_type_other;

	private String iskefu;//是否是客服部门
	
	public String getIsmore() {
		return ismore;
	}

	public void setIsmore(String ismore) {
		this.ismore = ismore;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName == null ? null : deptName.trim();
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo == null ? null : telNo.trim();
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo == null ? null : faxNo.trim();
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo == null ? null : deptNo.trim();
	}

	public String getDeptParent() {
		return deptParent;
	}

	public void setDeptParent(String deptParent) {
		this.deptParent = deptParent == null ? null : deptParent.trim();
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode == null ? null : deptCode.trim();
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType == null ? null : deptType.trim();
	}

	public String getDeptByname() {
		return deptByname;
	}

	public void setDeptByname(String deptByname) {
		this.deptByname = deptByname == null ? null : deptByname.trim();
	}

	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName == null ? null : printName.trim();
	}

	public Integer getOrgflag() {
		return orgflag;
	}

	public void setOrgflag(Integer orgflag) {
		this.orgflag = orgflag;
	}

	public String getDeptAddress() {
		return deptAddress;
	}

	public void setDeptAddress(String deptAddress) {
		this.deptAddress = deptAddress == null ? null : deptAddress.trim();
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

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager == null ? null : manager.trim();
	}

	public String getLeader1() {
		return leader1;
	}

	public void setLeader1(String leader1) {
		this.leader1 = leader1 == null ? null : leader1.trim();
	}

	public String getLeader2() {
		return leader2;
	}

	public void setLeader2(String leader2) {
		this.leader2 = leader2 == null ? null : leader2.trim();
	}

	public String getDeptFunc() {
		return deptFunc;
	}

	public void setDeptFunc(String deptFunc) {
		this.deptFunc = deptFunc == null ? null : deptFunc.trim();
	}

	/**  
	  * @Title:  getIsvisit <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public String getIsvisit() {
		return isvisit;
	}

	/**  
	  * @Title:  setIsvisit <BR>  
	  * @Description: please write your description <BR>  
	  * @return: String <BR>  
	  */
	public void setIsvisit(String isvisit) {
		this.isvisit = isvisit;
	}

	public String getDept_type_other() {
		return dept_type_other;
	}

	public void setDept_type_other(String dept_type_other) {
		this.dept_type_other = dept_type_other;
	}

	public String getIskefu() {
		return iskefu;
	}

	public void setIskefu(String iskefu) {
		this.iskefu = iskefu;
	}
}