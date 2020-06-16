package com.kqds.entity.base;

public class KqdsSoundRecord {
	private String seqId;

	private String linenum;

	private String phonenumber;

	private String filename;

	private String datedir;

	private String recordtime;

	private String createtime;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId == null ? null : seqId.trim();
	}

	public String getLinenum() {
		return linenum;
	}

	public void setLinenum(String linenum) {
		this.linenum = linenum == null ? null : linenum.trim();
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber == null ? null : phonenumber.trim();
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename == null ? null : filename.trim();
	}

	public String getDatedir() {
		return datedir;
	}

	public void setDatedir(String datedir) {
		this.datedir = datedir == null ? null : datedir.trim();
	}

	public String getRecordtime() {
		return recordtime;
	}

	public void setRecordtime(String recordtime) {
		this.recordtime = recordtime == null ? null : recordtime.trim();
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime == null ? null : createtime.trim();
	}
}