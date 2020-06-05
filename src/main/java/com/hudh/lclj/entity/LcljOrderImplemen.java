package com.hudh.lclj.entity;
/**
 * 项目实施情况表（记录每次手术订单下每一步骤下项目实施情况）
 * @author XKY
 *
 */
public class LcljOrderImplemen {
	private String id;
	private String parentid;//对应临床跟踪表id
	private String ss;//手术操作项
	private String shgc;//术后观察操作项
	private String dy;//戴牙操作项
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getSs() {
		return ss;
	}
	public void setSs(String ss) {
		this.ss = ss;
	}
	public String getShgc() {
		return shgc;
	}
	public void setShgc(String shgc) {
		this.shgc = shgc;
	}
	public String getDy() {
		return dy;
	}
	public void setDy(String dy) {
		this.dy = dy;
	}
	
	
}
