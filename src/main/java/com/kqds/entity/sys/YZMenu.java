package com.kqds.entity.sys;

public class YZMenu {
  private String seqId;
  
  private String createtime;
  
  private String createuser;
  
  private String menuId;
  
  private String menuName;
  
  private String image;
  
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
  
  public String getMenuName() {
    return this.menuName;
  }
  
  public void setMenuName(String menuName) {
    this.menuName = (menuName == null) ? null : menuName.trim();
  }
  
  public String getImage() {
    return this.image;
  }
  
  public void setImage(String image) {
    this.image = (image == null) ? null : image.trim();
  }
}
