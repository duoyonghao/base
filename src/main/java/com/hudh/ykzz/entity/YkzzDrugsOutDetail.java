package com.hudh.ykzz.entity;

import java.math.BigDecimal;

public class YkzzDrugsOutDetail {
	private String id; //	id
	private String orderno; //	药品编号
	private String contryCode; //code	国家编码
	private String chemistryName; //	药品化学名
	private String drugsTypeone; //	一级分类
	private String drugsTypetwo; //	二级分类
	private String company; //	单位
	private BigDecimal nuitPrice; //	单价
	private Integer kcQuantity; //	库存数量
	private Integer ckQuantity; //	出库数量
	private BigDecimal amount; //	金额
	private String remark; //	备注
	private String parentid; //	对应商品出库表ID
	private String drugsId; //	对应药品表id
	private Integer status; //	状态 0:正常 1:已删除 2：已修改
	private String batchnum;// #####添加批号字段#####
	private String createtime;// #####添加创建时间字段#####
	private String batchToNumber;// #####添加批号对应数量字段#####
	private String batchToId;//添加字段 入库明细表ID
	private String organization;//添加字段
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getBatchToId() {
		return batchToId;
	}
	public void setBatchToId(String batchToId) {
		this.batchToId = batchToId;
	}
	public String getCreatetime() {
		return createtime;
	}
	public String getBatchToNumber() {
		return batchToNumber;
	}
	public void setBatchToNumber(String batchToNumber) {
		this.batchToNumber = batchToNumber;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getBatchnum() {
		return batchnum;
	}
	public void setBatchnum(String batchnum) {
		this.batchnum = batchnum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getContryCode() {
		return contryCode;
	}
	public void setContryCode(String contryCode) {
		this.contryCode = contryCode;
	}
	public String getChemistryName() {
		return chemistryName;
	}
	public void setChemistryName(String chemistryName) {
		this.chemistryName = chemistryName;
	}
	public String getDrugsTypeone() {
		return drugsTypeone;
	}
	public void setDrugsTypeone(String drugsTypeone) {
		this.drugsTypeone = drugsTypeone;
	}
	public String getDrugsTypetwo() {
		return drugsTypetwo;
	}
	public void setDrugsTypetwo(String drugsTypetwo) {
		this.drugsTypetwo = drugsTypetwo;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public BigDecimal getNuitPrice() {
		return nuitPrice;
	}
	public void setNuitPrice(BigDecimal nuitPrice) {
		this.nuitPrice = nuitPrice;
	}
	public Integer getKcQuantity() {
		return kcQuantity;
	}
	public void setKcQuantity(Integer kcQuantity) {
		this.kcQuantity = kcQuantity;
	}
	public Integer getCkQuantity() {
		return ckQuantity;
	}
	public void setCkQuantity(Integer ckQuantity) {
		this.ckQuantity = ckQuantity;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getDrugsId() {
		return drugsId;
	}
	public void setDrugsId(String drugsId) {
		this.drugsId = drugsId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
