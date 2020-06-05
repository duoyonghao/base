package com.kqds.entity.wx;

public class WXTemplateitem {
	private String seqId;

	private String templateId;

	private String templateSeqid;

	private String first;

	private String keyword1;

	private String keyword2;

	private String keyword3;

	private String keyword4;

	private String keyword5;

	private String remark;

	private Integer status;

	private String createuser;

	private String createtime;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId == null ? null : templateId.trim();
	}

	public String getTemplateSeqid() {
		return templateSeqid;
	}

	public void setTemplateSeqid(String templateSeqid) {
		this.templateSeqid = templateSeqid == null ? null : templateSeqid.trim();
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first == null ? null : first.trim();
	}

	public String getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1 == null ? null : keyword1.trim();
	}

	public String getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2 == null ? null : keyword2.trim();
	}

	public String getKeyword3() {
		return keyword3;
	}

	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3 == null ? null : keyword3.trim();
	}

	public String getKeyword4() {
		return keyword4;
	}

	public void setKeyword4(String keyword4) {
		this.keyword4 = keyword4 == null ? null : keyword4.trim();
	}

	public String getKeyword5() {
		return keyword5;
	}

	public void setKeyword5(String keyword5) {
		this.keyword5 = keyword5 == null ? null : keyword5.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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