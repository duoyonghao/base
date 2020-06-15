package com.kqds.entity.wx;

public class WXReceivetext {
  private String seqId;
  
  private String createtime;
  
  private String fromusername;
  
  private String msgid;
  
  private String msgtype;
  
  private String tousername;
  
  private String accountid;
  
  private String usercode;
  
  private Integer ispush;
  
  private String mediaid;
  
  private String picurl;
  
  private String createuser;
  
  private String content;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = (seqId == null) ? null : seqId.trim();
  }
  
  public String getCreatetime() {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime) {
    this.createtime = (createtime == null) ? null : createtime.trim();
  }
  
  public String getFromusername() {
    return this.fromusername;
  }
  
  public void setFromusername(String fromusername) {
    this.fromusername = (fromusername == null) ? null : fromusername.trim();
  }
  
  public String getMsgid() {
    return this.msgid;
  }
  
  public void setMsgid(String msgid) {
    this.msgid = (msgid == null) ? null : msgid.trim();
  }
  
  public String getMsgtype() {
    return this.msgtype;
  }
  
  public void setMsgtype(String msgtype) {
    this.msgtype = (msgtype == null) ? null : msgtype.trim();
  }
  
  public String getTousername() {
    return this.tousername;
  }
  
  public void setTousername(String tousername) {
    this.tousername = (tousername == null) ? null : tousername.trim();
  }
  
  public String getAccountid() {
    return this.accountid;
  }
  
  public void setAccountid(String accountid) {
    this.accountid = (accountid == null) ? null : accountid.trim();
  }
  
  public String getUsercode() {
    return this.usercode;
  }
  
  public void setUsercode(String usercode) {
    this.usercode = (usercode == null) ? null : usercode.trim();
  }
  
  public Integer getIspush() {
    return this.ispush;
  }
  
  public void setIspush(Integer ispush) {
    this.ispush = ispush;
  }
  
  public String getMediaid() {
    return this.mediaid;
  }
  
  public void setMediaid(String mediaid) {
    this.mediaid = (mediaid == null) ? null : mediaid.trim();
  }
  
  public String getPicurl() {
    return this.picurl;
  }
  
  public void setPicurl(String picurl) {
    this.picurl = (picurl == null) ? null : picurl.trim();
  }
  
  public String getCreateuser() {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser) {
    this.createuser = (createuser == null) ? null : createuser.trim();
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = (content == null) ? null : content.trim();
  }
}
