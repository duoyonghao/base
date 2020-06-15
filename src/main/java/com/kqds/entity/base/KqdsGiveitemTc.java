package com.kqds.entity.base;

public class KqdsGiveitemTc {
  private String seqId;
  
  private String createuser;
  
  private String createtime;
  
  private String itemno;
  
  private String name;
  
  private String remark;
  
  private String num;
  
  private String organization;
  
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
  
  public String getItemno() {
    return this.itemno;
  }
  
  public void setItemno(String itemno) {
    this.itemno = (itemno == null) ? null : itemno.trim();
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = (name == null) ? null : name.trim();
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = (remark == null) ? null : remark.trim();
  }
  
  public String getNum() {
    return this.num;
  }
  
  public void setNum(String num) {
    this.num = (num == null) ? null : num.trim();
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = (organization == null) ? null : organization.trim();
  }
  
  public Integer getUseflag() {
    return this.useflag;
  }
  
  public void setUseflag(Integer useflag) {
    this.useflag = useflag;
  }
}
