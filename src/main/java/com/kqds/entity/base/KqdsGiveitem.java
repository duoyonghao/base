package com.kqds.entity.base;

public class KqdsGiveitem {
  private String seqId;
  
  private String createuser;
  
  private String createtime;
  
  private String itemno;
  
  private String itemname;
  
  private String unit;
  
  private String unitprice;
  
  private Integer sortno;
  
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
  
  public String getItemname() {
    return this.itemname;
  }
  
  public void setItemname(String itemname) {
    this.itemname = (itemname == null) ? null : itemname.trim();
  }
  
  public String getUnit() {
    return this.unit;
  }
  
  public void setUnit(String unit) {
    this.unit = (unit == null) ? null : unit.trim();
  }
  
  public String getUnitprice() {
    return this.unitprice;
  }
  
  public void setUnitprice(String unitprice) {
    this.unitprice = (unitprice == null) ? null : unitprice.trim();
  }
  
  public Integer getSortno() {
    return this.sortno;
  }
  
  public void setSortno(Integer sortno) {
    this.sortno = sortno;
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
