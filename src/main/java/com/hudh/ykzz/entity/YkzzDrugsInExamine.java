package com.hudh.ykzz.entity;

/**
 * 药库中心-入库审核实体类
 * @author ASUS
 *
 */
public class YkzzDrugsInExamine {
	private String id;
	private String drugsInId; //对应入库表id
	private String packing; //外包装是否破损异常  0：异常     1：通过
	private String certificate; //合格证   0：异常     1：通过
	private String report; //验收报告   0：异常     1：通过
	private String checkUserId; //验收人
	private String checkDate; //验收日期
	private String remark; //备注
	private String result; //审批结果 1：通过  2：不通过
	private String organization;//添加字段
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
	public String getDrugsInId() {
		return drugsInId;
	}
	public void setDrugsInId(String drugsInId) {
		this.drugsInId = drugsInId;
	}
	public String getPacking() {
		return packing;
	}
	public void setPacking(String packing) {
		this.packing = packing;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getCheckUserId() {
		return checkUserId;
	}
	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}
