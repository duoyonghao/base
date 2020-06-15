package com.kqds.entity.sys;

public class YZButton {
  private String seqId;
  
  private String name;
  
  private String qxName;
  
  private String bz;
  
  private String createtime;
  
  private String createuser;
  
  private String parentid;
  
  private Integer sortno;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = (seqId == null) ? null : seqId.trim();
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = (name == null) ? null : name.trim();
  }
  
  public String getQxName() {
    return this.qxName;
  }
  
  public void setQxName(String qxName) {
    this.qxName = (qxName == null) ? null : qxName.trim();
  }
  
  public String getBz() {
    return this.bz;
  }
  
  public void setBz(String bz) {
    this.bz = (bz == null) ? null : bz.trim();
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
  
  public String getParentid() {
    return this.parentid;
  }
  
  public void setParentid(String parentid) {
    this.parentid = (parentid == null) ? null : parentid.trim();
  }
  
  public Integer getSortno() {
    return this.sortno;
  }
  
  public void setSortno(Integer sortno) {
    this.sortno = sortno;
  }
}
