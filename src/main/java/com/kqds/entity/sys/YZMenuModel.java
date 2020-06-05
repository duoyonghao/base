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
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getLeaf() {
		return leaf;
	}

	public void setLeaf(int leaf) {
		this.leaf = leaf;
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<YZMenuModel> getHasmenu() {
		return hasmenu;
	}

	public void setHasmenu(List<YZMenuModel> hasmenu) {
		this.hasmenu = hasmenu;
	}

	public Integer getOrderno() { // ## 这里要写Integer，否则MenuCompartor类报错
									// "Cannot invoke compareTo(int) on the primitive type int"
		return orderno;
	}

	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}

	public List<YZButton> getHasbutton() {
		return hasbutton;
	}

	public void setHasbutton(List<YZButton> hasbutton) {
		this.hasbutton = hasbutton;
	}

}