package com.kqds.entity.base;

public class KqdsCkDept {
	private String seqId;

	private String deptname;

	private String housecode;

	private Integer sortno;

	private String organization;

	private String createuser;

	private String createtime;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname == null ? null : deptname.trim();
	}

	public String getHousecode() {
		return housecode;
	}

	public void setHousecode(String housecode) {
		this.housecode = housecode == null ? null : housecode.trim();
	}

	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
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
}