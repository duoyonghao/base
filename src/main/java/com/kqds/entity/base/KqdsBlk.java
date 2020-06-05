package com.kqds.entity.base;

public class KqdsBlk {
	private String seqId;

	private String createuser;

	private String createtime;

	private String blname;

	private String blkfl;

	private String type;

	private String mtype;

	private String organization;

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

	public String getBlname() {
		return blname;
	}

	public void setBlname(String blname) {
		this.blname = blname == null ? null : blname.trim();
	}

	public String getBlkfl() {
		return blkfl;
	}

	public void setBlkfl(String blkfl) {
		this.blkfl = blkfl == null ? null : blkfl.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getMtype() {
		return mtype;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype == null ? null : mtype.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}
}