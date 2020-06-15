package com.hudh.ykzz.entity;

import java.io.Serializable;

public class YkzzManufacturers implements Serializable {
  private String id;
  
  private String orderno;
  
  private String manufacturersName;
  
  private String createtime;
  
  private String creator;
  
  private String manufacturersCode;
  
  private String organization;
  
  private static final long serialVersionUID = 1L;
  
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
    this.id = (id == null) ? null : id.trim();
  }
  
  public String getOrderno() {
    return this.orderno;
  }
  
  public void setOrderno(String orderno) {
    this.orderno = (orderno == null) ? null : orderno.trim();
  }
  
  public String getManufacturersName() {
    return this.manufacturersName;
  }
  
  public void setManufacturersName(String manufacturersName) {
    this.manufacturersName = (manufacturersName == null) ? null : manufacturersName.trim();
  }
  
  public String getCreatetime() {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime) {
    this.createtime = (createtime == null) ? null : createtime.trim();
  }
  
  public String getCreator() {
    return this.creator;
  }
  
  public void setCreator(String creator) {
    this.creator = (creator == null) ? null : creator.trim();
  }
  
  public String getManufacturersCode() {
    return this.manufacturersCode;
  }
  
  public void setManufacturersCode(String manufacturersCode) {
    this.manufacturersCode = (manufacturersCode == null) ? null : manufacturersCode.trim();
  }
}
