package com.kqds.entity.base;

public class KqdsJhVideo {
	private String id;//id
	private String url;//路径
	private String createtime;//创建时间
	private String operator;//操作人
	private int del;//是否删除
	private String createuser;//创建人
	private String filename;//文件名
	private String organization;//门诊
	private String burningTime;//视频时长
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public int getDel() {
		return del;
	}
	public void setDel(int del) {
		this.del = del;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getBurningTime() {
		return burningTime;
	}
	public void setBurningTime(String burningTime) {
		this.burningTime = burningTime;
	}
}
