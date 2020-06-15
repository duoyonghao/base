package com.kqds.entity.base;

public class KqdsPush {
	private String seqId;

	private String content;

	private String createtime;

	private String reciveuser;

	private String notifytype;

	private String pcpushed;

	private String pcpushedtime;

	private String apppushed;

	private String apppushedtime;

	private String entityid;

	private String remindurl;

	private String pcpushreaded;

	private String pcpushreadedtime;

	private String isnowpush;

	private String targetpushtime;

	private String organization;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime == null ? null : createtime.trim();
	}

	public String getReciveuser() {
		return reciveuser;
	}

	public void setReciveuser(String reciveuser) {
		this.reciveuser = reciveuser == null ? null : reciveuser.trim();
	}

	public String getNotifytype() {
		return notifytype;
	}

	public void setNotifytype(String notifytype) {
		this.notifytype = notifytype == null ? null : notifytype.trim();
	}

	public String getPcpushed() {
		return pcpushed;
	}

	public void setPcpushed(String pcpushed) {
		this.pcpushed = pcpushed == null ? null : pcpushed.trim();
	}

	public String getPcpushedtime() {
		return pcpushedtime;
	}

	public void setPcpushedtime(String pcpushedtime) {
		this.pcpushedtime = pcpushedtime == null ? null : pcpushedtime.trim();
	}

	public String getApppushed() {
		return apppushed;
	}

	public void setApppushed(String apppushed) {
		this.apppushed = apppushed == null ? null : apppushed.trim();
	}

	public String getApppushedtime() {
		return apppushedtime;
	}

	public void setApppushedtime(String apppushedtime) {
		this.apppushedtime = apppushedtime == null ? null : apppushedtime.trim();
	}

	public String getEntityid() {
		return entityid;
	}

	public void setEntityid(String entityid) {
		this.entityid = entityid == null ? null : entityid.trim();
	}

	public String getRemindurl() {
		return remindurl;
	}

	public void setRemindurl(String remindurl) {
		this.remindurl = remindurl == null ? null : remindurl.trim();
	}

	public String getPcpushreaded() {
		return pcpushreaded;
	}

	public void setPcpushreaded(String pcpushreaded) {
		this.pcpushreaded = pcpushreaded == null ? null : pcpushreaded.trim();
	}

	public String getPcpushreadedtime() {
		return pcpushreadedtime;
	}

	public void setPcpushreadedtime(String pcpushreadedtime) {
		this.pcpushreadedtime = pcpushreadedtime == null ? null : pcpushreadedtime.trim();
	}

	public String getIsnowpush() {
		return isnowpush;
	}

	public void setIsnowpush(String isnowpush) {
		this.isnowpush = isnowpush == null ? null : isnowpush.trim();
	}

	public String getTargetpushtime() {
		return targetpushtime;
	}

	public void setTargetpushtime(String targetpushtime) {
		this.targetpushtime = targetpushtime == null ? null : targetpushtime.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization == null ? null : organization.trim();
	}
}