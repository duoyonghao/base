package com.hudh.lclj.entity;

import java.io.Serializable;

public class OperatingRecord implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String id;
  
  private String lcljId;
  
  private String name;
  
  private String createTime;
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getLcljId() {
    return this.lcljId;
  }
  
  public void setLcljId(String lcljId) {
    this.lcljId = lcljId;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getCreateTime() {
    return this.createTime;
  }
  
  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
  
  public String toString() {
    return "OperatingRecord [id=" + this.id + ", lcljId=" + this.lcljId + ", name=" + this.name + ", createTime=" + this.createTime + 
      "]";
  }
}
