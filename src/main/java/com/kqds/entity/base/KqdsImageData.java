package com.kqds.entity.base;

public class KqdsImageData {
	private String seqId;

	private String createuser;

	private String createtime;

	private String usercode;

	private String username;

	private String regno;

	private String attachmentid;

	private String attachmentname;

	private String yxfl;

	private String infosort;

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

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno == null ? null : regno.trim();
	}

	public String getAttachmentid() {
		return attachmentid;
	}

	public void setAttachmentid(String attachmentid) {
		this.attachmentid = attachmentid == null ? null : attachmentid.trim();
	}

	public String getAttachmentname() {
		return attachmentname;
	}

	public void setAttachmentname(String attachmentname) {
		this.attachmentname = attachmentname == null ? null : attachmentname.trim();
	}

	public String getYxfl() {
		return yxfl;
	}

	public void setYxfl(String yxfl) {
		this.yxfl = yxfl == null ? null : yxfl.trim();
	}

	public String getInfosort() {
		return infosort;
	}

	public void setInfosort(String infosort) {
		this.infosort = infosort == null ? null : infosort.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}
}