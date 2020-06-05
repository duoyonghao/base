package com.kqds.entity.base;

public class KqdsJzmdType {
	private String seqId;

	private String createuser;

	private String createtime;

	private String jzmd;

	private String jzmdchildname;

	private String txjzmd;

	private String organization;

	private Integer yxday;

	private Integer useflag;

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

	public String getJzmd() {
		return jzmd;
	}

	public void setJzmd(String jzmd) {
		this.jzmd = jzmd == null ? null : jzmd.trim();
	}

	public String getJzmdchildname() {
		return jzmdchildname;
	}

	public void setJzmdchildname(String jzmdchildname) {
		this.jzmdchildname = jzmdchildname == null ? null : jzmdchildname.trim();
	}

	public String getTxjzmd() {
		return txjzmd;
	}

	public void setTxjzmd(String txjzmd) {
		this.txjzmd = txjzmd == null ? null : txjzmd.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public Integer getYxday() {
		return yxday;
	}

	public void setYxday(Integer yxday) {
		this.yxday = yxday;
	}

	public Integer getUseflag() {
		return useflag;
	}

	public void setUseflag(Integer useflag) {
		this.useflag = useflag;
	}
}