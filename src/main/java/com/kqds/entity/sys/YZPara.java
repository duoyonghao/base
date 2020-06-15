package com.kqds.entity.sys;

public class YZPara {
  private String seqId;
  
  private String remark;
  
  private String createtime;
  
  private String createuser;
  
  private String organization;
  
  private String paraName;
  
  private String paraValue;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = (seqId == null) ? null : seqId.trim();
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = (remark == null) ? null : remark.trim();
  }
  
  public String getCreatetime() {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime) {
    this.createtime = (createtime == null) ? null : createtime.trim();
  }
  
  public String getCreateuser() {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser) {
    this.createuser = (createuser == null) ? null : createuser.trim();
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = (organization == null) ? null : organization.trim();
  }
  
  public String getParaName() {
    return this.paraName;
  }
  
  public void setParaName(String paraName) {
    this.paraName = (paraName == null) ? null : paraName.trim();
  }
  
  public String getParaValue() {
    return this.paraValue;
  }
  
  public void setParaValue(String paraValue) {
    this.paraValue = (paraValue == null) ? null : paraValue.trim();
  }
}
