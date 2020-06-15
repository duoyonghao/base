package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsCkGoodsOutDetail {
  private String seqId;
  
  private String goodsuuid;
  
  private BigDecimal outprice;
  
  private Integer outnum;
  
  private String sqremark;
  
  private String outcode;
  
  private String organization;
  
  private String createuser;
  
  private String createtime;
  
  private BigDecimal ckmoney;
  
  private String goodscode;
  
  private String addtime;
  
  private String addnumber;
  
  private String ph;
  
  private Integer phnum;
  
  private String type;
  
  private String yxdate;
  
  public String getAddtime() {
    return this.addtime;
  }
  
  public void setAddtime(String addtime) {
    this.addtime = addtime;
  }
  
  public String getType() {
    return this.type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public String getAddnumber() {
    return this.addnumber;
  }
  
  public void setAddnumber(String addnumber) {
    this.addnumber = addnumber;
  }
  
  public String getYxdate() {
    return this.yxdate;
  }
  
  public void setYxdate(String yxdate) {
    this.yxdate = yxdate;
  }
  
  public String getPh() {
    return this.ph;
  }
  
  public Integer getPhnum() {
    return this.phnum;
  }
  
  public void setPhnum(Integer phnum) {
    this.phnum = phnum;
  }
  
  public void setPh(String ph) {
    this.ph = ph;
  }
  
  public String getAddTime() {
    return this.addtime;
  }
  
  public void setAddTime(String addtime) {
    this.addtime = (addtime == null) ? null : addtime.trim();
  }
  
  public String getAddNumber() {
    return this.addnumber;
  }
  
  public void setAddNumber(String addnumber) {
    this.addnumber = (addnumber == null) ? null : addnumber.trim();
  }
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = (seqId == null) ? null : seqId.trim();
  }
  
  public String getGoodsuuid() {
    return this.goodsuuid;
  }
  
  public void setGoodsuuid(String goodsuuid) {
    this.goodsuuid = (goodsuuid == null) ? null : goodsuuid.trim();
  }
  
  public BigDecimal getOutprice() {
    return this.outprice;
  }
  
  public void setOutprice(BigDecimal outprice) {
    this.outprice = outprice;
  }
  
  public Integer getOutnum() {
    return this.outnum;
  }
  
  public void setOutnum(Integer outnum) {
    this.outnum = outnum;
  }
  
  public String getSqremark() {
    return this.sqremark;
  }
  
  public void setSqremark(String sqremark) {
    this.sqremark = (sqremark == null) ? null : sqremark.trim();
  }
  
  public String getOutcode() {
    return this.outcode;
  }
  
  public void setOutcode(String outcode) {
    this.outcode = (outcode == null) ? null : outcode.trim();
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = (organization == null) ? null : organization.trim();
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
  
  public BigDecimal getCkmoney() {
    return this.ckmoney;
  }
  
  public void setCkmoney(BigDecimal ckmoney) {
    this.ckmoney = ckmoney;
  }
  
  public String getGoodscode() {
    return this.goodscode;
  }
  
  public void setGoodscode(String goodscode) {
    this.goodscode = goodscode;
  }
}
