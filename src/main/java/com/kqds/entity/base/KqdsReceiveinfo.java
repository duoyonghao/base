package com.kqds.entity.base;

public class KqdsReceiveinfo {
  private String seqId;
  
  private String createuser;
  
  private String createtime;
  
  private String usercode;
  
  private String username;
  
  private String deptno;
  
  private String regno;
  
  private String recesort;
  
  private String askperson;
  
  private String detaildesc;
  
  private String failreason1;
  
  private Integer askstatus;
  
  private String organization;
  
  private String devItem;
  
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
  
  public String getDeptno() {
    return this.deptno;
  }
  
  public void setDeptno(String deptno) {
    this.deptno = (deptno == null) ? null : deptno.trim();
  }
  
  public String getRegno() {
    return this.regno;
  }
  
  public void setRegno(String regno) {
    this.regno = (regno == null) ? null : regno.trim();
  }
  
  public String getRecesort() {
    return this.recesort;
  }
  
  public void setRecesort(String recesort) {
    this.recesort = (recesort == null) ? null : recesort.trim();
  }
  
  public String getAskperson() {
    return this.askperson;
  }
  
  public void setAskperson(String askperson) {
    this.askperson = (askperson == null) ? null : askperson.trim();
  }
  
  public String getDetaildesc() {
    return this.detaildesc;
  }
  
  public void setDetaildesc(String detaildesc) {
    this.detaildesc = (detaildesc == null) ? null : detaildesc.trim();
  }
  
  public String getFailreason1() {
    return this.failreason1;
  }
  
  public void setFailreason1(String failreason1) {
    this.failreason1 = (failreason1 == null) ? null : failreason1.trim();
  }
  
  public Integer getAskstatus() {
    return this.askstatus;
  }
  
  public void setAskstatus(Integer askstatus) {
    this.askstatus = askstatus;
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = (organization == null) ? null : organization.trim();
  }
  
  public String getDevItem() {
    return this.devItem;
  }
  
  public void setDevItem(String devItem) {
    this.devItem = (devItem == null) ? null : devItem.trim();
  }
}
