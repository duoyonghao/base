package com.hudh.doctorpick.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsPickSendBack implements Serializable {
  private String seqId;
  
  private String goodsname;
  
  private String goodscode;
  
  private String deptpartname;
  
  private String housename;
  
  private String goodsunit;
  
  private String nums;
  
  private String goodsnorms;
  
  private String createuser;
  
  private String createtime;
  
  private String organization;
  
  private String goodsuuid;
  
  private BigDecimal nuitPrice;
  
  private BigDecimal amount;
  
  private String remark;
  
  private String supplier;
  
  private String detailId;
  
  private String batchnum;
  
  private static final long serialVersionUID = 1L;
  
  public String getDetailId() {
    return this.detailId;
  }
  
  public void setDetailId(String detailId) {
    this.detailId = detailId;
  }
  
  public String getSupplier() {
    return this.supplier;
  }
  
  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public BigDecimal getNuitPrice() {
    return this.nuitPrice;
  }
  
  public void setNuitPrice(BigDecimal nuitPrice) {
    this.nuitPrice = nuitPrice;
  }
  
  public BigDecimal getAmount() {
    return this.amount;
  }
  
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
  
  public String getGoodsuuid() {
    return this.goodsuuid;
  }
  
  public void setGoodsuuid(String goodsuuid) {
    this.goodsuuid = goodsuuid;
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = organization;
  }
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = (seqId == null) ? null : seqId.trim();
  }
  
  public String getGoodsname() {
    return this.goodsname;
  }
  
  public void setGoodsname(String goodsname) {
    this.goodsname = (goodsname == null) ? null : goodsname.trim();
  }
  
  public String getGoodscode() {
    return this.goodscode;
  }
  
  public void setGoodscode(String goodscode) {
    this.goodscode = (goodscode == null) ? null : goodscode.trim();
  }
  
  public String getDeptpartname() {
    return this.deptpartname;
  }
  
  public void setDeptpartname(String deptpartname) {
    this.deptpartname = (deptpartname == null) ? null : deptpartname.trim();
  }
  
  public String getHousename() {
    return this.housename;
  }
  
  public void setHousename(String housename) {
    this.housename = (housename == null) ? null : housename.trim();
  }
  
  public String getGoodsunit() {
    return this.goodsunit;
  }
  
  public void setGoodsunit(String goodsunit) {
    this.goodsunit = (goodsunit == null) ? null : goodsunit.trim();
  }
  
  public String getNums() {
    return this.nums;
  }
  
  public void setNums(String nums) {
    this.nums = (nums == null) ? null : nums.trim();
  }
  
  public String getGoodsnorms() {
    return this.goodsnorms;
  }
  
  public void setGoodsnorms(String goodsnorms) {
    this.goodsnorms = (goodsnorms == null) ? null : goodsnorms.trim();
  }
  
  public String getCreateuser() {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser) {
    this.createuser = (createuser == null) ? null : createuser.trim();
  }
  
  public String getCreatetime() {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime) {
    this.createtime = (createtime == null) ? null : createtime.trim();
  }
  
  public String getBatchnum() {
    return this.batchnum;
  }
  
  public void setBatchnum(String batchnum) {
    this.batchnum = batchnum;
  }
}
