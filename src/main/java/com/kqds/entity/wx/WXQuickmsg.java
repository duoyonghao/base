package com.kqds.entity.wx;

public class WXQuickmsg {
  private String seqId;
  
  private String msgtype;
  
  private String createtime;
  
  private String createuser;
  
  private String organization;
  
  private String msgcontent;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = (seqId == null) ? null : seqId.trim();
  }
  
  public String getMsgtype() {
    return this.msgtype;
  }
  
  public void setMsgtype(String msgtype) {
    this.msgtype = (msgtype == null) ? null : msgtype.trim();
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
  
  public String getMsgcontent() {
    return this.msgcontent;
  }
  
  public void setMsgcontent(String msgcontent) {
    this.msgcontent = (msgcontent == null) ? null : msgcontent.trim();
  }
}
