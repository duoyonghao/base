package com.hudh.lclj.entity;

/**
 * 临床路径表（记录主订单信息）
 * @author XKY
 *
 */
public class LcljOrder {
	private String id;
	private String orderNumber;
	private String totalTooth;
	private String createtime;
	private String blcode;
	private String remainTooth;
	private String status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getTotalTooth() {
		return totalTooth;
	}
	public void setTotalTooth(String totalTooth) {
		this.totalTooth = totalTooth;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getBlcode() {
		return blcode;
	}
	public void setBlcode(String blcode) {
		this.blcode = blcode;
	}
	public String getRemainTooth() {
		return remainTooth;
	}
	public void setRemainTooth(String remainTooth) {
		this.remainTooth = remainTooth;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}	
