package com.kqds.entity.base;

public class KqdsSms {
  private String seqId;
  
  private String usercode;
  
  private String smstype;
  
  private Integer smsstate;
  
  private Integer sendstate;
  
  private String sendphone;
  
  private String createtime;
  
  private String createuser;
  
  private String smsnexttype;
  
  private String organization;
  
  private String sendtime;
  
  private String smscontent;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = (seqId == null) ? null : seqId.trim();
  }
  
  public String getUsercode() {
    return this.usercode;
  }
  
  public void setUsercode(String usercode) {
    this.usercode = (usercode == null) ? null : usercode.trim();
  }
  
  public String getSmstype() {
    return this.smstype;
  }
  
  public void setSmstype(String smstype) {
    this.smstype = (smstype == null) ? null : smstype.trim();
  }
  
  public Integer getSmsstate() {
    return this.smsstate;
  }
  
  public void setSmsstate(Integer smsstate) {
    this.smsstate = smsstate;
  }
  
  public Integer getSendstate() {
    return this.sendstate;
  }
  
  public void setSendstate(Integer sendstate) {
    this.sendstate = sendstate;
  }
  
  public String getSendphone() {
    return this.sendphone;
  }
  
  public void setSendphone(String sendphone) {
    this.sendphone = (sendphone == null) ? null : sendphone.trim();
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
  
  public String getSmsnexttype() {
    return this.smsnexttype;
  }
  
  public void setSmsnexttype(String smsnexttype) {
    this.smsnexttype = (smsnexttype == null) ? null : smsnexttype.trim();
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = (organization == null) ? null : organization.trim();
  }
  
  public String getSendtime() {
    return this.sendtime;
  }
  
  public void setSendtime(String sendtime) {
    this.sendtime = (sendtime == null) ? null : sendtime.trim();
  }
  
  public String getSmscontent() {
    return this.smscontent;
  }
  
  public void setSmscontent(String smscontent) {
    this.smscontent = (smscontent == null) ? null : smscontent.trim();
  }
}
