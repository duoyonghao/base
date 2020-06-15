package com.kqds.entity.base;

public class KqdsMedicalrecordReview {
  private String seqId;
  
  private String organization;
  
  private String meid;
  
  private String usercode;
  
  private String healingTissue;
  
  private String remark;
  
  private String createuser;
  
  private String createtime;
  
  private String conditionImplants;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = (seqId == null) ? null : seqId.trim();
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = (organization == null) ? null : organization.trim();
  }
  
  public String getMeid() {
    return this.meid;
  }
  
  public void setMeid(String meid) {
    this.meid = (meid == null) ? null : meid.trim();
  }
  
  public String getUsercode() {
    return this.usercode;
  }
  
  public void setUsercode(String usercode) {
    this.usercode = (usercode == null) ? null : usercode.trim();
  }
  
  public String getHealingTissue() {
    return this.healingTissue;
  }
  
  public void setHealingTissue(String healingTissue) {
    this.healingTissue = (healingTissue == null) ? null : healingTissue.trim();
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = (remark == null) ? null : remark.trim();
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
  
  public String getConditionImplants() {
    return this.conditionImplants;
  }
  
  public void setConditionImplants(String conditionImplants) {
    this.conditionImplants = (conditionImplants == null) ? null : conditionImplants.trim();
  }
}
