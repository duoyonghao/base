package com.kqds.entity.sys;

public class YZFunc {
  private String seqId;
  
  private Integer orderno;
  
  private String createtime;
  
  private String createuser;
  
  private String menuId;
  
  private String funcName;
  
  private String funcCode;
  
  private String icon;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = (seqId == null) ? null : seqId.trim();
  }
  
  public Integer getOrderno() {
    return this.orderno;
  }
  
  public void setOrderno(Integer orderno) {
    this.orderno = orderno;
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
  
  public String getMenuId() {
    return this.menuId;
  }
  
  public void setMenuId(String menuId) {
    this.menuId = (menuId == null) ? null : menuId.trim();
  }
  
  public String getFuncName() {
    return this.funcName;
  }
  
  public void setFuncName(String funcName) {
    this.funcName = (funcName == null) ? null : funcName.trim();
  }
  
  public String getFuncCode() {
    return this.funcCode;
  }
  
  public void setFuncCode(String funcCode) {
    this.funcCode = (funcCode == null) ? null : funcCode.trim();
  }
  
  public String getIcon() {
    return this.icon;
  }
  
  public void setIcon(String icon) {
    this.icon = (icon == null) ? null : icon.trim();
  }
}
