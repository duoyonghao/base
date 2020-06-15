package com.hudh.ykzz.entity;

public class YkzzManu {
  private String id;
  
  private int orderno;
  
  private String manuName;
  
  private String createtime;
  
  private String creator;
  
  private String manuCode;
  
  private String organization;
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = organization;
  }
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public int getOrderno() {
    return this.orderno;
  }
  
  public void setOrderno(int orderno) {
    this.orderno = orderno;
  }
  
  public String getManuName() {
    return this.manuName;
  }
  
  public void setManuName(String manuName) {
    this.manuName = manuName;
  }
  
  public String getCreatetime() {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime) {
    this.createtime = createtime;
  }
  
  public String getCreator() {
    return this.creator;
  }
  
  public void setCreator(String creator) {
    this.creator = creator;
  }
  
  public String getManuCode() {
    return this.manuCode;
  }
  
  public void setManuCode(String manuCode) {
    this.manuCode = manuCode;
  }
}
