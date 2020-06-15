package com.hudh.lclj.entity;

public class LcljOrder {
  private String id;
  
  private String orderNumber;
  
  private String totalTooth;
  
  private String createtime;
  
  private String blcode;
  
  private String remainTooth;
  
  private String status;
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getOrderNumber() {
    return this.orderNumber;
  }
  
  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }
  
  public String getTotalTooth() {
    return this.totalTooth;
  }
  
  public void setTotalTooth(String totalTooth) {
    this.totalTooth = totalTooth;
  }
  
  public String getCreatetime() {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime) {
    this.createtime = createtime;
  }
  
  public String getBlcode() {
    return this.blcode;
  }
  
  public void setBlcode(String blcode) {
    this.blcode = blcode;
  }
  
  public String getRemainTooth() {
    return this.remainTooth;
  }
  
  public void setRemainTooth(String remainTooth) {
    this.remainTooth = remainTooth;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
}
