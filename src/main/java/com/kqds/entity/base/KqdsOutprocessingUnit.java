package com.kqds.entity.base;

public class KqdsOutprocessingUnit {
  private String seqId;
  
  private String createuser;
  
  private String createtime;
  
  private String code;
  
  private String name;
  
  private String address;
  
  private String phone;
  
  private String remark;
  
  private String organization;
  
  private Integer useflag;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = (seqId == null) ? null : seqId.trim();
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
  
  public String getCode() {
    return this.code;
  }
  
  public void setCode(String code) {
    this.code = (code == null) ? null : code.trim();
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = (name == null) ? null : name.trim();
  }
  
  public String getAddress() {
    return this.address;
  }
  
  public void setAddress(String address) {
    this.address = (address == null) ? null : address.trim();
  }
  
  public String getPhone() {
    return this.phone;
  }
  
  public void setPhone(String phone) {
    this.phone = (phone == null) ? null : phone.trim();
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = (remark == null) ? null : remark.trim();
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = (organization == null) ? null : organization.trim();
  }
  
  public Integer getUseflag() {
    return this.useflag;
  }
  
  public void setUseflag(Integer useflag) {
    this.useflag = useflag;
  }
}
