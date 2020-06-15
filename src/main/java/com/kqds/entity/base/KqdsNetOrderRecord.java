package com.kqds.entity.base;

public class KqdsNetOrderRecord {
  private String seqId;
  
  private String createuser;
  
  private String createtime;
  
  private String content;
  
  private String nerorderseqid;
  
  private String usercode;
  
  private String username;
  
  private String organization;
  
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
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = (content == null) ? null : content.trim();
  }
  
  public String getNerorderseqid() {
    return this.nerorderseqid;
  }
  
  public void setNerorderseqid(String nerorderseqid) {
    this.nerorderseqid = (nerorderseqid == null) ? null : nerorderseqid.trim();
  }
  
  public String getUsercode() {
    return this.usercode;
  }
  
  public void setUsercode(String usercode) {
    this.usercode = (usercode == null) ? null : usercode.trim();
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public void setUsername(String username) {
    this.username = (username == null) ? null : username.trim();
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = (organization == null) ? null : organization.trim();
  }
}
