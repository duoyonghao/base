package com.kqds.entity.sys;

public class YZOrganization {
  private String seqId;
  
  private String unitName;
  
  private String postcode;
  
  private String address;
  
  private String website;
  
  private String email;
  
  private String telephone;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = (seqId == null) ? null : seqId.trim();
  }
  
  public String getUnitName() {
    return this.unitName;
  }
  
  public void setUnitName(String unitName) {
    this.unitName = (unitName == null) ? null : unitName.trim();
  }
  
  public String getPostcode() {
    return this.postcode;
  }
  
  public void setPostcode(String postcode) {
    this.postcode = (postcode == null) ? null : postcode.trim();
  }
  
  public String getAddress() {
    return this.address;
  }
  
  public void setAddress(String address) {
    this.address = (address == null) ? null : address.trim();
  }
  
  public String getWebsite() {
    return this.website;
  }
  
  public void setWebsite(String website) {
    this.website = (website == null) ? null : website.trim();
  }
  
  public String getEmail() {
    return this.email;
  }
  
  public void setEmail(String email) {
    this.email = (email == null) ? null : email.trim();
  }
  
  public String getTelephone() {
    return this.telephone;
  }
  
  public void setTelephone(String telephone) {
    this.telephone = (telephone == null) ? null : telephone.trim();
  }
}
