package com.hudh.dzbl.entity;

/**
 * 电子病历模板类
 * @author XKY
 *
 */
public class DzblTemplate {
	private String id;
	private String title; //标题
	private String blfl;  //病历分类
	private String bc; //病程
	private String detail;//模板内容
	private String createtime;//创建时间
	private String modifytime;//最后修改时间
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
	public String getModifytime() {
		return modifytime;
	}
	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}
	public String getSstype() {
		return sstype;
	}
	public void setSstype(String sstype) {
		this.sstype = sstype;
	}
	
}
