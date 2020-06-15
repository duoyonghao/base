package com.kqds.entity.sys;

public class YZPriv {
  private String seqId;
  
  private String privName;
  
  private Integer privNo;
  
  private String createtime;
  
  private String createuser;
  
  private String organization;
  
  private String funcIdStr;
  
  private String funcbutton;
  
  private String privIdStr;
  
  private String visualPerson;
  
  private String visualDept;
  
  private String visitDept;
  
  private String orderVisualPerson;
  
  private String orderVisualDept;
  
  private String authority;
  
  private String firstFloor;
  
  private String upstair;
  
  public String getFirstFloor() {
    return this.firstFloor;
  }
  
  public void setFirstFloor(String firstFloor) {
    this.firstFloor = firstFloor;
  }
  
  public String getUpstair() {
    return this.upstair;
  }
  
  public void setUpstair(String upstair) {
    this.upstair = upstair;
  }
  
  public String getAuthority() {
    return this.authority;
  }
  
  public void setAuthority(String authority) {
    this.authority = authority;
  }
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = (seqId == null) ? null : seqId.trim();
  }
  
  public String getPrivName() {
    return this.privName;
  }
  
  public void setPrivName(String privName) {
    this.privName = (privName == null) ? null : privName.trim();
  }
  
  public Integer getPrivNo() {
    return this.privNo;
  }
  
  public void setPrivNo(Integer privNo) {
    this.privNo = privNo;
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
  
  public String getFuncIdStr() {
    return this.funcIdStr;
  }
  
  public void setFuncIdStr(String funcIdStr) {
    this.funcIdStr = (funcIdStr == null) ? null : funcIdStr.trim();
  }
  
  public String getFuncbutton() {
    return this.funcbutton;
  }
  
  public void setFuncbutton(String funcbutton) {
    this.funcbutton = (funcbutton == null) ? null : funcbutton.trim();
  }
  
  public String getPrivIdStr() {
    return this.privIdStr;
  }
  
  public void setPrivIdStr(String privIdStr) {
    this.privIdStr = (privIdStr == null) ? null : privIdStr.trim();
  }
  
  public String getVisualPerson() {
    return this.visualPerson;
  }
  
  public void setVisualPerson(String visualPerson) {
    this.visualPerson = (visualPerson == null) ? null : visualPerson.trim();
  }
  
  public String getVisualDept() {
    return this.visualDept;
  }
  
  public void setVisualDept(String visualDept) {
    this.visualDept = (visualDept == null) ? null : visualDept.trim();
  }
  
  public String getVisitDept() {
    return this.visitDept;
  }
  
  public void setVisitDept(String visitDept) {
    this.visitDept = visitDept;
  }
  
  public String getOrderVisualPerson() {
    return this.orderVisualPerson;
  }
  
  public void setOrderVisualPerson(String orderVisualPerson) {
    this.orderVisualPerson = (orderVisualPerson == null) ? null : orderVisualPerson.trim();
  }
  
  public String getOrderVisualDept() {
    return this.orderVisualDept;
  }
  
  public void setOrderVisualDept(String orderVisualDept) {
    this.orderVisualDept = (orderVisualDept == null) ? null : orderVisualDept.trim();
  }
}
