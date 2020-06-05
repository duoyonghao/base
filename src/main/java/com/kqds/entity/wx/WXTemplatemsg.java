package com.kqds.entity.wx;

public class WXTemplatemsg {
	private String seqId;

	private String templateId;

	private String primaryIndustry;

	private String deputyIndustry;

	private String title;

	private String content;

	private String example;

	private Integer status;

	private String createtime;

	private String createuser;

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

	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry == null ? null : primaryIndustry.trim();
	}

	public String getDeputyIndustry() {
		return deputyIndustry;
	}

	public void setDeputyIndustry(String deputyIndustry) {
		this.deputyIndustry = deputyIndustry == null ? null : deputyIndustry.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example == null ? null : example.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
}