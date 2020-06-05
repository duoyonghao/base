package com.kqds.entity.base;

public class KqdsPrintSet {
	private String seqId;

	private String createuser;

	private String createtime;

	private String printname;

	private String printtype;

	private String sortno;

	private String printurl;

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

	public String getPrintname() {
		return printname;
	}

	public void setPrintname(String printname) {
		this.printname = printname == null ? null : printname.trim();
	}

	public String getPrinttype() {
		return printtype;
	}

	public void setPrinttype(String printtype) {
		this.printtype = printtype == null ? null : printtype.trim();
	}

	public String getSortno() {
		return sortno;
	}

	public void setSortno(String sortno) {
		this.sortno = sortno == null ? null : sortno.trim();
	}

	public String getPrinturl() {
		return printurl;
	}

	public void setPrinturl(String printurl) {
		this.printurl = printurl == null ? null : printurl.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}
}