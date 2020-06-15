package com.hudh.ykzz.entity;

import java.math.BigDecimal;

public class YkzzDrugsOutDetail {
  private String id;
  
  private String orderno;
  
  private String contryCode;
  
  private String chemistryName;
  
  private String drugsTypeone;
  
  private String drugsTypetwo;
  
  private String company;
  
  private BigDecimal nuitPrice;
  
  private Integer kcQuantity;
  
  private Integer ckQuantity;
  
  private BigDecimal amount;
  
  private String remark;
  
  private String parentid;
  
  private String drugsId;
  
  private Integer status;
  
  private String batchnum;
  
  private String createtime;
  
  private String batchToNumber;
  
  private String batchToId;
  
  private String organization;
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = organization;
  }
  
  public String getBatchToId() {
    return this.batchToId;
  }
  
  public void setBatchToId(String batchToId) {
    this.batchToId = batchToId;
  }
  
  public String getCreatetime() {
    return this.createtime;
  }
  
  public String getBatchToNumber() {
    return this.batchToNumber;
  }
  
  public void setBatchToNumber(String batchToNumber) {
    this.batchToNumber = batchToNumber;
  }
  
  public void setCreatetime(String createtime) {
    this.createtime = createtime;
  }
  
  public String getBatchnum() {
    return this.batchnum;
  }
  
  public void setBatchnum(String batchnum) {
    this.batchnum = batchnum;
  }
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getOrderno() {
    return this.orderno;
  }
  
  public void setOrderno(String orderno) {
    this.orderno = orderno;
  }
  
  public String getContryCode() {
    return this.contryCode;
  }
  
  public void setContryCode(String contryCode) {
    this.contryCode = contryCode;
  }
  
  public String getChemistryName() {
    return this.chemistryName;
  }
  
  public void setChemistryName(String chemistryName) {
    this.chemistryName = chemistryName;
  }
  
  public String getDrugsTypeone() {
    return this.drugsTypeone;
  }
  
  public void setDrugsTypeone(String drugsTypeone) {
    this.drugsTypeone = drugsTypeone;
  }
  
  public String getDrugsTypetwo() {
    return this.drugsTypetwo;
  }
  
  public void setDrugsTypetwo(String drugsTypetwo) {
    this.drugsTypetwo = drugsTypetwo;
  }
  
  public String getCompany() {
    return this.company;
  }
  
  public void setCompany(String company) {
    this.company = company;
  }
  
  public BigDecimal getNuitPrice() {
    return this.nuitPrice;
  }
  
  public void setNuitPrice(BigDecimal nuitPrice) {
    this.nuitPrice = nuitPrice;
  }
  
  public Integer getKcQuantity() {
    return this.kcQuantity;
  }
  
  public void setKcQuantity(Integer kcQuantity) {
    this.kcQuantity = kcQuantity;
  }
  
  public Integer getCkQuantity() {
    return this.ckQuantity;
  }
  
  public void setCkQuantity(Integer ckQuantity) {
    this.ckQuantity = ckQuantity;
  }
  
  public BigDecimal getAmount() {
    return this.amount;
  }
  
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String getParentid() {
    return this.parentid;
  }
  
  public void setParentid(String parentid) {
    this.parentid = parentid;
  }
  
  public String getDrugsId() {
    return this.drugsId;
  }
  
  public void setDrugsId(String drugsId) {
    this.drugsId = drugsId;
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
}
