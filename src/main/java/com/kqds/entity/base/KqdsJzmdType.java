package com.kqds.entity.base;

public class KqdsJzmdType {
  private String seqId;
  
  private String createuser;
  
  private String createtime;
  
  private String jzmd;
  
  private String jzmdchildname;
  
  private String txjzmd;
  
  private String organization;
  
  private Integer yxday;
  
  private Integer useflag;
  
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
  
  public String getJzmd() {
    return this.jzmd;
  }
  
  public void setJzmd(String jzmd) {
    this.jzmd = (jzmd == null) ? null : jzmd.trim();
  }
  
  public String getJzmdchildname() {
    return this.jzmdchildname;
  }
  
  public void setJzmdchildname(String jzmdchildname) {
    this.jzmdchildname = (jzmdchildname == null) ? null : jzmdchildname.trim();
  }
  
  public String getTxjzmd() {
    return this.txjzmd;
  }
  
  public void setTxjzmd(String txjzmd) {
    this.txjzmd = (txjzmd == null) ? null : txjzmd.trim();
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = (organization == null) ? null : organization.trim();
  }
  
  public Integer getYxday() {
    return this.yxday;
  }
  
  public void setYxday(Integer yxday) {
    this.yxday = yxday;
  }
  
  public Integer getUseflag() {
    return this.useflag;
  }
  
  public void setUseflag(Integer useflag) {
    this.useflag = useflag;
  }
}
