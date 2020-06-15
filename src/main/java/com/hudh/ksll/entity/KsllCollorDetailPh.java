package com.hudh.ksll.entity;

import java.math.BigDecimal;

public class KsllCollorDetailPh {
  private String seqId;
  
  private String createtime;
  
  private String ph;
  
  private int phnum;
  
  private String parentid;
  
  private String addnumber;
  
  private String addtime;
  
  private BigDecimal price;
  
  private int nums;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = seqId;
  }
  
  public String getCreatetime() {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime) {
    this.createtime = createtime;
  }
  
  public String getPh() {
    return this.ph;
  }
  
  public void setPh(String ph) {
    this.ph = ph;
  }
  
  public int getPhnum() {
    return this.phnum;
  }
  
  public void setPhnum(int phnum) {
    this.phnum = phnum;
  }
  
  public String getParentid() {
    return this.parentid;
  }
  
  public void setParentid(String parentid) {
    this.parentid = parentid;
  }
  
  public String getAddnumber() {
    return this.addnumber;
  }
  
  public void setAddnumber(String addnumber) {
    this.addnumber = addnumber;
  }
  
  public BigDecimal getPrice() {
    return this.price;
  }
  
  public void setPrice(BigDecimal price) {
    this.price = price;
  }
  
  public String getAddtime() {
    return this.addtime;
  }
  
  public void setAddtime(String addtime) {
    this.addtime = addtime;
  }
  
  public int getNums() {
    return this.nums;
  }
  
  public void setNums(int nums) {
    this.nums = nums;
  }
}
