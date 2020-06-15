package com.kqds.entity.sys;

import java.util.List;

public class YZMenuModel {
  private String seqId;
  
  private String menuid;
  
  private String parentid;
  
  private String menuname;
  
  private String url;
  
  private String icon;
  
  private int leaf;
  
  private int orderno;
  
  private List<YZMenuModel> hasmenu;
  
  private List<YZButton> hasbutton;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = seqId;
  }
  
  public String getIcon() {
    return this.icon;
  }
  
  public void setIcon(String icon) {
    this.icon = icon;
  }
  
  public int getLeaf() {
    return this.leaf;
  }
  
  public void setLeaf(int leaf) {
    this.leaf = leaf;
  }
  
  public String getMenuid() {
    return this.menuid;
  }
  
  public void setMenuid(String menuid) {
    this.menuid = menuid;
  }
  
  public String getParentid() {
    return this.parentid;
  }
  
  public void setParentid(String parentid) {
    this.parentid = parentid;
  }
  
  public String getMenuname() {
    return this.menuname;
  }
  
  public void setMenuname(String menuname) {
    this.menuname = menuname;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public List<YZMenuModel> getHasmenu() {
    return this.hasmenu;
  }
  
  public void setHasmenu(List<YZMenuModel> hasmenu) {
    this.hasmenu = hasmenu;
  }
  
  public Integer getOrderno() {
    return Integer.valueOf(this.orderno);
  }
  
  public void setOrderno(int orderno) {
    this.orderno = orderno;
  }
  
  public List<YZButton> getHasbutton() {
    return this.hasbutton;
  }
  
  public void setHasbutton(List<YZButton> hasbutton) {
    this.hasbutton = hasbutton;
  }
}
