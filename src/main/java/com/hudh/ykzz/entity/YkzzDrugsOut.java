package com.hudh.ykzz.entity;

public class YkzzDrugsOut {
	private String id; //	id
	private String cktime; //	出库时间
	private String outtype; //	出库方式
	private String supplier; //	供应商
	private String outcode; //	单据编号
	private String outremark; //	附加说明
	private String zhaiyao; //	摘要
	private String creator; //	出库人
	private String createtime; //	创建时间
	private Integer status; //	状态 0:正常 1:已删除 3：已修改
	private String modifiyId; //	修改的原数据，本表的其他数据
	private String outdept; //出库部门
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
	public String getCktime() {
		return cktime;
	}
	public void setCktime(String cktime) {
		this.cktime = cktime;
	}
	public String getOuttype() {
		return outtype;
	}
	public void setOuttype(String outtype) {
		this.outtype = outtype;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getOutcode() {
		return outcode;
	}
	public void setOutcode(String outcode) {
		this.outcode = outcode;
	}
	public String getOutremark() {
		return outremark;
	}
	public void setOutremark(String outremark) {
		this.outremark = outremark;
	}
	public String getZhaiyao() {
		return zhaiyao;
	}
	public void setZhaiyao(String zhaiyao) {
		this.zhaiyao = zhaiyao;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getModifiyId() {
		return modifiyId;
	}
	public void setModifiyId(String modifiyId) {
		this.modifiyId = modifiyId;
	}
	public String getOutdept() {
		return outdept;
	}
	public void setOutdept(String outdept) {
		this.outdept = outdept;
	}
	
}
