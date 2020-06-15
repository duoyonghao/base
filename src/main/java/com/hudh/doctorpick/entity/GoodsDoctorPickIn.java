package com.hudh.doctorpick.entity;

import java.io.Serializable;

public class GoodsDoctorPickIn implements Serializable {
  private String seqId;
  
  private String supplier;
  
  private String incode;
  
  private String inremark;
  
  private String summary;
  
  private String pickPerson;
  
  private String userdocument;
  
  private String organization;
  
  private String createtime;
  
  private String creator;
  
  private String rktime;
  
  private String stocktime;
  
  private static final long serialVersionUID = 1L;
  
  public String getRktime() {
    return this.rktime;
  }
  
  public void setRktime(String rktime) {
    this.rktime = rktime;
  }
  
  public String getStocktime() {
    return this.stocktime;
  }
  
  public void setStocktime(String stocktime) {
    this.stocktime = stocktime;
  }
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = (seqId == null) ? null : seqId.trim();
  }
  
  public String getSupplier() {
    return this.supplier;
  }
  
  public void setSupplier(String supplier) {
    this.supplier = (supplier == null) ? null : supplier.trim();
  }
  
  public String getIncode() {
    return this.incode;
  }
  
  public void setIncode(String incode) {
    this.incode = (incode == null) ? null : incode.trim();
  }
  
  public String getInremark() {
    return this.inremark;
  }
  
  public void setInremark(String inremark) {
    this.inremark = (inremark == null) ? null : inremark.trim();
  }
  
  public String getSummary() {
    return this.summary;
  }
  
  public void setSummary(String summary) {
    this.summary = (summary == null) ? null : summary.trim();
  }
  
  public String getPickPerson() {
    return this.pickPerson;
  }
  
  public void setPickPerson(String pickPerson) {
    this.pickPerson = (pickPerson == null) ? null : pickPerson.trim();
  }
  
  public String getUserdocument() {
    return this.userdocument;
  }
  
  public void setUserdocument(String userdocument) {
    this.userdocument = (userdocument == null) ? null : userdocument.trim();
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = (organization == null) ? null : organization.trim();
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
}
