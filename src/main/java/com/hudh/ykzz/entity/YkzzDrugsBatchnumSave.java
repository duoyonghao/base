package com.hudh.ykzz.entity;

public class YkzzDrugsBatchnumSave {
	
	private String id;
	private String drugsno;//药品编号
	private String drugsname;//药品 名称
	private String batchnum;//批号
	private String number;//发药数量
	private String batchno;//发药批号
	private String costOrderDetailId;//费用明细表ID
	private String organization;//添加字段
	private String createtime;//创建人
	private String createname;//创建时间
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getBatchno() {
		return batchno;
	}
	public String getCostOrderDetailId() {
		return costOrderDetailId;
	}
	public void setCostOrderDetailId(String costOrderDetailId) {
		this.costOrderDetailId = costOrderDetailId;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDrugsno() {
		return drugsno;
	}
	public void setDrugsno(String drugsno) {
		this.drugsno = drugsno;
	}
	public String getDrugsname() {
		return drugsname;
	}
	public void setDrugsname(String drugsname) {
		this.drugsname = drugsname;
	}
	public String getBatchnum() {
		return batchnum;
	}
	public void setBatchnum(String batchnum) {
		this.batchnum = batchnum;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCreatename() {
		return createname;
	}
	public void setCreatename(String createname) {
		this.createname = createname;
	}

}
