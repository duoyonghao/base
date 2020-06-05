package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsDeindustryrecord {
	private String seqId;

	private String createuser;

	private String createtime;

	private String qyname;

	private String address;

	private String cooperationtype;

	private String lxr1;

	private String activitydate;

	private String organization;

	private String lxr2;

	private String ishz;

	private String activitycontacts;

	private BigDecimal outmoney;

	private String attachmentid;

	private String attachmentname;

	private String kehu;

	private String kehudesc;

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

	public String getQyname() {
		return qyname;
	}

	public void setQyname(String qyname) {
		this.qyname = qyname == null ? null : qyname.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getCooperationtype() {
		return cooperationtype;
	}

	public void setCooperationtype(String cooperationtype) {
		this.cooperationtype = cooperationtype == null ? null : cooperationtype.trim();
	}

	public String getLxr1() {
		return lxr1;
	}

	public void setLxr1(String lxr1) {
		this.lxr1 = lxr1 == null ? null : lxr1.trim();
	}

	public String getActivitydate() {
		return activitydate;
	}

	public void setActivitydate(String activitydate) {
		this.activitydate = activitydate == null ? null : activitydate.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}

	public String getLxr2() {
		return lxr2;
	}

	public void setLxr2(String lxr2) {
		this.lxr2 = lxr2 == null ? null : lxr2.trim();
	}

	public String getIshz() {
		return ishz;
	}

	public void setIshz(String ishz) {
		this.ishz = ishz == null ? null : ishz.trim();
	}

	public String getActivitycontacts() {
		return activitycontacts;
	}

	public void setActivitycontacts(String activitycontacts) {
		this.activitycontacts = activitycontacts == null ? null : activitycontacts.trim();
	}

	public BigDecimal getOutmoney() {
		return outmoney;
	}

	public void setOutmoney(BigDecimal outmoney) {
		this.outmoney = outmoney;
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

	public String getKehu() {
		return kehu;
	}

	public void setKehu(String kehu) {
		this.kehu = kehu == null ? null : kehu.trim();
	}

	public String getKehudesc() {
		return kehudesc;
	}

	public void setKehudesc(String kehudesc) {
		this.kehudesc = kehudesc == null ? null : kehudesc.trim();
	}
}