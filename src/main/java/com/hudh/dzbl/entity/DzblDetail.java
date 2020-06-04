package com.hudh.dzbl.entity;

/**
 * 电子病历模板类
 * @author XKY
 *
 */
public class DzblDetail {
	private String id;
	private String title; //标题
	private String blfl;  //病历分类
	private String bc; //病程
	private String detail;//模板内容
	private String createtime;//创建时间
	private String status;//0：暂存  1：生效的
	private String blcode;//病历号
	private String creator;//创建人
	private String sstype;//手术类型
	private String organization;//门诊
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBlfl() {
		return blfl;
	}
	public void setBlfl(String blfl) {
		this.blfl = blfl;
	}
	public String getBc() {
		return bc;
	}
	public void setBc(String bc) {
		this.bc = bc;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBlcode() {
		return blcode;
	}
	public void setBlcode(String blcode) {
		this.blcode = blcode;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getSstype() {
		return sstype;
	}
	public void setSstype(String sstype) {
		this.sstype = sstype;
	}
	
	
}
