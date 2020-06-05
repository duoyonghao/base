package com.hudh.ykzz.entity;

import java.math.BigDecimal;

public class YkzzDrugsInDetail {
	private String id; //
	private String orderno; //药品编号
	private String contryCode; //国家编码
	private String chemistryName; //药品化学名
	private String drugsTypeone; //一级分类
	private String drugsTypetwo; //二级分类
	private String company; //单位
	private BigDecimal nuitPrice; //单价
	private int quantity; //数量
	private BigDecimal amount; //金额
	private String remark; //备注
	private String createdate; //生产效期
	private String valid; //有效期
	private String batchnum; //批号
	private String regisnum; //注册证号
	private String parentid; //对应商品入库表ID
	private String drugsId; //药品id
	private int status; //状态 0:正常 1:已删除 2：已修改
	private String createtime; // #####添加创建时间字段#####
	private Integer batchnoNum; // #####添加统计批号数量字段#####
	private String organization;//添加字段
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public Integer getBatchnoNum() {
		return batchnoNum;
	}
	public void setBatchnoNum(Integer batchnoNum) {
		this.batchnoNum = batchnoNum;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
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
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getBatchnum() {
		return batchnum;
	}
	public void setBatchnum(String batchnum) {
		this.batchnum = batchnum;
	}
	public String getRegisnum() {
		return regisnum;
	}
	public void setRegisnum(String regisnum) {
		this.regisnum = regisnum;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
