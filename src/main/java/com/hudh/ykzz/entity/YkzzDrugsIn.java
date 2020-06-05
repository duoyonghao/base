package com.hudh.ykzz.entity;

public class YkzzDrugsIn {
	private String id;
	private String rktime; //入库时间
	private String intype; //入库方式
	private String supplier; //供应商
	private String stocktime; //进货时间
	private String incode; //单据编号
	private String inremark; //附加说明
	private String zhaiyao; //摘要
	private String creator; //入库人
	private String createtime; //创建时间
	private int status; //状态 0:正常 1:已删除 2：已修改
	private String modifiyId; //修改的原数据id，本表的其他数据
	private int checkStatus;//验收状态  0:待验收   1:已验收
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
	public String getRktime() {
		return rktime;
	}
	public void setRktime(String rktime) {
		this.rktime = rktime;
	}
	public String getIntype() {
		return intype;
	}
	public void setIntype(String intype) {
		this.intype = intype;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getStocktime() {
		return stocktime;
	}
	public void setStocktime(String stocktime) {
		this.stocktime = stocktime;
	}
	public String getIncode() {
		return incode;
	}
	public void setIncode(String incode) {
		this.incode = incode;
	}
	public String getInremark() {
		return inremark;
	}
	public void setInremark(String inremark) {
		this.inremark = inremark;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getModifiyId() {
		return modifiyId;
	}
	public void setModifiyId(String modifiyId) {
		this.modifiyId = modifiyId;
	}
	public int getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}
}
