package com.kqds.entity.base;

public class KqdsReceiveinfoContent {
	private String seqId;

	private String createuser;

	private String createtime;

	private String receiveid;

	private String zs;

	private String check;

	private String jy;

	private String member;

	private String fy;

	private String kaifa;

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

	public String getReceiveid() {
		return receiveid;
	}

	public void setReceiveid(String receiveid) {
		this.receiveid = receiveid == null ? null : receiveid.trim();
	}

	public String getZs() {
		return zs;
	}

	public void setZs(String zs) {
		this.zs = zs == null ? null : zs.trim();
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check == null ? null : check.trim();
	}

	public String getJy() {
		return jy;
	}

	public void setJy(String jy) {
		this.jy = jy == null ? null : jy.trim();
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member == null ? null : member.trim();
	}

	public String getFy() {
		return fy;
	}

	public void setFy(String fy) {
		this.fy = fy == null ? null : fy.trim();
	}

	public String getKaifa() {
		return kaifa;
	}

	public void setKaifa(String kaifa) {
		this.kaifa = kaifa == null ? null : kaifa.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}
}